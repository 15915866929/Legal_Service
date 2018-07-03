package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author hsj
 * @date 2017/11/06
 * 法律援助办理指南表
 */

@Getter
@Setter
@TableName("legal_aid_guide")
public class LegalAidGuide extends BaseModel {

    //表ID
    private String legalAidGuide_Id;
    //提交材料
    private String materialHtml;
    //办理流程
    private String processHtml;
    //收费标准
    private String chargeHtml;
    //表格Id数组
    private String fileIds;
    //状态
    private Integer status;

    //创建人userInfo_Id
    private String creater;
    //创建时间
    private Date ctime;
    //创建与更改记录
    //最后更改人(userId)
    private String lastUpdateOperator;
    //最后更改时间
    private Date lastUpdateTime;

}
