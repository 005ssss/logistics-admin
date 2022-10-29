package com.logistics.order.service;

import com.logistics.common.to.StoreStateTo;
import com.logistics.order.entity.OrderStoreMsgEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@EnableScheduling
@Service
public class ReliableMessageService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OrderStoreMsgService msgService;

    public void sendMsg(String exchange, String routing, StoreStateTo messageContext, String messageId){
        rabbitTemplate.convertAndSend(exchange,routing, messageContext, new CorrelationData(messageId));
    }

    @Scheduled(fixedDelay = 3 * 1000)
    private void autoSend() {
        List<OrderStoreMsgEntity> messageList = msgService.selectNeededToBeSentMessage();
        log.debug("执行定时任务。查询出有 {} 条待发送消息。", messageList.size());
        for (OrderStoreMsgEntity message : messageList) {
            log.debug("待发送消息 : id-{}", message.getId());
            if (msgService.touchMessage(message.getId(), message.getVersion()) > 0) {
                StoreStateTo storeStateTo = new StoreStateTo();
                String messageContent = message.getMessageContent();
                String[] content = messageContent.split(":");
                int id = Integer.parseInt(content[0]);
                String state = content[1];
                storeStateTo.setId(id);
                storeStateTo.setState(state);
                rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), storeStateTo, new CorrelationData(message.getId()));
                msgService.reduceRetryCount(message.getId());    // 重试
            }
        }
    }

}
