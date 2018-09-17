package com.thinkgem.jeesite.modules.charge.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 门诊收费日志Entity
 * @author zhaojing
 * @version 2017-10-26
 */
public class BsChargeLog extends DataEntity<BsChargeLog> {
	
	/** 发票状态-正常 */
	public static int STATUS_NORMAL = 1;
	/** 发票状态-作废 */
	public static int STATUS_DELETE = 0;
	/** 发票状态-全部 */
	public static int STATUS_ALL = 10;
	/** 发票状态-退款 */
	public static int STATUS_NALDEL = 2;
	/** 开票状态-已开票 */
	public static String BSTATUS_NORMAL = "1";
	/** 开票状态-未开票 */
	public static String BSTATUS_DEFAULT = "0";
	/** 开票状态-全部 */
	public static String BSTATUS_ALL = "10";
	/** 疫苗统计操作-打印 */
	public static String VACC_OPERA = "0";
	/** 收银员选择状态-全部 */
	public static String USERS_ALL = "0";
	/** 是否当天状态状态-当天 */
	public static String RSTATUS_SAME = "0";
	/** 是否当天状态状态-非当天 */
	public static String RSTATUS_DIFF = "1";
	/** 录入信息方式-扫码 */
	public static String INSER_SCAN = "1";
	/** 开票权限-可开票**/
	public static String BILLINGSTATUS_YES = "1";
	/** 开票权限-不可开票**/
	public static String BILLINGSTATUS_NO = "0";
	/** 是否调价-是**/
	public static String FUNDSTATUS_YES = "1";
	/** 是否调价-否**/
	public static String FUNDSTATUS_NO = "0";
	/** 狂犬病模块**/
	public static String MODULE_DOG = "1";
	/** 成人模块**/
	public static String MODULE_ADULT = "2";
	/** 不开票默认业务流水号**/
	public static String BILLNUM_DEFAULT = "0000000000";
	/** 收费类型-疫苗费**/
	public static String TYPE_VACC = "1";
	/** 收费类型-伤口处理费**/
	public static String TYPE_WOUN = "2";
	
	private static final long serialVersionUID = 1L;
	private String patientId;		// 记录信息
	private String patientName;		// 姓名
	private String billNum;			// 业务流水号
	
	private String vaccCode;		// 疫苗种类
	private String vaccBatchnum;	// 疫苗批次
	private String vaccManufacturer;// 疫苗厂家
	private String vaccDose;		// 疫苗规格
	private Double vaccPrice;
	private int vaccCount;			// 疫苗数量
	private String vaccName;    	//疫苗名称
	
	private Integer status;			// 状态  0：作废；1：正常；2：退款
	
	private Double sumPrice;  		//总价
	private Double pay; 			//付款
	private Double refund; 			//找零
	
	private Date createDate;   		//创建时间
	private String loginName;  		//登录者姓名
	private String createByName; 	//收银员姓名
	private String createById; 		//收银员id
	
	private Double updateSumPrice; 	//退费总额
	private String pid;  			//疫苗标识
	private String billing; 		//发票状态  0：未开票；1：已开票；
	
	private Date beginChargedate;  	//开始时间
	private Date endChargedate;  	//结束时间
	private String rstatus; 		//作废当天标识
	
	private String newBillNum;			// 更新业务流水号
	
	private String type;
	
	public BsChargeLog() {
		super();
	}

	public BsChargeLog(String id){
		super(id);
	}
	
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getVaccCode() {
		return vaccCode;
	}

	public void setVaccCode(String vaccCode) {
		this.vaccCode = vaccCode;
	}

	public String getVaccBatchnum() {
		return vaccBatchnum;
	}

	public void setVaccBatchnum(String vaccBatchnum) {
		this.vaccBatchnum = vaccBatchnum;
	}

	public String getVaccManufacturer() {
		return vaccManufacturer;
	}

	public void setVaccManufacturer(String vaccManufacturer) {
		this.vaccManufacturer = vaccManufacturer;
	}

	public String getVaccDose() {
		return vaccDose;
	}

	public void setVaccDose(String vaccDose) {
		this.vaccDose = vaccDose;
	}

	public Double getVaccPrice() {
		return vaccPrice;
	}

	public void setVaccPrice(Double vaccPrice) {
		this.vaccPrice = vaccPrice;
	}

	public int getVaccCount() {
		return vaccCount;
	}

	public void setVaccCount(int vaccCount) {
		this.vaccCount = vaccCount;
	}
	
	public String getVaccName() {
		return vaccName;
	}

	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public Double getRefund() {
		return refund;
	}

	public void setRefund(Double refund) {
		this.refund = refund;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Double getUpdateSumPrice() {
		return updateSumPrice;
	}

	public void setUpdateSumPrice(Double updateSumPrice) {
		this.updateSumPrice = updateSumPrice;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBilling() {
		return billing;
	}

	public void setBilling(String billing) {
		this.billing = billing;
	}

	public Date getBeginChargedate() {
		return beginChargedate;
	}

	public void setBeginChargedate(Date beginChargedate) {
		this.beginChargedate = beginChargedate;
	}

	public Date getEndChargedate() {
		return endChargedate;
	}

	public void setEndChargedate(Date endChargedate) {
		this.endChargedate = endChargedate;
	}

	public String getRstatus() {
		return rstatus;
	}

	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	public String getNewBillNum() {
		return newBillNum;
	}

	public void setNewBillNum(String newBillNum) {
		this.newBillNum = newBillNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BsChargeLog [patientId=" + patientId + ", patientName=" + patientName + ", billNum=" + billNum
				+ ", vaccCode=" + vaccCode + ", vaccBatchnum=" + vaccBatchnum + ", vaccManufacturer=" + vaccManufacturer
				+ ", vaccDose=" + vaccDose + ", vaccPrice=" + vaccPrice + ", vaccCount=" + vaccCount + ", status="
				+ status + ", sumPrice=" + sumPrice + ", pay=" + pay + ", refund=" + refund + ", createDate="
				+ createDate + ", loginName=" + loginName + ", createByName=" + createByName + ", createById="
				+ createById + ", updateSumPrice=" + updateSumPrice + ", pid=" + pid + ", billing=" + billing
				+ ", beginChargedate=" + beginChargedate + ", endChargedate=" + endChargedate + ", vaccName=" + vaccName
				+ ", rstatus=" + rstatus + ", type=" + type +"]";
	}
}