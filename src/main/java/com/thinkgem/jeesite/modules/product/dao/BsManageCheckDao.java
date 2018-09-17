/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.product.entity.BsManageCheck;

/**
 * 每日盘库DAO接口
 * @author fuxin
 * @version 2017-12-28
 */
@MyBatisDao
public interface BsManageCheckDao extends CrudDao<BsManageCheck> {

	/**
	 * 按照日期生成盘点表
	 * @author fuxin
	 * @date 2017年12月29日 上午10:29:29
	 * @description 
	 *		TODO
	 * @param bsManageCheck
	 * @return
	 *
	 */
	List<BsManageCheck> genCheckTable(BsManageCheck bsManageCheck);
}