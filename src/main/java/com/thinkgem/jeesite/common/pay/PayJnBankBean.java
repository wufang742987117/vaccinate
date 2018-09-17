package com.thinkgem.jeesite.common.pay;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Hash;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;

import net.sf.json.JSONObject;

/**
 * 江南银行支付工具类
 * 
 * @author fuxin
 * @date 2017年10月17日 下午10:35:43
 * @description TODO
 */
public class PayJnBankBean {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    /** 微信-扫码支付 */
    public final static String PAYTYPE_WX_SCANPAY = "12";
    /** 微信-app */
    public final static String PAYTYPE_WX_APP = "13";
    /** 微信-公众号 */
    public final static String PAYTYPE_WX_MP = "14";
    /** 疫苗类别-儿童 */
    public final static String VACCINE_TYPE_CHILD = "1";
    /** 疫苗类别-成人 */
    public final static String VACCINE_TYPE_ADULT = "2";
    
    /** 来源 线下接种台_扫码 1_1 */
    public final static String SOURCE_OFF_SACN_IN = "1_1";
    /** 来源 线下登记台_扫码 1_2 */
    public final static String SOURCE_OFF_SACN_AS = "1_2";
    /** 来源 一体机_扫码 2_1 */
    public final static String SOURCE_SELF_SACN = "2_1";
    
    /** 渠道江南银行 */
    public final static String CHANNEL_JNBANK = "JN";

	private String vaccineType; // 疫苗类别 VACCINE_TYPE_CHILD VACCINE_TYPE_ADULT
	private String vaccineName; // 疫苗名称
	private String remarks;
	private double total; // 合计
	private String officeCode; // 收款站点编码
	private String source; // 付款来源 SOURCE_OFF_SACN_IN...
	private String childCode; // 儿童/成人编号
	
	private List<BsManageProduct> bsManageProducts;	//疫苗列表
	private Map<String,Object> optional;
	private String orderNo;		//订单号
	private Date timestamp;		//订单号

	private Map<String, Object> para; 
	
	public String getVaccineType() {
		return vaccineType;
	}

	public String getVaccineName() {
		return vaccineName;
	}


