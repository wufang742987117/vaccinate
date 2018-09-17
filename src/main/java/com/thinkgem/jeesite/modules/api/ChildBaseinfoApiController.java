/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.junl.common.JsonBuild;
import com.junl.common.ResponseStatus;
import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.dwr.DwrUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.holiday.service.SysHolidayService;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;
import com.thinkgem.jeesite.modules.product.entity.ApiVaccGroupVo;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.entity.ImportContext;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccinenumService;

import sun.misc.BASE64Decoder;

/**
 * 
 * @author chenmaolong
 * @date 2017年3月3日 下午2:39:42
 * @description 儿童基本信息 TODO
 */
@Controller
@RequestMapping(value = "${frontPath}/childBaseinfo")
public class ChildBaseinfoApiController extends BaseController {

		@Autowired
		private ChildBaseinfoService childBaseinfoService;
		@Autowired
		private ChildVaccinaterecordService childVaccinaterecordService;
		@Autowired
		private BsManageProductService productService;
		@Autowired
		private BsManageVaccinenumService bsManageVaccinenumService;
		@Autowired
		private QueneService queneService;
		@Autowired
		private OfficeService officeService;
		@Autowired
		private AsyncService asyncService;
		@Autowired
		private VacChildRemindService remindService;
		@Autowired
		private BsManageVaccineService bsManageVaccineService;
		@Autowired
		private SysHolidayService sysHolidayService;
		
