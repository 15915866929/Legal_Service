package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.util.IdGenerator;
import com.module.entity.NotarizeArticle;
import com.module.mapper.NotarizeArticleMapper;
import com.module.protocol.notarize.DealNotarizeReqData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公证预约文章
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class NotarizeArticleService extends BaseModelService<NotarizeArticleMapper,NotarizeArticle> {

    @Autowired
    private NotarizeArticleMapper notarizeArticleMapper;

    public List<NotarizeArticle> queryAll(){
        return this.notarizeArticleMapper.selectAll();
    }

    public void add(NotarizeArticle notarizeArticle){
        notarizeArticle.setId(IdGenerator.generate());
        this.insert(notarizeArticle);
    }

    public void update(NotarizeArticle notarizeArticle){
        this.updateById(notarizeArticle);
    }

}
