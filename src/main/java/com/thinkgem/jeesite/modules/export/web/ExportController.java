package com.thinkgem.jeesite.modules.export.web;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.export.entity.ChirldFileStatistics;
import com.thinkgem.jeesite.modules.export.entity.ExpRoutinevacc61Detail;
import com.thinkgem.jeesite.modules.export.entity.Export;
import com.thinkgem.jeesite.modules.export.entity.ExportChildhelp;
import com.thinkgem.jeesite.modules.export.entity.ExportTeo;
import com.thinkgem.jeesite.modules.export.entity.HepatitisBSchedule;
import com.thinkgem.jeesite.modules.export.entity.MonthlyReportOnVaccConsumption;
import com.thinkgem.jeesite.modules.export.entity.WithinPlanVaccUse;
import com.thinkgem.jeesite.modules.export.service.ExpRoutinevacc6_1Service;
import com.thinkgem.jeesite.modules.export.service.Exp_vacc_7_2Service;
import com.thinkgem.jeesite.modules.export.service.Export6_2Service;
import com.thinkgem.jeesite.modules.export.service.ExportService;
import com.thinkgem.jeesite.modules.export.service.WithinPlanVaccUseService;
import com.thinkgem.jeesite.modules.holiday.service.SysHolidayService;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.GetTimeUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/export/export")
public class ExportController extends BaseController {
	
	@Autowired
	ExportService exportService;
	@Autowired
	ChildVaccinaterecordService recordService;
	@Autowired
	SysAreaService sysAreaService;
	@Autowired
	OfficeService officeService;
	@Autowired
	SysCommunityService sysCommunityService;
	@Autowired
	ChildBaseinfoService childBaseinfoService;
	@Autowired
	ExpRoutinevacc6_1Service expRoutinevacc6_1Service;
	@Autowired
	Export6_2Service export6_2Service;
	@Autowired
	Exp_vacc_7_2Service exp_vacc_7_2Service;
	@Autowired
	SysHolidayService holidayService;
	@Autowired
	WithinPlanVaccUseService withinPlanVaccUseService;
	
	/** 报表列表页 */
	@RequestMapping("exportlist")
	public String exportlist(Model model,String yearstr , String monthstr) {
		if(StringUtils.isNotBlank(yearstr)&&StringUtils.isNotBlank(monthstr)){
			model.addAttribute("yearstr",yearstr);
			model.addAttribute("monthstr",monthstr);
		}else{
			model.addAttribute("yearstr",DateUtils.formatDate(new Date(), "yyyy"));
			model.addAttribute("monthstr",DateUtils.formatDate(new Date(),"MM"));
		}
		return "modules/export/exportList";
	}
	
	/** bOPV免疫规划脊灰疫苗接种情况周报表 */
	@RequestMapping("bOPV_IPPVaccWeeklyReport")
	public String bOPV_IPPVaccWeeklyReport() {
		return "modules/export/useless/bOPV_IPPVaccWeeklyReport";
	}

	/** IPV试点地区脊灰疫苗接种日（周）报表 */
	@RequestMapping("iPV_PAPVaccDayOrWeekReport")
	public String iPV_PAPVaccDayOrWeekReport() {
		return "modules/export/useless/iPV_PAPVaccDayOrWeekReport";
	}

	/** 表八（生物制品使用情况统计年报表） */
	@RequestMapping("tableEightAnnualReportOTUOfBP")
	public String tableEightAnnualReportOTUOfBP() {
		return "modules/export/useless/tableEightAnnualReportOTUOfBP";
	}

	/** 表二（计划免疫工作人员统计年报表） */
	@RequestMapping("tableTwoAnnualReportOnPIS")
	public String tableTwoAnnualReportOnPIS() {
		return "modules/export/useless/tableTwoAnnualReportOnPIS";
	}

	/** 表九-1（冷链设备情况年报表（1）） */
	@RequestMapping("tableNine_1_AnnualReportOfCCE1")
	public String tableNine_1_AnnualReportOfCCE1() {
		return "modules/export/useless/tableNine_1_AnnualReportOfCCE1";
	}

	/** 表九-2（冷链设备情况年报表（2）） */
	@RequestMapping("tableNine_2_AnnualReportOfCCE2")
	public String tableNine_2_AnnualReportOfCCE2() {
		return "modules/export/useless/tableNine_2_AnnualReportOfCCE2";
	}

	/** 表九-3（冷链设备情况年报表（3）） */
	@RequestMapping("tableNine_3_AnnualReportOfCCE3")
	public String tableNine_3_AnnualReportOfCCE3() {
		return "modules/export/useless/tableNine_3_AnnualReportOfCCE3";
	}

	/** 表六（疾病发病死亡统计表） */
	@RequestMapping("tableSixDiseaseMorbidityAndMST")
	public String tableSixDiseaseMorbidityAndMST() {
		return "modules/export/useless/tableSixDiseaseMorbidityAndMST";
	}

	/** 表六-2（疾病发病死亡统计汇总表） */
	@RequestMapping("tableSix_2_SummaryOfDMS")
	public String tableSix_2_SummaryOfDMS() {
		return "modules/export/useless/tableSix_2_SummaryOfDMS";
	}

	/** 表七（基础免疫接种调查统计年报表） */
	@RequestMapping("tableSixBISSYearReport")
	public String tableSixBISSYearReport() {
		return "modules/export/useless/tableSixBISSYearReport";
	}

	/** 表三（计划免疫服务形式统计年报表） */
	@RequestMapping("tableThreeAnnualReportOnPIS")
	public String tableThreeAnnualReportOnPIS() {
		return "modules/export/useless/tableThreeAnnualReportOnPIS";
	}

	/** 表十-1（免疫成功率监测情况年报表（1）） */
	@RequestMapping("tableTen_1AnnualReportOnISR1")
	public String tableTen_1AnnualReportOnISR1() {
		return "modules/export/useless/tableTen_1AnnualReportOnISR1";
	}

	/** 表十-2（免疫成功率监测情况年报表（2）） */
	@RequestMapping("tableTen_2AnnualReportOnISR2")
	public String tableTen_2AnnualReportOnISR2() {
		return "modules/export/useless/tableTen_2AnnualReportOnISR2";
	}

	/** 表十二（扩大免疫疫苗接种情况年报表） */
	@RequestMapping("tableTwelveEPIVStatusOfTheReport")
	public String tableTwelveEPIVStatusOfTheReport() {
		return "modules/export/useless/tableTwelveEPIVStatusOfTheReport";
	}

	/** 表十三（计免保偿制入保人数统计表） */
	@RequestMapping("tableThirteenSOTNOInsuredPersons")
	public String tableThirteenSOTNOInsuredPersons() {
		return "modules/export/useless/tableThirteenSOTNOInsuredPersons";
	}

	/** 表十一（儿童预防年龄组人口统计表） */
	@RequestMapping("tableElevenCPAgeGroupDemographics")
	public String tableElevenCPAgeGroupDemographics() {
		return "modules/export/useless/tableElevenCPAgeGroupDemographics";
	}

	/** 表四（基础免疫接种情况统计年报表） */
	@RequestMapping("tableFourSAROnBasicImmunization")
	public String tableFourSAROnBasicImmunization() {
		return "modules/export/useless/tableFourSAROnBasicImmunization";
	}

	/** 表五（加强免疫接种情况统计年报表） */
	@RequestMapping("tableFiveAROnEnhancedImmunization")
	public String tableFiveAROnEnhancedImmunization() {
		return "modules/export/useless/tableFiveAROnEnhancedImmunization";
	}

	/** 表一（儿童计划免疫基础资料统计年报表） */
	@RequestMapping("tableOneAROnCPImmunizationBasicData")
	public String tableOneAROnCPImmunizationBasicData() {
		return "modules/export/useless/tableOneAROnCPImmunizationBasicData";
	}

	/** 短期居住儿童免疫规划疫苗接种情况统计汇总报表 */
	@RequestMapping("shortTermResidentCIPVSSummaryReport")
	public String shortTermResidentCIPVSSummaryReport() {
		return "modules/export/useless/shortTermResidentCIPVSSummaryReport";
	}

	/** 个案分析 */
	@RequestMapping("caseAnalysis")
	public String caseAnalysis() {
		return "modules/export/useless/caseAnalysis";
	}

	/** 建卡儿童地区分布情况 */
	@RequestMapping("gDistributionOfTheCardsChildren")
	public String gDistributionOfTheCardsChildren() {
		return "modules/export/useless/gDistributionOfTheCardsChildren";
	}

