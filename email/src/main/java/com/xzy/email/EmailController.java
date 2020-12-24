package com.xzy.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xzy
 * @date 2020-12-16 11:01
 * 说明：
 */
@RestController
@RequestMapping("mail/")
public class EmailController {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("send")
    public String send() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("maiis_alert@iport.com.cn");
        mailMessage.setTo("zhengyupython@qq.com");
        mailMessage.setSubject("主题：简单邮件");
        mailMessage.setText("邮件内容");
        mailSender.send(mailMessage);
        return "OK";
    }
}