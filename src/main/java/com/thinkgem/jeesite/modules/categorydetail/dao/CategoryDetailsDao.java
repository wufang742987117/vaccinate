/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.categorydetail.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.categorydetail.entity.CategoryDetailsEntity;

/**
 * 核查项详细信息DAO接口
 * @author wcy
 * @version 2016-08-11
 */
@MyBatisDao
public interface CategoryDetailsDao extends CrudDao<CategoryDetailsEntity> {
	
}