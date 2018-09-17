package com.thinkgem.jeesite.modules.child_vaccinaterecord.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;

public class sel extends DataEntity<ChildBaseinfo>{
	private static final long serialVersionUID = 1L;
	private String begin; 
	private String end; 
	private int distanceDay; 
	private String name;
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public int getDistanceDay() {
		return distanceDay;
	}
	public void setDistanceDay(int distanceDay) {
		this.distanceDay = distanceDay;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	
}
