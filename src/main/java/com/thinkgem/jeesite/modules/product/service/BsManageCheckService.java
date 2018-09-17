/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.product.dao.BsManageCheckDao;
import com.thinkgem.jeesite.modules.product.dao.BsManageProductDao;
import com.thinkgem.jeesite.modules.product.entity.BsManageCheck;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.vo.BsVaccineOrder2Epi;
import com.thinkgem.jeesite.modules.product.vo.BsVaccineOrderVo;
import com.thinkgem.jeesite.modules.product.vo.UploadEpiProCreateBy;
import com.thinkgem.jeesite.modules.product.vo.UploadEpiProInfo;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 每日盘库Service
 * @author fuxin
 * @version 2017-12-28
 */
@Service
@Transactional(readOnly = true)
public class BsManageCheckService extends CrudService<BsManageCheckDao, BsManageCheck> {

	@Autowired
	private BsManageProductDao bsManageProductDao;
	@Autowired
	private AsyncService asyncService;
	
	public BsManageCheck get(String id) {
		BsManageCheck bsManageCheck = super.get(id);
		return bsManageCheck;
	}
	
	public List<BsManageCheck> findList(BsManageCheck bsManageCheck) {
		return super.findList(bsManageCheck);
	}
	
	public Page<BsManageCheck> findPage(Page<BsManageCheck> page, BsManageCheck bsManageCheck) {
		return super.findPage(page, bsManageCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(BsManageCheck bsManageCheck) {
		super.save(bsManageCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsManageCheck bsManageCheck) {
		super.delete(bsManageCheck);
	}

	/**
	 * 按照日期生成盘点表
	 * @author fuxin
	 * @date 2017年12月29日 下午1:50:52
	 * @description 
	 *		TODO
	 * @param bsManageCheck
	 * @return
	 *
	 */
	public List<BsManageCheck> genCheckTable(BsManageCheck bsManageCheck) {
		bsManageCheck.setCreateBy(UserUtils.getUser());
		if(bsManageCheck.getProduct() == null){
			bsManageCheck.setProduct(new BsManageProduct());
		}
		return dao.genCheckTable(bsManageCheck);
	}

	/**
	 * 保存每日盘库
	 * @author fuxin
	 * @date 2017年12月29日 下午4:00:05
	 * @description 
	 *		TODO
	 * @param json
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	public Map<String, Object> saveTables(String json) {
		logger.info("------saveTables:开始保存每日盘库----------");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<BsManageCheck> tables = new ArrayList<BsManageCheck>();
		Date now = new Date();
		//市平台出库接口
		Map<String, BsVaccineOrder2Epi> orderlist = new HashMap<String, BsVaccineOrder2Epi>();
		
		BsVaccineOrderVo vo = new BsVaccineOrderVo(
				StringUtils.EMPTY, 
				new Date(), 
				new UploadEpiProCreateBy(UserUtils.getUser().getId()), 
				new Date(),
				StringUtils.EMPTY,  
				StringUtils.EMPTY, 
				"0", 
				OfficeService.getFirstOffice().getName(), 
				OfficeService.getFirstOffice().getCode(), 
				StringUtils.EMPTY, 
				StringUtils.EMPTY, 
				OfficeService.getFirstOffice().getCode());
		
		BsVaccineOrderVo vo2_1 = new BsVaccineOrderVo();
		BeanUtils.copyProperties(vo, vo2_1);
		vo2_1.setOrderType("2");
		vo2_1.setcType("1");
		BsVaccineOrderVo vo2_2 = new BsVaccineOrderVo();
		BeanUtils.copyProperties(vo, vo2_2);
		vo2_2.setOrderType("2");
		vo2_2.setcType("2");
		
		BsVaccineOrderVo vo4_1 = new BsVaccineOrderVo();
		BeanUtils.copyProperties(vo, vo4_1);
		vo4_1.setOrderType("4");
		vo4_1.setcType("1");
		BsVaccineOrderVo vo4_2 = new BsVaccineOrderVo();
		BeanUtils.copyProperties(vo, vo4_2);
		vo4_2.setOrderType("4");
		vo4_2.setcType("2");
		
		BsVaccineOrderVo vo6_1 = new BsVaccineOrderVo();
		BeanUtils.copyProperties(vo, vo6_1);
		vo6_1.setOrderType("6");
		vo6_1.setcType("1");
		BsVaccineOrderVo vo6_2 = new BsVaccineOrderVo();
		BeanUtils.copyProperties(vo, vo6_2);
		vo6_2.setOrderType("6");
		vo6_2.setcType("2");
		
		//报废
		orderlist.put("2_1", new BsVaccineOrder2Epi(vo2_1));	
		orderlist.put("2_2", new BsVaccineOrder2Epi(vo2_2));	
		//使用
		orderlist.put("4_1", new BsVaccineOrder2Epi(vo4_1));
		orderlist.put("4_2", new BsVaccineOrder2Epi(vo4_2));
		//报损
		orderlist.put("6_1", new BsVaccineOrder2Epi(vo6_1));
		orderlist.put("6_2", new BsVaccineOrder2Epi(vo6_2));
		
//		List<UploadEpiProInfo> list = new ArrayList<UploadEpiProInfo>();
		try {
			List<Map<String, Object>> data = (List<Map<String, Object>>) JsonMapper.fromJsonString(json, List.class);
			for(Map<String, Object> m : data){
				BsManageCheck check = (BsManageCheck) JsonMapper.fromJsonString(JsonMapper.toJsonString(m), BsManageCheck.class);
				if(check != null){
					//保存盘点记录
					check.setIsNewRecord(false);
					check.preInsert();
					check.setCreateDate(now);
					insert(check);
					tables.add(check);
					//修改库存
					check.getProduct().setStorenum(check.getRestNum());
					check.getProduct().preUpdate();
					bsManageProductDao.updateStock(check.getProduct());

					//接收市平台发送数据
					//报废
					if(check.getScrapNum() != null && check.getScrapNum() != 0){
						UploadEpiProInfo upInfo6 = new UploadEpiProInfo(check.getProduct().getEpiId(), check.getProduct().getIsforeign(), check.getProduct().getCostprice()+"", check.getProduct().getSellprice()+"", check.getScrapNum()+"");
						if(BsManageProduct.TYPE1.equals(check.getProduct().getIsforeign())){
							orderlist.get("6_1").getList().add(upInfo6);
						}else{
							orderlist.get("6_2").getList().add(upInfo6);
						}
					}
					//使用
					if(check.getUseNum() != null && check.getUseNum() != 0){
						long use = 0;
						if(check.getLastNum() != null && check.getLastNum() > 0){
							use = check.getLastNum() 
									- ((check.getRestNum() == null)?0:check.getRestNum()) 
									- ((check.getScrapNum() == null)?0:check.getScrapNum()) 
									- ((check.getOutNum() == null)?0:check.getOutNum()) 
									- ((check.getExchangeNum() == null)?0:check.getExchangeNum());
						}
						if(use > 0){
							UploadEpiProInfo upInfo4 = new UploadEpiProInfo(check.getProduct().getEpiId(), 
									check.getProduct().getIsforeign(), 
									check.getProduct().getCostprice()+"", 
									check.getProduct().getSellprice()+"", 
									use+"");
							//添加查询条件  pid|开始时间|结束时间
							upInfo4.setVaccPid(check.getProduct().getId());
							upInfo4.setVaccStartDate(check.getLastDate());
							upInfo4.setVaccEndDate(new Date());
							upInfo4.setRemarks("门诊上报生成订单");
							if(BsManageProduct.TYPE1.equals(check.getProduct().getIsforeign())){
								orderlist.get("4_1").getList().add(upInfo4);
							}else{
								orderlist.get("4_2").getList().add(upInfo4);
							}
						}
					}
					//报损
					if(check.getOutNum() != null && check.getOutNum() != 0){
						UploadEpiProInfo upInfo2 = new UploadEpiProInfo(check.getProduct().getEpiId(), check.getProduct().getIsforeign(), check.getProduct().getCostprice()+"", check.getProduct().getSellprice()+"", check.getOutNum()+"");
						if(BsManageProduct.TYPE1.equals(check.getProduct().getIsforeign())){
							orderlist.get("2_1").getList().add(upInfo2);
						}else{
							orderlist.get("2_2").getList().add(upInfo2);
						}
					}
					//更新数据用于局部刷新
					check.setLastDate(now);
					check.setUseNum(0l);
					check.setInNum(0l);
					check.setOutNum(0l);
					check.setExchangeNum(0l);
					check.setScrapNum(0l);
					check.setLastNum(check.getProduct().getStorenum());
				}
			}
			logger.info("------saveTables:保存每日盘库成功----------");
			returnMap.put("code", "200");
			returnMap.put("message", "保存成功");
			returnMap.put("list", JsonMapper.toJsonString(tables));
			logger.info("------saveTables:保存每日盘库开始上报市平台出库接口----------");
			BsVaccineOrderVo bsVaccineOrder = new BsVaccineOrderVo();
			bsVaccineOrder.setRemarks(StringUtils.EMPTY);
			bsVaccineOrder.setCreateDate(new Date());
			bsVaccineOrder.setCreateBy(new UploadEpiProCreateBy(UserUtils.getUser().getId()));
			bsVaccineOrder.setOutboundDate(new Date());
			asyncService.uploadProduct2Epi(orderlist);
			
		} catch (Exception e) {
			logger.error("------saveTables:保存每日盘库失败----------",e);
			returnMap.put("code", "500");
			returnMap.put("message", "保存失败");
		}
		return returnMap;
		
	}
	
	/**
	 * 每月1号凌晨0:30,为用户自动盘点一次上月末数据方法
	 * @author Jack
	 * @date 2018年3月6日 下午5:49:13
	 * @description 
	 *
	 */
	public void withinPlanVaccAutoCount(){
		BsManageCheck bsManageCheck = new BsManageCheck();
		int year = Integer.valueOf(DateUtils.getYear());
		int month = Integer.valueOf(DateUtils.getMonth())-1;
		String day = StringUtils.substringAfterLast(DateUtils.getLastDayOfMonth(year, month-1), "-");
		String checkDate = "";
		if(month<10){
			checkDate = year + "0" + month + day;
		}else{
			checkDate = year + month + day;
		}
		
		bsManageCheck.setCheckDate(checkDate);
		bsManageCheck.setShowNull(false);
		List<BsManageCheck> checkTables = genCheckTable(bsManageCheck);
		for(int i = 0;i < checkTables.size(); i ++){
			if(checkTables.get(i).getRestNum() == 0 
					&& checkTables.get(i).getInNum() == 0 
					&& checkTables.get(i).getOutNum() == 0
					&& checkTables.get(i).getUseNum() == 0){
				checkTables.remove(i--);
			}
		}
		
		String checkedResult = JsonMapper.toJsonString(checkTables);
		
		String json = StringEscapeUtils.unescapeHtml4(checkedResult);
		logger.info("------saveTables:开始保存盘点记录----------  json=" + JsonMapper.toJsonString(json));
		if(StringUtils.isNotBlank(json));
		Map<String, Object> returnMap = saveTables(json);
		logger.info("------saveTables:开始保存盘点记录----------  returnMap=" + JsonMapper.toJsonString(returnMap));
		
	}
	
}