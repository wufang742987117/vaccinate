/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.department.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 预防门诊编码Entity
 * @author wangdedi
 * @version 2017-04-08
 */
public class SysVaccDepartment extends DataEntity<SysVaccDepartment> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 门诊编码
	private String name;		// 门诊简称
	private String allName;		// 门诊全称
	private String codeLevel;		// code级别
	private String fCode;		// 父节点code
	private String nCode;		// 新code
	
	public SysVaccDepartment() {
		super();
	}

	public SysVaccDepartment(String id){
		super(id);
	}

	@Length(min=0, max=20, message="门诊编码长度必须介于 0 和 20 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=50, message="门诊简称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="门诊全称长度必须介于 0 和 100 之间")
	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}
	
	@Length(min=0, max=10, message="code级别长度必须介于 0 和 10 之间")
	public String getCodeLevel() {
		return codeLevel;
	}

	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	
	@Length(min=0, max=20, message="父节点code长度必须介于 0 和 20 之间")
	public String getFCode() {
		return fCode;
	}

	public void setFCode(String fCode) {
		this.fCode = fCode;
	}
	
	@Length(min=0, max=20, message="新code长度必须介于 0 和 20 之间")
	public String getNCode() {
		return nCode;
	}

	public void setNCode(String nCode) {
		this.nCode = nCode;
	}
	
}