/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inoculate.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.junl.common.JsonBuild;
import com.junl.common.ResponseStatus;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.dwr.DwrUtils;
import com.thinkgem.jeesite.common.websocket.WebSocket;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.inoculate.dao.QueneDao;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.vo.QueneVo;
import com.thinkgem.jeesite.modules.inoculate.vo.QuenelogVo;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.OfficePreference;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.entity.SysVaccRecord;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;
import com.thinkgem.jeesite.modules.vaccine.service.SysVaccRecordService;

/**
 * 排队叫号管理Service
 * @author fuxin
 * @version 2017-02-14
 */
@Service
@Transactional(readOnly = true)
public class QueneService extends CrudService<QueneDao, Quene> {

	public static final String CACHE_KEY_QUENE = "cache_key_quene_";
	
	@Autowired
	private OfficeService officeService;
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private BsManageVaccinenumService bsManageVaccinenumService;
	@Autowired
	private BsManageProductService productService;
	@Autowired
	private QueneDao queneDao;
	@Autowired
	private SysVaccRecordService vaccRecordService;
	@Autowired
	private VacChildRemindService remindService;
	@Autowired
	private AsyncService asyncService;
	
	private static final String TYPE_FINISH = "04";// 04:接种完成通知模板
	
	public Quene get(String queneno) {
		Quene q = new Quene();
		q.setQueueno(queneno);
		return dao.get(q);
	}
	
	public List<Quene> findList(Quene quene) {
		return super.findList(quene);
	}

	public List<QueneVo> findQueneList(Map<String, Object> map, String locaoCode) {
		map.put("localCode", locaoCode);
		return queneDao.findQueneList(map);
	}
	
	public List<QueneVo> findQueneList(Map<String, Object> map) {
		return findQueneList(map,OfficeService.getFirstOfficeCode());
	}
	
	public Page<Quene> findPage(Page<Quene> page, Quene quene) {
		return super.findPage(page, quene);
	}
	
