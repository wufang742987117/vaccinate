/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.department.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;
import com.thinkgem.jeesite.modules.department.service.SysVaccDepartmentService;

/**
 * 预防门诊编码Controller
 * @author wangdedi
 * @version 2017-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/department/sysVaccDepartment")
public class SysVaccDepartmentController extends BaseController {

	@Autowired
	private SysVaccDepartmentService sysVaccDepartmentService;
	
	@ModelAttribute
	public SysVaccDepartment get(@RequestParam(required=false) String id) {
		SysVaccDepartment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysVaccDepartmentService.get(id);
		}
		if (entity == null){
			entity = new SysVaccDepartment();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(SysVaccDepartment sysVaccDepartment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysVaccDepartment> page = sysVaccDepartmentService.findPage(new Page<SysVaccDepartment>(request, response), sysVaccDepartment); 
		model.addAttribute("page", page);
		return "modules/department/sysVaccDepartmentList";
	}

	
	@RequestMapping(value = "form")
	public String form(SysVaccDepartment sysVaccDepartment, Model model) {
		model.addAttribute("sysVaccDepartment", sysVaccDepartment);
		return "modules/department/sysVaccDepartmentForm";
	}


	@RequestMapping(value = "save")
	public String save(SysVaccDepartment sysVaccDepartment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysVaccDepartment)){
			return form(sysVaccDepartment, model);
		}
		sysVaccDepartmentService.save(sysVaccDepartment);
		/*addMessage(redirectAttributes, "保存信息成功");*/
		model.addAttribute("isok", "1");
		model.addAttribute("sysVaccDepartment",sysVaccDepartment);
		return "modules/department/sysVaccDepartmentForm";
	}
	
	
	@RequestMapping(value = "delete")
	public String delete(SysVaccDepartment sysVaccDepartment, RedirectAttributes redirectAttributes) {
		sysVaccDepartmentService.delete(sysVaccDepartment);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/department/sysVaccDepartment/?repage";
	}

}