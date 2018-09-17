package com.thinkgem.jeesite.modules.reconciliation.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reconciliation.entity.ReconciliationStock;

/**
 * 对帐管理
 * @author zhouqj
 * @date 2017年5月17日 上午11:16:28
 * @description 
 *		TODO
 */
@MyBatisDao
public interface ReconciliationStockDao extends CrudDao<ReconciliationStock> {

	/**
	 * 接种完成金额统计（登记台）
	 * @author zhouqj
	 * @date 2017年5月17日 下午3:53:02
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockQuery0(
			ReconciliationStock reconciliationStock);
	
	/**
	 * 接种完成金额统计（微信）
	 * @author zhouqj
	 * @date 2017年5月17日 下午3:53:02
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockQuery1(
			ReconciliationStock reconciliationStock);
	
	/**
	 * 接种完成金额统计（一体机）
	 * @author zhouqj
	 * @date 2017年5月17日 下午3:53:02
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockQuery2(
			ReconciliationStock reconciliationStock);

	/**
	 * 疫苗接种完成统计
	 * @author zhouqj
	 * @date 2017年5月18日 上午10:29:15
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockCount1(
			ReconciliationStock reconciliationStock);

	/**
	 * 疫苗报损统计
	 * @author zhouqj
	 * @date 2017年5月18日 上午10:29:21
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockCount2(
			ReconciliationStock reconciliationStock);
	

}
