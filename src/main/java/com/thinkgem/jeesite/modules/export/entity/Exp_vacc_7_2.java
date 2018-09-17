/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:4.1.2
 */

package com.thinkgem.jeesite.modules.export.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;

/**
 * 二类进销存(EXP_VACC_2)
 * 
 * @author bianj
 * @version 1.0.0 2018-01-26
 */
public class Exp_vacc_7_2 extends DataEntity<Exp_vacc_7_2> {
    /** 版本号 */
    private static final long serialVersionUID = 7846808381036531480L;
    
    private BsManageProduct product; // 疫苗产品id
    
    /** 疫苗编码 */
    private String vaccineid;
    
    /** 疫苗批号 */
    private String batchno;
    
    /** 疫苗厂家 */
    private String manufacturercode;
    
    /** 上月预售数 */
	private int lastwcount;
    
    /** 上月结余数 */
    private int lastjcount;
    
    /** 上月库存金额 */
    private double lastmoney;
    
    /** 本月领苗数 */
    private int income;
    
    /** 本月领苗金额 */
    private double incomemoney;
    
    /** 本月售苗数 */
    private int sellcount;
    
    /** 售苗成本 */
    private double sellcost;
    
    /** 售苗收入 */
    private double sellincome;
    
    /** 接种书 */
    private int vacccount;
    
    /** 接种成本 */
    private double vacccost;
    
    /** 接种收入 */
    private double vaccincome;
    
    /** 优惠数 */
    private int discount;
    
    /** 报废数 */
    private int baofei;
    
    /** 本月预售 */
    private int nwcount;
    
    /** 本月结余 */
    private int njcount;
    
    private int nalcount;
    
    /** 库存金额 */
    private double nmoney;
    
    /** 成本单价 */
    private double costprice;
    
    /** 销售单价 */
    private double sellprice;
    
    /** 报表月份 */
    private String yearmonth;
    
    private Date begdate;
    
    private Date enddate;
    
    private String vaccname;
    
    private String manufacturer;

	public BsManageProduct getProduct() {
//		if(product == null){
//			return new BsManageProduct();
//		}
		return product;
	}

	public void setProduct(BsManageProduct product) {
		this.product = product;
	}

	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getManufacturercode() {
		return manufacturercode;
	}

	public void setManufacturercode(String manufacturercode) {
		this.manufacturercode = manufacturercode;
	}

	public int getLastwcount() {
		return lastwcount;
	}

	public void setLastwcount(int lastwcount) {
		this.lastwcount = lastwcount;
	}

	public int getLastjcount() {
		return lastjcount;
	}

	public void setLastjcount(int lastjcount) {
		this.lastjcount = lastjcount;
	}

	public double getLastmoney() {
		return lastmoney;
	}

	public void setLastmoney(double lastmoney) {
		this.lastmoney = lastmoney;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public double getIncomemoney() {
		return incomemoney;
	}

	public void setIncomemoney(double incomemoney) {
		this.incomemoney = incomemoney;
	}

	public int getSellcount() {
		return sellcount;
	}

	public void setSellcount(int sellcount) {
		this.sellcount = sellcount;
	}

	public double getSellcost() {
		return sellcost;
	}

	public void setSellcost(double sellcost) {
		this.sellcost = sellcost;
	}

	public double getSellincome() {
		return sellincome;
	}

	public void setSellincome(double sellincome) {
		this.sellincome = sellincome;
	}

	public int getVacccount() {
		return vacccount;
	}

	public void setVacccount(int vacccount) {
		this.vacccount = vacccount;
	}

	public double getVacccost() {
		return vacccost;
	}

	public void setVacccost(double vacccost) {
		this.vacccost = vacccost;
	}

	public double getVaccincome() {
		return vaccincome;
	}

	public void setVaccincome(double vaccincome) {
		this.vaccincome = vaccincome;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getBaofei() {
		return baofei;
	}

	public void setBaofei(int baofei) {
		this.baofei = baofei;
	}

	public int getNwcount() {
		return nwcount;
	}

	public void setNwcount(int nwcount) {
		this.nwcount = nwcount;
	}

	public int getNjcount() {
		return njcount;
	}

	public void setNjcount(int njcount) {
		this.njcount = njcount;
	}

	public int getNalcount() {
		return nalcount;
	}

	public void setNalcount(int nalcount) {
		this.nalcount = nalcount;
	}

	public double getNmoney() {
		return nmoney;
	}

	public void setNmoney(double nmoney) {
		this.nmoney = nmoney;
	}

	public double getCostprice() {
		return costprice;
	}

	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}

	public double getSellprice() {
		return sellprice;
	}

	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}

	public String getYearmonth() {
		return yearmonth;
	}

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}

	public Date getBegdate() {
		return begdate;
	}

	public void setBegdate(Date begdate) {
		this.begdate = begdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getVaccname() {
		return vaccname;
	}

	public void setVaccname(String vaccname) {
		this.vaccname = vaccname;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	
	
    
}