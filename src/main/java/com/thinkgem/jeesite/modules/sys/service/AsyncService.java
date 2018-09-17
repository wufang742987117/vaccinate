package com.thinkgem.jeesite.modules.sys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SMS;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.modules.charge.entity.BsChargeLog;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.holiday.service.SysHolidayService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.product.vo.BsVaccineOrder2Epi;
import com.thinkgem.jeesite.modules.remind.dao.VacChildRemindDao;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.sys.entity.SysWorkingHours;
import com.thinkgem.jeesite.modules.sys.entity.User;

import sun.misc.BASE64Decoder;

/**
 * 异步业务
 * 
 * @author ngh
 * @datetime [2016年8月8日 下午1:44:34]
 *
 */
@Service
@Async
public class AsyncService {
	
	@Autowired
	ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	VacChildRemindDao vacChildRemindDao;
	@Autowired
	SysHolidayService holidayService;
	@Autowired
	SysWorkingHoursService sysWorkingHoursService;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String TYPE = "02";
	

	/**
	 * 发送计划通知
	 * 
	 * @param mobiles
	 *            审查人手机号
	 * @param names
	 *            审查人名称
	 */
	public void sendPlanNotice(List<String> mobiles, List<String> names) {
		SMS.send(SMS.PLAN_NOTICE, mobiles, names);
	}
	
	
	/**
	 * 完成操作成功后发送短信通知家长
	 * @author fuxin
	 * @date 2017年2月23日 下午2:20:43
	 * @description 
	 *		TODO
	 * @param mobiles(号码1,号码2)
	 * @param parms
	 *
	 */
	@SuppressWarnings("finally")
	public String sendInformSMS(String mobiles, Map<String, Object> parms){
		
		logger.info("发送短信:号码-" + mobiles + " 参数-" + JsonMapper.toJsonString(parms));
		String result = null;
		try {
			result = SMS.sendaliMSM(mobiles, parms, SMS.TEMP_FINISH);
			logger.info("短信发送结束:" + result);
		} catch (Exception e) {
			logger.error("短信发送失败" + e.getMessage());
		} finally{
			return result;
		}
	}
	
	/**
	 * 给制定家长发送提示短信
	 * @author fuxin
	 * @date 2017年2月23日 下午2:20:43
	 * @description 
	 *		TODO
	 * @param mobiles(号码1)  String(name)
	 * @param parms
	 *
	 */
	@SuppressWarnings("finally")
	public String sendNoticeSMS(String mobiles,String name){
		
		logger.info("发送短信:号码-" + mobiles + " 参数-" + JsonMapper.toJsonString(name));
		String result = null;
		try {
			Map<String,Object> parm = new HashMap<String, Object>();
			parm.put("name", name);
			result = SMS.sendaliMSM(mobiles, parm, SMS.TEMP_NOTICE);
			logger.info("短信发送成功:" + result);
		} catch (Exception e) {
			logger.error("短信发送失败" + e.getMessage());
		}finally{
			return result;
		}
	}
	
	/**
	 * 异步发送post请求
	 * @author fuxin
	 * @date 2017年3月28日 下午4:14:37
	 * @description 
	 *		TODO
	 * @param url
	 * @param map
	 *
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String sendWxMessage(String url, HashMap map){
		logger.info("发送post请求,url--> " + url + "  --> " + JsonMapper.toJsonString(map));
		try {
			String str = HttpClientReq.httpClientPost(url, map);
			logger.error("发送post请求成功" + "info--> " + str);
			return str;
		} catch (Exception e) {
			logger.error("发送post请求失败");
		}
		return null;
	}
	
	/**
	 * 异步发生post请求
	 * @author fuxin
	 * @date 2017年3月28日 下午4:17:31
	 * @description 
	 *		TODO
	 * @param url
	 * @param json
	 *
	 */
	public String sendWxMessage(String url, String json){
		logger.info("发送post请求,url--> " + url + "  --> " + json);
		try {
			String str = HttpClientReq.httpClientPostJson(url, json);
			logger.error("发送post请求成功" + "info--> " + str);
			return str;
		} catch (Exception e) {
			logger.error("发送post请求失败");
		}
		return null;
	}
	
