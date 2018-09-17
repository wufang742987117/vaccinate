/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nation.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.nation.entity.BsNation;
import com.thinkgem.jeesite.modules.nation.service.BsNationService;

/**
 * 民族Controller
 * @author wang
 * @version 2017-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/nation/bsNation")
public class BsNationController extends BaseController {

	@Autowired
	private BsNationService bsNationService;
	
	@ModelAttribute
	public BsNation get(@RequestParam(required=false) String id) {
		BsNation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsNationService.get(id);
		}
		if (entity == null){
			entity = new BsNation();
		}
		return entity;
	}
	
	@RequiresPermissions("nation:bsNation:view")
	@RequestMapping(value = {"list", ""})
	public String list(BsNation bsNation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BsNation> page = bsNationService.findPage(new Page<BsNation>(request, response), bsNation); 
		model.addAttribute("page", page);
		return "modules/nation/bsNationList";
	}

	@RequiresPermissions("nation:bsNation:view")
	@RequestMapping(value = "form")
	public String form(BsNation bsNation, Model model) {
		model.addAttribute("bsNation", bsNation);
		return "modules/nation/bsNationForm";
	}

	@RequiresPermissions("nation:bsNation:edit")
	@RequestMapping(value = "save")
	public String save(BsNation bsNation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsNation)){
			return form(bsNation, model);
		}
		bsNationService.save(bsNation);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/nation/bsNation/?repage";
	}
	
	@RequiresPermissions("nation:bsNation:edit")
	@RequestMapping(value = "delete")
	public String delete(BsNation bsNation, RedirectAttributes redirectAttributes) {
		bsNationService.delete(bsNation);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/nation/bsNation/?repage";
	}

}