	@Transactional(readOnly = false)
	public void save(Quene quene) {
		super.save(quene);
		if(quene != null && StringUtils.isNotBlank(quene.getRoomcode())){
			CacheUtils.remove(QueneService.CACHE_KEY_QUENE + quene.getRoomcode());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Quene quene) {
		super.delete(quene);
	}


	/**
	 * 过号操作<br>
	 * 改变叫号队列数据状态
	 * @param quene 
	 */
	@Transactional(readOnly = false)
	public void doPass(Quene quene) {
		quene.setIspass(Quene.ISPASS_Y);
		dao.update(quene);
		//清空科室缓存
		CacheUtils.remove(CACHE_KEY_QUENE + quene.getRoomcode());
	}

	
	/**
	 * 取消操作<br/>
	 * 删除排队信息
	 */
	@Transactional(readOnly=false)
	public String doCancel(Quene quene) {
		//删除已填加未完成的接种记录
			//查询接种记录信息
			ChildVaccinaterecord cvd = new ChildVaccinaterecord();
			cvd.setChildcode(quene.getChildid());
			cvd.setNid(quene.getVaccineid());
			cvd.setStatus(ChildVaccinaterecord.STATUS_NOT);
			List<ChildVaccinaterecord> listcvd = childVaccinaterecordService.findList(cvd);
			//接种记录逻辑删除
			
			//删除
			super.delete(quene);
			//清空科室缓存
			CacheUtils.remove(CACHE_KEY_QUENE + quene.getRoomcode().trim());
			DwrUtils.sendStartWith("vacc_" + quene.getQueueno().substring(0,1),waitNumDelay(quene.getQueueno()));
			
			String msg = "取消成功";
			for(ChildVaccinaterecord record : listcvd){
				if(record.getPrice() > 0 && "1".equals(record.getSource())){
					//来源-微信
					childVaccinaterecordService.delFlag(record);
					msg = "取消成功，请告知家长去微信公众号中退款";
				}else if(record.getPrice() > 0){
					//来源-一体机或登记台
					msg = "取消成功";
				}else{
					//来源
					childVaccinaterecordService.delFlag(record);
				}
			}
			return msg;
		
	}
	
	
	/**
	 * 获取所有未过号队列
	 */
	public List<Quene> getPassN(){
		
		Quene quene = new Quene();
		quene.setIspass("N");
		//TODO:fuxin获取科室信息
		quene.setRoomcode(officeService.getOfficeCode());
		List<Quene> list = super.findList(quene);
		return list;
	}
	
	
	/**
	 * 根据roomcode和localcode 查询未过号队列
	 * @author fuxin
	 * @date 2018年1月24日 下午7:35:40
	 * @description 
	 *		TODO
	 * @param quene
	 * @return
	 *
	 */
	public List<Quene> getPassN(Quene quene) {
		Quene q = new Quene();
		q.setIspass("N");
		//TODO:fuxin获取科室信息
		q.setRoomcode(quene.getRoomcode());
		q.setLocalCode(quene.getLocalCode());
		List<Quene> list = super.findList(q);
		return list;
	}
	
	/**
	 * 获取所有未过号队列
	 */
	@SuppressWarnings("unchecked")
	public List<Quene> getPassNCache() {
		
		String roomcode = officeService.getOfficeCode();
		Object obj = CacheUtils.get(CACHE_KEY_QUENE + roomcode);
		
		if(null != obj){
			return (List<Quene>) obj;
		}else{
			Quene quene = new Quene();
			quene.setIspass("N");
			//TODO:fuxin获取科室信息
			quene.setRoomcode(officeService.getOfficeCode());
			List<Quene> list = super.findList(quene);
			CacheUtils.put(CACHE_KEY_QUENE + roomcode, list);
			return list;
		}
	}
	
	/**
	 * 获取所有未过号队列
	 */
	public List<Quene> getPassNDelay() {
		
		Quene quene = new Quene();
		quene.setIspass("N");
		//TODO:fuxin获取科室信息
		quene.setRoomcode(officeService.getOfficeCode());
		List<Quene> list = super.findList(quene);
		
		//获取科室首选项
		Date now = new Date();
		for(int i = 0; i < list.size(); i ++){
			if(now.before(DateUtils.addSeconds(list.get(i).getCreateDate(), UserUtils.getUserPreference().getQueueDelay()))){
				list.remove(i--);
			}
		}
		return list;
	}
	
	/**
	 * 获取所有已过号队列
	 */
	public List<Quene> getPassY() {
		Quene quene = new Quene();
		quene.setIspass("Y");
		//TODO:fuxin获取科室信息
		quene.setRoomcode(officeService.getOfficeCode());
		return super.findList(quene);
	}
	
	/**
	 * 获取所有待交费队列
	 */
	public List<Quene> getForPay() {
		Quene quene = new Quene();
		quene.setStatus(Quene.STATUS_WAITPAY);
		return super.findList(quene);
	}
	
	/**
	 * 获取所有已完成队列
	 */
	public List<Quene> getFinshed() {
		Quene quene = new Quene();
		quene.setStatus(Quene.STATUS_FINSH);
		quene.setRoomcode(officeService.getOfficeCode());
		return super.findList(quene);
	}

	/**
	 * 排队队列插入
	 * @author fuxin
	 * @date 2017年2月24日 上午11:23:18
	 * @description 
	 *		TODO
	 * @param quene (childid,vaccineid,doctor)
	 * @param payStatus 是否付款 0-未付款 1-已付款(此时价格大于0则等待缴费)
	 * @return map ({code:200,msg:"success"})   code:200(成功)-msg:最新排号  code:500(错误)-msg:错误信息
	 * 
	 */
	@Transactional(readOnly = false)
	synchronized
	public Map<String, String> insertQuene(Quene quene, String payStatus, String room) {
		return insertQuene(quene, payStatus, room,OfficeService.getFirstOfficeCode());
	}
	
	@Transactional(readOnly = false)
	synchronized
	public Map<String, String> insertQuene(Quene quene, String payStatus, String room,String localcode) {
		//String localcode="3406030301";
		quene.setLocalCode(localcode);
		logger.info("排号保存接口获取参数" + JsonMapper.toJsonString(quene));
		Map<String,String> returnMap = new HashMap<String, String>();
		returnMap.put("code", "500");
		
		//参数判空
		if(!StringUtils.isNoneBlank(quene.getChildid()) || !StringUtils.isNoneBlank(quene.getVaccineid())){
			returnMap.put("msg", "排号保存失败档案id或疫苗id为空");
			logger.info(JsonMapper.toJsonString(returnMap));
			return returnMap;
		}
		
		//查询儿童信息
		ChildBaseinfo info = childBaseinfoService.getByNo(quene.getChildid(),localcode);
		if(info == null || !StringUtils.isNotBlank(info.getId())){
			returnMap.put("msg", "儿童信息不存在");
			logger.info(JsonMapper.toJsonString(returnMap));
			return returnMap;
		}
		
		//检查产品库存
		BsManageProduct p = productService.get(quene.getPid());
		if(p == null || p.getStorenum() <= 0){
			returnMap.put("msg", "该疫苗暂无库存");
			logger.info(JsonMapper.toJsonString(returnMap));
			return returnMap;
		}
		
		//若已存在，则直接返回
		Map<String, String> map = new HashMap<>();
		map.put("childcode", quene.getChildid());
		map.put("vaccineid", quene.getVaccineid());
		//map.put("localCode", OfficeService.getFirstOfficeCode());
		map.put("localCode", localcode);
		List<Quene> queneExist = dao.getIfExist(map);
/*		if(queneExist.size() > 0){
			returnMap.put("code", "501");
			returnMap.put("msg", "已出票,编号为" + queneExist.get(0).getQueueno());
			logger.info(JsonMapper.toJsonString(returnMap));
			return returnMap;
		}*/
		for(Quene qe : queneExist){
			qe.setStatus(Quene.STATUS_DEL);
			dao.update(qe);
		}
		
		//获取疫苗信息
		BsManageVaccinenum num = bsManageVaccinenumService.get(quene.getVaccineid(),localcode);
		//若计划外的排号生成虚拟计划信息
		if(num==null){
			num=new BsManageVaccinenum();
			num.setId(quene.getVaccineid());
			num.setGroup(quene.getVaccineid().substring(0, 2));
		}
		//口服疫苗，接种部位为口服
		if("1".equals(num.getIntype())){
			quene.setBodypart("7");
		}
		if(StringUtils.isNotBlank(room) && room.endsWith(",")){
			room = room.substring(0,room.length() - 1);
		}
		String Roomcode = room;
		if(StringUtils.isBlank(Roomcode)){
			//获取可以注射该疫苗的科室
			List<String> Roomcodes = officeService.getAllOfficeCodeByVaccined(num.getGroup(), p.getVaccineid(),localcode);
			if(Roomcodes.size() == 0){
				returnMap.put("msg", "排号保存失败未找到可以注射该疫苗的科室");
				logger.info(JsonMapper.toJsonString(returnMap));
				return returnMap;
			}
			//当有多个科室接种同一种疫苗时，计算优先级
			if(Roomcodes.size() > 1){
				//根据负载策略进行负载均衡
				Quene tempq = new Quene();
				tempq.setStatus(Quene.STATUS_NORMAL);
				tempq.setIspass(Quene.ISPASS_N);
				//tempq.setLocalCode(localcode);
				//将未完成的排号按科室进行分类汇总
				List<Quene> listq = findList(tempq);
				Map<String,List<Quene>> officeMap = new HashMap<String, List<Quene>>();
				List<Quene> tempadd = new ArrayList<Quene>();
				String op = "first";
				for(Quene q : listq){
					if("first".equals(op)){
						op = q.getRoomcode();
					}
					if(!op.equals(q.getRoomcode())){
						officeMap.put(op, tempadd);
						tempadd = new ArrayList<Quene>();
						op = q.getRoomcode();
					}
					tempadd.add(q);			
				}
				if(tempadd.size() > 0){
					officeMap.put(op, tempadd);
				}
				
				Roomcode = Roomcodes.get(0);
				String allCode = Roomcode.toString();
				for(String off:Roomcodes.subList(1, Roomcodes.size())){
					if(officeMap.get(Roomcode) == null && officeMap.get(off) == null){
						//若都为空，则拼接科室编号，用于均衡分配
						allCode += "," + off;
						continue;
					}else if(officeMap.get(off) == null){
						//若只有一个为空则，优先
						Roomcode = off;
						allCode = Roomcode.toString();
						continue;
					}else if(officeMap.get(Roomcode) == null){
						//若只有一个为空则，优先
						continue;
					}
					if(officeMap.get(off).size() < officeMap.get(Roomcode).size()){
						//都不为空，则较少的优先
						Roomcode = off;
						allCode = Roomcode.toString();
					}else {
						allCode += "," + off;
					}
				}
				
				if(allCode.contains(",")){
					Roomcode = Global.getInstance().getOfficePRJ(allCode.split(","));
				}
			}else{
				Roomcode = Roomcodes.get(0);
			}
		}
		
		//特殊权限
		UserUtils.clearCache();
		String specialStatus = "0";
		if(UserUtils.getUser() != null && StringUtils.isNotBlank(UserUtils.getUser().getSpecialStatus())){
			specialStatus =  UserUtils.getUser().getSpecialStatus();
		}
		
		//设置科室信息
		quene.setRoomcode(Roomcode);
		//获取最新排号
		quene.setQueueno(getLastQueneCode(Roomcode,localcode));
		quene.setIspass(Quene.ISPASS_N);
		quene.setPrice(p.getSellprice());
		quene.setLocalCode(localcode);
		//若排号操作来自登记台，并且疫苗价格大于0，则等待缴费
		if(ChildVaccinaterecord.PAY_STATUS_NO.equals(payStatus) && p.getSellprice() > 0 && Global.getInstance().isPayOption()){
			quene.setStatus(Quene.STATUS_WAITPAY);
		}
		super.save(quene);
		if("1".equals(specialStatus)){
			saveAdjustment(quene);
		}
		//清空科室缓存
		CacheUtils.remove(CACHE_KEY_QUENE + Roomcode);
		//刷新叫号屏
		//refresh(OfficeService.getFirstOfficeCode());
		refresh(localcode);
		logger.info("排号保存接口操作成功" + JsonMapper.toJsonString(quene));
		returnMap.put("code", "200");
		returnMap.put("msg", quene.getQueueno());
//		vacc_
		DwrUtils.sendStartWith("vacc_" + quene.getQueueno().substring(0,1),waitNumDelay(quene.getQueueno(),localcode));
		logger.info("返回排号结果" + JsonMapper.toJsonString(returnMap));
		return returnMap;
	}
	
	/**
	 * 获取一个最新排号
	 * @author fuxin
	 * @date 2017年2月24日 下午1:51:20
	 * @description 
	 *		TODO
	 * @param roomcode
	 * @return
	 *
	 */
	public synchronized String getLastQueneCode(String roomcode) {
		return getLastQueneCode(roomcode,OfficeService.getFirstOfficeCode());
	}
	/**
	 * 获取一体机最新排号
	 * @author liyuan
	 * @date 2018年2月9日 下午2:27:25
	 * @description 
	 *		TODO
	 * @param roomcode
	 * @param localcode
	 * @return
	 *
	 */
	public synchronized String getLastQueneCode(String roomcode,String localcode) {
		logger.info("==获取当前最新排号== [" + roomcode + "]" );
		String code = dao.getLastQueneCode(roomcode,localcode);
		logger.info("==获取当前最新排号== [" + roomcode + "] -- >" + code);
		if(code == null){
			return roomcode + "001";
		}
		String codeNum = (Integer.parseInt(code.replaceFirst(roomcode,"")) + 1) + "";
		int count = code.length() - roomcode.length() - codeNum.length();
		for(int i = 0; i < count; i ++){
			codeNum = "0" + codeNum;
		}
		logger.info("==获取最新排号成功== [" + roomcode + "] -- >" + roomcode + codeNum);
		return roomcode + codeNum;
	}

	/**
	 * 完成接种
	 * @author fuxin
	 * @date 2017年2月24日 下午4:11:29
	 * @description 
	 *		TODO
	 * @param quene
	 * @param selected
	 *
	 */
	@Transactional(readOnly=false)
	public ChildVaccinaterecord doFinish(Quene quene, String selected,ChildBaseinfo baseinfo, String isnew)throws RuntimeException {
		//记录最后一针完成时间,用于计算留观结束
		Global.getInstance().setLastFinishTime(new Date());
		
		logger.info("==接种台完成操作开始==" + JsonMapper.toJsonString(quene));
			//查询接种记录信息
			ChildVaccinaterecord cvd = new ChildVaccinaterecord();
			cvd.setChildcode(quene.getChildid());
			cvd.setNid(selected.trim());
//			cvd.setStatus(ChildVaccinaterecord.STATUS_NOT);
			List<ChildVaccinaterecord> listcvd = childVaccinaterecordService.findList(cvd);
			
			//库存-1
			BsManageProduct product = productService.get(quene.getPid());
			logger.info("==接种台完成操作==[" + quene.getQueueno() + "] 获取疫苗数据 --> " + JsonMapper.toJsonString(product));
			if(null != product  && StringUtils.isNoneBlank(product.getId())){
				if("1".equals(isnew)){
					//使用新苗
					product.setStorenum(product.getStorenum() - 1);
					product.setRest(product.getSpec() - 1);
				}else if ("0".equals(isnew)){
					//不使用新苗
					product.setRest(product.getRest() - 1);
				}
				productService.save(product);
				productService.updateRest(product);
			}
			
			logger.info("==接种台完成操作==[" + quene.getQueueno() + "] 获取扣除疫苗库存后数据 --> " + JsonMapper.toJsonString(product));
			//查询剂次信息
			BsManageVaccinenum num = bsManageVaccinenumService.get(selected);
			if(null == num){
				num = new BsManageVaccinenum();
				num.setId(quene.getVaccineid());
				num.setGroup(quene.getVaccineid().substring(0, 2));
			}
			
			if(listcvd.size() >= 1){
				cvd = listcvd.get(0);
				
			}else{
				cvd = new ChildVaccinaterecord(baseinfo.getId()	,num.getVaccine().getId(),num.getId(), new Date(), quene.getBodypart(), quene.getBatch(), quene.getOffice(), quene.getDoctor(), new Date(), quene.getPrice());
				//设置接种医生为当前用户
			}
			//更新接种记录
			//设置接种医生为当前用户
			cvd.setDoctor(UserUtils.getUser().getName());
			cvd.setVaccinatedate(new Date());
			cvd.setBodypart(quene.getPosition());
			//设置记录状态为已接种
			cvd.setStatus(ChildVaccinaterecord.STATUS_YET);
			//更新接种记录产品信息
			cvd.updateProduct(product);
			
			//保存接种记录状态
			childVaccinaterecordService.save(cvd);
			
			//更新排队记录信息
			quene.setStatus(Quene.STATUS_FINSH);
			if(!StringUtils.isNotBlank(quene.getQueueno())){
				//查看是否有排号信息
				Map<String, String> map = new HashMap<>();
				map.put("childcode", quene.getChildid());
				map.put("vaccineid", num.getVaccine().getId());
				map.put("localCode", OfficeService.getFirstOfficeCode());
				List<Quene> listIfExist = dao.getIfExist(map);
				//如果有排号信息则设置为已完成
				if(listIfExist.size() >= 1){
					quene = listIfExist.get(0);
					quene.setStatus(Quene.STATUS_FINSH);
					dao.update(quene);
				}else{
					quene.setQueueno(getLastQueneCode("T"));
					quene.setVaccineid(num.getVaccine().getId());
					quene.setRoomcode(officeService.getOfficeCode());
					quene.setIspass(Quene.ISPASS_N);
					quene.setStatus(Quene.STATUS_FINSH);
					dao.insert(quene);
					//清空科室缓存
					CacheUtils.remove(CACHE_KEY_QUENE + quene.getRoomcode());
				}
			}else{
				dao.update(quene);
				//清空科室缓存
				CacheUtils.remove(CACHE_KEY_QUENE + quene.getRoomcode());
			}
			
			//增加疫苗使用记录
			SysVaccRecord svr = new SysVaccRecord(product.getId(), UserUtils.getUser().getId(), product.getVaccName(), UserUtils.getUser().getName());
			svr.setId(cvd.getId());
			svr.setIsnew(isnew);
			svr.setStock(product.getStorenum());
			vaccRecordService.insert(svr);
			
			try {
				//发送提示短信
		  		HashMap<String, Object> map = new HashMap<>();
				map.put("name", baseinfo.getChildname());
				map.put("time", new Date());
				if(StringUtils.isNotBlank(baseinfo.getGuardianmobile())){
					asyncService.sendInformSMS(baseinfo.getGuardianmobile(), map);
				}
				
				//发送微信推送
		  		HashMap<String, String> wxmap = new HashMap<>();
		  		wxmap.put("childcode", quene.getChildid());
				asyncService.sendWxMessage(Global.getConfig("wechat.url") + "api/sendWxTempMsg.do", JsonMapper.toJsonString(wxmap));
				
				VacChildRemind vacChildRemind = new VacChildRemind();
				vacChildRemind.setChildcode(baseinfo.getChildcode());
				List<VacChildRemind> vacChildReminds = remindService.findList(vacChildRemind);
				if(vacChildReminds.size() > 0){
					vacChildRemind = vacChildReminds.get(vacChildReminds.size()-1);
				}
				
				Map<String, Object> tempParam = new HashMap<String, Object>();
				tempParam.put("username", baseinfo.getChildname());
				if(vacChildRemind == null){
					tempParam.put("nextVatTime", " ");
				}else{
					tempParam.put("nextVatTime", DateUtils.formatDate(vacChildRemind.getRemindDate()));
				}
				
				asyncService.sendMessageToWX(baseinfo.getChildcode(), TYPE_FINISH, tempParam);
				
			} catch (Exception e) {
				logger.error("消息提示发送失败",e);
			}
			logger.info("==接种台完成操作完成==" + JsonMapper.toJsonString(cvd));
			return cvd;
	}
	
	/**
	 * 刷新叫号屏
	 * @author fuxin
	 * @date 2017年12月25日 下午2:06:56
	 * @description 
	 *		TODO
	 * @param localcode
	 *
	 */
	public void refresh(String localcode){
		if(StringUtils.isNotBlank(localcode)){
			refresh(null, null,localcode);
		}
	}
	
	/**
	 * 刷新叫号屏数据
	 * @author fuxin
	 * @date 2017年3月20日 下午7:45:08
	 * @description 
	 *		TODO
	 *childname, queueno ,roomcode
	 */
	public void refresh(Quene curQuene, Quene nextQuene, String localcode){
		Quene quene = new Quene();
		//叫号队列数据状态-正常
		quene.setStatus(Quene.STATUS_NORMAL);
		//叫号队列过号状态-未过号
		quene.setIspass(Quene.ISPASS_N);
		
		List<LinkedMap> queueList = new ArrayList<>();
		LinkedMap codeMap = new LinkedMap();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Quene.STATUS_NORMAL);
		map.put("ispass", Quene.ISPASS_N);
		
//		List<Office> alloffice = UserUtils.getOfficeList();
		Office off = new Office();
		off.setParentCode(localcode);
		List<Office> alloffice = officeDao.findList(off);
		
		for(Office o : alloffice){
			if(StringUtils.isNotBlank(o.getVaccines())){
				//科室A
				quene.setRoomcode(o.getCode());
				map.put("roomcode",o.getCode());
				map.put("localCode",localcode);
				List<QueneVo> listA = findQueneList(map,localcode);				
				codeMap.put("code", o.getCode());
				codeMap.put("list", listA);
				queueList.add(codeMap);
				codeMap = new LinkedMap(); 
			}
		}
		
		//组织叫号信息
		String childname = "";
		String queueno = "";
		String roomcode = "";
		String nextChildname = "";
		String nextQueueno = ""; 
		if(curQuene != null){
			queueno = curQuene.getQueueno();
			roomcode = curQuene.getRoomcode().trim();
		}
		if(curQuene != null && curQuene.getChild() != null){
			childname = curQuene.getChild().getChildname();
		}
		if(nextQuene != null){
			nextQueueno = nextQuene.getQueueno();
		}
		if(nextQuene != null && nextQuene.getChild() != null){
			nextChildname = nextQuene.getChild().getChildname();
		}
		//队列信息4合1
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("childname", childname);
		jsonMap.put("queueno", queueno);		
		jsonMap.put("roomcode", roomcode);
		jsonMap.put("queue", queueList);
		
		//获取配置
		OfficePreference op = OfficeService.getOfficeOption();
		if(op.isCallReady()){
			jsonMap.put("nextChildname", nextChildname);
			jsonMap.put("nextQueueno", nextQueueno);
		}else{
			jsonMap.put("nextChildname", "");
			jsonMap.put("nextQueueno", "");
		}
		
		String json = JsonMapper.toJsonString(jsonMap);
		logger.info("发送叫号屏信息json:"+json);
		WebSocket.sendBroadCast(json, localcode);
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月6日 下午5:19:48
	 * @description 
	 *		推送消息
	 * @param text
	 * @param video 
	 *
	 */
	public void refreshMsg(String text, String video, String localcode){
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("text", text == null ? "" : text);
		jsonMap.put("video", video == null ? "" : video);
		String json = JsonMapper.toJsonString(jsonMap);
		//对文件进行读写操作
		File file = new File("notice.dat");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("写文件地址异常推送", e);
			}
		}
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(file.getName());
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(json);
	        bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("写文件内容异常推送", e);
		}
		logger.info("推送信息json:"+json);
		WebSocket.sendMsgCast(json, localcode);
	}
	
	/**
	 * 获取接种时间小于30分钟留观信息
	 * @author fuxin
	 * @date 2017年3月22日 下午3:53:44
	 * @description 
	 *		TODO
	 * @return JSONObject
	 *
	 */
	public String getObserv(String localcode) {
		List<HashMap<String, String>> map = queneDao.getObserv(localcode);
		JSONObject obj = new JSONObject();
		obj.put("observ", map);
		return JSONObject.toJSONString(obj);
	}
	
	/**
	 * 获取接种时间大于30分钟小于30分钟15秒的记录
	 * @author yangjian
	 * @date 2018年1月31日下午1:55:02
	 * @description
	 * 		TODO
	 * @return JSONObject
	 * */
	public List<HashMap<String, String>> getCompleteView(String localcode){
		List<HashMap<String, String>> map = queneDao.getCompleteView(localcode);
		return map;
	}
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月7日 下午8:00:59
	 * @description 
	 *		统计已完成的人数
	 * @param roomcode
	 * @return
	 *
	 */
	public List<Quene> total(String roomcode) {
		return dao.total(roomcode,OfficeService.getFirstOfficeCode());
		
	}
	
	/**
	 * 一体机排号
	 * @author liyuan
	 * @date 2018年2月26日 上午9:41:10
	 * @description 
	 *		TODO
	 * @param nid
	 * @param pid
	 * @param childcode
	 * @param insurance
	 * @param source
	 * @param orderNO
	 * @param signature
	 * @param payStatus
	 * @param rid
	 * @param stype
	 * @return
	 *
	 */
	public Map<String, Object> childVaccinatereBayNum(String nid, String pid, String childcode, String insurance,
			String source, String orderNO, byte[] signature, String payStatus, String rid, String stype){
		return childVaccinatereBayNum(nid,pid,childcode,insurance,source,orderNO,signature,payStatus,rid,stype,OfficeService.getFirstOfficeCode());
	}
	/**
	 * 一体机排号
	 * @author fuxin
	 * @date 2017年12月21日 下午8:37:01
	 * @description 
	 *		TODO
	 * @param nid
	 * @param pid
	 * @param childcode
	 * @param insurance
	 * @param source
	 * @param orderNO
	 * @param signature
	 * @param payStatus
	 * @param rid
	 * @param stype
	 * @return
	 *
	 */
	public Map<String, Object> childVaccinatereBayNum(String nid, String pid, String childcode, String insurance,
			String source, String orderNO, byte[] signature, String payStatus, String rid, String stype,String localcode) {
		//通过儿童编码获取儿童信息
		ChildBaseinfo baseinfo = new ChildBaseinfo();
		//String localcode = "3406030301";
		baseinfo.setChildcode(childcode);
		baseinfo.setLocalCode(localcode);
		baseinfo = childBaseinfoService.findList(baseinfo).get(0);
		
		//插入儿童计划表数据
		ChildVaccinaterecord childVaccinaterecord = new ChildVaccinaterecord();
		//儿童ID
		childVaccinaterecord.setChildid(baseinfo.getId());
		//儿童编码
		childVaccinaterecord.setChildcode(childcode);
		//nid
		childVaccinaterecord.setNid(nid);
		//产品id
		childVaccinaterecord.setProductid(pid);
		//数据来源
		childVaccinaterecord.setSource(source);
		//数据来源
		childVaccinaterecord.setOrderNO(orderNO);
		//保险
		childVaccinaterecord.setInsurance(insurance);
		//签字状态
		if(null != signature && signature.length > 0 ){
			childVaccinaterecord.setSignature(ChildVaccinaterecord.SIGNATURE_YES);
			childVaccinaterecord.setSignatureList(signature);
			//签字来源
			childVaccinaterecord.setStype(stype);
		}else{
			childVaccinaterecord.setSignature(ChildVaccinaterecord.SIGNATURE_NO);
		}
		//疫苗状态
		childVaccinaterecord.setStatus(ChildVaccinaterecord.STATUS_NOT);
		//付款状态
		childVaccinaterecord.setPayStatus(payStatus);
		//接种类型
		childVaccinaterecord.setVacctype(ChildVaccinaterecord.VACCTYPE_STATUS_YES);
		//有无异常反应
		childVaccinaterecord.setIseffect(ChildVaccinaterecord.ISEFFECT_NO);
		//查询大类数据信息
		BsManageVaccinenum num = bsManageVaccinenumService.get(nid,localcode);
		//疫苗针次
		childVaccinaterecord.setDosage(String.valueOf(num.getPin()));
		//疫苗大类名称
		childVaccinaterecord.setVaccCode(num.getName());
		//要打的疫苗信息
		BsManageProduct product	= productService.get(pid);
		//疫苗价格
		childVaccinaterecord.setPrice(product.getSellprice());
		//疫苗批号
		childVaccinaterecord.setBatch(product.getBatchno());
		//疫苗小类ID
		childVaccinaterecord.setVaccineid(product.getVaccineid());
		//疫苗小类的名称
		childVaccinaterecord.setVaccName(product.getVaccName());
		//疫苗厂家
		childVaccinaterecord.setManufacturer(product.getManufacturer());
		//疫苗厂家编码
		childVaccinaterecord.setManufacturercode(product.getCode());
		//接种单位
		//childVaccinaterecord.setOffice(OfficeService.getFirstOfficeCode());
		childVaccinaterecord.setOffice(localcode);
		
		childVaccinaterecord.setLocalCode(localcode);
		// 传递nid，pid
		Quene quene = new Quene();
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		quene.setVaccineid(nid);
		quene.setPid(pid);
		// 传递儿童编码
		quene.setChildid(childcode);
		
		//根据付款状态 修改排号状态
		if(product != null && product.getSellprice() > 0 && ChildVaccinaterecord.PAY_STATUS_NO.equals(payStatus)){
			quene.setStatus(Quene.STATUS_WAITPAY);
		}
		
		Map<String, String> map1 = insertQuene(quene,payStatus,StringUtils.EMPTY,localcode);
		logger.info("排队号返回值"+JsonMapper.toJsonString(map1));
		HashMap<String, Object> map3 = new HashMap<>(); 
		map3.put("msg", map1.get("msg"));
		map3.put("code", map1.get("code"));
		HashMap<String, Object> map2 = new HashMap<>();
		if ("200".equals(map1.get("code"))) {
			map2.put("name", product.getVaccName());
			map2.put("no", map1.get("msg"));
			map2.put("childname",baseinfo.getChildname());
			map2.put("price", product.getSellprice());
			map2.put("date", sdf.format(new Date()));
			map2.put("state", "0");
			map2.put("waitNum", waitNum((String)map1.get("msg"),localcode)+"");
			map2.put("nid", quene.getVaccineid());
			map2.put("com", product.getManufacturer());
			map2.put("batch", product.getBatchno());
			
			//完成预约记录
			if(StringUtils.isNotBlank(rid)){
				remindService.finsihRemind(rid);
			}
 			
			//判断数据库是否存在相同的排号信息
			if (childVaccinaterecordService.findList(childVaccinaterecord).size()>0) {
				
			}else{
				//判断该疫苗是否在其他地方已操作完成(未接种状态)
				ChildVaccinaterecord record = new ChildVaccinaterecord();
				record.setChildid(baseinfo.getId());
				record.setNid(nid);
				record.setLocalCode(localcode);
				if(childVaccinaterecordService.findList(record).size() > 0){
					
				}else{
					childVaccinaterecordService.save(childVaccinaterecord);
					//签字插入签字表
					if(null != signature && signature.length > 0 ){
						childVaccinaterecordService.saveSignature(childVaccinaterecord);
					}
				}
			}
			
			Map<String, Object> json=JsonBuild.build(true, "排号成功",ResponseStatus.REQUEST_SUCCESS, map2);
			logger.info("返回值" + JsonMapper.toJsonString(json));
			return map2;
		}else if("501".equals(map1.get("code"))){
			//判断是否有修改排号内容
			ChildVaccinaterecord a = new ChildVaccinaterecord();
			a.setChildid(childVaccinaterecord.getChildid());
			a.setDosage(childVaccinaterecord.getDosage());
			a.setNid(nid);
			a.setLocalCode(localcode);
			if(childVaccinaterecordService.findList(a).size()>0){
				//有修改之前的排号内容，就把之前的排号内容ID赋值给新的对象，修改之前的排号内容
				childVaccinaterecord.setId(childVaccinaterecordService.findList(a).get(0).getId());
				//更新保险和签字状态以及存在则插入签字
				childVaccinaterecordService.save(childVaccinaterecord);
				//签字插入签字表
				if(null != signature && signature.length > 0 ){
					childVaccinaterecordService.updateSignature(childVaccinaterecord);
				}
			}
		}
		return map3;
	}
	/**
	 * 根据大类查询所有在90天内到期的疫苗
	 * @author wangdedi
	 * @date 2017年4月24日 下午3:04:37
	 * @description 
	 *		TODO
	 * @param map
	 * @return
	 *
	 */
	public List<BsManageProduct> indate(Map<String, String> map) {
		map.put("localCode", OfficeService.getFirstOfficeCode());
		map.put("officeCode", UserUtils.getUser().getOffice().getCode());
		return	dao.indate(map);
	}
	
	public int update(Quene q) {
		int lin = dao.update(q);
		if(q != null &&  StringUtils.isNotBlank(q.getRoomcode())){			//清缓存
			CacheUtils.remove(QueneService.CACHE_KEY_QUENE + q.getRoomcode());
			DwrUtils.sendStartWith("vacc_" + q.getQueueno().substring(0,1),waitNumDelay(q.getQueueno()));
		}
		return	lin;
	}

	
	/**
	 * 疫苗消耗统计
	 * @author fuxin
	 * @date 2017年7月10日 下午9:09:22
	 * @description 
	 *		TODO
	 * @param listSimple
	 * @return
	 *
	 */
	public List<QuenelogVo> toQuenelogSimpleVo(List<List<ChildVaccinaterecord>> listSimple) {
		List<QuenelogVo> returnList = new ArrayList<QuenelogVo>();
		QuenelogVo voAll = new QuenelogVo();
		voAll.setVaccName("合计");
		for(List<ChildVaccinaterecord> temp : listSimple){
			QuenelogVo vo = new QuenelogVo();
			for(ChildVaccinaterecord rec : temp){ 
				switch (rec.getVacctype()) {
					case ChildVaccinaterecord.VACCTYPE_PRO:
						//加强
						vo.setPinPro(vo.getPinPro() + 1);
						continue;
					case ChildVaccinaterecord.VACCTYPE_NORMAL:
						//常规
						vo.setPinBase(vo.getPinBase() + 1);
						continue;
					case ChildVaccinaterecord.VACCTYPE_COLONY:
						//群体
						logger.debug("群体");
						continue;
					case ChildVaccinaterecord.VACCTYPE_EMERGENCY:
						//应急
						logger.debug("应急");
						continue;
					case ChildVaccinaterecord.VACCTYPE_OTHER:
						//其他
						logger.debug("其他");
						continue;
					default:
						continue;
				}
			}
			vo.setVaccName(temp.get(0).getVaccName());
			vo.setVaccId(temp.get(0).getVaccineid());
			returnList.add(vo);
			voAll.setPinBase(voAll.getPinBase() + vo.getPinBase());
			voAll.setPinPro(voAll.getPinPro() + vo.getPinPro());
			vo = new QuenelogVo();
		}
		returnList.add(voAll);
		return returnList;
	}

	/**
	 * 各疫苗具体剂次
	 * @author fuxin
	 * @date 2017年7月10日 下午9:09:48
	 * @description 
	 *		TODO
	 * @param listSimple
	 * @return
	 *
	 */
	public List<QuenelogVo> toQuenelogDetailVo(List<List<ChildVaccinaterecord>> listSimple) {
		List<QuenelogVo> returnList = new ArrayList<QuenelogVo>();
		QuenelogVo voAll = new QuenelogVo();
		voAll.setVaccName("总计");
		voAll.setPinnum("all");
		voAll.setItemsize(1);
		QuenelogVo vo = new QuenelogVo();
		for(List<ChildVaccinaterecord> temp : listSimple){
			List<QuenelogVo> templist = new ArrayList<QuenelogVo>();
			String pin = "first";
			for(ChildVaccinaterecord rec : temp){
				if(pin.equals("first")){
					pin = rec.getDosage();
				}
				if(!pin.equals(rec.getDosage())){
					vo.setPinnum(pin);
					pin = rec.getDosage();
					vo.setVaccId(rec.getVaccineid());
					//生成新vo
					templist.add(vo);
					vo = new QuenelogVo();
				}
				if(rec.getPrice() == 0){
					vo.setForFree(vo.getForFree() + 1);
				}else{
					vo.setForPay(vo.getForPay() + 1);
					vo.setPriceCount(new BigDecimal(rec.getPrice()).add(vo.getPriceCount()));
				}
			}
			if(vo.getPinCount() > 0){
				vo.setPinnum(pin);
				vo.setVaccId(temp.get(0).getVaccineid());
				templist.add(vo);
				vo = new QuenelogVo();
			}
			vo.setVaccName("合计");
			vo.setVaccId(temp.get(0).getVaccineid());
			for(QuenelogVo v : templist){
				vo.setForFree(vo.getForFree() + v.getForFree());
				vo.setForPay(vo.getForPay() + v.getForPay());
				vo.setPinCount(vo.getPinCount() + v.getPinCount());
				vo.setPriceCount(vo.getPriceCount().add(v.getPriceCount()));				
			}
			voAll.setForFree(voAll.getForFree() + vo.getForFree());
			voAll.setForPay(voAll.getForPay() + vo.getForPay());
			voAll.setPinCount(voAll.getPinCount() + vo.getPinCount());
			voAll.setPriceCount(voAll.getPriceCount().add(vo.getPriceCount()));
			templist.add(vo);
			vo = new QuenelogVo();
			templist.get(0).setItemsize(templist.size());
			templist.get(0).setVaccName(temp.get(0).getVaccName());
			templist.get(0).setVaccId(temp.get(0).getVaccineid());
			returnList.addAll(returnList.size(), templist);
		}
		returnList.add(voAll);
		return returnList;
	}
	
	/**
	 * 根据已出排号计算前面等待人数
	 * @author fuxin
	 * @date 2017年7月12日 下午5:51:47
	 * @description 
	 *		TODO
	 * @param queneno
	 * @return
	 *
	 */
	public int waitNum(String queneno){
		if(!StringUtils.isNotBlank(queneno) || queneno.length() < 1){
			return 0;
		}
		String roomcode = queneno.substring(0,1);
		Quene quene = new Quene();
		quene.setIspass("N");
		//TODO:fuxin获取科室信息
		quene.setRoomcode(roomcode);
		List<Quene> list = findList(quene);
		if(list.size() == 0){
			return 0;
		}
		return list.size() -1;
	
	}
	
	public int waitNum(String queneno,String localcode){
		if(!StringUtils.isNotBlank(queneno) || queneno.length() < 1){
			return 0;
		}
		String roomcode = queneno.substring(0,1);
		Quene quene = new Quene();
		quene.setIspass("N");
		//TODO:fuxin获取科室信息
		quene.setRoomcode(roomcode);
		quene.setLocalCode(localcode);
		List<Quene> list = findList(quene);
		if(list.size() == 0){
			return 0;
		}
		return list.size() -1;
	
	}
	
	/**
	 * 根据已出排号计算前面等待人数
	 * @author 
	 * @date 2017年7月12日 下午5:51:47
	 * @description 
	 *		TODO
	 * @param queneno
	 * @return
	 *
	 */
	public int waitNumDelay(String queneno){
		if(!StringUtils.isNotBlank(queneno) || queneno.length() < 1){
			return 0;
		}
		String roomcode = queneno.substring(0,1);
		Quene quene = new Quene();
		quene.setIspass("N");
		//TODO:fuxin获取科室信息
		quene.setRoomcode(roomcode);
		List<Quene> list = findList(quene);
		//获取科室首选项
		Date now = new Date();
		for(int i = 0; i < list.size(); i ++){
			if(now.before(DateUtils.addSeconds(list.get(i).getCreateDate(), UserUtils.getUserPreference().getQueueDelay()))){
				list.remove(i--);
			}
		}		
		if(list.size() == 0){
			return 0;
		}
		return list.size() -1;
	
	}

	public int waitNumDelay(String queneno,String localcode){
		if(!StringUtils.isNotBlank(queneno) || queneno.length() < 1){
			return 0;
		}
		String roomcode = queneno.substring(0,1);
		Quene quene = new Quene();
		quene.setIspass("N");
		//TODO:fuxin获取科室信息
		quene.setRoomcode(roomcode);
		quene.setLocalCode(localcode);
		List<Quene> list = findList(quene);
		//获取科室首选项
		Date now = new Date();
		for(int i = 0; i < list.size(); i ++){
			if(now.before(DateUtils.addSeconds(list.get(i).getCreateDate(), UserUtils.getUserPreference().getQueueDelay()))){
				list.remove(i--);
			}
		}		
		if(list.size() == 0){
			return 0;
		}
		return list.size() -1;
	
	}
	/**
	 * 线下支付回调
	 * @author fuxin
	 * @date 2018年1月14日 下午2:46:53
	 * @description 
	 *		TODO
	 * @param sessionId
	 * @return
	 *
	 */
	@Transactional(readOnly=false)
	public String offlinePayCallBack(String sessionId) {
		String[] ss = sessionId.split("_");
		//更新排号状态
		//查询排号信息
		Quene tempQuene = new Quene();
		tempQuene.setQueueno(ss[0]);
		tempQuene.setLocalCode(ss[1]);
		tempQuene.setStatus(Quene.STATUS_WAITPAY);
		List<Quene> queues = findList(tempQuene);
		logger.info("儿童线下支付完成支付状态[" + sessionId + "]，找到排号信息-->" + queues.size());
		if(queues.size() == 0){
			logger.error("儿童线下支付完成支付状态失败[" + sessionId + "]，找不到排号信息");
			return "error";
		}
		Quene quene = queues.get(0);
		if(quene == null || StringUtils.isBlank(quene.getQueueno()) || !Quene.STATUS_WAITPAY.equals(quene.getStatus())){
			logger.error("儿童线下支付完成支付状态失败[" + sessionId + "]，排号状态异常" + JsonMapper.toJsonString(quene));
			return "error";
		}
		quene.setStatus(Quene.STATUS_NORMAL);
		quene.setLocalCode(ss[1]);
		update(quene);
		//查询接种记录状态
		ChildVaccinaterecord rcv = new ChildVaccinaterecord();
		rcv.setChildcode(quene.getChildid());
		rcv.setNid(quene.getVaccineid());
		rcv.setStatus(ChildVaccinaterecord.STATUS_NOT);
		rcv.setLocalCode(ss[1]);
		List<ChildVaccinaterecord> rcvs = childVaccinaterecordService.findList(rcv);
		//更新接种记录状态
		if(rcvs.size()>0){
			for(ChildVaccinaterecord r : rcvs){
				r.setPayStatus(ChildVaccinaterecord.PAY_STATUS_YES);
				r.setLocalCode(ss[1]);
				childVaccinaterecordService.save(r);
			}
		}
		//刷新叫号屏
		refresh(ss[1]);
		logger.info("线下回调成功-->" + quene.getQueueno());
		return "success";
	}

	/**
	 * 排号退款
	 * @author zhouqj
	 * @date 2018年1月16日 下午11:00:29
	 * @description 
	 *		TODO
	 * @param queue
	 *
	 */
	@Transactional(readOnly = false)
	public void refundById(Quene queue) {
		dao.refundById(queue);
	}
	
	/**
	 * 调价保存
	 * @author zhouqj
	 * @date 2018年1月25日 上午10:48:00
	 * @description 
	 *		TODO
	 * @param quene
	 *
	 */
	@Transactional(readOnly = false)
	public void saveAdjustment(Quene quene) {
		dao.saveAdjustment(quene);
	}

	/**
	 * 获取排号人数
	 * @author yangjian
	 * @date 2018年3月2日 
	 * @description 
	 *		TODO
	 * 
	 *
	 */
	public Integer getQueneCount(){
		return dao.getQueneCount();
	}
	
}