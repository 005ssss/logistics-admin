package com.logistics.packages.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logistics.common.utils.PageUtils;
import com.logistics.packages.entity.PkgListEntity;

import java.text.ParseException;
import java.util.Map;

/**
 * 
 *
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-06 21:57:13
 */
public interface PkgListService extends IService<PkgListEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils getList(Map<String, Object> params) throws ParseException;

    PageUtils getLast(Map<String, Object> params);

    Map<String, Object> getTotalCount();

    void putDetectImage(int deviceId, String image_name);

    String getDetectImage(int deviceId);
}

