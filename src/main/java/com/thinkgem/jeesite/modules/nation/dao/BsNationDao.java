/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nation.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.nation.entity.BsNation;

/**
 * 民族DAO接口
 * @author wang
 * @version 2017-03-22
 */
@MyBatisDao
public interface BsNationDao extends CrudDao<BsNation> {
	
}