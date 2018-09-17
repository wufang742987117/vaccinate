/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inoculate.vo;

import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 排队叫号管理Entity
 * @author fuxin
 * @version 2017-02-14
 */
public class QueneVo {
	
	private String queueno;		// 排队号码
	private String childid;		// 儿童编号
	private String childname;		// 儿童编号
	private String vaccineid;		// 疫苗编号
	private String roomcode;		// 科室编号
	private String doctor;		// 接种医生姓名
	private String ispass;		// 是否过号 Y已过号 N未过号，排队中
	private String bodypart;	//接种部位
	private String batch;		//疫苗批次
	public String getQueueno() {
		return queueno;
	}
	public void setQueueno(String queueno) {
		this.queueno = queueno;
	}
	public String getChildid() {
		return childid;
	}
	public void setChildid(String childid) {
		this.childid = childid;
	}
	public String getVaccineid() {
		return vaccineid;
	}
	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	public String getRoomcode() {
		return roomcode;
	}
	public void setRoomcode(String roomcode) {
		this.roomcode = roomcode;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getIspass() {
		return ispass;
	}
	public void setIspass(String ispass) {
		this.ispass = ispass;
	}
	public String getBodypart() {
		return bodypart;
	}
	public void setBodypart(String bodypart) {
		this.bodypart = bodypart;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getChildname() {
		if(!StringUtils.isNotBlank(childname)){
			return " ";
		}
		return childname;
	}
	public void setChildname(String childname) {
		this.childname = childname;
	}
	
}