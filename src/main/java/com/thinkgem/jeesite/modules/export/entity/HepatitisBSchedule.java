package com.thinkgem.jeesite.modules.export.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class HepatitisBSchedule extends DataEntity<ExportChildhelp>{
	private static final long serialVersionUID = 1L;
	private String childcode;//儿童编码
	private String childname;//儿童姓名
	private String gender;//儿童性别1男2女
	private Date birthday;//出生日期
	private String dosage;//针次
	private Date vaccinatedate;//疫苗完成时间
	private Date vaccinatedateone;//疫苗完成时间(1)
	private Date vaccinatedatetwo;//疫苗完成时间(2)
	private Date vaccinatedatethree;//疫苗完成时间(3)
	private String evaluateo;//评价
	private String evaluateone;//评价(1)
	private String evaluatetwo;//评价(2)
	private String evaluatethree;//评价(3)
	public String getChildcode() {
		return childcode;
	}
	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}
	public String getChildname() {
		return childname;
	}
	public void setChildname(String childname) {
		this.childname = childname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEvaluateone() {
		return evaluateone;
	}
	public void setEvaluateone(String evaluateone) {
		this.evaluateone = evaluateone;
	}
	public String getEvaluatetwo() {
		return evaluatetwo;
	}
	public void setEvaluatetwo(String evaluatetwo) {
		this.evaluatetwo = evaluatetwo;
	}
	public String getEvaluatethree() {
		return evaluatethree;
	}
	public void setEvaluatethree(String evaluatethree) {
		this.evaluatethree = evaluatethree;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public String getEvaluateo() {
		return evaluateo;
	}
	public void setEvaluateo(String evaluateo) {
		this.evaluateo = evaluateo;
	}
	public Date getVaccinatedate() {
		return vaccinatedate;
	}
	public void setVaccinatedate(Date vaccinatedate) {
		this.vaccinatedate = vaccinatedate;
	}
	public Date getVaccinatedateone() {
		return vaccinatedateone;
	}
	public void setVaccinatedateone(Date vaccinatedateone) {
		this.vaccinatedateone = vaccinatedateone;
	}
	public Date getVaccinatedatetwo() {
		return vaccinatedatetwo;
	}
	public void setVaccinatedatetwo(Date vaccinatedatetwo) {
		this.vaccinatedatetwo = vaccinatedatetwo;
	}
	public Date getVaccinatedatethree() {
		return vaccinatedatethree;
	}
	public void setVaccinatedatethree(Date vaccinatedatethree) {
		this.vaccinatedatethree = vaccinatedatethree;
	}
	
	

}
