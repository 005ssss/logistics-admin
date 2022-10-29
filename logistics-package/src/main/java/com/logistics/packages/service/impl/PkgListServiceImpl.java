package com.logistics.packages.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.logistics.packages.entity.PkgImgEntity;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.packages.dao.PkgListDao;
import com.logistics.packages.entity.PkgListEntity;
import com.logistics.packages.service.PkgListService;
import org.springframework.util.StringUtils;


@Service("pkgListService")
public class PkgListServiceImpl extends ServiceImpl<PkgListDao, PkgListEntity> implements PkgListService {

    @Autowired
    PkgListDao pkgListDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PkgListEntity> page = this.page(
                new Query<PkgListEntity>().getPage(params),
                new QueryWrapper<PkgListEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getList(Map<String, Object> params) throws ParseException {
        int pageSize = (int)params.get("limit");
        int currPage = (int)params.get("page");
        Page<PkgListEntity> page= Page.of(currPage, pageSize);
        QueryWrapper<PkgListEntity> wrapper = new QueryWrapper<PkgListEntity>().orderByDesc("id");
        //判断日期范围是否为空
        if(params.get("date") != null){
            ArrayList<String> date = (ArrayList<String>)params.get("date");
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date start = sdf.parse(date.get(0) + " 00:00:00");
            Date end = sdf.parse(date.get(1) + " 23:59:59");
            wrapper.ge("detect_time",start).le("detect_time",end);
        }
        //判断订单号是否为空
        String order = (String)params.get("order");
        if(!org.apache.commons.lang3.StringUtils.isBlank(order)){
            wrapper.eq("id", order);
        }
        //判断检测结果是否为空
        String result = (String)params.get("result");
        if(!org.apache.commons.lang3.StringUtils.isBlank(result)){
            wrapper.eq("pkg_type", result);
        }
        pkgListDao.selectPage(page, wrapper);
        long totalCount = pkgListDao.selectCount(wrapper);
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

    public Map<String, Object> getListJson(){
        Map<String, Object> result = new HashMap<>();
        RLock lock = redissonClient.getLock("listLock");
        lock.lock(10, TimeUnit.SECONDS);
        try{
            String listJson = stringRedisTemplate.opsForValue().get("listJson");
            if(StringUtils.hasLength(listJson)){
                return JSON.parseObject(listJson, new TypeReference<Map<String, Object>>(){});
            }
            System.out.println("查询数据库...listJson");
            Calendar start = getStartOfDay();
            Calendar end = getEndOfDay();
            SimpleDateFormat format = new SimpleDateFormat("MM-dd");
            String[] date = new String[14];
            Long[] normal = new Long[14];
            Long[] problem = new Long[14];
            for (int i = 0; i < 14; i++) {
                date[i] = format.format(start.getTime());
                normal[i] = count(new QueryWrapper<PkgListEntity>().eq("pkg_type", 0).ge("detect_time",start.getTime()).le("detect_time",end.getTime()));
                problem[i] = count(new QueryWrapper<PkgListEntity>().eq("pkg_type", 1).ge("detect_time",start.getTime()).le("detect_time",end.getTime()));
                start.add(Calendar.DATE, 1);
                end.add(Calendar.DATE, 1);
            }
            result.put("date", date);
            result.put("normal", normal);
            result.put("problem", problem);
            listJson = JSON.toJSONString(result);
            stringRedisTemplate.opsForValue().set("listJson", listJson, 1, TimeUnit.HOURS);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public Map<String, Object> getTotalCount() {
        String listJson = stringRedisTemplate.opsForValue().get("listJson");
        if(!StringUtils.hasLength(listJson)){
            Map<String, Object> result = getListJson();
            return result;
        }
        Map<String, Object> res = JSON.parseObject(listJson, new TypeReference<Map<String, Object>>(){});
        return res;
    }


    public Calendar getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-14);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public Calendar getEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-14);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

}