package com.thinkgem.jeesite.modules.export.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.export.entity.ExpRoutinevacc6_1;

/**
 * 常规免疫6-1 DAO
 * @author Jack
 * @date 2017年10月13日 下午2:34:25
 * @description 
 */
@MyBatisDao
public interface ExpRoutinevacc6_1Dao extends  CrudDao<ExpRoutinevacc6_1>{
	
	//------------------------------------------------------------------------------------------
	//-------------------------------常规免疫6-1开始-------------------------------------------//
	//------------------------------------------------------------------------------------------
	
	//-------------------------------------获取 map数据开始-----------------------------------//
	/**
	 * 常规免疫接种 情况汇总表6-1 查询数据 =>按居住类型
	 * @author Jack
	 * @date 2017年9月20日 下午4:00:47
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccData61_unit(HashMap<String, String> map);
	
	/**
	 * 常规免疫接种 情况汇总表6-1 查询数据 =>按社区  分居住类型
	 * @author Jack
	 * @date 2017年9月20日 下午4:01:29
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccData61_unitAndReside(HashMap<String, String> map);
	
	/**
	 * 常规免疫接种 情况汇总表6-1 查询数据 =>按社区  不分居住类型
	 * @author Jack
	 * @date 2017年9月20日 下午4:03:26
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccData61_unitNoReside(HashMap<String, String> map);
	
	/**
	 * 常规免疫接种情况汇总表6-1 特殊类型疫苗统计
	 * @author Jack
	 * @date 2017年10月25日 下午7:09:48
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccSpecData61_unitNoReside(HashMap<String,String> map);
	
	
	/**
	 * 常规免疫接种 情况汇总表6-1 查询数据    总合计
	 * @author Jack
	 * @date 2017年9月20日 下午4:04:05
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccData61_all(HashMap<String, String> map);
	
	//-------------------------------------获取 map数据结束-----------------------------------//
	
	/**
	 * 查询Reside
	 * @author Jack
	 * @date 2017年10月13日 上午9:56:37
	 * @description 
	 * @param list_reside
	 * @return
	 *
	 */
	public List<ExpRoutinevacc6_1> selectResideList();
	
	
	/**
	 * 查询Community
	 * @author Jack
	 * @date 2017年10月13日 上午9:57:48
	 * @description 
	 * @param list_community
	 * @return
	 *
	 */
	public List<ExpRoutinevacc6_1> selectCommunityList(@Param(value="officeStr") String officeStr);
	
	/**
	 * 查询互联网版本社区信息
	 * @author Jack
	 * @date 2018年2月9日 下午12:34:04
	 * @description 
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectCommunityListNetVersion();
	
	/**
	 * 查询7岁内全部记录
	 * @author Jack
	 * @date 2017年10月13日 上午10:31:51
	 * @description 
	 * @param sql_total
	 * @return
	 *
	 */
	public List<ExpRoutinevacc6_1> selectTotalList();
	
	/**
	 * 查询7岁内全部记录，并拼接community、reside
	 * @author Jack
	 * @param end 
	 * @param start 
	 * @date 2017年10月17日 下午4:28:11
	 * @description 
	 * @return
	 *
	 */
	public List<Map<String, String>> selectSqlQuery(@Param(value = "monthNum")Integer monthNum, @Param(value="officeStr") String officeStr);
//	public List<Map<String, String>> selectSqlQuery();
	
	public List<Map<String, String>> selectSqlList(@Param(value = "id") String id, @Param(value = "monthNum") Integer monthNum);
//	public List<Map<String, String>> selectSqlList(@Param(value = "id") String id);
		
	//乙肝卡介特殊处理
	public List<Map<String, String>> selectSqlListSpecial(@Param(value = "id") String id, @Param(value = "monthNum1") Integer monthNum1, @Param(value = "monthNum2") Integer monthNum2);
	
	public List<Map<String, String>> selectSqlYzrq(@Param(value = "id") String id, @Param(value = "monthNum") Integer monthNum);
//	public List<Map<String, String>> selectSqlYzrq(@Param(value = "id") String id);
	
