/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.junl.common.CommonUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ExpChildBaseInfoVo;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.OverDueChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ExportExcelBean;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.shequ.entity.SysCommunity;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;

/**
 * 逾期未种
 * liumin
 * @version 2017-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/child_baseinfo/overduevacc")
//@RequestMapping(value = "/child_baseinfo/overduevacc")
public class OverduevaccController extends BaseController {

	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private SysCommunityService sysCommunityService;
	@Autowired
	private BsManageVaccinenumService bsManageVaccinenumService;
	
	@ModelAttribute
	public ChildBaseinfo get(@RequestParam(required = false) String id) {
		ChildBaseinfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = childBaseinfoService.get(id);
		}
		if (entity == null) {
			entity = new ChildBaseinfo();
		}
		return entity;
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:00:02
	 * @description 首次进入档案统计和查询
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:baseinfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(ChildBaseinfo childBaseinfo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Date d[] = childVaccinaterecordService.countmonth(12,childBaseinfo.getBirthbeginTime(), childBaseinfo.getBirthendTime());
		if (d != null) {
			childBaseinfo.setBirthbeginTime(d[0]);
			childBaseinfo.setBirthendTime(d[1]);
			childBaseinfo.setVaccinates("01,02,03,04,06,12,14,17,87,84,82");
			childBaseinfo.setResides("1");
			childBaseinfo.setSituations("1");
//			if(StringUtils.isNoneBlank(childBaseinfo.getOfficeinfo()) &&"1".equals(childBaseinfo.getOfficeinfo())){
//				childBaseinfo.setOfficeinfo(OfficeService.getFirstOffice().getCode());
//			}
//			Page<ChildBaseinfo> page = new Page<ChildBaseinfo>(request,response);
//			// 根据时间排序
//			page.setOrderBy("A.createdate ASC");
//			page = childBaseinfoService.findOD(page, childBaseinfo);
//			if (page.getList().size() > 0) {
//				ArrayList<ChildBaseinfo> childBaseinfolist = (ArrayList<ChildBaseinfo>) page.getList();
//				int checkEx=childBaseinfo.getCheckEx();
//				for (int i=0;i<childBaseinfolist.size();i++) {
//					if(checkEx==1 && !bsManageVaccinenumService.checkVacc(childBaseinfolist.get(i))){
//						childBaseinfolist.remove(i--);
//						continue;
//					}
//					// 转换地址信息
//					//childBaseinfo2 = childBaseinfoService.updateAddr(childBaseinfo2);
//					childBaseinfoService.convert(childBaseinfolist.get(i));
//					page.setList(childBaseinfolist);
//				}
//			}
			
//			model.addAttribute("page", page);
		}
		
		//赋默认值
		ExpChildBaseInfoVo baseinfo = new ExpChildBaseInfoVo();
		baseinfo.setDefaultValue();
		baseinfo.setVaccAll();
		model.addAttribute("expChildBaseInfoVo", baseinfo);
		
		//绑定前台社区数据
		List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("areas", areas);
		// 绑定前台疫苗名称数据
		ArrayList<BsManageVaccine> bsManageVaccineList = (ArrayList<BsManageVaccine>) bsManageVaccineService.findModelList();
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);
//		List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
//		model.addAttribute("areas", areas);
		
		return "modules/child_baseinfo/overduevacc";
	}
	
	/**
	 * 导出Excel格式逾期未种数据
	 * @author Jack
	 * @date 2018年2月27日 上午10:32:56
	 * @description 
	 * @return
	 *
	 */
	@RequestMapping(value="export")
	public String exportExcel(ExpChildBaseInfoVo baseinfo, HttpServletRequest req, HttpServletResponse resp, 
			Model model, RedirectAttributes redirectAttributes){
		//赋默认值
		baseinfo.setDefaultValue();
		baseinfo.setVaccAll();
		model.addAttribute("expChildBaseInfoVo", baseinfo);
		//绑定前台社区数据
		List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("areas", areas);
		// 绑定前台疫苗名称数据
		List<BsManageVaccine> bsManageVaccineList = bsManageVaccineService.findModelList();
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);

		//判断选择的日期格式是否正确
		if(baseinfo.getBirthbeginTime() == null){
			//开始生日为空,设置为当年第一天
			baseinfo.setBirthbeginTime(DateUtils.getYearFirst(Integer.valueOf(DateUtils.getYear() ) ) );
		}
		if(baseinfo.getBirthendTime() == null){
			//结束生日为空,设置为当天
			baseinfo.setBirthendTime(DateUtils.parseDate(DateUtils.getDate() ) );
		}
		if (baseinfo.getBirthbeginTime().after(baseinfo.getBirthendTime())) {
			addMessage(model, "你输入的出生日期起时间晚于止时间");
			return "modules/child_baseinfo/overduevacc";
		}
		
		//判断社区选项是否为空,为空则提示选择社区
		if(baseinfo.getAreas() == null || baseinfo.getAreas().length == 0){
			addMessage(model, "请选择您要查询的社区");
			return "modules/child_baseinfo/overduevacc";
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
		
		//现管单位为本地，更新officeinfo
		String officeCode = "";
		if(baseinfo.getOfficeinfo() != null){
			if(ExpChildBaseInfoVo.OFFICEINFO_LOCAL.equals(baseinfo.getOfficeinfo()) ){
				officeCode = OfficeService.getFirstOffice().getCode();
			}
		}
		
		//获取免疫程序id
		List<BsManageVaccinenum> nums = bsManageVaccinenumService.findList(new BsManageVaccinenum());
		Map<Object, List<BsManageVaccinenum>> numMap = CommonUtils.getTreeDateByParam(BsManageVaccinenum.class, nums, "group");
		
		boolean flag = false;
		
		//要查询的所有疫苗大类 + 针次 数字编码
		Set<BsManageVaccinenum> vaccNums = new HashSet<>();
		//将勾选到的疫苗名称下的所有疫苗大类+针次编码 拼接成字符串,在sql中作为vacccode in()查询条件
		if(baseinfo.getVaccinates() != null){
			for(Entry<Object, List<BsManageVaccinenum>> entry : numMap.entrySet()){
				if(baseinfo.getVaccinates().contains(entry.getKey())){
					flag = true;
					vaccNums.addAll(entry.getValue());
				}
			}
		}
		
		if(!flag){
			//未进入疫苗编码设置循环,原始数据库EXP_ROUTINEVACC6_1DETAIL表中暂无此项数据
			addMessage(model, "选定的疫苗无数据");
			return "modules/child_baseinfo/overduevacc";
		}
		
		//根据逾期未种的超时月数计算统计的时间开始范围,对应EXP_ROUTINEVACC6_1Detail表中的YEAR_MONTH(VARCHAR2)字段
		Calendar c = Calendar.getInstance();
		int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH) + 1;
		
		//定义查询截止的月份字符串
		String endSearchMonth = null;
		
		if(nowMonth > baseinfo.getOvmonth()){
			endSearchMonth = nowYear + "-" + (nowMonth-baseinfo.getOvmonth());
		}else if(nowMonth == baseinfo.getOvmonth()){
			endSearchMonth = (nowYear-1) + "-12";  
		}else{
			endSearchMonth = (nowYear-1) + "-" + (12-baseinfo.getOvmonth()+nowMonth);
		}
		
		//初始化areaArr
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("endSearchMonth", endSearchMonth);
		searchMap.put("officeCode", officeCode);
		searchMap.put("vaccNums", vaccNums);
		searchMap.put("baseinfo", baseinfo);

		//查询排除近期已种的记录总数
		resultList = bsManageVaccineService.getYQWZChildBaseInfoExcel(searchMap);
		
		if(resultList.size() == 0){
			addMessage(model, "无符合该条件的数据项");
			return "modules/child_baseinfo/overduevacc";
		}
		
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
			String fileName = "逾期未种数据" + DateUtils.getDate("yyyyMMddHHmmss")+ ".xlsx";
			new ExportExcel("逾期未种数据", ExportExcelBean.class).setDataList(list).write(resp, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出逾期未种数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/child_baseinfo/overduevacc/?repage";
	}

	/**
	 * 
	 * @author Jack
	 * @date 2018年2月6日 上午11:14:15
	 * @description 
	 * @param childBaseinfo 从此获取checEx(是否排除近期接种)
	 * @param birthbeginTime 生日范围开始时间
	 * @param birthendTime 生日范围结束时间
	 * ovmonth 逾期未种月份
	 * officeinfo 站点选择:0-所有站点 1-本地站点
	 * resides 居住属性:本地  流动  临时
	 * situations 当前状态:在册、离册、空挂户、死亡、服务器删除
	 * areas 社区选择,按照三位数社区编码划分
	 * 
	 */
	@RequestMapping(value = "sele")
	public String sele(ExpChildBaseInfoVo baseinfo, HttpServletRequest req, HttpServletResponse resp, Model model) throws ParseException {
		//赋默认值
		baseinfo.setDefaultValue();
		baseinfo.setVaccAll();
		model.addAttribute("expChildBaseInfoVo", baseinfo);
		
		//绑定前台社区数据
		List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("areas", areas);
		// 绑定前台疫苗名称数据
		List<BsManageVaccine> bsManageVaccineList = bsManageVaccineService.findModelList();
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);
		
		//判断选择的日期格式是否正确
		if(baseinfo.getBirthbeginTime() == null){
			//开始生日为空,设置为当年第一天
			baseinfo.setBirthbeginTime(DateUtils.getYearFirst(Integer.valueOf(DateUtils.getYear() ) ) );
		}
		if(baseinfo.getBirthendTime() == null){
			//结束生日为空,设置为当天
			baseinfo.setBirthendTime(DateUtils.parseDate(DateUtils.getDate() ) );
		}
		if (!baseinfo.resetTime()) {
			addMessage(model, "你输入的出生日期格式不正确");
			return "modules/child_baseinfo/overduevacc";
		}
		if (baseinfo.getBirthbeginTime().after(baseinfo.getBirthendTime())) {
			addMessage(model, "你输入的出生日期起时间晚于止时间");
			return "modules/child_baseinfo/overduevacc";
		}
		
		//判断社区选项是否为空,为空则提示选择社区
		if(baseinfo.getAreas() == null || baseinfo.getAreas().length == 0){
			addMessage(model, "请选择您要查询的社区");
			return "modules/child_baseinfo/overduevacc";
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
		
		//现管单位为本地，更新officeinfo
		String officeCode = "";
		if(baseinfo.getOfficeinfo() != null){
			if(ExpChildBaseInfoVo.OFFICEINFO_LOCAL.equals(baseinfo.getOfficeinfo()) ){
				officeCode = OfficeService.getFirstOffice().getCode();
			}
		}
		
		//获取免疫程序id
		List<BsManageVaccinenum> nums = bsManageVaccinenumService.findList(new BsManageVaccinenum());
		Map<Object, List<BsManageVaccinenum>> numMap = CommonUtils.getTreeDateByParam(BsManageVaccinenum.class, nums, "group");
		
		boolean flag = false;
		
		//要查询的所有疫苗大类 + 针次 数字编码
		Set<BsManageVaccinenum> vaccNums = new HashSet<>();
		//将勾选到的疫苗名称下的所有疫苗大类+针次编码 拼接成字符串,在sql中作为vacccode in()查询条件
		if(baseinfo.getVaccinates() != null){
			for(Entry<Object, List<BsManageVaccinenum>> entry : numMap.entrySet()){
				if(baseinfo.getVaccinates().contains(entry.getKey())){
					flag = true;
					vaccNums.addAll(entry.getValue());
				}
			}
		}
		
		if(!flag){
			//未进入疫苗编码设置循环,原始数据库EXP_ROUTINEVACC6_1DETAIL表中暂无此项数据
			addMessage(model, "选定的疫苗无数据");
			return "modules/child_baseinfo/overduevacc";
		}
		
		//根据逾期未种的超时月数计算统计的时间开始范围,对应EXP_ROUTINEVACC6_1Detail表中的YEAR_MONTH(VARCHAR2)字段
		Calendar c = Calendar.getInstance();
		int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH) + 1;
		
		//定义查询截止的月份字符串
		String endSearchMonth = null;
		
		if(nowMonth > baseinfo.getOvmonth()){
			endSearchMonth = nowYear + "-" + (nowMonth-baseinfo.getOvmonth());
		}else if(nowMonth == baseinfo.getOvmonth()){
			endSearchMonth = (nowYear-1) + "-12";  
		}else{
			endSearchMonth = (nowYear-1) + "-" + (12-baseinfo.getOvmonth()+nowMonth);
		}
		
		//初始化areaArr
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("endSearchMonth", endSearchMonth);
		searchMap.put("officeCode", officeCode);
		searchMap.put("vaccNums", vaccNums);
		searchMap.put("baseinfo", baseinfo);
		
		

		Page<HashMap<String, Object>> page = new Page<HashMap<String, Object>>(req, resp); 
