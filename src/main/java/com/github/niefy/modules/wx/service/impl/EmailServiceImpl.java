package com.github.niefy.modules.wx.service.impl;

import com.github.niefy.modules.wx.config.EmailProperties;
import com.github.niefy.modules.wx.service.EmailService;
import com.github.niefy.common.exception.RRException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
@EnableConfigurationProperties(EmailProperties.class)
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailProperties properties;

    /**
     * 获得邮件会话属性
     */
    private Session getDefaultSession() {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties p = new Properties();
        p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        p.setProperty("mail.smtp.socketFactory.fallback", "false");
        p.put("mail.smtp.host", properties.getHost());
        p.put("mail.smtp.port", properties.getPort());
        p.put("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.socketFactory.port", properties.getPort());
        return Session.getDefaultInstance(p, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getUsername(), properties.getPassword()); //发件人邮件用户名、密码
            }
        });
    }

    @Override
    public void sendMail(String to, String subject, String content, String... files) {
        // 获取默认session对象
        Session session = getDefaultSession();
        try {
            // 创建默认的 MimeMessage 对象。
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(properties.getUsername()));
            // Set To: 头部头字段
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            // Set Subject: 头字段
            message.setSubject(subject);
            // 创建消息部分
            MimeBodyPart textPart = new MimeBodyPart();
            // 消息
            textPart.setContent(content, "text/html;charset=utf-8");
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(textPart);
            for (String filename : files) {// 附件部分
                MimeBodyPart filePart = new MimeBodyPart();
                DataSource source = new FileDataSource(filename);
                filePart.setDataHandler(new DataHandler(source));
                filePart.setFileName(filename);
                multipart.addBodyPart(filePart);
            }
            // 发送完整消息
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RRException("邮件发送失败", e);
        }
    }
}
