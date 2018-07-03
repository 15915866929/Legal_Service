package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author hsj
 * @date 2017/11/07
 * 律师表
 */
@Getter
@Setter
@TableName("lawyer")
public class Lawyer extends BaseModel {

    //表ID
    private String lawyer_Id;
    //律师名字
    private String name;
    //所属律师事务所ID
    private String lawFirm_Id;
    private String lawFirmName;
    //所属街道ID
    private String street_Id;
    private String streetName;
    //律师的专业
    private String lawMajors;
//    private List<LawMajor> lawMajors;
    //图片文件id
    private String imageFileId;
    //个人简介
    private String introduction;
    //固定电话
    private String telephone;
    //电话
    private String phone;
    //email
    private String email;
    //qq
    private String qq;

    private String abbreviation;//村委会
    private String glory;//个人荣誉

    private Integer status;

    //创建与更改记录
    //创建人userInfo_Id
    private String creater;
    //创建时间
    private Date ctime;
    //最后更改人(userId)
    private String lastUpdateOperator;
    //最后更改时间
    private Date lastUpdateTime;

}
