package com.mc.blogadmin.model.dto;

import lombok.Data;

@Data
public class ArticleDTO {
    private Integer id;
    private String title;
    private String summary;
    private String content;
}
