package com.mc.blogadmin.model.po;

import lombok.Data;

@Data
public class ArticleDetailPO {
    private Integer id;
    private Integer articleId;
    private String content;
}