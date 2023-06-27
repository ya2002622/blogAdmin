package com.mc.blogadmin.service;

import com.mc.blogadmin.model.dto.ArticleDTO;
import com.mc.blogadmin.model.po.ArticlePO;

import java.util.List;

public interface ArticleService {
    List<ArticlePO> queryTopArticle();

    boolean addArticle(ArticleDTO articleDTO);

    ArticleDTO queryByArticleId(Integer id);

    boolean modifyArticle(ArticleDTO articleDTO);

    boolean removeArticle(List<Integer> idList);

    String queryTopContent(Integer id);
}
