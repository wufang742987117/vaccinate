/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.refund.web;

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
import com.thinkgem.jeesite.modules.refund.entity.SysRefund;
import com.thinkgem.jeesite.modules.refund.service.SysRefundService;

/**
 * 退款说明Controller
 * @author wangdedi
 * @version 2017-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/refund/sysRefund")
public class SysRefundController extends BaseController {

	@Autowired
	private SysRefundService sysRefundService;
	
	@ModelAttribute
	public SysRefund get(@RequestParam(required=false) String id) {
		SysRefund entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysRefundService.get(id);
		}
		if (entity == null){
			entity = new SysRefund();
		}
		return entity;
	}
	
	@RequiresPermissions("refund:sysRefund:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysRefund sysRefund, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysRefund> page = sysRefundService.findPage(new Page<SysRefund>(request, response), sysRefund); 
		model.addAttribute("page", page);
		return "modules/refund/sysRefundList";
	}

	@RequiresPermissions("refund:sysRefund:view")
	@RequestMapping(value = "form")
	public String form(SysRefund sysRefund, Model model) {
		model.addAttribute("sysRefund", sysRefund);
		return "modules/refund/sysRefundForm";
	}

	@RequiresPermissions("refund:sysRefund:edit")
	@RequestMapping(value = "save")
	public String save(SysRefund sysRefund, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysRefund)){
			return form(sysRefund, model);
		}
		sysRefundService.save(sysRefund);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/refund/sysRefund/?repage";
	}
	
	@RequiresPermissions("refund:sysRefund:edit")
	@RequestMapping(value = "delete")
	public String delete(SysRefund sysRefund, RedirectAttributes redirectAttributes) {
		sysRefundService.delete(sysRefund);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/refund/sysRefund/?repage";
	}

}