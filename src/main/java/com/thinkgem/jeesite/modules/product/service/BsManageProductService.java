/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.product.dao.BsManageProductDao;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.entity.ProductUseList;
import com.thinkgem.jeesite.modules.product.entity.RepertorySum;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

/**
 * 疫苗信息Service
 * 
 * @author 王德地
 * @version 2017-02-20
 */
@Service
@Transactional(readOnly = true)
public class BsManageProductService extends CrudService<BsManageProductDao, BsManageProduct> {
	
	public BsManageProduct get(String id) {
		return super.get(id);
	}

	public List<BsManageProduct> findList(BsManageProduct bsManageProduct) {
		return super.findList(bsManageProduct);
	}

	public Page<BsManageProduct> findPage(Page<BsManageProduct> page, BsManageProduct bsManageProduct) {
		return super.findPage(page, bsManageProduct);
	}

	@Transactional(readOnly = false)
	public void save(BsManageProduct bsManageProduct) {
		super.save(bsManageProduct);
	}

	@Transactional(readOnly = false)
	public void delete(BsManageProduct bsManageProduct) {
		super.delete(bsManageProduct);
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月24日 下午3:09:21
	 * @description 查询可以领用的疫苗种类
	 * @return
	 *
	 */
	public List<BsManageProduct> type() {
		return  dao.type(new BsManageProduct());
	}
	

	/**
	 * 查询疫苗名称和库存
	 * 
	 * @author zhouqj
	 * @date 2017年3月28日 上午9:51:30
	 * @description TODO
	 * @param vaccineStr
	 *
	 */
	public List<BsManageProduct> getQueneHeadNumPad() {
		return dao.getQueneHeadNumPad(new BsManageProduct());
	}
	
	/**
	 * 根据模型大类查询所有小类，库存大于0，不分科室
	 * @author fuxin
	 * @date 2017年7月20日 上午12:42:12
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	public List<BsManageProduct> findByMnum(String code) {
		return findByMnum(code,BsManageProduct.SHOWALL_YES, 0);
	}
	
	/**
	 * 根据模型大类查询所有小类，库存大于0，不分科室，设置预留库存
	 * @author fuxin
	 * @date 2018年1月5日 下午7:52:48
	 * @description 
	 *		TODO
	 * @param code	模型大类编号
	 * @param obligate	预留库存
	 * @return
	 *
	 */
	public List<BsManageProduct> findByMnum(String code, int obligate) {
		return findByMnum(code,BsManageProduct.SHOWALL_YES, obligate);
	}
	
	/**
	 * 根据模型大类查询所有小类，库存大于0-分科室
	 * @author fuxin
	 * @date 2017年11月28日 下午1:24:42
	 * @description 
	 *		TODO
	 * @param code
	 * @param showAll BsManageProduct.YES_NO_Y(1)-显示全部 BsManageProduct.YES_NO_N(0)显示本科室
	 * @param obligate 库存预留数量
	 * @return
	 *
	 */
	public List<BsManageProduct> findByMnum(String code, String showAll,int obligate) {
		BsManageProduct bsManageProduct = new BsManageProduct();
		bsManageProduct.setCode(code);
		bsManageProduct.setShowAll(showAll);
		bsManageProduct.setObligate(obligate);
		return dao.findByMnum(bsManageProduct);
	}
	
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
	public List<RepertorySum> repertorySum(BsManageProduct bsmanageproduct) {
		if(StringUtils.isNoneBlank(bsmanageproduct.getBatchno())&&StringUtils.isBlank(bsmanageproduct.getVaccineid())&&StringUtils.isBlank(bsmanageproduct.getBigcode())){
			return dao.repertorySumBatch(bsmanageproduct);
			
		}else if(StringUtils.isNoneBlank(bsmanageproduct.getBatchno())){
			return dao.repertorySum(bsmanageproduct);
		}
		
		if(StringUtils.isNoneBlank(bsmanageproduct.getVaccineid())){
			return dao.repertorySumSmall(bsmanageproduct);
		}
		return dao.repertorySumBig(bsmanageproduct);
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 下午2:22:40
	 * @description 
	 *		查询所有的小类库存
	 * @return
	 *
	 */
	public List<RepertorySum> repertorySum() {
		return dao.repertorySum1(OfficeService.getFirstOfficeCode());
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 下午2:22:43
	 * @description 
	 *		查询所有的批次
	 * @return
	 *
	 */
	public List<BsManageProduct> batch() {
		return dao.batch(OfficeService.getFirstOfficeCode());
	}
	
	/**
	 * 查询当日疫苗使用记录
	 * @author fuxin
	 * @date 2017年7月26日 下午5:20:03
	 * @description 
	 *		TODO
	 * @param record
	 * @return
	 *
	 */
	public List<BsManageProduct> findUseCount(ChildVaccinaterecord record) {
		return dao.findUseCount(record);
	}
	
	/**
	 * 清空多剂次疫苗未用完的rest
	 * @author fuxin
	 * @date 2017年8月16日 上午12:04:04
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public Long clearRest() {
		return dao.clearRest();
	}
	
	/**
	 * 库存合并后，列表查询
	 * @author fuxin
	 * @date 2017年9月14日 上午2:20:42
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findViewList(BsManageProduct bsManageProduct) {
		return dao.findViewList(bsManageProduct);
	}
	
	/**
	 * 库存合并后，列表查询
	 * @author zhouqj
	 * @date 2017年9月14日 上午2:20:42
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findViewListNo(BsManageProduct bsManageProduct) {
		return dao.findViewListNo(bsManageProduct);
	}
	
	/**
	 * 将所有rest统一替换
	 * @author fuxin
	 * @date 2017年9月14日 上午3:09:08
	 * @description 
	 *		TODO
	 * @param product
	 *
	 */
	public void updateRest(BsManageProduct product) {
		dao.updateRest(product);
	}
	
	/**
	 * 获取可用的所有疫苗小类信息
	 * @author fuxin
	 * @date 2017年9月14日 下午3:02:13
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageProduct> findVaccinateAble(BsManageProduct bsManageProduct) {
		return findVaccinateAble(bsManageProduct, BsManageProduct.SHOWALL_NO);
	}
	
	/**
	 * 获取可用的所有疫苗小类信息
	 * @author fuxin
	 * @date 2017年11月29日 上午10:45:13
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageProduct> findVaccinateAble(BsManageProduct bsManageProduct, String showAll) {
		bsManageProduct.setShowAll(showAll);
		return dao.findVaccinateAble(bsManageProduct);
	}
	
	/**
	 * 减库存
	 * @author fuxin
	 * @date 2017年9月14日 下午4:25:47
	 * @description 
	 *		TODO
	 * @param id
	 * @param num
	 *
	 */
	@Transactional(readOnly=false)
	public void minusStock(String id, Long num) {
		List<BsManageProduct> products = findAllById(id);
		for(BsManageProduct bsp : products){
			if(num > bsp.getStorenum()){
				num = num - bsp.getStorenum();
				bsp.setStorenum(0l);
				save(bsp);
			}else{
				bsp.setStorenum(bsp.getStorenum() - num);
				num = 0l;
				save(bsp);
			}
						
			if(num == 0){
				break;
			}
			
		}
		if(num > 0){
			throw new RuntimeException("疫苗出库异常,出库数量大于库存数");
		}
	}
	
	/**
	 * 根据首个id查询出同类产品集合
	 * @author fuxin
	 * @date 2017年9月14日 下午4:29:48
	 * @description 
	 *		TODO
	 * @param id
	 * @return
	 *
	 */
	public List<BsManageProduct> findAllById(String id) {
		BsManageProduct p = super.get(id);
		if(p == null){
			return null;
		}
		BsManageProduct temp = new BsManageProduct();
		temp.setVaccineid(p.getVaccineid());
		temp.setCode(p.getCode());
		temp.setBatchno(p.getBatchno());
		temp.setOfficeCode(p.getOfficeCodeDb());
		temp.setOrderBy("a.create_date");
		temp.setStorenumIsNull(true);
		return findList(temp);
	}
	
	/**
	 * 查询所有批号接口
	 * @author fuxin
	 * @date 2017年9月17日 下午11:13:02
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findAllBatch(BsManageProduct bsManageProduct) {
		return dao.findAllBatch(bsManageProduct);
	}
	
	public Double getMaxPriceByMNum(String group) {
		return dao.getMaxPriceByMNum(group);
	}

	public List<Double> getPriceByMNum(String group) {
		return dao.getPriceByMNum(group, OfficeService.getFirstOfficeCode());
	}

	/**
	 * 减库存
	 * @author zhouqj
	 * @date 2017年9月14日 下午4:25:47
	 * @description 
	 *		TODO
	 * @param id
	 * @param num
	 *
	 */
	@Transactional(readOnly=false)
	public Boolean minusStockIn(String id, Long num) {
		List<BsManageProduct> products = findAllById(id);
		for(BsManageProduct bsp : products){
			if(num > bsp.getStorenum()){
				num = num - bsp.getStorenum();
				bsp.setStorenum(0l);
				save(bsp);
			}else{
				bsp.setStorenum(bsp.getStorenum() - num);
				num = 0l;
				save(bsp);
			}
			if(num == 0){
				break;
			}
		}
		if(num > 0){
			if(products.size() > 0){
				products.get(0).setStorenum(products.get(0).getStorenum() - num);
				save(products.get(0));
			}
			return false;
		}
		return true;
	}

	/**
	 * 查询参与排号的所有疫苗
	 * @author fuxin
	 * @date 2017年11月29日 下午11:11:23
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findQueueViewListApi(BsManageProduct bsManageProduct) {
		List<BsManageProduct> list = dao.findQueueViewListApi(bsManageProduct);
		if(bsManageProduct.isExceptQuene()){
			List<BsManageProduct> qpro = dao.getQueneProduct(new BsManageProduct());
			for(int i = 0; i < list.size(); i++){
				for(BsManageProduct q : qpro){
					if(list.get(i).getId().equals(q.getId())){
						list.get(i).setStorenum(list.get(i).getStorenum() - q.getStorenum());
						if(list.get(i).getStorenum() == 0){
							list.remove(i--);
							break;
						}
					}
				}
			}
		}
		return dao.findQueueViewListApi(bsManageProduct);
	}

	/**
	 * 通过预约信息匹配疫苗产品信息
	 * @author fuxin
	 * @date 2017年12月8日 下午3:32:21
	 * @description 
	 *		TODO
	 * @param remind
	 * @return
	 *
	 */
	public BsManageProduct findByRemind(VacChildRemind remind, String localcode) {
		String vid = remind.getVaccId();
		if(StringUtils.isBlank(vid)){
			return null;
		}
		BsManageProduct tempPro = new BsManageProduct();
		tempPro.setLocalCode(remind.getLocalCode());
		List<BsManageProduct> products = null;
		if(vid.length() == 4){
			tempPro.setVaccineid(remind.getVaccId());
			tempPro.setOrderBy("a.create_date");
			tempPro.setShowAll(BsManageProduct.SHOWALL_YES);
			tempPro.setLocalCode(localcode);
			products = findQueueViewListApi(tempPro);
			if(products != null && products.size() > 0){
				return products.get(0);
			}
		}else if(vid.length() == 2){
			tempPro.setOrderBy("a.create_date");
			tempPro.setShowAll(BsManageProduct.SHOWALL_YES);
			tempPro.setLocalCode(localcode);
			products = findQueueViewListApi(tempPro);
			for(int i = 0; i < products.size(); i ++){
				if(vid.equals(products.get(i).getVaccinate().getmNum())){
					return products.get(i);
				}
			}
		}
		return null;
	}

	/**
	 * 查询同一厂商的其他批号同疫苗
	 * @author zhouqj
	 * @date 2017年12月18日 上午10:01:01
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public BsManageProduct findViewListMan(BsManageProduct bsManageProduct) {
		return dao.findViewListMan(bsManageProduct);
	}
	
	/**
	 * 统计疫苗使用详情
	 * @author chenming
	 * @param page 
	 * @date 2017年12月25日 上午10:01:01
	 * @description 
	 *		TODO
	 * @param ProductUseList
	 * @return
	 *
	 */
	public List<ProductUseList> productUseList(ProductUseList productUseList) {
		return dao.productUseList(productUseList);
	}
   
	/**
	 * 查看疫苗具体使用详情
	 * @author chenming
	 * @param page 
	 * @param page 
	 * @date 2017年12月26日 上午10:01:01
	 * @description 
	 *		TODO
	 * @param ProductUseList
	 * @return
	 *
	 */
	public List<ProductUseList> childVaccinatereCord(ProductUseList productUseList) {
		return dao.childVaccinatereCord(productUseList);
	}
}