package com.logistics.packages.dao;

import com.logistics.packages.entity.PkgListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-06 21:57:13
 */
@Mapper
@Repository
public interface PkgListDao extends BaseMapper<PkgListEntity> {
	
}
