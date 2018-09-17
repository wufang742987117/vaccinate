/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;


/**
 * 疫苗信息Entity
 * @author 王德地
 * @version 2017-02-20
 */
public class BsManageStatistics2 extends DataEntity<BsManageStatistics2> {
	
	private static final long serialVersionUID = 1L;
	private String vaccineid;	// 疫苗小类id
	private String vaccname;	// 疫苗小类名称
	private String batchno;		// 批次编号
	private String gnum;		// 疫苗大类id
	private String gname;		// 疫苗大类名称
	private String sums;		// 总量
	private String vcode;		// 分类
	
	/** 查询条件-开始时间 */
	private Date beginTime;

	/** 查询条件-结束时间 */
	private Date endTime;
	
	public BsManageStatistics2() {
		super();
	}

	public BsManageStatistics2(String id){
		super(id);
	}
	
	public String getVaccineid() {
		return vaccineid;
	}
	
	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	
	public String getVaccname() {
		return vaccname;
	}
	
	public void setVaccname(String vaccname) {
		this.vaccname = vaccname;
	}
	
	public String getBatchno() {
		return batchno;
	}
	
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="疫苗大类id", align=1, sort=10)
	public String getGnum() {
		return gnum;
	}
	
	public void setGnum(String gnum) {
		this.gnum = gnum;
	}
	
	@ExcelField(title="疫苗大类名称", align=1, sort=50)
	public String getGname() {
		return gname;
	}
	
	public void setGname(String gname) {
		this.gname = gname;
	}
	
	@ExcelField(title="接种总数", align=1, sort=60)
	public String getSums() {
		return sums;
	}
	
	public void setSums(String sums) {
		this.sums = sums;
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

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	
}