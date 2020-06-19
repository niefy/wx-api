package com.github.niefy.modules.wx.config;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class WxMpServiceConfiguration {

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService =  new WxMpServiceImpl();
        wxMpService.setMaxRetryTimes(3);
        return wxMpService;
    }
}
