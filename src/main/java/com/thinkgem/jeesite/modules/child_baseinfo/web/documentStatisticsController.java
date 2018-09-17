/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_baseinfo.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.shequ.entity.SysCommunity;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;

/**
 * 建档统计Controller
 * 
 * @author 王德地
 * @version 2017-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/child_baseinfo/documentStatistics")
public class documentStatisticsController extends BaseController {

	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private SysCommunityService sysCommunityService;

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
		Date d[] = childVaccinaterecordService.date(childBaseinfo.getBeginTime(), childBaseinfo.getEndTime());
		if (d != null) {
			childBaseinfo.setBeginTime(d[0]);
			childBaseinfo.setEndTime(d[1]);
			Page<ChildBaseinfo> page = new Page<ChildBaseinfo>(request,response);
			// 根据时间排序
			page.setOrderBy("A.createdate ASC");
			page = childBaseinfoService.findPage(page, childBaseinfo);
			if (page.getList().size() > 0) {
				ArrayList<ChildBaseinfo> childBaseinfolist = (ArrayList<ChildBaseinfo>) page.getList();
				for (ChildBaseinfo childBaseinfo2 : childBaseinfolist) {
					// 转换地址信息
					childBaseinfo2 = childBaseinfoService.updateAddr(childBaseinfo2);
					childBaseinfo2=	childBaseinfoService.convert(childBaseinfo2);
					page.setList(childBaseinfolist);
				}
			}
			
			model.addAttribute("page", page);
		}
		List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("areas", areas);
		return "modules/child_baseinfo/documentStatistics";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月7日 下午4:23:20
	 * @description 根据条件查询建档记录
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 * 
	 */
	@RequiresPermissions("child:baseinfo:view")
	@RequestMapping(value = "sele")
	public String sele(ChildBaseinfo childBaseinfo, HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam String beginTime, @RequestParam String endTime,
			@RequestParam String birthbeginTime, @RequestParam String birthendTime,
			RedirectAttributes redirectAttributes) throws ParseException {

		if (StringUtils.isNoneBlank(birthbeginTime)) {
			Date birthbegin = childBaseinfoService.convert(birthbeginTime);
			Date birthend = childBaseinfoService.convert(birthendTime);
			if(birthbegin==null||birthend==null){
				addMessage(redirectAttributes, "你输入的出生日期格式不正确");
				return "redirect:" + Global.getAdminPath()+ "/child_baseinfo/documentStatistics/?repage";
			}
			Date birthd[] = childVaccinaterecordService.date(birthbegin, birthend);
			if (birthd != null) {
				childBaseinfo.setBirthbeginTime(birthd[0]);
				childBaseinfo.setBirthendTime(birthd[1]);
			}else{
				addMessage(redirectAttributes, "你输入的出生日期起时间晚于止时间");
				return "redirect:" + Global.getAdminPath()+ "/child_baseinfo/documentStatistics/?repage";
			}
		}
		if (StringUtils.isNoneBlank(beginTime)) {
			Date begin = childBaseinfoService.convert(beginTime);
			Date end = childBaseinfoService.convert(endTime);
			if(begin==null||end==null){
				addMessage(redirectAttributes, "你输入的创建日期格式不正确");
				return "redirect:" + Global.getAdminPath()+ "/child_baseinfo/documentStatistics/?repage";
			}
			Date d[] = childVaccinaterecordService.date(begin, end);
			if (d != null) {
				childBaseinfo.setBeginTime(d[0]);
				childBaseinfo.setEndTime(d[1]);
			}else{
				addMessage(redirectAttributes, "你输入的创建日期起时间晚于止时间");
				return "redirect:" + Global.getAdminPath()+ "/child_baseinfo/documentStatistics/?repage";
			}
		}
		
	
		Page<ChildBaseinfo> page = new Page<ChildBaseinfo>(request, response);
		// 根据时间排序
		page.setOrderBy("A.createdate ASC");
		page = childBaseinfoService.findPage(page, childBaseinfo);
		if (page.getList().size() > 0) {
			ArrayList<ChildBaseinfo> childBaseinfolist = (ArrayList<ChildBaseinfo>) page.getList();
			for (ChildBaseinfo childBaseinfo2 : childBaseinfolist) {
				// 转换地址信息
				childBaseinfo2 = childBaseinfoService.updateAddr(childBaseinfo2);
				childBaseinfo2=	childBaseinfoService.convert(childBaseinfo2);
				page.setList(childBaseinfolist);
			}
		}
		model.addAttribute("page", page);
		List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("areas", areas);
		return "modules/child_baseinfo/documentStatistics";
	}

	@RequiresPermissions("child:baseinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ChildBaseinfo childBaseinfo,
			RedirectAttributes redirectAttributes) {
		childBaseinfoService.delete(childBaseinfo);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:" + Global.getAdminPath()+ "/child_baseinfo/documentStatistics/?repage";
	}

}