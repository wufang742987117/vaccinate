/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 每日盘库Entity
 * @author fuxin
 * @version 2017-12-28
 */
public class BsManageCheck extends DataEntity<BsManageCheck> {
	
	private static final long serialVersionUID = 1L;
	private BsManageProduct product; // 疫苗产品id
	private Date lastDate;		// 上期盘点时间
	private Long lastNum;		// 上期盘点库存
	private Long inNum;		// 本期入库
	private Long outNum;		// 本期出库
	private Long scrapNum;		// 本期出库
	private Long exchangeNum;	// 本期调剂
	private Long useNum;		// 本期使用
	private Long restNum;		// 本期剩余
	private String checkDate;		// 盘点日期-盘点所属日期
	private String checkType;		// 盘点类型-1当日 2-次日
	
	private boolean showAll;
	private boolean showNull;
	
	public BsManageCheck() {
		super();
	}

	public BsManageCheck(String id){
		super(id);
	}

	@NotNull(message="疫苗产品id不能为空")
	public BsManageProduct getProduct() {
//		if(product == null){
//			return new BsManageProduct();
//		}
		return product;
	}

	public void setProduct(BsManageProduct product) {
		this.product = product;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="上期盘点时间不能为空")
	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
	@NotNull(message="上期盘点库存不能为空")
	public Long getLastNum() {
		return lastNum;
	}

	public void setLastNum(Long lastNum) {
		this.lastNum = lastNum;
	}
	
	public Long getInNum() {
		return inNum;
	}

	public void setInNum(Long inNum) {
		this.inNum = inNum;
	}
	
	public Long getOutNum() {
		return outNum;
	}

	public void setOutNum(Long outNum) {
		this.outNum = outNum;
	}
	
	@NotNull(message="本期使用不能为空")
	public Long getUseNum() {
		return useNum;
	}

	public void setUseNum(Long useNum) {
		this.useNum = useNum;
	}
	
	@NotNull(message="本期剩余不能为空")
	public Long getRestNum() {
		return restNum;
	}

	public void setRestNum(Long restNum) {
		this.restNum = restNum;
	}
	
	@Length(min=0, max=1, message="盘点类型-1当日 2-次日长度必须介于 0 和 1 之间")
	public String getCheckType() {
		return checkType;
	}

	@NotNull(message="盘点日期-盘点所属日期不能为空")
	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public boolean isShowNull() {
		return showNull;
	}

	public void setShowNull(boolean showNull) {
		this.showNull = showNull;
	}

	public Long getScrapNum() {
		return scrapNum;
	}

	public void setScrapNum(Long scrapNum) {
		this.scrapNum = scrapNum;
	}

	public Long getExchangeNum() {
		return exchangeNum;
	}

	public void setExchangeNum(Long exchangeNum) {
		this.exchangeNum = exchangeNum;
	}
	
	

}