package com.xzy.activiti;

import lombok.extern.log4j.Log4j2;
import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
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
     * 流程定义查询
     */
    @Test
    void testProcessDefinitionQuery() {
        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取RepositoryService：流程管理类
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.获取ProcessDefinitionQuery：流程定义查询对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        // 4.流程定义查询：支持多条件、排序等个性化定制
        List<ProcessDefinition> myEvectionList = processDefinitionQuery
                .processDefinitionKey("myEvection")
                .orderByProcessDefinitionVersion().desc().list();

        // 5.输出相关信息
        for (ProcessDefinition processDefinition : myEvectionList) {
            System.out.println(myEvectionList);
        }
    }

    /**
     * 流程定义删除
     */
    @Test
    void testProcessDefinitionDelete() {
        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取RepositoryService：流程管理类
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.删除流程部署信息
        try {
            repositoryService.deleteDeployment("deploymentId");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // 如果某个流程存在未完成的流程实例，上述删除便无法成功完成，需要进行级联删除。
            repositoryService.deleteDeployment("deploymentId", true);
        }
    }

    /**
     * 资源文件下载
     * 方案1：使用Activiti提供的接口进行下载
     */
    @Test
    void testProcessFileDownload1() throws IOException {
        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3.获取资源文件
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("myEvection")
                .singleResult();

        // 流程部署ID、bpmn文件名、png文件名
        String deploymentId = processDefinition.getDeploymentId();
        String bpmnResourceName = processDefinition.getResourceName();
        String pngResourceName = processDefinition.getDiagramResourceName();

        // 指定文件对应的输入流
        InputStream bpmnInputStream = repositoryService.getResourceAsStream(deploymentId, bpmnResourceName);
        InputStream pngInputStream = repositoryService.getResourceAsStream(deploymentId, pngResourceName);

        // 4.保存资源文件（需要导入common-io.jar）
        File bpmnFile = new File("d:/evection.bpmn");
        File pngFile = new File("d:/evection.png");
        FileOutputStream bpmnOutputStream = new FileOutputStream(bpmnFile);
        FileOutputStream pngOutputStream = new FileOutputStream(pngFile);
        IOUtils.copy(bpmnInputStream, bpmnOutputStream);
        IOUtils.copy(pngInputStream, pngOutputStream);

        // 5.关闭流
        bpmnOutputStream.close();
        pngOutputStream.close();
        bpmnInputStream.close();
        pngInputStream.close();
    }

    /**
     * 历史信息查看
     */
    @Test
    void testProcessHistory() {
        // 1.获取流程引擎
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        // 2.获取HistoryService：历史信息管理类
        HistoryService historyService = processEngine.getHistoryService();

        // 3.创建相关查询对象
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        // HistoricDetailQuery historicDetailQuery = historyService.createHistoricDetailQuery();
        // HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        // HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        // HistoricVariableInstanceQuery historicVariableInstanceQuery = historyService.createHistoricVariableInstanceQuery();

        // 4.查询相关数据
        List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery
                .activityType("userTask")
                .orderByHistoricActivityInstanceEndTime().desc()
                .list();
    }
}
