package com.thinkgem.jeesite.modules.product.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.cms.entity.CmsDisclosure;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;

/**
 * 用于一体机查询可接种的大类vo
 * @author fuxin
 * @date 2017年4月19日 下午1:47:38
 * @description 
 *		TODO
 */
public class ApiVaccGroupVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 已预约 */
	public static final String TYPE_RECORD = "1";
	/** 模型计算 */
	public static final String TYPE_MODEL = "2";
	/** 模型计算-只显免费 */
	public static final String TYPE_MODEL_FREE = "3";
	
	/** 一类苗 */
	public static final String NUMTYPE_1 = "1";
	/** 二类苗 */
	public static final String NUMTYPE_2 = "2";
	
	private String name;			//大类名称
	private double sellprice;	//产品价格
	private int num;			//针数
	private String pid;			//产品id(pid or pid_pid)
	private String nid;			//计划id
	private String vid;			//疫苗小类id
	private String context;		//告知书内容
	private String insurance;	//是否购买保险
	private String paystatus;	//是否已付款
	private String payOption;	//支付选项
	
	private String type;		//数据类型
	private String pin;			//剂次
	private String pathema;		//预防疾病类型
	private String numType = "一类";		//标识一类还是二类
	
	private String rid;		//预约id
	private String sign;	//签字
	private String sType;	//签字来源
	
	public ApiVaccGroupVo() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSellprice() {
		return sellprice;
	}

	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getContext() {
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

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPathema() {
		return pathema;
	}

	public void setPathema(String pathema) {
		this.pathema = pathema;
	}

	public String getNumType() {
		if(StringUtils.isNotBlank(this.numType)){
			return numType;
		}
		return "";
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}
	
	
	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	/**
	 * 将接种记录转为api需要返回的vo
	 * @author fuxin
	 * @date 2017年4月19日 下午2:27:11
	 * @description 
	 *		TODO
	 * @param r
	 * @return
	 *
	 */
	public static ApiVaccGroupVo parseVo(ChildVaccinaterecord r){
		ApiVaccGroupVo vo = new ApiVaccGroupVo();
		if(null != r){
			vo.setNid(r.getNid());
			vo.setNum(1);
			vo.setPid(r.getProductid());
			vo.setInsurance(r.getInsurance());
			vo.setPaystatus(r.getPayStatus());
			if(null != r.getVaccnum()){
				vo.setName(r.getVaccnum().getName());
			}
		}
		return vo;
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
	public ApiVaccGroupVo addContext(CmsDisclosure cmsDisclosure) {
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
	public static ApiVaccGroupVo parseVo(BsManageVaccinenum n) {
		ApiVaccGroupVo vo = new ApiVaccGroupVo();
		if(null != n){
			vo.setInsurance(ChildVaccinaterecord.INSURANCE_STATUS_NO);
			vo.setPaystatus(ChildVaccinaterecord.PAY_STATUS_NO);
			vo.setNid(n.getId());
			vo.setName(n.getName());
			vo.setNum(1);
			vo.setNumType(n.getType()+"");
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
	public ApiVaccGroupVo addProduct(BsManageProduct product) {
		if(null != product){
			setSellprice(product.getSellprice());
			setPid(product.getId());
		}
		return this;
	}

	/**
	 * 两个产品相加
	 * @author fuxin
	 * @date 2017年4月19日 下午3:38:41
	 * @description 
	 *		TODO
	 * @param v1
	 * @param v2
	 * @return
	 *
	 */
	public static ApiVaccGroupVo mix(ApiVaccGroupVo v1, ApiVaccGroupVo v2) {
		ApiVaccGroupVo vo = new ApiVaccGroupVo();
		vo.setContext(v1.getContext() + "\r\n" + v2.getContext());
		if(ChildVaccinaterecord.INSURANCE_STATUS_YES.equals(v1.getInsurance()) || ChildVaccinaterecord.INSURANCE_STATUS_YES.equals(v2.getInsurance())){
			vo.setInsurance(ChildVaccinaterecord.INSURANCE_STATUS_YES);
		}else{
			vo.setInsurance(ChildVaccinaterecord.INSURANCE_STATUS_NO);
		}
		vo.setName(v1.getName() + "_" + v2.getName());
		vo.setNid(v1.getNid() + "_" + v2.getNid());
		vo.setPid(v1.getPid() + "_" + v2.getPid());
		vo.setNum(v1.getNum() + v2.getNum());
		vo.setPathema(v1.getPathema() + "_" + v2.getPathema());
		vo.setPin(v1.getPin() + "_" + v2.getPin());
		vo.setPaystatus(v1.getPaystatus());
		vo.setSellprice(v1.getSellprice() + v2.getSellprice());
		return vo;
	}

	public void addVaccInfo(BsManageVaccine vac, String pinNum) {
		this.pin = "第"+ pinNum + "针/共" + vac.getMaxpin() + "针";
		this.pathema = vac.getPathema();
	}
	
	public void addVaccInfo(BsManageVaccine vac, Long pinNum) {
		addVaccInfo(vac, pinNum+"");
	}

	public String getPayOption() {
		return payOption;
	}

	public void setPayOption(String payOption) {
		this.payOption = payOption;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}
	
	
}
