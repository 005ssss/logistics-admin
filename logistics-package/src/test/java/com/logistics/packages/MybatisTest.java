package com.logistics.packages;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.logistics.packages.dao.PkgImgDao;
import com.logistics.packages.entity.PkgImgEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MybatisTest {

    @Autowired
    private PkgImgDao pkgImgDao;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSelect(){
        Page<PkgImgEntity> page= new Page<>(1, 10);
        Calendar myTime = Calendar.getInstance();
        myTime.add(Calendar.MINUTE,-5);
        Date date = myTime.getTime();
        System.out.println(pkgImgDao.selectPage(page,new QueryWrapper<PkgImgEntity>().orderByDesc("detect_time").ge("detect_time",date)));
        page.setTotal(60);
        System.out.println(page.getSize());
        System.out.println(page.getPages());
    }

    @Test
    public void testRedis(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("test", "1234"+UUID.randomUUID().toString());
        String s = ops.get("test");
        System.out.println(s);

    }
}
