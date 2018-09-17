/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 库存期初建账Entity
 * 
 * @author hcy
 * @version 2017-07-18
 */
public class BsProductDO extends DataEntity<BsProductDO> {

	private static final long serialVersionUID = 1L;

	/** 订单状态-可用 */
	public static final String ORDERSTATUS_ABLE = "5";
	/** 订单状态-不可用 */
	public static final String ORDERSTATUS_DISABLE = "4";
	/** 产品为初期建账 */
	public static final String PRODUCT_TYPE = "0";

	private String id;
	private String vid;
	private BsVaccineBatchnoDO bsVaccineBatchno;
	private String vaccineid; // 疫苗编号
	private String batchno; // 批次编号
	private String spec; // 规格(剂/支或粒)字典
	private String companyName; // 疫苗的制造厂商
	private String type; // 1一类 2二类字典
	private Long storenum; // 库存(支或粒)
	private double sellprice; // 出售价格
	private String isshow; // 是否
	private String vaccName;
	private Date vaccExpDate; // 有效期
	private String codeall; // 疫苗英文名称
	private String companyCode; // 疫苗厂家编码
	private String dose; // 剂量字典
	private String dosetype; // 剂型字典
	private Date beginVaccExpDate; // 开始 有效期
	private Date endVaccExpDate; // 结束 有效期
	private String orderno;
	private String ordertype;
	private Date time;
	private String date;
	private String name;
	private String code;
	private String remarks;
	private String storeCode;
	// private Date createDat;
	private String areaName;
	private double orignprice;// 成本价

	private Date orderDate; // 订单日期
	private String orderStatus;// 疫苗状态 4不可用 5可用，对应出入库订单状态
	private String orderId;// 主订单ID
	private String batchType;   //类型
	
	private String cType;   //疫苗类型 一类，二类
	private String rooms;	//市平台下发门诊时用

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private String provider;
	private String receiver;
	private String recordor;
	private String batchnoId;

	public String getBatchnoId() {
		return batchnoId;
	}

	public void setBatchnoId(String batchnoId) {
		this.batchnoId = batchnoId;
	}

	public BsVaccineBatchnoDO getBsVaccineBatchno() {
		return bsVaccineBatchno;
	}

	public void setBsVaccineBatchno(BsVaccineBatchnoDO bsVaccineBatchno) {
		this.bsVaccineBatchno = bsVaccineBatchno;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getRecordor() {
		return recordor;
	}

	public void setRecordor(String recordor) {
		this.recordor = recordor;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getOrderno() {
		return orderno;
	}

	public BsProductDO() {
		super();
	}

	public BsProductDO(String id) {
		super(id);
	}

	@Length(min = 0, max = 10, message = "疫苗编号长度必须介于 0 和 10 之间")
	@ExcelField(title = "疫苗编号", align = 2, sort = 10)
	public String getVaccineid() {
		// return bsVaccineInfo.getVaccineId();
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}

	@Length(min = 0, max = 20, message = "批次编号长度必须介于 0 和 20 之间")
	@ExcelField(title = "批次编号", align = 2, sort = 50)
	public String getBatchno() {
		// return bsVaccineBatchno.getBatchno();
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	@ExcelField(title = "规格", align = 2, sort = 60)
	public String getSpec() {
		// return bsVaccineInfo.getSpec();
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Length(min = 0, max = 50, message = "疫苗的制造厂商长度必须介于 0 和 50 之间")
	@ExcelField(title = "厂家名称", align = 2, sort = 40)
	public String getCompanyName() {
		// return bsVaccineInfo.getCompanyName();
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Length(min = 0, max = 1, message = "1一类 2二类字典长度必须介于 0 和 1 之间")
	@ExcelField(title = "疫苗类型", dictType = "bs_product_type", align = 2, sort = 30)
	public String getType() {
		// return bsVaccineInfo.getType();
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotNull(message = "库存(支或粒)不能为空")
	@ExcelField(title = "库存", align = 2, sort = 80)
	public Long getStorenum() {
		return storenum;
	}

	public void setStorenum(Long storenum) {
		this.storenum = storenum;
	}

	public double getSellprice() {
		return sellprice;
	}

	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}

	@Length(min = 0, max = 1, message = "是否字典长度必须介于 0 和 1 之间")
	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	@Length(min = 0, max = 50, message = "疫苗小类名称长度必须介于 0 和 50 之间")
	@ExcelField(title = "疫苗名称", align = 2, sort = 20)
	public String getVaccName() {
		// return bsVaccineInfo.getVaccineName();
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVaccExpDate() {
		return vaccExpDate;
	}

	public void setVaccExpDate(Date vaccExpDate) {
		this.vaccExpDate = vaccExpDate;
	}

	@Length(min = 0, max = 50, message = "疫苗英文名称长度必须介于 0 和 50 之间")
	public String getCodeall() {
		return codeall;
	}

	public void setCodeall(String codeall) {
		this.codeall = codeall;
	}

	@Length(min = 0, max = 50, message = "疫苗厂家编码长度必须介于 0 和 50 之间")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Length(min = 1, max = 1, message = "剂量字典长度必须介于 1 和 1 之间")
	@ExcelField(title = "剂量", align = 2, sort = 70)
	public String getDose() {
		// return bsVaccineInfo.getDose();
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	@Length(min = 0, max = 1, message = "剂型字典长度必须介于 0 和 1 之间")
	public String getDosetype() {
		return dosetype;
	}

	public void setDosetype(String dosetype) {
		this.dosetype = dosetype;
	}

	public Date getBeginVaccExpDate() {
		return beginVaccExpDate;
	}

	public void setBeginVaccExpDate(Date beginVaccExpDate) {
		this.beginVaccExpDate = beginVaccExpDate;
	}

	public Date getEndVaccExpDate() {
		return endVaccExpDate;
	}

	public void setEndVaccExpDate(Date endVaccExpDate) {
		this.endVaccExpDate = endVaccExpDate;
	}

	public double getOrignprice() {
		return orignprice;
	}

	public void setOrignprice(double orignprice) {
		this.orignprice = orignprice;
	}

	private int check0; // 库存盘点时筛选条件：是否维护0库存

	public int getCheck0() {
		return check0;
	}

	public void setCheck0(int check0) {
		this.check0 = check0;
	}

	public String getBatchType() {
		return batchType;
	}

	public String getcType() {
		return cType;
	}

	public String getRooms() {
		return rooms;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public void setRooms(String rooms) {
		this.rooms = rooms;
	}
	
	

}