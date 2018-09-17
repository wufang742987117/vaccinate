/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWorkingHours;

/**
 * 工作时间段DAO接口
 * @author liyuan
 * @version 2018-01-26
 */
@MyBatisDao
public interface SysWorkingHoursDao extends CrudDao<SysWorkingHours> {
	
}