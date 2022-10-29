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
@TableName("customer_list")
public class CustomerListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private String id;
	/**
	 * 客户姓名
	 */
	private String name;
	/**
	 * 客户电话
	 */
	private String telNum;
	/**
	 * 客户地址
	 */
	private String address;

}
