/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manage_stock_in.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.filefilter.MagicNumberFileFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.JSONMessage;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.enter.entity.SysEnterpriseInfo;
import com.thinkgem.jeesite.modules.manage_stock_in.dao.ManageStockInDao;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.ManageStockIn;
import com.thinkgem.jeesite.modules.product.dao.BsManageProductDao;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.entity.BsProductDO;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;

/**
 * 疫苗入库记录Service
 * @author 王德地
 * @version 2017-02-08
 */
@Service
@Transactional(readOnly = true)
public class ManageStockInService extends CrudService<ManageStockInDao, ManageStockIn> {
	
	@Autowired
	private BsManageProductDao bsManageProductDao;
	@Autowired
	private ManageStockInDao manageStockInDao;

	public ManageStockIn get(String id) {
		return super.get(id);
	}
	
	public List<ManageStockIn> findList(ManageStockIn manageStockIn) {
		return super.findList(manageStockIn);
	}
	
	public Page<ManageStockIn> findPage(Page<ManageStockIn> page, ManageStockIn manageStockIn) {
		return super.findPage(page, manageStockIn);
	}
	
	@Transactional(readOnly = false)
	public void save(ManageStockIn manageStockIn) {
		super.save(manageStockIn);
	}
	
	@Transactional(readOnly = false)
	public void delete(ManageStockIn manageStockIn) {
		super.delete(manageStockIn);
	}

	/**
	 * 补全产品信息
	 * @author fuxin
	 * @date 2017年9月13日 下午7:18:03
	 * @description 
	 *		TODO
	 * @param in
	 * @param vaccs
	 * @param coms
	 *
	 */
	public ManageStockIn updateProduct(ManageStockIn in, List<BsManageVaccine> vaccs, List<SysEnterpriseInfo> coms) {
		if(in == null || in.getProduct() == null){
			return in;
		}
		BsManageProduct product = in.getProduct();
		product.setDosage((long)product.getSpec());
		product.setIsshow(BsManageProduct.IS_SHOW_YES);
		for(BsManageVaccine vacc: vaccs){
			if(vacc.getId().equals(product.getVaccineid())){
				product.setVaccName(vacc.getName());
				product.setCodeall(vacc.getCodeAll());
			}
		}
		for(SysEnterpriseInfo com: coms){
			if(com.getCode().equals(product.getCode())){
				product.setManufacturer(com.getName());
			}
		}
		//product.setOfficeCode(UserUtils.getUser().getOffice().getCode());
		in.setIndate(new Date());
		return in;
		
	}

	/**
	 * 保存上级平台下发数据
	 * @author fuxin
	 * @date 2017年9月15日 上午10:51:54
	 * @description 
	 *		TODO
	 * @param productDO
	 * @param now
	 *
	 */
	public void stockInFromEpi(BsProductDO productDO, Date now) throws Exception {
		BsManageProduct product = new BsManageProduct();
		product.setId(IdGen.uuid());
		product.setEpiId(productDO.getId());
		product.setVaccineid(productDO.getBsVaccineBatchno().getVaccineId());
		product.setBatchno(productDO.getBsVaccineBatchno().getBatchno());
//		product.setDosage(1l);
		product.setDosage(Long.valueOf(productDO.getBsVaccineBatchno().getSpec()));
		product.setIsforeign(productDO.getcType());
		product.setStorenum(productDO.getStorenum());
		product.setSellprice(productDO.getSellprice());
		product.setIsshow(BsManageProduct.IS_SHOW_YES);
		product.setVaccName(productDO.getBsVaccineBatchno().getVaccineName());
		product.setVaccExpDate(productDO.getBsVaccineBatchno().getOutBoundDate());
		product.setManufacturer(productDO.getBsVaccineBatchno().getCompanyName());
		product.setCode(productDO.getBsVaccineBatchno().getCompanyCode());
		product.setSpecification(productDO.getBsVaccineBatchno().getDose());
		try {
			product.setSpec(Integer.valueOf(productDO.getBsVaccineBatchno().getSpec()));
		} catch (Exception e) {
			product.setSpec(1);
		}
		product.setCostprice(productDO.getOrignprice());
		product.setCreateBy(UserUtils.getUser());
		product.setUpdateBy(UserUtils.getUser());
		product.setCreateDate(now);
		product.setUpdateDate(now);
		product.setOfficeCode(productDO.getRooms());
		bsManageProductDao.insert(product);		
		
		ManageStockIn manageStockIn = new ManageStockIn();
		manageStockIn.setId(IdGen.uuid());
		manageStockIn.setIndate(now);
		manageStockIn.setNum(productDO.getStorenum());
		manageStockIn.setType(ManageStockIn.TYPE_IN);
		manageStockIn.setOrderNo(productDO.getOrderno());
		manageStockIn.setProduct(product);
		manageStockIn.setRemarks("上级平台下发");
		manageStockIn.setCreateBy(UserUtils.getUser());
		manageStockIn.setUpdateBy(UserUtils.getUser());
		manageStockIn.setCreateDate(now);
		manageStockIn.setUpdateDate(now);
		manageStockInDao.insert(manageStockIn);
	}

