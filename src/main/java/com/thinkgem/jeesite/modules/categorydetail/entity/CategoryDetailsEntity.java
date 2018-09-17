/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.categorydetail.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 核查项详细信息Entity
 * @author wcy
 * @version 2016-08-11
 */
public class CategoryDetailsEntity extends DataEntity<CategoryDetailsEntity> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 检查项分类名
	private String isEnable;		// 是否启用（1是0否）
	private String sort;		// 排序
	private String categoryId;
	private String content;//核查内容
	private String mainPoints;//核查要点
	private String methodNote;
	private String good;
	private String general;
	private String poor;
	private String recordMainPoints;
	private String reference;
	private String remark;


	public CategoryDetailsEntity() {
		super();
	}

	public CategoryDetailsEntity(String id){
		super(id);
	}

	@Length(min=1, max=30, message="检查项分类名长度必须介于 1 和 30 之间")
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
	
	@Length(min=1, max=10, message="排序长度必须介于 1 和 10 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMainPoints() {
		return mainPoints;
	}

	public void setMainPoints(String mainPoints) {
		this.mainPoints = mainPoints;
	}

	public String getMethodNote() {
		return methodNote;
	}

	public void setMethodNote(String methodNote) {
		this.methodNote = methodNote;
	}

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public String getPoor() {
		return poor;
	}

	public void setPoor(String poor) {
		this.poor = poor;
	}

	public String getRecordMainPoints() {
		return recordMainPoints;
	}

	public void setRecordMainPoints(String recordMainPoints) {
		this.recordMainPoints = recordMainPoints;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}