/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.num.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 狂犬疫苗针次Entity
 * 
 * @author zhouqj
 * @version 2017-06-06
 */
public class BsRabiesSocket extends DataEntity<BsRabiesSocket> {

	private static final long serialVersionUID = 1L;

	private String vaccid;
	private Integer vaccinum; // 针次1,2,3,4,5,
	private String batchnum; // 疫苗批号
	private String manufacturer; // 生产厂家
	private String standard; // 疫苗规格
	private String storenum; // 疫苗库存
	private String storenum2; // 已付款总量
	private String vcount; // 小计
	private String vaccname; // 疫苗名称
	private int leng = 0; // 接种记录接口使用长度参数

	/** 查询条件-开始时间 */
	private Date beginTime;
	/** 查询条件-结束时间 */
	private Date endTime;

	public BsRabiesSocket() {
		super();
	}

	public BsRabiesSocket(String id) {
		super(id);
	}

	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}

	public String getManufacturer() {
		if (!StringUtils.isNotBlank(manufacturer)) {
			return "未知";
		}
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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

	public String getVcount() {
		return vcount;
	}

	public void setVcount(String vcount) {
		this.vcount = vcount;
	}

	public String getVaccname() {
		if (!StringUtils.isNotBlank(vaccname)) {
			return "未知";
		}
		return vaccname;
	}

	public void setVaccname(String vaccname) {
		this.vaccname = vaccname;
	}

	public Integer getVaccinum() {
		return vaccinum;
	}

	public void setVaccinum(Integer vaccinum) {
		this.vaccinum = vaccinum;
	}

	public int getLeng() {
		return leng;
	}

	public void setLeng(int leng) {
		this.leng = leng;
	}

	public String getStorenum() {
		return storenum;
	}

	public void setStorenum(String storenum) {
		this.storenum = storenum;
	}

	public String getStorenum2() {
		return storenum2;
	}

	public void setStorenum2(String storenum2) {
		this.storenum2 = storenum2;
	}

	public String getVaccid() {
		return vaccid;
	}

	public void setVaccid(String vaccid) {
		this.vaccid = vaccid;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	
}