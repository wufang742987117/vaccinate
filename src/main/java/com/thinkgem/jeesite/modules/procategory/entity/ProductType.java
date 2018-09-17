/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.procategory.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 产品类别Entity
 * @author 1
 * @version 2016-08-03
 */
public class ProductType extends TreeEntity<ProductType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 类别名称
	private Long pId;		// 父id
	private String isEnable;		// 是否启用（1是0否）
	
	public ProductType() {
		super();
	}

	public ProductType(String id){
		super(id);
	}

	@Length(min=1, max=30, message="类别名称长度必须介于 1 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="父id不能为空")
	public Long getPId() {
		return pId;
	}

	public void setPId(Long pId) {
		this.pId = pId;
	}
	
	@Length(min=1, max=1, message="是否启用（1是0否）长度必须介于 1 和 1 之间")
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public ProductType getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParent(ProductType parent) {
		// TODO Auto-generated method stub
		
	}
	
}