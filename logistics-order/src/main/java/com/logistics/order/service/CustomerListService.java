package com.logistics.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logistics.common.utils.PageUtils;
import com.logistics.order.entity.CustomerListEntity;

import java.util.Map;

/**
 * 
 *
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-26 20:57:32
 */
public interface CustomerListService extends IService<CustomerListEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

