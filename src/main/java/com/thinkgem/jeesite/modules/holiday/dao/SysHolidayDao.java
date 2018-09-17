/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.holiday.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.holiday.entity.SysHoliday;

/**
 * 节假日DAO接口
 * @author fuxin
 * @version 2017-09-23
 */
@MyBatisDao
public interface SysHolidayDao extends CrudDao<SysHoliday> {
	
}