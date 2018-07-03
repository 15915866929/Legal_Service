package com.module.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.module.entity.MediateArticle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediateArticleMapper extends BaseMapper<MediateArticle> {

    List<MediateArticle> selectAll();
}
