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
@TableName("pkg_prohibit")
public class PkgProhibitEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String pkgNumber;
	/**
	 * 
	 */
	private String itemType;
	/**
	 * 
	 */
	private Integer itemX;
	/**
	 * 
	 */
	private Integer itemY;
	/**
	 * 
	 */
	private Integer itemW;
	/**
	 * 
	 */
	private Integer itemH;
	/**
	 *
	 */
	private Float itemConf;
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

	public String getPkgNumber() {
		return pkgNumber;
	}

	public void setPkgNumber(String pkgNumber) {
		this.pkgNumber = pkgNumber;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Integer getItemX() {
		return itemX;
	}

	public void setItemX(Integer itemX) {
		this.itemX = itemX;
	}

	public Integer getItemY() {
		return itemY;
	}

	public void setItemY(Integer itemY) {
		this.itemY = itemY;
	}

	public Integer getItemW() {
		return itemW;
	}

	public void setItemW(Integer itemW) {
		this.itemW = itemW;
	}

	public Integer getItemH() {
		return itemH;
	}

	public void setItemH(Integer itemH) {
		this.itemH = itemH;
	}

	public Float getItemConf() {
		return itemConf;
	}

	public void setItemConf(Float itemConf) {
		this.itemConf = itemConf;
	}

	public Date getDetectTime() {
		return detectTime;
	}

	public void setDetectTime(Date detectTime) {
		this.detectTime = detectTime;
	}
}
