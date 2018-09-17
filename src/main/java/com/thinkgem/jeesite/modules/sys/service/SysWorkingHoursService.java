/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junl.common.CommonUtils;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysWorkingHours;
import com.thinkgem.jeesite.modules.remind.dao.VacChildRemindDao;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.sys.dao.SysWorkingHoursDao;

/**
 * 工作时间段Service
 * @author liyuan
 * @version 2018-01-26
 */
@Service
@Transactional(readOnly = true)
public class SysWorkingHoursService extends CrudService<SysWorkingHoursDao, SysWorkingHours> {

	@Autowired
	VacChildRemindDao childRemindDao;
	
	public SysWorkingHours get(String id) {
		return super.get(id);
	}
	
	public List<SysWorkingHours> findList(SysWorkingHours sysWorkingHours) {
		return super.findList(sysWorkingHours);
	}
	
	public Page<SysWorkingHours> findPage(Page<SysWorkingHours> page, SysWorkingHours sysWorkingHours) {
		return super.findPage(page, sysWorkingHours);
	}
	
	@Transactional(readOnly = false)
	public void save(SysWorkingHours sysWorkingHours) {
		super.save(sysWorkingHours);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysWorkingHours sysWorkingHours) {
		super.delete(sysWorkingHours);
	}


	/**
	 * 根据具体时间计算工作日信息
	 * @author fuxin
	 * @date 2018年1月27日 下午3:23:07
	 * @description 
	 *		TODO
	 * @param selectDate
	 * @param localcode
	 * @return
	 *
	 */
	public List<SysWorkingHours> getByDate(Date selectDate, String localcode) {
		SysWorkingHours sysWorkingHours = new SysWorkingHours();
		sysWorkingHours.setLocalCode(localcode);
		sysWorkingHours.setDateDay(DateUtils.getWeekInt(selectDate)+"");
		sysWorkingHours.setOrderBy(" a.DATE_DAY, a.TIME_SLICE ");
		List<SysWorkingHours> list = findList(sysWorkingHours);
		countRemindNum(list, selectDate, localcode);
		return list;
	}
	
	/**
	 * 计算时间段预约数量
	 * @author fuxin
	 * @date 2018年1月27日 下午7:15:48
	 * @description 
	 *		TODO
	 * @param list
	 * @param selectDate
	 * @param localcode
	 * @return
	 *
	 */
	public List<SysWorkingHours> countRemindNum(List<SysWorkingHours> list, Date selectDate, String localcode){
		//计算
		VacChildRemind tempRemind = new VacChildRemind();
		tempRemind.setLocalCode(localcode);
		tempRemind.setRemindDate(selectDate);
		List<VacChildRemind> reminds = childRemindDao.findList(tempRemind);
		Map<Object, List<VacChildRemind>> remindMap = CommonUtils.getTreeDateByParam(VacChildRemind.class, reminds, "selectTime");
		for(SysWorkingHours sh : list){
			List<VacChildRemind> li = remindMap.get(sh.getTimeSlice());
			if(li != null){
				sh.setNum(remindMap.get(sh.getTimeSlice()).size());
				sh.setPercent(Float.parseFloat(sh.getNum()+"")/sh.getMaximum());
				if(sh.getNum() >= sh.getMaximum()){
					sh.setPercent(1);
				}
			}else{
				sh.setNum(0);
				sh.setPercent(0);
			}
		}
		return list;
	}
	
}