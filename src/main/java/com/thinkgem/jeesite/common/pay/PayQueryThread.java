package com.thinkgem.jeesite.common.pay;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;

import net.sf.json.JSONObject;

public abstract class PayQueryThread implements Runnable{
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private int total; // 合计
	private String officeCode; // 收款站点编码
	private String source; // 付款来源 SOURCE_OFF_SACN_IN...
	private String childCode; // 儿童/成人编号
	private String orderNo;		//订单号
	private Map<String, Object> optional;

	public PayQueryThread() {
		super();
	}

	public PayQueryThread(int total, String officeCode, String source, String childCode,
			String orderNo, Map<String, Object> optional) {
		super();
		this.total = total;
		this.officeCode = officeCode;
		this.source = source;
		this.childCode = childCode;
		this.orderNo = orderNo;
		this.optional = optional;
	}

	public int getTotal() {
		return total;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public String getSource() {
		return source;
	}

	public String getChildCode() {
		return childCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public Map<String, Object> getOptional() {
		return optional;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setOptional(Map<String, Object> optional) {
		this.optional = optional;
	}
	
	private String getParamJson(){
		Map<String, String> param = new HashMap<String, String>();
		param.put("total", total+"");
		param.put("officeCode", officeCode);
		param.put("source", source);
		param.put("childCode", childCode);
		param.put("orderNo", orderNo);
		return JsonMapper.toJsonString(param);
	}

	@Override
	public void run() {
		Thread.currentThread().setName("PayQueryThread[" + getOrderNo() + "]");
		long payQueryTimeout = Long.parseLong(Global.getConfig("JnBank_queryTimeOut","180"));
		long startTime = System.currentTimeMillis();
		boolean payFinish = false;
		
		//轮询
		while(!payFinish && (System.currentTimeMillis() - startTime) < (payQueryTimeout * 1000)){
			try {
				String result = HttpClientReq.httpClientPostJson(Global.getConfig("wechat.url") + "api/payStatus.do", getParamJson());
//				String result = HttpClientReq.httpClientPostJson("http://127.0.0.1/wpwx/api/payStatus.do", getParamJson());
				JSONObject json = JSONObject.fromObject(result);
				//判断支付结果
				if(json.getBoolean("success")){
					logger.info("[" + getOrderNo() + "]支付成功,继续完成业务代码");
					payFinish = true;
					doSomething(optional);
				}else{
					//支付失败则等待1秒重连
					logger.info("[" + getOrderNo() + "]支付结果等待中...");
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 轮询请求成功后做一些业务逻辑
	 * @author fuxin
	 * @param op 
	 * @date 2017年10月19日 上午9:08:49
	 * @description 
	 *		TODO
	 *
	 */
	abstract public void doSomething(Map<String, Object> op);
	
}
