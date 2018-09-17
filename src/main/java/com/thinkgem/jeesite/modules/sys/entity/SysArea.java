/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 地区管理Entity
 * @author 王德地
 * @version 2017-02-13
 */
public class SysArea extends DataEntity<SysArea> {
	
	private static final long serialVersionUID = 1L;
	private SysArea parent;		// parent_id
	private String parentIds;		// parent_ids
	private String name;		// name
	private Integer sort;		// sort
	private String code;		// code
	private String type;		// type
	
	public SysArea() {
		super();
	}

	public SysArea(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="parent_id不能为空")
	public SysArea getParent() {
		return parent;
	}

	public void setParent(SysArea parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="parent_ids长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=100, message="name长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="sort不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=100, message="code长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=1, message="type长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}