	/**
	 * 异步发生post请求
	 * @author zhouqj
	 * @date 2017年4月27日 上午11:17:25
	 * @description 
	 *		TODO 
	 * @param url
	 * @param json
	 * @return
	 *
	 */
	public String sendWxMessageIn(String url, String json){
		logger.info("发送post请求,url--> " + url + "  --> " + json);
		try {
			String str = HttpClientReq.httpClientPostJsonIn(url, json);
			logger.error("发送post请求成功" + "info--> " + str);
			return  new String(str.getBytes("GBK"));
		} catch (Exception e) {
			logger.error("发送post请求失败");
		}
		return null;
	}
	
	public String postInsurancePolicyNew(){
		//交易流水号
		Date date = new Date();
		long ts = date.getTime();
		String timestamp = String.valueOf(ts);
		String third_serial_no = "57" + timestamp;
		//出单用户
		String user_name = "123";
		//渠道号
		String channel_detail = "57";
		//单证号(false)
		String policy_card_id = "";
		//保单投保日期
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String pol_apply_date = formatter.format(currentTime);
	    //保单生效日期
	    String cvali_date = formatter.format(currentTime);
	    //保单失效日期(false)
	    String end_date = "";
	    //保额(false)
	    String amnt = "";
	    //保费(false)
	    String prem = "";
	    //航班号(false)
	    String flight_no = "";
	    //备注(false)
	    String remark = "";
	    //业务标示(false)
	    String buss_sign = "";
	    //计划编码
	    String plan_code = "DMCTEST01";
	    //计划份数
	    String mult = "1";
	    
	    //投保人姓名
	    String appnt_name = "成天乐";
	    //投保人性别
	    String appnt_sex = "";
	    //投保人出生日期
	    String appnt_birthday = "";
	    //投保人证件类型
	    String appnt_id_type = "0";
	    //投保人证件号
	    String appnt_id_no = "130429198610198019";
	    //投保人职业编码(false)
	    String occupation_code = "";
	    //所在省(false)
	    String province = "";
	    //所在市(false)
	    String city = "";
	    //所在县(false)
	    String county = "";
	    //所在街道(false)
	    String street_address = "";
	    //投保人手机号码(false)
	    String mobile = "";
	    //投保人邮箱(false)
	    String email = "";
	    
	    //与投保人的关系
	    String relation_to_appnt = "39";
	    //被保人姓名
	    String insured_name = "成周周";
	    //被保人性别
	    String insured_sex = "M";
	    //被保人出生日期
	    String insured_birthday = "2014-12-29";
	    //被保人证件类型
	    String insured_id_type = "7";
	    //被保人证件号
	    String insured_id_no = "340603100120161687";
	    //被保人职业编码(false)
//	    String occupation_code = "";
	    
	    //受益人类别(false)
	    String bnf_type = "";
	    //受益人序号(false)
	    String bnf_no = "";
	    //受益人姓名(false)
	    String bnf_name = "";
	    //受益人证件类型(false)
	    String bnf_id_type = "";
	    //受益人证件号(false)
	    String bnf_id_no = "";
	    //受益人顺序(false)
	    String bnf_grade = "";
	    //受益比例(false)
	    String bnf_lot = "";
	    
	    String json_str = "{"+
				    "'third_serial_no': '"+ third_serial_no +"',"+
				    "'user_name': '"+ user_name +"',"+
				    "'channel_detail': '"+ channel_detail +"',"+
				    "'policy_card_id': '',"+
				    "'pol_apply_date':'"+ pol_apply_date+"',"+
				    "'cvali_date': '"+ cvali_date +"',"+
				    "'end_date': '',"+
				    "'amnt': '',"+
				    "'prem': '',"+
				    "'flight_no': '',"+
				    "'remark': '',"+
				    "'buss_sign': '',"+
				    "'plan': {"+
				        "'plan_code': '"+ plan_code +"',"+
				        "'mult': '"+ mult +"'"+
				    "},"+
				    "'appnt': {"+
				        "'appnt_name': '"+ appnt_name +"',"+
				        "'appnt_sex': '"+ appnt_sex +"',"+
				        "'appnt_birthday': '"+ appnt_birthday +"',"+
				        "'appnt_id_type': '"+ appnt_id_type +"',"+
				        "'appnt_id_no': '"+ insured_id_no +"',"+
				        "'occupation_code': '',"+
				        "'address': {"+
				            "'province': '',"+
				            "'city': '',"+
				            "'county': '',"+
				            "'street_address': '',"+
				            "'mobile': '',"+
				            "'email': ''"+
				        "}"+
				    "},"+
				    "'insured': {"+
				      "'relation_to_appnt': '"+ relation_to_appnt +"',"+
				       " 'insured_name': '"+ insured_name +"',"+
				        "'insured_sex': '"+ insured_sex +"',"+
				        "'insured_birthday': '"+ insured_birthday +"',"+
				        "'insured_id_type': '"+ insured_id_type +"',"+
				        "'insured_id_no': '"+ appnt_id_no +"',"+
				        "'occupation_code': ''"+
				    "},"+
				    "'bnfs': []"+		
				 "}";
	    String url = "http://test.hxlife.com/services/rest/policy/new";
   	 	String jsonTo = sendWxMessageIn(url, json_str);
   		System.out.println("退保" + jsonTo);
	    
	 	 if(StringUtils.isNoneBlank(jsonTo)){
	 	    return jsonTo;
	 	 }else{
	 	    String jsonOff = "{"+
	 	    			  	"'result': 'fail',"+
	 					    "'message': '投保失败,无返回结果',"+
	 					    "'third_serial_no': '"+third_serial_no+"'"+
	 					    "}";
	 	    return jsonOff;
	 	 }
	}

