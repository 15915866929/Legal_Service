package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by LJJ on 2017/7/26.
 * 二级菜单表
 */
@Getter
@Setter
@TableName("second_menu")
public class SecondMenu extends BaseModel{

    private String secondMenu_Id;
    private String secondMname; //二级菜单名字
    private String URL; //地址
    private String firstMenu_Id; //一级菜单id
    private Integer status; //是否可用 0不可用 1可用
    private Integer idx;  //菜单排序

}
