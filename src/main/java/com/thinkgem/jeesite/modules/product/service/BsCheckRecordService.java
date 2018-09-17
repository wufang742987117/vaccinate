/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.product.entity.BsCheckRecord;
import com.thinkgem.jeesite.modules.product.dao.BsCheckRecordDao;

/**
 * 库存盘点记录Service
 * @author fuxin
 * @version 2017-12-28
 */
@Service
@Transactional(readOnly = true)
public class BsCheckRecordService extends CrudService<BsCheckRecordDao, BsCheckRecord> {

	public BsCheckRecord get(String id) {
		return super.get(id);
	}
	
	public List<BsCheckRecord> findList(BsCheckRecord bsCheckRecord) {
		return super.findList(bsCheckRecord);
	}
	
	public Page<BsCheckRecord> findPage(Page<BsCheckRecord> page, BsCheckRecord bsCheckRecord) {
		return super.findPage(page, bsCheckRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(BsCheckRecord bsCheckRecord) {
		super.save(bsCheckRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsCheckRecord bsCheckRecord) {
		super.delete(bsCheckRecord);
	}
	
}