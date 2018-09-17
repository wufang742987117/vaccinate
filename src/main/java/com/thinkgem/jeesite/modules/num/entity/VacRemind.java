/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.num.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 狂犬疫苗针次Entity
 * 
 * @author zhouqj
 * @version 2017-06-06
 */
public class VacRemind extends DataEntity<VacRemind> {

	private static final long serialVersionUID = 1L;

	private String signature;    //签字内容
	private String stype;	//签字来源  2微信
	private String vid;	//记录id
	private String sid;	//签字id
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getStype() {
		return stype;
	}
	
	public void setStype(String stype) {
		this.stype = stype;
	}
	
	public String getVid() {
		return vid;
	}
	
	public void setVid(String vid) {
		this.vid = vid;
	}
	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String sid) {
		this.sid = sid;
	}
	
}