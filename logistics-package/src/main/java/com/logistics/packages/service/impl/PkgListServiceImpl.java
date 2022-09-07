package com.logistics.packages.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.packages.dao.PkgListDao;
import com.logistics.packages.entity.PkgListEntity;
import com.logistics.packages.service.PkgListService;


@Service("pkgListService")
public class PkgListServiceImpl extends ServiceImpl<PkgListDao, PkgListEntity> implements PkgListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PkgListEntity> page = this.page(
                new Query<PkgListEntity>().getPage(params),
                new QueryWrapper<PkgListEntity>()
        );

        return new PageUtils(page);
    }

}