/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.export.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.export.entity.ExpRoutinevacc61Detail;
import com.thinkgem.jeesite.modules.export.dao.ExpRoutinevacc61DetailDao;

/**
 * 按月份统计应种实种Service
 * @author Jack
 * @version 2018-02-01
 */
@Service
@Transactional(readOnly = true)
public class ExpRoutinevacc61DetailService extends CrudService<ExpRoutinevacc61DetailDao, ExpRoutinevacc61Detail> {

	public ExpRoutinevacc61Detail get(String id) {
		return super.get(id);
	}
	
	public List<ExpRoutinevacc61Detail> findList(ExpRoutinevacc61Detail expRoutinevacc61Detail) {
		return super.findList(expRoutinevacc61Detail);
	}
	
	public Page<ExpRoutinevacc61Detail> findPage(Page<ExpRoutinevacc61Detail> page, ExpRoutinevacc61Detail expRoutinevacc61Detail) {
		return super.findPage(page, expRoutinevacc61Detail);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpRoutinevacc61Detail expRoutinevacc61Detail) {
		super.save(expRoutinevacc61Detail);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExpRoutinevacc61Detail expRoutinevacc61Detail) {
		super.delete(expRoutinevacc61Detail);
	}
	
}