	/**
	 * 
	 * @author chenmaolong
	 * @date 2017年3月3日 下午2:39:01
	 * @description TODO 查询儿童基本信息详情
	 * 
	 *              请求地址：http://127.0.0.1:8080/vaccinate/f/childBaseinfo/findChildInfoDetail.do 参数： 参考数据Model
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(value = { "findChildInfoDetail" })
	public @ResponseBody
	Map<String, Object> findChildInfoDetail(ChildBaseinfo childBaseinfo,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		//数据校验
		if(null == childBaseinfo){
			Map<String, Object> json = JsonBuild.build(false, "参数异常",ResponseStatus.REQUEST_SUCCESS,new ArrayList<ChildBaseinfo>());
			//打印日志
			logger.info("儿童信息查询失败" + JsonMapper.toJsonString(json));
			return json;
		}
		
		String localcode = childBaseinfo.getLocalCode2();
		childBaseinfo.setLocalCode(localcode);
		ArrayList<ChildBaseinfo> childBaseinfolist = new ArrayList<ChildBaseinfo>();
		logger.info("传入的参数" + JsonMapper.toJsonString(childBaseinfo));
		try {
			if (childBaseinfo.getChildcode() != null|| childBaseinfo.getGuardianidentificationnumber() != null) {
					childBaseinfo.setFathercard(childBaseinfo.getGuardianidentificationnumber());
					childBaseinfolist = (ArrayList<ChildBaseinfo>) childBaseinfoService.findList1(childBaseinfo);
				}
			if(childBaseinfolist.size()!=0){
				for(ChildBaseinfo info:childBaseinfolist){
					info.setMouage(DateUtils.getMouthAge(info.getBirthday()));
				}
				Map<String, Object> json = JsonBuild.build(true, "操作成功",ResponseStatus.REQUEST_SUCCESS, childBaseinfolist);
				//打印日志
				logger.info("返回值" + JsonMapper.toJsonString(json));
				return json;
			}else{
				Map<String, Object> json = JsonBuild.build(false, "操作失败，该儿童信息不存在",ResponseStatus.REQUEST_SUCCESS, childBaseinfolist);
				//打印日志
				logger.info("返回值" + JsonMapper.toJsonString(json));
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("儿童基本信息查询异常：" + e);
			return JsonBuild.buildSystemError(null);
		}
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月14日 下午5:48:38
	 * @description 
	 *		根据儿童编码 ，nid 和疫苗价格，来打印排号
	 *请求地址：http://127.0.0.1:8080/vaccinate/f/childBaseinfo/bayNumber.do 
	 * @param product
	 * @param nid
	 * @param childBaseinfo
	 * @param state
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException 
	 *
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "bayNumber" })
	@Transactional
	public @ResponseBody
	Map<String, Object> bayNumber(@RequestParam(required=false, defaultValue="") String list,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		list = org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(list);
		List<Map<String,String>> maps = (List<Map<String,String>>) JsonMapper.fromJsonString(list, List.class);
		HashMap<String, Object> map2=new HashMap<>();
		//List<Map<String,Object>> lists = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> bayNum = new ArrayList<>();
		try {
			String nid;
			String pid;
			String childcode;
			String insurance;
			String source = ChildVaccinaterecord.SOURCE_YTJ;
			String orderNO;
			String payStatus;
//			String payOption;
			String base64;
			String rid;
			String sType = ChildVaccinaterecord.SIGNATURE_SOURCE_SELF;
			String localcode;
			
			if(maps.size() != 0){
				for(Map<String,String> map:maps) {
					nid = map.get("nid");
					pid = map.get("pid");
					childcode = map.get("childcode");
					insurance = map.get("insurance");
	//				source = map.get("source");
					orderNO = map.get("orderNO");
					payStatus = map.get("payStatus");
	//				payOption = map.get("payOption");
					rid = map.get("rid");
					base64 = map.get("base64");
					if( map.get("sType")!=null) {
						sType=map.get("sType");
					}
					localcode = map.get("localcode");
					logger.info("一体机排号参数-->[nid:" + nid + "  pid:" + pid + "  childcode:" + childcode + "  insurance:" + insurance + "  source:" + source + "  orderNO:" + orderNO + "  payStatus:" + payStatus + "  rid:" + rid + "  stype:" + sType+ "  localcode:" + localcode + "  base64:" + StringUtils.isNotBlank(base64) + "]");
					BASE64Decoder decoder = new BASE64Decoder(); 
					byte[] signature = decoder.decodeBuffer(base64.replaceAll("\r", "").replaceAll("\n", ""));
					
					//判断nid是否存在
					if(StringUtils.isBlank(nid)){
						return JsonBuild.build(false, "查询失败，你传入的nid不存在",ResponseStatus.REQUEST_SUCCESS, map2);
					}
					//判断childcode是否存在
					if(StringUtils.isBlank(childcode)){
						return JsonBuild.build(false, "查询失败，你传入的childcode不存在",ResponseStatus.REQUEST_SUCCESS, map2);
					}
					//判断pid是否存在
					if(StringUtils.isBlank(pid)){
						return JsonBuild.build(false, "查询失败，你传入的pid不存在",ResponseStatus.REQUEST_SUCCESS, map2);
					}
					//判断insurance是否存在 
					if(StringUtils.isBlank(insurance)){
						return JsonBuild.build(false, "查询失败，你传入的insurance不存在",ResponseStatus.REQUEST_SUCCESS, map2);
					}
					//判断source是否存在
					if(StringUtils.isBlank(source)){
						return JsonBuild.build(false, "查询失败，你传入的source不存在",ResponseStatus.REQUEST_SUCCESS, map2);
					}
					//判断localcode是否存在
					if(StringUtils.isBlank(localcode)){
						return JsonBuild.build(false, "查询失败，您的站点编号设置失败，请重新设置",ResponseStatus.REQUEST_SUCCESS, map2);
					}
					//判断signature是否存在
					/*if(signature.length == 0){
						return JsonBuild.build(false, "查询失败，你传入的signature不存在",ResponseStatus.REQUEST_SUCCESS, map2);
					}*/
					
					ChildBaseinfo baseinfo = childBaseinfoService.getByNo(childcode,localcode);
					
					
					Map<String, Object> mapNum = new HashMap<String, Object>();
					//String[] nList = null;
					//String[] pList = null; 
					//String[] iList = null; 
					//String msg = "";
					
