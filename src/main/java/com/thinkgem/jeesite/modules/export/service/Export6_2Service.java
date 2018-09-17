package com.thinkgem.jeesite.modules.export.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.export.dao.Export6_2Dao;
import com.thinkgem.jeesite.modules.export.entity.Export;
import com.thinkgem.jeesite.modules.export.entity.Export6_2;
import com.thinkgem.jeesite.modules.export.entity.ExportChildhelp;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 报表6-2 Service
 * 
 * @author Jack
 * @date 2017年10月23日 下午6:39:04
 * @description
 */

@Service
public class Export6_2Service extends CrudService<Export6_2Dao, Export6_2> {
	@Autowired
	Export6_2Dao dao;
	@Autowired
	OfficeService officeService;
	@Autowired
	SysAreaService sysAreaService;
	
	// ------------------------------------------第二类疫苗接种情况报表6-2开始-----------------------------------------------------------------//
	/**
	 * 第二类疫苗接种情况报表6-2数据处理
	 * @author Jack
	 * @date 2017年10月24日 上午9:07:51
	 * @description
	 * @param request
	 * @param export
	 * @param model
	 * @param yearstr
	 * @param monthstr
	 *
	 */
	public void dataService(HttpServletRequest request, Export export, Model model, String yearstr, String monthstr) {
		String yigan = "3901, 0201, 0202, 0203,"; //乙肝
		String baibaipo = "0401, 0402, 0403,"; //百白破
		String fengzhen = "1101,1102,"; //风疹
		String masaifeng = "1201,"; //麻腮风
		String mafeng = "1401,"; //麻风
		String ACliunaojiehe = "1702,"; //A+C群流脑疫苗(结合)
		String yinaojiandu = "1801,"; //乙脑减毒活疫苗
		String jiaganmiehuo = "1903,"; //甲肝灭活疫苗
		String shuidou = "2201,"; //水痘疫苗
		String lunzhuangbingdu = "2401,"; //轮状病毒疫苗
		String sevenjiafeiyanqiujun = "2502,"; //7价肺炎球菌疫苗
		String liji = "3301,"; //痢疾疫苗
		String shuyi = "3501,"; //鼠疫疫苗
		String bubing = "3701,"; //布病疫苗
		String senlinnaoyan = "4701,"; //森林脑炎疫苗
		String baibaipo_IPV_Hib_wulian = "5001,"; //百白破 IPV和Hib五联疫苗
		String ACliunaoHiblianhe = "5301,"; //A+C流脑Hib联合疫苗
		String chuxuere = "2901, 2902, 2903, 2904, 2905, 2906,"; //出血热
		String kuangquanbing = "4301, 4401, 4402, 2801, 2802, 2803, 2804, 2805,"; //狂犬病
		String jihui = "0303,";//脊灰疫苗(Salk株)
		String baipo = "0601,"; //白破疫苗
		String saixianyan = "1001,"; //腮腺炎疫苗
		String masai = "1301,"; //麻腮疫苗 
		String ACqunliunao = "1701,"; //A+C群流脑疫苗(多糖)
		String ACYW15liunao = "1703,"; //ACYW135流脑疫苗(多糖)
		String yinaomiehuo = "1802, 1803,"; //乙脑灭活疫苗(Vero)
		String jiayigan = "2001,"; //甲乙肝疫苗
		String Hib = "2301,"; //Hib疫苗
		String _23jiafeiyan = "2501,"; //23价肺炎球菌疫苗
		String shanghan = "3001, 3002, 3101, 3201,"; //伤寒
		String gouti = "3401,"; //钩体疫苗
		String tanju = "3601,"; //炭疽疫苗
		String huoluan = "3801,"; //霍乱疫苗
		String DTapHiblianhe = "4901,"; //DTaP-Hib联合疫苗
		String wugan = "5201,"; //戊肝疫苗
		String _71xingmiehuo = "5401,5402,"; //71型灭活疫苗
		String jiagan = "1901, 1902,"; //甲肝减毒(HepA-L)
		String liugan = "2101, 2102, 2103, 2104, 2105, 2106,"; //流感(Flu)
		//所有小类疫苗目前纳入64个
		
		String vaccCode = yigan + baibaipo  + masaifeng + mafeng + ACliunaojiehe + yinaojiandu + jiaganmiehuo + shuidou + lunzhuangbingdu + sevenjiafeiyanqiujun
				+ liji + shuyi + bubing + senlinnaoyan + baibaipo_IPV_Hib_wulian + ACliunaoHiblianhe + chuxuere + kuangquanbing + jihui + baipo
				+ saixianyan  + ACqunliunao + ACYW15liunao + yinaomiehuo + jiayigan + Hib + _23jiafeiyan + shanghan + gouti + tanju
				+ huoluan + DTapHiblianhe + wugan + _71xingmiehuo + jiagan + liugan + masai + fengzhen;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(yearstr) && StringUtils.isNotBlank(monthstr)) {
			String begintime = getFirstDayOfMonth(Integer.valueOf(yearstr), Integer.valueOf(monthstr));
			String endtime = getLastDayOfMonth(Integer.valueOf(yearstr), Integer.valueOf(monthstr));
			export.setBegintime(begintime);
			export.setEndtime(endtime);
			model.addAttribute("yearstr", yearstr);
			model.addAttribute("monthstr", monthstr);
			model.addAttribute("date", df.format(new Date()));
		} else {
			// 没有时间默认上月
			Integer endMonth = Integer.parseInt(DateUtils.getMonth());
			if(endMonth == 1){
				yearstr = (Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy"))-1) + "";
				monthstr = "12";
				export.setBegintime(getFirstDayOfMonth(Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")) - 1, 12));
				export.setEndtime(getLastDayOfMonth(Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")) - 1, 12));
				model.addAttribute("yearstr", Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy"))-1 + "");
				model.addAttribute("monthstr", "12");
			}else{
				yearstr = DateUtils.formatDate(new Date(), "yyyy");
				monthstr = DateUtils.formatDate(new Date(), "MM");
				export.setBegintime(getFirstDayOfMonth(Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")),
						Integer.valueOf(DateUtils.formatDate(new Date(), "MM")) - 1));
				export.setEndtime(getLastDayOfMonth(Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")),
						Integer.valueOf(DateUtils.formatDate(new Date(), "MM")) - 1));
				model.addAttribute("yearstr", DateUtils.formatDate(new Date(), "yyyy"));
				model.addAttribute("monthstr", endMonth-1);
			}
			model.addAttribute("date", df.format(new Date()));
		}
		// 根据code 查询所属的省，市 ，区
		String localCode = OfficeService.getFirstOfficeCode();// 获取一级单位编码 3406030301
		if (StringUtils.isNotBlank(localCode)) {
			String sheng = StringUtils.substring(localCode, 0, 2);
			String shi = StringUtils.substring(localCode, 2, 4);
			String qu = StringUtils.substring(localCode, 4, 6);
			model.addAttribute("sheng", sysAreaService.getAreaNameById(Integer.parseInt(sheng + "0000")).getName());
			model.addAttribute("shi", sysAreaService.getAreaNameById(Integer.parseInt(sheng + shi + "00")).getName());
			model.addAttribute("qu", sysAreaService.getAreaNameById(Integer.parseInt(sheng + shi + qu)).getName());
		}
		
		if (StringUtils.isNotBlank(yearstr) && StringUtils.isNotBlank(monthstr)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startTime", export.getBegintime() + "00:00:00");
			map.put("endTime", export.getEndtime() + "23:59:59");
			
			// 转换疫苗编码
			List<String> list = StringUtils.splitToList(vaccCode, ",");
			map.put("list", list);
			map.put("localCode", localCode);
			
			// 统计数据start
			List<HashMap<String, String>> yiganMaps = new LinkedList<HashMap<String, String>>(); //乙肝
			List<HashMap<String, String>> baibaipoMaps = new LinkedList<HashMap<String, String>>(); //百白破
			List<HashMap<String, String>> chuxuereMaps = new LinkedList<HashMap<String, String>>(); //出血热
			List<HashMap<String, String>> kuangquanMaps = new LinkedList<HashMap<String, String>>(); //狂犬病
			List<HashMap<String, String>> yinaomiehuoMaps = new LinkedList<HashMap<String, String>>(); //乙脑灭活
			List<HashMap<String, String>> shanghanMaps = new LinkedList<HashMap<String, String>>(); //伤寒
			List<HashMap<String, String>> jiaganjianduMaps = new LinkedList<HashMap<String, String>>(); //甲肝减毒
			List<HashMap<String, String>> liuganMaps = new LinkedList<HashMap<String, String>>(); //流感
			List<HashMap<String, String>> _71xingmiehuoMaps = new LinkedList<HashMap<String, String>>(); //71型肠道病毒
			List<HashMap<String, String>> fengzhenMaps = new LinkedList<HashMap<String, String>>(); //风疹
			List<HashMap<String, String>> maps = typeTwoVaccReport6_2(map);

			for (int i = 0; i < maps.size(); i++) {
				// 处理:乙肝,百白破,出血热,狂犬,肠道71型病毒,乙脑,伤寒,甲肝减毒,流感的各个疫苗小类
				HashMap<String, String> tmpMap = maps.get(i);
				
				if (tmpMap.get("id").equals("3901") || tmpMap.get("id").equals("0201")
						|| tmpMap.get("id").equals("0202") || tmpMap.get("id").equals("0203")) {
					// 乙肝总和统计
					yiganMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("0401") || tmpMap.get("id").equals("0402") 
						|| tmpMap.get("id").equals("0403")) {
					// 百白破总和统计
					baibaipoMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("2901") || tmpMap.get("id").equals("2902")
						|| tmpMap.get("id").equals("2903") || tmpMap.get("id").equals("2904")
						||tmpMap.get("id").equals("2905") || tmpMap.get("id").equals("2906")
						) {
					// 出血热总和统计
					chuxuereMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("4301") || tmpMap.get("id").equals("4401")
						|| tmpMap.get("id").equals("4402") || tmpMap.get("id").equals("2801")
						|| tmpMap.get("id").equals("2802") || tmpMap.get("id").equals("2803")
						|| tmpMap.get("id").equals("2804") || tmpMap.get("id").equals("2805")) {
					// 狂犬总和统计
					kuangquanMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("5401") || tmpMap.get("id").equals("5402")) {
					// 肠道病毒71总和统计
					_71xingmiehuoMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("1802") || tmpMap.get("id").equals("1803")) {
					// 乙脑灭活总和统计
					yinaomiehuoMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("3001") || tmpMap.get("id").equals("3002")
						|| tmpMap.get("id").equals("3101") || tmpMap.get("id").equals("3201")) {
					// 伤寒总和统计
					shanghanMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("1901") || tmpMap.get("id").equals("1902")) {
					// 甲肝减毒总和统计
					jiaganjianduMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("2101") || tmpMap.get("id").equals("2102")
						|| tmpMap.get("id").equals("2103") || tmpMap.get("id").equals("2104")
						|| tmpMap.get("id").equals("2105") || tmpMap.get("id").equals("2106")
						) {
					// 流感总和统计
					liuganMaps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("1101") || tmpMap.get("id").equals("1102")) {
					// 风疹总和统计
					fengzhenMaps.add(tmpMap);
				}
			}
			
			// 删除maps中:乙肝,百白破,出血热,狂犬,肠道71型病毒,乙脑,伤寒,甲肝减毒,流感的各个疫苗小类
			List<HashMap<String, String>> delList = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < maps.size(); i++) {
				HashMap<String, String> delMap = maps.get(i);
				if (delMap.get("id").equals("3901")
						|| delMap.get("id").equals("0202") || delMap.get("id").equals("0203")
						//乙肝结束
						
						|| delMap.get("id").equals("0402") || delMap.get("id").equals("0403")
						//百白破结束
						
						|| delMap.get("id").equals("2901") || delMap.get("id").equals("2902")
						|| delMap.get("id").equals("2903") || delMap.get("id").equals("2904")
						|| delMap.get("id").equals("2905") || delMap.get("id").equals("2906")
						//出血热结束
						
						|| delMap.get("id").equals("4301") || delMap.get("id").equals("4401")
						|| delMap.get("id").equals("4402") || delMap.get("id").equals("2801")
						|| delMap.get("id").equals("2802") || delMap.get("id").equals("2803")
						|| delMap.get("id").equals("2804") || delMap.get("id").equals("2805")
						//狂犬结束
						
						|| delMap.get("id").equals("5402")
						//71型肠道病毒结束
						
						|| delMap.get("id").equals("1803")
						//乙脑灭活结束
						
						|| delMap.get("id").equals("1102")
						//风疹结束
						
						|| delMap.get("id").equals("3002")
						|| delMap.get("id").equals("3101") || delMap.get("id").equals("3201")
						//伤寒结束
						
						|| delMap.get("id").equals("1901") || delMap.get("id").equals("1902")
						//甲肝减毒结束
						
						|| delMap.get("id").equals("2101") || delMap.get("id").equals("2102")
						|| delMap.get("id").equals("2103") || delMap.get("id").equals("2104")
						|| delMap.get("id").equals("2105") || delMap.get("id").equals("2106")
						//流感结束
						) {
					delList.add(delMap);
				}
			}
			for (HashMap<String, String> i : delList) {
				maps.remove(i);
			}

			// 暂存num和Map的变量
			int sum = 0;
			HashMap<String, String> innerMap = null;
			
			// 乙肝总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < yiganMaps.size(); i++) {
				HashMap<String, String> tmpMap = yiganMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "0200");
			innerMap.put("name", "乙肝(HepB)");
			innerMap.put("num", sum + "");
			yiganMaps.clear();
			yiganMaps.add(innerMap);
			sum = 0;
			innerMap = null;
			
			// 百白破总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < baibaipoMaps.size(); i++) {
				HashMap<String, String> tmpMap = baibaipoMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "0400");
			innerMap.put("name", "百白破(DTP)");
			innerMap.put("num", sum + "");
			baibaipoMaps.clear();
			baibaipoMaps.add(innerMap);
			sum = 0;
			innerMap = null;
			
			// 出血热总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < chuxuereMaps.size(); i++) {
				HashMap<String, String> tmpMap = chuxuereMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "2900");
			innerMap.put("name", "出血热(HF)");
			innerMap.put("num", sum + "");
			chuxuereMaps.clear();
			chuxuereMaps.add(innerMap);
			sum = 0;
			innerMap = null;
			
			// 狂犬总和计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < kuangquanMaps.size(); i++) {
				HashMap<String, String> tmpMap = kuangquanMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "2800");
			innerMap.put("name", "狂犬病(Rab)");
			innerMap.put("num", sum + "");
			kuangquanMaps.clear();
			kuangquanMaps.add(innerMap);
			sum = 0;
			innerMap = null;
			
			// 71型肠道病毒总和计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < _71xingmiehuoMaps.size(); i++) {
				HashMap<String, String> tmpMap = _71xingmiehuoMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "5400");
			innerMap.put("name", "肠道病毒71型疫苗(灭活)()EV71V-i");
			innerMap.put("num", sum + "");
			_71xingmiehuoMaps.clear();
			_71xingmiehuoMaps.add(innerMap);
			sum = 0;
			innerMap = null;
			
			// 乙脑灭活总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < yinaomiehuoMaps.size(); i++) {
				HashMap<String, String> tmpMap = yinaomiehuoMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "1800");
			innerMap.put("name", "乙脑(灭活)");
			innerMap.put("num", sum + "");
			yinaomiehuoMaps.clear();
			yinaomiehuoMaps.add(innerMap);
			sum = 0;
			innerMap = null;
			
			
			// 伤寒总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < shanghanMaps.size(); i++) {
				HashMap<String, String> tmpMap = shanghanMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "3100");
			innerMap.put("name", "伤寒");
			innerMap.put("num", sum + "");
			shanghanMaps.clear();
			shanghanMaps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 甲肝减毒总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < jiaganjianduMaps.size(); i++) {
				HashMap<String, String> tmpMap = jiaganjianduMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "1900");
			innerMap.put("name", "甲肝减毒(HepA-L)");
			innerMap.put("num", sum + "");
			jiaganjianduMaps.clear();
			jiaganjianduMaps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 流感总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < liuganMaps.size(); i++) {
				HashMap<String, String> tmpMap = liuganMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "2100");
			innerMap.put("name", "流感(Flu)");
			innerMap.put("num", sum + "");
			liuganMaps.clear();
			liuganMaps.add(innerMap);
			sum = 0;
			innerMap = null;
			
			// 风疹总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < fengzhenMaps.size(); i++) {
				HashMap<String, String> tmpMap = fengzhenMaps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "0200");
			innerMap.put("name", "风疹(Rub)");
			innerMap.put("num", sum + "");
			fengzhenMaps.clear();
			fengzhenMaps.add(innerMap);
			sum = 0;
			innerMap = null;
			
			String chuxuereStr = JsonMapper.toJsonString(chuxuereMaps);
			String jiaganjianduStr = JsonMapper.toJsonString(jiaganjianduMaps);
			String kuangquanStr = JsonMapper.toJsonString(kuangquanMaps);
			String liuganStr = JsonMapper.toJsonString(liuganMaps);
			String mapsStr = JsonMapper.toJsonString(maps);
			String result = mapsStr.replace("]", "") + "," + chuxuereStr.replace("[", "").replace("]", "") + ","
					+ jiaganjianduStr.replace("[", "").replace("]", "") + ","
					+ kuangquanStr.replace("[", "").replace("]", "") + "," + liuganStr.replace("[", "");
			model.addAttribute("maps", result);
		}
		model.addAttribute("username", UserUtils.getUser().getName());
		model.addAttribute("year", DateUtils.formatDate(new Date(), "yyyy"));
		model.addAttribute("month", DateUtils.formatDate(new Date(), "MM"));
		model.addAttribute("date", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		model.addAttribute("off", OfficeService.getFirstOffice().getName());

	}
	
	// ------------------------------------------第二类疫苗接种情况报表6-2结束-----------------------------------------------------------------//
	// ------------------------------------------常规免疫接种情况6-2汇总表开始-----------------------------------------------------------------//
	/**
	 * 常规免疫接种情况6-2汇总表 数据处理逻辑
	 * 
	 * @author Jack
	 * @return
	 * @date 2017年10月24日 上午9:22:01
	 * @description
	 *
	 */
	public void collectDataService(ExportChildhelp exportChildhelp, Model model, String yearstr, String startmonthstr,
			String endmonthstr, String vaccCode, String vaccname) {
		// 获取localCode编码
		String localCode = OfficeService.getFirstOfficeCode();// 3406030301
		if (StringUtils.isNotBlank(localCode)) {
			String sheng = StringUtils.substring(localCode, 0, 2);
			String shi = StringUtils.substring(localCode, 2, 4);
			String qu = StringUtils.substring(localCode, 4, 6);
			model.addAttribute("sheng", sysAreaService.getAreaNameById(Integer.parseInt(sheng + "0000")).getName());
			model.addAttribute("shi", sysAreaService.getAreaNameById(Integer.parseInt(sheng + shi + "00")).getName());
			model.addAttribute("qu", sysAreaService.getAreaNameById(Integer.parseInt(sheng + shi + qu)).getName());
		}
		
		if (StringUtils.isNotBlank(yearstr) && StringUtils.isNotBlank(endmonthstr)
				&& StringUtils.isNotBlank(startmonthstr)) {
			// 处理和绑定时间
			exportChildhelp
					.setStartTime(getFirstDayOfMonth(Integer.valueOf(yearstr), Integer.parseInt(startmonthstr))
							+ " " + "00:00:00");
			exportChildhelp.setEndTime(
					getLastDayOfMonth(Integer.valueOf(yearstr), Integer.parseInt(endmonthstr)) + " " + "23:59:59");
			model.addAttribute("yearstr", yearstr);
			model.addAttribute("startmonthstr", startmonthstr);
			model.addAttribute("endmonthstr", endmonthstr);
		} else {
			// 没有时间 则默认当月
			Integer endMonth = Integer.parseInt(DateUtils.getMonth());
			exportChildhelp.setStartTime((Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy"))) + "-"
					+ (DateUtils.formatDate(new Date(), "MM")) + "-01" + " " + "00:00:00");
			exportChildhelp.setEndTime(getLastDayOfMonth(Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")),
					Integer.valueOf(DateUtils.formatDate(new Date(), "MM")) - 1) + " " + "23:59:59");
			model.addAttribute("startmonthstr", "01");
			
			if(endMonth == 1){
				model.addAttribute("yearstr", Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy"))-1 + "");
				model.addAttribute("endmonthstr", "12");
			}else{
				model.addAttribute("yearstr", DateUtils.formatDate(new Date(), "yyyy"));
				model.addAttribute("endmonthstr", endMonth-1);
			}
		}
		if (StringUtils.isNotBlank(vaccCode)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startTime", exportChildhelp.getStartTime());
			map.put("endTime", exportChildhelp.getEndTime());

			// 转换疫苗名称
			List<String> list = StringUtils.splitToList(vaccCode, ",");
			map.put("list", list);
			map.put("localCode", localCode);

			// 统计数据
			List<HashMap<String, String>> yi_gan_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> chu_xue_re_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> jia_gan_jian_du_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> kuang_quan_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> feng_zhen_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> chang_dao_71_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> yi_nao_mie_huo_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> liu_gan_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> shang_han_Maps = new LinkedList<HashMap<String, String>>();
			List<HashMap<String, String>> maps = typeTwoVaccReport6_2(map);

			for (int i = 0; i < maps.size(); i++) {
				// 处理:乙肝,出血热,甲肝减毒,狂犬,71型肠道病毒,乙脑灭活,流感,伤寒 的各个疫苗小类
				HashMap<String, String> tmpMap = maps.get(i);
				if (tmpMap.get("id").equals("0201") || tmpMap.get("id").equals("0202")
						|| tmpMap.get("id").equals("0203")) {
					// 乙肝总和统计
					yi_gan_Maps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("2901") || tmpMap.get("id").equals("2902")
						|| tmpMap.get("id").equals("2903") || tmpMap.get("id").equals("2904")
						|| tmpMap.get("id").equals("2905") || tmpMap.get("id").equals("2906")) {
					// 出血热总和统计
					chu_xue_re_Maps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("1901") || tmpMap.get("id").equals("1902")) {
					// 甲肝减毒总和统计
					jia_gan_jian_du_Maps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("2801") || tmpMap.get("id").equals("2802")
						|| tmpMap.get("id").equals("2803") || tmpMap.get("id").equals("2804")
						|| tmpMap.get("id").equals("2805") || tmpMap.get("id").equals("4301")
						|| tmpMap.get("id").equals("4401") || tmpMap.get("id").equals("4402")) {
					// 狂犬总和统计
					kuang_quan_Maps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("1101") || tmpMap.get("id").equals("1102")) {
					// 风疹总和统计
					feng_zhen_Maps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("5401") || tmpMap.get("id").equals("5402")) {
					// 71型肠道病毒总和统计
					chang_dao_71_Maps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("1802") || tmpMap.get("id").equals("1803")) {
					// 乙脑灭活总和统计
					yi_nao_mie_huo_Maps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("2101") || tmpMap.get("id").equals("2102")
						|| tmpMap.get("id").equals("2103") || tmpMap.get("id").equals("2104")
						|| tmpMap.get("id").equals("2105") || tmpMap.get("id").equals("2106")
						) {
					// 流感总和统计
					liu_gan_Maps.add(tmpMap);
				}
				if (tmpMap.get("id").equals("3001") || tmpMap.get("id").equals("3002")
						|| tmpMap.get("id").equals("3101") || tmpMap.get("id").equals("3201")) {
					// 伤寒总和统计
					shang_han_Maps.add(tmpMap);
				}
				
			}

			// 删除maps中 乙肝,出血热,甲肝减毒,狂犬,71型肠道病毒,乙脑灭活,流感,伤寒的小项
			List<HashMap<String, String>> delList = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < maps.size(); i++) {
				HashMap<String, String> delMap = maps.get(i);
				if (delMap.get("id").equals("0202") || delMap.get("id").equals("0203")
						//乙肝结束
						
						|| delMap.get("id").equals("2902")
						|| delMap.get("id").equals("2903") || delMap.get("id").equals("2904")
						|| delMap.get("id").equals("2905") || delMap.get("id").equals("2906")
						//出血热结束
						
						|| delMap.get("id").equals("4301") || delMap.get("id").equals("4401")
						|| delMap.get("id").equals("4402")
						|| delMap.get("id").equals("2802") || delMap.get("id").equals("2803")
						|| delMap.get("id").equals("2804") || delMap.get("id").equals("2805")
						//狂犬结束
						
						|| delMap.get("id").equals("5402")
						//71型肠道病毒结束
						
						|| delMap.get("id").equals("1803")
						//乙脑灭活结束
						
						|| delMap.get("id").equals("1102")
						//风疹灭活结束
						
						|| delMap.get("id").equals("3002")
						|| delMap.get("id").equals("3101") || delMap.get("id").equals("3201")
						//伤寒结束
						
						|| delMap.get("id").equals("1902")
						//甲肝减毒结束
						
						|| delMap.get("id").equals("2102")
						|| delMap.get("id").equals("2103") || delMap.get("id").equals("2104")
						|| delMap.get("id").equals("2105") || delMap.get("id").equals("2106")
						//流感结束
						) {
					delList.add(delMap);
				}
			}
			for (HashMap<String, String> i : delList) {
				maps.remove(i);
			}

			// 暂存num和Map的变量
			int sum = 0;
			HashMap<String, String> innerMap = null;

			// 乙肝总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < yi_gan_Maps.size(); i++) {
				HashMap<String, String> tmpMap = yi_gan_Maps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "0200");
			innerMap.put("name", "乙肝(HepB)");
			innerMap.put("num", sum + "");
			yi_gan_Maps.clear();
			yi_gan_Maps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 出血热总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < chu_xue_re_Maps.size(); i++) {
				HashMap<String, String> tmpMap = chu_xue_re_Maps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "2900");
			innerMap.put("name", "出血热(HF)");
			innerMap.put("num", sum + "");
			chu_xue_re_Maps.clear();
			chu_xue_re_Maps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 甲肝减毒总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < jia_gan_jian_du_Maps.size(); i++) {
				HashMap<String, String> tmpMap = jia_gan_jian_du_Maps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "1900");
			innerMap.put("name", "甲肝减毒(HepA-L)");
			innerMap.put("num", sum + "");
			jia_gan_jian_du_Maps.clear();
			jia_gan_jian_du_Maps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 狂犬总和计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < kuang_quan_Maps.size(); i++) {
				HashMap<String, String> tmpMap = kuang_quan_Maps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "2800");
			innerMap.put("name", "狂犬病(Rab)");
			innerMap.put("num", sum + "");
			kuang_quan_Maps.clear();
			kuang_quan_Maps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 风疹总数计算和结果拼接 2017版本接种证上删除风疹,此处不再纳入计算
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < feng_zhen_Maps.size(); i++) {
				HashMap<String, String> tmpMap = feng_zhen_Maps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "2100");
			innerMap.put("name", "风疹(Flu)");
			innerMap.put("num", sum + "");
			feng_zhen_Maps.clear();
			feng_zhen_Maps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 肠道71型病毒总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < chang_dao_71_Maps.size(); i++) {
				HashMap<String, String> tmpMap = chang_dao_71_Maps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "2100");
			innerMap.put("name", "肠道病毒71型疫苗(灭活)(EV71V-i)");
			innerMap.put("num", sum + "");
			chang_dao_71_Maps.clear();
			chang_dao_71_Maps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 乙脑灭活总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < yi_nao_mie_huo_Maps.size(); i++) {
				HashMap<String, String> tmpMap = yi_nao_mie_huo_Maps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "1800");
			innerMap.put("name", "乙脑(灭活)(JE-i)");
			innerMap.put("num", sum + "");
			yi_nao_mie_huo_Maps.clear();
			yi_nao_mie_huo_Maps.add(innerMap);
			sum = 0;
			innerMap = null;

			// 流感总数计算和结果拼接
			innerMap = new HashMap<String, String>();
			for (int i = 0; i < liu_gan_Maps.size(); i++) {
				HashMap<String, String> tmpMap = liu_gan_Maps.get(i);
				sum += Integer.parseInt(String.valueOf((tmpMap.get("num"))));
			}
			innerMap.put("id", "2100");
			innerMap.put("name", "流感(Flu)");
			innerMap.put("num", sum + "");
			liu_gan_Maps.clear();
			liu_gan_Maps.add(innerMap);
			sum = 0;
			innerMap = null;

			String yi_gan_Str = JsonMapper.toJsonString(yi_gan_Maps);
			String chu_xue_re_Str = JsonMapper.toJsonString(chu_xue_re_Maps);
			String jia_gan_jian_du_Str = JsonMapper.toJsonString(jia_gan_jian_du_Maps);
			String kuang_quan_Str = JsonMapper.toJsonString(kuang_quan_Maps);
