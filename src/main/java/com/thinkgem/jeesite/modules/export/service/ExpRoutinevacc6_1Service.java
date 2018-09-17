package com.thinkgem.jeesite.modules.export.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.export.dao.ExpRoutinevacc6_1Dao;
import com.thinkgem.jeesite.modules.export.entity.ExpRoutinevacc61Detail;
import com.thinkgem.jeesite.modules.export.entity.ExpRoutinevacc6_1;

/**
 * 常规报表6-1 Service
 * 
 * @author Jack
 * @date 2017年10月13日 下午2:35:56
 */
@Service
public class ExpRoutinevacc6_1Service extends CrudService<ExpRoutinevacc6_1Dao, ExpRoutinevacc6_1> {
	@Autowired
	ExpRoutinevacc6_1Dao dao;
	
	@Autowired
	ExpRoutinevacc61DetailService expRoutinevacc61DetailService;
	
	List<ExpRoutinevacc6_1> ff = null;
	public HashMap<String, String> Vacc_in_Map = new HashMap<String, String>(); // 是否是需要进行统计疫苗信息 Map 根据模型ID
	
	// 应种本地
	public Map<String, Object> Y_BD_BCG_Map = new HashMap<String, Object>(); // 本地卡介应种

	public HashMap<Object, Object> Y_BD_JSS_Map = new HashMap<Object, Object>(); // 首针乙肝1针及时数
	public HashMap<String, String> Y_BD_HepB1_Map = new HashMap<String, String>(); // 本地乙肝1针应种
	public HashMap<String, String> Y_BD_HepB2_Map = new HashMap<String, String>(); // 本地乙肝2针应种
	public HashMap<String, String> Y_BD_HepB3_Map = new HashMap<String, String>(); // 本地乙肝3针应种

	public HashMap<String, String> Y_BD_PV1_Map = new HashMap<String, String>(); // 本 地脊灰1 应种2月
	public HashMap<String, String> Y_BD_PV2_Map = new HashMap<String, String>(); // 本 地脊灰2 应种3月
	public HashMap<String, String> Y_BD_PV3_Map = new HashMap<String, String>(); // 本 地脊灰3 应种4月
	public HashMap<String, String> Y_BD_PV4_Map = new HashMap<String, String>(); // 本 地脊灰4 应种4周

	public HashMap<String, String> Y_BD_DTP1_Map = new HashMap<String, String>(); // 本 地百白破1 应种3月
	public HashMap<String, String> Y_BD_DTP2_Map = new HashMap<String, String>(); // 本 地百白破2 应种4月
	public HashMap<String, String> Y_BD_DTP3_Map = new HashMap<String, String>(); // 本 地百白破3 应种5月
	public HashMap<String, String> Y_BD_DTP4_Map = new HashMap<String, String>(); // 本 地百白破4 应种18-24月

	public HashMap<String, Object> Y_BD_DT1_Map = new HashMap<String, Object>(); // 本 地白破1 应种 6周

	public HashMap<String, Object> Y_BD_MR1_Map = new HashMap<String, Object>(); // 本 地麻风1 应种 8月龄

	public HashMap<String, Object> Y_BD_MMR1_Map = new HashMap<String, Object>(); // 本 地麻腮风1 应种 18月龄

	public HashMap<String, String> Y_BD_JE_L1_Map = new HashMap<String, String>(); // 本 地乙脑减毒1 应种 8月龄
	public HashMap<String, String> Y_BD_JE_L2_Map = new HashMap<String, String>(); // 本 地乙脑减毒2 应种 24月龄

	public HashMap<String, String> Y_BD_JE_I1_Map = new HashMap<String, String>(); // 本 地乙脑灭活1 应种 8月龄
	public HashMap<String, String> Y_BD_JE_I2_Map = new HashMap<String, String>(); // 本 地乙脑灭活2 应种 8月龄
	public HashMap<String, String> Y_BD_JE_I3_Map = new HashMap<String, String>(); // 本 地乙脑灭活3 应种 24月龄
	public HashMap<String, String> Y_BD_JE_I4_Map = new HashMap<String, String>(); // 本 地乙脑灭活4 应种 72月龄

	public HashMap<String, String> Y_BD_MPSV_A1_Map = new HashMap<String, String>(); // 本 地A群流脑多糖 1 应种 6月
	public HashMap<String, String> Y_BD_MPSV_A2_Map = new HashMap<String, String>(); // 本 地A群流脑多糖 2 应种 9月

	public HashMap<String, String> Y_BD_MenA1_Map = new HashMap<String, String>(); // 本 地A+C群流脑结合1 应种 6月 模型ID=871
	public HashMap<String, String> Y_BD_MenA2_Map = new HashMap<String, String>(); // 本 地A+C群流脑结合 2 应种 7月 模型ID=872

	public HashMap<String, String> Y_BD_MenAC1_Map = new HashMap<String, String>(); // 本 地A+C群流脑多糖1 应种36月
	public HashMap<String, String> Y_BD_MenAC2_Map = new HashMap<String, String>(); // 本 地A+C群流脑多糖2 应种 72月

	public HashMap<String, Object> Y_BD_HepA_L1_Map = new HashMap<String, Object>(); // 本地甲肝减毒1针应种

	public HashMap<String, String> Y_BD_HepA_I1_Map = new HashMap<String, String>(); // 本地甲肝灭活1针应种
	public HashMap<String, String> Y_BD_HepA_I2_Map = new HashMap<String, String>(); // 本地甲肝灭活2针应种

	// 实种本地
	public HashMap<String, String> S_BD_BCG_Map = new HashMap<String, String>(); // 本地卡介实种

	public HashMap<String, String> S_BD_HepB1_Map = new HashMap<String, String>(); // 本地乙肝1针实种

	public HashMap<String, String> S_BD_HepB2_Map = new HashMap<String, String>(); // 本地乙肝2针实种

	public HashMap<String, String> S_BD_HepB3_Map = new HashMap<String, String>(); // 本地乙肝3针实种

	public HashMap<String, String> S_BD_PV1_Map = new HashMap<String, String>(); // 本 地脊灰1 实种2月
	public HashMap<String, String> S_BD_PV2_Map = new HashMap<String, String>(); // 本 地脊灰2 实种3月
	public HashMap<String, String> S_BD_PV3_Map = new HashMap<String, String>(); // 本 地脊灰3 实种4月
	public HashMap<String, String> S_BD_PV4_Map = new HashMap<String, String>(); // 本 地脊灰4 实种4周

	public HashMap<String, String> S_BD_DTP1_Map = new HashMap<String, String>(); // 本 地百白破1 实种3月
	public HashMap<String, String> S_BD_DTP2_Map = new HashMap<String, String>(); // 本 地百白破2 实种4月
	public HashMap<String, String> S_BD_DTP3_Map = new HashMap<String, String>(); // 本 地百白破3 实种5月
	public HashMap<String, String> S_BD_DTP4_Map = new HashMap<String, String>(); // 本 地百白破4 实种18-24月

	public HashMap<String, String> S_BD_DT1_Map = new HashMap<String, String>(); // 本 地白破1 实种 6周

	public HashMap<String, String> S_BD_MR1_Map = new HashMap<String, String>(); // 本 地麻风1 实种 8月龄

	public HashMap<String, String> S_BD_MMR1_Map = new HashMap<String, String>(); // 本 地麻腮风1 实种 18月龄

	public HashMap<String, String> S_BD_JE_L1_Map = new HashMap<String, String>(); // 本 地乙脑减毒1 实种 8月龄
	public HashMap<String, String> S_BD_JE_L2_Map = new HashMap<String, String>(); // 本 地乙脑减毒2 实种 24月龄

	public HashMap<String, String> S_BD_JE_I1_Map = new HashMap<String, String>(); // 本 地乙脑灭活1 实种 8月龄
	public HashMap<String, String> S_BD_JE_I2_Map = new HashMap<String, String>(); // 本 地乙脑灭活2 实种 8月龄
	public HashMap<String, String> S_BD_JE_I3_Map = new HashMap<String, String>(); // 本 地乙脑灭活3 实种 24月龄
	public HashMap<String, String> S_BD_JE_I4_Map = new HashMap<String, String>(); // 本 地乙脑灭活4 实种 72月龄

	public HashMap<String, String> S_BD_MPSV_A1_Map = new HashMap<String, String>(); // 本 地A群流脑多糖 1 实种 6月
	public HashMap<String, String> S_BD_MPSV_A2_Map = new HashMap<String, String>(); // 本 地A群流脑多糖 2 实种 9月

	public HashMap<String, String> S_BD_MenA1_Map = new HashMap<String, String>(); // 本 地A+C群流脑结合1 实种 6月 模型ID=871
	public HashMap<String, String> S_BD_MenA2_Map = new HashMap<String, String>(); // 本 地A+C群流脑结合 2 实种 7月 模型ID=872

	public HashMap<String, String> S_BD_MenAC1_Map = new HashMap<String, String>(); // 本 地A+C群流脑多糖1 实种36月
	public HashMap<String, String> S_BD_MenAC2_Map = new HashMap<String, String>(); // 本 地A+C群流脑多糖2 实种 72月

	public HashMap<String, String> S_BD_HepA_L1_Map = new HashMap<String, String>(); // 本地甲肝减毒1针实种

	public HashMap<String, String> S_BD_HepA_I1_Map = new HashMap<String, String>(); // 本地甲肝灭活1针实种
	public HashMap<String, String> S_BD_HepA_I2_Map = new HashMap<String, String>(); // 本地甲肝灭活2针实种
	
	private String createDate =null;
	private Integer monthNum = null;

	public ExpRoutinevacc6_1Service() {
		
	}

