package com.thinkgem.jeesite.modules.export.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class Export extends DataEntity<ExportChildhelp>{
	private static final long serialVersionUID = 1L;
	private String code;//编码
	private String name;//名字
	private String count;//个数
	private Date time;
	private String begintime;
	private String endtime;
	private String timestr;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public Date getTime() {
		if(null == time && StringUtils.isNotBlank(timestr)){
			return DateUtils.parseDate(timestr);
		}
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTimestr() {
		return timestr;
	}
	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	

}
