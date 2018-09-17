/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.nation.entity.BsNation;
import com.thinkgem.jeesite.modules.nation.dao.BsNationDao;

/**
 * 民族Service
 * @author wang
 * @version 2017-03-22
 */
@Service
@Transactional(readOnly = true)
public class BsNationService extends CrudService<BsNationDao, BsNation> {

	public BsNation get(String id) {
		return super.get(id);
	}
	
	public List<BsNation> findList(BsNation bsNation) {
		return super.findList(bsNation);
	}
	
	public Page<BsNation> findPage(Page<BsNation> page, BsNation bsNation) {
		return super.findPage(page, bsNation);
	}
	
	@Transactional(readOnly = false)
	public void save(BsNation bsNation) {
		super.save(bsNation);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsNation bsNation) {
		super.delete(bsNation);
	}
	
}