					//查询大类数据信息
					BsManageVaccinenum num = bsManageVaccinenumService.get(nid,localcode);
					
					if(num == null){
						return JsonBuild.build(false, "查询失败,nid相关大类不存在",ResponseStatus.REQUEST_SUCCESS, map2);
					}
					
					//要打的疫苗信息
					BsManageProduct product	= productService.get(pid);
					
					if(product == null){
						return JsonBuild.build(false, "查询失败,pid相关产品不存在",ResponseStatus.REQUEST_SUCCESS, map2);
					}
					mapNum = queneService.childVaccinatereBayNum(nid, pid, childcode, insurance, source, orderNO, signature,payStatus, rid, sType,localcode);
					mapNum.put("guardianname", baseinfo.getGuardianname());
					mapNum.put("base64", base64);
					// 查询儿童所有未接种的记录					
					ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
					tempRcv.setChildid(baseinfo.getId());
					tempRcv.setLocalCode(localcode);
					List<ChildVaccinaterecord> rcv = childVaccinaterecordService.findListWith4(tempRcv);
					ChildVaccinaterecord vRecord = new ChildVaccinaterecord();
					vRecord.setVaccinatedate(new Date());
					vRecord.setNid(nid);
					vRecord.setSource(ChildVaccinaterecord.SOURCE_DJT);
					vRecord.setStatus(ChildVaccinaterecord.STATUS_YET);
					vRecord.setVaccine(bsManageVaccineService.get(product.getVaccineid()));
					vRecord.setLocalCode(localcode);
					rcv.add(0, vRecord);
					
					// 一体机打印排号，添加小票信息
					addTips(num, product.getVaccineid(), baseinfo, mapNum, rid,rcv,localcode);

