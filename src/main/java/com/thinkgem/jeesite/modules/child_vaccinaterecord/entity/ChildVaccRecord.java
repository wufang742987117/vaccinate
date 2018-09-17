package com.thinkgem.jeesite.modules.child_vaccinaterecord.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
@SuppressWarnings("serial")
public class ChildVaccRecord extends DataEntity<ChildVaccRecord> {

	private String childid; // 儿童ID
	private String vaccineid; // 疫苗ID
	private String dosage; // 疫苗计次
	private Date vaccinatedate; // 接种日期
	private String bodypart; // 接种部位
	private String batch; // 疫苗批号
	private String status; // 接种状态0未完成1已完成9删除
	private String vaccName;		// 疫苗小类名称
	private String vacctype;//接种类型
	public String getChildid() {
		return childid;
	}
	public void setChildid(String childid) {
		this.childid = childid;
	}
	public String getVaccineid() {
		return vaccineid;
	}
	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public Date getVaccinatedate() {
		return vaccinatedate;
	}
	public void setVaccinatedate(Date vaccinatedate) {
		this.vaccinatedate = vaccinatedate;
	}
	public String getBodypart() {
		return bodypart;
	}
	public void setBodypart(String bodypart) {
		this.bodypart = bodypart;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	 
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	 
	 
	public String getVaccName() {
		return vaccName;
	}
	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}
	public String getVacctype() {
		return vacctype;
	}
	public void setVacctype(String vacctype) {
		this.vacctype = vacctype;
	}
	@Override
	public String toString() {
		return "ChildVaccRecord [childid=" + childid + ", vaccineid=" + vaccineid + ", dosage=" + dosage
				+ ", vaccinatedate=" + vaccinatedate + ", bodypart=" + bodypart + ", batch=" + batch + ", status="
				+ status + ", vaccName=" + vaccName + ", vacctype=" + vacctype + "]";
	}
	 
	 
	
	
 
	
	 
	 
	
	

	
}