package com.github.niefy.modules.wx.controller;

import com.github.niefy.modules.wx.form.TemplateMsgForm;
import com.github.niefy.modules.wx.service.TemplateMsgService;
import com.github.niefy.common.utils.IPUtils;
import com.github.niefy.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 模板消息
 */
@RestController
@RequestMapping("/templateMsg")
public class TemplateMsgController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TemplateMsgService templateMsgService;

    @PostMapping("/sendToUser")
    public R sendToUser(HttpServletRequest request, @RequestBody TemplateMsgForm form){
        String ip = IPUtils.getIpAddr(request);
        logger.info("发送模板消息，ip={},detail={}",ip,form);
        if(form.isValid())templateMsgService.sendToUser(form);
        return R.ok();
    }
}
