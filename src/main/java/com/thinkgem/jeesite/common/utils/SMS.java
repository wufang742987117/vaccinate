package com.thinkgem.jeesite.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;

/**
 * 发送短信
 * 
 * @author ngh
 * @datetime [2016年7月26日 下午3:12:11]
 *
 */
public final class SMS {
	
	public static final int PLAN_NOTICE = 3029011;
	/** 疫苗注射完成模板 */
	public static final String TEMP_FINISH = "SMS_61060259";
	/** 集体通知模板 */
	public static final String TEMP_NOTICE = "SMS_60965095";
	
	private SMS() {
	}

	/**
	 * 发送短信（验证码）
	 * 
	 * @param mobile
	 *            手机号
	 * @return 请求返回内容
	 */
	public static String send(String mobile) {
		String random = RandomStringUtils.randomAlphanumeric(16);
		String currentSeconds = String.valueOf(System.currentTimeMillis() / 1000);
		String json = Http.post(Global.getConfig("sms.uri"), 
				new ImmutableMap.Builder<String, String>()
					.put("AppKey", Global.getConfig("sms.appKey"))
					.put("Nonce", random)
					.put("CurTime", currentSeconds)
					.put("CheckSum", Hash.sha1(Global.getConfig("sms.appSecret") + random + currentSeconds))
					.build(), 
				new ImmutableMap.Builder<String, String>()
					.put("mobile", mobile)
					.build());
		Map<String, Object> jsonMap = Maps.newHashMap();
		try {
			jsonMap = JsonMapper.nonDefaultMapper().readValue(json, new TypeReference<Map<String, Object>>() {});
		} catch (Exception e) {
			
		}
		if ((int) jsonMap.get("code") == 200)
			return (String) jsonMap.get("obj");
		return StringUtils.EMPTY;
	}
	
	/**
	 * 发送模板短信
	 * 
	 * @param templateid
	 *            模板id
	 * @param mobiles
	 *            手机号集合
	 */
	public static void send(int templateid, List<String> mobiles) {
		send(templateid, mobiles, null);
	}
	
	/**
	 * 发送模板短信
	 * 
	 * @param templateid
	 *            模板id
	 * @param mobiles
	 *            手机号集合
	 * @param params
	 *            模板变量参数值
	 */
	public static String send(int templateid, List<String> mobiles, List<String> params) {
		String random = RandomStringUtils.randomAlphanumeric(16);
		String currentSeconds = String.valueOf(System.currentTimeMillis() / 1000);
		return Http.post(Global.getConfig("sms.templateUri"), 
				new ImmutableMap.Builder<String, String>()
					.put("AppKey", Global.getConfig("sms.appKey"))
					.put("Nonce", random)
					.put("CurTime", currentSeconds)
					.put("CheckSum", Hash.sha1(Global.getConfig("sms.appSecret") + random + currentSeconds))
					.build(), 
				new ImmutableMap.Builder<String, String>()
					.put("templateid", String.valueOf(templateid))
					.put("mobiles", JsonMapper.toJsonString(mobiles))
					.put("params", JsonMapper.toJsonString(params))
					.build());
	}
	
	
	/**
	 * 
	 * @author fuxin
	 * @date 2017年2月23日 上午11:46:38
	 * @description 
	 *		TODO
	 * @param phone(电话号码，多个用,分格)  args(参数{name:'data1',time:'data2'})
	 * @return
	 * @throws ApiException
	 * @throws UnsupportedEncodingException 
	 *
	 */
	public static String sendaliMSM(String phone, Map<String, Object> args, String TemplateCode) throws ApiException, UnsupportedEncodingException{
		if(!"open".equals(Global.getConfig("sms.option"))){
			return  "短信发送功能已关闭,若要打开此功能,请将配置文件 sms.option修改为open";
		}
		TaobaoClient client = new DefaultTaobaoClient(Global.getConfig("sms.templateUri"), Global.getConfig("sms.appKey"), Global.getConfig("sms.appSecret"));
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend( "" );
		req.setSmsType( "normal" );
		req.setSmsFreeSignName(Global.getConfig("sms.freeSignName"));
		req.setSmsParamString(JsonMapper.toJsonString(args));
		req.setRecNum(phone);
		req.setSmsTemplateCode(TemplateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		return rsp.getBody();
	}
	
	
	
}