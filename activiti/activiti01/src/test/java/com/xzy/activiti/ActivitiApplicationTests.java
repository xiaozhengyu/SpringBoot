package com.xzy.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ActivitiApplicationTests {

    /**
     * 使用activiti默认的方式来创建数据库表
     */
    @Test
    void contextLoads() {
        // 1.获取ProcessEngine实例（借助activiti提供的工具类）
        // getDefaultProcessEngine()会读取resource/activiti.cfg.xml文件。
        // 创建ProcessEngine实例时，按照指定的策略生成数据库表。
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    }

}
