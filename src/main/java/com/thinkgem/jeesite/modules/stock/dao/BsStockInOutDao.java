/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stock.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stock.entity.BsStockInOut;

/**
 * 进销存月报表DAO接口
 * @author fuxin
 * @version 2017-12-17
 */
@MyBatisDao
public interface BsStockInOutDao extends CrudDao<BsStockInOut> {
	
}