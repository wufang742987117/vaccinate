/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enter.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enter.entity.SysEnterpriseInfo;
import com.thinkgem.jeesite.modules.enter.dao.SysEnterpriseInfoDao;

/**
 * 疫苗生产厂家Service
 * @author wangdedi
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class SysEnterpriseInfoService extends CrudService<SysEnterpriseInfoDao, SysEnterpriseInfo> {

	public SysEnterpriseInfo get(String id) {
		return super.get(id);
	}
	
	public List<SysEnterpriseInfo> findList(SysEnterpriseInfo sysEnterpriseInfo) {
		return super.findList(sysEnterpriseInfo);
	}
	
	public Page<SysEnterpriseInfo> findPage(Page<SysEnterpriseInfo> page, SysEnterpriseInfo sysEnterpriseInfo) {
		return super.findPage(page, sysEnterpriseInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SysEnterpriseInfo sysEnterpriseInfo) {
		super.save(sysEnterpriseInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysEnterpriseInfo sysEnterpriseInfo) {
		super.delete(sysEnterpriseInfo);
	}

	public void update(SysEnterpriseInfo sysEnterpriseInfo) {
		dao.update(sysEnterpriseInfo);
		
	}
	
}