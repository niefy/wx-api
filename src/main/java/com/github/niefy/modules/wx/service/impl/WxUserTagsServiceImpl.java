package com.github.niefy.modules.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.niefy.modules.sys.entity.SysUserEntity;
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
    private WxMpService wxMpService;
    @Autowired
    private WxUserService wxUserService;
    public static final String CACHE_KEY="'WX_USER_TAGS'";

    @Override
    @Cacheable(key = CACHE_KEY+"+ #appid")
    public List<WxUserTag> getWxTags(String appid) throws WxErrorException {
        log.info("拉取公众号用户标签");
        wxMpService.switchoverTo(appid);
        return wxMpService.getUserTagService().tagGet();
    }

    @Override
    @CacheEvict(key = CACHE_KEY+"+ #appid")
    public void creatTag(String appid, String name) throws WxErrorException {
        wxMpService.switchoverTo(appid);
        wxMpService.getUserTagService().tagCreate(name);
    }

    @Override
    @CacheEvict(key = CACHE_KEY+"+ #appid")
    public void updateTag(String appid, Long tagid, String name) throws WxErrorException {
        wxMpService.switchoverTo(appid);
        wxMpService.getUserTagService().tagUpdate(tagid,name);
    }

    @Override
    @CacheEvict(key = CACHE_KEY+"+ #appid")
    public void deleteTag(String appid, Long tagid) throws WxErrorException {
        wxMpService.switchoverTo(appid);
        wxMpService.getUserTagService().tagDelete(tagid);
    }

    @Override
    public void batchTagging(String appid, Long tagid, String[] openidList) throws WxErrorException {
        wxMpService.switchoverTo(appid);
        wxMpService.getUserTagService().batchTagging(tagid,openidList);
        wxUserService.refreshUserInfoAsync(openidList,appid);//标签更新后更新对应用户信息
    }

    @Override
    public void batchUnTagging(String appid, Long tagid, String[] openidList) throws WxErrorException {
        wxMpService.switchoverTo(appid);
        wxMpService.getUserTagService().batchUntagging(tagid,openidList);
        wxUserService.refreshUserInfoAsync(openidList,appid);//标签更新后更新对应用户信息
    }

    @Override
    public void tagging(Long tagid, String openid) throws WxErrorException {
        wxMpService.getUserTagService().batchTagging(tagid,new String[]{openid});
        String appid = WxMpConfigStorageHolder.get();
        wxUserService.refreshUserInfo(openid,appid);
    }

    @Override
    public void untagging(Long tagid, String openid) throws WxErrorException {
        wxMpService.getUserTagService().batchUntagging(tagid,new String[]{openid});
        String appid = WxMpConfigStorageHolder.get();
        wxUserService.refreshUserInfo(openid,appid);
    }

    @CacheEvict(key = CACHE_KEY + "+ #appid")
    @Override
    public void refreshTagCache(String appid, SysUserEntity sysUserEntity) {
        log.info("refreshTagCache appid {} sysUserEntity {} ", appid, JSON.toJSONString(sysUserEntity));
    }


}
