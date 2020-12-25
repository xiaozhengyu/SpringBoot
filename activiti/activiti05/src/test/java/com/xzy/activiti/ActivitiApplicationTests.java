package com.xzy.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ActivitiApplicationTests {

    /**
     * 流程部署
     */
    @Test
    void testProcessDeployment() {

        // 1.获取ProcessEngineConfiguration
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

    /**
     * 创建流程实例（启动流程）
     */
    @Test
    void testProcessInstanceStart() {
        // Note：创建流程实例前需要先部署流程（具体步骤参照上文的testProcessDeployment()方法）

        // 1.获取ProcessEngine
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取RuntimeService：流程运行管理类
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.根据定义流程时指定的key，创建流程实例（启动流程）
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myEvection");

        // 4.打印信息
        System.out.println("流程定义ID："+processInstance.getProcessDefinitionId());
        System.out.println("流程定义Key："+processInstance.getProcessDefinitionKey());
        System.out.println("流程定义名称："+processInstance.getProcessDefinitionName());
        System.out.println("流程定义版本号："+processInstance.getProcessDefinitionVersion());
        System.out.println("流程实例ID："+processInstance.getProcessInstanceId());

    }

}
