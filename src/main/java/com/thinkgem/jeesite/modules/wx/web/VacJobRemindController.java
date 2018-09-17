/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.web;

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
import com.thinkgem.jeesite.modules.wx.entity.VacJobRemind;
import com.thinkgem.jeesite.modules.wx.service.VacJobRemindService;

/**
 * 微信定时提醒Controller
 * @author zhouqj
 * @version 2017-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/vacJobRemind")
public class VacJobRemindController extends BaseController {

	@Autowired
	private VacJobRemindService vacJobRemindService;
	
	@ModelAttribute
	public VacJobRemind get(@RequestParam(required=false) String id) {
		VacJobRemind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vacJobRemindService.get(id);
		}
		if (entity == null){
			entity = new VacJobRemind();
		}
		return entity;
	}
	
	@RequiresPermissions("wx:vacJobRemind:view")
	@RequestMapping(value = {"list", ""})
	public String list(VacJobRemind vacJobRemind, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<VacJobRemind> page = vacJobRemindService.findPage(new Page<VacJobRemind>(request, response), vacJobRemind); 
		model.addAttribute("page", page);
		return "modules/wx/vacJobRemindList";
	}

	@RequiresPermissions("wx:vacJobRemind:view")
	@RequestMapping(value = "form")
	public String form(VacJobRemind vacJobRemind, Model model) {
		model.addAttribute("vacJobRemind", vacJobRemind);
		return "modules/wx/vacJobRemindForm";
	}

	@RequiresPermissions("wx:vacJobRemind:edit")
	@RequestMapping(value = "save")
	public String save(VacJobRemind vacJobRemind, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vacJobRemind)){
			return form(vacJobRemind, model);
		}
		vacJobRemindService.save(vacJobRemind);
		addMessage(redirectAttributes, "保存微信定时提醒成功");
		return "redirect:"+Global.getAdminPath()+"/wx/vacJobRemind/?repage";
	}
	
	@RequiresPermissions("wx:vacJobRemind:edit")
	@RequestMapping(value = "delete")
	public String delete(VacJobRemind vacJobRemind, RedirectAttributes redirectAttributes) {
		vacJobRemindService.delete(vacJobRemind);
		addMessage(redirectAttributes, "删除微信定时提醒成功");
		return "redirect:"+Global.getAdminPath()+"/wx/vacJobRemind/?repage";
	}

}