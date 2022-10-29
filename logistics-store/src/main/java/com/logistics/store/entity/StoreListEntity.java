package com.logistics.store.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-09-26 21:00:14
 */
@Data
@TableName("store_list")
public class StoreListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 仓库编号
	 */
	@TableId
	private Integer id;
	/**
	 * 仓库名称
	 */
	private String storeName;
	/**
	 * 仓库地址
	 */
	private String storeAddr;
	/**
	 * 仓库容量
	 */
	private Integer storeCap;

}
