package com.thinkgem.jeesite.modules.child_baseinfo.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;

public class BaseInfoVo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String childcode; // 儿童编码
	private String cardcode; // 身份证号
	private String birthcode; // 出生证号
	private String childname; // 儿童姓名
	private String gender; // 性别
	private String birthday; // 出生日期
	private String birthhostipal; // 出生医院名称
	private String birthweight; // 出生体重 kg
	private String guardianname; // 母亲姓名
	private String guardianrelation; // 儿童与监护人的关系
	private String guardianidentificationnumber; // 母亲的身份证号
	private String homeaddress; // 家庭住址详细地址
	private String registryaddress; // 户籍地址
	private String paradoxicalreaction; // 是否异常反应
	private String officeinfo; // 接种单位名称和联系方式
	private String createdate; // 创建日期
	private String creater; // 创建者
	private String guardianmobile; // 母亲手机号码
	private String nation; // 民族
	private String childorder; // 孩次
	private String situation; // 在册情况
	private String properties; // 户口属性
	private String reside; // 居住属性
	private String area; // 区域划分
	private String father; // 父/母亲
	private String fatherphone; // 父/母亲电话
	private String fathercard; // 父/母亲身份证号
	private String mailingaddress; // 通讯地址

	private String province; // 省
	private String city; // 市
	private String county; // 县
	private String address; // 详细地址
	private String pr; // 省
	private String ci; // 市
	private String co; // 县
	private String add; // 详细地址

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChildcode() {
		return childcode;
	}

	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}

	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public String getBirthcode() {
		return birthcode;
	}

	public void setBirthcode(String birthcode) {
		this.birthcode = birthcode;
	}

	public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthhostipal() {
		return birthhostipal;
	}

	public void setBirthhostipal(String birthhostipal) {
		this.birthhostipal = birthhostipal;
	}

	public String getBirthweight() {
		return birthweight;
	}

	public void setBirthweight(String birthweight) {
		this.birthweight = birthweight;
	}

	public String getGuardianname() {
		return guardianname;
	}

	public void setGuardianname(String guardianname) {
		this.guardianname = guardianname;
	}

	public String getGuardianrelation() {
		return guardianrelation;
	}

	public void setGuardianrelation(String guardianrelation) {
		this.guardianrelation = guardianrelation;
	}

	public String getGuardianidentificationnumber() {
		return guardianidentificationnumber;
	}

	public void setGuardianidentificationnumber(
			String guardianidentificationnumber) {
		this.guardianidentificationnumber = guardianidentificationnumber;
	}

	public String getHomeaddress() {
		return homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}

	public String getRegistryaddress() {
		return registryaddress;
	}

	public void setRegistryaddress(String registryaddress) {
		this.registryaddress = registryaddress;
	}

	public String getParadoxicalreaction() {
		return paradoxicalreaction;
	}

	public void setParadoxicalreaction(String paradoxicalreaction) {
		this.paradoxicalreaction = paradoxicalreaction;
	}

	public String getOfficeinfo() {
		return officeinfo;
	}

	public void setOfficeinfo(String officeinfo) {
		this.officeinfo = officeinfo;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getGuardianmobile() {
		return guardianmobile;
	}

	public void setGuardianmobile(String guardianmobile) {
		this.guardianmobile = guardianmobile;
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

	public String getFathercard() {
		return fathercard;
	}

	public void setFathercard(String fathercard) {
		this.fathercard = fathercard;
	}

	public String getMailingaddress() {
		return mailingaddress;
	}

	public void setMailingaddress(String mailingaddress) {
		this.mailingaddress = mailingaddress;
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

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public static BaseInfoVo parse(ChildBaseinfo info){
		BaseInfoVo vo = new BaseInfoVo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setId(info.getId());
		vo.setChildcode(info.getChildcode());
		vo.setCardcode(info.getCardcode());
		vo.setBirthcode(info.getBirthcode());
		vo.setChildname(info.getChildname());
		vo.setGender(info.getGender());
		if(null != info.getBirthday()){
			vo.setBirthday(sdf.format(info.getBirthday()));
		}
		vo.setBirthhostipal(info.getBirthhostipal());
		vo.setBirthweight(info.getBirthweight());
		vo.setGuardianname(info.getGuardianname());
		vo.setGuardianrelation(info.getGuardianrelation());
		vo.setGuardianidentificationnumber(info.getGuardianidentificationnumber());
		vo.setHomeaddress(info.getHomeaddress());
		vo.setRegistryaddress(info.getRegistryaddress());
		vo.setParadoxicalreaction(info.getParadoxicalreaction());
		vo.setOfficeinfo(info.getOfficeinfo());
		if(null != info.getCreatedate()){
			vo.setCreatedate(sdf.format(info.getCreatedate()));
		}
		vo.setCreater(info.getCreater());
		vo.setGuardianmobile(info.getGuardianmobile());
		vo.setNation(info.getNation());
		vo.setChildorder(info.getChildorder());
		vo.setSituation(info.getSituation());
		vo.setProperties(info.getProperties());
		vo.setReside(info.getReside());
		vo.setArea(info.getArea());
		vo.setFather(info.getFather());
		vo.setFatherphone(info.getFatherphone());
		vo.setFathercard(info.getFathercard());
		vo.setMailingaddress(info.getMailingaddress());

		vo.setProvince(info.getProvince());
		vo.setCity(info.getCity());
		vo.setCounty(info.getCounty());
		vo.setAddress(info.getAddress());
		vo.setPr(info.getPr());
		vo.setCi(info.getCi());
		vo.setCo(info.getCo());
		vo.setAdd(info.getAdd());
		
		return vo;
		
	}
	
	
	public static HashMap<String, String> parseMap(ChildBaseinfo info){
		HashMap<String, String> vo = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		vo.put("id",info.getId());
		vo.put("childcode",info.getChildcode());
		vo.put("cardcode",info.getCardcode());
		vo.put("birthcode",info.getBirthcode());
		vo.put("childname",info.getChildname());
		vo.put("gender",info.getGender());


		if(null != info.getBirthday()){
			vo.put("birthday",sdf.format(info.getBirthday()));
		}


		vo.put("birthhostipal",info.getBirthhostipal());
		vo.put("birthweight",info.getBirthweight());
		vo.put("guardianname",info.getGuardianname());
		vo.put("guardianrelation",info.getGuardianrelation());
		vo.put("guardianidentificationnumber",info.getGuardianidentificationnumber());
		vo.put("homeaddress",info.getHomeaddress());
		vo.put("registryaddress",info.getRegistryaddress());
		vo.put("paradoxicalreaction",info.getParadoxicalreaction());
		vo.put("officeinfo",info.getOfficeinfo());


		if(null != info.getCreateDate()){
			vo.put("createdate",sdf.format(info.getCreateDate()));
		}

		vo.put("creater",info.getCreater());
		vo.put("guardianmobile",info.getGuardianmobile());
		vo.put("nation",info.getNation());
		vo.put("childorder",info.getChildorder());
		vo.put("situation",info.getSituation());
		vo.put("properties",info.getProperties());
		vo.put("reside",info.getReside());
		vo.put("area",info.getArea());
		vo.put("father",info.getFather());
		vo.put("fatherphone",info.getFatherphone());
		vo.put("fathercard",info.getFathercard());
		vo.put("mailingaddress",info.getMailingaddress());
		vo.put("province",info.getProvince());
		vo.put("city",info.getCity());
		vo.put("county",info.getCounty());
		vo.put("address",info.getAddress());
		vo.put("pr",info.getPr());
		vo.put("ci",info.getCi());
		vo.put("co",info.getCo());
		vo.put("add",info.getAdd());
		
		return vo;
		
	}
	
}
