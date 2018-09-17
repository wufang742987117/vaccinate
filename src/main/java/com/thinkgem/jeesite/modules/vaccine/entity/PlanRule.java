package com.thinkgem.jeesite.modules.vaccine.entity;

import java.io.Serializable;

public class PlanRule implements Serializable{
	private static final long serialVersionUID = 1L;

	private int type;	//类型
	private String description;

	//======rule1:特殊剂次间的时间间隔===========
	private Long targetPin;	//r1 r2
	private Long currentPin;  //r1
	private int offset;	//r1 r4
	private String group;	//r1 r2	r3 r4
	private String targetGroup;	//r1 r2	r3
	//==========================================
	//=====rule2:一种疫苗打完，另一种疫苗不显=====
	//==========================================
	//=====rule3:模型大类共享免疫程序============
	//==========================================
	//=====rule4:灵活间隔时间============
	private String interval;//r4 间隔时间单位 eg.year(一年接种一次)
	private int pinNum = 1;	//r4EX
	private int startAge;	//r4EX
	private int endAge;		//r4EX
	//==========================================
	
	
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTargetPin() {
		return targetPin;
	}

	public Long getCurrentPin() {
		return currentPin;
	}

	public void setTargetPin(Long targetPin) {
		this.targetPin = targetPin;
	}

	public void setCurrentPin(Long currentPin) {
		this.currentPin = currentPin;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	

	public String getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(String targetGroup) {
		this.targetGroup = targetGroup;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public int getPinNum() {
		return pinNum;
	}

	public int getStartAge() {
		return startAge;
	}

	public int getEndAge() {
		return endAge;
	}

	public void setPinNum(int pinNum) {
		this.pinNum = pinNum;
	}

	public void setStartAge(int startAge) {
		this.startAge = startAge;
	}

	public void setEndAge(int endAge) {
		this.endAge = endAge;
	}
	
	
	

}