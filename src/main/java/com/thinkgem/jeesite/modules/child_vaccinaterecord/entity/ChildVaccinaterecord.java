/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;

/**
 * 疫苗接种管理Entity
 * 
 * @author 王德地
 * @version 2017-02-07
 */
public class ChildVaccinaterecord extends DataEntity<ChildVaccinaterecord> {

	/** 状态-未接种 */
	public static final String STATUS_NOT = "0";
	/** 状态-已接种 */
	public static final String STATUS_YET = "1";
	/** 状态-逻辑删除 */
	public static final String STATUS_DEL = "9";
	/** 状态-拆解 */
	public static final String STATUS_DISMISS = "8";
	/** 保险-未缴纳 */
	public static final String INSURANCE_NOT = "0";
	/** 保险-已缴纳 */
	public static final String INSURANCE_YES = "1";
	
	/** 接种类型-注射 */
	public static final String INTYPE_INJECT = "1";
	/** 接种类型-口服 */
	public static final String INTYPE_TAKEN = "2";
	
	/** 付款状态-未付款 */
	public static final String PAY_STATUS_NO = "0";
	/** 付款状态-已付款 */
	public static final String PAY_STATUS_YES = "1";
	/** 保险状态-未付款 */
	public static final String INSURANCE_STATUS_NO = "0";
	/** 保险状态-已付款 */
	public static final String INSURANCE_STATUS_YES = "1";
	/** 接种类型-常规 */
	public static final String VACCTYPE_STATUS_YES = "1";
	
	/** 排号来源-登记台 */
	public final static String SOURCE_DJT = "0";
	/** 排号来源-微信 */
	public final static String SOURCE_WX = "1";
	/** 排号来源-一体机 */
	public final static String SOURCE_YTJ = "2";
	/** 排号来源-补录 */
	public final static String SOURCE_BL = "3";
	/** 排号来源-五联拆解 */
	public final static String SOURCE_CJ = "4";
	
	/** 有无异常反应-无 */
	public final static String ISEFFECT_NO = "0";
	
	/** 签字-有 */
	public final static String SIGNATURE_YES = "1";
	/** 签字-无 */
	public final static String SIGNATURE_NO = "0";
	
	/** 签字来源 - 一体机 */
	public final static String SIGNATURE_SOURCE_SELF = "0";
	/** 签字来源 - 接种台 */
	public final static String SIGNATURE_SOURCE_QUEUE = "1";
	/** 签字来源 - 微信 */
	public final static String SIGNATURE_SOURCE_WX = "2";
	/** 签字来源 - 登记台 */
	public final static String SIGNATURE_SOURCE_DJT = "3";
	
	/**接种类型-常规*/
	public final static String VACCTYPE_NORMAL = "1";
	/**接种类型-群体*/
	public final static String VACCTYPE_COLONY = "2";
	/**接种类型-应急*/
	public final static String VACCTYPE_EMERGENCY = "3";
	/**接种类型-其他*/
	public final static String VACCTYPE_OTHER = "4";
	/**接种类型-加强*/
	public final static String VACCTYPE_PRO = "5";
	
	
	private static final long serialVersionUID = 1L;
	private String childid; // 儿童ID
	private String vaccineid; // 疫苗ID
	private String dosage; // 疫苗计次
	private Date vaccinatedate; // 接种日期
	private String bodypart; // 接种部位
	private String batch; // 疫苗批号
	private String office; // 接种单位
	private String doctor; // 医生姓名
	private Date createdate; // 创建时间
	private double price; // 疫苗价格
	private String status; // 接种状态0未完成1已完成9删除
	/** 查询条件-开始时间 */
	private Date beginTime;
	/** 查询条件-结束时间 */
	private Date endTime;
	private String childcode; // 儿童编码
	private String isforeign;		// 是否进口 Y国产 N进口
	private String name;		// 疫苗名称
	private String childname; // 儿童姓名
	private Date birthday; // 儿童生日
	private String gender; // 儿童性别
	private String dose; //剂次名称
	private String parentsMoblie; // 家长电话
	private String parentsName; // 家长姓名
	private String childRemarks; // 过敏史
	private String childAbnormalReaction; // 是否异常反应
	private String manufacturer;// 疫苗的制造厂商
	private String manufacturercode;// 疫苗的制造厂商编码
	private String mouage;
	private String productid;// 对应产品ID
	private String vaccCode;		// 疫苗 大类名称
	private String vaccName;		// 疫苗小类名称
	private Long vaccMonage;		// 应当接种月龄
	private Long vaccWeighted;		// 疫苗权重
	private int size;//集合中同种疫苗的个数
	private String nid;//NUM表ID
	private String sign;//是否出生当天接种，0不是1是
	private String source;//数据来源
	private String orderNO;//订单号
	private String insurance = INSURANCE_NOT;//保险0没买1买了
	private byte[] signatureList;//签字
	private byte[] signatureData;//签字
	private String signature;//签字
	private String signList;//签字
	private String stype;//签字来源  0一体机  1电脑
	private String vacctype;//接种类型
	private String bigcode;//疫苗大类编码
	
