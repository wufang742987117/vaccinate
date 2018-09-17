/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inoculate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.vo.QueneVo;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;

/**
 * 排队叫号管理DAO接口
 * @author fuxin
 * @version 2017-02-14
 */
@MyBatisDao
public interface QueneDao extends CrudDao<Quene> {

	/**
	 * 获取一个最新排号
	 * @author fuxin
	 * @date 2017年2月24日 下午1:52:13
	 * @description 
	 *		TODO
	 * @param roomcode
	 * @return
	 *
	 */
	String getLastQueneCode(@Param("roomcode")String roomcode, @Param("localCode") String localCode);
	
	/**
	 * 根据儿童编号和疫苗id查询是否已经排号
	 * @author fuxin
	 * @date 2017年2月25日 下午2:23:07
	 * @description 
	 *		TODO
	 * @param map (childcode , vaccineid)
	 * @return
	 *
	 */
	List<Quene> getIfExist(Map<String, String> map);
	
	List<QueneVo> findQueneList(Map<String, Object> map);

	/**
	 * 删除队列数据
	 * @author fuxin
	 * @date 2017年2月28日 下午6:06:15
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	Long clear();

	
	/**
	 * 获取接种时间小于30分钟的儿童信息
	 * @author fuxin
	 * @date 2017年3月22日 下午3:48:32
	 * @description 
	 *		TODO
	 *
	 */
	List<HashMap<String, String>> getObserv(@Param("localCode") String localCode);
	
	/**
	 * 获取接种时间大于30分钟小于30分钟15秒留观信息
	 * @author fuxin
	 * @date 2017年3月22日 下午3:53:44
	 * @description 
	 *		TODO
	 * @return JSONObject
	 *
	 */
	List<HashMap<String, String>> getCompleteView(@Param("localCode") String localCode);
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月7日 下午7:57:17
	 * @description 
	 *		统计已完成的人数
	 * @param roomcode
	 * @return
	 *
	 */
	List<Quene> total(@Param("roomcode")String roomcode, @Param("localCode") String localCode);
	
	/**
	 * 根据大类查询所有在90天内到期的疫苗
	 * @author wangdedi
	 * @date 2017年4月24日 下午3:01:24
	 * @description 
	 *		TODO
	 * @param roomcode
	 * @return
	 *
	 */
	List<BsManageProduct> indate(Map<String, String> map);

	/**
	 * 排号退款
	 * @author zhouqj
	 * @date 2018年1月16日 下午11:00:35
	 * @description 
	 *		TODO
	 * @param queue
	 *
	 */
	void refundById(Quene queue);

	/**
	 * 调价保存
	 * @author zhouqj
	 * @date 2018年1月25日 上午10:48:54
	 * @description 
	 *		TODO
	 * @param quene
	 *
	 */
	void saveAdjustment(Quene quene);
	
	/**
	 * 获取排号人数
	 * @author yangjian
	 * @date 2018年3月2日 
	 * @description 
	 *		TODO
	 * 
	 *
	 */
	public Integer getQueneCount();
	
}