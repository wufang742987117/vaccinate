/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.num.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckin;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 狂犬疫苗针次Entity
 * 
 * @author wangdedi
 * @version 2017-03-06
 */
public class BsRabiesNum extends DataEntity<BsRabiesNum> {

	/** 默认剂量 */
	public static String DOSE_BEFOR = "0.5ml";
	/** 48小时后剂量 */
	public static String DOSE_ARROR = "1ml";
	/** 默认剂量2 */
	public static String JUDGMENT_ARROR = "10ml";
	/** 库存-1的状态-是 */
	public static String NSTATUS_ARROR = "1";
	/** 库存-1的状态-否 */
	public static String NSTATUS_BEFOR = "0";
	/** 是否异地完成的状态-是 */
	public static String WSTATUS_ARROR = "1";
	/** 是否异地完成的状态-否 */
	public static String WSTATUS_BEFOR = "0";
	/** 签字-有 */
	public final static String SIGNATURE_YES = "1";
	/** 签字-无 */
	public final static String SIGNATURE_NO = "0";
	/** 付款状态-未付 */
	public final static String STATUS_NORMAL = "0";
	/** 付款状态-付款 */
	public final static String STATUS_PAYMAL = "1";
	/** 完成状态-未完成 */
	public final static String STATUS_NOFINSH = "0";
	/** 完成状态-完成 */
	public final static String STATUS_FINSH = "1";
	/** 调价-有 */
	public final static String FUNDSTATUS_YES = "1";
	/** 调价-无 */
	public final static String FUNDSTATUS_NO = "0";
	
	private static final long serialVersionUID = 1L;
	private String checkid; // 狂犬疫苗ID
	private Integer vaccinum; // 针次1,2,3,4,5,
	private Date vaccidate; // 程序接种时间
	private Date realdate; // 实际接种时间
	private String dose; // 实际接种时间
	private String paystatus; // 状态：0 未付款、1 已付款
	private String status; // 状态：0 未完成、1 已完成
	private String vaccineid; // 疫苗编号
	private String batchnum; // 疫苗批号
	private String manufacturer; // 生产厂家
	private String nstatus; // 是否库存-1的状态 0，否 1，是
	private String createByName; // 姓名
	private String createById; // 姓名编号
	private BsRabiesCheckin kin; // 接种者档案
	private String dataStatus; // 时间状态
	private String wstatus; // 是否异地完成状态
	private String vaccicount; // 数量
	private byte[] signatureData; // 签字
	private String signature;//签字状态
	private String signatureList;//签字
	private String sId;//签字id
	
	private String sessionId;
	private String pid;
	private String stype;
	
	private String officeCode;	//所属科室
	
	private double originalPrice;		// 原价
	private double currentPrice;		// 现价
	private String fundStatus;		// 调价标识
	
	/** 查询条件-开始时间 */
	private Date beginTime;
	/** 查询条件-结束时间 */
	private Date endTime;

	public BsRabiesNum() {
		super();
	}

	public BsRabiesNum(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "狂犬疫苗ID长度必须介于 0 和 32 之间")
	public String getCheckid() {
		return checkid;
	}

	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}

	@ExcelField(title = "针次", align = 1, sort = 40, dictType = "pin")
	public Integer getVaccinum() {
		return vaccinum;
	}

	public void setVaccinum(Integer vaccinum) {
		this.vaccinum = vaccinum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@ExcelField(title = "程序接种时间", align = 1, sort = 90)
	public Date getVaccidate() {
		return vaccidate;
	}

	public void setVaccidate(Date vaccidate) {
		this.vaccidate = vaccidate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@ExcelField(title = "实际接种时间", align = 1, sort = 100)
	public Date getRealdate() {
		return realdate;
	}

	public void setRealdate(Date realdate) {
		this.realdate = realdate;
	}

	public BsRabiesCheckin getKin() {
		return kin;
	}

	public String getKinId() {
		return kin.getId();
	}

	@ExcelField(title = "姓名", align = 1, sort = 10)
	public String getKinUserName() {
		return kin.getUsername();
	}

//	@ExcelField(title = "身份证号", align = 1, sort = 20)
	public String getKinCard() {
		return kin.getCard();
	}

	@ExcelField(title = "性别", align = 1, sort = 20, dictType = "sex")
	public String getKinSex() {
		return kin.getSex();
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@ExcelField(title = "出生日期", align = 1, sort = 40)
	public Date getKinBirthday() {
		return kin.getBirthday();
	}

//	@ExcelField(title = "年龄", align = 1, sort = 50)
	public Integer getKinAge() {
		return kin.getAge();
	}

//	@ExcelField(title = "暴露级别", align = 1, sort = 60, dictType = "rank")
	public String getKinExposelevel() {
		return kin.getExposelevel();
	}
	
	@ExcelField(title = "编号", align = 1, sort = 1)
	public String getRabiescode() {
		return kin.getRabiescode();
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setKin(BsRabiesCheckin kin) {
		this.kin = kin;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}

	@ExcelField(title = "疫苗批次", align = 1, sort = 70)
	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}

	@ExcelField(title = "疫苗厂家", align = 1, sort = 60)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getNstatus() {
		return nstatus;
	}

	public void setNstatus(String nstatus) {
		this.nstatus = nstatus;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	// begin
	public String getBegin() {
		return DateUtils.formatDateTime(this.beginTime);
	}

	// end
	public String getEnd() {
		return DateUtils.formatDateTime(this.endTime);
	}

	@ExcelField(title = "接种医生", align = 1, sort = 80)
	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getWstatus() {
		return wstatus;
	}

	public void setWstatus(String wstatus) {
		this.wstatus = wstatus;
	}

	@ExcelField(title = "针数", align = 1, sort = 50)
	public String getVaccicount() {
		return vaccicount;
	}

	public void setVaccicount(String vaccicount) {
		this.vaccicount = vaccicount;
	}

	public byte[] getSignatureData() {
		return signatureData;
	}

	public void setSignatureData(byte[] signatureData) {
		this.signatureData = signatureData;
	}

	public String getSignature() {
		if(StringUtils.isBlank(signature)){
			signature="0";
		}
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignatureList() {
		return signatureList;
	}

	public void setSignatureList(String signatureList) {
		this.signatureList = signatureList;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getOfficeCode(){
		if(StringUtils.isNotBlank(officeCode)){
			return officeCode;
		}
		return UserUtils.getUser().getOffice().getCode();
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getFundStatus() {
		return fundStatus;
	}

	public void setFundStatus(String fundStatus) {
		this.fundStatus = fundStatus;
	}

}