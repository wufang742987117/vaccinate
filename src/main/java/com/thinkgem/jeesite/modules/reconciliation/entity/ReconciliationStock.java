package com.thinkgem.jeesite.modules.reconciliation.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 对帐管理
 * @author zhouqj
 * @date 2017年5月17日 上午11:17:01
 * @description 
 *		TODO
 */
public class ReconciliationStock extends DataEntity<ReconciliationStock> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gname;		// 大类名称
	private String gnum;		// 大类编号
	private String source;		// 来源
	private String price;		// 登记台金额
	private String price1;		// 微信金额
	private String price2;		// 一体机金额
	private String pricesum;	// 总金额
	private String timeYear;    //年
	private String timeMoon;   //月
	private String beginTime;
	private String endTime;
	private String countsum1;		// 疫苗接种完成统计
	private String countsum2;		// 疫苗报损统计
	private String countsum;	// 总统计
	
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getGnum() {
		return gnum;
	}
	public void setGnum(String gnum) {
		this.gnum = gnum;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPrice() {
		if(!StringUtils.isNotBlank(price)){
			return "0";
		}
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPrice1() {
		if(!StringUtils.isNotBlank(price1)){
			return "0";
		}
		return price1;
	}
	public void setPrice1(String price1) {
		this.price1 = price1;
	}
	public String getPrice2() {
		if(!StringUtils.isNotBlank(price2)){
			return "0";
		}
		return price2;
	}
	public void setPrice2(String price2) {
		this.price2 = price2;
	}
	public String getPricesum() {
		return pricesum;
	}
	public void setPricesum(String pricesum) {
		this.pricesum = pricesum;
	}
	public String getTimeYear() {
		return timeYear;
	}
	public void setTimeYear(String timeYear) {
		this.timeYear = timeYear;
	}
	public String getTimeMoon() {
		return timeMoon;
	}
	public void setTimeMoon(String timeMoon) {
		this.timeMoon = timeMoon;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCountsum1() {
		return countsum1;
	}
	public void setCountsum1(String countsum1) {
		this.countsum1 = countsum1;
	}
	public String getCountsum2() {
		return countsum2;
	}
	public void setCountsum2(String countsum2) {
		this.countsum2 = countsum2;
	}
	public String getCountsum() {
		return countsum;
	}
	public void setCountsum(String countsum) {
		this.countsum = countsum;
	}
	
	
}
