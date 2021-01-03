package com.xzy.activiti;

import lombok.extern.log4j.Log4j2;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author xzy
 * @date 2021/1/3 17:57
 * 说明：Activiti流程变量（局部变量）
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
class ActivitiLocalVariableTests {

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
                .name("出差申请流程_流程变量")
                .addClasspathResource("bpmn/evection_globalVariable.bpmn")
                .addClasspathResource("bpmn/evection_globalVariable.png")
                .deploy();

        // 5.打印信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署name：" + deployment.getName());

    }

    /**
     * 在完成任务时设置流程变量
     */
    @Test
    void setVariableOnTaskComplete() {
        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取TaskService：流程任务管理类
        TaskService taskService = processEngine.getTaskService();

        // 3.查询待处理任务
        TaskQuery taskQuery = taskService
                .createTaskQuery()
                .processDefinitionKey("myEvectionVariable")
                .taskAssignee("李经理");
        List<Task> taskList = taskQuery.list();

        // 4.完成任务，同时设置流程变量
        for (Task task : taskList) {

            // ① 一次设置一个变量
            Object value = null;
            taskService.setVariableLocal("taskId", "variableName", value);

            // ② 一次设置多个变量
            Map<String, Object> variables = new Hashtable<>();
            taskService.setVariablesLocal("taskId", variables);

            taskService.complete(task.getId());
            System.out.println("任务ID：" + task.getId() + " 任务名称：" + task.getName() + " 任务负责人：" + task.getAssignee());
        }
    }

    /**
     * 通过任务实例设置流程变量
     */
    @Test
    void setVariableByTaskInstance() {
        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取TaskService：任务管理类
        TaskService taskService = processEngine.getTaskService();

        // 3.通过任务实例设置流程变量
        // ① 一次设置一个变量
        Object value = null;
        taskService.setVariableLocal("taskId", "variableName", value);
        // ② 一次设置多个变量
        Map<String, Object> variables = new Hashtable<>();
        taskService.setVariablesLocal("taskId", variables);
    }

    /**
     * 完成用户需要处理的任务
     */
    @Test
    void testCompleteTask() {
        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取TaskService：流程任务管理类
        TaskService taskService = processEngine.getTaskService();

        // 3.查询待处理任务
        TaskQuery taskQuery = taskService
                .createTaskQuery()
                .processDefinitionKey("myEvectionVariable")
                .taskAssignee("赵财务");
        List<Task> taskList = taskQuery.list();

        // 4.完成任务
        for (Task task : taskList) {
            System.out.println("任务ID：" + task.getId() + " 任务名称：" + task.getName() + " 任务负责人：" + task.getAssignee());
            taskService.complete(task.getId());
        }
    }
}
