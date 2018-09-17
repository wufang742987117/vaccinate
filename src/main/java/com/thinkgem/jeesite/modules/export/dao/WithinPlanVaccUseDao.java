package com.thinkgem.jeesite.modules.export.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.export.entity.WithinPlanVaccUse;

/**
 * 计划免疫疫苗使用情况报表Dao
 * @author Jack
 * @date 2018年3月2日 下午6:55:13
 * @description 
 */
@MyBatisDao
public interface WithinPlanVaccUseDao extends CrudDao<WithinPlanVaccUse>{
	
	/**
	 * 按月份查询计划免疫疫苗使用情况报表
	 * @author Jack
	 * @date 2018年3月3日 上午11:02:33
	 * @description 
	 * @param year
	 * @param month
	 * @return
	 *
	 */
	public List<HashMap<String, String>> getWithinPlanVaccUseDataByMonth(@Param(value="localCode")String localCode, @Param(value="lastMonthStartTime") String lastMonthStartTime, 
			@Param(value="lastMonthEndTime") String lastMonthEndTime, @Param(value="startTime") String  startTime, @Param(value="endTime") String  endTime);
	
}
