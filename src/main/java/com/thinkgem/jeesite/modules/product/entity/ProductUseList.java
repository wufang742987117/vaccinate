package com.thinkgem.jeesite.modules.product.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ProductUseList extends DataEntity<ProductUseList> {
	private static final long serialVersionUID = 1L;
	private String id;		// 疫苗id
	private String batchno;		// 批次编号
	private String manufacturer;		// 疫苗的制造厂商
	private String isforeign;		//疫苗类型：  1一类 2二类
	private Long storesum;		// 库存
	private double sellprice;		// 出售价格
	private String name;		// 疫苗名称
	private Date vaccExpDate;		// 有效期
	private String vacsiteno;    //疫苗站点编号
	private String codeall;		// 疫苗英文名称
	private String nid;		// num表ID
	private String bigcode;  //疫苗大类编码
	private String gname; 	//疫苗大类名
	private int countAll;	//接种数量(盘点用)
	private int countUse;	//使用数量(盘点用)
	private String manufacturerCode;  //疫苗厂家编码
	private String usesum;   //疫苗使用总数
	private String orderBy;
	private String batchnoLike;
	private Date createDate;  //出库时间
	private Date beginCreateDate; //出库时间(起）
	private Date endCreateDate; // 出库时间(止）
	private Date vaccinateDate; //接种日期
	private double price;    //疫苗价格
	private String doctor;   //接种医生
	private String bodyPart;   //接种部位
	private String childCode;   //儿童编号
	private String childName;   //儿童姓名
	private String dosage;     //第几针
	private String localCode;   //接种单位编号
	private String[] nameIn;  
	
	

	public Date getVaccinateDate() {
		return vaccinateDate;
	}

	public void setVaccinateDate(Date vaccinateDate) {
		this.vaccinateDate = vaccinateDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(String bodyPart) {
		this.bodyPart = bodyPart;
	}

	public String getChildCode() {
		return childCode;
	}

	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	public String[] getNameIn() {
		return nameIn;
	}

	public void setNameIn(String[] nameIn) {
		this.nameIn = nameIn;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getIsforeign() {
		return isforeign;
	}
	public void setIsforeign(String isforeign) {
		this.isforeign = isforeign;
	}
	public Long getStoresum() {
		return storesum;
	}
	public void setStoresum(Long storesum) {
		this.storesum = storesum;
	}
	public double getSellprice() {
		return sellprice;
	}
	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getVaccExpDate() {
		return vaccExpDate;
	}
	public void setVaccExpDate(Date vaccExpDate) {
		this.vaccExpDate = vaccExpDate;
	}
	public String getVacsiteno() {
		return vacsiteno;
	}
	public void setVacsiteno(String vacsiteno) {
		this.vacsiteno = vacsiteno;
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
	public int getCountAll() {
		return countAll;
	}
	public void setCountAll(int countAll) {
		this.countAll = countAll;
	}
	public int getCountUse() {
		return countUse;
	}
	public void setCountUse(int countUse) {
		this.countUse = countUse;
	}
	public String getManufacturerCode() {
		return manufacturerCode;
	}
	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}
	public String getUsesum() {
		return usesum;
	}
	public void setUsesum(String usesum) {
		this.usesum = usesum;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getBatchnoLike() {
		return batchnoLike;
	}
	public void setBatchnoLike(String batchnoLike) {
		this.batchnoLike = batchnoLike;
	}
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}
	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	public Date getEndCreateDate() {
		return endCreateDate;
	}
	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	
}
