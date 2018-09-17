package com.thinkgem.jeesite.modules.export.entity;

public class CountInfo {
	private String childId; //儿童ID
	private String vaccineId; //疫苗ID
	private String vaccNid; //NID
	private String recordNum; //剂次
	private String vaccinateDate; //接种日期
	
	
	public CountInfo(String childId, String vaccineId, String vaccNid, String recordNum, String vaccinateDate) {
		super();
		this.childId = childId;
		this.vaccineId = vaccineId;
		this.vaccNid = vaccNid;
		this.recordNum = recordNum;
		this.vaccinateDate = vaccinateDate;
	}
	
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	public String getVaccineId() {
		return vaccineId;
	}
	public void setVaccineId(String vaccineId) {
		this.vaccineId = vaccineId;
	}
	public String getVaccNid() {
		return vaccNid;
	}
	public void setVaccNid(String vaccNid) {
		this.vaccNid = vaccNid;
	}
	public String getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}
	public String getVaccinateDate() {
		return vaccinateDate;
	}
	public void setVaccinateDate(String vaccinateDate) {
		this.vaccinateDate = vaccinateDate;
	}
	
}
