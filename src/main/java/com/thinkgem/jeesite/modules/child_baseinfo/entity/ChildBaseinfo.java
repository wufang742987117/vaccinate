/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_baseinfo.entity;

import java.util.Date;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 档案管理Entity
 * 
 * @author 王德地
 * @version 2017-02-06
 */
public class ChildBaseinfo extends DataEntity<ChildBaseinfo> {

	private static final long serialVersionUID = 1L;
	private String childcode; // 儿童编码
	private String cardcode; // 儿童身份证号
	private String birthcode; // 出生证号
	private String childname; // 儿童姓名
	private String gender; // 性别
	private Date birthday; // 出生日期
	private String birthhostipal; // 出生医院名称
	private String birthweight; // 出生体重 kg
	private String guardianname; // 母亲姓名
	private String guardianrelation; // 儿童与监护人的关系
	private String guardianidentificationnumber; // 母亲身份证号
	private String homeaddress; // 家庭住址详细地址
	private String registryaddress; // 户籍地址
	private String paradoxicalreaction; // 是否异常反应
	private String officeinfo; // 接种单位名称
	private Date createdate; // 创建日期
	private String creater; // 创建者
	private String guardianmobile; //母亲手机号码
	private String nation;		// 民族
	private String childorder;		// 孩次
	
	private String situation;		// 在册类别
	private String situations;		// 在册类别,建档统计时用
	
	private String properties;		// 户口类别
	private String propertiess;		// 户口类别,建档统计时用
	
	private String reside;		// 居住类别
	private String resides;		// 户籍类别,建档统计时用
	
	private String area;		// 区域划分
	private String areas;       //区域划分,建档统计时用
	private String father;		// 父/母亲
	private String fatherphone;		// 父亲/母亲电话
	private String fathercard;		// 父亲/母亲身份证号
	private String mailingaddress;		// 通讯地址

	private String province;		// 省
	private String city;		// 市
	private String county;		// 县
	private String address;		// 详细地址
	private String pr;		// 省
	private String ci;		// 市
	private String co;	//县
	private String add;	//详细地址
	private String upstatus;	//已上报标记1，未上报标记0
	
	/** 查询条件-创建时间——开始时间 */
	private Date beginTime;

	/** 查询条件-创建时间——结束时间 */
	private Date endTime;
	/** 查询条件-出生日期——开始时间 */
	private Date birthbeginTime;
	/** 查询条件-创建出生——结束时间 */
	private Date birthendTime;
	
	private String mouage;//儿童月龄
	private String vaccine;//乙肝或者或者乙肝免疫球蛋白
	private String bcg;//卡介苗
	private String hepbig;//乙肝球蛋白
	private String cho;//卡介苗
	private String scy;//卡介苗
	private String hpy;//卡介苗
	private String name;//疫苗名称
	private String officecode;//接种单位编码
	private String tempid;//建档编号
	private String localtype ;//是不是迁入的记录0本地1迁入
	private String notid;//接种记录里面的一类疫苗ID(打印入托证明时用)
	private String kindergartencode;//幼儿园编码

	private String searchBirth;
	private int mouthAgeInt;
	
	private String userId; //微信建卡用户ID
	private String vaccinate;
	private String vaccinates;
	private String nid;
	private Date sdate;
	private String ovmonth;
	private Map<String,String> dicts;	//存放字典值
	private String hasRemind;			//是否有预约
	private boolean hasRemindOption;	//是否包含预约开关
	private String fileorigin;	 //档案来源 1:本地 2：微信 3：APP



	
	public ChildBaseinfo() {
		super();
	}

	public ChildBaseinfo(String id) {
		super(id);
	}

