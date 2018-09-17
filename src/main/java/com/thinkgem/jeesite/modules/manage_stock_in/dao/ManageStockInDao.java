/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manage_stock_in.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.ManageStockIn;

/**
 * 疫苗入库记录DAO接口
 * @author 王德地
 * @version 2017-02-08
 */
@MyBatisDao
public interface ManageStockInDao extends CrudDao<ManageStockIn> {
	
}