//			String feng_zhen_Str = JsonMapper.toJsonString(feng_zhen_Maps);
			String chang_dao_71_Str = JsonMapper.toJsonString(chang_dao_71_Maps);
			String yi_nao_mie_huo_Str = JsonMapper.toJsonString(yi_nao_mie_huo_Maps);
			String liu_gan_Str = JsonMapper.toJsonString(liu_gan_Maps);
			String shang_han_Str = JsonMapper.toJsonString(shang_han_Maps);
			String maps_Str = JsonMapper.toJsonString(maps);
			String result = maps_Str.replace("]", "") + "," 
					+ yi_gan_Str.replace("[", "").replace("]", "") + ","
					+ chu_xue_re_Str.replace("[", "").replace("]", "") + ","
					+ jia_gan_jian_du_Str.replace("[", "").replace("]", "") + ","
					+ kuang_quan_Str.replace("[", "").replace("]", "") + ","
//					+ feng_zhen_Str.replace("[", "").replace("]", "") + ","
					+ chang_dao_71_Str.replace("[", "").replace("]", "") + ","
					+ yi_nao_mie_huo_Str.replace("[", "").replace("]", "") + "," 
					+ liu_gan_Str.replace("[", "").replace("]", "") + "," 
					+ shang_han_Str.replace("[", "");
			model.addAttribute("maps", result);

			model.addAttribute("maps", JsonMapper.toJsonString(maps));
		} else {
			// TODO:可以优化提示信息,提示用户未选择疫苗种类
		}
		
		model.addAttribute("vaccCode", vaccCode);
		model.addAttribute("vaccname", vaccname);
		model.addAttribute("off", OfficeService.getFirstOffice().getName());
		model.addAttribute("date", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
	}

	// ------------------------------------------常规免疫接种情况6-2汇总表结束-----------------------------------------------------------------//
	// 需要注意的是：月份是从0开始的，比如说如果输入5的话，实际上显示的是4月份的最后一天，千万不要搞错了哦
	public static String getLastDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}

	public static String getFirstDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}

	/**
	 * 6-2二类报表数据
	 * 
	 * @author Jack
	 * @date 2017年10月23日 下午6:39:33
	 * @description
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> typeTwoVaccReport6_2(HashMap<String, Object> map) {
		return dao.typeTwoVaccReport6_2(map);
	}

	/**
	 * 常规免疫接种情况6-2汇总表 数据处理
	 * 
	 * @author Jack
	 * @date 2017年10月24日 上午9:24:46
	 * @description
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> selectVaccData6_2(HashMap<String, Object> map) {
		return dao.selectVaccData6_2(map);
	}
}
