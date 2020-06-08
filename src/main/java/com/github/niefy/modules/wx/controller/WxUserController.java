package com.github.niefy.modules.wx.controller;

import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.entity.WxUser;
import com.github.niefy.modules.wx.service.WxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wxUser")
public class WxUserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    WxUserService wxUserService;

    @GetMapping("/getUserInfo")
    public R getUserInfo(@CookieValue String openid){
        WxUser wxUser = wxUserService.getById(openid);
        return R.ok().put(wxUser);
    }
}
