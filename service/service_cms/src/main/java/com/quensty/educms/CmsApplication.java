package com.quensty.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 轮播图管理服务
 * @author quensty
 * @version 1.0
 * @date 2021/11/25 10:49
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.quensty"})
@EnableDiscoveryClient
@MapperScan("com.quensty.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
