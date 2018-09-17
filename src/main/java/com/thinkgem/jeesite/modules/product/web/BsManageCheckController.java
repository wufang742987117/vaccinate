/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.ManageStockIn;
import com.thinkgem.jeesite.modules.manage_stock_in.service.ManageStockInService;
import com.thinkgem.jeesite.modules.product.entity.BsManageCheck;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageCheckService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 每日盘库Controller
 * @author fuxin
 * @version 2017-12-28
 */
@Controller
@RequestMapping(value = "${adminPath}/product/bsManageCheck")
public class BsManageCheckController extends BaseController {

	@Autowired
	private BsManageCheckService bsManageCheckService;
	@Autowired
	private ManageStockInService manageStockInService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	
	@ModelAttribute
	public BsManageCheck get(@RequestParam(required=false) String id) {
		BsManageCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsManageCheckService.get(id);
		}
		if (entity == null){
			entity = new BsManageCheck();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(BsManageCheck bsManageCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<BsManageVaccine> vaccinates = bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("vaccinates", vaccinates);
		if(bsManageCheck.getProduct() == null){
			bsManageCheck.setProduct(new BsManageProduct());
		}
		if(bsManageCheck.getCreateBy() == null || bsManageCheck.getCreateBy().getId() == null){
			bsManageCheck.setCreateBy(UserUtils.getUser());
		}
		Page<BsManageCheck> page = bsManageCheckService.findPage(new Page<BsManageCheck>(request, response), bsManageCheck); 
		model.addAttribute("page", page);
		model.addAttribute("doctorlist", UserUtils.getCompanyUsers());
		return "modules/product/bsManageCheckList";
	}

	@RequestMapping(value = "form")
	public String form(BsManageCheck bsManageCheck, Model model) {
		model.addAttribute("bsManageCheck", bsManageCheck);
		return "modules/product/bsManageCheckForm";
	}

	@RequestMapping(value = "save")
	public String save(BsManageCheck bsManageCheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsManageCheck)){
			return form(bsManageCheck, model);
		}
		bsManageCheckService.save(bsManageCheck);
		addMessage(redirectAttributes, "保存每日盘库成功");
		return "redirect:"+Global.getAdminPath()+"/product/bsManageCheck/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(BsManageCheck bsManageCheck, RedirectAttributes redirectAttributes) {
		bsManageCheckService.delete(bsManageCheck);
		addMessage(redirectAttributes, "删除每日盘库成功");
		return "redirect:"+Global.getAdminPath()+"/product/bsManageCheck/?repage";
	}
	
	/**
	 * 每日盘点
	 * @author fuxin
	 * @date 2018年1月1日 上午2:46:39
	 * @description 
	 *		TODO
	 * @param bsManageCheck
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/tables")
	public String tables(BsManageCheck bsManageCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		bsManageCheck.setShowNull(true);
		List<BsManageCheck> checkTables = bsManageCheckService.genCheckTable(bsManageCheck);
		for(int i = 0;i < checkTables.size(); i ++){
			if(checkTables.get(i).getRestNum() == 0 
					&& checkTables.get(i).getInNum() == 0 
					&& checkTables.get(i).getOutNum() == 0
					&& checkTables.get(i).getUseNum() == 0){
				checkTables.remove(i--);
			}
		}
		model.addAttribute("tables", checkTables);
		return "modules/product/bsManageCheckTable";
	}
	
	
	/**
	 * 保存盘点数据
	 * @author fuxin
	 * @date 2018年1月3日 下午9:06:53
	 * @description 
	 *		TODO
	 * @param json
	 * @return
	 *
	 */
	@RequestMapping(value = "saveTables", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveTables(@RequestParam(value="tables", required=false, defaultValue="") String json){
		json = StringEscapeUtils.unescapeHtml4(json);
		logger.info("------saveTables:开始保存盘点记录----------  json=" + JsonMapper.toJsonString(json));
		if(StringUtils.isNotBlank(json));
		Map<String, Object> returnMap = bsManageCheckService.saveTables(json);
		logger.info("------saveTables:开始保存盘点记录----------  returnMap=" + JsonMapper.toJsonString(returnMap));
		return returnMap;
	}
	
	/**
	 * 使用记录明细
	 * @author fuxin
	 * @date 2018年1月3日 下午9:06:41
	 * @description 
	 *		TODO
	 * @param type
	 * @param pid
	 * @param startTime
	 * @param endTime
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "detailList")
	public String dateilList(String type, String pid, @RequestParam(required=false)Date startTime, @RequestParam(required=false) Date endTime, 
			Model model, HttpServletRequest request, HttpServletResponse response){
		//第一次盘点，默认显示昨天的记录
		if(startTime == null){
			if(endTime != null){
				startTime = DateUtils.addDays(endTime, -1);
			}else{
				startTime = DateUtils.addDays(new Date(), -1);
			}
		}
		//盘点页面查看，则以当前时间为结束，盘点记录查看则是有开始和结束时间
		if(endTime == null){
			endTime = new Date();
		}
		
		//返回数组
//		List<?> detailList = new ArrayList<>();
		if("IN".equals(type.toUpperCase())){
			//入库明细
			Page<ManageStockIn> page = new Page<ManageStockIn>(request,response);
			ManageStockIn stin = new ManageStockIn();
			stin.setBeginTime(startTime);
			stin.setEndTime(endTime);
			stin.setProduct(new BsManageProduct(pid));
			stin.setPage(page);
			stin.setType(ManageStockIn.TYPE_IN);
			page.setList(manageStockInService.findList(stin));
			model.addAttribute("page", page);
		}else if("OUT".equals(type.toUpperCase())){
			//出库明细
			Page<ManageStockIn> page = new Page<ManageStockIn>(request,response);
			ManageStockIn stin = new ManageStockIn();
			stin.setBeginTime(startTime);
			stin.setEndTime(endTime);
			stin.setProduct(new BsManageProduct(pid));
			stin.setPage(page);
			stin.setType(ManageStockIn.TYPE_OUT);
			stin.setState(ManageStockIn.STATE_OTHER);
			page.setList(manageStockInService.findList(stin));
			model.addAttribute("page", page);
		}else if("EXCHANGE".equals(type.toUpperCase())){
			//调剂出库
			Page<ManageStockIn> page = new Page<ManageStockIn>(request,response);
			ManageStockIn stin = new ManageStockIn();
			stin.setBeginTime(startTime);
			stin.setEndTime(endTime);
			stin.setProduct(new BsManageProduct(pid));
			stin.setPage(page);
			stin.setType(ManageStockIn.TYPE_OUT);
			stin.setState(ManageStockIn.STATE_EXCHANGE); 
			page.setList(manageStockInService.findList(stin));
			model.addAttribute("page", page);
		}else if("SCRAP".equals(type.toUpperCase())){
			//出库明细
			Page<ManageStockIn> page = new Page<ManageStockIn>(request,response);
			ManageStockIn stin = new ManageStockIn();
			stin.setBeginTime(startTime);
			stin.setEndTime(endTime);
			stin.setProduct(new BsManageProduct(pid));
			stin.setPage(page);
			stin.setType(ManageStockIn.TYPE_OUT);
			stin.setState(ManageStockIn.STATE_BREAK);
			page.setList(manageStockInService.findList(stin));
			model.addAttribute("page", page);
		}else if("USE".equals(type.toUpperCase())){
			//使用明细
			Page<ChildVaccinaterecord> page = new Page<ChildVaccinaterecord>(request,response);
			ChildVaccinaterecord rec = new ChildVaccinaterecord();
			rec.setVaccinatedateBefore(endTime);
			rec.setVaccinatedateAfter(startTime);
			rec.setProductid(pid);
			rec.setStatus(ChildVaccinaterecord.STATUS_YET);
			rec.setPage(page);
//			detailList = childVaccinaterecordService.findList(rec);
			page.setList(childVaccinaterecordService.findList(rec));
			model.addAttribute("page", page);
		}
		model.addAttribute("type", type);
//		model.addAttribute("detailList", detailList);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("pid", pid);
		return "modules/product/bsManageCheckDetailList";
	}

}