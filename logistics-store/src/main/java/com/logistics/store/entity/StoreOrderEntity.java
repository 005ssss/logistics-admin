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
@TableName("store_order")
public class StoreOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 仓库编号
	 */
	private Integer storeId;
	/**
	 * 订单位置
	 */
	private String location;
	/**
	 * 入库时间
	 */
	private Date inTime;
	/**
	 * 出库时间
	 */
	private Date outTime;

}
