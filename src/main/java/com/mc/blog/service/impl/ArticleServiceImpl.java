package com.mc.blogadmin.service.impl;

import com.mc.blogadmin.map.ArticleAndDetailMap;
import com.mc.blogadmin.mapper.ArticleMapper;
import com.mc.blogadmin.model.dto.ArticleDTO;
import com.mc.blogadmin.model.po.ArticleDetailPO;
import com.mc.blogadmin.model.po.ArticlePO;
import com.mc.blogadmin.service.ArticleService;
import com.mc.blogadmin.settings.ArticleSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final ArticleSettings articleSettings;

/*    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }*/

    @Override
    public List<ArticlePO> queryTopArticle() {
        Integer lowRead = articleSettings.getLowRead();
        Integer topRead = articleSettings.getTopRead();
        return articleMapper.topSortByReadCount(lowRead,topRead);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addArticle(ArticleDTO articleDTO) {
        ArticlePO articlePO = new ArticlePO();
        articlePO.setUserId(new Random().nextInt(5000));
        articlePO.setTitle(articleDTO.getTitle());
        articlePO.setReadCount(new Random().nextInt(1000));
        articlePO.setSummary(articleDTO.getSummary());
        articlePO.setCreateTime(LocalDateTime.now());
        articlePO.setUpdateTime(LocalDateTime.now());
        int addArticle = articleMapper.insertArticle(articlePO);
        ArticleDetailPO articleDetailPO = new ArticleDetailPO();
        articleDetailPO.setArticleId(articlePO.getId());
        articleDetailPO.setContent(articleDTO.getContent());
        int addDetail = articleMapper.insertArticleDetail(articleDetailPO);
        return (addArticle + addDetail) == 2;
    }

    @Override
    public ArticleDTO queryByArticleId(Integer id) {
        //文章属性、内容
        ArticleAndDetailMap mapper = articleMapper.selectArticleDetail(id);
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(mapper.getId());
        articleDTO.setTitle(mapper.getTitle());
        articleDTO.setSummary(mapper.getSummary());
        articleDTO.setContent(mapper.getContent());
        return articleDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean modifyArticle(ArticleDTO articleDTO) {
        //修改文章属性
        ArticlePO articlePO = new ArticlePO();
        articlePO.setId(articleDTO.getId());
        articlePO.setTitle(articleDTO.getTitle());
        articlePO.setSummary(articleDTO.getSummary());
        articlePO.setUpdateTime(LocalDateTime.now());
        int article = articleMapper.updateArticle(articlePO);
        //修改文章内容
        ArticleDetailPO articleDetailPO = new ArticleDetailPO();
        articleDetailPO.setArticleId(articleDTO.getId());
        articleDetailPO.setContent(articleDTO.getContent());
        int detail = articleMapper.updateArticleDetail(articleDetailPO);
        return article+detail>=1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeArticle(List<Integer> idList) {
        //删除文章属性
        int delArt = articleMapper.deleteArticle(idList);
        //删除文章内容
        int delDet = articleMapper.deleteDetail(idList);
        return delArt+delDet==2;
    }

    @Override
    public String queryTopContent(Integer id) {
        ArticleDetailPO articleDetailPO = articleMapper.selectArticleDetailByArticleId(id);
        String content = "无内容";
        if(articleDetailPO!=null){
            content = articleDetailPO.getContent();
            if(StringUtils.hasText((content))){
                content=content.substring(0,content.length() >=100?100:content.length());
            }
        }
        return content;
    }
}
