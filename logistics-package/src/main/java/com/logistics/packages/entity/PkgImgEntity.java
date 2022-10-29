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
	/**
	 *
	 */
	private Integer pkgType;
	/**
	 *
	 */
	private Integer dao;
	/**
	 *
	 */
	private Integer qiang;
	/**
	 *
	 */
	private Integer qianzi;
	/**
	 *
	 */
	private Integer banshou;
	/**
	 *
	 */
	private Integer jiandao;

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

	public Integer getPkgType() {
		return pkgType;
	}

	public void setPkgType(Integer pkgType) {
		this.pkgType = pkgType;
	}

	public Integer getDao() {
		return dao;
	}

	public void setDao(Integer dao) {
		this.dao = dao;
	}

	public Integer getQiang() {
		return qiang;
	}

	public void setQiang(Integer qiang) {
		this.qiang = qiang;
	}

	public Integer getQianzi() {
		return qianzi;
	}

	public void setQianzi(Integer qianzi) {
		this.qianzi = qianzi;
	}

	public Integer getBanshou() {
		return banshou;
	}

	public void setBanshou(Integer banshou) {
		this.banshou = banshou;
	}

	public Integer getJiandao() {
		return jiandao;
	}

	public void setJiandao(Integer jiandao) {
		this.jiandao = jiandao;
	}
}
