/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.text.MessageFormat;

import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;

/**
 * Token工具类
 * @author ThinkGem
 * @version 2013-11-03
 */
public class TokenUtils {
	
	private static final String CACHE_NAME = "tokenCache";
	private static final String CACHE_KEY_TOKEN = "cache_token_";
	public static String APPID;
	public static String SECRET;
	
	private static String GENTOKEN_URL = "/api/sysToken/genToken?appid={0}&secret={1}";

	
	/**
	 * 获取token，若获取失败则返回null<br>有效期7200秒
	 * @author fuxin
	 * @date 2017年11月11日 下午3:35:51
	 * @description 
	 * @return String / StringUtils.EMPTY
	 *
	 */
	public static String genToken(){
		return genToken(false);
	}
	
	/**
	 * 获取token，若获取失败则返回null<br>有效期7200秒
	 * @author fuxin
	 * @date 2017年11月11日 下午3:35:51
	 * @description 
	 * @param isNew 是否重新生成  true-重新生成 false-优先使用缓存
	 * @return String / StringUtils.EMPTY
	 *
	 */
	public static String genToken(boolean isNew){
		if(StringUtils.isEmpty(APPID) || StringUtils.isEmpty(SECRET)){
			APPID = Global.getConfig("hbepi.appid", StringUtils.EMPTY);
			SECRET = Global.getConfig("hbepi.secret", StringUtils.EMPTY);
			if(StringUtils.isEmpty(APPID) || StringUtils.isEmpty(SECRET)){
				return StringUtils.EMPTY;
			}
		}
		
		//获取换成中token
		Object localToken = CacheUtils.get(CACHE_NAME, CACHE_KEY_TOKEN);
		if(!isNew && localToken != null){
			return (String) localToken;
		}
		String genTokenURL = Global.getConfig("hbepi.url") + GENTOKEN_URL;
		genTokenURL = MessageFormat.format(genTokenURL, APPID, SECRET);
		String hbepiToken = HttpClientReq.httpClientPostJson(genTokenURL, "");
		//将token存在缓存中，缓存超时7000秒
		if(StringUtils.isNotBlank(hbepiToken)){
			CacheUtils.put(CACHE_NAME, CACHE_KEY_TOKEN, hbepiToken);
		}
		return hbepiToken;
	}
	
	/**
	 * 手动清除token缓存
	 * @author fuxin
	 * @date 2017年11月11日 下午3:52:52
	 * @description 
	 *		TODO
	 *
	 */
	public static void clearToken(){
		CacheUtils.remove(CACHE_NAME, CACHE_KEY_TOKEN);
	}
    
}