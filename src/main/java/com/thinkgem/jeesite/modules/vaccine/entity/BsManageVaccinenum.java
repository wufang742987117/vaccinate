/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;

/**
 * 疫苗接种规则管理Entity
 * @author fuxin
 * @version 2017-03-04
 */
public class BsManageVaccinenum extends DataEntity<BsManageVaccinenum> {
	
	public static final long type_1 = 1;
	public static final long type_2 = 2;
	
	private static final long serialVersionUID = 1L;
	private String weight;		// 权重
	private String vaccineid;		// 疫苗id
	private Long mouage;		// 月龄
	private String code;		// 疫苗代码
	private String name;		// 疫苗名称
	private Long pin;		// 针次
	private String status;		// 状态
	private Long type;		// 疫苗类型
	private Long pintime;		// 距离上次针次的时间 间隔 单位（月 ）
	private Long lasttime;		// 最晚 接种月龄
	private int st;//疫苗是否在队列中
	private int size;//集合中同种疫苗的个数
	private Integer excep;		// 特殊疫苗，出生可选，只打一次
	private Long pentrep;		// 是否五联替代
	private Long price;		// 疫苗价格
	private String group;		// 大类CODE
	private String clazz;		// 小类CODE
	private String groupname;		// 育苗大类
	private String allname;		// 疫苗名称
	private String manufacturer;		// 疫苗厂家
	
	private BsManageVaccine vaccine;
	private BsManageProduct product;
	private String stock; //库存
	private String intype; //接种类型 0 针次1口服
	private double maxPrice;
	private double minPrice;
	
	private String vaccType;
	private Date remindDate;
	private String rid;
	private Date vaccDate;		//下次接种时间
	
	public BsManageVaccinenum() {
		super();
	}

	public BsManageVaccinenum(String id){
		super(id);
	}

	@Length(min=0, max=50, message="权重长度必须介于 0 和 50 之间")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@Length(min=0, max=32, message="疫苗id长度必须介于 0 和 32 之间")
	public String getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(String vaccineid) {
		this.vaccineid = vaccineid;
	}
	
	public Long getMouage() {
		return mouage;
	}

	public void setMouage(Long mouage) {
		this.mouage = mouage;
	}
	
	@Length(min=0, max=30, message="疫苗代码长度必须介于 0 和 30 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=30, message="疫苗名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getPin() {
		return pin;
	}

	public void setPin(Long pin) {
		this.pin = pin;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
	
	public Long getPintime() {
		return pintime;
	}

	public void setPintime(Long pintime) {
		this.pintime = pintime;
	}
	
	public Long getLasttime() {
		return lasttime;
	}

	public void setLasttime(Long lasttime) {
		this.lasttime = lasttime;
	}

	public BsManageVaccine getVaccine() {
		return vaccine;
	}

	public void setVaccine(BsManageVaccine vaccine) {
		this.vaccine = vaccine;
	}

	public int getSt() {
		return st;
	}

	public void setSt(int st) {
		this.st = st;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public BsManageProduct getProduct() {
		return product;
	}

	public void setProduct(BsManageProduct product) {
		this.product = product;
	}

	public Integer getExcep() {
		return excep;
	}

	public void setExcep(Integer excep) {
		this.excep = excep;
	}

	public Long getPentrep() {
		return pentrep;
	}

	public void setPentrep(Long pentrep) {
		this.pentrep = pentrep;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getAllname() {
		return allname;
	}

	public void setAllname(String allname) {
		this.allname = allname;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getIntype() {
		return intype;
	}

	public void setIntype(String intype) {
		this.intype = intype;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	
	/**
	 * <strong>将免疫类型添加到nid中</strong>
	 * <p>如果接种类型部位常规，则在nid后加一位vacctype</p>
	 * @author fuxin
	 * @date 2017年7月13日 下午9:53:27
	 * @description 
	 *		
	 * @return
	 *
	 */
	public BsManageVaccinenum updateNid24(){
		if(StringUtils.isNotBlank(super.id)){
			super.id = super.id.substring(0, 3) + getVaccTypeAfter();
		}
		return this;
	}
	
	public String getVaccTypeFromNid() {
		if(StringUtils.isNotBlank(id) && id.length() > 3){
			return id.substring(3,4);
		}
		return ChildVaccinaterecord.VACCTYPE_NORMAL;
	}
	
	public String getVaccTypeAfter() {
		return (StringUtils.isNotBlank(vaccType) && !ChildVaccinaterecord.VACCTYPE_NORMAL.equals(vaccType))?vaccType:"";
	}

	public String getVaccType() {
		return vaccType;
	}

	public void setVaccType(String vaccType) {
		this.vaccType = vaccType;
	}

	public Date getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public Date getVaccDate() {
		return vaccDate;
	}

	public void setVaccDate(Date vaccDate) {
		this.vaccDate = vaccDate;
	}
	
	
	
	
}