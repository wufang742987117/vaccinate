package com.thinkgem.jeesite.modules.sys.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OfficePreference implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 开关-开启状态 */
	public final static String OPTION_ON = "OPEN";
	/** 开关-关闭状态 */
	public final static String OPTION_OFF = "CLOSE";

	/** 归属机构id */
	private String id;
	/** 线下支付模式 */
	private String payOption = OPTION_OFF;
	/** 一体机签字模式 */
	private String reserveOption = OPTION_OFF;
	/** 排号使用模式 */
	private String quickOption = OPTION_ON;
	/** 小票打印模式 */
	private String receiptOption = OPTION_ON;
	/** 普通疫苗时间间隔 */
	private int vacDelay = 14;
	/** 疫苗库存预留 */
	private int obligate = 0;
	/** 叫号准备 */
	private String callReadyOption = OPTION_OFF;
	

	/** 线下支付模式 */
	public String getPayOption() {
		return payOption;
	}

	/** 一体机签字模式 */
	public String getReserveOption() {
		return reserveOption;
	}

	/** 排号使用模式 */
	public String getQuickOption() {
		return quickOption;
	}
	
	/** 小票打印模式 */
	public String getReceiptOption() {
		return receiptOption;
	}
	
	/** 小票打印模式 */
	@JsonIgnore 
	public boolean isReceipt() {
		return OPTION_ON.equals(receiptOption);
	}
	
	/** 排号使用模式 */
	@JsonIgnore 
	public boolean isQuick() {
		return OPTION_ON.equals(quickOption);
	}

	/** 线下支付模式 */
	@JsonIgnore 
	public boolean isPay() {
		return OPTION_ON.equals(payOption);
	}

	/** 一体机签字模式 */
	@JsonIgnore 
	public boolean isReserve() {
		return OPTION_ON.equals(reserveOption);
	}
	
	/** 叫号准备 */
	@JsonIgnore 
	public boolean isCallReady() {
		return OPTION_ON.equals(callReadyOption);
	}
	
	/** 小票打印模式 
	 * 返回变更后状态
	 */
	public boolean toggleReceiptOption() {
		receiptOption = isReceipt()?OPTION_OFF:OPTION_ON;
		return isReceipt();
	}
	
	/** 排号使用模式 
	 * 返回变更后状态
	 */
	public boolean toggleQuickOption() {
		quickOption = isQuick()?OPTION_OFF:OPTION_ON;
		return isQuick();
	}

	/** 线下支付模式
	 * 返回变更后状态
	 */
	public boolean togglePayOption() {
		payOption = isPay()?OPTION_OFF:OPTION_ON;
		return isPay();
	}

	/** 一体机签字模式
	 * 返回变更后状态
	 */
	public boolean toggleReserveOption() {
		reserveOption = isReserve()?OPTION_OFF:OPTION_ON;
		return isReserve();
	}
	
	public boolean togglecallReadyOption() {
		callReadyOption = isCallReady()?OPTION_OFF:OPTION_ON;
		return isCallReady();
	}

	/** 设置-线下支付模式 */
	public void setPayOption(String payOption) {
		this.payOption = payOption;
	}

	/** 设置-一体机签字模式 */
	public void setReserveOption(String reserveOption) {
		this.reserveOption = reserveOption;
	}

	/** 设置-排号使用模式 */
	public void setQuickOption(String quickOption) {
		this.quickOption = quickOption;
	}
	
	/** 设置-小票打印模式 */
	public void setReceiptOption(String receiptOption) {
		this.receiptOption = receiptOption;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVacDelay() {
		return vacDelay;
	}

	public void setVacDelay(int vacDelay) {
		this.vacDelay = vacDelay;
	}

	public int getObligate() {
		return obligate;
	}

	public void setObligate(int obligate) {
		this.obligate = obligate;
	}

	public String getCallReadyOption() {
		return callReadyOption;
	}

	public void setCallReadyOption(String callReadyOption) {
		this.callReadyOption = callReadyOption;
	}
	

}
