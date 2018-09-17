/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manage_stock_in.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;

/**
 * 疫苗入库记录Entity
 * 
 * @author 王德地
 * @version 2017-02-08
 */
public class ManageStockIn extends DataEntity<ManageStockIn> {

	private static final long serialVersionUID = 1L;

	/** 出入库类型-入库 */
	public final static String TYPE_IN = "0";
	/** 出入库类型-出库 */
	public final static String TYPE_OUT = "1";
	/** 出库说明 -报损*/
	public final static String STATE_BREAK = "1";
	/** 出库说明 -调剂*/
	public final static String STATE_EXCHANGE = "2";
	/** 出库说明 -其他*/
	public final static String STATE_OTHER = "3";
	

	private BsManageProduct product; // 入库疫苗编号
	private Long num; // 入库疫苗数量
	private Date indate; // 入库时间
	private String type; // 出入库类型
	private String state; // 出库说明
	private String orderNo;	//出入库单号
	private String indateStr;
	private String[] stateIn;

	/** 查询条件-开始时间 */
	private Date beginTime;
	/** 查询条件-结束时间 */
	private Date endTime;
	//调剂科室
	private String roomCode;

	public ManageStockIn() {
		super();
	}

	public ManageStockIn(String id) {
		super(id);
	}

	@NotNull
	public BsManageProduct getProduct() {
		return product;
	}

	public void setProduct(BsManageProduct product) {
		this.product = product;
	}

	@ExcelField(align = 2, title = "疫苗数量", sort = 40)
	@NotNull(message="出库数量不能为空")
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	@ExcelField(align = 2, title = "时间", sort = 50)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	// begin
	public String getBegin() {
		return DateUtils.formatDateTime(this.beginTime);

	}

	// end
	public String getEnd() {
		return DateUtils.formatDateTime(this.endTime);
	}

	@ExcelField(align = 2, title = "类型", sort = 10, dictType = "in_out")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ExcelField(align = 2, title = "说明", sort = 60, dictType = "state")
	@NotNull(message="出库类型不能为空")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getIndateStr() {
		if(indate != null){
			indateStr = DateUtils.formatDate(indate, "yyyyMMdd");
		}
		return indateStr;
	}

	public void setIndateStr(String indateStr) {
		this.indateStr = indateStr;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public void setStateIn(String[] stateIn) {
		this.stateIn = stateIn;
	}

	public String[] getStateIn() {
		return stateIn;
	}

}