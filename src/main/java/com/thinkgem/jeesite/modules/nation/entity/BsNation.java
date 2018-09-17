/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nation.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 民族Entity
 * @author wang
 * @version 2017-03-22
 */
public class BsNation extends DataEntity<BsNation> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// code
	private String name;		// 民族名称
	private String sort;		// 排序
	
	public BsNation() {
		super();
	}

	public BsNation(String id){
		super(id);
	}

	@Length(min=0, max=50, message="code长度必须介于 0 和 50 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=50, message="民族名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="排序长度必须介于 0 和 50 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}