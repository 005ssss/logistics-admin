package com.logistics.store.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.store.dao.StoreOrderDao;
import com.logistics.store.entity.StoreOrderEntity;
import com.logistics.store.service.StoreOrderService;


@Service("storeOrderService")
public class StoreOrderServiceImpl extends ServiceImpl<StoreOrderDao, StoreOrderEntity> implements StoreOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StoreOrderEntity> page = this.page(
                new Query<StoreOrderEntity>().getPage(params),
                new QueryWrapper<StoreOrderEntity>()
        );

        return new PageUtils(page);
    }

}