	private String iseffect; //是否异常反应0无1有
	private String intype; //是否口服或针剂
	private String payStatus = PAY_STATUS_NO;//付款状态
	private int leng = 0; //接种记录接口使用长度参数
	private String open;//查询时是否去掉补录的记录开关。
	private String openSource;//查询时是否去掉无用的记录开关。
	private BsManageVaccinenum vaccnum;
	private BsManageVaccine vaccine;
	private Date vaccExpDate;
	
	private String inocUnionCode;	//替代关系
	private String inocUnionRecord;	//替代来源记录
	
	private String vacselected; //接种台统计使用
	private String orderBy; //排序用
	private String sourceIn;	//多选数据来源
	private String queneWhere;	//接种台需要的筛选条件
	private String vaccineidlike;	//根据大类模糊查询用
	private String nidEq;
	
	private Date nextTime;	//下一针提醒时间
	private String nextVacc;	//下一针
	private String nextVaccGroup;	//下一针
	
	private String sessionId;
	Date vaccinatedateAfter;	//接种时间晚于，预约管理列表使用
	Date vaccinatedateBefore;	//接种时间早于，预约管理列表使用
	private Map<String, String> dicts;
	
	private String vaccNameBatch; // 接种疫苗及第几剂次
	private String fever; // 发热
	private String cough; // 咳嗽
	private String diarrhea; // 腹泻
	private String symptom; // 其他症状
	private String disease; // 近期是否患病
	
	private String createById; // 接种医生id
	private String vaccinatedateStr;
	
	private double originalPrice;		// 原价
	private double currentPrice;		// 现价
	private String fundStatus;		// 调价标识
	
	
	public Date getBeginTime() {
		return beginTime;
	}
	
	public ChildVaccinaterecord() {
		super();
	}

	public ChildVaccinaterecord(String id) {
		super(id);
	}
	
	public ChildVaccinaterecord(String childid, String vaccineid, String dosage,
			Date vaccinatedate, String bodypart, String batch, String office,
			String doctor, Date createdate, double price) {
		super();
		this.childid = childid;
		this.vaccineid = vaccineid;
		this.dosage = dosage;
		this.vaccinatedate = vaccinatedate;
		this.bodypart = bodypart;
		this.batch = batch;
		this.office = office;
		this.doctor = doctor;
		this.createdate = createdate;
		this.price = price;
	}

	public String getChildid() {
		return childid;
	}

	public void setChildid(String childid) {
		this.childid = childid;
	}

	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	@ExcelField(align=2, title="针次", sort = 40)
	public String getDosage() {
		return dosage;
	}
	
