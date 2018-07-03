package com.module.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 镇街表
 * @author hsj
 * @create 2017-11-04 10:36
 */
@Getter
@Setter
@TableName("street")
public class Street extends BaseModel {

    private String street_Id;   //表ID
    private String name;        //镇街名字
    private Integer status = 1; //0禁用 1可用
    private String creater; //创建人userInfo_Id
    private Date ctime; //创建时间
    //创建与更改记录
    private String lastUpdateOperator;  //最后更改人(userId)
    private Date lastUpdateTime; //最后更改时间

}
