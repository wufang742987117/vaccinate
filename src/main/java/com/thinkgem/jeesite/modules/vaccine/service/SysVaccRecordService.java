/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vaccine.entity.SysVaccRecord;
import com.thinkgem.jeesite.modules.vaccine.dao.SysVaccRecordDao;

/**
 * 疫苗使用记录Service
 * @author fuxin
 * @version 2017-04-22
 */
@Service
@Transactional(readOnly = true)
public class SysVaccRecordService extends CrudService<SysVaccRecordDao, SysVaccRecord> {

	public SysVaccRecord get(String id) {
		return super.get(id);
	}
	
	public List<SysVaccRecord> findList(SysVaccRecord sysVaccRecord) {
		return super.findList(sysVaccRecord);
	}
	
	public Page<SysVaccRecord> findPage(Page<SysVaccRecord> page, SysVaccRecord sysVaccRecord) {
		return super.findPage(page, sysVaccRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(SysVaccRecord sysVaccRecord) {
		super.save(sysVaccRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysVaccRecord sysVaccRecord) {
		super.delete(sysVaccRecord);
	}

	public void insert(SysVaccRecord svr) {
		dao.insert(svr);
		
	}
	
}