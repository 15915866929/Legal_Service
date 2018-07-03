package com.module.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author
 * @date 2017/11/07
 * 公证服务预约文章表
 */
@Getter
@Setter
@TableName("notarize_article")
public class NotarizeArticle extends BaseModel {

    private String title;//文章标题

    private String articleHtml;
}
