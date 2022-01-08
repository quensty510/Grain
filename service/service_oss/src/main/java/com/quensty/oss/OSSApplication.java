package com.quensty.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/1 21:35
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.quensty")
@EnableDiscoveryClient
public class OSSApplication {
    public static void main(String[] args) {
        SpringApplication.run(OSSApplication.class, args);
    }
}
