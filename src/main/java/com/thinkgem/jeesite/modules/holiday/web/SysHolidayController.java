/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.holiday.web;

import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.holiday.entity.SysHoliday;
import com.thinkgem.jeesite.modules.holiday.service.SysHolidayService;

/**
 * 节假日Controller
 * @author fuxin
 * @version 2017-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/holiday/sysHoliday")
public class SysHolidayController extends BaseController {

	@Autowired
	private SysHolidayService sysHolidayService;
	
	@ModelAttribute
	public SysHoliday get(@RequestParam(required=false) String id) {
		SysHoliday entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysHolidayService.get(id);
		}
		if (entity == null){
			entity = new SysHoliday();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SysHoliday sysHoliday, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysHoliday> page = sysHolidayService.findPage(new Page<SysHoliday>(request, response), sysHoliday); 
		model.addAttribute("page", page);
		return "modules/holiday/sysHolidayList";
	}

	@RequestMapping(value = "form")
	public String form(SysHoliday sysHoliday, Model model) {
		model.addAttribute("sysHoliday", sysHoliday);
		return "modules/holiday/sysHolidayForm";
	}

	@RequestMapping(value = "save")
	public String save(SysHoliday sysHoliday, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysHoliday)){
			return form(sysHoliday, model);
		}
		sysHolidayService.save(sysHoliday);
		addMessage(redirectAttributes, "保存节假日成功");
		return "redirect:"+Global.getAdminPath()+"/holiday/sysHoliday/?repage";
	}
	
	@RequestMapping(value = "saveApi")
	public @ResponseBody Map<String, Object> saveApi(SysHoliday sysHoliday, Model model, RedirectAttributes redirectAttributes) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", "500");
		String validateResult = beanValidatorApi(sysHoliday);
		if(StringUtils.isNotBlank(validateResult)){
			returnMap.put("msg",validateResult);
			return returnMap;
		}
		sysHolidayService.save(sysHoliday);
		returnMap.put("code", "200");
		returnMap.put("msg", "保存成功");
		return returnMap;
	}
	
//	@RequiresPermissions("holiday:sysHoliday:edit")
	@RequestMapping(value = "delete")
	public String delete(SysHoliday sysHoliday, RedirectAttributes redirectAttributes) {
		sysHolidayService.delete(sysHoliday);
		addMessage(redirectAttributes, "删除节假日成功");
		return "redirect:"+Global.getAdminPath()+"/holiday/sysHoliday/?repage";
	}

}