/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageBinded;
import com.thinkgem.jeesite.modules.vaccine.dao.BsManageBindedDao;

/**
 * 联合疫苗替代原则Service
 * @author fuxin
 * @version 2017-09-29
 */
@Service
@Transactional(readOnly = true)
public class BsManageBindedService extends CrudService<BsManageBindedDao, BsManageBinded> {

	public BsManageBinded get(String id) {
		return super.get(id);
	}
	
	public List<BsManageBinded> findList(BsManageBinded bsManageBinded) {
		return super.findList(bsManageBinded);
	}
	
	public Page<BsManageBinded> findPage(Page<BsManageBinded> page, BsManageBinded bsManageBinded) {
		return super.findPage(page, bsManageBinded);
	}
	
	@Transactional(readOnly = false)
	public void save(BsManageBinded bsManageBinded) {
		super.save(bsManageBinded);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsManageBinded bsManageBinded) {
		super.delete(bsManageBinded);
	}

	/**
	 * 根据联合疫苗查询替代规则
	 * @author fuxin
	 * @date 2017年9月29日 下午5:23:42
	 * @description 
	 *		TODO
	 * @param bindVaccID
	 * @return
	 *
	 */
	public List<BsManageBinded> findByBindVaccID(String bindVaccID) {
		BsManageBinded bsManageBinded = new BsManageBinded();
		bsManageBinded.setBindVaccId(bindVaccID);
		return findList(bsManageBinded);
	}
	
}