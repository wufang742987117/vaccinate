package com.thinkgem.jeesite.modules.product.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class RepertorySum extends DataEntity<RepertorySum>{	
	private static final long serialVersionUID = 1L;
	private Long storenum;		// 库存
	private String name;		// 疫苗名称
	private String batchno;		// 批次编号
	
	
	
	
	@ExcelField(align=2, title="库存", sort = 30 )
	public Long getStorenum() {
		return storenum;
	}
	public void setStorenum(Long storenum) {
		this.storenum = storenum;
	}
	@ExcelField(align=2, title="疫苗名称", sort = 10 )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(align=2, title="疫苗名称", sort = 20 )
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	
	
}
