/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 疫苗接种管理Entity
 * 
 * @author 王德地
 * @version 2017-02-07
 */
public class ChildVaccLog extends DataEntity<ChildVaccLog> {

	private static final long serialVersionUID = 1L;
	private Date vaccinatedate; // 接种日期
	private String childname; // 儿童姓名
	private String parentsName; // 家长姓名
	private String gender; // 儿童性别
	private Date birthday; // 儿童生日
	private String childAbnormalReaction; // 是否异常反应-过敏史
	private String vaccName; // 疫苗小类名称
	private String dosage; // 疫苗计次
	private String batch; // 疫苗批号
	private String manufacturer;// 疫苗的制造厂商
	private String signList;// 签字
	private String childRemarks; // 备注

	private String vaccNameBatch; // 接种疫苗及第几剂次
	private String fever; // 发热
	private String cough; // 咳嗽
	private String diarrhea; // 腹泻
	private String symptom; // 其他症状
	private String disease; // 近期是否患病
	

	@ExcelField(align=2, title="接种日期", sort = 10)
	public Date getVaccinatedate() {
		return vaccinatedate;
	}

	public void setVaccinatedate(Date vaccinatedate) {
		this.vaccinatedate = vaccinatedate;
	}

	@ExcelField(align=2, title="儿童姓名", sort = 20)
	public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}

	public String getParentsName() {
		return parentsName;
	}

	public void setParentsName(String parentsName) {
		this.parentsName = parentsName;
	}

	@ExcelField(align=2, title="性别", sort = 30, dictType="sex")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@ExcelField(align=2, title="出生日期", sort = 40)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@ExcelField(align=2, title="过敏史", sort = 50)
	public String getChildAbnormalReaction() {
		return childAbnormalReaction;
	}

	public void setChildAbnormalReaction(String childAbnormalReaction) {
		this.childAbnormalReaction = childAbnormalReaction;
	}

	@ExcelField(align=2, title="发热℃", sort = 60)
	public String getFever() {
		return fever;
	}

	public void setFever(String fever) {
		this.fever = fever;
	}

	@ExcelField(align=2, title="咳嗽", sort = 70)
	public String getCough() {
		return cough;
	}

	public void setCough(String cough) {
		this.cough = cough;
	}

	@ExcelField(align=2, title="腹泻", sort = 80)
	public String getDiarrhea() {
		return diarrhea;
	}

	public void setDiarrhea(String diarrhea) {
		this.diarrhea = diarrhea;
	}
	
	@ExcelField(align=2, title="其他症状", sort = 81)
	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	@ExcelField(align=2, title="近期是否患病", sort = 90)
	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getVaccName() {
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	
	@ExcelField(align=2, title="接种疫苗及第几剂次", sort = 100)
	public String getVaccNameBatch() {
		return vaccNameBatch;
	}

	public void setVaccNameBatch(String vaccNameBatch) {
		this.vaccNameBatch = vaccNameBatch;
	}

	@ExcelField(align=2, title="疫苗批号", sort = 110)
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	@ExcelField(align=2, title="生产企业", sort = 111)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@ExcelField(align=2, title="家长签字", sort = 120)
	public String getSignList() {
		return signList;
	}

	public void setSignList(String signList) {
		this.signList = signList;
	}

	@ExcelField(align=2, title="备注", sort = 130)
	public String getChildRemarks() {
		return childRemarks;
	}

	public void setChildRemarks(String childRemarks) {
		this.childRemarks = childRemarks;
	}

}