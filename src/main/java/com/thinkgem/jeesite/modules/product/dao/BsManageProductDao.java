/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.entity.ProductUseList;
import com.thinkgem.jeesite.modules.product.entity.RepertorySum;

/**
 * 疫苗信息DAO接口
 * @author 王德地
 * @version 2017-02-20
 */
@MyBatisDao
public interface BsManageProductDao extends CrudDao<BsManageProduct> {
	public BsManageProduct demand(Map<String, String> map);

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月24日 下午3:08:34
	 * @description 
	 *		查询可以领用的疫苗种类 
	 * @return
	 *
	 */
	public List<BsManageProduct> type(BsManageProduct bsManageProduct);
	
	/**
	 * 查询疫苗名称和库存
	 * @author zhouqj
	 * @date 2017年3月28日 上午9:55:39
	 * @description 
	 *		TODO
	 * @param vaccineStr
	 * @return
	 *
	 */
	public List<BsManageProduct> getQueneHeadNumPad(BsManageProduct bsManageProduct);
	
	/**
	 * 根据模型大类查询疫苗的所有小类，库存大于0
	 * @author fuxin
	 * @date 2017年7月20日 上午12:43:04
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	public List<BsManageProduct> findByMnum(BsManageProduct bsManageProduct);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 上午11:47:57
	 * @description 
	 *		库存统计，根据大类
	 * @param bsmanageproduct
	 * @return
	 *
	 */
	public List<RepertorySum> repertorySumBig(BsManageProduct bsmanageproduct);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 上午11:47:57
	 * @description 
	 *		库存统计，根据小类
	 * @param bsmanageproduct
	 * @return
	 *
	 */
	public List<RepertorySum> repertorySumSmall(BsManageProduct bsmanageproduct);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 上午11:47:57
	 * @description 
	 *		库存统计，根据批次
	 * @param bsmanageproduct
	 * @return
	 *
	 */
	public List<RepertorySum> repertorySumBatch(BsManageProduct bsmanageproduct);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 上午11:47:57
	 * @description 
	 *		库存统计，根据疫苗大类或者小类 和批次
	 * @param bsmanageproduct
	 * @return
	 *
	 */
	public List<RepertorySum> repertorySum(BsManageProduct bsmanageproduct);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 下午2:11:11
	 * @description 
	 *		查询所有的小类库存
	 * @return
	 *
	 */
	public List<RepertorySum> repertorySum1(@Param("localCode") String localCode);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 下午2:21:29
	 * @description 
	 *		查询所有的批次
	 * @return
	 *
	 */
	public List<BsManageProduct> batch(@Param("localCode") String localCode);
	
	/**
	 * 查询当日疫苗使用记录
	 * @author fuxin
	 * @date 2017年7月26日 下午5:21:01
	 * @description 
	 *		TODO
	 * @param record
	 * @return
	 *
	 */
	public List<BsManageProduct> findUseCount(ChildVaccinaterecord record);
	
	/**
	 * 清空多剂次疫苗未用完的rest
	 * @author fuxin
	 * @date 2017年8月16日 上午12:04:34
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public Long clearRest();
	
	/**
	 * 从视图中，条件查询
	 * @author fuxin
	 * @date 2017年9月14日 上午1:32:15
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findViewList(BsManageProduct bsManageProduct);
	
	/**
	 * 从视图中，条件查询
	 * @author fuxin
	 * @date 2017年9月14日 上午1:32:15
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findViewListNo(BsManageProduct bsManageProduct);
	
	
	/**
	 * 将所有rest统一替换
	 * @author fuxin
	 * @date 2017年9月14日 上午3:10:25
	 * @description 
	 *		TODO
	 * @param product
	 *
	 */
	public void updateRest(BsManageProduct product);
	
	/**
	 * 获取可用的所有疫苗小类信息
	 * @author fuxin
	 * @date 2017年9月14日 下午3:02:52
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageProduct> findVaccinateAble(BsManageProduct bsManageProduct);

	/**
	 * 查询所有批号接口
	 * @author fuxin
	 * @date 2017年9月17日 下午11:14:12
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findAllBatch(BsManageProduct bsManageProduct);
	
	/**
	 * 通过大类获取最高售价
	 * @author fuxin
	 * @date 2017年9月20日 上午7:34:06
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public Double getMaxPriceByMNum(@Param("mMun") String mMun);
	
	/**
	 * 获取group获取所有价格
	 * @author fuxin
	 * @date 2017年9月28日 上午12:08:22
	 * @description 
	 *		TODO
	 * @param group
	 * @return
	 *
	 */
	public List<Double> getPriceByMNum(@Param("group") String group,@Param("localCode") String localCode);

	
	/**
	 * 查询参与排号的所有疫苗
	 * @author fuxin
	 * @date 2017年11月29日 下午11:12:12
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findQueueViewListApi(BsManageProduct bsManageProduct);

	/**
	 * 查询同一厂商的其他批号同疫苗
	 * @author zhouqj
	 * @date 2017年12月18日 上午10:02:47
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public BsManageProduct findViewListMan(BsManageProduct bsManageProduct);
	
	/**
	 * 
	 * @author chenming
	 * @date 2017年12月25日 上午11:47:57
	 * @description 
	 *		统计疫苗库存
	 * @param ProductUseList
	 * @return
	 *
	 */
	public List<ProductUseList> productUseList(ProductUseList productUseList);
    
	/**
	 * 
	 * @author chenming
	 * @date 2017年12月26日 上午11:47:57
	 * @description 
	 *		疫苗使用详情
	 * @param ProductUseList
	 * @return
	 *
	 */
	public List<ProductUseList> childVaccinatereCord(ProductUseList productUseList);

	/**
	 * 根据id修改库存
	 * @author fuxin
	 * @date 2017年12月29日 下午5:07:19
	 * @description 
	 *		TODO
	 * @param product
	 * @return
	 *
	 */
	public int updateStock(BsManageProduct product);

	/**
	 * 获取已排号的产品数
	 * @author fuxin
	 * @date 2018年1月23日 下午7:37:42
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> getQueneProduct(BsManageProduct bsManageProduct);
	
}