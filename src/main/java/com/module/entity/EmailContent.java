package com.module.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by LJJ on 2017/11/13.
 */
@Data
public class EmailContent  {

    /**
     * 表id
     */
    private String emailContent_Id;
    /**
     * 标题
     */
    private String title;
    /**
     * 文本内容
     */
    private String text;
    /**
     * 附件(文件id数组)
     */
    private List<String> fileIds;
    /**
     * 最后修改人(userInfo_Id)
     */
    private String lastUpdateOperator;
    private String lastUpdateName;
    /**
     * 最后修改时间
     */
    private String lastUpdateTime;

}
