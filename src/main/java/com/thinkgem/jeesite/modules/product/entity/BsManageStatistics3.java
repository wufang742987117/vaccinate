/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;


/**
 * 疫苗信息Entity
 * @author 王德地
 * @version 2017-02-20
 */
public class BsManageStatistics3 extends DataEntity<BsManageStatistics3> {
	
	private static final long serialVersionUID = 1L;
	private String name;//疫苗大类名称
	private String code;//疫苗大类编码
	private String total;//疫苗入库数量
	private String scraptotal;//疫苗大类报废数量
	private String completion;//接种完成针数
	private String time;//时间
	private String yeay;//年
	private String month;//月
	private String day;//天
	
	/** 查询条件-开始时间 */
	private Date beginTime;

	/** 查询条件-结束时间 */
	private Date endTime;
	
	public BsManageStatistics3() {
		super();
	}
	@ExcelField(title="疫苗大类名称", align=1, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@ExcelField(title="入库数量", align=1, sort=20)
	public String getTotal() {
		if(StringUtils.isBlank(total)){
			total="0";
		}
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	@ExcelField(title="报废数量", align=1, sort=40)
	public String getScraptotal() {
		if(StringUtils.isBlank(scraptotal)){
			scraptotal="0";
		}
		return scraptotal;
	}

	public void setScraptotal(String scraptotal) {
		this.scraptotal = scraptotal;
	}
	@ExcelField(title="接种数量", align=1, sort=30)
	public String getCompletion() {
		if(StringUtils.isBlank(completion)){
			completion="0";
		}
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
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

	public BsManageStatistics3(String id){
		super(id);
	}
	
	// begin
		public String getBegin() {
			return DateUtils.formatDateTime(this.beginTime);
		}

		// end
		public String getEnd() {
			return DateUtils.formatDateTime(this.endTime);
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getYeay() {
			return yeay;
		}

		public void setYeay(String yeay) {
			this.yeay = yeay;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}
	
}