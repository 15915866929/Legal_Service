package com.module.service;

import com.aliyuncs.exceptions.ClientException;
import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Order;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.core.util.DateUtils;
import com.core.util.IdGenerator;
import com.core.util.StringUtils;
import com.module.entity.LegalAid;
import com.module.entity.LegalAidSituation;
import com.module.entity.Role;
import com.module.entity.UserInfo;
import com.module.mapper.LegalAidMapper;
import com.module.protocol.legalAid.DealWithLegalAidReqData;
import com.module.returnModel.ReturnLegalAidOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author hsj
 * @date 2017/11/03
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class LegalAidService extends BaseModelService<LegalAidMapper,LegalAid> {

    @Autowired
    private LegalAidSituationService legalAidSituationService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SmsAliyunService smsAliyunService;
    @Autowired
    private SmsService smsService;

    /**
     * @param page 页码
     * @param pageSize 每页数量
     * @param sort 排序字段
     * @param order 排序顺序 1升序-1降序
     * @param fieldName 搜索条件
     * @param value 搜索条件的值
     * @return
     */
    public HashMap findLegalAidList(Integer page, Integer pageSize, String sort, Integer order, String fieldName, String value){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        if(order!=null && StringUtils.isNotEmpty(sort)){
            condition.addOrder(sort,order);
        }
        if(StringUtils.isNotEmpty(fieldName) && StringUtils.isNotEmpty(value)){
            if("applicant_name".equals(fieldName)){
                condition.addExpression(Expression.like("applicant_name",value));
            }else if("phone".equals(fieldName)){
                condition.addExpression(Expression.like("phone",value));
            }else if("ctime".equals(fieldName)){
                String startTime = value+ " 00:00:00";
                String endTime = value+ " 23:59:59";
                Date startDate = DateUtils.stringToDate(startTime,"yyyy-MM-dd HH:mm:ss");
                Date endDate = DateUtils.stringToDate(endTime,"yyyy-MM-dd HH:mm:ss");
                condition.addExpression(Expression.ge("ctime",startTime));
                condition.addExpression(Expression.le("ctime",endDate));
            }
        }
        Page<LegalAid> legalAidPage = this.selectPage(condition);
        hashMap.put("list", legalAidPage.getEntities());
        hashMap.put("total", legalAidPage.getEntityCount());
        return hashMap;
    }

    /**
     * @param legalAid_Id LegalAid表ID
     * @return
     */
    public LegalAid findLegalAidDetail(String legalAid_Id){
        LegalAid legalAid = this.select(legalAid_Id);
        return legalAid;
    }

    public void dealWithLegalAid(String operatorId, DealWithLegalAidReqData reqData) {
        String legalAid_Id = reqData.getLegalAid_Id();
        Date dealTime = reqData.getDealTime();
        String message = reqData.getMessage();
        Integer status = reqData.getStatus();



        LegalAidSituation las = new LegalAidSituation();
        las.setLegalAidSituation_Id(IdGenerator.generate());
        las.setId(las.getLegalAidSituation_Id());
        las.setOperatorId(operatorId);
        las.setLegalAid_Id(legalAid_Id);
        las.setDealTime(dealTime);
        las.setMessage(message);
        las.setStatus(status);
        las.setCtime(new Date());
        this.legalAidSituationService.insert(las);
        LegalAid legalAid = this.select(legalAid_Id);
        legalAid.setStatus(status);
        legalAid.setDealTime(dealTime);
        legalAid.setDealNote(reqData.getDealNote());
        legalAid.setAcceptNote(reqData.getAcceptNote());
        legalAid.setAcceptTime(reqData.getAcceptTime());
        this.updateById(legalAid);
        try {
            Map map = new HashMap(16);
            map.put("type","法律援助预约");
            if (status == 1) {
                //操作员已接手申请 正在处理中
                map.put("date","两");
                //map.put("product","abcdefg");
                smsAliyunService.sendSms("SMS_115035012", map, legalAid.getPhone());
            }else if (status == 2){
                //操作员已处理申请
                smsAliyunService.sendSms("SMS_115035018", map, legalAid.getPhone());
            }
        } catch (ClientException e) {
            e.getStackTrace();
            throw new ResultException("发送短信失败");
        }
    }

    public List<LegalAidSituation> findLegalAidSituationList(String legalAid_Id){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("legalAid_Id",legalAid_Id));
        condition.addOrder(Order.desc("ctime"));
        List<LegalAidSituation> legalAidSituationList = this.legalAidSituationService.selectAll(condition);
        return legalAidSituationList;
    }

    public HashMap findOperatorList(String operatorId, String sortField, Integer order, Integer page, Integer pageSize, String name){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.addExpression(Expression.gt("type",2));
        condition.addExpression(Expression.eq("department_type",1));
        condition.addExpression(Expression.eq("status",1));
        if (StringUtils.isNotEmpty(name)){
            condition.addExpression(Expression.like("uname",name));
        }
        if (StringUtils.isNotEmpty(sortField) && order != null) {
            condition.addOrder(sortField,order);
        }
        List<UserInfo> userInfoList;
        if (page != null && pageSize != null) {
            condition.setPageSize(pageSize);
            condition.setPageNo(page);
            Page<UserInfo> userInfoPage = this.userInfoService.selectPage(condition);
            userInfoList = userInfoPage.getEntities();
            hashMap.put("total", userInfoPage.getEntityCount());
        }else {
            userInfoList = this.userInfoService.selectAll(condition);
            hashMap.put("total", userInfoList.size());
        }

        List<ReturnLegalAidOperator> operatorList = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            ReturnLegalAidOperator operator = new ReturnLegalAidOperator();
            operator.setUserInfo_Id(userInfo.getId());
            operator.setUname(userInfo.getUname());
            Role role = this.roleService.select(userInfo.getRole_Id());
            operator.setRoleName(role.getRoleName());
            operator.setContactPhone(userInfo.getContactPhone());
            operator.setCan_receive_SMS(userInfo.getCan_receive_SMS());
            operatorList.add(operator);
        }
        hashMap.put("list", operatorList);
        return hashMap;
    }

    public void updateReceiveStatus(String userInfo_Id, Integer can_receive_SMS){
        UserInfo userInfo = this.userInfoService.select(userInfo_Id);
        userInfo.setCan_receive_SMS(can_receive_SMS);
        this.userInfoService.updateById(userInfo);
    }

}
