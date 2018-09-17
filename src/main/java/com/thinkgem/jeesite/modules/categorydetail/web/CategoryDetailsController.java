/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.categorydetail.web;

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
import com.thinkgem.jeesite.modules.categorydetail.entity.CategoryDetailsEntity;
import com.thinkgem.jeesite.modules.categorydetail.service.CategoryDetailsService;

/**
 * 核查项详细信息Controller
 * @author wcy
 * @version 2016-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/categorydetail/examineItemCategory")
public class CategoryDetailsController extends BaseController {

	@Autowired
	private CategoryDetailsService examineItemCategoryService;
	
	@ModelAttribute
	public CategoryDetailsEntity get(@RequestParam(required=false) String id) {
		CategoryDetailsEntity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examineItemCategoryService.get(id);
		}
		if (entity == null){
			entity = new CategoryDetailsEntity();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CategoryDetailsEntity categoryDetailsEntity, HttpServletRequest request, HttpServletResponse response, Model model) {
		String catid = request.getParameter("catid");
		if (null==catid){
			catid = categoryDetailsEntity.getCategoryId();
		}else {
			categoryDetailsEntity.setCategoryId(catid);
		}
		Page<CategoryDetailsEntity> page = examineItemCategoryService.findPage(new Page<CategoryDetailsEntity>(request, response), categoryDetailsEntity);
		model.addAttribute("page", page);
		model.addAttribute("categoryid", catid);
		return "modules/categorydetail/examineItemCategoryList";
	}

	@RequestMapping(value = "form")
	public String form(CategoryDetailsEntity categoryDetailsEntity, Model model) {
		model.addAttribute("categoryDetailsEntity", categoryDetailsEntity);
		return "modules/categorydetail/examineItemCategoryForm";
	}

	@RequestMapping(value = "save")
	public String save(CategoryDetailsEntity categoryDetailsEntity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, categoryDetailsEntity)){
			return form(categoryDetailsEntity, model);
		}
		examineItemCategoryService.save(categoryDetailsEntity);
		addMessage(redirectAttributes, "保存核查详情成功");
		return "redirect:"+Global.getAdminPath()+"/categorydetail/examineItemCategory/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(CategoryDetailsEntity categoryDetailsEntity, RedirectAttributes redirectAttributes) {
		examineItemCategoryService.delete(categoryDetailsEntity);
		addMessage(redirectAttributes, "删除核查详情成功");
		return "redirect:"+Global.getAdminPath()+"/categorydetail/examineItemCategory/?repage";
	}

}