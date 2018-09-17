/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.junl.common.JsonBuild;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageBinded;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageBindedService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 联合疫苗替代原则Controller
 * @author fuxin
 * @version 2017-09-29
 */
@Controller
@RequestMapping(value = "${adminPath}/vaccine/bsManageBinded")
public class BsManageBindedController extends BaseController {

	@Autowired
	private BsManageBindedService bsManageBindedService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	
	@ModelAttribute
	public BsManageBinded get(@RequestParam(required=false) String id) {
		BsManageBinded entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsManageBindedService.get(id);
		}
		if (entity == null){
			entity = new BsManageBinded();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(BsManageBinded bsManageBinded, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BsManageBinded> page = bsManageBindedService.findPage(new Page<BsManageBinded>(request, response), bsManageBinded); 
		model.addAttribute("page", page);
		List<BsManageVaccine> vaccineList = bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("vaccineList",vaccineList);
		return "modules/vaccine/bsManageBindedList";
	}
	
	/**
	 * @author zhb
	 * @date 2018年1月16日 上午11:33:20
	 * @description 
	 *		跳转到form表单页面，用于展示信息或者修改
	 * @param bsManageBinded
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(BsManageBinded bsManageBinded,String id, Model model) {
		bsManageBinded.setId(id);
		List<BsManageVaccine> vaccineList = bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("vaccineList",vaccineList);
		model.addAttribute("bsManageBinded", bsManageBinded);
		return "modules/vaccine/bsManageBindedForm";
	}
	
	/**
	 * @author zhb
	 * @date 2018年1月16日 上午11:30:10
	 * @description 
	 *		添加 或 修改 联合疫苗替代原则
	 * @param bsManageBinded
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "save")
	public @ResponseBody Map<String, Object> save(BsManageBinded bsManageBinded, Model model) {
		if (!beanValidator(model, bsManageBinded)){
			return JsonBuild.build(false, "参数错误，请重试", "500", null);
		}
		String localCode = OfficeService.getFirstOfficeCode(); 
		bsManageBinded.setLocalCode(localCode);
		bsManageBindedService.save(bsManageBinded);
		return JsonBuild.build(true, "联合疫苗替代原则保存成功", "200", null);
	}
	
	/**
	 * @author zhb
	 * @date 2018年1月16日 上午11:31:46
	 * @description 
	 *		删除联合疫苗替代原则
	 * @param bsManageBinded
	 * @param model
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(BsManageBinded bsManageBinded, Model model) {
		bsManageBindedService.delete(bsManageBinded);
		return "联合疫苗替代原则删除成功";
	}
	
	

}