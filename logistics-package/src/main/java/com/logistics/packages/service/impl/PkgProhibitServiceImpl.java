package com.logistics.packages.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.logistics.packages.entity.PkgListEntity;
import com.logistics.packages.utils.ConstantUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logistics.common.utils.PageUtils;
import com.logistics.common.utils.Query;

import com.logistics.packages.dao.PkgProhibitDao;
import com.logistics.packages.entity.PkgProhibitEntity;
import com.logistics.packages.service.PkgProhibitService;
import org.springframework.util.StringUtils;


@Service("pkgProhibitService")
public class PkgProhibitServiceImpl extends ServiceImpl<PkgProhibitDao, PkgProhibitEntity> implements PkgProhibitService, ConstantUtils {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PkgProhibitEntity> page = this.page(
                new Query<PkgProhibitEntity>().getPage(params),
                new QueryWrapper<PkgProhibitEntity>()
        );

        return new PageUtils(page);
    }

    public Map<String, Long> getProhibitJson(){
        Map<String, Long> result = new HashMap<>();
        RLock lock = redissonClient.getLock("prohibitLock");
        lock.lock(10, TimeUnit.SECONDS);
        try{
            String prohibitJson = stringRedisTemplate.opsForValue().get("prohibitJson");
            if(StringUtils.hasLength(prohibitJson)){
                return JSON.parseObject(prohibitJson, new TypeReference<Map<String, Long>>(){});
            }
            System.out.println("查询数据库...prohibitJson");
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DATE,-14);
            Date date = time.getTime();
            for(String prohibit : prohibits){
                result.put(prohibit, count(new QueryWrapper<PkgProhibitEntity>().eq("item_type", prohibit).ge("detect_time",date)));
            }
            prohibitJson = JSON.toJSONString(result);
            stringRedisTemplate.opsForValue().set("prohibitJson", prohibitJson, 1, TimeUnit.HOURS);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public Map<String, Long> getTotalCount() {
        String prohibitJson = stringRedisTemplate.opsForValue().get("prohibitJson");
        if(!StringUtils.hasLength(prohibitJson)){
            Map<String, Long> result = getProhibitJson();
            return result;
        }
        Map<String, Long> res = JSON.parseObject(prohibitJson, new TypeReference<Map<String, Long>>(){});
        return res;
    }

}