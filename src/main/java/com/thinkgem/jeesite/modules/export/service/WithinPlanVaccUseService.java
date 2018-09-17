package com.thinkgem.jeesite.modules.export.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.export.dao.WithinPlanVaccUseDao;
import com.thinkgem.jeesite.modules.export.entity.WithinPlanVaccUse;

/**
 * 计划免疫疫苗使用情况Service
 * @author Jack
 * @date 2018年3月2日 下午6:57:12
 * @description 
 */
@Service
@Transactional(readOnly = true)
public class WithinPlanVaccUseService extends CrudService<WithinPlanVaccUseDao, WithinPlanVaccUse> {
	@Autowired
	WithinPlanVaccUseDao withinPlanVaccUseDao;
	
	/**
	 * 生成指定月份的计划免疫疫苗使用情况报表数据
	 * @author Jack
	 * @date 2018年3月2日 下午7:26:35
	 * @description 
	 * @param year
	 * @param month
	 * @return
	 *
	 */
	public String getWithinPlanVaccUseData(String localCode, String  lastMonthStartTime, String  lastMonthEndTime, String  startTime, String  endTime){
		
		List<HashMap<String, String>> dataList = getWithinPlanVaccUseDataByMonth(localCode, lastMonthStartTime, lastMonthEndTime, startTime, endTime);
		
		/*for(HashMap<String, String> map:dataList){
			int lastNum = 0; //上月末库存
			int inNum = 0;  //领苗数
			int useNum = 0;  //使用数
			int outNum = 0;  //报废数
			int restNum = 0;  //本月底库存数
			int nextNeedNum = 0;  //下月需求
			for(String key:map.keySet()){				
				switch (key) {
				case "LAST_NUM":
					lastNum = Integer.valueOf(String.valueOf(map.get("LAST_NUM")));
					break;
					
				case "IN_NUM":
					inNum = Integer.valueOf(String.valueOf(map.get("IN_NUM")));
					break;
					
				case "USE_NUM":
					useNum = Integer.valueOf(String.valueOf(map.get("USE_NUM")));
					break;
					
				case "OUT_NUM":
					outNum = Integer.valueOf(String.valueOf(map.get("OUT_NUM")));
					break;
					
				case "REST_NUM":
					restNum = Integer.valueOf(String.valueOf(map.get("REST_NUM")));
					break;

				default:
					break;
				}
			}
			nextNeedNum = lastNum + inNum - useNum - outNum;
			map.put("NEXT_NEED", String.valueOf(nextNeedNum));
		}*/
		System.out.println("\n"+JsonMapper.toJsonString(dataList));
		return JsonMapper.toJsonString(dataList);
	}
	
	/**
	 * 按月份查询计划免疫疫苗使用情况报表
	 * @author Jack
	 * @date 2018年3月3日 上午10:54:57
	 * @description 
	 * @param year
	 * @param month
	 * @return
	 *
	 */
	public List<HashMap<String, String>> getWithinPlanVaccUseDataByMonth(String localCode, String  lastMonthStartTime, String  lastMonthEndTime, String  startTime, String  endTime){
		
		return withinPlanVaccUseDao.getWithinPlanVaccUseDataByMonth(localCode, lastMonthStartTime, lastMonthEndTime, startTime, endTime);
	}
	
}
