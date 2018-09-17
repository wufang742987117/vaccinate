/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;

/**
 * 疫苗信息Entity
 * @author 王德地
 * @version 2017-02-20
 */
public class BsManageProduct extends DataEntity<BsManageProduct> {
	
	/** 免疫蛋白存在*/
	public static String VCC_ARROR = "1";
	/** 免疫蛋白不存在 */
	public static String VCC_BEFOR = "0";
	/** 免疫蛋白存在*/
	public static String IS_SHOW_YES = "Y";
	/** 免疫蛋白不存在 */
	public static String IS_SHOW_NO = "N";
	
	/** 疫苗类型-一类 */
	public static final String TYPE1 = "1";
	/** 疫苗类型-二类 */
	public static final String TYPE2 = "2";
	/** 疫苗分科室--全部显示 */
	public static final String SHOWALL_YES = "1";
	/** 疫苗分科室--分科室显示 */
	public static final String SHOWALL_NO = "0";
	
	private static final long serialVersionUID = 1L;
	private String vaccineid;		// 疫苗id
	private String batchno;		// 批次编号
	private Long dosage;		// 每盒剂次
	private String manufacturer;		// 疫苗的制造厂商
	private String isforeign;		//疫苗类型：  1一类 2二类
	private Long storenum;		// 库存
	private double sellprice;		// 出售价格
	private String isshow;		// 是否显示 Y显示 N不显示
	private String name;		// 疫苗名称
	private String vaccName;		// 疫苗小类名称
	private Date vaccExpDate;		// 有效期
	private String vacsiteno;    //疫苗站点编号
	private String codeall;		// 疫苗英文名称
	private String nid;		// num表ID
	private String code;		// 疫苗厂家编码
	private String context;		// 疫苗接种告知书
	private String insurance;  //保险
	private String bs;  //标识是否查询库存大于零的数据
	private String specification;  //规格
	private String bigcode;  //疫苗大类编码
	private int spec; 	//剂量(剂/(支、粒))
	private String fromId;
	private String epiId;
	
	private String gname; 	//疫苗大类名
	private int countAll;	//接种数量(盘点用)
	private int countUse;	//使用数量(盘点用)
	private double costprice;//成本价
	private int rest;//多剂次剩余剂次
	private int applicableMin;	//适用最小年龄
	private int applicableMax;	//适用最大年龄
	private int obligate;	//预留库存
	
	private boolean storenumIsNull;	//默认false，查询所有，当设定为true，只查询有库存的数据
	private String orderBy;
	private String batchnoLike;
	
	private String appid;		// app标识
	private String secret;		// app秘钥
	private String loginIp;		// 最后请求ip
	private String siteName;		// 站点名
	private String expiresSec;		// token默认超时
	private String token;		// token令牌
	private String officeCode;	//所属科室
	private String officeCodeDb;	//所属科室
	private String showAll = SHOWALL_NO;	//是否显示所有库存
	private String[] vaccineidIn;
	private boolean exceptQuene; 	//是否扣除已排号库存 true-扣除 ，false-不扣除
	
	
	public Double getCostprice() {
		return costprice;
	}

	public void setCostprice(Double costprice) {
		this.costprice = costprice;
	}

	private BsManageVaccine vaccinate;
	
	
	public BsManageProduct() {
		super();
	}

	public BsManageProduct(String id){
		super(id);
	}

	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	
	@Length(min=0, max=20, message="批次编号长度必须介于 0 和 20 之间")
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	public Long getDosage() {
		return dosage;
	}

	public void setDosage(Long dosage) {
		this.dosage = dosage;
	}
	
	@Length(min=0, max=50, message="疫苗的制造厂商长度必须介于 0 和 50 之间")
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@Length(min=0, max=1, message="是否进口 Y国产 N进口长度必须介于 0 和 1 之间")
	public String getIsforeign() {
		return isforeign;
	}

	public void setIsforeign(String isforeign) {
		this.isforeign = isforeign;
	}
	
	public Long getStorenum() {
		return storenum;
	}

