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
public class ReturnFirstMenuPart {

    private String id;
    private String name;
    private String url;
    private Integer idx;
    private List<ReturnSecondMenu> childMenus;

    public ReturnFirstMenuPart(){}

    public ReturnFirstMenuPart(FirstMenu firstMenu){
        this.id = firstMenu.getFirstMenu_Id();
        this.name = firstMenu.getFirstMname();
        this.url = firstMenu.getURL();
        this.idx = firstMenu.getIdx();
        this.setChildMenus(new ArrayList<>());
    }

    public ReturnFirstMenuPart(FirstMenu firstMenu, List<ReturnSecondMenu> childMenus){
        this.setId(firstMenu.getFirstMenu_Id());
        this.setName(firstMenu.getFirstMname());
        this.setUrl(firstMenu.getURL());
        this.setIdx(firstMenu.getIdx());
        this.childMenus = childMenus;
    }
}
