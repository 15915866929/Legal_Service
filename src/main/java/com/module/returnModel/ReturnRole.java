package com.module.returnModel;

import com.module.entity.Role;
import lombok.Data;

import java.util.Date;

/**
 * @author hsj
 * @date 2017/11/06
 */

@Data
public class ReturnRole {

    /**
     * 角色id
     */
    private String role_Id;
    /**
     * 角色名字
     */
    private String roleName;
    /**
     * 部门名字
     */
    private String departmentName;
    /**
     * 级别
     */
    private Integer type;
    /**
     * 是否可用
     */
    private Integer status;
    /**
     * 最后操作人
     */
    private String lastOperator;
    /**
     * 最后修改时间
     */
    private Date lastUpdateTime;
    /**
     * 备注
     */
    private String note;

    public ReturnRole(){}

    public ReturnRole(Role role){
        this.role_Id = role.getRole_Id();
        this.roleName = role.getRoleName();
        this.status = role.getStatus();
    }
}
