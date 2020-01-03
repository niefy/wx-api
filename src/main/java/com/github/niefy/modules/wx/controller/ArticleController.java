package com.github.niefy.modules.wx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.niefy.modules.wx.service.ArticleService;
import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.entity.Article;
import com.github.niefy.modules.wx.enums.ArticleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * 文章、公告
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    @RequestMapping("/detail")
    public R getArticle(int articleId){
        Article article = articleService.findById(articleId);
        //sysLogService.addLog(SysOperationEnum.查看文章,"articleId:"+articleId);
        return R.ok().put(article);
    }

    /**
     * 查看目录
     * @param category
     * @return
     */
    @RequestMapping("/category")
    public R getQuestions(String type,String category){
        ArticleTypeEnum articleType=ArticleTypeEnum.of(type);
        if(articleType==null) return R.error("文章类型有误");
        List<Article> articles = articleService.selectCategory(articleType,category);
        return R.ok().put(articles);
    }

    /**
     * 文章搜索
     * @param category
     * @param keywords
     * @return
     */
    @RequestMapping("/search")
    public R getQuestions(String type,
                          @RequestParam(required = false) String category,
                          @RequestParam(required = false)  String keywords){
        ArticleTypeEnum articleType=ArticleTypeEnum.of(type);
        if(articleType==null) return R.error("文章类型有误");
        if(StringUtils.isEmpty(keywords))return R.error("关键词不得为空");
        List<Article> articles = articleService.search(articleType,category,keywords);
        return R.ok().put(articles);
    }

    /**
     * 获取APP通知
     * 通知类型(ArticleTypeEnum)：POPUP(2),NOTICE(3),IMAGE(4)
     * 其中图片通知/文字通知 都存在时图片通知优先，滚动公告可并存
     * @return
     */
    @RequestMapping("/getAppNotice")
    public R getAppNotice(){
        List<Article> list = new LinkedList<>();
        QueryWrapper<Article> wrapper=new QueryWrapper<Article>()
                .apply("start_time<CURRENT_TIME and end_time>CURRENT_TIME")
                .orderByDesc("update_time")
                .last("LIMIT 1");
        //先检查有效的 图片通知/文字通知 选其一
        QueryWrapper<Article> wrapperImage= wrapper.clone();
        wrapperImage.eq("type", ArticleTypeEnum.IMAGE.getValue());
        Article image=articleService.getOne(wrapperImage,false);//图片通知
        if(image!=null){
            list.add(image);
        }else {//如没有图片通知检查文字通知
            QueryWrapper<Article> wrapperPopup= wrapper.clone();
            wrapperPopup.eq("type", ArticleTypeEnum.POPUP.getValue());
            Article popup=articleService.getOne(wrapperPopup,false);//图片通知
            if(popup!=null) list.add(popup);
        }
        //滚动公告
        QueryWrapper<Article> wrapperNotice= wrapper.clone();
        wrapperNotice.eq("type", ArticleTypeEnum.NOTICE.getValue());
        Article notice=articleService.getOne(wrapperNotice,false);
        if(notice!=null)list.add(notice);
        return R.ok().put(list).put("message","SUCCESS");
    }



}
