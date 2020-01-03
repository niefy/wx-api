package com.github.niefy.modules.wx.config;

import com.github.niefy.common.utils.Json;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email")
@Data
public class EmailProperties {
    private String host;
    private String username;
    private String password;
    private String port;
    @Override
    public String toString() {
        return Json.toJsonString(this);
    }
}
