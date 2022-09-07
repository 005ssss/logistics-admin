package com.logistics.packages.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.packages.dao.PkgProhibitDao;
import com.logistics.packages.entity.PkgProhibitEntity;
import com.logistics.packages.service.PkgProhibitService;


@Service("pkgProhibitService")
public class PkgProhibitServiceImpl extends ServiceImpl<PkgProhibitDao, PkgProhibitEntity> implements PkgProhibitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PkgProhibitEntity> page = this.page(
                new Query<PkgProhibitEntity>().getPage(params),
                new QueryWrapper<PkgProhibitEntity>()
        );

        return new PageUtils(page);
    }

}