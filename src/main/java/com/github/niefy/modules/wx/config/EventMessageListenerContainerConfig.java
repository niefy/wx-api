package com.github.niefy.modules.wx.config;

import com.github.niefy.modules.wx.listener.WxAccountChangedMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: cheng.tang
 * @Date: 2023/11/11
 * @Description: wx-api
 */
@Configuration
public class EventMessageListenerContainerConfig {

    public static final String WX_ACCOUNT_UPDATE = "event_wx_accounts_changed";

    @Autowired
    private WxAccountChangedMessageListener wxAccountChangedMessageListener;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(600);
        executor.setThreadNamePrefix("rEvent-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.setTaskExecutor(threadPoolTaskExecutor);
        container.addMessageListener(wxAccountChangedMessageListener, new ChannelTopic(WX_ACCOUNT_UPDATE));
        return container;
    }


}
