/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.num.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesSocket;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinDeal;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinPay;

/**
 * 狂犬疫苗针次DAO接口
 * 
 * @author wangdedi
 * @version 2017-03-06
 */
@MyBatisDao
public interface BsRabiesNumDao extends CrudDao<BsRabiesNum> {

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月14日 下午1:41:32
	 * @description 
	 *		TODO 查询犬伤疫苗数据
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesNum> findList2(BsRabiesNum bsRabiesNum);

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月19日 下午5:16:28
	 * @description TODO 查询产品pid
	 * @param map
	 * @return
	 * 
	 */
	BsManageProduct queryBsProductPId(Map<String, String> map);

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月6日 下午2:01:50
	 * @description TODO 疫苗消耗数
	 * @param bsRabiesNum
	 * @return
	 * 
	 */
	List<BsRabiesSocket> socketlist(BsRabiesNum bsRabiesNum);

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月6日 下午2:27:23
	 * @description TODO 接种统计数
	 * @param bsRabiesNum
	 * @return
	 * 
	 */
	List<BsRabiesSocket> socketlistUp(BsRabiesNum bsRabiesNum);

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月7日 下午4:32:18
	 * @description TODO 查询用户id
	 * @param createByName
	 * @return
	 * 
	 */
	String queryCreateById(String createByName);

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 上午10:19:48
	 * @description TODO 犬伤接种针次为0的数据
	 * @param bsRabiesNum
	 * @return
	 * 
	 */
	List<BsRabiesNum> findListVaccinumNot(BsRabiesNum bsRabiesNum);

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 上午10:20:27
	 * @description TODO 犬伤接种针次不为0的数据
	 * @param bsRabiesNum
	 * @return
	 * 
	 */
	List<BsRabiesNum> findListVaccinum(BsRabiesNum bsRabiesNum);

	/**
	 * 查询该时间应种针数
	 * @author zhouqj
	 * @date 2017年6月26日 下午2:02:15
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesNum> queryBsNumList0(BsRabiesNum bsRabiesNum);
	
	/**
	 * 查询全部免疫蛋白
	 * @author xuejinshan
	 * @date 2017年9月1日 下午6:48:25
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesNum> queryBsNumListDB(BsRabiesNum bsRabiesNum);

	/**
	 * 查询狂免疫苗
	 * @author zhouqj
	 * @date 2017年7月7日 下午6:05:43
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesNum> findListUp(BsRabiesNum bsRabiesNum);

	/**
	 * 查询除了蛋白的接种记录
	 * @author zhouqj
	 * @date 2017年7月12日 上午11:27:41
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesNum> findListDown(BsRabiesNum bsRabiesNum);

	/**
	 * 签字插入
	 * @author zhouqj
	 * @date 2017年7月13日 下午4:39:49
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	void insertSignature(BsRabiesNum bsRabiesNum);

	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年7月17日 下午3:32:31
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	void updateSignatures(BsRabiesNum bsRabiesNum);

	/**
	 * 查询全部
	 * @author zhouqj
	 * @date 2017年7月21日 上午10:20:38
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesNum> queryBsNumListOut(BsRabiesNum bsRabiesNum);

	/**
	 * 用户告知书签字
	 * @author zhouqj
	 * @date 2017年7月28日 上午9:29:38
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	void updateCheckSId(BsRabiesNum bsRabiesNum);

	/**
	 * 查询该记录签字是否存在
	 * @author zhouqj
	 * @date 2017年7月28日 下午8:10:12
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	int querySignature(BsRabiesNum bsRabiesNum);

	/**
	 * 查询疫苗当前库存
	 * @author zhouqj
	 * @date 2017年8月5日 下午3:28:32
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesSocket> storeList(BsRabiesNum bsRabiesNum);

	/**
	 * 已付款数
	 * @author zhouqj
	 * @date 2017年8月20日 下午1:29:55
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesSocket> vaccineStock2(BsRabiesNum bsRabiesNum);

	/**
	 * 插入pay表
	 * @author zhouqj
	 * @date 2017年8月21日 下午5:39:28
	 * @description 
	 *		TODO
	 * @param id
	 * @return 
	 *
	 */
	void insertPay(BsRabiesCheckinPay bsRabiesCheckinPay);

	/**
	 * 查询记录id
	 * @author zhouqj
	 * @date 2017年8月21日 下午6:06:52
	 * @description 
	 *		TODO
	 * @param id
	 * @return
	 *
	 */
	String queryPay(String id);

	/**
	 * 查询已完成并且已减库存记录
	 * @author zhouqj
	 * @date 2017年9月9日 上午11:01:00
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<BsRabiesNum> findByCheckId(BsRabiesNum bsRabiesNum);

	/**
	 * 获取当前未种并且未签字针次
	 * @author zhouqj
	 * @date 2017年10月18日 上午10:02:06
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	List<String> findByChenkWxId(BsRabiesNum bsRabiesNum);

	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年11月14日 下午5:09:45
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	void updateSignStatus(BsRabiesNum bsRabiesNum);

	/**
	 * 记录退款
	 * @author zhouqj
	 * @date 2018年1月16日 上午9:23:18
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	void refundById(BsRabiesNum bsRabiesNum);

	/**
	 * 记录退款
	 * @author yangjian
	 * @date 2018年3月6日 
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckinDeal
	 *
	 */
	void refundDealById(BsRabiesCheckinDeal bsRabiesCheckinDeal);
	/**
	 * 保存调价信息
	 * @author zhouqj
	 * @date 2018年1月24日 下午3:11:45
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	void saveAdjustment(BsRabiesNum bsRabiesNum);
	
	/**
	 * 获取当天接种完成数量
	 * @author zhouqj
	 * @date 2018年1月24日 下午3:11:45
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 */
	public Integer getRabiesNumCount();
	
	/**
	 * 查询当前犬伤用户伤口处理费用
	 * @author yangjian
	 * @date 2018年3月5日 下午13:11:45
	 * @description 
	 *		TODO
	 * @param checkinId
	 * @param localCode
	 */
	public List<BsRabiesCheckinDeal> findCheckinDeal(BsRabiesCheckinDeal beBsRabiesCheckinDeal);
	
	/**
	 * 添加伤口处理费用记录
	 * @author yangjian
	 * @date 2018年3月5日 下午13:11:45
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckinDeal
	 */
	public void insertCheckinDeal(BsRabiesCheckinDeal bsRabiesCheckinDeal);

}