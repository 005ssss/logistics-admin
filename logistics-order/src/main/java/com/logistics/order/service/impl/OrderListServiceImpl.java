package com.logistics.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.logistics.common.to.StoreStateTo;
import com.logistics.order.entity.OrderStoreMsgEntity;
import com.logistics.order.feign.StoreFeignService;
import com.logistics.order.service.OrderStoreMsgService;
import com.logistics.order.service.ReliableMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.order.dao.OrderListDao;
import com.logistics.order.entity.OrderListEntity;
import com.logistics.order.service.OrderListService;
import org.springframework.transaction.annotation.Transactional;


@Service("orderListService")
public class OrderListServiceImpl extends ServiceImpl<OrderListDao, OrderListEntity> implements OrderListService {

    @Autowired
    OrderListDao orderListDao;

    @Autowired
    StoreFeignService storeFeignService;

    @Autowired
    OrderStoreMsgService orderStoreMsgService;

    @Autowired
    ReliableMessageService reliableMessageService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderListEntity> page = this.page(
                new Query<OrderListEntity>().getPage(params),
                new QueryWrapper<OrderListEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getList(Map<String, Object> params) {
        int pageSize = (int)params.get("limit");
        int currPage = (int)params.get("page");
        long totalCount = orderListDao.selectCount(null);
        Page<OrderListEntity> page= Page.of(currPage, pageSize);
        orderListDao.selectPage(page, new QueryWrapper<OrderListEntity>().orderByDesc("id"));
        page.setTotal(totalCount);
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void changeState(OrderListEntity orderList) {
        this.updateById(orderList);
        String exchange = "store-event-exchange";
        String routing = "order.store.msg";
        String messageContext = "";
        messageContext += orderList.getStoreId();
        messageContext += ":";
        OrderStoreMsgEntity msgEntity = new OrderStoreMsgEntity();
        msgEntity.setId(UUID.randomUUID().toString());
        msgEntity.setExchange(exchange);
        msgEntity.setRoutingKey(routing);
        msgEntity.setStatus("ready");
        StoreStateTo storeStateTo = new StoreStateTo();
        storeStateTo.setId(orderList.getStoreId());
        if(orderList.getOrderState().equals("已入库")){
//            storeFeignService.increaseOrder(orderList.getStoreId());
            msgEntity.setMessageContent(messageContext+"increase");
            storeStateTo.setState("increase");
            orderStoreMsgService.save(msgEntity);
            reliableMessageService.sendMsg(exchange, routing, storeStateTo, msgEntity.getId());
        }
        if(orderList.getOrderState().equals("已出库")){
//            storeFeignService.reduceOrder(orderList.getStoreId());
            msgEntity.setMessageContent(messageContext+"reduce");
            storeStateTo.setState("reduce");
            orderStoreMsgService.save(msgEntity);
            reliableMessageService.sendMsg(exchange, routing, storeStateTo, msgEntity.getId());
        }
    }

}