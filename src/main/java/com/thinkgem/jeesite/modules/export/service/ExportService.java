package com.thinkgem.jeesite.modules.export.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.export.dao.ExportDao;
import com.thinkgem.jeesite.modules.export.entity.ChirldFileStatistics;
import com.thinkgem.jeesite.modules.export.entity.Export;
import com.thinkgem.jeesite.modules.export.entity.ExportChildhelp;
import com.thinkgem.jeesite.modules.export.entity.ExportTeo;
import com.thinkgem.jeesite.modules.export.entity.HepatitisBSchedule;
import com.thinkgem.jeesite.modules.export.entity.MonthlyReportOnVaccConsumption;

@Service
@Transactional(readOnly = true)
public class ExportService extends CrudService<ExportDao, ExportChildhelp> {
	@Autowired
	ExportDao dao;
	
	/**
	 * 二类疫苗统计表
	 * @author zhouqj
	 * @date 2017年5月5日 下午5:58:13
	 * @description 
	 * @param export
	 * @return
	 *
	 */
	public List<ExportTeo> twoCategoriesOfVaccStatistics(ExportTeo export) {
		return dao.twoCategoriesOfVaccStatistics(export);
	}
	
	/**
	 * 接种乙肝明细表
	 * @author wangdedi
	 * @date 2017年5月5日 下午5:03:17
	 * @description 
	 * @param hepatitisBSchedule
	 * @return
	 *
	 */
	public List<HepatitisBSchedule> hepatitisBSchedule(Export export) {
		return dao.hepatitisBSchedule(export);
	}
	
	/**
	 * 疫苗消耗日报
	 * @author wangdedi
	 * @date 2017年5月5日 下午7:04:35
	 * @description 
	 * @param export
	 * @return
	 *
	 */
	public List<MonthlyReportOnVaccConsumption> monthlyReportOnVaccConsumption(Export export) {
		return dao.monthlyReportOnVaccConsumption(export);
	}

	/**
	 * 查询所有的社区
	 * @author xuejinshan
	 * @date 2017年9月11日 下午8:21:50
	 * @description 
	 * @return
	 *
	 */
	public List<HashMap<String, String>> findAllshequ() {
		return dao.findAllshequ();
	}