	public double getTotal() {
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

	public List<BsManageProduct> getBsManageProducts() {
		return bsManageProducts;
	}

	public void setVaccineType(String vaccineType) {
		this.vaccineType = vaccineType;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public void setTotal(double total) {
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

	public void setBsManageProducts(List<BsManageProduct> bsManageProducts) {
		this.bsManageProducts = bsManageProducts;
	}
	
	public Map<String, Object> getOptional() {
		return optional;
	}

	public void setOptional(Map<String, Object> optional) {
		this.optional = optional;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Map<String, Object> getPara() {
		return para;
	}

	public void setPara(Map<String, Object> para) {
		this.para = para;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * 获取签名
	 * @author fuxin
	 * @date 2017年10月17日 下午10:39:59
	 * @description 
	 *		TODO
	 * @param map
	 * @return
	 *
	 */
    public Map<String,String> signParam(Map<String, String> map) {
    	String key = Global.getConfig("JnBank_signKey", "123456");
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        sb.append("key=" + key);
        logger.info("江南银行支付sb.toString:"+sb.toString());
        
        String result = Hash.md5(sb.toString()).toUpperCase();
        map.put("sign", result);
        logger.info("江南银行支付sign:"+result);

        String jsonStr = JsonMapper.toJsonString(map);
        String params = null;
		try {
			params = Base64.getEncoder().encodeToString(jsonStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("江南银行支付base64:"+params);
		
        Map<String,String> paramsMap = new HashMap<String, String>();
        paramsMap.put("params", params);
        return paramsMap;
    }
    
    /**
     * 根据疫苗类别生成订单号
     * @author fuxin
     * @date 2017年10月17日 下午11:09:20
     * @description 
     *		TODO
     * @param vaccineType
     * @return
     *
     */
    private String genOrderNo(String vaccineType){
    	String timestamp = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + IdGen.randomLong(2); //14+4 =18 32-4
		timestamp = "OFF" + CHANNEL_JNBANK +VACCINE_TYPE_CHILD +timestamp;
		this.orderNo= timestamp;
    	return timestamp;
    }

    
	/**
	 * 生成扫码支付订单
	 * @author fuxin
	 * @date 2017年10月17日 下午10:43:10
	 * @description 
	 *		TODO
	 * @param 
	 * <p>
	 * 		vaccineType:疫苗类别  VACCINE_TYPE_CHILD VACCINE_TYPE_ADULT<br>
	 * 		vaccineName:疫苗名称<br>
	 * 		batch:批号<br>
	 * 		manufacturer:厂商<br>
	 * 		total:合计<br>
	 * 		officeCode:收款站点编码<br>
	 * 		source:付款来源 SOURCE_OFF_SACN_IN... <br>
	 * 		childCode:儿童/成人编号 <br>
	 * </p>
	 * @return
	 *
	 */
	public Map<String, String> genOrder(){
		if(bsManageProducts == null || bsManageProducts.size() == 0){
			return null;
		}
		String timestamp = System.currentTimeMillis() + "";
		
		Map<String, String> order = new HashMap<String, String>();
        order.put("channel", Global.getConfig("JnBank_channel"));	//渠道号
        order.put("outerNumber", genOrderNo(vaccineType));	//订单号
        order.put("outerDtTm", timestamp);	//订单时间
        order.put("merId", Global.getConfig("JnBank_merId"));	//商户号
        order.put("orderInfo", vaccineName);//疫苗名称
        order.put("remark", remarks);//描述
        order.put("transType", "01");   //消费
        order.put("payType", PAYTYPE_WX_SCANPAY);   //扫码支付
        order.put("orderAmount", total+"");	
        order.put("frontInformUrl", Global.getConfig("JnBank_frontInformUrl"));
        order.put("backInformUrl", Global.getConfig("JnBank_backInformUrl"));
        order.put("currencyCode", "156");	//人民币

        Map<String,Object> optional = new HashMap<String, Object>();
        String optionalStr = order.get("outerNumber") + "|"  + source  + "|" + officeCode + "|" + childCode + "|" + (int)(total* 100) + "#";
        optional.put("no", order.get("outerNumber"));
        optional.put("chan", CHANNEL_JNBANK);
        optional.put("src", source);
        optional.put("ben", officeCode);	//收款方
        optional.put("cno", childCode);	//儿童/成人编号
        optional.put("vt", vaccineType);	//疫苗类型
        optional.put("tt", (int)(total* 100));	//合计
        StringBuilder detail = new StringBuilder();
        for (BsManageProduct product : bsManageProducts) {
            detail.append(product.getVaccineid() + "_" + product.getBatchno() + "_" + product.getCode() + "_" + (int)(product.getSellprice()*100)+ "_" + product.getStorenum() + "#");
        }
        optional.put("v", detail.toString());
        optionalStr += detail.toString();
        order.put("attach", optionalStr);
        logger.info("江南银行生成支付订单：" + JsonMapper.toJsonString(order));
		return signParam(order);
	}
	
	/**
	 * 发送预下单请求，获取付款二维码信息
	 * @author fuxin
	 * @date 2017年10月18日 下午4:07:12
	 * @description 
	 *		TODO
	 * @return qrCodePath
	 *
	 */
	public String sendOrder(PayQueryThread payQueryThread){
		Map<String, String> order = genOrder();
		String qrCodePath = "";
		try {
			String result = HttpClientReq.httpClientPostJson(Global.getConfig("JnBank_payHost") + "?params=" + order.get("params"), JsonMapper.toJsonString(order));
			logger.info("江南银行支付请求返回:"+result);
			JSONObject obj = JSONObject.fromObject(result);
			qrCodePath = Global.getConfig("JnBank_payQrCode") + "?code=" + obj.getString("code");
			payQueryThread.setOrderNo(orderNo);
			new Thread(payQueryThread).start();
		} catch (Exception e) {
			logger.error("江南银行支付请求失败"+e.getMessage());
		}
		return qrCodePath;
	}
	
	/**
	 * 生成查询订单
	 * @author fuxin
	 * @date 2017年10月17日 下午10:43:10
	 * @description 
	 *		TODO
	 * @param 
	 * <p>
	 * 		vaccineType:疫苗类别  VACCINE_TYPE_CHILD VACCINE_TYPE_ADULT<br>
	 * 		vaccineName:疫苗名称<br>
	 * 		batch:批号<br>
	 * 		manufacturer:厂商<br>
	 * 		total:合计<br>
	 * 		officeCode:收款站点编码<br>
	 * 		source:付款来源 SOURCE_OFF_SACN_IN... <br>
	 * 		childCode:儿童/成人编号 <br>
	 * </p>
	 * @return
	 *
	 */
	public Map<String, String> genQueryOrder(){
		Map<String, String> order = new HashMap<String, String>();
		order.put("channel", Global.getConfig("JnBank_channel"));	//渠道号
//		order.put("outerDtTm", timestamp.getTime()+"");	//订单时间
		order.put("orderNumber", orderNo);	//订单号
		order.put("currencyType", "156");	//人民币
		order.put("merId", Global.getConfig("JnBank_merId"));	//商户号
		
		logger.info("江南银行生成查询订单：" + JsonMapper.toJsonString(order));
		return signParam(order);
	}
	
	/**
	 * 发送预下单请求，获取付款二维码信息
	 * @author fuxin
	 * @date 2017年10月18日 下午4:07:12
	 * @description 
	 *		TODO
	 * @return qrCodePath
	 *
	 */
	public String queryOrder(){
		Map<String, String> order = genQueryOrder();
		System.out.println(JsonMapper.toJsonString(order));
		String qrCodePath = "";
		try {
			String result = HttpClientReq.httpClientPostJson(Global.getConfig("JnBank_queryHost") + "?params=" + order.get("params"), JsonMapper.toJsonString(order));
			logger.info("江南银行支付请求返回:"+result);
			JSONObject obj = JSONObject.fromObject(result);
			System.out.println(obj.toString());
		} catch (Exception e) {
			logger.error("江南银行支付请求失败"+e.getMessage());
		}
		return qrCodePath;
	}

	
}
