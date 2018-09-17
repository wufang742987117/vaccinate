/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shequ.web;

import java.util.List;

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
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.shequ.entity.SysCommunity;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;

/**
 * 社区Controller
 * @author wang
 * @version 2017-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/shequ/sysCommunity")
public class SysCommunityController extends BaseController {

	@Autowired
	private SysCommunityService sysCommunityService;
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	
	@ModelAttribute
	public SysCommunity get(@RequestParam(required=false) String id) {
		SysCommunity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysCommunityService.get(id);
		}
		if (entity == null){
			entity = new SysCommunity();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(SysCommunity sysCommunity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysCommunity> page = sysCommunityService.findPage(new Page<SysCommunity>(request, response), sysCommunity); 
		model.addAttribute("page", page);
		//加载所有社区
		List<SysCommunity> coms = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("coms", JsonMapper.toJsonString(coms));
		return "modules/shequ/sysCommunityList";
	}

	
	@RequestMapping(value = "form")
	public String form(SysCommunity sysCommunity, Model model) {
		model.addAttribute("sysCommunity", sysCommunity);
		return "modules/shequ/sysCommunityForm";
	}
	
	@RequestMapping(value = "edit")
	public String edit(SysCommunity sysCommunity, Model model) {
		model.addAttribute("sysCommunity", sysCommunity);
		return "modules/shequ/sysCommunityEdit";
	}

	
	@RequestMapping(value = "save")
	public String save(SysCommunity sysCommunity, Model model, RedirectAttributes redirectAttributes) {
		SysCommunity Community=new SysCommunity();
		//判断是否存在相同的社区编码
		Community.setCode(sysCommunity.getCode());
		if(sysCommunityService.findList(Community).size()>0&&StringUtils.isBlank(sysCommunity.getId())){
			model.addAttribute("arg", "你添加的社区编码已存在,请修改");
			return "modules/shequ/sysCommunityForm";
		}
		//判断是否存在相同的社区名称
		Community=new SysCommunity();
		Community.setName(sysCommunity.getName());
		if(sysCommunityService.findList(Community).size()>0&&StringUtils.isBlank(sysCommunity.getId())){
			model.addAttribute("arg", "你添加的社区名称已存在,请修改");
			return "modules/shequ/sysCommunityForm";
		}
		sysCommunityService.save(sysCommunity);
		model.addAttribute("isok", "ok");
		return "modules/shequ/sysCommunityForm";
	}
	
	
	@RequestMapping(value = "delete")
	public String delete(SysCommunity sysCommunity, RedirectAttributes redirectAttributes) {
		if((sysCommunityService.findList(sysCommunity).size()==1 && sysCommunityService.findList(sysCommunity).get(0).getCount()<=0)){
			sysCommunityService.delete(sysCommunity);
			addMessage(redirectAttributes, "删除信息成功");
		}else{
			addMessage(redirectAttributes, "删除信息失败");
		}
		return "redirect:"+Global.getAdminPath()+"/shequ/sysCommunity/?repage";
	}
	
	@RequestMapping("/savetrs")
	public @ResponseBody String savetrs(SysCommunity sysCommunity,@RequestParam String newarea ) {
		if((sysCommunityService.findList(sysCommunity).size()==1 && sysCommunityService.findList(sysCommunity).get(0).getCount()>0)){
			ChildBaseinfo cbi=new ChildBaseinfo();
			cbi.setArea(sysCommunity.getCode());
			List<ChildBaseinfo> list=childBaseinfoService.findList(cbi);
			for (ChildBaseinfo childBaseinfo : list) {
				childBaseinfo.setArea(newarea);
				childBaseinfoService.save(childBaseinfo);
			}
			return "200";
		}
		return  "转移社区儿童信息失败";
	}

}