	public int getDosageInt(){
		int returnValue = 0;
		if(StringUtils.isNotBlank(dosage)){
			try {
				returnValue = Integer.parseInt(dosage);
			} catch (Exception e) {
				System.out.println("===[getDosageInt]获取接种该记录针次失败===");
			}
		}
		return returnValue;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	@ExcelField(align=2, title="接种时间", sort = 50 )
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getVaccinatedate() {
		return vaccinatedate;
	}

	public void setVaccinatedate(Date vaccinatedate) {
		this.vaccinatedate = vaccinatedate;
	}
	@ExcelField(align=2, title="接种部位", sort = 60,dictType="position" )
	@Length(min = 0, max = 20, message = "接种部位长度必须介于 0 和 20 之间")
	public String getBodypart() {
		if(bodypart != null){
			return bodypart.trim();
		}
		return bodypart;
	}
	
	public String getBodypartLabel() {
		if(bodypart != null){
			return DictUtils.getDictLabel(bodypart.trim(),"position" , "");
		}
		return "";
	}

	public void setBodypart(String bodypart) {
		if(bodypart != null){
			bodypart = bodypart.trim();
		}
		this.bodypart = bodypart;
	}
	@ExcelField(align=2, title="疫苗批号", sort = 70 )
	@Length(min = 0, max = 20, message = "疫苗批号长度必须介于 0 和 20 之间")
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	@ExcelField(align=2, title="接种单位", sort = 80 )
	@Length(min = 0, max = 50, message = "接种单位长度必须介于 0 和 50 之间")
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	@ExcelField(align=2, title="医生姓名", sort = 90 )
	@Length(min = 0, max = 20, message = "医生姓名长度必须介于 0 和 20 之间")
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@ExcelField(align=2, title="疫苗价格", sort = 100)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	@Length(min = 1, max = 1, message = "接种状态长度必须为1")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	// begin
	public String getBegin() {
		if(null != this.beginTime){
			return DateUtils.formatDateTime(this.beginTime);
		}
		return "";
	}
	
	public String getBeginShort() {
		if(null != this.beginTime){
			return DateUtils.formatDate(this.beginTime);
		}
		return "";
	}

	// end
	public String getEnd() {
		if(null != this.endTime){
			return DateUtils.formatDateTime(this.endTime);
		}
		return "";
	}
	
	public String getEndShort() {
		if(null != this.endTime){
			return DateUtils.formatDate(this.endTime);
		}
		return "";
	}
	@ExcelField(align=2, title="儿童编号", sort = 20 )
	public String getChildcode() {
		return childcode;
	}

	public void setChildcode(String childcode) {
		this.childcode = childcode;
	}
	
	public String getIsforeign() {
		return isforeign;
	}

	public void setIsforeign(String isforeign) {
		this.isforeign = isforeign;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@ExcelField(align=2, title="儿童名称", sort = 10 )
 public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}

public static ChildVaccinaterecord addRecord(int i,long childid,Date vaccinatedate){
	 ChildVaccinaterecord  childVaccinaterecord=new ChildVaccinaterecord();
	if (i==1) {
		childVaccinaterecord.setChildid(childid+"");
		childVaccinaterecord.setVaccineid("11");
		childVaccinaterecord.setDosage("1");
		childVaccinaterecord.setVaccinatedate(vaccinatedate);
		childVaccinaterecord.setBodypart("右肩");
		childVaccinaterecord.setStatus("1");
		
	}else{
		childVaccinaterecord.setChildid(childid+"");
		childVaccinaterecord.setVaccineid("12");
		childVaccinaterecord.setDosage("1");
		childVaccinaterecord.setVaccinatedate(vaccinatedate);
		childVaccinaterecord.setBodypart("左肩");
		childVaccinaterecord.setStatus("1");
	}
	
	return childVaccinaterecord;
	
	}
	
	public String getDose() {
		return dose;
	}
	
	public void setDose(String dose) {
		this.dose = dose;
	}
	@ExcelField(align=2, title="疫苗厂家", sort = 35 )
	public String getManufacturer() {
		if(null==manufacturer){
			return "";	
			}
		return manufacturer;
	}
	
	public void setManufacturer(String manufacturer) {
		
		this.manufacturer = manufacturer;
	}
	
	public String getMouage() {
		return mouage;
	}
	
	public void setMouage(String mouage) {
		this.mouage = mouage;
	}
	
	public String getProductid() {
		return productid;
	}
	
	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	public String getVaccCode() {
		return vaccCode;
	}
	
	public void setVaccCode(String vaccCode) {
		this.vaccCode = vaccCode;
	}
	@ExcelField(align=2, title="疫苗名称", sort = 30 )
	public String getVaccName() {
		return vaccName;
	}
	
	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}
	
	public Long getVaccMonage() {
		return vaccMonage;
	}
	
	public void setVaccMonage(Long vaccMonage) {
		this.vaccMonage = vaccMonage;
	}
	
	public Long getVaccWeighted() {
		return vaccWeighted;
	}
	
