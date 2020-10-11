package com.scd.flowablesystem;


import com.scd.flowablesystem.config.ApplicationConfiguration;
import com.scd.flowablesystem.servlet.AppDispatcherServletConfiguration;
import org.flowable.ui.idm.properties.FlowableIdmAppProperties;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author
 * @since
 */
@Import({
    ApplicationConfiguration.class,
    AppDispatcherServletConfiguration.class
})
@MapperScan("com.scd.*.dao.*")
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.scd"})
public class FlowableSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlowableSystemApplication.class, args);
  }

}

