package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.modules.wx.dao.MsgNewsMapper;
import com.github.niefy.modules.wx.entity.MsgNews;
import com.github.niefy.modules.wx.service.MsgNewsService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MsgNewsServiceImpl extends ServiceImpl<MsgNewsMapper, MsgNews> implements MsgNewsService {
    @Autowired
    MsgNewsMapper msgNewsMapper;

    /**
     * 保存图文消息，数据库不存在时插入，存在时更新
     *
     * @param msgNews
     */
    @Override
    public boolean save(MsgNews msgNews) {
        if (msgNews.getNewsId() > 0) {
            msgNewsMapper.updateById(msgNews);
        } else {
            msgNewsMapper.insert(msgNews);
        }
        return false;
    }

    /**
     * 获取所有的图文消息
     *
     * @return
     */
    @Override
    public List<MsgNews> getMsgNews() {
        return msgNewsMapper.selectList(new QueryWrapper<MsgNews>());
    }

    /**
     * 根据ID列表查询
     *
     * @param ids
     * @return
     */
    @Override
    public List<WxMpKefuMessage.WxArticle> findByIds(String ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        List<MsgNews> msgNewsList = msgNewsMapper.selectList(
            new QueryWrapper<MsgNews>()
                .in("news_id", ids)
                .orderByAsc("`order`"));//使用ID列表查询，结果根据order字段排序
        List<WxMpKefuMessage.WxArticle> newsList =
            msgNewsList.stream().map(msgNews -> {
                WxMpKefuMessage.WxArticle article = new WxMpKefuMessage.WxArticle();
                article.setTitle(msgNews.getTitle());
                article.setDescription(msgNews.getDescription());
                article.setPicUrl(msgNews.getPicUrl());
                article.setUrl(msgNews.getUrl());
                return article;
            }).collect(Collectors.toList());
        return newsList;
    }
}
