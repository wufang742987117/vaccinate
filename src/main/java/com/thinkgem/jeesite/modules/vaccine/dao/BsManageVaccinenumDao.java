/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;

/**
 * 疫苗剂次管理DAO接口
 * @author fuxin
 * @version 2017-02-24
 */
@MyBatisDao
public interface BsManageVaccinenumDao extends CrudDao<BsManageVaccinenum> {
	
	public Long sel(int days);

	/**
	 * 根据模型计算计划接种的疫苗
	 * @author fuxin
	 * @date 2017年3月4日 下午5:43:28
	 * @description 
	 *		TODO
	 * @param parm
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccList(Map<String, String> parm);

	/**
	 * 获取已经接种过的计划id
	 * @author fuxin
	 * @date 2017年3月4日 下午5:26:45
	 * @description 
	 *		TODO
	 * @param code
	 * @param finish 1-不包含未完成 0包含未完成
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> findFinish(String code, int finish, String officeCode);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月10日 下午3:19:43
	 * @description 
	 *	根据模型计算计划接种的疫苗,并根据疫苗名称排序
	 * @param parm
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccNameList(Map<String, String> parm);

	
	/**
	 * 获取儿童已经接种过的记录列表
	 * @author fuxin
	 * @date 2017年4月7日 下午2:27:45
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	public List<ChildVaccinaterecord> findReservedList(@Param("code") String code, @Param("officeCode") String officeCode);
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月19日 下午5:51:59
	 * @description 
	 *		查询所有由五联替代的疫苗名字和大类编码
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> disassemble();

	/**
	 * 查询儿童未完成的接种计划
	 * @author fuxin
	 * @date 2017年11月3日 下午7:04:00
	 * @description 
	 *		TODO
	 * @param childid
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> findNotFinishNum(ChildBaseinfo info);

	/**
	 * 
	 * @author fuxin
	 * @param bsManageVaccinenum 
	 * @date 2018年1月23日 下午4:38:17
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getQueneStock(BsManageVaccinenum bsManageVaccinenum);

}