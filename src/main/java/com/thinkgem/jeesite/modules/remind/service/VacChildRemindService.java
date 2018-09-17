/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.remind.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.cms.dao.CmsDisclosureDao;
import com.thinkgem.jeesite.modules.cms.entity.CmsDisclosure;
import com.thinkgem.jeesite.modules.product.entity.ApiVaccGroupVo;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.remind.dao.VacChildRemindDao;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 儿童接种提醒Service
 * @author fuxin
 * @version 2017-12-07
 */
@Service
@Transactional(readOnly = true)
public class VacChildRemindService extends CrudService<VacChildRemindDao, VacChildRemind> {

	@Autowired
	BsManageProductService bsManageProductService;
	@Autowired
	ChildBaseinfoService childBaseinfoService;
	@Autowired
	AsyncService asyncService;
	@Autowired
	BsManageVaccineService bsManageVaccineService;
	@Autowired
	ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	CmsDisclosureDao cmsDisclosureDao;
	@Autowired
	OfficeService officeService;
	
	public VacChildRemind get(String id) {
		return super.get(id);
	}
	
	public List<VacChildRemind> findList(VacChildRemind vacChildRemind) {
		return super.findList(vacChildRemind);
	}
	
	public Page<VacChildRemind> findPage(Page<VacChildRemind> page, VacChildRemind vacChildRemind) {
		return super.findPage(page, vacChildRemind);
	}
	
	@Transactional(readOnly = false)
	public void save(VacChildRemind vacChildRemind) {
		super.save(vacChildRemind);
	}
	
	@Transactional(readOnly = false)
	public void delete(VacChildRemind vacChildRemind) {
		super.delete(vacChildRemind);
	}

	/**
	 * 通过儿童编号查询
	 * @author fuxin
	 * @date 2017年12月7日 下午8:52:27
	 * @description 
	 *		TODO
	 * @param childcode
	 * @return
	 *
	 */
	public List<VacChildRemind> findByChildcode(String childcode) {
		VacChildRemind remind = new VacChildRemind();
		remind.setEndSelectDate(DateUtils.getDayEnd(new Date()));
		remind.setChildcode(childcode);
		remind.setStatus(VacChildRemind.STATUS_NORMAL);
		return findList(remind);
	}

