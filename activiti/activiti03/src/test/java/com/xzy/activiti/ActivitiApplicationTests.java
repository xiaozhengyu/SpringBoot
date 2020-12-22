package com.xzy.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
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

        //ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("配置文件名");
        //ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("配置文件名","bean的id");
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
    }

}
