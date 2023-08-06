package com.logistics.packages;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.logistics.packages.dao")
@EnableFeignClients(basePackages = "com.logistics.packages.feign")
public class LogisticsPackageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsPackageApplication.class, args);
    }

}
