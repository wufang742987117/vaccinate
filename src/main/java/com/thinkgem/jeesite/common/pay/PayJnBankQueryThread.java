package com.thinkgem.jeesite.common.pay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.directwebremoting.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.dwr.DwrUtils;
import com.thinkgem.jeesite.modules.hepatitis.service.BsHepatitisBcheckinService;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.service.BsHepatitisBNumService;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.service.BsRabiesNumService;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;

public class PayJnBankQueryThread extends PayQueryThread {
	
	private static BsRabiesCheckinService bsRabiesCheckinService = SpringContextHolder.getBean(BsRabiesCheckinService.class);
	private static BsHepatitisBcheckinService bsHepatitisBcheckinService = SpringContextHolder.getBean(BsHepatitisBcheckinService.class);
	private static BsRabiesNumService bsRabiesNumService = SpringContextHolder.getBean(BsRabiesNumService.class);
	private static BsHepatitisBNumService bsHepatitisBNumService = SpringContextHolder.getBean(BsHepatitisBNumService.class);
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public PayJnBankQueryThread() {
		super();
	}

	public PayJnBankQueryThread(int total, String officeCode, String source, String childCode,
			String orderNo, Map<String, Object> optional) {
		super(total, officeCode, source, childCode, orderNo, optional);
	}

	@Override
	public void doSomething(Map<String, Object> op) {
		logger.info("接到线下支付回调，param=" + JsonMapper.toJsonString(op));
		if(op != null){
			//获取回调参数
			String sessionId = (String) op.get("sessionId");
			String payId = (String) op.get("payId");
			String type = (String) op.get("type");
			try {
				String check = bsRabiesNumService.queryPay(payId);
				String[] chenks = check.split(",");
				List<String> checkList = new ArrayList<String>();
				//修改付款状态为1
				if(type.equals("1")){
					for(String checkId : chenks){
						BsRabiesNum bsRabiesNum = bsRabiesNumService.get(checkId);
						if(!bsRabiesNum.getStatus().equals("1")){
							bsRabiesCheckinService.updateByPayStatus(checkId);
							checkList.add(checkId);
						}
					}
				}else if(type.equals("2")){
					for(String checkId : chenks){
						BsHepatitisBNum bsHepatitisBNum = bsHepatitisBNumService.get(checkId);
						if(!bsHepatitisBNum.getStatus().equals("1")){
							bsHepatitisBcheckinService.updateByPayStatus(checkId);
							checkList.add(checkId);
						}
					}
				}
				DwrUtils.send(sessionId, JsonUtil.toJson(checkList));
				DwrUtils.send(sessionId, "");
				logger.info("线下支付回调成功，param=" + JsonMapper.toJsonString(op));
			} catch (Exception e) {
				logger.info("线下支付回调失败，param=" + JsonMapper.toJsonString(op),e);
			}
		}
	}
}
