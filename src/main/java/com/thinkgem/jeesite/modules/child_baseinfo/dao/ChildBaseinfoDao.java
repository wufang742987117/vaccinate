/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_baseinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.BaseInfoRoot;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.Nursery;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.aefi;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.chhe;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.sel;

/**
 * 档案管理DAO接口
 * @author 王德地
 * @version 2017-02-06
 */
@MyBatisDao
public interface ChildBaseinfoDao extends CrudDao<ChildBaseinfo> {

	ChildBaseinfo getByNo(ChildBaseinfo info);
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月6日 上午10:14:31
	 * @description 
	 *	根据开始时间和结束时间和疫苗名称以及间隔天数查询儿童信息 
	 * @param map
	 * @return
	 *
	 */
	public List<ChildBaseinfo> sel1(sel sel);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月9日 下午6:11:55
	 * @description 
	 *	根据儿童id查询儿童接种记录并根据疫苗名称和针次排序 (一类疫苗)
	 * @param childBaseinfo
	 * @return
	 *
	 */
	public List<Nursery>  one(ChildBaseinfo childBaseinfo);
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月9日 下午6:12:11
	 * @description 
	 *	根据儿童id查询儿童接种记录并根据疫苗名称和针次排序 (二类疫苗)
	 * @param childBaseinfo
	 * @return
	 *
	 */
	public List<Nursery>  two(ChildBaseinfo childBaseinfo);
	
	/**
	 * 儿童编码后四位生成规则
	 * @author wangdedi
	 * @date 2017年3月21日 上午8:44:34
	 * @return
	 *
	 */
	public String  bianma(@Param("year")String year, @Param("localCode")String localCode);
	
	/**
	 * 犬伤编码后四位生成规则
	 * @author zhouqj
	 * @date 2017年3月21日 上午8:44:34
	 * @return
	 *
	 */
	public String  bianmadog(@Param("year")String year, @Param("localCode")String localCode);
	
	/**
	 * 根据儿童编码或者身份证号码查询儿童信息
	 * @author zhouqj
	 * @date 2017年4月11日 下午2:52:56
	 * @param childBaseinfo
	 * @return
	 *
	 */
	public List<ChildBaseinfo> findListByCode(ChildBaseinfo childBaseinfo);
	
	void insertRoot(BaseInfoRoot root);
	
	/**
	 * 
	 * <strong> 通过code查询id </strong>
	 * @author fuxin
	 * @date 2017年7月17日 下午5:53:23
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	String getIdByCode(@Param("code")String code, @Param("localCode")String localCode);
	
	
	void insertAefi(aefi f);
	
	
	void insertChhe(chhe c);
	
	void clearBaseInfo(@Param("id")String id, @Param("localCode")String localCode);
	
	void clearRecord(@Param("id")String id, @Param("localCode")String localCode);
	
	void clearAefi(@Param("code")String code, @Param("localCode")String localCode);
	
	void clearHere(@Param("code")String code, @Param("localCode")String localCode);
	
	public List<ChildBaseinfo> findOD(ChildBaseinfo childBaseinfo);
	
	/**
	 * 查询指定日期完成接种的儿童
	 * @author fuxin
	 * @date 2017年12月10日 下午7:53:56
	 * @description 
	 *		TODO
	 * @param childBaseinfo
	 * @return
	 *
	 */
	List<ChildBaseinfo> findFinishInDay(ChildBaseinfo childBaseinfo);
}