package com.logistics.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LogisticsStatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsStatisticsApplication.class, args);
    }

}
