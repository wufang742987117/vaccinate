package com.thinkgem.jeesite.modules.manage_stock_in.entity;

import java.util.Date;

public class Sele {
	private String storeid;	//疫苗信息ID
	private String proposer;//领用人
	private Date date;	//入库出库时间
	private Date d;	//入库出库时间
	private String num;	//入库出库数量
	private String mark;//备注
	private String vaccinatename;//疫苗名称
	private String batchno;//疫苗批次
	private String name;//领用人
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	public String getProposer() {
		return proposer;
	}
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getVaccinatename() {
		return vaccinatename;
	}
	public void setVaccinatename(String vaccinatename) {
		this.vaccinatename = vaccinatename;
	}
	public Date getD() {
		return d;
	}
	public void setD(Date d) {
		this.d = d;
	}
	
}
