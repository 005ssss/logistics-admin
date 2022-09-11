package com.logistics.packages;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.logistics.packages.dao")
public class LogisticsPackageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsPackageApplication.class, args);
    }

}
