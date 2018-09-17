/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enter.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 疫苗生产厂家Entity
 * @author wangdedi
 * @version 2017-03-24
 */
public class SysEnterpriseInfo extends DataEntity<SysEnterpriseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String nameAll;		// 全名称
	private String code;		// code
	private Long indexNum;		// 排序字段
	private Long doIf = 1l;		// 1：可用 0：不可用
	
	public SysEnterpriseInfo() {
		super();
	}

	public SysEnterpriseInfo(String id){
		super(id);
	}

	@Length(min=0, max=50, message="名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="全名称长度必须介于 0 和 100 之间")
	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}
	
	@Length(min=0, max=10, message="code长度必须介于 0 和 10 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Long getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Long indexNum) {
		this.indexNum = indexNum;
	}
	
	public Long getDoIf() {
		return doIf;
	}

	public void setDoIf(Long doIf) {
		this.doIf = doIf;
	}
	
}