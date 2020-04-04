package com.github.niefy.modules.wx.service.impl;

import com.github.niefy.modules.wx.dao.WxQrCodeMapper;
import com.github.niefy.modules.wx.entity.WxQrCodeEntity;
import com.github.niefy.modules.wx.form.WxQrCodeForm;
import com.github.niefy.modules.wx.service.WxQrCodeService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import org.springframework.util.StringUtils;


@Service("wxQrCodeService")
@RequiredArgsConstructor
public class WxQrCodeServiceImpl extends ServiceImpl<WxQrCodeMapper, WxQrCodeEntity> implements WxQrCodeService {
    private final WxMpService wxService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String sceneStr = (String) params.get("sceneStr");
        IPage<WxQrCodeEntity> page = this.page(
            new Query<WxQrCodeEntity>().getPage(params),
            new QueryWrapper<WxQrCodeEntity>()
                .like(!StringUtils.isEmpty(sceneStr), "scene_str", sceneStr)
        );

        return new PageUtils(page);
    }

    /**
     * 创建公众号带参二维码
     *
     * @param form
     * @return
     */
    @Override
    public WxMpQrCodeTicket createQrCode(WxQrCodeForm form) throws WxErrorException {
        WxMpQrCodeTicket ticket;
        if (form.getIsTemp()) {//创建临时二维码
            ticket = wxService.getQrcodeService().qrCodeCreateTmpTicket(form.getSceneStr(), form.getExpireSeconds());
        } else {//创建永久二维码
            ticket = wxService.getQrcodeService().qrCodeCreateLastTicket(form.getSceneStr());
        }
        WxQrCodeEntity wxQrCodeEntity = new WxQrCodeEntity(form);
        wxQrCodeEntity.setTicket(ticket.getTicket());
        wxQrCodeEntity.setUrl(ticket.getUrl());
        if (form.getIsTemp()) {
            wxQrCodeEntity.setExpireTime(new Date(new Date().getTime() + ticket.getExpireSeconds() * 1000L));
        }
        this.save(wxQrCodeEntity);
        return ticket;
    }

}
