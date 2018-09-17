/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.web;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.junl.common.CommonUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ExpChildBaseInfoVo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ExportExcelBean;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.sel;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.shequ.entity.SysCommunity;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;
import com.thinkgem.jeesite.modules.sys.entity.SysWorkingHours;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysWorkingHoursService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageBinded;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.entity.SysVaccRecord;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageBindedService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;
import com.thinkgem.jeesite.modules.vaccine.service.SysVaccRecordService;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 接种登记Controller
 * 
 * @author 王德地
 * @version 2017-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/child_vaccinaterecord/childVaccinaterecord1")
public class ChildVaccinaterecordController1 extends BaseController {
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private BsManageProductService bsManageProductService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private BsManageVaccinenumService bsManageVaccinenumService;
	@Autowired
	private QueneService queneService;
	@Autowired
	private SysVaccRecordService vaccRecordService;
	@Autowired
	private VacChildRemindService remindService;
	@Autowired
	private SysWorkingHoursService sysWorkingHoursService;
	@Autowired
	private SysCommunityService sysCommunityService;
	@Autowired
	private BsManageBindedService bsManageBindedService;

	private static final String TYPE = "02"; // 预约通知模板

	@ModelAttribute
	public ChildVaccinaterecord get(@RequestParam(required = false) String id) {
		ChildVaccinaterecord entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = childVaccinaterecordService.get(id);
		}
		if (entity == null) {
			entity = new ChildVaccinaterecord();
		}
		return entity;
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:52:20
	 * @description 首次进入接种登记
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = { "list", "" })
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 小票打印功能是否开启
		model.addAttribute("receiptType", Global.getInstance().isReceiptOption());
		return "modules/child_vaccinaterecord/childVaccinaterecordList";
	}

	/**
	 * 
	 * @author Jack
	 * @date 2018年2月7日 下午4:14:57
	 * @description
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param model
	 * @param birthbeginTime
	 * @param birthendTime
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "childDailyManagement")
	public String childDailyManagement(ExpChildBaseInfoVo baseinfo, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		// 赋默认值
		baseinfo.setDefaultValue();
		baseinfo.setVaccBCG();
		/*if(baseinfo.getVaccinates() == null){
			baseinfo.setVaccBCG();
		}*/
		model.addAttribute("expChildBaseInfoVo", baseinfo);

		// 绑定前台社区数据
		List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("areas", areas);
		// 绑定前台疫苗名称数据
		List<BsManageVaccine> bsManageVaccineList = bsManageVaccineService.findModelList();
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);

		// 判断选择的日期格式是否正确
		if (baseinfo.getBirthbeginTime() == null) {
			// 开始生日为空,设置为当年第一天
			baseinfo.setBirthbeginTime(DateUtils.getYearFirst(Integer.valueOf(DateUtils.getYear())));
		}
		if (baseinfo.getBirthendTime() == null) {
			// 结束生日为空,设置为当天
			baseinfo.setBirthendTime(DateUtils.parseDate(DateUtils.getDate()));
		}
		if (!baseinfo.resetTime()) {
			addMessage(model, "你输入的出生日期格式不正确");
			return "modules/child_vaccinaterecord/dailyManagement";
		}
		if (baseinfo.getBirthbeginTime().after(baseinfo.getBirthendTime())) {
			addMessage(model, "你输入的出生日期起时间晚于止时间");
			return "modules/child_vaccinaterecord/dailyManagement";
		}

		// 判断社区选项是否为空,为空则提示选择社区
		if (baseinfo.getAreas() == null || baseinfo.getAreas().length == 0) {
			addMessage(model, "请选择您要查询的社区");
			return "modules/child_vaccinaterecord/dailyManagement";
		}else{
			if("total".equals(String.valueOf(baseinfo.getAreas()[0] ) ) ){
				//设置所有的社区项为选中项
				String[] areaArr = new String[areas.size()];
				if(areas != null){
					for(int i=0; i<areas.size(); i++){
						areaArr[i] = areas.get(i).getCode();
					}
				}
				baseinfo.setAreas(areaArr);
			}
			if("none".equals(String.valueOf(baseinfo.getAreas()[0] ) ) ){
				String[] areaArr = new String[1];
				baseinfo.setAreas(areaArr);
			}
			
		}

		// 现管单位为本地，更新officeinfo
		String officeCode = "";
		if (baseinfo.getOfficeinfo() != null) {
			if (ExpChildBaseInfoVo.OFFICEINFO_LOCAL.equals(baseinfo.getOfficeinfo())) {
				officeCode = OfficeService.getFirstOffice().getCode();
			}
		}

		// 获取免疫程序id
		List<BsManageVaccinenum> nums = bsManageVaccinenumService.findList(new BsManageVaccinenum());
		Map<Object, List<BsManageVaccinenum>> numMap = CommonUtils.getTreeDateByParam(BsManageVaccinenum.class, nums,
				"group");

		boolean flag = false;

		// 要查询的所有疫苗大类 + 针次 数字编码
		Set<BsManageVaccinenum> vaccNums = new HashSet<>();
		// 将勾选到的疫苗名称下的所有疫苗大类+针次编码 拼接成字符串,在sql中作为vacccode in()查询条件
		if (baseinfo.getVaccinates() != null) {
			for (Entry<Object, List<BsManageVaccinenum>> entry : numMap.entrySet()) {
				if (baseinfo.getVaccinates().contains(entry.getKey())) {
					flag = true;
					vaccNums.addAll(entry.getValue());
				}
			}
		}

		if (!flag) {
			// 未进入疫苗编码设置循环,原始数据库EXP_ROUTINEVACC6_1DETAIL表中暂无此项数据
			addMessage(model, "选定的疫苗无数据");
			return "modules/child_vaccinaterecord/dailyManagement";
		}

		// 根据逾期未种的超时月数计算统计的时间开始范围,对应EXP_ROUTINEVACC6_1Detail表中的YEAR_MONTH(VARCHAR2)字段
		Calendar c = Calendar.getInstance();
		int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH) + 1;

		// 定义查询截止的月份字符串
		String endSearchMonth = null;

		if (nowMonth > baseinfo.getOvmonth()) {
			endSearchMonth = nowYear + "-" + (nowMonth - baseinfo.getOvmonth());
		} else if (nowMonth == baseinfo.getOvmonth()) {
			endSearchMonth = (nowYear - 1) + "-12";
		} else {
			endSearchMonth = (nowYear - 1) + "-" + (12 - baseinfo.getOvmonth() + nowMonth);
		}

		// 初始化areaArr
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("endSearchMonth", endSearchMonth);
		searchMap.put("officeCode", officeCode);
		searchMap.put("vaccNums", vaccNums);
		searchMap.put("baseinfo", baseinfo);

		// 统计不排除近期已种的记录总数
		Integer totalRowNum = childVaccinaterecordService.getYQWZChildBaseInfoCount(searchMap);

		Page<HashMap<String, Object>> page = new Page<HashMap<String, Object>>(req, resp);
		
		// 根据传入条件获取查询数据
		resultList = childVaccinaterecordService.getYQWZChildBaseInfo(searchMap, page);

		if (resultList.size() == 0) {
			addMessage(model, "无符合该条件的数据项");
			return "modules/child_vaccinaterecord/dailyManagement";
		}

		page.setCount(totalRowNum);
		page.setList(resultList);
		model.addAttribute("page", page);

		return "modules/child_vaccinaterecord/dailyManagement";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年2月28日 上午10:05:38
	 * @description 短信通知
	 * @param request
	 * @param response
	 * @param model
	 * @param beginTime
	 * @param endTime
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "shortNote")
	public @ResponseBody String shortNote(HttpServletRequest request, HttpServletResponse response, Model model,
			String valArr) {
		// AJAX传递的数组转换
		String json = HtmlUtils.htmlUnescape(valArr);
		if (json.length() <= 2) {
			return "请选择你要发送的儿童个案";
		}
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = (List<Map<String, String>>) JsonMapper.fromJsonString(json, List.class);
		int success = 1;
		for (Map<String, String> arg : list) {
			/*
			 * String result =
			 * asyncService.sendNoticeSMS(arg.get("phone"),arg.get("name"));
			 */
			String result = childVaccinaterecordService.sendNoticeSMS(arg.get("phone"), arg.get("name"));
			if (StringUtils.isBlank(result)) {
				return "发送失败";
			}
			logger.debug(result);
			if (result.indexOf("true") > -1) {
				success = 0;
			}

		}
		String state = null;
		if (success == 0) {
			state = "发送成功";
		} else {
			state = "发送失败";
		}
		return state;

	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月20日 下午1:15:34
	 * @description 根据儿童编码后六位查询儿童信息
	 * @param request
	 * @param response
	 * @param model
	 * @param valArr
	 * @return
	 *
	 */
	/* @RequiresPermissions("child:cord:view") */
	@RequestMapping(value = "childlist")
	public String childlist(HttpServletRequest request, HttpServletResponse response, Model model,
			ChildBaseinfo baseinfo) {

		if (StringUtils.isNotBlank(baseinfo.getId())) {
			baseinfo = childBaseinfoService.get(baseinfo.getId());
			model.addAttribute("arg", "1");
			model.addAttribute("code1", baseinfo.getChildcode());
		} else {
			if (baseinfo.getBirthday() != null) {
				Date d[] = childVaccinaterecordService.date(baseinfo.getBirthday(), baseinfo.getBirthday());
				baseinfo.setBirthbeginTime(d[0]);
				baseinfo.setBirthendTime(d[1]);
				baseinfo.setBirthday(null);
			}
			if (StringUtils.isNotBlank(baseinfo.getSearchBirth())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				sdf.setLenient(false);
				String yearpre = DateUtils.getYear().substring(0, 2);
				try {
					if (null == sdf.parse(yearpre + baseinfo.getSearchBirth())) {
						baseinfo.setSearchBirth("");
					} else {
						baseinfo.setSearchBirth(yearpre + baseinfo.getSearchBirth());
					}
				} catch (Exception e) {
					baseinfo.setSearchBirth("");
				}
			}
			baseinfo.setSituations("1,2,4,9");
			List<ChildBaseinfo> childlist = childBaseinfoService.findList(baseinfo);
			if (childlist.size() > 1) {
				model.addAttribute("childlist", childlist);
				model.addAttribute("childBaseinfo", baseinfo);
			} else if (childlist.size() > 0) {
				model.addAttribute("arg", "1");
				model.addAttribute("code1", childlist.get(0).getChildcode());
			} else {
				model.addAttribute("arg", "2");
			}
		}

		return "modules/child_vaccinaterecord/childlist";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:25:41
	 * @description 输入儿童编码查询儿童信息
	 * @param request
	 * @param response
	 * @param model
	 * @param childid
	 * @param childid1
	 * @return
	 * 
	 */

	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = { "list1" })
	public String list1(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam String childid, @RequestParam(required = false) String from) {
		// 根据儿童编码（childid），转换地址信息
		ChildBaseinfo childBaseinfo = childBaseinfoService.getByNo(childid);
		if (childBaseinfo != null) {
			childBaseinfo.setMouage(DateUtils.getMouthAge(childBaseinfo.getBirthday()));
			model.addAttribute("ocode", childBaseinfo.getOfficeinfo());
		}
		childBaseinfo = childBaseinfoService.convert(childBaseinfo);
		if (childBaseinfo != null) {
			model.addAttribute("childBaseinfo", childBaseinfo);
			// 查询儿童所有已接种的记录
			ChildVaccinaterecord childVaccinaterecord = new ChildVaccinaterecord();
			childVaccinaterecord.setChildid(childBaseinfo.getId());
			childVaccinaterecord.setOrderBy(
					" A .STATUS, SUBSTR(A .VACCINEID, 0, 2), SUBSTR(A .NID, 0, 2) DESC,A.vacctype ,A .DOSAGE ");
			// 根据儿童的ID和创建时间排序
			List<ChildVaccinaterecord> childVaccinaterecordList = childVaccinaterecordService
					.findList(childVaccinaterecord);
			if (childVaccinaterecordList.size() > 0) {
				// 数组转树形
				String op = "first";
				int a = 0;
				int b = 0;
				List<ChildVaccinaterecord> temp = new ArrayList<>();
				List<List<ChildVaccinaterecord>> returnlist = new ArrayList<>();
				for (int i = 0; i < childVaccinaterecordList.size(); i++) {
					if (op.equals("first")) {
						op = childVaccinaterecordList.get(i).getVaccCode();
					}
					if (!op.equals(childVaccinaterecordList.get(i).getVaccCode())) {
						returnlist.add(temp);
						temp = new ArrayList<>();
						op = childVaccinaterecordList.get(i).getVaccCode();

					}
					if (temp.size() == 0 && i != 0) {
						if (b == 0) {
							childVaccinaterecordList.get(i - i).setSize(i - a);
						}
						childVaccinaterecordList.get(i - (i - b)).setSize(i - a);
						b = i;
						a = i;
					}
					temp.add(childVaccinaterecordList.get(i));
					if (i == childVaccinaterecordList.size() - 1) {
						childVaccinaterecordList.get(i - (i - b)).setSize(i - a + 1);
					}
				}
				returnlist.add(temp);
				// 数组转树形 结束
				model.addAttribute("returnlist", returnlist);
			}
			// 查询儿童所有未接种的记录
			List<BsManageVaccinenum> VaccList = bsManageVaccinenumService.getVaccList(childBaseinfo.getChildcode());
			// 查询已预约记录
			List<VacChildRemind> reminds = remindService.findByChildcode(childBaseinfo.getChildcode());
			List<BsManageVaccine> vaccines = bsManageVaccineService.findList(new BsManageVaccine());
			int preIdx = 0;
			for (int i = VaccList.size() - 1; i >= 0; i--) {
				for (VacChildRemind remind : reminds) {
					BsManageVaccine vaccine = null;
					for (BsManageVaccine bv : vaccines) {
						if (bv.getId().equals(remind.getVaccId())) {
							vaccine = bv;
							break;
						}
					}
					if (vaccine == null) {
						for (BsManageVaccine bv : vaccines) {
							if (bv.getmNum().equals(remind.getVaccId())) {
								vaccine = bv;
								break;
							}
						}
					}
					if (vaccine == null) {
						continue;
					}
					if (VaccList.get(i).getId().substring(0, 2).equals(vaccine.getmNum())) {
						VaccList.get(i).setRemindDate(remind.getRemindDate());
						VaccList.get(i).setRid(remind.getId());
						VaccList.add(0, VaccList.get(i));
						VaccList.remove(i + 1);
						preIdx++;
						i++;
						break;
					}
				}
				if (i < preIdx) {
					break;
				}
			}

			// 查询一月内是否接种过活苗
			int lastLiveDays = childVaccinaterecordService.getLastLiveDays(childBaseinfo);
			if (lastLiveDays < 31 && lastLiveDays > 0) {
				model.addAttribute("lastLiveDaysTips",
						"上次接种活疫苗距离今天&nbsp;<strong style=\'color: red;\'>" + lastLiveDays + "</strong>&nbsp;天");
			}

			model.addAttribute("lastLiveDays", lastLiveDays);
			model.addAttribute("VaccList", VaccList);
			model.addAttribute("reminds", reminds);
		}
		model.addAttribute("childid", childid);
		if (StringUtils.isNotBlank(from)) {
			model.addAttribute("from", "inoculate");
		}
		model.addAttribute("zdbm", OfficeService.getFirstOfficeCode());

		// 小票打印功能是否开启
		model.addAttribute("receiptType", Global.getInstance().isReceiptOption());
		return "modules/child_vaccinaterecord/childVaccinaterecordList";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:24:39
	 * @description 打印排号
	 * @param childVaccinaterecord
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @param price
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "stamp")
	public @ResponseBody Map<String, Object> stamp(ChildVaccinaterecord childVaccinaterecord,
			VacChildRemind vacChildRemind, @RequestParam String nid,
			@RequestParam(required = false, defaultValue = "") String rid,
			@RequestParam(required = false, value = "roomcode", defaultValue = "") String roomcode) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", "500");

		ChildVaccinaterecord record = new ChildVaccinaterecord();
		record.setChildid(childVaccinaterecord.getChildid());
		record.setNidEq(nid);
		if (childVaccinaterecordService.findList(record).size() > 0) {
			returnMap.put("msg", "该儿童已接种该疫苗");
			return returnMap;
		}

		if (StringUtils.isBlank(childVaccinaterecord.getProductid())) {
			returnMap.put("msg", "疫苗信息异常");
			return returnMap;
		}

		// 特殊权限
		UserUtils.clearCache();
		String specialStatus = UserUtils.getUser().getSpecialStatus();

		BsManageProduct product = bsManageProductService.get(childVaccinaterecord.getProductid());
		// 疫苗小类名称
		childVaccinaterecord.setVaccName(product.getName());
		childVaccinaterecord = childVaccinaterecordService.insertion(childVaccinaterecord, nid, product);
		childVaccinaterecord.setSource(ChildVaccinaterecord.SOURCE_DJT);
		// 获取儿童信息
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		if (baseinfo != null) {
			baseinfo.setMouage(DateUtils.getMouthAge(baseinfo.getBirthday()));
		}
		// 用于返回小票信息
		returnMap.put("name", baseinfo);
		Map<String, Object> code = new HashMap<String, Object>();
		if (UserUtils.getUserPreference().isQuick()) {
			//快速模式,直接完成
			//记录最后一针完成时间,用于计算留观结束
			Global.getInstance().setLastFinishTime(new Date());
			
			code.put("no", "");
			code.put("name", baseinfo);
			code.put("product", product);
			code.put("vac", childVaccinaterecord.getVaccName());
			code.put("manu", childVaccinaterecord.getManufacturer());
			if ("1".equals(specialStatus)) {
				code.put("price", childVaccinaterecord.getCurrentPrice());
			} else {
				code.put("price", childVaccinaterecord.getPrice());
			}
			code.put("time", DateUtils.formatDate(new Date(), " yyyy-MM-dd HH:mm:ss "));
			code.put("wait", "");
			code.put("pin", childVaccinaterecord.getDosage());
			code.put("impart", bsManageVaccineService.getImpartByVaccinateId(childVaccinaterecord.getVaccineid()));
			// code.put("callBackUrl",
			// request.getRequestURL().toString().replace(request.getRequestURI(),
			// "")+ "/vaccinate"+ Global.getFrontPath() +
			// "/childBaseinfo/payCallBack?queueno="+map.get("msg"));
			if (childVaccinaterecord.getPrice() > 0) {
				// pay=0 打印小票时会转为未付款，并且附加二维码
				code.put("pay", "0");
			} else {
				code.put("pay", "免费");
			}
			childVaccinaterecord.setStatus(ChildVaccinaterecord.STATUS_YET);
			childVaccinaterecord.setDoctor(UserUtils.getUser().getName());
			childVaccinaterecord.setVaccinatedate(new Date());
			childVaccinaterecord.updateProduct(product);
			// 保存接种记录
			childVaccinaterecordService.save(childVaccinaterecord);
			if ("1".equals(specialStatus)) {
				childVaccinaterecordService.saveAdjustment(childVaccinaterecord);
			}
			// 更新库存记录
			// 库存-1
			String isnew = "1";
			if (product.getSpec() > 1) {
				if (product.getRest() - 1 < 0) {
					product.setStorenum(product.getStorenum() - 1);
					product.setRest(product.getSpec() - 1);
				} else {
					product.setRest(product.getRest() - 1);
					isnew = "0";
				}
			} else {
				product.setStorenum(product.getStorenum() - 1);
			}
			bsManageProductService.save(product);
			bsManageProductService.updateRest(product);
			// 增加疫苗使用记录
			SysVaccRecord svr = new SysVaccRecord(product.getId(), UserUtils.getUser().getId(), product.getVaccName(),
					UserUtils.getUser().getName());
			svr.setId(childVaccinaterecord.getId());
			svr.setIsnew(isnew);
			svr.setStock(product.getStorenum());
			vaccRecordService.insert(svr);
			// 获取微信签字结果更新到本地
			Map<String, Object> maplist = new HashMap<String, Object>();
			maplist.put("list", null);
			maplist.put("success", false);
			//处理本地签字
			if(StringUtils.isNotBlank(childVaccinaterecord.getSignList())){
				updateDJTIE10Sign(childVaccinaterecord, maplist, code);
			}else{
				if (StringUtils.isNotBlank(rid)) {
					// 完成后，将预约记录设置为完成状态
					remindService.finsihRemind(rid);
					updateWxSign(childVaccinaterecord, nid, rid, maplist, code);
				}
				// 微信没有签字，使用本地签字
				if (ChildVaccinaterecord.SIGNATURE_NO.equals(childVaccinaterecord.getSignature())) {
					updateDJTSign(childVaccinaterecord, maplist, code);
				}
			}
			returnMap.put("code", "200");
			returnMap.put("msg", "登记保存成功");
			returnMap.put("tips", code);
		} else {
			// 排号
			Quene q = new Quene();
			q.setChildid(baseinfo.getChildcode());
			q.setVaccineid(nid);
			q.setPid(product.getId());
			q.setOriginalPrice(childVaccinaterecord.getOriginalPrice());
			q.setCurrentPrice(childVaccinaterecord.getCurrentPrice());
			Map<String, String> map = queneService.insertQuene(q, ChildVaccinaterecord.PAY_STATUS_NO, roomcode);
			logger.info("接到排号返回数据" + JsonMapper.toJsonString(map));

			if ("200".equals(map.get("code"))) {
				code.put("no", map.get("msg"));
				code.put("name", baseinfo);
				code.put("product", product);
				code.put("vac", childVaccinaterecord.getVaccName());
				code.put("manu", childVaccinaterecord.getManufacturer());
				if ("1".equals(specialStatus)) {
					code.put("price", childVaccinaterecord.getCurrentPrice());
				} else {
					code.put("price", childVaccinaterecord.getPrice());
				}
				code.put("time", DateUtils.formatDate(new Date(), " yyyy-MM-dd HH:mm:ss "));
				code.put("wait", queneService.waitNum(map.get("msg")));
				code.put("pin", childVaccinaterecord.getDosage());
				code.put("impart", bsManageVaccineService.getImpartByVaccinateId(childVaccinaterecord.getVaccineid()));
				// code.put("callBackUrl",
				// request.getRequestURL().toString().replace(request.getRequestURI(),
				// "")+ "/vaccinate"+ Global.getFrontPath() +
				// "/childBaseinfo/payCallBack?queueno="+map.get("msg"));
				if (childVaccinaterecord.getPrice() > 0) {
					// pay=0 打印小票时会转为未付款，并且附加二维码
					code.put("pay", "0");
				} else {
					code.put("pay", "免费");
				}
				childVaccinaterecord.setBodypart(null);
				// 保存接种记录
				childVaccinaterecordService.save(childVaccinaterecord);
				if ("1".equals(specialStatus)) {
					childVaccinaterecordService.saveAdjustment(childVaccinaterecord);
				}
				// 获取微信签字结果更新到本地
				Map<String, Object> maplist = new HashMap<String, Object>();
				maplist.put("list", null);
				maplist.put("success", false);
				//处理本地签字
				if(StringUtils.isNotBlank(childVaccinaterecord.getSignList())){
					updateDJTIE10Sign(childVaccinaterecord, maplist, code);
				}else{
					if (StringUtils.isNotBlank(rid)) {
						// 完成后，将预约记录设置为完成状态
						remindService.finsihRemind(rid);
						updateWxSign(childVaccinaterecord, nid, rid, maplist, code);
					}
					// 微信没有签字，使用本地签字
					if (ChildVaccinaterecord.SIGNATURE_NO.equals(childVaccinaterecord.getSignature())) {
						updateDJTSign(childVaccinaterecord, maplist, code);
					}
				}
				returnMap.put("code", "200");
				returnMap.put("msg", "排号成功");
				returnMap.put("tips", code);
			} else {
				returnMap.put("msg", map.get("msg"));
				return returnMap;
			}
		}
		// 保存预约信息
		vacChildRemind.setChildcode(baseinfo.getChildcode());
		Map<String, Object> remindResult = remindService.saveRemind(vacChildRemind, TYPE);
		// 判断下一针时间
		if (remindResult.containsKey("nextTime") && remindResult.containsKey("nextVacc")
				&& StringUtils.isNoneBlank(remindResult.get("nextVacc").toString())) {
			code.put("nextTime", remindResult.get("nextTime"));
			code.put("nextVacc", remindResult.get("nextVacc"));
			code.put("selectTime", remindResult.get("selectTime"));
		}

		return returnMap;
	}

	/**
	 * 获取登记台ie签字
	 * @author zhouqj
	 * @date 2018年3月1日 下午8:27:41
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param maplist
	 * @param code
	 *
	 */
	private void updateDJTIE10Sign(ChildVaccinaterecord childVaccinaterecord, Map<String, Object> maplist, Map<String, Object> code) {
			//转换图片背景为白色
			String signStr = updateImageColor(childVaccinaterecord.getSignList());
			if(StringUtils.isNotBlank(signStr)){
				// 判断签字是否存在
				// 打印签字信息
				code.put("sign", signStr);
				// base64 转换签字
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					byte[] sign = decoder.decodeBuffer(signStr);
					if (null != sign && sign.length > 0) {
						childVaccinaterecord.setSignatureData(sign);
						childVaccinaterecord.setStype(ChildVaccinaterecord.SIGNATURE_SOURCE_DJT);
						// 查询该记录签字是否存在
						int count = childVaccinaterecordService.querySignature(childVaccinaterecord);
						if (count == 0) {
							// 新增签字
							childVaccinaterecordService.insertSignatures(childVaccinaterecord);
						}
						// 修改签字状态
						childVaccinaterecord.setSignature(ChildVaccinaterecord.SIGNATURE_YES);
						childVaccinaterecordService.updateSignatures(childVaccinaterecord);
					}
					maplist.put("success", true);
				} catch (Exception e) {
					logger.error("本地签字base64转bytes失败", e.getMessage());
				}
			}
	}

	/**
	 * 转换图片背景为白色
	 * @author zhouqj
	 * @date 2018年3月1日 下午8:34:08
	 * @description 
	 *		TODO
	 * @param signature
	 * @return
	 *
	 */
	private String updateImageColor(String signature) {
		//转白色背景--------------
	    try {
	    	BASE64Decoder decoder = new BASE64Decoder();
			byte[] arr =  decoder.decodeBuffer(signature);
			ByteArrayInputStream bais = new ByteArrayInputStream(arr);
			BufferedImage ima = ImageIO.read(bais); 
			BufferedImage bufIma = new BufferedImage(ima.getWidth(null),ima.getHeight(null),BufferedImage.TYPE_INT_BGR);  
			//这里是关键部分  
	        Graphics2D g = bufIma.createGraphics();  
	        bufIma = g.getDeviceConfiguration().createCompatibleImage(ima.getWidth(null), ima.getHeight(null), Transparency.TRANSLUCENT);  
	        g = bufIma.createGraphics();  
	        g.setColor(Color.white);
	        g.fillRect(0, 0, ima.getWidth(null),ima.getHeight(null));//这里填充背景颜色
	        g.drawImage(ima, 0, 0, null);  
	        
	        BASE64Encoder encoder = new BASE64Encoder();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(bufIma, "png", baos);
	        byte[] bytes_out = baos.toByteArray();
	        signature = encoder.encodeBuffer(bytes_out).trim();
	        return signature;
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return null;
	}

	/**
	 * 获取登记台签字
	 * 
	 * @author fuxin
	 * @date 2017年12月28日 上午12:20:22
	 * @description TODO
	 * @param childVaccinaterecord
	 * @param maplist
	 * @param code
	 *
	 */
	@SuppressWarnings("restriction")
	private void updateDJTSign(ChildVaccinaterecord childVaccinaterecord, Map<String, Object> maplist,
			Map<String, Object> code) {
		logger.info("获取排号签字数据开始" + childVaccinaterecord.getNid().substring(0, 2) + "||"
				+ childVaccinaterecord.getVaccineid());
		Object signObjVaccid = CacheUtils.get(CacheUtils.SIGN_CACHE,
				childVaccinaterecord.getChildid() + "_" + childVaccinaterecord.getNid().substring(0, 2));
		logger.info("signObjVaccid-->" + (signObjVaccid == null));
		CacheUtils.remove(CacheUtils.SIGN_CACHE,
				childVaccinaterecord.getChildid() + "_" + childVaccinaterecord.getNid().substring(0, 2));
		Object signObj = CacheUtils.get(CacheUtils.SIGN_CACHE,
				childVaccinaterecord.getChildid() + "_" + childVaccinaterecord.getVaccineid());
		logger.info("signObj-->" + (signObj == null));
		CacheUtils.remove(CacheUtils.SIGN_CACHE,
				childVaccinaterecord.getChildid() + "_" + childVaccinaterecord.getVaccineid());
		if (signObj == null) {
			signObj = signObjVaccid;
		}
		logger.info("signObjFinal-->" + (signObj == null));
		if (signObj != null) {
			String signStr = (String) signObj;
			// 初始化记录数据
			childVaccinaterecord = childVaccinaterecordService.get(childVaccinaterecord);
			if (childVaccinaterecord != null) {
				// 判断签字是否存在
				// 打印签字信息
				code.put("sign", signStr);
				// base64 转换签字
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					byte[] sign = decoder.decodeBuffer(signStr);
					if (null != sign && sign.length > 0) {
						childVaccinaterecord.setSignatureData(sign);
						childVaccinaterecord.setStype(ChildVaccinaterecord.SIGNATURE_SOURCE_DJT);
						// 查询该记录签字是否存在
						int count = childVaccinaterecordService.querySignature(childVaccinaterecord);
						if (count == 0) {
							// 新增签字
							childVaccinaterecordService.insertSignatures(childVaccinaterecord);
						}
						// 修改签字状态
						childVaccinaterecord.setSignature(ChildVaccinaterecord.SIGNATURE_YES);
						childVaccinaterecordService.updateSignatures(childVaccinaterecord);
					}
				} catch (Exception e) {
					logger.error("微信签字base64转bytes失败", e.getMessage());
				}
				// 签字状态
			}
			maplist.put("success", true);
			logger.error("打印排号获取签字成功" + childVaccinaterecord.getNid().substring(0, 2) + "||"
					+ childVaccinaterecord.getVaccineid());
		} else {
			logger.error("打印排号获取签字失败" + childVaccinaterecord.getNid().substring(0, 2) + "||"
					+ childVaccinaterecord.getVaccineid());
		}
	}

	/**
	 * 获取微信签字，更新接种记录，打印小票时打印
	 * 
	 * @author zhouqj
	 * @date 2017年11月2日 上午10:22:29
	 * @description TODO
	 * @param childVaccinaterecord
	 * @param nid
	 * @param rid
	 * @param maplist
	 * @param code
	 * @throws IOException
	 *
	 */
	@SuppressWarnings("restriction")
	public void updateWxSign(ChildVaccinaterecord childVaccinaterecord, String nid, String rid,
			Map<String, Object> maplist, Map<String, Object> code) {
		List<String> list = new ArrayList<String>();
		// 获取微信签字 ,不取微信
		VacChildRemind vacRemind = remindService.getWxSignData(rid, false);

		// 判断结果集是否存在
		if (vacRemind != null && StringUtils.isNotBlank(vacRemind.getSignature())) {
			// 初始化记录数据
			childVaccinaterecord = childVaccinaterecordService.get(childVaccinaterecord);
			if (childVaccinaterecord != null) {
				// 判断签字是否存在
				// 打印签字信息
				code.put("sign", vacRemind.getSignature());
				// base64 转换签字
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					byte[] sign = decoder.decodeBuffer(vacRemind.getSignature());
					if (null != sign && sign.length > 0) {
						childVaccinaterecord.setSignatureData(sign);
						childVaccinaterecord.setStype(vacRemind.getStype());
						// 查询该记录签字是否存在
						int count = childVaccinaterecordService.querySignature(childVaccinaterecord);
						if (count == 0) {
							// 新增签字
							childVaccinaterecordService.insertSignatures(childVaccinaterecord);
						}
						// 修改签字状态
						childVaccinaterecord.setSignature(ChildVaccinaterecord.SIGNATURE_YES);
						childVaccinaterecordService.updateSignatures(childVaccinaterecord);

						// 成功获取签字id
						list.add(vacRemind.getVid());
					}
				} catch (Exception e) {
					logger.error("微信签字base64转bytes失败", e.getMessage());
				}
				// 签字状态
			}
			maplist.put("list", list);
			maplist.put("success", true);
		}
	}

	/**
	 * 选择预约日期，加载可预约疫苗
	 * 
	 * @author fuxin
	 * @date 2017年12月7日 下午9:30:30
	 * @description TODO
	 * @return
	 *
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "daypick")
	public @ResponseBody Map<String, Object> daypick(ChildVaccinaterecord childVaccinaterecord,
			HttpServletRequest request, HttpServletResponse response, @RequestParam("datef") String datef,
			@RequestParam(value = "childid", required = false, defaultValue = "") String childid,
			@RequestParam(value = "childcode", required = false, defaultValue = "") String childcode,
			@RequestParam(value = "nid", required = false, defaultValue = "") String nid,
			@RequestParam(value = "localCode", required = false, defaultValue = "") String localCode) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<ChildVaccinaterecord> tempRcvs = new ArrayList<ChildVaccinaterecord>();

		// 若排号界面有排号则，则做虚拟记录
		if (childVaccinaterecord != null && StringUtils.isNotBlank(childVaccinaterecord.getProductid())) {
			BsManageProduct p = bsManageProductService.get(childVaccinaterecord.getProductid());
			childVaccinaterecord.setVaccName(p.getName());
			childVaccinaterecord.setDosage(nid.substring(2, 3));
			childVaccinaterecord.setVaccinatedate(new Date());
			childVaccinaterecord = childVaccinaterecordService.insertion(childVaccinaterecord, nid, p);
			BsManageVaccine bsv = bsManageVaccineService.getWithModel(childVaccinaterecord.getVaccineid());
			childVaccinaterecord.setVaccine(bsv);
			tempRcvs.add(childVaccinaterecord);
		}

		// 加载儿童信息
		ChildBaseinfo baseinfo = new ChildBaseinfo();
		if (StringUtils.isNotBlank(childid)) {
			baseinfo = childBaseinfoService.get(childid);
		} else if (StringUtils.isNotBlank(childcode)) {
			baseinfo = childBaseinfoService.getByNo(childcode);
		}

		Date selectDate = DateUtils.parseDate(datef);
		// 计算月龄
		returnMap.put("mouthAge", DateUtils.getMouthAge(baseinfo.getBirthday(), selectDate));

		// 获取未完成的接种记录
		List<BsManageVaccinenum> vacctemp = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null, 0,
				true, "t.mouage", DateUtils.parseDate(datef), tempRcvs);
		// mnum转换成gnum

		List<BsManageVaccinenum> vaccs1 = new ArrayList<BsManageVaccinenum>();
		List<BsManageVaccinenum> vaccs2 = new ArrayList<BsManageVaccinenum>();
		for (BsManageVaccinenum num : vacctemp) {
			if (BsManageVaccinenum.type_1 == num.getType()) {
				vaccs1.add(num);
			} else {
				vaccs2.add(num);
			}
		}
		returnMap.put("vaccs1", vaccs1);
		returnMap.put("vaccs2", vaccs2);

		// 加载产品信息

		// 加载可用疫苗信息
		BsManageProduct tempProduct = new BsManageProduct();
		tempProduct.setOrderBy(" a.vaccineid, a.sellprice desc, a.batchno ");
		tempProduct.setShowAll(BsManageProduct.SHOWALL_YES);
		List<BsManageProduct> products = bsManageProductService.findQueueViewListApi(tempProduct);

		// 加载接种记录
		ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
		tempRcv.setChildid(baseinfo.getId());
		List<ChildVaccinaterecord> records = childVaccinaterecordService.findList(tempRcv);

		// 处理特殊情况
		for (BsManageVaccinenum num : vaccs2) {
			if ("54".equals(num.getGroup())) {
				for (ChildVaccinaterecord rcv : records) {
					if (StringUtils.isNotBlank(rcv.getNid()) && num.getGroup().equals(rcv.getNid().substring(0, 2))) {
						// 有接种记录
						for (int i = 0; i < products.size(); i++) {
							if (products.get(i).getVaccineid().equals(rcv.getVaccineid())
									&& !products.get(i).getCode().equals(rcv.getManufacturercode())) {
								products.remove(i--);
							}
						}
					}
				}
			}
		}
		returnMap.put("products", products);

		// 获取时段信息
		List<SysWorkingHours> workHours = sysWorkingHoursService.getByDate(selectDate, localCode);
		returnMap.put("workHours", workHours);

		return returnMap;
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月6日 下午3:07:38
	 * @description 再次打印排号
	 * @param childVaccinaterecord
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "stampAgain")
	public String stampAgain(ChildVaccinaterecord childVaccinaterecord, HttpServletRequest request,
			HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		// 根据儿童ID查询儿童信息
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		if (baseinfo != null) {
			baseinfo.setMouage(DateUtils.getMouthAge(baseinfo.getBirthday()));
		}
		Quene q = new Quene();
		q.setChildid(baseinfo.getChildcode());
		q.setVaccineid(childVaccinaterecord.getNid());
		q.setPid(childVaccinaterecord.getProductid());
		Map<String, String> map = queneService.insertQuene(q, childVaccinaterecord.getPayStatus(), StringUtils.EMPTY);
		// 日志
		logger.info("接到排号返回数据" + JsonMapper.toJsonString(map));
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		if ("200".equals(map.get("code"))) {
			Map<String, Object> code = new HashMap<String, Object>();
			// String code=null;
			code.put("no", map.get("msg"));
			code.put("name", baseinfo);
			code.put("vac", childVaccinaterecord.getVaccName());
			code.put("price", childVaccinaterecord.getPrice());
			code.put("time", sdf.format(new Date()));
			code.put("wait", queneService.waitNum(map.get("msg")));
			code.put("pin", childVaccinaterecord.getDosage());
			code.put("impart",
					bsManageVaccineService.getImpartByGnum(childVaccinaterecord.getVaccineid().substring(0, 2)));
			code.put("callBackUrl", request.getRequestURL().toString().replace(request.getRequestURI(), "")
					+ "/vaccinate" + Global.getFrontPath() + "/childBaseinfo/payCallBack?queueno=" + map.get("msg"));
			if (ChildVaccinaterecord.PAY_STATUS_YES.equals(childVaccinaterecord.getPayStatus())) {
				code.put("pay", "已付款");
			} else if (childVaccinaterecord.getPrice() > 0) {
				code.put("pay", "0");
			} else {
				code.put("pay", "免费");
			}
			childVaccinaterecordService.save(childVaccinaterecord);
			model.addAttribute("ts", "排号成功");
			model.addAttribute("code", JsonMapper.toJsonString(code));
			model.addAttribute("childVaccinaterecord", childVaccinaterecord);

		} else {
			model.addAttribute("ts", "排号失败，" + map.get("msg"));
			model.addAttribute("childVaccinaterecord", childVaccinaterecord);

		}
		model.addAttribute("childcode", baseinfo.getChildcode());
		return "modules/child_vaccinaterecord/childVaccinaterecordFormagain";
	}

	/**
	 * 导出应种统计的Excel格式数据
	 * @author Jack
	 * @date 2018年2月27日 下午4:17:39
	 * @description 
	 * @param baseinfo
	 * @param req
	 * @param resp
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(ExpChildBaseInfoVo baseinfo, HttpServletRequest req, HttpServletResponse resp,
			Model model, RedirectAttributes redirectAttributes) {
		// 赋默认值
		baseinfo.setDefaultValue();
		baseinfo.setVaccBCG();
		model.addAttribute("expChildBaseInfoVo", baseinfo);

		// 绑定前台社区数据
		List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("areas", areas);
		// 绑定前台疫苗名称数据
		List<BsManageVaccine> bsManageVaccineList = bsManageVaccineService.findModelList();
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);

		// 判断选择的日期格式是否正确
		if (baseinfo.getBirthbeginTime() == null) {
			// 开始生日为空,设置为当年第一天
			baseinfo.setBirthbeginTime(DateUtils.getYearFirst(Integer.valueOf(DateUtils.getYear())));
		}
		if (baseinfo.getBirthendTime() == null) {
			// 结束生日为空,设置为当天
			baseinfo.setBirthendTime(DateUtils.parseDate(DateUtils.getDate()));
		}
		if (!baseinfo.resetTime()) {
			addMessage(model, "你输入的出生日期格式不正确");
			return "modules/child_vaccinaterecord/dailyManagement";
		}
		if (baseinfo.getBirthbeginTime().after(baseinfo.getBirthendTime())) {
			addMessage(model, "你输入的出生日期起时间晚于止时间");
			return "modules/child_vaccinaterecord/dailyManagement";
		}

		// 判断社区选项是否为空,为空则提示选择社区
		if (baseinfo.getAreas() == null || baseinfo.getAreas().length == 0) {
			addMessage(model, "请选择您要查询的社区");
			return "modules/child_vaccinaterecord/dailyManagement";
		}else{
			if("total".equals(String.valueOf(baseinfo.getAreas()[0] ) ) ){
				//设置所有的社区项为选中项
				String[] areaArr = new String[areas.size()];
				if(areas != null){
					for(int i=0; i<areas.size(); i++){
						areaArr[i] = areas.get(i).getCode();
					}
				}
				baseinfo.setAreas(areaArr);
			}
			if("none".equals(String.valueOf(baseinfo.getAreas()[0] ) ) ){
				String[] areaArr = new String[1];
				baseinfo.setAreas(areaArr);
			}
			
		}

		// 现管单位为本地，更新officeinfo
		String officeCode = "";
		if (baseinfo.getOfficeinfo() != null) {
			if (ExpChildBaseInfoVo.OFFICEINFO_LOCAL.equals(baseinfo.getOfficeinfo())) {
				officeCode = OfficeService.getFirstOffice().getCode();
			}
		}

		// 获取免疫程序id
		List<BsManageVaccinenum> nums = bsManageVaccinenumService.findList(new BsManageVaccinenum());
		Map<Object, List<BsManageVaccinenum>> numMap = CommonUtils.getTreeDateByParam(BsManageVaccinenum.class, nums,
				"group");

		boolean flag = false;

		// 要查询的所有疫苗大类 + 针次 数字编码
		Set<BsManageVaccinenum> vaccNums = new HashSet<>();
		// 将勾选到的疫苗名称下的所有疫苗大类+针次编码 拼接成字符串,在sql中作为vacccode in()查询条件
		if (baseinfo.getVaccinates() != null) {
			for (Entry<Object, List<BsManageVaccinenum>> entry : numMap.entrySet()) {
				if (baseinfo.getVaccinates().contains(entry.getKey())) {
					flag = true;
					vaccNums.addAll(entry.getValue());
				}
			}
		}

		if (!flag) {
			// 未进入疫苗编码设置循环,原始数据库EXP_ROUTINEVACC6_1DETAIL表中暂无此项数据
			addMessage(model, "选定的疫苗无数据");
			return "modules/child_vaccinaterecord/dailyManagement";
		}

		// 根据逾期未种的超时月数计算统计的时间开始范围,对应EXP_ROUTINEVACC6_1Detail表中的YEAR_MONTH(VARCHAR2)字段
		Calendar c = Calendar.getInstance();
		int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH) + 1;

		// 定义查询截止的月份字符串
		String endSearchMonth = null;

		if (nowMonth > baseinfo.getOvmonth()) {
			endSearchMonth = nowYear + "-" + (nowMonth - baseinfo.getOvmonth());
		} else if (nowMonth == baseinfo.getOvmonth()) {
			endSearchMonth = (nowYear - 1) + "-12";
		} else {
			endSearchMonth = (nowYear - 1) + "-" + (12 - baseinfo.getOvmonth() + nowMonth);
		}

		// 初始化areaArr
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("endSearchMonth", endSearchMonth);
		searchMap.put("officeCode", officeCode);
		searchMap.put("vaccNums", vaccNums);
		searchMap.put("baseinfo", baseinfo);

		// 根据传入条件获取查询数据
		resultList = childVaccinaterecordService.getYQWZChildBaseInfoExcel(searchMap);

		//将查询出的list数据绑定到ExportExcelBean entity
		List<ExportExcelBean> list = new ArrayList<ExportExcelBean>();
		for(int i=0; i<resultList.size(); i++){
			ExportExcelBean exportExcelBean = new ExportExcelBean();
			for(String key : resultList.get(i).keySet()){
				switch (key) {
				case "CHILDCODE":
					exportExcelBean.setChildcode(String.valueOf(resultList.get(i).get("CHILDCODE") ) );
					break;
					
				case "CHILDNAME":
					exportExcelBean.setChildname(String.valueOf(resultList.get(i).get("CHILDNAME") ) );
					break;
					
				case "GENDER":
					exportExcelBean.setGender(String.valueOf(resultList.get(i).get("GENDER") ) );
					break;
					
				case "BIRTHDAY":
					exportExcelBean.setBirthDay(String.valueOf(resultList.get(i).get("BIRTHDAY")).substring(0, 10) );
					break;
					
				case "RESIDE":
					exportExcelBean.setReside(String.valueOf(resultList.get(i).get("RESIDE") ) );
					break;
					
				case "GUARDIANNAME":
					exportExcelBean.setGuardianname(String.valueOf(resultList.get(i).get("GUARDIANNAME") ) );
					break;
					
				case "GUARDIANMOBILE":
					exportExcelBean.setGuardianmobile(String.valueOf(resultList.get(i).get("GUARDIANMOBILE") ) );
					break;
					
				case "FATHER":
					exportExcelBean.setFatherName(String.valueOf(resultList.get(i).get("FATHER") ) );
					break;
					
				case "FATHERPHONE":
					exportExcelBean.setFatherphone(String.valueOf(resultList.get(i).get("FATHERPHONE") ) );
					break;
					
				case "ADDRESS":
					exportExcelBean.setAddress(String.valueOf(resultList.get(i).get("ADDRESS") ) );
					break;
					
				case "COMMNAME":
					exportExcelBean.setArea(String.valueOf(resultList.get(i).get("COMMNAME") ) );
					break;
					
				case "NAME":
					exportExcelBean.setvaccName(String.valueOf(resultList.get(i).get("NAME") ) );
					break;
					
				case "DOSAGE":
					exportExcelBean.setDosage(String.valueOf(resultList.get(i).get("DOSAGE") ) );
					break;
					
				default:
					break;
				}
			}
			list.add(exportExcelBean);
		}
		
		try {
			String fileName = "应种统计数据" + DateUtils.getDate("yyyyMMddHHmmss")+ ".xlsx";
			new ExportExcel("应种统计数据", ExportExcelBean.class).setDataList(list).write(resp, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出应种统计数据失败！失败信息：" + e.getMessage());
		}
		
		return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/dailyManagement";
	}

}
