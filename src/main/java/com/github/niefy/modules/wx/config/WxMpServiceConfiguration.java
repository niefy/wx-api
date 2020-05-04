package com.github.niefy.modules.wx.config;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import me.chanjar.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceHttpClientImpl;
import me.chanjar.weixin.mp.api.impl.WxMpServiceOkHttpImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpServiceConfiguration {
    private final WxMpProperties properties;

    @Bean
    public WxMpService wxMpService() {
        // 代码里 getConfigs()处报错的同学，请注意仔细阅读项目说明，你的IDE需要引入lombok插件！！！！
        final List<WxMpProperties.MpConfig> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new RuntimeException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
        }
        DefaultApacheHttpClientBuilder httpClientBuilder = DefaultApacheHttpClientBuilder.get();
        httpClientBuilder.setConnectionRequestTimeout(-1);//默认设置情况下多线程并发推送模板消息会有部分线程获取不到连接导致发送失败
        httpClientBuilder.setMaxConnPerHost(50);
        WxMpService service = new WxMpServiceHttpClientImpl();
        service.setMultiConfigStorages(configs
            .stream().map(a -> {
                WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
                configStorage.setAppId(a.getAppId());
                configStorage.setSecret(a.getSecret());
                configStorage.setToken(a.getToken());
                configStorage.setAesKey(a.getAesKey());
                configStorage.setApacheHttpClientBuilder(httpClientBuilder);
                return configStorage;
            }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
        return service;
    }
}
