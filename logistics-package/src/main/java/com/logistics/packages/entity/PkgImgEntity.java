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
@TableName("pkg_img")
public class PkgImgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String imgNumber;
	/**
	 * 
	 */
	private Date detectTime;
	/**
	 * 
	 */
	private Integer detectDevice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImgNumber() {
		return imgNumber;
	}

	public void setImgNumber(String imgNumber) {
		this.imgNumber = imgNumber;
	}

	public Date getDetectTime() {
		return detectTime;
	}

	public void setDetectTime(Date detectTime) {
		this.detectTime = detectTime;
	}

	public Integer getDetectDevice() {
		return detectDevice;
	}

	public void setDetectDevice(Integer detectDevice) {
		this.detectDevice = detectDevice;
	}

}
