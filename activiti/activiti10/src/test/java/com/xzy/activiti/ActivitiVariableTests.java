package com.xzy.activiti;

import com.xzy.activiti.entity.EvectionEntity;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author xzy
 * @date 2021/1/3 17:57
 * 说明：Activiti流程变量
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
class ActivitiVariableTests {

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
     * 在启动流程（创建流程实例）时设置任务负责人、设置流程变量
     */
    @Test
    void setVariableOnProcessStart() {
        // 1.获取ProcessEngine
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取RuntimeService：流程运行管理类
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.启动流程（创建流程实例）,同时指定各个任务的负责人、为流程变量赋值
        Map<String, Object> variablesMap = new HashMap<>(5);
        // 设置任务负责人
        variablesMap.put("assignee1", "张三");
        variablesMap.put("assignee2", "李经理");
        variablesMap.put("assignee3", "王总经理");
        variablesMap.put("assignee4", "赵财务");
        // 赋值流程变量
        variablesMap.put("evection", new EvectionEntity("8a8190dd7303ee0c017305497219004d", "张三", 1d, new Date(), new Date(), ""));
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myEvectionVariable", variablesMap);

        // 4.打印信息
        System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
        System.out.println("流程定义Key：" + processInstance.getProcessDefinitionKey());
        System.out.println("流程定义名称：" + processInstance.getProcessDefinitionName());
        System.out.println("流程定义版本号：" + processInstance.getProcessDefinitionVersion());
        System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());
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
            Map<String, Object> variablesMap = new HashMap<>(1);
            variablesMap.put("evection", new EvectionEntity("8a8190dd7303ee0c017305497219004d", "张三", 1d, new Date(), new Date(), ""));
            taskService.complete(task.getId(), variablesMap);

            System.out.println("任务ID：" + task.getId() + " 任务名称：" + task.getName() + " 任务负责人：" + task.getAssignee());
        }
    }

    /**
     * 通过流程实例设置流程变量
     *
     * @implNote 只有当流程实例还未结束时，才能借助流程实例设置流程变量。
     */
    @Test
    void setVariableByProcessInstance() {
        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取RuntimeService：Activiti运行时管理类
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.通过流程实例设置流程变量
        // ① 一次设置一个变量
        Object value = null;
        runtimeService.setVariable("executionId", "variableName", value);
        // ② 一次设置多个变量
        Map<String, Object> variables = new Hashtable<>();
        runtimeService.setVariables("executionId", variables);
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
        taskService.setVariable("taskId", "variableName", value);
        // ② 一次设置多个变量
        Map<String, Object> variables = new Hashtable<>();
        taskService.setVariables("taskId", variables);
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
