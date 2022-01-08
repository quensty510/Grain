package com.quensty.statisticService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author quensty
 * @version 1.0
 * @date 2022/1/4 17:51
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.quensty"})
@MapperScan("com.quensty.statisticService.mapper")
@EnableDiscoveryClient
@EnableFeignClients
//开启定时任务
@EnableScheduling
public class StatisticApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class,args);
    }
}
