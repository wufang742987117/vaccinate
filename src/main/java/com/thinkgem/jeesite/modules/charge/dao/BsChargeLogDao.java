/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.charge.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.charge.entity.BsChargeLog;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;

/**
 * 门诊收费DAO接口
 * 
 * @author zhaojing
 * @version 2017-10-26
 */
@MyBatisDao
public interface BsChargeLogDao extends CrudDao<BsChargeLog> {

	/**
	 * 发票总览数据
	 * @author zhouqj
	 * @date 2018年2月5日 下午5:08:56
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public List<BsChargeLog> findListOverview(BsChargeLog bsChargeLog);

	/**
	 * 发票明细数据
	 * @author zhouqj
	 * @date 2018年2月5日 下午5:08:59
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public List<BsChargeLog> findListDetails(BsChargeLog bsChargeLog);
	
	/**
	 * 查询业务流水号所属记录
	 * @author zhouqj
	 * @date 2018年1月16日 下午10:01:42
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public String getPatientIdByTicket(BsChargeLog bsChargeLog);
	
	/**  
	 * zhaojing
	 * 2017年11月2日  下午1:24:25
	 * 更新发票状态
	 */  
	public int updateTicketStatus(BsChargeLog bsChargeLog);
	
	/**
	 * 修改开票状态
	 * @author zhouqj
	 * @date 2018年1月22日 下午3:14:29
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public int updateBilling(BsChargeLog bsChargeLog);
	
	/**  
	* zhaojing
	* 2017年11月2日  上午11:28:50
	* 获得当前业务流水号
	*/  
	public int getBillNumCurral(@Param("localCode") String localCode,@Param("createById") String createById);

	/**  
	* zhaojing
	* 2017年11月2日  上午11:23:59
	* 获得疫苗选择列表
	* @param keyword
	*/  
	public List<Map<String, String>> getVaccList(@Param("localCode") String localCode);
	
	/**  
	* zhaojing
	* 2017年11月2日  上午11:24:55
	* 按照id，批号，厂家查询疫苗相关信息
	* @param map 包含参数编号，批号，厂家
	*/  
	public Map<String, String> getVaccById(Map<String, String> map);
	
	/**
	 * 查询业务流水号是否存在
	 * @author zhouqj
	 * @date 2018年2月7日 上午10:18:11
	 * @description 
	 *		TODO
	 * @param billNum
	 * @param string 
	 * @return
	 *
	 */
	public int getBillNumCount(@Param("billNum") String billNum,@Param("localCode") String localCode);
	
	/**
	 * 新增业务流水号绑定用户
	 * @author zhouqj
	 * @date 2018年2月7日 上午10:56:31
	 * @description 
	 *		TODO
	 * @param billNum
	 * @param localCode
	 * @param createById
	 *
	 */
	public void insertBillNum(@Param("billNum") Integer billNum,@Param("localCode") String localCode,@Param("createById") String createById);
	
	/**
	 * 根据前台输入发票编号修改发票编号
	 * @author yangjian
	 * @date 2018年1月26日下午16:17:14
	 * @description 
	 *		TODO
	 * @param billNum
	 * @param localCode 
	 * @param createById 
	 */
	public void updateBillNum(@Param("billNum") Integer billNum,@Param("localCode") String localCode,@Param("createById") String createById);
	
	/**
	 * 查询改单位用户是否存在业务流水号
	 * @author zhouqj
	 * @date 2018年2月7日 上午10:47:48
	 * @description 
	 *		TODO
	 * @param localCode
	 * @param createById
	 *
	 */
	public int getBillNumByUserCount(@Param("localCode") String localCode,@Param("createById") String createById);

	/**
	 * 修改用户开票权限
	 * @author zhouqj
	 * @date 2018年2月7日 上午11:04:47
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public int updateUserBillingStatus(BsChargeLog bsChargeLog);
	
	/**  
	* zhaojing
	* 2017年11月2日  上午11:27:21
	* 业务流水号+1，不返回业务流水号
	*/  
	public int getBillNumNextval(@Param("localCode") String localCode,@Param("createById") String createById);
	
	/**
	 * chenming
	 * 根据业务流水号获取疫苗信息
	 */
	public List<BsChargeLog> findChargeCase(BsChargeLog bsChargeLog);
	
	/**
	 * chenming
	 * 更新发票，保存退费信息
	 */
	public int updateCharge(BsChargeLog bsChargeLog);
	
	/**
	 * 更新发票退款金额
	 * @author zhouqj
	 * @date 2018年1月18日 下午8:42:47
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	public void updateChargeList(BsChargeLog bsChargeLog);

	/**
	 * 更改未开票发票业务流水号
	 * @author zhouqj
	 * @date 2018年2月7日 下午1:29:05
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	public void updateChargeByBillNum(BsChargeLog bsChargeLog);
	
	/**
	 * chenming
	 * 统计疫苗使用详情
	 */
	public List<BsChargeLog> findChargeVaccine(BsChargeLog bsChargeLog);
	
	/**
	 * 定时查询前一天发票记录 每天00:03
	 * @author yangjian
	 * @date 2018年1月25日 下午15:30
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	public List<BsChargeLog> findDataListChargeEveryday();

	/**
	 * 疫苗数据联动接口
	 * @author zhouqj
	 * @date 2018年3月1日 下午4:28:05
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findViewList(BsManageProduct bsManageProduct);
	
	/**
	 * 查询打印发票
	 * @author zhouqj
	 * @date 2018年3月7日 
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return list
	 *
	 */
	public List<BsChargeLog> findPrintList(BsChargeLog bsChargeLog);

}