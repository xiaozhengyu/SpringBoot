package com.xzy.activiti;

import lombok.extern.log4j.Log4j2;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xzy
 * @date 2021/1/3 20:45
 * 说明：Activiti组任务
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
class ActivitiGroupTaskTest {
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
                .name("出差申请流程_组任务")
                .addClasspathResource("bpmn/evection_group.bpmn")
                .addClasspathResource("bpmn/evection_group.png")
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
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("evevtion_group");

        // 4.打印信息
        System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
        System.out.println("流程定义Key：" + processInstance.getProcessDefinitionKey());
        System.out.println("流程定义名称：" + processInstance.getProcessDefinitionName());
        System.out.println("流程定义版本号：" + processInstance.getProcessDefinitionVersion());
        System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());

    }

    /**
     * 处理组任务
     *
     * @implNote <userTask id="usertask2" name="经理审批" activiti:candidateUsers="李经理,王经理"></userTask>
     */
    @Test
    void testGroupTaskHandel() {

        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取TaskService：流程任务管理类
        TaskService taskService = processEngine.getTaskService();

        // 3.查询可以处理的组任务
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("evevtion_group")
                .taskCandidateUser("李经理")
                .list();

        // 4.拾取/退回组任务
        for (Task task : taskList) {
            System.out.println("任务ID：" + task.getId() + " 任务名称：" + task.getName() + " 任务负责人：" + task.getAssignee());

            // 拾取组任务：将当前候选人设置为任务的负责人
            taskService.claim(task.getId(), "李经理");

            // 退回组任务：
            // ① taskService.unclaim(task.getId());
            // ② taskService.setAssignee(task.getId(), null);
        }
    }
}