	/**
	 * 根据传入6位数年月生成EXP6-1数据
	 * @author Jack
	 * @date 2017年10月13日 上午9:33:43
	 */
	public void getDataByMonth(String yearStr, String monthStr, String localCode) {
		
		//判断给定的localCode下的指定月份数据是否已经生成,若已经生成则不再生成计算该条件下的数据
		int dataNum = countDataByMonthAndLocalCode(yearStr+"-"+monthStr, localCode);
		if(dataNum > 0){
			return;
		}

		Vacc_in_Map.put("16", "流脑A"); // 流脑A模型Map
		Vacc_in_Map.put("87", "A群C群流脑结合"); // A群C群流脑结合模型Map
		Vacc_in_Map.put("01", "卡介"); // 卡介疫苗模型Map
		Vacc_in_Map.put("02", "乙肝"); // 乙肝疫苗模型Map
		Vacc_in_Map.put("03", "脊灰"); // 脊灰疫苗模型Map
		Vacc_in_Map.put("04", "百白破疫苗"); // 百白破疫苗模型Map
		Vacc_in_Map.put("06", "白破疫苗"); // 白破疫苗模型Map
		Vacc_in_Map.put("14", "麻风疫苗"); // 麻风疫苗模型Map
		Vacc_in_Map.put("12", "麻腮风疫苗"); // 麻腮风疫苗模型Map
		Vacc_in_Map.put("84", "乙脑减毒活疫苗"); // 乙脑减毒活疫苗模型Map
		Vacc_in_Map.put("87", "A+C群流脑结合疫苗"); // A+C群流脑结合疫苗模型Map
		Vacc_in_Map.put("17", "A+C群流脑多糖疫苗"); // A+C群流脑多糖疫苗模型Map
		Vacc_in_Map.put("82", "甲肝减毒活疫苗"); // 甲肝减毒活疫苗模型Map
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		
		if(StringUtils.isNotBlank(yearStr) && StringUtils.isNotBlank(monthStr)){
			Calendar c = Calendar.getInstance();
			Integer nowYear = c.get(Calendar.YEAR);
			Integer nowMonth = c.get(Calendar.MONTH) + 1;
			Integer year = Integer.valueOf(yearStr);
			Integer month = Integer.valueOf(monthStr);
			if(nowYear.equals(year)){
				//获取数据在当年,只有月份差异
				monthNum = -(nowMonth - month);
				
				//初始化当前时间的上个月份数据
				c.setTime(new Date());
				c.add(Calendar.MONTH, monthNum);
				Date dd = c.getTime();
				createDate = sf.format(dd);
			}else{
				if(nowYear > year){
					//获取数据不在当年,需要按照年计算中间的间隔月
					monthNum = -((nowYear - year)*12 + nowMonth - month);
				}else{
					monthNum = (nowYear - year)*12 + nowMonth - month;
				}
				
				//初始化指定月份的数据
				c.setTime(new Date());
				c.add(Calendar.MONTH, monthNum);
				Date dd = c.getTime();
				createDate = sf.format(dd);
			}
			
		}else{
			//设置数据初始化月份为上个月
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
	        c.add(Calendar.MONTH, -1);
	        Date dd = c.getTime();
	        String createDate = sf.format(dd);
		}
		
		//设置officeStr 为本地站点编码
//		String officeStr = OfficeService.getFirstOfficeCode();
		String officeStr = localCode;
		
		List<ExpRoutinevacc6_1> list_reside = selectResideList(); //1本地    2流动   3临时
		
		List<ExpRoutinevacc6_1> list_community = selectCommunityList(officeStr); //社区
		
		int residesize=list_reside.size();
		int list_communitysize=list_community.size();
		ff = new ArrayList<ExpRoutinevacc6_1>();
		for(int i=0;i<residesize;i++){
			//按居住属性遍历
			for (int j=0;j<list_communitysize;j++){
				//按社区遍历
				ExpRoutinevacc6_1 bb6_1=new ExpRoutinevacc6_1();
				bb6_1.setId(IdGen.uuid());
 				Map<?, ?> mp_reside = (Map<?, ?>) list_reside.get(i);
				String value = (String) mp_reside.get("VALUE"); //获取居住属性
				bb6_1.setReside(value);
				Map<?, ?> mp_community = (Map<?, ?>) list_community.get(j);
				String code = (String) mp_community.get("CODE"); //获取社区code
				bb6_1.setUnitCode(code);
				String name = (String) mp_community.get("NAME"); //获取社区名称
				bb6_1.setUnitName(name);
				String sysCommunityLocalCode = (String) mp_community.get("LOCALCODE"); 
				bb6_1.setLocalCode(sysCommunityLocalCode);
				bb6_1.setYearMonth(createDate);
				ff.add(bb6_1);
			}
		}
		
		List<Map<String, String>> list_rel = new ArrayList<Map<String, String>>();
		list_rel = selectSqlQuery(monthNum + 1, officeStr); //根据设定月份中最后一秒时间节点计算儿童信息
		StringBuffer sbf = new StringBuffer();
		
		for(int i = 0; i < list_rel.size(); i++){
			Map mp_rel = (Map) list_rel.get(i);
			String id = (String) mp_rel.get("ID");
			String childcode= (String)mp_rel.get("CHILDCODE");
			String mon_age = (String) mp_rel.get("MON_AGE");
			double d_mon_age = Double.parseDouble(mon_age); //月龄
			Date birthday = (Date) mp_rel.get("AAC");  //出生日期(XXXX-XX-XX)
			Date HepB_1_day=null;
			//String pr = (String) mp_rel.get("PR");//省
			//String ci = (String) mp_rel.get("CI");//市
			String co = (String) mp_rel.get("CO");//县
			String reside=(String) mp_rel.get("RESIDE");//居住属性1本地;2流动;3临时 
			String area=(String) mp_rel.get("AREA");//区域划分
			//System.out.println(mp_rel.get("vage_num"));
			String situation = (String)mp_rel.get("SITUATION");
			String officeInfo = (String) mp_rel.get("OFFICEINFO");
			String create_age = (String) mp_rel.get("CREATE_AGE"); //出生日期与本月间隔

			Map<String, Map<String,String>> vacc_list = new HashMap<String, Map<String, String>>();
			List<Map<String, String>> list_vaccre = new ArrayList<Map<String, String>>();
			list_vaccre = selectSqlList(id,monthNum+1); //根据设定时间,统计所有该儿童已接种的疫苗
			for (int j = 0; j < list_vaccre.size(); j++) {

				Map m_vr = (Map) list_vaccre.get(j);

				String vacc_pin = (String) m_vr.get("VACC_PIN");// 大类+针次
				if(vacc_pin.equals("021")){
					HepB_1_day=(Date) m_vr.get("BBC"); //接种日期
				}
				String office = (String)m_vr.get("OFFICE");
				String source = (String)m_vr.get("SOURCE");
				String vaccineid = (String) m_vr.get("VACCINEID"); 
				String vacc_name = (String) m_vr.get("VACC_NAME"); 
				String dif_mon = (String) m_vr.get("DIF_MON"); //接种间隔
				Map<String, String> t_map = new HashMap<String, String>();
				t_map.put("office", office);
				t_map.put("dif_mon", dif_mon);
				t_map.put("source",source);
			
				vacc_list.put(vacc_pin, t_map);
			}
			
			//卡介特殊处理开始
			Map<String, Map<String, String>> vacc_list_special = new HashMap<String,  Map<String, String>>();
			List<Map<String, String>> list_vaccre_special = new ArrayList<Map<String, String>>();
			list_vaccre_special = selectSqlList(id,monthNum+1); //卡介乙肝处理：将补录记录统计进来
			for (int j = 0; j < list_vaccre_special.size(); j++) {

				Map m_vr_special = (Map) list_vaccre_special.get(j);

				String vacc_pin_special = (String) m_vr_special.get("VACC_PIN");// 大类+针次
				if(vacc_pin_special.equals("021")){
					HepB_1_day=(Date) m_vr_special.get("BBC"); //接种日期
				}
				String office = (String)m_vr_special.get("OFFICE");
				String source = (String)m_vr_special.get("SOURCE");
				String vaccineid_special = (String) m_vr_special.get("VACCINEID"); 
				String vacc_name_special = (String) m_vr_special.get("VACC_NAME"); 
				String dif_mon_special = (String) m_vr_special.get("DIF_MON"); 
				Map<String, String> t_map_2 = new HashMap<String, String>();
				t_map_2.put("office", office);
				t_map_2.put("dif_mon", dif_mon_special);
				t_map_2.put("source",source);
				vacc_list_special.put(vacc_pin_special, t_map_2);
			}
			//卡介特殊处理结束
			
			//应种<13月
			List<Map<String, String>> list_yzrq = new ArrayList<Map<String, String>>();
			Map<String, String> vacc_yzrq = new HashMap<String, String>();
			list_yzrq = selectSqlYzrq(id, monthNum+1);
			
			for(int j = 0; j < list_yzrq.size(); j++){
				Map m_vr = (Map) list_yzrq.get(j);
				String vacc_pin = (String) m_vr.get("ID");// 大类+针次
				String childcode1 = (String) m_vr.get("CHILDCODE");
				vacc_yzrq.put(vacc_pin, childcode1);
			}
			
			//超过7岁龄儿童数据处理开始
			//白破
			/*for (int k = 0; k < ff.size(); k++) {
				if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
					//统计白破疫苗接种时间在生成数据的月份内且当月月龄超过84岁的情况,加入应种实种
					Integer num = get_yuefendayu84_Num("061" , monthNum + 1, reside, area);
					int a = ff.get(k).getDtRe();
					ff.get(k).setDtRe(a+num);
					a = ff.get(k).getDtSh();
					ff.get(k).setDtSh(a+num);
					break;
				}
			}*/
			//超过7岁龄儿童数据处理结束
			
			if(d_mon_age>72){
				//6岁至7岁龄
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_4(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破4
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_4(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合2
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖2
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻风1
				if(Vacc_in_Map.containsKey("14")){
					do_MR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑减毒2
				if(Vacc_in_Map.containsKey("84")){
					do_JEL_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑灭活 4
				if(Vacc_in_Map.containsKey("83")){
					do_JEI_4(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风1
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风2
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
			
				//甲肝减毒
				if(Vacc_in_Map.containsKey("82")){
					do_HEPAL_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//甲肝灭活
				if(Vacc_in_Map.containsKey("81")){
					do_HEPAI_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
					
				}
				
				//A+C群多糖第二针
				if(Vacc_in_Map.containsKey("17")){
					do_MENAC_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//白破1
				if(Vacc_in_Map.containsKey("06")){
					do_DT_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>48 && d_mon_age<=72){
				//48-72月

				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰4
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_4(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破4
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_4(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合2
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖2
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻风1
				if(Vacc_in_Map.containsKey("14")){
					do_MR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑减毒2
				if(Vacc_in_Map.containsKey("84")){
					do_JEL_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑灭活 3
				if(Vacc_in_Map.containsKey("83")){
					do_JEI_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风1
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风2
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
			
				//甲肝减毒
				if(Vacc_in_Map.containsKey("82")){
					do_HEPAL_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				//甲肝灭活
				if(Vacc_in_Map.containsKey("81")){
					do_HEPAI_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群多糖第一针
				if(Vacc_in_Map.containsKey("17")){
					do_MENAC_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>36 && d_mon_age<=48){
				//36----48
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
					do_PV_4_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破4
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_4(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合2
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖2
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻风1
				if(Vacc_in_Map.containsKey("14")){
					do_MR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				
				//乙脑减毒2
				if(Vacc_in_Map.containsKey("84")){
					do_JEL_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑灭活 3
				if(Vacc_in_Map.containsKey("83")){
					do_JEI_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风1
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风2
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
			
				//甲肝减毒
				if(Vacc_in_Map.containsKey("82")){
					do_HEPAL_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//甲肝灭活
				if(Vacc_in_Map.containsKey("81")){
					do_HEPAI_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
					
				}
				//A+C群多糖第一针
				
				if(Vacc_in_Map.containsKey("17")){
					do_MENAC_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>24 && d_mon_age<=36){
				//24-36
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
					do_PV_4_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破4
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_4(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合2
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖2
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻风1
				if(Vacc_in_Map.containsKey("14")){
					do_MR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑减毒2
				if(Vacc_in_Map.containsKey("84")){
					do_JEL_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑灭活 3
				if(Vacc_in_Map.containsKey("83")){
					do_JEI_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风1
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风2
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
			
				//甲肝减毒
				if(Vacc_in_Map.containsKey("82")){
					do_HEPAL_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
					
				}
				//甲肝灭活
				if(Vacc_in_Map.containsKey("81")){
					do_HEPAI_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
					
				}
				
			}else if(d_mon_age>18 && d_mon_age<=24){
				//18月---24月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
					do_PV_4_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破4
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_4(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合2
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖1
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻风1
				if(Vacc_in_Map.containsKey("14")){
					do_MR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑减毒1
				if(Vacc_in_Map.containsKey("84")){
					do_JEL_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑灭活 1-2
				if(Vacc_in_Map.containsKey("83")){
					do_JEI_1_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风1
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻腮风2
				if(Vacc_in_Map.containsKey("12")){
					do_MMR_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
			
				//甲肝减毒
				if(Vacc_in_Map.containsKey("82")){
					do_HEPAL_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				//甲肝灭活
				if(Vacc_in_Map.containsKey("81")){
					do_HEPAI_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
					
				}
				
			}else if(d_mon_age>9 && d_mon_age<=18){			
		
				//8月-----9月  //国家版本的接种A群流脑多糖 第二针
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破3
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合2
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖2
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻风1
				if(Vacc_in_Map.containsKey("14")){
					do_MR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				
				//乙脑减毒1
				if(Vacc_in_Map.containsKey("84")){
					do_JEL_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑灭活 1-2
				if(Vacc_in_Map.containsKey("83")){
					do_JEI_1_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>8 && d_mon_age<=9){
				//8月-----9月 
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破3
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合2
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖1
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//麻风1
				if(Vacc_in_Map.containsKey("14")){
					do_MR_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑减毒1
				if(Vacc_in_Map.containsKey("84")){
					do_JEL_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//乙脑灭活 1-2
				if(Vacc_in_Map.containsKey("83")){
					do_JEI_1_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>7 && d_mon_age<=8){
				//7月-----8月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破3
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合2
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖1
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>6 && d_mon_age<=7){
				//6月------7月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝3
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_3(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破3
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合1
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A群流脑多糖1
				if(Vacc_in_Map.containsKey("16")){
					do_MPSVA_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				
				
			}else if(d_mon_age>5 && d_mon_age<=6){
				//5月  ----6 月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				
				//乙肝2
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_2(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破3
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合1 对AC-Hib拆解的疫苗做殊处理
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_1_Special(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>4 && d_mon_age<=5){
				//4月 ------ 5月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝2
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_2(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰3
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_3(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破2
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合1 对AC-Hib拆解的疫苗做殊处理
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_1_Special(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				
			}else if(d_mon_age>3 && d_mon_age<=4){
				//3月-------4月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝2
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_2(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰2
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 乙肝 疫苗
					do_PV_2(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//百白破1
				if(Vacc_in_Map.containsKey("04")){
					do_DTP_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合1 对AC-Hib拆解的疫苗做殊处理
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_1_Special(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>2 && d_mon_age<=3){
				//2月-----3月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝2
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_2(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
				
				//脊灰
				if(Vacc_in_Map.containsKey("03")){ //根据模型ID = 03 确定是否配置接种 脊灰 疫苗
					do_PV_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
				//A+C群流脑结合1 对AC-Hib拆解的疫苗做殊处理
				if(Vacc_in_Map.containsKey("87")){
					do_MENA_1_Special(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo);
				}
				
			}else if(d_mon_age>1 && d_mon_age<=2){
				//1月------2月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝2
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_2(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day, createDate, officeStr, situation ,officeInfo, create_age);
				}
			}else if(d_mon_age>0 && d_mon_age<=1){
				//0月 ------1月
				
				//卡介
				if(Vacc_in_Map.containsKey("01")){ //根据模型ID = 01 确定是否配置接种 卡介 疫苗
				   do_BCG_1(vacc_list_special, childcode, vacc_yzrq, reside, area, createDate, officeStr, situation ,officeInfo); //卡介			
				}
				//乙肝1
				if(Vacc_in_Map.containsKey("02")){ //根据模型ID = 02 确定是否配置接种 乙肝 疫苗
					do_HepB_1(vacc_list_special, childcode, vacc_yzrq, reside, area, birthday, HepB_1_day ,createDate, officeStr, situation, officeInfo);
				}
			}
			
		}
		
		DecimalFormat df = new DecimalFormat("#.0000");
		for(int k = 0; k < ff.size(); k++){
			
			//设置乙肝及时数为乙肝第一针数
			ff.get(k).setHepbAaSh(ff.get(k).getHepbASh());
			
//			根据实种、应种,计算接种率	
			if(ff.get(k).getHepbASh() != 0){
				ff.get(k).setHepbASr( Float.parseFloat(df.format( (float) ff.get(k).getHepbARe() / ff.get(k).getHepbASh()))*100);
			}
			
			if(ff.get(k).getHepbAaSh() != 0){
				ff.get(k).setHepbAaSr(Float.parseFloat(df.format((float) ff.get(k).getHepbAaRe() / ff.get(k).getHepbAaSh()))*100);
			}
			
			if(ff.get(k).getHepbBSh() != 0){
				ff.get(k).setHepbBSr(Float.parseFloat(df.format((float) ff.get(k).getHepbBRe() / ff.get(k).getHepbBSh()))*100);
			}
			
			if(ff.get(k).getHepbCSh() != 0){
				ff.get(k).setHepbCSr(Float.parseFloat(df.format((float) ff.get(k).getHepbCRe() / ff.get(k).getHepbCSh()))*100);
			}
			
			if(ff.get(k).getBcgSh() != 0){
				ff.get(k).setBcgSr(Float.parseFloat(df.format((float) ff.get(k).getBcgRe() / ff.get(k).getBcgSh()))*100);
			}
			
			if(ff.get(k).getPvASh() != 0){
				ff.get(k).setPvASr(Float.parseFloat(df.format((float) ff.get(k).getPvARe() / ff.get(k).getPvASh()))*100);
			}
			
			if(ff.get(k).getPvBSh() != 0){
				ff.get(k).setPvBSr(Float.parseFloat(df.format((float) ff.get(k).getPvBRe() / ff.get(k).getPvBSh()))*100);
			}
			
			if(ff.get(k).getPvBSh() != 0){
				ff.get(k).setPvBSr(Float.parseFloat(df.format((float) ff.get(k).getPvBRe() / ff.get(k).getPvBSh()))*100);
			}
			if(ff.get(k).getPvDSh() != 0){
				ff.get(k).setPvDSr(Float.parseFloat(df.format((float) ff.get(k).getPvDRe() / ff.get(k).getPvDSh()))*100);
			}
			if(ff.get(k).getDtpASh() != 0){
				ff.get(k).setDtpASr(Float.parseFloat(df.format((float) ff.get(k).getDtpARe() / ff.get(k).getDtpASh()))*100);
			}
			
			if(ff.get(k).getDtpBSh() != 0){
				ff.get(k).setDtpBSr(Float.parseFloat(df.format((float) ff.get(k).getDtpBRe() / ff.get(k).getDtpBSh()))*100);
			}
			
			if(ff.get(k).getDtpCSh() != 0){
				ff.get(k).setDtpCSr(Float.parseFloat(df.format((float) ff.get(k).getDtpCRe() / ff.get(k).getDtpCSh()))*100);
			}
			
			if(ff.get(k).getDtpDSh() != 0){
				ff.get(k).setDtpDSr(Float.parseFloat(df.format((float) ff.get(k).getDtpDRe() / ff.get(k).getDtpDSh()))*100);
			}
			
			if(ff.get(k).getDtSh() != 0){
				ff.get(k).setDtSr(Float.parseFloat(df.format((float) ff.get(k).getDtRe() / ff.get(k).getDtSh()))*100);
			}
			
			if(ff.get(k).getMrASh() != 0){
				ff.get(k).setMrASr(Float.parseFloat(df.format((float) ff.get(k).getMrARe() / ff.get(k).getMrASh()))*100);
			}
			
			if(ff.get(k).getMrBSh() != 0){
				ff.get(k).setMrBSr(Float.parseFloat(df.format((float) ff.get(k).getMrBRe() / ff.get(k).getMrBSh()))*100);
			}
			
			if(ff.get(k).getMmrASh() != 0){
				ff.get(k).setMmrASr(Float.parseFloat(df.format((float) ff.get(k).getMmrARe() / ff.get(k).getMmrASh()))*100);
			}
			
			if(ff.get(k).getMmrBSh() != 0){
				ff.get(k).setMmrBSr(Float.parseFloat(df.format((float) ff.get(k).getMmrBRe() / ff.get(k).getMmrBSh()))*100);
			}
			
			if(ff.get(k).getMmASh() != 0){
				ff.get(k).setMmASr(Float.parseFloat(df.format((float) ff.get(k).getMmARe() / ff.get(k).getMmASh()))*100);
			}
			
			if(ff.get(k).getMmBSh() != 0){
				ff.get(k).setMmBSr(Float.parseFloat(df.format((float) ff.get(k).getMmBRe() / ff.get(k).getMmBSh()))*100);
			}
			
			if(ff.get(k).getMvASh() != 0){
				ff.get(k).setMvASr(Float.parseFloat(df.format((float) ff.get(k).getMvARe() / ff.get(k).getMvASh()))*100);
			}
			
			if(ff.get(k).getMvBSh() != 0){
				ff.get(k).setMvBSr(Float.parseFloat(df.format((float) ff.get(k).getMvBRe() / ff.get(k).getMvBSh()))*100);
			}
			
			if(ff.get(k).getMenaASh() != 0){
				ff.get(k).setMenaASr(Float.parseFloat(df.format((float) ff.get(k).getMenaARe() / ff.get(k).getMenaASh()))*100);
			}
			
			if(ff.get(k).getMenaBSh() != 0){
				ff.get(k).setMenaBSr(Float.parseFloat(df.format((float) ff.get(k).getMenaBRe() / ff.get(k).getMenaBSh()))*100);
			}
			
			if(ff.get(k).getMenacASh() != 0){
				ff.get(k).setMenacASr(Float.parseFloat(df.format((float) ff.get(k).getMenacARe() / ff.get(k).getMenacASh()))*100);
			}
			
			if(ff.get(k).getMenacBSh() != 0){
				ff.get(k).setMenacBSr(Float.parseFloat(df.format((float) ff.get(k).getMenacBRe() / ff.get(k).getMenacBSh()))*100);
			}
			
			if(ff.get(k).getJelASh() != 0){
				ff.get(k).setJelASr(Float.parseFloat(df.format((float) ff.get(k).getJelARe() / ff.get(k).getJelASh()))*100);
			}
			
			if(ff.get(k).getJelBSh() != 0){
				ff.get(k).setJelBSr(Float.parseFloat(df.format((float) ff.get(k).getJelBRe() / ff.get(k).getJelBSh()))*100);
			}
			
			if(ff.get(k).getJeiASh() != 0){
				ff.get(k).setJeiASr(Float.parseFloat(df.format((float) ff.get(k).getJeiARe() / ff.get(k).getJeiASh()))*100);
			}
			
			if(ff.get(k).getJeiBSh() != 0){
				ff.get(k).setJeiBSr(Float.parseFloat(df.format((float) ff.get(k).getJeiBRe() / ff.get(k).getJeiBSh()))*100);
			}
			
			if(ff.get(k).getJeiCSh() != 0){
				ff.get(k).setJeiCSr(Float.parseFloat(df.format((float) ff.get(k).getJeiCRe() / ff.get(k).getJeiCSh()))*100);
			}
			
			if(ff.get(k).getJeiDSh() != 0){
				ff.get(k).setJeiDSr(Float.parseFloat(df.format((float) ff.get(k).getJeiDRe() / ff.get(k).getJeiDSh()))*100);
			}
			
			if(ff.get(k).getHepalSh() != 0){
				ff.get(k).setHepalSr(Float.parseFloat(df.format((float) ff.get(k).getHepalRe() / ff.get(k).getHepalSh()))*100);
			}
			
			if(ff.get(k).getHepaiASh() != 0){
				ff.get(k).setHepaiASr(Float.parseFloat(df.format((float) ff.get(k).getHepaiARe() / ff.get(k).getHepaiASh()))*100);
			}
			
			if(ff.get(k).getHepaiBSh() != 0){
				ff.get(k).setHepaiBSr(Float.parseFloat(df.format((float) ff.get(k).getHepaiBRe() / ff.get(k).getHepaiBSh()))*100);
			}
			
			//计算MCV,RCV,MumCV,HepA类型总和接种率
			ff.get(k).setMcvSumASh(ff.get(k).getMvASh() + ff.get(k).getMrASh() + ff.get(k).getMmASh() + ff.get(k).getMmrASh());
			ff.get(k).setMcvSumARe(ff.get(k).getMvARe() + ff.get(k).getMrARe() + ff.get(k).getMmARe() + ff.get(k).getMmrARe());
			ff.get(k).setMcvSumBSh(ff.get(k).getMvSumBSh() + ff.get(k).getMrSumBSh() + ff.get(k).getMmSumBSh() + ff.get(k).getMmrBSh());
			ff.get(k).setMcvSumBRe(ff.get(k).getMvBRe() + ff.get(k).getMrBRe() + ff.get(k).getMmBRe() + ff.get(k).getMmrBRe());		
			
			ff.get(k).setRcvSumASh(ff.get(k).getMrASh() + ff.get(k).getMmrASh());
			ff.get(k).setRcvSumARe(ff.get(k).getMrARe() + ff.get(k).getMmrARe());
			ff.get(k).setRcvSumBSh(ff.get(k).getMrSumBSh() + ff.get(k).getMmrBSh());
			ff.get(k).setRcvSumBRe(ff.get(k).getMrBRe() + ff.get(k).getMmrBRe());
			
			ff.get(k).setMumcvSumASh(ff.get(k).getMmASh() + ff.get(k).getMmrASh());
			ff.get(k).setMumcvSumARe(ff.get(k).getMmARe() + ff.get(k).getMmrARe());
			ff.get(k).setMumcvSumBSh(ff.get(k).getMmBSh() + ff.get(k).getMmrBSh());
			ff.get(k).setMumcvSumBRe(ff.get(k).getMmBRe() + ff.get(k).getMmrBRe());
			
			ff.get(k).setHepalSumASh(ff.get(k).getHepalSh());
			ff.get(k).setHepalSumARe(ff.get(k).getHepalRe());
			
			ff.get(k).setHepaiSumASh(ff.get(k).getHepaiASh());
			ff.get(k).setHepaiSumARe(ff.get(k).getHepaiARe());
			ff.get(k).setHepaiSumBSh(ff.get(k).getHepaiBSh());
			ff.get(k).setHepaiSumBRe(ff.get(k).getHepaiBRe());
			
			if(ff.get(k).getMcvSumASh() != 0){
				ff.get(k).setMcvSumASr(Float.parseFloat(df.format((float) ff.get(k).getMcvSumARe() / ff.get(k).getMcvSumASh()))*100);
			}
			if(ff.get(k).getMcvSumBSh() != 0){
				ff.get(k).setMcvSumBSr(Float.parseFloat(df.format((float) ff.get(k).getMcvSumBRe() / ff.get(k).getMcvSumBSh()))*100);
			}
			
			if(ff.get(k).getRcvSumASh() != 0){
				ff.get(k).setRcvSumASr(Float.parseFloat(df.format((float) ff.get(k).getRcvSumARe() / ff.get(k).getRcvSumASh()))*100);
			}
			if(ff.get(k).getRcvSumBSh() != 0){
				ff.get(k).setRcvSumBSr(Float.parseFloat(df.format((float) ff.get(k).getRcvSumBRe() / ff.get(k).getRcvSumBSh()))*100);
			}
			
			if(ff.get(k).getMumcvSumASh() != 0){
				ff.get(k).setMumcvSumASr(Float.parseFloat(df.format((float) ff.get(k).getMumcvSumARe() / ff.get(k).getMumcvSumASh()))*100);
			}
			if(ff.get(k).getMumcvSumBSh() != 0){
				ff.get(k).setMumcvSumBSr(Float.parseFloat(df.format((float) ff.get(k).getMumcvSumBRe() / ff.get(k).getMumcvSumBSh()))*100);
			}
			
			if(ff.get(k).getHepalSumASh() != 0 ){
				ff.get(k).setHepalSumASr(Float.parseFloat(df.format((float) ff.get(k).getHepalSumARe() / ff.get(k).getHepalSumASh()))*100);
			}
			
			if(ff.get(k).getHepaiSumASh() != 0){
				ff.get(k).setHepaiSumASr(Float.parseFloat(df.format((float) ff.get(k).getHepaiSumARe() / ff.get(k).getHepaiSumASh()))*100);
			}
			if(ff.get(k).getHepaiSumBSh() != 0){
				ff.get(k).setHepaiSumBSr(Float.parseFloat(df.format((float) ff.get(k).getHepaiSumBRe() / ff.get(k).getHepaiSumBSh()))*100);
			}
			
			ff.get(k).setIsNewRecord(true);
			save(ff.get(k));
		}
		
		ff = null;
		System.out.println(yearStr + "年" + monthStr + "月 站点编码为" +  localCode + "的数据生成完成");
		logger.info(yearStr + "年" + monthStr + "月 站点编码为" +  localCode + "的数据生成完成");
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 根据接种记录按月龄遍历全部数据
	 * @author Jack
	 * @date 2017年10月18日 下午8:05:12
	 * @description 
	 * @param vacc_list
	 * @param childcode 儿童编码
	 * @param vacc_yzrq <br> ID:疫苗大类+剂次<br> CHILDCODE:儿童编码
	 * @param reside 居住属性
	 * @param area 
	 * @param createDate 接种记录创建时间
	 * @param commName 社区名称
	 *
	 */
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * **乙肝1和卡介苗的sql计算公式：
	 *	*当月实际产生的记录：
	 *		--当月应种：当月新生儿数量------>应种+1
	 *		--当月实种：当月新生儿中接种了乙肝1(或卡介)的数量------>实种+1
	 *	*当月建卡产生的记录：
	 *		sql统计当月建卡中所有已种乙肝1(或卡介)苗且出生日期不在当月的记录数量------>应种实种都+1
	 * @author Jack
	 * @date 2018年1月26日 下午4:08:00
	 * @description 
	 */
//	public void do_BCG_1(String reside,String area) {
		
		//根据提供的月份间隔 计算当月的新生儿数(当月基本应种数)
//		Integer baseShNum =  getNewChildNumByMonth(monthNum, monthNum + 1);
		
		//当月新生儿中接种了乙肝1(或卡介)的数量(当月基本实种数)
//		Integer baseReNum = getReNumByVaccAndMonth("0101", monthNum, monthNum + 1);
		
		//sql统计当月建卡中所有已种乙肝1(或卡介)苗且出生日期不在当月的记录数量------>应种实种都+1
//		Integer jiankaNum = getJianKaNum("0101", monthNum, monthNum + 1);
		
		
//		for (int k = 0; k < ff.size(); k++) {
//			if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
//				
//				ff.get(k).setBcgSh(baseShNum + jiankaNum);
//				ff.get(k).setBcgRe(baseReNum + jiankaNum); 
//				break;
//			}
//		}
//		
//	}
	
	// 卡介1
	public void do_BCG_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo) {
		
		//////////// 0月龄开始 ///////////
		Map<String, String> t_map = (Map<String, String>) vacc_list.get("011");
		if (t_map == null) {
			if (vacc_yzrq.containsKey("011") && situation.equals("1") && officeInfo.equals(office)) {
				// 卡介苗
				Y_BD_BCG_Map.put(childcode, vacc_list.get("011"));
				// System.out.println("卡介应种");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("011");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getBcgSh();
						ff.get(k).setBcgSh(++a);
						break;
					}
				}
			}
		} else {
			if (Double.parseDouble((String) t_map.get("dif_mon")) < 1 ) {
				// 判断接种时间是否在当月
				Y_BD_BCG_Map.put(childcode, "011");
				S_BD_BCG_Map.put(childcode, "011");
				
				// System.out.println(">>>卡介疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("011");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getBcgRe();
						ff.get(k).setBcgRe(++a);
						a = ff.get(k).getBcgSh();
						ff.get(k).setBcgSh(++a);
						break;
					}
				}
			}
		}

	}

	// 乙肝第一针
	public void do_HepB_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area,
			Date birthday,Date HepB_1_day, String createDate, String office, String situation, String officeInfo) {
		
		Map<String, String> t_map = (Map<String, String>) vacc_list.get("021");

		// 判断乙肝第一针是否接种
		if (t_map != null) {
			// 第一针乙肝不为空
			if (Double.parseDouble((String) t_map.get("dif_mon")) < 1 ) {
				// 判断第一针接种时间是否在当月

				// 乙肝1针次是应种针次
				Y_BD_HepB1_Map.put(childcode, "021");
				S_BD_HepB1_Map.put(childcode, "021");
				// System.out.println(">>>乙肝疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("021");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepbARe();
						ff.get(k).setHepbARe(++a);
						a = ff.get(k).getHepbASh();
						ff.get(k).setHepbASh(++a);
						if ((int) ((HepB_1_day.getTime() - birthday.getTime()) / 1000 / 60 / 60 / 24) <= 2) {
							a = ff.get(k).getHepbAaRe();
							ff.get(k).setHepbAaRe(++a);
						}
						break;
					}
				}
			}
		} else {
			// 第一针乙肝为空
			// 乙肝第1针次是应种
			if (vacc_yzrq.containsKey("021") && situation.equals("1") && officeInfo.equals(office)) {
				Y_BD_HepB1_Map.put(childcode, "021");
				// System.out.println(">>>乙肝疫苗1应种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("021");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepbASh();
						ff.get(k).setHepbASh(++a);
						break;
					}
				}
			}

		}

	}

	// 乙肝第二针
	public void do_HepB_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, 
			Date birthday,Date HepB_1_day, String createDate, String office, String situation, String officeInfo, String create_age_t) {

		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("022");
		
		// 判断乙肝第二针是否有接种纪录
		if (t_map_2 != null) {
			// 乙肝第二针已经有接种纪录
			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1 
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断第二针接种时间是否在当月
				
				// 乙肝2针次是应种针次
				Y_BD_HepB2_Map.put(childcode, "022");
				S_BD_HepB2_Map.put(childcode, "022");
				
				// System.out.println(">>>乙肝疫苗2应种 实种>>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("022");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepbBRe();
						ff.get(k).setHepbBRe(++a);
						a = ff.get(k).getHepbBSh();
						ff.get(k).setHepbBSh(++a);
						break;
					}
				}
				
				//加入当月建卡的乙肝第一针数据统计
				if(Double.valueOf(create_age_t) < 1){
					//建卡月份在当月的记录
					// 乙肝1针次是应种针次
					Y_BD_HepB1_Map.put(childcode, "021");
					S_BD_HepB1_Map.put(childcode, "021");
					// System.out.println(">>>乙肝疫苗1应种实种 >>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("021");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getHepbARe();
							ff.get(k).setHepbARe(++a);
							a = ff.get(k).getHepbASh();
							ff.get(k).setHepbASh(++a);
							if ((int) ((HepB_1_day.getTime() - birthday.getTime()) / 1000 / 60 / 60 / 24) <= 2) {
								a = ff.get(k).getHepbAaRe();
								ff.get(k).setHepbAaRe(++a);
							}
							break;
						}
					}
				}
			}
			
		} else {
			Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("021");
			
			// 判断乙肝第一针是否接种
			if (t_map_1 != null) {
				// 第一针乙肝不为空
				if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1 ) {
					// 判断第一针接种时间是否在当月

					// 乙肝1针次是应种实种针次
					Y_BD_HepB1_Map.put(childcode, "021");
					S_BD_HepB1_Map.put(childcode, "021");
					// System.out.println(">>>乙肝疫苗1应种实种 >>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("021");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getHepbARe();
							ff.get(k).setHepbARe(++a);
							a = ff.get(k).getHepbASh();
							ff.get(k).setHepbASh(++a);
							if ((int) ((HepB_1_day.getTime() - birthday.getTime()) / 1000 / 60 / 60 / 24) <= 2) {
								a = ff.get(k).getHepbAaRe();
								ff.get(k).setHepbAaRe(++a);
							}
							break;
						}
					}
				} else {
					if (vacc_yzrq.containsKey("022")  && situation.equals("1") && officeInfo.equals(office)) {
						// 乙肝2针次是应种针次
						Y_BD_HepB2_Map.put(childcode, "022");
						
						// System.out.println(">>>乙肝疫苗2应种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("022");
								expRoutinevacc61Detail.setDosage("2");
								expRoutinevacc61Detail.setType("1");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getHepbBSh();
								ff.get(k).setHepbBSh(++a);
								break;
							}
						}
					}
				}

			} else {
				if (vacc_yzrq.containsKey("021")  && situation.equals("1") && officeInfo.equals(office)) {
					// 第一针乙肝为空
					// 乙肝第1针次是应种
					Y_BD_HepB1_Map.put(childcode, "021");
					// System.out.println(">>>乙肝疫苗1应种 >>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("021");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getHepbASh();
							ff.get(k).setHepbASh(++a);
							break;
						}
					}
				}

			}
		}

	}

	// 大于6月龄的三针乙肝
	public void do_HepB_3( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area,
			Date birthday,Date HepB_1_day, String createDate, String office, String situation, String officeInfo, String create_age_t) {
		
		Map<String, String> t_map_3 = (Map<String, String>) vacc_list.get("023");
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("022");
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("021");
		

		if (t_map_3 != null) {
			// 乙肝第三针有接种纪录

			if (Double.parseDouble((String) t_map_3.get("dif_mon")) < 1 
					&& t_map_3.get("office").equals(office) && !(t_map_3.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_HepB3_Map.put(childcode, "023");
				S_BD_HepB3_Map.put(childcode, "023");
				// System.out.println(">>>乙肝疫苗3应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("023");
						expRoutinevacc61Detail.setDosage("3");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepbCRe();
						ff.get(k).setHepbCRe(++a);
						a = ff.get(k).getHepbCSh();
						ff.get(k).setHepbCSh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>乙肝疫苗3很早就接种了 >>>");
			}
		} else {
			
			// 判断乙肝第二针是否有接种纪录
			if (t_map_2 != null) {
				// 乙肝第二针已经有接种纪录
				if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1 
						&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
					// 判断第二针接种时间是否在当月

					// 乙肝针次是应种针次
					Y_BD_HepB2_Map.put(childcode, "022");
					S_BD_HepB2_Map.put(childcode, "022");
					
					// System.out.println(">>>乙肝疫苗2应种实种 >>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("022");
							expRoutinevacc61Detail.setDosage("2");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getHepbBRe();
							ff.get(k).setHepbBRe(++a);
							a = ff.get(k).getHepbBSh();
							ff.get(k).setHepbBSh(++a);
							break;
						}
					}
					
					
					//加入当月建卡的乙肝第一针数据统计
					if(Double.valueOf(create_age_t) < 1){
						//建卡月份在当月的记录
						// 乙肝1针次是应种针次
						Y_BD_HepB1_Map.put(childcode, "021");
						S_BD_HepB1_Map.put(childcode, "021");
						// System.out.println(">>>乙肝疫苗1应种实种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("021");
								expRoutinevacc61Detail.setDosage("1");
								expRoutinevacc61Detail.setType("3");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getHepbARe();
								ff.get(k).setHepbARe(++a);
								a = ff.get(k).getHepbASh();
								ff.get(k).setHepbASh(++a);
								if ((int) ((HepB_1_day.getTime() - birthday.getTime()) / 1000 / 60 / 60 / 24) <= 2) {
									a = ff.get(k).getHepbAaRe();
									ff.get(k).setHepbAaRe(++a);
								}
								break;
							}
						}
					}
					
				} else {
					if (vacc_yzrq.containsKey("023")  && situation.equals("1") && officeInfo.equals(office)) {
						if(t_map_1 != null && t_map_2 != null){
							if ((Double.parseDouble((String) t_map_2.get("dif_mon")) > 2)
									&& Double.parseDouble((String) t_map_1.get("dif_mon")) >6 ) {
								// 乙肝第二针三个月且第一针6个月，则第三针次是应种
								Y_BD_HepB3_Map.put(childcode, "023");
								// System.out.println(">>>乙肝疫苗3应种 >>>");
								for (int k = 0; k < ff.size(); k++) {
									if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
										
										ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
										expRoutinevacc61Detail.setId(IdGen.uuid());
										expRoutinevacc61Detail.setYearMonth(createDate);
										expRoutinevacc61Detail.setChildcode(childcode);
										expRoutinevacc61Detail.setUnitCode(area);
										expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
										expRoutinevacc61Detail.setVaccCode("023");
										expRoutinevacc61Detail.setDosage("3");
										expRoutinevacc61Detail.setType("1");
										expRoutinevacc61Detail.setLocalCode(office);
										expRoutinevacc61Detail.setIsNewRecord(true);
										expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
										
										int a = ff.get(k).getHepbCSh();
										ff.get(k).setHepbCSh(++a);
										break;
									}
								}
							} else {
								// System.out.println(">>>乙肝疫苗3未到间隔 >>>");
							}
						}
					}
				}
			} else {
				
				// 判断乙肝第一针是否接种
				if (t_map_1 != null) {
					// 第一针乙肝不为空
					if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1) {
						// 判断第一针接种时间是否在当月

						// 乙肝1针次是应种针次
						Y_BD_HepB1_Map.put(childcode, "021");
						S_BD_HepB1_Map.put(childcode, "021");
						// System.out.println(">>>乙肝疫苗1应种实种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("021");
								expRoutinevacc61Detail.setDosage("1");
								expRoutinevacc61Detail.setType("3");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getHepbARe();
								ff.get(k).setHepbARe(++a);
								a = ff.get(k).getHepbASh();
								ff.get(k).setHepbASh(++a);
								if ((int) ((HepB_1_day.getTime() - birthday.getTime()) / 1000 / 60 / 60 / 24) <= 2) {
									a = ff.get(k).getHepbAaRe();
									ff.get(k).setHepbAaRe(++a);
								}
								break;
							}
						}
					} else {
						if (vacc_yzrq.containsKey("022")  && situation.equals("1") && officeInfo.equals(office)) {
							// 乙肝2针次是应种针次
							Y_BD_HepB2_Map.put(childcode, "022");
							// System.out.println(">>>乙肝疫苗2应种 >>>");
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("022");
									expRoutinevacc61Detail.setDosage("2");
									expRoutinevacc61Detail.setType("1");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
									int a = ff.get(k).getHepbBSh();
									ff.get(k).setHepbBSh(++a);
									break;
								}
							}
						}
					}

				} else {
					if (vacc_yzrq.containsKey("021")  && situation.equals("1") && officeInfo.equals(office)) {
						// 第一针乙肝为空
						// 乙肝第1针次是应种
						Y_BD_HepB1_Map.put(childcode, "021");
						// System.out.println(">>>乙肝疫苗1应种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("021");
								expRoutinevacc61Detail.setDosage("1");
								expRoutinevacc61Detail.setType("1");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getHepbASh();
								ff.get(k).setHepbASh(++a);
								break;
							}
						}
					}
				}
			}

		}

	}

	// 脊灰第一针 2月龄
	public void do_PV_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("031");

		// 判断脊灰疫苗第1 针是否接种
		if (t_map_1 != null) {
			// 第一针脊灰不为空
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种是否在当月

				// 脊灰1针次是应种针次
				Y_BD_PV1_Map.put(childcode, "031");
				S_BD_PV1_Map.put(childcode, "031");
				// System.out.println(">>>脊灰疫苗1应种实种>>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("031");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getPvARe();
						ff.get(k).setPvARe(++a);
						a = ff.get(k).getPvASh();
						ff.get(k).setPvASh(++a);
						break;
					}
				}
			} else {
				// 第一针脊灰不为空 接种日期比较早
				// System.out.println(">>>脊灰疫苗1已经接种很久了 >>>");
			}
		} else {

			if (vacc_yzrq.containsKey("031")  && situation.equals("1") && officeInfo.equals(office)) {
				// 脊灰第1针次是应种
				Y_BD_PV1_Map.put(childcode, "031");
				// System.out.println(">>>脊灰疫苗1应种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("031");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getPvASh();
						ff.get(k).setPvASh(++a);
						break;
					}
				}
			}
			

		}

	}

	// 脊灰第二针3月龄
	public void do_PV_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("032");

		// 判断脊灰疫苗第2针是否接种
		if (t_map_2 != null) {
			// 第2针脊灰不为空
			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断第2针接种时间是否在当月

				// 脊灰2针次是应种针次
				Y_BD_PV2_Map.put(childcode, "032");
				S_BD_PV2_Map.put(childcode, "032");
				// System.out.println(">>>脊灰疫苗2应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("032");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getPvBRe();
						ff.get(k).setPvBRe(++a);
						a = ff.get(k).getPvBSh();
						ff.get(k).setPvBSh(++a);
						break;
					}
				}
			} else {

				// System.out.println(">>>脊灰疫苗2已经接种很久了 >>>");
			}

		} else {
			
			Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("031");
			
			// 判断脊灰疫苗第1 针是否接种
			if (t_map_1 != null) {
				// 第一针脊灰不为空
				if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
						&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
					// 判断第1针接种是否在当月

					// 脊灰1针次是应种针次
					Y_BD_PV1_Map.put(childcode, "031");
					S_BD_PV1_Map.put(childcode, "031");
					// System.out.println(">>>脊灰疫苗1应种实种>>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("031");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getPvARe();
							ff.get(k).setPvARe(++a);
							a = ff.get(k).getPvASh();
							ff.get(k).setPvASh(++a);
							break;
						}
					}
				} else {
					if (vacc_yzrq.containsKey("032")  && situation.equals("1") && officeInfo.equals(office)) {
						// 第一针脊灰不为空 接种日期比较早

						// 脊灰2针次是应种针次
						Y_BD_PV2_Map.put(childcode, "032");
						// System.out.println(">>>脊灰疫苗2应种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("032");
								expRoutinevacc61Detail.setDosage("2");
								expRoutinevacc61Detail.setType("1");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getPvBSh();
								ff.get(k).setPvBSh(++a);
								break;
							}
						}
					}
				}
			} else {
				
					if (vacc_yzrq.containsKey("031")  && situation.equals("1") && officeInfo.equals(office)) {
						// 脊灰第1针次是应种
						Y_BD_PV1_Map.put(childcode, "031");
						// System.out.println(">>>脊灰疫苗1应种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("031");
								expRoutinevacc61Detail.setDosage("1");
								expRoutinevacc61Detail.setType("1");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getPvASh();
								ff.get(k).setPvASh(++a);
								break;
							}
						}
					}
			}
			
		}

	}

