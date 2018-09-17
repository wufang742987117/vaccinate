/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manage_stock_in.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 疫苗入库记录Entity
 * 
 * @author 王德地
 * @version 2017-02-08
 */
public class VaccInfo extends DataEntity<VaccInfo> {
	
	/** 默认配置 */
	public static String DEFAULTAEFOR = "0";
	/** 特殊配置-疫苗小类 */
	public static String SPECIALAEFOR1 = "1";
	/** 特殊配置-厂家 */
	public static String SPECIALAEFOR2 = "2";
	/** 特殊配置-针次 */
	public static String SPECIALAEFOR3 = "3";
	
	private static final long serialVersionUID = 1L;
	private String vaccName; // 疫苗类型
	private String vaccCode; // 疫苗编号
	private String status;	//状态 0 正常 1 疫苗小类 2 厂家 3 针次
	private String type;   //类型
	private String id;    //编号
	
	private String vid;    //编号
	private String lasting;    //时间类别
	private String interval;    //间隔
	private String defaultNeedle;    //默认针数
	private String count;    //条件内容
	
	public VaccInfo() {
		super();
	}

	public VaccInfo(String id) {
		super(id);
	}

	public String getVaccName() {
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}

	public String getVaccCode() {
		return vaccCode;
	}

	public void setVaccCode(String vaccCode) {
		this.vaccCode = vaccCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getLasting() {
		return lasting;
	}

	public void setLasting(String lasting) {
		this.lasting = lasting;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getDefaultNeedle() {
		return defaultNeedle;
	}

	public void setDefaultNeedle(String defaultNeedle) {
		this.defaultNeedle = defaultNeedle;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
}