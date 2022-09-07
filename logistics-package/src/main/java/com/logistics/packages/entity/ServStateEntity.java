package com.logistics.packages.entity;

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
 * @date 2022-09-06 21:57:13
 */
@Data
@TableName("serv_state")
public class ServStateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer state;
	/**
	 * 
	 */
	private Integer rate;

}
