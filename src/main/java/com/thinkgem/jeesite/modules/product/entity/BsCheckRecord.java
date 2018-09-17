/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 库存盘点记录Entity
 * @author fuxin
 * @version 2017-12-28
 */
public class BsCheckRecord extends DataEntity<BsCheckRecord> {
	
	/** 状态-正常 */
	public static final String STATUS_NORMAL = "0";
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 产品id
	private Long checkBefore;		// 库存盘点前
	private Long checkAfter;		// 库存盘点后
	private String status;		// 状态
	private String code;		// 盘点库存一次盘点哪些
	private String checknum;		// 盘点数量
	
	private String manufacturer;  //疫苗厂商
	private String name;      //疫苗名称
	private String vaccineid;  //疫苗编号
	private String batchno;       //批次
	private Date createDate;  //盘点时间
	private Date beginCreateDate; //盘点时间(起）
	private Date endCreateDate; // 盘点时间(止）
	
	
	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setVaccName(String Name) {
		this.name = Name;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public BsCheckRecord() {
		super();
	}

	public BsCheckRecord(String id){
		super(id);
	}

	@Length(min=1, max=32, message="产品id长度必须介于 1 和 32 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public Long getCheckBefore() {
		return checkBefore;
	}

	public void setCheckBefore(Long checkBefore) {
		this.checkBefore = checkBefore;
	}
	
	public Long getCheckAfter() {
		return checkAfter;
	}

	public void setCheckAfter(Long checkAfter) {
		this.checkAfter = checkAfter;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="盘点库存一次盘点哪些长度必须介于 0 和 32 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=32, message="盘点数量长度必须介于 0 和 32 之间")
	public String getChecknum() {
		return checknum;
	}

	public void setChecknum(String checknum) {
		this.checknum = checknum;
	}
	
}