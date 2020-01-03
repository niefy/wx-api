package com.github.niefy.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.wx.entity.WxQrCodeEntity;
import com.github.niefy.modules.wx.form.WxQrCodeForm;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.util.Map;

/**
 * 公众号带参二维码
 *
 * @author niefy
 * @email niefy@qq.com
 * @date 2020-01-02 11:11:55
 */
public interface WxQrCodeService extends IService<WxQrCodeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 创建公众号带参二维码
     * @param form
     * @return
     */
    WxMpQrCodeTicket createQrCode(WxQrCodeForm form) throws WxErrorException;
}

