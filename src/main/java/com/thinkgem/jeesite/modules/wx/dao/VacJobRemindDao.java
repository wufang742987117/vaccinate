/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wx.entity.VacJobRemind;

/**
 * 微信定时提醒DAO接口
 * @author zhouqj
 * @version 2017-04-26
 */
@MyBatisDao
public interface VacJobRemindDao extends CrudDao<VacJobRemind> {
	
}