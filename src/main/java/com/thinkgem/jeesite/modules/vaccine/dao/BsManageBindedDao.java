/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageBinded;

/**
 * 联合疫苗替代原则DAO接口
 * @author fuxin
 * @version 2017-09-29
 */
@MyBatisDao
public interface BsManageBindedDao extends CrudDao<BsManageBinded> {
	
}