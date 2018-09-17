/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yiyuan.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 信息Entity
 * @author 王德地
 * @version 2017-03-21
 */
public class SysBirthHospital extends DataEntity<SysBirthHospital> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 医院编码
	private String name;		// 医院名称
	private Long indexNum;		// 排序
	private String localCodeLike;		// 排序
	
	public SysBirthHospital() {
		super();
	}

	public SysBirthHospital(String id){
		super(id);
	}

	@Length(min=0, max=50, message="医院编码长度必须介于 0 和 50 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="医院名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Long indexNum) {
		this.indexNum = indexNum;
	}

	public String getLocalCodeLike() {
		return localCodeLike;
	}

	public void setLocalCodeLike(String localCodeLike) {
		this.localCodeLike = localCodeLike;
	}
	
	
	
}