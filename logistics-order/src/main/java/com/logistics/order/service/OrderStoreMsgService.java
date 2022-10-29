package com.logistics.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logistics.common.utils.PageUtils;
import com.logistics.order.entity.OrderStoreMsgEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-10-04 19:50:08
 */
public interface OrderStoreMsgService extends IService<OrderStoreMsgEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<OrderStoreMsgEntity> selectNeededToBeSentMessage();

    int touchMessage(String id, Long version);

    void reduceRetryCount(String id);

    void changeStatus2Success(String id);
}

