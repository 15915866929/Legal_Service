package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.core.util.IdGenerator;
import com.core.util.StringUtils;
import com.module.entity.LawFirm;
import com.module.entity.Street;
import com.module.mapper.LawFirmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author hsj
 * @date 2017/11/06
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class LawFirmService extends BaseModelService<LawFirmMapper,LawFirm> {

    @Autowired
    private StreetService streetService;

    /**
     * @param page 页码
     * @param pageSize 每页数量
     * @param sort 排序字段
     * @param order 排序顺序 1升序-1降序
     * @param name 模糊查询(事务所名字)
     * @return
     */
    public HashMap findLawFirmList(Integer page, Integer pageSize, String sort, Integer order, String name){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        if (!StringUtils.isEmpty(name)){
            condition.addExpression(Expression.like("name",name));
        }
        if (sort != null && order != null) {
            condition.addOrder(sort,order);
        }
        List<LawFirm> lawFirmList;
        if (page != null && pageSize != null) {
            condition.setPageNo(page);
            condition.setPageSize(pageSize);
            Page<LawFirm> lawFirmPage = this.selectPage(condition);
            lawFirmList = lawFirmPage.getEntities();
            hashMap.put("total", lawFirmPage.getEntityCount());
        }else {
            lawFirmList = this.selectAll(condition);
            hashMap.put("total", lawFirmList.size());
        }
        for (LawFirm lawFirm : lawFirmList) {
            if(StringUtils.isNotEmpty(lawFirm.getStreet_Id())) {
                Street street = this.streetService.select(lawFirm.getStreet_Id());
                lawFirm.setStreetName(street.getName());
            }
        }
        hashMap.put("list", lawFirmList);
        return hashMap;
    }

    public void addLawFirm(String operatorId, String name, String street_Id, Double longitude, Double latitude, String address, String email, String phone, String profile, String team, String field) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("name",name));
        List<LawFirm> lawFirmList = this.selectAll(condition);
        if(lawFirmList!=null && lawFirmList.size()>0){
            throw new ResultException("事务所已存在，请重新填写");
        }
        LawFirm lawFirm = new LawFirm();
        lawFirm.setLawFirm_Id(IdGenerator.generate());
        lawFirm.setId(lawFirm.getLawFirm_Id());
        lawFirm.setName(name);
        lawFirm.setStreet_Id(street_Id);
        lawFirm.setLongitude(longitude);
        lawFirm.setLatitude(latitude);
        lawFirm.setEmail(email);
        lawFirm.setAddress(address);
        lawFirm.setPhone(phone);
        lawFirm.setProfile(profile);
        lawFirm.setTeam(team);
        lawFirm.setField(field);
        lawFirm.setCreater(operatorId);
        lawFirm.setLastUpdateOperator(operatorId);
        lawFirm.setCtime(new Date());
        lawFirm.setLastUpdateTime(new Date());
        lawFirm.setStatus(1);
        this.insert(lawFirm);
    }

    public LawFirm findLawFirmDetail(String lawFirm_Id) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("lawFirm_Id",lawFirm_Id));
        List<LawFirm> lawFirmList = this.selectAll(condition);
        if(lawFirmList==null || lawFirmList.size()==0){
            throw new ResultException("异常！该律师事务所不存在！");
        }
        if(StringUtils.isNotEmpty(lawFirmList.get(0).getStreet_Id())) {
            Street street = this.streetService.select(lawFirmList.get(0).getStreet_Id());
            lawFirmList.get(0).setStreetName(street.getName());
        }
        return lawFirmList.get(0);
    }

    public void updateLawFirm(String operatorId, String lawFirm_Id, String name, String street_Id, Double longitude, Double latitude, String address, String email, String phone, String profile, String team, String field) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("lawFirm_Id",lawFirm_Id));
        List<LawFirm> lawFirmList = this.selectAll(condition);
        if(lawFirmList==null || lawFirmList.size()==0){
            throw new ResultException("异常！该律师事务所不存在！");
        }
        LawFirm lawFirm = lawFirmList.get(0);
        lawFirm.setName(name);
        lawFirm.setStreet_Id(street_Id);
        lawFirm.setLongitude(longitude);
        lawFirm.setLatitude(latitude);
        lawFirm.setAddress(address);
        lawFirm.setEmail(email);
        lawFirm.setPhone(phone);
        lawFirm.setProfile(profile);
        lawFirm.setTeam(team);
        lawFirm.setField(field);
        lawFirm.setLastUpdateOperator(operatorId);
        lawFirm.setLastUpdateTime(new Date());
        this.updateById(lawFirm);
    }

    public void deleteLawFirm(String id){
        LawFirm lawFirm = this.select(id);
        lawFirm.setStatus(0);
        this.updateById(lawFirm);
    }

}
