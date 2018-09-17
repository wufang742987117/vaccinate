package com.thinkgem.jeesite.modules.rabiesvaccine.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class BsRabiesCheckinDeal extends DataEntity<BsRabiesCheckinDeal> {
	/** 付款状态-未付 */
	public final static String PAYSTATUS_NORMAL = "0";
	/** 付款状态-付款 */
	public final static String PAYSTATUS_PAYMAL = "1";
	
	private static final long serialVersionUID = 1L;
	private String checkinId;//犬伤ID
	private Date createDate;//创建时间（处理时间）
	private String paystatus; // 状态：0 未付款、1 已付款
	private String createName;//创建者名
	private String status; // 状态：0 未完成、1 已完成
	private String remarks;//备注
	private String refund;//退款标识
	private Double price;//伤口处理价格
	
	public BsRabiesCheckinDeal(){
		super();
	}
	public BsRabiesCheckinDeal(String id){
		super(id);
	}
	
	@Length(min = 0, max = 32, message = "狂犬疫苗ID长度必须介于 0 和 32 之间")
	public String getCheckinId() {
		return checkinId;
	}
	public void setCheckinId(String checkinId) {
		this.checkinId = checkinId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	@ExcelField(title = "处理医生", align = 1, sort = 80)
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRefund() {
		return refund;
	}
	public void setRefund(String refund) {
		this.refund = refund;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
