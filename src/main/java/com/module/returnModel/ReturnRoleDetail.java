package com.module.returnModel;

import lombok.Data;

import java.util.List;

/**
 * @author hsj
 * @date 2017/11/06
 */

@Data
public class ReturnRoleDetail {

    private String roleName;
    private String note;
    private List<ReturnFirstMenu> menus;
    private Integer type;
    private String department_Id;
    public ReturnRoleDetail(){}

}
