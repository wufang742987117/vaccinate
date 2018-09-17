/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	void savePreference(@Param("id") String id, @Param("pref") String pref);

	/**
	 * 根据code查询id
	 * @author zhouqj
	 * @date 2017年12月15日 下午2:43:12
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	Office getOfficeByCode(String code);

	/**
	 * 获取机构完整信息
	 * @author fuxin
	 * @date 2018年3月5日 下午4:57:18
	 * @description 
	 \*		
	 * @param code
	 * @return
	 *
	 */
	Office getByCode(String code);

	/**
	 * 根据parent_id查询子节点
	 * @author zhouqj
	 * @date 2017年12月15日 下午2:57:22
	 * @description 
	 *		TODO
	 * @param companyOffice
	 * @return
	 *
	 */
	Office getOfficeByParentId(Office companyOffice);
	
	/**
	 * 
	 * @author liyuan
	 * @date 2018年1月27日 下午1:18:08
	 * @description 
	 *		根据用户名查询用户所在的部门和部门的编号
	 * @param name
	 * @return
	 *
	 */
	List<Office> getOfficeNameAndCode(String name);
	
	
	/**
	 * 
	 * @author liyuan
	 * @date 2018年2月9日 下午1:49:19
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	List<Office> getAllOfficeByCode(String code);
}
