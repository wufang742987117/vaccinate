/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.kindergarten.web;

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
import com.thinkgem.jeesite.modules.kindergarten.entity.BsKindergarten;
import com.thinkgem.jeesite.modules.kindergarten.service.BsKindergartenService;

/**
 * 儿童信息中的幼儿园Controller
 * @author sen
 * @version 2017-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/kindergarten/bsKindergarten")
public class BsKindergartenController extends BaseController {

	@Autowired
	private BsKindergartenService bsKindergartenService;
	
	@ModelAttribute
	public BsKindergarten get(@RequestParam(required=false) String id) {
		BsKindergarten entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsKindergartenService.get(id);
		}
		if (entity == null){
			entity = new BsKindergarten();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(BsKindergarten bsKindergarten, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BsKindergarten> page = bsKindergartenService.findPage(new Page<BsKindergarten>(request, response), bsKindergarten); 
		model.addAttribute("page", page);
		return "modules/kindergarten/bsKindergartenList";
	}

	@RequestMapping(value = "form")
	public String form(BsKindergarten bsKindergarten, Model model) {
		model.addAttribute("bsKindergarten", bsKindergarten);
		return "modules/kindergarten/bsKindergartenForm";
	}

	@RequestMapping(value = "save")
	public String save(BsKindergarten bsKindergarten, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsKindergarten)){
			return form(bsKindergarten, model);
		}
		BsKindergarten BsKindergarten=new BsKindergarten();
		//判断是否存在相同的幼儿园编码
		BsKindergarten.setKindergartenCode(bsKindergarten.getKindergartenCode());
		if(bsKindergartenService.findList(BsKindergarten).size()>0&&StringUtils.isBlank(bsKindergarten.getId())){
			model.addAttribute("arg", "你添加的幼儿园编码已存在,请修改");
			return "modules/kindergarten/bsKindergartenForm";
		}
		//判断是否存在相同的医幼儿园名称
		BsKindergarten=new BsKindergarten();
		BsKindergarten.setName(bsKindergarten.getName());
		if(bsKindergartenService.findList(BsKindergarten).size()>0&&StringUtils.isBlank(bsKindergarten.getId())){
			model.addAttribute("arg", "你添加的幼儿园名称已存在，请修改");
			return "modules/kindergarten/bsKindergartenForm";
		}
		bsKindergartenService.save(bsKindergarten);
		model.addAttribute("isok", "ok");
		return "modules/kindergarten/bsKindergartenForm";
	}
	
	@RequestMapping(value = "delete")
	public String delete(BsKindergarten bsKindergarten, RedirectAttributes redirectAttributes) {
		bsKindergartenService.delete(bsKindergarten);
		addMessage(redirectAttributes, "删除幼儿园信息成功");
		return "redirect:"+Global.getAdminPath()+"/kindergarten/bsKindergarten/?repage";
	}

}