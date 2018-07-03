package com.module.service;


import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.exception.ResultException;
import com.core.util.DateUtils;
import com.core.util.IdGenerator;
import com.module.entity.LegalAidGuide;
import com.module.mapper.LegalAidGuideMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LegalAidGuideService extends BaseModelService<LegalAidGuideMapper,LegalAidGuide> {

    @Autowired
    private LegalAidGuideMapper LegalAidGuideMapper;

    public void addLegalAidGuide(String operatorId, String materialHtml, String processHtml, String chargeHtml, List<String> fileIdsList){
        LegalAidGuide legalAidGuide = new LegalAidGuide();
        legalAidGuide.setLegalAidGuide_Id(IdGenerator.generate());
        legalAidGuide.setId(legalAidGuide.getLegalAidGuide_Id());
        legalAidGuide.setCreater(operatorId);
        legalAidGuide.setLastUpdateOperator(operatorId);
        legalAidGuide.setCtime(new Date());
        legalAidGuide.setLastUpdateTime(new Date());
        legalAidGuide.setMaterialHtml(materialHtml);
        legalAidGuide.setProcessHtml(processHtml);
        legalAidGuide.setChargeHtml(chargeHtml);
        String fileIds = "";
        if(fileIdsList!=null && fileIdsList.size()>0){
            for (String s : fileIdsList) {
                fileIds += s+",";
            }
        }
        legalAidGuide.setFileIds(fileIds);
        legalAidGuide.setStatus(1);
        this.insert(legalAidGuide);
    }

    public LegalAidGuide findLegalAidGuide(){
//        Condition condition = Condition.create();
//        condition.addExpression(Expression.eq("status",1));
        List<LegalAidGuide> legalAidGuideList = this.LegalAidGuideMapper.selectByStatus(1);
        if(legalAidGuideList!=null && legalAidGuideList.size()>0){
            return legalAidGuideList.get(0);
        }
        return null;
    }

    public void updateLegalAidGuide(String operatorId, String legalAidGuide_Id, String materialHtml, String processHtml, String chargeHtml, String fileIds) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("legalAidGuide_Id",legalAidGuide_Id));
        condition.addExpression(Expression.eq("status",1));
        List<LegalAidGuide> legalAidGuideList = this.selectAll(condition);
        if(legalAidGuideList==null || legalAidGuideList.size()==0){
            throw new ResultException("异常！没有该法律援助办理指南！");
        }
        LegalAidGuide legalAidGuide = legalAidGuideList.get(0);
        legalAidGuide.setMaterialHtml(materialHtml);
        legalAidGuide.setProcessHtml(processHtml);
        legalAidGuide.setChargeHtml(chargeHtml);
        legalAidGuide.setFileIds(fileIds);
        legalAidGuide.setLastUpdateOperator(operatorId);
        legalAidGuide.setLastUpdateTime(new Date());
        this.updateById(legalAidGuide);

    }

}
