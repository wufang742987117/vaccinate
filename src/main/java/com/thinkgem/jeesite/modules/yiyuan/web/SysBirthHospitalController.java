/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.yiyuan.web;

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
import com.thinkgem.jeesite.modules.yiyuan.entity.SysBirthHospital;
import com.thinkgem.jeesite.modules.yiyuan.service.SysBirthHospitalService;

/**
 * 信息Controller
 * @author 王德地
 * @version 2017-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/yiyuan/sysBirthHospital")
public class SysBirthHospitalController extends BaseController {

	@Autowired
	private SysBirthHospitalService sysBirthHospitalService;
	
	@ModelAttribute
	public SysBirthHospital get(@RequestParam(required=false) String id) {
		SysBirthHospital entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysBirthHospitalService.get(id);
		}
		if (entity == null){
			entity = new SysBirthHospital();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(SysBirthHospital sysBirthHospital, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysBirthHospital> page = sysBirthHospitalService.findPage(new Page<SysBirthHospital>(request, response), sysBirthHospital); 
		model.addAttribute("page", page);
		return "modules/yiyuan/sysBirthHospitalList";
	}


	@RequestMapping(value = "form")
	public String form(SysBirthHospital sysBirthHospital, Model model) {
		model.addAttribute("sysBirthHospital", sysBirthHospital);
		return "modules/yiyuan/sysBirthHospitalForm";
	}

	
	@RequestMapping(value = "save")
	public String save(SysBirthHospital sysBirthHospital, Model model, RedirectAttributes redirectAttributes) {
		SysBirthHospital BirthHospital=new SysBirthHospital();
		//判断是否存在相同的医院编码
		BirthHospital.setCode(sysBirthHospital.getCode());
		if(sysBirthHospitalService.findList(BirthHospital).size()>0&&StringUtils.isBlank(sysBirthHospital.getId())){
			model.addAttribute("arg", "你添加的医院编码已存在,请修改");
			return "modules/yiyuan/sysBirthHospitalForm";
		}
		//判断是否存在相同的医院名称
		BirthHospital=new SysBirthHospital();
		BirthHospital.setName(sysBirthHospital.getName());
		if(sysBirthHospitalService.findList(BirthHospital).size()>0&&StringUtils.isBlank(sysBirthHospital.getId())){
			model.addAttribute("arg", "你添加的医院名称已存在，请修改");
			return "modules/yiyuan/sysBirthHospitalForm";
		}
		sysBirthHospitalService.save(sysBirthHospital);
		model.addAttribute("isok", "ok");
		return "modules/yiyuan/sysBirthHospitalForm";
	}

	@RequestMapping(value = "delete")
	public String delete(SysBirthHospital sysBirthHospital, RedirectAttributes redirectAttributes) {
		sysBirthHospitalService.delete(sysBirthHospital);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/yiyuan/sysBirthHospital/?repage";
	}

}