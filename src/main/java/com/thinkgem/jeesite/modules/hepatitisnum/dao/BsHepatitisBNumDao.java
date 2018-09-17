/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitisnum.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckinPay;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBSocket;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 乙肝疫苗针次信息DAO接口
 * 
 * @author xuejinshan
 * @version 2017-07-26
 */
@MyBatisDao
public interface BsHepatitisBNumDao extends CrudDao<BsHepatitisBNum> {

	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月28日 下午2:25:21
	 * @description TODO 查找针次信息
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	List<BsHepatitisBNum> findById(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 查询疫苗产品信息
	 * 
	 * @author xuejinshan
	 * @date 2017年7月29日 下午12:02:33
	 * @description TODO
	 * @param batch
	 * @param vaccineId
	 * @return
	 *
	 */
	BsManageProduct findBsProduct(Map<String, String> map);

	/**
	 * 查询乙肝疫苗数据
	 * 
	 * @author xuejinshan
	 * @date 2017年7月29日 下午5:30:52
	 * @description TODO
	 * @return
	 *
	 */
	List<BsHepatitisBNum> findHepaList(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 查询所有用户
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 上午10:11:12
	 * @description TODO
	 * @return
	 *
	 */
	List<User> getUserName();

	/**
	 * 查询乙肝疫苗接种明细
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 下午1:49:49
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	List<BsHepatitisBNum> findListDown(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 疫苗消耗数
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 下午4:45:23
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	List<BsHepatitisBSocket> socketlist(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 接种统计数  要显示已接种针刺
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 下午4:45:59
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	List<BsHepatitisBSocket> socketlistUp(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 查询用户id
	 * 
	 * @author xuejinshan
	 * @date 2017年8月3日 上午10:36:30
	 * @description TODO
	 * @param createById
	 * @return
	 *
	 */
	String queryCreateById(String createById);

	/**
	 * 查询全部乙肝疫苗
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:30:08
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	List<BsHepatitisBNum> queryBsNumListOut(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 查询该记录签字是否存在
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:35:37
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	int querySignature(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 签字插入
	 * 
	 * @author xuejinshan
	 * @date 2017年7月31日 下午3:11:47
	 * @description TODO
	 * @param bsRabiesNum
	 *
	 */
	void insertSignature(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 用户告知书签字
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:43:51
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	void updateCheckSId(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:52:34
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	void updateSignatures(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 查询乙肝疫苗实际库存 
	 * @author xuejinshan
	 * @date 2017年8月11日 下午7:13:40
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	List<BsHepatitisBSocket> storeList(BsHepatitisBNum bsHepatitisBNum);
	
	/**
	 * 根据checkId删除针次
	 * @author xuejinshan
	 * @date 2017年8月14日 下午7:26:31
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	 void deleteByCheckid(BsHepatitisBNum bsHepatitisBNum);

	 /**
	  * 已付款数
	  * @author zhouqj
	  * @date 2017年8月20日 下午2:28:22
	  * @description 
	  *		TODO
	  * @param bsHepatitisBNum
	  * @return
	  *
	  */
	List<BsHepatitisBSocket> vaccineHepaStock2(BsHepatitisBNum bsHepatitisBNum);

 	/**
	  * 根据checkid查询针次信息
	  * @author xuejinshan
	  * @date 2017年8月21日 上午10:41:39
	  * @description 
	  *		TODO
	  * @param bsHepatitisBNum
	  * @return
	  *
	  */
	List<BsHepatitisBNum> findByCheckid(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 查询已完成并且已减库存记录
	 * @author zhouqj
	 * @date 2017年9月9日 上午11:12:33
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	List<BsHepatitisBNum> findByCheckId(BsHepatitisBNum bsHepatitisBNum);

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
	void insertPay(BsHepatitisBcheckinPay bsHepatitisBcheckinPay);

	/**
	 * 获取当前未种并且未签字针次
	 * @author zhouqj
	 * @date 2017年10月18日 下午8:20:59
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	List<String> findByChenkWxId(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年11月14日 下午9:32:32
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	void updateSignStatus(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 记录退款
	 * @author zhouqj
	 * @date 2018年1月16日 上午10:00:10
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	void refundById(BsHepatitisBNum bsHepatitisBNum);

	/**
	 * 保存调价信息
	 * @author zhouqj
	 * @date 2018年1月24日 下午4:10:38
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	void saveAdjustment(BsHepatitisBNum bsHepatitisBNum);
}