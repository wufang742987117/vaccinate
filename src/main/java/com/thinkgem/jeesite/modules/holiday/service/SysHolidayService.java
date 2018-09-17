/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.holiday.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.holiday.dao.SysHolidayDao;
import com.thinkgem.jeesite.modules.holiday.entity.SysHoliday;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

/**
 * 节假日Service
 * @author fuxin
 * @version 2017-09-23
 */
@Service
@Transactional(readOnly = true)
public class SysHolidayService extends CrudService<SysHolidayDao, SysHoliday> {

	public SysHoliday get(String id) {
		return super.get(id);
	}
	
	public List<SysHoliday> findList(SysHoliday sysHoliday) {
		return super.findList(sysHoliday);
	}
	
	public Page<SysHoliday> findPage(Page<SysHoliday> page, SysHoliday sysHoliday) {
		return super.findPage(page, sysHoliday);
	}
	
	@Transactional(readOnly = false)
	public void save(SysHoliday sysHoliday) {
		super.save(sysHoliday);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysHoliday sysHoliday) {
		super.delete(sysHoliday);
	}
	
	/**
	 * 计算下一个工作日
	 * @author fuxin
	 * @date 2017年9月22日 下午2:31:23
	 * @description 
	 *		TODO
	 * @param date
	 * @return Date
	 * @return null 节假日不可用
	 *
	 */
	public Date nextWorkDay(Date date){
		return nextWorkDay(date, OfficeService.getFirstOfficeCode());
	}
	
	/**
	 * 计算下一个工作日
	 * @author fuxin
	 * @date 2017年9月22日 下午2:31:23
	 * @description 
	 *		TODO
	 * @param date
	 * @return Date
	 * @return null 节假日不可用
	 *
	 */
	public Date nextWorkDay(Date date, String localcode){
		SysHoliday temp = new SysHoliday();
		temp.setLocalCode(localcode);
		List<SysHoliday> holidays = findList(temp);
		if(!isAvailable(holidays)){
			return null;
		}
		while(isHoliday(date, holidays)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date); 
			calendar.add(Calendar.DATE, 1);//把日期往后增加一年.整数往后推,负数往前移动
			date = calendar.getTime();
		}
		return date;
	}
	
	/**
	 * 
	 * @author fuxin
	 * @date 2017年9月22日 下午4:41:24
	 * @description 
	 *		TODO
	 * @param date
	 * @return
	 *
	 */
	public boolean isHoliday(Date date){
		return isHoliday(date, null);
	}
	
	/**
	 * 判断是否为工作日
	 * @author fuxin
	 * @date 2017年9月22日 下午2:31:59
	 * @description 
	 *		TODO
	 * @param date
	 * @return
	 *
	 */
	public boolean isHoliday(Date date, List<SysHoliday> holidays){
		if(holidays == null){
			holidays = findList(new SysHoliday());
		}
		//将时间包装成Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//循环匹配节假日
		for(SysHoliday h : holidays){
			if(SysHoliday.DATETYPE_WEEK.equals(h.getDateType())){
				int day = calendar.get(Calendar.DAY_OF_WEEK);
				//星期六或星期天。星期天是1，类推
				if(day == h.getDateDayInt()){
				    return true;
				}
			}
			if(SysHoliday.DATETYPE_DAY.equals(h.getDateType())){
				//将日期格式化比较
				if(DateUtils.formatDate(date).equals(DateUtils.formatDate(h.getDateTime()))){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 检查节假日是否可用，即周一到周日是否都包含
	 * @author fuxin
	 * @date 2017年9月23日 上午10:59:47
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	private boolean isAvailable(List<SysHoliday> holidays){
		//将一周的放到set中
		Set<Integer> weekSet = new HashSet<Integer>();
		for(SysHoliday h:holidays){
			if(SysHoliday.DATETYPE_WEEK.equals(h.getDateType())){
				weekSet.add(h.getDateDayInt());
			}
		}
		//若有7个都有则不可用
		if(weekSet.size() >= 7){
			return false;
		}
		return true;
	}

	/**
	 * 获取可用节假日
	 * @author fuxin
	 * @date 2017年12月8日 上午10:55:06
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<SysHoliday> findAbleDays() {
		SysHoliday tempHoliday = new SysHoliday();
		tempHoliday.setDateTimeAfter(new Date());
		tempHoliday.setDateType(SysHoliday.DATETYPE_DAY);
		return findList(tempHoliday);
	}

	/**
	 * 获取可用 每周非工作日
	 * @author fuxin
	 * @return 
	 * @date 2017年12月8日 上午10:55:30
	 * @description 
	 *		TODO
	 *
	 */
	public List<SysHoliday> findAbleWeeks() {
		SysHoliday tempweek = new SysHoliday();
		tempweek.setDateType(SysHoliday.DATETYPE_WEEK);
		return findList(tempweek); 
	}
	
}
