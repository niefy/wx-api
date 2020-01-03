package com.github.niefy.modules.wx.service;

public interface EmailService {
    void sendMail(String to, String subject, String content, String... files);
}
