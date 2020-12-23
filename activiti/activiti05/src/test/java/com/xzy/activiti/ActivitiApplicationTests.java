package com.xzy.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ActivitiApplicationTests {

    /**
     * 测试流程部署
     */
    @Test
    void testProcessDeployment() {

        // 1.获取ProcessEngineConfiguration
        //ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("配置文件名");
        //ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("配置文件名","bean的id");
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

        // 2.获取ProcessEngine
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 3.获取RepositoryService：activiti资源管理类
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 4.流程部署：定义一个流程名称，把bpmn文件和png文件报文到数据库
        Deployment deployment = repositoryService.createDeployment()
                .name("出差申请流程")
                .addClasspathResource("bpmn/evection.bpmn")
                .addClasspathResource("bpmn/evection.png")
                .deploy();

        // 5.打印信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署name：" + deployment.getName());

    }


}
