/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitis.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckin;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckinStock;
import com.thinkgem.jeesite.modules.hepatitis.service.BsHepatitisBcheckinService;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.service.BsHepatitisBNumService;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckin;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;

/**
 * 乙肝信息管理Controller
 * 
 * @author xuejinshan
 * @version 2017-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/hepatitis/bsHepatitisBcheckin")
public class BsHepatitisBcheckinController extends BaseController {

	@Autowired
	private AsyncService asyncService;
	@Autowired
	private BsManageProductService productService;
	@Autowired
	private BsHepatitisBNumService bsHepatitisBNumService;
	@Autowired
	private BsHepatitisBcheckinService bsHepatitisBcheckinService;
	@Autowired
	private BsRabiesCheckinService bsRabiesCheckinService;

	@ModelAttribute
	public BsHepatitisBcheckin get(@RequestParam(required = false) String id) {
		BsHepatitisBcheckin entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = bsHepatitisBcheckinService.get(id);
		}
		if (entity == null) {
			entity = new BsHepatitisBcheckin();
		}
		return entity;
	}

	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:41:26
	 * @description TODO 进入乙肝管理页面
	 * @param bsHepatitisBcheckin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = { "list", "" })
	public String list(BsHepatitisBcheckin bsHepatitisBcheckin, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/hepatitis/bsHepatitisBcheckinList";
	}
	
	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:50:13
	 * @description TODO 查询乙肝用户基本信息
	 * @param bsHepatitisBcheckin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "namelist")
	public String nameList(BsHepatitisBcheckin bsHepatitisBcheckin, @RequestParam(required=false, value="searchName") String searchName, HttpServletRequest request, HttpServletResponse response, Model model) {
		//初始化犬伤对象
		BsRabiesCheckin bsRabiesCheckin = new BsRabiesCheckin();
		List<BsRabiesCheckin> list = new ArrayList<BsRabiesCheckin>();
		// 判断id是否存在
		if (StringUtils.isNoneBlank(bsHepatitisBcheckin.getId())) {
			model.addAttribute("id", bsHepatitisBcheckin.getId());
			model.addAttribute("search", "0");
			return "modules/hepatitis/hepachildlist";
		}
		try {
			String search = URLDecoder.decode(searchName,"utf-8");
			if(search.substring(0,1).equals("H") && search.length() >= 20 && search.substring(search.length()-1, search.length()).equals("A")){
				bsHepatitisBcheckin.setSearchName(search.substring(0, search.length()-1));
				model.addAttribute("search", "1");
			}else{
				bsHepatitisBcheckin.setSearchName(search);
				bsRabiesCheckin.setSearchName(search);
				model.addAttribute("search", "0");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 根据名字或者电话或者身份证号查询所有的个案
		List<BsHepatitisBcheckin> namelist = bsHepatitisBcheckinService.namelist(bsHepatitisBcheckin);
		List<BsRabiesCheckin> bsRabiesCheckins = bsRabiesCheckinService.namelist(bsRabiesCheckin);
		if (namelist.size() == 0 && bsRabiesCheckins.size() == 0) {
			model.addAttribute("arg", "未查到建档信息请重新输入");
			return "modules/hepatitis/hepachildlist";
		} else if (namelist.size() == 1) {
			bsHepatitisBcheckin = namelist.get(0);
			model.addAttribute("id", bsHepatitisBcheckin.getId());
			return "modules/hepatitis/hepachildlist";
		}else if(bsRabiesCheckins.size() == 1){
			bsRabiesCheckin = bsRabiesCheckins.get(0);
			model.addAttribute("id", bsRabiesCheckin.getId()); 
			return "redirect:" + Global.getAdminPath() + "/rabiesvaccine/bsRabiesCheckin/namelist";
		}
		//转换当前档案的总针次
		namelist = bsHepatitisBcheckinService.updateNameList(namelist);
		bsRabiesCheckins = bsRabiesCheckinService.updateNameList(bsRabiesCheckins);
		for (BsRabiesCheckin rabiesCheckin : bsRabiesCheckins) {
			rabiesCheckin = bsRabiesCheckinService.updatVaccName(rabiesCheckin);
			list.add(rabiesCheckin);
		}
		model.addAttribute("namelist", namelist);
		model.addAttribute("bsRabiesCheckins", bsRabiesCheckins);
		model.addAttribute("bsHepatitisBcheckin", bsHepatitisBcheckin);
		return "modules/hepatitis/hepachildlist";
	}

	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:45:38
	 * @description TODO 展示乙肝信息
	 * @param bsHepatitisBcheckin
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "form")
	public String form(BsHepatitisBcheckin bsHepatitisBcheckin, @RequestParam String id, Model model, HttpServletRequest request) {
		//查询该条记录
		if(StringUtils.isNotBlank(bsHepatitisBcheckin.getId())){
			bsHepatitisBcheckin.setId(id);
		}
		String search = "0";
		if(StringUtils.isNotBlank(bsHepatitisBcheckin.getSearch())){
			search = bsHepatitisBcheckin.getSearch();
		}
		//重新查询
		bsHepatitisBcheckin = bsHepatitisBcheckinService.get(bsHepatitisBcheckin);
		if(bsHepatitisBcheckin == null){
			return "redirect:" + Global.getAdminPath() +"/rabiesvaccine/bsRabiesCheckin/form?id="+id+"&search="+search;
		}
		//转换地址信息
		bsHepatitisBcheckin = bsHepatitisBcheckinService.updateAddr(bsHepatitisBcheckin);
		//转换疫苗名称
		bsHepatitisBcheckin = bsHepatitisBcheckinService.updatVaccName(bsHepatitisBcheckin);
		//赋值search
		bsHepatitisBcheckin.setSearch(search);
		model.addAttribute("bsHepatitisBcheckin", bsHepatitisBcheckin);
		// 清除用户缓存
		UserUtils.clearCache();
		//自动流程权限
		model.addAttribute("openStatus", UserUtils.getUser().getOpenStatus());
		//自动打印权限
		model.addAttribute("printStatus", UserUtils.getUser().getPrintStatus());
		//调价权限
		model.addAttribute("specialStatus", UserUtils.getUser().getSpecialStatus());
		// 查询疫苗针次记录
		BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
		bsHepatitisBNum.setCheckId(bsHepatitisBcheckin.getId());
		//接种记录
		int count = 0;
		int bsCount = 0;
		int signCount = 0;
		List<BsHepatitisBNum> bsHepatitisBNumsList = bsHepatitisBNumService.findById(bsHepatitisBNum);
		for(BsHepatitisBNum bs : bsHepatitisBNumsList){
			if(BsHepatitisBNum.STATUS_PAYMAL.equals(bs.getPayStatus())){
				count++;
			}
			if(BsHepatitisBNum.STATUS_FINSH.equals(bs.getStatus())){
				bsCount++;
			}
			if(BsHepatitisBNum.SIGNATURE_YES.equals(bs.getSignature())){
				signCount++;
			}
		}
		model.addAttribute("Numlist", bsHepatitisBNumsList);
		//签字总数
		String signType = "0";
		if(signCount == bsHepatitisBNumsList.size()){
			signType = "1";
		}
		model.addAttribute("signType", signType);
		//展示当前疫苗付款与接种统计
		String payMessage = "";
		if(count != bsHepatitisBNumsList.size() && bsCount != bsHepatitisBNumsList.size()){
			payMessage = "已付款"+ count +"支，已接种"+ bsCount +"支";
		}else if(count == bsHepatitisBNumsList.size() && bsCount != bsHepatitisBNumsList.size()){
			payMessage = "全款付清，已接种"+ bsCount +"支";
		}else if(count != bsHepatitisBNumsList.size() && bsCount == bsHepatitisBNumsList.size()){
			payMessage = "已付款"+ count +"支，全部接种";
		}else if(count == bsHepatitisBNumsList.size() && bsCount == bsHepatitisBNumsList.size()){
			payMessage = "全款付清，全部接种";
		}
		model.addAttribute("payMessage", payMessage);
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
		return "modules/hepatitis/bsHepatitisBcheckinList";
	}

	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:47:33
	 * @description TODO 进入添加乙肝信息
	 * @param bsHepatitisBcheckin
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "update")
	public String update(BsHepatitisBcheckin bsHepatitisBcheckin, @RequestParam String type, Model model) {
		//部分实体参数赋初值
		bsHepatitisBcheckin = bsHepatitisBcheckinService.updateBsHepaCheckin(bsHepatitisBcheckin);
		//判断新增或修改
		bsHepatitisBcheckinService.updatePcc(bsHepatitisBcheckin,model);
		if (StringUtils.isNotBlank(bsHepatitisBcheckin.getId())) {
			//转化默认接种日期
			bsHepatitisBcheckin = bsHepatitisBNumService.updateNewDate(bsHepatitisBcheckin);
		}
		//疫苗类型
		List<VaccInfo> list = bsHepatitisBcheckinService.getQueryVacc(null);
		model.addAttribute("vaccInfoList", list);
		//进入渠道
		model.addAttribute("type",type);
		model.addAttribute("bsHepatitisBcheckin", bsHepatitisBcheckin);
		return "modules/hepatitis/bsHepatitisBcheckinForm";
	}
	
	/**
	 * 查询对应疫苗类型的疫苗信息
	 * @author zhouqj
	 * @date 2017年10月10日 下午3:15:24
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	@RequestMapping("/findAllVaccNameApi")
	@ResponseBody
	List<BsManageProduct> findAllVaccNameApi(BsHepatitisBcheckin bsHepatitisBcheckin){
		//公共查询疫苗类型配置
		BsManageVaccine bsManageVaccine = bsHepatitisBcheckinService.queryVacc(bsHepatitisBcheckin.getVaccType());
		// 查询疫苗信息（除犬伤）
		List<BsManageProduct> productList = bsHepatitisBcheckinService.vaccineTypeHepa(bsManageVaccine);
		return productList;
	}
	
	/**
	 * 查询疫苗配置
	 * @author zhouqj
	 * @date 2017年12月26日 下午8:42:03
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	@RequestMapping("/findVaccInterVer")
	@ResponseBody 
	String findVaccInterVer(BsHepatitisBcheckin bsHepatitisBcheckin){
		//查询疫苗配置
		VaccInfo vaccInfo = bsHepatitisBcheckinService.queryVaccInterVal(bsHepatitisBcheckin.getVaccType(), null ,VaccInfo.DEFAULTAEFOR);
		return vaccInfo.getDefaultNeedle();
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年8月12日 下午12:10:55
	 * @description 
	 *		TODO 自助建档
	 * @param bsHepatitisBcheckin
	 * @param code
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "code")
	public String code(BsHepatitisBcheckin bsHepatitisBcheckin, @RequestParam String code,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, Model model) {
		if(StringUtils.isBlank(code) || !code.matches("^[0-9]*$")){
			addMessage(redirectAttributes, "输入格式错误,存在非数字");
			return "redirect:" + Global.getAdminPath() + "/hepatitis/bsHepatitisBcheckin/update?repage&type=0";
		}
		// 网络请求微信服务器，获得自助建档信息
		logger.info("自助建档接口参数："+code);
		String wx = null;
		try {
			wx = HttpClientReq.httpClientPost(Global.getConfig("wpwxdoge.url") + "api/hepbTemp/" + code + ".do", new HashMap<String, String>());
			if(wx.equals("") || wx.contains("<html>")){
				addMessage(redirectAttributes, "未找到你的自助建档信息");
				return "redirect:" + Global.getAdminPath() + "/hepatitis/bsHepatitisBcheckin/update?repage&type=0";
			}
		} catch (Exception e) {
			logger.error("自助建档接口异常",e);
			addMessage(redirectAttributes, "网络异常，请稍后重试");
			return "redirect:" + Global.getAdminPath() + "/hepatitis/bsHepatitisBcheckin/update?repage&type=0";
		}
		//打印日志
		logger.info("自助建档接口返回值："+wx);
		if(wx != null){
			JSONObject obj = JSONObject.parseObject(wx);
			if (obj.getBoolean("success")) {
				bsHepatitisBcheckin = (BsHepatitisBcheckin) JsonMapper.fromJsonString( obj.get("data").toString(), BsHepatitisBcheckin.class);
			} else {
				addMessage(redirectAttributes, "未找到你的自助建档信息");
				return "redirect:" + Global.getAdminPath() + "/hepatitis/bsHepatitisBcheckin/update?repage&type=0";
			}
			//部分实体参数赋初值
			bsHepatitisBcheckin = bsHepatitisBcheckinService.updateBsHepaCheckin(bsHepatitisBcheckin);
			//判断地址是否存在
			bsHepatitisBcheckinService.updatePcc(bsHepatitisBcheckin,model);
			//疫苗类型
			List<VaccInfo> list = bsHepatitisBcheckinService.getQueryVacc(null);
			model.addAttribute("vaccInfoList", list);
			//进入渠道
			model.addAttribute("type","0");
			model.addAttribute("bsHepatitisBcheckin", bsHepatitisBcheckin);
		}
		return "modules/hepatitis/bsHepatitisBcheckinForm";
	}

	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:48:37
	 * @description TODO 保存乙肝信息
	 * @param bsHepatitisBcheckin
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "save")
	public String save(BsHepatitisBcheckin bsHepatitisBcheckin, Model model, RedirectAttributes redirectAttributes) {
		// 清除用户缓存
		UserUtils.clearCache();
		//填充创建者
		bsHepatitisBcheckin.setCreateBy(UserUtils.getUser());
		// 判断是否新建档案
		boolean isNew = StringUtils.isBlank(bsHepatitisBcheckin.getId());
		//疫苗类型
		String vaccType = bsHepatitisBcheckin.getVaccType();
		//疫苗小类编码
		String vaccCode = bsHepatitisBcheckin.getVaccineName();
		//疫苗厂家
		String vaccMan = bsHepatitisBcheckin.getManufacturer();
		// 添加新档案时对针次信息的处理
		int vnum = Integer.valueOf(bsHepatitisBcheckin.getDosage());
		if (isNew) {
			// 新增时设置档案编号
			bsHepatitisBcheckin.setHepaBcode("H" + bsHepatitisBcheckinService.codedog(vaccType) + vaccType);
			int count = bsHepatitisBcheckinService.countHepaBcode(bsHepatitisBcheckin);
			if(count != 0){
				model.addAttribute("msg", "该编号档案已存在！");
				return "modules/hepatitis/bsHepatitisBcheckinForm";
			}
			if(StringUtils.isNotBlank(bsHepatitisBcheckin.getOpenId())){
				Map<String, String> wxmap = new HashMap<>();
				wxmap.put("openid",bsHepatitisBcheckin.getOpenId());
				wxmap.put("username",bsHepatitisBcheckin.getUsername());
				wxmap.put("checkno",bsHepatitisBcheckin.getHepaBcode());
				wxmap.put("nexttime", "2");
				asyncService.sendWxMessage(Global.getConfig("wpwxdoge.url") + "api/sendWxTempMsgUp.do",JsonMapper.toJsonString(wxmap));
			}
		}
		// 保存乙肝疫苗接种者信息
		bsHepatitisBcheckinService.save(bsHepatitisBcheckin);
		addMessage(redirectAttributes, "保存信息成功");
		model.addAttribute("id", bsHepatitisBcheckin.getId());
		//记录列表
		List<BsHepatitisBNum> bsList = bsHepatitisBcheckinService.finishpinNum(bsHepatitisBcheckin);
		//查询疫苗配置
		VaccInfo vaccInfo = bsHepatitisBNumService.updateVaccInterVal(vaccType,vaccCode,vaccMan,vnum);
		if (isNew || bsList.size() == 0) {
			for (int i = 0; i < vnum; i++) {
				bsHepatitisBNumService.saveNew(bsHepatitisBcheckin,vaccInfo,vnum,i,"1");
			}
		}else{
			//修改狂苗信息
			for(BsHepatitisBNum bs : bsList){
				if((bs.getRealDate() == null || !"1".equals(bs.getStatus())) && "0".equals(bs.getnStatus()) ){
					bs.setPid(bsHepatitisBcheckin.getPid());
					bs.setBatch(bsHepatitisBcheckin.getBatch());
					bs.setDose(bsHepatitisBcheckin.getStandard());
					bs.setStandard(bsHepatitisBcheckin.getStandard());
					bs.setVaccineId(bsHepatitisBcheckin.getVaccineName());
					bs.setManufacturer(bsHepatitisBcheckin.getManufacturer());
					bs.setVaccType(bsHepatitisBcheckin.getVaccType());
					bsHepatitisBNumService.save(bs);
				}
			}
		}
		return "modules/hepatitis/bsHepatitisBcheckinForm";
	}

	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:49:27
	 * @description TODO 删除乙肝信息
	 * @param bsHepatitisBcheckin
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "delete")
	public String delete(BsHepatitisBcheckin bsHepatitisBcheckin, RedirectAttributes redirectAttributes) {
		// 清除用户缓存
		UserUtils.clearCache();
		//初始化产品
		BsManageProduct product = new BsManageProduct();
		//初始化记录
		BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
		bsHepatitisBNum.setCheckId(bsHepatitisBcheckin.getId());
		//删除档案
		bsHepatitisBcheckinService.delete(bsHepatitisBcheckin);
		//查询记录信息
		List<BsHepatitisBNum> bsList = bsHepatitisBNumService.findByCheckId(bsHepatitisBNum);
		for(BsHepatitisBNum bs : bsList){
			//查询产品信息
			product = bsHepatitisBNumService.findBsProduct(bs.getPid());
			if (null != product && StringUtils.isNoneBlank(product.getId())) {
				product.setStorenum(product.getStorenum() + 1);
				productService.save(product);
				bs.setnStatus(BsHepatitisBNum.NSTATUS_BEFOR);
				bs.setRealDate(null);
				bs.setPayStatus(BsHepatitisBNum.STATUS_NORMAL);
				bs.setStatus(BsHepatitisBNum.STATUS_NOFINSH);
				bs.setCreateBy(UserUtils.getUser());
				bsHepatitisBNumService.save(bs);
				bsHepatitisBNumService.delete(bs);
			}
		}
		//删除记录
		bsHepatitisBNumService.deleteByCheckid(bsHepatitisBNum);
		addMessage(redirectAttributes, "删除疫苗信息管理成功");
		return "redirect:"+Global.getAdminPath()+"/hepatitis/bsHepatitisBcheckin/?repage";
	}

	/**
	 * 个案统计
	 * 
	 * @author xuejinshan
	 * @date 2017年7月31日 下午3:52:15
	 * @description TODO
	 * @param bsHepatitisBcheckin
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "bsCheckin")
	public String bsCheckin(BsHepatitisBcheckin bsHepatitisBcheckin, Model model, HttpServletRequest request, HttpServletResponse response) {
		Date d[] = bsHepatitisBcheckinService.dateTime(bsHepatitisBcheckin.getBeginTime(), bsHepatitisBcheckin.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsHepatitisBcheckin.setBeginTime(d[0]);
			bsHepatitisBcheckin.setEndTime(d[1]);
		} else {
			return "modules/hepatitis/bsHepatitisBcheckin";
		}
		//疫苗类型
		List<VaccInfo> list = bsHepatitisBcheckinService.getQueryVacc(null);
		model.addAttribute("vaccInfoList", list);
		if(StringUtils.isBlank(bsHepatitisBcheckin.getVaccType())){
			bsHepatitisBcheckin.setVaccType("2");
		}else if(bsHepatitisBcheckin.getVaccType().equals("0")){
			bsHepatitisBcheckin.setVaccType(null);
		}
		//查询统计数据
		Page<BsHepatitisBcheckin> page = bsHepatitisBcheckinService.findPage(new Page<BsHepatitisBcheckin>(request, response), bsHepatitisBcheckin);
		for (BsHepatitisBcheckin bs : page.getList()) {
			bs = bsHepatitisBcheckinService.updateAddr(bs);
			//转换疫苗名称
			bs = bsHepatitisBcheckinService.updatVaccName(bs);
			//查询疫苗类型
			bs.setVaccType(bsHepatitisBcheckinService.getQueryVaccName(bs.getVaccType()));
		}
		model.addAttribute("page", page);
		return "modules/hepatitis/bsHepatitisBcheckin";
	}
	
	/**
	 * 个案记录导出
	 * @author xuejinshan
	 * @date 2017年8月3日 上午11:17:27
	 * @description TODO 导出乙肝疫苗个案记录
	 * @param bsHepatitisBcheckin
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(BsHepatitisBcheckin bsHepatitisBcheckin, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		Date d[] = bsHepatitisBcheckinService.dateTime(bsHepatitisBcheckin.getBeginTime(), bsHepatitisBcheckin.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsHepatitisBcheckin.setBeginTime(d[0]);
			bsHepatitisBcheckin.setEndTime(d[1]);
		} else {
			return "modules/hepatitis/bsHepatitisBcheckin";
		}
		//疫苗类型
		if(bsHepatitisBcheckin.getVaccType().equals("0")){
			bsHepatitisBcheckin.setVaccType(null);
		}
		List<BsHepatitisBcheckin> page = bsHepatitisBcheckinService.findList(bsHepatitisBcheckin);
		for (BsHepatitisBcheckin bs : page) {
			bs = bsHepatitisBcheckinService.updateAddr(bs);
			//转换疫苗名称
			bs = bsHepatitisBcheckinService.updatVaccName(bs);
			//查询疫苗类型
			bs.setVaccType(bsHepatitisBcheckinService.getQueryVaccName(bs.getVaccType()));
		}
		try {
			String fileName = "成人疫苗个案记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			new ExportExcel("成人疫苗个案记录", BsHepatitisBcheckin.class).setDataList(page).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出成人疫苗个案记录失败！失败信息：" + e.getMessage());
		}
		return "modules/hepatitis/bsHepatitisBcheckin";
	}
	
	/**
	 * 库存明细页面的展示
	 * @author xuejinshan
	 * @date 2017年8月11日 下午7:14:39
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckinStock
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "bsCheckinStock")
	public String bsCheckinStock(BsHepatitisBcheckinStock  bsHepatitisBcheckinStock, Model model, HttpServletRequest request, HttpServletResponse response) {
		//疫苗类型
		List<VaccInfo> list = bsHepatitisBcheckinService.getQueryVacc(null);
		model.addAttribute("vaccInfoList", list);
		if(StringUtils.isNotBlank(bsHepatitisBcheckinStock.getVaccType()) && bsHepatitisBcheckinStock.getVaccType().equals("0")){
			bsHepatitisBcheckinStock.setVaccType(null);
		}
		//库存统计
		List<List<BsHepatitisBcheckinStock>> returnlistOne = bsHepatitisBcheckinService.vaccineHepaStock(bsHepatitisBcheckinStock);
		model.addAttribute("bsList", returnlistOne);
		return "modules/hepatitis/bsHepatitisBcheckinStock";
	}
	
	/**
	 * 打印库存明细
	 * @author xuejinshan
	 * @date 2017年8月10日 下午9:36:26
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckinStock
	 * @param model
	 * @param beginTime
	 * @param endTime
	 * @return
	 *
	 */
	@RequestMapping(value = "printStock")
	public String printStock(BsHepatitisBcheckinStock bsHepatitisBcheckinStock, Model model) {
		//疫苗类型
		if(bsHepatitisBcheckinStock.getVaccType().equals("0")){
			bsHepatitisBcheckinStock.setVaccType(null);
		}
		//库存统计
		List<List<BsHepatitisBcheckinStock>> returnlistOne = bsHepatitisBcheckinService.vaccineHepaStock(bsHepatitisBcheckinStock);
		model.addAttribute("bsList", returnlistOne);
		model.addAttribute("date", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		return "modules/hepatitis/bsHepatitisBCheckinStockPrint";
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年8月9日 下午3:21:25
	 * @description 
	 *		TODO 打印接种小票
	 * @param bsHepatitisBcheckin
	 * @param items
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printInvoice")
	public @ResponseBody Map<String, Object> printInvoice(BsHepatitisBcheckin bsHepatitisBcheckin,@RequestParam List<String> items, @RequestParam String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer check = new StringBuffer();
		//总价格
		double total = 0;
		//初始化产品
		BsManageProduct product = new BsManageProduct();
		BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
		//疫苗支数
		int count = items.size();
		if(count != 0){
			//查询首条记录信息
			for(String id : items){
				check.append(id + ",");
				bsHepatitisBNum = bsHepatitisBNumService.get(id);
				product = bsHepatitisBNumService.updatePidByName(bsHepatitisBNum,product);
				if(product == null){
					map.put("error","打印小票失败，请检查疫苗库存和所属科室");
					return map;
				}
				if(product.getStorenum() <= 0){
					map.put("error","打印小票失败，("+ bsHepatitisBNum.getManufacturer() + "-" + bsHepatitisBNum.getBatch() +")库存为空，请重新登记选择疫苗。");
					return map;
				}
				if(BsHepatitisBNum.FUNDSTATUS_YES.equals(bsHepatitisBNum.getFundStatus())){
					total += bsHepatitisBNum.getCurrentPrice();
				}else{
					total += product.getSellprice();
				}
			}
			//查询首条记录信息
			BsHepatitisBNum bs = new BsHepatitisBNum();
			bs = bsHepatitisBNumService.get(items.get(0));
			if(bs == null){
				map.put("error","打印小票失败，记录信息不存在");
				return map;
			}
			//查询疫苗类型
			String vaccName = bsHepatitisBcheckinService.getQueryVaccName(bsHepatitisBcheckin.getVaccType());
			if(vaccName.contains("疫苗")){
				map.put("vaccinatename", vaccName);
			}else{
				map.put("vaccinatename", vaccName + "疫苗");
			}
			map.put("pid", bs.getPid());
			map.put("manufacturer", bs.getManufacturer());
			map.put("dose", DictUtils.getDictLabel(String.valueOf(bs.getVaccineNum()), "pin", ""));
			map.put("status", DictUtils.getDictLabel(bs.getStatus(), "wstatus", ""));
			//规格 standard
			if(StringUtils.isNotBlank(bs.getStandard())){
				map.put("standard",bs.getStandard());
			}else{
				map.put("standard", "???");
			}
			//统计已付款记录
			int num = 0;
			bsHepatitisBNum = new BsHepatitisBNum();
			bsHepatitisBNum.setCheckId(bsHepatitisBcheckin.getId());
			List<BsHepatitisBNum> numlist = bsHepatitisBNumService.findById(bsHepatitisBNum);
			for(BsHepatitisBNum returnBs : numlist){
				if(BsHepatitisBNum.STATUS_PAYMAL.equals(returnBs.getPayStatus())){
					num++;
				}
			}
			String payMessage = "";
			if(num == numlist.size()){
				payMessage = "全款付清";
			}else{
				payMessage = "已付"+ num +"支";
			}
			map.put("paystatus", DictUtils.getDictLabel(bs.getPayStatus(), "paystatus", "") + "(" + payMessage + ")");
		}
		map.put("count", count);
		//总价
		map.put("total", total);
		//插入pay表
		if(type.equals("1")){
			String payId = bsHepatitisBNumService.insertPay(check.substring(0,check.length()-1).toString());
			map.put("payId", payId);
		}
		//用户信息
		map.put("username", bsHepatitisBcheckin.getUsername());
		map.put("rabiescode", bsHepatitisBcheckin.getHepaBcode());
		map.put("sex", DictUtils.getDictLabel(bsHepatitisBcheckin.getSex(), "sex", ""));
		map.put("age", bsHepatitisBcheckin.getAge());
		//当前日期
		map.put("data", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		// 清除用户缓存
		UserUtils.clearCache();
		//医生名称
		map.put("docaterName", UserUtils.getUser().getName());
		//接种单位
		Office o = OfficeService.getFirstOffice();
		String officeName = o.getName();
		String officeCode = o.getCode();
		map.put("officeName", officeName);
		map.put("officeCode", officeCode);
		//接种室
		String roomCode = UserUtils.getUser().getOffice().getCode();
		map.put("roomCode", roomCode);
		map.put("sendOrder", null);
		return map;
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年8月10日 上午9:11:28
	 * @description 
	 *		TODO 打印注射单
	 * @param bsRabiesCheckin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printInjection")
	public String printInjection(BsHepatitisBcheckin bsHepatitisBcheckin, HttpServletRequest request, HttpServletResponse response, Model model) {
		//乙肝疫苗（非蛋白）
		List<BsHepatitisBNum> bsList = bsHepatitisBcheckinService.finishpinNum(bsHepatitisBcheckin);
		model.addAttribute("bsList", bsList);
		//查询疫苗类型
		String vaccName = bsHepatitisBcheckinService.getQueryVaccName(bsHepatitisBcheckin.getVaccType());
		model.addAttribute("vaccinatename", vaccName + "疫苗");
		//犬伤档案信息
		model.addAttribute("bsHepatitisBcheckin", bsHepatitisBcheckin);
		//当前日期
		model.addAttribute("data", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		//接种单位
		String officeName = OfficeService.getFirstOffice().getName();
		model.addAttribute("officeName", officeName);
		//医生名称
		model.addAttribute("docaterName", UserUtils.getUser().getName());
		return "modules/hepatitis/bsHepatitisChaneckinPrintInjection";
	}
	
	/**
	 * @author zhouqj
	 * @date 2017年8月10日 上午10:37:11
	 * @description 
	 *		TODO 打印登记单
	 * @param bsHepatitisBcheckin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printRegister")
	public String printRegister(BsHepatitisBcheckin bsHepatitisBcheckin, HttpServletRequest request, HttpServletResponse response, Model model) {
		bsHepatitisBcheckin = bsHepatitisBcheckinService.updateAddr(bsHepatitisBcheckin);
		//查询疫苗类型
		String vaccName = bsHepatitisBcheckinService.getQueryVaccName(bsHepatitisBcheckin.getVaccType());
		model.addAttribute("vaccinatename", vaccName + "疫苗");
		//查询疫苗名称
		bsHepatitisBcheckin.setVaccineName(bsHepatitisBcheckinService.vaccineName(bsHepatitisBcheckin.getVaccineName()));
		model.addAttribute("bsHepatitisBcheckin", bsHepatitisBcheckin);
		//乙肝疫苗（非蛋白）
		List<BsHepatitisBNum> bsList = bsHepatitisBcheckinService.finishpinNum(bsHepatitisBcheckin);
		model.addAttribute("bsNum", bsList.size());
		model.addAttribute("bsList", bsList);
		//当前日期
		model.addAttribute("data", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		//接种单位
		Office o = OfficeService.getFirstOffice();
		String officeName = o.getName();
		model.addAttribute("officeName", officeName);
		//医生名称
		model.addAttribute("docaterName", UserUtils.getUser().getName());
		return "modules/hepatitis/bsHepatitisChaneckinPrintRegister";
	}
	
	/**
	 * 根据年龄查询规格和厂家信息
	 * @author xuejinshan
	 * @date 2017年8月15日 下午4:47:14
	 * @description 
	 *		TODO
	 * @param age
	 * @param model
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	@RequestMapping(value="queryAge")
	public @ResponseBody String queryAge(@RequestParam Integer age,@RequestParam String vacctype, Model model, BsHepatitisBcheckin bsHepatitisBcheckin){
		Map<String, String> dataList = new HashMap<String, String>();
		String attr = null;
		if("2".equals(vacctype)){
			if(age >= 16){
				attr = "20ug";
			}else{
				attr = "10ug";
			}
			bsHepatitisBcheckin = bsHepatitisBcheckinService.findData(attr, vacctype);
		}else if("4".equals(vacctype)){
			if(age <= 25){
				attr = "5501";
			}else{
				attr = "5502";
			}
			bsHepatitisBcheckin = bsHepatitisBcheckinService.findData(attr, vacctype);
		}
		
		//判断是否存在
		if(bsHepatitisBcheckin != null){
			dataList.put("batchno",bsHepatitisBcheckin.getBatch());
			dataList.put("vaccineId",bsHepatitisBcheckin.getVaccineId());
			dataList.put("pid",bsHepatitisBcheckin.getPid());
			dataList.put("specification",bsHepatitisBcheckin.getStandard());
			dataList.put("manufacturer",bsHepatitisBcheckin.getManufacturer());
			dataList.put("flag","true");
		}else{
			dataList.put("flag","false");
		}
		return JsonMapper.toJsonString(dataList);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("qrcode/{code}")
	public void qrcode(HttpServletResponse response, @PathVariable("code")String code, @RequestParam(required=false,value="w") int w, @RequestParam(required=false, value="h") int h){
		response.setHeader("Content-Type","image/png");
		//指定大小
		int width = (0==w)?300:w;
		int height = (0==h)?300:h;
		String format = "png";   
		
		HashMap hints = new HashMap();
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
	 * 签字展示方法
	 * @author zhouqj
	 * @date 2017年10月9日 下午2:06:50
	 * @description 
	 *		TODO
	 * @param response
	 * @param bsHepatitisBNum
	 * @param model
	 *
	 */
	@RequestMapping(value = "signatureimg")
	public void signatureimg(HttpServletResponse response, BsHepatitisBNum bsHepatitisBNum, Model model) {
		bsHepatitisBNum = bsHepatitisBNumService.get(bsHepatitisBNum);
		ServletOutputStream os = null;
		try {
			if(null != bsHepatitisBNum && StringUtils.isNotBlank(bsHepatitisBNum.getId())){
				byte[] stgn = bsHepatitisBNum.getSignatureData();
				response.getOutputStream().write(stgn);
				response.getOutputStream().flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != os){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取微信签字
	 * @author zhouqj
	 * @date 2017年10月20日 下午12:43:54
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "signUp")
	public @ResponseBody Map<String, Object> signUp(BsHepatitisBcheckin bsHepatitisBcheckin, @RequestParam String id, 
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", null);
		map.put("success", false);
		//查询疫苗针次记录
		BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
		bsHepatitisBNum.setCheckId(bsHepatitisBcheckin.getId());
		//获取微信签字结果更新到本地（checkId）
		bsHepatitisBNumService.updateWxSign(bsHepatitisBNum,map);
		return map;
	}
}