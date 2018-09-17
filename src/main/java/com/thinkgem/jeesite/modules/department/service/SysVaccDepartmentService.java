/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.department.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;
import com.thinkgem.jeesite.modules.department.dao.SysVaccDepartmentDao;

/**
 * 预防门诊编码Service
 * @author wangdedi
 * @version 2017-04-08
 */
@Service
@Transactional(readOnly = true)
public class SysVaccDepartmentService extends CrudService<SysVaccDepartmentDao, SysVaccDepartment> {

	public SysVaccDepartment get(String id) {
		return super.get(id);
	}
	
	public List<SysVaccDepartment> findList(SysVaccDepartment sysVaccDepartment) {
		return super.findList(sysVaccDepartment);
	}
	
	public Page<SysVaccDepartment> findPage(Page<SysVaccDepartment> page, SysVaccDepartment sysVaccDepartment) {
		return super.findPage(page, sysVaccDepartment);
	}
	
	@Transactional(readOnly = false)
	public void save(SysVaccDepartment sysVaccDepartment) {
		super.save(sysVaccDepartment);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysVaccDepartment sysVaccDepartment) {
		super.delete(sysVaccDepartment);
	}
	
}