/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rabiesvaccine.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 狂犬疫苗-接种者档案
 * 
 * @author wangdedi
 * @version 2017-02-24
 */
public class BsRabiesCheckin extends DataEntity<BsRabiesCheckin> {

	/** 暴露前免疫 */
	public static String EXPOSE_BEFOR = "0";
	/** 默认否 */
	public static String EBEFOR = "0";
	/** 暴露后免疫 */
	public static String EXPOSE_AFTER = "1";
	/** 默认咬伤部分其他 */
	public static String BITEPART_AFTER = "other";

	private static final long serialVersionUID = 1L;
	private String username; // 姓名
	private String sex; // 性别
	private String card; // 身份证号
	private Date birthday; // 出生日期
	private Integer age; // 年龄
	private String homeaddress; // 详细地址
	private String linkphone; // 联系电话
	private Date bitedate; // 咬伤时间
	private String bitepart; // 受伤部位
	private String bitetype; // 受伤方式
	private String animal; // 动物名称
	private Date dealdate; // 处理时间
	private String dealaddress; // 处理地点
	private String exposebefore; // 暴露前免疫
	private String exposeafter; // 暴露后免疫
	private String exposelevel; // 暴露级别1,2,3,4,5
	private String vaccinatename; // 疫苗名称
	private String standard; // 规格
	private String manufacturer; // 生产厂家（制造商）
	private String batchnum; // 批号
	private String isinoculate; // 是否接种狂犬病人免疫球蛋白：0，否 1，是
	private String weight; // 受种者体重
	private String dosage; // 接种剂次
	private String province; // 省
	private String city; // 市
	private String county; // 县
	private String address; // 详细地址
	private String expose; // 暴露前后免疫：0，暴露前 1，暴露后
	private String standardNo; // 规格 -- 免疫蛋白
	private String manufacturerNo; // 生产厂家（制造商） -- 免疫蛋白
	private String batchnumNo; // 批号 -- 免疫蛋白
	private String remarks; // 备注
	private String judgmentTimes; // 是否48小时：0，否 1，是
	private String history; // 既往病史
	private String payment; // 是否已付款：0 未付款、1 已付款
	private String vaccinatenameNo; // 疫苗名称
	private String openid; // 微信建档openid
	private String tempid; // 微信建档编号temid
	private int finishpin; // 已完成针数
	private int undonepin; // 所有的针数
	private String searchName; // 查询字段
	private String rabiescode; // 犬伤编码
	private String dosageNo; // 免疫蛋白接种剂次
	private String sId; // 签字
	private byte[] signatureData; // 签字
	private String search; // 查询标识
	private Date newdate; // 接种时间
	
	private String pid; // 非蛋白pid
	private String pidNo; // 蛋白pid
	private String stype;
	
	/** 查询条件-开始时间 */
	private Date beginTime;
	/** 查询条件-结束时间 */
	private Date endTime;

	public BsRabiesCheckin() {
		super();
	}

	public BsRabiesCheckin(String id) {
		super(id);
	}

	@Length(min = 0, max = 50, message = "姓名长度必须介于 0 和 50 之间")
	@ExcelField(title = "姓名", align = 1, sort = 10)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Length(min = 0, max = 10, message = "性别长度必须介于 0 和 10 之间")
	@ExcelField(title = "性别", align = 1, sort = 30, dictType = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "出生日期", align = 1, sort = 40)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@ExcelField(title = "年龄", align = 1, sort = 50)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Length(min = 0, max = 200, message = "详细地址长度必须介于 0 和 200 之间")
	public String getHomeaddress() {
		return homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}

