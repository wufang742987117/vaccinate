/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kindergarten.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 儿童信息中的幼儿园Entity
 * @author sen
 * @version 2017-08-11
 */
public class BsKindergarten extends DataEntity<BsKindergarten> {
	
	private static final long serialVersionUID = 1L;
	private String kindergartenCode;		// 幼儿园编码
	private String nameAll;		// 单位全称
	private String name;		// 单位简称
	private String address;		// 地址
	private String contacts;		// 联系人
	private String teletephone;		// 联系电话
	private String moblie;		// 手机
	
	public BsKindergarten() {
		super();
	}

	public BsKindergarten(String id){
		super(id);
	}

	@Length(min=0, max=3, message="幼儿园编码长度必须介于 0 和 3 之间")
	public String getKindergartenCode() {
		return kindergartenCode;
	}

	public void setKindergartenCode(String kindergartenCode) {
		this.kindergartenCode = kindergartenCode;
	}
	
	@Length(min=0, max=32, message="单位全称长度必须介于 0 和 32 之间")
	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}
	
	@Length(min=0, max=32, message="单位简称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=5, message="联系人长度必须介于 0 和 5 之间")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@Length(min=0, max=11, message="联系电话长度必须介于 0 和 11 之间")
	public String getTeletephone() {
		return teletephone;
	}

	public void setTeletephone(String teletephone) {
		this.teletephone = teletephone;
	}
	
	@Length(min=0, max=11, message="手机长度必须介于 0 和 11 之间")
	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	
}