package com.thinkgem.jeesite.modules.product.entity;

import java.io.Serializable;

import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.cms.entity.CmsDisclosure;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;

/**
 * 用于一体机查询可接种的产品vo
 * 
 * @author fuxin
 * @date 2017年4月7日 下午3:12:20
 * @description TODO
 */
@Deprecated
public class ApiProductVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String id;			//产品id(pid)
	public String batchno;		//产品批号
	public String manufacturer;	//产品生产厂家
	public double sellprice;	//产品价格
	public String name;			//产品名称
	public String context;		//告知书内容
	public String nid;			//计划id

	public String insurance;	//是否购买保险
	public String paystatus;	//是否已付款
	
	public ApiProductVo() { }

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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	
	
	/**
	 * 将接种记录转为api需要返回的vo
	 * @author fuxin
	 * @date 2017年4月7日 下午3:34:29
	 * @description 
	 *		TODO
	 * @param r
	 * @return
	 *
	 */
	public static ApiProductVo parseVo(ChildVaccinaterecord r){
		ApiProductVo vo = new ApiProductVo();
		if(null != r){
			vo.setBatchno(r.getBatch());
			vo.setId(r.getProductid());
			vo.setInsurance(r.getInsurance());
			vo.setManufacturer(r.getManufacturer());
			vo.setName(r.getVaccName());
			vo.setNid(r.getNid());
			vo.setPaystatus(r.getPayStatus());
			vo.setSellprice(r.getPrice());
		}
		return  vo;
	}

	
	/**
	 * 向vo中添加告知书
	 * @author fuxin
	 * @date 2017年4月7日 下午4:08:20
	 * @description 
	 *		TODO
	 * @param cmsDisclosure
	 * @return
	 *
	 */
	public ApiProductVo addContext(CmsDisclosure cmsDisclosure) {
		if(null != cmsDisclosure){
			this.setContext(cmsDisclosure.getContext());
		}
		return this;
	}

	
	/**
	 * 将计划信息转为api需要返回的vo
	 * @author fuxin
	 * @date 2017年4月7日 下午3:34:29
	 * @description 
	 *		TODO
	 * @param r
	 * @return
	 *
	 */
	public static ApiProductVo parseVo(BsManageVaccinenum n) {
		ApiProductVo vo = new ApiProductVo();
		if(null != n){
			vo.setInsurance(ChildVaccinaterecord.INSURANCE_STATUS_NO);
			vo.setPaystatus(ChildVaccinaterecord.PAY_STATUS_NO);
			vo.setNid(n.getId());
		}
		return  vo;
	}
	
	
	/**
	 * 向vo中加入产品信息
	 * @author fuxin
	 * @date 2017年4月7日 下午4:08:02
	 * @description 
	 *		TODO
	 * @param product
	 * @return
	 *
	 */
	public ApiProductVo addProduct(BsManageProduct product) {
		if(null != product){
			this.setBatchno(product.getBatchno());
			this.setId(product.getId());
			this.setManufacturer(product.getManufacturer());
			this.setName(product.getName());
			this.setSellprice(product.getSellprice());
			
		}
		return this;
	}

}
