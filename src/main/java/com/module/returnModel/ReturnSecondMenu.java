package com.module.returnModel;

import com.module.entity.SecondMenu;
import lombok.Data;

/**
 * @author hsj
 * @date 2017/11/06
 */
@Data
public class ReturnSecondMenu {

    private String secondMenu_Id;
    private String secondMname;
    private String URL;
    private Integer idx;  //菜单排序

    public ReturnSecondMenu(SecondMenu secondMenu){
        this.setSecondMenu_Id(secondMenu.getSecondMenu_Id());
        this.setSecondMname(secondMenu.getSecondMname());
        this.setURL(secondMenu.getURL());
        this.setIdx(secondMenu.getIdx());
    }
    public ReturnSecondMenu(){
        super();
    }
}
