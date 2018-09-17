/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_baseinfo.entity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;

/**
 * 逾期未种查询vo
 * @author Jack
 * @date 2018年2月8日 上午10:22:16
 */
public class ExpChildBaseInfoVo extends DataEntity<ChildBaseinfo> {
	
	@Autowired
	SysCommunityService sysCommunityService;
	@Autowired
	DictService dictService;
	@Autowired
	Dict dict;


	private static final long serialVersionUID = 1L;

	/** 现管理单位-本地 */
	public static final String OFFICEINFO_LOCAL  = "0";
	/** 现管理单位-全部 */
	public static final String OFFICEINFO_ALL  = "1";
	
	/** 居住属性-reside*/
	public static final String DICT_RESIDE = "reside";
	/** 居住属性-value本地*/
	public static final String RESIDE_LOCAL = "1";
	/** 居住属性-value流动*/
	public static final String RESIDE_LIUDONG = "2";
	/** 居住属性-value临时*/
	public static final String RESIDE_LINSHI = "3";
	
	/** 在册情况*/
	public static final String DICT_SITUATION = "situation";
	/** 在册情况-在册*/
	public static final String SITUATION_ZAICE = "1";
	/** 在册情况-离册*/
	public static final String SITUATION_LICE = "2";
	/** 在册情况-空挂户*/
	public static final String SITUATION_KONGGUAHU = "9";
	/** 在册情况-死亡*/
	public static final String SITUATION_SIWANG = "4";
	/** 在册情况-服务器删除*/
	public static final String SITUATION_FUWUQISHANCHU = "3";
	
	private int ovmonth;	// 未种月限
	private String officeinfo; // 现管理单位
	private Date birthbeginTime;// 生日(起)
	private Date birthendTime; // 生日(止)
	private String[] resides; // 居住类别
	private String[] situations; // 在册类别
	private String[] areas; // 区域划分
	private List<String> vaccinates; // 疫苗名称
	private boolean checkEx;	//排除近期已接种

	public ExpChildBaseInfoVo() {
		super();
	}

	public ExpChildBaseInfoVo(String id) {
		super(id);
	}
	
	/** 未种月限 */
	public int getOvmonth() {
		return ovmonth;
	}

	/** 未种月限 */
	public void setOvmonth(int ovmonth) {
		this.ovmonth = ovmonth;
	}

	/** 现管理单位 */
	public String getOfficeinfo() {
		return officeinfo;
	}

	/** 现管理单位 */
	public void setOfficeinfo(String officeinfo) {
		this.officeinfo = officeinfo;
	}

	/** 生日(起) */
	public Date getBirthbeginTime() {
		return birthbeginTime;
	}

	/** 生日(起) */
	public void setBirthbeginTime(Date birthbeginTime) {
		this.birthbeginTime = birthbeginTime;
	}

	/** 生日(止) */
	public Date getBirthendTime() {
		return birthendTime;
	}

	/** 生日(止) */
	public void setBirthendTime(Date birthendTime) {
		this.birthendTime = birthendTime;
	}

	/** 居住类别 */
	public String[] getResides() {
		return resides;
	}

	/** 居住类别 */
	public void setResides(String[] resides) {
		this.resides = resides;
	}

	/** 在册类别 */
	public String[] getSituations() {
		return situations;
	}

	/** 在册类别 */
	public void setSituations(String[] situations) {
		this.situations = situations;
	}

	/** 区域划分 */
	public String[] getAreas() {
		return areas;
	}

	/** 区域划分 */
	public void setAreas(String[] areas) {
		this.areas = areas;
	}

	/** 疫苗名称 */
	public List<String> getVaccinates() {
		return vaccinates;
	}

	/** 疫苗名称 */
	public void setVaccinates(List<String> vaccinates) {
		this.vaccinates = vaccinates;
	}

	/** 排除近期已接种 */
	public boolean isCheckEx() {
		return checkEx;
	}

