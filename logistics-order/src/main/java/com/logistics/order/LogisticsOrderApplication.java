package com.logistics.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableRabbit
@EnableFeignClients(basePackages = "com.logistics.order.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class LogisticsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsOrderApplication.class, args);
    }

}
