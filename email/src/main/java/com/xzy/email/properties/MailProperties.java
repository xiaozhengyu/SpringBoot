package com.xzy.email.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xzy
 * @date 2020-01-27 12:27
 * 说明：保存系统Mail配置，便于程序中灵活使用。
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    /**
     * 邮箱服务器地址
     */
    private String host;
    /**
     * 邮箱服务器端口
     */
    private Integer port;
    /**
     * 邮箱
     */
    private String username;
    /**
     * 授权码
     */
    private String password;
}
