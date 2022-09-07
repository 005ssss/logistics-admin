package com.logistics.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LogisticsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsGatewayApplication.class, args);
    }

}
