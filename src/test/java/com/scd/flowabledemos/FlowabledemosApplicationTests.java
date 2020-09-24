package com.scd.flowabledemos;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class FlowabledemosApplicationTests {

    @Test
    void createTable() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable-spring-boot?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&nullCatalogMeansCurrent=true")
                .setJdbcUsername("root")
                .setJdbcPassword("542846492")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                //DB_SCHEMA_UPDATE_FALSE 不创建表
                //DB_SCHEMA_UPDATE_TRUE 如果没有表创建表 有表更新
                //DB_SCHEMA_UPDATE_CREATE_DROP 删除现有表 创建新表
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = cfg.buildProcessEngine();
    }




}
