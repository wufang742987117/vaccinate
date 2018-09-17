/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inoculate.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;

/**
 * 预约排号表
 * 
 * @author fuxin
 * @date 2017年9月24日 下午2:29:30
 * @description TODO
 */
public class Reserve extends DataEntity<Reserve> {

	private static final long serialVersionUID = 1L;
	private String childId; // 儿童id
	private ChildBaseinfo child;
	private String vaccId; // 疫苗id
	private BsManageVaccine vaccinate;
	private String vaccName; // 疫苗名称
	private String pid; // 产品id
	private BsManageProduct product;
	private String nid; // 计划id
	private BsManageVaccinenum num;
	private String context; // 风险告知书id
	private String vaccType; // 疫苗种类第一类、第二类

	public Reserve() {
		super();
	}

	public Reserve(String id) {
		super(id);
	}

	public String getChildId() {
		return childId;
	}

	public String getVaccId() {
		return vaccId;
	}

	public String getVaccName() {
		return vaccName;
	}

	public String getPid() {
		return pid;
	}

	public String getNid() {
		return nid;
	}

	public String getContext() {
		return context;
	}

	public String getVaccType() {
		return vaccType;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public void setVaccId(String vaccId) {
		this.vaccId = vaccId;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setVaccType(String vaccType) {
		this.vaccType = vaccType;
	}

	public ChildBaseinfo getChild() {
		return child;
	}

	public BsManageVaccine getVaccinate() {
		return vaccinate;
	}

	public BsManageProduct getProduct() {
		return product;
	}

	public BsManageVaccinenum getNum() {
		return num;
	}

	public void setChild(ChildBaseinfo child) {
		this.child = child;
	}

	public void setVaccinate(BsManageVaccine vaccinate) {
		this.vaccinate = vaccinate;
	}

	public void setProduct(BsManageProduct product) {
		this.product = product;
	}

	public void setNum(BsManageVaccinenum num) {
		this.num = num;
	}

}