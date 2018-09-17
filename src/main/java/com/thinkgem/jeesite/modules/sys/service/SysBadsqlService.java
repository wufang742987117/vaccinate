/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysBadsql;
import com.thinkgem.jeesite.modules.sys.dao.SysBadsqlDao;

/**
 * sql上报失败Service
 * @author liyuan
 * @version 2018-01-19
 */
@Service
@Transactional(readOnly = true)
public class SysBadsqlService extends CrudService<SysBadsqlDao, SysBadsql> {

	public SysBadsql get(String id) {
		return super.get(id);
	}
	
	public List<SysBadsql> findList(SysBadsql sysBadsql) {
		return super.findList(sysBadsql);
	}
	
	public Page<SysBadsql> findPage(Page<SysBadsql> page, SysBadsql sysBadsql) {
		return super.findPage(page, sysBadsql);
	}
	
	@Transactional(readOnly = false)
	public void save(SysBadsql sysBadsql) {
		super.save(sysBadsql);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysBadsql sysBadsql) {
		super.delete(sysBadsql);
	}
	
}