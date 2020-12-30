package com.xzy.activiti;

import lombok.extern.log4j.Log4j2;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xzy
 * @date 2020-12-30 19:49
 * 说明：以UEL表达式的方式设置任务负责人
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
class AssigneeUelTest {
    /**
     * 流程部署：bpmn、png形式
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
                .name("出差申请流程_uel1")
                .addClasspathResource("bpmn/evection_uel.bpmn")
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

        // 3.根据定义流程时指定的key，创建流程实例（启动流程）,同时指定各个任务的负责人
        Map<String, Object> assigneeMap = new HashMap<>(3);
        assigneeMap.put("assignee0", "张三");
        assigneeMap.put("assignee1", "李经理");
        assigneeMap.put("assignee2", "王总经理");
        assigneeMap.put("assignee3", "赵财务");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myEvection1", assigneeMap);

        // 4.打印信息
        System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
        System.out.println("流程定义Key：" + processInstance.getProcessDefinitionKey());
        System.out.println("流程定义名称：" + processInstance.getProcessDefinitionName());
        System.out.println("流程定义版本号：" + processInstance.getProcessDefinitionVersion());
        System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());

    }
}
