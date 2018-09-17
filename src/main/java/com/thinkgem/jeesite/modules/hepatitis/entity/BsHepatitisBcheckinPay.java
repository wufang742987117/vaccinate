/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitis.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 狂犬疫苗-接种者档案
 * 
 * @author wangdedi
 * @version 2017-02-24
 */
public class BsHepatitisBcheckinPay extends DataEntity<BsHepatitisBcheckinPay> {

	private static final long serialVersionUID = 1L;
	private String check; // 记录id

	public BsHepatitisBcheckinPay() {
		super();
	}

	public BsHepatitisBcheckinPay(String id) {
		super(id);
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	
}