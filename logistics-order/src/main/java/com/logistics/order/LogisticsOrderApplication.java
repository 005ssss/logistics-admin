package com.logistics.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LogisticsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsOrderApplication.class, args);
    }

}
