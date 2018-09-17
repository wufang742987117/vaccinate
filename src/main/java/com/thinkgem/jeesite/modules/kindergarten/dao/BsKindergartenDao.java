/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kindergarten.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.kindergarten.entity.BsKindergarten;

/**
 * 儿童信息中的幼儿园DAO接口
 * @author sen
 * @version 2017-08-11
 */
@MyBatisDao
public interface BsKindergartenDao extends CrudDao<BsKindergarten> {
	
}