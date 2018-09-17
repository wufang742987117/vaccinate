/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.refund.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 退款记录Entity
 * @author wangdedi
 * @version 2017-05-17
 */
public class SysRefund extends DataEntity<SysRefund> {
	
	private static final long serialVersionUID = 1L;
	private String childcode;		// 儿童编号
	private String nid;		// 计划表ID
	private String uid;		// 用户ID
	private String childname;		// 儿童姓名

	private String refundsource;		// 退款来源0登记台1微信2一体机
	private Double refundmoney;		// 退款金额
	private String pid;		// pid
	private String orderNo;		// 退款订单号
	private String insuranceorderno;		// 保险退款单号
	
	public SysRefund() {
		super();
	}

	public SysRefund(String id){
		super(id);
	}

	@Length(min=1, max=20, message="儿童编号长度必须介于 1 和 20 之间")
	public String getChildcode() {
		return childcode;
	}

	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}
	
	@Length(min=1, max=10, message="计划表ID长度必须介于 1 和 10 之间")
	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}
	
	@Length(min=1, max=32, message="用户ID长度必须介于 1 和 32 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=1, max=20, message="儿童姓名长度必须介于 1 和 20 之间")
	public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}
	@Length(min=1, max=2, message="退款来源0登记台1微信2一体机长度必须介于 1 和 2 之间")
	public String getRefundsource() {
		return refundsource;
	}

	public void setRefundsource(String refundsource) {
		this.refundsource = refundsource;
	}
	
	@NotNull(message="退款金额不能为空")
	public Double getRefundmoney() {
		return refundmoney;
	}

	public void setRefundmoney(Double refundmoney) {
		this.refundmoney = refundmoney;
	}
	
	@Length(min=1, max=32, message="pid长度必须介于 1 和 32 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getInsuranceorderno() {
		return insuranceorderno;
	}

	public void setInsuranceorderno(String insuranceorderno) {
		this.insuranceorderno = insuranceorderno;
	}

	
	
}