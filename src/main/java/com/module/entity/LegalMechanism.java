package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 法援机构
 * @author chc
 * @create 2017-11-03 9:18
 **/
@Getter
@Setter
@TableName("legal_mechanism")
public class LegalMechanism extends BaseModel {

    //id
    private String legalMechanism_Id;
    //机构名
    private String name;

    //镇街ID
    private String street_Id;

    //经纬度
//    private Location location;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    //机构地址
    private String address;
    //图片文件id
    private String imageFileIds;
    //联系人
    private String contact;
    //电话
    private String phones;
    //备注
    private String note;
    //状态 0禁用 1可用
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
