package com.logistics.order.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JsonbMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
public class MyRabbitMQConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter(){
        return new JsonbMessageConverter();
    }

    @Bean
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("correlationData["+correlationData+"]==>ack["+b+"]==>cause["+s+"]");
            }
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {

            @Override
            public void returnedMessage(@NotNull ReturnedMessage returnedMessage) {
                System.out.println(returnedMessage);
            }
        });
    }
}
