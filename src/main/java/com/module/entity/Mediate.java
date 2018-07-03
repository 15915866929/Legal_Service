package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author
 * @date 2017/11/07
 * 人民调解申请表
 */
@Getter
@Setter
@TableName("mediate")
public class Mediate extends BaseModel {

    //表ID
//    private String mediate_id;
    private String openid;
    //现居住地
    private String address;
    //户籍地
    private String household;
    //所在村居
    private String village;
    //调解事宜
    private String mediate_remark;
    //姓名
    private String name;
    //电话
    private String phone;
    //调解员
    private String mediator;

    private Date ctime;

    private Integer status = 0;        //完成状态 0未开始 1处理中 2已处理

    private String acceptNote;               //接受处理备注

    private String dealNote;               //处理备注

    private Date acceptTime; //处理时间

    private Date dealTime; //处理时间
}
