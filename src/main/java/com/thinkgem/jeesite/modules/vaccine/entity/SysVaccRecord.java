/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 疫苗使用记录Entity
 * @author fuxin
 * @version 2017-04-22
 */
public class SysVaccRecord extends DataEntity<SysVaccRecord> {
	
	private static final long serialVersionUID = 1L;
	private String pid;		// 产品id
	private String uid;		// 用户id
	private String pname;		// 产品名称
	private String uname;		// 用户名称
	private String isnew;		// 是否消耗疫苗1是 0否
	private long stock;			//当前库存
	
	public SysVaccRecord() {
		super();
	}

	public SysVaccRecord(String id){
		super(id);
	}
	
	public SysVaccRecord(String pid, String uid, String pname,
			String uname) {
		super();
		this.pid = pid;
		this.uid = uid;
		this.pname = pname;
		this.uname = uname;
	}

	@Length(min=0, max=32, message="产品id长度必须介于 0 和 32 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=32, message="用户id长度必须介于 0 和 32 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=0, max=50, message="产品名称长度必须介于 0 和 50 之间")
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	@Length(min=0, max=50, message="用户名称长度必须介于 0 和 50 之间")
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}
	
}