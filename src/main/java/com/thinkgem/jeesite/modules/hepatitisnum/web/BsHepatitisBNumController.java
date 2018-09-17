/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitisnum.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hepatitis.service.BsHepatitisBcheckinService;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.service.BsHepatitisBNumService;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;


/**
 * 乙肝疫苗针次信息Controller
 * 
 * @author xuejinshan
 * @version 2017-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/hepatitisnum/bsHepatitisBNum")
public class BsHepatitisBNumController extends BaseController {

	@Autowired
	private BsHepatitisBNumService bsHepatitisBNumService;
	@Autowired
	private BsHepatitisBcheckinService bsHepatitisBcheckinService;

	@ModelAttribute
	public BsHepatitisBNum get(@RequestParam(required = false) String id) {
		BsHepatitisBNum entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = bsHepatitisBNumService.get(id);
		}
		if (entity == null) {
			entity = new BsHepatitisBNum();
		}
		return entity;
	}

	/**
	 * 进入针刺添加页面
	 * @author xuejinshan
	 * @date 2017年8月8日 下午7:21:47
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "form")
	public String form(BsHepatitisBNum bsHepatitisBNum, @RequestParam String type, Model model) {
		//初始化记录参数
		bsHepatitisBNum = bsHepatitisBNumService.updateBsHepaNum(bsHepatitisBNum,type);
		model.addAttribute("bsHepatitisBNum", bsHepatitisBNum);
		//疫苗类型
		List<VaccInfo> list = bsHepatitisBcheckinService.getQueryVacc(null);
		model.addAttribute("vaccInfoList", list);
		//进入渠道
		model.addAttribute("type",type);
		return "modules/hepatitisnum/bsHepatitisBNumForm";
	}
	
	/**
	 * 保存针刺信息
	 * @author xuejinshan
	 * @date 2017年7月28日 上午11:12:29
	 * @description TODO 保存乙肝疫苗针次信息
	 * @param bsHepatitisBNum
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "save")
	public String save(BsHepatitisBNum bsHepatitisBNum, Model model, RedirectAttributes redirectAttributes, @RequestParam String vaccineDate, @RequestParam String type) {
		// 清除用户缓存
		UserUtils.clearCache();
		// 设置添加人
		bsHepatitisBNum.setCreateBy(UserUtils.getUser());
		// String转Date，补充遗漏的程序接种时间
		bsHepatitisBNum.setVaccineDate(DateUtils.parseDate(vaccineDate));
		bsHepatitisBNum.setDose("1");
		// 判断库存是否-1
		if(bsHepatitisBNum.getnStatus() == null){
			bsHepatitisBNum = bsHepatitisBNumService.dealProduct(bsHepatitisBNum, -1, model);
		}else if(bsHepatitisBNum.getnStatus().equals("0")){
			bsHepatitisBNum = bsHepatitisBNumService.dealProduct(bsHepatitisBNum, 0, model);
		}else if(bsHepatitisBNum.getnStatus().equals("1")){
			bsHepatitisBNum = bsHepatitisBNumService.dealProduct(bsHepatitisBNum, 1, model);
		}
		model.addAttribute("id",bsHepatitisBNum.getCheckId());
		addMessage(redirectAttributes, "保存信息成功");
		return "modules/hepatitisnum/bsHepatitisBNumForm";
	}
	
	/**
	 * 删除针次信息
	 * @author xuejinshan
	 * @date 2017年8月8日 下午7:24:17
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param model
	 * @param redirectAttributes
	 * @param id
	 * @return
	 *
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody Map<String, Object> delete(BsHepatitisBNum bsHepatitisBNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		//打印日志
		logger.info("删除针次信息开始["+ bsHepatitisBNum.getId() + "]----------------------------");
		//删除针次信息
		bsHepatitisBNumService.delete(bsHepatitisBNum);
		map.put("success", true);
		logger.info("删除针次信息结束["+ bsHepatitisBNum.getId() + "]----------------------------");
		return map;
	}
	
	/**
	 * 疫苗调价
	 * @author zhouqj
	 * @date 2018年1月23日 上午10:54:42
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	@RequestMapping(value = "adjustment")
	public String adjustment(BsHepatitisBNum bsHepatitisBNum,@RequestParam List<String> items, Model model) {
		List<BsHepatitisBNum> list = new ArrayList<BsHepatitisBNum>();
		BsManageProduct product = new BsManageProduct();
		BsHepatitisBNum bs = new BsHepatitisBNum();
		//成人
		for(String id : items){
			bs = bsHepatitisBNumService.get(id);
			product = bsHepatitisBNumService.updatePidByName(bs,product);
			if(product == null){
				model.addAttribute("message","疫苗查询失败，请检查疫苗库存和所属科室");
				return "modules/hepatitisnum/bsHepatitisBNumList";
			}
			if(product.getStorenum() <= 0){
				model.addAttribute("message","疫苗查询失败，("+ bsHepatitisBNum.getManufacturer() + "-" + bsHepatitisBNum.getBatch() +")库存为空，请重新登记选择疫苗。");
				return "modules/num/bsRabiesNumList";
			}
			bs.setOriginalPrice(product.getSellprice());
			if(bs.getFundStatus().equals(BsRabiesNum.FUNDSTATUS_YES)){
				bs.setCurrentPrice(bs.getCurrentPrice());
			}else{
				bs.setCurrentPrice(product.getSellprice());
			}
			list.add(bs);
		}
		if(list.size() > 0){
			model.addAttribute("list",list);
		}else{
			model.addAttribute("message","疫苗查询失败，针次记录不存在");
		}
		return "modules/hepatitisnum/bsHepatitisBNumList";
	}
	
	/**
	 * 保存调价信息
	 * @author zhouqj
	 * @date 2018年1月24日 下午2:15:41
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "saveAdjustment")
	public @ResponseBody Map<String, Object> saveAdjustment(@RequestParam(value="list", required=false, defaultValue="") String json) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			json = StringEscapeUtils.unescapeHtml4(json);
			List<Map<String, Object>> data = (List<Map<String, Object>>) JsonMapper.fromJsonString(json, List.class);
			for(Map<String, Object> map : data){
				BsHepatitisBNum bsHepatitisBNum = (BsHepatitisBNum) JsonMapper.fromJsonString(JsonMapper.toJsonString(map), BsHepatitisBNum.class);
				//保存调价结果
				bsHepatitisBNumService.saveAdjustment(bsHepatitisBNum);
			}
			returnMap.put("code", "200");
			returnMap.put("message", "保存成功");
		} catch (Exception e) {
			logger.error("调价保存失败",e);
			returnMap.put("code", "500");
			returnMap.put("message", "保存失败");
		}
		return returnMap;
	}
	
	/**
	 * 记录退款
	 * @author zhouqj
	 * @date 2018年1月16日 上午9:59:24
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	@RequestMapping(value = "refundById")
	public @ResponseBody Map<String, Object> refundById(BsHepatitisBNum bsHepatitisBNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		//打印日志
		logger.info("退款针次信息开始["+ bsHepatitisBNum.getId() + "]----------------------------");
		bsHepatitisBNum.setRealDate(null);
		bsHepatitisBNum.setnStatus(BsHepatitisBNum.NSTATUS_BEFOR);
		bsHepatitisBNum.setPayStatus(BsHepatitisBNum.STATUS_NORMAL);
		bsHepatitisBNum.setStatus(BsHepatitisBNum.STATUS_NOFINSH);
		//记录退款
		bsHepatitisBNumService.refundById(bsHepatitisBNum);
		map.put("success", true);
		logger.info("退款针次信息结束["+ bsHepatitisBNum.getId() + "]----------------------------");
		return map;
	}
	
	/**
	 * 查询进入接种明细页面
	 * @author zhouqj
	 * @date 2017年8月11日 下午1:05:31
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "list")
	public String list(BsHepatitisBNum bsHepatitisBNum, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date d[] = bsHepatitisBcheckinService.dateTime(bsHepatitisBNum.getBeginTime(), bsHepatitisBNum.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsHepatitisBNum.setBeginTime(d[0]);
			bsHepatitisBNum.setEndTime(d[1]);
		} else {
			return "modules/hepatitisnum/bsHepatitisBProductList";
		}
		//医生名称
		if(StringUtils.isBlank(bsHepatitisBNum.getCreateById())){
			// 清除用户缓存
			UserUtils.clearCache();
			bsHepatitisBNum.setCreateById(UserUtils.getUser().getId());
		}else if(bsHepatitisBNum.getCreateById().equals("0")){
			bsHepatitisBNum.setCreateById(null);
		}
		// 查询所有用户
		model.addAttribute("createByNameList", UserUtils.getCompanyUsers());
		//完成状态
		if(StringUtils.isBlank(bsHepatitisBNum.getPayStatus())){
			bsHepatitisBNum.setPayStatus("2");
		}
		//疫苗类型
		List<VaccInfo> list = bsHepatitisBcheckinService.getQueryVacc(null);
		model.addAttribute("vaccInfoList", list);
		if(StringUtils.isBlank(bsHepatitisBNum.getVaccType())){
			bsHepatitisBNum.setVaccType("2");
		}else if(bsHepatitisBNum.getVaccType().equals("0")){
			bsHepatitisBNum.setVaccType(null);
		}
		// 查询接种明细
		Page<BsHepatitisBNum> page = bsHepatitisBNumService.findPage(new Page<BsHepatitisBNum>(request, response), bsHepatitisBNum);
		for (BsHepatitisBNum bs : page.getList()) {
			//查询疫苗类型
			bs.setVaccType(bsHepatitisBcheckinService.getQueryVaccName(bs.getVaccType()));
		}
		model.addAttribute("page", page);
		return "modules/hepatitisnum/bsHepatitisBProductList";

	}
	
	/**
	 * 导出接种明细信息
	 * @author xuejinshan
	 * @date 2017年8月9日 下午6:04:26
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(BsHepatitisBNum bsHepatitisBNum, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Date d[] = bsHepatitisBcheckinService.dateTime(bsHepatitisBNum.getBeginTime(),bsHepatitisBNum.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsHepatitisBNum.setBeginTime(d[0]);
			bsHepatitisBNum.setEndTime(d[1]);
		} else {
			return "modules/hepatitisnum/bsHepatitisBProductList";
		}
		//医生名称
		if(bsHepatitisBNum.getCreateById().equals("0")){
			bsHepatitisBNum.setCreateById(null);
		}
		//疫苗类型
		if(bsHepatitisBNum.getVaccType().equals("0")){
			bsHepatitisBNum.setVaccType(null);
		}
		//一次性导出全部
		List<BsHepatitisBNum> p = bsHepatitisBNumService.findList(bsHepatitisBNum);
		for (BsHepatitisBNum bs : p) {
			//查询疫苗类型
			bs.setVaccType(bsHepatitisBcheckinService.getQueryVaccName(bs.getVaccType()));
		}
		try {
			String fileName = "成人疫苗接种明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			new ExportExcel("成人疫苗接种明细", BsHepatitisBNum.class) .setDataList(p).write(response, fileName) .dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出成人疫苗接种明细失败！失败信息：" + e.getMessage());
		}
		return "modules/hepatitisnum/bsHepatitisBProductList";
	}
	
	/**
	 * 接种明细统计打印
	 * @author xuejinshan
	 * @date 2017年8月9日 下午3:46:41
	 * @description 
	 *		TODO
	 * @param 
	 * @param createById
	 * @param manufacturer
	 * @param beginTime
	 * @param endTime
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printProduct")
	public String printProduct(BsHepatitisBNum bsHepatitisBNum,@RequestParam String createById, String manufacturer, @RequestParam String beginTime, @RequestParam String endTime, @RequestParam String vaccType, Model model) {
		//转换记录参数
		bsHepatitisBNum = bsHepatitisBNumService.updateBsRabiesNum(bsHepatitisBNum,manufacturer,beginTime,endTime,createById,vaccType);
		//查询所有用户
		model.addAttribute("createByNameList", UserUtils.getCompanyUsers());
		// 查询乙肝接种明细
		List<List<BsHepatitisBNum>> returnlistOne = bsHepatitisBNumService.findListDown(bsHepatitisBNum);
		model.addAttribute("page", returnlistOne);
		model.addAttribute("date", DateUtils.formatDate(new Date()));
		return "modules/hepatitisnum/bsHepatitisBProductListPrint";
	}
	

	/**
	 * 根据厂家名称查询对应厂家疫苗的 接种明细
	 * @author xuejinshan
	 * @date 2017年8月11日 下午7:04:05
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param createById
	 * @param manufacturer
	 * @param beginTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "listUp")
	public String listUp(BsHepatitisBNum bsHepatitisBNum,@RequestParam String createById, String manufacturer,
			@RequestParam String beginTime, @RequestParam String endTime, @RequestParam String vaccType,
			HttpServletRequest request,HttpServletResponse response, Model model) {
		//转换记录参数
		bsHepatitisBNum = bsHepatitisBNumService.updateBsRabiesNum(bsHepatitisBNum,manufacturer,beginTime,endTime,createById,vaccType);
		//疫苗类型
		List<VaccInfo> list = bsHepatitisBcheckinService.getQueryVacc(null);
		model.addAttribute("vaccInfoList", list);
		//查询所有用户
		model.addAttribute("createByNameList", UserUtils.getCompanyUsers());
		// 查询接种明细
		Page<BsHepatitisBNum> page = bsHepatitisBNumService.findPage(new Page<BsHepatitisBNum>(request, response), bsHepatitisBNum);
		for (BsHepatitisBNum bs : page.getList()) {
			//查询疫苗类型
			bs.setVaccType(bsHepatitisBcheckinService.getQueryVaccName(bs.getVaccType()));
		}
		model.addAttribute("page", page);
		return "modules/hepatitisnum/bsHepatitisBProductList";
	}
	
	/**
	 * 统计记录页面的数据展示
	 * @author xuejinshan
	 * @date 2017年8月11日 下午7:11:07
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "bsHepatitisBStockList")
	public String bsHepatitisBStockList(BsHepatitisBNum bsHepatitisBNum, Model model) {
		Date d[] = bsHepatitisBcheckinService.dateTime(bsHepatitisBNum.getBeginTime(), bsHepatitisBNum.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsHepatitisBNum.setBeginTime(d[0]);
			bsHepatitisBNum.setEndTime(d[1]);
		} else {
			return "modules/hepatitisnum/bsHepatitisBStockList";
		}
		// 医生名称
		if(StringUtils.isBlank(bsHepatitisBNum.getCreateById())){
			// 清除用户缓存
			UserUtils.clearCache();
			bsHepatitisBNum.setCreateById(UserUtils.getUser().getId());
		}else if(bsHepatitisBNum.getCreateById().equals("0")){
			bsHepatitisBNum.setCreateById(null);
		}
		//查询所有用户
		model.addAttribute("createByNameList", UserUtils.getCompanyUsers());
		//疫苗类型
		List<VaccInfo> list = bsHepatitisBcheckinService.getQueryVacc(null);
		model.addAttribute("vaccInfoList", list);
		if(StringUtils.isBlank(bsHepatitisBNum.getVaccType())){
			bsHepatitisBNum.setVaccType("2");
		}else if(bsHepatitisBNum.getVaccType().equals("0")){
			bsHepatitisBNum.setVaccType(null);
		}		
		//统计记录公用方法
		bsHepatitisBNumService.updateStock(bsHepatitisBNum,model);
		return "modules/hepatitisnum/bsHepatitisBStockList";
	}
	
	/**
	 * 统计记录页面的数据打印页面
	 * @author xuejinshan
	 * @date 2017年8月11日 下午7:09:17
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param model
	 * @param beginTime
	 * @param endTime
	 * @return
	 *
	 */
	@RequestMapping(value = "printStock")
	public String printStock(BsHepatitisBNum bsHepatitisBNum, Model model,
			@RequestParam String beginTime, @RequestParam String endTime) {
		// 默认当前时间
		Date begin = DateUtils.parseDate(beginTime);
		Date end = DateUtils.parseDate(endTime);
		bsHepatitisBNum.setBeginTime(begin);
		bsHepatitisBNum.setEndTime(end);
		// 医生名称
		if (bsHepatitisBNum.getCreateById().equals("0")) {
			model.addAttribute("createByName", "全部");
			bsHepatitisBNum.setCreateById(null);
		}else{
			model.addAttribute("createByName", bsHepatitisBNumService.queryCreateById(bsHepatitisBNum.getCreateById()));
		}
		//疫苗类型
		if(bsHepatitisBNum.getVaccType().equals("0")){
			bsHepatitisBNum.setVaccType(null);
		}
		//统计记录公用方法
		bsHepatitisBNumService.updateStock(bsHepatitisBNum,model);
		return "modules/hepatitisnum/bsHepatitisBStockListPrint";
	}
	
	/**
	 * 获取签字状态
	 * 
	 * @author xuejinshan
	 * @date 2017年7月31日 下午2:39:48
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "hmap")
	public @ResponseBody String smap(BsHepatitisBNum bsHepatitisBNum, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 打印日志
		logger.info("获取签字状态开始[" + bsHepatitisBNum.getId() + "]----------------------------");
		String text = Global.isHmap("get", bsHepatitisBNum.getId());
		// 打印日志
		logger.info("获取签字状态结束[" + bsHepatitisBNum.getId() + "]：" + text + "---------------");
		return text;
	}
	
	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年11月21日 上午11:04:49
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "updateSignStatus")
	public @ResponseBody Map<String, Object> updateSignStatus(BsHepatitisBNum bsHepatitisBNum, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//打印日志
		logger.info("修改签字状态开始["+ bsHepatitisBNum.getId() + "]----------------------------");
		//修改签字状态
		bsHepatitisBNumService.updateSignStatus(bsHepatitisBNum);
		map.put("success", true);
		logger.info("修改签字状态结束["+ bsHepatitisBNum.getId() + "]----------------------------");
		return map;
	}
}