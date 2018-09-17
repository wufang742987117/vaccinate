/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysBadsql;

/**
 * sql上报失败DAO接口
 * @author liyuan
 * @version 2018-01-19
 */
@MyBatisDao
public interface SysBadsqlDao extends CrudDao<SysBadsql> {
	
}