/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.web;

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

import com.junl.common.JsonBuild;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 疫苗信息Controller
 * @author wangdedi
 * @version 2017-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/vaccine/bsManageVaccine")
public class BsManageVaccineController extends BaseController {
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private BsManageProductService productService;
	@ModelAttribute
	public BsManageVaccine get(@RequestParam(required=false) String id) {
		BsManageVaccine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsManageVaccineService.get(id);
		}
		if (entity == null){
			entity = new BsManageVaccine();
		}
		return entity;
	}
	
//	@RequiresPermissions("vaccine:bsManageVaccine:view")
	@RequestMapping(value = {"list", ""})
	public String list(BsManageVaccine bsManageVaccine, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<BsManageVaccine> groups = bsManageVaccineService.findGroupList();
		model.addAttribute("groups", groups);
		Page<BsManageVaccine> page = bsManageVaccineService.findPage(new Page<BsManageVaccine>(request, response), bsManageVaccine); 
		model.addAttribute("page", page);
		return "modules/vaccine/bsManageVaccineList";
	}

//	@RequiresPermissions("vaccine:bsManageVaccine:view")
	@RequestMapping(value = "form")
	public String form(BsManageVaccine bsManageVaccine, Model model) {
		model.addAttribute("bsManageVaccine", bsManageVaccine);
		return "modules/vaccine/bsManageVaccineForm";
	}

//	@RequiresPermissions("vaccine:bsManageVaccine:edit")
	@RequestMapping(value = "save")
	public String save(BsManageVaccine bsManageVaccine, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsManageVaccine)){
			return form(bsManageVaccine, model);
		}
		bsManageVaccineService.save(bsManageVaccine);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/vaccine/bsManageVaccine/?repage";
	}
	
	@RequestMapping(value = "/saveApi")
	public @ResponseBody Map<String, Object> saveApi(BsManageVaccine bsManageVaccine, Model model) {
		if (!beanValidator(model, bsManageVaccine)){
			return JsonBuild.build(false, "参数错误，请重试", "500", null);
		}
		try {
			bsManageVaccineService.saveModel(bsManageVaccine);
		} catch (Exception e) {
			logger.error("添加疫苗名称出错",e);
			return JsonBuild.build(false, "添加疫苗失败，请重试", "500", null);
		}
		return JsonBuild.build(true, "保存成功", "200", null);
	}
	
	@RequiresPermissions("vaccine:bsManageVaccine:edit")
	@RequestMapping(value = "delete")
	public String delete(BsManageVaccine bsManageVaccine, RedirectAttributes redirectAttributes) {
		bsManageVaccineService.delete(bsManageVaccine);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/vaccine/bsManageVaccine/?repage";
	}
	
	@RequestMapping("/findGroupList")
	public @ResponseBody List<BsManageVaccine> findGroupList(){
		return bsManageVaccineService.findGroupList();
	}
	
	@RequestMapping("/findModelList")
	public @ResponseBody List<BsManageVaccine> findModelList(){
		return bsManageVaccineService.findModelList();
	}
	
	/**
	 * 根据大类获取有库存的小类信息
	 * @author fuxin
	 * @date 2017年8月25日 下午5:06:40
	 * @description 
	 *		TODO
	 * @param group
	 * @return
	 *
	 */
	@RequestMapping("/findVaccListAble")
	public @ResponseBody List<BsManageVaccine> findVaccListAble(@RequestParam(required=false, defaultValue="", value="group") String group){
		return bsManageVaccineService.findVaccListAble(group);
	}
	
	
	@RequestMapping("/findVaccListAbleModel")
	public @ResponseBody List<BsManageProduct> findVaccListAbleModel(@RequestParam(required=false, defaultValue="", value="group") String group){
		return productService.findByMnum(group);
	}
	
	
	/**
	 * 接口调用findlist
	 * @author fuxin
	 * @date 2017年8月25日 下午5:10:09
	 * @description 
	 *		TODO
	 * @param bsManageVaccine
	 * @return
	 *
	 */
	@RequestMapping("/findListApi")
	public @ResponseBody List<BsManageVaccine> findListApi(BsManageVaccine bsManageVaccine){
		bsManageVaccine.setVacctype("1");
		return bsManageVaccineService.findList(bsManageVaccine);
		
	}

}