	// 脊灰第三针 4月龄
	public void do_PV_3( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_3 = (Map<String, String>) vacc_list.get("033");
		
		// 判断脊灰第3针是否有接种纪录
		if (t_map_3 != null) {
			// 脊灰第3针已经有接种纪录
			if (Double.parseDouble((String) t_map_3.get("dif_mon")) < 1
					&& t_map_3.get("office").equals(office) && !(t_map_3.get("source").equals("3"))) {
				// 判断第3针接种时间是否在当月
				
				// 脊灰3针次是应种针次
				Y_BD_PV3_Map.put(childcode, "033");
				S_BD_PV3_Map.put(childcode, "033");
				// System.out.println(">>>脊灰疫苗3应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("033");
						expRoutinevacc61Detail.setDosage("3");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getPvCRe();
						ff.get(k).setPvCRe(++a);
						a = ff.get(k).getPvCSh();
						ff.get(k).setPvCSh(++a);
						break;
					}
				}
			} else {

				// System.out.println(">>>脊灰疫苗3接种很久了>>>");
			}
		} else {
			
			Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("032");
			
				// 没有替代 没有第三针，则判断脊灰疫苗第2针是否接种
				if (t_map_2 != null) {
					// 第2针脊灰不为空
					if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
							&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
						// 判断第2针接种时间是否在当月

						// 脊灰2针次是应种针次
						Y_BD_PV2_Map.put(childcode, "032");
						S_BD_PV2_Map.put(childcode, "032");
						// System.out.println(">>>脊灰疫苗2应种实种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("032");
								expRoutinevacc61Detail.setDosage("2");
								expRoutinevacc61Detail.setType("3");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getPvBRe();
								ff.get(k).setPvBRe(++a);
								a = ff.get(k).getPvBSh();
								ff.get(k).setPvBSh(++a);
								break;
							}
						}
					} else {
						if (vacc_yzrq.containsKey("033")  && situation.equals("1") && officeInfo.equals(office)) {
							// 脊灰3针次是应种针次
							Y_BD_PV3_Map.put(childcode, "033");
							// System.out.println(">>>脊灰疫苗3应种 >>>");
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("033");
									expRoutinevacc61Detail.setDosage("3");
									expRoutinevacc61Detail.setType("1");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
									int a = ff.get(k).getPvCSh();
									ff.get(k).setPvCSh(++a);
									break;
								}
							}
						}
					}

				} else {
					
					Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("031");
					
						// 判断脊灰疫苗第1 针是否接种
						if (t_map_1 != null) {
							// 第一针脊灰不为空
							if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
									&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
								// 判断第1针接种是否在当月

								// 脊灰1针次是应种针次
								Y_BD_PV1_Map.put(childcode, "031");
								S_BD_PV1_Map.put(childcode, "031");
								// System.out.println(">>>脊灰疫苗1应种实种>>>");
								for (int k = 0; k < ff.size(); k++) {
									if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
										
										ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
										expRoutinevacc61Detail.setId(IdGen.uuid());
										expRoutinevacc61Detail.setYearMonth(createDate);
										expRoutinevacc61Detail.setChildcode(childcode);
										expRoutinevacc61Detail.setUnitCode(area);
										expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
										expRoutinevacc61Detail.setVaccCode("031");
										expRoutinevacc61Detail.setDosage("1");
										expRoutinevacc61Detail.setType("3");
										expRoutinevacc61Detail.setLocalCode(office);
										expRoutinevacc61Detail.setIsNewRecord(true);
										expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
										
										int a = ff.get(k).getPvARe();
										ff.get(k).setPvARe(++a);
										a = ff.get(k).getPvASh();
										ff.get(k).setPvASh(++a);
										break;
									}
								}
							} else {
								if (vacc_yzrq.containsKey("032")  && situation.equals("1") && officeInfo.equals(office)) {
									// 第一针脊灰不为空 接种日期比较早
									// 脊灰2针次是应种针次
									Y_BD_PV2_Map.put(childcode, "032");
									// System.out.println(">>>脊灰疫苗2应种 >>>");
									for (int k = 0; k < ff.size(); k++) {
										if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
											
											ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
											expRoutinevacc61Detail.setId(IdGen.uuid());
											expRoutinevacc61Detail.setYearMonth(createDate);
											expRoutinevacc61Detail.setChildcode(childcode);
											expRoutinevacc61Detail.setUnitCode(area);
											expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
											expRoutinevacc61Detail.setVaccCode("032");
											expRoutinevacc61Detail.setDosage("2");
											expRoutinevacc61Detail.setType("1");
											expRoutinevacc61Detail.setLocalCode(office);
											expRoutinevacc61Detail.setIsNewRecord(true);
											expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
											
											int a = ff.get(k).getPvBSh();
											ff.get(k).setPvBSh(++a);
											break;
										}
									}
								} else {
									// System.out.println(">>>脊灰疫苗2不统计了 >>>");
								}
							}
						} else {
							
								if (vacc_yzrq.containsKey("031")  && situation.equals("1") && officeInfo.equals(office)) {
									// 脊灰第1针次是应种
									Y_BD_PV1_Map.put(childcode, "031");
									// System.out.println(">>>脊灰疫苗1应种 >>>");
									for (int k = 0; k < ff.size(); k++) {
										if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
											
											ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
											expRoutinevacc61Detail.setId(IdGen.uuid());
											expRoutinevacc61Detail.setYearMonth(createDate);
											expRoutinevacc61Detail.setChildcode(childcode);
											expRoutinevacc61Detail.setUnitCode(area);
											expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
											expRoutinevacc61Detail.setVaccCode("031");
											expRoutinevacc61Detail.setDosage("1");
											expRoutinevacc61Detail.setType("1");
											expRoutinevacc61Detail.setLocalCode(office);
											expRoutinevacc61Detail.setIsNewRecord(true);
											expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
											
											int a = ff.get(k).getPvASh();
											ff.get(k).setPvASh(++a);
											break;
										}
									}
								} else {
									// System.out.println(">>>脊灰疫苗1不统记了 >>>");
								}
							

						}
					

				}
			
		}

	}

	//// 脊灰 4针次
	public void do_PV_4( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_4 = (Map<String, String>) vacc_list.get("034");

		if (t_map_4 != null) {
			// 脊灰疫苗第4针有接种记录

			if (Double.parseDouble((String) t_map_4.get("dif_mon")) < 1
					&& t_map_4.get("office").equals(office) && !(t_map_4.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_PV4_Map.put(childcode, "034");
				S_BD_PV4_Map.put(childcode, "034");
				// System.out.println(">>>脊灰疫苗4应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("034");
						expRoutinevacc61Detail.setDosage("4");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getPvDRe();
						ff.get(k).setPvDRe(++a);
						a = ff.get(k).getPvDSh();
						ff.get(k).setPvDSh(++a);
						break;
					}
				}

			} else {
				// System.out.println(">>>脊灰疫苗4接种很久了>>>");
			}
		} else {
			
			Map<String, String> t_map_3 = (Map<String, String>) vacc_list.get("033");
			
			// 判断脊灰第3针是否有接种纪录
			if (t_map_3 != null) {
				// 脊灰第3针已经有接种纪录
				if (Double.parseDouble((String) t_map_3.get("dif_mon")) < 1
						&& t_map_3.get("office").equals(office) && !(t_map_3.get("source").equals("3"))) {
					// 判断第3针接种时间是否在当月

					// 脊灰3针次是应种针次
					Y_BD_PV3_Map.put(childcode, "033");
					S_BD_PV3_Map.put(childcode, "033");
					// System.out.println(">>>脊灰疫苗3应种实种 >>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("033");
							expRoutinevacc61Detail.setDosage("3");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getPvCRe();
							ff.get(k).setPvCRe(++a);
							a = ff.get(k).getPvCSh();
							ff.get(k).setPvCSh(++a);
							break;
						}
					}
				} else {
					if (vacc_yzrq.containsKey("034")  && situation.equals("1") && officeInfo.equals(office)) {
						// 脊灰第4针次是应种
						Y_BD_PV4_Map.put(childcode, "034");
						// System.out.println(">>>脊灰疫苗4应种>>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("034");
								expRoutinevacc61Detail.setDosage("4");
								expRoutinevacc61Detail.setType("1");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getPvDSh();
								ff.get(k).setPvDSh(++a);
								break;
							}
						}
					} else {
						// System.out.println("不做统计了");
					}
				}
			} else {
				
				Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("032");
				
					// 判断脊灰疫苗第2针是否接种
					if (t_map_2 != null) {
						// 第2针脊灰不为空
						if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
								&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
							// 判断第2针接种时间是否在当月

							// 脊灰2针次是应种针次
							Y_BD_PV2_Map.put(childcode, "032");
							S_BD_PV2_Map.put(childcode, "032");
							// System.out.println(">>>脊灰疫苗2应种实种 >>>");
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("032");
									expRoutinevacc61Detail.setDosage("2");
									expRoutinevacc61Detail.setType("3");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
									int a = ff.get(k).getPvBRe();
									ff.get(k).setPvBRe(++a);
									a = ff.get(k).getPvBSh();
									ff.get(k).setPvBSh(++a);
									break;
								}
							}
						} else {
							if (vacc_yzrq.containsKey("033")  && situation.equals("1") && officeInfo.equals(office)) {
								// 脊灰3针次是应种针次
								Y_BD_PV3_Map.put(childcode, "033");
								// System.out.println(">>>脊灰疫苗3应种 >>>");
								for (int k = 0; k < ff.size(); k++) {
									if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
										
										ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
										expRoutinevacc61Detail.setId(IdGen.uuid());
										expRoutinevacc61Detail.setYearMonth(createDate);
										expRoutinevacc61Detail.setChildcode(childcode);
										expRoutinevacc61Detail.setUnitCode(area);
										expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
										expRoutinevacc61Detail.setVaccCode("033");
										expRoutinevacc61Detail.setDosage("3");
										expRoutinevacc61Detail.setType("1");
										expRoutinevacc61Detail.setLocalCode(office);
										expRoutinevacc61Detail.setIsNewRecord(true);
										expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
										
										int a = ff.get(k).getPvCSh();
										ff.get(k).setPvCSh(++a);
										break;
									}
								}
							} else {
								// System.out.println("不做统计了");
							}
						}

					} else {
						
						Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("031");
						
							// 判断脊灰疫苗第1 针是否接种
							if (t_map_1 != null) {
								// 第一针脊灰不为空
								if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
										&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
									// 判断第1针接种是否在当月

									// 脊灰1针次是应种针次
									Y_BD_PV1_Map.put(childcode, "031");
									S_BD_PV1_Map.put(childcode, "031");
									// System.out.println(">>>脊灰疫苗1应种实种>>>");
									for (int k = 0; k < ff.size(); k++) {
										if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
											
											ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
											expRoutinevacc61Detail.setId(IdGen.uuid());
											expRoutinevacc61Detail.setYearMonth(createDate);
											expRoutinevacc61Detail.setChildcode(childcode);
											expRoutinevacc61Detail.setUnitCode(area);
											expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
											expRoutinevacc61Detail.setVaccCode("031");
											expRoutinevacc61Detail.setDosage("1");
											expRoutinevacc61Detail.setType("3");
											expRoutinevacc61Detail.setLocalCode(office);
											expRoutinevacc61Detail.setIsNewRecord(true);
											expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
											
											int a = ff.get(k).getPvARe();
											ff.get(k).setPvARe(++a);
											a = ff.get(k).getPvASh();
											ff.get(k).setPvASh(++a);
											break;
										}
									}
								} else {
									if (vacc_yzrq.containsKey("032")  && situation.equals("1") && officeInfo.equals(office)) {
										// 第一针脊灰不为空 接种日期比较早

										// 脊灰2针次是应种针次
										Y_BD_PV2_Map.put(childcode, "032");
										// System.out.println(">>>脊灰疫苗2应种
										// >>>");
										for (int k = 0; k < ff.size(); k++) {
											if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
												
												ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
												expRoutinevacc61Detail.setId(IdGen.uuid());
												expRoutinevacc61Detail.setYearMonth(createDate);
												expRoutinevacc61Detail.setChildcode(childcode);
												expRoutinevacc61Detail.setUnitCode(area);
												expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
												expRoutinevacc61Detail.setVaccCode("032");
												expRoutinevacc61Detail.setDosage("2");
												expRoutinevacc61Detail.setType("1");
												expRoutinevacc61Detail.setLocalCode(office);
												expRoutinevacc61Detail.setIsNewRecord(true);
												expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
												
												int a = ff.get(k).getPvBSh();
												ff.get(k).setPvBSh(++a);
												break;
											}
										}
									} else {
										// System.out.println("不做统计了");
									}
								}
							} else {
								
									if (vacc_yzrq.containsKey("031")  && situation.equals("1") && officeInfo.equals(office)) {
										// 脊灰第1针次是应种
										Y_BD_PV1_Map.put(childcode, "031");
										// System.out.println(">>>脊灰疫苗1应种
										for (int k = 0; k < ff.size(); k++) {
											if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
												
												ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
												expRoutinevacc61Detail.setId(IdGen.uuid());
												expRoutinevacc61Detail.setYearMonth(createDate);
												expRoutinevacc61Detail.setChildcode(childcode);
												expRoutinevacc61Detail.setUnitCode(area);
												expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
												expRoutinevacc61Detail.setVaccCode("031");
												expRoutinevacc61Detail.setDosage("1");
												expRoutinevacc61Detail.setType("1");
												expRoutinevacc61Detail.setLocalCode(office);
												expRoutinevacc61Detail.setIsNewRecord(true);
												expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
												
												int a = ff.get(k).getPvASh();
												ff.get(k).setPvASh(++a);
												break;
											}
										}
									} else {
										// System.out.println("不做统计了");
									}
								
							}
						
					}
				
			}
			
		}
	}
	
	public void do_PV_4_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_4 = (Map<String, String>) vacc_list.get("034");

		if (t_map_4 != null) {
			// 脊灰疫苗第4针有接种记录

			if (Double.parseDouble((String) t_map_4.get("dif_mon")) < 1
					&& t_map_4.get("office").equals(office) && !(t_map_4.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_PV4_Map.put(childcode, "034");
				S_BD_PV4_Map.put(childcode, "034");
				// System.out.println(">>>脊灰疫苗4应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("034");
						expRoutinevacc61Detail.setDosage("4");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getPvDRe();
						ff.get(k).setPvDRe(++a);
						a = ff.get(k).getPvDSh();
						ff.get(k).setPvDSh(++a);
						break;
					}
				}

			} else {
				// System.out.println(">>>脊灰疫苗4接种很久了>>>");
			}
		}
	}

	////

	// 百白破1
	public void do_DTP_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("041");

		// 判断百白破疫苗第1 针是否接种
		if (t_map_1 != null) {
			// 第一针百白破不为空
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种是否在当月

				// 百白破1针次是应种针次
				Y_BD_DTP1_Map.put(childcode, "041");
				S_BD_DTP1_Map.put(childcode, "041");
				
				// System.out.println(">>>百白破疫苗1应种实种>>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("041");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getDtpARe();
						ff.get(k).setDtpARe(++a);
						a = ff.get(k).getDtpASh();
						ff.get(k).setDtpASh(++a);
						break;
					}
				}
			} else {

				// System.out.println(">>>百白破疫苗1已经很久了>>>");

			}
		} else {
			
				if (vacc_yzrq.containsKey("041")  && situation.equals("1") && officeInfo.equals(office)) {
					// 第一针百白破为空
					// 百白破第1针次是应种
					Y_BD_DTP1_Map.put(childcode, "041");
					
					// System.out.println(">>>百白破疫苗1应种 >>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("041");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getDtpASh();
							ff.get(k).setDtpASh(++a);
							break;
						}
					}
				} else {
					// System.out.println("不做统计了");
				}
			
		}

	}

	// 百白破2
	public void do_DTP_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("042");

		// 判断百白破疫苗第2针是否接种
		if (t_map_2 != null) {
			// 第2针百白破不为空
			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断第2针接种时间是否在当月

				// 百白破2针次是应种针次
				Y_BD_DTP2_Map.put(childcode, "042");
				S_BD_DTP2_Map.put(childcode, "042");
				// System.out.println(">>>百白破疫苗2应种 实种>>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("042");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getDtpBRe();
						ff.get(k).setDtpBRe(++a);
						a = ff.get(k).getDtpBSh();
						ff.get(k).setDtpBSh(++a);
						break;
					}
				}
			} else {

				// System.out.println(">>>百白破疫苗2 很早以前就接种了 >>>");
			}

		} else {
			
			Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("041");
			
				// 判断百白破疫苗第1 针是否接种
				if (t_map_1 != null) {
					// 第一针百白破不为空
					if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
							&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
						// 判断第1针接种是否在当月

						// 百白破1针次是应种针次
						Y_BD_DTP1_Map.put(childcode, "041");
						S_BD_DTP1_Map.put(childcode, "041");
						
						// System.out.println(">>>百白破疫苗1应种实种>>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("041");
								expRoutinevacc61Detail.setDosage("1");
								expRoutinevacc61Detail.setType("3");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getDtpARe();
								ff.get(k).setDtpARe(++a);
								a = ff.get(k).getDtpASh();
								ff.get(k).setDtpASh(++a);
								break;
							}
						}
					} else {
						if (vacc_yzrq.containsKey("042")  && situation.equals("1") && officeInfo.equals(office)) {
							// 百白破2针次是应种针次
							Y_BD_DTP2_Map.put(childcode, "042");
							// System.out.println(">>>百白破疫苗2应种 >>>");
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("042");
									expRoutinevacc61Detail.setDosage("2");
									expRoutinevacc61Detail.setType("1");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
									int a = ff.get(k).getDtpBSh();
									ff.get(k).setDtpBSh(++a);
									break;
								}
							}
						} else {
							// System.out.println("不做统计了");
						}
					}
				} else {
					
						if (vacc_yzrq.containsKey("041")  && situation.equals("1") && officeInfo.equals(office)) {
							// 第一针百白破为空
							// 百白破第1针次是应种
							Y_BD_DTP1_Map.put(childcode, "041");
							
							// System.out.println(">>>百白破疫苗1应种 >>>");
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("041");
									expRoutinevacc61Detail.setDosage("1");
									expRoutinevacc61Detail.setType("1");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
									int a = ff.get(k).getDtpASh();
									ff.get(k).setDtpASh(++a);
									break;
								}
							}
						} else {
							// System.out.println("不做统计了");
						}
					

				}
			
		}

	}

	// 百白破3
	public void do_DTP_3( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_3 = (Map<String, String>) vacc_list.get("043");

		// 判断百白破第3针是否有接种纪录
		if (t_map_3 != null) {
			// 百白破第3针已经有接种纪录
			if (Double.parseDouble((String) t_map_3.get("dif_mon")) < 1
					&& t_map_3.get("office").equals(office) && !(t_map_3.get("source").equals("3"))) {
				// 判断第3针接种时间是否在当月

				// 百白破3针次是应种针次
				Y_BD_DTP3_Map.put(childcode, "043");
				S_BD_DTP3_Map.put(childcode, "043");

				// System.out.println(">>>百白破疫苗3应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("043");
						expRoutinevacc61Detail.setDosage("3");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getDtpCRe();
						ff.get(k).setDtpCRe(++a);
						a = ff.get(k).getDtpCSh();
						ff.get(k).setDtpCSh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>百白破疫苗3很早就接种了>>>");
			}
		} else {
			
			Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("042");
			
				// 判断百白破疫苗第2针是否接种
				if (t_map_2 != null) {
					// 第2针百白破不为空
					if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
							&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
						// 判断第2针接种时间是否在当月

						// 百白破2针次是应种针次
						Y_BD_DTP2_Map.put(childcode, "042");
						S_BD_DTP2_Map.put(childcode, "042");

						// System.out.println(">>>百白破疫苗2应种 实种>>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("042");
								expRoutinevacc61Detail.setDosage("2");
								expRoutinevacc61Detail.setType("3");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getDtpBRe();
								ff.get(k).setDtpBRe(++a);
								a = ff.get(k).getDtpBSh();
								ff.get(k).setDtpBSh(++a);
								break;
							}
						}
					} else {
						if (vacc_yzrq.containsKey("043")  && situation.equals("1") && officeInfo.equals(office)) {
							// 百白破3针次是应种针次
							Y_BD_DTP3_Map.put(childcode, "043");
							// System.out.println(">>>百白破疫苗3应种 >>>");
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("043");
									expRoutinevacc61Detail.setDosage("3");
									expRoutinevacc61Detail.setType("1");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
									int a = ff.get(k).getDtpCSh();
									ff.get(k).setDtpCSh(++a);
									break;
								}
							}
						} else {
							// System.out.println("不做统计了");
						}
					}

				} else {
					
					Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("041");
					
						// 判断百白破疫苗第1 针是否接种
						if (t_map_1 != null) {
							// 第一针百白破不为空
							if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
									&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
								// 判断第1针接种是否在当月

								// 百白破1针次是应种针次
								Y_BD_DTP1_Map.put(childcode, "041");
								S_BD_DTP1_Map.put(childcode, "041");
								
								// System.out.println(">>>百白破疫苗1应种实种>>>");
								for (int k = 0; k < ff.size(); k++) {
									if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
										
										ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
										expRoutinevacc61Detail.setId(IdGen.uuid());
										expRoutinevacc61Detail.setYearMonth(createDate);
										expRoutinevacc61Detail.setChildcode(childcode);
										expRoutinevacc61Detail.setUnitCode(area);
										expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
										expRoutinevacc61Detail.setVaccCode("041");
										expRoutinevacc61Detail.setDosage("1");
										expRoutinevacc61Detail.setType("3");
										expRoutinevacc61Detail.setLocalCode(office);
										expRoutinevacc61Detail.setIsNewRecord(true);
										expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
										
										int a = ff.get(k).getDtpARe();
										ff.get(k).setDtpARe(++a);
										a = ff.get(k).getDtpASh();
										ff.get(k).setDtpASh(++a);
										break;
									}
								}
							} else {
								if (vacc_yzrq.containsKey("042")  && situation.equals("1") && officeInfo.equals(office)) {
									// 百白破2针次是应种针次
									Y_BD_DTP2_Map.put(childcode, "042");
									// System.out.println(">>>百白破疫苗2应种 >>>");
									for (int k = 0; k < ff.size(); k++) {
										if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
											
											ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
											expRoutinevacc61Detail.setId(IdGen.uuid());
											expRoutinevacc61Detail.setYearMonth(createDate);
											expRoutinevacc61Detail.setChildcode(childcode);
											expRoutinevacc61Detail.setUnitCode(area);
											expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
											expRoutinevacc61Detail.setVaccCode("042");
											expRoutinevacc61Detail.setDosage("2");
											expRoutinevacc61Detail.setType("1");
											expRoutinevacc61Detail.setLocalCode(office);
											expRoutinevacc61Detail.setIsNewRecord(true);
											expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
											
											int a = ff.get(k).getDtpBSh();
											ff.get(k).setDtpBSh(++a);
											break;
										}
									}
								} else {
									// System.out.println("不做统计了");
								}
							}
						} else {
							
								if (vacc_yzrq.containsKey("041")  && situation.equals("1") && officeInfo.equals(office)) {
									// 第一针百白破为空
									// 百白破第1针次是应种
									Y_BD_DTP1_Map.put(childcode, "041");

									// System.out.println(">>>百白破疫苗1应种 >>>");
									for (int k = 0; k < ff.size(); k++) {
										if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
											
											ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
											expRoutinevacc61Detail.setId(IdGen.uuid());
											expRoutinevacc61Detail.setYearMonth(createDate);
											expRoutinevacc61Detail.setChildcode(childcode);
											expRoutinevacc61Detail.setUnitCode(area);
											expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
											expRoutinevacc61Detail.setVaccCode("041");
											expRoutinevacc61Detail.setDosage("1");
											expRoutinevacc61Detail.setType("1");
											expRoutinevacc61Detail.setLocalCode(office);
											expRoutinevacc61Detail.setIsNewRecord(true);
											expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
											
											int a = ff.get(k).getDtpASh();
											ff.get(k).setDtpASh(++a);
											break;
										}
									}
								} else {
									// System.out.println("不做统计了");
								}
							
						}
					
				}
			
		}

	}

	// 百白破4
	public void do_DTP_4( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_4 = (Map<String, String>) vacc_list.get("044");

		if (t_map_4 != null) {
			// 百白破疫苗第4针有接种记录
			if (Double.parseDouble((String) t_map_4.get("dif_mon")) < 1
					&& t_map_4.get("office").equals(office) && !(t_map_4.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_DTP4_Map.put(childcode, "044");
				S_BD_DTP4_Map.put(childcode, "044");

				// System.out.println(">>>百白破疫苗4应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("044");
						expRoutinevacc61Detail.setDosage("4");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getDtpDRe();
						ff.get(k).setDtpDRe(++a);
						a = ff.get(k).getDtpDSh();
						ff.get(k).setDtpDSh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>百白破疫苗4很早就接种了 >>>");
			}
		} else {
			
			Map<String, String> t_map_3 = (Map<String, String>) vacc_list.get("043");
			
				// 判断百白破第3针是否有接种纪录
				if (t_map_3 != null) {
					// 百白破第3针已经有接种纪录
					if (Double.parseDouble((String) t_map_3.get("dif_mon")) < 1
							&& t_map_3.get("office").equals(office) && !(t_map_3.get("source").equals("3"))) {
						// 判断第3针接种时间是否在当月

						// 百白破3针次是应种针次
						Y_BD_DTP3_Map.put(childcode, "043");
						S_BD_DTP3_Map.put(childcode, "043");
						// System.out.println(">>>百白破疫苗3应种实种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("043");
								expRoutinevacc61Detail.setDosage("3");
								expRoutinevacc61Detail.setType("3");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getDtpCRe();
								ff.get(k).setDtpCRe(++a);
								a = ff.get(k).getDtpCSh();
								ff.get(k).setDtpCSh(++a);
								break;
							}
						}
					} else {
						if (vacc_yzrq.containsKey("044")  && situation.equals("1") && officeInfo.equals(office)) {
							if (Double.parseDouble((String) t_map_3.get("dif_mon")) > 6) {
								// 百白破第4针次是应种
								Y_BD_DTP4_Map.put(childcode, "044");
								// System.out.println(">>>百白破疫苗4应种>>>");
								for (int k = 0; k < ff.size(); k++) {
									if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
										
										ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
										expRoutinevacc61Detail.setId(IdGen.uuid());
										expRoutinevacc61Detail.setYearMonth(createDate);
										expRoutinevacc61Detail.setChildcode(childcode);
										expRoutinevacc61Detail.setUnitCode(area);
										expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
										expRoutinevacc61Detail.setVaccCode("044");
										expRoutinevacc61Detail.setDosage("4");
										expRoutinevacc61Detail.setType("1");
										expRoutinevacc61Detail.setLocalCode(office);
										expRoutinevacc61Detail.setIsNewRecord(true);
										expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
										
										int a = ff.get(k).getDtpDSh();
										ff.get(k).setDtpDSh(++a);
										break;
									}else{
										//TODO:将应种值计入默认社区
									}
								}
							} else {
								// System.out.println(">>>百白破疫苗4未到间隔>>>");
							}
						} else {
							// System.out.println("不做统计了");
						}

					}
				} else {
					
					Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("042");
					
						// 判断百白破疫苗第2针是否接种
						if (t_map_2 != null) {
							// 第2针百白破不为空
							if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
									&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
								// 判断第2针接种时间是否在当月

								// 百白破2针次是应种针次
								Y_BD_DTP2_Map.put(childcode, "042");
								S_BD_DTP2_Map.put(childcode, "042");
								// System.out.println(">>>百白破疫苗2应种实种 >>>");
								for (int k = 0; k < ff.size(); k++) {
									if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
										
										ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
										expRoutinevacc61Detail.setId(IdGen.uuid());
										expRoutinevacc61Detail.setYearMonth(createDate);
										expRoutinevacc61Detail.setChildcode(childcode);
										expRoutinevacc61Detail.setUnitCode(area);
										expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
										expRoutinevacc61Detail.setVaccCode("042");
										expRoutinevacc61Detail.setDosage("2");
										expRoutinevacc61Detail.setType("3");
										expRoutinevacc61Detail.setLocalCode(office);
										expRoutinevacc61Detail.setIsNewRecord(true);
										expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
										
										int a = ff.get(k).getDtpBRe();
										ff.get(k).setDtpBRe(++a);
										a = ff.get(k).getDtpBSh();
										ff.get(k).setDtpBSh(++a);
										break;
									}
								}
							} else {
								if (vacc_yzrq.containsKey("043")  && situation.equals("1") && officeInfo.equals(office)) {
									// 百白破3针次是应种针次
									Y_BD_DTP3_Map.put(childcode, "043");

									// System.out.println(">>>百白破疫苗3应种 >>>");
									for (int k = 0; k < ff.size(); k++) {
										if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
											
											ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
											expRoutinevacc61Detail.setId(IdGen.uuid());
											expRoutinevacc61Detail.setYearMonth(createDate);
											expRoutinevacc61Detail.setChildcode(childcode);
											expRoutinevacc61Detail.setUnitCode(area);
											expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
											expRoutinevacc61Detail.setVaccCode("043");
											expRoutinevacc61Detail.setDosage("3");
											expRoutinevacc61Detail.setType("1");
											expRoutinevacc61Detail.setLocalCode(office);
											expRoutinevacc61Detail.setIsNewRecord(true);
											expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
											
											int a = ff.get(k).getDtpCSh();
											ff.get(k).setDtpCSh(++a);
											break;
										}
									}
								} else {
									// System.out.println("不做统计了");
								}
							}

						} else {
							
							Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("041");
							
								// 判断百白破疫苗第1 针是否接种
								if (t_map_1 != null) {
									// 第一针百白破不为空
									if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
											&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
										// 判断第1针接种是否在当月

										// 百白破1针次是应种针次
										Y_BD_DTP1_Map.put(childcode, "041");
										S_BD_DTP1_Map.put(childcode, "041");

										// System.out.println(">>>百白破疫苗1应种实种>>>");
										for (int k = 0; k < ff.size(); k++) {
											if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
												
												ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
												expRoutinevacc61Detail.setId(IdGen.uuid());
												expRoutinevacc61Detail.setYearMonth(createDate);
												expRoutinevacc61Detail.setChildcode(childcode);
												expRoutinevacc61Detail.setUnitCode(area);
												expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
												expRoutinevacc61Detail.setVaccCode("041");
												expRoutinevacc61Detail.setDosage("1");
												expRoutinevacc61Detail.setType("3");
												expRoutinevacc61Detail.setLocalCode(office);
												expRoutinevacc61Detail.setIsNewRecord(true);
												expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
												
												int a = ff.get(k).getDtpARe();
												ff.get(k).setDtpARe(++a);
												a = ff.get(k).getDtpASh();
												ff.get(k).setDtpASh(++a);
												break;
											}
										}
									} else {
										if (vacc_yzrq.containsKey("042")  && situation.equals("1") && officeInfo.equals(office)) {

											// 百白破2针次是应种针次
											Y_BD_DTP2_Map.put(childcode, "042");

											// System.out.println(">>>百白破疫苗2应种
											// >>>");
											for (int k = 0; k < ff.size(); k++) {
												if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
													
													ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
													expRoutinevacc61Detail.setId(IdGen.uuid());
													expRoutinevacc61Detail.setYearMonth(createDate);
													expRoutinevacc61Detail.setChildcode(childcode);
													expRoutinevacc61Detail.setUnitCode(area);
													expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
													expRoutinevacc61Detail.setVaccCode("042");
													expRoutinevacc61Detail.setDosage("2");
													expRoutinevacc61Detail.setType("1");
													expRoutinevacc61Detail.setLocalCode(office);
													expRoutinevacc61Detail.setIsNewRecord(true);
													expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
													
													int a = ff.get(k).getDtpBSh();
													ff.get(k).setDtpBSh(++a);
													break;
												}
											}
										} else {
											// System.out.println("不做统计了");
										}
									}
								} else {
									
										if (vacc_yzrq.containsKey("041")  && situation.equals("1") && officeInfo.equals(office)) {
											// 第一针百白破为空
											// 百白破第1针次是应种
											Y_BD_DTP1_Map.put(childcode, "041");

											// System.out.println(">>>百白破疫苗1应种
											// >>>");
											for (int k = 0; k < ff.size(); k++) {
												if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
													
													ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
													expRoutinevacc61Detail.setId(IdGen.uuid());
													expRoutinevacc61Detail.setYearMonth(createDate);
													expRoutinevacc61Detail.setChildcode(childcode);
													expRoutinevacc61Detail.setUnitCode(area);
													expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
													expRoutinevacc61Detail.setVaccCode("041");
													expRoutinevacc61Detail.setDosage("1");
													expRoutinevacc61Detail.setType("1");
													expRoutinevacc61Detail.setLocalCode(office);
													expRoutinevacc61Detail.setIsNewRecord(true);
													expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
													
													int a = ff.get(k).getDtpASh();
													ff.get(k).setDtpASh(++a);
													break;
												}
											}
										} else {
											// System.out.println("不做统计了");
										}
									
								}
							
						}
					
				}
			
		}
	}
	
	

	/////////////////////

	// A+C群流脑结合1
	public void do_MENA_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("871");

		// 判断A+C群流脑结合疫苗第1针是否有接种纪录
		if (t_map_1 != null) {
			// A+C群流脑结合疫苗第1针已经有接种纪录
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种时间是否在当月

				// A+C群流脑结合疫苗第1针次是应种针次
				Y_BD_MenA1_Map.put(childcode, "871");
				S_BD_MenA1_Map.put(childcode, "871");
				// System.out.println(">>>A+C群流脑结合疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("871");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMenaARe();
						ff.get(k).setMenaARe(++a);
						a = ff.get(k).getMenaASh();
						ff.get(k).setMenaASh(++a);
						break;
					}
				}
			} else {
				// A+C群流脑结合疫苗第2针次是应种

				// System.out.println(">>>A+C群流脑结合疫苗1很早就接种了>>>");
			}
		} else {			
				
			if (vacc_yzrq.containsKey("871")  && situation.equals("1") && officeInfo.equals(office)) {
				// A+C群流脑结合疫苗1应种
				Y_BD_MenA1_Map.put(childcode, "871");

				// System.out.println(">>>A+C群流脑结合疫苗1应种>>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("871");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMenaASh();
						ff.get(k).setMenaASh(++a);
						break;
					}
				}
			} else {
				// System.out.println("不做统计了");
			}				
			
		}

	}
	
	/**
	 * A+C群流脑结合1 对AC-Hib联合疫苗拆解的情况做特殊处理,判断月龄:2-6月<br>
	 * 此情况只判断由ACHib拆解而来,如果是拆解实种：实种+1、应种+1,如果是拆解的应种未种则不计入应种和实种.
	 * @author Jack
	 * @date 2018年2月2日 下午5:47:09
	 * @description 
	 * @param vacc_list
	 * @param childcode
	 * @param vacc_yzrq
	 * @param reside
	 * @param area
	 * @param createDate
	 * @param office
	 * @param situation
	 * @param officeInfo
	 *
	 */
	public void do_MENA_1_Special( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("871");

		// 判断A+C群流脑结合疫苗第1针是否有接种纪录
		if (t_map_1 != null) {
			// A+C群流脑结合疫苗第1针已经有接种纪录
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种时间是否在当月

				// A+C群流脑结合疫苗第1针次是应种针次
				Y_BD_MenA1_Map.put(childcode, "871");
				S_BD_MenA1_Map.put(childcode, "871");
				// System.out.println(">>>A+C群流脑结合疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("871");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMenaARe();
						ff.get(k).setMenaARe(++a);
						a = ff.get(k).getMenaASh();
						ff.get(k).setMenaASh(++a);
						break;
					}
				}
			} else {
				// A+C群流脑结合疫苗第2针次是应种

				// System.out.println(">>>A+C群流脑结合疫苗1很早就接种了>>>");
			}
		}

	}

	// A+C群流脑结合2
	public void do_MENA_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("872");

		if (t_map_2 != null) {
			// A+C群流脑结合疫苗第2针有接种记录

			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_MenA2_Map.put(childcode, "872");
				S_BD_MenA2_Map.put(childcode, "872");

				// System.out.println(">>>A+C群流脑结合疫苗2应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("872");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMenaBRe();
						ff.get(k).setMenaBRe(++a);
						a = ff.get(k).getMenaBSh();
						ff.get(k).setMenaBSh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>A+C群流脑结合疫苗2很早就接种了 >>>");
			}
		} else {
			
			Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("871");
			
			if (t_map_1 != null) {
				// A+C群流脑疫苗第1针已经有接种纪录
				if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
						&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
					// 判断第1针接种时间是否在当月

					// A+C群流脑结合疫苗第1针次是应种针次
					Y_BD_MenA1_Map.put(childcode, "871");
					S_BD_MenA1_Map.put(childcode, "871");
					// System.out.println(">>>A+C群流脑结合疫苗1应种实种
					// >>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("871");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getMenaARe();
							ff.get(k).setMenaARe(++a);
							a = ff.get(k).getMenaASh();
							ff.get(k).setMenaASh(++a);
							break;
						}
					}
				} else {
					if (vacc_yzrq.containsKey("872")  && situation.equals("1") && officeInfo.equals(office)) {
						// A+C群流脑结合疫苗第2针次是应种
						Y_BD_MenA2_Map.put(childcode, "872");

						// System.out.println(">>>A+C群流脑结合疫苗2应种>>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("872");
								expRoutinevacc61Detail.setDosage("2");
								expRoutinevacc61Detail.setType("1");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getMenaBSh();
								ff.get(k).setMenaBSh(++a);
								break;
							}
						}
					} else {
						// System.out.println("不做统计了");
					}
				}
			} else {
				if (vacc_yzrq.containsKey("871")  && situation.equals("1") && officeInfo.equals(office)) {
					// A+C群流脑结合疫苗1应种
					Y_BD_MenA1_Map.put(childcode, "871");

					// System.out.println(">>>A+C群流脑结合疫苗1应种>>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("871");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getMenaASh();
							ff.get(k).setMenaASh(++a);
							break;
						}
					}
				} else {
					// System.out.println("不做统计了");
				}
			}
		}
	}
	////////////////////////////////////

	// A群流脑多糖1
	public void do_MPSVA_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("161");
		
		// 判断A群流脑多糖疫苗第1针是否有接种纪录
		if (t_map_1 != null) {
			// A群流脑多糖疫苗第1针已经有接种纪录
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种时间是否在当月

				// A群流脑多糖疫苗第1针次是应种针次
				Y_BD_MPSV_A1_Map.put(childcode, "161");
				S_BD_MPSV_A1_Map.put(childcode, "161");
				
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("161");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
					}
				}
				
				// System.out.println(">>>A群流脑多糖疫苗1应种实种 >>>");

			} else {
				// System.out.println(">>>A群流脑多糖疫苗1已经接种很久了>>>");
			}
		} else {
			
				
			if (vacc_yzrq.containsKey("161") && situation.equals("1") && officeInfo.equals(office)) {
				// A群流脑多糖疫苗1应种
				Y_BD_MPSV_A1_Map.put(childcode, "161");
				
				
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("161");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
					}
				}
				

				// System.out.println(">>>A群流脑多糖疫苗1应种>>>");
			} else {
				// System.out.println("不做统计了");
			}
				

			
		}

	}

	// A群流脑多糖2(未做统计)
	public void do_MPSVA_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("162");

		if (t_map_2 != null) {
			// A群流脑多糖疫苗第2针有接种记录

			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_MPSV_A2_Map.put(childcode, "162");
				S_BD_MPSV_A2_Map.put(childcode, "162");
				
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
				
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("162");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
					}
				}
				
				
				// System.out.println(">>>A群流脑多糖疫苗2应种实种 >>>");
			} else {
				// System.out.println(">>>A群流脑多糖疫苗2很早就接种了 >>>");

			}
		} else {
			
			Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("161");
			
				
			if (t_map_1 != null) {
				if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
						&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
					// 判断第1针接种时间是否在当月

					// A群流脑多糖疫苗第1针次是应种针次
					Y_BD_MPSV_A1_Map.put(childcode, "161");
					S_BD_MPSV_A1_Map.put(childcode, "161");
					
					
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("161");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
						}
					}
					
					// System.out.println(">>>A群流脑多糖疫苗1应种实种>>>");
				} else {
					if (vacc_yzrq.containsKey("162")  && situation.equals("1") && officeInfo.equals(office)) {
						if (Double.parseDouble((String) t_map_1.get("dif_mon")) > 3) {
							Y_BD_MPSV_A2_Map.put(childcode, "162");
							
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("162");
									expRoutinevacc61Detail.setDosage("2");
									expRoutinevacc61Detail.setType("1");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
								}
							}
							
							// System.out.println(">>>A群流脑多糖疫苗2应种>>>");
						} else {
							// System.out.println(">>>A群流脑多糖疫苗2未到间隔>>>");
						}
					} else {
						// System.out.println("不做统计了");
					}
				}
			} else {
				
					
				if (vacc_yzrq.containsKey("161")  && situation.equals("1") && officeInfo.equals(office)) {
					// A群流脑多糖疫苗1应种
					Y_BD_MPSV_A1_Map.put(childcode, "161");
					
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("161");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
						}
					}
					

					// System.out.println(">>>A群流脑多糖疫苗1应种>>>");
				} else {
					// System.out.println("不做统计了");
				}
					
				

			}
				
			
		}
	}

	//////////////////////////////

	// 麻风1
	public void do_MR_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("141");

		if (t_map_1 == null) {
			if (vacc_yzrq.containsKey("141")  && situation.equals("1") && officeInfo.equals(office)) {
				// 麻风
				Y_BD_MR1_Map.put(childcode, vacc_list.get("141"));

				// System.out.println("麻风1应种");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("141");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMrASh();
						ff.get(k).setMrASh(++a);
						break;
					}
				}
			} else {
				// System.out.println("不做统计了");
			}
		} else {

			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_MR1_Map.put(childcode, "141");
				S_BD_MR1_Map.put(childcode, "141");

				// System.out.println(">>>麻风疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("141");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMrARe();
						ff.get(k).setMrARe(++a);
						a = ff.get(k).getMrASh();
						ff.get(k).setMrASh(++a);
						break;
					}
				}

			}
		}
	}

	//////////////////

	// 乙脑减毒活疫苗1
	public void do_JEL_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("841");

		// 判断乙脑减毒活疫苗第1针是否有接种纪录
		if (t_map_1 != null) {
			// 乙脑减毒活疫苗第1针已经有接种纪录
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种时间是否在当月

				// 乙脑减毒活疫苗第1针次是应种针次
				Y_BD_JE_L1_Map.put(childcode, "841");
				S_BD_JE_L1_Map.put(childcode, "841");

				// System.out.println(">>>乙脑减毒活疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("841");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getJelARe();
						ff.get(k).setJelARe(++a);
						a = ff.get(k).getJelASh();
						ff.get(k).setJelASh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>乙脑减毒活疫苗1已经接种很久了>>>");
			}
		} else {
			
			if (vacc_yzrq.containsKey("841")  && situation.equals("1") && officeInfo.equals(office)) {
				// 乙脑减毒活疫苗1应种
				Y_BD_JE_L1_Map.put(childcode, "841");
				// System.out.println(">>>乙脑减毒活疫苗1应种>>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("841");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getJelASh();
						ff.get(k).setJelASh(++a);
						break;
					}
				}
			} else {
				// System.out.println("不做统计了");
			}
			
		}

	}

	// 乙脑减毒活疫苗2
	public void do_JEL_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("842");

		if (t_map_2 != null) {
			// 乙脑减毒活疫苗第2针有接种记录

			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_JE_L2_Map.put(childcode, "842");
				S_BD_JE_L2_Map.put(childcode, "842");
				// System.out.println(">>>乙脑减毒活疫苗2应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("842");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getJelBRe();
						ff.get(k).setJelBRe(++a);
						a = ff.get(k).getJelBSh();
						ff.get(k).setJelBSh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>乙脑减毒活疫苗2已经接种很久了>>>");
			}
		} else {
			
			Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("841");
			
				// 判断乙脑减毒活疫苗第1针是否有接种纪录
				if (t_map_1 != null) {
					// 乙脑减毒活疫苗第1针已经有接种纪录
					if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
							&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
						// 判断第1针接种时间是否在当月

						// 乙脑减毒活疫苗第1针次是应种针次
						Y_BD_JE_L1_Map.put(childcode, "841");
						S_BD_JE_L1_Map.put(childcode, "841");
						// System.out.println(">>>乙脑减毒活疫苗1应种实种 >>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("841");
								expRoutinevacc61Detail.setDosage("1");
								expRoutinevacc61Detail.setType("3");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getJelARe();
								ff.get(k).setJelARe(++a);
								a = ff.get(k).getJelASh();
								ff.get(k).setJelASh(++a);
								break;
							}
						}
					} else {
						if (vacc_yzrq.containsKey("842")  && situation.equals("1") && officeInfo.equals(office)) {
							if (Double.parseDouble((String) t_map_1.get("dif_mon")) > 12) {
								// 判断第1针接种时间是否在当月

								// 乙脑减毒活疫苗第2针次是应种
								Y_BD_JE_L2_Map.put(childcode, "842");
								// System.out.println(">>>乙脑减毒活疫苗2应种>>>");
								for (int k = 0; k < ff.size(); k++) {
									if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
										
										ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
										expRoutinevacc61Detail.setId(IdGen.uuid());
										expRoutinevacc61Detail.setYearMonth(createDate);
										expRoutinevacc61Detail.setChildcode(childcode);
										expRoutinevacc61Detail.setUnitCode(area);
										expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
										expRoutinevacc61Detail.setVaccCode("842");
										expRoutinevacc61Detail.setDosage("2");
										expRoutinevacc61Detail.setType("1");
										expRoutinevacc61Detail.setLocalCode(office);
										expRoutinevacc61Detail.setIsNewRecord(true);
										expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
										
										int a = ff.get(k).getJelBSh();
										ff.get(k).setJelBSh(++a);
										break;
									}
								}
							} else {
								// System.out.println(">>>乙脑减毒活疫苗2未到间隔>>>");
							}
						} else {
							// System.out.println("不做统计了");
						}
					}
				} else {
					
					if (vacc_yzrq.containsKey("841")  && situation.equals("1") && officeInfo.equals(office)) {
						// 乙脑减毒活疫苗1应种
						Y_BD_JE_L1_Map.put(childcode, "841");
						// System.out.println(">>>乙脑减毒活疫苗1应种>>>");
						for (int k = 0; k < ff.size(); k++) {
							if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("841");
								expRoutinevacc61Detail.setDosage("1");
								expRoutinevacc61Detail.setType("1");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								int a = ff.get(k).getJelASh();
								ff.get(k).setJelASh(++a);
								break;
							}
						}
					} else {
						// System.out.println("不做统计了");
					}
						
					
				}
			
		}
	}

	//////////////////

	// 乙脑灭活1_2（未校验替代）(未做统计)
	public void do_JEI_1_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("832");

		// 判断乙脑灭活疫苗第2针是否接种
		if (t_map_2 != null) {
			// 第2针乙脑灭活不为空
			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断第2针接种时间是否在当月

				// 乙脑灭活2针次是应种针次
				Y_BD_JE_I2_Map.put(childcode, "832");
				S_BD_JE_I2_Map.put(childcode, "832");
				
				ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
				expRoutinevacc61Detail.setId(IdGen.uuid());
				expRoutinevacc61Detail.setYearMonth(createDate);
				expRoutinevacc61Detail.setChildcode(childcode);
				expRoutinevacc61Detail.setUnitCode(area);
//				expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
				expRoutinevacc61Detail.setVaccCode("832");
				expRoutinevacc61Detail.setDosage("2");
				expRoutinevacc61Detail.setType("3");
				expRoutinevacc61Detail.setLocalCode(office);
				expRoutinevacc61Detail.setIsNewRecord(true);
				expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
				
				// System.out.println(">>>乙脑灭活疫苗2应种实种 >>>");
			} else {

				// System.out.println(">>>乙脑灭活疫苗2已经接种很久了 >>>");
			}

		} else {
			if (vacc_yzrq.containsKey("832")  && situation.equals("1") && officeInfo.equals(office)) {
				// 乙脑灭活2针次是应种针次
				Y_BD_JE_I2_Map.put(childcode, "832");
				
				ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
				expRoutinevacc61Detail.setId(IdGen.uuid());
				expRoutinevacc61Detail.setYearMonth(createDate);
				expRoutinevacc61Detail.setChildcode(childcode);
				expRoutinevacc61Detail.setUnitCode(area);
//				expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
				expRoutinevacc61Detail.setVaccCode("832");
				expRoutinevacc61Detail.setDosage("2");
				expRoutinevacc61Detail.setType("1");
				expRoutinevacc61Detail.setLocalCode(office);
				expRoutinevacc61Detail.setIsNewRecord(true);
				expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
				
				// System.out.println(">>>乙脑灭活疫苗2应种 >>>");
			} else {
				// System.out.println("不做统计了");
			}
		}
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("831");

		// 判断乙脑灭活疫苗第1 针是否接种
		if (t_map_1 != null) {
			// 第一针乙脑灭活不为空
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种是否在当月

				// 乙脑灭活1针次是应种针次
				Y_BD_JE_I1_Map.put(childcode, "831");
				S_BD_JE_I1_Map.put(childcode, "831");
				
				ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
				expRoutinevacc61Detail.setId(IdGen.uuid());
				expRoutinevacc61Detail.setYearMonth(createDate);
				expRoutinevacc61Detail.setChildcode(childcode);
				expRoutinevacc61Detail.setUnitCode(area);
//				expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
				expRoutinevacc61Detail.setVaccCode("831");
				expRoutinevacc61Detail.setDosage("1");
				expRoutinevacc61Detail.setType("3");
				expRoutinevacc61Detail.setLocalCode(office);
				expRoutinevacc61Detail.setIsNewRecord(true);
				expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
				
				// System.out.println(">>>乙脑灭活疫苗1应种实种>>>");
			} else {
				// System.out.println(">>>乙脑灭活疫苗1已经接种很久了 >>>");
			}
		} else {
			if (vacc_yzrq.containsKey("831")  && situation.equals("1") && officeInfo.equals(office)) {
				// 第一针乙脑灭活为空
				// 乙脑灭活第1针次和第二针次都是应种（因为两针一起接种）
				Y_BD_JE_I1_Map.put(childcode, "831");
				
				ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
				expRoutinevacc61Detail.setId(IdGen.uuid());
				expRoutinevacc61Detail.setYearMonth(createDate);
				expRoutinevacc61Detail.setChildcode(childcode);
				expRoutinevacc61Detail.setUnitCode(area);
//				expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
				expRoutinevacc61Detail.setVaccCode("831");
				expRoutinevacc61Detail.setDosage("1");
				expRoutinevacc61Detail.setType("1");
				expRoutinevacc61Detail.setLocalCode(office);
				expRoutinevacc61Detail.setIsNewRecord(true);
				expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
				
				// System.out.println(">>>乙脑灭活疫苗1应种 >>>");
			} else {
				// System.out.println("不做统计了");
			}
		}

	}

	// 乙脑灭活3（未校验替代）(未做统计)
	public void do_JEI_3( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_3 = (Map<String, String>) vacc_list.get("833");
		
		// 判断乙脑灭活第3针是否有接种纪录
		if (t_map_3 != null) {
			// 乙脑灭活第3针已经有接种纪录
			if (Double.parseDouble((String) t_map_3.get("dif_mon")) < 1
					&& t_map_3.get("office").equals(office) && !(t_map_3.get("source").equals("3"))) {
				// 判断第3针接种时间是否在当月

				// 乙脑灭活3针次是应种针次
				Y_BD_JE_I3_Map.put(childcode, "833");
				S_BD_JE_I3_Map.put(childcode, "833");
				
				ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
				expRoutinevacc61Detail.setId(IdGen.uuid());
				expRoutinevacc61Detail.setYearMonth(createDate);
				expRoutinevacc61Detail.setChildcode(childcode);
				expRoutinevacc61Detail.setUnitCode(area);
//				expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
				expRoutinevacc61Detail.setVaccCode("833");
				expRoutinevacc61Detail.setDosage("3");
				expRoutinevacc61Detail.setType("3");
				expRoutinevacc61Detail.setLocalCode(office);
				expRoutinevacc61Detail.setIsNewRecord(true);
				expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
				
				// System.out.println(">>>乙脑灭活疫苗3应种 实种>>>");
			} else {

				// System.out.println(">>>乙脑灭活疫苗3已经接种 很久了 >>>");
			}
		} else {
			
			Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("832");
			
			// 判断乙脑灭活疫苗第2针是否接种
			if (t_map_2 != null) {
				// 第2针乙脑灭活不为空
				if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
						&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
					// 判断第2针接种时间是否在当月

					// 乙脑灭活2针次是应种针次
					Y_BD_JE_I2_Map.put(childcode, "832");
					S_BD_JE_I2_Map.put(childcode, "832");
					
					ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
					expRoutinevacc61Detail.setId(IdGen.uuid());
					expRoutinevacc61Detail.setYearMonth(createDate);
					expRoutinevacc61Detail.setChildcode(childcode);
					expRoutinevacc61Detail.setUnitCode(area);
//					expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
					expRoutinevacc61Detail.setVaccCode("832");
					expRoutinevacc61Detail.setDosage("2");
					expRoutinevacc61Detail.setType("3");
					expRoutinevacc61Detail.setLocalCode(office);
					expRoutinevacc61Detail.setIsNewRecord(true);
					expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
					
					// System.out.println(">>>乙脑灭活疫苗2应种实种 >>>");
				} else {
					if (vacc_yzrq.containsKey("833")  && situation.equals("1") && officeInfo.equals(office)) {
						// 乙脑灭活3针次是应种针次
						Y_BD_JE_I3_Map.put(childcode, "833");
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
//						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("833");
						expRoutinevacc61Detail.setDosage("3");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);

						// System.out.println(">>>乙脑灭活疫苗3应种 >>>");
					} else {
						// System.out.println("不做统计了");
					}
				}

			} else {
				
				Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("831");
				
				// 判断乙脑灭活疫苗第1 针是否接种
				if (t_map_1 != null) {
					// 第一针乙脑灭活不为空
					if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
							&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
						// 判断第1针接种是否在当月

						// 乙脑灭活1针次是应种针次
						Y_BD_JE_I1_Map.put(childcode, "831");
						S_BD_JE_I1_Map.put(childcode, "831");
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
//						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("831");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						// System.out.println(">>>乙脑灭活疫苗1应种实种>>>");
					} else {
						if (vacc_yzrq.containsKey("832")  && situation.equals("1") && officeInfo.equals(office)) {

							// 乙脑灭活2针次是应种针次
							Y_BD_JE_I2_Map.put(childcode, "832");
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
//							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("832");
							expRoutinevacc61Detail.setDosage("2");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							// System.out.println(">>>乙脑灭活疫苗2应种 >>>");
						} else {
							// System.out.println("不做统计了");
						}
					}
				} else {
					if (vacc_yzrq.containsKey("831")  && situation.equals("1") && officeInfo.equals(office)) {
						// 第一针乙脑灭活为空
						// 乙脑灭活第1针次是应种
						Y_BD_JE_I1_Map.put(childcode, "831");
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
//						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("831");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						// System.out.println(">>>乙脑灭活疫苗1应种 >>>");
					} else {
						// System.out.println("不做统计了");
					}
				}
			}
		}

	}

	// 乙脑灭活4（未校验替代）(未做统计)
	public void do_JEI_4( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_4 = (Map<String, String>) vacc_list.get("834");

		if (t_map_4 != null) {
			// 乙脑灭活疫苗第4针有接种记录

			if (Double.parseDouble((String) t_map_4.get("dif_mon")) < 1
					&& t_map_4.get("office").equals(office) && !(t_map_4.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_JE_I4_Map.put(childcode, "834");
				S_BD_JE_I4_Map.put(childcode, "834");
				
				ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
				expRoutinevacc61Detail.setId(IdGen.uuid());
				expRoutinevacc61Detail.setYearMonth(createDate);
				expRoutinevacc61Detail.setChildcode(childcode);
				expRoutinevacc61Detail.setUnitCode(area);
//				expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
				expRoutinevacc61Detail.setVaccCode("834");
				expRoutinevacc61Detail.setDosage("4");
				expRoutinevacc61Detail.setType("3");
				expRoutinevacc61Detail.setLocalCode(office);
				expRoutinevacc61Detail.setIsNewRecord(true);
				expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
				
				// System.out.println(">>>乙脑灭活疫苗4应种实种 >>>");
			}

			/// 很早就接种了

		} else {
			
			Map<String, String> t_map_3 = (Map<String, String>) vacc_list.get("833");
			
			// 判断乙脑灭活第3针是否有接种纪录
			if (t_map_3 != null) {
				// 乙脑灭活第3针已经有接种纪录
				if (Double.parseDouble((String) t_map_3.get("dif_mon")) < 1
						&& t_map_3.get("office").equals(office) && !(t_map_3.get("source").equals("3"))) {
					// 判断第3针接种时间是否在当月

					// 乙脑灭活3针次是应种针次
					Y_BD_JE_I3_Map.put(childcode, "833");
					S_BD_JE_I3_Map.put(childcode, "833");
					
					ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
					expRoutinevacc61Detail.setId(IdGen.uuid());
					expRoutinevacc61Detail.setYearMonth(createDate);
					expRoutinevacc61Detail.setChildcode(childcode);
					expRoutinevacc61Detail.setUnitCode(area);
//					expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
					expRoutinevacc61Detail.setVaccCode("833");
					expRoutinevacc61Detail.setDosage("3");
					expRoutinevacc61Detail.setType("3");
					expRoutinevacc61Detail.setLocalCode(office);
					expRoutinevacc61Detail.setIsNewRecord(true);
					expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
					
					// System.out.println(">>>乙脑灭活疫苗3应种实种 >>>");
				} else {
					if (vacc_yzrq.containsKey("834")  && situation.equals("1") && officeInfo.equals(office)) {
						// 乙脑灭活第4针次是应种
						Y_BD_JE_I4_Map.put(childcode, "834");
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
//						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("834");
						expRoutinevacc61Detail.setDosage("4");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						// System.out.println(">>>乙脑灭活疫苗4应种>>>");
					} else {
						// System.out.println("不做统计了");
					}
				}
			} else {
				
				Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("832");
				
				// 判断乙脑灭活疫苗第2针是否接种
				if (t_map_2 != null) {
					// 第2针乙脑灭活不为空
					if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
							&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
						// 判断第2针接种时间是否在当月

						// 乙脑灭活2针次是应种针次
						Y_BD_JE_I2_Map.put(childcode, "832");
						S_BD_JE_I2_Map.put(childcode, "832");
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
//						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("832");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						// System.out.println(">>>乙脑灭活疫苗2应种实种 >>>");
					} else {
						if (vacc_yzrq.containsKey("833")  && situation.equals("1") && officeInfo.equals(office)) {
							// 乙脑灭活3针次是应种针次
							Y_BD_JE_I3_Map.put(childcode, "833");
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
//							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("833");
							expRoutinevacc61Detail.setDosage("3");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							// System.out.println(">>>乙脑灭活疫苗3应种 >>>");
						} else {
							// System.out.println("不做统计了");
						}
					}

				} else {
					
					Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("831");
					
					// 判断乙脑灭活疫苗第1 针是否接种
					if (t_map_1 != null) {
						// 第一针乙脑灭活不为空
						if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
								&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
							// 判断第1针接种是否在当月

							// 乙脑灭活1针次是应种针次
							Y_BD_JE_I1_Map.put(childcode, "831");
							S_BD_JE_I1_Map.put(childcode, "831");
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
//							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("831");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							// System.out.println(">>>乙脑灭活疫苗1应种实种>>>");
						} else {
							if (vacc_yzrq.containsKey("832")  && situation.equals("1") && officeInfo.equals(office)) {
								// 乙脑灭活2针次是应种针次
								Y_BD_JE_I2_Map.put(childcode, "832");
								
								ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
								expRoutinevacc61Detail.setId(IdGen.uuid());
								expRoutinevacc61Detail.setYearMonth(createDate);
								expRoutinevacc61Detail.setChildcode(childcode);
								expRoutinevacc61Detail.setUnitCode(area);
//								expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
								expRoutinevacc61Detail.setVaccCode("832");
								expRoutinevacc61Detail.setDosage("2");
								expRoutinevacc61Detail.setType("1");
								expRoutinevacc61Detail.setLocalCode(office);
								expRoutinevacc61Detail.setIsNewRecord(true);
								expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
								
								// System.out.println(">>>乙脑灭活疫苗2应种 >>>");
							} else {
								// System.out.println("不做统计了");
							}
						}
					} else {
						if (vacc_yzrq.containsKey("831")  && situation.equals("1") && officeInfo.equals(office)) {
							// 第一针乙脑灭活为空
							// 乙脑灭活第1针次是应种
							Y_BD_JE_I1_Map.put(childcode, "831");
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
//							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("831");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							// System.out.println(">>>乙脑灭活疫苗1应种 >>>");
						} else {
							// System.out.println("不做统计了");
						}
					}
				}
			}
		}
	}

	/////////////////////////////////////

	// 麻腮风
	public void do_MMR_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("121");
		
		//TODO:统计麻腮风疫苗接种时间在生成数据的月份内且当月月龄超过84岁的情况,加入应种实种

		if (t_map_1 == null) {
			if (vacc_yzrq.containsKey("121")  && situation.equals("1") && officeInfo.equals(office)) {
				// 麻腮风
				Y_BD_MMR1_Map.put(childcode, vacc_list.get("121"));

				// System.out.println("麻腮风应种");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("121");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMmrASh();
						ff.get(k).setMmrASh(++a);
						break;
					}
				}

			} else {
				// System.out.println("不做统计了");
			}
		} else {

			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_MMR1_Map.put(childcode, "121");
				S_BD_MMR1_Map.put(childcode, "121");

				// System.out.println(">>>麻腮风疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("121");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMmrARe();
						ff.get(k).setMmrARe(++a);
						a = ff.get(k).getMmrASh();
						ff.get(k).setMmrASh(++a);
						break;
					}
				}
			}
		}
	}
	
	
	/**
	 * 统计麻腮风接种月龄大于18个月份的情况,如果月龄大于18个月后接种了麻腮风第二针,则应种实种数都+1
	 * @author Jack
	 * @date 2018年2月2日 下午6:14:05
	 * @description 
	 * @param vacc_list
	 * @param childcode
	 * @param vacc_yzrq
	 * @param reside
	 * @param area
	 * @param createDate
	 * @param office
	 * @param situation
	 * @param officeInfo
	 *
	 */
	public void do_MMR_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("122");
		
		if (t_map_1 != null) {
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_MMR1_Map.put(childcode, "122");
				S_BD_MMR1_Map.put(childcode, "122");

				// System.out.println(">>>麻腮风疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("122");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMmrARe();
						ff.get(k).setMmrARe(++a);
						a = ff.get(k).getMmrASh();
						ff.get(k).setMmrASh(++a);
						break;
					}
				}
			}
		}
	}
	
	
	
	////////////////////////////////////

	// 甲肝减毒活疫苗
	public void do_HEPAL_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo) {
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("821");

		if (t_map_1 == null) {
			if (vacc_yzrq.containsKey("821")  && situation.equals("1") && officeInfo.equals(office)) {
				// 甲肝
				Y_BD_HepA_L1_Map.put(childcode, vacc_list.get("821"));
				
				// System.out.println("甲肝减毒活疫苗应种");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("821");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepalSh();
						ff.get(k).setHepalSh(++a);
						break;
					}
				}
			} else {
				// System.out.println("不做统计了");
			}
		} else {

			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_HepA_L1_Map.put(childcode, "821");
				S_BD_HepA_L1_Map.put(childcode, "821");
				// System.out.println(">>>甲肝减毒活疫苗应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("821");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepalRe();
						ff.get(k).setHepalRe(++a);
						a = ff.get(k).getHepalSh();
						ff.get(k).setHepalSh(++a);
						break;
					}
				}
			}
		}
	}

	////////////////////////////////////////////

	// 甲肝灭活疫苗1
	public void do_HEPAI_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("811");

		// 判断甲肝灭活疫苗第1针是否有接种纪录
		if (t_map_1 != null) {
			// 甲肝灭活疫苗第1针已经有接种纪录
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种时间是否在当月

				// 甲肝灭活疫苗第1针次是应种针次
				Y_BD_HepA_I1_Map.put(childcode, "811");
				S_BD_HepA_I1_Map.put(childcode, "811");
				// System.out.println(">>>甲肝灭活疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("811");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepaiARe();
						ff.get(k).setHepaiARe(++a);
						a = ff.get(k).getHepaiASh();
						ff.get(k).setHepaiASh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>甲肝灭活疫苗1已经接种很久了 >>>>>>>");
			}
		} else {
		
			if (vacc_yzrq.containsKey("811") && situation.equals("1") && officeInfo.equals(office)) {
				// 甲肝灭活疫苗1应种
				Y_BD_HepA_I1_Map.put(childcode, "811");

				// System.out.println(">>>甲肝灭活疫苗1应种>>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("811");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepaiASh();
						ff.get(k).setHepaiASh(++a);
						break;
					}
				}
			} else {
				// System.out.println("不做统计了");
			}
			
		}

	}

	// 甲肝灭活疫苗2
	public void do_HEPAI_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("812");

		if (t_map_2 != null) {
			// 甲肝灭活疫苗第2针有接种记录

			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_HepA_I2_Map.put(childcode, "812");
				S_BD_HepA_I2_Map.put(childcode, "812");

				// System.out.println(">>>甲肝灭活疫苗2应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("812");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getHepaiBRe();
						ff.get(k).setHepaiBRe(++a);
						a = ff.get(k).getHepaiBSh();
						ff.get(k).setHepaiBSh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>甲肝灭活疫苗2已经接种很久了 >>>>>>>");
			}
		} else {
			
			Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("811");
			
			// 判断甲肝灭活疫苗第1针是否有接种纪录
			if (t_map_1 != null) {
				// 甲肝灭活疫苗第1针已经有接种纪录
				if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
						&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
					// 判断第1针接种时间是否在当月

					// 甲肝灭活疫苗第1针次是应种针次
					Y_BD_HepA_I1_Map.put(childcode, "811");
					S_BD_HepA_I1_Map.put(childcode, "811");
					// System.out.println(">>>甲肝灭活疫苗1应种 实种>>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("811");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getHepaiARe();
							ff.get(k).setHepaiARe(++a);
							a = ff.get(k).getHepaiASh();
							ff.get(k).setHepaiASh(++a);
							break;
						}
					}
				} else {
					if (vacc_yzrq.containsKey("812") && situation.equals("1") && officeInfo.equals(office)) {
						if (Double.parseDouble((String) t_map_1.get("dif_mon")) > 6) {
							// 甲肝灭活疫苗第2针次是应种
							Y_BD_HepA_I2_Map.put(childcode, "812");

							// System.out.println(">>>甲肝灭活疫苗2应种>>>");
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("812");
									expRoutinevacc61Detail.setDosage("2");
									expRoutinevacc61Detail.setType("1");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
									int a = ff.get(k).getHepaiBSh();
									ff.get(k).setHepaiBSh(++a);
									break;
								}
							}
						} else {
							// System.out.println(">>>甲肝灭活疫苗2未到间隔>>>");
						}
					} else {
						// System.out.println("不做统计了");
					}
				}
			} else {
				
				if (vacc_yzrq.containsKey("811") && situation.equals("1") && officeInfo.equals(office)) {
					// 甲肝灭活疫苗1应种
					Y_BD_HepA_I1_Map.put(childcode, "811");

					// System.out.println(">>>甲肝灭活疫苗1应种>>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("811");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getHepaiASh();
							ff.get(k).setHepaiASh(++a);
							break;
						}
					}
				} else {
					// System.out.println("不做统计了");
				}
				
			}
		}
	}

	////////////////////////////////////

	// A+C群流脑多糖1
	public void do_MENAC_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("171");

		// 判断A+C群流脑多糖疫苗第1针是否有接种纪录
		if (t_map_1 != null) {
			// A+C群流脑多糖疫苗第1针已经有接种纪录
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断第1针接种时间是否在当月

				// A+C群流脑多糖疫苗第1针次是应种针次
				Y_BD_MenAC1_Map.put(childcode, "171");
				S_BD_MenAC1_Map.put(childcode, "171");

				// System.out.println(">>>A+C群流脑多糖疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("171");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMenacARe();
						ff.get(k).setMenacARe(++a);
						a = ff.get(k).getMenacASh();
						ff.get(k).setMenacASh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>A+C群流脑多糖疫苗1已经接种很久了>>>");
			}
		} else {
			
			if (vacc_yzrq.containsKey("171") && situation.equals("1") && officeInfo.equals(office)) {
				// A+C群流脑多糖疫苗1应种
				Y_BD_MenAC1_Map.put(childcode, "171");

				// System.out.println(">>>A+C群流脑多糖疫苗1应种>>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("171");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMenacASh();
						ff.get(k).setMenacASh(++a);
						break;
					}
				}
			} else {
				// System.out.println("不做统计了");
			}
			
		}

	}

	// A+C群流脑多糖2
	public void do_MENAC_2( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo) {
		
		Map<String, String> t_map_2 = (Map<String, String>) vacc_list.get("172");

		if (t_map_2 != null) {
			// A+C群流脑多糖疫苗第2针有接种记录

			if (Double.parseDouble((String) t_map_2.get("dif_mon")) < 1
					&& t_map_2.get("office").equals(office) && !(t_map_2.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_MenAC2_Map.put(childcode, "172");
				S_BD_MenAC2_Map.put(childcode, "172");

				// System.out.println(">>>A+C群流脑多糖疫苗2应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("172");
						expRoutinevacc61Detail.setDosage("2");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getMenacBRe();
						ff.get(k).setMenacBRe(++a);
						a = ff.get(k).getMenacBSh();
						ff.get(k).setMenacBSh(++a);
						break;
					}
				}
			} else {
				// System.out.println(">>>A+C群流脑多糖疫苗2已经接种很久了>>>");
			}
		} else {
			
			Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("171");
			
			// 判断A+C群流脑多糖疫苗第1针是否有接种纪录
			if (t_map_1 != null) {
				// A+C群流脑多糖疫苗第1针已经有接种纪录
				if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
						&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
					// 判断第1针接种时间是否在当月

					// A+C群流脑多糖疫苗第1针次是应种针次
					Y_BD_MenAC1_Map.put(childcode, "171");
					S_BD_MenAC1_Map.put(childcode, "171");

					// System.out.println(">>>A+C群流脑多糖疫苗1应种 实种>>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("171");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("3");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getMenacARe();
							ff.get(k).setMenacARe(++a);
							a = ff.get(k).getMenacASh();
							ff.get(k).setMenacASh(++a);
							break;
						}
					}
				} else {
					if (vacc_yzrq.containsKey("172") && situation.equals("1") && officeInfo.equals(office)) {
						if (Double.parseDouble((String) t_map_1.get("dif_mon")) > 36) {
							// A+C群流脑多糖疫苗第2针次是应种
							Y_BD_MenAC2_Map.put(childcode, "172");

							// System.out.println(">>>A+C群流脑多糖疫苗2应种>>>");
							for (int k = 0; k < ff.size(); k++) {
								if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
									
									ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
									expRoutinevacc61Detail.setId(IdGen.uuid());
									expRoutinevacc61Detail.setYearMonth(createDate);
									expRoutinevacc61Detail.setChildcode(childcode);
									expRoutinevacc61Detail.setUnitCode(area);
									expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
									expRoutinevacc61Detail.setVaccCode("172");
									expRoutinevacc61Detail.setDosage("2");
									expRoutinevacc61Detail.setType("1");
									expRoutinevacc61Detail.setLocalCode(office);
									expRoutinevacc61Detail.setIsNewRecord(true);
									expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
									
									int a = ff.get(k).getMenacBSh();
									ff.get(k).setMenacBSh(++a);
									break;
								}
							}
						} else {
							// System.out.println(">>>A+C群流脑多糖疫苗2未到间隔>>>");
						}
					} else {
						// System.out.println("不做统计了");
					}
				}
			} else {
				
				if (vacc_yzrq.containsKey("171") && situation.equals("1") && officeInfo.equals(office)) {
					// A+C群流脑多糖疫苗1应种
					Y_BD_MenAC1_Map.put(childcode, "171");

					// System.out.println(">>>A+C群流脑多糖疫苗1应种>>>");
					for (int k = 0; k < ff.size(); k++) {
						if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
							
							ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
							expRoutinevacc61Detail.setId(IdGen.uuid());
							expRoutinevacc61Detail.setYearMonth(createDate);
							expRoutinevacc61Detail.setChildcode(childcode);
							expRoutinevacc61Detail.setUnitCode(area);
							expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
							expRoutinevacc61Detail.setVaccCode("171");
							expRoutinevacc61Detail.setDosage("1");
							expRoutinevacc61Detail.setType("1");
							expRoutinevacc61Detail.setLocalCode(office);
							expRoutinevacc61Detail.setIsNewRecord(true);
							expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
							
							int a = ff.get(k).getMenacASh();
							ff.get(k).setMenacASh(++a);
							break;
						}
					}
				} else {
					// System.out.println("不做统计了");
				}
				
			}
		}
	}

	//////////////////////////////////////
	
	// 白破1
	public void do_DT_1( Map vacc_list ,String childcode,Map vacc_yzrq,String reside,String area, String createDate, String office, String situation, String officeInfo){
		
		Map<String, String> t_map_1 = (Map<String, String>) vacc_list.get("061");
		
		if (t_map_1 == null) {
			if (vacc_yzrq.containsKey("061") && situation.equals("1") && officeInfo.equals(office)) {
				// 白破
				Y_BD_DT1_Map.put(childcode, vacc_list.get("061"));
				
				// System.out.println("白破疫苗1应种");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("061");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("1");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getDtSh();
						ff.get(k).setDtSh(++a);
						break;
					}
				}
			} else {
				// System.out.println("不做统计了");
			}
		} else {
			if (Double.parseDouble((String) t_map_1.get("dif_mon")) < 1
					&& t_map_1.get("office").equals(office) && !(t_map_1.get("source").equals("3"))) {
				// 判断接种时间是否在当月
				Y_BD_DT1_Map.put(childcode, "061");
				S_BD_DT1_Map.put(childcode, "061");

				// System.out.println(">>>白破疫苗1应种实种 >>>");
				for (int k = 0; k < ff.size(); k++) {
					if (ff.get(k).getReside().equals(reside) && ff.get(k).getUnitCode().equals(area)) {
						
						ExpRoutinevacc61Detail expRoutinevacc61Detail = new ExpRoutinevacc61Detail();
						expRoutinevacc61Detail.setId(IdGen.uuid());
						expRoutinevacc61Detail.setYearMonth(createDate);
						expRoutinevacc61Detail.setChildcode(childcode);
						expRoutinevacc61Detail.setUnitCode(area);
						expRoutinevacc61Detail.setUnitName(ff.get(k).getUnitName());
						expRoutinevacc61Detail.setVaccCode("061");
						expRoutinevacc61Detail.setDosage("1");
						expRoutinevacc61Detail.setType("3");
						expRoutinevacc61Detail.setLocalCode(office);
						expRoutinevacc61Detail.setIsNewRecord(true);
						expRoutinevacc61DetailService.save(expRoutinevacc61Detail);
						
						int a = ff.get(k).getDtRe();
						ff.get(k).setDtRe(++a);
						a = ff.get(k).getDtSh();
						ff.get(k).setDtSh(++a);
						break;
					}
				}
			}
		}

	}
	////////////////////////////////


	public ExpRoutinevacc6_1 get(String id) {
		return super.get(id);
	}
	
