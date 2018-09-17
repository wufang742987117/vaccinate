/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 疫苗信息Entity
 * @author wangdedi
 * @version 2017-02-20
 */
public class BsManageVaccine extends DataEntity<BsManageVaccine> {
	
	public static final String VACC_DOGE_GNUM = "28";
	public static final String VACC_HIB_GNUM = "23";
	public static final String LIVE_YES = "1";
	public static final String LIVE_NO = "0";	
	public static final String EXCEP_YES = "1";
	public static final String EXCEP_NO = "0";
	public static final Long STATUS_NORMAL = 1L;
	
	private static final long serialVersionUID = 1L;
	private String name;		// 疫苗名称
	private String pathema;		// 可预防疾病名称
	private String ban;		// 接种的禁忌事项
	private String code;		// 疫苗编号
	private String price;		// 价格
	private String manufacturer;// 疫苗的制造厂商
	private String excep;// 出生当天接种0，
	private String gNum;// 疫苗大类code，
	private String gName;// 疫苗大类名字，
	private String codeAll;		// code_all
	private String gCode;		// g_code
	private String nameAll;		// name_all
	private String nid;		// nid
	private String pid;		// pid
	private Long status;		// 是否可用
	private String type;
	private int sort;
	private String stock;//库存 接种台作显示用
	
	private String maxpin; //所有针次数，仅作一体机接口使用 
	private String vacctype;//疫苗类型，区分儿童疫苗和成人疫苗 1：儿童，2：成人
	private String mNum;//模型大类
	private String mName;//模型大类
	
	private String inType;	//是否活苗
	private String live;	//是否活苗
	private String rule;	//特殊规则
	private String impart;	//告知书回执
	
	private String officeCode;	//所属科室
	
	private String inGroups;	//根据大类查询相关小类	in
	
	private List<String> vaccSum;  //疫苗类型编号集合
	private List<BsManageVaccine> subList;
	
	public BsManageVaccine() {
		super();
	}

	public BsManageVaccine(String id){
		super(id);
	}

	@Length(min=0, max=20, message="疫苗名称长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="可预防疾病名称长度必须介于 0 和 50 之间")
	public String getPathema() {
		return pathema;
	}

	public void setPathema(String pathema) {
		this.pathema = pathema;
	}
	
	@Length(min=0, max=200, message="接种的禁忌事项长度必须介于 0 和 200 之间")
	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}
	
	@Length(min=0, max=100, message="疫苗编号长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * 库存-仅作接种台显示用
	 * @author fuxin
	 * @date 2017年3月14日 下午6:44:33
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String getStock() {
		return stock;
	}

	/**
	 * 库存-仅作接种台显示用
	 * @author fuxin
	 * @date 2017年3月14日 下午6:44:33
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getExcep() {
		return excep;
	}

	public void setExcep(String excep) {
		this.excep = excep;
	}

	public String getgNum() {
		return gNum;
	}

	public void setgNum(String gNum) {
		this.gNum = gNum;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getCodeAll() {
		return codeAll;
	}

	public void setCodeAll(String codeAll) {
		this.codeAll = codeAll;
	}

	public String getgCode() {
		return gCode;
	}

	public void setgCode(String gCode) {
		this.gCode = gCode;
	}

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getMaxpin() {
		return maxpin;
	}

	public void setMaxpin(String maxpin) {
		this.maxpin = maxpin;
	}

	public String getVacctype() {
		return vacctype;
	}

	public void setVacctype(String vacctype) {
		this.vacctype = vacctype;
	}

	public String getmNum() {
		return mNum;
	}

	public void setmNum(String mNum) {
		this.mNum = mNum;
	}

	public String getLive() {
		return live;
	}

	public String getRule() {
		return rule;
	}

	public void setLive(String live) {
		this.live = live;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getImpart() {
		return impart;
	}

	public void setImpart(String impart) {
		this.impart = impart;
	}

	public String getInGroups() {
		return inGroups;
	}

	public void setInGroups(String inGroups) {
		this.inGroups = inGroups;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public List<String> getVaccSum() {
		return vaccSum;
	}

	public void setVaccSum(List<String> vaccSum) {
		this.vaccSum = vaccSum;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public String getOfficeCode(){
		if(StringUtils.isNotBlank(officeCode)){
			return officeCode;
		}
		return UserUtils.getUser().getOffice().getCode();
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public List<BsManageVaccine> getSubList() {
		return subList;
	}

	public void setSubList(List<BsManageVaccine> subList) {
		this.subList = subList;
	}

	
	
}