/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rabiesvaccine.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;

/**
 * 狂犬疫苗-接种者档案
 * 
 * @author wangdedi
 * @version 2017-02-24
 */
public class BsRabiesCheckinStock extends DataEntity<BsRabiesCheckinStock> {

	private static final long serialVersionUID = 1L;
	private String vaccinateid; // 疫苗名称
	private String vaccinatename; // 疫苗名称
	private String standard; // 规格
	private String manufacturer; // 生产厂家（制造商）
	private String batchnum; // 批号
	private String storenum; // 现有库存
	private String storenum1; // 未付款总量
	private String storenum2; // 已付款总量
	private String storenum3; // 已完成总量
	private int leng = 0; // 记录接口使用长度参数
	private String createByName; // 姓名
	private String createById; // 姓名编号
	
	/** 查询条件-开始时间 */
	private Date beginTime;
	/** 查询条件-结束时间 */
	private Date endTime;

	public BsRabiesCheckinStock() {
		super();
	}

	public BsRabiesCheckinStock(String id) {
		super(id);
	}

	@Length(min = 0, max = 50, message = "疫苗名称长度必须介于 0 和 50 之间")
	public String getVaccinatename() {
		return vaccinatename;
	}

	public void setVaccinatename(String vaccinatename) {
		this.vaccinatename = vaccinatename;
	}

	@Length(min = 0, max = 50, message = "规格长度必须介于 0 和 50 之间")
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Length(min = 0, max = 200, message = "生产厂家（制造商）长度必须介于 0 和 200 之间")
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Length(min = 0, max = 50, message = "批号长度必须介于 0 和 50 之间")
	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}

	public String getVaccinateid() {
		return vaccinateid;
	}

	public void setVaccinateid(String vaccinateid) {
		this.vaccinateid = vaccinateid;
	}

	public String getStorenum() {
		return storenum;
	}

	public void setStorenum(String storenum) {
		this.storenum = storenum;
	}

	public String getStorenum1() {
		return storenum1;
	}

	public void setStorenum1(String storenum1) {
		this.storenum1 = storenum1;
	}

	public String getStorenum2() {
		return storenum2;
	}

	public void setStorenum2(String storenum2) {
		this.storenum2 = storenum2;
	}

	public String getStorenum3() {
		return storenum3;
	}

	public void setStorenum3(String storenum3) {
		this.storenum3 = storenum3;
	}

	public int getLeng() {
		return leng;
	}

	public void setLeng(int leng) {
		this.leng = leng;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	// begin
	public String getBegin() {
		return DateUtils.formatDateTime(this.beginTime);
	}

	// end
	public String getEnd() {
		return DateUtils.formatDateTime(this.endTime);
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	
}