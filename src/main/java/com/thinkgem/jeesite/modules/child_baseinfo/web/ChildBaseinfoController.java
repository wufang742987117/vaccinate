/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_baseinfo.web;

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

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.Nursery;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.chhe;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.dao.ChildVaccinaterecordDao;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;
import com.thinkgem.jeesite.modules.department.service.SysVaccDepartmentService;
import com.thinkgem.jeesite.modules.kindergarten.entity.BsKindergarten;
import com.thinkgem.jeesite.modules.kindergarten.service.BsKindergartenService;
import com.thinkgem.jeesite.modules.nation.entity.BsNation;
import com.thinkgem.jeesite.modules.nation.service.BsNationService;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.shequ.entity.SysCommunity;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;
import com.thinkgem.jeesite.modules.yiyuan.entity.SysBirthHospital;
import com.thinkgem.jeesite.modules.yiyuan.service.SysBirthHospitalService;

/**
 * 档案管理Controller
 * 
 * @author 王德地
 * @version 2017-02-06
 */
@Controller
@RequestMapping(value = "${adminPath}/child_baseinfo/childBaseinfo")
public class ChildBaseinfoController extends BaseController {
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private SysAreaService sysAreaService;
	@Autowired
	private BsManageVaccinenumService bsManageVaccinenumService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private SysCommunityService sysCommunityService;
	@Autowired
	private SysBirthHospitalService sysBirthHospitalService;
	@Autowired
	private BsNationService bsNationService;
	@Autowired
	private SysVaccDepartmentService sysVaccDepartmentService;
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private BsKindergartenService bsKindergartenService;
	@Autowired
	private ChildVaccinaterecordDao childRecordDao;
	@Autowired
	private BsManageProductService productService;
	@Autowired
	private VacChildRemindService remindService;
	