	@ExcelField(align=2, title="儿童编码", sort = 10 )
	@Length(min = 1, max = 50, message = "儿童编码长度必须介于 1 和 50 之间")
	public String getChildcode() {
		return childcode;
	}

	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}

	@ExcelField(align=2, title="儿童身份证号", sort = 20 )
	@Length(min = 0, max = 50, message = "身份证号长度必须介于 0 和 50 之间")
	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	@ExcelField(align=2, title="出生证号", sort = 30 )
	@Length(min = 0, max = 50, message = "出生证号长度必须介于 0 和 50 之间")
	public String getBirthcode() {
		return birthcode;
	}

	public void setBirthcode(String birthcode) {
		this.birthcode = birthcode;
	}

	@ExcelField(align=2, title="儿童姓名", sort = 40 )
	@Length(min = 1, max = 20, message = "儿童姓名长度必须介于 1 和 20 之间")
	public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}

	@ExcelField(align=2, title="性别", sort = 50, dictType="sex" )
	@Length(min = 1, max = 1, message = "性别长度必须介于 1 和 1 之间")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@ExcelField(align = 2, title = "出生日期", sort = 60, fieldType = Date.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@ExcelField(align=2, title="出生医院名称", sort = 70)
	@Length(min = 0, max = 50, message = "出生医院名称长度必须介于 0 和 50 之间")
	public String getBirthhostipal() {
		return birthhostipal;
	}

	public void setBirthhostipal(String birthhostipal) {
		this.birthhostipal = birthhostipal;
	}

	@ExcelField(align=2, title="出生体重(kg)", sort = 80)
	public String getBirthweight() {
		return birthweight;
	}

	public void setBirthweight(String birthweight) {
		this.birthweight = birthweight;
	}

	@ExcelField(align=2, title="母亲姓名", sort = 90)
	@Length(min = 0, max = 20, message = "监护人姓名长度必须介于 0 和 20 之间")
	public String getGuardianname() {
		return guardianname;
	}

	public void setGuardianname(String guardianname) {
		this.guardianname = guardianname;
	}

	
	@Length(min = 0, max = 20, message = "儿童与监护人的关系长度必须介于 0 和 20 之间")
	public String getGuardianrelation() {
		return guardianrelation;
	}

	public void setGuardianrelation(String guardianrelation) {
		this.guardianrelation = guardianrelation;
	}

	@ExcelField(align = 2, title = "家庭住址详细地址", sort = 110)
	@Length(min = 0, max = 100, message = "家庭住址详细地址长度必须介于 0 和 100 之间")
	public String getHomeaddress() {
		return homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}

	@ExcelField(align = 2, title = "户籍地址", sort = 120)
	@Length(min = 0, max = 20, message = "户籍地址长度必须介于 0 和 20 之间")
	public String getRegistryaddress() {
		return registryaddress;
	}

	public void setRegistryaddress(String registryaddress) {
		this.registryaddress = registryaddress;
	}

	
	@Length(min = 0, max = 1, message = "是否异常反应长度必须介于 0 和 1 之间")
	public String getParadoxicalreaction() {
		return paradoxicalreaction;
	}

	public void setParadoxicalreaction(String paradoxicalreaction) {
		this.paradoxicalreaction = paradoxicalreaction;
	}

	@ExcelField(align = 2, title = "接种单位名称", sort = 140)
	@Length(min = 0, max = 100, message = "接种单位名称和联系方式长度必须介于 0 和 100 之间")
	public String getOfficeinfo() {
		return officeinfo;
	}

	public void setOfficeinfo(String officeinfo) {
		this.officeinfo = officeinfo;
	}

	
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Length(min = 0, max = 20, message = "创建者长度必须介于 0 和 20 之间")
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	/*public String getProvince() {
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProvince1() {
		return province1;
	}

	public void setProvince1(String province1) {
		this.province1 = province1;
	}

	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getArea1() {
		return area1;
	}

	public void setArea1(String area1) {
		this.area1 = area1;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}*/

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
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

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthbeginTime() {
		return birthbeginTime;
	}

	public void setBirthbeginTime(Date birthbeginTime) {
		this.birthbeginTime = birthbeginTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthendTime() {
		return birthendTime;
	}

	public void setBirthendTime(Date birthendTime) {
		this.birthendTime = birthendTime;
	}
	
	
	
	
	// begin
	public String getBegin() {
		if(this.beginTime != null){
			return DateUtils.formatDateTime(this.beginTime);
		}
		else{
			return "";
		}
	}
	// end
	public String getEnd() {
		if(this.endTime != null){
			return DateUtils.formatDateTime(this.endTime);
		}else{
			return "";
		}
	}
	// birthbegin
	public String getBirthbegin() {
		if(this.birthbeginTime != null){
			return DateUtils.formatDateTime(this.birthbeginTime);
		}
		else{
			return "";
		}
	}
	// birthend
	public String getBirthend() {
		if(this.birthendTime != null){
			return DateUtils.formatDateTime(this.birthendTime);
		}else{
			return "";
		}
	}
	
	/**
	 *获取儿童编码前12位 
	 */
	public String getChildcodePre(){
		if(StringUtils.isNotBlank(childcode) && childcode.length() == 18){
			return childcode.substring(0,12);
		}
		return "";
	}
	
	
	/**
	 *获取儿童编码后6位 
	 */
	public String getChildcodeEnd(){
		if(StringUtils.isNotBlank(childcode) && childcode.length() == 18){
			return childcode.substring(12,18);
		}
		return "";
	}

	@ExcelField(align = 2, title = "母亲手机号", sort = 95)
	public String getGuardianmobile() {
		return guardianmobile;
	}

	public void setGuardianmobile(String guardianmobile) {
		this.guardianmobile = guardianmobile;
	}
	@ExcelField(align = 2, title = "母亲身份证号", sort = 92)
	public String getGuardianidentificationnumber() {
		return guardianidentificationnumber;
	}

	public void setGuardianidentificationnumber(String guardianidentificationnumber) {
		this.guardianidentificationnumber = guardianidentificationnumber;
	}

	/**
	 * 默认计算月龄
	 * @author fuxin
	 * @date 2017年12月11日 上午9:40:44
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String getMouage() {
		if(StringUtils.isBlank(mouage) && birthday != null){
			return DateUtils.getMouthAge(birthday);
		}
		return mouage;
	}

	public void setMouage(String mouage) {
		this.mouage = mouage;
	}

	public String getVaccine() {
		return vaccine;
	}

	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

	public String getBcg() {
		return bcg;
	}

	public void setBcg(String bcg) {
		this.bcg = bcg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getChildorder() {
		return childorder;
	}

	public void setChildorder(String childorder) {
		this.childorder = childorder;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}
	@ExcelField(align=2, title="户籍类别", sort = 55, dictType="reside" )
	public String getReside() {
		return reside;
	}

	public void setReside(String reside) {
		this.reside = reside;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getFatherphone() {
		return fatherphone;
	}

	public void setFatherphone(String fatherphone) {
		this.fatherphone = fatherphone;
	}

	public String getMailingaddress() {
		return mailingaddress;
	}

	public void setMailingaddress(String mailingaddress) {
		this.mailingaddress = mailingaddress;
	}

	public String getFathercard() {
		return fathercard;
	}

	public void setFathercard(String fathercard) {
		this.fathercard = fathercard;
	}

	public String getCho() {
		return cho;
	}

	public void setCho(String cho) {
		this.cho = cho;
	}

	public String getScy() {
		return scy;
	}

	public void setScy(String scy) {
		this.scy = scy;
	}

	public String getHpy() {
		return hpy;
	}

	public void setHpy(String hpy) {
		this.hpy = hpy;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getResides() {
		if(StringUtils.isNoneBlank(resides)){
			return ("'" + resides + "'").replace(",", "','");
		}
		return null;
	}

	public void setResides(String resides) {
		this.resides = resides;
	}

	public String getSituations() {
		if(StringUtils.isNoneBlank(situations)){
			return ("'" + situations + "'").replace(",", "','");
		}
		return null;
	}

	public void setSituations(String situations) {
		this.situations = situations;
	}

	public String getPropertiess() {
		if(StringUtils.isNoneBlank(propertiess)){
			return ("'" + propertiess + "'").replace(",", "','");
		}
		return null;
	}

	public void setPropertiess(String propertiess) {
		this.propertiess = propertiess;
	}
	
	public String getAreas() {
		if(StringUtils.isNoneBlank(areas)){
			return ("'" + areas + "'").replace(",", "','");
		}
		return null;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getOfficecode() {
		return officecode;
	}

	public void setOfficecode(String officecode) {
		this.officecode = officecode;
	}

	public String getLocaltype() {
		return localtype;
	}

	public void setLocaltype(String localtype) {
		this.localtype = localtype;
	}

	public String getTempid() {
		return tempid;
	}

	public void setTempid(String tempid) {
		this.tempid = tempid;
	}
	
	//已上报标记1，未上报标记0
	public String getUpstatus() {
		if(StringUtils.isBlank(upstatus)){
			return "0";
		}
		return upstatus;
	}
	public void setUpstatus(String upstatus) {
		this.upstatus = upstatus;
	}

	public String getNotid() {
		if(StringUtils.isNoneBlank(notid)){
			notid=notid.trim();
		}
		return notid;
	}

	public void setNotid(String notid) {
		this.notid = notid;
	}

	public String getSearchBirth() {
		return searchBirth;
	}

	public void setSearchBirth(String searchBirth) {
		this.searchBirth = searchBirth;
	}

	public String getHepbig() {
		return hepbig;
	}

	public void setHepbig(String hepbig) {
		this.hepbig = hepbig;
	}

	public String getKindergartencode() {
		return kindergartencode;
	}

	public void setKindergartencode(String kindergartencode) {
		this.kindergartencode = kindergartencode;
	}

	public int getMouthAgeInt() {
		return mouthAgeInt;
	}

	public void setMouthAgeInt(int mouthAgeInt) {
		this.mouthAgeInt = mouthAgeInt;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVaccinates() {
		if(StringUtils.isNoneBlank(vaccinates)){
			return ("'" + vaccinates + "'").replace(",", "','");
		}
		return vaccinates;
	}

	public void setVaccinates(String vaccinates) {
		this.vaccinates = vaccinates;
	}

	public String getVaccinate() {
		return vaccinate;
	}

	public void setVaccinate(String vaccinate) {
		this.vaccinate = vaccinate;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public String getOvmonth() {
		return ovmonth;
	}

	public void setOvmonth(String ovmonth) {
		this.ovmonth = ovmonth;
	}
	
	private int checkEx;		//排除近期接种

	public int getCheckEx() {
		return checkEx;
	}

	public void setCheckEx(int checkEx) {
		this.checkEx = checkEx;
	}

	public Map<String, String> getDicts() {
		return dicts;
	}

	public void setDicts(Map<String, String> dicts) {
		this.dicts = dicts;
	}


	public String getHasRemind() {
		return hasRemind;
	}

	public void setHasRemind(String hasRemind) {
		this.hasRemind = hasRemind;
	}

	public boolean isHasRemindOption() {
		return hasRemindOption;
	}

	public void setHasRemindOption(boolean hasRemindOption) {
		this.hasRemindOption = hasRemindOption;
	}

	public String getFileorigin() {
		return fileorigin;
	}

	public void setFileorigin(String fileorigin) {
		this.fileorigin = fileorigin;
	}
}