	package com.thinkgem.jeesite.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.pay.PayJnBankBean;
import com.thinkgem.jeesite.common.pay.PayJnBankQueryThread;
import com.thinkgem.jeesite.common.pay.PayQueryThread;
import com.thinkgem.jeesite.common.test.SpringTransactionalContextTests;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.sys.service.JobService;

public class TestSpring extends SpringTransactionalContextTests {
	
	@Autowired
	BsManageProductService productService;
	@Autowired
	JobService JobService;
	
	@Test
	public void testJnBank(){
		PayJnBankBean bean = new PayJnBankBean();
		bean.setVaccineType(PayJnBankBean.VACCINE_TYPE_ADULT);
		bean.setVaccineName("狂犬病疫苗");
		bean.setRemarks("成大生物狂苗*1 血清*7");
		bean.setTotal(0.01d);
		bean.setOfficeCode("3406030301");
		bean.setSource(PayJnBankBean.SOURCE_OFF_SACN_IN);
		bean.setChildCode("D3201211994901353X");
		
		BsManageProduct tempProduct = new BsManageProduct();
		tempProduct.setVaccineid("1703");
		tempProduct.setLocalCode("3406030301");
		tempProduct.setShowAll(BsManageProduct.SHOWALL_YES);
		List<BsManageProduct> products = productService.findList(tempProduct);
		for(BsManageProduct product : products){
			product.setStorenum(1l);
		}
		bean.setBsManageProducts(products);
		Map<String, Object> optional = new HashMap<String, Object>();
		optional.put("sessionId", "123");
		optional.put("payId", "123");
		bean.setPara(optional);
		
		PayQueryThread payQueryThread = new PayJnBankQueryThread((int)(bean.getTotal() * 100), bean.getOfficeCode(), bean.getSource(), bean.getChildCode(), bean.getOrderNo(), bean.getPara());
		
		System.out.println(JsonMapper.toJsonString(bean.sendOrder(payQueryThread)));
		
	}
	
	
	@Test
	public void testQueryJnBank(){
		PayJnBankBean bean = new PayJnBankBean();
		bean.setOrderNo("OFFJN12017120611330980");
//		bean.setTimestamp(DateUtils.parseDate("2017-11-28 14:25:43"));		
		System.out.println(JsonMapper.toJsonString(bean.queryOrder()));
		
	}
	
	@Test
	public void testSubString(){
		System.out.println("resp=Yjmf311BFGXtMimEmsDET2QQN97KQUw7VWWeOvHJ9l".substring(5));
	}

	@Test
	public void testSentCharge(){
		JobService.findDataListChargeEveryday();
	}
	
	
}
