package com.module.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.module.entity.NotarizeArticle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotarizeArticleMapper extends BaseMapper<NotarizeArticle> {

    List<NotarizeArticle> selectAll();
}
