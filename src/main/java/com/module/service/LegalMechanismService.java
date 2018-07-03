package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Order;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.core.util.BeanUtils;
import com.core.util.DateUtils;
import com.core.util.IdGenerator;
import com.core.util.StringUtils;
import com.module.entity.LegalAid;
import com.module.entity.LegalMechanism;
import com.module.entity.Street;
import com.module.mapper.LegalAidMapper;
import com.module.mapper.LegalMechanismMapper;
import com.module.returnModel.ReturnLegalMechanism;
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
public class LegalMechanismService extends BaseModelService<LegalMechanismMapper,LegalMechanism> {

    @Autowired
    private StreetService streetService;

    /**
     * @param page 页码
     * @param pageSize 每页数量
     * @param sort 排序字段
     * @param order 排序顺序 1升序-1降序
     * @param name 模糊查询(法律援助机构名字)
     * @return
     */
    public HashMap findLegalMechanism(Integer page, Integer pageSize, String sort, Integer order, String name){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        condition.addExpression(Expression.ge("status",1));
        if(order!=null && StringUtils.isNotEmpty(sort)){
            condition.addOrder(sort,order);
        }
        if(StringUtils.isNotEmpty(name)){
            condition.addExpression(Expression.like("name",name));
        }
        Page<LegalMechanism> legalMechanismPage = this.selectPage(condition);
        List<ReturnLegalMechanism> returnList = new ArrayList<>();
        for (LegalMechanism legalMechanism : legalMechanismPage.getEntities()) {
            ReturnLegalMechanism returnLegalMechanism = new ReturnLegalMechanism();
            BeanUtils.copyPropertiesWithoutNull(legalMechanism,returnLegalMechanism);
            if(StringUtils.isNotEmpty(legalMechanism.getStreet_Id())){
                Street street = this.streetService.select(legalMechanism.getStreet_Id());
                returnLegalMechanism.setStreetName(street.getName());
            }
            returnList.add(returnLegalMechanism);
        }
        hashMap.put("list", returnList);
        hashMap.put("total", legalMechanismPage.getEntityCount());
        return hashMap;
    }


    /**
     * @param operatorId 当前操作人(userInfo_Id）
     * @param name 法律援助机构名字
     * @param longitude 经度
     * @param latitude 纬度
     * @param imageFileIdsList 图片文件id(数组)
     * @param address 机构地址
     * @param contact 联系人
     * @param phonesList 电话(数组)
     * @param note 备注
     */
    public void addLegalMechanism(String operatorId, String name, String street_Id, Double longitude,
                                  Double latitude, List<String> imageFileIdsList,String address, String contact,
                                  List<String> phonesList, String note) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("name",name));
        condition.addExpression(Expression.eq("status",1));
        List<LegalMechanism> legalMechanismList = this.selectAll(condition);
        if(legalMechanismList!=null && legalMechanismList.size()>0){
            throw new ResultException("机构已存在，请重新填写");
        }

        LegalMechanism legalMechanism = new LegalMechanism();
        legalMechanism.setLegalMechanism_Id(IdGenerator.generate());
        legalMechanism.setId(legalMechanism.getLegalMechanism_Id());
        legalMechanism.setName(name);
        legalMechanism.setStreet_Id(street_Id);
        legalMechanism.setLongitude(longitude);
        legalMechanism.setLatitude(latitude);
        String imageFileIds = "";
        if(imageFileIdsList!=null && imageFileIdsList.size()>0){
            for (String s : imageFileIdsList) {
                imageFileIds += s+",";
            }
        }
        legalMechanism.setImageFileIds(imageFileIds);
        legalMechanism.setAddress(address);
        legalMechanism.setContact(contact);
        String phones = "";
        if(phonesList!=null && phonesList.size()>0){
            for (String s : phonesList) {
                phones += s+",";
            }
        }
        legalMechanism.setPhones(phones);
        legalMechanism.setNote(note);
        legalMechanism.setCreater(operatorId);
        legalMechanism.setLastUpdateOperator(operatorId);
        legalMechanism.setCtime(new Date());
        legalMechanism.setLastUpdateTime(new Date());
        legalMechanism.setStatus(1);
        this.insert(legalMechanism);
    }

    public void deleteLegalMechanism(String legalMechanism_Id){
        this.deleteById(legalMechanism_Id);
    }

    public LegalMechanism findLegalMechanismDetail(String legalMechanism_Id) {
        LegalMechanism legalMechanism = this.select(legalMechanism_Id);
        if(legalMechanism==null){
            throw new ResultException("异常！没有该法律援助机构！");
        }
        return legalMechanism;
    }

    public void updateLegalMechanism(String operatorId, String legalMechanism_Id, String name, String street_Id,
                                     Double longitude, Double latitude, List<String> imageFileIdsList, String address,
                                     String contact, List<String> phonesList, String note) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("legalMechanism_Id",legalMechanism_Id));
        condition.addExpression(Expression.eq("status",1));
        List<LegalMechanism> legalMechanismList = this.selectAll(condition);
        if(legalMechanismList==null || legalMechanismList.size()==0){
            throw new ResultException("异常！没有该法律援助机构！");
        }
        LegalMechanism legalMechanism = legalMechanismList.get(0);
        legalMechanism.setStreet_Id(street_Id);
        legalMechanism.setName(name);
        legalMechanism.setLongitude(longitude);
        legalMechanism.setLatitude(latitude);
        String imageFileIds = "";
        if(imageFileIdsList!=null && imageFileIdsList.size()>0){
            for (String s : imageFileIdsList) {
                imageFileIds += s+",";
            }
        }
        legalMechanism.setImageFileIds(imageFileIds);
        legalMechanism.setAddress(address);
        legalMechanism.setContact(contact);
        String phones = "";
        if(phonesList!=null && phonesList.size()>0){
            for (String s : phonesList) {
                phones += s+",";
            }
        }
        legalMechanism.setPhones(phones);
        legalMechanism.setNote(note);
        legalMechanism.setLastUpdateOperator(operatorId);
        legalMechanism.setLastUpdateTime(new Date());
        this.updateById(legalMechanism);
    }

    public List<LegalMechanism> findLegalMechanismAgg(Condition condition, String sortField, Integer order, Integer page, Integer pageSize){
        if (StringUtils.isNotEmpty(sortField) && order != null) {
            condition.addOrder(sortField,order);
        }
        List<LegalMechanism> legalMechanismList;
        if (page != null && pageSize != null) {
            condition.setPageSize(pageSize);
            condition.setPageNo(page);
            Page<LegalMechanism> legalMechanismPage = this.selectPage(condition);
            legalMechanismList = legalMechanismPage.getEntities();
        }else {
            legalMechanismList = this.selectAll(condition);
        }
        return legalMechanismList;
    }

}