	/** 建卡建证统计表 */
	@RequestMapping("buildCardToBuildTheStatisticsTable")
	public String buildCardToBuildTheStatisticsTable() {
		return "modules/export/useless/buildCardToBuildTheStatisticsTable";
	}

	/** 接种单位运转情况 */
	@RequestMapping("inoculationUnitOperation")
	public String inoculationUnitOperation() {
		return "modules/export/useless/inoculationUnitOperation";
	}

	/** 接种门诊季度核查儿童接种卡、证统计 */
	@RequestMapping("vaccOQCCVVardCardStatistics")
	public String vaccOQCCVVardCardStatistics() {
		return "modules/export/useless/vaccOQCCVVardCardStatistics";
	}

	/** 五苗全程情况 */
	@RequestMapping("fiveSeedlingsFullSituation")
	public String fiveSeedlingsFullSituation() {
		return "modules/export/useless/fiveSeedlingsFullSituation";
	}

	/** 乙肝疫苗接种人次数统计表 */
	@RequestMapping("numberOfVaccForHBV")
	public String numberOfVaccForHBV() {
		return "modules/export/useless/numberOfVaccForHBV";
	}

	/** 疫苗消耗月报表 */
	@RequestMapping("monthlyReportOnVaccConsumption")
	public String monthlyReportOnVaccConsumption() {
		return "modules/export/useless/monthlyReportOnVaccConsumption";
	}
	
	/** 流动儿童月报 */
	@RequestMapping("mobileChildrenMonthly")
	public String mobileChildrenMonthly() {
		return "modules/export/mobileChildrenMonthly";
	}

	/** 免疫规划使用情况 */
	@RequestMapping("immunizationPlanningUsage")
	public String immunizationPlanningUsage() {
		return "modules/export/immunizationPlanningUsage";
	}
	
	//--------------------------------//
	/**
	 * 常规接种(3-1-1) 前端数据加载
	 * @author Jack
	 * @date 2017年11月1日 下午7:18:11
	 * @description 
	 * @param exportChildhelp entity
	 * @param model model
	 * @param yearstr 年份
	 * @param monthstr 月份
	 * @param liveProp 居住属性
	 * @param showType 显示类型
	 * @return
	 *
	 */
	@RequestMapping("routineVacc3_1_1")
	public String routineVacc3_1_1(ExportChildhelp exportChildhelp,Model model,String yearstr,String monthstr,String liveProp,String showType) {
//		exportService.initRoutine3_1_1(); //主动调用常规接种3_1_1报表数据初始化方法
		//3-1系列公用方法
		controllerHelp(model, exportChildhelp, yearstr, monthstr, liveProp, showType);
		//出生年月初始时间
		exportChildhelp.setStartDate(String.valueOf(Integer.parseInt(DateUtils.formatDate(new Date(), "yyyy"))-1)+"-01-01"+" "+"00:00:00");
		exportChildhelp.setEndDate(String.valueOf(Integer.parseInt(DateUtils.formatDate(new Date(), "yyyy"))-1)+"-12-31"+" "+"23:59:59");
		//查询去年出生人数 和总人数
		ExportChildhelp newAndAll = exportService.findNewAndAll311(exportChildhelp);
		model.addAttribute("newAndAll",newAndAll);
		//计算出生率
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		model.addAttribute("avgAll",numberFormat.format((float)newAndAll.getNumNew()/(float)newAndAll.getNumAll()*100)+"%");
		//统计数据
		List<HashMap<String, Object>> maps = exportService.selectVaccData311_(exportChildhelp);
		model.addAttribute("maps", maps);
		return "modules/export/routineVacc3_1_1";
	}

	/** 常规接种（3-1-2）  */
	@RequestMapping("routineVacc3_1_2")
	public String routineVacc3_1_2(Model model,String yearstr,String monthstr,Export export,
			ExportChildhelp exportChildhelp,String liveProp, String showType){
		//3-1系列公用方法
		controllerHelp(model, exportChildhelp, yearstr, monthstr, liveProp, showType);
		//统计数据单位总数
		List<HashMap<String, Object>> unitsize = exportService.selectVaccData312_(exportChildhelp);
		model.addAttribute("totalshequ",unitsize.size());
		//上报单位总数
		int count = 0;
		for(HashMap<String, Object> map : unitsize){
			if(map.get("REPORT_STATUS").equals("1")){
				count ++;
			}
		}
		model.addAttribute("count",count);
		//计算报告率
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		model.addAttribute("avgAll",numberFormat.format((float)count/(float)unitsize.size()*100)+"%");
		//统计数据
		List<List<HashMap<String, Object>>> returnlist = exportService.manageVaccData312_(exportChildhelp);
		model.addAttribute("returnlist",returnlist);
		return "modules/export/routineVacc3_1_2";
	}

	/** 常规接种（3-1-2）累计 */
	@RequestMapping("routineVacc3_1_2_GrandTotal")
	public String routineVacc3_1_2_GrandTotal(Model model,String yearstr,String monthstr,Export export,
			ExportChildhelp exportChildhelp,String liveProp,String showType) {
		//3-1系列公用方法
		controllerHelp(model, exportChildhelp, yearstr, monthstr, liveProp, showType);
		//统计数据单位总数
		List<HashMap<String, Object>> unitsize = exportService.selectVaccData312_(exportChildhelp);
		model.addAttribute("totalshequ",unitsize.size());
		//上报单位总数
		int count = 0;
		for(HashMap<String, Object> map : unitsize){
			if(map.get("REPORT_STATUS").equals("1")){
				count ++;
			}
		}
		model.addAttribute("count",count);
		//计算报告率
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		model.addAttribute("avgAll",numberFormat.format((float)count/(float)unitsize.size()*100)+"%");
		//统计数据
		List<List<HashMap<String, Object>>> returnlist = exportService.manageVaccData312_(exportChildhelp);
		model.addAttribute("returnlist",returnlist);
		return "modules/export/routineVacc3_1_2_GrandTotal";
	}

	/** 常规接种（3-1-3） */
	@RequestMapping("routineVacc3_1_3")
	public String routineVacc3_1_3(ExportChildhelp exportChildhelp,Model model,String yearstr,
			String monthstr,String liveProp ,String showType) {
		//3-1系列公用方法
		controllerHelp(model, exportChildhelp, yearstr, monthstr, liveProp, showType);
		//统计数据单位总数
		List<HashMap<String, Object>> unitsize = exportService.selectVaccData313_(exportChildhelp);
		model.addAttribute("totalshequ",unitsize.size());
		//上报单位总数
		int count = 0;
		for(HashMap<String, Object> map : unitsize){
			if(map.get("REPORT_STATUS").equals("1")){
				count ++;
			}
		}
		model.addAttribute("count",count);
		//计算报告率
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		model.addAttribute("avgAll",numberFormat.format((float)count/(float)unitsize.size()*100)+"%");
		//统计数据
		List<List<HashMap<String, Object>>> returnlist = exportService.manageVaccData313_(exportChildhelp);
		model.addAttribute("returnlist",returnlist);
		return "modules/export/routineVacc3_1_3";
	}

	/** 常规接种（3-1-3）累计 */
	@RequestMapping("routineVacc3_1_3_GrandTotal")
	public String routineVacc3_1_3_GrandTotal(ExportChildhelp exportChildhelp,Model model,String yearstr,
			String monthstr ,String liveProp,String showType) {
		//3-1系列公用方法
		controllerHelp(model, exportChildhelp, yearstr, monthstr, liveProp, showType);
		//统计数据单位总数
		List<HashMap<String, Object>> unitsize = exportService.selectVaccData313_(exportChildhelp);
		model.addAttribute("totalshequ",unitsize.size());
		//上报单位总数
		int count = 0;
		for(HashMap<String, Object> map : unitsize){
			if(map.get("REPORT_STATUS").equals("1")){
				count ++;
			}
		}
		model.addAttribute("count",count);
		//计算报告率
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		model.addAttribute("avgAll",numberFormat.format((float)count/(float)unitsize.size()*100)+"%");
		//统计数据
		List<List<HashMap<String, Object>>> returnlist = exportService.manageVaccData313_(exportChildhelp);
		model.addAttribute("returnlist",returnlist);
		return "modules/export/routineVacc3_1_3_GrandTotal";
	}

