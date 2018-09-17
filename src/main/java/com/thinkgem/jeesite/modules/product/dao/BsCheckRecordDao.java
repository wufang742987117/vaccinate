/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.product.entity.BsCheckRecord;

/**
 * 库存盘点记录DAO接口
 * @author fuxin
 * @version 2017-12-28
 */
@MyBatisDao
public interface BsCheckRecordDao extends CrudDao<BsCheckRecord> {
	
}