	//统计月龄超过84,但是接种了某疫苗的所有记录数(计入该疫苗的应种和实种)
	public Integer get_yuefendayu84_Num(@Param(value="nid")String nid, @Param(value="monthNum") Integer monthNum,
			@Param(value="reside")String reside, @Param(value="area")String area);
	
	/**
	 * 根据点击选中的社区、疫苗名称、查询应种数据
	 * @author Jack
	 * @date 2018年2月1日 下午5:24:21
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @return
	 *
	 */
	public List<Map<String, String>> queryDetailInfo(@Param(value="community")String community, @Param(value="vaccNum")String vaccNum, @Param(value="type")String type, 
			@Param(value="startTime") String startTime, @Param(value="endTime")String endTime, @Param(value="startRowNum") Integer startRowNum, @Param(value="endRowNum") Integer endRowNum);
	
	/**
	 * 根据点击选中的社区、疫苗名称、查询应种数据总数
	 * @author Jack
	 * @date 2018年2月5日 下午4:03:16
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public Integer queryDetailInfoCount(@Param(value="community")String community, @Param(value="vaccNum")String vaccNum, 
			@Param(value="type")String type, @Param(value="startTime") String startTime, @Param(value="endTime")String endTime);
	
	/**
	 * 获取按社区划分的所有应种儿童个案信息
	 * @author Jack
	 * @date 2018年2月5日 上午11:36:32
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public List<Map<String, String>> queryShouldDetailInfo(@Param(value="community")String community, @Param(value="vaccNum")String vaccNum,
			@Param(value="startTime") String startTime, @Param(value="endTime")String endTime, @Param(value="startRowNum") Integer startRowNum, @Param(value="endRowNum") Integer endRowNum);
	
	/**
	 * 获取按社区划分的所有应种儿童个案信息总数
	 * @author Jack
	 * @date 2018年2月5日 下午4:13:42
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param startTime
	 * @param endTime
	 * @param startRowNum
	 * @param endRowNum
	 * @return
	 *
	 */
	public Integer queryShouldDetailInfoCount(@Param(value="community")String community, @Param(value="vaccNum")String vaccNum,
			@Param(value="startTime") String startTime, @Param(value="endTime")String endTime);
	
	/**
	 * 获取最后接种活疫苗的接种时间
	 * @author Jack
	 * @date 2018年2月3日 上午10:50:51
	 * @description 
	 * @param childId
	 * @return
	 *
	 */
	public Date getLastLiveVaccDate(@Param(value="childId") String childId);
	
	
	/**
	 * 根据儿童Id查询该儿童接种最后一针的时间
	 * @author Jack
	 * @date 2018年2月3日 上午11:10:09
	 * @description 
	 * @return
	 *
	 */
	public Date getLastVaccDate(@Param(value="childId") String childId);
	
	
	/**
	 * 查询应种未种超过3个月仍然应种未种的儿童个案
	 * @author Jack
	 * @date 2018年2月3日 下午3:58:32
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @param timeStr
	 * @return
	 *
	 */
	public List<Map<String, String>> getyzwzData(@Param(value="community")String community, @Param(value="vaccNum")String vaccNum, 
			@Param(value="type")String type, @Param(value="timeStr") String timeStr, @Param(value="startRowNum") int startRowNum, @Param(value="endRowNum") int endRowNum);
	
	/**
	 * 查询应种未种超过3个月仍然应种未种的儿童个案记录总数
	 * @author Jack
	 * @date 2018年2月5日 下午3:14:45
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @param timeStr
	 * @param startRowNum
	 * @param endRowNum
	 * @return
	 *
	 */
	public int getyzwzDataCount(@Param(value="community")String community, @Param(value="vaccNum")String vaccNum, 
			@Param(value="type")String type, @Param(value="timeStr") String timeStr);
	
	/**
	 *  根据传入的年月、localCode判断该条件下生成的数据总条数根据传入的年月、localCode判断该条件下生成的数据总条数
	 * @author Jack
	 * @date 2018年2月9日 下午1:21:02
	 * @description 
	 * @param yearMonth 年月字符串
	 * @param localCode 本地编码
	 * @return
	 *
	 */
	public int countDataByMonthAndLocalCode(@Param(value="yearMonth") String yearMonth, @Param(value="localCode") String localCode);

	
	//----------------------------------------------------------------------------------------//
	//-------------------------------常规免疫6-1结束-------------------------------------------//
	//----------------------------------------------------------------------------------------//
	
	
	
