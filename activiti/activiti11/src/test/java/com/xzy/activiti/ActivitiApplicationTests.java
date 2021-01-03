package com.xzy.activiti;

import lombok.extern.log4j.Log4j2;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
class ActivitiApplicationTests {

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
                .name("出差申请流程")
                .addClasspathResource("bpmn/evection.bpmn")
                .addClasspathResource("bpmn/evection.png")
                .deploy();

        // 5.打印信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署name：" + deployment.getName());

    }

    /**
     * 流程部署：zip包形式
     */
    @Test
    void testZipProcessDeployment() {
        // 1.获取ProcessEngineConfiguration
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

        // 2.获取ProcessEngine
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 3.获取RepositoryService：activiti资源管理类
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 4.使用zip包部署流程
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/evection.zip");
        assert inputStream != null;
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
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
        System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
        System.out.println("流程定义Key：" + processInstance.getProcessDefinitionKey());
        System.out.println("流程定义名称：" + processInstance.getProcessDefinitionName());
        System.out.println("流程定义版本号：" + processInstance.getProcessDefinitionVersion());
        System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());

    }

    /**
     * 查询用户需要处理的任务
     */
    @Test
    void testFindPersonalTaskList() {
        // 1.获取ProcessEngine
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取TaskService：流程任务管理类
        TaskService taskService = processEngine.getTaskService();

        // 3.查询待处理任务
        TaskQuery taskQuery = taskService
                .createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("zhangsan");
        List<Task> taskList = taskQuery.list();
        for (Task task : taskList) {
            System.out.println("流程实例ID：" + task.getProcessInstanceId());
            System.out.println("任务ID：" + task.getId());
            System.out.println("任务名称：" + task.getName());
            System.out.println("任务负责人：" + task.getAssignee());
        }
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
                .processDefinitionKey("myEvection")
                .taskAssignee("zhangsan");
        List<Task> taskList = taskQuery.list();

        // 4.完成任务
        for (Task task : taskList) {
            System.out.println("任务ID：" + task.getId() + " 任务名称：" + task.getName() + " 任务负责人：" + task.getAssignee());
            taskService.complete(task.getId());
        }
    }

    /**
     * 创建流程实例，同时使用businessKey字段关联系统业务表
     */
    @Test
    void testProcessInstanceStartWithBusinessKey() {
        // 1.获取ProcessEngine
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取RuntimeService：流程运行管理类
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.创建、启动一个出差申请流程，并关联出差申请信息（通过出差申请表ID）
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myEvection", "小王出差申请记录ID");

        // 4.打印信息
        System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
        System.out.println("流程定义Key：" + processInstance.getProcessDefinitionKey());
        System.out.println("流程定义名称：" + processInstance.getProcessDefinitionName());
        System.out.println("流程定义版本号：" + processInstance.getProcessDefinitionVersion());
        System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());
        System.out.println("出差申请表ID：" + processInstance.getBusinessKey());
    }

    /**
     * 挂起所有流程实例/挂起整个流程
     */
    @Test
    void testSuspendAllProcessInstance() {
        // 1.获取ProcessEngine
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.RepositoryService：流程资源管理类
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.查询流程定义信息
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myEvection")
                .singleResult();

        // 4.挂起流程
        if (processDefinition != null && !processDefinition.isSuspended()) {
            repositoryService.suspendProcessDefinitionById(processDefinition.getId());
        }
    }

    /**
     * 激化所有流程实例/激化整个流程
     */
    @Test
    void testActivateAllProcessInstance() {
        // 1.获取ProcessEngine
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.RepositoryService：流程资源管理类
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.查询流程定义信息
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myEvection")
                .singleResult();

        // 4.激化流程
        if (processDefinition != null && processDefinition.isSuspended()) {
            repositoryService.activateProcessDefinitionById(processDefinition.getId());
        }
    }

    /**
     * 挂起单个流程实例
     */
    @Test
    void testSuspendSingleProcessInstance() {
        // 1.获取ProcessEngine
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.RuntimeService：流程运行管理
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.获取流程实例
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                // 随便查询一个未被挂起的实例
                .active()
                .singleResult();

        // 4.挂起流程实例
        if (processInstance != null) {
            runtimeService.suspendProcessInstanceById(processInstance.getId());
        }
    }

    /**
     * 激活单个流程实例
     */
    @Test
    void testActivateSingleProcessInstance() {
        // 1.获取ProcessEngine
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.RuntimeService：流程运行管理
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 3.获取流程实例
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                // 随便查询一个被挂起的实例
                .suspended()
                .singleResult();

        // 4.激活流程实例
        if (processInstance != null) {
            runtimeService.activateProcessInstanceById(processInstance.getId());
        }
    }
}
