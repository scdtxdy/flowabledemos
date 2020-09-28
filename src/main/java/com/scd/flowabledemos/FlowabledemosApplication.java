package com.scd.flowabledemos;

import com.scd.flowabledemos.config.AppDispatcherServletConfiguration;
import com.scd.flowabledemos.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Springboot启动类Import两个配置类，并排除springsecurity认证
@Import({
        ApplicationConfiguration.class,
        AppDispatcherServletConfiguration.class
})
@RestController
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FlowabledemosApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowabledemosApplication.class, args);
    }

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

}
