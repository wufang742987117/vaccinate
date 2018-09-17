/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.export.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 按月份统计应种实种Entity
 * @author Jack
 * @version 2018-02-01
 */
public class ExpRoutinevacc61Detail extends DataEntity<ExpRoutinevacc61Detail> {
	
	private static final long serialVersionUID = 1L;
	private String yearMonth;		// 计算数据的年月
	private String unitCode;		// 社区编码
	private String unitName;		// 社区编码
	private String vaccCode;		// 疫苗编码
	private String dosage;		// 针次
	private String childcode;		// 儿童编码
	private String type;		// 类型：1、应种；2、实种(暂无此类型)；3、应种和实种
	private String localCode; 	//互联网版本的标识编码
	
	public ExpRoutinevacc61Detail() {
		super();
	}

	public ExpRoutinevacc61Detail(String id){
		super(id);
	}

	@Length(min=0, max=32, message="计算数据的年月长度必须介于 0 和 32 之间")
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	
	@Length(min=0, max=64, message="社区编码长度必须介于 0 和 64 之间")
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	@Length(min=0, max=64, message="社区编码长度必须介于 0 和 64 之间")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Length(min=0, max=32, message="疫苗编码长度必须介于 0 和 32 之间")
	public String getVaccCode() {
		return vaccCode;
	}

	public void setVaccCode(String vaccCode) {
		this.vaccCode = vaccCode;
	}
	
	@Length(min=0, max=32, message="针次长度必须介于 0 和 32 之间")
	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	
	@Length(min=0, max=64, message="儿童编码长度必须介于 0 和 64 之间")
	public String getChildcode() {
		return childcode;
	}

	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}
	
	@Length(min=0, max=10, message="类型：1、应种；2、实种(暂无此类型)；3、应种和实种；长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	
}