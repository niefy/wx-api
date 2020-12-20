package com.github.niefy.modules.wx.controller;

import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.entity.Article;
import com.github.niefy.modules.wx.enums.ArticleTypeEnum;
import com.github.niefy.modules.wx.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * cms文章
 */
@RestController
@RequestMapping("/article")
@Api(tags = {"CMS文章"})
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 查看文章详情
     *
     * @param articleId
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation(value = "文章详情",notes = "")
    public R getArticle(int articleId) {
        Article article = articleService.findById(articleId);
        return R.ok().put(article);
    }

    /**
     * 查看目录
     *
     * @param category
     * @return
     */
    @GetMapping("/category")
    @ApiOperation(value = "目录信息",notes = "")
    public R getQuestions(String type, String category) {
        ArticleTypeEnum articleType = ArticleTypeEnum.of(type);
        if (articleType == null) {
            return R.error("文章类型有误");
        }
        List<Article> articles = articleService.selectCategory(articleType, category);
        return R.ok().put(articles);
    }

    /**
     * 文章搜索
     *
     * @param category
     * @param keywords
     * @return
     */
    @GetMapping("/search")
    @ApiOperation(value = "文章搜索",notes = "")
    public R getQuestions(String type,
                          @RequestParam(required = false) String category,
                          @RequestParam(required = false) String keywords) {
        ArticleTypeEnum articleType = ArticleTypeEnum.of(type);
        if (articleType == null) {
            return R.error("文章类型有误");
        }
        if (!StringUtils.hasText(keywords)) {
            return R.error("关键词不得为空");
        }
        List<Article> articles = articleService.search(articleType, category, keywords);
        return R.ok().put(articles);
    }


}
