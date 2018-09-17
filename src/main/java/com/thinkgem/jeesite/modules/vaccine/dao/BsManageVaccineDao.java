/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.entity.BsProductDO;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;

/**
 * 疫苗信息DAO接口
 * @author wangdedi
 * @version 2017-02-20
 */
@MyBatisDao
public interface BsManageVaccineDao extends CrudDao<BsManageVaccine> {

	List<BsManageVaccine> getVaccineByNumOrder(int sort);

	
	/**
	 * 获取大类列表
	 * @author fuxin
	 * @date 2017年3月27日 上午11:23:43
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	List<BsManageVaccine> findGroupList(BsManageVaccine bsManageVaccine);
	
	/**
	 * 获取大类列表
	 * @author fuxin
	 * @date 2017年3月27日 上午11:23:43
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	List<BsManageVaccine> findDistinctVaccine(BsManageVaccine bsManageVaccine);


	/**
	 * 根据大类获取有库存的小类信息
	 * @author fuxin
	 * @date 2017年4月21日 下午3:20:22
	 * @description 
	 *		TODO
	 * @param group
	 * @return
	 *
	 */
	List<BsManageVaccine> findVaccListAble(BsManageVaccine bsManageVaccine);
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 下午1:56:48
	 * @description 
	 *	查询所有的疫苗小类（去重）
	 * @return
	 *
	 */
	
	List<BsManageVaccine> repertorySum(BsManageVaccine bsManageVaccine);


	/**
	 * 获取疫苗信息，最大针次+其他信息
	 * @author fuxin
	 * @date 2017年5月25日 上午10:13:51
	 * @description 
	 *		TODO
	 * @param gnum
	 * @return
	 *
	 */
	BsManageVaccine getLastPin(BsManageVaccine bsManageVaccine);


	/**
	 * 获取模型大类列表
	 * @author fuxin
	 * @date 2017年7月20日 上午12:12:32
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	List<BsManageVaccine> findModelList(BsManageVaccine bsManageVaccine);


	/**
	 * 获取活苗模型大类的集合
	 * @author fuxin
	 * @date 2017年7月29日 下午4:01:29
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	List<String> findLiveGnumList(BsManageVaccine bsManageVaccine);


	/**
	 * 获取所有特殊规则
	 * @author fuxin
	 * @date 2017年7月30日 下午5:46:37
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	List<String> findRules(BsManageVaccine bsManageVaccine);


	/**
	 * 根据大类查询知情告知书回执
	 * @author fuxin
	 * @date 2017年8月2日 上午1:20:59
	 * @description 
	 *		TODO
	 * @param gnum
	 * @return
	 *
	 */
	List<String> getImpartByGnum(BsManageVaccine bsManageVaccine);


	/**
	 * 获取有库存的产品大类
	 * @author fuxin
	 * @date 2017年8月25日 下午2:47:14
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	List<BsManageVaccine> findGroupListAble(BsManageVaccine bsManageVaccine);


	/**
	 * 获取有库存的产品模型大类
	 * @author fuxin
	 * @date 2017年8月25日 下午2:57:06
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	List<BsManageVaccine> findGroupListAbleModel(BsManageProduct bsManageProduct);


	/**
	 * 保存疫苗模型信息
	 * @author fuxin
	 * @date 2017年11月15日 下午4:10:24
	 * @description 
	 *		TODO
	 * @param bsManageVaccine
	 *
	 */
	void saveModel(BsManageVaccine bsManageVaccine);

	/**
	 * 根据id获取疫苗级联模型大类
	 * @author fuxin
	 * @date 2018年2月3日 下午12:24:27
	 * @description 
	 *		TODO
	 * @param vaccine
	 * @return
	 *
	 */
	BsManageVaccine getWithModel(BsManageVaccine vaccine);


