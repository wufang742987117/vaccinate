package com.junl.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author chenmaolong
 * @date 2017年2月21日 下午2:22:19
 * @description 
 *		接口统一响应
 */
public class JsonBuild {
	
	/**
	 * 
	 * @method build
	 * @author LEON
	 * @date 2015年8月25日 上午11:24:40
	 * @description
	 *		TODO
	 *
	 * @param success
	 * @param msg
	 * @param t
	 * @return
	 */
	public static <T> Map<String, Object> build(boolean success, String msg, String status, T t) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", success);
		resultMap.put("msg", msg);
		resultMap.put("status", status);
		resultMap.put("data", t);
		
		return resultMap;
	}
	
	/**
	 * 
	 * @method buildSystemError
	 * @author LEON
	 * @date 2015年8月29日 下午3:57:48
	 * @description
	 *		系统异常状态均为 success: false   status: 0
	 *
	 * @param map
	 */
	public static Map<String, Object> buildSystemError(Map<String, Object> map) {
		if(map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("success", false);
		map.put("status", ResponseStatus.SYSTEM_ERROR);
		map.put("msg", "请求失败：系统异常！");
		map.put("data", null);
		
		return map;
	}
	
	/**
	 * 
	 * @author chenmaolong
	 * @date 2017年2月21日 下午4:40:36
	 * @description 
	 *		响应提交数据校验信息
	 * @param message
	 * @return
	 *
	 */
	public static Map<String, Object> buildValidateMessage(Object message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("status", ResponseStatus.SYSTEM_ERROR);
		map.put("msg", message);
		map.put("data", null);
		
		return map;
	}
	
}
