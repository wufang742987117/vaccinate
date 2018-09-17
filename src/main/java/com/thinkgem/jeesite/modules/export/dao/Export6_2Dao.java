package com.thinkgem.jeesite.modules.export.dao;

import java.util.HashMap;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.export.entity.Export6_2;

/**
 * 6-2报表Dao
 * @author Jack
 * @date 2017年10月23日 下午6:40:23
 * @description 
 */
@MyBatisDao
public interface Export6_2Dao extends CrudDao<Export6_2>{
	
	/**
	 * 6-2二类报表数据
	 * @author Jack
	 * @date 2017年10月23日 下午6:40:19
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	List<HashMap<String, String>> typeTwoVaccReport6_2(HashMap<String, Object> map);
	
	/**
	 * 常规免疫接种情况6-2汇总表 Dao
	 * @author Jack
	 * @date 2017年10月24日 上午9:25:47
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	List<HashMap<String, Object>> selectVaccData6_2(HashMap<String, Object> map);
	
}