	/** 常规接种（3-1-4） */
	@RequestMapping("routineVacc3_1_4")
	public String routineVacc3_1_4(ExportChildhelp exportChildhelp,Model model,String yearstr,
			String monthstr ,String liveProp,String showType) {
		//3-1系列公用方法
		controllerHelp(model, exportChildhelp, yearstr, monthstr, liveProp, showType);
		//统计数据
		List<List<HashMap<String, Object>>> returnlist = exportService.manageVaccData314_(exportChildhelp);
		model.addAttribute("returnlist",returnlist);
		return "modules/export/routineVacc3_1_4";
	}

	/** 常规接种（3-1-5） */
	@RequestMapping("routineVacc3_1_5")
	public String routineVacc3_1_5(ExportChildhelp exportChildhelp,Model model,String yearstr,
			String monthstr ,String liveProp,String showType) {
		//3-1系列公用方法
		controllerHelp(model, exportChildhelp, yearstr, monthstr, liveProp, showType);
		//统计数据
		List<List<HashMap<String, Object>>>returnlist = exportService.manageVaccData315_(exportChildhelp);
		model.addAttribute("returnlist",returnlist);
		return "modules/export/routineVacc3_1_5";
	}
	
	/**
	 *进入 routineVacc6_1 的弹出框
	 * @author xuejinshan
	 * @date 2017年9月7日 下午5:19:58
	 * @description 
	 * @return
	 *
	 */
	
	@RequestMapping("openbox6_1")
	public String openboxfor6_1(ExportChildhelp exportChildhelp,Model model,String yearstr,
			String monthstr ,String liveProp,String showType) {
		return "modules/export/openbox6_1";
	}
	
