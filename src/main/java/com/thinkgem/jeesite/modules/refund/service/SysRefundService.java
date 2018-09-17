/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.refund.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.refund.dao.SysRefundDao;
import com.thinkgem.jeesite.modules.refund.entity.SysRefund;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 退款说明Service
 * @author wangdedi
 * @version 2017-05-17
 */
@Service
@Transactional(readOnly = true)
public class SysRefundService extends CrudService<SysRefundDao, SysRefund> {

	public SysRefund get(String id) {
		return super.get(id);
	}
	
	public List<SysRefund> findList(SysRefund sysRefund) {
		return super.findList(sysRefund);
	}
	
	public Page<SysRefund> findPage(Page<SysRefund> page, SysRefund sysRefund) {
		return super.findPage(page, sysRefund);
	}
	
	@Transactional(readOnly = false)
	public void save(SysRefund sysRefund) {
		super.save(sysRefund);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysRefund sysRefund) {
		super.delete(sysRefund);
	}
	/**
	 * 退款记录插入
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public String refund(ChildVaccinaterecord childVaccinaterecord){
		/*Map<String, Object> map=new HashMap<String, Object>();
		//BeeCloud的唯一标识
		String app_id=Global.getConfig("app_id");
		//时间戳，毫秒数
		Long timestamp=	new Date().getTime();
		//BeeCloud秘钥
		String master_secret=Global.getConfig("master_secret");
		
		 * 加密签名
		 * 算法: md5(app_id+timestamp+master_secret)，32位16进制格式,不区分大小写
		 
		String app_sign=getMd5(app_id+timestamp+master_secret);
		
		 * 商户退款单号
		 * 格式为:退款日期(8位) + 流水号(3~24 位)。请自行确保在商户系统中唯一，且退款日期必须是发起退款的当天日期,同一退款单号不可重复提交，否则会造成退款单重复。
		 * 流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”
		 
		String refund_no=DateUtils.formatDate(new Date(), "yyyyMMdd")+timestamp;
		//商户订单号(发起支付时填写的订单号)
		String bill_no=childVaccinaterecord.getOrderNO();
		//退款金额(必须为正整数，单位为分，必须小于或等于对应的已支付订单的total_fee)
		int  refund_fee=0;
		//判断订单是否买保险
		if(childVaccinaterecord.getPrice()>0&&"1".equals(childVaccinaterecord.getInsurance())){
			refund_fee=(int) (childVaccinaterecord.getPrice()*100)+200;
		}else if(childVaccinaterecord.getPrice()>0&&!"1".equals(childVaccinaterecord.getInsurance())){
			refund_fee=(int) (childVaccinaterecord.getPrice()*100);
		}else if(childVaccinaterecord.getPrice()==0&&"1".equals(childVaccinaterecord.getInsurance())){
			refund_fee=200;
		}
		map.put("app_id", app_id);
		map.put("timestamp", timestamp);
		map.put("app_sign", app_sign);
		map.put("refund_no", refund_no);
		map.put("bill_no", bill_no);
		map.put("refund_fee", refund_fee);
		//未找到订单号则视为支付失败，不予退款
		if(StringUtils.isBlank(bill_no)){
			return "OK";
		}
		logger.info("发起退款[" + bill_no + "]参数 --> "+JsonMapper.toJsonString(map));
		String tk=null;
		try {
			tk = HttpClientReq.httpClientPostJson(Global.getConfig("beeCloud.url"),JsonMapper.toJsonString(map));
		} catch (Exception e) {
			logger.error("退款请求异常",e);
			return "网络异常，请稍后重试";
		}
		logger.info("退款接口[" + bill_no + "]返回 --> "+tk);
		JSONObject obj = JSONObject.parseObject(tk);
		boolean b = true;
		if(obj != null){
			b =  ("OK".equals(obj.get("result_msg")));
		}
		
		if(!b){
			switch ((Integer)obj.get("result_code")) {
			case 1:
				return "根据app_id找不到对应的APP或者app_sign不正确";
			case 2:
				return "支付要素在后台没有设置";
			case 3:
				return "channel参数不合法";
			case 4:
				return "缺少必填参数";
			case 5:
				return "参数不合法";
			case 6:
				return "证书错误";
			case 7:
				return "渠道内部错误";
			case 8:
				logger.warn("退款未找到订单",JsonMapper.toJsonString(map));
				return "OK";
//				return "没有该订单";
				
			case 9:
				return "该订单没有支付成功";
				
			case 10:
				return "已超过可退款时间";
				
			case 11:
				return "该订单已有正在处理中的退款";
				
			case 12:
				return "提交的退款金额超出可退额度";
			case 13:
				return "没有该退款记录";
			default:				
				return "运行时错误";
				
			}
		}
		String tb=null;
		if("1".equals(childVaccinaterecord.getInsurance())){
			//若有保险，退保
			map=new HashMap<>();
			try {
				map.put("nid", childVaccinaterecord.getNid());
				map.put("childcode", childVaccinaterecord.getChildcode());
				logger.info("发起退保参数 --> "+JsonMapper.toJsonString(map));
				tb= HttpClientReq.httpClientPostJson(Global.getConfig("wechat.url") +"/api/refundIns.do",JsonMapper.toJsonString(map));
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("退保请求异常",e);
			}
			logger.info("退保接口返回 --> "+tb);
			
		}
		
		String 	insuranceorderno=null;
		if(StringUtils.isNoneBlank(tb)){
			JSONObject objtb = JSONObject.parseObject(tb);
			String result=(String) objtb.get("result");
			if(!"fail".equals(result)){
				insuranceorderno=(String) objtb.get("third_serial_no");	
			}
		}
		String id=childVaccinaterecord.getId();
		String childCode = childVaccinaterecord.getChildcode();
		String nid = childVaccinaterecord.getNid();
		String pid = childVaccinaterecord.getProductid();
		String childName = childVaccinaterecord.getChildname();
		String source = childVaccinaterecord.getSource();
		double price = childVaccinaterecord.getPrice();
		
		User u=UserUtils.getUser();
		
		SysRefund refund = new SysRefund();
		refund.setId(id);
		refund.setChildcode(childCode);
		refund.setChildname(childName);
		refund.setNid(nid);
		refund.setPid(pid);
		refund.setRefundmoney(price);
		refund.setRefundsource(source);
		refund.setUid(u.getId());
		refund.setOrderNo(refund_no);
		refund.setInsuranceorderno(insuranceorderno);
		super.insert(refund);*/
		return "OK";
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * MD5加密
	 * @author wangdedi
	 * @date 2017年5月19日 上午11:09:21
	 * @description 
	 *		TODO
	 * @param plainText
	 * @return
	 *
	 */
	
	public static String getMd5(String plainText) {  
		         try {  
		              MessageDigest md = MessageDigest.getInstance("MD5");  
		            md.update(plainText.getBytes());  
		            byte b[] = md.digest();  
		   
		            int i;  
		 
		           StringBuffer buf = new StringBuffer("");  
		           for (int offset = 0; offset < b.length; offset++) {  
		                 i = b[offset];  
		                 if (i < 0)  
		                    i += 256;  
		                 if (i < 16)  
		                     buf.append("0");  
		                 buf.append(Integer.toHexString(i));  
		             }  
		            //32位加密  
		           return buf.toString();  
		             // 16位的加密  
		             //return buf.toString().substring(8, 24);  
		         } catch (NoSuchAlgorithmException e) {  
		             e.printStackTrace();  
		             return null;  
		         }  
		  
		     }  
}