package com.thinkgem.jeesite.modules.export.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.export.entity.ChirldFileStatistics;
import com.thinkgem.jeesite.modules.export.entity.Export;
import com.thinkgem.jeesite.modules.export.entity.ExportChildhelp;
import com.thinkgem.jeesite.modules.export.entity.ExportTeo;
import com.thinkgem.jeesite.modules.export.entity.HepatitisBSchedule;
import com.thinkgem.jeesite.modules.export.entity.MonthlyReportOnVaccConsumption;
@MyBatisDao
public interface ExportDao extends CrudDao<ExportChildhelp>{

	/**
	 * 二类疫苗统计表
	 * @author zhouqj
	 * @date 2017年5月5日 下午5:58:02
	 * @description 
	 * @param export
	 * @return
	 *
	 */
	public List<ExportTeo> twoCategoriesOfVaccStatistics(ExportTeo export);
	
	/**
	 * 接种乙肝明细表
	 * @author wangdedi
	 * @date 2017年5月5日 下午5:04:05
	 * @description 
	 * @param hepatitisBSchedule
	 * @return
	 *
	 */
	public List<HepatitisBSchedule> hepatitisBSchedule(Export export);

	/**
	 * 疫苗消耗日报
	 * @author wangdedi
	 * @date 2017年5月5日 下午7:04:35
	 * @description 
	 * @param export
	 * @return
	 *
	 */
	public List<MonthlyReportOnVaccConsumption> monthlyReportOnVaccConsumption(Export export);


	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年8月29日 下午2:27:51
	 * @description 
	 * @param exportChirldhelp
	 * @return
	 *
	 */
	public List<ExportChildhelp> findData(ExportChildhelp exportChirldhelp);

	/**
	 * 查询去年儿童的 总数和新出生的儿童数目
	 * @author xuejinshan
	 * @date 2017年8月30日 下午9:48:47
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public ExportChildhelp findNewAndAll(ExportChildhelp exportChildhelp);

	/**
	 * 查询所有的社区
	 * @author xuejinshan
	 * @date 2017年9月11日 下午8:22:42
	 * @description 
	 * @return
	 *
	 */
	public List<HashMap<String, String>> findAllshequ();

	/**
	 * 查询儿童建卡及时率
	 * @author xuejinshan
	 * @date 2017年9月12日 下午2:37:51
	 * @description 
	 * @param map
	 * @return
	 */
	public List<HashMap<String, String>> findCard_in_time(HashMap<String, Object> map);

	/**
	 * 儿童建卡查询  根据居住 类型
	 * @author xuejinshan
	 * @date 2017年9月13日 上午11:13:34
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> findCard_num(HashMap<String, Object> map);

	/**
	 * 安在册情况查询  儿童 建卡数
	 * @author xuejinshan
	 * @date 2017年9月13日 下午12:29:06
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> findCard_num2(HashMap<String, Object> map);

	/**
	 * 统计儿童基本信息完整性
	 * @author xuejinshan
	 * @date 2017年9月14日 下午2:48:41
	 * @description 
	 * @param chirldFileStatistics
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> findChirld_baseInfo_integrity(ChirldFileStatistics chirldFileStatistics);

	/**
	 * 统计儿童基本信息完整性的 同时查询儿童基本信息
	 * @author xuejinshan
	 * @date 2017年9月15日 下午4:07:13
	 * @description 
	 * @param chirldFileStatistics
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> findChirld_baseInfo(ChirldFileStatistics chirldFileStatistics);

	/**
	 * 查询儿童接种记录完整 性
	 * @author xuejinshan
	 * @date 2017年9月16日 下午1:53:46
	 * @description 
	 * @param chirldFileStatistics
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> findChirld_vaccnum_integrity(ChirldFileStatistics chirldFileStatistics);

	/**
	 * 查询儿童接种记录完整性 的同时查询接种记录的一些基本信息
	 * @author xuejinshan
	 * @date 2017年9月16日 下午1:59:19
	 * @description 
	 * @param chirldFileStatistics
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> findChirld_vaccnumInfo(ChirldFileStatistics chirldFileStatistics);

	/**
	 * 查询去年出生人数 和总人数
	 * @author xuejinshan
	 * @date 2017年9月4日 下午3:09:54
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public ExportChildhelp findNewAndAll311(ExportChildhelp exportChildhelp);
	
	/**
	 * 常规3-1-1统计
	 * @author xuejinshan
	 * @date 2017年9月16日 下午8:56:56
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData311_(ExportChildhelp exportChildhelp);

	/**
	 * 常规3-1-2统计
	 * @author zhouqj
	 * @date 2017年9月19日 下午3:11:14
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData312_(ExportChildhelp exportChildhelp);
	
	/**
	 * 常规3-1-3统计
	 * @author zhouqj
	 * @date 2017年9月19日 下午3:11:14
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData313_(ExportChildhelp exportChildhelp);
	
	/**
	 * 常规3-1-4统计
	 * @author zhouqj
	 * @date 2017年9月19日 下午3:11:14
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData314_(ExportChildhelp exportChildhelp);
	
	/**
	 * 常规3-1-5统计
	 * @author zhouqj
	 * @date 2017年9月19日 下午3:11:14
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData315_(ExportChildhelp exportChildhelp);
	
	
	/*常规接种3-1-1报表数据初始化开始*/
	
	public List<HashMap<String, String>> selectRoutine3_1_1(@Param(value="startDate")String startDate, @Param(value="endDate")String endDate);
	
	/*常规接种3-1-1报表数据初始化结束*/
	
}
