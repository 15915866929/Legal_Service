package com.module.service;


import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.core.util.BeanUtils;
import com.core.util.IdGenerator;
import com.core.util.StringUtils;
import com.module.entity.LawFirm;
import com.module.entity.Lawyer;
import com.module.entity.Street;
import com.module.mapper.LawyerMapper;
import com.module.protocol.lawyer.AddLawyerReqData;
import com.module.protocol.lawyer.UpdateLawyerReqData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LawyerService extends BaseModelService<LawyerMapper,Lawyer> {

    @Autowired
    private StreetService streetService;
    @Autowired
    private LawFirmService lawFirmService;
    /**
     * @param page 页码
     * @param pageSize 每页数量
     * @param sort 排序字段
     * @param order 排序顺序 1升序-1降序
     * @param name 模糊查询(事务所名字)
     * @param lawFirm_Id 律师事务所ID
     * @param street_Id 街道ID
     * @return
     */
    public HashMap findLawyerList(Integer page, Integer pageSize, String sort, Integer order, String name, String lawFirm_Id, String street_Id){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        if (!StringUtils.isEmpty(name)){
            condition.addExpression(Expression.like("name",name));
        }
        if (!StringUtils.isEmpty(lawFirm_Id)){
            condition.addExpression(Expression.eq("lawFirm_Id",lawFirm_Id));
        }
        if (!StringUtils.isEmpty(street_Id)){
            condition.addExpression(Expression.eq("street_Id",street_Id));
        }
        if (sort != null && order != null) {
            condition.addOrder(sort,order);
        }
        List<Lawyer> lawyerList;
        if (page != null && pageSize != null) {
            condition.setPageNo(page);
            condition.setPageSize(pageSize);
            Page<Lawyer> lawyerPage = this.selectPage(condition);
            lawyerList = lawyerPage.getEntities();
            hashMap.put("total", lawyerPage.getEntityCount());
        }else {
            lawyerList = this.selectAll(condition);
            hashMap.put("total", lawyerList.size());
        }
        for (Lawyer lawyer : lawyerList) {
            if(StringUtils.isNotEmpty(lawyer.getStreet_Id())) {
                Street street = this.streetService.select(lawyer.getStreet_Id());
                lawyer.setStreetName(street.getName());
            }
            if(StringUtils.isNotEmpty(lawyer.getLawFirm_Id())){
                LawFirm lawFirm = this.lawFirmService.select(lawyer.getLawFirm_Id());
                lawyer.setLawFirmName(lawFirm.getName());
            }
        }
        hashMap.put("list", lawyerList);
        return hashMap;
    }

    public void addLawyer(String operatorId, AddLawyerReqData reqData){
        Lawyer lawyer = new Lawyer();
        BeanUtils.copyPropertiesWithoutNull(reqData,lawyer);
        lawyer.setLawyer_Id(IdGenerator.generate());
        lawyer.setId(lawyer.getLawyer_Id());
        lawyer.setCreater(operatorId);
        lawyer.setLastUpdateOperator(operatorId);
        lawyer.setCtime(new Date());
        lawyer.setLastUpdateTime(new Date());
        lawyer.setStatus(1);
        this.insert(lawyer);
    }

    public Lawyer findLawyerDetail(String lawyer_Id) {
        Lawyer lawyer = this.select(lawyer_Id);
        if(lawyer==null){
            throw new ResultException("异常！没有该律师信息");
        }
        if(StringUtils.isNotEmpty(lawyer.getStreet_Id())){
            Street street = this.streetService.select(lawyer.getStreet_Id());
            lawyer.setStreetName(street.getName());
        }
        if(StringUtils.isNotEmpty(lawyer.getLawFirm_Id())){
            LawFirm lawFirm = this.lawFirmService.select(lawyer.getLawFirm_Id());
            lawyer.setLawFirmName(lawFirm.getName());
        }
        return lawyer;
    }

    public void updateLawyer(String operatorId,UpdateLawyerReqData reqData){
        Lawyer lawyer = this.select(reqData.getLawyer_Id());
        BeanUtils.copyPropertiesWithoutNull(reqData,lawyer);
        lawyer.setLastUpdateOperator(operatorId);
        lawyer.setLastUpdateTime(new Date());
        this.updateById(lawyer);
    }

}
