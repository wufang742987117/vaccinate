/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;

/**
 * 地区管理DAO接口
 * @author 王德地
 * @version 2017-02-13
 */
@MyBatisDao
public interface SysAreaDao extends CrudDao<SysArea> {
	/**
	 * 根据父级区域查询
	 * @return
	 */
	public List<Area> gtbByPId(int id);

	
	/**
	 * 根据id获取地区信息
	 * @author xuejinshan
	 * @date 2017年8月26日 下午4:22:31
	 * @description 
	 *		TODO
	 * @param areanum
	 * @return
	 *
	 */
	public SysArea getAreaNameById(int areanum);
}
