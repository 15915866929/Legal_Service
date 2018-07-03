package com.module.service;


import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.core.util.IdGenerator;
import com.core.util.StringUtils;
import com.module.entity.LawMajor;
import com.module.entity.Street;
import com.module.mapper.LawMajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LawMajorService extends BaseModelService<LawMajorMapper,LawMajor> {

    /**
     * @param page 页码
     * @param pageSize 每页数量
     * @param sort 排序字段
     * @param order 排序顺序 1升序-1降序
     * @param name 模糊查询(专业名字)
     * @return
     */
    public HashMap findLawMajorList(Integer page, Integer pageSize, String sort, Integer order, String name){
        HashMap hashMap = new HashMap(16);
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        if (!StringUtils.isEmpty(name)){
            condition.addExpression(Expression.like("name",name));
        }
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        Page<LawMajor> lawMajorPage = this.selectPage(condition);
        hashMap.put("list", lawMajorPage.getEntities());
        hashMap.put("total", lawMajorPage.getEntityCount());
        return hashMap;
    }

    /**
     * @param name 专业名字
     * @return
     */
    public boolean checkLawMajorExist(String name){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("name",name));
        List<LawMajor> lawMajorList = this.selectAll(condition);
        if(lawMajorList!=null && lawMajorList.size()>0){
            return true;
        }else {
            return false;
        }
    }

    public void addLawMajor(String operatorId, List<String> lawMajors) {
        if (lawMajors == null || lawMajors.size() == 0) {
            throw new ResultException("专业数组为空");
        }
        List insertList = new ArrayList();
        for (String name : lawMajors){
            LawMajor lawMajor = new LawMajor();
            lawMajor.setLawMajor_Id(IdGenerator.generate());
            lawMajor.setId(lawMajor.getLawMajor_Id());
            lawMajor.setName(name);
            lawMajor.setStatus(1);
            lawMajor.setCreater(operatorId);
            lawMajor.setLastUpdateOperator(operatorId);
            lawMajor.setCtime(new Date());
            lawMajor.setLastUpdateTime(new Date());
            this.insert(lawMajor);
        }
    }

    public void updateLawMajor(String operatorId, String lawMajor_Id, String name) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("lawMajor_Id",lawMajor_Id));
        List<LawMajor> lawMajorList = this.selectAll(condition);
        if(lawMajorList==null || lawMajorList.size()==0){
            throw new ResultException("异常！没有该专业！");
        }
        LawMajor lawMajor = lawMajorList.get(0);
        lawMajor.setName(name);
        lawMajor.setLastUpdateOperator(operatorId);
        lawMajor.setLastUpdateTime(new Date());
        this.updateById(lawMajor);
    }

}