	private static final String TYPE="01";
	
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
	 * @date 2017年3月3日 下午3:03:58
	 * @description 新增档案
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:baseinfo:view")
	@RequestMapping(value = { "list", "" })
	public String list1(ChildBaseinfo childBaseinfo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		//所有的社区
		List<SysCommunity> list = sysCommunityService.findList(new SysCommunity());
		//所有的出生医院
		List<SysBirthHospital> birthlist = sysBirthHospitalService.findList(new SysBirthHospital());
		//所有的民族
		List<BsNation> nationlist = bsNationService.findList(new BsNation());
		//所有的幼儿园
		List<BsKindergarten> kindergartenlist=bsKindergartenService.findList(new BsKindergarten());
		//所有的接种门诊
		List<SysVaccDepartment> departmentlist=	sysVaccDepartmentService.findList(new SysVaccDepartment());	
		//所有的省
		model.addAttribute("sheng", JsonMapper.toJsonString(sysAreaService.getByPid(0)));
		if (childBaseinfo.getId() == null || childBaseinfo.getId().equals("")) {
			//默认出生日期
			 Calendar c = Calendar.getInstance();
			 c.setTime(new Date());
		     c.add(Calendar.MONTH, -1);
	         Date m = c.getTime();
			childBaseinfo.setBirthday(m);
			//默认胎次
			childBaseinfo.setChildorder("1");
			//默认民族
			childBaseinfo.setNation("01");
			//默认出生医院
			/*childBaseinfo.setBirthhostipal("3406020102");*/
			//默认社区
//			childBaseinfo.setArea("001");
			//默认无异常反应
			childBaseinfo.setParadoxicalreaction("无");
			//接种单位
			String code = OfficeService.getFirstOfficeCode();
			//默认接种单位
			childBaseinfo.setOfficeinfo(code);
			
			model.addAttribute("shi", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(code.subSequence(0, 2)+"0000"))));
			model.addAttribute("qu", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(code.subSequence(0, 4)+"00"))));
			model.addAttribute("defaultShen", Integer.valueOf(code.subSequence(0, 2)+"0000"));
			model.addAttribute("defaultShi", Integer.valueOf(code.subSequence(0, 4)+"00"));
			model.addAttribute("defaultQu",code.subSequence(0, 6));
			model.addAttribute("shi1", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(code.subSequence(0, 2)+"0000"))));
			model.addAttribute("qu1", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(code.subSequence(0, 4)+"00"))));
			model.addAttribute("defaultShen1", Integer.valueOf(code.subSequence(0, 2)+"0000"));
			model.addAttribute("defaultShi1", Integer.valueOf(code.subSequence(0, 4)+"00"));
			model.addAttribute("defaultQu1", code.subSequence(0, 6));
		} else {
			//<li>出生医院code和name 的转换</li> <li>区域name和code的转换</li><li>民族code和name的转换</li><li>接种单位code和name的转换</li>
			/*childBaseinfo=	childBaseinfoService.convert(childBaseinfo);*/
			model.addAttribute("shi", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getProvince()))));
			model.addAttribute("qu", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getCity()))));
			model.addAttribute("defaultShen", childBaseinfo.getProvince());
			model.addAttribute("defaultShi", childBaseinfo.getCity());
			if (StringUtils.isBlank(childBaseinfo.getCounty())) {
				model.addAttribute("defaultQu", "-1");
			} else {
				model.addAttribute("defaultQu", childBaseinfo.getCounty());
			}
			model.addAttribute("shi1", JsonMapper.toJsonString(sysAreaService
					.getByPid(Integer.valueOf(childBaseinfo.getPr()))));
			model.addAttribute("qu1", JsonMapper.toJsonString(sysAreaService
					.getByPid(Integer.valueOf(childBaseinfo.getCi()))));
			model.addAttribute("defaultShen1", childBaseinfo.getPr());
			model.addAttribute("defaultShi1", childBaseinfo.getCi());
			if (StringUtils.isBlank(childBaseinfo.getCo())) {
				model.addAttribute("defaultQu1", "-1");
			} else {
				model.addAttribute("defaultQu1", childBaseinfo.getCo());
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("birthlist", birthlist);
		model.addAttribute("kindergartenlist", kindergartenlist);
		model.addAttribute("nationlist", nationlist);
		model.addAttribute("departmentlist", departmentlist);
		return "modules/child_baseinfo/childBaseinfoForm";
	}

	/**
	 * 自助建档
	 * 
	 * @author wangdedi
	 * @date 2017年3月13日 上午10:34:14
	 * @description TODO
	 * @param childBaseinfo
	 * @param code
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:baseinfo:view")
	@RequestMapping(value = "code")
	public String code(ChildBaseinfo childBaseinfo, @RequestParam(required=false, value="code", defaultValue = "") String code,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, Model model) {
		if(StringUtils.isNotBlank(code) &&  code.matches("^[0-9]*$")){
//			code = OfficeService.getFirstOfficeCode()+code;
		}else{
			addMessage(redirectAttributes, "查询失败，输入格式错误");
			return "redirect:" + Global.getAdminPath() + "/child_baseinfo/childBaseinfo/?repage";
		}
		//所有的社区
		List<SysCommunity> list = sysCommunityService.findList(new SysCommunity());
		//所有的出生医院
		List<SysBirthHospital> birthlist = sysBirthHospitalService.findList(new SysBirthHospital());
		//所有的民族
		List<BsNation> nationlist = bsNationService.findList(new BsNation());
		//所有的接种门诊
		List<SysVaccDepartment> departmentlist=	sysVaccDepartmentService.findList(new SysVaccDepartment());	
		model.addAttribute("list", list);
		model.addAttribute("birthlist", birthlist);
		model.addAttribute("nationlist", nationlist);
		// 网络请求微信服务器，获得自助建档信息
		logger.info("自助建档接口参数："+code);
		String wx=null;
		try {
			wx = HttpClientReq.httpClientPost(Global.getConfig("wechat.url") + "api/childTemp/" + code + ".do", new HashMap<String, String>());
			if(wx.equals("")){
				addMessage(redirectAttributes, "未找到你的自助建档信息");
				return "redirect:" + Global.getAdminPath() + "/child_baseinfo/childBaseinfo/?repage";
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("自助建档接口异常",e);
			addMessage(redirectAttributes, "网络异常，请稍后重试");
			return "redirect:" + Global.getAdminPath() + "/child_baseinfo/childBaseinfo/?repage";
		}
		
		//打印日志
		logger.info("自助建档接口返回值："+wx);
		JSONObject obj = JSONObject.parseObject(wx);
		if (obj.getBoolean("success")) {
			childBaseinfo = (ChildBaseinfo) JsonMapper.fromJsonString(obj.get("data").toString(), ChildBaseinfo.class);
		} else {
			addMessage(redirectAttributes, "未找到你的自助建档信息");
			return "redirect:" + Global.getAdminPath() + "/child_baseinfo/childBaseinfo/?repage";
		}
		/*childBaseinfo.setBirthday(DateUtils.addDays(childBaseinfo.getBirthday(), 1));*/
		model.addAttribute("sheng",JsonMapper.toJsonString(sysAreaService.getByPid(0)));
		model.addAttribute("shi", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getProvince()))));
		model.addAttribute("qu", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getCity()))));
		model.addAttribute("defaultShen", childBaseinfo.getProvince());
		model.addAttribute("defaultShi", childBaseinfo.getCity());
		model.addAttribute("defaultQu", childBaseinfo.getCounty());
		model.addAttribute("shi1", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getPr()))));
		model.addAttribute("qu1", JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getCi()))));
		model.addAttribute("defaultShen1", childBaseinfo.getPr());
		model.addAttribute("defaultShi1", childBaseinfo.getCi());
		model.addAttribute("defaultQu1", childBaseinfo.getCo());
		model.addAttribute("childBaseinfo", childBaseinfo);
		model.addAttribute("departmentlist", departmentlist);
		childBaseinfo.setOfficeinfo(OfficeService.getFirstOfficeCode());
		return "modules/child_baseinfo/childBaseinfoForm";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午2:57:24
	 * @description 修改档案信息时AJAX请求
	 * @param childcodeval
	 * @param childcodeval1
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(value = { "searchData" })
	public @ResponseBody
	ArrayList<Object> searchData(@RequestParam String childcodeval,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ChildBaseinfo childBaseinfo1 = new ChildBaseinfo();
		childBaseinfo1.setChildcode(childcodeval);
		Page<ChildBaseinfo> page = childBaseinfoService.findPage(new Page<ChildBaseinfo>(request, response, -1), childBaseinfo1);
		ChildBaseinfo childBaseinfo = null;
		if (page.getList().size() > 0) {
			childBaseinfo = page.getList().get(0);
		}
		ArrayList<Object> c = new ArrayList<Object>();
		if (childBaseinfo != null) {
			c.add("1111");
			c.add(childBaseinfo);
			if(StringUtils.isBlank(childBaseinfo.getCounty())){
				childBaseinfo.setCounty("");	
			}
			if(StringUtils.isBlank(childBaseinfo.getCo())){
				childBaseinfo.setCo("");	
			}
			c.add(JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getProvince()))));
			c.add(JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getCity()))));
			c.add(JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getPr()))));
			c.add(JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getCi()))));
			
		}else{
			childBaseinfo=new ChildBaseinfo();
			//默认出生日期
			 Calendar cc = Calendar.getInstance();
			 cc.setTime(new Date());
		     cc.add(Calendar.MONTH, -1);
	         Date m = cc.getTime();
			childBaseinfo.setBirthday(m);
			
			//接种单位
			String code = OfficeService.getFirstOfficeCode();
			childBaseinfo.setProvince(code.subSequence(0, 2)+"0000");// 家庭地址的省
			childBaseinfo.setCity(code.subSequence(0, 4)+"00");// 家庭地址的市
			childBaseinfo.setCounty(code.subSequence(0, 6)+"");// 家庭地址的区
			
			childBaseinfo.setPr(code.subSequence(0, 2)+"0000");// 户籍地址的省
			childBaseinfo.setCi(code.subSequence(0, 4)+"00");// 户籍地址的市
			childBaseinfo.setCo(code.subSequence(0, 6)+"");// 户籍地址的区
			
			c.add("");
			c.add(childBaseinfo);
			
			c.add(JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getProvince()))));
			c.add(JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getCity()))));
			c.add(JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getPr()))));
			c.add(JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(childBaseinfo.getCi()))));
		}
		return c;
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午2:57:57
	 * @description 信息保存及修改
	 * @param request
	 * @param response
	 * @param childBaseinfo
	 * @param model
	 * @param redirectAttributes
	 * @param code1
	 * @param code
	 * @param go
	 * @return
	 * 
	 */
	@RequiresPermissions("child:baseinfo:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,
			HttpServletResponse response, ChildBaseinfo childBaseinfo,
			Model model, RedirectAttributes redirectAttributes,
			@RequestParam String code) {
		String codeupload = null;
		//添加的疫苗类型
		String hepbig=childBaseinfo.getHepbig();
		String bcg=childBaseinfo.getBcg();
		String cho=childBaseinfo.getCho();
		String scy=childBaseinfo.getScy();
		String hpy=childBaseinfo.getHpy();
		ArrayList<Object> a =new ArrayList<>();
		//判断选择的疫苗种类
		if(StringUtils.isNoneBlank(hepbig)){
			a.add(hepbig);
			
		}
		if(StringUtils.isNoneBlank(bcg)){
			a.add(bcg);
			
		}
		if(StringUtils.isNoneBlank(cho)){
			a.add(cho);
			
		}
		if(StringUtils.isNoneBlank(scy)){
			a.add(scy);
			
		}
		if(StringUtils.isNoneBlank(hpy)){
			a.add(hpy);
			
		}
		
		if (code != null && !"".equals(code)) {
			if (code.length() != 18) {
				addMessage(redirectAttributes, "你输入的儿童编码不正确");
				return "redirect:" + Global.getAdminPath() + "/child_baseinfo/childBaseinfo";
			}
		}
		ChildBaseinfo childBaseinfo1 = new ChildBaseinfo();
		if (StringUtils.isBlank(code)) {
			code = childBaseinfoService.code();
			childBaseinfo.setLocaltype("0");
			codeupload = code;
		}else{
			if(StringUtils.isBlank(childBaseinfo.getId())){
				childBaseinfo.setLocaltype("1");
			}
		}
		//校验生日
		if(new Date().before(childBaseinfo.getBirthday())){
			addMessage(redirectAttributes, "保存失败,生日不能大于今天");
			return "redirect:" + Global.getAdminPath() + "/child_baseinfo/childBaseinfo";
		}
		
		//接种单位编码
//		Office o = OfficeService.getFirstOffice();
//		childBaseinfo.setOfficeinfo(o.getCode());
		// 对儿童编码进行拼接
		if (StringUtils.isNotBlank(code)) {
			childBaseinfo.setChildcode(code);
			childBaseinfo1.setChildcode(code);
		}
		childBaseinfo.setCreater(UserUtils.getUser().getName());
		String id = childBaseinfo.getId();
		childBaseinfoService.save(childBaseinfo);
		String userId=childBaseinfo.getUserId();
		String childCode=childBaseinfo.getChildcode();

		Map<String, Object> tempParam = new HashMap<String, Object>();
		tempParam.put("childCode", childCode);
		tempParam.put("username", childBaseinfo.getChildname());
		
		
		//新建档案，发送微信推送
		if (id.equals("")) {
			
			asyncService.sendMessageToWXDelay(childCode, TYPE, tempParam,3000);
//			if(!"".equals(childBaseinfo.getFileorigin())&&childBaseinfo.getFileorigin()!=null) {
			//新建卡请求微信服务器,自动关注宝宝
			asyncService.autoAttendChild(userId, childCode, childBaseinfo.getFileorigin());
//			}
			//新建卡,自动预约第一针乙肝
			//asyncService.remindFirstHepB(childBaseinfo, productService, remindService, OfficeService.getFirstOfficeCode());
		}
		
		//上传数据 需配置uploadNewChild=true
		String temp = Global.getConfig("uploadNewChild");
		if(StringUtils.isNotBlank(temp) && "true".equalsIgnoreCase(temp)){
			if(StringUtils.isNotBlank(childBaseinfo.getId()) && StringUtils.isNotBlank(codeupload)){
				asyncService.uploadNewChild(codeupload);
			}
		}
		
		// 循环插入接种记录
		if (id.equals("")) {
			childBaseinfo = childBaseinfoService.findList(childBaseinfo1).get(0);
			BsManageVaccine bsManageVaccine=new BsManageVaccine();
			bsManageVaccine.setExcep("0");
			// 查询所有的出生当天要打的疫苗
			List<BsManageVaccine> list = bsManageVaccineService .findListSimple(bsManageVaccine);
			for (int i = 0; i < a.size(); i++) {
				for (BsManageVaccine bsManageVaccine2 : list) {
					if(bsManageVaccine2.getName().equals(a.get(i))){
						ChildVaccinaterecord childVaccinaterecord = new ChildVaccinaterecord();
						//儿童ID
						childVaccinaterecord.setChildid(childBaseinfo.getId());
						//接种部位
						childVaccinaterecord.setBodypart(String.valueOf(i+1));
						//疫苗小类ID
						childVaccinaterecord.setVaccineid(bsManageVaccine2.getId());
						//接种时间
						childVaccinaterecord.setVaccinatedate(childBaseinfo.getBirthday());
						//状态
						childVaccinaterecord.setStatus(ChildVaccinaterecord.STATUS_YET);
						//出生医院
						childVaccinaterecord.setOffice(childBaseinfo.getBirthhostipal());
						//针次
						childVaccinaterecord.setDosage("1");
						//疫苗大类名称
						childVaccinaterecord.setVaccCode(bsManageVaccine2.getgName());
						//nid
						String t_str=bsManageVaccine2.getgNum();
						childVaccinaterecord.setNid(t_str+"1");
						//标记是否为出生当天接种的
						childVaccinaterecord.setSign("1");
						//疫苗小类名称
						childVaccinaterecord.setVaccName(bsManageVaccine2.getName());
						//数据来源
						childVaccinaterecord.setSource(ChildVaccinaterecord.SOURCE_BL);
						//接种类型
						childVaccinaterecord.setVacctype("1");
						//异常反应
						childVaccinaterecord.setIseffect("0");
						childVaccinaterecordService.save(childVaccinaterecord);
					}
				}
			}
		} else {
			
			ChildVaccinaterecord record=new ChildVaccinaterecord();
			record.setChildid(id);
			record.setSign("1");
			List<ChildVaccinaterecord> recordlist=	childVaccinaterecordService.findList(record);
			//修改建档是补录的疫苗信息接种日期
		for (ChildVaccinaterecord childVaccinaterecord : recordlist) {
			childVaccinaterecord.setVaccinatedate(childBaseinfo.getBirthday());
			childVaccinaterecordService.save(childVaccinaterecord);
		}
		}
		model.addAttribute("code", childBaseinfo.getChildcode());
		return "modules/child_baseinfo/childBaseinfoForm";

	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午2:59:05
	 * @description 数据导出
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(ChildBaseinfo childBaseinfo,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes,
			@RequestParam String beginTime, @RequestParam String endTime,
			@RequestParam String birthbeginTime, @RequestParam String birthendTime) throws ParseException {
		//判断出生日期的格式
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
			//起止时间
		if (StringUtils.isNoneBlank(beginTime)) {
			Date begin = childBaseinfoService.convert(beginTime);
			Date end = childBaseinfoService.convert(endTime);
			Date d[] = childVaccinaterecordService.date(begin, end);
			if (d != null) {
				childBaseinfo.setBeginTime(d[0]);
				childBaseinfo.setEndTime(d[1]);
			}
		}
		
		Page<ChildBaseinfo> page = new Page<ChildBaseinfo>();
		// 根据名字和时间排序
		page.setOrderBy("A.createdate ASC");
		page = childBaseinfoService.findPage(page, childBaseinfo);
		childBaseinfo.setPage(page);
		List<ChildBaseinfo> list = childBaseinfoService.findList(childBaseinfo);
		if (list.size() > 0) {
			for (ChildBaseinfo childBaseinfo2 : list) {
				// 转换地址信息
				childBaseinfo2 = childBaseinfoService.updateAddr(childBaseinfo2);
				childBaseinfo2=	childBaseinfoService.convert(childBaseinfo2);
			}
		}
		try {
			String fileName = "档案数据" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			new ExportExcel("档案数据", ChildBaseinfo.class)
					.setDataList(list).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出档案失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()
				+ "/child_baseinfo/childBaseinfo/?repage";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月9日 下午6:49:51
	 * @description 打印档案信息
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:baseinfo:view")
	@RequestMapping(value = "print")
	public String print(ChildBaseinfo childBaseinfo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if ("".equals(childBaseinfo.getId())) {
			return "modules/child_baseinfo/baseinfo";
		}
		Date date = childBaseinfo.getCreateDate();
		if(date==null){
			date=new Date();
		}
		Calendar calInfo = GregorianCalendar.getInstance();
		calInfo.setTime(date);
		int year = calInfo.get(Calendar.YEAR);
		int month = calInfo.get(Calendar.MONTH) + 1;
		int day = calInfo.get(Calendar.DATE);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		model.addAttribute("childBaseinfo", childBaseinfo);
		childBaseinfo = childBaseinfoService.updateAddr(childBaseinfo);
		String homeaddress = childBaseinfo.getHomeaddress();
		String registryaddress = childBaseinfo.getRegistryaddress();
		String a[] = homeaddress.split(" ");
		String b[] = registryaddress.split(" ");
		
		model.addAttribute("province", "");
		model.addAttribute("city", "");
		model.addAttribute("county", "");
		model.addAttribute("address", "");
		if(a.length > 0){
			model.addAttribute("province", a[0]);
		}
		if(a.length > 1){
			model.addAttribute("city", a[1]);
		}
		if (a.length > 2) {
			model.addAttribute("county", a[2]);
		}
		if (a.length > 3) {
			model.addAttribute("address", a[3]);
		}
		
		model.addAttribute("pr", "");
		model.addAttribute("ci", "");
		model.addAttribute("co", "");
		model.addAttribute("add", "");
		if (b.length > 0) {
			model.addAttribute("pr", b[0]);
		}
		if (b.length > 1) {
			model.addAttribute("ci", b[1]);
		}
		if (b.length > 2) {
			model.addAttribute("co", b[2]);
		}
		if (b.length > 3) {
			model.addAttribute("add", b[3]);
		}
		childBaseinfo=	childBaseinfoService.convert(childBaseinfo);
		return "modules/child_baseinfo/baseinfo";
	}
	
	
	/**
	 * 生成二维码
	 * @author fuxin
	 * @date 2017年4月12日 下午6:12:37
	 * @description 
	 *		TODO
	 * @param response
	 * @param code
	 * @param w
	 * @param h
	 *
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("qrcode/{code}")
	public void qrcode(HttpServletResponse response, @PathVariable("code")String code, 
			@RequestParam(required=false,value="w") int w, @RequestParam(required=false, value="h") int h){
		response.setHeader("Content-Type","image/png");
		//指定大小
		int width = (0==w)?300:w;
		int height = (0==h)?300:h;
		String format = "png";   
		
		HashMap<EncodeHintType, Comparable> hints = new HashMap<EncodeHintType, Comparable>();
		hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H);//纠错等级
		hints.put(EncodeHintType.MARGIN,2);

		// 生成二维码(内容，格式，)
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月10日 下午12:59:37
	 * @description 打印入托证明
	 * @param request
	 * @param response
	 * @param childBaseinfo
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("child:baseinfo:view")
	@RequestMapping(value = "prove")
	public String prove(HttpServletRequest request,
			HttpServletResponse response, ChildBaseinfo childBaseinfo,
			Model model) {
		if (StringUtils.isBlank(childBaseinfo.getId())) {
			return "modules/child_baseinfo/table";
		}
		// 查询已接种的一类疫苗
		List<Nursery> one = childBaseinfoService.one(childBaseinfo);
		String notid="";
		for (int i = 0; i <one.size(); i++) {
			if(i!=one.size()-1){
				notid=notid+"'"+one.get(i).getId()+"',";
			}else{
				notid=notid+"'"+one.get(i).getId()+"'";
			}
			
		}
		if(StringUtils.isNoneBlank(notid)){
			childBaseinfo.setNotid(notid);
		}
		
		// 查询已接种的二类疫苗
		List<Nursery> two = childBaseinfoService.two(childBaseinfo);

		// 查询儿童所有未接种的记录
		List<BsManageVaccinenum> VaccList = bsManageVaccinenumService.getVaccNameList(childBaseinfo.getChildcode());
		
		// 已接种一类疫苗数组转树形
		String op = "first";
		int a = 0;
		int b = 0;
		List<Nursery> templist = new ArrayList<>();
		List<List<Nursery>> returnlist = new ArrayList<>();
		for (int i = 0; i < one.size(); i++) {
			if(StringUtils.isNoneBlank(one.get(i).getVaccinatedate())){
				one.get(i).setVaccinatedate(one.get(i).getVaccinatedate().substring(0, 10));
			}
			
			if (op.equals("first")) {
				op = one.get(i).getName();
			}
			if (!op.equals(one.get(i).getName())) {
				returnlist.add(templist);
				templist = new ArrayList<>();
				op = one.get(i).getName();

			}
			if (templist.size() == 0 && i != 0) {
				if (b == 0) {
					one.get(i - i).setSize(i - a);
				}
				one.get(i - (i - b)).setSize(i - a);
				b = i;
				a = i;
			}

			templist.add(one.get(i));
			if (i == one.size() - 1) {
				one.get(i - (i - b)).setSize(i - a + 1);
			}
		}

		returnlist.add(templist);
		model.addAttribute("list", returnlist);
		// 数组转树形 结束

		// 已接种二类疫苗数组转树形
		op = "first";
		a = 0;
		b = 0;
		templist = new ArrayList<>();
		returnlist = new ArrayList<>();
		for (int i = 0; i < two.size(); i++) {
			if(StringUtils.isNoneBlank(two.get(i).getVaccinatedate())){
				two.get(i).setVaccinatedate(two.get(i).getVaccinatedate().substring(0, 10));
			}
			if (op.equals("first")) {
				op = two.get(i).getName();
			}
			if (!op.equals(two.get(i).getName())) {
				returnlist.add(templist);
				templist = new ArrayList<>();
				op = two.get(i).getName();

			}
			if (templist.size() == 0 && i != 0) {
				if (b == 0) {
					two.get(i - i).setSize(i - a);
				}
				two.get(i - (i - b)).setSize(i - a);
				b = i;
				a = i;
			}

			templist.add(two.get(i));
			if (i == two.size() - 1) {
				two.get(i - (i - b)).setSize(i - a + 1);
			}
		}
		returnlist.add(templist);
		// 数组转树形 结束
		model.addAttribute("list1", returnlist);

		// 未种一类苗预约时间
		VaccList = bsManageVaccinenumService.setReserveDate(VaccList,childBaseinfo.getId());
		
		// 未接种一类疫苗数组转树形
		op = "first";
		a = 0;
		b = 0;
		List<BsManageVaccinenum> temp = new ArrayList<>();
		List<List<BsManageVaccinenum>> returnlist1 = new ArrayList<>();
		for (int i = 0; i < VaccList.size(); i++) {
			if (op.equals("first")) {
				op = VaccList.get(i).getName();
			}
			if (!op.equals(VaccList.get(i).getName())) {
				returnlist1.add(temp);
				temp = new ArrayList<>();
				op = VaccList.get(i).getName();

			}
			if (temp.size() == 0 && i != 0) {
				if (b == 0) {
					VaccList.get(i - i).setSize(i - a);
				}
				VaccList.get(i - (i - b)).setSize(i - a);
				b = i;
				a = i;
			}
			temp.add(VaccList.get(i));
			if (i == VaccList.size() - 1) {
				VaccList.get(i - (i - b)).setSize(i - a + 1);
			}
		}
		returnlist1.add(temp);
		// 数组转树形 结束
		model.addAttribute("list2", returnlist1);
		model.addAttribute("childBaseinfo", childBaseinfo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		model.addAttribute("date", date);
		return "modules/child_baseinfo/table";
	}
	
	/**
	 * 异地迁入搜索页
	 * @author fuxin
	 * @date 2017年7月21日 下午1:38:30
	 * @description 
	 *		TODO
	 * @return
	 */
	@RequestMapping("/ingoingForm")
	public String ingoingForm(Model model){
		List<SysVaccDepartment> depts = sysVaccDepartmentService.findList(new SysVaccDepartment());
		model.addAttribute("depts", JsonMapper.toJsonString(depts));
		List<SysCommunity> coms = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("coms", JsonMapper.toJsonString(coms));
		return "modules/child_baseinfo/ingoingForm";
	}
	
	
	@RequestMapping("/upLoaddata")
	public @ResponseBody String upLoaddata(Model model){
		return HttpClientReq.webServiceInvoke("aboutuploadEx", new Object[]{OfficeService.getFirstOfficeCode()}, logger);
	}
	
	/**
	 * 删除个案
	 * @author liyuan
	 * @date 2018年2月28日 下午1:23:52
	 * @description 
	 *		TODO
	 * @param childBaseinfo
	 * @return
	 *
	 */
	@RequestMapping(value = "del")
	public @ResponseBody String delete(ChildBaseinfo childBaseinfo) {
		childBaseinfo.setSituation("5");
		if(childBaseinfo.getRemarks()!=null) {
			if(childBaseinfo.getRemarks().endsWith("删除后恢复")) {
				childBaseinfo.setRemarks(childBaseinfo.getRemarks().substring(0, childBaseinfo.getRemarks().length()-5));
			}
		}
		childBaseinfoService.save(childBaseinfo);
		return  "删除个案成功";
	}
	
	/**
	 * 恢复个案
	 * @author liyuan
	 * @date 2018年2月28日 下午1:24:16
	 * @description 
	 *		TODO
	 * @param childBaseinfo
	 * @return
	 *
	 */
	@RequestMapping(value = "restore")
	public @ResponseBody String restoreinfo(ChildBaseinfo childBaseinfo) {
		if (StringUtils.isBlank(childBaseinfo.getId()) ||  !("5".equals(childBaseinfo.getSituation()))) {
			return "500";
		}
		childBaseinfo.setSituation("1");
		String remarks = childBaseinfo.getRemarks()==null?"":childBaseinfo.getRemarks();
		childBaseinfo.setRemarks(remarks+"删除后恢复");
		childBaseinfoService.save(childBaseinfo);
		return  "200";
	}
	
	
	/**
	 * 个案对比
	 * @return
	 */
	@RequestMapping("/compareForm")
	public String compareForm(Model model,@RequestParam String id){
		ChildBaseinfo childBaseinfo = childBaseinfoService.get(id);
		if(childBaseinfo != null){
			childBaseinfo.setMouage(DateUtils.getMouthAge(childBaseinfo.getBirthday()));
		}
		String officeinfo=childBaseinfo.getOfficeinfo()+"";
		String larea=childBaseinfo.getArea()+"";
		childBaseinfo=	childBaseinfoService.convert(childBaseinfo);
		if (childBaseinfo != null) {
			model.addAttribute("childBaseinfo", childBaseinfo);
			model.addAttribute("islocal", officeinfo.equals(OfficeService.getFirstOfficeCode())?"1":officeinfo);
			model.addAttribute("larea", larea);	
			// 查询儿童所有已接种的记录
			ChildVaccinaterecord childVaccinaterecord = new ChildVaccinaterecord();
			childVaccinaterecord.setChildid(childBaseinfo.getId());
			childVaccinaterecord.setOrderBy(" SUBSTR(A .VACCINEID, 0, 2), SUBSTR(A .NID, 0, 2) DESC,A.vacctype ,A .DOSAGE ");
			// 根据儿童的ID和创建时间排序
			List<ChildVaccinaterecord> childVaccinaterecordList =ChildVaccinaterecordService.findList4(childRecordDao.findList(childVaccinaterecord), true);
				// 数组转树形 结束	
			model.addAttribute("returnlist",childVaccinaterecordList);
		}
		List<SysVaccDepartment> depts = sysVaccDepartmentService.findList(new SysVaccDepartment());
		model.addAttribute("depts", JsonMapper.toJsonString(depts));
		List<SysCommunity> coms = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("coms", JsonMapper.toJsonString(coms));
		return "modules/child_baseinfo/compareForm";
	}
	
	/**
	 * 在册变更
	 * @return
	 */
	@RequestMapping("/changeSitu")
	public String changeSitu(Model model,@RequestParam String id){
		ChildBaseinfo childBaseinfo = childBaseinfoService.get(id);
		if(childBaseinfo == null){
			return "modules/child_baseinfo/baseinfo";  
		}
		model.addAttribute("childBaseinfo", childBaseinfo);
		List<SysCommunity> coms = sysCommunityService.findList(new SysCommunity());
		model.addAttribute("coms", coms);
		return "modules/child_baseinfo/changeSitu";
	}
	
	/**
	 * 保存在册变更
	 * @return
	 */
	@RequestMapping("/savechangeSitu")
	public @ResponseBody String savechangeSitu(Model model,@RequestParam String id,
			@RequestParam String reside,@RequestParam String situation,
			@RequestParam String guardianmobile,@RequestParam String address,
			@RequestParam String area,@RequestParam String remarks){
		ChildBaseinfo childBaseinfo = childBaseinfoService.get(id);
		if(childBaseinfo == null){
			return "modules/child_baseinfo/baseinfo";
		}
		if(!childBaseinfo.getSituation().equals(situation)){
			chhe c=new chhe();
			c.setChhe_changedate(DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			c.setChhe_chil_id(childBaseinfo.getChildcode());
			c.setChhe_depa_id(OfficeService.getFirstOfficeCode());
			c.setChhe_prehere(childBaseinfo.getSituation());
			c.setLocalCode(OfficeService.getFirstOfficeCode());
			c.setChhe_here(situation);
			c.setChhe_editstate("0");
			c.setChhe_chil_provinceremark(remarks);
			childBaseinfoService.insertChhe(c);
		}
		childBaseinfo.setArea(area);
		childBaseinfo.setReside(reside);
		childBaseinfo.setSituation(situation);
		childBaseinfo.setGuardianmobile(guardianmobile);
		childBaseinfo.setAddress(address);
		if(childBaseinfo.getRemarks()!=null && !childBaseinfo.getRemarks().isEmpty()){
			remarks=childBaseinfo.getRemarks()+ ""+remarks;			
		}
		childBaseinfo.setRemarks(remarks);
		childBaseinfoService.save(childBaseinfo);
		return "200";
	}

	/**
	 * 请求儿童信息接口
	 * @author fuxin
	 * @date 2017年12月11日 上午9:34:45
	 * @description 
	 *		TODO
	 * @param childBaseinfo
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/getApi")
	public @ResponseBody ChildBaseinfo getApi(ChildBaseinfo childBaseinfo, Model model){
		if(StringUtils.isBlank(childBaseinfo.getId()) || StringUtils.isBlank(childBaseinfo.getChildcode())){
			childBaseinfo = childBaseinfoService.getByNo(childBaseinfo.getChildcode(),childBaseinfo.getLocalCode());
		}
		Map<String, String> dicts = new HashMap<String, String>();
		dicts.put("sex", DictUtils.getDictLabel(childBaseinfo.getGender(), "sex", ""));
		dicts.put("situation", DictUtils.getDictLabel(childBaseinfo.getSituation(), "situation", ""));
		childBaseinfoService.convert(childBaseinfo);
		childBaseinfo.setMouthAgeInt(DateUtils.subtractMonths(childBaseinfo.getBirthday(), new Date()));
		childBaseinfo.setDicts(dicts);
		return childBaseinfo;
	}
}
