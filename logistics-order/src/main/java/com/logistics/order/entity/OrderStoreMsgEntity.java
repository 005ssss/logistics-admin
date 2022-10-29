package com.logistics.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * 
 * 
 * @author 005ssss
 * @email sunlightcs@gmail.com
 * @date 2022-10-04 19:50:08
 */
@Data
@TableName("order_store_msg")
public class OrderStoreMsgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String exchange;
	/**
	 * 
	 */
	private String routingKey;
	/**
	 * 
	 */
	private String messageContent;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private Integer retryCount;
	/**
	 * 
	 */
	@Version
	private Long version;

}
