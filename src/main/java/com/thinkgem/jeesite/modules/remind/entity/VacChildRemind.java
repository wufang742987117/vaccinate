/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.remind.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;

/**
 * 儿童接种提醒Entity
 * @author fuxin
 * @version 2017-12-07
 */
public class VacChildRemind extends DataEntity<VacChildRemind> {
	
	/** 状态-已完成 */
	public static final String STATUS_FINISH = "1";
	/** 状态-未完成 */
	public static final String STATUS_NORMAL = "0";
	/** 没有签字默认值 */
	public static final String NOT_SIGN = "0";
	/** 付款状态 - 未付款*/
	public static final String PAY_STATUS_NO = "0";
	/** 付款状态 - 已付款 */
	public static final String PAY_STATUS_YES = "1";
	
	private static final long serialVersionUID = 1L;
	private String childcode;		// 儿童编号
	private Date remindDate;		// 提醒日期
	private String remindVacc;		// 提醒的内容
	private String vaccId;		// 疫苗编号
	private String com;		// 厂商编号
	private String batch;		// 批号
	private String spec;		// 剂量
	private Date selectDate;		// 自选时段-日期
	private String selectTime;		// 自选时段-时间段
	private String status;		// 状态 0未完成  1已完成
	private String insuranceId;		// 保险id，没有保险null
	private String sign;		// 签字状态
	private Date beginRemindDate;		// 开始 提醒日期
	private Date endRemindDate;		// 结束 提醒日期
	private Date beginSelectDate;		// 开始 自选日期
	private Date endSelectDate;		// 结束 自选日期
	private String code;		// 预约码
	private String payStatus;
	private String payPrice;
	
	private String orderBy;
	
	private String signature;    //签字内容
	private String stype;	//签字来源  2微信
	private String vid;	//记录id
	private String sid;	//签字id
	private byte[] signatureByte;
	
	
	public VacChildRemind() {
		super();
	}

	public VacChildRemind(String id){
		super(id);
	}

	@Length(min=1, max=32, message="儿童编号长度必须介于 1 和 32 之间")
	public String getChildcode() {
		return childcode;
	}

	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="提醒日期不能为空")
	public Date getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}
	
	@Length(min=1, max=255, message="提醒的内容长度必须介于 1 和 255 之间")
	public String getRemindVacc() {
		return remindVacc;
	}

	public void setRemindVacc(String remindVacc) {
		this.remindVacc = remindVacc;
	}
	
	@Length(min=1, max=4, message="疫苗编号长度必须介于 1 和 4 之间")
	public String getVaccId() {
		return vaccId;
	}

	public void setVaccId(String vaccId) {
		this.vaccId = vaccId;
	}
	
	@Length(min=0, max=10, message="厂商编号长度必须介于 0 和 10 之间")
	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}
	
	@Length(min=0, max=20, message="批号长度必须介于 0 和 20 之间")
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	@Length(min=0, max=20, message="剂量长度必须介于 0 和 20 之间")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}
	
	@Length(min=0, max=4, message="自选时段-时间段长度必须介于 0 和 4 之间")
	public String getSelectTime() {
		return selectTime;
	}

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}
	
	@Length(min=1, max=1, message="状态 0未完成  1已完成长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="保险id，没有保险null长度必须介于 0 和 32 之间")
	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}
	
	public Date getBeginRemindDate() {
		return beginRemindDate;
	}

	/**
	 * 起始预约时间
	 * @author fuxin
	 * @date 2017年12月8日 下午2:51:13
	 * @description 
	 *		TODO
	 * @param beginRemindDate
	 *
	 */
	public void setBeginRemindDate(Date beginRemindDate) {
		this.beginRemindDate = beginRemindDate;
	}
	
	public Date getEndRemindDate() {
		return endRemindDate;
	}

	public void setEndRemindDate(Date endRemindDate) {
		this.endRemindDate = endRemindDate;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getBeginSelectDate() {
		return beginSelectDate;
	}

	public Date getEndSelectDate() {
		return endSelectDate;
	}

	public void setBeginSelectDate(Date beginSelectDate) {
		this.beginSelectDate = beginSelectDate;
	}

	public void setEndSelectDate(Date endSelectDate) {
		this.endSelectDate = endSelectDate;
	}
	
	//===============================
	public String getSignature() {
		return signature;
	}

	public String getStype() {
		return stype;
	}

	public String getVid() {
		return vid;
	}

	public String getSid() {
		return sid;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	
	
	//===============================


	public String getPayStatus() {
		return payStatus;
	}

	public String getPayPrice() {
		return payPrice;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}

	/**
	 * 设置产品信息
	 * @author fuxin
	 * @date 2017年12月8日 上午10:18:40
	 * @description 
	 *		TODO
	 * @param pro
	 *
	 */
	public void setProduct(BsManageProduct pro) {
		if(pro != null){
			this.vaccId = pro.getVaccineid();
			this.batch = pro.getBatchno();
			this.com = pro.getCode();
			this.spec = pro.getSpecification();
			this.remindVacc = pro.getName();
		}
	}

	public byte[] getSignatureByte() {
		return signatureByte;
	}

	public void setSignatureByte(byte[] signatureByte) {
		this.signatureByte = signatureByte;
	}


	
	
	
		
}