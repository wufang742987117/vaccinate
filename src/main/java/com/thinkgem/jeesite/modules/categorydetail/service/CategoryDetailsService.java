/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.categorydetail.service;

import java.util.List;

import com.thinkgem.jeesite.modules.categorydetail.dao.CategoryDetailsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.categorydetail.entity.CategoryDetailsEntity;

/**
 * 核查项详细信息Service
 * @author wcy
 * @version 2016-08-11
 */
@Service
@Transactional(readOnly = true)
public class CategoryDetailsService extends CrudService<CategoryDetailsDao, CategoryDetailsEntity> {

	
	public CategoryDetailsEntity get(String id) {
		CategoryDetailsEntity examineItemCategory = super.get(id);
		return examineItemCategory;
	}
	
	public List<CategoryDetailsEntity> findList(CategoryDetailsEntity examineItemCategory) {
		return super.findList(examineItemCategory);
	}
	
	public Page<CategoryDetailsEntity> findPage(Page<CategoryDetailsEntity> page, CategoryDetailsEntity examineItemCategory) {
		return super.findPage(page, examineItemCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(CategoryDetailsEntity examineItemCategory) {
		super.save(examineItemCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(CategoryDetailsEntity examineItemCategory) {
		super.delete(examineItemCategory);
	}
	
}