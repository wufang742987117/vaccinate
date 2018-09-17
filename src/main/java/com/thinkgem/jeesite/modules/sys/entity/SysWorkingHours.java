/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 工作时间段Entity
 * @author liyuan
 * @version 2018-01-26
 */
public class SysWorkingHours extends DataEntity<SysWorkingHours> {
	
	private static final long serialVersionUID = 1L;
	private String week;		// 星期
	private String timeSlice;		// 时间段
	private int maximum;		// 该时段最大接种人数
	private String dateDay;		// 星期(从1到7)
	
	private int num;	//已预约人数
	private float percent;	//比例
	
	public SysWorkingHours() {
		super();
	}

	public SysWorkingHours(String id){
		super(id);
	}

	
	@Length(min=0, max=50, message="星期长度必须介于 0 和 50 之间")
	@NotNull(message="星期不能为空")
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
	@Length(min=0, max=20, message="时间段长度必须介于 0 和 20 之间")
	@NotNull(message="时段不能为空")
	public String getTimeSlice() {
		return timeSlice;
	}

	public void setTimeSlice(String timeSlice) {
		this.timeSlice = timeSlice;
	}
	
	@NotNull
	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum; 
	}
	
	@Length(min=0, max=1, message="星期(从1到7)长度必须介于 0 和 1 之间")
	@NotNull(message="星期不能为空")
	public String getDateDay() {
		return dateDay;
	}

	public void setDateDay(String dateDay) {
		this.dateDay = dateDay;
	}

	public int getNum() {
		return num;
	}

	public float getPercent() {
		return percent;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}
	
	/**
	 * 更新星期
	 * @author fuxin
	 * @date 2018年1月29日 上午11:03:52
	 * @description 
	 *		TODO
	 *
	 */
	public void updateDayAndWeek(){
		if(StringUtils.isBlank(dateDay) && StringUtils.isBlank(week)){
			return;
		}
		if(StringUtils.isNotBlank(dateDay) && StringUtils.isBlank(week)){
			week = DateUtils.day2Week(dateDay);
			return;
		}
		if(StringUtils.isBlank(dateDay) && StringUtils.isNotBlank(week)){
			dateDay = DateUtils.week2Day(week);
			return;
		}
	}
	
}