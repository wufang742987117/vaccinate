/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitis.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 乙肝信息管理Entity
 * 
 * @author xuejinshan
 * @version 2017-07-25
 */
public class BsHepatitisBcheckin extends DataEntity<BsHepatitisBcheckin> {

	/** 针次默认值 */
	public static String DOSAGE_DEFAULTS = "3"; // Defaults
	/** 接种针次默认值 */
	public static Integer TIMES_DEFAULT = 3;
	/** 第一次默认间隔时间 */
	public static Integer INTERVAL_1 = 0;
	/** 第二次默认间隔时间 */
	public static Integer INTERVAL_2 = 1;
	public static Integer INTERVAL_4 = 5;
	/** 第三次默认间隔时间 */
	public static Integer INTERVAL_3 = 6;
	/** 默认否 */
	public static String EBEFOR = "0";
	/**抗体水平默认值 为 0 即为阴性 */
	public static final String ANTIBODIES_DEFAULT="0";

	private static final long serialVersionUID = 1L;
	private String username; // 姓名
	private String sex; // 性别
	private Date birthday; // 出生日期
	private Integer age; // 年龄
	private String homeAddress; // 详细地址
	private String linkPhone; // 联系电话
	private String idcardNo; // 身份证号
	private String vaccineName; // 疫苗名称
	private String standard; // 规格
	private String batch; // 批号
	private String inoculationStatus; // 是否接种疫苗：0，否 1，是
	private String weight; // 体重
	private String dosage; // 接种剂量
	private String manufacturer; // 生产厂家（制造商）
	private String tempId; // 建档编号
	private String openId; // 微信建档openid
	private String payStatus; // 是否已付款;0 未付款、1 已付款
	private String history; // 既往病史
	private String address; // 详细地址
	private String province; // 省
	private String city; // 市
	private String county; // 区
	private String searchName; // 查询字段 searchName
	private Integer finishTimes; // 已完成针数
	private Integer totalTimes; // 所有的针数
	private String id;
	private String hepaBcode;//乙肝编码
	private Date createDate;
	private String beginCode;
	private String endCode; //
	private String deFlag;//是否删除
	private String antibodies; //抗体水平  0 阴性（无抗体）   1 弱阳性（有，较弱）
	private String search; // 查询标识
	private Date newdate; // 接种时间
	
	private String pid; // pid
	private String vaccineId; // 疫苗小类id
	private String realname; //监护人姓名
	private String vaccType; // 疫苗类型
	
	/** 查询条件-开始时间 */
	private Date beginTime; // 开始时间
	/** 查询条件-结束时间 */
	private Date endTime; // 结束时间
	
	public BsHepatitisBcheckin() {
		super();
	}

	public BsHepatitisBcheckin(String id) {
		super(id);
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title="档案编码",sort=10)
	public String getHepaBcode() {
		return hepaBcode;
	}

	public void setHepaBcode(String hepaBcode) {
		this.hepaBcode = hepaBcode;
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

	public Integer getFinishTimes() {
		return finishTimes;
	}

	public void setFinishTimes(Integer finishTimes) {
		this.finishTimes = finishTimes;
	}

	public Integer getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(Integer totalTimes) {
		this.totalTimes = totalTimes;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	@Length(min = 1, max = 50, message = "姓名长度必须介于 1 和 50 之间")
	@ExcelField(title = "姓名", align = 1, sort = 20)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Length(min = 1, max = 10, message = "性别长度必须介于 1 和 10 之间")
	@ExcelField(title = "性别", align = 1, sort = 40, dictType = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "出生日期", align = 1, sort = 50)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@ExcelField(title = "年龄", align = 1, sort =60)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Length(min = 0, max = 200, message = "详细地址长度必须介于 0 和 200 之间")
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Length(min = 0, max = 50, message = "联系电话长度必须介于 0 和 50 之间")
	@ExcelField(title = "联系电话", align = 1, sort = 70)
	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	@Length(min = 0, max = 50, message = "身份证号长度必须介于 0 和 50 之间")
	@ExcelField(title = "身份证号", align = 1, sort = 30)
	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	@Length(min = 0, max = 50, message = "疫苗名称长度必须介于 0 和 50 之间")
	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	@Length(min = 0, max = 50, message = "规格长度必须介于 0 和 50 之间")
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Length(min = 0, max = 50, message = "批号长度必须介于 0 和 50 之间")
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Length(min = 0, max = 10, message = "是否接种疫苗：0，否 1，是长度必须介于 0 和 10 之间")
	public String getInoculationStatus() {
		return inoculationStatus;
	}

	public void setInoculationStatus(String inoculationStatus) {
		this.inoculationStatus = inoculationStatus;
	}

	@Length(min = 0, max = 10, message = "体重长度必须介于 0 和 10 之间")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Length(min = 0, max = 10, message = "接种剂量长度必须介于 0 和 10 之间")
	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	@Length(min = 0, max = 50, message = "生产厂家（制造商）长度必须介于 0 和 50 之间")
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Length(min = 0, max = 32, message = "建档编号长度必须介于 0 和 32 之间")
	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	@Length(min = 0, max = 32, message = "微信建档openid长度必须介于 0 和 32 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Length(min = 0, max = 1, message = "是否已付款;0 未付款、1 已付款长度必须介于 0 和 1 之间")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	@Length(min = 0, max = 255, message = "既往病史长度必须介于 0 和 255 之间")
	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@Length(min = 0, max = 200, message = "详细地址长度必须介于 0 和 200 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min = 1, max = 50, message = "省长度必须介于 1 和 50 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Length(min = 1, max = 50, message = "市长度必须介于 1 和 50 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Length(min = 0, max = 50, message = "区长度必须介于 0 和 50 之间")
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@ExcelField(title="建档日期",sort=150)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBeginCode() {
		return beginCode;
	}

	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getDeFlag() {
		return deFlag;
	}

	public void setDeFlag(String deFlag) {
		this.deFlag = deFlag;
	}

	public String getAntibodies() {
		return antibodies;
	}

	public void setAntibodies(String antibodies) {
		this.antibodies = antibodies;
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

	@ExcelField(title = "疫苗类型", align = 1, sort = 11)
	public String getVaccType() {
		return vaccType;
	}

	public void setVaccType(String vaccType) {
		this.vaccType = vaccType;
	}

	@ExcelField(title = "监护人姓名", align = 1, sort = 21)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(String vaccineId) {
		this.vaccineId = vaccineId;
	}
	
}