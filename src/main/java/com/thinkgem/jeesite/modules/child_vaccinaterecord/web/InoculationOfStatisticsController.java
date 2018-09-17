/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccLog;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;
import com.thinkgem.jeesite.modules.department.service.SysVaccDepartmentService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;
import com.thinkgem.jeesite.modules.yiyuan.entity.SysBirthHospital;
import com.thinkgem.jeesite.modules.yiyuan.service.SysBirthHospitalService;

/**
 * 接种统计管理Controller
 * 
 * @author 王德地
 * @version 2017-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/child_vaccinaterecord/inoculationOfStatistics")
public class InoculationOfStatisticsController extends BaseController {

	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private SysVaccDepartmentService sysVaccDepartmentService;
	@Autowired
	private SysBirthHospitalService sysBirthHospitalService;

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
	 * @date 2017年3月3日 下午3:27:40
	 * @description 进入接种统计
	 * @param childVaccinaterecord
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:view")
	@RequestMapping(value = { "list", "" })
	public String list(ChildVaccinaterecord childVaccinaterecord,
			HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) {
		// 加载所有的疫苗名称
		ArrayList<BsManageVaccine> bsManageVaccineList = (ArrayList<BsManageVaccine>) bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);
		Date d[] = childVaccinaterecordService.date(childVaccinaterecord.getBeginTime(),childVaccinaterecord.getEndTime());
		// 判断输入的时间是否正确
		if (d != null) {
			childVaccinaterecord.setBeginTime(d[0]);
			childVaccinaterecord.setEndTime(d[1]);
			Page<ChildVaccinaterecord> p = new Page<ChildVaccinaterecord>(request, response);
			// 根据儿童ID和接种时间排序
			p.setOrderBy("A.childid,A.vaccinatedate");
			//获取疫苗的vid
			String vid=childVaccinaterecord.getVaccName();
			if(StringUtils.isNoneBlank(vid)){
				childVaccinaterecord.setVaccineid(vid);
				childVaccinaterecord.setVaccName("");
			}
			childVaccinaterecord.setOpen("1111111");//若open为NULL,则不排除补录的疫苗的记录
			childVaccinaterecord.setOpenSource("1111111");//若open为NULL,则不排除补录的疫苗的记录
			Page<ChildVaccinaterecord> page = childVaccinaterecordService.findPage(p, childVaccinaterecord);
			childVaccinaterecord.setVaccName(vid);
			List<ChildVaccinaterecord>list=new ArrayList<>();
			for (ChildVaccinaterecord record : page.getList()) {
				//是否出生当天接种的
				if(record.getSign().equals("1")){
					SysBirthHospital		BirthHospital	=new SysBirthHospital();
					BirthHospital.setCode(record.getOffice());
					List<SysBirthHospital> BirthHospitallist = sysBirthHospitalService.findList(BirthHospital);
					record.setOffice(BirthHospitallist.get(0).getName());		
				}else{
					SysVaccDepartment 	department=new SysVaccDepartment();
					department.setCode(record.getOffice());
					List<SysVaccDepartment> departmentlist =sysVaccDepartmentService.findList(department);
					if(departmentlist.size() > 0){
						record.setOffice(departmentlist.get(0).getName());
					}
				}
				//排除已经删除的接种记录和补录的接种记录
				if(!record.getSource().equals("3")){
					list.add(record);
				}
			}
			page.setList(list);
			model.addAttribute("page", page);
		}
		return "modules/child_vaccinaterecord/inoculationOfStatistics";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:29:22
	 * @description 删除接种记录
	 * @param childVaccinaterecord
	 * @param redirectAttributes
	 * @return
	 * 
	 */
	@RequiresPermissions("child:cord:edit")
	@RequestMapping(value = "delete")
	public String delete(ChildVaccinaterecord childVaccinaterecord,
			RedirectAttributes redirectAttributes) {
		childVaccinaterecordService.delete(childVaccinaterecord);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:" + Global.getAdminPath()+ "/child_vaccinaterecord/InoculationOfStatistics/?repage";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:29:47
	 * @description 导出接种记录
	 * @param childVaccinaterecord
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * 
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(ChildVaccinaterecord childVaccinaterecord, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		childVaccinaterecord = childVaccinaterecordService.sel(childVaccinaterecord);
		if (childVaccinaterecord == null) {
			return "modules/child_vaccinaterecord/inoculationOfStatistics";
		}
		Page<ChildVaccinaterecord> p = new Page<ChildVaccinaterecord>();
		// 根据儿童的ID和创建时间排序
		p.setOrderBy("A.childid,A.createdate");
		//获取疫苗的vid
		String vid=childVaccinaterecord.getVaccName();
		if(StringUtils.isNoneBlank(vid)){
			childVaccinaterecord.setVaccineid(vid);
			childVaccinaterecord.setVaccName("");
		}
		p = childVaccinaterecordService.findPage(p, childVaccinaterecord);
		/*Page<ChildVaccinaterecord> page = childVaccinaterecordService.findPage(p, childVaccinaterecord);*/
		childVaccinaterecord.setPage(p);
		List<ChildVaccinaterecord> list = childVaccinaterecordService.findList(childVaccinaterecord);
		List<ChildVaccinaterecord>list1=new ArrayList<>();
		for (ChildVaccinaterecord record : list) {
			if(record.getSign().equals("1")){
				SysBirthHospital BirthHospital	=new SysBirthHospital();
				BirthHospital.setCode(record.getOffice());
				List<SysBirthHospital> BirthHospitallist = sysBirthHospitalService
						.findList(BirthHospital);
						record.setOffice(BirthHospitallist.get(0).getName());		
			}else{
				SysVaccDepartment 	department=new SysVaccDepartment();
				department.setCode(record.getOffice());
				List<SysVaccDepartment> departmentlist =sysVaccDepartmentService.findList(department);
				
				record.setOffice(departmentlist.get(0).getName());
			}
			if(!record.getSource().equals("3")){
				list1.add(record);
			}
		}
		try {
			String fileName = "接种数据" + DateUtils.getDate("yyyyMMddHHmmss")+ ".xlsx";
			
			new ExportExcel("接种数据", ChildVaccinaterecord.class)
					.setDataList(list1).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出接种数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/inoculationOfStatistics/?repage";
	}
	
	/**
	 * 预约接种门诊日志统计与打印
	 * @author zhouqj
	 * @date 2017年12月20日 下午4:30:19
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param type
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "logList")
	public String logList(ChildVaccinaterecord childVaccinaterecord, HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) {
		// 加载所有的疫苗名称
		ArrayList<BsManageVaccine> bsManageVaccineList = (ArrayList<BsManageVaccine>) bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);
		//处理开始结束时间
		Date d[] = childVaccinaterecordService.dateTime(childVaccinaterecord.getBeginTime(),childVaccinaterecord.getEndTime());
		// 判断输入的时间是否正确
		if (d != null) {
			childVaccinaterecord.setBeginTime(d[0]);
			childVaccinaterecord.setEndTime(d[1]);
			//分页实例化
			Page<ChildVaccinaterecord> p = new Page<ChildVaccinaterecord>(request, response);
			// 根据接种时间排序
			p.setOrderBy("A.vaccinatedate");
			p.setPageSize(16);
			//接种医生
			if(StringUtils.isBlank(childVaccinaterecord.getCreateById())){
				// 清除用户缓存
				UserUtils.clearCache();
				// 医生名称
				childVaccinaterecord.setCreateById(UserUtils.getUser().getName());
			}else if("0".equals(childVaccinaterecord.getCreateById())){
				childVaccinaterecord.setCreateById(null);
			}
			//查询所有医生
			model.addAttribute("createByNameList", UserUtils.getCompanyUsers());
			//获取疫苗的vid
			String vid = childVaccinaterecord.getVaccName();
			if(StringUtils.isNoneBlank(vid)){
				childVaccinaterecord.setVaccineid(vid);
				childVaccinaterecord.setVaccName("");
			}
			//若open为NULL,则不排除补录的疫苗的记录
			childVaccinaterecord.setOpen("open");
			childVaccinaterecord.setOpenSource("open");
			//已接种
			childVaccinaterecord.setStatus(ChildVaccinaterecord.STATUS_YET);
			//查询记录
			Page<ChildVaccinaterecord> page = childVaccinaterecordService.findPage(p, childVaccinaterecord);
			//默认选中
			childVaccinaterecord.setVaccName(vid);
			List<ChildVaccinaterecord> list = new ArrayList<>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			//循环处理业务
			for (ChildVaccinaterecord record : page.getList()) {
				//累计数量
				if(StringUtils.isNotBlank(record.getVaccName())){
					if(map.get(record.getVaccName()) == null){
						map.put(record.getVaccName(), 1);
					}else{
						int count = map.get(record.getVaccName());
						count++;
						map.put(record.getVaccName(), count);
					}
				}
				
				//是否出生当天接种的
				if(!record.getSign().equals("1")){
					SysVaccDepartment department = new SysVaccDepartment();
					department.setCode(record.getOffice());
					List<SysVaccDepartment> departmentlist = sysVaccDepartmentService.findList(department);
					if(departmentlist.size() > 0){
						record.setOffice(departmentlist.get(0).getName());
					}	
				}
				//用户签字
				if(record.getSignature().equals("1") && record.getStype().equals("1")){
					try {
						record.setSignList(new String(record.getSignatureList(),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				//排除已经删除的接种记录和补录的接种记录
				if(!record.getSource().equals("3") && !record.getSource().equals("4")){
					list.add(record);
				}
			}
			page.setList(list);
			model.addAttribute("page", page);
			model.addAttribute("map", JsonMapper.toJsonString(map));
		}
		
		return "modules/child_vaccinaterecord/inoculationLog";
	}
	
	/**
	 *  预约接种门诊日志导出
	 * @author zhouqj
	 * @date 2017年12月20日 下午4:30:58
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "logExport", method = RequestMethod.POST)
	public String logExport(ChildVaccinaterecord childVaccinaterecord, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		//处理开始结束时间
		Date d[] = childVaccinaterecordService.dateTime(childVaccinaterecord.getBeginTime(),childVaccinaterecord.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			childVaccinaterecord.setBeginTime(d[0]);
			childVaccinaterecord.setEndTime(d[1]);
		} else {
			return "modules/child_vaccinaterecord/inoculationLog";
		}
		Page<ChildVaccinaterecord> p = new Page<ChildVaccinaterecord>(request, response);
		// 根据接种时间排序
		p.setOrderBy("A.vaccinatedate");
		p.setPageSize(16);
		//获取疫苗的vid
		String vid = childVaccinaterecord.getVaccName();
		if(StringUtils.isNoneBlank(vid)){
			childVaccinaterecord.setVaccineid(vid);
			childVaccinaterecord.setVaccName("");
		}
		//接种医生
		if("0".equals(childVaccinaterecord.getCreateById())){
			childVaccinaterecord.setCreateById(null);
		}
		//若open为NULL,则不排除补录的疫苗的记录
		childVaccinaterecord.setOpen("open");
		childVaccinaterecord.setOpenSource("open");
		//查询记录
		Page<ChildVaccinaterecord> page = childVaccinaterecordService.findPage(p, childVaccinaterecord);
		List<ChildVaccinaterecord> list = new ArrayList<>();
		for (ChildVaccinaterecord record : page.getList()) {
			//是否出生当天接种的
			if(!record.getSign().equals("1")){
				SysVaccDepartment department = new SysVaccDepartment();
				department.setCode(record.getOffice());
				List<SysVaccDepartment> departmentlist = sysVaccDepartmentService.findList(department);
				record.setOffice(departmentlist.get(0).getName());
			}
			//用户签字
			if(record.getSignature().equals("1") && record.getStype().equals("1")){
				try {
					record.setSignList(new String(record.getSignatureList(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			//排除已经删除的接种记录和补录的接种记录
			if(!record.getSource().equals("3") && !record.getSource().equals("4")){
				list.add(record);
			}
		}
		page.setList(list);
		try {
			String fileName = "预约接种门诊日志" + DateUtils.getDate("yyyyMMddHHmmss")+ ".xlsx";
			new ExportExcel("预约接种门诊日志", ChildVaccLog.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出预约接种门诊日志失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/child_vaccinaterecord/inoculationOfStatistics/logList/?repage";
	}
	
	/**
	 * 预约接种门诊日志打印
	 * @author zhouqj
	 * @date 2017年12月22日 下午2:16:45
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "logListPrint")
	public String logListPrint(ChildVaccinaterecord childVaccinaterecord, HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) {
		// 加载所有的疫苗名称
		ArrayList<BsManageVaccine> bsManageVaccineList = (ArrayList<BsManageVaccine>) bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);
		//处理开始结束时间
		Date d[] = childVaccinaterecordService.dateTime(childVaccinaterecord.getBeginTime(),childVaccinaterecord.getEndTime());
		// 判断输入的时间是否正确
		if (d != null) {
			childVaccinaterecord.setBeginTime(d[0]);
			childVaccinaterecord.setEndTime(d[1]);
			//接种医生
			if("0".equals(childVaccinaterecord.getCreateById())){
				childVaccinaterecord.setCreateById(null);
			}
			//获取疫苗的vid
			String vid = childVaccinaterecord.getVaccName();
			if(StringUtils.isNoneBlank(vid)){
				childVaccinaterecord.setVaccineid(vid);
				childVaccinaterecord.setVaccName("");
			}
			//若open为NULL,则不排除补录的疫苗的记录
			childVaccinaterecord.setOpen("open");
			childVaccinaterecord.setOpenSource("open");
			//根据接种时间排序
			childVaccinaterecord.setOrderBy("A.vaccinatedate");
			//查询记录
			List<ChildVaccinaterecord> pageList = childVaccinaterecordService.findList(childVaccinaterecord);
			//默认选中
			childVaccinaterecord.setVaccName(vid);
			//承接实例
			List<ChildVaccinaterecord> list = new ArrayList<>();
			List<List<ChildVaccinaterecord>> returnList = new ArrayList<List<ChildVaccinaterecord>>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			int num = 0;
			//循环处理业务
			for (ChildVaccinaterecord record : pageList) {
				//累计个数
				num++;
				//累计数量
				if(StringUtils.isNotBlank(record.getVaccName())){
					if(map.get(record.getVaccName()) == null){
						map.put(record.getVaccName(), 1);
					}else{
						int count = map.get(record.getVaccName());
						count++;
						map.put(record.getVaccName(), count);
					}
				}
				
				//是否出生当天接种的
				if(!record.getSign().equals("1")){
					SysVaccDepartment department = new SysVaccDepartment();
					department.setCode(record.getOffice());
					List<SysVaccDepartment> departmentlist = sysVaccDepartmentService.findList(department);
					if(departmentlist.size() > 0){
						record.setOffice(departmentlist.get(0).getName());
					}	
				}
				//用户签字
				if(record.getSignature().equals("1") && record.getStype().equals("1")){
					try {
						record.setSignList(new String(record.getSignatureList(),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				//排除已经删除的接种记录和补录的接种记录
				if(!record.getSource().equals("3") && !record.getSource().equals("4")){
					list.add(record);
				}
				//判断累计个数
				if(num >= 16){
					returnList.add(list);
					list = new ArrayList<ChildVaccinaterecord>();
					num = 0;
				}
			}
			if(num > 0){
				returnList.add(list);
			}
			model.addAttribute("returnList", returnList);
			model.addAttribute("map", JsonMapper.toJsonString(map));
			//医生名称
			model.addAttribute("docaterName",childVaccinaterecord.getCreateById());
			//接种单位
			model.addAttribute("officeName",OfficeService.getFirstOffice().getName());
		}
		return "modules/child_vaccinaterecord/inoculationLogPrint";
	}
}