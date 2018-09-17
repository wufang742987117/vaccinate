/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;

/**
 * 告知书Entity
 * @author yangjian
 * @version 2018-01-22
 */
public class CmsDisclosure extends DataEntity<CmsDisclosure> {
	
	private static final long serialVersionUID = 1L;
	//private String vid;		// 疫苗外键id
	private BsManageVaccine vaccine;
	private String context;		// context
	
	public CmsDisclosure() {
		super();
	}
	
	public CmsDisclosure(String id){
		super(id);
	}

/*	@Length(min=1, max=32, message="疫苗外键id长度必须介于 1 和 32 之间")
	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}*/
	
	public BsManageVaccine getVaccine() {
		return vaccine;
	}

	public void setVaccine(BsManageVaccine vaccine) {
		this.vaccine = vaccine;
	}
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
}