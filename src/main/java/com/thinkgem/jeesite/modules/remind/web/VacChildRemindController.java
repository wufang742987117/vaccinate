/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.remind.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.holiday.service.SysHolidayService;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;

/**
 * 儿童接种提醒Controller
 * @author fuxin
 * @version 2017-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/remind/vacChildRemind")
public class VacChildRemindController extends BaseController {

	@Autowired
	private VacChildRemindService vacChildRemindService;
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private SysHolidayService holidayService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private BsManageVaccinenumService bsManageVaccinenumService;
	@Autowired
	private AsyncService asyncService;
	
	private static final String TYPE_REMIND="02"; //预约通知模板
	private static final String TYPE_CNACEL_REMIND="07";//取消预约通知
	
	@ModelAttribute
	public VacChildRemind get(@RequestParam(required=false) String id) {
		VacChildRemind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vacChildRemindService.get(id);
		}
		if (entity == null){
			entity = new VacChildRemind();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(VacChildRemind vacChildRemind, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<VacChildRemind> page = vacChildRemindService.findPage(new Page<VacChildRemind>(request, response), vacChildRemind); 
		model.addAttribute("page", page);
		return "modules/remind/vacChildRemindList";
	}

	@RequestMapping(value = "form")
	public String form(VacChildRemind vacChildRemind, Model model) {
		model.addAttribute("vacChildRemind", vacChildRemind);
		//小票打印功能是否开启
		model.addAttribute("receiptType", Global.getInstance().isReceiptOption());
		return "modules/remind/vacChildRemindForm";
	}

	@RequestMapping(value = "save")
	public String save(VacChildRemind vacChildRemind, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vacChildRemind)){
			return form(vacChildRemind, model);
		}
		vacChildRemindService.save(vacChildRemind);
		addMessage(redirectAttributes, "保存儿童接种提醒成功");
		return "redirect:"+Global.getAdminPath()+"/remind/vacChildRemind/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(VacChildRemind vacChildRemind, RedirectAttributes redirectAttributes) {
		vacChildRemindService.delete(vacChildRemind);
		addMessage(redirectAttributes, "删除儿童接种提醒成功");
		return "redirect:"+Global.getAdminPath()+"/remind/vacChildRemind/?repage";
	}
	
	/**
	 * 预约页面
	 * @author fuxin
	 * @date 2017年9月18日 下午9:46:36
	 * @description 
	 *		TODO
	 * @param childcode
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/remindForm")
	public String remindForm(@RequestParam(value = "childcode", required = false, defaultValue = "")String childcode, Model model){
		model.addAttribute("childcode", childcode);
		ChildBaseinfo baseinfo = childBaseinfoService.getByNo(childcode);
		if(baseinfo != null){
			baseinfo.setMouthAgeInt(DateUtils.subtractMonths(baseinfo.getBirthday(), new Date()));
		}
		//获取节假日，展示在日历上
		model.addAttribute("weekdays", JsonMapper.toJsonString(holidayService.findAbleWeeks()));
		model.addAttribute("holidays", JsonMapper.toJsonString(holidayService.findAbleDays()));
		model.addAttribute("baseinfo", JsonMapper.toJsonString(baseinfo));
		//小票打印功能是否开启
		model.addAttribute("receiptType", Global.getInstance().isReceiptOption());
		
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
		ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
		tempRcv.setChildid(baseinfo.getId());
		List<ChildVaccinaterecord> rcvs = childVaccinaterecordService.findList(tempRcv);
		//将未完成的记录改成已完成，用于计算
		for(ChildVaccinaterecord rcv : rcvs){
			if(ChildVaccinaterecord.STATUS_NOT.equals(rcv.getStatus())){
				rcv.setStatus(ChildVaccinaterecord.STATUS_YET);
			}
			if(rcv.getVaccinatedate() == null){
				rcv.setVaccinatedate(new Date());
			}
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
		return "modules/remind/childRemindForm";
	}
	
	/**
	 * 预约界面保存预约记录
	 * @author fuxin
	 * @date 2017年12月8日 上午10:10:07
	 * @description 
	 *		TODO
	 * @param vacChildRemind
	 * @return
	 *
	 */
	@RequestMapping("/saveRemind")
	@ResponseBody
	Map<String, Object> saveRemind(VacChildRemind vacChildRemind){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", "500");
		
		if(null == vacChildRemind.getRemindDate() || StringUtils.isBlank(vacChildRemind.getRemindVacc()) || StringUtils.isBlank(vacChildRemind.getChildcode())){
			returnMap.put("msg", "儿童信息异常");
			return returnMap;
		}
		//保存预约记录
		returnMap = vacChildRemindService.saveRemind(vacChildRemind, TYPE_REMIND);
		return returnMap;
	}
	
	/**
	 * 取消预约
	 * @author fuxin
	 * @date 2017年12月8日 上午11:23:34
	 * @description 
	 *		TODO
	 * @param vacChildRemind
	 * @return
	 *
	 */
	@RequestMapping("/cancelRemind")
	@ResponseBody
	Map<String, Object> cancelRemind(VacChildRemind vacChildRemind){
		String price="";
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", "500");
		returnMap.put("msg", "取消失败");
		if(StringUtils.isNotBlank(vacChildRemind.getId())){
			try {
				ChildBaseinfo baseinfo = childBaseinfoService.getByNo(vacChildRemind.getChildcode());

				ChildBaseinfo childBaseinfo = childBaseinfoService.convert(baseinfo);
				Map<String, Object> tempParam = new HashMap<String, Object>();
				tempParam.put("childCode", vacChildRemind.getChildcode());//编号
				tempParam.put("username", baseinfo.getChildname());//姓名
				tempParam.put("vaccineName",vacChildRemind.getRemindVacc());//接种疫苗
				tempParam.put("clinicName", childBaseinfo.getOfficeinfo());//接种单位名称officeinfo
				tempParam.put("vatTime", DateUtils.formatDate(vacChildRemind.getRemindDate()));//接种时间
				if(vacChildRemind.getPayPrice() == null || vacChildRemind.getPayPrice() == ""){
					tempParam.put("price", "免费");//疫苗价格 /免费就”免费”
				}else{
					tempParam.put("price", vacChildRemind.getPayPrice());//疫苗价格 /免费就”免费”
				}
				
				asyncService.sendMessageToWX(vacChildRemind.getChildcode(), TYPE_CNACEL_REMIND, tempParam);
				
				vacChildRemindService.delete(vacChildRemind);
				returnMap.put("code", "200");
				returnMap.put("msg", "取消成功");
			} catch (Exception e) {
				logger.error("取消预约失败[" + vacChildRemind.getId() + "]");
			}
		}
		return returnMap;
	}
	
	/**
	 * 接口调用儿童接种提醒查询所有方法
	 * @author fuxin
	 * @date 2017年9月16日 上午11:33:51
	 * @description 
	 *		TODO
	 * @param vacChildRemind
	 * @return
	 *
	 */
	@RequestMapping("/findListApi")
	@ResponseBody
	List<VacChildRemind> findListApi(VacChildRemind vacChildRemind){
		return vacChildRemindService.findList(vacChildRemind);
	}
	
	@RequestMapping("/remindAllList")
	public String remindAllList(ChildBaseinfo childBaseinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<ChildBaseinfo> page = new Page<ChildBaseinfo>(request,response);
		childBaseinfo.setPage(page);
		childBaseinfo.setBeginTime(DateUtils.getDay(new Date()));
		childBaseinfo.setEndTime(DateUtils.getDayEnd(new Date()));
		List<ChildBaseinfo> list = childBaseinfoService.findFinishInDay(childBaseinfo);
		page.setList(list);
		model.addAttribute("page", page);
		
		List<ChildVaccinaterecord> rcvs = new ArrayList<ChildVaccinaterecord>();
		if(list.size() > 0){
			ChildVaccinaterecord childVaccinaterecord = new ChildVaccinaterecord();
			childVaccinaterecord.setVaccinatedateAfter(DateUtils.getDay(new Date()));
			childVaccinaterecord.setVaccinatedateBefore(DateUtils.getDayEnd(new Date()));
			rcvs = childVaccinaterecordService.findList(childVaccinaterecord);
		}
		model.addAttribute("rcvs", JsonMapper.toJsonString(rcvs));
		model.addAttribute("childBaseinfo", childBaseinfo);
		return "modules/remind/remindAllList";
	}
	
	/**
	 * 批量预约操作页面，只加载页面，数据全部使用异步刷新
	 * @author fuxin
	 * @date 2017年12月11日 上午9:35:20
	 * @description 
	 *		TODO
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/remindAllForm")
	public String remindAllForm(ChildBaseinfo childBaseinfo, HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "code", required=false, defaultValue="")String curChildcode){
		ChildBaseinfo temp = new ChildBaseinfo();
		temp.setBeginTime(DateUtils.getDay(new Date()));
		temp.setEndTime(DateUtils.getDayEnd(new Date()));
		List<ChildBaseinfo> list = childBaseinfoService.findFinishInDay(temp);
		model.addAttribute("list", JsonMapper.toJsonString(list));
		model.addAttribute("localCode", OfficeService.getFirstOfficeCode());
		//获取节假日，展示在日历上
		model.addAttribute("weekdays", JsonMapper.toJsonString(holidayService.findAbleWeeks()));
		model.addAttribute("holidays", JsonMapper.toJsonString(holidayService.findAbleDays()));
		int curIdx = 0;
		if(StringUtils.isNotBlank(curChildcode)){
			for(int i = 0; i < list.size(); i ++){
				if(curChildcode.equals(list.get(i).getChildcode())){
					curIdx = i;
					break;
				}
			}
		}
		model.addAttribute("curIdx", curIdx);
		
		return "modules/remind/remindAllForm";
	}

	

}