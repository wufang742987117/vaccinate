/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysVaccDepartmentInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysVaccDepartmentInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 科室信息管理Controller
 * @author yangjian
 * @version 2018-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysVaccDepartmentInfo")
public class SysVaccDepartmentInfoController extends BaseController {

	@Autowired
	private SysVaccDepartmentInfoService sysVaccDepartmentInfoService;
	
	@ModelAttribute
	public SysVaccDepartmentInfo get(@RequestParam(required=false) String id) {
		SysVaccDepartmentInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysVaccDepartmentInfoService.get(id);
		}
		if (entity == null){
			entity = new SysVaccDepartmentInfo();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SysVaccDepartmentInfo sysVaccDepartmentInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SysVaccDepartmentInfo> departmentInfos = sysVaccDepartmentInfoService.findList(sysVaccDepartmentInfo);
		for (SysVaccDepartmentInfo departmentInfo : departmentInfos) {
			sysVaccDepartmentInfo = departmentInfo;
		}
		model.addAttribute("sysVaccDepartmentInfo", sysVaccDepartmentInfo);
		return "modules/sys/sysVaccDepartmentInfoList";
	}

	@RequestMapping(value = "form")
	public String form(SysVaccDepartmentInfo sysVaccDepartmentInfo, Model model) {
		model.addAttribute("sysVaccDepartmentInfo", sysVaccDepartmentInfo);
		return "modules/sys/sysVaccDepartmentInfoForm";
	}

	@RequestMapping(value = "save")
	public String save(SysVaccDepartmentInfo sysVaccDepartmentInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysVaccDepartmentInfo)){
			return form(sysVaccDepartmentInfo, model);
		}
		sysVaccDepartmentInfoService.save(sysVaccDepartmentInfo);
		addMessage(redirectAttributes, "保存科室信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysVaccDepartmentInfo/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(SysVaccDepartmentInfo sysVaccDepartmentInfo, RedirectAttributes redirectAttributes) {
		sysVaccDepartmentInfoService.delete(sysVaccDepartmentInfo);
		addMessage(redirectAttributes, "删除科室信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysVaccDepartmentInfo/?repage";
	}
	
	@RequestMapping(value="getCurrentInfo")
	@ResponseBody
	public String getCurrentInfo(SysVaccDepartmentInfo sysVaccDepartmentInfo, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> data = new HashMap<String, Object>();
		List<SysVaccDepartmentInfo> departmentInfos = sysVaccDepartmentInfoService.findList(sysVaccDepartmentInfo);
		for (SysVaccDepartmentInfo departmentInfo : departmentInfos) {
			sysVaccDepartmentInfo = departmentInfo;
		}
		data.put("departmentInfo", sysVaccDepartmentInfo);
		
		//清除用户缓存
		UserUtils.clearCache();
		User user = UserUtils.getUser();
		data.put("user", user);
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
}