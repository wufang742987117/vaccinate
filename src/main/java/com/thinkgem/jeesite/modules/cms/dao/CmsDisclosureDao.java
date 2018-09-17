/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.CmsDisclosure;

/**
 * 告知书DAO接口
 * @author yangjian
 * @version 2018-01-22
 */
@MyBatisDao
public interface CmsDisclosureDao extends CrudDao<CmsDisclosure> {
	void saveModel(CmsDisclosure cmsDisclosure);
}