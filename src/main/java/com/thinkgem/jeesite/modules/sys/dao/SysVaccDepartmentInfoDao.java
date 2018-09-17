/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysVaccDepartmentInfo;

/**
 * 可是信息管理DAO接口
 * @author yangjian
 * @version 2018-02-27
 */
@MyBatisDao
public interface SysVaccDepartmentInfoDao extends CrudDao<SysVaccDepartmentInfo> {
	
}