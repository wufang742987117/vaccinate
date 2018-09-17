/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.BCV;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;

/**
 * 疫苗接种管理DAO接口
 * @author 王德地
 * @version 2017-02-07
 */
@MyBatisDao
public interface ChildVaccinaterecordDao extends CrudDao<ChildVaccinaterecord> {

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月28日 上午11:38:04
	 * @description 
	 *	根据儿童ID查询儿童最近的一次接种时间，若等于今天则取第二个，第三个......
	 * @param record
	 * @return
	 *
	 */
	public List<ChildVaccinaterecord> date(ChildVaccinaterecord record);

	/**
	 * 查看签字内容
	 * @author wangdedi
	 * @date 2017年4月27日 下午8:50:37
	 * @description 
	 *		TODO
	 * @param signature
	 * @return
	 *
	 */
	public String signature(@Param("signature")String signature, @Param("localCode") String localCode);

	public void insertBcv(BCV bc);

	/**
	 * 签字插入
	 * @author zhouqj
	 * @date 2017年5月23日 下午2:18:17
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 *
	 */
	public void insertSignature(ChildVaccinaterecord childVaccinaterecord);
	
	public void insertSignatures(ChildVaccinaterecord childVaccinaterecord);
	
	public void updateSignature(ChildVaccinaterecord childVaccinaterecord);

	/**
	 * 获取最后一剂活苗时间
	 * @author fuxin
	 * @date 2017年7月28日 下午8:05:00
	 * @description 
	 *		TODO
	 * @param childid
	 * @return
	 *
	 */
	public int getLastLiveDays(ChildBaseinfo childid);
	

	/**
	 * 查询该记录签字是否存在
	 * @author zhouqj
	 * @date 2017年9月8日 下午7:22:12
	 * @description 
	 *		TODO
	 * @param record
	 * @return
	 *
	 */
	public int querySignature(ChildVaccinaterecord record);

	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年9月8日 下午7:32:14
	 * @description 
	 *		TODO
	 * @param record
	 *
	 */
	public void updateSignatures(ChildVaccinaterecord record);


	/**
	 * 取消预约提醒
	 * @author fuxin
	 * @date 2017年9月16日 下午2:41:13
	 * @description 
	 *		TODO
	 * @param id
	 * @return
	 *
	 */
	public int cancelRemind(String id);
	


	/**
	 * 根据条件删除记录
	 * @author fuxin
	 * @date 2017年10月4日 下午5:55:33
	 * @description 
	 *		TODO
	 * @param param
	 *
	 */
	public int deleteWhere(Map<String, Object> param);

	/**
	 * 造签字假数据接口（一条一插）
	 * @author zhouqj
	 * @date 2017年12月22日 上午9:05:08
	 * @description 
	 *		TODO
	 * @param cccc
	 *
	 */
	public void updateSignTest(ChildVaccinaterecord cccc);

	/**
	 * 记录退款
	 * @author zhouqj
	 * @date 2018年1月16日 下午10:42:36
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 *
	 */
	public void refundById(ChildVaccinaterecord childVaccinaterecord);

	/**
	 * 保存调价信息
	 * @author zhouqj
	 * @date 2018年1月25日 上午10:26:35
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 *
	 */
	public void saveAdjustment(ChildVaccinaterecord childVaccinaterecord);
	
	/**
	 * 查询所有符合筛选条件的应种儿童个案详细记录总条数
	 * @author Jack
	 * @date 2018年2月7日 下午7:08:33
	 * @description 
	 * @param startTime
	 * @param endTime
	 * @param residesStr
	 * @param situationStr
	 * @param areaArr
	 * @param vaccNumArr
	 * @return
	 *
	 */
	public Integer getYQWZChildBaseInfoCount(@Param(value="searchMap") Map<String, Object> searchMap);
	
	/**
	 * 查询所有符合筛选条件的应种儿童个案详细记录
	 * @author Jack
	 * @date 2018年2月7日 下午7:13:32
	 * @description 
	 * @param startTime
	 * @param endTime
	 * @param residesStr
	 * @param situationStr
	 * @param areaArr
	 * @param vaccNumArr
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> getYQWZChildBaseInfo(@Param(value="searchMap") Map<String, Object> searchMap, @Param(value="page") Page<HashMap<String, Object>> page);
	
	
	
	/**
	 * 获取应种数据导出的Excel数据结果
	 * @author Jack
	 * @date 2018年2月27日 下午4:25:40
	 * @description 
	 * @param searchMap
	 * @param page
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> getYQWZChildBaseInfoExcel(@Param(value="searchMap") Map<String, Object> searchMap);

	/**
	 * 获取最后一个完成记录
	 * @author fuxin
	 * @date 2018年2月28日 下午7:40:54
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public ChildVaccinaterecord getLastFinishRecord();

	/**
	 * 获取完成人数
	 * @author yangjian
	 * @date 2018年3月2日 下午4:25:40
	 * @description 
	 * @return
	 *
	 */
	public Integer getRecordCount();
}