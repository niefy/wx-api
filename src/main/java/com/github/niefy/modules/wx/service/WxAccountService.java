package com.github.niefy.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.wx.entity.WxAccount;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 公众号账号
 *
 * @author niefy
 * @date 2020-06-17 13:56:51
 */
public interface WxAccountService extends IService<WxAccount> {

    PageUtils queryPage(Map<String, Object> params);

    @Override
    boolean save(WxAccount entity);

    @Override
    boolean removeByIds(Collection<? extends Serializable> idList);
}

