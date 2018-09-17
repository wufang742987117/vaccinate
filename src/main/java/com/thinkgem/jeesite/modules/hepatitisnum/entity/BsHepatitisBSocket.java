package com.thinkgem.jeesite.modules.hepatitisnum.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;

public class BsHepatitisBSocket extends DataEntity<BsHepatitisBSocket> {

	private static final long serialVersionUID = 1L;

	private String vaccineId; // 疫苗名称
	private Integer vaccineNum; // 针次1,2,3,4,5,
	private String batch; // 疫苗批号
	private String manufacturer; // 生产厂家
	private String standard; // 规格
	private String Vcount; // 小计
	private String vaccineName; // 疫苗名称
	private String storeNum;
	private String storeNum2; // 已付款总量

	private String vaccType; // 疫苗类型
	
	private int leng = 0; // 接种记录接口使用长度参数

	/** 查询条件-开始时间 */
	private Date beginTime;
	/** 查询条件-结束时间 */
	private Date endTime;

	public String getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}
	
	public Integer getVaccineNum() {
		return vaccineNum;
	}

	public void setVaccineNum(Integer vaccineNum) {
		this.vaccineNum = vaccineNum;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getVcount() {
		return Vcount;
	}

	public void setVcount(String vcount) {
		Vcount = vcount;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public int getLeng() {
		return leng;
	}

	public void setLeng(int leng) {
		this.leng = leng;
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

	public String getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(String vaccineId) {
		this.vaccineId = vaccineId;
	}

	public String getStoreNum2() {
		return storeNum2;
	}

	public void setStoreNum2(String storeNum2) {
		this.storeNum2 = storeNum2;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getVaccType() {
		return vaccType;
	}

	public void setVaccType(String vaccType) {
		this.vaccType = vaccType;
	}

}
