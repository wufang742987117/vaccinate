/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inoculate.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;

/**
 * 排队叫号管理Entity
 * @author fuxin
 * @version 2017-02-14
 */
public class Quene extends DataEntity<Quene> {
	
	/** 叫号队列过号状态-过号 */
	public final static String ISPASS_Y = "Y";
	/** 叫号队列过号状态-未过号 */
	public final static String ISPASS_N = "N";
	
	/** 叫号队列数据状态-正常 */
	public final static String STATUS_NORMAL = "0";
	/** 叫号队列数据状态-删除 */
	public final static String STATUS_DEL = "1";
	/** 叫号队列数据状态-完成 */
	public final static String STATUS_FINSH = "2";
	/** 叫号队列数据状态-等待缴费 */
	public final static String STATUS_WAITPAY = "3";
	/** 调价-有 */
	public final static String FUNDSTATUS_YES = "1";
	/** 调价-无 */
	public final static String FUNDSTATUS_NO = "0";
	
	private static final long serialVersionUID = 1L;
	private String queueno;		// 排队号码
	private String childid;		// 儿童编号
	private String vaccineid;		// 疫苗编号  //疫苗计划id
	private String roomcode;		// 科室编号
	private String doctor;		// 接种医生姓名
	private String ispass;		// 是否过号 Y已过号 N未过号，排队中
	private String bodypart;	//接种部位
	private String batch;		//疫苗批次
	private String office;
	private double price; //接种单位
	
	private ChildBaseinfo child; // 儿童姓名
	private BsManageVaccine vaccine; // 疫苗名称
	
	private String status = "0"; //队列状态 0正常状态 1删除 2完成 
	
	private String pid; //productid
	
	private String orderBy = "a.queueno";
	
	private String position;  //接种部位
	
	private String name;//疫苗名称，统计用
	private String total;//已完成人数，统计用
	
	private String statusIn;
	
	private double originalPrice;		// 原价
	private double currentPrice;		// 现价
	private String fundStatus;		// 调价标识
	
	public Quene() {
		super();
	}

	public Quene(String id, String queueno){
		this.id = id;
		this.queueno = queueno;
	}
	
	@Length(min=1, max=20, message="排队号码长度必须介于 1 和 20 之间")
	public String getQueueno() {
		return queueno;
	}

	public void setQueueno(String queueno) {
		this.queueno = queueno;
	}
	
	@NotNull(message="儿童编号不能为空")
	public String getChildid() {
		return childid;
	}

	public void setChildid(String childid) {
		this.childid = childid;
	}
	
	@NotNull(message="疫苗编号不能为空")
	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	
	@Length(min=1, max=5, message="科室编号长度必须介于 1 和 5 之间")
	public String getRoomcode() {
		if(null != roomcode){
			return roomcode.trim();
		}
		return roomcode;
	}

	public void setRoomcode(String roomcode) {
		this.roomcode = roomcode;
	}
	
	@Length(min=0, max=20, message="接种医生姓名长度必须介于 0 和 20 之间")
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	
	@Length(min=0, max=1, message="是否过号 Y已过号 N未过号，排队中长度必须介于 0 和 1 之间")
	public String getIspass() {
		return ispass;
	}

	public void setIspass(String ispass) {
		this.ispass = ispass;
	}

	public ChildBaseinfo getChild() {
		return child;
	}

	public void setChild(ChildBaseinfo child) {
		this.child = child;
	}

	public BsManageVaccine getVaccine() {
		return vaccine;
	}

	public void setVaccine(BsManageVaccine vaccine) {
		this.vaccine = vaccine;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getPin(){
		if(StringUtils.isNotBlank(vaccineid) && vaccineid.length() >= 3){
			return vaccineid.substring(2);
		}
		return "";
	}

	public String getStatusIn() {
		return statusIn;
	}

	public void setStatusIn(String statusIn) {
		this.statusIn = statusIn;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getFundStatus() {
		return fundStatus;
	}

	public void setFundStatus(String fundStatus) {
		this.fundStatus = fundStatus;
	}
	

}