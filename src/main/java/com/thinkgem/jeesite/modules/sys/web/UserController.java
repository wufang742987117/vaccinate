/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.service.BsRabiesNumService;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.OfficePreference;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired 
	private VacChildRemindService vacChildRemindService;
	@Autowired
	private QueneService queneService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private BsRabiesNumService bsRabiesNumService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		user.setRoleList(systemService.findAllRole());
		return "modules/sys/userForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("allRoles", systemService.findAllRole());
		user.setNo(user.getLoginName());
		if(!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			model.addAttribute("user", user);
			model.addAttribute("arg", "1");
			return "modules/sys/userForm";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
			user.setNewPassword(user.getPassword());
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		//更新用户信息到市平台
		Office off = officeService.get(request.getParameter("company.id"));
		asyncService.pushUserInfo(user,off.getCode(),"1");
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		model.addAttribute("user", user);
		model.addAttribute("arg", "2");
		return "modules/sys/userForm";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		// 清除用户缓存
		UserUtils.clearCache();
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getPhone());
			currentUser.setOldLoginName(user.getLoginName());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			currentUser.setName(user.getName());
			currentUser.setNewPassword(currentUser.getPassword());
			//更新科室信息
			if(user.getOffice() != null && StringUtils.isNoneBlank(user.getOffice().getId())){
				currentUser.setOffice(officeService.get(user.getOffice().getId()));
			}
			//自动流程状态
			currentUser.setOpenStatus(user.getOpenStatus());
			//自动出票状态
			currentUser.setPrintStatus(user.getPrintStatus());
			//更新用户信息到市平台
			Office off = officeService.get(currentUser.getCompany().getId());
			asyncService.pushUserInfo(currentUser,off.getCode(),"2");
			//更新用户信息
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		
		//获取所有不是一级的科室信息
		 List<Office> officelist = UserUtils.getOfficeList();
		 for(int i = 0; i < officelist.size(); i ++){
			 if(!"60".equals(officelist.get(i).getGrade())){
				 officelist.remove(i--);
			 }
		 }
		model.addAttribute("officelist",officelist);
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				//更新用户信息到市平台
				User updateUser = systemService.getUser(user.getId());
				updateUser.setNewPassword(updateUser.getPassword());
				Office off = officeService.get(updateUser.getCompany().getId());
				asyncService.pushUserInfo(updateUser,off.getCode(),"3");
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
	
	/**
	 * 修改个人用户密码
	 * @return
	 */
	@RequestMapping(value = "about")
	public String about(Model model) {
		model.addAttribute("payOption", Global.getInstance().isPayOption());
		model.addAttribute("reserveOption", Global.getInstance().isReserveOption());
		model.addAttribute("quickOption", Global.getInstance().isQuickOption());
		model.addAttribute("receiptOption", Global.getInstance().isReceiptOption());
		OfficePreference op = OfficeService.getOfficeOption();
		model.addAttribute("vacDelay", op.getVacDelay());
		model.addAttribute("obligate", op.getObligate());
		model.addAttribute("callReadyOption", op.isCallReady());
		return "modules/sys/about";
	}
	
	/**
	 * 用户首选项
	 * @author fuxin
	 * @date 2017年8月20日 上午11:55:26
	 * @description 
	 *		TODO
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/userSetting")
	public String userSetting(Model model,@RequestParam(value="from", required=false, defaultValue="")String from){
		String vac = officeService.getOfficeVaccines();
		List<BsManageVaccine> vaccs = null;
		if(StringUtils.isNotBlank(vac)){
			vac  = "'" + vac.replaceAll(",", "','") + "'";
			BsManageVaccine vaccine = new BsManageVaccine();
			vaccine.setInGroups(vac);
			vaccs = bsManageVaccineService.findList(vaccine);

		}else{
			vaccs = new ArrayList<BsManageVaccine>();
		}
		
		//获取所有不是一级的科室信息
		 List<Office> officelist = UserUtils.getOfficeList();
		 for(int i = 0; i < officelist.size(); i ++){
			 if(!"60".equals(officelist.get(i).getGrade()) || !"1".equals(officelist.get(i).getQueueAble())){
				 officelist.remove(i--);
			 }
		 }
		model.addAttribute("officelist",officelist);
		
		model.addAttribute("vaccs", JsonMapper.toJsonString(vaccs));
		model.addAttribute("from", from);
		return "modules/inoculate/userSetting";
	}
	
	/**
	 * 保存用户首选项
	 * @author fuxin
	 * @date 2017年8月20日 上午11:55:05
	 * @description 
	 *		TODO
	 * @param preference
	 * @return
	 *
	 */
	@RequestMapping("/saveUserSetting")
	public @ResponseBody Map<String, String> saveUserSetting(@RequestParam(value="preference", required=false, defaultValue="")String preference){
		Map<String, String> rs = new HashMap<String, String>();
		rs.put("code", "200");
		try {
			if(StringUtils.isBlank(preference)){
				throw new NullPointerException("preference为空");
			}
			preference = StringEscapeUtils.unescapeHtml4(preference);
			UserUtils.getUser().setPreference(preference);
			systemService.saveUser(UserUtils.getUser());
		} catch (Exception e) {
			logger.error("用户首选项保存失败");
			rs.put("code", "500");
			rs.put("msg", e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 修改门诊默认疫苗时间间隔
	 * @author fuxin
	 * @date 2018年1月5日 下午4:23:06
	 * @description 
	 *		TODO
	 * @param vacDelay
	 * @return
	 *
	 */
	@RequestMapping("/saveVacDelay")
	public @ResponseBody Map<String, String> saveVacDelay(@RequestParam(value="vacDelay", required=false, defaultValue="14")int vacDelay){
		logger.info("门诊首选项[默认疫苗间隔时间]" + vacDelay  + "，操作人：" + UserUtils.getUser().getName());
		Map<String, String> rs = new HashMap<String, String>();
		rs.put("code", "200");
		if(vacDelay < 7){
			rs.put("code", "500");
			rs.put("msg", "间隔天数过小，保存失败" + vacDelay  + "，操作人：" + UserUtils.getUser().getName());
			return rs;
		}
		try {
			//获取首选项
			OfficePreference pref = OfficeService.getOfficeOption();
			//更新首选项
			pref.setVacDelay(vacDelay);
			OfficeService.saveOfficeOption(pref);
			//返回更新后状态
			logger.info("门诊首选项[默认疫苗间隔时间]保存失败" + vacDelay  + "，操作人：" + UserUtils.getUser().getName());
		} catch (Exception e) {
			logger.error("门诊首选项[默认疫苗间隔时间]保存失败" + e.getMessage());
			rs.put("code", "500");
			rs.put("msg", e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 修改门诊疫苗预留库存
	 * @author fuxin
	 * @date 2018年1月5日 下午4:23:48
	 * @description 
	 *		TODO
	 * @param obligate
	 * @return
	 *
	 */
	@RequestMapping("/saveObligate")
	public @ResponseBody Map<String, String> saveObligate(@RequestParam(value="obligate", required=false, defaultValue="0")int obligate){
		logger.info("修改门诊首选项[保留库存数量]-->" + obligate + "，操作人：" + UserUtils.getUser().getName());
		Map<String, String> rs = new HashMap<String, String>();
		rs.put("code", "200");
		try {
			//获取首选项
			OfficePreference pref = OfficeService.getOfficeOption();
			//更新首选项
			pref.setObligate(obligate);
			OfficeService.saveOfficeOption(pref);
			logger.info("门诊首选项[保留库存数量]保存成功-->" + obligate + "，操作人：" + UserUtils.getUser().getName());
			//返回更新后状态
		} catch (Exception e) {
			logger.error("门诊首选项[保留库存数量]保存失败-->" + obligate + "，操作人：" + UserUtils.getUser().getName() + e.getMessage());
			rs.put("code", "500");
			rs.put("msg", e.getMessage());
		}
		return rs;
	}
	
	@RequestMapping("/toggleCallReady")
	public @ResponseBody Map<String, Object> toggleCallReady() {
		logger.info("修改门诊首选项[叫号准备]，操作人：" + UserUtils.getUser().getName());
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("code", "200");
		try {
			//获取首选项
			OfficePreference pref = OfficeService.getOfficeOption();
			//更新首选项
			pref.togglecallReadyOption();
			OfficeService.saveOfficeOption(pref);
			logger.info("门诊首选项[叫号准备]保存成功-->" + pref.isCallReady() + "，操作人：" + UserUtils.getUser().getName());
			rs.put("msg", pref.isCallReady());
			//返回更新后状态
		} catch (Exception e) {
			logger.error("门诊首选项[叫号准备]保存失败-->，操作人：" + UserUtils.getUser().getName() + e.getMessage());
			rs.put("code", "500");
			rs.put("msg", e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 获取当天预约、排号、完成人数
	 * @author yangjian
	 * @date 2018年3月2日 下午2:15:01
	 * @description 
	 *		TODO
	 * @return
	 */
	@RequestMapping("getRemindCount")
	@ResponseBody
	public String getRemindCount(){
		Map<String, Object> data = new HashMap<String, Object>();
		int childRemind = vacChildRemindService.getRemindCount();
		int queneNum = queneService.getQueneCount();
		int recordNum = childVaccinaterecordService.getRecordCount();
		int rabiesNum = bsRabiesNumService.getRabiesNumCount();
		data.put("childRemind", childRemind);
		data.put("queneNum", queneNum);
		data.put("recordNum", recordNum);
		data.put("rabiesNum", rabiesNum);
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
}
