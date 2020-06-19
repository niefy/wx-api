package com.github.niefy.modules.wx.service.impl;

import com.github.niefy.modules.wx.service.WxUserService;
import com.github.niefy.modules.wx.service.WxUserTagsService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"wxUserTagsServiceCache"})
@Slf4j
public class WxUserTagsServiceImpl implements WxUserTagsService {
    @Autowired
    private WxMpService wxService;
    @Autowired
    private WxUserService wxUserService;
    public static final String CACHE_KEY="'WX_USER_TAGS'";

    @Override
    @Cacheable(key = CACHE_KEY)
    public List<WxUserTag> getWxTags() throws WxErrorException {
        log.info("拉取公众号用户标签");
        return wxService.getUserTagService().tagGet();
    }

    @Override
    @CacheEvict(key = CACHE_KEY)
    public void creatTag(String name) throws WxErrorException {
        wxService.getUserTagService().tagCreate(name);
    }

    @Override
    @CacheEvict(key = CACHE_KEY)
    public void updateTag(Long tagid, String name) throws WxErrorException {
        wxService.getUserTagService().tagUpdate(tagid,name);
    }

    @Override
    @CacheEvict(key = CACHE_KEY)
    public void deleteTag(Long tagid) throws WxErrorException {
        wxService.getUserTagService().tagDelete(tagid);
    }

    @Override
    public void batchTagging(Long tagid, String[] openidList) throws WxErrorException {
        wxService.getUserTagService().batchTagging(tagid,openidList);
        String appid = WxMpConfigStorageHolder.get();
        wxUserService.refreshUserInfoAsync(openidList,appid);//标签更新后更新对应用户信息
    }

    @Override
    public void batchUnTagging(Long tagid, String[] openidList) throws WxErrorException {
        wxService.getUserTagService().batchUntagging(tagid,openidList);
        String appid = WxMpConfigStorageHolder.get();
        wxUserService.refreshUserInfoAsync(openidList,appid);//标签更新后更新对应用户信息
    }

    @Override
    public void tagging(Long tagid, String openid) throws WxErrorException {
        wxService.getUserTagService().batchTagging(tagid,new String[]{openid});
        String appid = WxMpConfigStorageHolder.get();
        wxUserService.refreshUserInfo(openid,appid);
    }

    @Override
    public void untagging(Long tagid, String openid) throws WxErrorException {
        wxService.getUserTagService().batchUntagging(tagid,new String[]{openid});
        String appid = WxMpConfigStorageHolder.get();
        wxUserService.refreshUserInfo(openid,appid);
    }

}
