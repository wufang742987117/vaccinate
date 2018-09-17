/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.procategory.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.procategory.entity.ProductType;

/**
 * 产品类别DAO接口
 * @author 1
 * @version 2016-08-03
 */
@MyBatisDao
public interface ProductTypeDao extends CrudDao<ProductType> {
	
	public List<ProductType> findtree();
}