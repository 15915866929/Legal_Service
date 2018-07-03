package com.module.returnModel;

import lombok.Data;

import java.util.Date;

/**
 * @author hsj
 * @date 2017/11/06
 */
@Data
public class ReturnAccount {

    //账号
    private String account;
    //id
    private String userInfo_Id;
    //账户人名字
    private String uname;
    //角色名字
    private String roleName;
    //状态
    private Integer status;
    //备注
    private String note;
    //最后登录时间
    private Date lastLoginTime;
    //创建时间
    private Date ctime;

    private Integer pwd_status;

    public ReturnAccount(){}
}
