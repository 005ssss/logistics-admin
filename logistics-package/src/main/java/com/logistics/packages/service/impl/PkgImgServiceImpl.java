package com.logistics.packages.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private PkgImgDao pkgImgDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PkgImgEntity> page = this.page(
                new Query<PkgImgEntity>().getPage(params),
                new QueryWrapper<PkgImgEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getList(Map<String, Object> params) {
        int pageSize = (int)params.get("limit");
        int currPage = (int)params.get("page");
        long totalCount = pkgImgDao.selectCount(null);
        Page<PkgImgEntity> page= Page.of(currPage, pageSize);
        pkgImgDao.selectPage(page, new QueryWrapper<PkgImgEntity>().orderByDesc("id"));
        page.setTotal(totalCount);
        return new PageUtils(page);
    }
}