package com.logistics.store.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange storeEventExchange(){
        return new TopicExchange("store-event-exchange",true,false);
    }

    @Bean
    public Queue orderStoreQueue(){
        return new Queue("order.store.queue",true,false,false);
    }

    @Bean
    public Binding storeBinding(){
        return new Binding("order.store.queue", Binding.DestinationType.QUEUE,
                "store-event-exchange","order.store.#",null);
    }
}
