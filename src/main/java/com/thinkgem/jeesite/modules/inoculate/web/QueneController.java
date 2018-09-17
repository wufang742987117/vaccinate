/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inoculate.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;
import com.thinkgem.jeesite.modules.inoculate.vo.QuenelogVo;
import com.thinkgem.jeesite.modules.product.entity.BsManageCheck;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageCheckService;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;

/**
 * 排队叫号管理Controller
 * @author fuxin
 * @version 2017-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/inoculate/quene")
public class QueneController extends BaseController {

	@Autowired
	private QueneService queneService;
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private BsManageVaccinenumService bsManageVaccinenumService;
	@Autowired
	private BsManageProductService productService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BsManageCheckService bsManageCheckService;
	
	@ModelAttribute
	public Quene get(@RequestParam(required=false) String queueno,
			@RequestParam(required=false) String localCode) {
		Quene entity = null;
		if (StringUtils.isNotBlank(queueno)){
			Quene q = new Quene(StringUtils.EMPTY, queueno);
			if(StringUtils.isNotBlank(localCode)){
				q.setLocalCode(localCode);
			}
			if(StringUtils.isNotBlank(q.getLocalCode())){
				entity = queneService.get(q);
			}else{
				entity = new Quene();
			}
		}
		if (entity == null){
			entity = new Quene();
		}
		return entity;
	}
	
	/**
	 * 排队列表
	 * @param quene
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("inoculate:quene:view")
	@RequestMapping(value = {"list"})
	public String list(Quene quene, HttpServletRequest request, HttpServletResponse response, Model model) {
		quene.setIspass("N");
		//TODO: fuxin获取科室类型
		quene.setRoomcode(officeService.getOfficeCode());
		Page<Quene> p = new Page<Quene>();
		p.setPageSize(200);
		Page<Quene> page = queneService.findPage(p, quene); 
		page.setPageSize(page.getList().size());
		page.setList(page.getList());
		model.addAttribute("page", page);
		model.addAttribute("ispass", "N");
		return "modules/inoculate/queneList";
	}
	
	/**
	 * 过号列表
	 * @param quene
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("inoculate:quene:view")
	@RequestMapping(value = {"listY"})
	public String listY(Quene quene, HttpServletRequest request, HttpServletResponse response, Model model) {
		quene.setIspass("Y");
		//TODO: fuxin获取科室类型
		quene.setRoomcode(officeService.getOfficeCode());
		Page<Quene> p = new Page<Quene>();
		p.setPageSize(200);
		Page<Quene> page = queneService.findPage(p, quene); 
		model.addAttribute("page", page);
		model.addAttribute("ispass", "Y");
		return "modules/inoculate/queneList";
	}
	
	/**
	 * 完成列表
	 * @param quene
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("inoculate:quene:view")
	@RequestMapping(value = {"listF"})
	public String listF(Quene quene, HttpServletRequest request, HttpServletResponse response, Model model) {
		//TODO: fuxin获取科室类型
		quene.setRoomcode(officeService.getOfficeCode());
		quene.setStatus(Quene.STATUS_FINSH);
		quene.setOrderBy("a.CREATETIME DESC");
		Page<Quene> p = new Page<Quene>();
		p.setPageSize(200);
		Page<Quene> page = queneService.findPage(p, quene); 
		model.addAttribute("page", page);
		model.addAttribute("ispass", "F");
		List<Quene> quenelist=queneService.total(officeService.getOfficeCode());
		model.addAttribute("quenelist", quenelist);
		int	total=0;
		for (Quene quene2 : quenelist) {
			total=total+Integer.valueOf(quene2.getTotal());
		}
		model.addAttribute("total", total);
		return "modules/inoculate/queneList";
	}
	
	/**
	 * 接种台首页
	 * @author fuxin
	 * @date 2017年3月14日 下午7:06:41
	 * @description 
	 *		TODO
	 * @param model
	 * @return
	 *
	 */
	@RequiresPermissions("inoculate:quene:view")
	@RequestMapping("inoculateIndex")
	public String inoculateHead(Model model, HttpServletRequest request){
//		String vaccineStr =  officeService.getOfficeVaccines();
//		if(StringUtils.isNotBlank(vaccineStr)){
			List<BsManageProduct> list = new ArrayList<>();
//			vaccineStr = "'" + vaccineStr.replace(",", "','") + "'";
//			List<BsManageProduct> productList = productService.getQueneHeadNumPad(vaccineStr);
			List<BsManageProduct> productList = productService.getQueneHeadNumPad();
			model.addAttribute("productList", productList);
			model.addAttribute("vaccines",list);

//		}
		Map<String, String> map=new HashMap<String, String>();
//		if(StringUtils.isNoneBlank(vaccineStr)){
//			map.put("vaccineStr", vaccineStr);
//		}else{
//			map.put("vaccineStr", "''");
//		}
	
		map.put("days", Global.getConfig("days"));
		List<BsManageProduct> indatelist=queneService.indate(map);
		if(indatelist.size()>0){
			model.addAttribute("indatelist", indatelist);
		}
		//host
		StringBuffer url = request.getRequestURL();
		//对子字符串进行匹配
		Matcher slashMatcher = Pattern.compile("/").matcher(url); 
		int end = 0;
		//尝试查找与该模式匹配的输入序列的下一个子序列
		while(slashMatcher.find()) {  
			end++;  
	        //当modelStr字符第count次出现的位置  
	        if(end == 3){  
	           break;  
	        }  
	    }  
		int num = slashMatcher.start();
		String host = url.substring(0,num);
		model.addAttribute("host", host);
		//单位
		String offceCode = officeService.getOfficeCode();
		model.addAttribute("officeCode", offceCode);
		return "modules/inoculate/inoculateHead";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月6日 下午5:17:47
	 * @description 
	 *		推送消息
	 * @param model
	 * @param quene
	 * @param request
	 * @param response
	 * @param context
	 * @return
	 *
	 */
	@RequestMapping(value = "msgPost")
	public @ResponseBody String msgPost(Model model,Quene quene, HttpServletRequest StringBuilder, 
			HttpServletResponse response,@RequestParam String context,@RequestParam String video) {
		//推送消息
		if(context.length() != 0 || video.length() != 0){
			queneService.refreshMsg(context,video,OfficeService.getFirstOfficeCode());
			return "success";
		}else{
			return "";
		}
	}
	@RequestMapping(value = "msgPostTo")
	public String msgPostTo(Model model){
		return "modules/inoculate/queneSocketForm";
	}
	
	
	/**
	 * 排号操作首页
	 */
	@RequiresPermissions("inoculate:quene:view")
	@RequestMapping(value = {"inoculateMain", ""})
	public String inoculateMain(Model model,RedirectAttributes redirectAttributes, Quene quene, 
			@RequestParam(value="callNumber", required = false ,defaultValue="false")String callNumber) {
		
		//盘点日期
		String checkDay = DateUtils.formatDate(new Date(), "yyyyMMdd");
		model.addAttribute("checkDate", checkDay);
		//盘点是否提示盘点
		if(UserUtils.getUserPreference().getCheckReq() == 1){
			String lastCheckDate = DateUtils.formatDate(DateUtils.addDays(new Date(),-1), "yyyyMMdd");
			BsManageCheck tempCheck = new BsManageCheck();
			tempCheck.setCheckDate(lastCheckDate);
			tempCheck.setCreateBy(UserUtils.getUser());
			List<BsManageCheck> checks = bsManageCheckService.findList(tempCheck);
			if(checks.size() == 0){
				return "redirect:"+Global.getAdminPath()+"/product/bsManageCheck/tables?checkDate=" + lastCheckDate;
			}
		}
		
		//获取已过号队列
		List<Quene> ListY = queneService.getPassY();
				model.addAttribute("queneY", ListY.size());
				//获取未过号队列
				List<Quene> ListN = queneService.getPassNDelay();
				model.addAttribute("queneN", ListN.size());
				
				List<Quene> ListF = queneService.getFinshed();
				model.addAttribute("finished", ListF.size());
				
				//根据已过号id获取是否[正常显示]还是[显示已过号的信息]
				ChildBaseinfo childBaseinfo = null;
 				if(StringUtils.isNotBlank(quene.getQueueno())){
					//验证该id是否在过号队列
					boolean isExits = false;
					for(Quene q : ListY){
						if(quene.getQueueno().equals(q.getQueueno())){
							isExits = true;
						}
					}
					for(Quene q : ListN){
						if(quene.getQueueno().equals(q.getQueueno())){
							isExits = true;
						}
					}
					//若该id无效，则不操作
					if(!isExits){
						if(StringUtils.isNotBlank(quene.getChildid())){
							return "redirect:"+Global.getAdminPath()+"/inoculate/quene/inoculateMain?childid=" + quene.getChildid();
						}
						addMessage(redirectAttributes, "该编号不存在");
						return "redirect:"+Global.getAdminPath()+"/inoculate/quene/list/N";
					}
					//过号存在，本次请求已过号数据
					childBaseinfo = childBaseinfoService.convert(childBaseinfoService.getByNo(String.valueOf(quene.getChildid())));
//					model.addAttribute("isPass", "yes");
					//更新显示已过号统计
				}else if(StringUtils.isNotBlank(quene.getChildid())){
					//通过儿童编号直接查询
					childBaseinfo = childBaseinfoService.convert(childBaseinfoService.getByNo(quene.getChildid()));
					//查询儿童是否在队列中
					for(Quene q : ListY){
						if(quene.getChildid().equals(q.getChildid())){
							quene = q;
						}
					}
					List<Quene> tn = queneService.getPassN();
					for(Quene q : tn){
						if(quene.getChildid().equals(q.getChildid())){
							quene = q;
						}
					}
				}else if(ListN.size() > 0){
					//过号不存在，本次请求未过号数据
					childBaseinfo = childBaseinfoService.convert(childBaseinfoService.getByNo(String.valueOf(ListN.get(0).getChildid())));
					//更新显示未过号统计
					quene = ListN.get(0);
				}
				else{
					addMessage(model, "当前无任务");
				}
 				if(childBaseinfo != null){
 					childBaseinfo.setMouage(DateUtils.getMouthAge(childBaseinfo.getBirthday()));
 				}
				model.addAttribute("childBaseinfo", childBaseinfo);
				model.addAttribute("quene", quene);
				model.addAttribute("bsNum", bsManageVaccinenumService.get(quene.getVaccineid()));
				
				
				//若幼儿信息存在，则获取接种记录
				if(childBaseinfo != null && StringUtils.isNotBlank(childBaseinfo.getId())){
					//获取幼儿接种记录
					ChildVaccinaterecord record = new ChildVaccinaterecord();
					record.setChildid(childBaseinfo.getId());
					record.setOrderBy(" A .STATUS, SUBSTR(A .VACCINEID, 0, 2), SUBSTR(A .NID, 0, 2) DESC,A.vacctype ,A .DOSAGE ");
					if(StringUtils.isNotBlank(quene.getVaccineid())){
						record.setQueneWhere(quene.getVaccineid());
					}else{
						record.setStatus(ChildVaccinaterecord.STATUS_YET);
					}
					List<ChildVaccinaterecord> rcs = childVaccinaterecordService.findList(record);
					
					if(rcs.size() > 0 && StringUtils.isNotBlank(quene.getVaccineid())){
						//获取接种记录，并分类显示
						List<ChildVaccinaterecord> qlist = new ArrayList<ChildVaccinaterecord>();
						for(int i = 1; i < rcs.size(); i ++){
							if(rcs.get(i).getVaccineid().substring(0,2).equals(rcs.get(0).getVaccineid().substring(0,2))){
								qlist.add(rcs.get(i));
								rcs.remove(i);
								i --;
							}
						}
						if(rcs.size() > 0){
							qlist.add(rcs.get(0));
							rcs.remove(0);
							rcs.addAll(0, qlist);
						}
					}
					
					String op = "first";
					int idx = 0;
					int size = 0;
					for(int i = 0; i < rcs.size(); i ++){
					
						if("first".equals(op)){
							idx = i;
							op = rcs.get(i).getVaccCode();
						}
						if(!op.equals(rcs.get(i).getVaccCode())){
							rcs.get(idx).setSize(size);
							idx = i;
							size = 0;
							op = rcs.get(i).getVaccCode();
						}
						size ++;
						
					}
					if(size != 0){
						rcs.get(idx).setSize(size);
					}
					model.addAttribute("records", rcs);
				}	
				
				//是否立即叫号
				if("true".equals(callNumber) && StringUtils.isNotBlank(quene.getQueueno())){
					callNumber(quene);
				}
				//获取站点编码
				model.addAttribute("zdbm", OfficeService.getFirstOfficeCode());
				return "modules/inoculate/inoculateMain";
	}

	@RequiresPermissions("inoculate:quene:view")
	@RequestMapping(value = "form")
	public String form(Quene quene, Model model) {
		model.addAttribute("quene", quene);
		return "modules/inoculate/queneForm";
	}

	@RequiresPermissions("inoculate:quene:edit")
	@RequestMapping(value = "save")
	public String save(Quene quene, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, quene)){
			return form(quene, model);
		}
		queneService.save(quene);
		addMessage(redirectAttributes, "保存排队叫号管理成功");
		return "redirect:"+Global.getAdminPath()+"/inoculate/quene/list/?repage";
	}
	
	@RequiresPermissions("inoculate:quene:edit")
	@RequestMapping(value = "delete")
	public String delete(Quene quene, RedirectAttributes redirectAttributes) {
		queneService.delete(quene);
		addMessage(redirectAttributes, "删除排队叫号管理成功");
		return "redirect:"+Global.getAdminPath()+"/inoculate/quene/list/?repage";
	}
	
	/**
	 * 叫号操作
	 * @return 
	 */
	@RequestMapping("callNumber")
	public @ResponseBody String callNumber(Quene quene) {
		try {
//			quene = queneService.get(quene);
			Quene nextQuene = null;
			List<Quene> qlist = queneService.getPassN(quene);
			for(Quene q: qlist){
				if(q.getQueueno().equals(quene.getQueueno())){
					continue;
				}
				nextQuene = q;
				break;
			}
			//TODO:此处叫号对接接口
			queneService.refresh(quene,nextQuene,quene.getLocalCode());
		} catch (Exception e) {
			return "叫号失败";
		}
		return "叫号成功";
	}
	
	/**
	 * 过号操作<br>
	 * 改变叫号队列数据状态
	 * @return 
	 */
	@RequiresPermissions("inoculate:quene:edit")
	@RequestMapping("doPass")
	public Object doPass(Quene quene, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(quene.getQueueno())){
			queneService.doPass(quene);
			//刷新叫号屏
			queneService.refresh(OfficeService.getFirstOfficeCode());
			addMessage(redirectAttributes, "过号操作成功");
		}else{
			addMessage(redirectAttributes, "过号操作失败");
		}
		return "redirect:"+Global.getAdminPath()+"/inoculate/quene";
	}
	
	/**
	 * 完成操作操作<br>
	 * <ol>
	 * 	<li>删除排号信息 </li>
	 * 	<li>录入接种信息 </li>
	 * </ol>
	 */
	@RequiresPermissions("inoculate:quene:edit")
	@RequestMapping("doFinish")
	@Transactional(readOnly = false)
	public Object doFinish(Quene quene, RedirectAttributes redirectAttributes, @NotNull @RequestParam(value="selected") String selected, 
			@RequestParam(value="position") String position, @RequestParam(value="isnew", required=false, defaultValue = "0") String isnew) {
		quene.setPosition(position);
		if(quene == null || !Quene.STATUS_NORMAL.equals(quene.getStatus())){
			addMessage(redirectAttributes, "排号状态异常,完成操作失败");
			return "redirect:"+Global.getAdminPath()+"/inoculate/quene";
		}
		if(StringUtils.isNoneBlank(selected) || StringUtils.isNoneBlank(quene.getChildid())){
			if(StringUtils.isNotBlank(quene.getChildid())){
				//查询档案信息
				ChildBaseinfo baseinfo = childBaseinfoService.getByNo(quene.getChildid());
				if(baseinfo == null){
					addMessage(redirectAttributes, "完成操作失败,儿童信息异常");
				}
				try {
					queneService.doFinish(quene, selected, baseinfo, isnew);
					//刷新叫号屏
					queneService.refresh(OfficeService.getFirstOfficeCode());
					//更新在册状态
					if(!"1".equals(baseinfo.getSituation())){
						baseinfo.setSituation("1");
						baseinfo.setUpdateDate(new Date());
						childBaseinfoService.save(baseinfo);
					}
					
				} catch (Exception e) {
					addMessage(redirectAttributes, e.getMessage());
					return "redirect:"+Global.getAdminPath()+"/inoculate/quene";
				}
				
				addMessage(redirectAttributes, "完成操作成功");
			}else{
				addMessage(redirectAttributes, "完成操作失败,参数错误");
			}
		}else{
			addMessage(redirectAttributes, "完成操作失败,请选择疫苗");
		}
		
		return "redirect:"+Global.getAdminPath()+"/inoculate/quene";
	}
	
	/**
	 * 完成操作操作<br>
	 * <ol>
	 * 	<li>删除排号信息 </li>
	 * 	<li>录入接种信息 </li>
	 * </ol>
	 */
	@RequiresPermissions("inoculate:quene:edit")
	@RequestMapping("/doFinishApi")
	@Transactional(readOnly = false)
	public @ResponseBody Object[] doFinishApi(Quene quene, RedirectAttributes redirectAttributes, @NotNull @RequestParam(value="selected") String selected, 
			@RequestParam(value="position") String position, @RequestParam(value="isnew", required=false, defaultValue = "0") String isnew) {
		Object[] msg = {"500",""};
		if(quene == null || !Quene.STATUS_NORMAL.equals(quene.getStatus())){
			msg[1] = "排号状态异常,完成操作失败";
			return msg;
		}
		quene.setPosition(position);
		if(StringUtils.isNoneBlank(selected) || StringUtils.isNoneBlank(quene.getChildid())){
			if(StringUtils.isNotBlank(quene.getChildid())){
				//查询档案信息
				ChildBaseinfo baseinfo = childBaseinfoService.getByNo(quene.getChildid());
				if(baseinfo == null){
					msg[1] = "完成操作失败,儿童信息异常";
				}
				try {
					ChildVaccinaterecord cvd = queneService.doFinish(quene, selected, baseinfo, isnew);
						cvd.setBodypart(DictUtils.getDictLabel(cvd.getBodypart(), "position", ""));
						cvd.setVacctype(DictUtils.getDictLabel(cvd.getVacctype(), "vacctype", ""));
					msg[1] = cvd;
					msg[0]="200";
					//刷新叫号屏
					queneService.refresh(OfficeService.getFirstOfficeCode());
					//更新在册状态
					if(!"1".equals(baseinfo.getSituation())){
						baseinfo.setSituation("1");
						baseinfo.setUpdateDate(new Date());
						childBaseinfoService.save(baseinfo);
					}
				} catch (Exception e) {
					logger.error("完成操作失败",e);
					msg[1] =  "完成操作失败";
				}
			}else{
				msg[1] =  "完成操作失败,参数错误";
			}
		}else{
			msg[1] = "完成操作失败,请选择疫苗";
		}
		
		return msg;
	}
	
	/**
	 * 取消操作操作<br>
	 * <ol>
	 * 	<li>删除排号信息 </li>
	 * 	<li>录入接种信息 </li>
	 * </ol>
	 */
	@RequiresPermissions("inoculate:quene:edit")
	@RequestMapping("doCancel")
	@Transactional(readOnly = false)
	public Object doCancel(Quene quene, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(quene.getQueueno())){
			String msg = queneService.doCancel(quene);
			//刷新叫号屏
			queneService.refresh(OfficeService.getFirstOfficeCode());
			addMessage(redirectAttributes, msg);
		}else{
			addMessage(redirectAttributes, "取消操作失败");
		}
		
		return "redirect:"+Global.getAdminPath()+"/inoculate/quene";
	}
	
	/**
	 * 接种台确认接种弹出层
	 * @author fuxin
	 * @date 2017年8月14日 下午11:43:11
	 * @description 
	 *		TODO
	 * @param quene
	 * @param model
	 * @return
	 *
	 */
	@RequiresPermissions("inoculate:quene:edit")
	@RequestMapping("confirm")
	public Object confirm(Quene quene, Model model) {
		if(StringUtils.isNotBlank(quene.getQueueno())){
			//根据计划id查询所有产品小类
			BsManageVaccinenum num = bsManageVaccinenumService.get(quene.getVaccineid());
			
			//若计划外的排号生成虚拟计划信息
			if(num==null){
				num=new BsManageVaccinenum();
				num.setId(quene.getVaccineid());
				num.setGroup(quene.getVaccineid().substring(0, 2));
			}
			
			//根据pid查询产品信息，附加默认值
			List<BsManageProduct> vaccs = productService.findByMnum(num.getGroup());
			model.addAttribute("vacclist", vaccs);
			BsManageProduct pro = productService.get(quene.getPid());
			model.addAttribute("pid", quene.getPid());
			model.addAttribute("mouthAge", childBaseinfoService.getMouAge(quene.getChildid()));
			if(pro != null){
				model.addAttribute("vacc", pro.getVaccineid());
			}
		}else{
			return "";
		}
		return "modules/inoculate/confirm";
	}
	
	/**
	 * 接种台定时刷新请求
	 * @author fuxin
	 * @date 2017年8月14日 下午11:42:47
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("checkQuene")
	public @ResponseBody String checkQuene(){
		//获取未过号队列
		List<Quene> ListN = queneService.getPassNDelay();
		if(ListN.size() > 0){
			return "refresh";
		}
		return "";
	}
	
	/**
	 * 接种日志
	 * @author fuxin
	 * @date 2017年7月26日 下午5:19:19
	 * @description 
	 *		TODO
	 * @param record
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "quenelog")
	public String quenelog(ChildVaccinaterecord record, Model model,HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="includeBlue", required=false, defaultValue="false")String includeBlue,@RequestParam(required = false) String type) {
		if(null == record){
			record = new ChildVaccinaterecord();
		}
		//查询医生信息
		model.addAttribute("doctorlist", UserUtils.getCompanyUsers());

		//默认医生
		if(record.getDoctor() == null){
			record.setDoctor(UserUtils.getUser().getName());
		}
		
		//获取所有小类信息
		BsManageVaccine vac = new BsManageVaccine();
		//设置为儿童疫苗，排除成人
		List<BsManageVaccine> vaccinates = bsManageVaccineService.findListChild(vac);
		String vacc = "";
		//设置默认值
		if(StringUtils.isNotBlank(record.getVacselected())){
			vacc = record.getVacselected();
		}else{
			vacc = officeService.getOfficeVaccines();
			record.setVacselected(vacc);
		}
		if(StringUtils.isNotBlank(vacc)){
			record.setVacselected("'" + record.getVacselected().replaceAll(",", "','") + "'");
		}
		model.addAttribute("vacc", vacc);
		
		//查询接种记录
		Date[] d = childVaccinaterecordService.date(record.getBeginTime(),record.getEndTime());
		if (!StringUtils.isNotBlank(record.getBegin()) ||  !StringUtils.isNotBlank(record.getEnd()) || d == null){
			addMessage(model, "日期输入错误");
			record.setBeginTime(DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd") + " 00:00:00"));// 表示当天的零点);
			record.setEndTime(new Date());
		}else{
			record.setBeginTime(DateUtils.parseDate(DateUtils.formatDate(record.getBeginTime(), "yyyy-MM-dd") + " 00:00:00"));// 表示当天的零点);
			record.setEndTime(DateUtils.parseDate(DateUtils.formatDate(record.getEndTime(), "yyyy-MM-dd") + " 23:59:59"));// 表示当天的零点);
		}
		//已接种
		record.setStatus(ChildVaccinaterecord.STATUS_YET);
		//排除五联和补录
		if("false".equals(includeBlue)){
			record.setSourceIn("'" + ChildVaccinaterecord.SOURCE_DJT + "','" + ChildVaccinaterecord.SOURCE_WX + "','" + ChildVaccinaterecord.SOURCE_YTJ + "'");
		}
		

		//承接实例
		List<ChildVaccinaterecord> returnList = new ArrayList<>();
		List<List<ChildVaccinaterecord>> returnListAll = new ArrayList<List<ChildVaccinaterecord>>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		int num = 0;
		//不是打印接种明细功能
		if(StringUtils.isBlank(type)){
			//排序条件
			Page<ChildVaccinaterecord> p = new Page<ChildVaccinaterecord>();
			p.setOrderBy("A .vacc_name , a.dosage");
			record.setPage(p);
			List<ChildVaccinaterecord> list = childVaccinaterecordService.findList(record);
			model.addAttribute("record", record);
			
			//循环处理业务
			for (ChildVaccinaterecord rd : list) {
				//用户签字
				if(rd.getSignature().equals("1") && rd.getStype().equals("1")){
					try {
						rd.setSignList(new String(rd.getSignatureList(),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				returnList.add(rd);
			}
			model.addAttribute("returnList", returnList);
			
			//统计儿童数
			Set<String> childcodes = new HashSet<String>();
			for(ChildVaccinaterecord r:list){
				childcodes.add(r.getChildcode());
			}
			model.addAttribute("childCount", childcodes.size());
			
			//默认勾选并排序
			String[] vaccs;
			List<BsManageVaccine> ordervacc = new ArrayList<>();
			if(StringUtils.isNotBlank(vacc) && (vaccs = vacc.split(",")).length > 0){
				for(int i = 0; i < vaccs.length; i ++){
					for(int j = 0; j < vaccinates.size(); j++){
						if(vaccs[i].equals(vaccinates.get(j).getmNum()) || vaccs[i].equals(vaccinates.get(j).getId())){
							ordervacc.add(vaccinates.get(j));
							vaccinates.remove(j);
							j--;
						}
					}
				}
				vaccinates.addAll(0, ordervacc);
			}
			model.addAttribute("vacclist", vaccinates);
			
			
			//统计分析
			//基础表
			String option = "first";
			List<ChildVaccinaterecord> temp = new ArrayList<ChildVaccinaterecord>();
			List<List<ChildVaccinaterecord>> listSimple = new ArrayList<>();
			for(ChildVaccinaterecord rec : list){
				rec.setMouage(DateUtils.getMouthAge(rec.getBirthday()));
				if(option.equals("first")){
					option = rec.getVaccName();
				}
				if(!option.equals(rec.getVaccName())){
					listSimple.add(temp);
					option = rec.getVaccName();
					temp = new ArrayList<ChildVaccinaterecord>();
				}
				temp.add(rec);
			}
			if(temp.size() > 0){
				listSimple.add(temp);
			}
			
			List<QuenelogVo> simpleVo = queneService.toQuenelogSimpleVo(listSimple);
			List<QuenelogVo> detailVo = queneService.toQuenelogDetailVo(listSimple);
			model.addAttribute("simplelist", simpleVo);
			model.addAttribute("detaillist", detailVo);
			//基础表结束
			model.addAttribute("sysdata",DateUtils.formatDate(new Date()));
			model.addAttribute("includeBlue", includeBlue);
			
			return "modules/inoculate/quenelog";
		}else if("1".equals(type)){
			//排序条件
			Page<ChildVaccinaterecord> p = new Page<ChildVaccinaterecord>();
			p.setOrderBy("A.vaccinatedate");
			record.setPage(p);
			List<ChildVaccinaterecord> list = childVaccinaterecordService.findList(record);
			model.addAttribute("record", record);
			//循环处理业务
			for (ChildVaccinaterecord rd : list) {
				//累计个数
				num++;
				//累计数量
				if(StringUtils.isNotBlank(rd.getVaccName())){
					if(map.get(rd.getVaccName()) == null){
						map.put(rd.getVaccName(), 1);
					}else{
						int count = map.get(rd.getVaccName());
						count++;
						map.put(rd.getVaccName(), count);
					}
				}
				
				//用户签字
				if(rd.getSignature().equals("1") && rd.getStype().equals("1")){
					try {
						rd.setSignList(new String(rd.getSignatureList(),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				returnList.add(rd);
				
				//判断累计个数
				if(num >= 16){
					returnListAll.add(returnList);
					returnList = new ArrayList<ChildVaccinaterecord>();
					num = 0;
				}
			}
			if(num > 0){
				returnListAll.add(returnList);
			}
			model.addAttribute("returnList", returnListAll);
			model.addAttribute("map", JsonMapper.toJsonString(map));
			//医生名称
			model.addAttribute("docaterName",record.getDoctor());
			//接种单位
			model.addAttribute("officeName",OfficeService.getFirstOffice().getName());
			return "modules/child_vaccinaterecord/inoculationLogPrint";
		}
		return "modules/inoculate/quenelog";
	}
	
	/**
	 * 检查是否存在的排号
	 * @author fuxin
	 * @date 2017年8月14日 下午11:42:05
	 * @description 
	 *		TODO
	 * @param no
	 * @return
	 *
	 */
	@RequestMapping("/checkQueneNo")
	public @ResponseBody String checkQueneNo(Quene quene) {
		if(null != quene && StringUtils.isNotBlank(quene.getQueueno())){
			Quene q=queneService.get(quene.getQueueno());
			if(null!=q && !Quene.STATUS_DEL.equals(q.getStatus())){
				return quene.getQueueno();
			}
		}
		return "";
	}
	
	
	/**
	 * 清除排号缓存
	 * @author fuxin
	 * @date 2017年8月22日 下午11:18:11
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("/clearQueneCache")
	public @ResponseBody int clearCache() {
		int count = 0;
		try {
			Office o1 = new Office();
			o1.setParentIds("0,");
			List<Office> offices = officeService.findList(o1);
			for(Office o : offices){
				CacheUtils.remove(QueneService.CACHE_KEY_QUENE + o.getCode());
				count ++;
			}
		} catch (Exception e) {
			logger.error("清空缓存API错误",e);
		}
		return count;
	}
	
	
	/**
	 * 扫码支付开关,关闭功能则更改待交费为正常状态
	 * @author fuxin
	 * @date 2017年8月22日 下午11:17:47
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("/togglePay")
	public @ResponseBody boolean togglePay() {
		if(Global.getInstance().isPayOption()){
			List<Quene> fp=queneService.getForPay();
			if(!fp.isEmpty()){
				for(Quene q : fp){
					q.setStatus(Quene.STATUS_NORMAL);
					queneService.update(q);
				}
			}
			queneService.refresh(OfficeService.getFirstOfficeCode());
		}
		return Global.getInstance().togglePayOption();
	}
	
	/**
	 * 一体机签字开关,关闭功能则将关闭一体机签字
	 * @author fuxin
	 * @date 2017年8月22日 下午11:17:47
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("/toggleReserve")
	public @ResponseBody boolean toggleReserve() {
		return Global.getInstance().toggleReserveOption();
	}
	
	
	/**
	 * 预约确认开关,关闭功能则将预约信息直接排号
	 * @author fuxin
	 * @date 2017年8月22日 下午11:17:47
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("/toggleQuick")
	public @ResponseBody boolean toggleQuick() {
		return Global.getInstance().toggleQuickOption();
	}
	
	/**
	 * 打印小票开关,关闭功能则将登记台不会产生小票
	 * @author fuxin
	 * @date 2017年8月22日 下午11:17:47
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("/toggleReceipt")
	public @ResponseBody boolean toggleReceipt() {
		return Global.getInstance().toggleReceiptOption();
	}
	
}