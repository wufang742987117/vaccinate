/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.junl.common.WebUtil;
import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.BCV;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.BaseInfoRoot;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.aefi;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.chhe;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;
import com.thinkgem.jeesite.modules.department.service.SysVaccDepartmentService;
import com.thinkgem.jeesite.modules.enter.entity.SysEnterpriseInfo;
import com.thinkgem.jeesite.modules.enter.service.SysEnterpriseInfoService;
import com.thinkgem.jeesite.modules.holiday.entity.SysHoliday;
import com.thinkgem.jeesite.modules.holiday.service.SysHolidayService;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.refund.service.SysRefundService;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.OfficePreference;
import com.thinkgem.jeesite.modules.sys.entity.SimpleEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.entity.SysVaccRecord;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;
import com.thinkgem.jeesite.modules.vaccine.service.SysVaccRecordService;
import com.thinkgem.jeesite.modules.yiyuan.entity.SysBirthHospital;
import com.thinkgem.jeesite.modules.yiyuan.service.SysBirthHospitalService;

/**
 * 接种补录Controller
 * 
 * @author 王德地
 * @version 2017-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/child_vaccinaterecord/childVaccinaterecord")
public class ChildVaccinaterecordController extends BaseController {

	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private BsManageProductService bsManageProductService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private BsManageVaccinenumService bsManageVaccinenumService;
	@Autowired
	private QueneService queneService;
	@Autowired
	private SysVaccDepartmentService sysVaccDepartmentService;
	@Autowired
	private SysBirthHospitalService sysBirthHospitalService;
	@Autowired
	private SysEnterpriseInfoService sysEnterpriseInfoService;
	@Autowired
	private SysVaccRecordService sysVaccRecordService;
	@Autowired
	private SysRefundService sysRefundService;
	@Autowired
	private SysVaccRecordService vaccRecordService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SysHolidayService holidayService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private VacChildRemindService remindService;
	
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
	 * @date 2017年3月3日 下午3:06:37
	 * @description 进入修改页面 
	 * @param childVaccinaterecord
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = { "list", "" })
	public String list(ChildVaccinaterecord childVaccinaterecord, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//接种地点
		List<SysVaccDepartment> departmentlist =sysVaccDepartmentService.findList(new SysVaccDepartment());
		model.addAttribute("departmentlist", departmentlist);
		//出生医院
		List<SysBirthHospital> BirthHospitallist = sysBirthHospitalService.findList(new SysBirthHospital());
		model.addAttribute("BirthHospitallist", BirthHospitallist);
		//根据大类编码所有疫苗小类的名字
		List<BsManageVaccine> vaccs = bsManageVaccineService.findByGroup(childVaccinaterecord.getNid().substring(0,2));
//				List <BsManageProduct> productlist=bsManageProductService.name(childVaccinaterecord.getVaccineid().substring(0, 2));
		model.addAttribute("vacclist", vaccs);
		childVaccinaterecord.setVaccName(childVaccinaterecord.getVaccineid());
		//判断此疫苗是否属于五联疫苗
		if("50".equals(childVaccinaterecord.getNid().substring(0, 2))){
			childVaccinaterecord.setBigcode("50");
		}
		//获取所有的厂家
		List<SysEnterpriseInfo>EnterpriseInfoList=	sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
		model.addAttribute("EnterpriseInfoList", EnterpriseInfoList);
		
		//获取所有的批次
		BsManageProduct bsManageProduct = new BsManageProduct();
		bsManageProduct.setVaccineid(childVaccinaterecord.getVaccineid());
		Page<BsManageProduct> p = new Page<BsManageProduct>();
		// 根据批次排序
		p.setOrderBy("BATCHNO ASC");
		ArrayList<BsManageProduct> bsManageProductList = (ArrayList<BsManageProduct>) bsManageProductService.findPage(p, bsManageProduct).getList();
		model.addAttribute("bsManageProductList", bsManageProductList);
		
		//根据疫苗名称和批次获取疫苗厂家
		BsManageProduct tempProduct = new BsManageProduct();
		tempProduct.setVaccineid(childVaccinaterecord.getVaccineid());
		tempProduct.setBatchno(childVaccinaterecord.getBatch());
		List<BsManageProduct> productList = bsManageProductService.findList(tempProduct);
		model.addAttribute("productList", productList);
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		model.addAttribute("baseinfo", JsonMapper.toJsonString(baseinfo));
		return "modules/child_vaccinaterecord/checkIn";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:15:21
	 * @description 进入排号页面
	 * @param childVaccinaterecord
	 * @param numID
	 * @param childid
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "add")
	public String add(ChildVaccinaterecord childVaccinaterecord, HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("group") String group,
			@RequestParam("nid") String nid,
			@RequestParam("rid") String rid,
			@RequestParam(value="signFlag", required=false, defaultValue="false") Boolean signFlag) {
		List<User> users = systemService.findUser(new User());
		List<Map<String, String>> batchs = UserUtils.getBatchPreferences(users);
		model.addAttribute("defaultBatchs", JsonMapper.toJsonString(batchs));
		//保险状态0未买
		childVaccinaterecord.setInsurance("0");
		//异常反应0无
		childVaccinaterecord.setIseffect("0");
		//根据疫苗大类查询疫苗的所有小类，库存大于0
		OfficePreference option = OfficeService.getOfficeOption();
	    List <BsManageProduct> productlist=bsManageProductService.findByMnum(group,option.getObligate());
	    
	    //TODO:TEMP 流感只用2106
/*	    for(int i = 0; i < productlist.size(); i ++){
	    	if(productlist.get(i).getId().substring(0, 2).equals("21") 
	    			&& !productlist.get(i).getId().equals("2106")){
	    		productlist.remove(i--);
	    	}
	    }*/
	    //TODO:END
	    
	    model.addAttribute("obligate", option.getObligate());
		model.addAttribute("productlist", productlist);
		model.addAttribute("nid", nid);
		model.addAttribute("rid", rid);
		if(StringUtils.isNotBlank(nid) && nid.length() > 3){
			childVaccinaterecord.setVacctype(nid.substring(3,4));
		}
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		if(baseinfo == null){
			addMessage(model, "儿童信息异常");
		}else{
			model.addAttribute("mouthAge", childBaseinfoService.getMouAge(baseinfo.getBirthday()));
			model.addAttribute("mouth1", DateUtils.formatDate(baseinfo.getBirthday()));
			model.addAttribute("mouth2", DateUtils.getMouthAge(baseinfo.getBirthday()));
		}
		
		//计算是否存在活苗间隔
		boolean isLive = false;
		ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
		tempRcv.setChildid(baseinfo.getId());
		List<ChildVaccinaterecord> rcvs = childVaccinaterecordService.findList(tempRcv);
		ChildVaccinaterecord[] lastRcv = BsManageVaccinenumService.getLastRecord(rcvs);
		if(null != lastRcv[1] && DateUtils.getDay(new Date()).before(DateUtils.addMonths(DateUtils.getDay(lastRcv[1].getVaccinatedate()), 1)) ){
			isLive = true;
		}
		model.addAttribute("isLive", isLive);
		
		VacChildRemind vacChildRemind = new VacChildRemind();
		vacChildRemind.setChildcode(baseinfo.getChildcode());
		vacChildRemind.setOrderBy("REMIND_DATE");
		vacChildRemind.setStatus(VacChildRemind.STATUS_NORMAL);
		List<VacChildRemind> remindlist = remindService.findList(vacChildRemind);
		model.addAttribute("remindlist", JsonMapper.toJsonString(remindlist));
		
		if(baseinfo != null){
			baseinfo.setMouthAgeInt(DateUtils.subtractMonths(baseinfo.getBirthday(), new Date()));
		}
		model.addAttribute("baseinfo", JsonMapper.toJsonString(baseinfo));
		//获取节假日，展示在日历上
		model.addAttribute("weekdays", JsonMapper.toJsonString(holidayService.findAbleWeeks()));
		model.addAttribute("holidays", JsonMapper.toJsonString(holidayService.findAbleDays()));

		//小票打印功能是否开启
		model.addAttribute("receiptType", Global.getInstance().isReceiptOption());
		model.addAttribute("signFlag", signFlag);
		//调价权限
		UserUtils.clearCache();
		model.addAttribute("specialStatus", UserUtils.getUser().getSpecialStatus());
		
		//下一针时间
		List<BsManageVaccinenum> vaccs = new ArrayList<BsManageVaccinenum>();
		if(baseinfo.getMouthAgeInt() < 18){
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 2));
		}else if(baseinfo.getMouthAgeInt() < 36){
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 18));
		}else {
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 48));
		}
		
		List<Map<String, Object>> nextDate = new ArrayList<Map<String, Object>>();
		
		List<BsManageVaccine> vaccinates = bsManageVaccineService.findList(new BsManageVaccine());
		BsManageVaccine vaccine = null;
		for(BsManageVaccine bv : vaccinates){
			if(bv.getmNum().equals(nid.subSequence(0, 2))){
				vaccine = bv;
				break;
			}
		}
		
		//将当前排号放到接种记录中
		if(vaccine != null){
			ChildVaccinaterecord tempRecord = new ChildVaccinaterecord();
			tempRecord.setDosage(nid.substring(2, 3));
			tempRecord.setNid(nid);
			tempRecord.setVaccinatedate(new Date());
			tempRecord.setStatus(ChildVaccinaterecord.STATUS_YET);
			tempRecord.setVaccine(vaccine);
			rcvs.add(tempRecord);
		}
		
		for(BsManageVaccinenum va : vaccs){
			BsManageVaccinenum temp = bsManageVaccinenumService.getNextVaccDate(rcvs, baseinfo, va);
			if(temp.getVaccDate() != null){
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("vaccName", va.getName());
				tempMap.put("vaccDate", holidayService.nextWorkDay(va.getVaccDate()));
				nextDate.add(tempMap);
			}
		}
		
		//按照时间排序
		Comparator<Map<String, Object>> compar = new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				if(((Date) o1.get("vaccDate")).after((Date) o2.get("vaccDate"))){
					return 1;
				}
				if(((Date) o1.get("vaccDate")).before((Date) o2.get("vaccDate"))){
					return -1;
				}
				return 0;
			}
		};
		nextDate.sort(compar);
		if(nextDate.size() > 0){
			model.addAttribute("nextTime", DateUtils.formatDate((Date)nextDate.get(0).get("vaccDate")));
		}else{
			model.addAttribute("nextTime", DateUtils.formatDate(new Date()));
		}
		model.addAttribute("host", WebUtil.getHost(request));
		return "modules/child_vaccinaterecord/childVaccinaterecordForm";
	}
	
	/**
	 * 任意排号界面
	 * @author fuxin
	 * @date 2017年10月8日 上午10:53:02
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param group
	 * @param nid
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "addAnyWay")
	public String addAnyWay(ChildVaccinaterecord childVaccinaterecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<User> users = systemService.findUser(new User());
		List<Map<String, String>> batchs = UserUtils.getBatchPreferences(users);
		model.addAttribute("defaultBatchs", JsonMapper.toJsonString(batchs));
		//保险状态0未买
		childVaccinaterecord.setInsurance("0");
		//异常反应0无
		childVaccinaterecord.setIseffect("0");
		//根据疫苗大类查询疫苗的所有小类，库存大于0

		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		if(baseinfo == null){
			addMessage(model, "儿童信息异常");
			return "modules/child_vaccinaterecord/recordFormAnyWay";
		}else{
			model.addAttribute("mouthAge", childBaseinfoService.getMouAge(baseinfo.getBirthday()));
			model.addAttribute("mouth1", DateUtils.formatDate(baseinfo.getBirthday()));
			model.addAttribute("mouth2", DateUtils.getMouthAge(baseinfo.getBirthday()));
		}
		
		List<BsManageVaccine> vaccGroups = bsManageVaccineService.findGroupListAbleModel(BsManageProduct.SHOWALL_YES);
		model.addAttribute("vacclist", vaccGroups);
		
		
		VacChildRemind vacChildRemind = new VacChildRemind();
		vacChildRemind.setChildcode(baseinfo.getChildcode());
		vacChildRemind.setOrderBy("REMIND_DATE");
		vacChildRemind.setStatus(VacChildRemind.STATUS_NORMAL);
		List<VacChildRemind> remindlist = remindService.findList(vacChildRemind);
		model.addAttribute("remindlist", JsonMapper.toJsonString(remindlist));
		
		//下次接种时间
//
		List<BsManageVaccinenum> vaccs1 = new ArrayList<BsManageVaccinenum>();
		List<BsManageVaccinenum> vaccs2 = new ArrayList<BsManageVaccinenum>();

		model.addAttribute("vaccs1", vaccs1);
		model.addAttribute("vaccs2", vaccs2);
		if(baseinfo != null){
			baseinfo.setMouthAgeInt(DateUtils.subtractMonths(baseinfo.getBirthday(), new Date()));
		}
		model.addAttribute("baseinfo", JsonMapper.toJsonString(baseinfo));
		//获取节假日，展示在日历上
		SysHoliday tempHoliday = new SysHoliday();
		tempHoliday.setDateTimeAfter(new Date());
		List<SysHoliday> holidays = holidayService.findList(tempHoliday);
		for(int i = 0; i < holidays.size(); i ++){
			if(SysHoliday.DATETYPE_WEEK.equals(holidays.get(i))){
				holidays.remove(i--);
			}
		}
		SysHoliday tempweek = new SysHoliday();
		tempweek.setDateType("1");
		List<SysHoliday> weekdays = holidayService.findList(tempweek);	
		model.addAttribute("weekdays", JsonMapper.toJsonString(weekdays));
		model.addAttribute("holidays", JsonMapper.toJsonString(holidays));
		//小票打印功能是否开启
		model.addAttribute("receiptType", Global.getInstance().isReceiptOption());
		
		
		ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
		tempRcv.setChildid(baseinfo.getId());
		List<ChildVaccinaterecord> rcvs = childVaccinaterecordService.findList(tempRcv);
		//下一针时间
		List<BsManageVaccinenum> vaccs = new ArrayList<BsManageVaccinenum>();
		if(baseinfo.getMouthAgeInt() < 18){
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 2));
		}else if(baseinfo.getMouthAgeInt() < 36){
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 18));
		}else {
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 48));
		}
		
		List<Map<String, Object>> nextDate = new ArrayList<Map<String, Object>>();
		
		List<BsManageVaccine> vaccinates = bsManageVaccineService.findList(new BsManageVaccine());
		BsManageVaccine vaccine = null;
		for(BsManageVaccine bv : vaccinates){
			if(bv.getgNum().equals("18")){ 
				vaccine = bv;
				break;
			}
		}
		
		//将当前排号放到接种记录中
		if(vaccine != null){
			ChildVaccinaterecord tempRecord = new ChildVaccinaterecord();
			tempRecord.setDosage("5");
			tempRecord.setNid("185");
			tempRecord.setVaccinatedate(new Date());
			tempRecord.setStatus(ChildVaccinaterecord.STATUS_YET);
			tempRecord.setVaccine(vaccine);
			rcvs.add(tempRecord);
		}
		
		for(BsManageVaccinenum va : vaccs){
			BsManageVaccinenum temp = bsManageVaccinenumService.getNextVaccDate(rcvs, baseinfo, va);
			if(temp.getVaccDate() != null){
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("vaccName", va.getName());
				tempMap.put("vaccDate", holidayService.nextWorkDay(va.getVaccDate()));
				nextDate.add(tempMap);
			}
		}
		
		//按照时间排序
		Comparator<Map<String, Object>> compar = new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				if(((Date) o1.get("vaccDate")).after((Date) o2.get("vaccDate"))){
					return 1;
				}
				if(((Date) o1.get("vaccDate")).before((Date) o2.get("vaccDate"))){
					return 1;
				}
				return 0;
			}
		};
		nextDate.sort(compar);
		if(nextDate.size() > 0){
			model.addAttribute("nextTime", DateUtils.formatDate((Date)nextDate.get(0).get("vaccDate")));
		}else{
			model.addAttribute("nextTime", DateUtils.formatDate(new Date()));
		}
		model.addAttribute("host", WebUtil.getHost(request));
		return "modules/child_vaccinaterecord/recordFormAnyWay";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月6日 下午3:07:06
	 * @description 
	 *		进入再次排号页面
	 * @param childVaccinaterecord
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "again")
	public String again(ChildVaccinaterecord childVaccinaterecord,@RequestParam String childcode,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		//下次接种时间
//		model.addAttribute("preMonth", childVaccinaterecordService.preMonth());
		//小票打印功能是否开启
		model.addAttribute("receiptType", Global.getInstance().isReceiptOption());
		return "modules/child_vaccinaterecord/childVaccinaterecordFormagain";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:18:00
	 * @description 首次进入补录页面
	 * @param childVaccinaterecord
	 * @param numID
	 * @param childid
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "update")
	public String update(ChildVaccinaterecord childVaccinaterecord,@RequestParam String childid,@RequestParam String childcode,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		//获取所有可用补录的疫苗
		//小类
		List<BsManageVaccine> list=bsManageVaccineService.findList(new BsManageVaccine());
		//大类
		List<BsManageVaccine> Glist=bsManageVaccineService.findGroupList();
		List<BsManageVaccine> xlist = new ArrayList<>();
		List<SimpleEntity> vacclist = new ArrayList<>();
		for (BsManageVaccine bsManageVaccine : Glist) {
			for (BsManageVaccine vaccine : list) {
				if(bsManageVaccine.getgNum().equals(vaccine.getId().substring(0, 2))){
					xlist.add(vaccine);
				}
			}
			vacclist.add(new SimpleEntity(bsManageVaccine.getgName(), xlist));
			xlist = new ArrayList<>();
		}
		//获取接种单位
		Office o = OfficeService.getFirstOffice();
		String n = o.getName();
		model.addAttribute("Glist", Glist);
		model.addAttribute("n", n);
		model.addAttribute("childid", childid);
		model.addAttribute("vacclist", vacclist);
		model.addAttribute("childcode", childcode);
		model.addAttribute("size",vacclist.size()/2 );
		return "modules/child_vaccinaterecord/update";
	}
	
	/**
	 * 
	 * @author sen
	 * @date 2017年8月8日
	 * @description 首次进入补录页面
	 * @param childVaccinaterecord
	 * @param numID
	 * @param childid
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "addRecord")
	public String addRecord(ChildVaccinaterecord childVaccinaterecord, @RequestParam String childid,
			@RequestParam String childcode, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 获取所有可用补录的疫苗
		// 小类
		List<BsManageVaccine> list = bsManageVaccineService.findListChild(new BsManageVaccine());
		// 查询儿童所有已接种的记录
		ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
		tempRcv.setChildid(childid);
		tempRcv.setStatus(ChildVaccinaterecord.STATUS_YET);
		tempRcv.setOrderBy("a.vaccinatedate");
		List<ChildVaccinaterecord> childrecodeList = childVaccinaterecordService.findList(tempRcv);
/*		List<ChildVaccRecord> childrecodeList = childVaccinaterecordService.findListByChildId(childid);
		List<ChildVaccRecord> newList =new ArrayList<ChildVaccRecord>();
		for(int i=0;i<childrecodeList.size();i++){
			if(ChildVaccinaterecord.STATUS_YET.equals(childrecodeList.get(i).getStatus())){
				newList.add(childrecodeList.get(i));
			}
		}*/
		
		model.addAttribute("office", OfficeService.getFirstOffice());
		model.addAttribute("childid", childid);
		model.addAttribute("list", list);
		model.addAttribute("vacclist", JsonMapper.toJsonString(childrecodeList));
		model.addAttribute("childcode", childcode);
		return "modules/child_vaccinaterecord/addRecord";
	}
	
	/**
	 * 
	 * @author sen
	 * @date 2017年8月8日 
	 * @description 保存补录信息
	 * @param request
	 * @param response
	 * @param model
	 * @param valArr
	 * @return
	 *
	 */
	@RequestMapping(value = "saveRecord")
	@Transactional
	public @ResponseBody Map<String, Object> saveRecord(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String childid, String office, Model model, String valArr) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", "500");
		// AJAX传递的数组转换
		String json = HtmlUtils.htmlUnescape(valArr);
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = (List<Map<String, String>>) JsonMapper.fromJsonString(json, List.class);
		List<BsManageVaccine> vaccs = bsManageVaccineService.findList(new BsManageVaccine());
		ChildBaseinfo baseinfo = childBaseinfoService.get(childid);
		if(baseinfo == null){
			returnMap.put("msg", "儿童信息错误，请重试");
			logger.error("批量补录失败-->[" + childid + "]" + JsonMapper.toJsonString(returnMap));
			return returnMap;
		}
		for (Map<String, String> arg : list) {
			//获取疫苗信息
			BsManageVaccine vaccine = null;
			for(BsManageVaccine v : vaccs){
				if(v.getId().equals(arg.get("vaccineId"))){
					vaccine = v;
				}
			}
			if(vaccine == null){
				returnMap.put("msg", "疫苗信息错误，请重试");
				logger.error("批量补录失败-->[" + childid + "]" + JsonMapper.toJsonString(returnMap));
				return returnMap;
			}
			
			ChildVaccinaterecord childVaccinaterecord = new ChildVaccinaterecord();
			// 儿童编码
			childVaccinaterecord.setChildid(baseinfo.getId());
			childVaccinaterecord.setVaccineid(vaccine.getId());
			childVaccinaterecord.setVaccinatedate(DateUtils.parseDate(arg.get("date")));
			childVaccinaterecord.setDosage(String.valueOf(arg.get("pin")));
			if(!StringUtils.isNoneBlank(office)){
				 SysVaccDepartment department = new SysVaccDepartment();
				 department.setName(office);
				 List<SysVaccDepartment> departmentlist = sysVaccDepartmentService.findList(department);
				 if (departmentlist.size() > 0) { 
					 childVaccinaterecord.setOffice(departmentlist.get(0).getCode());
				 }
			}else{
				childVaccinaterecord.setOffice(office);
			}
			 
			childVaccinaterecord.setVaccName(vaccine.getName());
			// 接种部位
			childVaccinaterecord.setBodypart("");
			// 状态
			childVaccinaterecord.setStatus(ChildVaccinaterecord.STATUS_YET);
			// 无异常反应
			childVaccinaterecord.setIseffect("0");
			// 数据来源
			childVaccinaterecord.setSource(ChildVaccinaterecord.SOURCE_BL);
			// 疫苗接种类型
			childVaccinaterecord.setVacctype("1");
			// 批次信息是否为空
			childVaccinaterecord.setBatch("00000000");
			// 接种单位是否为空
			childVaccinaterecord.setOffice(office);
			// 价格是否为空
			childVaccinaterecord.setPrice(0);
			// 判断疫苗厂家是否为空
//			if (StringUtils.isNoneBlank(arg.get("manufacturer"))) {
//				childVaccinaterecord.setManufacturer(arg.get("manufacturer"));
//			}
			// 判断接种医生是否为空
//			if (StringUtils.isNoneBlank(arg.get("doctor"))) {
//				childVaccinaterecord.setDoctor(arg.get("doctor"));
//			}
			
			// 获取大类编码
			childVaccinaterecord.setNid(vaccine.getmNum() + arg.get("pin"));
			// 大类名字
			childVaccinaterecord.setVaccCode(vaccine.getgName());
			childVaccinaterecordService.save(childVaccinaterecord);
		}
		
		returnMap.put("code", "200");
		returnMap.put("msg", "补录成功");
		logger.error("批量补录失败-->[" + childid + "]" + JsonMapper.toJsonString(returnMap));
		return returnMap;
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月30日 上午9:05:13
	 * @description 
	 *		保存补录信息
	 * @param request
	 * @param response
	 * @param model
	 * @param valArr
	 * @return
	 *
	 */
	@RequestMapping(value = "blsave")
	public @ResponseBody
	String shortNote(HttpServletRequest request, HttpServletResponse response,@RequestParam String childid,@RequestParam String childcode,
			Model model, String valArr) {
		// AJAX传递的数组转换
		String json = HtmlUtils.htmlUnescape(valArr);
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = (List<Map<String, String>>) JsonMapper.fromJsonString(json, List.class);
		for (Map<String, String> arg : list) {
			ChildVaccinaterecord childVaccinaterecord=new ChildVaccinaterecord();
			//儿童编码
			childVaccinaterecord.setChildid(childid);
			childVaccinaterecord.setVaccineid(arg.get("vaccineid"));
			childVaccinaterecord.setVaccinatedate(DateUtils.parseDate(arg.get("vaccinatedate")));
			childVaccinaterecord.setVaccName(arg.get("vaccName"));
			//接种部位
			childVaccinaterecord.setBodypart(arg.get("bodypart"));
			//状态
			childVaccinaterecord.setStatus(ChildVaccinaterecord.STATUS_YET);
			//无异常反应
			childVaccinaterecord.setIseffect("0");
			//数据来源
			childVaccinaterecord.setSource(ChildVaccinaterecord.SOURCE_BL);
			//疫苗接种类型
			childVaccinaterecord.setVacctype("1");
			//判断批次信息是否为空
			if(StringUtils.isNoneBlank(arg.get("batch"))){
				childVaccinaterecord.setBatch(arg.get("batch"));
			}
			//判断接种单位是否为空
			if(StringUtils.isNoneBlank(arg.get("office"))){
				/*childVaccinaterecord.setOffice(arg.get("office"));*/
				SysVaccDepartment	department	=new SysVaccDepartment();
				department.setName(arg.get("office"));
				List<SysVaccDepartment> departmentlist =sysVaccDepartmentService.findList(department);
				if(departmentlist.size()>0){
					childVaccinaterecord.setOffice(departmentlist.get(0).getCode());
				}
			}
			//判断价格是否为空
			if(StringUtils.isNoneBlank(arg.get("price"))){
				childVaccinaterecord.setPrice(Integer.valueOf(arg.get("price")));
			}
			//判断疫苗厂家是否为空
			if(StringUtils.isNoneBlank(arg.get("manufacturer"))){
				childVaccinaterecord.setManufacturer(arg.get("manufacturer"));
			}
			//判断接种医生是否为空
			if(StringUtils.isNoneBlank(arg.get("doctor"))){
				childVaccinaterecord.setDoctor(arg.get("doctor"));
			}
			
			//获取大类编码
			String code=arg.get("vaccineid").substring(0, 2);
			ChildVaccinaterecord childVaccinaterecord1=new ChildVaccinaterecord();
			childVaccinaterecord1.setChildid(childid);
			childVaccinaterecord1.setNid(code);
			//获取针次和Nid
			String nid=null;
			int j=1;
			List<ChildVaccinaterecord> recordlist=childVaccinaterecordService.findList(childVaccinaterecord1);
			for (int i = 0; i < recordlist.size(); i++) {
				//生成nid,大类编码加针次
				 nid=code+String.valueOf(j);
				if(nid.equals(recordlist.get(i).getNid())){
					j=j+1;
					i=-1;
				}
			}
			if(nid==null){
				nid=code+String.valueOf(recordlist.size()+1);
			}
			// 针次
			childVaccinaterecord.setDosage(nid.substring(2));
			BsManageVaccine	bsManageVaccine=new BsManageVaccine();
			//大类编码
			bsManageVaccine.setgNum(code);
			//大类名字
			childVaccinaterecord.setVaccCode(bsManageVaccineService.findList(bsManageVaccine).get(0).getgName());
			childVaccinaterecord.setNid(nid);
			childVaccinaterecordService.save(childVaccinaterecord);
		}
		return childcode;
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:07:44
	 * @description 保存并修改状态
	 * @param request
	 * @param response
	 * @param childVaccinaterecord
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,
			HttpServletResponse response,
			ChildVaccinaterecord childVaccinaterecord, Model model,@RequestParam String nid,
			RedirectAttributes redirectAttributes) {
		//修改疫苗小类，库存加1（不是补录的0本地1微信2一体机3补录9五联拆解）
		if(!childVaccinaterecord.getSource().equals("3")){
			//childVaccinaterecord.getName()获取的是疫苗的小类ID，
			ChildVaccinaterecord record=childVaccinaterecordService.get(childVaccinaterecord.getId());
			if(StringUtils.isNotBlank(record.getProductid()) && StringUtils.isNotBlank(childVaccinaterecord.getProductid()) 
					&& !record.getProductid().equals(childVaccinaterecord.getProductid())){
				//原库存加1
				String pid=record.getProductid();
				BsManageProduct	pro=bsManageProductService.get(pid);
				if(pro.getSpec() > 1 ){
					if(record.getVaccinatedate() != null && DateUtils.getDay(record.getVaccinatedate()).compareTo(DateUtils.getDay(new Date())) == 0){
						if(pro.getRest()+1 >= pro.getSpec()){
							pro.setStorenum(pro.getStorenum()+1);
							pro.setRest(pro.getRest()+1-pro.getSpec());
						}else{
							pro.setRest(pro.getRest()+1);
						}
					}
				}else{
					pro.setStorenum(pro.getStorenum()+1);
				}
				bsManageProductService.save(pro);
				bsManageProductService.updateRest(pro);
				//库存减1
				String pid2=childVaccinaterecord.getProductid();
				BsManageProduct	pro2=bsManageProductService.get(pid2);
				if(pro2.getSpec() > 1 ){
					if(record.getVaccinatedate() != null && DateUtils.getDay(record.getVaccinatedate()).compareTo(DateUtils.getDay(new Date())) == 0){
						if(pro2.getRest()-1 < 0){
							pro2.setStorenum(pro2.getStorenum()-1);
							pro2.setRest(pro2.getSpec()-1);
						}else{
							pro2.setRest(pro2.getRest()-1);
						}
					}
				}else{
					pro2.setStorenum(pro2.getStorenum()-1);
				}
				bsManageProductService.save(pro2);
				bsManageProductService.updateRest(pro2);
				childVaccinaterecord.setManufacturer(pro2.getManufacturer());
				//修改接种记录
				SysVaccRecord Record=sysVaccRecordService.get(childVaccinaterecord.getId());
				Record.setPid(pid2);
				Record.setPname(pro2.getName());
				sysVaccRecordService.save(Record);
			}
		}
		
		//修改nid
		BsManageVaccine vaccinate = bsManageVaccineService.getWithModel(childVaccinaterecord.getVaccineid());
		childVaccinaterecord.setNid(vaccinate.getmNum() + childVaccinaterecord.getDosage());
		
		BsManageProduct	p=new BsManageProduct();
		childVaccinaterecord.setVaccineid(childVaccinaterecord.getVaccineid());
		childVaccinaterecord.setVaccName(vaccinate.getName());
		childVaccinaterecord=childVaccinaterecordService.insertion(childVaccinaterecord, nid, p);
		
		if(StringUtils.isNotBlank(childVaccinaterecord.getManufacturer())){
			SysEnterpriseInfo sysEnterpriseInfo=new SysEnterpriseInfo();
			//疫苗厂家编码
			sysEnterpriseInfo.setName(childVaccinaterecord.getManufacturer());
			List<SysEnterpriseInfo> list	=	sysEnterpriseInfoService.findList(sysEnterpriseInfo);
			if(list.size()>0){
				childVaccinaterecord.setManufacturercode(list.get(0).getCode());
			}
		}
		// 更改状态
		childVaccinaterecord.setStatus("1");
		//已付款
		childVaccinaterecord.setPayStatus(ChildVaccinaterecord.PAY_STATUS_YES);
		//接种单位
		List<SysVaccDepartment> departmentlist =sysVaccDepartmentService.findList(new SysVaccDepartment());
		model.addAttribute("departmentlist", departmentlist);
		//出生医院
		List<SysBirthHospital> BirthHospitallist = sysBirthHospitalService.findList(new SysBirthHospital());
		model.addAttribute("BirthHospitallist", BirthHospitallist);
		childVaccinaterecordService.save(childVaccinaterecord);
		model.addAttribute("arg", "1");
		model.addAttribute("childcode",childVaccinaterecord.getChildcode());
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		model.addAttribute("baseinfo", JsonMapper.toJsonString(baseinfo));
		return "modules/child_vaccinaterecord/checkIn";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月28日 上午11:48:13
	 * @description 
	 *		根据儿童ID查询儿童最近的一次接种时间，若等于今天则取第二个，第三个......
	 * @param request
	 * @param response
	 * @param childVaccinaterecord
	 * @param model
	 * @param valArr
	 * @return
	 *
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = "date")
	public @ResponseBody
	String date(HttpServletRequest request, HttpServletResponse response,ChildVaccinaterecord childVaccinaterecord,
			Model model) {
		List<ChildVaccinaterecord> list=childVaccinaterecordService.date(childVaccinaterecord);
		String data=DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		//获取最近的一次不是今天的接种记录时间
		String data1="4";
		if(list.size()>0){
			 data1=DateUtils.formatDate(list.get(0).getVaccinatedate(), "yyyy-MM-dd");
		}else{
			return "4";
		}
		if(data.equals(data1)){
			if(list.size()>1){
				 data1=DateUtils.formatDate(list.get(1).getVaccinatedate(), "yyyy-MM-dd");
			}else{
				return "4";	
			}
			 if(data.equals(data1)){
				 if(list.size()>2){
					 data1=DateUtils.formatDate(list.get(2).getVaccinatedate(), "yyyy-MM-dd");  
				 }else{
					 return "4";	 
				 }
				 if(data.equals(data1)){
					 data1=null;
				 }
			 }
			 
		}
		return data1;
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月11日 上午10:30:52
	 * @description 
	 *		展示签字内容
	 * @param request
	 * @param response
	 * @param childVaccinaterecord
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "signatureimg")
	public void signatureimg(HttpServletResponse response, ChildVaccinaterecord rec, Model model) {
		ServletOutputStream os = null;
		try {
			if(null != rec && StringUtils.isNotBlank(rec.getId())){
				byte[] stgn = rec.getSignatureList();
				response.getOutputStream().write(stgn);
				response.getOutputStream().flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != os){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月11日 上午10:30:52
	 * @description 
	 *		进入签字页面
	 * @param request
	 * @param response
	 * @param childVaccinaterecord
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "signature")
	public String signature(HttpServletResponse response, ChildVaccinaterecord rec, @RequestParam String type, Model model) {
		if(StringUtils.isBlank(rec.getId()) && type.equals("0")){
			return "modules/child_vaccinaterecord/checkIn";
		}else if(StringUtils.isBlank(rec.getId()) && type.equals("1")){
			return "modules/inoculate/inoculateHead";
		}
		String signature = childVaccinaterecordService.signature(rec.getId());
		if(StringUtils.isNoneBlank(signature)){
//			signature = signature.replaceAll("\r\n", "<br>&emsp;&emsp;");
			signature = signature.replaceAll("#unit#", OfficeService.getFirstOffice().getName());
			model.addAttribute("signature", signature);
		}
		//用户签字
		if("1".equals(rec.getSignature()) && "1".equals(rec.getStype())){
			try {
				model.addAttribute("signatureList", new String(rec.getSignatureList(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("rec", rec);
		model.addAttribute("type", type);
		return "modules/child_vaccinaterecord/signature";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月17日 下午7:49:13
	 * @description 
	 *	删除补录的记录
	 * @param response
	 * @param recordid
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "del")
	public @ResponseBody String del(@RequestParam String id,@RequestParam String code) {
		ChildVaccinaterecord	childVaccinaterecord=childVaccinaterecordService.get(id);
		if(childVaccinaterecord != null){
			try {
				return childVaccinaterecordService.deleteRecord(childVaccinaterecord);
			} catch (Exception e) {
				logger.error("删除接种记录失败" + e.getMessage());
				return "删除失败";
			}
		}else{
			return "未找到接种记录";
		}
		
	}
	
	/**
	 * 五联拆解
	 * @author wangdedi
	 * @date 2017年4月19日 下午5:43:42
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "disassemble")
	public String disassemble(ChildVaccinaterecord childVaccinaterecord, Model model) {
/*		List<BsManageVaccinenum> numlist=	bsManageVaccinenumService.disassemble();
		String uid=childVaccinaterecord.getId();
		String nid=childVaccinaterecord.getNid();
		String vaccName=childVaccinaterecord.getVaccName();
		String bigname=childVaccinaterecord.getVaccCode();
		String source=childVaccinaterecord.getSource();
		for (BsManageVaccinenum bsManageVaccinenum : numlist) {
			ChildVaccinaterecord record=new ChildVaccinaterecord();
			record=childVaccinaterecord;
			//更改nid
			record.setNid(bsManageVaccinenum.getGroup()+childVaccinaterecord.getDosage());
			//更改小类名
			record.setVaccName(bsManageVaccinenum.getName()+"（五联）");
			//更改疫苗大类
			record.setVaccCode(bsManageVaccinenum.getName());
			//更改数据来源
			record.setSource("4");
			//更改ID
			record.setId(null);
			record.setInocUnionCode(childVaccinaterecord.getVaccineid());
			childVaccinaterecordService.save(record);
		}
		//逻辑删除
		childVaccinaterecord.setId(uid);
		childVaccinaterecord.setNid(nid);
		childVaccinaterecord.setVaccName(vaccName);
		childVaccinaterecord.setVaccCode(bigname);
		childVaccinaterecord.setSource(source);*/
//		childVaccinaterecord.setStatus("9");
		childVaccinaterecord.setStatus("8");
		childVaccinaterecord.setInocUnionCode(null);
		childVaccinaterecordService.save(childVaccinaterecord);
		model.addAttribute("arg", "1");
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		String childcode=baseinfo.getChildcode();
		model.addAttribute("childcode", childcode);
		model.addAttribute("baseinfo", JsonMapper.toJsonString(baseinfo));
		return "modules/child_vaccinaterecord/checkIn";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月26日 上午11:05:19
	 * @description 
	 *		查看五联拆解的疫苗
	 * @param childVaccinaterecord
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "check")
	public String check(ChildVaccinaterecord childVaccinaterecord, Model model) {
		return "modules/child_vaccinaterecord/check";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月27日 上午10:49:56
	 * @description 
	 *		TODO
	 *http://127.0.0.1:8080/vaccinate/a/child_vaccinaterecord/childVaccinaterecord/testwdsl
	 */
	@RequestMapping("/testwdsl")
	@Transactional(readOnly = false)
	public @ResponseBody String testwdsl(ChildBaseinfo baseinfo){
		if(baseinfo == null || StringUtils.isBlank(baseinfo.getChildcode())){
			//参数异常
			return "参数异常";
		}
		String childcode = baseinfo.getChildcode();
		if(childcode.length()!=18){
			//参数异常
			return "参数异常";
		}
		// 判断数据库是否存在该儿童信息
		ChildBaseinfo childBaseinfo=new ChildBaseinfo();
		childBaseinfo.setChildcode(childcode);
		List<ChildBaseinfo> list = childBaseinfoService.findList(childBaseinfo);
		if(list.size()>0){
			//已存在个案
			logger.info( "已存在个案，进行更新操作");
		}
		String a = null;
		try {
			//获取省平台信息
			a=childVaccinaterecordService.downloadChildBaseInfo(childcode,childBaseinfo.getLocalCode());
		} catch (Exception e) {
			logger.error("下载省平台数据失败",e);
			a="下载省平台数据失败";
		}

		logger.info(a);
		//判断是否查询成功1表示失败，
		if(StringUtils.isNotBlank(a) && a.substring(0, 1).equals("1")){
			
		}else{
			//反序列化
			BaseInfoRoot root = (BaseInfoRoot) JsonMapper.fromJsonString(a, BaseInfoRoot.class);
			
			if(null != root && list.size() > 0){
				childBaseinfoService.clearBaseInfo(list.get(0));
			}
			
			//修改属性
			root.setArea(baseinfo.getArea());
			root.setSituation(baseinfo.getSituation());
			root.setReside(baseinfo.getReside());
			
			childBaseinfoService.insertRoot(root);
			for(BCV bc : root.getBcv()){
				childVaccinaterecordService.insertBcv(bc);
			}
			for(aefi f : root.getAefi()){
				childBaseinfoService.insertAefi(f);
			}
			for(chhe c : root.getChhe()){
				childBaseinfoService.insertChhe(c);
			}
			//("childcode","1"[1-下载并迁入，0-只下载])
			if("1".equals(baseinfo.getOfficeinfo())){
				//HttpClientReq.webServiceInvoke("download", new Object[]{root.getChildcode(),"0"}, logger);	
			}else{
				HttpClientReq.webServiceInvoke("downloaddept", new Object[]{root.getChildcode(),"1",childBaseinfo.getLocalCode()}, logger);
			}
			childVaccinaterecordService.clearRecord(root.getChildcode());
			//success
			a="200";
		}
		return a;
	}
	
	
	/**
	 * <strong> 跳转到单剂补录 </strong>
	 * @author fuxin
	 * @date 2017年7月17日 下午5:50:00
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/addVacc")
	public Object addVacc(ChildVaccinaterecord childVaccinaterecord, Model model) {
		//根据pid查询产品信息，附加默认值
		//所有有库存的模型大类
		List<BsManageVaccine> vaccs = bsManageVaccineService.findGroupListAbleModel();
		//所有疫苗种类
		List<BsManageVaccine> vaccs2 = bsManageVaccineService.findGroupList();
		List<BsManageVaccine> vaccs2Top = new ArrayList<BsManageVaccine>();
		Office office = officeService.get(UserUtils.getUser().getOffice());
		model.addAttribute("localcode", OfficeService.getFirstOfficeCode());
		for(int i = 0; i < vaccs2.size(); i++){
			//28,43,44
			if("28".equals(vaccs2.get(i).getgNum()) || "43".equals(vaccs2.get(i).getgNum()) ||"44".equals(vaccs2.get(i).getgNum())){
				vaccs2.remove(i--);
				continue;
			}
			if(null!=office.getVaccines()){
 				if(office.getVaccines().indexOf(vaccs2.get(i).getgNum())> -1){
 					vaccs2Top.add(vaccs2.get(i));
 					vaccs2.remove(i--);
 				}
			}
		}
		vaccs2.addAll(0, vaccs2Top);
// 			List<BsManageVaccinenum> vaccs = bsManageVaccinenumService.getVaccList(childVaccinaterecord.getChildcode(),0,null,1);
		model.addAttribute("vacclist", vaccs);
		model.addAttribute("vacclist2", vaccs2);
		if(StringUtils.isNotBlank(childVaccinaterecord.getChildcode())){
			childVaccinaterecord.setChildid(childBaseinfoService.getIdByCode(childVaccinaterecord.getChildcode()));
		}
		if(!StringUtils.isNotBlank(childVaccinaterecord.getChildid())){
			addMessage(model, "儿童信息不存在，请重试");
		}
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		model.addAttribute("baseinfo", JsonMapper.toJsonString(baseinfo));
		model.addAttribute("mouthAge", childBaseinfoService.getMouAge(childVaccinaterecord.getChildcode()));
		//加载厂商信息
		model.addAttribute("enterprise", sysEnterpriseInfoService.findList(new SysEnterpriseInfo()));
		//接种地点
		List<SysVaccDepartment> departmentlist =sysVaccDepartmentService.findList(new SysVaccDepartment());
		model.addAttribute("departmentlist", departmentlist);
		//加载未完成计划信息
		List<BsManageVaccinenum> notFinishNum = bsManageVaccinenumService.findNotFinishNum(childVaccinaterecord.getChildid());
		model.addAttribute("notFinishNum", JsonMapper.toJsonString(notFinishNum));
		return "modules/inoculate/addVacc";
	}
	
	/**
	 * <strong> 接种台单剂补录 </strong>
	 * @author
	 * @description 
	 * @param model
	 * @return
	 *
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping("/addRecordS")
	public String addVaccS(ChildVaccinaterecord childVaccinaterecord,@RequestParam String childid,Model model) {
		//根据pid查询产品信息，附加默认值
		//所有疫苗种类
		List<BsManageVaccine> vaccs2 = bsManageVaccineService.findGroupList();
		for(int i = 0; i < vaccs2.size(); i++){
			//28,43,44
			if("28".equals(vaccs2.get(i).getgNum()) || "43".equals(vaccs2.get(i).getgNum()) ||"44".equals(vaccs2.get(i).getgNum())){
				vaccs2.remove(i--);
				continue;
			}
		}
		model.addAttribute("vacclist2", vaccs2);
		if(!StringUtils.isNotBlank(childid)){
			addMessage(model, "儿童信息不存在，请重试");
		}
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		model.addAttribute("baseinfo", JsonMapper.toJsonString(baseinfo));
		//加载厂商信息
		model.addAttribute("enterprise", sysEnterpriseInfoService.findList(new SysEnterpriseInfo()));
		//接种地点
		List<SysVaccDepartment> departmentlist =sysVaccDepartmentService.findList(new SysVaccDepartment());
		model.addAttribute("departmentlist", departmentlist);
		//加载未完成计划信息
		List<BsManageVaccinenum> notFinishNum = bsManageVaccinenumService.findNotFinishNum(childVaccinaterecord.getChildid());
		model.addAttribute("notFinishNum", JsonMapper.toJsonString(notFinishNum));
		return "modules/child_vaccinaterecord/addRecordS";
	}
	
	/**
	 * <strong> 单剂补录 </strong>
	 * @author fuxin
	 * @date 2017年7月17日 下午5:49:34
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping("/saveAddVacc")
	@Transactional(readOnly=false)
	public String saveAddVacc(ChildVaccinaterecord childVaccinaterecord, Model model ,RedirectAttributes redirectAttributes, 
			@RequestParam(value="isnew", required=false, defaultValue="0")String isnew){
		//验证数据完整
		if(!StringUtils.isNotBlank(childVaccinaterecord.getChildid())){
			addMessage(redirectAttributes, "儿童信息错误，请关闭弹窗重试");
			return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/childVaccinaterecord/addVacc";
		}
		if(!StringUtils.isNotBlank(childVaccinaterecord.getVaccineid())){
			addMessage(redirectAttributes, "疫苗类型错误，请重新选择");
			return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/childVaccinaterecord/addVacc";
		}
		if(!StringUtils.isNotBlank(childVaccinaterecord.getBatch())){
			addMessage(redirectAttributes, "疫苗批号错误，请重新选择");
			return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/childVaccinaterecord/addVacc";
		}
		if(!StringUtils.isNotBlank(childVaccinaterecord.getProductid())){
			addMessage(redirectAttributes, "疫苗信息错误，请重新选择");
			return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/childVaccinaterecord/addVacc";
		}
		//查找
		BsManageProduct product = bsManageProductService.get(childVaccinaterecord.getProductid());
		//校验库存
		if(product.getStorenum() <= 0){
			addMessage(redirectAttributes, "疫苗库存为0，添加失败请重新选择");
			return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/childVaccinaterecord/addVacc";
		}
		ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
		BsManageVaccine vaccinate = bsManageVaccineService.getWithModel(childVaccinaterecord.getVaccineid());
		if(product != null && StringUtils.isNotBlank(product.getId()) 
				&& baseinfo!= null && StringUtils.isNotBlank(baseinfo.getId()) 
				&& vaccinate != null && StringUtils.isNotBlank(vaccinate.getId())){
			
			ChildVaccinaterecord rec=new ChildVaccinaterecord();
			//儿童编码
			rec.setChildid(baseinfo.getId());
			rec.setVaccineid(vaccinate.getId());
			Date vaccDate = childVaccinaterecord.getVaccinatedate();
			if(vaccDate == null){
				vaccDate = new Date();
			}else{
				try {
					vaccDate = DateUtils.parseDate(DateUtils.formatDate(vaccDate, "yyyy-MM-dd") + DateUtils.formatDate(new Date(), " HH:mm:ss"));
				} catch (Exception e) {
					logger.error("单剂录入，接种时间拼接失败，已使用选中时间");
				}
			}
			rec.setVaccinatedate(vaccDate);
			rec.setVaccName(vaccinate.getName());
			rec.updateProduct(product);
			//接种部位
			rec.setBodypart(childVaccinaterecord.getBodypart());
			//状态
			rec.setStatus(ChildVaccinaterecord.STATUS_YET);
			//无异常反应
			rec.setIseffect("0");
			//数据来源
			rec.setSource(ChildVaccinaterecord.SOURCE_DJT);
			//判断接种单位是否为空
			rec.setOffice(OfficeService.getFirstOfficeCode());
			//判断接种医生是否为空
			rec.setDoctor(UserUtils.getUser().getName());
			//接种类型
			rec.setVacctype(childVaccinaterecord.getVacctype());
			//已付款
			rec.setPayStatus(ChildVaccinaterecord.PAY_STATUS_YES);
			//获取模型大类编码
			String code=vaccinate.getmNum();
			ChildVaccinaterecord childVaccinaterecord1=new ChildVaccinaterecord();
			childVaccinaterecord1.setChildid(childVaccinaterecord.getChildid());
			childVaccinaterecord1.setNid(code+childVaccinaterecord.getNidVacctype());
			//获取针次和Nid
			String nid = null;
			int j=1;
			List<ChildVaccinaterecord> recordlist=childVaccinaterecordService.findList(childVaccinaterecord1);
			for (int i = 0; i < recordlist.size(); i++) {
				//生成nid,大类编码加针次
				 nid=code+String.valueOf(j);
				if(nid.equals(recordlist.get(i).getNid())){
					j=j+1;
					i=-1;
				}
			}
			if(nid==null){
				nid=code+String.valueOf(recordlist.size()+1);
			}
				// 针次
				rec.setDosage(nid.substring(2,3));
				//大类编码
				//大类名字
				rec.setVaccCode(vaccinate.getgName());
				rec.setNid(nid + rec.getNidVacctype());
				childVaccinaterecordService.save(rec);
				if(StringUtils.isNotBlank(rec.getId())){
					addMessage(model, "保存成功！！！");
					model.addAttribute("refresh", "true");
					model.addAttribute("childcode", baseinfo.getChildcode());
					//库存-1
					if(product.getSpec() > 1 ){
						if("1".equals(isnew)){
							//使用新苗
							product.setStorenum(product.getStorenum() - 1);
							product.setRest(product.getSpec() - 1);
						}else if ("0".equals(isnew)){
							//不使用新苗
							product.setRest(product.getRest() - 1);
						}
					}else{
						isnew = "1";
						product.setStorenum(product.getStorenum()-1);
					}
					bsManageProductService.save(product);
					bsManageProductService.updateRest(product);
					
					//增加疫苗使用记录
					SysVaccRecord svr = new SysVaccRecord(product.getId(), UserUtils.getUser().getId(), product.getVaccName(), UserUtils.getUser().getName());
					svr.setId(rec.getId());
					svr.setIsnew(isnew);
					svr.setStock(product.getStorenum());
					vaccRecordService.insert(svr);
					//记录最后一针完成时间,用于计算留观结束
					Global.getInstance().setLastFinishTime(new Date());
				}else{
					addMessage(redirectAttributes, "保存失败，请重试！！！");
					return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/childVaccinaterecord/addVacc";
				}
		}else{
			addMessage(redirectAttributes, "疫苗信息错误，请重新选择");
			return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/childVaccinaterecord/addVacc";
		}
		return "modules/inoculate/addVacc";
	}
	
	/**
	 * 登记台补录-异地补录，不减库存
	 * @author fuxin
	 * @date 2017年8月25日 下午10:02:02
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("/saveAddVacc3")
	@Transactional(readOnly=false)
	public @ResponseBody Map<String, String> saveAddVacc3(ChildVaccinaterecord childVaccinaterecord){
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("code", "500");
		try {
			ChildBaseinfo baseinfo = childBaseinfoService.get(childVaccinaterecord.getChildid());
			//验证数据完整
			if(!StringUtils.isNotBlank(childVaccinaterecord.getChildid()) || baseinfo==null){
				returnMap.put("msg","儿童信息错误，请关闭弹窗重试");
				return returnMap;
			}
			if(!StringUtils.isNotBlank(childVaccinaterecord.getVaccineid())){
				returnMap.put("msg","疫苗类型错误，请重新选择");
				return returnMap;
			}
			if(!StringUtils.isNotBlank(childVaccinaterecord.getDosage())){
				returnMap.put("msg","疫苗类型错误，请重新选择");
				return returnMap;
			}
			if(null == childVaccinaterecord.getVaccinatedate()){
				returnMap.put("msg","接种时间错误，请重新选择");
				return returnMap;
			}
			//验证疫苗数据
			BsManageVaccine vaccinate = bsManageVaccineService.getWithModel(childVaccinaterecord.getVaccineid());
			if(vaccinate== null || StringUtils.isBlank(vaccinate.getmNum())){
				returnMap.put("msg","疫苗信息错误，保存失败");
				return returnMap;
			}
			
			//验证是否已存在
			ChildVaccinaterecord temp = new ChildVaccinaterecord();
			temp.setChildid(childVaccinaterecord.getChildid());
			temp.setNid(vaccinate.getmNum() + childVaccinaterecord.getDosage() + childVaccinaterecord.getNidVacctype());
			temp.setDosage(childVaccinaterecord.getDosage());
//			List<ChildVaccinaterecord> list = childVaccinaterecordService.findList(temp);
//			if(list.size() > 0){
//				returnMap.put("msg","接种记录已存在，保存失败");
//				return returnMap;
//			}else{
				childVaccinaterecord.setSource(ChildVaccinaterecord.SOURCE_BL);
				childVaccinaterecord.setStatus(ChildVaccinaterecord.STATUS_YET);
				
				//给出一些默认值
				childVaccinaterecord.setNid(vaccinate.getmNum() + childVaccinaterecord.getDosage() + childVaccinaterecord.getNidVacctype());
				childVaccinaterecord.setVaccCode(vaccinate.getgName());
				childVaccinaterecord.setVaccName(vaccinate.getName());
				childVaccinaterecord.setInsurance(ChildVaccinaterecord.INSURANCE_NOT);
				childVaccinaterecord.setPayStatus(ChildVaccinaterecord.PAY_STATUS_YES);
//				childVaccinaterecord.setVacctype(ChildVaccinaterecord.VACCTYPE_NORMAL);
				childVaccinaterecord.setIseffect(ChildVaccinaterecord.ISEFFECT_NO);
				childVaccinaterecord.setSignature("0");					
				childVaccinaterecordService.save(childVaccinaterecord);		
				if(StringUtils.isNotBlank(childVaccinaterecord.getId())){
					returnMap.put("code", "200");
					returnMap.put("childcode", baseinfo.getChildcode());
				}
//			}
		} catch (Exception e) {
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}
	
	/**
	 * 从接种台删除接种记录
	 * @author fuxin
	 * @date 2017年8月6日 下午6:08:46
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @return
	 *
	 */
	@Transactional(readOnly = false)
	@RequestMapping(value="/deleteFromQuene", method=RequestMethod.DELETE)
	public @ResponseBody String deleteFromQuene(ChildVaccinaterecord childVaccinaterecord){
		if(StringUtils.isNotBlank(childVaccinaterecord.getId())){
			try {
				if(!ChildVaccinaterecord.SOURCE_BL.equals(childVaccinaterecord.getSource())){
					BsManageProduct pro = bsManageProductService.get(childVaccinaterecord.getProductid());
					if(null != pro && DateUtils.getDay(childVaccinaterecord.getVaccinatedate()).compareTo(DateUtils.getDay(new Date())) == 0){
						if(pro.getSpec() > 1 ){
							if(childVaccinaterecord.getVaccinatedate() != null){
								if(pro.getRest()+1 >= pro.getSpec()){
									pro.setStorenum(pro.getStorenum()+1);
									pro.setRest(pro.getRest()+1-pro.getSpec());
								}else{
									pro.setRest(pro.getRest()+1);
								}
							}
						}else{
							pro.setStorenum(pro.getStorenum()+1);
						}
						bsManageProductService.save(pro);
						bsManageProductService.updateRest(pro);
						//更新排号信息
						Quene q=new Quene();
						q.setChildid(childVaccinaterecord.getChildcode());
						q.setVaccineid(childVaccinaterecord.getNid());
						q.setStatus(Quene.STATUS_FINSH);
						List<Quene> qlist	=queneService.findList(q);
						if(qlist.size()>0){
							q=qlist.get(0);
							queneService.delete(q);
							
						}
					}
				}
				childVaccinaterecordService.delFlag(childVaccinaterecord);
				sysVaccRecordService.delete(new SysVaccRecord(childVaccinaterecord.getId()));
			} catch (Exception e) {
				logger.error("接种台删除接种记录失败",e);
				return "500";
			}
			return "200";
		}else{
			return "404";
		}
	}
	
	/**
	 * 接口调用查询接种记录
	 * @author fuxin
	 * @date 2017年8月16日 下午6:40:01
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @return
	 *
	 */
	@RequestMapping("/findListApi")
	public @ResponseBody List<ChildVaccinaterecord> findListApi(ChildVaccinaterecord childVaccinaterecord){
		if(childVaccinaterecord != null){
			return childVaccinaterecordService.findListWith4(childVaccinaterecord);
		}
		return new ArrayList<ChildVaccinaterecord>();
	}
	
	/**
	 * 根据接种大类计算下一针次
	 * @author fuxin
	 * @date 2017年12月8日 下午4:01:04
	 * @description 
	 *		TODO
	 * @param group
	 * @param childid
	 * @param vacctype
	 * @return
	 *
	 */
	@RequestMapping("/findLastPinByGroup")
	public @ResponseBody int findLastPinByGroup(
			@RequestParam(required=false,defaultValue="",value="group")String group ,
			@RequestParam(required=false,defaultValue="",value="childid")String childid,
			@RequestParam(required=false,defaultValue="",value="vacctype")String vacctype){
		if(StringUtils.isBlank(group) || StringUtils.isBlank(childid) ||StringUtils.isBlank(vacctype) ){
			return 1;
		}
		return childVaccinaterecordService.findLastPinByGroup(group, childid, vacctype);
	}
	
	/**
	 * 查询儿童接种记录api
	 * @author fuxin
	 * @date 2017年12月11日 上午11:07:36
	 * @description 
	 *		TODO
	 * @param childid
	 * @param childcode
	 * @return
	 *
	 */
	@RequestMapping("/findShowListApi")
	public @ResponseBody List<ChildVaccinaterecord> findListApi(
			@RequestParam(value="childid", required=false, defaultValue="")String childid, 
			@RequestParam(value="childcode", required=false, defaultValue="")String childcode){
		if(StringUtils.isBlank(childid) && StringUtils.isBlank(childcode)){
			return new ArrayList<ChildVaccinaterecord>();
		}
		if(StringUtils.isBlank(childid)){
			ChildBaseinfo baseinfo = childBaseinfoService.getByNo(childcode);
			childid = baseinfo.getId();
		}
		ChildVaccinaterecord record = new ChildVaccinaterecord();
		record.setChildid(childid);
		record.setStatus(ChildVaccinaterecord.STATUS_YET);
		record.setOrderBy(" A .STATUS, SUBSTR(A .VACCINEID, 0, 2), SUBSTR(A .NID, 0, 2) DESC,A.vacctype ,A .DOSAGE ");
		List<ChildVaccinaterecord> rcs = childVaccinaterecordService.findList(record);
		
		Map<String, String> dicts;
		for(ChildVaccinaterecord r : rcs){
			dicts = new HashMap<String, String>();
			dicts.put("vacctype", DictUtils.getDictLabel(r.getVacctype(), "vacctype", ""));
			dicts.put("bodypart", DictUtils.getDictLabel(r.getBodypart(),"position" , ""));
			dicts.put("paystatus", DictUtils.getDictLabel(r.getPayStatus(),"paystatus" , ""));
			r.setDicts(dicts);
		}
		
		String op = "first";
		int idx = 0;
		int size = 0;
		for(int i = 0; i < rcs.size(); i ++){
			if("first".equals(op)){
				idx = i;
				op = rcs.get(i).getVaccName();
			}
			if(!op.equals(rcs.get(i).getVaccName())){
				rcs.get(idx).setSize(size);
				idx = i;
				size = 0;
				op = rcs.get(i).getVaccName();
			}
			size ++;
		}
		if(size != 0){
			rcs.get(idx).setSize(size);
		}
		return rcs;
	}
	
}