	/**
	 * 预约界面保存预约记录
	 * @author fuxin
	 * @date 2017年12月8日 上午10:42:21
	 * @description 
	 *		TODO
	 * @param vacChildRemind
	 * @return 
	 *
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	public Map<String, Object> saveRemind(VacChildRemind vacChildRemind, String type) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", "500");
		if(vacChildRemind == null || vacChildRemind.getRemindDate() == null){
			return returnMap;
		}
		String nextTime = DateUtils.formatDate(vacChildRemind.getRemindDate());
		String nextVacc = "";

		String selectTime = "";
		String price="";
		String allPrice = "";

		
		ChildBaseinfo baseinfo = childBaseinfoService.getByNo(vacChildRemind.getChildcode(), vacChildRemind.getLocalCode());
		String json = StringEscapeUtils.unescapeHtml4(vacChildRemind.getRemindVacc());
		List<Map<String, String>> pids = (List<Map<String, String>>) JsonMapper.fromJsonString(json, List.class);
		if(pids == null || pids.size() == 0){
			//加载可用疫苗信息
			returnMap.put("code", "500");
			returnMap.put("msg", "保存失败");
			returnMap.put("baseinfo", baseinfo);
			returnMap.put("selectTime", selectTime);
			return returnMap;
		}
		
		//加载所有疫苗信息
		for(Map<String, String> pid : pids){
			VacChildRemind remind = new VacChildRemind();
			remind.setChildcode(baseinfo.getChildcode());
			remind.setRemindDate(vacChildRemind.getRemindDate());
			remind.setSelectDate(vacChildRemind.getRemindDate());
			remind.setVaccId(pid.get("vaccId"));
			remind.setRemindVacc(pid.get("remindVacc"));
			remind.setCode(genRemindCode());
			remind.setSelectTime(pid.get("selectTime"));
			remind.setPayPrice(pid.get("price"));
			remind.setLocalCode(vacChildRemind.getLocalCode());
			price = pid.get("price");

			logger.info("保存预约记录，清除过期疫苗" + JsonMapper.toJsonString(remind));
			dao.clearOldRemind(vacChildRemind);
			super.save(remind);
			nextVacc += remind.getRemindVacc() + "&nbsp;";

			selectTime = remind.getSelectTime() + "&nbsp;";
			if(price.equals("0")){
				price = "免费";
			}			
			
			allPrice += price;

		}
		
		//微信推送
		Office office = officeService.getOfficeByCode(vacChildRemind.getLocalCode());
		Map<String, Object> tempParam = new HashMap<String,Object>();
		tempParam.put("childCode", vacChildRemind.getChildcode());//编号
		tempParam.put("username", baseinfo.getChildname());//姓名
		tempParam.put("vaccineName", nextVacc.replace("&nbsp;", " "));//接种疫苗
		tempParam.put("clinicName", office.getName());//接种单位名称officeinfo
		tempParam.put("vatTime", DateUtils.formatDate(vacChildRemind.getRemindDate()));//接种时间
		tempParam.put("price", allPrice);//疫苗价格 /免费就”免费”
		asyncService.sendMessageToWX(vacChildRemind.getChildcode(), type, tempParam);
		
		//加载可用疫苗信息
		returnMap.put("code", "200");
		returnMap.put("msg", "保存成功");
		returnMap.put("baseinfo", baseinfo);
		returnMap.put("nextTime", nextTime);
		returnMap.put("nextVacc", nextVacc);
		returnMap.put("selectTime", selectTime);
		return returnMap;
	}

	/**
	 * 生成预约码
	 * @author fuxin
	 * @date 2017年12月8日 下午2:36:13
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	private String genRemindCode() {
		return IdGen.randomLong(6);
	}

	/**
	 * 将预约记录包装成一体机接口数据
	 * @author fuxin
	 * @date 2017年12月8日 下午4:11:43
	 * @description 
	 *		TODO
	 * @param reminds
	 * @return
	 *
	 */
	public List<ApiVaccGroupVo> toApiVaccGroupVo(List<VacChildRemind> reminds, String localcode) {
		List<ApiVaccGroupVo> returnList = new ArrayList<ApiVaccGroupVo>();
		ApiVaccGroupVo tempVo;
		for(VacChildRemind remind : reminds){
			tempVo = new ApiVaccGroupVo();
			BsManageProduct product = bsManageProductService.findByRemind(remind, localcode);
			if(product == null){
				continue;
			}
			ChildBaseinfo baseinfo = childBaseinfoService.getByNo(remind.getChildcode(), localcode);
			BsManageVaccine vaccine = bsManageVaccineService.getWithModel(product.getVaccineid(), localcode);
			int pin = childVaccinaterecordService.findLastPinByGroup(vaccine.getmNum(), baseinfo.getId(), ChildVaccinaterecord.VACCTYPE_NORMAL, localcode);
			CmsDisclosure context = cmsDisclosureDao.get(product.getVaccineid().substring(0,2));
			if(context != null){
				tempVo.setContext(context.getContext().replaceAll("<([^>]*)>", "").replaceAll("#([^#]*)#", ""));
			}
			tempVo.setInsurance(ChildVaccinaterecord.INSURANCE_STATUS_NO);
			tempVo.setName(remind.getRemindVacc());
			tempVo.setNid(vaccine.getmNum() + pin);
			tempVo.setVid(vaccine.getId());
			tempVo.setPathema(vaccine.getPathema());
			tempVo.setPid(product.getId());
			tempVo.setPin(pin+"");
			tempVo.setSellprice(product.getSellprice());
			tempVo.setPaystatus(ChildVaccinaterecord.PAY_STATUS_NO);
			tempVo.setRid(remind.getId());
			tempVo.setNumType(vaccine.getType());
			returnList.add(tempVo);
		}
		return returnList;
	}

	/**
	 * 将预约记录设置为已完成
	 * @author fuxin
	 * @date 2017年12月10日 下午1:43:28
	 * @description 
	 *		TODO
	 * @param rid
	 *
	 */
	public void finsihRemind(String rid) {
		if(StringUtils.isNotBlank(rid)){
			dao.finsihRemind(rid);
		}
	}
	
