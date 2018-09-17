/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.invoicing.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.invoicing.entity.BsInvoicing;
import com.thinkgem.jeesite.modules.invoicing.dao.BsInvoicingDao;

/**
 * 进销存统计Service
 * @author qjzhou
 * @version 2018-01-11
 */
@Service
@Transactional(readOnly = true)
public class BsInvoicingService extends CrudService<BsInvoicingDao, BsInvoicing> {

	public BsInvoicing get(String id) {
		return super.get(id);
	}
	
	public List<BsInvoicing> findList(BsInvoicing bsInvoicing) {
		return super.findList(bsInvoicing);
	}
	
	public Page<BsInvoicing> findPage(Page<BsInvoicing> page, BsInvoicing bsInvoicing) {
		return super.findPage(page, bsInvoicing);
	}
	
	@Transactional(readOnly = false)
	public void save(BsInvoicing bsInvoicing) {
		super.save(bsInvoicing);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsInvoicing bsInvoicing) {
		super.delete(bsInvoicing);
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年5月8日 上午9:21:33
	 * @description 
	 *		TODO 当前时间
	 * @param arges
	 * @return
	 *
	 */
	public Date[] dateTime(Date... arges){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (arges[0] == null) {
			c.set(Calendar.HOUR_OF_DAY,00);
			c.set(Calendar.MINUTE,00);
			c.set(Calendar.SECOND, 00);
			c.set(Calendar.MILLISECOND, 000);
			arges[0] = c.getTime();
		}
		//判断结束时间，如果结束时间为空则默认当前时间
		if (arges[1] == null) {
			c.set(Calendar.HOUR_OF_DAY,23);
			c.set(Calendar.MINUTE,59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			arges[1] = c.getTime();
		}
		// 确保beginTime在endTime之前
		if (arges[0].compareTo(arges[1]) == 0||arges[1] != null) {
			arges = updateDate(arges);
		}
		if (arges[0].compareTo(arges[1])>0) {
			Date argess = null;
			argess = arges[0];
			arges[0] = arges[1];
			arges[1] = argess;
			arges = updateDate(arges);
		}
		return arges;
	}
	
	/**
	 * 转换时分秒
	 * @author zhouqj
	 * @date 2017年9月14日 下午4:09:35
	 * @description 
	 *		TODO
	 * @param arges
	 * @return
	 *
	 */
	private Date[] updateDate(Date... arges) {
		Calendar day = Calendar.getInstance();
		day.setTime(arges[1]);
		day.set(Calendar.HOUR_OF_DAY,23);
		day.set(Calendar.MINUTE,59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		arges[1] = day.getTime();
		day.setTime(arges[0]);
		day.set(Calendar.HOUR_OF_DAY,00);
		day.set(Calendar.MINUTE,00);
		day.set(Calendar.SECOND, 00);
		day.set(Calendar.MILLISECOND, 000);
		arges[0] = day.getTime();
		return arges;
	}
	
	/**
	 * 进销存实时统计，带分页
	 * @author zhouqj
	 * @date 2018年1月12日 上午10:27:49
	 * @description 
	 *		TODO
	 * @param page
	 * @param bsInvoicing
	 * @return
	 *
	 */
	public Page<BsInvoicing> findPageSql(Page<BsInvoicing> page, BsInvoicing bsInvoicing) {
		bsInvoicing.setPage(page);
		page.setList(dao.findListSql(bsInvoicing));
		return page;
	}

	/**
	 * 进销存实时统计
	 * @author zhouqj
	 * @date 2018年1月12日 上午10:27:49
	 * @description 
	 *		TODO
	 * @param page
	 * @param bsInvoicing
	 * @return
	 *
	 */
	public List<BsInvoicing> findListSql(BsInvoicing bsInvoicing) {
		return dao.findListSql(bsInvoicing);
	}
	
}