package com.logistics.packages.service.impl;

import com.logistics.packages.dao.PkgImgDao;
import com.logistics.packages.entity.PkgImgEntity;
import com.logistics.packages.service.PkgImgService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.packages.dao.PkgImgDao;
import com.logistics.packages.entity.PkgImgEntity;
import com.logistics.packages.service.PkgImgService;


@Service("pkgImgService")
public class PkgImgServiceImpl extends ServiceImpl<PkgImgDao, PkgImgEntity> implements PkgImgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PkgImgEntity> page = this.page(
                new Query<PkgImgEntity>().getPage(params),
                new QueryWrapper<PkgImgEntity>()
        );

        return new PageUtils(page);
    }

}