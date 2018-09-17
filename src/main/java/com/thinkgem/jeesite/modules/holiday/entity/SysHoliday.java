/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.holiday.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 节假日Entity
 * @author fuxin
 * @version 2017-09-23
 */
public class SysHoliday extends DataEntity<SysHoliday> {
	
	private static final long serialVersionUID = 1L;
	
	public final static String DATETYPE_WEEK = "1";
	public final static String DATETYPE_DAY = "0";
	
	private String dateType;		// 0法定节假日1非工作日
	private Date dateTime;		// 具体日期
	private String dateDay;		// 具体星期几
	private Date dateTimeAfter;
	private Date dateTimeBefore;
	
	public SysHoliday() {
		super();
	}

	public SysHoliday(String id){
		super(id);
	}

	@Length(min=1, max=1, message="0法定节假日1非工作日长度必须介于 1 和 1 之间")
	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	@Length(min=0, max=1, message="具体星期几长度必须介于 0 和 1 之间")
	public String getDateDay() {
		return dateDay;
	}
	
	public int getDateDayInt(){
		if(StringUtils.isNotBlank(dateDay)){
			return Integer.valueOf(dateDay);
		}
		return 0;
	}

	public void setDateDay(String dateDay) {
		this.dateDay = dateDay;
	}

	public Date getDateTimeAfter() {
		return dateTimeAfter;
	}

	public Date getDateTimeBefore() {
		return dateTimeBefore;
	}

	public void setDateTimeAfter(Date dateTimeAfter) {
		this.dateTimeAfter = dateTimeAfter;
	}

	public void setDateTimeBefore(Date dateTimeBefore) {
		this.dateTimeBefore = dateTimeBefore;
	}
	
	
	
}