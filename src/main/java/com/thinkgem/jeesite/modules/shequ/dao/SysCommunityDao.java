/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shequ.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shequ.entity.SysCommunity;

/**
 * 社区DAO接口
 * @author wang
 * @version 2017-03-21
 */
@MyBatisDao
public interface SysCommunityDao extends CrudDao<SysCommunity> {
	
}