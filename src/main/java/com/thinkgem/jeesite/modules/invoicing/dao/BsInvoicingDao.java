/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.invoicing.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.invoicing.entity.BsInvoicing;

/**
 * 进销存统计DAO接口
 * @author qjzhou
 * @version 2018-01-11
 */
@MyBatisDao
public interface BsInvoicingDao extends CrudDao<BsInvoicing> {

	/**
	 * 进销存实时统计，带分页
	 * @author zhouqj
	 * @date 2018年1月12日 上午10:28:34
	 * @description 
	 *		TODO
	 * @param bsInvoicing
	 * @return
	 *
	 */
	public List<BsInvoicing> findListSql(BsInvoicing bsInvoicing);
	
}