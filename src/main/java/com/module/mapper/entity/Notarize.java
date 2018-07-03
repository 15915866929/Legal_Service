package com.module.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author
 * @date 2017/11/07
 * 公证服务预约表
 */
@Getter
@Setter
@TableName("notarize")
public class Notarize extends BaseModel {

    //表ID
//    private String notarize_Id;
    private String openid;
    //预约地址
    private String appoint_address;
    //预约业务
    private String notarize_type;
    //姓名
    private String name;
    //电话
    private String phone;
    //预约时间
    private Date notarize_time;
}
