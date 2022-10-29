package com.logistics.store.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.logistics.common.to.StoreStateTo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.store.dao.StoreListDao;
import com.logistics.store.entity.StoreListEntity;
import com.logistics.store.service.StoreListService;

@RabbitListener
@Service("storeListService")
public class StoreListServiceImpl extends ServiceImpl<StoreListDao, StoreListEntity> implements StoreListService {

    @Autowired
    StoreListDao storeListDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StoreListEntity> page = this.page(
                new Query<StoreListEntity>().getPage(params),
                new QueryWrapper<StoreListEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getList(Map<String, Object> params) {
        int pageSize = (int)params.get("limit");
        int currPage = (int)params.get("page");
        long totalCount = storeListDao.selectCount(null);
        Page<StoreListEntity> page= Page.of(currPage, pageSize);
        storeListDao.selectPage(page, new QueryWrapper<StoreListEntity>().orderByDesc("id"));
        page.setTotal(totalCount);
        return new PageUtils(page);
    }

    @RabbitHandler
    public void handleStoreState(StoreStateTo storeStateTo, Message message, Channel channel) throws IOException {
        StoreListEntity entity = this.getById(storeStateTo.getId());
        if(storeStateTo.getState().equals("increase")){
            entity.setStoreCap(entity.getStoreCap()-1);
        } else{
            entity.setStoreCap(entity.getStoreCap()+1);
        }
        storeListDao.updateById(entity);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

    }

}