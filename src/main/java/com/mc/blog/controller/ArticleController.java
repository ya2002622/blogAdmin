package com.mc.blogadmin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mc.blogadmin.formatter.IdType;
import com.mc.blogadmin.handler.exp.IdTypeException;
import com.mc.blogadmin.model.dto.ArticleDTO;
import com.mc.blogadmin.model.param.ArticleParam;
import com.mc.blogadmin.model.po.ArticlePO;
import com.mc.blogadmin.model.vo.ArticleVO;
import com.mc.blogadmin.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping(value={"/","/article/hot"})
    public String showHotArticle(Model model){
        List<ArticlePO> articlePOList = articleService.queryTopArticle();
        List<ArticleVO> articleVOList = BeanUtil.copyToList(articlePOList, ArticleVO.class);
        model.addAttribute("articleList",articleVOList);
        return "/blog/articleList";
    }

    //发布新文章
    @PostMapping("/article/add")
    public String addArticle(@Validated(ArticleParam.AddArticle.class) ArticleParam param){
        //业务逻辑，调用service方法
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setContent(param.getContent());
        articleDTO.setSummary(param.getSummary());
        articleDTO.setTitle(param.getTitle());
        boolean add = articleService.addArticle(articleDTO);
        return "redirect:/article/hot";
    }

    //查询文章内容
    @GetMapping("/article/get")
    public String queryById(Integer id,Model model){
        if(id != null && id > 0){
            ArticleDTO articleDTO = articleService.queryByArticleId(id);
            ArticleVO  articleVO = BeanUtil.copyProperties(articleDTO,ArticleVO.class);
            model.addAttribute("article",articleVO);
            return "/blog/editArticle";
        }else{
            return "/blog/error/error";
        }
    }

    //更新文章
    @PostMapping("/article/edit")
    public String modifyArticle(@Validated(ArticleParam.EditArticle.class)ArticleParam articleParam){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(articleParam.getId());
        articleDTO.setTitle(articleParam.getTitle());
        articleDTO.setSummary(articleParam.getSummary());
        articleDTO.setContent(articleParam.getContent());
        boolean edit = articleService.modifyArticle(articleDTO);
        return "redirect:/article/hot";
    }

    //删除文章
    @PostMapping("/article/remove")
    public String removeArticle(@RequestParam("ids")IdType idType){
        if(idType==null){
            throw new IdTypeException("id为空");
        }
        boolean delete = articleService.removeArticle(idType.getIdList());
        return "redirect:/article/hot";
    }

    @GetMapping("/article/detail/overView")
    @ResponseBody
    public String queryDetail(Integer id){
        String topContent = "无ID";
        if(id!=null&&id>0){
            topContent = articleService.queryTopContent(id);
        }
        return topContent;
    }
}
