package com.thinkgem.jeesite.modules.child_vaccinaterecord.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class ExportExcelBean extends DataEntity<ExportExcelBean> {
	private static final long serialVersionUID = 1L;
	private String childcode; // 儿童编码
	private String childname; // 儿童姓名
	private String gender; // 性别
	private String birthDay; // 出生日期的字符串格式-应种统计和或逾期未种报表生成字段
	private String reside; // 居住类别
	private String guardianname; // 母亲姓名
	private String guardianmobile; // 母亲手机号码
	private String fatherName; // 父/母亲
	private String fatherphone; // 父亲/母亲电话
	private String address; // 详细地址
	private String area; // 区域划分
	private String vaccName;// 疫苗名称
	private String dosage; // 针次-应种统计或逾期未种报表生成字段

	@ExcelField(align = 2, title = "儿童编码", sort = 10)
	@Length(min = 1, max = 50, message = "儿童编码长度必须介于 1 和 50 之间")
	public String getChildcode() {
		return childcode;
	}

	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}

	@ExcelField(align = 2, title = "儿童姓名", sort = 20)
	@Length(min = 1, max = 20, message = "儿童姓名长度必须介于 1 和 20 之间")
	public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}

	@ExcelField(align = 2, title = "性别", sort = 30, dictType = "sex")
	@Length(min = 1, max = 1, message = "性别长度必须介于 1 和 1 之间")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@ExcelField(align = 2, title = "出生日期", sort = 40)
	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	@ExcelField(align = 2, title = "母亲姓名", sort = 50)
	@Length(min = 0, max = 20, message = "监护人姓名长度必须介于 0 和 20 之间")
	public String getGuardianname() {
		return guardianname;
	}

	public void setGuardianname(String guardianname) {
		this.guardianname = guardianname;
	}
	
	@ExcelField(align = 2, title = "母亲手机号", sort = 55)
	public String getGuardianmobile() {
		return guardianmobile;
	}
	
	public void setGuardianmobile(String guardianmobile) {
		this.guardianmobile = guardianmobile;
	}
	
	@ExcelField(align = 2, title = "父亲姓名", sort = 60)
	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	@ExcelField(align = 2, title = "父亲电话", sort = 65)
	public String getFatherphone() {
		return fatherphone;
	}

	public void setFatherphone(String fatherphone) {
		this.fatherphone = fatherphone;
	}
	
	@ExcelField(align = 2, title = "户籍类别", sort = 70, dictType="reside")
	public String getReside() {
		return reside;
	}

	public void setReside(String reside) {
		this.reside = reside;
	}

	@ExcelField(align = 2, title = "区域划分", sort = 80)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@ExcelField(align = 2, title = "地址", sort = 90)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(align = 2, title = "疫苗名称", sort = 100)
	public String getvaccName() {
		return vaccName;
	}

	public void setvaccName(String vaccName) {
		this.vaccName = vaccName;
	}
	
	@ExcelField(align = 2, title = "针次", sort = 110)
	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

}
