/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitisnum.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckin;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 乙肝疫苗针次信息Entity
 * 
 * @author xuejinshan
 * @version 2017-07-26
 */
public class BsHepatitisBNum extends DataEntity<BsHepatitisBNum> {
	
	/** 默认剂量 */
	public static String DOSE_BEFOR = "0.5ml";
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
	private String checkId; // 乙肝登录id
	private Integer vaccineNum; // 疫苗接种针次1,2,3,4,5,
	private Date vaccineDate; // 程序接种时间
	private Date realDate; // 实际接种时间
	private String dose; // 针次剂量
	private String payStatus; // 状态;0 未付款、1 已付款
	private String status; // 状态;0 未完成、1 已完成
	private String vaccineId; // 疫苗编号
	private String batch; // 批号
	private String standard; // 规格
	private String manufacturer; // 生产厂家
	private String createName; // 创建者
	private String signature; // 签字状态
	private String wStatus; // 是否异地完成的状态 0，否 1，是 wStatus
	private String createById; // 姓名编号
	private String nStatus; // 是否库存-1的状态 0，否 1，是
	private String dataStatus; // 时间状态
	private String vacciCount;//每针次 打几下
	private BsHepatitisBcheckin kin; // 接种者档案
	private byte[] signatureData;
	private String signatureList;//签字
	private String sId;//签字id
	private String deFlag;
	
	private String sessionId;
	private String pid;
	private String stype;
	private String vaccType; // 疫苗类型
	
	private String officeCode;	//所属科室
	
	private double originalPrice;		// 原价
	private double currentPrice;		// 现价
	private String fundStatus;		// 调价标识
	
	/** 查询开始时间 */
	private Date beginTime;
	/** 查询截止时间 */
	private Date endTime;

	public BsHepatitisBNum() {
		super();
	}

	public BsHepatitisBNum(String id) {
		super(id);
	}
	
	public BsHepatitisBcheckin getKin() {
		return kin;
	}

	public void setKin(BsHepatitisBcheckin kin) {
		this.kin = kin;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
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

	public byte[] getSignatureData() {
		return signatureData;
	}

	public void setSignatureData(byte[] signatureData) {
		this.signatureData = signatureData;
	}

	public String getnStatus() {
		return nStatus;
	}

	public void setnStatus(String nStatus) {
		this.nStatus = nStatus;
	}

	public String getwStatus() {
		return wStatus;
	}

	public void setwStatus(String wStatus) { // wStatus
		this.wStatus = wStatus;
	}

	@Length(min = 0, max = 32, message = "乙肝登录id长度必须介于 0 和 32 之间")
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	@ExcelField(title="针次", align = 1, sort = 40, dictType = "pin")
	public Integer getVaccineNum() {
		return vaccineNum;
	}

	public void setVaccineNum(Integer vaccineNum) {
		this.vaccineNum = vaccineNum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVaccineDate() {
		return vaccineDate;
	}

	public void setVaccineDate(Date vaccineDate) {
		this.vaccineDate = vaccineDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRealDate() {
		return realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	@Length(min = 0, max = 50, message = "针次剂量长度必须介于 0 和 50 之间")
	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	@Length(min = 0, max = 1, message = "状态;0 未付款、1 已付款、2 已完成长度必须介于 0 和 1 之间")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min = 0, max = 50, message = "疫苗编号长度必须介于 0 和 50 之间")
	public String getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(String vaccineId) {
		this.vaccineId = vaccineId;
	}

	@Length(min = 0, max = 50, message = "批号长度必须介于 0 和 50 之间")
	@ExcelField(title = "疫苗批次", align = 1, sort = 70)
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Length(min = 0, max = 200, message = "生产厂家长度必须介于 0 和 200 之间")
	@ExcelField(title = "疫苗厂家", align = 1, sort = 60)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Length(min = 0, max = 50, message = "创建者长度必须介于 0 和 50 之间")
	@ExcelField(title = "接种医生", align = 1, sort = 90)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Length(min = 1, max = 2, message = "签字状态长度必须介于 1 和 2 之间")
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

	@ExcelField(title="针数", align = 1, sort = 50)
	public String getVacciCount() {
		return vacciCount;
	}

	public void setVacciCount(String vacciCount) {
		this.vacciCount = vacciCount;
	}
	
	public String getKinId() {
		return kin.getId();
	}
	
	@ExcelField(title = "姓名", align = 1, sort = 10)
	public String getKinUserName() {
		return kin.getUsername();
	}
	
	@ExcelField(title = "性别", align = 1, sort = 20, dictType = "sex")
	public String getKinSex() {
		return kin.getSex();
	}
	
//	@ExcelField(title = "年龄", align = 1, sort = 30)
	public Integer getKinAge() {
		return kin.getAge();
	}
	
	@ExcelField(title = "编号", align = 1, sort = 1)
	public String getKinHepaBcode() {
		return kin.getHepaBcode();
	}
	
	public String getDeFlag() {
		return deFlag;
	}

	public void setDeFlag(String deFlag) {
		this.deFlag = deFlag;
	}

	@ExcelField(title = "疫苗规格", align = 1, sort = 80)
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
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

	@ExcelField(title = "疫苗类型", align = 1, sort = 51)
	public String getVaccType() {
		return vaccType;
	}

	public void setVaccType(String vaccType) {
		this.vaccType = vaccType;
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