	@Length(min = 0, max = 50, message = "联系电话长度必须介于 0 和 50 之间")
	@ExcelField(title = "联系电话", align = 1, sort = 60)
	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "咬伤时间", align = 1, sort = 70)
	public Date getBitedate() {
		return bitedate;
	}

	public void setBitedate(Date bitedate) {
		this.bitedate = bitedate;
	}

	@Length(min = 0, max = 50, message = "咬伤部位长度必须介于 0 和 50 之间")
	@ExcelField(title = "受伤部位", align = 1, sort = 90)
	public String getBitepart() {
		return bitepart;
	}

	public void setBitepart(String bitepart) {
		this.bitepart = bitepart;
	}

	@Length(min = 0, max = 50, message = "动物名称长度必须介于 0 和 50 之间")
	@ExcelField(title = "动物名称", align = 1, sort = 100, dictType = "animal")
	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDealdate() {
		return dealdate;
	}

	public void setDealdate(Date dealdate) {
		this.dealdate = dealdate;
	}

	@Length(min = 0, max = 200, message = "处理地点长度必须介于 0 和 200 之间")
	public String getDealaddress() {
		return dealaddress;
	}

	public void setDealaddress(String dealaddress) {
		this.dealaddress = dealaddress;
	}

	@Length(min = 0, max = 200, message = "暴露前免疫长度必须介于 0 和 200 之间")
	public String getExposebefore() {
		return exposebefore;
	}

	public void setExposebefore(String exposebefore) {
		this.exposebefore = exposebefore;
	}

	@Length(min = 0, max = 200, message = "暴露后免疫长度必须介于 0 和 200 之间")
	public String getExposeafter() {
		return exposeafter;
	}

	public void setExposeafter(String exposeafter) {
		this.exposeafter = exposeafter;
	}

	@Length(min = 0, max = 1, message = "暴露级别1,2,3,4,5长度必须介于 0 和 1 之间")
	public String getExposelevel() {
		return exposelevel;
	}

	public void setExposelevel(String exposelevel) {
		this.exposelevel = exposelevel;
	}

	@Length(min = 0, max = 50, message = "疫苗名称长度必须介于 0 和 50 之间")
	public String getVaccinatename() {
		return vaccinatename;
	}

	public void setVaccinatename(String vaccinatename) {
		this.vaccinatename = vaccinatename;
	}

	@Length(min = 0, max = 50, message = "规格长度必须介于 0 和 50 之间")
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Length(min = 0, max = 200, message = "生产厂家（制造商）长度必须介于 0 和 200 之间")
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Length(min = 0, max = 50, message = "批号长度必须介于 0 和 50 之间")
	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}

	@Length(min = 0, max = 1, message = "是否接种狂犬病人免疫球蛋白：0，否 1，是长度必须介于 0 和 1 之间")
	public String getIsinoculate() {
		return isinoculate;
	}

	public void setIsinoculate(String isinoculate) {
		this.isinoculate = isinoculate;
	}

	@Length(min = 0, max = 10, message = "受种者体重长度必须介于 0 和 10 之间")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Length(min = 0, max = 10, message = "接种剂次长度必须介于 0 和 10 之间")
	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	@ExcelField(title = "身份证号", align = 1, sort = 20)
	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExpose() {
		return expose;
	}

	public void setExpose(String expose) {
		this.expose = expose;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "建档日期", align = 1, sort = 110)
	public Date getCreateDate() {
		return createDate;
	}

	public String getStandardNo() {
		return standardNo;
	}

	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}

	public String getManufacturerNo() {
		return manufacturerNo;
	}

	public void setManufacturerNo(String manufacturerNo) {
		this.manufacturerNo = manufacturerNo;
	}

	public String getBatchnumNo() {
		return batchnumNo;
	}

	public void setBatchnumNo(String batchnumNo) {
		this.batchnumNo = batchnumNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getJudgmentTimes() {
		return judgmentTimes;
	}

	public void setJudgmentTimes(String judgmentTimes) {
		this.judgmentTimes = judgmentTimes;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getVaccinatenameNo() {
		return vaccinatenameNo;
	}

	public void setVaccinatenameNo(String vaccinatenameNo) {
		this.vaccinatenameNo = vaccinatenameNo;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getFinishpin() {
		return finishpin;
	}

	public void setFinishpin(int finishpin) {
		this.finishpin = finishpin;
	}

	public int getUndonepin() {
		return undonepin;
	}

	public void setUndonepin(int undonepin) {
		this.undonepin = undonepin;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getTempid() {
		return tempid;
	}

	public void setTempid(String tempid) {
		this.tempid = tempid;
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

	@ExcelField(title = "受伤方式", align = 1, sort = 80, dictType = "biteType")
	public String getBitetype() {
		return bitetype;
	}

	public void setBitetype(String bitetype) {
		this.bitetype = bitetype;
	}

	@ExcelField(title = "犬伤编号", align = 1, sort = 1)
	public String getRabiescode() {
		return rabiescode;
	}

	public void setRabiescode(String rabiescode) {
		this.rabiescode = rabiescode;
	}

	public String getDosageNo() {
		return dosageNo;
	}

	public void setDosageNo(String dosageNo) {
		this.dosageNo = dosageNo;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public byte[] getSignatureData() {
		return signatureData;
	}

	public void setSignatureData(byte[] signatureData) {
		this.signatureData = signatureData;
	}

	public String getSearch() {
		if(StringUtils.isBlank(search)){
			search = "0";
		}
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Date getNewdate() {
		return newdate;
	}

	public void setNewdate(Date newdate) {
		this.newdate = newdate;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPidNo() {
		return pidNo;
	}

	public void setPidNo(String pidNo) {
		this.pidNo = pidNo;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}
	
	
}