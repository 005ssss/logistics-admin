package com.logistics.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.order.dao.CustomerListDao;
import com.logistics.order.entity.CustomerListEntity;
import com.logistics.order.service.CustomerListService;


@Service("customerListService")
public class CustomerListServiceImpl extends ServiceImpl<CustomerListDao, CustomerListEntity> implements CustomerListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CustomerListEntity> page = this.page(
                new Query<CustomerListEntity>().getPage(params),
                new QueryWrapper<CustomerListEntity>()
        );

        return new PageUtils(page);
    }

}