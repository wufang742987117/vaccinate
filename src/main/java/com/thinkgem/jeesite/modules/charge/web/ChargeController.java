package com.thinkgem.jeesite.modules.charge.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.json.JsonUtil;
import org.directwebremoting.json.parse.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.charge.entity.BsChargeLog;
import com.thinkgem.jeesite.modules.charge.service.BsChargeLogService;
import com.thinkgem.jeesite.modules.charge.utils.ChargePrint;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/charge")
public class ChargeController extends BaseController {

	@Autowired
	private BsChargeLogService bsChargeLogService;
	@Autowired
	private BsRabiesCheckinService bsRabiesCheckinService;
	@Autowired
	private BsManageProductService bsManageProductService;

	/**
	 * 默认进入收银台页面
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:53:26
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "findCharge")
	public String findCharge(BsChargeLog bsChargeLog, Model model) {
		//清除用户缓存
		UserUtils.clearCache();
		model.addAttribute("printAgain", UserUtils.getUser().getAgainStatus());
		return "modules/charge/charge";
	}
	
	/**
	 * 发票总览数据接口
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:56:14
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "findListOverview")
	@ResponseBody
	public String findListOverview(BsChargeLog bsChargeLog,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> data = new HashMap<String, Object>();
		// 根据时间判断输入的时间是否正确
		Date d[] = bsRabiesCheckinService.dateTime(bsChargeLog.getBeginChargedate(), bsChargeLog.getEndChargedate());
		if(d != null){
			bsChargeLog.setBeginChargedate(d[0]);
			bsChargeLog.setEndChargedate(d[1]);
		}else{
			data.put("result", false);
			data.put("message", "查询参数时间段为空");
			return new JsonMapper(Include.ALWAYS).toJson(data);
		}
		// 清除用户缓存
		UserUtils.clearCache();
		bsChargeLog.setCreateBy(UserUtils.getUser());
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		//获取当前时间前后一天
		Calendar c1 = Calendar.getInstance();
		c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) -1);
		Calendar c2 = Calendar.getInstance();
		c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH) +1);
		Date old = c1.getTime();
		Date now = c2.getTime();
		//定义分页
		Page<BsChargeLog> page = new Page<BsChargeLog>(request, response);
		//总览数据
		Page<BsChargeLog> OverviewPageList = bsChargeLogService.findListOverview(page, bsChargeLog);
		for(BsChargeLog bc : OverviewPageList.getList()){
			if(bc.getCreateDate() != null){
				 if(bc.getCreateDate().after(old) && bc.getCreateDate().before(now)) {
		            bc.setRstatus(BsChargeLog.RSTATUS_DIFF);
				 }
			}
		}
		data.put("OverviewPageList", OverviewPageList);
		data.put("result", true);
		data.put("message", "查询成功");
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 发票明细数据接口
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:56:38
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "findListDetails")
	@ResponseBody
	public String findListDetails(BsChargeLog bsChargeLog,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> data = new HashMap<String, Object>();
		// 根据时间判断输入的时间是否正确
		Date d[] = bsRabiesCheckinService.dateTime(bsChargeLog.getBeginChargedate(), bsChargeLog.getEndChargedate());
		if(d != null){
			bsChargeLog.setBeginChargedate(d[0]);
			bsChargeLog.setEndChargedate(d[1]);
		}else{
			data.put("result", false);
			data.put("message", "查询参数时间段为空");
			return new JsonMapper(Include.ALWAYS).toJson(data);
		}
		// 清除用户缓存
		UserUtils.clearCache();
		bsChargeLog.setCreateBy(UserUtils.getUser());
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		//定义分页
		Page<BsChargeLog> page = new Page<BsChargeLog>(request, response);
		//明细数据
		Page<BsChargeLog> DetailsPageList = bsChargeLogService.findListDetails(page, bsChargeLog);
		data.put("DetailsPageList", DetailsPageList);
		data.put("result", true);
		data.put("message", "查询成功");
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 默认进入作废页面
	 * @author zhouqj
	 * @date 2018年2月6日 下午9:48:00
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "deleteTicket")
	public String deleteTicket(BsChargeLog bsChargeLog, Model model) {
		return "modules/charge/deleteTicket";
	}
	
	/**
	 * 保存作废发票
	 * @author zhouqj
	 * @date 2018年1月17日 下午9:42:39
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@RequestMapping(value = "deleteTicketSave")
	@ResponseBody
	public String deleteTicketSave(BsChargeLog bsChargeLog) {
		return bsChargeLogService.deleteTicketData(bsChargeLog);
	}
	
	/**
	 * 进入打印发票页面
	 * @author zhouqj
	 * @date 2018年2月6日 下午5:29:28
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "findPrintDatil")
	public String findPrintDatil(BsChargeLog bsChargeLog, Model model) {
		return "modules/charge/findPrintDatil";
	}
	
	/**
	 * 未打印发票修改开票状态
	 * @author zhouqj
	 * @date 2018年1月22日 下午3:14:14
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@RequestMapping(value = "updateBilling")
	@ResponseBody 
	public String updateBilling(BsChargeLog bsChargeLog) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		// 清除用户缓存
		UserUtils.clearCache();
		bsChargeLog.setCreateBy(UserUtils.getUser());
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		//修改未开票发票业务流水号
		if(StringUtils.isNotBlank(bsChargeLog.getNewBillNum())){
			String newbillNum = bsChargeLogService.verificationBillNum(bsChargeLog.getNewBillNum());
			if(!billNum.equals(newbillNum)){
				bsChargeLog.setNewBillNum(newbillNum);
				int bcount = bsChargeLogService.getBillNumCount(bsChargeLog.getNewBillNum());
				if(bcount == 0){
					bsChargeLogService.updateChargeByBillNum(bsChargeLog);
					bsChargeLog.setBillNum(bsChargeLog.getNewBillNum());
				}else{
					map.put("result", false);
					map.put("message", "该业务流水号数据已存在");
					return new JsonMapper(Include.ALWAYS).toJson(map);
				}
			}
		}
		//获取打印发票记录
		List<BsChargeLog> list = bsChargeLogService.findChargeCase(bsChargeLog);
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getVaccName() == null){
				list.get(i).setVaccName("伤口处理费");
			}
		}
		map.put("list", list);
		//修改开票状态
		int count = bsChargeLogService.updateBilling(bsChargeLog);
		if(count > 0){
			map.put("result", true);
			map.put("message", "修改开票状态成功");
		}else{
			map.put("result", false);
			map.put("message", "未找到可修改发票");
		}
		return new JsonMapper(Include.ALWAYS).toJson(map);
	}
	
	/**
	 * 默认进入录入信息页面
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:55:54
	 * @description 
	 *		TODO
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "insertPer")
	public String insertPer(@RequestParam(required = false) String sessionId,
			@RequestParam(required = false) String type, Model model) {
		if(BsChargeLog.INSER_SCAN.equals(type)){
			model.addAttribute("sessionId", sessionId);
		}
		return "modules/charge/insertPer";
	}
	
	/**
	 * 录入信息基本数据接口
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:56:54
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@RequestMapping(value = "insertCharge")
	@ResponseBody
	public String insertCharge(BsChargeLog bsChargeLog) {
		Map<String, Object> data = new HashMap<String, Object>();
		//查询疫苗名称列表
		List<Map<String, String>> vacclist = bsChargeLogService.getVaccList();
		data.put("vacclist", vacclist);
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 疫苗数据联动接口
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:57:50
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	@RequestMapping("/findViewListApi")
	@ResponseBody
	public String findViewListApi(BsManageProduct bsManageProduct){
		Map<String, Object> data = new HashMap<String, Object>();
		//查询疫苗批号信息
		List<BsManageProduct> productList = bsChargeLogService.findViewList(bsManageProduct);
		data.put("productList", productList);
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 验证产品是否存在是否为空接口
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:58:02
	 * @description 
	 *		TODO
	 * @param map
	 * @return
	 *
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "getVaccById")
	@ResponseBody
	public String getVaccById(@RequestParam Map<String,String> map){
		String batchnum = map.get("BATCHNUM");
		String manufacturer = map.get("MANUFACTURER");
		String dose = map.get("DOSE");
		String pid = map.get("PID");
		//查询疫苗产品价格信息
		Map<String, String> vacc = bsChargeLogService.getVaccById(map);
		if(vacc == null){
			vacc.put("RESULT", "false");
			vacc.put("MESSAGE", "查询失败，疫苗产品信息不存在");
		}else{
			vacc.put("BATCHNUM", batchnum);
			vacc.put("MANUFACTURER", manufacturer);
			vacc.put("DOSE", dose);
			vacc.put("PID",pid);
			vacc.put("RESULT", "true");
			vacc.put("MESSAGE", "查询成功");
		}
		return new JsonMapper(Include.ALWAYS).toJson(vacc);
	}
	
	/**
	 * 保存发票接口，完成缴费状态
	 * @author fuxin
	 * @date 2018年1月2日 下午11:03:25
	 * @description 
	 *		TODO
	 * @param request
	 * @return
	 * @throws JsonParseException
	 *
	 */
	@RequestMapping(value = "saveChargeData")
	@ResponseBody
	public String saveChargeData(HttpServletRequest request) throws JsonParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取传参
		String sessionId = request.getParameter("paySession");
		String jsonStr = request.getParameter("jsonData");
		Map<String, Object> data = JsonUtil.toSimpleObject(jsonStr);
		logger.info("接到保存发票信息回调，param=" + sessionId + "  jsonStr=" + jsonStr);
		try {
			//完成缴费状态返回msg
			map = bsChargeLogService.saveChargeData(sessionId, data);
			return new JsonMapper(Include.ALWAYS).toJson(map);
		} catch (Exception e) {
			logger.info("保存发票信息回调失败，param=" + sessionId,e);
			map.put("result", false);
			map.put("message", "缴费功能异常");
			return new JsonMapper(Include.ALWAYS).toJson(map);
		}
	}
	
	/**
	 * 获取业务流水号
	 * @author zhouqj
	 * @date 2018年2月6日 下午5:16:29
	 * @description 
	 *		TODO
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping(value = "getBillNumCurral")
	@ResponseBody
	public String getBillNumCurral(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		//业务流水号
		String billNum = bsChargeLogService.getBillNumCurral();
		if(billNum == null){
			data.put("result", false);
			data.put("message", "业务流水号不存在");
		}else{
			data.put("billNum", billNum);
			data.put("result", true);
			data.put("message", "查询成功");
		}
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 获取用户开票权限
	 * @author zhouqj
	 * @date 2018年2月6日 下午9:06:24
	 * @description 
	 *		TODO
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping(value = "getBillingStatus")
	@ResponseBody
	public String getBillingStatus(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		// 清除用户缓存
		UserUtils.clearCache();
		if(UserUtils.getUser().getBillingStatus() != null){
			data.put("billingStatus", UserUtils.getUser().getBillingStatus());
		}else{
			data.put("billingStatus", BsChargeLog.BILLINGSTATUS_YES);
		}
		data.put("result", true);
		data.put("message", "查询成功");
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 进入退费发票页面
	 * @author zhouqj
	 * @date 2018年2月6日 下午5:29:28
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "financeDatil")
	public String financeDatil(BsChargeLog bsChargeLog, Model model) {
		return "modules/charge/financeDatil";
	}
	
	/**
	 * 根据业务流水号返回发票具体信息
	 * @author zhouqj
	 * @date 2018年2月6日 下午7:04:46
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@RequestMapping(value = "findChargeCase")
	@ResponseBody 
	public String findChargeCase(BsChargeLog bsChargeLog) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		// 清除用户缓存
		UserUtils.clearCache();
		bsChargeLog.setCreateBy(UserUtils.getUser());
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		List<BsChargeLog> list = bsChargeLogService.findChargeCase(bsChargeLog);
		map.put("list", list);
		return new JsonMapper(Include.ALWAYS).toJson(map);
	}
	
	/**
	 * 退款保存
	 * @author zhouqj
	 * @date 2018年2月6日 下午8:49:56
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@RequestMapping(value = "refundCharge")
	@ResponseBody
	public String refundCharge(BsChargeLog bsChargeLog) {
		Map<String, Object> data = new HashMap<String, Object>();
		logger.info("接到退款操作-->" + bsChargeLog.getBillNum());
		// 清除用户缓存
		UserUtils.clearCache();
		bsChargeLog.setCreateBy(UserUtils.getUser());
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		List<BsChargeLog> list = bsChargeLogService.findList(bsChargeLog);
		if(list.size() > 0){
			double sumPrice = list.get(0).getSumPrice();
			double updateSumPriceOld = list.get(0).getUpdateSumPrice();
			double updateSumPriceNew = bsChargeLog.getUpdateSumPrice();
			if((sumPrice - updateSumPriceOld) < updateSumPriceNew){
				logger.error("线下退款回调失败，退款金额大于可退金额" + bsChargeLog.getBillNum());
				data.put("result", false);
				data.put("message", "线下退款回调失败，退款金额大于可退金额，可退金额为："+(sumPrice - updateSumPriceOld));
				return new JsonMapper(Include.ALWAYS).toJson(data);
			}
			list.get(0).setId(IdGen.uuid());
			list.get(0).setStatus(BsChargeLog.STATUS_NALDEL);
			list.get(0).setCreateDate(new Date());
			list.get(0).setUpdateSumPrice(bsChargeLog.getUpdateSumPrice());
			BsChargeLog bl = (BsChargeLog) JsonMapper.fromJsonString(JsonMapper.toJsonString(list.get(0)), BsChargeLog.class);
			bl.setCreateBy(UserUtils.getUser());
			logger.info("<--退款生成新记录开始-->");
			int updateCharge = bsChargeLogService.updateCharge(bl);
			bsChargeLogService.updateChargeList(bsChargeLog);
			logger.info("<--退款生成新记录结束-->");
			if(updateCharge > 0){
				logger.info("退款操作结束-->" + bsChargeLog.getBillNum());
				data.put("result", true);
				data.put("message", "退款成功");
				return new JsonMapper(Include.ALWAYS).toJson(data);
			}else{
				logger.error("线下退款回调失败，新增数量为0" + bsChargeLog.getBillNum());
				data.put("result", false);
				data.put("message", "线下退款回调失败，新增数量为0");
				return new JsonMapper(Include.ALWAYS).toJson(data);
			}
		}else{
			logger.error("线下退款回调失败，查询发票结果不存在" + bsChargeLog.getBillNum());
			data.put("result", false);
			data.put("message", "线下退款回调失败，查询发票结果不存在");
			return new JsonMapper(Include.ALWAYS).toJson(data);
		}
	}
	
	/**
	 * 进入设置页面
	 * @author zhouqj
	 * @date 2018年2月6日 下午5:29:28
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "setCharge")
	public String setCharge(BsChargeLog bsChargeLog, Model model) {
		return "modules/charge/setCharge";
	}
	
	/**
	 * 保存设置功能
	 * @author zhouqj
	 * @date 2018年2月7日 上午11:07:35
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@RequestMapping(value = "saveSetCharge")
	@ResponseBody 
	public String saveSetCharge(BsChargeLog bsChargeLog) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		// 清除用户缓存
		UserUtils.clearCache();
		bsChargeLog.setCreateBy(UserUtils.getUser());
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		//更新业务流水号
		int count = bsChargeLogService.getBillNumCount(bsChargeLog.getBillNum());
		if(count > 0){
			map.put("result", false);
			map.put("message", "业务流水号数据库已存在，不可重复");
			return new JsonMapper(Include.ALWAYS).toJson(map);
		}
		try {
			bsChargeLogService.updateBillNum(bsChargeLog.getBillNum());
			//更新用户开票权限
			bsChargeLogService.updateUserBillingStatus(bsChargeLog);
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "保存设置功能异常");
			return new JsonMapper(Include.ALWAYS).toJson(map);
		}
		map.put("result", true);
		map.put("message", "业务流水号和用户权限更新成功");
		return new JsonMapper(Include.ALWAYS).toJson(map);
	}
	
	/**
	 * 进入统计报表页面
	 * @author yangjian
	 * @date 2018年2月7日 下午5:29:28
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping(value="findReport")
	public String findReport(){
		return "modules/charge/statisticsReport";
	}
	
	/**
	 * 获取用户信息接口
	 * @author yangjian
	 * @date 2018年2月7日 下午13:47:25
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="getUsers")
	@ResponseBody
	public String getUsers(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> data = new HashMap<String, Object>();
		//查询所有用户
		List<User> users = UserUtils.getCompanyUsers();
		data.put("users", users);
		data.put("loginUser", UserUtils.getUser().getId());
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 报表统计，发票统计接口
	 * @author yangjian
	 * @date 2018年2月7日 下午13:47:25
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "chargeReport")
	@ResponseBody
	public String chargeReport(BsChargeLog bsChargeLog, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> data = new HashMap<String, Object>();
		Date d[] = bsRabiesCheckinService.dateTime(bsChargeLog.getBeginChargedate(), bsChargeLog.getEndChargedate());
		//判断前端输入的时间是否正确
		if(d != null){
			bsChargeLog.setBeginChargedate(d[0]);
			bsChargeLog.setEndChargedate(d[1]);
		}
		//判断前台传入的status、billing的值
		if(BsChargeLog.STATUS_ALL == bsChargeLog.getStatus()){
			bsChargeLog.setStatus(null);
		}
		if(BsChargeLog.BSTATUS_ALL.equals(bsChargeLog.getBilling())){
			bsChargeLog.setBilling(null);
		}
		//清除用户缓存
		UserUtils.clearCache();
		if(StringUtils.isBlank(bsChargeLog.getCreateById())){
			//用户名称
			bsChargeLog.setCreateById(UserUtils.getUser().getId());
		}else if(BsChargeLog.USERS_ALL.equals(bsChargeLog.getCreateById())){
			bsChargeLog.setCreateById(null);
		}
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		//定义分页
		Page<BsChargeLog> page = new Page<BsChargeLog>(request, response);
		//根据前端输入的查询条件查询发票记录
		Page<BsChargeLog> chargeLogs = bsChargeLogService.findPage(page, bsChargeLog);
		data.put("chargeLogs", chargeLogs);
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 进入发票统计打印页面
	 * @author yangjian
	 * @date 2018年2月7日 下午5:29:28
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping(value="findChargeTable")
	public String findChargeTable(){
		return "modules/charge/chargeTable";
	}
	
	/**
	 * 报表统计，发票统计打印接口
	 * @author chenming
	 * @date 2018年01月02日 上午9:10:23
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "printChargeReport")
	@ResponseBody
	public String printChargeReport(BsChargeLog bsChargeLog,HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=false, value="beg", defaultValue = "")Date beg,	
			@RequestParam(required=false, value="end", defaultValue = "")Date end,
			@RequestParam(required=false, value="createById", defaultValue = "")String createById) {
		Map<String, Object> data = new HashMap<String, Object>();
		//时间设置默认值
		beg = (beg==null)?DateUtils.getDay(new Date()):beg;
		end = (end==null)?DateUtils.getDayEnd(new Date()):end;
		bsChargeLog.setBeginChargedate(beg);
		bsChargeLog.setEndChargedate(end);
		//收银员
		if(BsChargeLog.USERS_ALL.equals(bsChargeLog.getCreateById())){
			bsChargeLog.setCreateById(null);
			data.put("createByName", "全部");
		}else{
			bsChargeLog.setCreateById(createById);
			if(UserUtils.get(bsChargeLog.getCreateById()) != null){
				data.put("createByName", UserUtils.get(bsChargeLog.getCreateById()).getLoginName());
			}else{
				data.put("createByName", "无");
			}
		}
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		//统计总额
		Double sumPrice = 0.00;
		//实际金额
		Double sumVaccPrice = 0.00;
		Double sumWounPrice = 0.00; //伤口处理费
		String billNumStart = "";  	//业务流水号开始
		String billNumEnd = "";		//业务流水号结束
		int chargeSum = 0 ;			//发票数量
		//实际金额(未开票)
		Double sumVaccPriceNo = 0.00;
		List<String> billNumNo = new ArrayList<String>();  	//业务流水号(未开票)
		int chargeSumNo = 0 ;		//发票数量(未开票)
		List<String> expBillNumNo = new ArrayList<String>();//未开发票作废业务流水号
		//作废金额
		Double sumVaccPriceNull = 0.00;
		List<String> expBillNum = new ArrayList<String>();		//作废业务流水号
		int expChargeSum = 0 ;		//作废发票数量
		//退费金额
		Double returnSumPrice = 0.00;
		List<String> returnBillNum = new ArrayList<String>();  //退费业务流水号
		int returnCharge = 0;		//退费发票数量
		//发票数量统计
		List<BsChargeLog> list = bsChargeLogService.findPrintList(bsChargeLog);
		boolean flag = true;   //判断是否是未开作废状态
		for(int i = 0;i < list.size();i++){
			flag = true;
			if(BsChargeLog.STATUS_NORMAL == list.get(i).getStatus() && BsChargeLog.BSTATUS_NORMAL.equals(list.get(i).getBilling())){
				if(BsChargeLog.TYPE_VACC.equals(list.get(i).getType())){
					//实际发票
					sumVaccPrice += list.get(i).getVaccCount() * list.get(i).getVaccPrice();
					chargeSum ++;
				}else if(BsChargeLog.TYPE_WOUN.equals(list.get(i).getType())){
					sumWounPrice += list.get(i).getVaccPrice();
				}
				
				sumPrice = sumVaccPrice + sumWounPrice;
			}else if(BsChargeLog.STATUS_NORMAL == list.get(i).getStatus() && BsChargeLog.BSTATUS_DEFAULT.equals(list.get(i).getBilling())){
				//实际未开发票
				if(BsChargeLog.TYPE_VACC.equals(list.get(i).getType())){
					sumVaccPriceNo += list.get(i).getSumPrice();
					billNumNo.add(list.get(i).getBillNum());
					chargeSumNo ++;
				}else if(BsChargeLog.TYPE_WOUN.equals(list.get(i).getType())){
					sumVaccPriceNo += list.get(i).getVaccPrice();
				}
				
			}else if(BsChargeLog.STATUS_DELETE == list.get(i).getStatus() && BsChargeLog.BSTATUS_NORMAL.equals(list.get(i).getBilling())){
				//作废发票
				if(BsChargeLog.TYPE_VACC.equals(list.get(i).getType())){
					sumVaccPriceNull += list.get(i).getSumPrice();
					expBillNum.add(list.get(i).getBillNum());
					expChargeSum ++;
				}else if(BsChargeLog.TYPE_WOUN.equals(list.get(i).getType())){
					sumVaccPriceNull += list.get(i).getVaccPrice();
				}
				
			}else if (BsChargeLog.STATUS_DELETE == list.get(i).getStatus() && BsChargeLog.BSTATUS_DEFAULT.equals(list.get(i).getBilling())) {
				//未开发票作废
				if(BsChargeLog.TYPE_VACC.equals(list.get(i).getType())){
					expBillNumNo.add(list.get(i).getBillNum());
					flag = false;
				}
				
			}
			//统计业务流水号
			if(flag){
				if(StringUtils.isBlank(billNumEnd)){
					billNumEnd = list.get(i).getBillNum();
					billNumStart = list.get(i).getBillNum();
				}else{
					billNumStart = list.get(i).getBillNum();
				}
			}
			//统计退费业务流水号
			if(list.get(i).getUpdateSumPrice() != 0 && BsChargeLog.STATUS_DELETE != list.get(i).getStatus()){
				if(BsChargeLog.TYPE_VACC.equals(list.get(i).getType())){
					returnSumPrice += list.get(i).getUpdateSumPrice();
					returnBillNum.add(list.get(i).getBillNum());
					returnCharge ++;
				}
			}
		}
		//金额大写-正常发票
		String sumVaccPriceBig = ChargePrint.number2CNMontrayUnit(new BigDecimal(sumPrice-returnSumPrice));
		//查询日期
		if(DateUtils.formatDate(beg).equals(DateUtils.formatDate(end))){
			data.put("date", DateUtils.formatDate(beg));
		}else{
			data.put("date", DateUtils.formatDate(beg)+" ~ "+DateUtils.formatDate(end));
		}
		data.put("newdate", DateUtils.getDate());
		if(billNumStart.equals(billNumEnd)){
			data.put("billFromTo",billNumStart);  				 	//实际业务流水号
		}else{
			data.put("billFromTo",billNumStart + "-" + billNumEnd); 	//实际业务流水号
		}
		data.put("sumNum", chargeSum);       						//实际发票数量
		data.put("sumVaccPrice", sumVaccPrice-returnSumPrice); 	//疫苗费
		data.put("sumWounPrice", sumWounPrice); 	//伤口处理费
		data.put("sumPrice", sumPrice-returnSumPrice); //实际总金额
		data.put("sumVaccPriceBig",sumVaccPriceBig);    			//实际金额大写
		//实际未开票----*不展示*
		data.put("billNumNo",billNumNo);    						//实际未开票业务流水号
		data.put("sumNumNo", chargeSumNo);       					//实际未开票发票数量
		data.put("sumVaccPriceNo", sumVaccPriceNo);  				//实际未开票总金额
		//作废发票
		data.put("expBillNum",expBillNum);    					//作废业务流水号
		data.put("expChargeSum", expChargeSum);       			//作废发票数量
		data.put("sumVaccPriceNull", sumVaccPriceNull);  			//作废总金额
		//退费发票
		data.put("returnBillNum",returnBillNum);    				//退费业务流水号
		data.put("returnCharge", returnCharge);      		 		//退费发票数量
		data.put("returnSumPrice",returnSumPrice);    			//退费总金额
		data.put("expBillNumNo", expBillNumNo);//未开发票作废流水号
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 进入疫苗详情打印页面
	 * @author yangjian
	 * @date 2018年2月7日 下午5:29:28
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping(value="findVaccTable")
	public String findVaccTable(){
		return "modules/charge/vaccTable";
	}
	
	/**
	 * 统计报表，疫苗使用详情接口
	 * @author yangjian
	 * @date 2018年02月06日
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @param request
	 * @param response
	 * @param beg
	 * @param end
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "findChargeVaccine")
	@ResponseBody
	public String chargeDatil(BsChargeLog bsChargeLog, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=false, value="beg", defaultValue = "")Date beg,	
			@RequestParam(required=false, value="end", defaultValue = "")Date end,
			@RequestParam(required=false, value="type", defaultValue = "")String type) {
		Map<String, Object> data = new HashMap<String, Object>();
		//时间设置默认值
		beg = (beg==null)?DateUtils.getDay(new Date()):beg;
		end = (end==null)?DateUtils.getDayEnd(new Date()):end;
		bsChargeLog.setBeginChargedate(beg);
		bsChargeLog.setEndChargedate(end);
		bsChargeLog.setCreateBy(UserUtils.getUser());
		//除去报废的发票记录
		bsChargeLog.setStatus(1);
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		//分页查询疫苗
		if(!BsChargeLog.VACC_OPERA.equals(type)){
			//定义分页
			Page<BsChargeLog> page = new Page<BsChargeLog>(request, response);
			Page<BsChargeLog> vaccinesPage = bsChargeLogService.PageChargeVaccine(page, bsChargeLog);
			data.put("vaccines", vaccinesPage);
			data.put("isPrint", "false");
		}
		//打印查询退费疫苗
		if(BsChargeLog.VACC_OPERA.equals(type)){
			List<BsChargeLog> vaccines = bsChargeLogService.findChargeVaccine(bsChargeLog);
			data.put("vaccines", vaccines);
			data.put("beg", DateUtils.formatDate(beg, "yyyy-MM-dd"));
			data.put("end", DateUtils.formatDate(end, "yyyy-MM-dd"));
			data.put("isPrint", "true");
		}
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 进入发票重打页
	 * @author yangjian
	 * @date 2018年3月6日
	 * @param bsChargeLog
	 * @param model
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("printChargeAgain")
	public String printChargeAgain(BsChargeLog bsChargeLog, Model model){
		return "modules/charge/printChargeAgain";
	}
	
	/**
	 * 进入发票重打搜索页
	 * @author yangjian
	 * @date 2018年3月6日
	 * @param bsChargeLog
	 * @param model
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("findPrintAgain")
	public String findPrintAgain(BsChargeLog bsChargeLog, Model model){
		return "modules/charge/findPrintAgain";
	}
	
	/**
	 * 根据业务流水号返回需要重打发票信息
	 * @author zhouqj
	 * @date 2018年2月6日 下午7:04:46
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@RequestMapping(value = "findChargePrintAgain")
	@ResponseBody 
	public String findChargePrintAgain(BsChargeLog bsChargeLog) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		UserUtils.clearCache();
		bsChargeLog.setCreateBy(UserUtils.getUser());
		//业务流水号统一验证方法
		String billNum = bsChargeLogService.verificationBillNum(bsChargeLog.getBillNum());
		bsChargeLog.setBillNum(billNum);
		List<BsChargeLog> list = bsChargeLogService.findChargeCase(bsChargeLog);
		if(list.size() != 0){
			data.put("patientName", list.get(0).getPatientName());//犬伤姓名
			data.put("billNum", list.get(0).getBillNum());//业务流水号
			data.put("sumPrice", list.get(0).getSumPrice());//总价
			data.put("pay", list.get(0).getPay());//支付金额
			data.put("refund", list.get(0).getRefund());//找零
			data.put("billing", list.get(0).getBilling());//开票状态
		}
		
		List<Map<String, Object>> vacc = new ArrayList<Map<String, Object>>();
		for (BsChargeLog chargeLog : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			if(chargeLog.getVaccName() == null){
				map.put("vaccName", "伤口处理费");
			}else{
				map.put("vaccName", chargeLog.getVaccName());
			}
			map.put("pid", chargeLog.getPid());
			map.put("vaccCode", chargeLog.getVaccCode());
			map.put("vaccPrice", chargeLog.getVaccPrice());
			map.put("batchnum", chargeLog.getVaccBatchnum());
			map.put("manufacturer", chargeLog.getVaccManufacturer());
			map.put("dose", chargeLog.getVaccDose());
			map.put("vaccCount", chargeLog.getVaccCount());
			
			vacc.add(map);
		}
		data.put("vacc", vacc);
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
}