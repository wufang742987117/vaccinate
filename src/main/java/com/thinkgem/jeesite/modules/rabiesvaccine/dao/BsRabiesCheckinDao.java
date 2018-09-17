/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rabiesvaccine.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesBubble;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckin;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinStock;

/**
 * 狂犬疫苗管理DAO接口
 * @author wangdedi
 * @version 2017-02-24
 */
@MyBatisDao
public interface BsRabiesCheckinDao extends CrudDao<BsRabiesCheckin> {
	/**
	 * 根据名字或者电话或者身份证号查询所有的个案
	 * @author wangdedi
	 * @date 2017年4月24日 下午7:36:05
	 * @description 
	 *		TODO
	 * @param bsrabiescheckin
	 * @return
	 *
	 */
	public List<BsRabiesCheckin> namelist(BsRabiesCheckin bsrabiescheckin) ;
	/**
	 * 根据id查询所有的针次
	 * @author wangdedi
	 * @date 2017年4月24日 下午7:36:09
	 * @description 
	 *		TODO
	 * @param bsrabiescheckin
	 * @return
	 *
	 */
	public List<BsRabiesNum> finishpin(BsRabiesCheckin bsrabiescheckin) ;
	/**
	 * 新建卡数
	 * @author zhouqj
	 * @param bsRabiesNum 
	 * @date 2017年6月6日 上午10:39:19
	 * @description 
	 *		TODO 
	 * @return
	 *
	 */
	public int countNumOne(BsRabiesNum bsRabiesNum);
	/**
	 * 接种数
	 * @author zhouqj
	 * @param bsRabiesNum 
	 * @date 2017年6月6日 上午10:39:23
	 * @description 
	 *		TODO 
	 * @return
	 *
	 */
	public int countNumTwo(BsRabiesNum bsRabiesNum);
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 上午10:47:20
	 * @description 
	 *		TODO 根据id查询针次为0
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public List<BsRabiesNum> finishpinNot(BsRabiesCheckin bsRabiesCheckin);
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 上午10:47:20
	 * @description 
	 *		TODO 根据id查询针次不为0
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public List<BsRabiesNum> finishpinNum(BsRabiesCheckin bsRabiesCheckin);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月21日 上午8:44:34
	 * @description 
	 *	儿童编码后四位生成规则
	 * @return
	 *
	 */
	public String bianmadog(@Param("year")String year, @Param("localCode") String localCode);
	
	/**
	 * 查询用户签字
	 * @author zhouqj
	 * @date 2017年7月28日 下午6:40:27
	 * @description 
	 *		TODO 
	 * @param bsRabiesCheckin
	 *
	 */
	public BsRabiesCheckin querySignature(BsRabiesCheckin bsRabiesCheckin);
	
	/**
	 * 修改付款状态为1
	 * @author zhouqj
	 * @date 2017年8月10日 下午5:27:27
	 * @description 
	 *		TODO
	 * @param checkId
	 *
	 */
	public void updateByPayStatus(@Param("id")String checkId);
	
	/**
	 * 修改付款状态为1
	 * @author yangjian
	 * @date 2018年3月6日 
	 * @description 
	 *		TODO
	 * @param checkId
	 *
	 */
	public void updateDealByPayStatus(@Param("id")String checkId);
	
	/**
	 * 删除记录
	 * @author zhouqj
	 * @date 2017年8月15日 上午9:59:22
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	public void deleteCheckId(BsRabiesNum bsRabiesNum);
	
	/**
	 * 每天定时删除bs_rabies_pay表中的数据
	 * @author xuejinshan
	 * @date 2017年8月22日 上午9:49:41
	 * @description 
	 *		TODO
	 *
	 */
	public void deleteDataFromBsRabiesPayEveryday();
	
	/**
	 * 查询建档是否重复
	 * @author zhouqj
	 * @date 2017年9月9日 下午1:11:33
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public int countRabiesCode(BsRabiesCheckin bsRabiesCheckin);
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月6日 上午11:25:01
	 * @description 查询疫苗名称
	 * @param vname
	 * @return
	 * 
	 */
	public String vaccineName(@Param("vname")String vname, @Param("localCode") String localCode);
	
	/**
	 * 
	 * @author zhouqj
	 * @param map
	 * @date 2017年4月18日 上午10:06:36
	 * @description 查询数据库的狂犬疫苗种类
	 * @return
	 * 
	 */
	public List<BsManageProduct> vaccineType(@Param("localCode") String localCode,@Param("officeCode") String officeCode);
	
	/**
	 * 查询蛋白所有的种类
	 * @author zhouqj
	 * @date 2017年8月5日 下午4:13:08
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<String> vaccById(@Param("localCode") String localCode);
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月19日 下午2:21:18
	 * @description TODO 查询犬伤疫苗现有库存
	 * @return
	 * 
	 */
	public List<BsRabiesCheckinStock> vaccineStock(@Param("localCode") String localCode,@Param("officeCode") String officeCode);
	
	/**
	 * 
	 * @author zhouqj
	 * @param bsRabiesCheckinStock 
	 * @date 2017年4月19日 下午2:21:18
	 * @description TODO 查询未付款总量
	 * @return
	 * 
	 */
	public List<BsRabiesCheckinStock> vaccineStock1(BsRabiesCheckinStock bsRabiesCheckinStock);

	/**
	 * 
	 * @author zhouqj
	 * @param bsRabiesCheckinStock 
	 * @date 2017年4月19日 下午2:21:18
	 * @description TODO 查询已付款总量
	 * @return
	 * 
	 */
	public List<BsRabiesCheckinStock> vaccineStock2(BsRabiesCheckinStock bsRabiesCheckinStock);

	/**
	 * 
	 * @author zhouqj
	 * @param bsRabiesCheckinStock 
	 * @date 2017年4月19日 下午2:21:18
	 * @description TODO 查询已完成总量
	 * @return
	 * 
	 */
	public List<BsRabiesCheckinStock> vaccineStock3(BsRabiesCheckinStock bsRabiesCheckinStock);
	
	/**
	 * 查询疫苗配置
	 * @author zhouqj
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param vaccType
	 * @param status
	 * @param firstOfficeCode
	 * @param count
	 * @return
	 *
	 */
	public VaccInfo queryVaccInterVal(@Param("vaccType") String vaccType, @Param("status") String status, @Param("localCode") String localCode, @Param("count") String count);
	
	/**
	 * 添加犬伤气泡队列
	 * @author yangjian
	 * @date @date 2018年2月23日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param bsRabiesBubble
	 * @return
	 *
	 */
	public void insertBsRabiesBubble(BsRabiesBubble bsRabiesBubble);
	
	/**
	 * 查询犬伤气泡队列
	 * @author yangjian
	 * @date @date 2018年2月23日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param bsRabiesBubble
	 * @return List
	 *
	 */
	public List<BsRabiesBubble> findBsRabiesBubble(BsRabiesBubble bsRabiesBubble);
	
	/**
	 * 删除犬伤气泡队列
	 * @author yangjian
	 * @date @date 2018年2月23日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param bsRabiesBubble
	 * @return
	 */
	public void deleteBsRabiesBubble(BsRabiesBubble bsRabiesBubble);
	
	/**
	 * 查询所有气泡
	 * @author yangjian
	 * @date 2018年2月23日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param bsRabiesBubble
	 * @return
	 */
	public List<BsRabiesBubble> findBubbleList(BsRabiesBubble bsRabiesBubble);
	
	/**
	 * 每天定时删除表中的数据
	 * @author yangjian
	 * @date 2018年2月26日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param 
	 * @return
	 */
	public void deleteBubble();
	
}