	/**
	 * 常规免疫6-1表数据处理 Controller
	 * @author Jack
	 * @date 2017年10月12日 下午1:49:01
	 * @description 
	 * @param exportChildhelp
	 * @param model
	 * @param yearstr
	 * @param startmonthstr
	 * @param endmonthstr
	 * @param live
	 * @param showType
	 * @param typename
	 * @param vaccCode
	 * @param vaccname
	 * @return
	 *
	 */
	@RequestMapping("routineVacc6_1")
	public String routineVacc6_1(ExportChildhelp exportChildhelp,Model model,String yearstr,String startmonthstr ,
			String endmonthstr,String live,String showType,String typename,String vaccCode,String vaccname) {
		
		String localCode = OfficeService.getFirstOfficeCode();
 		if(StringUtils.isNotBlank(yearstr) && StringUtils.isNotBlank(endmonthstr) && StringUtils.isNotBlank(startmonthstr) ){
 			//处理和绑定时间
			exportChildhelp.setStartTime(GetTimeUtils.getFirstDayOfMonth(Integer.valueOf(yearstr),Integer.parseInt(startmonthstr)-1)+" "+"00:00:00");
			exportChildhelp.setEndTime(GetTimeUtils.getLastDayOfMonth(Integer.valueOf(yearstr),Integer.parseInt(endmonthstr)-1)+" "+"23:59:59");
			model.addAttribute("yearstr",  yearstr);
			model.addAttribute("startmonthstr", startmonthstr);
			model.addAttribute("endmonthstr", endmonthstr);
			model.addAttribute("live",live);
			
			//当传入的年、月、日不为空的时候,执行数据查询逻辑
			HashMap< String , String> map = new HashMap<String, String>();
			map.put("localCode", localCode);
			map.put("startTime",exportChildhelp.getStartTime());
			map.put("endTime",exportChildhelp.getEndTime());
			if(StringUtils.isNotBlank(showType)&& StringUtils.isNotBlank(live)){
				
				if(showType.equals("3")){
					//按居住属性
					List<HashMap<String, String>> live_maps = expRoutinevacc6_1Service.selectVaccData61_unit(map);
					if(live_maps.size() == 1){
						//将无本地和流动数据情况下的合计数据清空
						live_maps = new ArrayList<HashMap<String, String>>();
					}
					model.addAttribute("live_maps",JsonMapper.toJsonString(live_maps));
				}else if(showType.equals("0")&& live.equals("1")){
					//按居委名称-按居住属性
					List<HashMap<String, String>> shequ_live_maps = expRoutinevacc6_1Service.selectVaccData61_unitAndReside(map);
					model.addAttribute("shequ_live_maps",JsonMapper.toJsonString(shequ_live_maps));
				}else if(showType.equals("0")&&live.equals("0")){
					//按居委名称-不按居住属性
					List<HashMap<String, String>> maps = null;
					if(typename.equals("MCV") || typename.equals("RCV") || typename.equals("MumCV") || typename.equals("HepA")){
						maps = expRoutinevacc6_1Service.selectVaccSpecData61_unitNoReside(map); 
					}else{
						maps = expRoutinevacc6_1Service.selectVaccData61_unitNoReside(map); //常规类型
					}
					model.addAttribute("maps",JsonMapper.toJsonString(maps));
				}else{
					//按常规修订-不按居住属性   //按常规修订-按居住属性  //按特殊修订-不按居住属性  //按特殊修订-按居住属性
					List<HashMap<String, String>> all_maps = expRoutinevacc6_1Service.selectVaccData61_all(map);
					model.addAttribute("all_maps",JsonMapper.toJsonString(all_maps));
				}
			}else{
				return "modules/export/routineVacc6_1";
			}
			
		}else{
			//默认设置 开始月份:当年1月,结束月份:当月的上一个月份
			Integer endMonth = Integer.parseInt(DateUtils.getMonth());
			exportChildhelp.setStartTime((Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")))+"-"+(DateUtils.formatDate(new Date(), "MM"))+"-01"+" "+"00:00:00");
			exportChildhelp.setEndTime(GetTimeUtils.getLastDayOfMonth(Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")), endMonth-1)+" "+"23:59:59");
			model.addAttribute("startmonthstr", "01");
			if(endMonth == 1){
				model.addAttribute("yearstr", Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy"))-1 + "");
				model.addAttribute("endmonthstr", "12");
			}else{
				model.addAttribute("yearstr",  DateUtils.formatDate(new Date(), "yyyy"));
				model.addAttribute("endmonthstr", endMonth-1);
			}
			model.addAttribute("live","0");
		}
		model.addAttribute("liveProp",live);
		model.addAttribute("showType",showType);
		model.addAttribute("vaccCode",vaccCode);
		model.addAttribute("typename",typename);
		model.addAttribute("vaccname", vaccname);
		model.addAttribute("date", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		return "modules/export/routineVacc6_1";
	}
	
	/**
	 * 常规免疫接种情况6-1汇总表 数据初始化
	 * @author Jack
	 * @date 2017年10月24日 下午3:08:53
	 * @description 
	 *
	 */
	@RequestMapping("routineVacc6_1/initData")
	public @ResponseBody String initRoutine6_1Data(HttpServletRequest request){
		//distinct查询SYS_COMMUNITY表中的所有LOCALCODE,遍历执行所有站点数据生成
		List<HashMap<String, String>> communityList = expRoutinevacc6_1Service.selectCommunityListNetVersion();
		
		if(request.getParameter("time") != null){
			String time = request.getParameter("time");
			int year = Integer.valueOf(time.substring(0, 4));
			int month = Integer.valueOf(time.substring(4, time.length()));
			for(HashMap<String, String> map : communityList){
				String localCode = map.get("LOCALCODE");
//				if(month > 1){
					expRoutinevacc6_1Service.getDataByMonth(year + "", month + "", localCode);
//				}else{
//					expRoutinevacc6_1Service.getDataByMonth(year-1 + "", 12 + "", localCode);
//				}
			}
			return "Hello,常规免疫接种情况6-1汇总表 " + year +"年" + month + "月份数据初始化完成!";
		}else{
			return "请在请求中带入时间参数,参数形式为: XXX/routineVacc6_1/initData?time=XXXXXX(或XXXXX),\n说明:前4位为年,后面一位或两位为月";
		}
	}
	
	/**
	 * 重新结算某年指定月份范围内6-1数据
	 * @author Jack
	 * @date 2018年3月3日 下午2:56:11
	 * @description 
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("routineVacc6_1/reCountData")
	public @ResponseBody String reCountData(HttpServletRequest req){
		int year = Integer.valueOf(req.getParameter("year"));
		int startMonth = Integer.valueOf(req.getParameter("startMonth"));
		int endMonth = Integer.valueOf(req.getParameter("endMonth"));
		
		String localCode = OfficeService.getFirstOfficeCode();
		String msg = "";
		if(localCode != null && localCode != ""){
			expRoutinevacc6_1Service.clearExist61Data(localCode, year, startMonth, endMonth);
			//仅在判断localCode正确获取到的时候才重新生成,以此避免删除其他站点数据
			msg = expRoutinevacc6_1Service.reCount61Data(localCode, year, startMonth, endMonth);
			return JsonMapper.toJsonString(msg);
		}else{
			return JsonMapper.toJsonString("条件不足无法重新生成数据");
		}
		
	}
	
	/**
	 * 国家免疫规划疫苗常规接种情况报表6-1 处理
	 * @author Jack
	 * @date 2017年10月25日 下午3:41:32
	 * @description 
	 * @param exportChildhelp
	 * @param model
	 * @param yearstr
	 * @param monthstr
	 * @param liveProp
	 * @param showType
	 * @return
	 *
	 */
	@RequestMapping("nationalIPVRoutineVaccReport6_1")
	public String nationalIPVRoutineVaccReport6_1(ExportChildhelp exportChildhelp,Model model,String yearstr,
			String monthstr,String liveProp ,String showType) {
		//获取当前登录用户的LOCALCODE标识互联网版本数据
		String localCode = OfficeService.getFirstOfficeCode();
		//3-1系列公用方法
		controllerHelp(model, exportChildhelp, yearstr, monthstr, liveProp, showType);
		if(StringUtils.isNotBlank(liveProp)){
			HashMap< String , String> map = new HashMap<String, String>();
			map.put("startTime",exportChildhelp.getStartTime());
			map.put("endTime",exportChildhelp.getEndTime());
			if(liveProp.equals("1")){
				//居住属性为:本地
				map.put("reside", "1");
				
			}else if(liveProp.equals("2")){
				//居住属性为:流动
				map.put("reside", "2");
				
			}else if(liveProp.equals("3")){
				//居住属性为:临时
				map.put("reside", "3");
				
			}else{
				//合计,不区分居住属性
				map.put("reside","0");
			}
			map.put("localCode", localCode);
			map.put("reside",liveProp); //居住属性存入map中
			
			String officeCode = OfficeService.getFirstOfficeCode();
			String officeName = OfficeService.getFirstOffice().getName();
			List<List<HashMap<String, String>>> mapslist = expRoutinevacc6_1Service.manageNationalIPVRoutineVaccReport6_1(map);
			// 查询乙肝疫苗、卡介苗、脊灰疫苗、百白破疫苗、白破疫苗、麻风、麻腮风、A+C群流脑结合疫苗、A+C群流脑多糖疫苗、甲肝减毒活疫苗数据
			List<HashMap<String, String>> existLocalDataList = expRoutinevacc6_1Service.getExistLocalDataList(map); 
			List<HashMap<String, String>> existFlowDataList = expRoutinevacc6_1Service.getExistFlowDataList(map);
			List<HashMap<String, String>> existDataList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> tmpMap = new HashMap<String, String>();
			
			if(existLocalDataList != null && existLocalDataList.get(0) != null){
				for(HashMap<String, String> localMap : existLocalDataList){
					for(String localMapKey : localMap.keySet()){
						tmpMap.put(localMapKey, String.valueOf(localMap.get(localMapKey)));
					}
				}
			}
			if(existFlowDataList != null && existFlowDataList.get(0) != null){
				for(HashMap<String, String> flowMap : existFlowDataList){
					for(String flowMapKey : flowMap.keySet()){
						tmpMap.put(flowMapKey, String.valueOf(flowMap.get(flowMapKey)));
					}
				}
			}
			existDataList.add(tmpMap);
			
			//TODO: 麻腮、麻疹目前不接种,数值直接置零.乙脑灭活、甲肝灭活从DB接种记录表中查询统计值
			
			model.addAttribute("officeCode", officeCode);
			model.addAttribute("officeName", officeName);
			model.addAttribute("existDataList", existDataList);
			model.addAttribute("returnlist", mapslist);
		}
		return "modules/export/nationalIPVRoutineVaccReport6_1";
	}

	/**
	 * 进入 routineVacc6_2 的弹出框
	 * @author xuejinshan
	 * @date 2017年9月8日 下午5:39:02
	 * @description 
	 * @param exportChildhelp
	 * @param model
	 * @param yearstr
	 * @param monthstr
	 * @param liveProp
	 * @param showType
	 * @return
	 *
	 */
	@RequestMapping("openbox6_2")
	public String openboxfor6_2(ExportChildhelp exportChildhelp,Model model,String yearstr,
			String monthstr ,String liveProp,String showType) {
		return "modules/export/openbox6_2";
	}
	
	
	/**
	 * 常规免疫接种情况6-2汇总表
	 * @author Jack
	 * @date 2017年10月20日 下午4:28:01
	 * @description
	 * @param exportChildhelp
	 * @param model
	 * @param yearstr
	 * @param startmonthstr
	 * @param endmonthstr
	 * @param vaccCode
	 * @param vaccname
	 *
	 */
	@RequestMapping("routineVacc6_2")
	public String routineVacc6_2(ExportChildhelp exportChildhelp,Model model, String yearstr, String startmonthstr,
			String endmonthstr, String vaccCode, String vaccname) {
		export6_2Service.collectDataService(exportChildhelp, model, yearstr, startmonthstr, endmonthstr, vaccCode, vaccname);
		return "modules/export/routineVacc6_2";
	}
		
	/**
	 * 
	 * 统计7-2
	 * 
	 */
	@RequestMapping("Exp_vacc_7_2/initData")
	public @ResponseBody  String initVaccReport7_2Data(HttpServletRequest request){
		try {
			if(request.getParameter("time") != null){
				String time = request.getParameter("time");
				String year = time.substring(0, 4);
				String month = time.substring(4, 6);
				exp_vacc_7_2Service.getMonthData(year, month);  //主动调取数据统计方法,传入统计年月
				request = null;
				return  year +"年" + month + "月份数据初始化完成!";
			}else{
				return "请在请求中带入时间参数,参数形式为: ***/Exp_vacc_7_2/initData?time=XXXXXX,\n说明:前4位为年,后面两位为月";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "初始化失败";
	}
	
	/**
	 * 第二类进销存
	 * @return
	 *
	 */
	@RequestMapping(value = { "Exp_vacc_7_2"})
	public String Exp_VaccReport7_2(HttpServletRequest request, Model model,String yearstr,String monthstr) {
		exp_vacc_7_2Service.dataService(request, model, yearstr, monthstr);		
		return "modules/export/Exp_vacc_7_2";
	}
	
	/**
	 * 第二类疫苗接种情况报表6-2
	 * @author Jack
	 * @date 2017年10月23日 下午6:24:30
	 * @description 
	 * @param request
	 * @param export
	 * @param model
	 * @param yearstr
	 * @param monthstr
	 * @return
	 *
	 */
	@RequestMapping(value = { "typeTwoVaccReport6_2"})
	public String typeTwoVaccReport6_2(HttpServletRequest request, Export export, Model model,String yearstr,String monthstr) {
		export6_2Service.dataService(request, export, model, yearstr, monthstr);
		String officeCode = OfficeService.getFirstOfficeCode();
		model.addAttribute("officeCode", officeCode);
		return "modules/export/typeTwoVaccReport6_2";
	}
	
	/**
     * 3-1系列公用方法<br>绑定时间,地区,居住属性,显示类型等信息
     * @author xuejinshan
     * @date 2017年8月30日 下午10:26:58
     * @description 
     * 区域划分--->当前登录账号的接种点
     * 时间处理--->有时间使用用户选择时间,未指定时间默认上月
     */
    public void controllerHelp(Model model,ExportChildhelp exportChildhelp ,String yearstr,String monthstr,String liveProp,String showType){
    	//处理 省、市、区信息
    	String code = OfficeService.getFirstOfficeCode();//3406030301
    	if(StringUtils.isNotBlank(code)){
			String sheng = StringUtils.substring(code, 0, 2);
			String shi = StringUtils.substring(code, 2, 4);
			String qu = StringUtils.substring(code, 4, 6);
			model.addAttribute("sheng", sysAreaService.getAreaNameById(Integer.parseInt(sheng+"0000")).getName());
			model.addAttribute("shi", sysAreaService.getAreaNameById(Integer.parseInt(sheng+shi+"00")).getName());
			model.addAttribute("qu", sysAreaService.getAreaNameById(Integer.parseInt(sheng+shi+qu)).getName());
		}
		//处理和绑定时间
		if(StringUtils.isNotBlank(yearstr) && StringUtils.isNotBlank(monthstr)){
			if(StringUtils.isNotBlank(liveProp)){
				if(liveProp.equals("0")){
					exportChildhelp.setReside(null);
				}else{
					exportChildhelp.setReside(liveProp);
				}	
			}else{
				exportChildhelp.setReside("1");
			}
			if(StringUtils.isNotBlank(showType)){
				if(showType.equals("0")){
					exportChildhelp.setDisplay(null);
				}else{
					exportChildhelp.setDisplay(showType);
				}
			}
			exportChildhelp.setStartTime(GetTimeUtils.getFirstDayOfMonth(Integer.valueOf(yearstr),Integer.parseInt(monthstr)-1)+" "+"00:00:00");
			exportChildhelp.setEndTime(GetTimeUtils.getLastDayOfMonth(Integer.valueOf(yearstr),Integer.parseInt(monthstr)-1)+" "+"23:59:59");
			model.addAttribute("yearstr",  yearstr);
			model.addAttribute("monthstr", monthstr);
		//没有时间 则默认上月
		}else{
			Integer endMonth = Integer.parseInt(DateUtils.getMonth());
			exportChildhelp.setStartTime((Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")))+"-"+ (DateUtils.formatDate(new Date(), "MM"))+"-01"+" "+"00:00:00");
			exportChildhelp.setEndTime(GetTimeUtils.getLastDayOfMonth(Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")), Integer.valueOf(DateUtils.formatDate(new Date(), "MM"))-1)+" "+"23:59:59");
			if(endMonth == 1){
				model.addAttribute("yearstr", Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy"))-1 + "");
				model.addAttribute("monthstr", "12");
			}else{
				model.addAttribute("yearstr",  DateUtils.formatDate(new Date(), "yyyy"));
				model.addAttribute("monthstr", endMonth-1);
			}
		}
		model.addAttribute("showType",showType);
		model.addAttribute("liveProp",liveProp);
		model.addAttribute("username",UserUtils.getUser().getName());
		model.addAttribute("off", OfficeService.getFirstOffice().getName());
    	model.addAttribute("year",  DateUtils.formatDate(new Date(), "yyyy"));
		model.addAttribute("month", DateUtils.formatDate(new Date(), "MM"));
		model.addAttribute("date", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
    }

	/**
	 * 儿童建卡查询
	 * @author xuejinshan
	 * @date 2017年9月11日 下午6:49:40
	 * @description 
	 * @return
	 *
	 */
	@RequestMapping("findChirldBuildCard")
	public String findChirldBuildCard(Model model,String startBirthday,String endBirthday,String startCarday,String sex,
			String endCarday,String areaCodes,String hukouCodes,String resideCodes,String recordCodes,String showType){
		List<HashMap<String, String>> shequMaplist=exportService .findAllshequ();
		HashMap<String, Object>map= new HashMap<String, Object>();
		//设置日期参数
		if(StringUtils.isNotBlank(startBirthday)&& StringUtils.isNotBlank(endBirthday)){
    		map.put("startBirthday",startBirthday);
    		map.put("endBirthday", endBirthday);
    		model.addAttribute("startBirthday",DateUtils.parseDate(startBirthday));
    		model.addAttribute("endBirthday",DateUtils.parseDate(endBirthday));
    	}else{
    		map.put("startBirthday",null);
    		map.put("endBirthday", null);
    	}
    	if(StringUtils.isNotBlank(startCarday)&& StringUtils.isNotBlank(endCarday)){
    		map.put("startCarday",startCarday);
    		map.put("endCarday", endCarday);
    		model.addAttribute("startCarday", startCarday);
    		model.addAttribute("endCarday",endCarday);
    	}
		//设置户口参数条件
		if(StringUtils.isNotBlank(hukouCodes)){
			List<String> hukoulist=StringUtils.splitToList(hukouCodes, "/");
    		map.put("hukouCodes", hukouCodes);
    		map.put("hukoulist", hukoulist);
		}else{
			map.put("hukouCodes", null);
		}
		//居住类型code
    	if(StringUtils.isNotBlank(resideCodes)){
    		List<String> residelist=StringUtils.splitToList(resideCodes, "/");
    		map.put("resideCodes", resideCodes);
    		map.put("residelist", residelist);
    	}else{
    		map.put("areaCodes", null);
    	}
    	//在册情况code
    	if(StringUtils.isNotBlank(recordCodes)){
    		List<String> recordlist=StringUtils.splitToList(recordCodes, "/");
    		map.put("recordCodes", recordCodes);
    		map.put("recordlist", recordlist);
    	}else{
    		map.put("recordCodes", null);
    	}
       	//户口类别code
    	if(StringUtils.isNotBlank(recordCodes)){
    		List<String> hukoulist=StringUtils.splitToList(recordCodes, "/");
    		map.put("hukouCodes", hukouCodes);
    		map.put("hukoulist", hukoulist);
    	}else{
    		map.put("hukouCodes", null);
    	}
    	//设置性别条件
    	if(StringUtils.isNotBlank(sex)){
    		if(sex.equals("0")){
    			map.put("sex", null);
    		}else{
    			map.put("sex",sex);
    		}
    	}
    	if(StringUtils.isNotBlank(showType)){
    		if(showType.equals("1")){
    			List<HashMap<String, String >> card1_numMaps=exportService.findCard_num(map);
    			model.addAttribute("card1_numMaps",JsonMapper.toJsonString(card1_numMaps));
    		}else if(showType.equals("2")){
    			List<HashMap<String, String >> card2_numMaps=exportService.findCard_num2(map);
    			model.addAttribute("card2_numMaps",JsonMapper.toJsonString(card2_numMaps));
    		}
    	}
    	model.addAttribute("showType",showType);	
		model.addAttribute("shequMaplist",JsonMapper.toJsonString(shequMaplist));
		model.addAttribute("date", new Date());
		return "modules/export/findChirldBuildCard";
	}
	
	/**
     * 儿童建卡及时率查询
     * @author xuejinshan
     * @date 2017年9月12日 下午2:30:40
     * @description 
     * @return
     *
     */
    @RequestMapping("card_in_time")
    public String card_in_time(Model model,String startBirthday,String endBirthday,String startCarday,String endCarday,
    			String areaCodes,String in_time_days,String hege_days,String recordCodes){
    	HashMap<String, Object > map=new HashMap<String, Object>();
    	//社区类型code
    	if(StringUtils.isNotBlank(areaCodes)){
    		List<String> codelist=StringUtils.splitToList(areaCodes, "/");
    		map.put("areaCodes", areaCodes);
    		map.put("list", codelist);
    	}else{
    		map.put("areaCodes", areaCodes);
    		map.put("list",null);
    	}
    	//在册情况code
    	if(StringUtils.isNotBlank(recordCodes)){
    		List<String> codelist=StringUtils.splitToList(recordCodes, "/");
    		map.put("recordCodes", recordCodes);
    		map.put("codelist", codelist);
    	}else{
    		map.put("recordCodes", recordCodes);
    		map.put("codelist",null);
    	}
    	
    	if(StringUtils.isNotBlank(startBirthday)&& StringUtils.isNotBlank(endBirthday)){
    		map.put("startBirthday",startBirthday);
    		map.put("endBirthday", endBirthday);
    	}
    	if(StringUtils.isNotBlank(startCarday)&& StringUtils.isNotBlank(endCarday)){
    		map.put("startCarday",startCarday);
    		map.put("endCarday", endCarday);
    	}
    	if(StringUtils.isNotBlank(in_time_days )&& StringUtils.isNotBlank(hege_days)){
    		model.addAttribute("in_time_days",in_time_days);
    		model.addAttribute("hege_days",hege_days);
    	}else{
    		model.addAttribute("in_time_days","30");
    		model.addAttribute("hege_days","120");
    	}
    	
    	List<HashMap<String, String>> shequMaplist=exportService .findAllshequ();
		model.addAttribute("shequMaplist",JsonMapper.toJsonString(shequMaplist));
		model.addAttribute("date", new Date());
    	List<HashMap<String , String>> cardMaps=exportService.findCard_in_time(map);
    	model.addAttribute("cardMaps", JsonMapper.toJsonString(cardMaps));
    	return "modules/export/card_in_time";
    }
    
    /**
     * 个案信息完整性统计=》基本资料完整性
     * @author xuejinshan
     * @date 2017年9月13日 上午10:06:48
     * @description 
     * @return
     *
     */
    @RequestMapping("record_integrity")
    public String record_integrity(Model model,ChirldFileStatistics chirldFileStatistics,HttpServletRequest request,String wanzhenCodes){
    	List<HashMap<String, String>> shequMaplist=exportService .findAllshequ();
    	if(StringUtils.isNotBlank(chirldFileStatistics.getAreaCodes())){
    		List<String> areaList=StringUtils.splitToList(chirldFileStatistics.getAreaCodes(), "/");
    		chirldFileStatistics.setArealist(areaList);
    		chirldFileStatistics.setRecordlist(StringUtils.splitToList(chirldFileStatistics.getRecordCodes(), "/"));
        	chirldFileStatistics.setResidelist(StringUtils.splitToList(chirldFileStatistics.getResideCodes(), "/"));
    	}
    	if(StringUtils.isNotBlank(wanzhenCodes)){
    		List<String> namelist=StringUtils.splitToList(wanzhenCodes, "/");
    		model.addAttribute("namelist",JsonMapper.toJsonString(namelist));
    		List<HashMap<String , Object >> maps=exportService.findChirld_baseInfo_integrity(chirldFileStatistics);
        	List<HashMap<String , Object >> map_infos=exportService.findChirld_baseInfo(chirldFileStatistics);
        	model.addAttribute("map_infos",map_infos);
        	model.addAttribute("maps",JsonMapper.toJsonString(maps));
    	}
    	model.addAttribute("shequMaplist",JsonMapper.toJsonString(shequMaplist));
    	return "modules/export/record_integrity";
    }
    
    /**
     * 个案信息完整性统计=》接种资料完整性
     * @author xuejinshan
     * @date 2017年9月14日 上午10:44:56
     * @description 
     * @param model
     * @return
     *
     */
    @RequestMapping("record_integrity_num")
    public String record_integrity_num(Model model,ChirldFileStatistics chirldFileStatistics,String wanzhenCodes){
    	List<HashMap<String, String>> shequMaplist=exportService .findAllshequ();
    	if(StringUtils.isNotBlank(chirldFileStatistics.getAreaCodes())){
    		List<String> areaList=StringUtils.splitToList(chirldFileStatistics.getAreaCodes(), "/");
    		chirldFileStatistics.setArealist(areaList);
    		chirldFileStatistics.setRecordlist(StringUtils.splitToList(chirldFileStatistics.getRecordCodes(), "/"));
        	chirldFileStatistics.setResidelist(StringUtils.splitToList(chirldFileStatistics.getResideCodes(), "/"));
        	chirldFileStatistics.setVacclist(StringUtils.splitToList(chirldFileStatistics.getVaccCodes(), "/"));
    	}
    	if(StringUtils.isNotBlank(wanzhenCodes)){
    		List<String> namelist=StringUtils.splitToList(wanzhenCodes, "/");
    		model.addAttribute("namelist",JsonMapper.toJsonString(namelist));
    		List<HashMap<String, Object>> maps=exportService.findChirld_vaccnum_integrity(chirldFileStatistics);
    		List<HashMap<String, Object>> map_numinfo=exportService .findChirld_vaccnumInfo(chirldFileStatistics);
    		
        	model.addAttribute("map_infos",map_numinfo);
        	model.addAttribute("maps",JsonMapper.toJsonString(maps));
    	}
    	model.addAttribute("shequMaplist",JsonMapper.toJsonString(shequMaplist));
    	return "modules/export/record_integrity_num";
    }
    
    
    /** 疫苗消耗日报 */
	@RequestMapping("vaccConsumptionDaily")
	public String vaccConsumptionDaily(Export export, Model model) {
		Date[] date=recordService.date(new Date(),new Date());
		export.setBegintime(DateUtils.formatDate(date[0], "yyyy-MM-dd HH:mm:ss"));
		export.setEndtime(DateUtils.formatDate(date[1], "yyyy-MM-dd HH:mm:ss"));
		List<MonthlyReportOnVaccConsumption> List = exportService.monthlyReportOnVaccConsumption(export);
		model.addAttribute("List", List);
		model.addAttribute("date", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		return "modules/export/vaccConsumptionDaily";
	}

	/**
	 * 接种乙肝明细表
	 * @author wangdedi
	 * @date 2017年5月5日 下午4:32:05
	 * @description 
	 * @param request
	 * @param export
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = { "hepatitisBSchedule" })
	public String hepatitisBSchedule(HttpServletRequest request, Export export,  Model model) {
		Date[] date=recordService.date(DateUtils.parseDate(export.getBegintime()),DateUtils.parseDate(export.getEndtime()));
		if(date==null){
			model.addAttribute("arg", "0");
			model.addAttribute("export", export);
			return "modules/export/hepatitisBSchedule";	
		}
		export.setBegintime(DateUtils.formatDate(date[0], "yyyy-MM-dd HH:mm:ss"));
		export.setEndtime(DateUtils.formatDate(date[1], "yyyy-MM-dd HH:mm:ss"));
		List<HepatitisBSchedule> List = exportService.hepatitisBSchedule(export);
		List<HepatitisBSchedule> HepatitisBSchedulelist=new ArrayList<>();
		HepatitisBSchedule hepatitisBSchedule=new HepatitisBSchedule();
		String childcode=null;
		String dosage=null;
		for (int i = 0; i < List.size(); i++) {
			if(i==0){
				childcode=List.get(i).getChildcode();
				dosage="0";
			}
			if(childcode.equals(List.get(i).getChildcode())&&!dosage.equals(List.get(i).getDosage())){
				hepatitisBSchedule.setChildcode(List.get(i).getChildcode());
				hepatitisBSchedule.setChildname(List.get(i).getChildname());
				hepatitisBSchedule.setGender(List.get(i).getGender());
				hepatitisBSchedule.setBirthday(List.get(i).getBirthday());
				hepatitisBSchedule.setCreateDate(List.get(i).getCreateDate());
				switch (List.get(i).getDosage()) {
				case "1":
					hepatitisBSchedule.setVaccinatedateone(List.get(i).getVaccinatedate());
					hepatitisBSchedule.setEvaluateone(List.get(i).getEvaluateo());
					break;
				case "2":
					hepatitisBSchedule.setVaccinatedatetwo(List.get(i).getVaccinatedate());
					hepatitisBSchedule.setEvaluatetwo(List.get(i).getEvaluateo());
					break;
	
				default:
					hepatitisBSchedule.setVaccinatedatethree(List.get(i).getVaccinatedate());
					hepatitisBSchedule.setEvaluatethree(List.get(i).getEvaluateo());
					break;
				}
			}else{
				HepatitisBSchedulelist.add(hepatitisBSchedule);
				hepatitisBSchedule=new HepatitisBSchedule();
				childcode=List.get(i).getChildcode();
				i--;
			}
		}
		HepatitisBSchedulelist.add(hepatitisBSchedule);
		model.addAttribute("export", export);
		model.addAttribute("HepatitisBSchedulelist", HepatitisBSchedulelist);
		return "modules/export/hepatitisBSchedule";
	}
	
	/**
	 * 二类疫苗统计表
	 * @author zhouqj
	 * @date 2017年5月5日 下午5:55:53
	 * @description 
	 * @return
	 *
	 */
	@RequestMapping("twoCategoriesOfVaccStatistics")
	public String twoCategoriesOfVaccStatistics(HttpServletRequest request, ExportTeo export,  Model model) {
		int count1  = 0;
		int count2  = 0;
		int count3  = 0;
		List<ExportTeo> exportList = null;
		if(StringUtils.isNotBlank(export.getTimeYear()) && StringUtils.isNotBlank(export.getTimeQuarterly())){
			if(export.getTimeYear().length() == 4 && export.getTimeQuarterly().length() == 1){
				String startTime = null;
				String endTime = null;
				switch (export.getTimeQuarterly()) {
				case "1":
					startTime = export.getTimeYear() + "-01-01 00:00:00";
					endTime = export.getTimeYear() + "-03-31 23:59:59";
					break;
				case "2":
					startTime = export.getTimeYear() + "-04-01 00:00:00";
					endTime = export.getTimeYear() + "-06-30 23:59:59";
					break;
				case "3":
					startTime = export.getTimeYear() + "-07-01 00:00:00";
					endTime = export.getTimeYear() + "-09-30 23:59:59";
					break;
				case "4":
					startTime = export.getTimeYear() + "-10-01 00:00:00";
					endTime = export.getTimeYear() + "-12-31 23:59:59";
					break;
				default:
					startTime = export.getTimeYear() + "-01-01 00:00:00";
					endTime = export.getTimeYear() + "-03-31 23:59:59";
					break;
				}
				export.setBegintime(startTime);
				export.setEndtime(endTime);
				exportList = exportService.twoCategoriesOfVaccStatistics(export);
				for(ExportTeo e:exportList){
					count1  += Integer.valueOf(e.getNum());
					count2  += Integer.valueOf(e.getNum1());
					count3  += Integer.valueOf(e.getNum2());
				}
			}else{
				model.addAttribute("arg", "你输入的年或者季度有误！");
			}
		}
		model.addAttribute("exportList", exportList);
		model.addAttribute("count1", count1);
		model.addAttribute("count2", count2);
		model.addAttribute("count3", count3);
		model.addAttribute("export", export);
		model.addAttribute("date", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		return "modules/export/twoCategoriesOfVaccStatistics";
	}
	
	/**
	 * 根据点击选中的社区、疫苗及针次、应种数据,弹窗显示详细应种记录
	 * type="1" 应种未种儿童个案信息
	 * type="2" 应种儿童个案信息 
	 * type="3" 实种儿童个案信息
	 * type="4" 按社区统计儿童超过3个月仍然应种未种的信息 
	 * @author Jack
	 * @date 2018年2月1日 下午3:07:46
	 * @description 
	 * @param req 
	 * @param response 
	 * @param model 
	 *
	 */
	@RequestMapping("routineVacc6_1/getShouldInfo")
	public @ResponseBody Map<String, Object>  getShouldInfo(HttpServletRequest req, HttpServletResponse resp, Model model){
		//传入的社区、疫苗名称、列信息
		String community = req.getParameter("community");
		String vaccName = req.getParameter("vaccName");
		String type = req.getParameter("type");
		int pageNum = 0;
		if(req.getParameter("page") != null){
			pageNum = Integer.valueOf(req.getParameter("page"));
		}
		int pageSize = 0;
		if(req.getParameter("pageSize") != null){
			pageSize = Integer.valueOf(req.getParameter("pageSize"));
		}
		List<Map<String, String>> detailInfoList = new ArrayList<Map<String, String>>();
		Page<Map<String, String>> page = new Page<Map<String, String>>(req, resp); 
		
		int startRowNum = pageNum * pageSize - pageSize + 1;
		int endRowNum = pageNum * pageSize;
		int totalRowNum = 0;
		int totalPageNum = 0;
		
		int year = 0;
		int startMonth = 0;
		int endMonth = 0;
		
		if(req.getParameter("year") != null){
			year = Integer.valueOf(req.getParameter("year"));
		}
		if(req.getParameter("startMonth") != null){
			startMonth = Integer.valueOf(req.getParameter("startMonth"));
		}
		if(req.getParameter("endMonth") != null){
			endMonth = Integer.valueOf(req.getParameter("endMonth"));
		}
		
		String startTime = year + "-" + startMonth;
		String endTime = year + "-" + endMonth;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//设定各个疫苗名称对应疫苗大类和针次
		HashMap<String, String> vaccShouldMap = new HashMap<String, String>();
		vaccShouldMap.put("HepB1", "021");
		vaccShouldMap.put("及时数", "021");
		vaccShouldMap.put("HepB2", "022");
		vaccShouldMap.put("HepB3", "023");
		vaccShouldMap.put("BCG", "011");
		vaccShouldMap.put("PV1", "031");
		vaccShouldMap.put("PV2", "032");
		vaccShouldMap.put("PV3", "033");
		vaccShouldMap.put("PV4", "034");
		vaccShouldMap.put("DTP1", "041");
		vaccShouldMap.put("DTP2", "042");
		vaccShouldMap.put("DTP3", "043");
		vaccShouldMap.put("DTP4", "044");
		vaccShouldMap.put("DT", "061");
		vaccShouldMap.put("MR1", "141");
		vaccShouldMap.put("MR2", "");
		vaccShouldMap.put("MMR1", "121");
		vaccShouldMap.put("MMR2", "");
		vaccShouldMap.put("MM1", "");
		vaccShouldMap.put("MM2", "");
		vaccShouldMap.put("MV1", "");
		vaccShouldMap.put("MV2", "");
		vaccShouldMap.put("MenA1", "871");
		vaccShouldMap.put("MenA2", "872");
		vaccShouldMap.put("MenAC1", "161");
		vaccShouldMap.put("MenAC2", "162");
		vaccShouldMap.put("JE-l1", "841");
		vaccShouldMap.put("JE-l2", "842");
		vaccShouldMap.put("JE-i1", "831");
		vaccShouldMap.put("JE-i2", "832");
		vaccShouldMap.put("JE-i3", "833");
		vaccShouldMap.put("JE-i4", "834");
		vaccShouldMap.put("HepA-l1", "821");
		vaccShouldMap.put("HepA-i1", "811");
		vaccShouldMap.put("HepA-i2", "812");
		
		ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
		//输出page对象
		
		Map<String, Object> pageMap = new HashMap<>();
		
		//应种实种儿童个案详细信息数据接口
		if(type.equals("1") || type.equals("2") || type.equals("3")){
			
			//由疫苗名称转换后的疫苗编码:大类+针次
			String vaccNum = null;
			
			for(String key : vaccShouldMap.keySet()){
				if(vaccName.equals(key)){
					vaccNum = vaccShouldMap.get(key);
				}
			}
			
			detailInfoList = new ArrayList<Map<String, String>>();
			if(vaccNum != null){
				if(type.equals("1") || type.equals("3")){
					//type为1时获取应种未种儿童个案详情,type为3时获取实种儿童个案详情
					totalRowNum = expRoutinevacc6_1Service.queryDetailInfoCount(community, vaccNum, type, startTime, endTime);
					if(totalRowNum < pageSize){
						totalPageNum = 1;
					}else{
						totalPageNum = (int) Math.ceil(totalRowNum / pageSize);
					}
					detailInfoList = expRoutinevacc6_1Service.queryDetailInfo(community, vaccNum, type, startTime, endTime, startRowNum, endRowNum);
				}else{
					//type为2时获取按社区划分的所有应种儿童个案信息
					totalRowNum = expRoutinevacc6_1Service.queryShouldDetailInfoCount(community, vaccNum, startTime, endTime);
					if(totalRowNum < pageSize){
						totalPageNum = 1;
					}else{
						totalPageNum = (int) Math.ceil(totalRowNum / pageSize);
					}
					detailInfoList = expRoutinevacc6_1Service.queryShouldDetailInfo(community, vaccNum, startTime, endTime, startRowNum, endRowNum);
				}
			}
			
			//计算每个儿童当前点击的疫苗的下一针应种时间
			for(Map<String, String> map : detailInfoList){
				//儿童编码
				String childId = map.get("CHILDID");
				
				//该儿童接种最后一针活苗的时间字符串
				Date lastLiveVaccDate = expRoutinevacc6_1Service.getLastLiveVaccDate(childId);
				
				//根据儿童ID查询该儿童最后一针接种疫苗的接种时间
				Date lastVaccDate = expRoutinevacc6_1Service.getLastVaccDate(childId);
				
				//判断取最后一针活苗接种时间和最后一针苗接种时间两者中较接近现在时间的Date
				Calendar   calendar   =   new   GregorianCalendar(); 
				
				if(lastLiveVaccDate != null){
					//时间设置为最后一次接种活苗的时间
					calendar.setTime(lastLiveVaccDate); 
					//Date往后加一个月
					calendar.add(calendar.DAY_OF_MONTH, 1);
					//活苗接种的时间往后推一个月的时间
					lastLiveVaccDate=calendar.getTime();    
				}
				
				if(lastVaccDate != null){
					//时间设置为最后一次接种疫苗的时间
					calendar.setTime(lastVaccDate);
					//日期往后推14天,得到最后一针加上间隔14天的时间
					calendar.add(calendar.DATE,14);
					lastVaccDate = calendar.getTime();
				}
			    
			    @SuppressWarnings("unused")
				Date allowVaccDate = null;
			    
			    if(lastVaccDate == null && lastLiveVaccDate != null){
			    	allowVaccDate = lastLiveVaccDate;
			    }else if(lastLiveVaccDate == null && lastVaccDate != null){
			    	allowVaccDate = lastVaccDate;
			    }else if(lastLiveVaccDate == null && lastVaccDate == null){
			    	allowVaccDate = new Date();
			    }else{
			    	if(lastVaccDate.getTime() > lastLiveVaccDate.getTime()){
			    		allowVaccDate = lastVaccDate;
			    	}else{
			    		allowVaccDate = lastLiveVaccDate;
			    	}
			    }
			    
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date birthday = null;
				try {
					birthday = sdf.parse(String.valueOf(map.get("BIRTHDAY")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//计算预约时间
				Date remindDate = DateUtils.addMonths(birthday, 1);
				
				//判断如果可预约时间小于当前时间,则把当前时间起的下一个工作日算为下一针可预约时间
				if(new Date().after(remindDate)){
					remindDate = DateUtils.getDay(new Date());
				}
				remindDate = holidayService.nextWorkDay(remindDate);
				
				String remindTimeStr = sdf.format(remindDate);
				map.put("remindDate", remindTimeStr.toString());
				
			}
			resultMap.put("type", type);
			resultMap.put("status", "success");
			pageMap.put("data", detailInfoList);
			pageMap.put("totalPageNum", totalPageNum);
		}
		
		//截止至统计时超过3个月仍应种未种的儿童个案数据
		if(type.equals("4")){
			//设置内部查询条件 innerType值为1,即查询应种未种的信息
			String innerType = "1";
			//获取当前年月
			Calendar cal = Calendar.getInstance();
			int yearVar = cal.get(Calendar.YEAR);
			int monthVar = cal.get(Calendar.MONTH )+1;
			
			String timeStr = null;
			if(monthVar > 3){
				timeStr = yearVar + "-" + (monthVar-3);
			}else{
				timeStr = (yearVar-1) + "-" + (12 - 3 + monthVar);
			}
			
			//由疫苗名称转换后的疫苗编码:大类+针次
			String vaccNum = null;
			
			for(String key : vaccShouldMap.keySet()){
				if(vaccName.equals(key)){
					vaccNum = vaccShouldMap.get(key);
				}
			}
			
			if(vaccNum != null){
				totalRowNum = expRoutinevacc6_1Service.getyzwzDataCount(community, vaccNum, innerType, timeStr);
				if(totalRowNum < pageSize){
					totalPageNum = 1;
				}else{
					if(pageSize != 0){
						totalPageNum = (int) Math.ceil(totalRowNum / pageSize);
					}
				}
				detailInfoList = expRoutinevacc6_1Service.getyzwzData(community, vaccNum, innerType, timeStr, startRowNum, endRowNum);
			}
			
			//计算每个儿童当前点击的疫苗的下一针应种时间
			for(Map<String, String> map : detailInfoList){
				//儿童编码
				String childId = map.get("CHILDID");
				
				//该儿童接种最后一针活苗的时间字符串
				Date lastLiveVaccDate = expRoutinevacc6_1Service.getLastLiveVaccDate(childId);
				
				//根据儿童ID查询该儿童最后一针接种疫苗的接种时间
				Date lastVaccDate = expRoutinevacc6_1Service.getLastVaccDate(childId);
				
				//判断取最后一针活苗接种时间和最后一针苗接种时间两者中较接近现在时间的Date
				Calendar   calendar   =   new   GregorianCalendar(); 
				
				if(lastLiveVaccDate != null){
					//时间设置为最后一次接种活苗的时间
					calendar.setTime(lastLiveVaccDate); 
					//Date往后加一个月
					calendar.add(calendar.DAY_OF_MONTH, 1);
					//活苗接种的时间往后推一个月的时间
					lastLiveVaccDate=calendar.getTime();    
				}
				
				if(lastVaccDate != null){
					//时间设置为最后一次接种疫苗的时间
					calendar.setTime(lastVaccDate);
					//日期往后推14天,得到最后一针加上间隔14天的时间
					calendar.add(calendar.DATE,14);
					lastVaccDate = calendar.getTime();
				}
			    
			    @SuppressWarnings("unused")
				Date allowVaccDate = null;
			    
			    if(lastVaccDate == null && lastLiveVaccDate != null){
			    	allowVaccDate = lastLiveVaccDate;
			    }else if(lastLiveVaccDate == null && lastVaccDate != null){
			    	allowVaccDate = lastVaccDate;
			    }else if(lastLiveVaccDate == null && lastVaccDate == null){
			    	allowVaccDate = new Date();
			    }else{
			    	if(lastVaccDate.getTime() > lastLiveVaccDate.getTime()){
			    		allowVaccDate = lastVaccDate;
			    	}else{
			    		allowVaccDate = lastLiveVaccDate;
			    	}
			    }
			    
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date birthday = null;
				try {
					birthday = sdf.parse(String.valueOf(map.get("BIRTHDAY")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//计算预约时间
				Date remindDate = DateUtils.addMonths(birthday, 1);
				
				//判断如果可预约时间小于当前时间,则把当前时间起的下一个工作日算为下一针可预约时间
				if(new Date().after(remindDate)){
					remindDate = DateUtils.getDay(new Date());
				}
				remindDate = holidayService.nextWorkDay(remindDate);
				
				String remindTimeStr = sdf.format(remindDate);
				map.put("remindDate", remindTimeStr.toString());
				
			}
			resultMap.put("type", type);
			resultMap.put("totalPageNum", totalPageNum);
			resultMap.put("status", "success");
			resultMap.put("data", JsonMapper.toJsonString(detailInfoList));
		}
		page.setCount(totalRowNum);
		page.setPageNo(pageNum);
		page.setPageSize(pageSize);
		page.setList(detailInfoList);
		resultMap.put("page", page);
		return resultMap;
	}
	
	@RequestMapping("/upload/{methodNme}")
	public @ResponseBody String upload(@PathVariable(value="methodNme") String methodNme, 
			@RequestParam List<Object> data){
		logger.info("wsdl代理开始:methodNme=" + methodNme + ",data=" + JsonMapper.toJsonString(data));
		return HttpClientReq.webServiceInvoke(methodNme,data.toArray(), logger);
	}
	
	/**
	 * 返回调用页面
	 * @author chenming
	 * @date 2018年3月6日 下午3:36:19
	 * @description 
	 * @param withinPlanVaccUse
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("withinPlanVaccUse")
	public String withinPlanVaccUse(WithinPlanVaccUse withinPlanVaccUse, HttpServletRequest req, HttpServletResponse resp, Model model){
		
		return "modules/export/withinPlanVaccUse";
	}
	
	/**
	 * 计划免疫疫苗使用情况报表
	 * @author Jack
	 * @date 2018年3月2日 下午7:09:46
	 * @description 
	 * @return
	 *
	 */
	@RequestMapping("withinPlanVaccUseData")
	public @ResponseBody String withinPlanVaccUseData(WithinPlanVaccUse withinPlanVaccUse, HttpServletRequest req, HttpServletResponse resp, Model model){
		int year = Integer.valueOf(DateUtils.getYear());
		int month = Integer.valueOf(DateUtils.getMonth())-1; 
		if(req.getParameter("year") != null){
			year = Integer.valueOf(req.getParameter("year"));
		}
		if(req.getParameter("month") != null){
			month = Integer.valueOf(req.getParameter("month"));
		}
		
		String localCode = OfficeService.getFirstOfficeCode();
		String startTime = DateUtils.getFirstDayOfMonth(year, month-1) + " 00:00:00";
		String endTime = DateUtils.getLastDayOfMonth(year, month-1) + " 23:59:59";
		String lastMonthStartTime = DateUtils.getFirstDayOfMonth(year, month-2) + " 00:00:00";
		String lastMonthEndTime = DateUtils.getLastDayOfMonth(year, month-2) + " 23:59:59";
		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("localCode", localCode);
		
		String resultString = withinPlanVaccUseService.getWithinPlanVaccUseData(localCode, lastMonthStartTime, lastMonthEndTime, startTime, endTime);
		
		String userName = UserUtils.getUser().getName();
		String officeName = OfficeService.getFirstOffice().getName();
		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("status", "success");
		resultMap.put("data", resultString);
		resultMap.put("userName", userName);
		resultMap.put("officeName", officeName);
		
		return JsonMapper.toJsonString(resultMap);
	}
	
}
