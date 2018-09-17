/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cms.entity.CmsDisclosure;
import com.thinkgem.jeesite.modules.cms.dao.CmsDisclosureDao;

/**
 * 告知书Service
 * @author yangjian
 * @version 2018-01-22
 */
@Service
@Transactional(readOnly = true)
public class CmsDisclosureService extends CrudService<CmsDisclosureDao, CmsDisclosure> {

	public CmsDisclosure get(String id) {
		return super.get(id);
	}
	
	public List<CmsDisclosure> findList(CmsDisclosure cmsDisclosure) {
		return super.findList(cmsDisclosure);
	}
	
	public Page<CmsDisclosure> findPage(Page<CmsDisclosure> page, CmsDisclosure cmsDisclosure) {
		return super.findPage(page, cmsDisclosure);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsDisclosure cmsDisclosure) {
		super.save(cmsDisclosure);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsDisclosure cmsDisclosure) {
		super.delete(cmsDisclosure);
	}
	
}