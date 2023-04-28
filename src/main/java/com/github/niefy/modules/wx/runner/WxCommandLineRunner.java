package com.github.niefy.modules.wx.runner;

import com.github.niefy.modules.wx.service.WxAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WxCommandLineRunner implements CommandLineRunner {
    @Autowired
    WxAccountService wxAccountService;
    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(10*1000);
        wxAccountService.loadWxMpConfigStorages();
    }
}
