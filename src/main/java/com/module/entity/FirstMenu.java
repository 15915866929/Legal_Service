package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LJJ on 2017/7/26.
 * 一级菜单表
 */
@Getter
@Setter
@TableName("first_menu")
public class FirstMenu extends BaseModel{

    private String firstMenu_Id;
    private String icon; //图标
    private String firstMname; //一级菜单名字
    private String URL; //地址
    private Integer status; //是否可用 0不可用 1可用
    private String secondMids;
    private Integer independence = 0; //是否独立 1独立(没有2级菜单也显示) 默认非独立
    private Integer idx;  //菜单排序

}
