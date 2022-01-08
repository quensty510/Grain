package com.quensty.eduService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/10/29 14:30
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.quensty"})
@MapperScan("com.quensty.**.mapper")
@EnableDiscoveryClient  //Nacos注册
@EnableFeignClients //服务调用
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
