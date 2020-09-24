package com.scd.flowabledemos;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
class FlowabledemosApplicationTests {

    @Test
    void createTable() {
        System.out.println("创建流程");
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable-spring-boot?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&nullCatalogMeansCurrent=true")
                .setJdbcUsername("root")
                .setJdbcPassword("542846492")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                //DB_SCHEMA_UPDATE_FALSE 不创建表
                //DB_SCHEMA_UPDATE_TRUE 如果没有表创建表 有表更新
                //DB_SCHEMA_UPDATE_CREATE_DROP 删除现有表 创建新表
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_DROP_CREATE);
        ProcessEngine processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")//存路径的方式部署流程定义文件
                .deploy();

        System.out.println("部署流程");
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());

        System.out.println("开始流程");
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", "scd");
        variables.put("nrOfHolidays", 1);
        variables.put("description", "scdscdscd");
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        String processId = processInstance.getId();
        String name = processInstance.getName();
        System.out.println(processId + ":" + name);


    }

//    @Test
//    void deployment(){
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("holiday-request.bpmn20.xml")//存路径的方式部署流程定义文件
//                .deploy();
//
//
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .deploymentId(deployment.getId())
//                .singleResult();
//        System.out.println("Found process definition : " + processDefinition.getName());
//
//    }
//
//    @Test
//    void startDeployment(){
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("employee", "scd");
//        variables.put("nrOfHolidays", 1);
//        variables.put("description", "scdscdscd");
//        ProcessInstance processInstance =
//                runtimeService.startProcessInstanceByKey("holidayRequest", variables);
//        String processId = processInstance.getId();
//        String name = processInstance.getName();
//        System.out.println(processId + ":" + name);
//    }
//






}