	public void setStorenum(Long storenum) {
		this.storenum = storenum;
	}
	

	
	public double getSellprice() {
		return sellprice;
	}

	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}

	@Length(min=0, max=1, message="是否显示 Y显示 N不显示长度必须介于 0 和 1 之间")
	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 站点疫苗编号 获取配置文件
	 * @author fuxin
	 * @date 2017年3月11日 上午10:45:41
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String getVacsiteno() {
		return vacsiteno;
//		String no = Global.getConfig("vacsiteno");
//		return StringUtils.isNotBlank(no) ? no : "";
	}

	public void setVacsiteno(String vacsiteno) {
		this.vacsiteno = vacsiteno;
	}

	public String getVaccName() {
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}

	public Date getVaccExpDate() {
		return vaccExpDate;
	}

	public void setVaccExpDate(Date vaccExpDate) {
		this.vaccExpDate = vaccExpDate;
	}

	public String getCodeall() {
		return codeall;
	}

	public void setCodeall(String codeall) {
		this.codeall = codeall;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContext() {
		if(StringUtils.isBlank(context)){
			return "";
			
		}
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getBs() {
		return bs;
	}

	public void setBs(String bs) {
		this.bs = bs;
	}
	
	/**
	 *  根据产品获取大类code
	 * @author fuxin
	 * @date 2017年4月17日 上午11:37:02
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String getgnum(){
		if(StringUtils.isNotBlank(this.vaccineid) && this.vaccineid.length() > 2){
			return this.vaccineid.substring(0, 2);
		}else{
			return null;
		}
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getSpecificationname() {
		if(null !=  this.specification){
			return DictUtils.getDictLabel(this.specification, "specification", "");
		}
		return "";
	}

	public String getBigcode() {
		return bigcode;
	}

	public void setBigcode(String bigcode) {
		this.bigcode = bigcode;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public int getSpec() {
		return spec;
	}

	public void setSpec(int spec) {
		this.spec = spec;
	}


	public int getCountAll() {
		return countAll;
	}

	public int getCountUse() {
		return countUse;
	}

	public void setCountAll(int countAll) {
		this.countAll = countAll;
	}

	public void setCountUse(int countUse) {
		this.countUse = countUse;
	}

	public BsManageVaccine getVaccinate() {
		return vaccinate;
	}

	public void setVaccinate(BsManageVaccine vaccinate) {
		this.vaccinate = vaccinate;
	}

	public int getRest() {
		return rest;
	}

	public void setRest(int rest) {
		this.rest = rest;
	}

	public int getApplicableMin() {
		return applicableMin;
	}

	public int getApplicableMax() {
		return applicableMax;
	}

	public void setApplicableMin(int applicableMin) {
		this.applicableMin = applicableMin;
	}

	public void setApplicableMax(int applicableMax) {
		this.applicableMax = applicableMax;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
	private int check0;		//库存盘点时筛选条件：是否维护0库存

	public int getCheck0() {
		return check0;
	}

	public void setCheck0(int check0) {
		this.check0 = check0;
	}

	/** 当设定为true，只查询有库存的数据 */
	public boolean isStorenumIsNull() {
		return storenumIsNull;
	}
	

	/** 当设定为true，只查询有库存的数据 */
	public void setStorenumIsNull(boolean storenumIsNull) {
		this.storenumIsNull = storenumIsNull;
	}

	public String getBatchnoLike() {
		return batchnoLike;
	}

	public void setBatchnoLike(String batchnoLike) {
		this.batchnoLike = batchnoLike;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getExpiresSec() {
		return expiresSec;
	}

	public void setExpiresSec(String expiresSec) {
		this.expiresSec = expiresSec;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getShowAll() {
		if(StringUtils.isBlank(showAll)){
			return SHOWALL_NO;
		}
		return showAll;
	}

	public void setShowAll(String showAll) {
		this.showAll = showAll;
	}

	public String[] getVaccineidIn() {
		if(vaccineidIn == null || vaccineidIn.length == 0){
			return null;
		}
		return vaccineidIn;
	}

	public void setVaccineidIn(String[] vaccineidIn) {
		this.vaccineidIn = vaccineidIn;
	}

	public int getObligate() {
		return obligate;
	}

	public void setObligate(int obligate) {
		this.obligate = obligate;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getOfficeCodeDb() {
		return officeCodeDb;
	}

	public void setOfficeCodeDb(String officeCodeDb) {
		this.officeCodeDb = officeCodeDb;
	}

	public String getEpiId() {
		return epiId;
	}

	public void setEpiId(String epiId) {
		this.epiId = epiId;
	}

	public boolean isExceptQuene() {
		return exceptQuene;
	}

	public void setExceptQuene(boolean exceptQuene) {
		this.exceptQuene = exceptQuene;
	}
	
	
}