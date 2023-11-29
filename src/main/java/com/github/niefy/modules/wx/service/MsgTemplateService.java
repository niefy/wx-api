package com.github.niefy.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.modules.wx.entity.MsgTemplate;

import me.chanjar.weixin.common.error.WxErrorException;

import com.github.niefy.common.utils.PageUtils;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;

import java.util.List;
import java.util.Map;

/**
 * 消息模板
 *
 * @author niefy
 * @email niefy@qq.com
 * @date 2019-11-12 18:30:15
 */
public interface MsgTemplateService extends IService<MsgTemplate> {
    /**
     * 分页查询用户数据
     * @param params 查询参数
     * @return PageUtils 分页结果
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 通过模板名称查询
     *
     * @param name
     * @return
     */
    MsgTemplate selectByName(String name);

    /**
     * 同步公众号已添加的消息模板
     * @throws WxErrorException
     */
    void syncWxTemplate(String appid, List<WxMpTemplate> allPrivateTemplateList) throws WxErrorException;
}

