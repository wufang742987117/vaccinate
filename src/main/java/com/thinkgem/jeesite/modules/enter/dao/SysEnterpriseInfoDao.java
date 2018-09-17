/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enter.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enter.entity.SysEnterpriseInfo;

/**
 * 疫苗生产厂家DAO接口
 * @author wangdedi
 * @version 2017-03-24
 */
@MyBatisDao
public interface SysEnterpriseInfoDao extends CrudDao<SysEnterpriseInfo> {
	
}