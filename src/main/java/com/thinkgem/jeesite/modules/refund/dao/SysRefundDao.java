/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.refund.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.refund.entity.SysRefund;

/**
 * 退款说明DAO接口
 * @author wangdedi
 * @version 2017-05-17
 */
@MyBatisDao
public interface SysRefundDao extends CrudDao<SysRefund> {
	
}