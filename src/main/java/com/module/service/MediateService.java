package com.module.service;

import com.aliyuncs.exceptions.ClientException;
import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.core.util.BeanUtils;
import com.core.util.DateUtils;
import com.core.util.StringUtils;
import com.module.entity.LegalAid;
import com.module.entity.Mediate;
import com.module.entity.UserInfo;
import com.module.mapper.MediateMapper;
import com.module.param.MediateParam;
import com.module.protocol.mediate.DealMediateReqData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人民调解
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class MediateService extends BaseModelService<MediateMapper,Mediate> {

    @Autowired
    private SmsAliyunService smsAliyunService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * @param page 页码
     * @param pageSize 每页数量
     * @return
     */
    public HashMap findMediateList(Integer page, Integer pageSize,UserInfo userInfo){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        if(userInfo.getType()==5){
            condition.addExpression(Expression.eq("mediator",userInfo.getId()));
        }
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        Page<MediateParam> mediatePage = this.selectPage(condition,MediateParam.class);
        for (MediateParam mediateParam : mediatePage.getEntities()) {
            if(StringUtils.isNotEmpty(mediateParam.getMediator())){
                UserInfo mediator = this.userInfoService.select(mediateParam.getMediator());
                mediateParam.setMediator_name(mediator.getUname());
            }

        }
        hashMap.put("list", mediatePage.getEntities());
        hashMap.put("total", mediatePage.getEntityCount());
        return hashMap;
    }

    public MediateParam findMediateDetail(String id){
        Mediate mediate = this.select(id);
        MediateParam mediateParam = new MediateParam();
        BeanUtils.copyPropertiesWithoutNull(mediate,mediateParam);
        if(StringUtils.isNotEmpty(mediateParam.getMediator())){
            UserInfo userInfo = this.userInfoService.select(mediateParam.getMediator());
            mediateParam.setMediator_name(userInfo.getUname());
        }
        return mediateParam;
    }

    public void dealMediate(DealMediateReqData reqData, UserInfo userInfo){
        Mediate mediate = this.select(reqData.getId());
        mediate.setStatus(reqData.getStatus());
        mediate.setAcceptNote(reqData.getAcceptNote());
        mediate.setAcceptTime(reqData.getAcceptTime());
        mediate.setDealNote(reqData.getDealNote());
        mediate.setDealTime(reqData.getDealTime());
        this.updateById(mediate);
        try {
            Map map = new HashMap();
            map.put("type","人民调解");
            if (mediate.getStatus() == 1) {
                //操作员已接手申请 正在处理中
                map.put("date","两");
                //map.put("product","abcdefg");
                smsAliyunService.sendSms("SMS_115035012", map, mediate.getPhone());
            }else if (mediate.getStatus() == 2){
                //操作员已处理申请
                smsAliyunService.sendSms("SMS_115035018", map, mediate.getPhone());
            }
        } catch (ClientException e) {
            e.getStackTrace();
            throw new ResultException("发送短信失败");
        }

    }

    /**
     * 分配调解员
     * @param id
     * @param mediator
     */
    public void assignMediate(String id,String mediator){
        Mediate mediate = this.select(id);
        mediate.setMediator(mediator);
        this.updateById(mediate);
        Map map = new HashMap();
        UserInfo userInfo = this.userInfoService.select(mediate.getMediator());
        map.put("name",userInfo.getUname());
        try {
            smsAliyunService.sendSms("SMS_135032413", map, userInfo.getContactPhone());
        } catch (ClientException e) {
            e.getStackTrace();
            throw new ResultException("发送短信失败");
        }
    }


}
