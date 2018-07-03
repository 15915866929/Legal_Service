package com.module.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 部门表
 * @author hsj
 * @date 2017-11-13
 */

@Getter
@Setter
@TableName("department")
public class Department extends BaseModel {

    //表id
    private String department_Id;
    //部门名称
    private String name;
    //状态
    private Integer status;
    /**
     * 1法律援助部门 2....
     */
    private Integer type;
    //
    private String note;
    //管理员(userInfo_Id)
    private String userInfo_Id;
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
