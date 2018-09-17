/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vaccine.entity.SysVaccRecord;

/**
 * 疫苗使用记录DAO接口
 * @author fuxin
 * @version 2017-04-22
 */
@MyBatisDao
public interface SysVaccRecordDao extends CrudDao<SysVaccRecord> {
	
}