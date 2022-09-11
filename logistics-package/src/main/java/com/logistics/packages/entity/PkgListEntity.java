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
@TableName("pkg_list")
public class PkgListEntity implements Serializable {
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
	private String pkgNumber;
	/**
	 * 
	 */
	private Integer pkgType;
	/**
	 * 
	 */
	private Integer detectDevice;
	/**
	 * 
	 */
	private Date detectTime;

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

	public String getPkgNumber() {
		return pkgNumber;
	}

	public void setPkgNumber(String pkgNumber) {
		this.pkgNumber = pkgNumber;
	}

	public Integer getPkgType() {
		return pkgType;
	}

	public void setPkgType(Integer pkgType) {
		this.pkgType = pkgType;
	}

	public Integer getDetectDevice() {
		return detectDevice;
	}

	public void setDetectDevice(Integer detectDevice) {
		this.detectDevice = detectDevice;
	}

	public Date getDetectTime() {
		return detectTime;
	}

	public void setDetectTime(Date detectTime) {
		this.detectTime = detectTime;
	}
}
