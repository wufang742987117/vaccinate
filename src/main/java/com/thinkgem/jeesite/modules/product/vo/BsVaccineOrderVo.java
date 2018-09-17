package com.thinkgem.jeesite.modules.product.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BsVaccineOrderVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String remarks;
	private Date createDate;
	private UploadEpiProCreateBy createBy = new UploadEpiProCreateBy();
	private Date outboundDate;
	private String orderType;
	private String cType;
	private String orderStatus;
	private String supplyorgName;
	private String supplyorgCode;
	private String consignorName;
	private String createorgName;
	private String createcode;

	public BsVaccineOrderVo() {
		super();
	}

	public BsVaccineOrderVo(String remarks, Date createDate, UploadEpiProCreateBy createBy, Date outboundDate,
			String orderType, String cType, String orderStatus, String supplyorgName, String supplyorgCode,
			String consignorName, String createorgName, String createcode) {
		super();
		this.remarks = remarks;
		this.createDate = createDate;
		this.createBy = createBy;
		this.outboundDate = outboundDate;
		this.orderType = orderType;
		this.cType = cType;
		this.orderStatus = orderStatus;
		this.supplyorgName = supplyorgName;
		this.supplyorgCode = supplyorgCode;
		this.consignorName = consignorName;
		this.createorgName = createorgName;
		this.createcode = createcode;
	}

	public String getRemarks() {
		return remarks;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public UploadEpiProCreateBy getCreateBy() {
		return createBy;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getOutboundDate() {
		return outboundDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getcType() {
		return cType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public String getSupplyorgName() {
		return supplyorgName;
	}

	public String getSupplyorgCode() {
		return supplyorgCode;
	}

	public String getConsignorName() {
		return consignorName;
	}

	public String getCreateorgName() {
		return createorgName;
	}

	public String getCreatecode() {
		return createcode;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateBy(UploadEpiProCreateBy createBy) {
		this.createBy = createBy;
	}

	public void setOutboundDate(Date outboundDate) {
		this.outboundDate = outboundDate;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setSupplyorgName(String supplyorgName) {
		this.supplyorgName = supplyorgName;
	}

	public void setSupplyorgCode(String supplyorgCode) {
		this.supplyorgCode = supplyorgCode;
	}

	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}

	public void setCreateorgName(String createorgName) {
		this.createorgName = createorgName;
	}

	public void setCreatecode(String createcode) {
		this.createcode = createcode;
	}

}



