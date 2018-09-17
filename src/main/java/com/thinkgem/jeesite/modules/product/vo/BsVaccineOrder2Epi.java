package com.thinkgem.jeesite.modules.product.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 市平台上报vo
 * 
 * @author fuxin
 * @date 2018年1月18日 下午9:31:51
 * @description TODO
 */
public class BsVaccineOrder2Epi implements Serializable {
	private static final long serialVersionUID = 1L;

	BsVaccineOrderVo bsVaccineOrder ;
	List<UploadEpiProInfo> list = new ArrayList<UploadEpiProInfo>();

	public BsVaccineOrder2Epi() {
		super();
	}

	public BsVaccineOrder2Epi(BsVaccineOrderVo bsVaccineOrder) {
		super();
		this.bsVaccineOrder = bsVaccineOrder;
	}

	public BsVaccineOrder2Epi(BsVaccineOrderVo bsVaccineOrder, List<UploadEpiProInfo> list) {
		super();
		this.bsVaccineOrder = bsVaccineOrder;
		this.list = list;
	}

	public BsVaccineOrderVo getBsVaccineOrder() {
		return bsVaccineOrder;
	}

	public List<UploadEpiProInfo> getList() {
		return list;
	}

	public void setBsVaccineOrder(BsVaccineOrderVo bsVaccineOrder) {
		this.bsVaccineOrder = bsVaccineOrder;
	}

	public void setList(List<UploadEpiProInfo> list) {
		this.list = list;
	}

}
