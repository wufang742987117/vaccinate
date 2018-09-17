/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;

import java.util.ArrayList;
import java.util.List;

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
import com.thinkgem.jeesite.modules.cms.entity.CmsDisclosure;
import com.thinkgem.jeesite.modules.cms.service.CmsDisclosureService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 告知书Controller
 * @author yangjian
 * @version 2018-01-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsDisclosure")
public class CmsDisclosureController extends BaseController {

	@Autowired
	private CmsDisclosureService cmsDisclosureService;
	
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	
	@ModelAttribute
	public CmsDisclosure get(@RequestParam(required=false) String id) {
		CmsDisclosure entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsDisclosureService.get(id);
		}
		if (entity == null){
			entity = new CmsDisclosure();
		}
		return entity;
	}
	
	/*@RequiresPermissions("cms:cmsDisclosure:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(CmsDisclosure cmsDisclosure, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsDisclosure> pageSize = new Page<CmsDisclosure>(request, response);
//		pageSize.setPageSize(5);
		Page<CmsDisclosure> page = cmsDisclosureService.findPage(pageSize, cmsDisclosure);
		model.addAttribute("page", page);
		return "modules/cms/cmsDisclosureList";
	}

	/*@RequiresPermissions("cms:cmsDisclosure:view")*/
	@RequestMapping(value = "form")
	public String form(CmsDisclosure cmsDisclosure, Model model) {
		List<BsManageVaccine> vaccines = bsManageVaccineService.findGroupList();
		List<BsManageVaccine> list = new ArrayList<BsManageVaccine>();
		for(BsManageVaccine bsManageVaccine : vaccines){
			if(!list.contains(bsManageVaccine)){
				list.add(bsManageVaccine);
			}
		}
		model.addAttribute("cmsDisclosure", cmsDisclosure);
		model.addAttribute("groups",list);
		
		return "modules/cms/cmsDisclosureForm";
	}

	/*@RequiresPermissions("cms:cmsDisclosure:edit")*/
	@RequestMapping(value = "save")
	public String save(CmsDisclosure cmsDisclosure, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsDisclosure)){
			return form(cmsDisclosure, model);
		}
		
		cmsDisclosure.setContext(cmsDisclosure.getContext().replace("$", ""));
		cmsDisclosureService.save(cmsDisclosure);
		addMessage(redirectAttributes, "保存告知书成功");
		model.addAttribute("isok", "ok");
		return "modules/cms/cmsDisclosureForm";
	}
	
	/*@RequiresPermissions("cms:cmsDisclosure:edit")*/
	@RequestMapping(value = "delete")
	public String delete(CmsDisclosure cmsDisclosure, RedirectAttributes redirectAttributes) {
		cmsDisclosureService.delete(cmsDisclosure);
		addMessage(redirectAttributes, "删除告知书成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsDisclosure/?repage";
	}

}