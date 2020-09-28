package com.scd.flowabledemos;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
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
        String name = processInstance.getProcessDefinitionId();
        System.out.println(processId + ":" + name);

        //任务列表
        System.out.println("获取任务列表");
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i=0; i<tasks.size(); i++) {
            System.out.println((i+1) + ") " + tasks.get(i).getName());
        }

        //获取指定任务的参数
        System.out.println("获取指定任务的参数");
        Task task = tasks.get(0);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " wants " +
                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");

        // 完成任务
        System.out.println("完成任务");
        variables = new HashMap<String, Object>();
        variables.put("approved", true);
        taskService.complete(task.getId(), variables);

        //历史数据
        System.out.println("历史数据");
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .finished()
                        .orderByHistoricActivityInstanceEndTime().asc()
                        .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took "
                    + activity.getDurationInMillis() + " milliseconds");
        }
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
