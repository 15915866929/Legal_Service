package com.module.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author hsj
 * @date 2017/11/07
 */
@Getter
@Setter
@TableName("law_major")
public class LawMajor extends BaseModel {

    //表ID
    private String lawMajor_Id;
    //专业名称
    private String name;
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
