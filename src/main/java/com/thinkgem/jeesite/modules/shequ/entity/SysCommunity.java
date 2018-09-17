/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shequ.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 社区Entity
 * @author wang
 * @version 2017-03-21
 */
public class SysCommunity extends DataEntity<SysCommunity> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 键
	private String name;		// 名称
	private int count;    //人数
	
	public SysCommunity() {
		super();
	}

	public SysCommunity(String id){
		super(id);
	}

	@Length(min=0, max=50, message="键长度必须介于 0 和 50 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}