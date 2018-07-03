package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Order;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.core.util.IdGenerator;
import com.module.entity.Street;
import com.module.mapper.StreetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author hsj
 * @date 2017/11/03
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class StreetService extends BaseModelService<StreetMapper,Street> {

    /**
     * @param operatorId 当前操作人(userInfo_Id）
     */
    public void addStreet(String operatorId, String name) {
        /*if (streets == null || streets.size() == 0) {
            throwBaseException("镇街数组为空");
        }*/
        Street street = new Street();
        street.setStreet_Id(IdGenerator.generate());
        street.setId(street.getStreet_Id());
        street.setName(name);
        street.setCreater(operatorId);
        street.setCtime(new Date());
        street.setLastUpdateOperator(operatorId);
        street.setLastUpdateTime(new Date());
        this.insert(street);
    }

    /**
     * @param name 镇街名字
     * @return
     */
    public boolean checkStreetExist(String name){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("name",name));
        List<Street> streetList = this.selectAll(condition);
        if(streetList!=null && streetList.size()>0){
            return true;
        }
        return false;
    }

    /**
     * @param page 页码
     * @param pageSize 每页数量
     * @param sort 排序字段
     * @param order 排序顺序 1升序-1降序
     * @param name 模糊查询(镇街名字)
     * @return
     */
    public HashMap findStreetList(Integer page, Integer pageSize, String sort, Integer order, String name){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));

        if (!StringUtils.isEmpty(name)){
            condition.addExpression(Expression.like("name",name));
        }
        condition.addOrder(Order.desc("name"));
        if (sort != null && order != null){
            condition.addOrder(sort,order);
        }
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        Page<Street> streetPage = this.selectPage(condition);
        hashMap.put("list", streetPage.getEntities());
        hashMap.put("total", streetPage.getEntityCount());
        return hashMap;
    }

    public void updateStreet(String operatorId, String street_Id, String name) {
        Street street = this.select(street_Id);
        if (street == null) {
            throw new ResultException("异常！没有该镇街信息");
        }
        street.setName(name);
        street.setLastUpdateOperator(operatorId);
        street.setLastUpdateTime(new Date());
        this.updateById(street);
    }

    /*public void banOrEnableStreet(String operatorId, String street_Id, Integer status) throws BaseException {
        Street street = daoFactory.getStreetDao().findStreetById(street_Id);
        if (street == null)
            throwBaseException("异常！没有该镇街信息");
        if (status == 0){

        }
        street.setStatus(status);
        street.setLastUpdateOperator(operatorId);
        street.setLastUpdateTime(DateUtils.currentDatetime());
        daoFactory.getStreetDao().update(street);
    }*/


}