//	@Transactional(readOnly = false)
	public void save(ExpRoutinevacc6_1 expRoutinevacc6_1) {
		super.save(expRoutinevacc6_1);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExpRoutinevacc6_1 expRoutinevacc6_1) {
		super.delete(expRoutinevacc6_1);
	}
	
	public void inster(ExpRoutinevacc6_1 expRoutinevacc6_1) {
		super.insert(expRoutinevacc6_1);
	}
	
	
	
	// ------------------------------------------------------------------------------------------
	// -------------------------------常规免疫6-1开始-------------------------------------------//
	// ------------------------------------------------------------------------------------------

	// --------------------------------------常规免疫6-1map数据处理开始------------------------------------------//
	/**
	 * 常规免疫接种 情况汇总表6-1 查询数据 =>按居住类型
	 * 
	 * @author Jack
	 * @date 2017年9月20日 上午11:15:48
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccData61_unit(HashMap<String, String> map) {
		return dao.selectVaccData61_unit(map);
	}

	/**
	 * 常规免疫接种 情况汇总表6-1 查询数据 =>按社区 s分居住类型
	 * 
	 * @author Jack
	 * @date 2017年9月20日 下午2:22:51
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccData61_unitAndReside(HashMap<String, String> map) {
		return dao.selectVaccData61_unitAndReside(map);
	}

	/**
	 * 常规免疫接种 情况汇总表6-1 查询数据 =>按社区 不分居住类型
	 * 
	 * @author Jack
	 * @date 2017年9月20日 下午2:24:45
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccData61_unitNoReside(HashMap<String, String> map) {
		return dao.selectVaccData61_unitNoReside(map);
	}
	
	/**
	 * 常规免疫接种情况汇总表6-1 特殊类型疫苗统计
	 * @author Jack
	 * @date 2017年10月25日 下午7:07:24
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccSpecData61_unitNoReside(HashMap<String,String> map){
		return dao.selectVaccSpecData61_unitNoReside(map);
	}
	
	/**
	 * 常规免疫接种 情况汇总表6-1 查询数据 总合计
	 * 
	 * @author Jack
	 * @date 2017年9月20日 下午2:26:15
	 * @param map
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectVaccData61_all(HashMap<String, String> map) {
		return dao.selectVaccData61_all(map);
	}

	// --------------------------------------常规免疫6-1map数据处理结束------------------------------------------//

	/**
	 * 查询Reside
	 * 
	 * @author Jack
	 * @date 2017年10月13日 上午9:56:37
	 * @param list_reside
	 * @return
	 *
	 */
	public List<ExpRoutinevacc6_1> selectResideList() {
		return dao.selectResideList();
	}

	/**
	 * 查询Community
	 * 
	 * @author Jack
	 * @date 2017年10月13日 上午9:57:48
	 * @param list_community
	 * @return
	 *
	 */
	public List<ExpRoutinevacc6_1> selectCommunityList(String officeStr) {
		return dao.selectCommunityList(officeStr);
	}
	
	/**
	 * 查询互联网版本社区信息
	 * @author Jack
	 * @date 2018年2月9日 下午12:33:33
	 * @description 
	 * @return
	 *
	 */
	public List<HashMap<String, String>> selectCommunityListNetVersion() {
		return dao.selectCommunityListNetVersion();
	}

	/**
	 * 查询7岁内全部记录
	 * 
	 * @author Jack
	 * @date 2017年10月13日 上午10:31:51
	 * @param sql_total
	 * @return
	 *
	 */
	public List<ExpRoutinevacc6_1> selectTotalList() {
		return dao.selectTotalList();
	}

	/**
	 * 查询7岁内全部记录，并拼接community、reside
	 * 
	 * @author Jack
	 * @date 2017年10月17日 下午4:28:11
	 * @return
	 *
	 */
	public List<Map<String, String>> selectSqlQuery(Integer monthNum, String officeStr) {
		return dao.selectSqlQuery(monthNum, officeStr);
		
	}
	
	public List<Map<String, String>> selectSqlList(String id, Integer monthNum){
		return dao.selectSqlList(id, monthNum);
	}
	
	//乙肝卡介特殊处理
	public List<Map<String, String>> selectSqlListSpecial(String id, Integer monthNum1,Integer monthNum2){
		return dao.selectSqlListSpecial(id, monthNum1,monthNum2);
	}
	
	private List<Map<String, String>> selectSqlYzrq(String id, Integer monthNum){
		return dao.selectSqlYzrq(id, monthNum);
	}
	
	/**
	 * 根据点击选中的社区、疫苗名称、查询应种数据
	 * @author Jack
	 * @date 2018年2月1日 下午5:18:17
	 * @description 
	 * @param community
	 * @param vaccName
	 * @param type
	 * @return
	 *
	 */
	public List<Map<String, String>> queryDetailInfo(String community, String vaccNum, String type, String startTime, String endTime, Integer startRowNum, Integer endRowNum){
		return dao.queryDetailInfo(community, vaccNum, type, startTime, endTime, startRowNum, endRowNum);
	}
	
	/**
	 * 根据点击选中的社区、疫苗名称、查询应种数据总数
	 * @author Jack
	 * @date 2018年2月5日 下午4:02:07
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public Integer queryDetailInfoCount(String community, String vaccNum, String type, String startTime, String endTime){
		return dao.queryDetailInfoCount(community, vaccNum, type, startTime, endTime);
	}
	
	/**
	 * 获取按社区划分的所有应种儿童个案信息
	 * @author Jack
	 * @date 2018年2月5日 上午11:34:54
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public List<Map<String, String>> queryShouldDetailInfo(String community, String vaccNum, String startTime, String endTime, Integer startRowNum, Integer endRowNum){
		return dao.queryShouldDetailInfo(community, vaccNum, startTime, endTime, startRowNum, endRowNum);
	}
	
	/**
	 * 获取按社区划分的所有应种儿童个案信息总数
	 * @author Jack
	 * @date 2018年2月5日 下午4:12:48
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param startTime
	 * @param endTime
	 * @param startRowNum
	 * @param endRowNum
	 * @return
	 *
	 */
	public Integer queryShouldDetailInfoCount(String community, String vaccNum, String startTime, String endTime){
		return dao.queryShouldDetailInfoCount(community, vaccNum, startTime, endTime);
	}
	
	/**
	 * 获取最后接种活疫苗的接种时间
	 * @author Jack
	 * @date 2018年2月3日 上午10:49:36
	 * @description 
	 * @param childId
	 * @return
	 *
	 */
	public Date getLastLiveVaccDate(String childId){
		return dao.getLastLiveVaccDate(childId);
		
	}
	
	/**
	 * 根据儿童Id查询该儿童接种最后一针的时间
	 * @author Jack
	 * @date 2018年2月3日 上午11:11:22
	 * @description 
	 * @return
	 *
	 */
	public Date getLastVaccDate(String childId){
		return dao.getLastVaccDate(childId);
	}
	
	/**
	 * 按社区统计超过三个月仍然应种未种的儿童个案
	 * @author Jack
	 * @date 2018年2月5日 下午3:13:02
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @param timeStr
	 * @param startRowNum
	 * @param endRowNum
	 * @return
	 *
	 */
	public List<Map<String, String>> getyzwzData(String community, String vaccNum, String type, String timeStr, int startRowNum, int endRowNum){
		return dao.getyzwzData(community, vaccNum, type, timeStr, startRowNum, endRowNum);
	}
	
	/**
	 * 按社区统计超过三个月仍然应种未种的儿童个案记录总数
	 * @author Jack
	 * @date 2018年2月5日 下午3:14:17
	 * @description 
	 * @param community
	 * @param vaccNum
	 * @param type
	 * @param timeStr
	 * @return
	 *
	 */
	public int getyzwzDataCount(String community, String vaccNum, String type, String timeStr){
		return dao.getyzwzDataCount(community, vaccNum, type, timeStr);
	}
	
	/**
	 * 根据传入的年月、localCode判断该条件下生成的数据总条数根据传入的年月、localCode判断该条件下生成的数据总条数
	 * @author Jack
	 * @date 2018年2月9日 下午1:19:54
	 * @description 
	 * @param yearMonth 年月字符串
	 * @param localCode 本地编码
	 * @return
	 */
	public int countDataByMonthAndLocalCode(String yearMonth, String localCode){
		return dao.countDataByMonthAndLocalCode(yearMonth, localCode);
	}

	// ------------------------------------------------------------------------------------------
	// -----------------------------------常规免疫6-1结束-------------------------------------------//
	// ------------------------------------------------------------------------------------------
	
	
	
	
	// ------------------------------------------------------------------------------------------
	// ------------------------------------国家常规免疫6-1开始---------------------------------------//
	// ------------------------------------------------------------------------------------------
	
	/**
	 * 处理国家免疫规划疫苗常规接种情况报表(表6-1)数据
	 * @author Jack
	 * @date 2017年10月19日 上午9:36:07
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	public List<List<HashMap<String, String>>> manageNationalIPVRoutineVaccReport6_1(HashMap<String, String> map) {
		List<HashMap<String, String>> datalist = null;
		if(!map.get("reside").equals("0")){
			datalist = nationalRoutineVaccWithReside6_1(map);
		}else{
			datalist = nationalRoutineVaccNoReside6_1(map);
		}
		List<List<HashMap<String, String>>> returnlist = new ArrayList<List<HashMap<String, String>>>();
		List<HashMap<String, String>> tempList = new ArrayList<HashMap<String, String>>();
		if(datalist !=null && datalist.size()>0){
			for(int i=1;(i<=datalist.size()/20);i++){
				tempList.addAll(datalist.subList((i-1)*20, i*20));
				returnlist.add(tempList);
				tempList = new ArrayList<HashMap<String, String>>();
			}
			tempList.addAll(datalist.subList(datalist.size()-datalist.size()%20, datalist.size()));
			returnlist.add(tempList);
			tempList = new ArrayList<HashMap<String, String>>();
		}else{
			return new ArrayList<List<HashMap<String, String>>>();
		}
		return returnlist;
	}
	
	/**
	 * 查询乙肝疫苗、卡介苗、脊灰疫苗、百白破疫苗、白破疫苗、麻风、麻腮风、A+C群流脑结合疫苗、A+C群流脑多糖疫苗、甲肝减毒活疫苗数据
	 * @author Jack
	 * @date 2017年11月20日 下午8:34:27
	 * @description 
	 * @param map 居住属性、开始时间、结束时间
	 * @return
	 *
	 */
	public List<HashMap<String, String>> getExistLocalDataList(HashMap<String, String> map){
		return dao.getExistLocalDataList(map);
	}
	
	public List<HashMap<String, String>> getExistFlowDataList(HashMap<String, String> map){
		return dao.getExistFlowDataList(map);
	}
	
	/**
	 * 国家免疫规划疫苗常规接种情况报表(表6-1)
	 * @author Jack
	 * @date 2017年10月19日 上午9:45:29
	 * @description 
	 * @param map
	 * @return
	 *
	 */
	private List<HashMap<String, String>> nationalRoutineVaccWithReside6_1(HashMap<String, String> map) {
		return dao.nationalRoutineVaccWithReside6_1(map);
	}
	
	private List<HashMap<String, String>> nationalRoutineVaccNoReside6_1(HashMap<String, String> map) {
		return dao.nationalRoutineVaccNoReside6_1(map);
	}
	
	/**
	 * 统计月龄超过84,但是接种了某疫苗的所有记录数(计入该疫苗的应种和实种)
	 * @author Jack
	 * @date 2018年1月27日 下午1:41:55
	 * @description 
	 * @param vaccID 疫苗ID
	 * @param monthNum1 月份间隔
	 * @param monthNum2 月份间隔+1
	 * @return
	 *
	 */
	private Integer get_yuefendayu84_Num(String nid, Integer monthNum, String reside, String area){
		return dao.get_yuefendayu84_Num(nid, monthNum, reside, area);
	}
	
	// ------------------------------------------------------------------------------------------
	// ------------------------------------国家常规免疫6-1结束---------------------------------------//
	// ------------------------------------------------------------------------------------------
	
	//------------------------------------重新生成6-1数据开始------------------------------------//
	
	/**
	 * 清空指定条件下的6_1和6_1Detail 表数据
	 * @author Jack
	 * @date 2018年3月5日 上午10:14:20
	 * @description 
	 * @param localCode
	 * @param year
	 * @param startMonth
	 * @param endMonth
	 *
	 */
	public void clearExist61Data(String localCode, int year, int startMonth, int endMonth){
		String startTime = "";
		String endTime = "";
		if(year != 0 && startMonth != 0){
			startTime = DateUtils.getFirstDayOfMonth(year, startMonth-1) + " 00:00:00";
		}
		if(year != 0 && endMonth != 0){
			endTime = DateUtils.getLastDayOfMonth(year, endMonth-1) + " 23:59:59";
		}
		
		//计算指定年的某个月份范围内EXP_ROUTINEVACC_6_1表数据总条数
		int data61Num = getDataCount61ByTimeRange(localCode, startTime, endTime);
		
		if(data61Num > 0){
			//清空EXP_ROUTINEVACC_6_1指定数据
			clearDataCount61ByTimeRange(localCode, startTime, endTime);
		}
		
		for(int i=startMonth; i<=endMonth; i++){
			String yearMonth = "";
			if(i<10){
				yearMonth = year + "-0"  + i;
			}else{
				yearMonth = year + "-"  + i;
			}
			//计算指定年的某个月份范围内EXP_ROUTINEVACC_6_1_DETAIL表数据总条数
			int data61DetailNum = getDataCount61DetailByTimeRange(localCode, yearMonth);
			if(data61DetailNum > 0){
				//清空EXP_ROUTINEVACC_6_1_DETAIL指定数据
				clearDataCount61DetailByTimeRange(localCode, yearMonth);
			}
		}
	}
	/**
	 * 重新结算某年指定月份范围内6-1数据
	 * @author Jack
	 * @date 2018年3月3日 下午3:04:45
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public String reCount61Data(String localCode, int year, int startMonth, int endMonth){
		String startTime = "";
		String endTime = "";
		if(year != 0 && startMonth != 0){
			startTime = DateUtils.getFirstDayOfMonth(year, startMonth-1) + " 00:00:00";
		}
		if(year != 0 && endMonth != 0){
			endTime = DateUtils.getLastDayOfMonth(year, endMonth-1) + " 23:59:59";
		}
		
		int data61NumAfterClear = getDataCount61ByTimeRange(localCode, startTime, endTime);
		
		int totalDetailNumAfterClear = 0;
		for(int i=startMonth; i<=endMonth; i++){
			String yearMonth = year + "-"  + i;
			//计算指定年的某个月份范围内EXP_ROUTINEVACC_6_1_DETAIL表数据总条数
			totalDetailNumAfterClear += getDataCount61DetailByTimeRange(localCode, yearMonth);
			
		}
		
		if(data61NumAfterClear==0 && totalDetailNumAfterClear==0){
			try {
				for(int i=startMonth; i<=endMonth; i++){
					//distinct查询SYS_COMMUNITY表中的所有LOCALCODE,遍历执行所有站点数据生成
					List<HashMap<String, String>> communityList = selectCommunityListNetVersion();
					
					for(HashMap<String, String> map : communityList){
						String localCode_ = map.get("LOCALCODE");
					
						getDataByMonth(String.valueOf(year), String.valueOf(i), localCode_);
					}
				}
			} catch (Exception e) {
				logger.info("注意：重新生成错误");
				return "注意：重新生成错误";
			}
			logger.info("数据已重新生成");
			return "数据已重新生成";
		}else{
			//告知用户数据清空失败无法重新生成
			logger.info("注意:数据清空失败,暂时无法重新生成");
			return "注意:数据清空失败,暂时无法重新生成";
		}
		
	}
	
	/**
	 * 计算指定年的某个月份范围内EXP_ROUTINEVACC_6_1表数据总条数
	 * @author Jack
	 * @date 2018年3月3日 下午3:26:51
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public int getDataCount61ByTimeRange(String localCode, String startTime, String endTime){
		return dao.getDataCount61ByTimeRange(localCode, startTime, endTime);
	}
	
	/**
	 * 计算指定年的某个月份范围内EXP_ROUTINEVACC_6_1_Detail表数据总条数
	 * @author Jack
	 * @date 2018年3月3日 下午3:35:53
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public int getDataCount61DetailByTimeRange(String localCode, String yearMonth){
		return dao.getDataCount61DetailByTimeRange(localCode, yearMonth);
	}
	
	/**
	 * 清空指定年的某个月份范围内EXP_ROUTINEVACC_6_1表数据项
	 * @author Jack
	 * @date 2018年3月3日 下午3:39:12
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 *
	 */
	public void clearDataCount61ByTimeRange(String localCode, String startTime, String endTime){
		dao.clearDataCount61ByTimeRange(localCode, startTime, endTime);
	}
	
	/**
	 * 清空指定年的某个月份范围内EXP_ROUTINEVACC_6_1_Detail表数据项
	 * @author Jack
	 * @date 2018年3月3日 下午3:38:40
	 * @description 
	 * @param localCode
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public void clearDataCount61DetailByTimeRange(String localCode, String yearMonth){
		dao.clearDataCount61DetailByTimeRange(localCode, yearMonth);
	}
	
	//------------------------------------重新生成6-1数据结束------------------------------------//
	
}
