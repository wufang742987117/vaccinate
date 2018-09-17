package com.thinkgem.jeesite.modules.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.junl.common.JsonBuild;
import com.junl.common.ResponseStatus;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 一体机检查版本信息接口
 * @author fuxin
 * @date 2017年5月5日 上午10:25:12
 * @description 
 *		TODO
 */
@RestController
@RequestMapping(value = "${frontPath}/sys/app")
public class AppController extends BaseController{

	/**
	 * 检查版本
	 * @author fuxin
	 * @date 2017年5月5日 上午11:19:24
	 * @description 
	 *		TODO
	 * @param request
	 * @param args
	 * @return
	 *
	 */
	@RequestMapping("checkVersion")
	public @ResponseBody Map<String, Object> checkVersion(HttpServletRequest request,  
			@RequestParam(value="appName", required=false, defaultValue="")String appName, 
			@RequestParam(value="appVersion", required=false, defaultValue="")String appVersion){
		
		Map<String, Object> json = null;
		logger.info("接到app检查版本请求：-->" + "[" + appName + "," + appVersion + "]");
		try {
			if(StringUtils.isNotBlank(appVersion) && StringUtils.isNotBlank(appName)){
				//解析参数
				if(StringUtils.isNotBlank(appVersion) && StringUtils.isNotBlank(appName)){
					//获取对应版本号
					String curVersion = Global.getConfig("appVersion." + appName);
					if(appVersion.equals(curVersion)){
						//版本相同						
						json = JsonBuild.build(true, "当前已经是最新版本",ResponseStatus.REQUEST_SUCCESS, "");
					}else{
						String basepath = request.getRequestURL().toString().replaceAll(request.getServletPath(), "");
						//版本不同
						json = JsonBuild.build(true, "有新版本可用"+curVersion,ResponseStatus.REQUEST_SUCCESS, basepath + "/static/apk/" + appName + ".apk");
					}
				}
			}else{
				//参数异常
				json = JsonBuild.build(false, "检查失败,参数异常",ResponseStatus.SYSTEM_ERROR, "");
			}
		} catch (Exception e) {
			logger.error("系统异常",e);
			json = JsonBuild.build(false, "检查失败,参数异常",ResponseStatus.SYSTEM_ERROR, "");
		}finally{
			if(null == json){
				json = JsonBuild.build(false, "检查失败,未知错误",ResponseStatus.SYSTEM_ERROR, "");
			}
		}
		logger.info("app检查版本请求结束  参数-->" +"[" + appName + "," + appVersion + "], return -->" + JsonMapper.toJsonString(json));
		return json;
	}
	
}
