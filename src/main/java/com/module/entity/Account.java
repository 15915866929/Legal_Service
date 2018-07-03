package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by LJJ on 2017/7/25.
 * 账户表
 */

@Getter
@Setter
@TableName("account")
public class Account extends BaseModel{

    private String uaccount;   //用户账号
    private String upassword;  //登录的密码
    private String userInfo_Id;  //用户id
    private Integer status = 1;   //是否可以登录  0不可登录 1可登录 -1已删除
    private Date ctime;   //创建时间
    private Integer generalAccount;  //1后台可以的账号 2学生账号
    private String fileId;  //附件

}