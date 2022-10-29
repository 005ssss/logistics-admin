package com.logistics.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.order.dao.OrderStoreMsgDao;
import com.logistics.order.entity.OrderStoreMsgEntity;
import com.logistics.order.service.OrderStoreMsgService;


@Service("orderStoreMsgService")
public class OrderStoreMsgServiceImpl extends ServiceImpl<OrderStoreMsgDao, OrderStoreMsgEntity> implements OrderStoreMsgService {

    @Autowired
    OrderStoreMsgDao orderStoreMsgDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderStoreMsgEntity> page = this.page(
                new Query<OrderStoreMsgEntity>().getPage(params),
                new QueryWrapper<OrderStoreMsgEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<OrderStoreMsgEntity> selectNeededToBeSentMessage() {
        return orderStoreMsgDao.selectList(new QueryWrapper<OrderStoreMsgEntity>().eq("status", "ready"));
    }

    @Override
    public int touchMessage(String id, Long version) {
        OrderStoreMsgEntity orderStoreMsgEntity = new OrderStoreMsgEntity();
        orderStoreMsgEntity.setId(id);
        orderStoreMsgEntity.setVersion(version);
        return orderStoreMsgDao.updateById(orderStoreMsgEntity);
    }

    @Override
    public void reduceRetryCount(String id) {
        OrderStoreMsgEntity orderStoreMsgEntity = orderStoreMsgDao.selectById(id);
        orderStoreMsgEntity.setRetryCount(orderStoreMsgEntity.getRetryCount()-1);
        orderStoreMsgDao.updateById(orderStoreMsgEntity);

    }

    @Override
    public void changeStatus2Success(String id) {
        OrderStoreMsgEntity orderStoreMsgEntity = orderStoreMsgDao.selectById(id);
        orderStoreMsgEntity.setStatus("success");
        orderStoreMsgDao.updateById(orderStoreMsgEntity);

    }

}