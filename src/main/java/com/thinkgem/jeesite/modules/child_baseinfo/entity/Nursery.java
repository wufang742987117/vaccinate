package com.thinkgem.jeesite.modules.child_baseinfo.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;

public class Nursery extends DataEntity<ChildVaccinaterecord> {
	private static final long serialVersionUID = 1L;
	
	private String name;//疫苗名称
	private String vaccinatedate;//疫苗接种时间
	private String pin;//疫苗针次
	private String status;//是否完成。0未完成，1完成
	private String vaccname;//疫苗小类名称
	private String vaccineid;	//疫苗id
	private String inocUnionCode;//替代关系
	public String getVaccname() {
		return vaccname;
	}

	public void setVaccname(String vaccname) {
		this.vaccname = vaccname;
	}

	private int size;//长度
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVaccinatedate() {
		return vaccinatedate;
	}

	public void setVaccinatedate(String vaccinatedate) {
		this.vaccinatedate = vaccinatedate;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}

	public String getInocUnionCode() {
		return inocUnionCode;
	}

	public void setInocUnionCode(String inocUnionCode) {
		this.inocUnionCode = inocUnionCode;
	}

	

}
