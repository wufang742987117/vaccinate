/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.stock.entity.BsStockInOut;
import com.thinkgem.jeesite.modules.stock.dao.BsStockInOutDao;

/**
 * 进销存月报表Service
 * @author fuxin
 * @version 2017-12-17
 */
@Service
@Transactional(readOnly = true)
public class BsStockInOutService extends CrudService<BsStockInOutDao, BsStockInOut> {

	public BsStockInOut get(String id) {
		return super.get(id);
	}
	
	public List<BsStockInOut> findList(BsStockInOut bsStockInOut) {
		return super.findList(bsStockInOut);
	}
	
	public Page<BsStockInOut> findPage(Page<BsStockInOut> page, BsStockInOut bsStockInOut) {
		return super.findPage(page, bsStockInOut);
	}
	
	@Transactional(readOnly = false)
	public void save(BsStockInOut bsStockInOut) {
		super.save(bsStockInOut);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsStockInOut bsStockInOut) {
		super.delete(bsStockInOut);
	}
	
}