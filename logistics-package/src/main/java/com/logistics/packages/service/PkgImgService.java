package com.logistics.packages.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logistics.common.utils.PageUtils;
import com.logistics.packages.entity.PkgImgEntity;

import java.util.Map;

/**
 * 
 *
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-06 21:57:13
 */
public interface PkgImgService extends IService<PkgImgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

