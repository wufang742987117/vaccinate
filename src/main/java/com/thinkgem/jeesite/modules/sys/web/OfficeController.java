/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;
import com.thinkgem.jeesite.modules.department.service.SysVaccDepartmentService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private SysVaccDepartmentService sysVaccDepartmentService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}
	

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
		model.addAttribute("list", officeService.findList(office));
		return "modules/sys/officeList";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model, @RequestParam(value="saveSuccess" ,required = false) String saveSuccess) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		
		//查询科室接种的疫苗
//		office.setVaccinesList(bsManageVaccineService.findList(new BsManageVaccine()));
		//获取小类列表
		
		List<BsManageVaccine> vcacclist = bsManageVaccineService.findList(new BsManageVaccine());
		String option = "first";
		BsManageVaccine tempGroup;
		List<BsManageVaccine> temp = new ArrayList<BsManageVaccine>();
		List<BsManageVaccine> groupList = new ArrayList<BsManageVaccine>();
		for(BsManageVaccine bv : vcacclist){
			if(option.equals("first")){
				option = bv.getId().substring(0, 2);
			}
			if(!option.equals(bv.getId().substring(0, 2))){
				tempGroup = new BsManageVaccine();
				BeanUtils.copyProperties(temp.get(0), tempGroup);
				tempGroup.setSubList(temp);
				tempGroup.setId(temp.get(0).getId().substring(0, 2));
				groupList.add(tempGroup);
				option = bv.getId().substring(0, 2);
				temp = new ArrayList<BsManageVaccine>();
			}
			temp.add(bv);
		}
		if(temp.size() > 0){
			tempGroup = new BsManageVaccine();
			BeanUtils.copyProperties(temp.get(0), tempGroup);
			tempGroup.setSubList(temp);
			groupList.add(tempGroup);
		}
		
		office.setVaccinesList(groupList);
		model.addAttribute("office", office);
		model.addAttribute("saveSuccess", (StringUtils.isNotBlank(saveSuccess))?"保存成功":null);
		if(StringUtils.isNotBlank(office.getFrom()) && office.getFrom().equals("admin")){
			return "modules/sys/officeFormAdmin";
		}else{
			return "modules/sys/officeForm";
		}
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if(office.getParent() == null || StringUtils.isBlank(office.getParent().getId())){
			addMessage(redirectAttributes, "保存失败，未找到上级机构");
			return "redirect:" + adminPath + "/sys/office/form?id="+office.getId() + "&saveSuccess=true";
		}
		office.setParent(get(office.getParent().getId()));
		office.setArea(office.getParent().getArea());
		office.setGrade("60");
		if (!beanValidator(model, office)){
			return form(office, model, null);
		}
		officeService.save(office);
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+10));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}

		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
//		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
//		return "redirect:" + adminPath + "/sys/user/info";
		return "redirect:" + adminPath + "/sys/office/form?id="+office.getId() + "&saveSuccess=true";
	}
	
	/**
	 * 管理员添加机构
	 * @author fuxin
	 * @date 2017年12月27日 上午10:26:07
	 * @description 
	 *		TODO
	 * @param office
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "saveAdmin")
	public String saveAdmin(Office office, Model model, RedirectAttributes redirectAttributes) {
		
		if (!beanValidator(model, office)){
			return form(office, model,null);
		}
		officeService.save(office);

		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}

		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds();
	}


	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
		officeService.delete(office);
		addMessage(redirectAttributes, "删除机构成功");
//		}
		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
											  @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月7日 下午3:23:49
	 * @description 
	 *		进入接种单位配置页面
	 * @param office
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = {"localconf"})
	public String localconf(Office office, Model model) {
		List<Office> list = officeService.findAll();
		if(list.size()>0){
			for (Office office2 : list) {
				if("1".equals(office2.getGrade())){
					office = office2;	
					model.addAttribute("office", office);
					List<SysVaccDepartment> departmentlist=	sysVaccDepartmentService.findList(new SysVaccDepartment());	
					model.addAttribute("departmentlist", departmentlist);
					model.addAttribute("list",JsonMapper.toJsonString(departmentlist));
					return "modules/sys/localconf";
				}
			}
		
		}else{
			office = new Office();
		}
		List<SysVaccDepartment> departmentlist = sysVaccDepartmentService.findList(new SysVaccDepartment());	
		model.addAttribute("departmentlist", departmentlist);
		model.addAttribute("list",JsonMapper.toJsonString(departmentlist));
		return "modules/sys/localconf";
	}
	
	
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月7日 下午3:34:50
	 * @description 
	 *		接种单位配置保存
	 * @param office
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
//	@RequiresPermissions("local:admin")
	@RequestMapping(value = "localconfsave")
	public String localconfsave(Office office,Model model, RedirectAttributes redirectAttributes) {
		Office company = UserUtils.getUser().getCompany();
		company.setCode(office.getCode());
		company.setName(office.getName());
		officeService.save(company);
		List<SysVaccDepartment> departmentlist=	sysVaccDepartmentService.findList(new SysVaccDepartment());	
		model.addAttribute("departmentlist", departmentlist);
		model.addAttribute("list",JsonMapper.toJsonString(departmentlist));
		return "modules/sys/localconf";
	}
	
	@RequestMapping(value = "officeInfo")
	public String officeInfo() {
		
		return "modules/sys/officeInfo";
	}
	
	@RequestMapping(value="workTime")
	public String workTime(){
		
		
		return "modules/sys/workTime";
	}
	
}
