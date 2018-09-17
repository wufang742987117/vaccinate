/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 可是信息管理Entity
 * @author yangjian
 * @version 2018-02-27
 */
public class SysVaccDepartmentInfo extends DataEntity<SysVaccDepartmentInfo> {
	
	private static final long serialVersionUID = 1L;
	private String localname;		// 接种单位名称
	private String jobstime;		// 工作时间
	private String phonenumber;		// 联系电话
	private String address;		// 详细地址
	
	public SysVaccDepartmentInfo() {
		super();
	}

	public SysVaccDepartmentInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=50, message="接种单位名称长度必须介于 0 和 50 之间")
	public String getLocalname() {
		return localname;
	}

	public void setLocalname(String localname) {
		this.localname = localname;
	}
	
	@Length(min=0, max=50, message="工作时间长度必须介于 0 和 50 之间")
	public String getJobstime() {
		return jobstime;
	}

	public void setJobstime(String jobstime) {
		this.jobstime = jobstime;
	}
	
	@Length(min=0, max=50, message="联系电话长度必须介于 0 和 50 之间")
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	@Length(min=0, max=500, message="详细地址长度必须介于 0 和 500 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}