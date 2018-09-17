/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.JSONMessage;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * @author zhouqj
 * @date 2017年12月15日 下午2:23:12
 * @description 
 *		和市平台同步用户数据的接口
 */
@Controller
@RequestMapping(value = "${frontPath}/sys")
public class UserSyncController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年12月15日 下午2:23:45
	 * @description 
	 *		保存用户信息
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = {"saveUser"})
	@ResponseBody 
	@Transactional(readOnly=false)
	public JSONMessage saveUser(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody(required = false)  String param) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map = (Map) JsonMapper.fromJsonString(param, Map.class);
		JSONMessage returnMsg;
		User user = (User) JsonMapper.fromJsonString(map.get("user").toString(), User.class);
		//接种单位编码
		String localCode = map.get("localCode").toString();
		//1 新增 2 修改用户基本信息 3 修改密码
		String type = map.get("type").toString();
		if (user == null || StringUtils.isBlank(localCode) || StringUtils.isBlank(type) ) {
			returnMsg = new JSONMessage(false,null);
			returnMsg.setMsg("缺失参数！保存失败！");
			return returnMsg;
		}
		logger.info("接收保存用户请求 -->[user:" + user + ",localCode:" + localCode + "]");
		String loginName = user.getLoginName();
		String oldLoginName = user.getOldLoginName();
		if (!"3".equals(type)) {//密码修改不校验登陆名
			if (loginName !=null && loginName.equals(oldLoginName)) {
				logger.info("登陆名未修改 -->[LoginName:" + user.getLoginName() + "]");
			} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
				logger.info("登陆名验证通过 -->[LoginName:" + user.getLoginName() + "]");
			}else {
				logger.info("操作失败，登陆名重复 -->[LoginName:" + user.getLoginName() + "]");
				returnMsg = new JSONMessage(false, "操作失败，登陆名重复");
				return returnMsg;
			}
		}
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(user.getNewPassword());
		}
		// 获取接种单位
		Office companyOffice = officeService.getOfficeByCode(localCode);
		if (companyOffice == null || StringUtils.isBlank(companyOffice.getId())) {
			returnMsg = new JSONMessage(false,null);
			returnMsg.setMsg("门诊站点编号不存在，保存失败！");
			return returnMsg;
		}
		user.setCompany(companyOffice);
		// 获取接种单位下默认科室
		Office office = officeService.getOfficeByParentId(companyOffice);
		if (office == null || StringUtils.isBlank(office.getId())) {
			returnMsg = new JSONMessage(false,null);
			returnMsg.setMsg("科室不存在，保存失败！");
			return returnMsg;
		}
		user.setOffice(office);
		try {
			if ("1".equals(type)) {
				// 角色数据有效性验证，过滤不在授权内的角色
				List<Role> roleList = Lists.newArrayList();
				for (Role r : systemService.findRole(new Role())){
					if (!"6".equals(r.getId())){
						roleList.add(r);
					}
				}
				user.setRoleList(roleList);
				systemService.insertUser(user);
			}else if ("2".equals(type)){
				/*User updateUser = systemService.getUser(user.getId());
				user.setCompany(updateUser.getCompany());
				user.setOffice(updateUser.getOffice());*/
				systemService.updateUser(user);
			}else if ("3".equals(type)) {
				systemService.updatePassword(user.getId(), user.getLoginName(), user.getNewPassword());
			}else {
				throw new Exception("保存失败，操作类型未定义");
			}
		}catch(Exception e) {
			e.printStackTrace();
			returnMsg = new JSONMessage(true,null);
			returnMsg.setMsg("保存失败！");
			logger.error("保存报错！ -->[LoginName:"  +user.getLoginName()+ "]");
			return returnMsg;
		}
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();// 清除当前用户缓存
		}
		returnMsg = new JSONMessage(true,null);
		returnMsg.setMsg("保存成功！");
		logger.info("保存成功！ -->[LoginName:"+user.getLoginName()+ "]");
		return returnMsg;
	}
	
}