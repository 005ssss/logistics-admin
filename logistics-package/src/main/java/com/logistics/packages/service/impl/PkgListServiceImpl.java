package com.logistics.packages.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.logistics.packages.entity.PkgImgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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

    @Autowired
    PkgListDao pkgListDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PkgListEntity> page = this.page(
                new Query<PkgListEntity>().getPage(params),
                new QueryWrapper<PkgListEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getList(Map<String, Object> params) {
        int pageSize = (int)params.get("limit");
        int currPage = (int)params.get("page");
        long totalCount = pkgListDao.selectCount(null);
        Page<PkgListEntity> page= Page.of(currPage, pageSize);
        pkgListDao.selectPage(page, new QueryWrapper<PkgListEntity>().orderByDesc("id"));
        page.setTotal(totalCount);
        return new PageUtils(page);
    }

    @Override
    public PageUtils getLast(Map<String, Object> params) {
        int pageSize = (int)params.get("limit");
        int currPage = (int)params.get("page");
        long totalCount = pkgListDao.selectCount(null);
        Page<PkgListEntity> page= Page.of(currPage, pageSize);
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MINUTE,-5);
        Date date = time.getTime();
        pkgListDao.selectPage(page, new QueryWrapper<PkgListEntity>().orderByDesc("id").ge("detect_time",date));
        page.setTotal(totalCount);
        return new PageUtils(page);
    }

}