	/**
	 * 保存出库记录失败
	 * @author fuxin
	 * @date 2018年1月12日 下午11:48:39
	 * @description 
	 *		TODO
	 * @param manageStockIn
	 * @return 
	 *
	 */
	@Transactional(readOnly=false)
	public Map<String, String> saveStockOut(ManageStockIn manageStockIn) {
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("code", "500");
		manageStockIn.setIndate(new Date());
		save(manageStockIn);
		BsManageProduct product = bsManageProductDao.get(manageStockIn.getProduct());
		if(product == null){
			returnMap.put("msg", "疫苗信息错误,请重试");
			logger.error("出库信息保存失败,疫苗信息不存在["+manageStockIn.getProduct().getId() + "]");
			return returnMap;
		}
		product.setStorenum(product.getStorenum() - manageStockIn.getNum());
		product.preUpdate();
		bsManageProductDao.update(product);
		logger.info("==出库信息保存成功" + JsonMapper.toJsonString(product));
		//调剂
		if(ManageStockIn.STATE_EXCHANGE.equals(manageStockIn.getState())){
			logger.info("==出库类型为调剂");
			//生成入库记录
			ManageStockIn si = new ManageStockIn();
			BeanUtils.copyProperties(manageStockIn, si);
			si.setType(ManageStockIn.TYPE_IN);
			si.preInsert();
			BsManageProduct fromProduct = bsManageProductDao.get(new BsManageProduct(product.getFromId()));
			if(StringUtils.isNotBlank(product.getFromId()) && fromProduct.getOfficeCodeDb().equals(manageStockIn.getRoomCode())){
				//原路返回，则直接回退库存
				fromProduct.setStorenum(fromProduct.getStorenum() + manageStockIn.getNum());
				bsManageProductDao.update(fromProduct);
				si.setProduct(fromProduct);
				logger.info("==出库调剂原路退回成功,退回id为" + fromProduct.getId());
			}else{
				//生成新产品信息
				BsManageProduct toProduct = new BsManageProduct();
				BeanUtils.copyProperties(product, toProduct);
				toProduct.setStorenum(manageStockIn.getNum());
				toProduct.setOfficeCode(manageStockIn.getRoomCode());
				toProduct.setId(StringUtils.EMPTY);
				toProduct.preInsert();
				toProduct.setFromId(product.getId());
				bsManageProductDao.insert(toProduct);
				si.setProduct(toProduct);
				logger.info("==出库调剂保存成功,新纪录为" + toProduct.getId());
			}
			dao.insert(si);
		}
		returnMap.put("code", "200");
		returnMap.put("msg", "出库保存成功");
		logger.info("==出库保存成功" + JsonMapper.toJsonString(returnMap));
		return returnMap;
		
	}
}