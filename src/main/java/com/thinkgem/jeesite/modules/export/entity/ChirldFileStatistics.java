package com.thinkgem.jeesite.modules.export.entity;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ChirldFileStatistics extends DataEntity<ChirldFileStatistics>{

	private static final long serialVersionUID = -6745038243193784930L;
	
	private String wanzhenCodes;//档案完整性字段，以'/'分割的 字符串
	private String recordCodes;//在册类别  ，以'/'分割的 字符串
	private String resideCodes;//居住类型，以'/'分割的 字符串
	private String areaCodes;//社区类型，以'/'分割的 字符串
	private String vaccCodes;//疫苗种类  ，以'/'分割的字符串
	private String selectType;//查询类型，本点建卡、等
	private String sex;//儿童数组
	private	Date startBirthday;//出生日期开始时间
	private	Date endBirthday;//出生日期截止时间
	private	Date startCarday;//建卡开始时间
	private	Date endCarday;//建卡截止时间
	private Date startVaccday;//接种时间 开始
	private Date endVaccday;// 接种时间结束
	private List<String> wanzhenlist ;//完整性字段数组
	private List<String> recordlist;//在册类别数组
	private List<String> residelist;//居住类型数组
	private List<String> arealist;//社区数组
	private List<String> vacclist;
	public String getWanzhenCodes() {
		return wanzhenCodes;
	}
	public void setWanzhenCodes(String wanzhenCodes) {
		this.wanzhenCodes = wanzhenCodes;
	}
	public String getRecordCodes() {
		return recordCodes;
	}
	public void setRecordCodes(String recordCodes) {
		this.recordCodes = recordCodes;
	}
	public String getResideCodes() {
		return resideCodes;
	}
	public void setResideCodes(String resideCodes) {
		this.resideCodes = resideCodes;
	}
	public String getAreaCodes() {
		return areaCodes;
	}
	public void setAreaCodes(String areaCodes) {
		this.areaCodes = areaCodes;
	}
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public Date getStartBirthday() {
		return startBirthday;
	}
	public void setStartBirthday(Date startBirthday) {
		this.startBirthday = startBirthday;
	}
	public Date getEndBirthday() {
		return endBirthday;
	}
	public void setEndBirthday(Date endBirthday) {
		this.endBirthday = endBirthday;
	}
	public Date getStartCarday() {
		return startCarday;
	}
	public void setStartCarday(Date startCarday) {
		this.startCarday = startCarday;
	}
	public Date getEndCarday() {
		return endCarday;
	}
	public void setEndCarday(Date endCarday) {
		this.endCarday = endCarday;
	}
	public List<String> getWanzhenlist() {
		return wanzhenlist;
	}
	public void setWanzhenlist(List<String> list) {
		this.wanzhenlist = list;
	}
	public List<String> getRecordlist() {
		return recordlist;
	}
	public void setRecordlist(List<String> list) {
		this.recordlist = list;
	}
	public List<String> getResidelist() {
		return residelist;
	}
	public void setResidelist(List<String> list) {
		this.residelist = list;
	}
	public List<String> getArealist() {
		return arealist;
	}
	public void setArealist(List<String> list) {
		this.arealist = list;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getVaccCodes() {
		return vaccCodes;
	}
	public void setVaccCodes(String vaccCodes) {
		this.vaccCodes = vaccCodes;
	}
	public List<String> getVacclist() {
		return vacclist;
	}
	public void setVacclist(List<String> vacclist) {
		this.vacclist = vacclist;
	}
	public Date getStartVaccday() {
		return startVaccday;
	}
	public void setStartVaccday(Date startVaccday) {
		this.startVaccday = startVaccday;
	}
	public Date getEndVaccday() {
		return endVaccday;
	}
	public void setEndVaccday(Date endVaccday) {
		this.endVaccday = endVaccday;
	}
	
	

}
