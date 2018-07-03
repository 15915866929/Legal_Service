package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author hsj
 * @date 2017/11/16
 * 法律援助处理情况表
 */
@Getter
@Setter
@TableName("legal_aid_situation")
public class LegalAidSituation extends BaseModel {

    /**
     * 表id
     */
    private String LegalAidSituation_Id;
    /**
     * 法律援助申请表id
     */
    private String legalAid_Id;
    /**
     * 处理时间
     */
    private Date dealTime;
    /**
     * 处理信息
     */
    private String message;
    /**
     * 处理情况 0申请中 1处理中 2已处理
     */
    private Integer status;
    /**
     * 操作员(userInfo_Id)
     */
    private String operatorId;
    private String operatorName;
    /**
     * 提交时间
     */
    private Date ctime;

}
