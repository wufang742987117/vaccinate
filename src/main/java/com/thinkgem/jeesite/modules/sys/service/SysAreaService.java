/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.SysAreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;

/**
 * 地区管理Service
 * @author 王德地
 * @version 2017-02-13
 */
@Service
@Transactional(readOnly = true)
public class SysAreaService extends CrudService<SysAreaDao, SysArea> {

//	public static final String AREA_CACHE = "area_cache";
	public static final String AREA_CACHE_ID = "area_id_";
	
	
	@Autowired
	SysAreaDao areaDao;
	
	public SysArea get(String id) {
		if(!StringUtils.isNotBlank(id) || "null".equals(id)){
			return null;
		}
		SysArea area = (SysArea)CacheUtils.get(AREA_CACHE_ID + id);
		if (area ==  null){
			area = areaDao.get(id);
			if (area == null){
				return null;
			}
			CacheUtils.put(AREA_CACHE_ID + area.getId(), area);
		}
		return area;
	}
	
	public List<SysArea> findList(SysArea sysArea) {
		return super.findList(sysArea);
	}
	
	public Page<SysArea> findPage(Page<SysArea> page, SysArea sysArea) {
		return super.findPage(page, sysArea);
	}
	
	@Transactional(readOnly = false)
	public void save(SysArea sysArea) {
		super.save(sysArea);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysArea sysArea) {
		super.delete(sysArea);
	}

	public List<Area> getByPid(int id) {
		return areaDao.gtbByPId(id);
	}

	
	/**
	 * 获取地区
	 * @author xuejinshan
	 * @date 2017年8月26日 下午4:20:45
	 * @description 
	 *		TODO
	 * @param areanum
	 * @return
	 *
	 */
	public SysArea getAreaNameById(int areanum) {
		return dao.getAreaNameById(areanum);
	}
	
}