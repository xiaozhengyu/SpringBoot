package com.xzy.activiti;

import org.activiti.engine.*;
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

        // 1.获取ProcessEngineConfiguration
        //ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("配置文件名");
        //ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("配置文件名","bean的id");
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

        // 2.获取ProcessEngine
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 3.获取相关Service
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        TaskService taskService = processEngine.getTaskService();
        HistoryService historyService = processEngine.getHistoryService();
        ManagementService managementService = processEngine.getManagementService();
    }

}
