/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.web;

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
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.product.entity.BsCheckRecord;
import com.thinkgem.jeesite.modules.product.service.BsCheckRecordService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 库存盘点记录Controller
 * @author fuxin
 * @version 2017-12-28
 */
@Controller
@RequestMapping(value = "${adminPath}/product/bsCheckRecord")
public class BsCheckRecordController extends BaseController {

	@Autowired
	private BsCheckRecordService bsCheckRecordService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	
	@ModelAttribute
	public BsCheckRecord get(@RequestParam(required=false) String id) {
		BsCheckRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsCheckRecordService.get(id);
		}
		if (entity == null){
			entity = new BsCheckRecord();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(BsCheckRecord bsCheckRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		//获取操作者名字
		String userName = UserUtils.getUser().getName();
		model.addAttribute("userName",userName);
		// 加载所有的疫苗名称
		List<BsManageVaccine> bsManageVaccineList = bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);
		
		Page<BsCheckRecord> page = bsCheckRecordService.findPage(new Page<BsCheckRecord>(request, response), bsCheckRecord); 
		model.addAttribute("page", page);
		return "modules/product/bsCheckRecordList";
	}

	@RequestMapping(value = "form")
	public String form(BsCheckRecord bsCheckRecord, Model model) {
		model.addAttribute("bsCheckRecord", bsCheckRecord);
		return "modules/product/bsCheckRecordForm";
	}

	@RequestMapping(value = "save")
	public String save(BsCheckRecord bsCheckRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsCheckRecord)){
			return form(bsCheckRecord, model);
		}
		bsCheckRecordService.save(bsCheckRecord);
		addMessage(redirectAttributes, "保存库存盘点记录成功");
		return "redirect:"+Global.getAdminPath()+"/product/bsCheckRecord/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(BsCheckRecord bsCheckRecord, RedirectAttributes redirectAttributes) {
		bsCheckRecordService.delete(bsCheckRecord);
		addMessage(redirectAttributes, "删除库存盘点记录成功");
		return "redirect:"+Global.getAdminPath()+"/product/bsCheckRecord/?repage";
	}

}