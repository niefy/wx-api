package com.github.niefy.modules.wx.service;

import com.github.niefy.modules.wx.enums.ArticleTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;
    @Test
    void search() {
        articleService.search(ArticleTypeEnum.QUESTION,"","开户");
    }
}