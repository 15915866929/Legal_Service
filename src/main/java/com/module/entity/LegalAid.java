package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by LJJ on 2017/11/3.
 */

/**
 * 法律援助model
 */
@Getter
@Setter
@TableName("legal_aid")
public class LegalAid extends BaseModel {

    private String legalAid_Id;        //表ID
    private String openid;             //申请人openid
    private String applicant_name;     //申请人名字
    private String phone;              //联系方式
    private String address;            //现居住地
    private String domicile;           //户籍地
    private String appeal;             //申请诉求
    private String reason;             //事实与理由
    private Integer status = 0;        //完成状态 0未开始 1处理中 2已处理
    private String note;               //备注
    private Date ctime;              //创建时间

    private String acceptNote;               //接受处理备注

    private String dealNote;               //处理备注

    private Date acceptTime; //处理时间

    private Date dealTime; //处理时间
}
