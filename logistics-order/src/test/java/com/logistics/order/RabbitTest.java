package com.logistics.order;


import com.logistics.order.entity.OrderListEntity;
import com.logistics.order.entity.OrderStoreMsgEntity;
import com.logistics.order.service.OrderStoreMsgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitTest {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OrderStoreMsgService msgService;


    @Test
    public void sendMessage(){
        OrderListEntity orderListEntity = new OrderListEntity();
        orderListEntity.setId("10086");
        orderListEntity.setPkgNumber("100225");
        orderListEntity.setStoreId(1001);

        rabbitTemplate.convertAndSend("my.exchange.derict","logistics",orderListEntity,new CorrelationData(UUID.randomUUID().toString()));
    }

    //测试乐观锁
//    @Test
//    public void testLock(){
//        OrderStoreMsgEntity entity = new OrderStoreMsgEntity();
//        entity.setId("1");
//        entity.setExchange("store-event-exchange");
//        entity.setRoutingKey("order.store.msg");
//        entity.setMessageContent("iddd");
//        entity.setStatus("ready");
//        msgService.save(entity);
//        List<OrderStoreMsgEntity> list = msgService.selectNeededToBeSentMessage();
//        long version = 0;
//        for(OrderStoreMsgEntity order: list){
//            version = order.getVersion();
//            System.out.println(order.toString());
//        }
//        System.out.println(version);
//        System.out.println(msgService.touchMessage((long)1, version));
//        System.out.println(msgService.touchMessage((long)1, version));
//    }
}