//		//统计排除近期已种的记录总数
//		Integer totalRowNum_ = bsManageVaccineService.getYQWZChildBaseInfoRealCount(searchMap);
//		
//		//根据传入条件获取查询数据
//		resultList = bsManageVaccineService.getYQWZChildBaseInfoReal(searchMap, page);
//		page.setCount(totalRowNum_);
		
		if(baseinfo.isCheckEx()){
			//统计不排除近期已种的记录总数
			Integer totalRowNum = bsManageVaccineService.getYQWZChildBaseInfoCount(searchMap);
			resultList = bsManageVaccineService.getYQWZChildBaseInfo(searchMap, page);
			page.setCount(totalRowNum);
		}else{
			//统计排除近期已种的记录总数
			Integer totalRowNum_ = bsManageVaccineService.getYQWZChildBaseInfoRealCount(searchMap);
			resultList = bsManageVaccineService.getYQWZChildBaseInfoReal(searchMap, page);
			page.setCount(totalRowNum_);
		}
		
		if(resultList.size() == 0){
			addMessage(model, "无符合该条件的数据项");
			return "modules/child_baseinfo/overduevacc";
		}
		
		page.setList(resultList);
		model.addAttribute("page", page);

		return "modules/child_baseinfo/overduevacc";
	}
}