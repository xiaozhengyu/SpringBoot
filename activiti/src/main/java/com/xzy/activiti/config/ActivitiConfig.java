package com.xzy.activiti.config;

import lombok.extern.log4j.Log4j2;
import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author xzy
 * @date 2020-12-18 15:37
 * 说明：Activiti配置
 */
@Configuration
@Log4j2
public class ActivitiConfig {
    /*
     * 配置分为以下几个步骤：
     * 1. 创建ProcessEngineConfiguration
     * 2. 使用ProcessEngineConfiguration创建ProcessEngineFactoryBean
     * 3. 使用ProcessEngineFactoryBean创建ProcessEngine
     * 4. 使用ProcessEngine创建需要的服务对象
     */

    private final DataSource dataSource;
    private final PlatformTransactionManager platformTransactionManager;

    @Autowired
    public ActivitiConfig(DataSource dataSource, PlatformTransactionManager platformTransactionManager) {
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration spec = new SpringProcessEngineConfiguration();
        spec.setDataSource(dataSource);
        spec.setTransactionManager(platformTransactionManager);
        spec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 自动部署流程
        try {
            // 扫描process包下的.bpmn文件（流程定义文件）
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:process/*.bpmn");
            // 部署流程
            spec.setDeploymentResources(resources);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return spec;
    }

    @Bean
    public ProcessEngineFactoryBean processEngineFactoryBean() {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }

    @Bean
    public RepositoryService repositoryService() throws Exception {
        return processEngineFactoryBean().getObject().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() throws Exception {
        return processEngineFactoryBean().getObject().getRuntimeService();
    }

    @Bean
    public TaskService taskService() throws Exception {
        return processEngineFactoryBean().getObject().getTaskService();
    }

    @Bean
    public HistoryService historyService() throws Exception {
        return processEngineFactoryBean().getObject().getHistoryService();
    }
}