	public String postInsurancePolicyCancel(){
		//第三方流水号
	    String third_serial_no="571493118153480";
	    //退保用户
	    String user_name = "123";
	    //保单号
	    String policy_no="26000002041068";
		//退保日期
		Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String cancel_date = formatter.format(currentTime);
	    //保费(false)
	    String prem = "200";
		
	    String json_str = "{"+
				    "'third_serial_no': '"+ third_serial_no +"',"+
				    "'user_name': '"+ user_name +"',"+
				    "'policy_no': '"+ policy_no +"',"+
				    "'cancel_date':'"+ cancel_date +"',"+
				    "'prem':'"+ prem +"'"+
				    "}";
	    String url = "http://test.hxlife.com/services/rest/policy/cancel";
	    String jsonTo = sendWxMessageIn(url, json_str);
	    System.out.println("退保" + jsonTo);
	    
	    if(StringUtils.isNoneBlank(jsonTo)){
	    	return jsonTo;
	    }else{
	    	String jsonOff = "{"+
	    			  	"'result': 'fail',"+
					    "'message': '交易失败,无返回结果',"+
					    "'third_serial_no': '"+third_serial_no+"'"+
					    "}";
	    	return jsonOff;
	    }
	}


	/**
	 * 建卡上报省平台
	 * @author fuxin
	 * @date 2017年7月26日 上午10:22:45
	 * @description 
	 *		TODO
	 * @param codeupload
	 *
	 */
	public void uploadNewChild(String codeupload) {
		try {
			Thread.sleep(1000*60*1);
			logger.info("开始上报建卡记录：" + codeupload);
			String result = HttpClientReq.webServiceInvoke("upload", new Object[]{codeupload}, logger);
			logger.info("上报建卡记录结束：" + codeupload,result);
		} catch (InterruptedException e) {
			logger.error("上报建卡记录失败：" + codeupload,e);
		}
		
	}


	/**
	 * 异步格式化接种记录
	 * @author fuxin
	 * @date 2017年10月8日 下午5:59:06
	 * @description 
	 *		TODO
	 * @param childcode
	 *
	 */
	public void clearRecordAsync(String childcode, String localcode) {
		logger.info("clearRecord[code:" + childcode + " ,local:" + localcode + "]");
		childVaccinaterecordService.clearRecord(childcode, localcode);
	}


