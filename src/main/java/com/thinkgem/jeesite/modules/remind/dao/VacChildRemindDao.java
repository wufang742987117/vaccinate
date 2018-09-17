/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.remind.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;

/**
 * 儿童接种提醒DAO接口
 * @author fuxin
 * @version 2017-12-07
 */
@MyBatisDao
public interface VacChildRemindDao extends CrudDao<VacChildRemind> {

	/**
	 * 将预约记录设置为已完成
	 * @author fuxin
	 * @date 2017年12月10日 下午1:44:25
	 * @description 
	 *		TODO
	 * @param rid
	 *
	 */
	int finsihRemind(String rid);

	/**
	 * 保存预约信息，清除旧预约信息
	 * @author fuxin
	 * @date 2017年12月16日 下午9:53:40
	 * @description 
	 *		TODO
	 * @param vacChildRemind
	 *
	 */
	int clearOldRemind(VacChildRemind vacChildRemind);

	/**
	 * 从本地数据库读取微信签字数据
	 * @author fuxin
	 * @date 2017年12月21日 下午3:56:28
	 * @description 
	 *		TODO
	 * @param rid
	 * @return
	 *
	 */
	VacChildRemind getWxSignFromLocal(String rid);

	/**
	 * 插入微信签字表
	 * @author fuxin
	 * @date 2017年12月21日 下午7:44:36
	 * @description 
	 *		TODO
	 * @param vacRemind
	 *
	 */
	void insertWxSign(VacChildRemind vacRemind);

	/**
	 * 清除过时签字信息
	 * @author fuxin
	 * @date 2017年12月22日 下午2:15:01
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	int clearSignJob();
	
	/**
	 * 获取当天预约人数
	 * @author yangjian
	 * @date 2018年3月2日 下午2:15:01
	 * @description 
	 *		TODO
	 * @return
	 */
	public Integer getRemindCount();
	
}