					if(!mapNum.containsKey("msg")){
						bayNum.add(mapNum);
					}
					if(bayNum.size() == 0){
						logger.info("参数 bayNum:"+bayNum);
						return JsonBuild.buildValidateMessage(mapNum.get("msg"));
					}
//					else{
//						//Map<String, Object> returnValue = JsonBuild.build(true, "排号成功",ResponseStatus.REQUEST_SUCCESS, bayNum);
//						logger.info("一体机排号返回" + JsonMapper.toJsonString(returnValue));
//						//return returnValue;
//					}
					
				}
			}
			Map<String, Object> returnValue = JsonBuild.build(true, "排号成功",ResponseStatus.REQUEST_SUCCESS, bayNum);
			logger.info("一体机排号返回" + JsonMapper.toJsonString(returnValue));
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询排号异常：" + e);
			return JsonBuild.buildSystemError(null);
		}
	}
	
	/**
	 * 一体机打印排号，添加小票信息
	 * @author fuxin
	 * @date 2017年12月16日 下午4:28:33
	 * @description 
	 *		TODO
	 * @param num
	 * @param vid
	 * @param childid
	 * @param mapNum
	 *
	 */
	private void addTips(BsManageVaccinenum num, String vid , ChildBaseinfo baseinfo,
			Map<String, Object> mapNum, String nid, List<ChildVaccinaterecord> rcv,String localcode){
		//String localcode = "3406030301";
		String pinStr = "";
		ImportContext imp = bsManageVaccineService.getImpartByVaccinateId(vid);
		if(num.getPin() == 1){
			pinStr = "第一剂次";
		}
		if(num.getPin()==2){
			pinStr = "第二剂次";
		}
		if(num.getPin()==3){
			pinStr = "第三剂次";
		}
		if(num.getPin()==4){
			pinStr = "第四剂次";
		}
		if(num.getPin()==5){
			pinStr = "第五剂次";
		}
		if(num.getPin()==6){
			pinStr = "第六剂次";
		}
		if(imp.getGroup().equals("17") && num.getPin() == 1){
			pinStr = "第三剂次";
		}
		if(imp.getGroup().equals("17") && num.getPin()==2){
			pinStr = "第四剂次";
		}
		mapNum.put("title", imp.getTitle().replace("<br>", ""));
		mapNum.put("pin", pinStr);
		mapNum.put("choose", StringEscapeUtils.unescapeHtml4(imp.getChoose()).replace("<p style=#margin: 0; line-height: 22px;font-size: 13px;padding-left: 5px;#><span><strong>请您选择接种的疫苗上打√</strong></span><br><span style=#text-indent: 2em;#>", "")
				.replace("</span></p>", "").replace("</span><br><span style=#text-indent: 2em;#>", ",").replace("<br>", "").replace("<br/>", ""));
		mapNum.put("followCode", Global.getConfig("wechat.url") + "child/attenT.do?id=" + baseinfo.getId());
		
		baseinfo.setMouthAgeInt(DateUtils.subtractMonths(baseinfo.getBirthday(), new Date()));
		
		List<BsManageVaccinenum> vaccs = new ArrayList<BsManageVaccinenum>();
		if(baseinfo.getMouthAgeInt() < 18){ 
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 2),localcode);
		}else if(baseinfo.getMouthAgeInt() < 36){
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 18),localcode);
		}else {
			vaccs = bsManageVaccinenumService.getVaccList(baseinfo.getChildcode(), 0, null,  0, true, "t.MOUAGE", DateUtils.addMonths(new Date(), 26),localcode);
		}
		
		List<Map<String, Object>> date1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> date2 = new ArrayList<Map<String, Object>>();
		for(BsManageVaccinenum va : vaccs){
			BsManageVaccinenum temp = bsManageVaccinenumService.getNextVaccDate(rcv, baseinfo, va,localcode);
			if(temp.getVaccDate() != null){
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("vaccName", va.getName());
				tempMap.put("vaccDate", sysHolidayService.nextWorkDay(va.getVaccDate(),localcode));
				if(temp.getType() == 1){
					date1.add(tempMap);
				}else{
					date2.add(tempMap);
				}
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
		date1.sort(compar);
		date2.sort(compar);
		
		if(date1.size() == 0 && date2.size() == 0){
			mapNum.put("nextDate", "");
			mapNum.put("nextDateEx", "");
		}else if(date1.size() == 0){
			mapNum.put("nextDate", DateUtils.formatDate((Date)date2.get(0).get("vaccDate")) + "[" + date2.get(0).get("vaccName") + "]" + "（自费）");
			mapNum.put("nextDateEx", "");
		}else if(date2.size() == 0){
			mapNum.put("nextDate", DateUtils.formatDate((Date)date1.get(0).get("vaccDate")) + "[" + date1.get(0).get("vaccName") + "]" + "（免费）");
			mapNum.put("nextDateEx", "");
		}else{
			if(((Date) date2.get(0).get("vaccDate")).before((Date) date1.get(0).get("vaccDate"))){
				mapNum.put("nextDate", DateUtils.formatDate((Date)date1.get(0).get("vaccDate")) + "[" + date1.get(0).get("vaccName") + "]" + "（免费）");
				mapNum.put("nextDateEx", DateUtils.formatDate((Date)date2.get(0).get("vaccDate")) + "[" + date2.get(0).get("vaccName") + "]" + "（自费）");
			}else{
				mapNum.put("nextDate", DateUtils.formatDate((Date)date1.get(0).get("vaccDate")) + "[" + date1.get(0).get("vaccName") + "]" + "（免费）");
				mapNum.put("nextDateEx", "");
			}
		}

	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月30日 下午1:47:19
	 * @description 
	 *		TODO根据儿童编码查询儿童的接种记录
	 *请求地址：http://127.0.0.1:8080/vaccinate/f/childBaseinfo/vaccrecord.do 
	 * @param childBaseinfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping(value = { "vaccrecord" })
	public @ResponseBody
	Map<String, Object> vaccrecord(@RequestBody ChildBaseinfo childBaseinfo,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		String localcode=childBaseinfo.getLocalCode2();
		childBaseinfo.setLocalCode(localcode);
		try {
			List<List<ChildVaccinaterecord>> returnlist = new ArrayList<>();
			ArrayList<ChildVaccinaterecord> list = new ArrayList<>();
			List<ChildBaseinfo> infolist = childBaseinfoService.findList(childBaseinfo);
			if(infolist.size()>0){
				ChildVaccinaterecord childVaccinaterecord = new ChildVaccinaterecord();
				childVaccinaterecord.setLocalCode(localcode);
				childVaccinaterecord.setChildid(infolist.get(0).getId());
				list = (ArrayList<ChildVaccinaterecord>) childVaccinaterecordService.findList(childVaccinaterecord);
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getStatus().equals("0")){
						list.remove(i);
						i = i - 1;
					}
				}
				
				//数组转树形
				String op = "first";
				List<ChildVaccinaterecord> templist = new ArrayList<>();
				for(ChildVaccinaterecord cv : list){
					if(op.equals("first")){
						op = cv.getVaccCode();
					}
					if(!op.equals(cv.getVaccCode())){
						templist.get(0).setLeng(templist.size()+1);
						returnlist.add(templist);
						templist = new ArrayList<>();
						op = cv.getVaccCode();
					}
					templist.add(cv);
				}
				if(templist.size() > 0){
					templist.get(0).setLeng(templist.size()+1);
					returnlist.add(templist);
				}
				//数组转树形 结束
				
				//数组转换成html
				String str = "";
				str += "<!DOCTYPE html>";
				str += "<html>";
				str += "<head>";
				str += "<meta charset=\"UTF-8\">";
				str += "<title>已种疫苗</title>";
				str += "</head>";
				str += "<body>";
				str += "<div>";
				str += "<div>";
				str += "<h1 style=\"text-align:center;\">已种疫苗</h1>";
				str += "<div>";
				if(returnlist.size() <= 0){
					str += "暂无数据";
				}
				if(returnlist.size() > 0){
					str += "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%;\">";
					for(List<ChildVaccinaterecord> l : returnlist){
						str += "<tr style=\"text-align:center;\">";
						str += "<td rowspan='" + l.get(0).getLeng() +" '> " + l.get(0).getVaccCode() + "</td>";
						str += "<td>针次</td>";
						str += "<td>接种时间</td>";
						str += "<td>名称</td>";
						str += "</tr>";
						for(ChildVaccinaterecord ii : l){
							str += "<tr style=\"text-align:center;\">";
							str += "<td>第" + ii.getDosage()  + "剂</td>";
							String date = DateUtils.formatDate(ii.getVaccinatedate(), "yyyy-MM-dd");
							str += "<td>" + date + "</td>";
							str += "<td>" + ii.getVaccName() + "</td>";
							str += "</tr>";
						}
					}
					str += "</table>";
				}
				str += "</div>";
				str += "</div>";
				str += "</div>";
				str += "</body>";
				str += "</html>";
				
				str = StringEscapeUtils.unescapeHtml4(str);
				File f = new File("d://html.html");
				FileWriter w = new FileWriter(f);
				w.write(str);
				w.flush(); 
				w.close();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("html", str);
				return JsonBuild.build(true, "儿童基本信息详情信息成功",ResponseStatus.REQUEST_SUCCESS, map);
			}
			return JsonBuild.build(false, "没找到该儿童的用户信息",ResponseStatus.REQUEST_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("儿童基本信息查询异常：" + e);
			return JsonBuild.buildSystemError(null);
		}
	}
	
	/**
	 * 犬伤排号
	 * @author fuxin
	 * @date 2017年7月7日 下午3:17:41
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@RequestMapping("/insertQueneDoge")
	public @ResponseBody Object insertQueneDoge(HttpServletResponse response){
		response.setHeader( "Access-Control-Allow-Origin", "*" ); 
		logger.info("===接到犬伤排号请求===");
		String queneno = "";
		//获取接种科室
		String roomCode = officeService.getOfficeCodeByVaccined(BsManageVaccine.VACC_DOGE_GNUM);
		logger.debug("获取犬伤科室：" + roomCode);
		if(null != roomCode){
			//获取最新排号
			queneno = queneService.getLastQueneCode(roomCode);
			logger.debug("获取犬伤排号：" + queneno);
			try {
				Quene q = new Quene();
				q.setQueueno(queneno);
				q.setChildid(queneno);
				q.setVaccineid(BsManageVaccine.VACC_DOGE_GNUM);
				q.setRoomcode(roomCode);
				q.setIspass(Quene.ISPASS_N);
				q.setStatus(Quene.STATUS_NORMAL);
				queneService.save(q);
			} catch (Exception e) {
				logger.error("获取犬伤排号失败!!!");
			}
		}
		queneno += "_" + queneService.waitNum(queneno);
		logger.info("===接到犬伤排号请求结束  --> " + queneno);
		return queneno;
	}
	
	/**
	 * 犬伤叫号
	 * @author fuxin
	 * @date 2017年7月7日 下午4:52:20
	 * @description 
	 *		TODO
	 * @param queneno
	 * @return
	 *
	 */
	@Deprecated
	@RequestMapping("/callQueneDoge")
	public @ResponseBody Object callQueneDoge(HttpServletResponse response,
			@RequestParam(required=false, value="queueno") String queneno,
			@RequestParam(required=false, value="localCode") String localCode){
			response.setHeader( "Access-Control-Allow-Origin", "*" ); 
		if(StringUtils.isBlank(localCode)){
			logger.error("犬伤叫号失败,缺少localcode");
			return "error";
		}
		try {
			if(StringUtils.isNotBlank(queneno)){
				//获取将要叫号信息
				Quene q = new Quene(StringUtils.EMPTY,  queneno);
				q.setLocalCode(localCode);
				Quene quene = queneService.get(q);
				//获取下一号
				Quene nextQuene = null;
				List<Quene> qlist = queneService.getPassN(quene);
				for(Quene qu: qlist){
					if(qu.getQueueno().equals(quene.getQueueno())){
						continue;
					}
					nextQuene = qu;
					break;
				}
				queneService.refresh(quene, nextQuene, localCode);
			}else{
				//获取当前最新排号
				Quene q = new Quene();
				q.setIspass("N");
				//TODO:fuxin获取科室信息
				q.setRoomcode(officeService.getOfficeCodeByVaccined(BsManageVaccine.VACC_DOGE_GNUM));
				q.setLocalCode(localCode);
				List<Quene> list = queneService.findList(q);
				Quene quene = null;
				//获取下一号
				Quene nextQuene = null;
				if(list.size() > 0){
					quene = list.get(0);
				}
				if(list.size() > 1){
					nextQuene = list.get(1);
				}
				queneService.refresh(quene, nextQuene, localCode);
			}
		} catch (Exception e) {
			return "fail";
		}
		
		return "success";
	}
	
	/**
	 * 犬伤叫号
	 * @author fuxin
	 * @date 2017年7月7日 下午4:52:20
	 * @description 
	 *		TODO
	 * @param queneno
	 * @return
	 *
	 */
	@RequestMapping("/finishQueneDoge")
	public @ResponseBody Object finishQueneDoge(HttpServletResponse response,
			@RequestParam(required=false, value="queueno") String queneno,
			@RequestParam(required=false, value="localCode") String localCode){
			response.setHeader( "Access-Control-Allow-Origin", "*" ); 

		if(StringUtils.isBlank(localCode)){
			logger.error("犬伤排号完成失败,缺少localcode");
			return "error";
		}
		try {
			Quene q = new Quene();
			q.setStatus(Quene.STATUS_NORMAL);
			q.setIspass(Quene.ISPASS_N);
			q.setRoomcode(officeService.getOfficeCodeByVaccined(BsManageVaccine.VACC_DOGE_GNUM));
			q.setOrderBy(" a.queueno ");
			q.setLocalCode(localCode);
			//返回的qq
			List<Quene> listA = queneService.findList(q);
			if(listA != null && listA.size()>0){
				if(StringUtils.isNotBlank(queneno)){
					for(Quene qq : listA){
						if(qq.getQueueno().equals(queneno)){
							//完成
							qq.setStatus(Quene.STATUS_FINSH);
							queneService.update(qq);
							queneService.refresh(localCode);
						}
					}
				}else{
					//完成
					listA.get(0).setStatus(Quene.STATUS_FINSH);
					queneService.update(listA.get(0));
					queneService.refresh(localCode);
				}
			}
			listA = queneService.findList(q);
			if(listA.size() > 0){
				return listA.get(0).getQueueno();
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	
	/**
	 * 获取犬伤排号
	 * @author fuxin
	 * @date 2017年7月7日 下午4:52:20
	 * @description 
	 *		TODO
	 * @param queneno
	 * @return
	 *
	 */
	@RequestMapping("/QueneDoge")
	public @ResponseBody Object QueneDoge(HttpServletResponse response){
		response.setHeader( "Access-Control-Allow-Origin", "*" ); 
		try {
			Quene q = new Quene();
			q.setStatus(Quene.STATUS_NORMAL);
			q.setIspass(Quene.ISPASS_N);
			q.setRoomcode(officeService.getOfficeCodeByVaccined(BsManageVaccine.VACC_DOGE_GNUM));
			q.setOrderBy(" a.queueno ");
			//返回的qq
			List<Quene> listA = queneService.findList(q);
			if(listA.size() > 0){
				return listA.get(0).getQueueno();
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	
	@RequestMapping(value = "/wsdlProxy/{methodNme}" ,method=RequestMethod.POST)
	public @ResponseBody Object wsdlProxy(HttpServletResponse response, 
			@PathVariable(value="methodNme") String methodNme, 
			@RequestParam(required=false) List<Object> data){
		List<ChildBaseinfo> bsList = new ArrayList<ChildBaseinfo>();
		ChildBaseinfo base = new ChildBaseinfo();
		base.setSituations("1,2,9");
		if(data.toArray().length==3 && data.toArray()[0].toString().length()==18){
			base.setChildcode(data.toArray()[0].toString());
			base.setLocalCode(data.toArray()[2].toString());
			bsList=childBaseinfoService.findList(base);
		}else if(data.toArray().length>3){
			base.setChildname(data.toArray()[0].toString());
			base.setGender(data.toArray()[1].toString());
			base.setBirthday(DateUtils.parseDate(data.toArray()[2].toString()));
			if( Boolean.parseBoolean(data.toArray()[4].toString())){
				base.setGuardianname(data.toArray()[3].toString());
			}else {
				base.setFather(data.toArray()[3].toString());
			}
			bsList=childBaseinfoService.findList(base);
		}
		if(!bsList.isEmpty()){
			return bsList;
		}
		
		response.setHeader( "Access-Control-Allow-Origin", "*" ); 
		logger.info("wsdl代理开始:methodNme=" + methodNme + ",data=" + JsonMapper.toJsonString(data));
		return HttpClientReq.webServiceInvoke(methodNme,data.toArray(), logger);
	}
	
	@RequestMapping("/TestPay")
	public @ResponseBody Object TestPay(HttpServletResponse response, HttpServletRequest request, String httpSessionId){
		try {
			 HttpSession pc_httpSession = DwrUtils.httpSessionMap.get(httpSessionId);  
            pc_httpSession.setAttribute("jsonstring", "from_API");  
            DwrUtils.send(httpSessionId, "from_API"); 
            DwrUtils.send(httpSessionId, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping("/clearRecord")
	public @ResponseBody String clearRecord(String childcode, String localcode){
		childVaccinaterecordService.clearRecord(childcode, localcode);
		return "success";
	}
	
	@RequestMapping("/clearAllRecord/{localcode}")
	public @ResponseBody String clearAllRecord(@PathVariable("localcode")String localcode,
			@RequestParam(value="year", defaultValue="0", required=false ) int year){
		ChildBaseinfo base = new ChildBaseinfo();
		base.setUpstatus("1");
		base.setLocalCode(localcode);
		List<ChildBaseinfo> allChild = childBaseinfoService.findList(base);
		int count = 0;
		if(year != 0){
			for(int i = 0; i < allChild.size(); i ++){
				if((allChild.get(i).getBirthday().getYear() + 1900) != year){
					allChild.remove(i--);
					continue;
				}
			}
		}
		int i = 0;
		while(i < allChild.size()){
			for(int j = i; j < i+30; j ++ ){
				logger.info("clearRecord--- [" + (i+j) + "]");
				if(j >= allChild.size()){
					continue;
				}
				logger.info("clearRecord[" + allChild.get(j).getChildcode() + "]");
				asyncService.clearRecordAsync(allChild.get(j).getChildcode(), localcode);
				count ++;
			}
			i+=30;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace(); 
			}
		}
		try {
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "success" + count;
	}
	
	@RequestMapping(value = { "reserveApi" })
	public @ResponseBody Map<String, Object> reserveApi(ChildBaseinfo childBaseinfo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String localcode =childBaseinfo.getLocalCode2();
		childBaseinfo.setLocalCode(localcode);
		logger.info("一体机获取可接种疫苗传入的参数" + JsonMapper.toJsonString(childBaseinfo));
		try {
			if (StringUtils.isBlank(childBaseinfo.getChildcode())) {
				return JsonBuild.buildValidateMessage("参数有误");
			}
			//判断儿童信息是否存在
			childBaseinfo = childBaseinfoService.getByNo(childBaseinfo.getChildcode(),localcode);
			if (childBaseinfo != null) {
				
				VacChildRemind temp = new VacChildRemind();
				temp.setChildcode(childBaseinfo.getChildcode());
				temp.setEndSelectDate(DateUtils.getDayEnd(new Date()));
				temp.setStatus(VacChildRemind.STATUS_NORMAL);
				temp.setLocalCode(localcode);
				List<VacChildRemind> reminds = remindService.findList(temp);
				
				//查询接种记录状态
				//List<Object> returnList = new ArrayList<Object>();
				//returnList.add(remindService.toApiVaccGroupVo(reminds, childBaseinfo.getLocalCode()));
				List<ApiVaccGroupVo> returnList = remindService.toApiVaccGroupVo(reminds, childBaseinfo.getLocalCode());
				//returnList.add(new ArrayList<VacChildRemind>());
				
				//if(returnList.size()>0){
					Map<String, Object> json = JsonBuild.build(true, "查询儿童疫苗信息成功",ResponseStatus.REQUEST_SUCCESS, returnList);
					logger.info("一体机获取可接种疫苗返回值" + JsonMapper.toJsonString(json));
					return JsonBuild.build(true, "查询疫苗信息成功",ResponseStatus.REQUEST_SUCCESS, returnList);
				//}else{
				//	return JsonBuild.build(true, "查询失败",ResponseStatus.REQUEST_SUCCESS, returnList);
				//}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("儿童预约疫苗信息查询异常：" + e);
			return JsonBuild.buildSystemError(null);
		}
		logger.error("儿童预约疫苗信息查询失败");
		return JsonBuild.build(false, "儿童预约疫苗信息查询失败,未找到儿童信息",ResponseStatus.SYSTEM_ERROR,null); 
	}
	
}
