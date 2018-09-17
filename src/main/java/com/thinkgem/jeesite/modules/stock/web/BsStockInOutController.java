/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stock.web;

import java.util.ArrayList;
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
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stock.entity.BsStockInOut;
import com.thinkgem.jeesite.modules.stock.service.BsStockInOutService;

/**
 * 进销存月报表Controller
 * @author fuxin
 * @version 2017-12-17
 */
@Controller
@RequestMapping(value = "${adminPath}/stock/bsStockInOut")
public class BsStockInOutController extends BaseController {

	@Autowired
	private BsStockInOutService bsStockInOutService;
	
	@ModelAttribute
	public BsStockInOut get(@RequestParam(required=false) String id) {
		BsStockInOut entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsStockInOutService.get(id);
		}
		if (entity == null){
			entity = new BsStockInOut();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(BsStockInOut bsStockInOut, HttpServletRequest request, HttpServletResponse response, Model model) {

		if(bsStockInOut.getYear() == null || bsStockInOut.getYear() == 0){
			bsStockInOut.setYear(Long.parseLong(DateUtils.getYear()));
		}
		if(bsStockInOut.getMouth() == null || bsStockInOut.getMouth() == 0){
			bsStockInOut.setMouth(Long.parseLong(DateUtils.getMonth()));
		}
		Page<BsStockInOut> page = bsStockInOutService.findPage(new Page<BsStockInOut>(request, response), bsStockInOut); 
		model.addAttribute("page", page);
		return "modules/stock/bsStockInOutList";
	}
	
	@RequestMapping("/findListApi")
	public @ResponseBody List<BsStockInOut> findListApi(BsStockInOut bsStockInOut) {
		List<BsStockInOut> list = new ArrayList<BsStockInOut>();
		if(bsStockInOut.getYear() == null || bsStockInOut.getYear() == 0 || bsStockInOut.getMouth() == null || bsStockInOut.getMouth() == 0){
			return list;
		}
		list = bsStockInOutService.findList(bsStockInOut); 
		return list;
	}

	@RequestMapping(value = "form")
	public String form(BsStockInOut bsStockInOut, Model model) {
		model.addAttribute("bsStockInOut", bsStockInOut);
		return "modules/stock/bsStockInOutForm";
	}

	@RequestMapping(value = "save")
	public String save(BsStockInOut bsStockInOut, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsStockInOut)){
			return form(bsStockInOut, model);
		}
		bsStockInOutService.save(bsStockInOut);
		addMessage(redirectAttributes, "保存进销存月报表成功");
		return "redirect:"+Global.getAdminPath()+"/stock/bsStockInOut/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(BsStockInOut bsStockInOut, RedirectAttributes redirectAttributes) {
		bsStockInOutService.delete(bsStockInOut);
		addMessage(redirectAttributes, "删除进销存月报表成功");
		return "redirect:"+Global.getAdminPath()+"/stock/bsStockInOut/?repage";
	}
	
	@RequestMapping("/editTable")
	public String editTable(BsStockInOut bsStockInOut, Model model){
		return "modules/stock/bsStockInOutTable";
	}

}