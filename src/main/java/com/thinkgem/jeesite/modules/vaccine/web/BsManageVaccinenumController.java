/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.web;

import java.util.List;

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
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;

/**
 * 疫苗剂次管理Controller
 * @author fuxin
 * @version 2017-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/vaccine/bsManageVaccinenum")
public class BsManageVaccinenumController extends BaseController {

	@Autowired
	private BsManageVaccinenumService bsManageVaccinenumService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	
	@ModelAttribute
	public BsManageVaccinenum get(@RequestParam(required=false) String id) {
		BsManageVaccinenum entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsManageVaccinenumService.get(id);
		}
		if (entity == null){
			entity = new BsManageVaccinenum();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(BsManageVaccinenum bsManageVaccinenum, HttpServletRequest request, HttpServletResponse response, Model model) {
		//-1表示不分页
		Page<BsManageVaccinenum> p=new Page<BsManageVaccinenum>(request, response);
		//根据月龄排序
		p.setOrderBy("A.mouage");
		Page<BsManageVaccinenum> page = bsManageVaccinenumService.findPage(p, bsManageVaccinenum); 
		model.addAttribute("page", page);
		List<BsManageVaccine> VaccineList=bsManageVaccineService.findModelList();
		model.addAttribute("VaccineList", VaccineList);
		model.addAttribute("list",JsonMapper.toJsonString(VaccineList));
		return "modules/vaccine/bsManageVaccinenumList";
	}

	
	@RequestMapping(value = "form")
	public String form(BsManageVaccinenum bsManageVaccinenum, Model model) {
		//所有可以配置的疫苗大类
		List<BsManageVaccine> VaccineList=bsManageVaccineService.findModelList();
		//如果没有ID，则默认疫苗为一类，可用,针剂，无关
		if(StringUtils.isBlank(bsManageVaccinenum.getId())){
			bsManageVaccinenum.setStatus("1");
			bsManageVaccinenum.setType(1L);
			bsManageVaccinenum.setIntype("0");
			bsManageVaccinenum.setPentrep(0L);
			bsManageVaccinenum.setWeight("1");
		}
		model.addAttribute("bsManageVaccinenum", bsManageVaccinenum);
		model.addAttribute("VaccineList", VaccineList);
		model.addAttribute("list",JsonMapper.toJsonString(VaccineList));
		return "modules/vaccine/bsManageVaccinenumForm";
	}

	
	@RequestMapping(value = "save")
	public String save(BsManageVaccinenum bsManageVaccinenum, Model model, RedirectAttributes redirectAttributes) {
//		BsManageVaccinenum num=new BsManageVaccinenum();
//		num.setGroup(bsManageVaccinenum.getGroup());
//		num.setPin(bsManageVaccinenum.getPin());
//		List<BsManageVaccinenum> numlist=bsManageVaccinenumService.findList(num);
		if(StringUtils.isBlank(bsManageVaccinenum.getId())){
			BsManageVaccinenum n = bsManageVaccinenumService.get(bsManageVaccinenum.getGroup() + bsManageVaccinenum.getPin() + bsManageVaccinenum.getVaccTypeAfter());
			if(null != n){
				//所有可以配置的疫苗大类
				List<BsManageVaccine> VaccineList=bsManageVaccineService.findModelList();
				model.addAttribute("bsManageVaccinenum", bsManageVaccinenum);
				model.addAttribute("VaccineList", VaccineList);
				model.addAttribute("list",JsonMapper.toJsonString(VaccineList));
				model.addAttribute("hint","该疫苗该针次已存在");
				return "modules/vaccine/bsManageVaccinenumForm";
			/*	return "redirect:"+Global.getAdminPath()+"/vaccine/bsManageVaccinenum/form?repage";*/
			}else{
				bsManageVaccinenum.setId(bsManageVaccinenum.getGroup()+bsManageVaccinenum.getPin());
				bsManageVaccinenumService.insert(bsManageVaccinenum);	
			}
		}else{
			bsManageVaccinenumService.save(bsManageVaccinenum);
		}
		addMessage(redirectAttributes, "保存成功");
		model.addAttribute("arg", "1");
		
		return "modules/vaccine/bsManageVaccinenumForm";
	}
	
	
	@RequestMapping(value = "delete")
	public String delete(BsManageVaccinenum bsManageVaccinenum, RedirectAttributes redirectAttributes) {
		bsManageVaccinenumService.delete(bsManageVaccinenum);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/vaccine/bsManageVaccinenum/?repage";
	}

}