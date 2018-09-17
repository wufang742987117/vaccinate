/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 对账管理Entity
 * @author fuxin
 * @version 2017-06-07
 */
public class BillRecord extends DataEntity<BillRecord> {
	
	private static final long serialVersionUID = 1L;
	private String childid;		// 儿童ID
	private String vaccineid;		// 疫苗ID
	private String dosage;		// 第几针
	private Date vaccinatedate;		// 接种日期
	private String bodypart;		// 接种部位
	private String batch;		// 疫苗批号
	private String office;		// 接种单位编码
	private String doctor;		// 医生姓名
	private Date createdate;		// 创建时间
	private String price;		// 疫苗价格
	private String vaccName;		// 疫苗 名称
	private String manufacturer;		// 疫苗厂家
	private String productid;		// 对应产品ID
	private String nid;		// nid
	private String manufacturercode;		// 疫苗厂家编码
	private String source;		// 数据来源,0：登记台 1：微信 2：一体机3:补录4.五联拆解
	private String vaccBigname;		// 疫苗大类名称
	private String insurance;		// 保险0未买1已买
	private String vacctype;		// 接种类型
	private String iseffect;		// 是否异常反应0：无1有
	private String localcode;		// 站点编号
	private String orderno;		// 订单号
	private String county;		// 县
	private String city;		// 市
	private String province;		// 省
	private Date beginVaccinatedate;		// 开始 接种日期
	private Date endVaccinatedate;		// 结束 接种日期
	
	public BillRecord() {
		super();
	}

	public BillRecord(String id){
		super(id);
	}

	@Length(min=1, max=32, message="儿童ID长度必须介于 1 和 32 之间")
	public String getChildid() {
		return childid;
	}

	public void setChildid(String childid) {
		this.childid = childid;
	}
	
	@Length(min=0, max=50, message="疫苗ID长度必须介于 0 和 50 之间")
	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	
	@Length(min=0, max=50, message="第几针长度必须介于 0 和 50 之间")
	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVaccinatedate() {
		return vaccinatedate;
	}

	public void setVaccinatedate(Date vaccinatedate) {
		this.vaccinatedate = vaccinatedate;
	}
	
	@Length(min=0, max=20, message="接种部位长度必须介于 0 和 20 之间")
	public String getBodypart() {
		return bodypart;
	}

	public void setBodypart(String bodypart) {
		this.bodypart = bodypart;
	}
	
	@Length(min=0, max=20, message="疫苗批号长度必须介于 0 和 20 之间")
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	@Length(min=0, max=50, message="接种单位编码长度必须介于 0 和 50 之间")
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
	@Length(min=0, max=20, message="医生姓名长度必须介于 0 和 20 之间")
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@Length(min=0, max=50, message="疫苗价格长度必须介于 0 和 50 之间")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=30, message="疫苗 名称长度必须介于 0 和 30 之间")
	public String getVaccName() {
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}
	
	@Length(min=0, max=50, message="疫苗厂家长度必须介于 0 和 50 之间")
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@Length(min=0, max=32, message="对应产品ID长度必须介于 0 和 32 之间")
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	@Length(min=0, max=32, message="nid长度必须介于 0 和 32 之间")
	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}
	
	@Length(min=0, max=200, message="疫苗厂家编码长度必须介于 0 和 200 之间")
	public String getManufacturercode() {
		return manufacturercode;
	}

	public void setManufacturercode(String manufacturercode) {
		this.manufacturercode = manufacturercode;
	}
	
	@Length(min=0, max=200, message="五联拆解长度必须介于 0 和 200 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=0, max=200, message="疫苗大类名称长度必须介于 0 和 200 之间")
	public String getVaccBigname() {
		return vaccBigname;
	}

	public void setVaccBigname(String vaccBigname) {
		this.vaccBigname = vaccBigname;
	}
	
	@Length(min=0, max=2, message="保险0未买1已买长度必须介于 0 和 2 之间")
	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	
	@Length(min=1, max=2, message="接种类型长度必须介于 1 和 2 之间")
	public String getVacctype() {
		return vacctype;
	}

	public void setVacctype(String vacctype) {
		this.vacctype = vacctype;
	}
	
	@Length(min=0, max=1, message="是否异常反应0：无1有长度必须介于 0 和 1 之间")
	public String getIseffect() {
		return iseffect;
	}

	public void setIseffect(String iseffect) {
		this.iseffect = iseffect;
	}
	
	@Length(min=0, max=32, message="站点编号长度必须介于 0 和 32 之间")
	public String getLocalcode() {
		return localcode;
	}

	public void setLocalcode(String localcode) {
		this.localcode = localcode;
	}
	
	@Length(min=0, max=32, message="订单号长度必须介于 0 和 32 之间")
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
	@Length(min=0, max=32, message="县长度必须介于 0 和 32 之间")
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	@Length(min=0, max=32, message="市长度必须介于 0 和 32 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=32, message="省长度必须介于 0 和 32 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public Date getBeginVaccinatedate() {
		return beginVaccinatedate;
	}

	public void setBeginVaccinatedate(Date beginVaccinatedate) {
		this.beginVaccinatedate = beginVaccinatedate;
	}
	
	public Date getEndVaccinatedate() {
		return endVaccinatedate;
	}

	public void setEndVaccinatedate(Date endVaccinatedate) {
		this.endVaccinatedate = endVaccinatedate;
	}
		
}