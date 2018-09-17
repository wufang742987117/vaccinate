package com.thinkgem.jeesite.modules.reconciliation.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reconciliation.dao.ReconciliationStockDao;
import com.thinkgem.jeesite.modules.reconciliation.entity.ReconciliationStock;

/**
 * 对帐管理
 * @author zhouqj
 * @date 2017年5月17日 上午11:20:59
 * @description 
 *		TODO
 */
@Service
@Transactional(readOnly = true)
public class ReconciliationStockService extends
		CrudService<ReconciliationStockDao, ReconciliationStock> {

	/**
	 * 接种完成金额统计（登记台）
	 * @author zhouqj
	 * @date 2017年5月17日 下午3:53:32
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockQuery0(
			ReconciliationStock reconciliationStock) {
		return dao.reconciliationStockQuery0(reconciliationStock);
	}
	
	/**
	 * 接种完成金额统计（微信）
	 * @author zhouqj
	 * @date 2017年5月17日 下午3:53:32
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockQuery1(
			ReconciliationStock reconciliationStock) {
		return dao.reconciliationStockQuery1(reconciliationStock);
	}
	
	/**
	 * 接种完成金额统计（一体机）
	 * @author zhouqj
	 * @date 2017年5月17日 下午3:53:32
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockQuery2(
			ReconciliationStock reconciliationStock) {
		return dao.reconciliationStockQuery2(reconciliationStock);
	}

	/**
	 * 疫苗接种完成统计
	 * @author zhouqj
	 * @date 2017年5月18日 上午10:23:28
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockCount1(
			ReconciliationStock reconciliationStock) {
		return dao.reconciliationStockCount1(reconciliationStock);
	} 
	
	/**
	 * 疫苗报损统计
	 * @author zhouqj
	 * @date 2017年5月18日 上午10:23:33
	 * @description 
	 *		TODO
	 * @param reconciliationStock
	 * @return
	 *
	 */
	public List<ReconciliationStock> reconciliationStockCount2(
			ReconciliationStock reconciliationStock) {
		return dao.reconciliationStockCount2(reconciliationStock);
	}
	
	//需要注意的是：月份是从0开始的，比如说如果输入5的话，实际上显示的是4月份的最后一天，千万不要搞错了哦  
    public static String getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
       return  new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime()) + "23:59:59";  
    }   
    public static String getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
       return   new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime()) + "00:00:00";
    }

}
