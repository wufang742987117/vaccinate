/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enter.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.junl.common.JsonBuild;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.enter.entity.SysEnterpriseInfo;
import com.thinkgem.jeesite.modules.enter.service.SysEnterpriseInfoService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

import net.sf.json.util.JSONBuilder;

/**
 * 疫苗生产厂家Controller
 * @author wangdedi
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/enter/sysEnterpriseInfo")
public class SysEnterpriseInfoController extends BaseController {

	@Autowired
	private SysEnterpriseInfoService sysEnterpriseInfoService;
	
	@ModelAttribute
	public SysEnterpriseInfo get(@RequestParam(required=false) String id) {
		SysEnterpriseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysEnterpriseInfoService.get(id);
		}
		if (entity == null){
			entity = new SysEnterpriseInfo();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SysEnterpriseInfo sysEnterpriseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysEnterpriseInfo> page = sysEnterpriseInfoService.findPage(new Page<SysEnterpriseInfo>(request, response), sysEnterpriseInfo); 
		model.addAttribute("page", page);
		return "modules/enter/sysEnterpriseInfoList";
	}

	@RequestMapping(value = "form")
	public String form(SysEnterpriseInfo sysEnterpriseInfo, Model model) {
		model.addAttribute("sysEnterpriseInfo", sysEnterpriseInfo);
		return "modules/enter/sysEnterpriseInfoForm";
	}

	/**
	 * 
	 * @author liyuan
	 * @date 2018年1月25日 上午9:53:12
	 * @description 
	 *		企业产品信息修改和添加，判断用户填写id是否存在，如果存在则 修改产品信息，
	 *		如果不存在则添加该产品信息！
	 * @param sysEnterpriseInfo
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "save")
	public @ResponseBody Map<String, Object> save(SysEnterpriseInfo sysEnterpriseInfo, Model model) {
		if (!beanValidator(model, sysEnterpriseInfo)){
			return JsonBuild.build(false, "参数错误，请重试", "500", null);
		}
		if(sysEnterpriseInfo.getIsNewRecord()){
			sysEnterpriseInfo.preInsert();
			sysEnterpriseInfoService.insert(sysEnterpriseInfo);
		}else{
			sysEnterpriseInfo.preUpdate();
			sysEnterpriseInfoService.update(sysEnterpriseInfo);
		}
		return JsonBuild.build(true, "企业产品信息保存成功", "200", null);
	}
	

	@RequestMapping(value = "delete")
	public @ResponseBody String delete(SysEnterpriseInfo sysEnterpriseInfo) {
		sysEnterpriseInfoService.delete(sysEnterpriseInfo);
		return "删除信息成功";
	}

}