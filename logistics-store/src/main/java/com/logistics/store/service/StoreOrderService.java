package com.logistics.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logistics.common.utils.PageUtils;
import com.logistics.store.entity.StoreOrderEntity;

import java.util.Map;

/**
 * 
 *
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-26 21:00:14
 */
public interface StoreOrderService extends IService<StoreOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

