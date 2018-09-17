package com.thinkgem.jeesite.modules.hepatitis.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;

/**
 * 乙肝疫苗接种着档案
 * 
 * @author xuejinshan
 * @date 2017年7月31日 下午7:14:13
 * @description TODO
 */
public class BsHepatitisBcheckinStock extends DataEntity<BsHepatitisBcheckinStock> {

	private static final long serialVersionUID = 1L;
	private String vaccineId; // 疫苗名称
	private String vaccineName; // 疫苗名称
	private String standard; // 规格
	private String manufacturer; // 生产厂家（制造商）
	private String batch; // 批号
	private String storeNum; // 现有库存
	private String storeNum1; // 未付款总量
	private String storeNum2; // 已付款总量
	private String storeNum3; // 已完成总量
	private int leng = 0; // 记录接口使用长度参数
	private int vaccineNum;
	private String vcount;
	private String createById;
	
	private String vaccType; // 疫苗类型
	
	/** 查询条件-开始时间 */
	private Date beginTime; // 开始时间
	/** 查询条件-结束时间 */
	private Date endTime; // 结束时间
	
	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	public void setVaccineNum(int vaccineNum) {
		this.vaccineNum = vaccineNum;
	}

	public String getVcount() {
		return vcount;
	}

	public void setVcount(String vcount) {
		this.vcount = vcount;
	}

	public String getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(String vaccineId) {
		this.vaccineId = vaccineId;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}

	public String getStoreNum1() {
		return storeNum1;
	}

	public void setStoreNum1(String storeNum1) {
		this.storeNum1 = storeNum1;
	}

	public String getStoreNum2() {
		return storeNum2;
	}

	public void setStoreNum2(String storeNum2) {
		this.storeNum2 = storeNum2;
	}

	public String getStoreNum3() {
		return storeNum3;
	}

	public void setStoreNum3(String storeNum3) {
		this.storeNum3 = storeNum3;
	}

	public int getLeng() {
		return leng;
	}

	public void setLeng(int leng) {
		this.leng = leng;
	}

	public int getVaccineNum() {
		return vaccineNum;
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

	public String getVaccType() {
		return vaccType;
	}

	public void setVaccType(String vaccType) {
		this.vaccType = vaccType;
	}

}
