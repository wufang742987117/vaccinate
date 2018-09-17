package com.thinkgem.jeesite.modules.export.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ExportTeo extends DataEntity<ExportChildhelp>{
	
	private static final long serialVersionUID = 1L;
	
	private String begintime;
	private String endtime;
	private String timeYear;
	private String timeQuarterly;
	private String name;
	private String specification;
	private String num;
	private String num1;
	private String num2;
	
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
	public String getTimeYear() {
		return timeYear;
	}
	public void setTimeYear(String timeYear) {
		this.timeYear = timeYear;
	}
	public String getTimeQuarterly() {
		return timeQuarterly;
	}
	public void setTimeQuarterly(String timeQuarterly) {
		this.timeQuarterly = timeQuarterly;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getNum1() {
		return num1;
	}
	public void setNum1(String num1) {
		this.num1 = num1;
	}
	public String getNum2() {
		return num2;
	}
	public void setNum2(String num2) {
		this.num2 = num2;
	}
	
}