	/**
	 * 获取微信签字数据-查询微信服务器
	 * @author fuxin
	 * @date 2017年12月21日 下午3:33:51
	 * @description 
	 *		TODO
	 * @param rids
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	public List<VacChildRemind> getSignDataFromWx(List<String> rids){
		List<VacChildRemind> returnList = new ArrayList<VacChildRemind>();
		//组织请求参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rids",rids);
		String wx = null;
		//请求微信服务器
		try {
			//"http://127.0.0.1/wpwx/api/vacTempSign.do"
			wx = HttpClientReq.httpClientPostJson(Global.getConfig("wechat.url") + "api/vacTempSign.do", JsonMapper.toJsonString(param));
			logger.info("获取微信签字结果[rid=" + JsonMapper.toJsonString(rids) + "]：" + wx);
		} catch (Exception e) {
			logger.error("微信请求异常",e);
		}
		//解析签字数据
		if(StringUtils.isNotBlank(wx)){
			JSONObject obj = JSONObject.parseObject(wx);
			VacChildRemind vacRemind = null;
			//判断是否成功
			if (obj.getBoolean("success")) {
				JSONArray array = new JSONArray((List<Object>) obj.get("data"));
				for (int i = 0; i < array.size(); i++){
					vacRemind = new VacChildRemind();
					vacRemind =(VacChildRemind) JsonMapper.fromJsonString(array.get(i).toString(), VacChildRemind.class);
					//判断结果集是否存在
					if(StringUtils.isNotBlank(vacRemind.getSid())){
						returnList.add(vacRemind);
						//异步保存签字数据
						asyncService.insertWxSign(vacRemind);
					}
				}
			}
		}
		return  returnList;
	}
	
	
	/**
	 * 获取微信签字信息，先查询本地，在查询微信服务器
	 * @author fuxin
	 * @date 2017年12月21日 下午5:21:18
	 * @description 
	 *		TODO
	 * @param rid
	 * @param isFromWx 是否获取微信数据
	 * @return
	 *
	 */
	@Transactional(readOnly=false)
	public VacChildRemind getWxSignData(String rid, boolean isFromWx){
		VacChildRemind remind = dao.getWxSignFromLocal(rid);
		if(remind != null){
			String sign = Base64.getEncoder().encodeToString(remind.getSignatureByte());
			if(StringUtils.isNotBlank(sign)){
				remind.setSignature(sign);
			}
			return remind;
		}else if(isFromWx){
			List<VacChildRemind> list = getSignDataFromWx(Arrays.asList(rid));
			if(list != null && list.size() > 0){
				for(VacChildRemind re : list){
					if(re.getVid().equals(rid)){
						return re;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取微信签字信息，先查询本地，在查询微信服务器
	 * @author fuxin
	 * @date 2017年12月21日 下午5:21:18
	 * @description 
	 *		TODO
	 * @param rid
	 * @param isFromWx 是否获取微信数据 default-true
	 * @return
	 *
	 */
	@Transactional(readOnly=false)
	public VacChildRemind getWxSignData(String rid){
		return getWxSignData(rid, true);
	}
	
	/**
	 * 获取微信签字内容，若为空则返回"0"
	 * @author fuxin
	 * @date 2017年12月21日 下午7:59:49
	 * @description 
	 *		TODO
	 * @param rid
	 * @return
	 *
	 */
	public String getWxSignSignature(String rid){
		VacChildRemind remind = getWxSignData(rid, false);
		if(remind == null){
			return "0";
		}else{
			return remind.getSignature();
		}
	}

	/**
	 * 清除过时签字信息
	 * @author fuxin
	 * @date 2017年12月22日 下午2:14:06
	 * @description 
	 */
	public int clearSignJob() {
		return dao.clearSignJob();
		
	}
	
	/**
	 * 获取当天预约人数
	 * @author yangjian
	 * @date 2018年3月2日 下午2:15:01
	 * @description 
	 *		TODO
	 * @return
	 */
	public Integer getRemindCount(){
		return dao.getRemindCount();
	}
	
}