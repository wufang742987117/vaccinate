package com.thinkgem.jeesite.modules.export.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 计划免疫疫苗使用情况报表Entity
 * @author Jack
 * @date 2018年3月2日 下午6:56:17
 * @description 
 */
public class WithinPlanVaccUse extends DataEntity<WithinPlanVaccUse>{

	private static final long serialVersionUID = 1L;
	
	private String yearMonth; 	//生成数据年月
	private String vaccName; 	//疫苗名称
	private String specification; 	//规格
	private String lastMonthNum; 	//上月末库存数量
	private int gainNum; 	//当月领苗数量
	private int usedNum; 	//使用数
	private int discardNum;		//报废数
	private int thisMonthEndNum; 	//本月底库存数
	private int nextMonthNeedNum;	//下月需求数量
	
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getVaccName() {
		return vaccName;
	}
	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getLastMonthNum() {
		return lastMonthNum;
	}
	public void setLastMonthNum(String lastMonthNum) {
		this.lastMonthNum = lastMonthNum;
	}
	public int getGainNum() {
		return gainNum;
	}
	public void setGainNum(int gainNum) {
		this.gainNum = gainNum;
	}
	public int getUsedNum() {
		return usedNum;
	}
	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}
	public int getDiscardNum() {
		return discardNum;
	}
	public void setDiscardNum(int discardNum) {
		this.discardNum = discardNum;
	}
	public int getThisMonthEndNum() {
		return thisMonthEndNum;
	}
	public void setThisMonthEndNum(int thisMonthEndNum) {
		this.thisMonthEndNum = thisMonthEndNum;
	}
	public int getNextMonthNeedNum() {
		return nextMonthNeedNum;
	}
	public void setNextMonthNeedNum(int nextMonthNeedNum) {
		this.nextMonthNeedNum = nextMonthNeedNum;
	}

}
