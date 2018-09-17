/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.entity.VacJobRemind;
import com.thinkgem.jeesite.modules.wx.dao.VacJobRemindDao;

/**
 * 微信定时提醒Service
 * @author zhouqj
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = true)
public class VacJobRemindService extends CrudService<VacJobRemindDao, VacJobRemind> {

	public VacJobRemind get(String id) {
		return super.get(id);
	}
	
	public List<VacJobRemind> findList(VacJobRemind vacJobRemind) {
		return super.findList(vacJobRemind);
	}
	
	public Page<VacJobRemind> findPage(Page<VacJobRemind> page, VacJobRemind vacJobRemind) {
		return super.findPage(page, vacJobRemind);
	}
	
	@Transactional(readOnly = false)
	public void save(VacJobRemind vacJobRemind) {
		super.save(vacJobRemind);
	}
	
	@Transactional(readOnly = false)
	public void delete(VacJobRemind vacJobRemind) {
		super.delete(vacJobRemind);
	}
	
}