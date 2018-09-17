/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rabiesvaccine.web;

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

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.pay.PayJnBankBean;
import com.thinkgem.jeesite.common.pay.PayJnBankQueryThread;
import com.thinkgem.jeesite.common.pay.PayQueryThread;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckin;
import com.thinkgem.jeesite.modules.hepatitis.service.BsHepatitisBcheckinService;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.service.BsRabiesNumService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesBubble;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckin;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinDeal;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinStock;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 狂犬疫苗管理Controller
 * 
 * @author wangdedi
 * @version 2017-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/rabiesvaccine/bsRabiesCheckin")
public class BsRabiesCheckinController extends BaseController {

	@Autowired
	private AsyncService asyncService;
	@Autowired
	private BsManageProductService productService;
	@Autowired
	private BsRabiesNumService bsRabiesNumService;
	@Autowired
	private BsRabiesCheckinService bsRabiesCheckinService;
	@Autowired
	private BsHepatitisBcheckinService bsHepatitisBcheckinService;
	
	@ModelAttribute
	public BsRabiesCheckin get(@RequestParam(required = false) String id) {
		BsRabiesCheckin entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = bsRabiesCheckinService.get(id);
		}
		if (entity == null) {
			entity = new BsRabiesCheckin();
		}
		return entity;
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 下午2:49:13
	 * @description 
	 *		TODO 默认犬伤页面数据显示
	 * @param bsRabiesCheckin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequiresPermissions("rabiesvaccine:bsRabiesCheckin:view")
	@RequestMapping(value = { "list", "" })
	public String list(BsRabiesCheckin bsRabiesCheckin, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/rabiesvaccine/bsRabiesCheckinList";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月24日 下午5:11:41
	 * @description 
	 *		根据名字或者电话或者身份证号查询所有的个案
	 * @param bsRabiesCheckin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "namelist")
	public String namelist(BsRabiesCheckin bsRabiesCheckin, @RequestParam(required=false, value="searchName") String searchName, HttpServletRequest request, HttpServletResponse response, Model model) {
		//初始化成人对象
		BsHepatitisBcheckin bsHepatitisBcheckin = new BsHepatitisBcheckin();
		//判断id是否存在
		if(StringUtils.isNoneBlank(bsRabiesCheckin.getId())){
			model.addAttribute("id", bsRabiesCheckin.getId());
			model.addAttribute("search", "0");
			return "modules/rabiesvaccine/childlist";
		}
		
		try {
			String search = URLDecoder.decode(searchName,"utf-8");
			if(search.substring(0,1).equals("D") && search.length() == 20 && search.substring(19, 20).equals("A")){
				bsRabiesCheckin.setSearchName(search.substring(0, 19));
				model.addAttribute("search", "1");
			}else{
				bsRabiesCheckin.setSearchName(search);
				bsHepatitisBcheckin.setSearchName(search);
				model.addAttribute("search", "0");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//根据名字或者电话或者身份证号查询所有的个案
		List<BsRabiesCheckin> namelist = bsRabiesCheckinService.namelist(bsRabiesCheckin);
		List<BsHepatitisBcheckin> hepatitisBcheckins = bsHepatitisBcheckinService.namelist(bsHepatitisBcheckin);
		if(namelist.size() == 0 && hepatitisBcheckins.size() == 0){
			model.addAttribute("arg", "未查到建档信息请重新输入");
			return "modules/rabiesvaccine/childlist";	
		}else if(namelist.size() == 1){
			bsRabiesCheckin = namelist.get(0);
			model.addAttribute("id", bsRabiesCheckin.getId());  
			return "modules/rabiesvaccine/childlist";	
		}else if(hepatitisBcheckins.size() == 1){
			bsHepatitisBcheckin = hepatitisBcheckins.get(0);
			model.addAttribute("id", bsHepatitisBcheckin.getId());  
			return "redirect:" + Global.getAdminPath() + "/hepatitis/bsHepatitisBcheckin/namelist";
		}
		//转换当前档案的总针次
		namelist = bsRabiesCheckinService.updateNameList(namelist);
		hepatitisBcheckins = bsHepatitisBcheckinService.updateNameList(hepatitisBcheckins);
		model.addAttribute("namelist", namelist);
		model.addAttribute("hepatitisBcheckins", hepatitisBcheckins);
		model.addAttribute("bsRabiesCheckin", bsRabiesCheckin);	
		return "modules/rabiesvaccine/childlist";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月6日 下午3:33:14
	 * @description 查询当前个档相关狂犬疫苗信息
	 * @param bsRabiesCheckin
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("rabiesvaccine:bsRabiesCheckin:view")
	@RequestMapping(value = "form")
	public String form(BsRabiesCheckin bsRabiesCheckin, @RequestParam String id, Model model, HttpServletRequest request) {
		//查询该条记录
		if(StringUtils.isNotBlank(bsRabiesCheckin.getId())){
			bsRabiesCheckin.setId(id);
		}
		String search = "0";
		if(StringUtils.isNotBlank(bsRabiesCheckin.getSearch())){
			search = bsRabiesCheckin.getSearch();
		}
		//重新查询
		bsRabiesCheckin = bsRabiesCheckinService.get(bsRabiesCheckin);
		if(bsRabiesCheckin == null){
			return "redirect:" + Global.getAdminPath() + "/hepatitis/bsHepatitisBcheckin/form?id="+id+"&search="+search;
		}
		
		//转换地址信息
		bsRabiesCheckin = bsRabiesCheckinService.updateAddr(bsRabiesCheckin);
		//转换咬伤部位显示
		bsRabiesCheckin = bsRabiesCheckinService.updateBitepart(bsRabiesCheckin);
		//转换疫苗名称
		bsRabiesCheckin = bsRabiesCheckinService.updatVaccName(bsRabiesCheckin);
		//赋值search
		bsRabiesCheckin.setSearch(search);
		model.addAttribute("bsRabiesCheckin", bsRabiesCheckin);
		// 清除用户缓存
		UserUtils.clearCache();
		//自动流程权限
		model.addAttribute("openStatus", UserUtils.getUser().getOpenStatus());
		//自动打印权限
		model.addAttribute("printStatus", UserUtils.getUser().getPrintStatus());
		//调价权限
		model.addAttribute("specialStatus", UserUtils.getUser().getSpecialStatus());
		//查询疫苗针次记录
		BsRabiesNum bsRabiesNum = new BsRabiesNum();
		bsRabiesNum.setCheckid(bsRabiesCheckin.getId());
		//接种针次为0
		int countNo = 0;
		int bsCountNo = 0;
		int signCountNo = 0;
		List<BsRabiesNum> notNumlist = bsRabiesNumService.findListVaccinumNot(bsRabiesNum);
		for(BsRabiesNum bs : notNumlist){
			if(BsRabiesNum.STATUS_PAYMAL.equals(bs.getPaystatus())){
				countNo++;
			}
			if(BsRabiesNum.STATUS_FINSH.equals(bs.getStatus())){
				bsCountNo++;
			}
			if(BsRabiesNum.SIGNATURE_YES.equals(bs.getSignature())){
				signCountNo++;
			}
		}
		model.addAttribute("NotNumlist",notNumlist);
		//接种针次不为0
		int count = 0;
		int bsCount = 0;
		int signCount = 0;
		List<BsRabiesNum> numlist = bsRabiesNumService.findListVaccinum(bsRabiesNum);
		for(BsRabiesNum bs : numlist){
			if(BsRabiesNum.STATUS_PAYMAL.equals(bs.getPaystatus())){
				count++;
			}
			if(BsRabiesNum.STATUS_FINSH.equals(bs.getStatus())){
				bsCount++;
			}
			if(BsRabiesNum.SIGNATURE_YES.equals(bs.getSignature())){
				signCount++;
			}
		}
		model.addAttribute("Numlist",numlist);
		//签字总数
		String signType = "0";
		if(signCount == numlist.size() && signCountNo == notNumlist.size()){
			signType = "1";
		}
		model.addAttribute("signType", signType);
		//展示当前疫苗付款与接种统计
		String payMessage = "";
		if((count != numlist.size() || countNo != notNumlist.size()) && (bsCount != numlist.size() || bsCountNo != notNumlist.size())){
			payMessage = "已付款"+ (count+countNo) +"支，已接种"+ (bsCount+bsCountNo) +"支";
		}else if(count == numlist.size() && countNo == notNumlist.size() && (bsCount != numlist.size() || bsCountNo != notNumlist.size())){
			payMessage = "全款付清，已接种"+ (bsCount+bsCountNo) +"支";
		}else if((count != numlist.size() || countNo != notNumlist.size()) && bsCount == numlist.size() && bsCountNo == notNumlist.size()){
			payMessage = "已付款"+ (count+countNo) +"支，全部接种";
		}else if(count == numlist.size() && countNo == notNumlist.size() && bsCount == numlist.size() && bsCountNo == notNumlist.size()){
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
		
		//查询伤口处理记录
		BsRabiesCheckinDeal bsRabiesCheckinDeal = new BsRabiesCheckinDeal();
		bsRabiesCheckinDeal.setCheckinId(bsRabiesCheckin.getId());
		List<BsRabiesCheckinDeal> dealList = bsRabiesNumService.findCheckinDeal(bsRabiesCheckinDeal);
		model.addAttribute("dealList", dealList);
		return "modules/rabiesvaccine/bsRabiesCheckinList";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月6日 下午3:33:56
	 * @description 进入登记、修改档案
	 * @param bsRabiesCheckin
	 * @param model
	 * @return
	 * 
	 */
	@RequiresPermissions("rabiesvaccine:bsRabiesCheckin:view")
	@RequestMapping(value = "update")
	public String update(BsRabiesCheckin bsRabiesCheckin, @RequestParam String type, Model model) {
		//部分实体参数赋初值
		bsRabiesCheckin = bsRabiesCheckinService.updateBsRabiesCheckin(bsRabiesCheckin);
		//判断新增或修改
		bsRabiesCheckinService.updatePcc(bsRabiesCheckin,model);
		if(StringUtils.isNotBlank(bsRabiesCheckin.getId())){
			//转化默认接种日期
			bsRabiesCheckin = bsRabiesNumService.updateNewDate(bsRabiesCheckin);
		}
		//进入渠道
		model.addAttribute("type",type);
		//分类犬伤疫苗（蛋白与非蛋白）
		bsRabiesCheckinService.updateBsProduct(model);
		return "modules/rabiesvaccine/bsRabiesCheckinForm";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月19日 上午9:18:01
	 * @description 
	 *		狂犬疫苗自助建档
	 * @param childBaseinfo
	 * @param code
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "code")
	public String code(BsRabiesCheckin bsRabiesCheckin, @RequestParam String code, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
		if(StringUtils.isBlank(code) || !code.matches("^[0-9]*$")){
			addMessage(redirectAttributes, "输入格式错误,存在非数字");
			return "redirect:" + Global.getAdminPath() + "/rabiesvaccine/bsRabiesCheckin/update?repage&type=0";
		}
		// 网络请求微信服务器，获得自助建档信息
		logger.info("自助建档接口参数："+code);
		String wx = null;
		try {
			wx = HttpClientReq.httpClientPost(Global.getConfig("wpwxdoge.url") + "api/rabiesTemp/" + code + ".do", new HashMap<String, String>());
			if(wx.equals("") || wx.contains("<html>")){
				addMessage(redirectAttributes, "未找到你的自助建档信息");
				return "redirect:" + Global.getAdminPath() + "/rabiesvaccine/bsRabiesCheckin/update?repage&type=0";
			}
		} catch (Exception e) {
			logger.error("自助建档接口异常",e);
			addMessage(redirectAttributes, "网络异常，请稍后重试");
			return "redirect:" + Global.getAdminPath() + "/rabiesvaccine/bsRabiesCheckin/update?repage&type=0";
		}
		//打印日志
		logger.info("自助建档接口返回值："+wx);
		if(wx != null){
			JSONObject obj = JSONObject.parseObject(wx);
			if (obj.getBoolean("success")) {
				bsRabiesCheckin = (BsRabiesCheckin) JsonMapper.fromJsonString(obj.get("data").toString(), BsRabiesCheckin.class);
			} else {
				addMessage(redirectAttributes, "未找到你的自助建档信息");
				return "redirect:" + Global.getAdminPath() + "/rabiesvaccine/bsRabiesCheckin/update?repage&type=0";
			}
			//部分实体参数赋初值
			bsRabiesCheckin = bsRabiesCheckinService.updateBsRabiesCheckin(bsRabiesCheckin);
			//判断地址是否存在
			bsRabiesCheckinService.updatePcc(bsRabiesCheckin,model);
			//分类犬伤疫苗（蛋白与非蛋白）
			bsRabiesCheckinService.updateBsProduct(model);
			//进入渠道
			model.addAttribute("type","0");
			model.addAttribute("bsRabiesCheckin", bsRabiesCheckin);
		}
		return "modules/rabiesvaccine/bsRabiesCheckinForm";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月6日 下午5:38:22
	 * @description 保存或修改档案
	 * @param bsRabiesCheckin
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * 
	 */
	@RequiresPermissions("rabiesvaccine:bsRabiesCheckin:edit")
	@RequestMapping(value = "save")
	public String save(BsRabiesCheckin bsRabiesCheckin, @RequestParam(required=false, value="wound") String price, Model model, RedirectAttributes redirectAttributes) {
		// 清除用户缓存
		UserUtils.clearCache();
		//填充创建者
		bsRabiesCheckin.setCreateBy(UserUtils.getUser());
		//判断是否是新增建档
		boolean isNew = StringUtils.isBlank(bsRabiesCheckin.getId());
		//判断是否是48小时后接种
		boolean isJudgmentTimes = bsRabiesCheckin.getJudgmentTimes().equals("1");
		//判断是否是注射免疫蛋白
		boolean isInoculate = bsRabiesCheckin.getIsinoculate().equals("1");
		//疫苗类型
		String vaccType = "1";
		//疫苗小类编码
		String vaccCode = bsRabiesCheckin.getVaccinatename();
		//疫苗厂家
		String vaccMan = bsRabiesCheckin.getManufacturer();
		//疫苗数量
		int vnum = Integer.valueOf(bsRabiesCheckin.getDosage());
		if(!isInoculate){
			//去除非必要注射免疫蛋白记录
			bsRabiesCheckin.setStandardNo(null);
			bsRabiesCheckin.setManufacturerNo(null);
			bsRabiesCheckin.setBatchnumNo(null);
			bsRabiesCheckin.setVaccinatenameNo(null);
		}
		if(isNew){
			//新建档默认生成犬伤code
			bsRabiesCheckin.setRabiescode("D" + bsRabiesCheckinService.codedog());
			int count = bsRabiesCheckinService.countRabiesCode(bsRabiesCheckin);
			if(count != 0){
				model.addAttribute("msg", "该编号档案已存在！");
				return "modules/rabiesvaccine/bsRabiesCheckinForm";
			}
			if(StringUtils.isNotBlank(bsRabiesCheckin.getOpenid())){
				Map<String, String> wxmap = new HashMap<>();
				wxmap.put("openid",bsRabiesCheckin.getOpenid());
				wxmap.put("username",bsRabiesCheckin.getUsername());
				wxmap.put("checkno",bsRabiesCheckin.getRabiescode());
				wxmap.put("nexttime", "1");
				asyncService.sendWxMessage(Global.getConfig("wpwxdoge.url") + "api/sendWxTempMsgUp.do",JsonMapper.toJsonString(wxmap));
			}
			
		}
		//保存狂犬病疫苗接种者档案信息
		bsRabiesCheckinService.save(bsRabiesCheckin);
		addMessage(redirectAttributes, "保存信息成功");
		model.addAttribute("id", bsRabiesCheckin.getId());
		//新增或修改记录表数据
		//查询疫苗配置
		VaccInfo vaccInfo = bsRabiesNumService.updateVaccInterVal(vaccType,vaccCode,vaccMan,vnum);
		if(isNew){
			//判断是否是注射免疫蛋白
			if(isInoculate){
				//判断输入的蛋白针次是否存在
				if(StringUtils.isBlank(bsRabiesCheckin.getDosageNo())){
					bsRabiesCheckin.setDosageNo("1");
				}
				//免疫蛋白数量
				int num = Integer.parseInt(bsRabiesCheckin.getDosageNo());
				for(int i = 0; i < num; i++){
					bsRabiesNumService.saveDosage(bsRabiesCheckin,null,0,0,0);
				}
			}
			//判断是否是48小时后接种
			if(isJudgmentTimes){
				bsRabiesNumService.saveDosage(bsRabiesCheckin,null,1,0,1);
			}
			for(int i = 0;i < vnum;i++){
				bsRabiesNumService.saveDosage(bsRabiesCheckin,vaccInfo,vnum,i,1);
			}
		}else{
			//蛋白
			List<BsRabiesNum> blistOut = bsRabiesCheckinService.finishpinNot(bsRabiesCheckin);
			//非蛋白
			List<BsRabiesNum> blistOn = bsRabiesCheckinService.finishpinNum(bsRabiesCheckin);
			if(isInoculate){
				if(blistOut.size() == 0){
					//判断输入的蛋白针次是否存在
					if(StringUtils.isBlank(bsRabiesCheckin.getDosageNo())){
						bsRabiesCheckin.setDosageNo("1");
					}
					//免疫蛋白数量
					int num = Integer.parseInt(bsRabiesCheckin.getDosageNo());
					for(int i = 0; i < num; i++){
						bsRabiesNumService.saveDosage(bsRabiesCheckin,null,0,0,0);
					}
				}else{
					//修改狂免疫苗信息
					for(BsRabiesNum bs : blistOut){
						if((bs.getRealdate() == null || !bs.getStatus().equals("1")) && bs.getNstatus().equals("0")){
							bs.setPid(bsRabiesCheckin.getPidNo());
							bs.setDose(bsRabiesCheckin.getStandardNo());
							bs.setBatchnum(bsRabiesCheckin.getBatchnumNo());
							bs.setVaccineid(bsRabiesCheckin.getVaccinatenameNo());
							bs.setManufacturer(bsRabiesCheckin.getManufacturerNo());
							bsRabiesNumService.save(bs);
						}
					}
				}
			}else{
				//删除所有蛋白记录
				for(BsRabiesNum bs : blistOut){
					if((bs.getRealdate() == null || !bs.getStatus().equals("1")) && bs.getNstatus().equals("0")){
						bsRabiesNumService.delete(bs);
					}
				}
			}
			//修改狂苗信息
			for(BsRabiesNum bs : blistOn){
				if((bs.getRealdate() == null || !bs.getStatus().equals("1")) && bs.getNstatus().equals("0")){
					bs.setPid(bsRabiesCheckin.getPid());
					bs.setDose(bsRabiesCheckin.getStandard());
					bs.setBatchnum(bsRabiesCheckin.getBatchnum());
					bs.setVaccineid(bsRabiesCheckin.getVaccinatename());
					bs.setManufacturer(bsRabiesCheckin.getManufacturer());
					bsRabiesNumService.save(bs);
				}
			}
		}
		
		//添加伤口处理记录
		if(isNew){
			BsRabiesCheckinDeal bsRabiesCheckinDeal = new BsRabiesCheckinDeal();
			if (!price.isEmpty()) {
				bsRabiesCheckinDeal.setPrice(Double.valueOf(price));
				bsRabiesNumService.saveCheckinDeal(bsRabiesCheckin, bsRabiesCheckinDeal);
			}
		}
		return "modules/rabiesvaccine/bsRabiesCheckinForm";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 下午3:04:55
	 * @description 
	 *		TODO 删除信息
	 * @param bsRabiesCheckin
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequiresPermissions("rabiesvaccine:bsRabiesCheckin:edit")
	@RequestMapping(value = "delete")
	public String delete(BsRabiesCheckin bsRabiesCheckin, RedirectAttributes redirectAttributes) {
		// 清除用户缓存
		UserUtils.clearCache();
		//初始化产品
		BsManageProduct product = new BsManageProduct();
		//初始化记录
		BsRabiesNum bsRabiesNum = new BsRabiesNum();
		bsRabiesNum.setCheckid(bsRabiesCheckin.getId());
		//删除档案
		bsRabiesCheckinService.delete(bsRabiesCheckin);
		//查询记录信息
		List<BsRabiesNum> bsList = bsRabiesNumService.findByCheckId(bsRabiesNum);
		for(BsRabiesNum bs : bsList){
			//查询产品信息
			product = bsRabiesNumService.queryBsProductPId(bs.getPid());
			// 库存+1
			if (null != product && StringUtils.isNoneBlank(product.getId())) {
				product.setStorenum(product.getStorenum() + 1);
				productService.save(product);
				bs.setNstatus(BsRabiesNum.NSTATUS_BEFOR);
				bs.setRealdate(null);
				bs.setPaystatus(BsRabiesNum.STATUS_NORMAL);
				bs.setStatus(BsRabiesNum.STATUS_NOFINSH);
				bs.setCreateBy(UserUtils.getUser());
				bsRabiesNumService.save(bs);
				bsRabiesNumService.delete(bs);
			}
		}
		//删除记录
		bsRabiesCheckinService.deleteCheckId(bsRabiesNum);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/rabiesvaccine/bsRabiesCheckin/?repage";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月10日 上午10:57:16
	 * @description 
	 *		个案统计
	 * @param bsRabiesCheckin
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "bsCheckin")
	public String bsCheckin(BsRabiesCheckin bsRabiesCheckin, Model model, HttpServletRequest request, HttpServletResponse response) {
		Date d[] = bsRabiesCheckinService.dateTime(bsRabiesCheckin.getBeginTime(), bsRabiesCheckin.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsRabiesCheckin.setBeginTime(d[0]);
			bsRabiesCheckin.setEndTime(d[1]);
		} else {
			return "modules/rabiesvaccine/bsRabiesCheckin";
		}
		Page<BsRabiesCheckin> page = bsRabiesCheckinService.findPage(new Page<BsRabiesCheckin>(request, response), bsRabiesCheckin);
		for(BsRabiesCheckin bs : page.getList()){
			bs =  bsRabiesCheckinService.updateAddr(bs);
			//转换咬伤部位显示
			bs = bsRabiesCheckinService.updateBitepart(bs);
			//转换疫苗名称
			bs = bsRabiesCheckinService.updatVaccName(bs);
		}
		model.addAttribute("page", page);
		return "modules/rabiesvaccine/bsRabiesCheckin";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月10日 上午11:52:40
	 * @description 
	 *		导出狂犬疫苗个案记录
	 * @param bsRabiesCheckin
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(BsRabiesCheckin bsRabiesCheckin, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Date d[] = bsRabiesCheckinService.dateTime(bsRabiesCheckin.getBeginTime(), bsRabiesCheckin.getEndTime());
		// 根据时间判断输入的时间是否正确
		if (d != null) {
			bsRabiesCheckin.setBeginTime(d[0]);
			bsRabiesCheckin.setEndTime(d[1]);
		} else {
			return "modules/rabiesvaccine/bsRabiesCheckin";
		}
		List<BsRabiesCheckin> page = bsRabiesCheckinService.findList(bsRabiesCheckin);
		for(BsRabiesCheckin bs : page){
			bs =  bsRabiesCheckinService.updateAddr(bs);
			//转换咬伤部位显示
			bs = bsRabiesCheckinService.updateBitepart(bs);
			//转换疫苗名称
			bs = bsRabiesCheckinService.updatVaccName(bs);
		}
		try {
			String fileName = "狂犬疫苗个案记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx"; 
			new ExportExcel("狂犬疫苗个案记录", BsRabiesCheckin.class).setDataList(page).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出狂犬疫苗个案记录失败！失败信息：" + e.getMessage());
		}
		return "modules/rabiesvaccine/bsRabiesCheckin";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月19日 下午2:13:49
	 * @description 
	 *		TODO 默认显示犬伤疫苗库存统计
	 * @param bsRabiesCheckinStock
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "bsCheckinStock")
	public String bsCheckinStock(BsRabiesCheckinStock bsRabiesCheckinStock, Model model, HttpServletRequest request, HttpServletResponse response) {
		//库存统计
		List<List<BsRabiesCheckinStock>> returnlistOne = bsRabiesCheckinService.vaccineStock(bsRabiesCheckinStock);
		model.addAttribute("bsList", returnlistOne);
		return "modules/rabiesvaccine/bsRabiesCheckinStock";
	}
	
	/**
	 * 打印犬伤疫苗库存统计
	 * @author zhouqj
	 * @date 2017年7月11日 上午9:08:51
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckinStock
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printStock")
	public String printStock(BsRabiesCheckinStock bsRabiesCheckinStock, Model model) {
		//库存统计
		List<List<BsRabiesCheckinStock>> returnlistOne = bsRabiesCheckinService.vaccineStock(bsRabiesCheckinStock);
		model.addAttribute("bsList", returnlistOne);
		model.addAttribute("date", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		return "modules/rabiesvaccine/bsRabiesCheckinStockPrint";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月26日 下午1:47:38
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
	public String printInjection(BsRabiesCheckin bsRabiesCheckin, HttpServletRequest request, HttpServletResponse response, Model model) {
		//疫苗针次为0
		List<BsRabiesNum> bsRabiesNumNotList =	bsRabiesCheckinService.finishpinNot(bsRabiesCheckin);
		if(bsRabiesNumNotList.size() != 0){
			model.addAttribute("bsNum", bsRabiesNumNotList.size());
			model.addAttribute("bsRabiesNumNotList", bsRabiesNumNotList.get(0));
		}
		//疫苗针次不为0
		List<BsRabiesNum> bsRabiesNumList =	bsRabiesCheckinService.finishpinNum(bsRabiesCheckin);
		model.addAttribute("bsRabiesNumList", bsRabiesNumList);
		//犬伤档案信息
		model.addAttribute("bsRabiesCheckin", bsRabiesCheckin);
		//当前日期
		model.addAttribute("data", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		//接种单位
		Office o = OfficeService.getFirstOffice();
		String officeName = o.getName();
		model.addAttribute("officeName", officeName);
		//医生名称
		model.addAttribute("docaterName", UserUtils.getUser().getName());
		return "modules/rabiesvaccine/chaneckinPrintInjection";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月3日 上午10:15:09
	 * @description 
	 *		TODO 打印登记单 
	 * @param bsRabiesCheckin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printRegister")
	public String printRegister(BsRabiesCheckin bsRabiesCheckin, HttpServletRequest request, HttpServletResponse response, Model model) {
		//转换地址信息
		bsRabiesCheckin = bsRabiesCheckinService.updateAddr(bsRabiesCheckin);
		//转换咬伤部位
		bsRabiesCheckin = bsRabiesCheckinService.updateBitepart(bsRabiesCheckin);
		//转换疫苗名称
		bsRabiesCheckin = bsRabiesCheckinService.updatVaccName(bsRabiesCheckin);
		model.addAttribute("bsRabiesCheckin", bsRabiesCheckin);
		//疫苗针次为0
		List<BsRabiesNum> bsRabiesNumNotList =	bsRabiesCheckinService.finishpinNot(bsRabiesCheckin);
		if(bsRabiesNumNotList.size() != 0){
			model.addAttribute("bsNum", bsRabiesNumNotList.size());
			model.addAttribute("bsRabiesNumNotList", bsRabiesNumNotList.get(0));
		}
		//疫苗针次不为0
		List<BsRabiesNum> bsRabiesNumList =	bsRabiesCheckinService.finishpinNum(bsRabiesCheckin);
		model.addAttribute("bsNotNum", bsRabiesNumList.size());
		model.addAttribute("bsRabiesNumList", bsRabiesNumList);
		//当前日期
		model.addAttribute("data", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		//接种单位
		Office o = OfficeService.getFirstOffice();
		String officeName = o.getName();
		model.addAttribute("officeName", officeName);
		//医生名称
		model.addAttribute("docaterName", UserUtils.getUser().getName());
		return "modules/rabiesvaccine/chaneckinPrintRegister";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年7月28日 下午3:47:18
	 * @description 
	 *		TODO 打印告知书
	 * @param bsRabiesCheckin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printInform")
	public String printInform(BsRabiesCheckin bsRabiesCheckin, HttpServletRequest request, HttpServletResponse response, Model model) {
		//暴露级别
		model.addAttribute("exposelevel", DictUtils.getDictLabel(bsRabiesCheckin.getExposelevel(), "rank", ""));
		model.addAttribute("exposelevelnum", bsRabiesCheckin.getExposelevel());
		//时间
		model.addAttribute("data", DateUtils.formatDate(bsRabiesCheckin.getCreateDate(), "yyyy年MM月dd日"));
		//是否选择免疫蛋白
		boolean isInoculate = bsRabiesCheckin.getIsinoculate().equals("1");
		model.addAttribute("isinoculate", bsRabiesCheckin.getIsinoculate());
		//用户签字
		if(StringUtils.isNotBlank(bsRabiesCheckin.getsId())){
			//查询用户签字
			BsRabiesCheckin bs = bsRabiesCheckinService.querySignature(bsRabiesCheckin);
			if(!bs.getStype().equals("0")){
				model.addAttribute("sid",bs.getsId());
			}else{
				try {
					model.addAttribute("signatureList", new String(bs.getSignatureData(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			model.addAttribute("stype",bs.getStype());
		}
		//查询记录数据
		List<BsRabiesNum> numlist = null;
		if(isInoculate){
			numlist = bsRabiesCheckinService.finishpinNot(bsRabiesCheckin);
		}else{
			numlist = bsRabiesCheckinService.finishpinNum(bsRabiesCheckin);
		}
		//查询记录单条数据
		BsRabiesNum bs = null;
		if(numlist.size() > 0 ){
			bs = numlist.get(0);
			model.addAttribute("createByName", bs.getCreateByName());
		}
		//接种点名称
		Office o = OfficeService.getFirstOffice();
		String officeName = o.getName();
		model.addAttribute("officeName", officeName);
		return "modules/rabiesvaccine/chaneckinPrintInform";
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年7月25日 下午6:13:41
	 * @description 
	 *		TODO 打印发票
	 * @param bsRabiesCheckin
	 * @param items
	 * @param notitems
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = "printInvoice")
	public @ResponseBody Map<String, Object> printInvoice(BsRabiesCheckin bsRabiesCheckin,@RequestParam List<String> items,@RequestParam List<String> notitems,
			@RequestParam List<String> witems, @RequestParam String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer check = new StringBuffer();
		//总价格
		double total = 0;
		double totalCount = 0;
		double totalNCount = 0;
		double totalWCount = 0;
		//初始化产品
		BsManageProduct product = new BsManageProduct();
		BsRabiesNum bsRabiesNum = new BsRabiesNum();
		BsRabiesCheckinDeal bsRabiesCheckinDeal = new BsRabiesCheckinDeal();
		//狂苗支数
		int count = items.size();
		if(count != 0){
			for(String id : items){
				check.append(id + ",");
				bsRabiesNum = bsRabiesNumService.get(id);
				product = bsRabiesNumService.updatePidByName(bsRabiesNum,product);
				if(product == null){
					map.put("error","打印小票失败，请检查疫苗库存和所属科室");
					return map;
				}
				if(product.getStorenum() <= 0){
					map.put("error","打印小票失败，("+ bsRabiesNum.getManufacturer() + "-" + bsRabiesNum.getBatchnum() +")库存为空，请重新登记选择疫苗。");
					return map;
				}
				if(BsRabiesNum.FUNDSTATUS_YES.equals(bsRabiesNum.getFundStatus())){
					total += bsRabiesNum.getCurrentPrice();
					totalCount += bsRabiesNum.getCurrentPrice();
				}else{
					total += product.getSellprice();
					totalCount += product.getSellprice();
				}
			}
			map.put("totalCount", totalCount);
			//查询首条记录信息
			BsRabiesNum bs = new BsRabiesNum();
			bs = bsRabiesNumService.get(items.get(0));
			if(bs == null){
				map.put("error","打印小票失败，记录信息不存在");
				return map;
			}
			if(type.equals("0")){
				map.put("num", bsRabiesNumService.queryBsNumListDB(bs).size());
			}
			map.put("vaccinatename", "狂犬疫苗");
			map.put("pid", bs.getPid());
			map.put("manufacturer", bs.getManufacturer());
			map.put("dose", DictUtils.getDictLabel(String.valueOf(bs.getVaccinum()), "pin", ""));
			map.put("status", DictUtils.getDictLabel(bs.getStatus(), "wstatus", ""));
			//统计已付款记录
			int num = 0;
			bsRabiesNum = new BsRabiesNum();
			bsRabiesNum.setCheckid(bsRabiesCheckin.getId());
			List<BsRabiesNum> numlist = bsRabiesNumService.queryBsNumListDB(bsRabiesNum);
			for(BsRabiesNum returnBs : numlist){
				if(BsRabiesNum.STATUS_PAYMAL.equals(returnBs.getPaystatus())){
					num++;
				}
			}
			String payMessage = "";
			if(num == numlist.size()){
				payMessage = "全款付清";
			}else{
				payMessage = "已付"+ num +"支";
			}
			map.put("paystatus", DictUtils.getDictLabel(bs.getPaystatus(), "paystatus", "") + "(" + payMessage + ")");
		}
		map.put("count", count);
		//狂免支数
		int ncount = notitems.size();
		if(ncount != 0){
			for(String id : notitems){
				check.append(id + ",");
				bsRabiesNum = bsRabiesNumService.get(id);
				product = bsRabiesNumService.updatePidByName(bsRabiesNum,product);
				if(product == null){
					map.put("error","打印小票失败，请检查疫苗库存和所属科室");
					return map;
				}
				if(product.getStorenum() <= 0){
					map.put("error","打印小票失败，("+ bsRabiesNum.getManufacturer() + "-" + bsRabiesNum.getBatchnum() +")库存为空，请重新登记选择疫苗。");
					return map;
				}
				if(BsRabiesNum.FUNDSTATUS_YES.equals(bsRabiesNum.getFundStatus())){
					total += bsRabiesNum.getCurrentPrice();
					totalNCount += bsRabiesNum.getCurrentPrice();
				}else{
					total += product.getSellprice();
					totalNCount += product.getSellprice();
				}
			}
			map.put("totalNCount", totalNCount);
			//查询首条记录信息
			BsRabiesNum bs = new BsRabiesNum();
			bs = bsRabiesNumService.get(notitems.get(0));
			if(bs == null){
				map.put("error","打印小票失败，记录信息不存在");
				return map;
			}
			if(type.equals("0")){
				map.put("numNo", bsRabiesNumService.queryBsNumListDB(bs).size());
			}
			map.put("vaccinatenameNo", "狂免蛋白");
			map.put("pidNo", bs.getPid());
			map.put("manufacturerNo", bs.getManufacturer());
			map.put("doseNo", DictUtils.getDictLabel(String.valueOf(bs.getVaccinum()), "pin", ""));
			map.put("statusNo", DictUtils.getDictLabel(bs.getStatus(), "wstatus", ""));
			//统计已付款记录
			int num = 0;
			bsRabiesNum = new BsRabiesNum();
			bsRabiesNum.setCheckid(bsRabiesCheckin.getId());
			List<BsRabiesNum> numlist = bsRabiesNumService.queryBsNumListDB(bsRabiesNum);
			for(BsRabiesNum returnBs : numlist){
				if(BsRabiesNum.STATUS_PAYMAL.equals(returnBs.getPaystatus())){
					num++;
				}
			}
			String payMessage = "";
			if(num == numlist.size()){
				payMessage = "全款付清";
			}else{
				payMessage = "已付"+ num +"支";
			}
			map.put("paystatusNo", DictUtils.getDictLabel(bs.getPaystatus(), "paystatus", "") + "(" + payMessage + ")");
		}
		map.put("ncount", ncount);
		
		//伤口处理
		int wcount = witems.size();
		if(wcount != 0){
			List<BsRabiesCheckinDeal> list = new ArrayList<BsRabiesCheckinDeal>();
			for (String id : witems) {
				check.append(id + ",");
				bsRabiesCheckinDeal.setId(id);
				bsRabiesCheckinDeal.setCheckinId(bsRabiesCheckin.getId());
				list = bsRabiesNumService.findCheckinDeal(bsRabiesCheckinDeal);
			}
			
			if(list.size() == 0){
				map.put("error","打印小票失败，记录信息不存在");
				return map;
			}else{
				for (BsRabiesCheckinDeal rabiesCheckinDeal : list) {
					total += rabiesCheckinDeal.getPrice();
					totalWCount += rabiesCheckinDeal.getPrice();
				}
			}
			map.put("dealName", "伤口处理");
			map.put("totalWCount", totalWCount);
		}
		map.put("wcount", wcount);
		//总价
		map.put("total", total);
		//插入pay表
		if(type.equals("1")){
			String payId = bsRabiesNumService.insertPay(check.substring(0,check.length()-1).toString());
			map.put("payId", payId);
		}
		//用户信息
		map.put("username", bsRabiesCheckin.getUsername());
		map.put("rabiescode", bsRabiesCheckin.getRabiescode());
		map.put("sex", DictUtils.getDictLabel(bsRabiesCheckin.getSex(), "sex", ""));
		map.put("age", bsRabiesCheckin.getAge());
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
	public void qrcode(HttpServletResponse response, @PathVariable("code")String code, 
			@RequestParam(required=false,value="w") int w, @RequestParam(required=false, value="h") int h){
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
	public void signatureimg(HttpServletResponse response, BsRabiesNum bsRabiesNum, Model model) {
		bsRabiesNum = bsRabiesNumService.get(bsRabiesNum);
		ServletOutputStream os = null;
		try {
			if(null != bsRabiesNum && StringUtils.isNotBlank(bsRabiesNum.getId())){
				byte[] stgn = bsRabiesNum.getSignatureData();
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
	 * @date 2017年10月20日 上午10:57:56
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "signUp")
	public @ResponseBody Map<String, Object> signUp(BsRabiesCheckin bsRabiesCheckin, @RequestParam String id, 
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", null);
		map.put("success", false);
		//查询疫苗针次记录
		BsRabiesNum bsRabiesNum = new BsRabiesNum();
		bsRabiesNum.setCheckid(bsRabiesCheckin.getId());
		//获取微信签字结果更新到本地（checkId）
		bsRabiesNumService.updateWxSign(bsRabiesNum,map);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "printPay")
	public @ResponseBody Map<String, Object> printPay(@RequestParam String json,@RequestParam String sessionId,
			@RequestParam double str,@RequestParam String type, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		//转换
		json = StringEscapeUtils.unescapeHtml4(json);
		Map<String, Object> pmp = (Map<String, Object>) JsonMapper.fromJsonString(json, Map.class);
		//获取参数
		if(pmp != null){
			String officeCode = (String) pmp.get("officeCode");
			String rabiescode = (String) pmp.get("rabiescode");
			String payId = (String) pmp.get("payId");
			int count = (int) pmp.get("count");
			String pid = null;
			String vaccinatename = null;
			String manufacturer = null;
			if(count != 0){
				pid = (String) pmp.get("pid");
				vaccinatename = (String) pmp.get("vaccinatename");
				manufacturer = (String) pmp.get("manufacturer");
			}
			//狂犬
			if(type.equals("1")){
				int ncount = (int) pmp.get("ncount");
				String pidNo = null;
				String vaccinatenameNo = null;
				String manufacturerNo = null;
				if(ncount != 0){
					pidNo = (String) pmp.get("pidNo");
					vaccinatenameNo = (String) pmp.get("vaccinatenameNo");
					manufacturerNo = (String) pmp.get("manufacturerNo");
				}
				
				//支付url
				if(ncount != 0 || count != 0){
					PayJnBankBean bean = new PayJnBankBean();
					bean.setVaccineType(PayJnBankBean.VACCINE_TYPE_ADULT);
					bean.setVaccineName("狂犬病疫苗");
					bean.setTotal(str);
					bean.setOfficeCode(officeCode);
					bean.setSource(PayJnBankBean.SOURCE_OFF_SACN_IN);
					bean.setChildCode(rabiescode);
					
					List<BsManageProduct> products = new ArrayList<BsManageProduct>();
					if(count != 0 && ncount != 0){
						BsManageProduct tempProduct1 = productService.get(pid);
						tempProduct1.setStorenum((long) count);
						products.add(tempProduct1);
						BsManageProduct tempProduct2 = productService.get(pidNo);
						tempProduct2.setStorenum((long) ncount);
						products.add(tempProduct2);
						bean.setRemarks(vaccinatename+"_"+manufacturer+"*"+count+"-"+vaccinatenameNo+"_"+manufacturerNo+"*"+ncount);
					}else if(count != 0 && ncount == 0){
						BsManageProduct tempProduct1 = productService.get(pid);
						tempProduct1.setStorenum((long) count);
						products.add(tempProduct1);
						bean.setRemarks(vaccinatename+"_"+manufacturer+"*"+count);
					}else if(count == 0 && ncount != 0){
						BsManageProduct tempProduct1 = productService.get(pidNo);
						tempProduct1.setStorenum((long) ncount);
						products.add(tempProduct1);
						bean.setRemarks(vaccinatenameNo+"_"+manufacturerNo+"*"+ncount);
					}
					bean.setBsManageProducts(products);
					
					//回调成功参数
					if(StringUtils.isNotBlank(sessionId) && StringUtils.isNotBlank(payId) && StringUtils.isNotBlank(type)){
						map.put("sessionId", sessionId);
						map.put("payId", payId);
						map.put("type", type);
						bean.setPara(map);
						
						PayQueryThread payQueryThread = new PayJnBankQueryThread((int)(bean.getTotal() * 100), bean.getOfficeCode(), bean.getSource(), bean.getChildCode(), bean.getOrderNo(), bean.getPara());
						pmp.put("sendOrder", bean.sendOrder(payQueryThread));
					}
					pmp.put("str", str);
				}
			}else{
				//成人
				if(count != 0){
					PayJnBankBean bean = new PayJnBankBean();
					bean.setVaccineType(PayJnBankBean.VACCINE_TYPE_ADULT);
					bean.setVaccineName("成人疫苗");
					bean.setRemarks(vaccinatename+"_"+manufacturer+"*"+count);
					bean.setTotal(str);
					bean.setOfficeCode(officeCode);
					bean.setSource(PayJnBankBean.SOURCE_OFF_SACN_IN);
					bean.setChildCode(rabiescode);
					
					List<BsManageProduct> products = new ArrayList<BsManageProduct>();
					BsManageProduct tempProduct1 = productService.get(pid);
					tempProduct1.setStorenum((long) count);
					products.add(tempProduct1);
					bean.setBsManageProducts(products);
					
					//回调成功参数
					if(StringUtils.isNotBlank(sessionId) && StringUtils.isNotBlank(payId) && StringUtils.isNotBlank(type)){
						map.put("sessionId", sessionId);
						map.put("payId", payId);
						map.put("type", type);
						bean.setPara(map);
						
						PayQueryThread payQueryThread = new PayJnBankQueryThread((int)(bean.getTotal() * 100), bean.getOfficeCode(), bean.getSource(), bean.getChildCode(), bean.getOrderNo(), bean.getPara());
						pmp.put("sendOrder", bean.sendOrder(payQueryThread));
					}
					pmp.put("str", str);
				}
			}
		}
		return pmp;
	}
	
	/**
	 * 查询犬伤气泡队列
	 * @author yangjian
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param type
	 * @return
	 */
	@RequestMapping(value="findBsRabiesBubble")
	@ResponseBody
	public String findBsRabiesBubble(BsRabiesBubble bsRabiesBubble, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> data = new HashMap<String, Object>();
		List<BsRabiesBubble> list = bsRabiesCheckinService.findBsRabiesBubble(bsRabiesBubble);
		data.put("bubbles", list);
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 删除犬伤气泡队列
	 * @author yangjian
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param type
	 * @return
	 */
	@RequestMapping(value="deleteBsRabiesBubble")
	@ResponseBody
	public String deleteBsRabiesBubble(BsRabiesBubble bsRabiesBubble, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> data = new HashMap<String, Object>();
		if(bsRabiesBubble != null){
			bsRabiesCheckinService.deleteBsRabiesBubble(bsRabiesBubble);
			data.put("result", "success");
		}
		
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 添加犬伤气泡队列
	 * @author yangjian
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param type
	 * @return
	 */
	@RequestMapping(value="insertBsRabiesBubble")
	@ResponseBody
	public String insertBsRabiesBubble(BsRabiesBubble bsRabiesBubble, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> data = new HashMap<String, Object>();
		List<BsRabiesBubble> list = bsRabiesCheckinService.findBubbleList(bsRabiesBubble);
		if(bsRabiesBubble != null){
			if(isExistBubble(list, bsRabiesBubble)){
				bsRabiesCheckinService.insertBsRabiesBubble(bsRabiesBubble);
				data.put("result", "success");
			}
		}
		
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 判断气泡队列中是否存在该人
	 * @author yangjian
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param list
	 * @param bsRabiesBubble
	 * @return Boolean
	 */
	public Boolean isExistBubble(List<BsRabiesBubble> list, BsRabiesBubble bsRabiesBubble){
		for (BsRabiesBubble bubble : list) {
			if (bubble.getRabiescode().equals(bsRabiesBubble.getRabiescode())) {
				return false;
			}
		}
		return true;
	}
}