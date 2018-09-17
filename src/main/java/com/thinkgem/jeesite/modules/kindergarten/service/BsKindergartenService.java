/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kindergarten.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.kindergarten.entity.BsKindergarten;
import com.thinkgem.jeesite.modules.kindergarten.dao.BsKindergartenDao;

/**
 * 儿童信息中的幼儿园Service
 * @author sen
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class BsKindergartenService extends CrudService<BsKindergartenDao, BsKindergarten> {

	public BsKindergarten get(String id) {
		return super.get(id);
	}
	
	public List<BsKindergarten> findList(BsKindergarten bsKindergarten) {
		return super.findList(bsKindergarten);
	}
	
	public Page<BsKindergarten> findPage(Page<BsKindergarten> page, BsKindergarten bsKindergarten) {
		return super.findPage(page, bsKindergarten);
	}
	
	@Transactional(readOnly = false)
	public void save(BsKindergarten bsKindergarten) {
		super.save(bsKindergarten);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsKindergarten bsKindergarten) {
		super.delete(bsKindergarten);
	}
	
}