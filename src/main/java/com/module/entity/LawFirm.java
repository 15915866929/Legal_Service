package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 律师事务所
 * @author chc
 * @create 2017-11-03 9:34
 **/
@Getter
@Setter
@TableName("law_firm")
public class LawFirm extends BaseModel{

    //主键Id
    private String lawFirm_Id;
    //名称
    private String name;
    //镇街ID
    private String street_Id;
    private String streetName;
    //经纬度
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    //事务所地址
    private String address;
    //事务所概况
    private String profile;
    //专业领域
    private String field;
    //律师团队
    private String team;
    //邮件
    private String email;
    //电话
    private String phone;
    //官网
    private String website;
    //图片文件id
    private String imageFileId;
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
