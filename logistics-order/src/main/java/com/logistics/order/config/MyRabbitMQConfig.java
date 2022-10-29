package com.logistics.order.config;

import com.logistics.order.service.OrderStoreMsgService;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyRabbitMQConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    private static final Logger logger = LoggerFactory.getLogger(MyRabbitMQConfig.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OrderStoreMsgService msgService;

    /**
     * 当前类的构造方法执行完成后，会自动触发该方法，可以在该方法中，完成一些对象的初始化操作
     */

    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                if (b) {
                    logger.info("消息到达交换机了，消息：{}", correlationData.getId());
                    msgService.changeStatus2Success(correlationData.getId());
                } else {
                    logger.error("消息未到达交换机，消息：{}", correlationData.getId());
                }
            }
        });
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(@NotNull ReturnedMessage returnedMessage) {
                logger.error("消息未到达队列：{}", returnedMessage.toString());
            }
        });
    }

    /**
     * 如果不管有没有到达交换机，都会触发该方法
     *
     * @param correlationData 这个是携带的 correlationId 在这里
     * @param ack：这个值如果为      true，表示交换机收到消息了，false 表示消息未到达交换机
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info("消息到达交换机了，消息 id：{}", correlationData.getId());
        } else {
            logger.error("消息未到达交换机，消息 id：{}", correlationData.getId());
        }
    }

    /**
     * 如果消息未到达队列，会触发该方法
     *
     * @param returned
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        logger.error("消息未到达队列：{}", returned.toString());
    }
}