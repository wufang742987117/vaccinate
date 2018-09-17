/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckin;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckinStock;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;

/**
 * 乙肝档案信息管理DAO接口
 * 
 * @author xuejinshan
 * @version 2017-07-25
 */
@MyBatisDao
public interface BsHepatitisBcheckinDao extends CrudDao<BsHepatitisBcheckin> {

	/**
	 * 根据名字或者电话或者身份证号查询所有的个案
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:59:28
	 * @description TODO
	 * @param bsHepatitisBchekin
	 * @return
	 *
	 */
	List<BsHepatitisBcheckin> namelist(BsHepatitisBcheckin bsHepatitisBcheckin);

	/**
	 * 查询所有针次信息
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 下午5:52:09
	 * @description TODO
	 * @param bsHepatitisBchekin2
	 * @return
	 *
	 */
	List<BsHepatitisBNum> finishTimes(BsHepatitisBcheckin bsHepatitisBcheckin2);

	/**
	 * 新建卡数
	 * @author xuejinshan
	 * @date 2017年8月2日 下午4:11:05
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	int countNumOne(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 接种数
	 * @author xuejinshan
	 * @date 2017年8月9日 下午4:04:22
	 * @description 
	 *		TODO
	 * @param year
	 * @return
	 *
	 */
	int countNumTwo(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 生成编码
	 * @author xuejinshan
	 * @date 2017年8月9日 下午4:04:22
	 * @description 
	 *		TODO
	 * @param year
	 * @param vaccType 
	 * @return
	 *
	 */
	public String bianmadog(@Param("year") String year, @Param("vaccType") String vaccType, @Param("localCode") String localCode);

	/**
	 * 乙肝疫苗（非蛋白）
	 * @author zhouqj
	 * @date 2017年8月10日 上午9:21:48
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	List<BsHepatitisBNum> finishpinNum(BsHepatitisBcheckin bsHepatitisBcheckin);

	/**
	 * 修改付款状态为1
	 * @author zhouqj
	 * @date 2017年8月10日 下午6:51:08
	 * @description 
	 *		TODO
	 * @param id
	 *
	 */
	void updateByPayStatus(String id);

	/**
	 *根据年龄查询规格和厂家信息s
	 * @author xuejinshan
	 * @date 2017年8月15日 下午4:48:16
	 * @description 
	 *		TODO
	 * @param specification
	 * @return
	 *
	 */
	BsHepatitisBcheckin findData(@Param("attr") String attr, @Param("localCode") String localCode, @Param("officeCode") String officeCode, @Param("vaccType") String vaccType);

	/**
	 * 查询建档是否重复
	 * @author zhouqj
	 * @date 2017年9月9日 下午1:37:38
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	int countHepaBcode(BsHepatitisBcheckin bsHepatitisBcheckin);

	/**
	 * 查询疫苗名称
	 * @author zhouqj
	 * @date 2017年9月14日 下午7:01:48
	 * @description 
	 *		TODO
	 * @param vname
	 * @return
	 *
	 */
	String vaccineName(@Param("vname") String vname, @Param("localCode") String localCode);

	/**
	 * 乙肝疫苗库存信息
	 * @author xuejinshan
	 * @date 2017年7月31日 下午7:30:34
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckinStock> vaccineHepaStock(@Param("localCode") String localCode, @Param("officeCode") String officeCode);
	
	/**
	 * 乙肝疫苗    查询未付款总量
	 * @author xuejinshan
	 * @date 2017年7月31日 下午7:30:34
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckinStock> vaccineHepaStock1(BsHepatitisBcheckinStock bsHepatitisBcheckinStock);
	
	/**
	 * 乙肝疫苗    查询已付款总量
	 * @author xuejinshan
	 * @date 2017年7月31日 下午7:30:34
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckinStock> vaccineHepaStock2(BsHepatitisBcheckinStock bsHepatitisBcheckinStock);
	
	/**
	 * 乙肝疫苗    查询已完成总量
	 * @author xuejinshan
	 * @param bsHepatitisBcheckinStock 
	 * @date 2017年7月31日 下午7:30:34
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckinStock> vaccineHepaStock3(BsHepatitisBcheckinStock bsHepatitisBcheckinStock);

	/**
	 * 
	 * @author xuejinshan
	 * @param bsManageVaccine 
	 * @date 2017年7月27日 下午2:39:43
	 * @description 查询数据库乙肝疫苗种类
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageProduct> vaccineTypeHepa(BsManageVaccine bsManageVaccine);

	/**
	 * 
	 * 查询疫苗类型配置(除犬伤)
	 * @author zhouqj
	 * @param vaccType 
	 * @date 2017年10月10日 下午1:42:10
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	List<VaccInfo> getQueryVacc(@Param("vaccType") String vaccType, @Param("localCode") String localCode);

	/**
	 * 根据疫苗type查询疫苗名称
	 * @author zhouqj
	 * @date 2017年10月11日 下午3:38:46
	 * @description 
	 *		TODO
	 * @param vaccType
	 * @return
	 *
	 */
	String getQueryVaccName(@Param("vaccType") String vaccType, @Param("localCode") String localCode);

	/**
	 * 查询疫苗配置
	 * @author zhouqj
	 * @param count 
	 * @param string 
	 * @date 2017年12月26日 下午7:11:23
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	VaccInfo queryVaccInterVal(@Param("vaccType") String vaccType, @Param("status") String status, @Param("localCode") String localCode, @Param("count") String count);
}