	/**
	 * 单表查询
	 * @author fuxin
	 * @date 2018年2月3日 下午2:35:02
	 * @description 
	 *		TODO
	 * @param bsManageVaccine
	 * @return
	 *
	 */
	List<BsManageVaccine> findListSimple(BsManageVaccine bsManageVaccine);


	
	/**
	 * 根据条件筛选所有符合条件的逾期未种儿童个案
	 * @author Jack
	 * @date 2018年2月6日 下午3:07:59
	 * @description 
	 * @param endSearchMonth 结束查询时间,EXP_ROUTINEVACC6_1DETAIL 表中YEAR_MONTH字段
	 * @param officeCode 管理单位编码
	 * @param birthbegin 查询儿童生日开始时间
	 * @param birthend 查询儿童生日结束时间
	 * @param residesStr 居住属性
	 * @param situationStr 在册情况
	 * @param areaStr 社区的编码
	 * @param vaccNumStr 查询的疫苗大类 + 针次编码
	 * @return
	 *
	 */
	List<HashMap<String, Object>> getYQWZChildBaseInfo(@Param(value="searchMap") Map<String, Object> searchMap, @Param(value="page") Page<HashMap<String, Object>> page);
	
	/**
	 * 根据条件筛选所有符合条件的逾期未种儿童个案排除近期已接种的儿童个案
	 * @author Jack
	 * @date 2018年2月6日 下午9:23:01
	 * @description 
	 * @param endSearchMonth 结束查询时间,EXP_ROUTINEVACC6_1DETAIL 表中YEAR_MONTH字段
	 * @param officeCode 管理单位编码
	 * @param birthbegin 查询儿童生日开始时间
	 * @param birthend 查询儿童生日结束时间
	 * @param residesStr 居住属性
	 * @param situationStr 在册情况
	 * @param areaStr 社区的编码
	 * @param vaccNumStr 查询的疫苗大类 + 针次编码
	 * @param page
	 * @return
	 *
	 */
	List<HashMap<String, Object>> getYQWZChildBaseInfoReal(@Param(value="searchMap") Map<String, Object> searchMap, @Param(value="page") Page<HashMap<String, Object>> page);
	
	/**
	 * 导出逾期未种数据
	 * @author Jack
	 * @date 2018年2月27日 下午3:53:40
	 * @description 
	 * @param searchMap
	 * @param page
	 * @return
	 *
	 */
	List<HashMap<String, Object>> getYQWZChildBaseInfoExcel(@Param(value="searchMap") Map<String, Object> searchMap);
	
	/**
	 * 根据条件筛选所有符合条件的逾期未种儿童个案总数
	 * @author Jack
	 * @date 2018年2月6日 下午6:54:54
	 * @description 
	 * @param endSearchMonth
	 * @param officeCode
	 * @param birthDayStart
	 * @param birthDayEnd
	 * @param residesStr
	 * @param situationStr
	 * @param areaArr
	 * @param vaccNumArr
	 * @return
	 *
	 */
	int getYQWZChildBaseInfoCount(@Param(value="searchMap") Map<String, Object> searchMap);
//	int getYQWZChildBaseInfoCount(@Param(value="endSearchMonth") String endSearchMonth, @Param(value="officeCode") String officeCode,
//			@Param(value="birthDayStart") String birthDayStart, @Param(value="birthDayEnd") String birthDayEnd, @Param(value="residesStr") String residesStr, 
//			@Param(value="situationStr") String situationStr, @Param(value="areaArr") String[] areaArr, @Param(value="vaccNumArr") String[] vaccNumArr);
	
	/**
	 * 统计所有排除近期接种的符合筛选条件的记录总数
	 * @author Jack
	 * @date 2018年2月7日 下午10:29:51
	 * @description 
	 * @param endSearchMonth
	 * @param officeCode
	 * @param birthDayStart
	 * @param birthDayEnd
	 * @param residesStr
	 * @param situationStr
	 * @param areaArr
	 * @param vaccNumArr
	 * @return
	 *
	 */
	int getYQWZChildBaseInfoRealCount(@Param(value="searchMap")Map<String, Object> searchMap);

	
}