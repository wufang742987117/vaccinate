package com.thinkgem.jeesite.modules.export.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class MonthlyReportOnVaccConsumption extends DataEntity<ExportChildhelp>{
	private static final long serialVersionUID = 1L;
	private String name;//疫苗名称
	private String manufacturer;//厂家
	private String batchno;//批号
	private String count;//个数
	private String isforeign;//疫苗类型
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getIsforeign() {
		return isforeign;
	}
	public void setIsforeign(String isforeign) {
		this.isforeign = isforeign;
	}
	
	
	

}
