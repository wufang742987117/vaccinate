/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysVaccDepartmentInfo;
import com.thinkgem.jeesite.modules.sys.dao.SysVaccDepartmentInfoDao;

/**
 * 可是信息管理Service
 * @author yangjian
 * @version 2018-02-27
 */
@Service
@Transactional(readOnly = true)
public class SysVaccDepartmentInfoService extends CrudService<SysVaccDepartmentInfoDao, SysVaccDepartmentInfo> {

	public SysVaccDepartmentInfo get(String id) {
		return super.get(id);
	}
	
	public List<SysVaccDepartmentInfo> findList(SysVaccDepartmentInfo sysVaccDepartmentInfo) {
		return super.findList(sysVaccDepartmentInfo);
	}
	
	public Page<SysVaccDepartmentInfo> findPage(Page<SysVaccDepartmentInfo> page, SysVaccDepartmentInfo sysVaccDepartmentInfo) {
		return super.findPage(page, sysVaccDepartmentInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SysVaccDepartmentInfo sysVaccDepartmentInfo) {
		super.save(sysVaccDepartmentInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysVaccDepartmentInfo sysVaccDepartmentInfo) {
		super.delete(sysVaccDepartmentInfo);
	}
	
}