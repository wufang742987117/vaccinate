/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.invoicing.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 进销存统计Entity
 * @author qjzhou
 * @version 2018-01-11
 */
public class BsInvoicing extends DataEntity<BsInvoicing> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 产品id
	private String vaccineid;		// 疫苗编号
	private String vaccName;		// 疫苗小类名称
	private String batchno;		// 批次编号
	private String specification;		// 疫苗规格
	private String manufacturer;		// 疫苗的制造厂商
	private Date vaccExpDate;		// 有效期
	private Long sellprice;		// 出售价格
	private Long costprice;		// 成本价
	private Long oldStorenum;		// 期初库存
	private Long newStorenum;		// 期末库存
	private Long receive;		// 领取/购进（入库）
	private Long consume;		// 损耗（出库）
	private Long apply;		// 使用/接种记录（存储）
	private String status;		// 状态
	private String localcode;		// 操作单位编码
	private Date histroydate;		// 历史时间
	
	private Long sellpriceNew;		// 新产品出售价
	private Long costpriceNew;		// 新产品成本价
	private String[] vaccineidIn;   //疫苗小类编码集合
	/** 查询条件-开始时间 */
	private Date beginTime;
	/** 查询条件-结束时间 */
	private Date endTime;
	
	public BsInvoicing() {
		super();
	}

	public BsInvoicing(String id){
		super(id);
	}

	@Length(min=1, max=32, message="产品id长度必须介于 1 和 32 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=10, message="疫苗编号长度必须介于 0 和 10 之间")
	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	
	@ExcelField(title = "疫苗名称", align = 1, sort = 10)
	@Length(min=0, max=50, message="疫苗小类名称长度必须介于 0 和 50 之间")
	public String getVaccName() {
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}
	
	@ExcelField(title = "批次编号", align = 1, sort = 20)
	@Length(min=0, max=20, message="批次编号长度必须介于 0 和 20 之间")
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title = "疫苗规格", align = 1, sort = 30)
	@Length(min=0, max=20, message="疫苗规格长度必须介于 0 和 20 之间")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@ExcelField(title = "生产厂商", align = 1, sort = 40)
	@Length(min=0, max=50, message="疫苗的制造厂商长度必须介于 0 和 50 之间")
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@ExcelField(title = "有效期至", align = 1, sort = 50)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getVaccExpDate() {
		return vaccExpDate;
	}

	public void setVaccExpDate(Date vaccExpDate) {
		this.vaccExpDate = vaccExpDate;
	}
	
	public Long getSellprice() {
		return sellprice;
	}

	public void setSellprice(Long sellprice) {
		this.sellprice = sellprice;
	}
	
	public Long getCostprice() {
		return costprice;
	}

	public void setCostprice(Long costprice) {
		this.costprice = costprice;
	}
	
	@ExcelField(title = "期初库存", align = 1, sort = 80)
	public Long getOldStorenum() {
		return oldStorenum;
	}

	public void setOldStorenum(Long oldStorenum) {
		this.oldStorenum = oldStorenum;
	}
	
	@ExcelField(title = "期末库存", align = 1, sort = 120)
	public Long getNewStorenum() {
		return newStorenum;
	}

	public void setNewStorenum(Long newStorenum) {
		this.newStorenum = newStorenum;
	}
	
	@ExcelField(title = "领取", align = 1, sort = 90)
	public Long getReceive() {
		return receive;
	}

	public void setReceive(Long receive) {
		this.receive = receive;
	}
	
	@ExcelField(title = "损耗", align = 1, sort = 100)
	public Long getConsume() {
		return consume;
	}

	public void setConsume(Long consume) {
		this.consume = consume;
	}
	
	@ExcelField(title = "售出", align = 1, sort = 110)
	public Long getApply() {
		return apply;
	}

	public void setApply(Long apply) {
		this.apply = apply;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="操作单位编码长度必须介于 0 和 32 之间")
	public String getLocalcode() {
		return localcode;
	}

	public void setLocalcode(String localcode) {
		this.localcode = localcode;
	}
	
	@ExcelField(title = "历史时间", align = 1, sort = 130)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getHistroydate() {
		return histroydate;
	}

	public void setHistroydate(Date histroydate) {
		this.histroydate = histroydate;
	}

	@ExcelField(title = "出售价", align = 1, sort = 60)
	public Long getSellpriceNew() {
		return sellpriceNew;
	}

	public void setSellpriceNew(Long sellpriceNew) {
		this.sellpriceNew = sellpriceNew;
	}

	@ExcelField(title = "成本价", align = 1, sort = 70)
	public Long getCostpriceNew() {
		return costpriceNew;
	}

	public void setCostpriceNew(Long costpriceNew) {
		this.costpriceNew = costpriceNew;
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
	
	public String[] getVaccineidIn() {
		if(vaccineidIn == null || vaccineidIn.length == 0){
			return null;
		}
		return vaccineidIn;
	}

	public void setVaccineidIn(String[] vaccineidIn) {
		this.vaccineidIn = vaccineidIn;
	}
}