	//----------------------------------------------------------------------------------------//
	//-------------------------------国家常规免疫6-1开始---------------------------------------//
	//----------------------------------------------------------------------------------------//
	/**
	 * 国家免疫规划疫苗常规接种情况报表(表6-1)<br>带居住属性的查询方法:本地,流动,临时
	 * @author Jack
	 * @date 2017年10月19日 上午9:48:24
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> nationalRoutineVaccWithReside6_1(HashMap<String, String> map);

	/**
	 * 国家免疫规划疫苗常规接种情况报表(表6-1)<br>不带居住属性的查询方法:合计
	 * @author Jack
	 * @date 2017年10月25日 下午5:11:13
	 * @description 
	 *		TODO
	 * @param reside
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public List<HashMap<String, String>> nationalRoutineVaccNoReside6_1(HashMap<String, String> map);
	
	/**
	 * 查询乙肝疫苗、卡介苗、脊灰疫苗、百白破疫苗、白破疫苗、麻风、麻腮风、A+C群流脑结合疫苗、A+C群流脑多糖疫苗、甲肝减毒活疫苗  本地数据
	 * @author Jack
	 * @date 2017年11月20日 下午8:37:01
	 * @description 
	 * @param map
	 * @return 查询统计值结果HashMap
	 *
	 */
	public List<HashMap<String, String>> getExistLocalDataList(HashMap<String, String> map);
	
	/**
	 * 查询乙肝疫苗、卡介苗、脊灰疫苗、百白破疫苗、白破疫苗、麻风、麻腮风、A+C群流脑结合疫苗、A+C群流脑多糖疫苗、甲肝减毒活疫苗  流动数据
	 * @author Jack
	 * @date 2017年11月21日 上午11:04:17
	 * @description 
	 * @param map
	 * @return 查询统计值结果HashMap
	 *
	 */
	public List<HashMap<String, String>> getExistFlowDataList(HashMap<String, String> map);
	
	
	public List<HashMap<String, String>> selectSpecialData(@Param(value = "vaccID")String vaccID, @Param(value = "monthNum")Integer monthNum);
	
	//----------------------------------------------------------------------------------------//
	//-------------------------------国家常规免疫6-1结束---------------------------------------//
	//----------------------------------------------------------------------------------------//
	
	
	//------------------------------------重新生成6-1数据开始------------------------------------//
	
	/**
	 * 计算指定年的某个月份范围内EXP_ROUTINEVACC_6_1表数据总条数
	 * @author Jack
	 * @date 2018年3月3日 下午3:20:44
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public int getDataCount61ByTimeRange(@Param(value="localCode")  String localCode,  @Param(value="startTime") String startTime, @Param(value="endTime") String endTime);
	
	/**
	 * 计算指定年的某个月份范围内EXP_ROUTINEVACC_6_1_Detail表数据总条数
	 * @author Jack
	 * @date 2018年3月3日 下午3:33:07
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public int getDataCount61DetailByTimeRange(@Param(value="localCode")  String localCode,  @Param(value="yearMonth") String yearMonth);
	
	/**
	 * 清空指定年的某个月份范围内EXP_ROUTINEVACC_6_1表数据项
	 * @author Jack
	 * @date 2018年3月3日 下午3:40:27
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public void clearDataCount61ByTimeRange(@Param(value="localCode")  String localCode,  @Param(value="startTime") String startTime, @Param(value="endTime") String endTime);
	
	/**
	 * 清空指定年的某个月份范围内EXP_ROUTINEVACC_6_1_Detail表数据项
	 * @author Jack
	 * @date 2018年3月3日 下午3:40:32
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public void clearDataCount61DetailByTimeRange(@Param(value="localCode")  String localCode,  @Param(value="yearMonth") String yearMonth);
	
	//------------------------------------重新生成6-1数据结束------------------------------------//
}
