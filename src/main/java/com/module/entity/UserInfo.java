package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by LJJ on 2017/8/3.
 */

@Getter
@Setter
@TableName("user_info")
public class UserInfo extends BaseModel{

    /**
     * 用户id
     */
    private String userInfo_Id;
    /**
     * role表 对应的主键id
     */
    private String role_Id;
    /**
     * 部门id
     */
    private String department_Id;
    /**
     * 部门类型
     */
    private Integer department_type;
    /**
     * 是否可以登录  0不可登录 1可登录 -1已删除
     */
    private Integer status = 1;
    /**
     * 姓名
     */
    private String uname;
    /**
     * 备注
     */
    private String note;
    /**
     * 上下级关系
     * 1超管 2局长 3主任/科长 4操作员 5调解员
     */
    private Integer type;
    /**
     * 1管理员
     */
    private Integer admin;

    //基本信息(可不必填)
    /**
     * 1男 2女
     */
    private Integer sex;
    /**
     * 电话
     */
    private String contactPhone;
    private Integer can_receive_SMS;
    /**
     * 民族
     */
    private String nation;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 邮箱
     */
    private String email;
    /**
     * qq
     */
    private String qq;
    /**
     * 身份证
     */
    private String identityCard;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 村委会
     */
    private String abbreviation;

    /**
     * 创建与更改记录
     * 创建人userInfo_Id
     */
    private String creater;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 最后更改人(userId)
     */
    private String lastUpdateOperator;
    /**
     * 最后更改时间
     */
    private Date lastUpdateTime;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    @TableField(exist = false)
    private String uaccount;   //用户账号

}
