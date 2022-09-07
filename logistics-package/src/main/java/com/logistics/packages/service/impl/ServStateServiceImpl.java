package com.logistics.packages.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.packages.dao.ServStateDao;
import com.logistics.packages.entity.ServStateEntity;
import com.logistics.packages.service.ServStateService;


@Service("servStateService")
public class ServStateServiceImpl extends ServiceImpl<ServStateDao, ServStateEntity> implements ServStateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ServStateEntity> page = this.page(
                new Query<ServStateEntity>().getPage(params),
                new QueryWrapper<ServStateEntity>()
        );

        return new PageUtils(page);
    }

}