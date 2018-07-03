package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * @date 2017/11/07
 * 人民调解文章表
 */
@Getter
@Setter
@TableName("mediate_article")
public class MediateArticle extends BaseModel {

    private String title;//文章标题

    private String articleHtml;
}
