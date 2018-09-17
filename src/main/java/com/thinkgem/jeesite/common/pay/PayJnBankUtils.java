package com.thinkgem.jeesite.common.pay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Hash;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;

import sun.misc.BASE64Encoder;

/**
 * 江南银行支付工具类
 * @author fuxin
 * @date 2017年10月17日 下午10:35:43
 * @description 
 *		TODO
 */
@SuppressWarnings("restriction")
public class PayJnBankUtils {

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
    public final static String CHANNEL_JBBANK = "jnBank";
	
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
	public static Map<String, String> genOrder(Map<String,String> param, BsManageProduct... bsManageProducts){
		if(bsManageProducts.length == 0){
			return null;
		}
		String timestamp = System.currentTimeMillis() + "";
		
		Map<String, String> order = new HashMap<String, String>();
        order.put("channel", Global.getConfig("JnBank_channel"));	//渠道号
        order.put("outerNumber", genOrderNo(param.get("vaccineType").toString()));	//订单号
        order.put("outerDtTm", timestamp);	//订单时间
        order.put("merId", Global.getConfig("JnBank_merId"));	//商户号
        order.put("orderInfo", param.get("vaccineName"));//疫苗名称
        order.put("remark", param.get("manufacturer") + "[" + param.get("batch") + "]");//疫苗厂商[批号]
        order.put("transType", "01");   //消费
        order.put("payType", PAYTYPE_WX_SCANPAY);   //扫码支付
        order.put("orderAmount", param.get("total"));	
        order.put("frontInformUrl", Global.getConfig("JnBank_frontInformUrl"));
        order.put("backInformUrl", Global.getConfig("JnBank_backInformUrl"));
        order.put("currencyCode", "156");	//人民币

        Map<String,Object> optional = new HashMap<String, Object>();
        optional.put("o_no", order.get("outerNumber"));
        optional.put("chan", CHANNEL_JBBANK);
        optional.put("src", param.get("source"));
        optional.put("ben", param.get("officeCode"));	//收款方
        optional.put("c_no", param.get("childCode"));	//儿童/成人编号
        optional.put("v_t", param.get("vaccineType"));	//疫苗类型
        optional.put("tt", (int)(Double.parseDouble(param.get("manufacturer"))* 100));	//合计
        StringBuilder detail = new StringBuilder();
        for (BsManageProduct product : bsManageProducts) {
//            optional.put("v", "");	//疫苗id
//            optional.put("b", "");	//疫苗批号
//            optional.put("m", "");	//厂商
//            optional.put("p", "");	//价格
        	//vaccine_batch_manufacturerCode_price_num 	
            detail.append(product.getVaccineid() + "_" + product.getBatchno() + "_" + product.getCode() + "_" + (int)(product.getSellprice()*100) + product.getRest() + "#");
        }
        optional.put("v", detail.toString());
        order.put("attach", optional.toString());
		
		return signParam(order);
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
    public static Map<String,String> signParam(Map<String, String> map) {
        String key = "123456";
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
        String result = Hash.md5(sb.toString()).toUpperCase();
        map.put("sign", result);

        String jsonStr = JsonMapper.toJsonString(map);
        String params = new BASE64Encoder().encode(jsonStr.getBytes());
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
    public static String genOrderNo(String vaccineType){
    	String timestamp = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + IdGen.randomLong(4); //14+4 =18 32-4
    	if(VACCINE_TYPE_CHILD.equals(vaccineType)){
    		timestamp = "OFF" + "C"+timestamp;
    	}else if(VACCINE_TYPE_ADULT.equals(vaccineType)){
    		timestamp = "OFF" + "A"+timestamp;
    	}
    	return timestamp;
    }
}
