/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.cache.decorators.LoggingCache;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.logging.LoggingClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.junl.common.JsonBuild;
import com.mysql.fabric.xmlrpc.base.Array;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysBadsql;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysBadsqlService;

/**
 * sql上报失败Controller
 * @author liyuan
 * @version 2018-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysBadsql")
public class SysBadsqlController extends BaseController {

	@Autowired
	private SysBadsqlService sysBadsqlService;
	
	@ModelAttribute
	public SysBadsql get(@RequestParam(required=false) String id) {
		SysBadsql entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysBadsqlService.get(id);
		}
		if (entity == null){
			entity = new SysBadsql();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SysBadsql sysBadsql, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysBadsql> page = sysBadsqlService.findPage(new Page<SysBadsql>(request, response), sysBadsql); 
		model.addAttribute("page", page);
		return "modules/sys/sysBadsqlList";
	}

	@RequestMapping(value = "form")
	public String form(SysBadsql sysBadsql, Model model) {
		model.addAttribute("sysBadsql", sysBadsql);
		return "modules/sys/sysBadsqlForm";
	}

	@RequestMapping(value = "save")
	public @ResponseBody Map<String,Object> save(SysBadsql sysBadsql, Model model) {
		if (!beanValidator(model, sysBadsql)){
			return JsonBuild.build(false, "参数错误，请重试", "500", null);
		}
		String localCode = OfficeService.getFirstOfficeCode(); 
		sysBadsql.setLocalCode(localCode);
		sysBadsqlService.save(sysBadsql);
		return JsonBuild.build(true, "sql上报失败保存成功", "200", null);
	}
	/**
	 * 
	 * @author liyuan
	 * @date 2018年1月24日 上午9:16:38
	 * @description 
	 *		sql上报失败单个和批量删除
	 * @param sysBadsql
	 * @return
	 *
	 */
	
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(SysBadsql sysBadsql) {
		String id=sysBadsql.getId();
		String[] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			id=ids[i];
			logger.info(id);
			sysBadsql.setId(id);
			sysBadsqlService.delete(sysBadsql);
		}
		return "sql上报失败删除成功";
	}
	

	/**
	 * 
	 * @author liyuan
	 * @date 2018年1月23日 下午4:29:24
	 * @description 
	 *		对sql上报失败的sql语句进行批量补录
	 * @param 接收页面传递的string数组
	 * @return 操作成功标记
	 *
	 */
	@RequestMapping(value = "sqlqueue")
	public @ResponseBody String sqlQueue(String[] ids) {
		logger.info("批量补录上传开始==>ids"+Arrays.toString(ids));
		try {
			for(int i=0;i<ids.length;i++){
				String id=ids[i];
				SysBadsql sysbadsql=sysBadsqlService.get(id);
				String sql=sysbadsql.getSqlContext();
				Global.getInstance().pullSqlQueue(sql);
			}
		} catch (InterruptedException e) {
			logger.error("",e.getMessage());
			return "批量补录失败";
		}
		logger.info("批量补录上传结束==>ids"+Arrays.toString(ids));
		return "批量补录成功";
	}
	
	

}