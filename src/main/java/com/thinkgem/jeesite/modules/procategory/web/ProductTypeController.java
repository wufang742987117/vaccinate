/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.procategory.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.procategory.entity.ProductType;
import com.thinkgem.jeesite.modules.procategory.service.ProductTypeService;

/**
 * 产品类别Controller
 * @author 1
 * @version 2016-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/procategory/productType")
public class ProductTypeController extends BaseController {

	@Autowired
	private ProductTypeService productTypeService;
	
	@ModelAttribute
	public ProductType get(@RequestParam(required=false) String id) {
		ProductType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productTypeService.get(id);
		}
		if (entity == null){
			entity = new ProductType();
		}
		return entity;
	}
	
	@RequiresPermissions("procategory:productType:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductType productType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductType> page = productTypeService.findPage(new Page<ProductType>(request, response), productType); 
		model.addAttribute("page", page);
		return "modules/procategory/productTypeList";
	}

	@RequiresPermissions("procategory:productType:view")
	@RequestMapping(value = "form")
	public String form(ProductType productType, Model model) {
		model.addAttribute("productType", productType);
		return "modules/procategory/productTypeForm";
	}

	@RequiresPermissions("procategory:productType:edit")
	@RequestMapping(value = "save")
	public String save(ProductType productType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productType)){
			return form(productType, model);
		}
		productTypeService.save(productType);
		addMessage(redirectAttributes, "保存产品类别成功");
		return "redirect:"+Global.getAdminPath()+"/procategory/productType/?repage";
	}
	
	@RequiresPermissions("procategory:productType:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductType productType, RedirectAttributes redirectAttributes) {
		productTypeService.delete(productType);
		addMessage(redirectAttributes, "删除产品类别成功");
		return "redirect:"+Global.getAdminPath()+"/procategory/productType/?repage";
	}

	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "protypedata")
	public List<Map<String, Object>> prodata(String module, @RequestParam(required=false) String extId, HttpServletResponse response){
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ProductType> list = productTypeService.findtree();
		for (int i=0; i<list.size(); i++){
			ProductType p =list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", p.getId());
			map.put("pId", p.getPId());
			map.put("name", p.getName());
			mapList.add(map);
		}
		
		return mapList;
		
	}
	
	
	
	
	
}