package com.module.returnModel;

import lombok.Data;

import java.util.List;

/**
 * @author hsj
 * @date 2017/11/06
 */
@Data
public class MenuAgg {
    private _id _id;

    private List<ReturnSecondMenu> stus;

    @Data
    public class _id{
        private String firstMenu_Id;
        private String icon;
        private String firstMname;
        private String URL;
        private Integer order;
    }
}
