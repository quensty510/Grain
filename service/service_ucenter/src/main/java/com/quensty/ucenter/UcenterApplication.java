package com.quensty.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/28 17:34
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.quensty"})
@MapperScan("com.quensty.ucenter.mapper")
@EnableDiscoveryClient
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
