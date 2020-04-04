package com.github.niefy.modules.wx.manage;

import java.util.Arrays;
import java.util.Map;

import com.github.niefy.modules.wx.entity.Article;
import com.github.niefy.modules.wx.service.ArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.R;


/**
 * 文章
 *
 * @author niefy
 * @email niefy@qq.com
 * @date 2019-11-12 18:30:16
 */
@RestController
@RequestMapping("/manage/article")
public class ArticleManageController {
    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("wx:article:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = articleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:article:info")
    public R info(@PathVariable("id") Integer id) {
        Article article = articleService.getById(id);

        return R.ok().put("article", article);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("wx:article:save")
    public R save(@RequestBody Article article) {
        articleService.save(article);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("wx:article:update")
    public R update(@RequestBody Article article) {
        articleService.updateById(article);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("wx:article:delete")
    public R delete(@RequestBody Integer[] ids) {
        articleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
