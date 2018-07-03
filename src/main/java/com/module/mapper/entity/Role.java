package com.module.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by LJJ on 2017/7/26.
 * 权限表
 */
@Getter
@Setter
@TableName("role")
public class Role extends BaseModel {

    /**
     * 角色id
     */
    private String role_Id;
    /**
     * 部门id
     */
    private String department_Id;
    /**
     * 上下级关系
     * 1超管 2局长 3主任/科长 4操作员 5调解员
     */
    private Integer type;
    /**
     * 1管理员
     */
    private Integer admin;
    /**
     * 角色名字
     */
    private String roleName;
    /**
     * 是否可用
     */
    private Integer status = 1;
    /**
     * 创建人userInfo_Id
     */
    private String creater;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 备注
     */
    private String note;

    /**
     * 一级菜单ID(数组)
     */
    private String firstMenu_Ids;
    /**
     * 二级菜单ID(数组)
     */
    private String secondMenu_Ids;
    /**
     * 最后更改人(userId)
     */
    private String lastUpdateOperator;
    /**
     * 最后更改时间
     */
    private Date lastUpdateTime;

}
