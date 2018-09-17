/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.department.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;

/**
 * 预防门诊编码DAO接口
 * @author wangdedi
 * @version 2017-04-08
 */
@MyBatisDao
public interface SysVaccDepartmentDao extends CrudDao<SysVaccDepartment> {
	
}