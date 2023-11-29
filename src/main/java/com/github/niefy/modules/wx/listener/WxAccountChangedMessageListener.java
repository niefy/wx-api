package com.github.niefy.modules.wx.listener;

import com.github.niefy.modules.wx.service.WxAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * @Auther: cheng.tang
 * @Date: 2023/11/11
 * @Description: wx-api
 */
@Service
@Slf4j
public class WxAccountChangedMessageListener implements MessageListener {

    @Autowired
    private WxAccountService wxAccountService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("receiving channel {}  body {} pattern {} ", new String(message.getChannel()), new String(message.getBody()), new String(pattern));
        try {
            wxAccountService.loadWxMpConfigStorages();
            log.info("finish ");
        } catch (Exception e) {
            log.error("消息处理失败了 {} ", e.getMessage());
        }
    }


}
