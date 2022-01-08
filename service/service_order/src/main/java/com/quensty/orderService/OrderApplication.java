package com.quensty.orderService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author quensty
 * @version 1.0
 * @date 2022/1/2 15:05
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.quensty"})
@MapperScan("com.quensty.orderService.mapper")
@EnableDiscoveryClient  //Nacos注册
@EnableFeignClients //服务调用
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
