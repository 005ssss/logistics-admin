package com.logistics.order.entity;

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
 * @date 2022-09-26 20:57:32
 */
@Data
@TableName("order_list")
public class OrderListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	@TableId
	private String id;
	/**
	 * 运单编号
	 */
	private String pkgNumber;
	/**
	 * 下单时间
	 */
	private Date orderTime;
	/**
	 * 订单状态
	 */
	private String orderState;
	/**
	 * 仓库编号
	 */
	private Integer storeId;
	/**
	 * 发件人编号
	 */
	private String senderId;
	/**
	 * 收件人编号
	 */
	private String receiverId;

}
