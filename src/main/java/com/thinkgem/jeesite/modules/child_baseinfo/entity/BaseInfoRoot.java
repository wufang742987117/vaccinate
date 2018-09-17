package com.thinkgem.jeesite.modules.child_baseinfo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

public class BaseInfoRoot implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty("CARDCODE")
	private String cardcode;

	@JsonProperty("RESIDE")
	private String reside;

	@JsonProperty("MAILINGADDRESS")
	private String mailingaddress;

	@JsonProperty("BIRTHHOSTIPAL")
	private String birthhostipal;

	@JsonProperty("FATHERCARD")
	private String fathercard;

	@JsonProperty("UPDATEDATE")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedate;

	@JsonProperty("REMARKS")
	private String remarks;

	@JsonProperty("PROPERTIES")
	private String properties;

	@JsonProperty("CHILDCODE")
	private String childcode;

	@JsonProperty("CHILDNAME")
	private String childname;

	@JsonProperty("FATHERPHONE")
	private String fatherphone;

	@JsonProperty("LOCALTYPE")
	private String localtype;

	@JsonProperty("FATHER")
	private String father;

	@JsonProperty("BIRTHCODE")
	private String birthcode;

	@JsonProperty("COUNTY")
	private String county;

	@JsonProperty("GUARDIANIDENTIFICATIONNUMBER")
	private String guardianidentificationnumber;

	@JsonProperty("ID")
	private String id;

	@JsonProperty("CHILDORDER")
	private String childorder;

	@JsonProperty("BIRTHDAY")
	private String birthday;

	@JsonProperty("AREA")
	private String area;

	@JsonProperty("OFFICEINFO")
	private String officeinfo;

	@JsonProperty("BIRTHWEIGHT")
	private int birthweight;

	@JsonProperty("OFFICECODE")
	private String officecode;

	@JsonProperty("BCV")
	private List<BCV> bcv;

	@JsonProperty("ADDRESS")
	private String address;

	@JsonProperty("CI")
	private String ci;

	@JsonProperty("GUARDIANNAME")
	private String guardianname;

	@JsonProperty("CREATEDATE")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdate;

	@JsonProperty("CREATER")
	private String creater;

	@JsonProperty("PROVINCE")
	private String province;

	@JsonProperty("PR")
	private String pr;

	@JsonProperty("GENDER")
	private int gender;

	@JsonProperty("GUARDIANMOBILE")
	private String guardianmobile;

	@JsonProperty("SITUATION")
	private String situation;

	@JsonProperty("NATION")
	private String nation;

	@JsonProperty("CO")
	private String co;

	@JsonProperty("CITY")
	private String city;

	@JsonProperty("REGADDRESS")
	private String regaddress;

	@JsonProperty("PARADOXICALREACTION")
	private String paradoxicalreaction;
	
	@JsonProperty("aefi")
	private List<aefi> aefi;
	
	@JsonProperty("chhe")
	private List<chhe> chhe;

	public String getCardcode() {
		return this.cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public String getReside() {
		return this.reside;
	}

	public void setReside(String reside) {
		this.reside = reside;
	}

	public String getMailingaddress() {
		return this.mailingaddress;
	}

	public void setMailingaddress(String mailingaddress) {
		this.mailingaddress = mailingaddress;
	}

	public String getBirthhostipal() {
		return this.birthhostipal;
	}

	public void setBirthhostipal(String birthhostipal) {
		this.birthhostipal = birthhostipal;
	}

	public String getFathercard() {
		return this.fathercard;
	}

	public void setFathercard(String fathercard) {
		this.fathercard = fathercard;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProperties() {
		return this.properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getChildcode() {
		return this.childcode;
	}

	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}

	public String getChildname() {
		return this.childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}

	public String getFatherphone() {
		return this.fatherphone;
	}

	public void setFatherphone(String fatherphone) {
		this.fatherphone = fatherphone;
	}

	public String getLocaltype() {
		return this.localtype;
	}

	public void setLocaltype(String localtype) {
		this.localtype = localtype;
	}

	public String getFather() {
		return this.father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getBirthcode() {
		return this.birthcode;
	}

	public void setBirthcode(String birthcode) {
		this.birthcode = birthcode;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getGuardianidentificationnumber() {
		return this.guardianidentificationnumber;
	}

	public void setGuardianidentificationnumber(
			String guardianidentificationnumber) {
		this.guardianidentificationnumber = guardianidentificationnumber;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChildorder() {
		return this.childorder;
	}

	public void setChildorder(String childorder) {
		this.childorder = childorder;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOfficeinfo() {
		return this.officeinfo;
	}

	public void setOfficeinfo(String officeinfo) {
		this.officeinfo = officeinfo;
	}

	public int getBirthweight() {
		return this.birthweight;
	}

	public void setBirthweight(int birthweight) {
		this.birthweight = birthweight;
	}

	public String getOfficecode() {
		return this.officecode;
	}

	public void setOfficecode(String officecode) {
		this.officecode = officecode;
	}

	public List<BCV> getBcv() {
		return this.bcv;
	}

	public void setBcv(List<BCV> bcv) {
		this.bcv = bcv;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCi() {
		return this.ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getGuardianname() {
		return this.guardianname;
	}

	public void setGuardianname(String guardianname) {
		this.guardianname = guardianname;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPr() {
		return this.pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getGuardianmobile() {
		return this.guardianmobile;
	}

	public void setGuardianmobile(String guardianmobile) {
		this.guardianmobile = guardianmobile;
	}

	public String getSituation() {
		return this.situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getCo() {
		return this.co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegaddress() {
		return this.regaddress;
	}

	public void setRegaddress(String regaddress) {
		this.regaddress = regaddress;
	}

	public String getParadoxicalreaction() {
		return this.paradoxicalreaction;
	}

	public void setParadoxicalreaction(String paradoxicalreaction) {
		this.paradoxicalreaction = paradoxicalreaction;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	public String getLocalCode(){
		return OfficeService.getFirstOfficeCode();
		
	}

	public List<aefi> getAefi() {
		return aefi;
	}

	public List<chhe> getChhe() {
		return chhe;
	}

	public void setAefi(List<aefi> aefi) {
		this.aefi = aefi;
	}

	public void setChhe(List<chhe> chhe) {
		this.chhe = chhe;
	}
	
	

}
