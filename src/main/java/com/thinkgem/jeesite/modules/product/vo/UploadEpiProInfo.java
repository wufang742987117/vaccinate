package com.thinkgem.jeesite.modules.product.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UploadEpiProInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String productId;
	private String sort;
	private String orignprice;
	private String sellprice;
	private String num;
	private String vaccPid;
	private Date vaccStartDate;
	private Date vaccEndDate;
	private String remarks;
	

	public UploadEpiProInfo() {
		super();
	}
	
	public UploadEpiProInfo(String productId, String sort, String orignprice, String sellprice, String num) {
		super();
		this.productId = productId;
		this.sort = sort;
		this.orignprice = orignprice;
		this.sellprice = sellprice;
		this.num = num;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrignprice() {
		return this.orignprice;
	}

	public void setOrignprice(String orignprice) {
		this.orignprice = orignprice;
	}

	public String getSellprice() {
		return this.sellprice;
	}

	public void setSellprice(String sellprice) {
		this.sellprice = sellprice;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getVaccPid() {
		return vaccPid;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getVaccStartDate() {
		return vaccStartDate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getVaccEndDate() {
		return vaccEndDate;
	}

	public void setVaccPid(String vaccPid) {
		this.vaccPid = vaccPid;
	}

	public void setVaccStartDate(Date vaccStartDate) {
		this.vaccStartDate = vaccStartDate;
	}

	public void setVaccEndDate(Date vaccEndDate) {
		this.vaccEndDate = vaccEndDate;
	}
	
	
	
}