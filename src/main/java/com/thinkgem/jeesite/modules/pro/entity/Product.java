/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.procategory.entity.ProductType;

/**
 * 产品Entity
 * @author 1
 * @version 2016-08-01
 */
public class Product extends DataEntity<Product> {
	
	private static final long serialVersionUID = 1L;
	private ProductType productTypeId;		// 产品类别id
	private String code;		// 产品编号
	private String name;		// 产品名称
	private String isEnable;		// 是否启用（1是0否）
	private String proName;    // 产品类型
	private String pid;//父级
	


	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Product() {
		super();
	}

	public Product(String id){
		super(id);
	}

	
	public ProductType getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(ProductType productTypeId) {
		this.productTypeId = productTypeId;
	}

	@Length(min=1, max=30, message="产品编号长度必须介于 1 和 30 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=30, message="产品名称长度必须介于 1 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=1, message="是否启用（1是0否）长度必须介于 1 和 1 之间")
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
}