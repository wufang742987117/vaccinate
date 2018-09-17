/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.junl.common.CommonUtils;
import com.junl.common.JsonBuild;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysWorkingHours;
import com.thinkgem.jeesite.modules.sys.service.SysWorkingHoursService;

/**
 * 工作时间段Controller
 * @author liyuan
 * @version 2018-01-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysWorkingHours")
public class SysWorkingHoursController extends BaseController {

	@Autowired
	private SysWorkingHoursService sysWorkingHoursService;
	
	@ModelAttribute
	public SysWorkingHours get(@RequestParam(required=false) String id) {
		SysWorkingHours entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysWorkingHoursService.get(id);
		}
		if (entity == null){
			entity = new SysWorkingHours();
		}
		return entity;
	}
	
	//@RequiresPermissions("sys:sysWorkingHours:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysWorkingHours sysWorkingHours, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysWorkingHours> page = sysWorkingHoursService.findPage(new Page<SysWorkingHours>(request, response), sysWorkingHours); 
		model.addAttribute("page", page);
		return "modules/sys/sysWorkingHoursList";
	}

	//@RequiresPermissions("sys:sysWorkingHours:view")
	@RequestMapping(value = "form")
	public String form(SysWorkingHours sysWorkingHours, Model model) {
		model.addAttribute("sysWorkingHours", sysWorkingHours);
		return "modules/sys/sysWorkingHoursForm";
	}

	//@RequiresPermissions("sys:sysWorkingHours:edit")
	@RequestMapping(value = "save")
	public @ResponseBody Map<String, Object> save(SysWorkingHours sysWorkingHours, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysWorkingHours)){
			return JsonBuild.build(false, "参数错误，请重试", "500", null);
		}
		sysWorkingHoursService.save(sysWorkingHours);
		//addMessage(redirectAttributes, "保存工作时间段成功");
		return JsonBuild.build(true, "工作时间段保存成功", "200", null);
	}
	
	//@RequiresPermissions("sys:sysWorkingHours:edit")
	@RequestMapping(value = "delete")
	public String delete(SysWorkingHours sysWorkingHours, RedirectAttributes redirectAttributes) {
		sysWorkingHoursService.delete(sysWorkingHours);
		addMessage(redirectAttributes, "删除工作时间段成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysWorkingHours/?repage";
	}
	
	/**
	 * 获取工作日时段
	 * @author fuxin
	 * @date 2018年1月29日 上午10:44:44
	 * @description 
	 *		TODO
	 * @param sysWorkingHours
	 * @return
	 *
	 */
	@RequestMapping("findTreeListApi")
	@ResponseBody
	public Map<String, Object>  findTreeListApi(SysWorkingHours sysWorkingHours){
		//查询时段信息
		sysWorkingHours.setOrderBy(" a.DATE_DAY, a.TIME_SLICE ");
		List<SysWorkingHours> list = sysWorkingHoursService.findList(sysWorkingHours);
		//按星期分组
		Map<Object, List<SysWorkingHours>> workTimeMap = CommonUtils.getTreeDateByParam(SysWorkingHours.class, list, "week");
		//遍历周一至周日，获取信息
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		for(Entry<String, String> entry: DateUtils.week2DayMap.entrySet()){
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("dayName", entry.getKey());
			List<SysWorkingHours> tp = workTimeMap.get(entry.getKey());
			if(tp != null){
				tempMap.put("timeEvent", tp);
			}else{
				tempMap.put("timeEvent", new ArrayList<SysWorkingHours>());
			}
			returnList.add(tempMap);
		}
		return JsonBuild.build(true, "获取成功", "200", returnList);
	}
	
	/**
	 * 获取工作日时段
	 * @author fuxin
	 * @date 2018年1月29日 上午10:44:44
	 * @description 
	 *		TODO
	 * @param sysWorkingHours
	 * @return
	 *
	 */
	@RequestMapping("saveApi")
	@ResponseBody
	public Map<String, Object> saveApi(SysWorkingHours sysWorkingHours){
		sysWorkingHours.updateDayAndWeek();
		String error = beanValidatorApi(sysWorkingHours);
		if (StringUtils.isNotBlank(error)){
			return JsonBuild.build(false, "参数错误，请重试", "500", null);
		}
		sysWorkingHoursService.save(sysWorkingHours);
		return JsonBuild.build(true, "保存成功", "200", sysWorkingHours);
	}
	
	/**
	 * 删除工作日时段
	 * @author fuxin
	 * @date 2018年1月29日 上午10:44:44
	 * @description 
	 *		TODO
	 * @param sysWorkingHours
	 * @return
	 *
	 */
	@RequestMapping("deleteApi")
	@ResponseBody
	public Map<String, Object> deleteApi(SysWorkingHours sysWorkingHours){
		try {
			sysWorkingHoursService.delete(sysWorkingHours);
		} catch (Exception e) {
			return JsonBuild.build(false, "删除失败，请重试", "500", null);
		}
		return JsonBuild.build(true, "删除成功", "200", sysWorkingHours.getId());
	}

}