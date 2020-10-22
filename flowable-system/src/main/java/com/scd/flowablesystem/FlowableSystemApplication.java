package com.scd.flowablesystem;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author
 * @since
 */
@MapperScan("com.scd.*.dao.*")
@EnableTransactionManagement
@SpringBootApplication
public class FlowableSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlowableSystemApplication.class, args);
  }

}

