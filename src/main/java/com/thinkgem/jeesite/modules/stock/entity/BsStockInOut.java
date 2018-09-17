/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stock.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 进销存月报表Entity
 * @author fuxin
 * @version 2017-12-17
 */
public class BsStockInOut extends DataEntity<BsStockInOut> {
	
	private static final long serialVersionUID = 1L;
	private Long year;		// 年
	private Long mouth;		// 月
	private String vaccineId;		// 疫苗id
	private String comId;		// 厂商id
	private String vaccineName;		// vaccine_name
	private String comName;		// com_name
	private Long stockOld;		// 上月库存
	private Long numIn;		// 入库
	private Long numOut;		// 出库
	private Long numReturn;		// 退货
	private Long numScrap;		// 报废
	private Long stockNow;		// 本月库存
	
	public BsStockInOut() {
		super();
	}

	public BsStockInOut(String id){
		super(id);
	}

	@NotNull(message="年不能为空")
	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}
	
	@NotNull(message="月不能为空")
	public Long getMouth() {
		return mouth;
	}

	public void setMouth(Long mouth) {
		this.mouth = mouth;
	}
	
	@Length(min=0, max=32, message="疫苗id长度必须介于 0 和 32 之间")
	public String getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(String vaccineId) {
		this.vaccineId = vaccineId;
	}
	
	@Length(min=0, max=10, message="厂商id长度必须介于 0 和 10 之间")
	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}
	
	@Length(min=0, max=100, message="vaccine_name长度必须介于 0 和 100 之间")
	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	
	@Length(min=0, max=50, message="com_name长度必须介于 0 和 50 之间")
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}
	
	public Long getStockOld() {
		return stockOld;
	}

	public void setStockOld(Long stockOld) {
		this.stockOld = stockOld;
	}
	
	public Long getNumIn() {
		return numIn;
	}

	public void setNumIn(Long numIn) {
		this.numIn = numIn;
	}
	
	public Long getNumOut() {
		return numOut;
	}

	public void setNumOut(Long numOut) {
		this.numOut = numOut;
	}
	
	public Long getNumReturn() {
		return numReturn;
	}

	public void setNumReturn(Long numReturn) {
		this.numReturn = numReturn;
	}
	
	public Long getNumScrap() {
		return numScrap;
	}

	public void setNumScrap(Long numScrap) {
		this.numScrap = numScrap;
	}
	
	public Long getStockNow() {
		return stockNow;
	}

	public void setStockNow(Long stockNow) {
		this.stockNow = stockNow;
	}
	
}