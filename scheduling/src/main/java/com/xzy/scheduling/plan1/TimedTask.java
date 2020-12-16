package com.xzy.scheduling.plan1;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author xzy
 * @date 2020-12-16 17:23
 * 说明：使用注解实现的定时任务。
 * 优点：简单
 * 缺点：
 */
@EnableScheduling
@Configuration
public class TimedTask {
    private int numberOfExecutions = 1;

    @Scheduled(cron = "00 01 18 * * ?")
    public void execute() {
        System.out.printf("thread id: %d  number of executions: %d\n", Thread.currentThread().getId(), numberOfExecutions++);
    }
}
