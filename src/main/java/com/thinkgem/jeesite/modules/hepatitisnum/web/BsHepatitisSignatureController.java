/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitisnum.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.dwr.DwrUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.service.BsHepatitisBNumService;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;

/**
 * 乙肝疫苗针次信息Controller
 * 
 * @author xuejinshan
 * @version 2017-07-26
 */
@Controller
@RequestMapping(value = "${frontPath}/hepatitisnum/bsHepatitisBNum")
public class BsHepatitisSignatureController extends BaseController {

	@Autowired
	private BsHepatitisBNumService bsHepatitisBNumService;

	@ModelAttribute
	public BsHepatitisBNum get(@RequestParam(required = false) String id) {
		BsHepatitisBNum entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = bsHepatitisBNumService.get(id);
		}
		if (entity == null) {
			entity = new BsHepatitisBNum();
		}
		return entity;
	}

	/**
	 * 签字
	 * @author zhouqj
	 * @date 2017年7月12日 下午5:37:48
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param model
	 * @return
	 *
	 */
	@Deprecated
	@RequestMapping(value = "signatureId")
	public String signatureId(BsHepatitisBNum bsHepatitisBNum, Model model) {
		bsHepatitisBNum.setSessionId(bsHepatitisBNum.getSessionId() +"_"+ bsHepatitisBNum.getId());
		model.addAttribute("bsHepatitisBNum", bsHepatitisBNum);
		return "modules/hepatitisnum/bsHepatitisBSignature";
	}
	
	/**
	 * 签字保存
	 * @author zhouqj
	 * @date 2017年7月21日 上午8:30:54
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param model
	 * @return
	 *
	 */
	@Deprecated
	@RequestMapping(value = "saveSignature")
	public String saveSignature(BsHepatitisBNum bsHepatitisBNum,Model model) {
		String sessionId = bsHepatitisBNum.getSessionId();
		String id = bsHepatitisBNum.getId();
		if(bsHepatitisBNum.getPayStatus().equals(BsHepatitisBNum.STATUS_NORMAL)){
			//签字状态
			if(null != bsHepatitisBNum.getSignatureData() && bsHepatitisBNum.getSignatureData().length > 0 ){
				//查询该记录签字是否存在
				int count = bsHepatitisBNumService.querySignature(bsHepatitisBNum);
				if(count == 0){
					//新增签字
					bsHepatitisBNumService.saveSignature(bsHepatitisBNum);
				}
				//补充用户告知书签字
				bsHepatitisBNumService.updateCheckSId(bsHepatitisBNum);
				//补充记录签字
				bsHepatitisBNum.setSignature(BsRabiesNum.SIGNATURE_YES);
				bsHepatitisBNum.setsId(id);
				bsHepatitisBNumService.updateSignatures(bsHepatitisBNum);
				//推送前台
				DwrUtils.sendSignture(sessionId, id);
				DwrUtils.sendSignture(sessionId, "");
				model.addAttribute("id", bsHepatitisBNum.getCheckId());
			}
		}else{
			// 查询全部
			List<BsHepatitisBNum> bs = bsHepatitisBNumService.queryBsNumListOut(bsHepatitisBNum);
			if(bs.size() != 0){
				//签字状态
				if(null != bsHepatitisBNum.getSignatureData() && bsHepatitisBNum.getSignatureData().length > 0 ){
					//查询该记录签字是否存在
					int count = bsHepatitisBNumService.querySignature(bsHepatitisBNum);
					if(count == 0){
						//新增签字
						bsHepatitisBNumService.saveSignature(bsHepatitisBNum);
					}
					//补充用户告知书签字
					bsHepatitisBNumService.updateCheckSId(bsHepatitisBNum);
					//补充记录签字
					for(int i = 0;i < bs.size();i++){
						bsHepatitisBNum.setId(bs.get(i).getId());
						bsHepatitisBNum.setSignature(BsRabiesNum.SIGNATURE_YES);
						bsHepatitisBNum.setsId(id);
						bsHepatitisBNumService.updateSignatures(bsHepatitisBNum);
					}
					//推送前台
					DwrUtils.sendSignture(sessionId, id);
					model.addAttribute("id", bsHepatitisBNum.getCheckId());
				}
			}
		}
		model.addAttribute("bsHepatitisBNum", bsHepatitisBNum);
		return "modules/hepatitisnum/bsHepatitisBSignature";
	}
	

}