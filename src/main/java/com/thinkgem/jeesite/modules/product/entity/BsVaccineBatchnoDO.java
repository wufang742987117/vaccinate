/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 生成批次Entity
 * 
 * @author hcy
 * @version 2017-08-14
 */
public class BsVaccineBatchnoDO extends DataEntity<BsVaccineBatchnoDO> {

	private static final long serialVersionUID = 1L;

	private String batchno; // 批号
	private String secondBatchno; // 亚批号
	private String entrance; // 是否进口(字典)
	private String code; // 进口通关单编号
	private String certifiCode; // 批签发合格证明编号
	private String vid; // 疫苗固有信息表id
	private Date outBoundDate;
	private String vaccineId;
	private String companyCode;
	private String storeCode;//单位编码
	private Long sellprice;//出售价格 
	private String inoculation;//接种途径
	private Date updateDate;//修改时间
	private String updateCompany;//修改单位
	private String isshow;//隐藏 开启
	private String vaccineName;//疫苗名称
	private String companyName;//生产企业
	private String containerType;//容器类型	

	private String customBillNo;//批次文号
	private String dose;//剂量
	private String spec;//规格
	private String dateString;
	private String type;
	private String doseType;
	private String largePackage;
	
	private String createOrgCode;	//创建单位编码
	private String createOrgName;	//创建单位名称
	
	
	public String getDoseType() {
		return doseType;
	}

	public void setDoseType(String doseType) {
		this.doseType = doseType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String type1;
	public String getType1() {
		return type1 = DictUtils.getDictLabel(type, "bs_vaccineInfo_type", null);
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getDateString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (outBoundDate!=null){
			dateString = format.format(outBoundDate);
		}
		return dateString ;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getCustomBillNo() {
		return customBillNo;
	}

	public void setCustomBillNo(String customBillNo) {
		this.customBillNo = customBillNo;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateCompany() {
		return updateCompany;
	}

	public void setUpdateCompany(String updateCompany) {
		this.updateCompany = updateCompany;
	}

	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	public String getInoculation() {
		return inoculation;
	}

	public void setInoculation(String inoculation) {
		this.inoculation = inoculation;
	}

	public Long getSellprice() {
		return sellprice;
	}

	public void setSellprice(Long sellprice) {
		this.sellprice = sellprice;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(String vaccineId) {
		this.vaccineId = vaccineId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getOutBoundDate() {
		return outBoundDate;
	}

	public void setOutBoundDate(Date outBoundDate) {
		this.outBoundDate = outBoundDate;
	}

	public BsVaccineBatchnoDO() {
		super();
	}

	public BsVaccineBatchnoDO(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "批号长度必须介于 0 和 32 之间")
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	@Length(min = 0, max = 32, message = "亚批号长度必须介于 0 和 32 之间")
	public String getSecondBatchno() {
		return secondBatchno;
	}

	public void setSecondBatchno(String secondBatchno) {
		this.secondBatchno = secondBatchno;
	}

	@Length(min = 0, max = 1, message = "是否进口(字典)长度必须介于 0 和 1 之间")
	public String getEntrance() {
		return entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

	@Length(min = 0, max = 255, message = "进口通关单编号长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min = 0, max = 255, message = "批签发合格证明编号长度必须介于 0 和 255 之间")
	public String getCertifiCode() {
		return certifiCode;
	}

	public void setCertifiCode(String certificode) {
		this.certifiCode = certificode;
	}

	@Length(min = 0, max = 32, message = "疫苗固有信息表id长度必须介于 0 和 32 之间")
	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}
	
	public String getLargePackage() {
		return largePackage;
	}
	
	public void setLargePackage(String largePackage) {
		this.largePackage = largePackage;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
	

}