package com.xzy.email.util;

import com.xzy.email.properties.MailProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author xzy
 * @date 2020-12-16 11:31
 * 说明：邮件工具类
 */
@Component
@Log4j2
public class MailUtils {
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Autowired
    public MailUtils(JavaMailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    /**
     * 发送邮件
     *
     * @param mailMessage - 待发送的邮件，包含发送人、接收人、主题、发送日期等信息。
     */
    public void send(SimpleMailMessage mailMessage) {
        mailSender.send(mailMessage);
    }

    /**
     * 发送邮件
     *
     * @param subject - 主题
     * @param text    - 内容
     * @param to      - 接收人（单个）
     */
    public void send(String subject, String text, String to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setTo(to);
        mailSender.send(mailMessage);
    }

    /**
     * 发送邮件
     *
     * @param subject - 主题
     * @param text    - 内容
     * @param to      - 接收人（多个）
     */
    public void send(String subject, String text, String[] to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setTo(to);
        mailSender.send(mailMessage);
    }
}