	/** 排除近期已接种 */
	public void setCheckEx(boolean checkEx) {
		this.checkEx = checkEx;
	} 
	
	
	/**
	 * 设置默认值
	 * @author Jack
	 * @date 2018年2月8日 上午11:00:37
	 * @description 
	 * @return
	 *
	 */
	public ExpChildBaseInfoVo setDefaultValue(){
		if(this.ovmonth == 0){
			this.ovmonth = 3;
		}
		if(StringUtils.isBlank(this.officeinfo)){
			this.officeinfo = OFFICEINFO_LOCAL;
		}
		if(this.birthbeginTime == null){
			try {
				this.birthbeginTime = DateUtils.parseDate((Integer.valueOf(DateUtils.getYear())-1) + "-" + DateUtils.getMonth() + "-" + DateUtils.getDay() , "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(this.birthendTime == null){
			this.birthendTime = DateUtils.parseDate(DateUtils.getDate());
		}
		if(this.resides == null){
		/*	this.resides = new String[5];
			List<HashMap<String, String>> situationList = dictService.getDictData(DICT_SITUATION);
			for(HashMap<String, String> map : situationList){
				for(String key : map.keySet()){
					if(SITUATION_ZAICE.equals(key)){
						this.resides[0] = map.get(key);
					}
					if(SITUATION_LICE.equals(key)){
						this.resides[1] = map.get(key);
					}
					if(SITUATION_KONGGUAHU.equals(key)){
						this.resides[2] = map.get(key);
					}
					if(SITUATION_SIWANG.equals(key)){
						this.resides[3] = map.get(key);
					}
					if(SITUATION_FUWUQISHANCHU.equals(key)){
						this.resides[4] = map.get(key);
					}
				}
			}*/
		}
		
		if(this.situations == null){
			/*this.situations = new String[3];
			DictService dictService = new DictService();
			List<Dict> list = dictService.findList(dict);
			for(Dict dict : list){
				if(dict.getType().equals(DICT_RESIDE) && dict.getValue().equals(RESIDE_LOCAL)){
					this.situations[0] = dict.getLabel();
				}
				if(dict.getType().equals(DICT_RESIDE) && dict.getValue().equals(RESIDE_LIUDONG)){
					this.situations[1] = dict.getLabel();
				}
				if(dict.getType().equals(DICT_RESIDE) && dict.getValue().equals(RESIDE_LINSHI)){
					this.situations[2] = dict.getLabel();
				}
			}*/
			
			/*List<HashMap<String, String>> resideList = dictService.getDictData(DICT_RESIDE);
			for(HashMap<String, String> map : resideList){
				for(String key : map.keySet()){
					if(RESIDE_LOCAL.equals(key)){
						this.situations[0] = map.get(key);
					}
					if(RESIDE_LIUDONG.equals(key)){
						this.situations[1] = map.get(key);
					}
					if(RESIDE_LINSHI.equals(key)){
						this.situations[2] = map.get(key);
					}
					
				}
			}*/
		}
		
		if(this.areas == null){
			/*List<SysCommunity> areas = sysCommunityService.findList(new SysCommunity());
			this.areas = new String[1];
			this.areas[0] = areas.get(0).getCode();*/
		}
		
		if(this.vaccinates == null || this.vaccinates.size() == 0){
			
		}
		return this;
	}
	
	/**
	 * 设置生日起止时间
	 * @author Jack
	 * @date 2018年2月26日 下午3:38:47
	 * @description 
	 * @return
	 *
	 */
	public boolean resetTime(){
		try {
			if(this.birthbeginTime == null || this.birthendTime == null){
				return false;
			}
			this.birthbeginTime = DateUtils.getDay(this.birthbeginTime);
			this.birthendTime = DateUtils.getDayEnd(this.birthendTime);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 应种统计设置选中疫苗为卡介苗
	 * @author Jack
	 * @date 2018年2月26日 下午4:12:18
	 * @description 
	 *
	 */
	public ExpChildBaseInfoVo setVaccBCG(){
		if(this.vaccinates == null || this.vaccinates.size() == 0){
			this.vaccinates = new ArrayList<String>();
			this.vaccinates.add("01");
		}
		return this;
	}
	
	/**
	 * 
	 * @author Jack
	 * @date 2018年2月26日 下午4:31:14
	 * @description 
	 * @return
	 *
	 */
	public ExpChildBaseInfoVo setVaccAll(){
		if(this.vaccinates == null || this.vaccinates.size() == 0){
			this.vaccinates = new ArrayList<String>();
			this.vaccinates.add("55");
			this.vaccinates.add("16");
			this.vaccinates.add("14");
			this.vaccinates.add("18");
			this.vaccinates.add("03");
			this.vaccinates.add("01");
			this.vaccinates.add("13");
			this.vaccinates.add("06");
			this.vaccinates.add("17");
			this.vaccinates.add("11");
			this.vaccinates.add("19");
			this.vaccinates.add("04");
			this.vaccinates.add("07");
			this.vaccinates.add("10");
			this.vaccinates.add("12");
			this.vaccinates.add("02");
			this.vaccinates.add("09");
		}
		return this;
	}
}