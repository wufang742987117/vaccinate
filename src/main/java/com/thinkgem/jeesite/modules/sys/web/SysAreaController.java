/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;

/**
 * 地区管理Controller
 * @author 王德地
 * @version 2017-02-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysArea")
public class SysAreaController extends BaseController {
	
	@Autowired
	private SysAreaService sysAreaService;
	
	@ModelAttribute
	public SysArea get(@RequestParam(required=false) String id) {
		SysArea entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysAreaService.get(id);
		}
		if (entity == null){
			entity = new SysArea();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysArea:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysArea sysArea, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysArea> page = sysAreaService.findPage(new Page<SysArea>(request, response), sysArea); 
		model.addAttribute("page", page);
		return "modules/sys/sysAreaList";
	}

	@RequiresPermissions("sys:sysArea:view")
	@RequestMapping(value = "form")
	public String form(SysArea sysArea, Model model) {
		model.addAttribute("sysArea", sysArea);
		return "modules/sys/sysAreaForm";
	}

	@RequiresPermissions("sys:sysArea:edit")
	@RequestMapping(value = "save")
	public String save(SysArea sysArea, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysArea)){
			return form(sysArea, model);
		}
		sysAreaService.save(sysArea);
		addMessage(redirectAttributes, "保存地区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysArea/?repage";
	}
	

	@RequestMapping("/getbypid/{id}")
	public @ResponseBody Object getByPid(@PathVariable int id) throws UnsupportedEncodingException{
		System.out.println(id);
		System.out.println(sysAreaService.getByPid(id));
		return sysAreaService.getByPid(id);
	}	
	
	@RequiresPermissions("sys:sysArea:edit")
	@RequestMapping(value = "delete")
	public String delete(SysArea sysArea, RedirectAttributes redirectAttributes) {
		sysAreaService.delete(sysArea);
		addMessage(redirectAttributes, "删除地区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysArea/?repage";
	}
	
	@RequestMapping("/getByIds")
	public @ResponseBody List<SysArea> getByIds(@RequestParam(required = false) List<String> data){
		List<SysArea> list = new ArrayList<SysArea>(data.size());
		if(null != data){
			for(String str : data){
				list.add(sysAreaService.get(str));
			}
		}
		return list;
	}
	
	@RequestMapping("/getByCounty")
	public @ResponseBody String getByCounty(@RequestParam(required = false) String data){
		String area = "";
		if(StringUtils.isNotBlank(data) && data.length() == 6){
			area += (sysAreaService.get(data.substring(0,2) + "0000")).getName() + " ";
			area += (sysAreaService.get(data.substring(0,4) + "00")).getName() + " ";
			area += (sysAreaService.get(data)).getName() + " ";
		}
		return area;
	}	

}