package com.logistics.packages.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.packages.dao.PkgImgDao;
import com.logistics.packages.entity.PkgImgEntity;
import com.logistics.packages.service.PkgImgService;

import javax.xml.crypto.Data;


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
    public PageUtils getList(Map<String, Object> params) throws ParseException {
        int pageSize = (int)params.get("limit");
        int currPage = (int)params.get("page");
        Page<PkgImgEntity> page= Page.of(currPage, pageSize);
        QueryWrapper<PkgImgEntity> wrapper = new QueryWrapper<PkgImgEntity>().orderByDesc("id");
        //判断日期范围是否为空
        if(params.get("date") != null){
            ArrayList<String> date = (ArrayList<String>)params.get("date");
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date start = sdf.parse(date.get(0) + " 00:00:00");
            Date end = sdf.parse(date.get(1) + " 23:59:59");
            wrapper.ge("detect_time",start).le("detect_time",end);
        }
        //判断违禁品种类是否为空
        if(params.get("prohibit") != null){
            ArrayList<ArrayList<String>> prohibit = (ArrayList<ArrayList<String>>)params.get("prohibit");
            int n = prohibit.size();
            if(n < 6 && n > 0){
                wrapper.and(new Consumer<QueryWrapper<PkgImgEntity>>() {
                    @Override
                    public void accept(QueryWrapper<PkgImgEntity> pkgImgEntityQueryWrapper) {
                        for(ArrayList<String> arr : prohibit){
                            if (arr.get(0).equals("wuweijinpin")){
                                pkgImgEntityQueryWrapper.or().eq("pkg_type", "0");
                            } else{
                                pkgImgEntityQueryWrapper.or().gt(arr.get(1), 0);
                            }
                        }
                    }
                });
            }
        }
        //判断订单号是否为空
        String order = (String)params.get("order");
        if(!StringUtils.isBlank(order)){
            wrapper.eq("id", order);
        }
        //判断检测结果是否为空
        String result = (String)params.get("result");
        if(!StringUtils.isBlank(result)){
            wrapper.eq("pkg_type", result);
        }
        pkgImgDao.selectPage(page, wrapper);
        long totalCount = pkgImgDao.selectCount(wrapper);
        page.setTotal(totalCount);
        return new PageUtils(page);
    }
}