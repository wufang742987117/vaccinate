/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 联合疫苗替代原则Entity
 * @author fuxin
 * @version 2017-09-29
 */
public class BsManageBinded extends DataEntity<BsManageBinded> {
	
	public static final String BIND_YES = "1";
	public static final String BIND_NO = "0";
	
	private static final long serialVersionUID = 1L;
	private String bindVaccId;		// 联合疫苗
	private Long bindVaccPin;		// 联合疫苗剂次
	private String vaccId;		// 被联合疫苗id
	private Long vaccPin;		// 被联合疫苗剂次
	private Long defaultVaccId;		// 默认使用疫苗
	private String level;		//级别
	private String bind;		//是否联合疫苗 1是 0不是
	
	/*业务字段*/
	private String defaultName; //默认疫苗名称
	private String bindName; //被联合疫苗名称
	
	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public String getBindName() {
		return bindName;
	}

	public void setBindName(String bindName) {
		this.bindName = bindName;
	}
	/*业务字段*/
	
	private BsManageVaccine vaccine;	//被联合疫苗信息
	private String orderBy;
	
	public BsManageBinded() {
		super();
	}

	public BsManageBinded(String id){
		super(id);
	}

	@Length(min=1, max=10, message="联合疫苗长度必须介于 1 和 10 之间")
	public String getBindVaccId() {
		return bindVaccId;
	}

	public void setBindVaccId(String bindVaccId) {
		this.bindVaccId = bindVaccId;
	}
	
	@NotNull(message="联合疫苗剂次不能为空")
	public Long getBindVaccPin() {
		return bindVaccPin;
	}

	public void setBindVaccPin(Long bindVaccPin) {
		this.bindVaccPin = bindVaccPin;
	}
	
	@Length(min=1, max=10, message="被联合疫苗id长度必须介于 1 和 10 之间")
	public String getVaccId() {
		return vaccId;
	}

	public void setVaccId(String vaccId) {
		this.vaccId = vaccId;
	}
	
	@NotNull(message="被联合疫苗剂次不能为空")
	public Long getVaccPin() {
		return vaccPin;
	}

	public void setVaccPin(Long vaccPin) {
		this.vaccPin = vaccPin;
	}
	
	public Long getDefaultVaccId() {
		return defaultVaccId;
	}

	public void setDefaultVaccId(Long defaultVaccId) {
		this.defaultVaccId = defaultVaccId;
	}

	public BsManageVaccine getVaccine() {
		return vaccine;
	}

	public void setVaccine(BsManageVaccine vaccine) {
		this.vaccine = vaccine;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}
	
	
}