	/**
	 * 查询儿童建卡及时率
	 * @author xuejinshan
	 * @date 2017年9月12日 下午2:38:45
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> findCard_in_time(HashMap<String, Object> map) {
		return dao.findCard_in_time(map);
	}

	/**
	 * 按居住 类型查询建卡数   儿童建卡查询
	 * @author xuejinshan
	 * @date 2017年9月13日 上午11:11:42
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> findCard_num(HashMap<String, Object> map) {
		return dao.findCard_num(map);
	}

	/**
	 * 按在册情况 查询儿童加卡数
	 * @author xuejinshan
	 * @date 2017年9月13日 下午12:27:05
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> findCard_num2(HashMap<String, Object> map) {
		return dao.findCard_num2(map);
	}

	/**
	 * 统计儿童基本信息完整性
	 * @author xuejinshan
	 * @date 2017年9月14日 下午2:47:48
	 * @description 
	 * @param chirldFileStatistics
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> findChirld_baseInfo_integrity(ChirldFileStatistics chirldFileStatistics) {
		return dao.findChirld_baseInfo_integrity(chirldFileStatistics);
	}
	
	public List<HashMap<String, Object>> manageFindChirld_baseInfo_integrity(ChirldFileStatistics chirldFileStatistics) {
		List<HashMap<String, Object>> temps=findChirld_baseInfo_integrity(chirldFileStatistics);
		for(HashMap<String, Object> map:temps){
			map.put("nianfen", DateUtils.formatDate(DateUtils.parseDate(map.get("nianfen")), "yyyy-MM-dd"));
		}
		return temps;
	}

	/**
	 * 统计儿童基础档案完整性时  同时查询儿童基本信息，
	 * @author xuejinshan
	 * @date 2017年9月15日 下午4:05:47
	 * @description 
	 * @param chirldFileStatistics
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> findChirld_baseInfo(ChirldFileStatistics chirldFileStatistics) {
		return dao.findChirld_baseInfo(chirldFileStatistics);
	}

	/**
	 * 查询儿童接种几录完整性
	 * @author xuejinshan
	 * @date 2017年9月16日 下午1:53:18
	 * @description 
	 * @param chirldFileStatistics
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> findChirld_vaccnum_integrity(ChirldFileStatistics chirldFileStatistics) {
		return dao.findChirld_vaccnum_integrity(chirldFileStatistics);
	}

	
	/**
	 * 查询儿童接种记录完整性 的同时查询一些接种记录的相关的信息
	 * @author xuejinshan
	 * @date 2017年9月16日 下午1:57:29
	 * @description 
	 * @param chirldFileStatistics
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> findChirld_vaccnumInfo(ChirldFileStatistics chirldFileStatistics) {
		return dao.findChirld_vaccnumInfo(chirldFileStatistics);
	}
	
	/**
	 *  查询去年出生人数 和总人数
	 * @author xuejinshan
	 * @date 2017年9月4日 下午3:10:48
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public ExportChildhelp findNewAndAll311(ExportChildhelp exportChildhelp){
		return dao.findNewAndAll311(exportChildhelp);
	}

	/**
	 * 常规3-1-1统计
	 * @author xuejinshan
	 * @date 2017年9月16日 下午8:55:58
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData311_(ExportChildhelp exportChildhelp) {
		return dao.selectVaccData311_(exportChildhelp);
	}

	/**
	 * 常规3-1-2统计
	 * @author zhouqj
	 * @date 2017年9月19日 下午3:08:50
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData312_(ExportChildhelp exportChildhelp) {
		return dao.selectVaccData312_(exportChildhelp);
	}
	
	/**
	 * 常规3-1-2的数据分类
	 * @author zhouqj
	 * @date 2017年9月19日 下午3:14:13
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<List<HashMap<String, Object>>> manageVaccData312_(ExportChildhelp exportChildhelp){
		List<HashMap<String, Object>> datalist = selectVaccData312_(exportChildhelp);
		return manageVaccDataHelp_(datalist);
	}
	
	/**
	 * 常规3-1-3统计
	 * @author zhouqj
	 * @date 2017年9月19日 下午3:08:50
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData313_(ExportChildhelp exportChildhelp) {
		return dao.selectVaccData313_(exportChildhelp);
	}
	
	/**
	 * 常规3-1-3的数据分类
	 * @author zhouqj
	 * @date 2017年9月19日 下午3:14:13
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<List<HashMap<String, Object>>> manageVaccData313_(ExportChildhelp exportChildhelp){
		List<HashMap<String, Object>> datalist = selectVaccData313_(exportChildhelp);
		return manageVaccDataHelp_(datalist);
	}
	
	/**
	 * 常规3-1-4统计
	 * @author zhouqj
	 * @date 2017年9月19日 下午6:43:10
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData314_(ExportChildhelp exportChildhelp){
		return dao.selectVaccData314_(exportChildhelp);
	}
	
	/**
	 * 常规3-1-4的数据分类
	 * @author zhouqj
	 * @date 2017年9月19日 下午6:43:10
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<List<HashMap<String, Object>>> manageVaccData314_(ExportChildhelp exportChildhelp){
		List<HashMap<String, Object>> datalist = selectVaccData314_(exportChildhelp);
		return manageVaccDataHelp_(datalist);
	}
	
	/**
	 * 常规3-1-5统计
	 * @author zhouqj
	 * @date 2017年9月19日 下午6:43:10
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData315_(ExportChildhelp exportChildhelp){
		return dao.selectVaccData315_(exportChildhelp);
	}
	
	/**
	 * 常规3-1-5的数据分类
	 * @author zhouqj
	 * @date 2017年9月19日 下午6:43:10
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<List<HashMap<String, Object>>> manageVaccData315_(ExportChildhelp exportChildhelp){
		List<HashMap<String, Object>> datalist = selectVaccData315_(exportChildhelp);
		return manageVaccDataHelp_(datalist);
	}
	
	/**
	 * manageVaccData处理方法   即封装双层的list集合 
	 * @author xuejinshan
	 * @date 2017年8月31日 下午7:06:18
	 * @description 
	 * @param exportChildhelp
	 * @return
	 *
	 */
	public List<List<HashMap<String, Object>>> manageVaccDataHelp_(List<HashMap<String, Object>> datalist){
		List<List<HashMap<String, Object>>> returnlist = new ArrayList<List<HashMap<String, Object>>>();
		List<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
		if(datalist != null && datalist.size() > 0){
			for(int i=1;i<=(datalist.size()/25);i++){
				tempList.addAll(datalist.subList((i-1)*25, i*25));
				returnlist.add(tempList);
				tempList = new ArrayList<HashMap<String, Object>>();
			}
			tempList.addAll(datalist.subList(datalist.size()-datalist.size()%25, datalist.size()));
			returnlist.add(tempList);
			tempList = new ArrayList<HashMap<String, Object>>();
		}else{
			return new ArrayList<List<HashMap<String, Object>>>();
		}
		return returnlist;
	}
	
	/*计算并保存常规接种(3-1-1)数据开始*/
	/**
	 * 常规接种(3-1-1)数据初始化 暂时未用到
	 * @author Jack
	 * @date 2017年11月1日 下午7:26:01
	 * @description 
	 *	卡介苗 0101
     *	脊灰(减毒二倍体) 0301
     *	百白破 0401
     *	白破 0601
     *	乙肝(酿酒酵母) 0202
     *	麻疹 0901
     *	乙脑(灭活) 1803
     *	风疹(二倍体) 1101
     *	腮腺炎 1001
     *	流脑A 1601
	 */
//	public void initRoutine3_1_1(){
//		
//	}
	
	/**
	 * 查询报表3-1-1数据 暂时未用到
	 * @author Jack
	 * @date 2017年11月3日 下午5:12:02
	 * @description 
	 * @param startDate
	 * @param endDate
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectRoutine3_1_1(String startDate, String endDate){
		return dao.selectRoutine3_1_1(startDate, endDate);
	}
	
	
	/*计算并保存常规接种(3-1-1)数据结束*/
	
}
