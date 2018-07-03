package com.module.returnModel;

import com.module.entity.FirstMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hsj
 * @date 2017/11/06
 */
@Data
public class ReturnFirstMenu {

    private String id;
    private String icon;
    private String name;
    private String URL;
    private Integer idx;
    private List<ReturnSecondMenu> childMenus;

    public ReturnFirstMenu(){}

    public ReturnFirstMenu(FirstMenu firstMenu){
        this.setId(firstMenu.getFirstMenu_Id());
        this.setIcon(firstMenu.getIcon());
        this.setName(firstMenu.getFirstMname());
        this.setURL(firstMenu.getURL());
        this.setIdx(firstMenu.getIdx());
        this.setChildMenus(new ArrayList<>());
    }

    public ReturnFirstMenu(FirstMenu firstMenu, List<ReturnSecondMenu> childMenus){
        this.setId(firstMenu.getFirstMenu_Id());
        this.setIcon(firstMenu.getIcon());
        this.setName(firstMenu.getFirstMname());
        this.setURL(firstMenu.getURL());
        this.setIdx(firstMenu.getIdx());
        this.childMenus = childMenus;
    }
}