	/**
	 * 自动关注宝宝
	 * @author Lonny
	 * @date 2017年10月8日 下午5:59:06
	 * @description 
	 * @param childcode
	 */
	public void autoAttendChild(String userId, String childCode,String fileorigin) {
		// 网络请求微信服务器，自动关注宝宝
		logger.info("关注宝宝接口入参："+userId+"--"+childCode);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			logger.error("自动关注宝宝异常",e1.getMessage());
		}
		HashMap<String, String> map= new HashMap<String, String>();
		map.put("userId", userId); 
		map.put("childCode", childCode);
		map.put("fileorigin",fileorigin);
		String wx=null;
		try {
			wx = HttpClientReq.httpClientPostJson(Global.getConfig("wechat.url")+ "api/getConcerBaby.do",JsonMapper.toJsonString(map));
			logger.info("自动关注宝宝请求成功-->" + wx.toString());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("自动关注宝宝接口异常",e.getMessage());
		}
	}

	/**
	 * 自动预约下一针乙肝
	 * @author fuxin
	 * @date 2017年12月13日 上午10:10:20
	 * @description 
	 *		TODO
	 * @param childBaseinfo
	 *
	 */
	public void remindFirstHepB(ChildBaseinfo childBaseinfo, BsManageProductService productService, VacChildRemindService remindService, String localCode) {
		try {
			Thread.sleep(3100);
		} catch (InterruptedException e1) {
			logger.error("自动预约下一针乙肝等待异常",e1.getMessage());
		}
		logger.info("自动预约下一针乙肝" + childBaseinfo.getChildcode());
		//自动预约下一针乙肝
		BsManageProduct tempProduct = new BsManageProduct();
		//TODO:建卡自动预约乙肝
		tempProduct.setBigcode("02");
		tempProduct.setOrderBy("a.create_date desc");
		tempProduct.setShowAll(BsManageProduct.SHOWALL_YES);
		tempProduct.setLocalCode(localCode);
		List<BsManageProduct> products = productService.findQueueViewListApi(tempProduct);
		logger.info("查询乙肝疫苗库存" + products.size());
		if(products.size() > 0){
			List<Map<String , String>> tempList = new ArrayList<Map<String,String>>();
			Map<String , String> map = new HashMap<String, String>();
			map.put("vaccId", products.get(0).getVaccineid());
			map.put("remindVacc", products.get(0).getName() + "(免费)");
			map.put("price", "0");
			//计算预约时间
			Date remindDate = DateUtils.addMonths(childBaseinfo.getBirthday(), 1);
			if(new Date().after(remindDate)){
				remindDate = DateUtils.getDay(new Date());
			}
			remindDate = holidayService.nextWorkDay(remindDate, localCode);
			
			List<SysWorkingHours> hours = sysWorkingHoursService.getByDate(remindDate, localCode);
			if(hours.size() > 0){
				map.put("selectTime", hours.get(0).getTimeSlice());
			}
			for(SysWorkingHours ho : hours){
				if(ho.getPercent() != 1){
					map.put("selectTime", ho.getTimeSlice());
				}
			}
			tempList.add(map);
			VacChildRemind remind = new VacChildRemind();
			remind.setChildcode(childBaseinfo.getChildcode());
			remind.setRemindDate(remindDate);
			remind.setRemindVacc(JsonMapper.toJsonString(tempList));
			remind.setLocalCode(localCode);
			remindService.saveRemind(remind,TYPE);
			logger.info("自动预约下一针乙肝成功");
		}else{
			logger.error("自助预约下一针乙肝失败，找不到库存");
		}
		
	}

	/**
	 * 更新用户信息到市平台
	 * @author zhouqj
	 * @date 2017年12月14日 下午7:30:45
	 * @description 
	 *		TODO
	 * @param user
	 * @param code
	 *
	 */
	public void pushUserInfo(User user, String localCode , String type) {
		TokenUtils.genToken();
		logger.info("更新用户信息接口入参："+JsonMapper.toJsonString(user)+"--"+localCode+"--"+type);
		user.setCreateById(user.getCreateBy().getId());
		user.setUpdateById(user.getUpdateBy().getId());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user", JsonMapper.toJsonString(user)); 
		param.put("localCode", localCode); 
		param.put("type", type); 
		// 网络请求服务器
		String logInfo = null;
		try {
			logger.info("--------------用户更新开始--------------");
			logInfo = HttpClientReq.httpClientPostJson(Global.getConfig("hbepi.url")+"/f/sys/saveUser?appid="+TokenUtils.APPID+"&token="+TokenUtils.genToken(), JsonMapper.toJsonString(param));
			logger.info("更新用户信息接口请求成功并返回信息："+logInfo);
			logger.info("--------------用户更新结束--------------");
		} catch (Exception e) {
			logger.error("更新用户信息接口异常",e.getMessage());
		}
	}


	/**
	 * 将微信签字信息插入到数据库中
	 * @author fuxin
	 * @date 2017年12月21日 下午7:45:16
	 * @description 
	 *		TODO
	 * @param vacRemind
	 *
	 */
	public void insertWxSign(VacChildRemind vacRemind) {
		if(vacRemind.getIsNewRecord()){
			vacRemind.preInsert();
		}
		try {
			BASE64Decoder decoder = new BASE64Decoder(); 
			byte[] data1 = decoder.decodeBuffer(vacRemind.getSignature());
			vacRemind.setSignatureByte(data1);
			vacChildRemindDao.insertWxSign(vacRemind);
		} catch (Exception e) {
			logger.error("保存微信签字到本地时异常",e.getMessage());
			
		}
		
	}


	/**
	 * 上报出库数据
	 * @author fuxin
	 * @date 2018年1月18日 下午11:02:25
	 * @description 
	 *		TODO
	 * @param orderlist
	 *
	 */
	public void uploadProduct2Epi(Map<String, BsVaccineOrder2Epi> orderlist) {
		TokenUtils.genToken();
		for(Entry<String, BsVaccineOrder2Epi> kv :orderlist.entrySet()){
			if(kv.getValue().getList().size() > 0){
				logger.info("数据上报市平台开始" + JsonMapper.toJsonString(kv.getValue()));
				String result = HttpClientReq.httpClientPostJson(Global.getConfig("hbepi.url") 
						+ "/hbepi/f/bsOutAndIn/outOrder?appid="+TokenUtils.APPID+"&token=" + TokenUtils.genToken() , JsonMapper.toJsonString(kv.getValue()));
				logger.info("数据上报市平台结束" + result);
			}
		}
		
	}
	
	/**
	 * 上报发票记录数据
	 * @author yangjian
	 * @date 2018年1月25日 下午16:13:30
	 * @description 
	 *		TODO
	 * @param orderlist
	 */
	public void uploadBsChargeLog(Map<String, List<BsChargeLog>> orderlist) {
		TokenUtils.genToken();
		if(orderlist.size() > 0){
			logger.info("发票数据上报市平台开始" + JsonMapper.toJsonString(orderlist));
			String result = HttpClientReq.httpClientPostJson(Global.getConfig("hbepi.url")
					+ "/hbepi/f/bsOutAndIn/chargeLogReport?appid="+TokenUtils.APPID+"&token=" + TokenUtils.genToken() , JsonMapper.toJsonString(orderlist));
			logger.info("发票数据上报市平台结束" + result);
		}
	}
	
	/**
	 * 发送微信消息公用方法
	 * @author yangjian
	 * @date 2018年1月30日下午15:23:14
	 * @description
	 * 		TODO
	 * @param childCode type data
	 * type 模板编码 01:建档成功通知模板 02:预约通知模板 04:接种完成通知模板 05:留观完成通知模板 07：取消预约通知模板
	 * */
	public void sendMessageToWX(String childCode, String type, Map<String, Object> data){
		try {
			Map<String, Object> tempParam = new HashMap<String, Object>();
			tempParam.put("childCode", childCode);
			tempParam.put("type", type);
			tempParam.put("data", data);//data对象，可为数组，也可为对象

			logger.info("发送微信消息开始："+ JsonMapper.toJsonString(tempParam));
			String str = sendWxMessage(Global.getConfig("wechat.url")+"api/sendWxTempResation.do", JsonMapper.toJsonString(tempParam));//调用发送方法进行消息发送
			logger.info("发送微信消息结束：" + JsonMapper.toJsonString(tempParam) + "str:" + str);
		} catch (Exception e) {
			logger.error("发送post请求失败");
		}
	}
	
	/**
	 * 发送微信消息公用方法  样式发送
	 * @author fuxin
	 * @date 2018年1月30日下午15:23:14
	 * @description
	 * 		TODO
	 * @param childCode type data
	 * type 模板编码 01:建档成功通知模板 02:预约通知模板 04:接种完成通知模板 05:留观完成通知模板 07：取消预约通知模板
	 * */
	public void sendMessageToWXDelay(String childCode, String type, Map<String, Object> data, long timeout){
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		sendMessageToWX(childCode, type, data);
	}
}
