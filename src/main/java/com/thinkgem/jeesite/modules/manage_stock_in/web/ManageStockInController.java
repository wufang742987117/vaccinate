/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manage_stock_in.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.JSONMessage;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.enter.entity.SysEnterpriseInfo;
import com.thinkgem.jeesite.modules.enter.service.SysEnterpriseInfoService;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.ManageStockIn;
import com.thinkgem.jeesite.modules.manage_stock_in.service.ManageStockInService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 疫苗入库记录Controller
 * 
 * @author 王德地
 * @version 2017-02-08
 */
@Controller
@RequestMapping(value = "${adminPath}/manage_stock_in/manageStockIn")
public class ManageStockInController extends BaseController {

	@Autowired
	private ManageStockInService manageStockInService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private BsManageProductService bsManageProductService;
	@Autowired
	private SysEnterpriseInfoService sysEnterpriseInfoService;

	@ModelAttribute
	public ManageStockIn get(@RequestParam(required = false) String id) {
		ManageStockIn entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = manageStockInService.get(id);
		}
		if (entity == null) {
			entity = new ManageStockIn();
		}
		return entity;
	}
/**
 * 
 * @author wangdedi
 * @date 2017年3月27日 下午1:57:29
 * @description 
 *		//进入出入库页面
 * @param manageStockIn
 * @param request
 * @param response
 * @param model
 * @return
 *
 */
	@RequiresPermissions("managestockin:IN:view")
	@RequestMapping(value = { "list", "" })
	public String list(ManageStockIn manageStockIn, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ManageStockIn> page = manageStockInService.findPage(new Page<ManageStockIn>(request,response), manageStockIn);
		model.addAttribute("page", page);
		List<BsManageVaccine> vaccinates = bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("products", vaccinates);
		return "modules/manage_stock_in/manageStockInList";
	}
	
	@RequiresPermissions("managestockin:IN:view")
	@RequestMapping(value = { "form"})
	public String form(ManageStockIn manageStockIn, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<BsManageProduct> VaccineList = bsManageProductService.type();
		model.addAttribute("VaccineList", VaccineList);
		Date date = new Date();
		manageStockIn.setIndate(date);
		return "modules/manage_stock_in/manageStockInForm";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:31:37
	 * @description 疫苗出/入库判断，以及计算库存
	 * @param manageStockIn
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * 
	 */
	@RequiresPermissions("managestockin:IN:edit")
	@RequestMapping(value = "save")
	public String save(ManageStockIn manageStockIn, Model model,
			RedirectAttributes redirectAttributes) {
		// 判断输入的数量是否为空
		if (manageStockIn.getNum() == null) {
			model.addAttribute("arg", "1");
			return "modules/manage_stock_in/manageStockInForm";
		}
		return "modules/manage_stock_in/manageStockInForm";
	}
	
	@RequestMapping("/addStockIn")
	public String addStockIn(ManageStockIn manageStockIn, HttpServletRequest request, HttpServletResponse response, Model model){
		//查询所有疫苗信息
		List<BsManageVaccine> vaccinates = bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("offices", JsonMapper.toJsonString(UserUtils.getOfficeList()));
		
		model.addAttribute("vaccinates", JsonMapper.toJsonString(vaccinates));
		List<SysEnterpriseInfo> companys = sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
		model.addAttribute("companys", JsonMapper.toJsonString(companys));
		model.addAttribute("type", ManageStockIn.TYPE_IN);
		return "modules/manage_stock_in/manageStockIn";
	}
	
	@RequestMapping("/addStockOut")
	public String addStockOut(ManageStockIn manageStockIn,HttpServletRequest request, HttpServletResponse response, Model model){
		//查询所有有库存的产品信息
		List<BsManageProduct> products = bsManageProductService.findVaccinateAble(new BsManageProduct());
		model.addAttribute("products", JsonMapper.toJsonString(products));
		List<SysEnterpriseInfo> companys = sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
		model.addAttribute("companys", JsonMapper.toJsonString(companys));
		model.addAttribute("type",ManageStockIn.TYPE_OUT);
		return "modules/manage_stock_in/manageStockOut";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveStockIn")
	@Transactional(readOnly=false)
	public @ResponseBody Map<String, Object> saveStockIn(@RequestParam(value="list", required=false, defaultValue="") String json){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Date now = new Date();
		try {
			json = StringEscapeUtils.unescapeHtml4(json);
			List<Map<String, Object>> data = (List<Map<String, Object>>) JsonMapper.fromJsonString(json, List.class);
				for(Map<String, Object> m : data){
					ManageStockIn in = (ManageStockIn) JsonMapper.fromJsonString(JsonMapper.toJsonString(m), ManageStockIn.class);
					//入库
					if(ManageStockIn.TYPE_IN.equals(in.getType())){
						//补全产品信息数据
						List<BsManageVaccine> vaccs = bsManageVaccineService.findList(new BsManageVaccine());
						List<SysEnterpriseInfo> coms = sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
						in = manageStockInService.updateProduct(in, vaccs, coms);
						bsManageProductService.save(in.getProduct());
						manageStockInService.save(in);
					}
					//出库
					if(ManageStockIn.TYPE_OUT.equals(in.getType())){
						//减库存
						bsManageProductService.minusStock(in.getProduct().getId(), in.getNum());
						in.setIndate(now);
						manageStockInService.save(in);
					}
				}
				returnMap.put("code", "200");
				returnMap.put("message", "保存成功");
		} catch (Exception e) {
			logger.error("入库保存失败",e);
			returnMap.put("code", "500");
			returnMap.put("message", "保存失败");
		}
		return returnMap;
	}
	
	/**
	 * 出库
	 * @author liyuan
	 * @date 2018年2月28日 下午2:44:59
	 * @description 
	 *		TODO
	 * @param manageStockIn
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/stockOutForm")
	public String stockOutForm(ManageStockIn manageStockIn,HttpServletRequest request, HttpServletResponse response, Model model){
		if(manageStockIn.getProduct() == null || StringUtils.isBlank(manageStockIn.getProduct().getId())){
			model.addAttribute("error", "疫苗信息异常");
			return "modules/manage_stock_in/stockOutForm";
		}
		BsManageProduct product = bsManageProductService.get(manageStockIn.getProduct());
		if(product == null){
			model.addAttribute("error", "疫苗信息异常");
			return "modules/manage_stock_in/stockOutForm";
		}
		manageStockIn.setProduct(product);
		manageStockIn.setType(ManageStockIn.TYPE_OUT);
		manageStockIn.setRemarks("报损");
		manageStockIn.setState(ManageStockIn.STATE_BREAK);
		
		//获取所有不是一级的科室信息
		 List<Office> officelist = UserUtils.getOfficeList();
		 for(int i = 0; i < officelist.size(); i ++){
			 if(!"60".equals(officelist.get(i).getGrade())){
				 officelist.remove(i--);
				 continue;
			 }
			 if(officelist.get(i).getCode().equals("djt")){
				 officelist.remove(i--);
				 continue;
			 }
			 if(officelist.get(i).getId().equals(UserUtils.getUser().getOffice().getId())){
				 officelist.remove(i--);
				 continue;
			 }
		 }
		model.addAttribute("officelist",officelist);
		return "modules/manage_stock_in/stockOutForm";
	}
	
	/**
	 * 保存出库记录
	 * @author fuxin
	 * @date 2018年1月13日 上午12:16:14
	 * @description 
	 *		TODO
	 * @param manageStockIn
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/saveStockOut")
	@ResponseBody
	public Map<String,String > saveStockOut(ManageStockIn manageStockIn,HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String,String > returnMap = new HashMap<String, String>();
		returnMap.put("code", "500");
		String error = beanValidatorApi(manageStockIn);
		if(StringUtils.isNotBlank(error)){
			returnMap.put("msg", error);
			logger.error("保存出库记录失败" + error);
			return returnMap;
		}
		//验证疫苗出库数量是否合法
		BsManageProduct bs = bsManageProductService.get(manageStockIn.getProduct().getId());
		if(bs==null) {
			String message = "疫苗信息不存在!";
			returnMap.put("msg", message);
			logger.error("保存出库记录失败," + message);
			return returnMap;
		}else{
			Long storenum = bs.getStorenum();
			Long num = manageStockIn.getNum();
			if(num.longValue()>storenum.longValue()) {
				String message = "疫苗出库数量大于库存!";
				returnMap.put("msg", message);
				logger.error("保存出库记录失败," + message);
				return returnMap;
			}
		}
		returnMap = manageStockInService.saveStockOut(manageStockIn);
		return returnMap;
	}
}
