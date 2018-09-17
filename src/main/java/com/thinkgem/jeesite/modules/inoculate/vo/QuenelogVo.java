package com.thinkgem.jeesite.modules.inoculate.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class QuenelogVo implements Serializable {
	private static final long serialVersionUID = 1L;

	//疫苗消耗
	private String vaccName;
	private String vaccId;
	private int pinBase;
	private int pinPro;
	private int pinTmp;
	@SuppressWarnings("unused")
	private int pinCount;
	
	//各疫苗具体剂次
	private String pinnum;
	private int forFree;
	private int forPay;
	private BigDecimal priceCount;
	
	private int itemsize;
	

	public QuenelogVo() {
		priceCount = new BigDecimal(0);
	}

	public QuenelogVo(String vaccName, int pinBase, int pinPro, int pinTmp, int pinCount) {
		super();
		this.vaccName = vaccName;
		this.pinBase = pinBase;
		this.pinPro = pinPro;
		this.pinTmp = pinTmp;
		this.pinCount = pinCount;
	}

	public String getVaccName() {
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}

	public int getPinBase() {
		return pinBase;
	}

	public void setPinBase(int pinBase) {
		this.pinBase = pinBase;
	}

	public int getPinPro() {
		return pinPro;
	}

	public void setPinPro(int pinPro) {
		this.pinPro = pinPro;
	}

	public int getPinTmp() {
		return pinTmp;
	}

	public void setPinTmp(int pinTmp) {
		this.pinTmp = pinTmp;
	}

	public int getPinCount() {
		return pinBase + pinPro + pinTmp + forFree + forPay;
	}

	public void setPinCount(int pinCount) {
		this.pinCount = pinCount;
	}

	public String getPinnum() {
		return pinnum;
	}

	public void setPinnum(String pinnum) {
		this.pinnum = pinnum;
	}

	public int getForFree() {
		return forFree;
	}

	public void setForFree(int forFree) {
		this.forFree = forFree;
	}

	public int getForPay() {
		return forPay;
	}

	public void setForPay(int forPay) {
		this.forPay = forPay;
	}

	public BigDecimal getPriceCount() {
		return priceCount;
	}
	
	public double getPriceCountDouble() {
		if(priceCount != null){
			return priceCount.doubleValue();
		}
		return 0d;
	}

	public void setPriceCount(BigDecimal priceCount) {
		this.priceCount = priceCount;
	}

	public int getItemsize() {
		return itemsize;
	}

	public void setItemsize(int itemsize) {
		this.itemsize = itemsize;
	}

	public String getVaccId() {
		return vaccId;
	}

	public void setVaccId(String vaccId) {
		this.vaccId = vaccId;
	}
	
	
	
	

}
