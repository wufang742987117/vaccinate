/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yiyuan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.yiyuan.entity.SysBirthHospital;
import com.thinkgem.jeesite.modules.yiyuan.dao.SysBirthHospitalDao;

/**
 * 信息Service
 * @author 王德地
 * @version 2017-03-21
 */
@Service
@Transactional(readOnly = true)
public class SysBirthHospitalService extends CrudService<SysBirthHospitalDao, SysBirthHospital> {

	public SysBirthHospital get(String id) {
		return super.get(id);
	}
	
	public List<SysBirthHospital> findList(SysBirthHospital sysBirthHospital) {
		if(StringUtils.isNotBlank(sysBirthHospital.getLocalCode())){
			sysBirthHospital.setLocalCodeLike(sysBirthHospital.getLocalCode().substring(0, 6));
		}
		return super.findList(sysBirthHospital);
	}
	
	public Page<SysBirthHospital> findPage(Page<SysBirthHospital> page, SysBirthHospital sysBirthHospital) {
		if(StringUtils.isNotBlank(sysBirthHospital.getLocalCode())){
			sysBirthHospital.setLocalCodeLike(sysBirthHospital.getLocalCode().substring(0, 6));
		}
		return super.findPage(page, sysBirthHospital);
	}
	
	@Transactional(readOnly = false)
	public void save(SysBirthHospital sysBirthHospital) {
		super.save(sysBirthHospital);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysBirthHospital sysBirthHospital) {
		super.delete(sysBirthHospital);
	}
	
}