	public void setVaccWeighted(Long vaccWeighted) {
		this.vaccWeighted = vaccWeighted;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public String getNid() {
		return nid;
	}
	
	public void setNid(String nid) {
		this.nid = nid;
	}
	
	public String getSign() {
		if(StringUtils.isBlank(sign)){
			return "0";
		}
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getManufacturercode() {
		return manufacturercode;
	}
	
	public void setManufacturercode(String manufacturercode) {
		this.manufacturercode = manufacturercode;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getInsurance() {
		return insurance;
	}
	
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	
	public byte[] getSignatureList() {
		return signatureList;
	}

	public void setSignatureList(byte[] signatureList) {
		this.signatureList = signatureList;
	}

	public String getSignature() {
		if(StringUtils.isBlank(signature)){
			signature="0";
		}
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getIntype() {
		if(!StringUtils.isNotBlank(intype)){
			return "";
		}
		return intype;
	}
	
	public void setIntype(String intype) {
		this.intype = intype;
	}
	
	public String getPayStatus() {
		return payStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public int getLeng() {
		return leng;
	}
	
	public void setLeng(int leng) {
		this.leng = leng;
	}
	
	/**
	 * 接种类型
	 * @author fuxin
	 * @date 2018年1月11日 下午2:09:39
	 * @description 
	 *		TODO VACCTYPE_NORMAL
	 * @return 
	 *
	 */
	public String getVacctype() {
		return vacctype;
	}
	
	/**
	 * 获取接种类型，用于拼接nid
	 * @author fuxin
	 * @date 2017年10月4日 下午10:03:16
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String getNidVacctype() {
		if(StringUtils.isBlank(vacctype) || VACCTYPE_NORMAL.equals(vacctype)){
			return "";
		}
		return vacctype;
	}
	
	public void setVacctype(String vacctype) {
		this.vacctype = vacctype;
	}
	
	
	/**
	 * 更新产品信息相关信息
	 * @author fuxin
	 * @date 2017年4月17日 下午7:55:38
	 * @description 
	 *		TODO
	 *
	 */
	public void updateProduct(BsManageProduct product) {
		if(null != product){
			this.batch = product.getBatchno();
			this.vaccineid = product.getVaccineid();
			this.vaccName = product.getVaccName();
			this.manufacturer = product.getManufacturer();
			this.manufacturercode = product.getCode();
			this.price = product.getSellprice();
			this.productid = product.getId();
			this.vaccExpDate = product.getVaccExpDate();
		}
	}

	public String getIseffect() {
		return iseffect;
	}

	public void setIseffect(String iseffect) {
		this.iseffect = iseffect;
	}

	public BsManageVaccinenum getVaccnum() {
		return vaccnum;
	}

	public void setVaccnum(BsManageVaccinenum vaccnum) {
		this.vaccnum = vaccnum;
	}

	public String getBigcode() {
		return bigcode;
	}

	public void setBigcode(String bigcode) {
		this.bigcode = bigcode;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getOpenSource() {
		return openSource;
	}

	public void setOpenSource(String openSource) {
		this.openSource = openSource;
	}

	public String getVacselected() {
		return vacselected;
	}

	public void setVacselected(String vacselected) {
		this.vacselected = vacselected;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSourceIn() {
		return sourceIn;
	}

	public void setSourceIn(String sourceIn) {
		this.sourceIn = sourceIn;
	}

	public String getQueneWhere() {
		return queneWhere;
	}

	public void setQueneWhere(String queneWhere) {
		this.queneWhere = queneWhere;
	}

	public String getInocUnionCode() {
		return inocUnionCode;
	}

	public void setInocUnionCode(String inocUnionCode) {
		this.inocUnionCode = inocUnionCode;
	}

	public BsManageVaccine getVaccine() {
		return vaccine;
	}

	public void setVaccine(BsManageVaccine vaccine) {
		this.vaccine = vaccine;
	}

	public String getVaccineidlike() {
		return vaccineidlike;
	}

	public void setVaccineidlike(String vaccineidlike) {
		this.vaccineidlike = vaccineidlike;
	}

	public Date getNextTime() {
		return nextTime;
	}

	public String getNextVacc() {
		return nextVacc;
	}
	
	
	/**
	 * 获取页面传过来疫苗
	 * @author fuxin
	 * @date 2017年8月27日 下午3:15:43
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<String> getNextVaccs() {
		List<String> vaccs = new ArrayList<String>();
		if(StringUtils.isNotBlank(nextVacc)){
			String[] vac = nextVacc.split(",");
			for(String v : vac){
				vaccs.add(v);
			}
		}
		return vaccs;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public void setNextVacc(String nextVacc) {
		this.nextVacc = nextVacc;
	}

	public String getNextVaccGroup() {
		return nextVaccGroup;
	}
	
	public List<String> getNextVaccGroups() {
		List<String> vaccs = new ArrayList<String>();
		if(StringUtils.isNotBlank(nextVaccGroup)){
			String[] vac = nextVaccGroup.split(",");
			for(String v : vac){
				vaccs.add(v);
			}
		}
		return vaccs;
	}

	public void setNextVaccGroup(String nextVaccGroup) {
		this.nextVaccGroup = nextVaccGroup;
	}

	public byte[] getSignatureData() {
		return signatureData;
	}

	public void setSignatureData(byte[] signatureData) {
		this.signatureData = signatureData;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getInocUnionRecord() {
		return inocUnionRecord;
	}

	public void setInocUnionRecord(String inocUnionRecord) {
		this.inocUnionRecord = inocUnionRecord;
	}

	public String getNidEq() {
		return nidEq;
	}

	public void setNidEq(String nidEq) {
		this.nidEq = nidEq;
	}
	
	public Date getVaccinatedateAfter() {
		return vaccinatedateAfter;
	}

	public void setVaccinatedateAfter(Date vaccinatedateAfter) {
		this.vaccinatedateAfter = vaccinatedateAfter;
	}

	public Date getVaccinatedateBefore() {
		return vaccinatedateBefore;
	}

	public void setVaccinatedateBefore(Date vaccinatedateBefore) {
		this.vaccinatedateBefore = vaccinatedateBefore;
	}
	
	public Map<String, String> getDicts() {
		return dicts;
	}

	public void setDicts(Map<String, String> dicts) {
		this.dicts = dicts;
	}

	public String getParentsMoblie() {
		return parentsMoblie;
	}

	public void setParentsMoblie(String parentsMoblie) {
		this.parentsMoblie = parentsMoblie;
	}

	public String getParentsName() {
		return parentsName;
	}

	public void setParentsName(String parentsName) {
		this.parentsName = parentsName;
	}

	public String getChildRemarks() {
		if(StringUtils.isBlank(childRemarks)){
			return "无";
		}
		return childRemarks;
	}

	public void setChildRemarks(String childRemarks) {
		this.childRemarks = childRemarks;
	}

	public String getChildAbnormalReaction() {
		if(StringUtils.isBlank(childAbnormalReaction)){
			return "无";
		}
		return childAbnormalReaction;
	}

	public void setChildAbnormalReaction(String childAbnormalReaction) {
		this.childAbnormalReaction = childAbnormalReaction;
	}

	public String getSignList() {
		return signList;
	}

	public void setSignList(String signList) {
		this.signList = signList;
	}

	public String getFever() {
		if(StringUtils.isBlank(fever)){
			return "无";
		}
		return fever;
	}

	public void setFever(String fever) {
		this.fever = fever;
	}

	public String getCough() {
		if(StringUtils.isBlank(cough)){
			return "无";
		}
		return cough;
	}

	public void setCough(String cough) {
		this.cough = cough;
	}

	public String getDiarrhea() {
		if(StringUtils.isBlank(diarrhea)){
			return "无";
		}
		return diarrhea;
	}

	public void setDiarrhea(String diarrhea) {
		this.diarrhea = diarrhea;
	}

	public String getDisease() {
		if(StringUtils.isBlank(disease)){
			return "无";
		}
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getSymptom() {
		if(StringUtils.isBlank(symptom)){
			return "无";
		}
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public String getVaccNameBatch() {
		return vaccName + "-- 第"+ getDosage() + "剂次";
	}

	public void setVaccNameBatch(String vaccNameBatch) {
		this.vaccNameBatch = vaccNameBatch;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getVaccinatedateStr() {
		if(vaccinatedate != null){
			vaccinatedateStr = DateUtils.formatDate(vaccinatedate, "yyyyMMdd");
		}
		return vaccinatedateStr;
	}

	public void setVaccinatedateStr(String vaccinatedateStr) {
		this.vaccinatedateStr = vaccinatedateStr;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getVaccExpDate() {
		return vaccExpDate;
	}

	public void setVaccExpDate(Date vaccExpDate) {
		this.vaccExpDate = vaccExpDate;
	}

	/**
	 * 获取nid前两位
	 * @author fuxin
	 * @date 2018年1月14日 下午11:26:55
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String getNidPre() {
		if(StringUtils.isNotBlank(nid)){
			return nid.substring(0, 2);
		}
		return StringUtils.EMPTY;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getFundStatus() {
		return fundStatus;
	}

	public void setFundStatus(String fundStatus) {
		this.fundStatus = fundStatus;
	}
	
}