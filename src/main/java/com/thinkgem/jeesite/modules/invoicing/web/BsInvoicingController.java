/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.invoicing.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.invoicing.entity.BsInvoicing;
import com.thinkgem.jeesite.modules.invoicing.service.BsInvoicingService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 进销存统计Controller
 * @author qjzhou
 * @version 2018-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/invoicing/bsInvoicing")
public class BsInvoicingController extends BaseController {

	@Autowired
	private BsInvoicingService bsInvoicingService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	
	@ModelAttribute
	public BsInvoicing get(@RequestParam(required=false) String id) {
		BsInvoicing entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsInvoicingService.get(id);
		}
		if (entity == null){
			entity = new BsInvoicing();
		}
		return entity;
	}
	
	/**
	 * 查询进销存统计记录
	 * @author zhouqj
	 * @date 2018年1月11日 下午3:15:23
	 * @description 
	 *		TODO
	 * @param bsInvoicing
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = {"list", ""})
	public String list(BsInvoicing bsInvoicing, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date d[] = bsInvoicingService.dateTime(bsInvoicing.getBeginTime(),bsInvoicing.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsInvoicing.setBeginTime(d[0]);
			bsInvoicing.setEndTime(d[1]);
		} else {
			return "modules/invoicing/bsInvoicingList";
		}
		/*Page<BsInvoicing> page = bsInvoicingService.findPage(new Page<BsInvoicing>(request, response), bsInvoicing); */
		Page<BsInvoicing> page = bsInvoicingService.findPageSql(new Page<BsInvoicing>(request, response), bsInvoicing);
		model.addAttribute("page", page);
		List<BsManageVaccine> vaccinates = bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("products", vaccinates);
		return "modules/invoicing/bsInvoicingList";
	}

	@RequestMapping(value = "form")
	public String form(BsInvoicing bsInvoicing, Model model) {
		model.addAttribute("bsInvoicing", bsInvoicing);
		return "modules/invoicing/bsInvoicingForm";
	}

	@RequestMapping(value = "save")
	public String save(BsInvoicing bsInvoicing, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsInvoicing)){
			return form(bsInvoicing, model);
		}
		bsInvoicingService.save(bsInvoicing);
		addMessage(redirectAttributes, "保存进销存统计成功");
		return "redirect:"+Global.getAdminPath()+"/invoicing/bsInvoicing/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(BsInvoicing bsInvoicing, RedirectAttributes redirectAttributes) {
		bsInvoicingService.delete(bsInvoicing);
		addMessage(redirectAttributes, "删除进销存统计成功");
		return "redirect:"+Global.getAdminPath()+"/invoicing/bsInvoicing/?repage";
	}
	
	/**
	 * 导出进销存统计记录
	 * @author zhouqj
	 * @date 2018年1月11日 下午4:06:26
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(BsInvoicing bsInvoicing, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Date d[] = bsInvoicingService.dateTime(bsInvoicing.getBeginTime(),bsInvoicing.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsInvoicing.setBeginTime(d[0]);
			bsInvoicing.setEndTime(d[1]);
		} else {
			return "modules/invoicing/bsInvoicingList";
		}
		//一次性导出全部
		/*List<BsInvoicing> list = bsInvoicingService.findList(bsInvoicing);*/
		List<BsInvoicing> list = bsInvoicingService.findListSql(bsInvoicing);
		try {
			String fileName = "进销存统计记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx"; 
			new ExportExcel("进销存统计记录", BsInvoicing.class) .setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出进销存统计记录失败！失败信息：" + e.getMessage());
		}
		return "modules/invoicing/bsInvoicingList";
	}

}