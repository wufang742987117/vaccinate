package com.thinkgem.jeesite.modules.child_baseinfo.entity;

import java.io.Serializable;
import java.util.Date;




import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

public class BCV implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("VACCINEID")
	private String vaccineid;

	@JsonProperty("DOCTOR")
	private String doctor;

	@JsonProperty("PRICE")
	private String price;

	@JsonProperty("BODYPART")
	private String bodypart;

	@JsonProperty("REMARKS")
	private String remarks;

	@JsonProperty("VACCTYPE")
	private String vacctype;

	@JsonProperty("STATUS")
	private int status;

	@JsonProperty("MANUFACTURERCODE")
	private String manufacturercode;

	@JsonProperty("SOURCE")
	private String source;

	@JsonProperty("ID")
	private String id;

	@JsonProperty("PAYSTATUS")
	private String paystatus;

	@JsonProperty("MANUFACTURER")
	private String manufacturer;

	@JsonProperty("BATCH")
	private String batch;

	@JsonProperty("CHILDID")
	private String childid;

	@JsonProperty("DOSAGE")
	private String dosage;

	@JsonProperty("PRODUCTID")
	private String productid;

	@JsonProperty("sign")
	private String sign;

	@JsonProperty("CREATEDATE")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdate;

	@JsonProperty("INSURANCE")
	private String insurance;

	@JsonProperty("OFFICE")
	private String office;

	@JsonProperty("VACC_NAME")
	private String vacc_name;

	@JsonProperty("VACC_BIGNAME")
	private String vaccCode;

	@JsonProperty("ISEFFECT")
	private String iseffect;

	@JsonProperty("VACCINATEDATE")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date vaccinatedate;

	@JsonProperty("NID")
	private String nid;

	public String getVaccineid() {
		return this.vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		if(StringUtils.isNoneBlank(vaccineid)){
			vaccineid=	vaccineid.trim();
		}
		
		this.vaccineid = vaccineid;
	}

	public String getDoctor() {
		return this.doctor;
	}

	public void setDoctor(String doctor) {
		if(StringUtils.isNoneBlank(doctor)){
			doctor=doctor.trim();	
		}
	
		this.doctor = doctor;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		if(StringUtils.isNoneBlank(price)){
			price=price.trim();
		}
	
		this.price = price;
	}

	public String getBodypart() {
		return this.bodypart;
	}

	public void setBodypart(String bodypart) {
		if(StringUtils.isNoneBlank(bodypart)){
			bodypart=bodypart.trim();
		}
		
		this.bodypart = bodypart;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		if(StringUtils.isNoneBlank(remarks)){
			remarks=remarks.trim();	
		}
		this.remarks = remarks;
	}

	public String getVacctype() {
		return this.vacctype;
	}

	public void setVacctype(String vacctype) {
		if(StringUtils.isNoneBlank(vacctype)){
			vacctype=vacctype.trim();	
		}
		this.vacctype = vacctype;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getManufacturercode() {
		return this.manufacturercode;
	}

	public void setManufacturercode(String manufacturercode) {
		if(StringUtils.isNoneBlank(manufacturercode)){
			manufacturercode=manufacturercode.trim();
		}
		this.manufacturercode = manufacturercode;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		 if(StringUtils.isNoneBlank(source)){
			 source=source.trim();
		 }
		this.source = source;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		if(StringUtils.isNoneBlank(id)){
			id=id.trim();
		}
		this.id = id;
	}

	public String getPaystatus() {
		return this.paystatus;
	}

	public void setPaystatus(String paystatus) {
		if(StringUtils.isNoneBlank(paystatus)){
			paystatus=paystatus.trim();	
		}
		this.paystatus = paystatus;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		if(StringUtils.isNoneBlank(manufacturer)){
			manufacturer=manufacturer.trim();
		}
		this.manufacturer = manufacturer;
	}

	public String getBatch() {
		return this.batch;
	}

	public void setBatch(String batch) {
		if(StringUtils.isNoneBlank(batch)){
			batch=batch.trim();
		}
		this.batch = batch;
	}

	public String getChildid() {
		return this.childid;
	}

	public void setChildid(String childid) {
		if(StringUtils.isNoneBlank(childid)){
			childid=childid.trim();
		}
		this.childid = childid;
	}

	public String getDosage() {
		return this.dosage;
	}

	public void setDosage(String dosage) {
		if(StringUtils.isNoneBlank(dosage)){
			dosage=dosage.trim();
			
		}
		this.dosage = dosage;
	}

	public String getProductid() {
		return this.productid;
	}

	public void setProductid(String productid) {
		if(StringUtils.isNoneBlank(productid)){
			productid=productid.trim();
		}
		this.productid = productid;
	}

	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		if(StringUtils.isNoneBlank(sign)){
			sign=sign.trim();
		}
		this.sign = sign;
	}

	public String getInsurance() {
		return this.insurance;
	}

	public void setInsurance(String insurance) {
		if(StringUtils.isNoneBlank(insurance)){
			insurance=insurance.trim();
		}
		this.insurance = insurance;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		if(StringUtils.isNoneBlank(office)){
			office=office.trim();
		}
		this.office = office;
	}

	public String getVacc_name() {
		return this.vacc_name;
	}

	public void setVacc_name(String vacc_name) {
		if(StringUtils.isNoneBlank(vacc_name)){
			vacc_name=vacc_name.trim();
		}
		this.vacc_name = vacc_name;
	}

	
	public String getVaccCode() {
		return vaccCode;
	}

	public void setVaccCode(String vaccCode) {
		if(StringUtils.isNoneBlank(vaccCode)){
			vaccCode=vaccCode.trim();
		}
		this.vaccCode = vaccCode;
	}

	public String getIseffect() {
		return this.iseffect;
	}

	public void setIseffect(String iseffect) {
		if(StringUtils.isNoneBlank(iseffect)){
			iseffect=iseffect.trim();
		}
		this.iseffect = iseffect;
	}

	public String getNid() {
		return this.nid;
	}

	public void setNid(String nid) {
		if(StringUtils.isNoneBlank(nid)){
			nid=nid.trim();
		}
		this.nid = nid;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getVaccinatedate() {
		return vaccinatedate;
	}

	public void setVaccinatedate(Date vaccinatedate) {
		this.vaccinatedate = vaccinatedate;
	}
	
	public String getLocalCode(){
		return OfficeService.getFirstOfficeCode();
	}

}