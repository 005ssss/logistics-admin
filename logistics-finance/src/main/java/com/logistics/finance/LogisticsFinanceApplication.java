package com.logistics.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LogisticsFinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsFinanceApplication.class, args);
    }

}
