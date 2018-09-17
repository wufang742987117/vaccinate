/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shequ.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shequ.entity.SysCommunity;
import com.thinkgem.jeesite.modules.shequ.dao.SysCommunityDao;

/**
 * 社区Service
 * @author wang
 * @version 2017-03-21
 */
@Service
@Transactional(readOnly = true)
public class SysCommunityService extends CrudService<SysCommunityDao, SysCommunity> {

	public SysCommunity get(String id) {
		return super.get(id);
	}
	
	public List<SysCommunity> findList(SysCommunity sysCommunity) {
		return super.findList(sysCommunity);
	}
	
	public Page<SysCommunity> findPage(Page<SysCommunity> page, SysCommunity sysCommunity) {
		return super.findPage(page, sysCommunity);
	}
	
	@Transactional(readOnly = false)
	public void save(SysCommunity sysCommunity) {
		super.save(sysCommunity);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysCommunity sysCommunity) {
		super.delete(sysCommunity);
	}
	
}