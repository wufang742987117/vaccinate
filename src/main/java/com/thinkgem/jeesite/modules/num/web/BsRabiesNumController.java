/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.num.web;

import java.text.ParseException;
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
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.service.BsRabiesNumService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinDeal;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 狂犬疫苗针次Controller
 * 
 * @author wangdedi
 * @version 2017-03-06
 */
@Controller
@RequestMapping(value = "${adminPath}/num/bsRabiesNum")
public class BsRabiesNumController extends BaseController {

	@Autowired
	private BsRabiesNumService bsRabiesNumService;
	@Autowired
	private BsRabiesCheckinService bsRabiesCheckinService;

	@ModelAttribute
	public BsRabiesNum get(@RequestParam(required = false) String id) {
		BsRabiesNum entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = bsRabiesNumService.get(id);
		}
		if (entity == null) {
			entity = new BsRabiesNum();
		}
		return entity;
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月6日 下午4:55:12
	 * @description 添加、登记针次
	 * @param bsRabiesNum
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(value = "form")
	public String form(BsRabiesNum bsRabiesNum, @RequestParam String type, Model model) {
		//初始化记录参数
		bsRabiesNum = bsRabiesNumService.updateBsRabiesNum(bsRabiesNum,type);
		model.addAttribute("bsRabiesNum", bsRabiesNum);
		//查询狂苗种类
		List<BsManageProduct> productlist = bsRabiesCheckinService.vaccineType();
		model.addAttribute("productlist", productlist);
		//进入渠道
		model.addAttribute("type",type);
		return "modules/num/bsRabiesNumForm";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 下午2:35:30
	 * @description TODO 完成或修改针次数据并保存
	 * @param bsRabiesNum
	 * @param model
	 * @param vaccidate
	 * @param redirectAttributes
	 * @return
	 * 
	 */
	@RequestMapping(value = "save")
	public String save(BsRabiesNum bsRabiesNum, Model model, @RequestParam String vaccidate, @RequestParam String type, RedirectAttributes redirectAttributes) {
		// 清除用户缓存
		UserUtils.clearCache();
		bsRabiesNum.setCreateBy(UserUtils.getUser());
		// String转Date，补充遗漏的程序接种时间
		bsRabiesNum.setVaccidate(DateUtils.parseDate(vaccidate));
		// 判断库存是否-1
		if (bsRabiesNum.getNstatus() == null) {
			bsRabiesNum = bsRabiesNumService.saveNstatus(bsRabiesNum,-1,type,model);
		} else if ("0".equals(bsRabiesNum.getNstatus())) {
			bsRabiesNum = bsRabiesNumService.saveNstatus(bsRabiesNum,0,type,model);
		} else if ("1".equals(bsRabiesNum.getNstatus())) {
			bsRabiesNum = bsRabiesNumService.saveNstatus(bsRabiesNum,1,type,model);
		}
		model.addAttribute("id", bsRabiesNum.getCheckid());
		addMessage(redirectAttributes, "保存信息成功");
		return "modules/num/bsRabiesNumForm";
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 下午2:36:13
	 * @description TODO 删除针次记录
	 * @param bsRabiesNum
	 * @param redirectAttributes
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody Map<String, Object> delete(BsRabiesNum bsRabiesNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		//打印日志
		logger.info("删除针次信息开始["+ bsRabiesNum.getId() + "]----------------------------");
		//删除记录
		bsRabiesNumService.delete(bsRabiesNum);
		map.put("success", true);
		logger.info("删除针次信息结束["+ bsRabiesNum.getId() + "]----------------------------");
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
	public String adjustment(BsRabiesNum bsRabiesNum,@RequestParam List<String> items,@RequestParam List<String> notitems, Model model) {
		List<BsRabiesNum> list = new ArrayList<BsRabiesNum>();
		BsManageProduct product = new BsManageProduct();
		BsRabiesNum bs = new BsRabiesNum();
		//狂免
		for(String id : notitems){
			bs = bsRabiesNumService.get(id);
			product = bsRabiesNumService.updatePidByName(bs,product);
			if(product == null){
				model.addAttribute("message","疫苗查询失败，请检查疫苗库存和所属科室");
				return "modules/num/bsRabiesNumList";
			}
			if(product.getStorenum() <= 0){
				model.addAttribute("message","疫苗查询失败，("+ bsRabiesNum.getManufacturer() + "-" + bsRabiesNum.getBatchnum() +")库存为空，请重新登记选择疫苗。");
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
		//狂苗
		for(String id : items){
			bs = bsRabiesNumService.get(id);
			product = bsRabiesNumService.updatePidByName(bs,product);
			if(product == null){
				model.addAttribute("message","疫苗查询失败，请检查疫苗库存和所属科室");
				return "modules/num/bsRabiesNumList";
			}
			if(product.getStorenum() <= 0){
				model.addAttribute("message","疫苗查询失败，("+ bsRabiesNum.getManufacturer() + "-" + bsRabiesNum.getBatchnum() +")库存为空，请重新登记选择疫苗。");
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
		return "modules/num/bsRabiesNumList";
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
				BsRabiesNum bsRabiesNum = (BsRabiesNum) JsonMapper.fromJsonString(JsonMapper.toJsonString(map), BsRabiesNum.class);
				//保存调价结果
				bsRabiesNumService.saveAdjustment(bsRabiesNum);
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
	 * @date 2018年1月16日 上午9:23:42
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	@RequestMapping(value = "refundById")
	public @ResponseBody Map<String, Object> refundById(BsRabiesNum bsRabiesNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		//打印日志
		logger.info("退款针次信息开始["+ bsRabiesNum.getId() + "]----------------------------");
		bsRabiesNum.setRealdate(null);
		bsRabiesNum.setNstatus(BsRabiesNum.NSTATUS_BEFOR);
		bsRabiesNum.setPaystatus(BsRabiesNum.STATUS_NORMAL);
		bsRabiesNum.setStatus(BsRabiesNum.STATUS_NOFINSH);
		//记录退款
		bsRabiesNumService.refundById(bsRabiesNum);
		map.put("success", true);
		logger.info("退款针次信息结束["+ bsRabiesNum.getId() + "]----------------------------");
		return map;
	}
	
	/**
	 * 记录退款
	 * @author zhouqj
	 * @date 2018年1月16日 上午9:23:42
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	@RequestMapping(value = "refundDealById")
	public @ResponseBody Map<String, Object> refundDealById(BsRabiesCheckinDeal bsRabiesCheckinDeal) {
		Map<String, Object> map = new HashMap<String, Object>();
		//打印日志
		logger.info("退款伤口处理信息开始["+ bsRabiesCheckinDeal.getId() + "]----------------------------");
		bsRabiesCheckinDeal.setPaystatus(BsRabiesCheckinDeal.PAYSTATUS_NORMAL);
		//记录退款
		bsRabiesNumService.refundDealById(bsRabiesCheckinDeal);
		map.put("success", true);
		logger.info("退款针次信息结束["+ bsRabiesCheckinDeal.getId() + "]----------------------------");
		return map;
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 下午2:46:20
	 * @description TODO 默认接种明细数据显示
	 * @param bsRabiesNum
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(value = "list")
	public String listUp(BsRabiesNum bsRabiesNum, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date d[] = bsRabiesCheckinService.dateTime(bsRabiesNum.getBeginTime(),bsRabiesNum.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsRabiesNum.setBeginTime(d[0]);
			bsRabiesNum.setEndTime(d[1]);
		} else {
			return "modules/num/bsRabiesProductList";
		}
		if(StringUtils.isBlank(bsRabiesNum.getCreateById())){
			// 清除用户缓存
			UserUtils.clearCache();
			// 医生名称
			bsRabiesNum.setCreateById(UserUtils.getUser().getId());
		}else if(bsRabiesNum.getCreateById().equals("0")){
			bsRabiesNum.setCreateById(null);
		}
		if(StringUtils.isBlank(bsRabiesNum.getPaystatus())){
			bsRabiesNum.setPaystatus("2");
		}
		//查询所有用户
		model.addAttribute("createByNameList", bsRabiesNumService.getUserName());
		// 查询接种明细
		Page<BsRabiesNum> page = bsRabiesNumService.findPage(new Page<BsRabiesNum>(request, response), bsRabiesNum);
		model.addAttribute("page", page);
		return "modules/num/bsRabiesProductList";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月5日 下午2:17:49
	 * @description 导出狂犬疫苗接种明细
	 * @param bsRabiesNum
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * 
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(BsRabiesNum bsRabiesNum, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Date d[] = bsRabiesCheckinService.dateTime(bsRabiesNum.getBeginTime(), bsRabiesNum.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsRabiesNum.setBeginTime(d[0]);
			bsRabiesNum.setEndTime(d[1]);
		} else {
			return "modules/num/bsRabiesProductList";
		}
		if(bsRabiesNum.getCreateById().equals("0")){
			bsRabiesNum.setCreateById(null);
		}
		//一次性导出全部
		List<BsRabiesNum> p = bsRabiesNumService.findList(bsRabiesNum);
		try {
			String fileName = "狂犬疫苗接种明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx"; 
			new ExportExcel("狂犬疫苗接种明细", BsRabiesNum.class) .setDataList(p).write(response, fileName) .dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出狂犬疫苗接种明细失败！失败信息：" + e.getMessage());
		}
		return "modules/num/bsRabiesProductList";
	}
	
	/**
	 * 打印接种明细
	 * @author zhouqj
	 * @date 2017年7月10日 上午9:45:02
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param createById
	 * @param manufacturer
	 * @param beginTime
	 * @param endTime
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printProduct")
	public String printProduct(BsRabiesNum bsRabiesNum,@RequestParam String createById, String manufacturer, @RequestParam String beginTime, @RequestParam String endTime, Model model) {
		//转换记录参数
		bsRabiesNum = bsRabiesNumService.updateBsRabiesNum(bsRabiesNum,manufacturer,beginTime,endTime,createById);
		//查询所有用户
		model.addAttribute("createByNameList", bsRabiesNumService.getUserName());
		// 查询接种明细
		List<List<BsRabiesNum>> returnlistOne = bsRabiesNumService.findListDown(bsRabiesNum);
		model.addAttribute("page", returnlistOne);
		// 查询狂免疫苗
		List<BsRabiesNum> socketlistUp = bsRabiesNumService.findListUp(bsRabiesNum);
		model.addAttribute("socketlistUp", socketlistUp);
		model.addAttribute("date", DateUtils.formatDate(new Date()));
		return "modules/num/bsRabiesProductListPrint";
	}
	
	/**
	 * 跳转查询后接种明细数据显示
	 * @author zhouqj
	 * @date 2017年7月4日 下午11:15:42
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "listUp")
	public String listUpDown(BsRabiesNum bsRabiesNum,@RequestParam String createById, String manufacturer, @RequestParam String beginTime, @RequestParam String endTime, HttpServletRequest request,HttpServletResponse response, Model model) {
		//转换记录参数
		bsRabiesNum = bsRabiesNumService.updateBsRabiesNum(bsRabiesNum,manufacturer,beginTime,endTime,createById);
		//查询所有用户
		model.addAttribute("createByNameList", bsRabiesNumService.getUserName());
		// 查询接种明细
		Page<BsRabiesNum> page = bsRabiesNumService.findPage(new Page<BsRabiesNum>(request, response), bsRabiesNum);
		model.addAttribute("page", page);
		return "modules/num/bsRabiesProductList";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月6日 上午10:24:28
	 * @description TODO 统计记录默认显示（列出新建卡和接种数，列出不同疫苗批次消耗信息，疫苗接种剂次统计信息）
	 * @param bsRabiesNum
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(value = "bsRabiesStockList")
	public String bsRabiesStockList(BsRabiesNum bsRabiesNum, Model model) {
		Date d[] = bsRabiesCheckinService.dateTime(bsRabiesNum.getBeginTime(), bsRabiesNum.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsRabiesNum.setBeginTime(d[0]);
			bsRabiesNum.setEndTime(d[1]);
		} else {
			return "modules/num/bsRabiesStockList";
		}
		if(StringUtils.isBlank(bsRabiesNum.getCreateById())){
			// 清除用户缓存
			UserUtils.clearCache();
			// 医生名称
			bsRabiesNum.setCreateById(UserUtils.getUser().getId());
		}else if(bsRabiesNum.getCreateById().equals("0")){
			bsRabiesNum.setCreateById(null);
		}
		//查询所有用户
		model.addAttribute("createByNameList", bsRabiesNumService.getUserName());
		//统计记录公用方法
		bsRabiesNumService.updateStock(bsRabiesNum,model);
		return "modules/num/bsRabiesStockList";
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月6日 上午10:24:28
	 * @description TODO 统计记录打印
	 * @param bsRabiesNum
	 * @param model
	 * @return
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "printStock")
	public String printStock(BsRabiesNum bsRabiesNum, Model model, @RequestParam String beginTime, @RequestParam String endTime) {
		// 默认当前时间
		Date begin = DateUtils.parseDate(beginTime);
		Date end = DateUtils.parseDate(endTime);
		bsRabiesNum.setBeginTime(begin);
		bsRabiesNum.setEndTime(end);
		// 医生名称
		if (bsRabiesNum.getCreateById().equals("0")) {
			model.addAttribute("createByName", "全部");
			bsRabiesNum.setCreateById(null);
		}else{
			model.addAttribute("createByName", bsRabiesNumService.queryCreateById(bsRabiesNum.getCreateById()));
		}
		//统计记录公用方法
		bsRabiesNumService.updateStock(bsRabiesNum,model);
		return "modules/num/bsRabiesStockListPrint";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年7月26日 上午10:58:54
	 * @description 
	 *		TODO 获取签字状态
	 * @param bsRabiesNum
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "smap")
	public @ResponseBody String smap(BsRabiesNum bsRabiesNum, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//打印日志
		logger.info("获取签字状态开始["+ bsRabiesNum.getId() + "]----------------------------");
		String text = Global.isSmap("get",bsRabiesNum.getId());
		//打印日志
		logger.info("获取签字状态结束["+ bsRabiesNum.getId() + "]：" + text + "---------------");
		return text;
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年11月14日 下午4:56:26
	 * @description 
	 *		TODO 修改签字状态
	 * @param bsRabiesNum
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "updateSignStatus")
	public @ResponseBody Map<String, Object> updateSignStatus(BsRabiesNum bsRabiesNum, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//打印日志
		logger.info("修改签字状态开始["+ bsRabiesNum.getId() + "]----------------------------");
		//修改签字状态
		bsRabiesNumService.updateSignStatus(bsRabiesNum);
		map.put("success", true);
		logger.info("修改签字状态结束["+ bsRabiesNum.getId() + "]----------------------------");
		return map;
	}

}