/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.num.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.dwr.DwrUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.dao.CmsDisclosureDao;
import com.thinkgem.jeesite.modules.cms.entity.CmsDisclosure;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.service.BsHepatitisBNumService;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.service.BsRabiesNumService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

import sun.misc.BASE64Decoder;

/**
 * 狂犬疫苗针次Controller
 * 
 * @author wangdedi
 * @version 2017-03-06
 */
@Controller
@RequestMapping(value = "${frontPath}/num/bsRabiesNum")
public class BsRabiesSignatureController extends BaseController {

	@Autowired
	private BsRabiesNumService bsRabiesNumService;
	@Autowired
	private CmsDisclosureDao cmsDisclosureDao;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private BsHepatitisBNumService bsHepatitisBNumService;

	@ModelAttribute
	public BsRabiesNum get(@RequestParam(required = false) String id) {
		BsRabiesNum entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = bsRabiesNumService.get(id);
		}
		if (entity == null) {
			entity = new BsRabiesNum();
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
	public String signatureId(BsRabiesNum bsRabiesNum, Model model) {
		bsRabiesNum.setSessionId(bsRabiesNum.getSessionId() +"_"+ bsRabiesNum.getId());
		model.addAttribute("bsRabiesNum", bsRabiesNum);
		return "modules/num/bsRabiesSignature";
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
	public String saveSignature(BsRabiesNum bsRabiesNum,Model model) {
		String sessionId = bsRabiesNum.getSessionId();
		String id = bsRabiesNum.getId();
		if(bsRabiesNum.getPaystatus().equals(BsRabiesNum.STATUS_NORMAL)){
			//签字状态
			if(null != bsRabiesNum.getSignatureData() && bsRabiesNum.getSignatureData().length > 0 ){
				//查询该记录签字是否存在
				int count = bsRabiesNumService.querySignature(bsRabiesNum);
				if(count == 0){
					//新增签字
					bsRabiesNumService.saveSignature(bsRabiesNum);
				}
				//补充用户告知书签字
				bsRabiesNumService.updateCheckSId(bsRabiesNum);
				//补充记录签字
				bsRabiesNum.setSignature(BsRabiesNum.SIGNATURE_YES);
				bsRabiesNum.setsId(id);
				bsRabiesNumService.updateSignatures(bsRabiesNum);
				//推送前台
				DwrUtils.sendSignture(sessionId, id);
				DwrUtils.sendSignture(sessionId, "");
				model.addAttribute("id", bsRabiesNum.getCheckid());
			}
		}else{
			// 查询全部
			List<BsRabiesNum> bsBumList = bsRabiesNumService.queryBsNumListOut(bsRabiesNum);
			if(bsBumList.size() != 0){
				//签字状态
				if(null != bsRabiesNum.getSignatureData() && bsRabiesNum.getSignatureData().length > 0 ){
					//查询该记录签字是否存在
					int count = bsRabiesNumService.querySignature(bsRabiesNum);
					if(count == 0){
						//新增签字
						bsRabiesNumService.saveSignature(bsRabiesNum);
					}
					//补充用户告知书签字
					bsRabiesNumService.updateCheckSId(bsRabiesNum);
					//补充记录签字
					for(int i = 0;i < bsBumList.size();i++){
						bsRabiesNum.setId(bsBumList.get(i).getId());
						bsRabiesNum.setSignature(BsRabiesNum.SIGNATURE_YES);
						bsRabiesNum.setsId(id);
						bsRabiesNumService.updateSignatures(bsRabiesNum);
					}
					//推送前台
					DwrUtils.sendSignture(sessionId, id);
					DwrUtils.sendSignture(sessionId, "");
					model.addAttribute("id", bsRabiesNum.getCheckid());
				}
			}
		}
		model.addAttribute("bsRabiesNum", bsRabiesNum);
		return "modules/num/bsRabiesSignature";
	}
	
	/**
	 * 签字页首页
	 * @author zhouqj
	 * @date 2017年10月9日 上午10:58:54
	 * @description 
	 *		TODO
	 * @param response
	 * @param id
	 * @param model
	 * @return
	 *
	 */
	@Deprecated
	@RequestMapping("/signIndex")
	public String signIndex(HttpServletResponse response, String id, Model model) {
		return "modules/sys/signIndex";
	}
	
	/**
	 * 显示知情告知书
	 * @author zhouqj
	 * @date 2017年10月9日 上午10:59:03
	 * @description 
	 *		TODO
	 * @param response
	 * @param id
	 * @param model
	 * @return
	 *
	 */
	@Deprecated
	@RequestMapping("/disclosure")
	public String disclosure(HttpServletResponse response, String id, Model model) {
		String vaccid = "";
		//判断是犬伤或其他
		if(id.contains("A")){
			//初始化查询单条记录
			BsRabiesNum bsRabiesNum = new BsRabiesNum();
			String ids = id.substring(1);
			bsRabiesNum.setId(ids);
			bsRabiesNum = bsRabiesNumService.get(bsRabiesNum);
			vaccid = bsRabiesNum.getVaccineid();
		}else{
			BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
			String ids = id.substring(1);
			bsHepatitisBNum.setId(ids);
			bsHepatitisBNum = bsHepatitisBNumService.get(bsHepatitisBNum);
			vaccid = bsHepatitisBNum.getVaccineId();
		}
		BsManageVaccine vacc = bsManageVaccineService.get(vaccid);
		String disContext = "";
		if(vacc != null){
			CmsDisclosure disclosure = cmsDisclosureDao.get(vacc.getgNum());
			if(disclosure != null && StringUtils.isNotBlank(disclosure.getContext())){
//				disContext = disclosure.getContext().replaceAll("\r\n", "<br>&emsp;&emsp;");
				disContext = disclosure.getContext().replaceAll("#unit#", " ");
				model.addAttribute("disclosure", disContext);
			}
		}
		return "modules/num/signatureShow";
	}
	
	/**
	 * 签字处理回调接口
	 * @author zhouqj
	 * @date 2017年9月8日 下午4:45:36
	 * @description 
	 *		TODO
	 * @param request
	 * @param sessionId
	 * @return
	 *
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/offlineSignatureCallBackApi")
	public @ResponseBody String offlinePayCallBackApi(@RequestBody(required=false)String data){
		logger.info("接到签字回调，data=" + data);
		
		Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(data, Map.class);
		String signatureData = map.get("signatureData");
		String sessionId = map.get("sessionId");
		String id = map.get("id");
		
		logger.info("接到签字回调，signatureData=" + signatureData);
		signatureData = signatureData.replaceAll(" ", "+");
		logger.info("接到签字回调，sessionId=" + sessionId);
		logger.info("接到签字回调，id=" + id);
		if(StringUtils.isBlank(sessionId)){
			return "error";
		}
		try {
			//判断是犬伤或其他
			if(id.contains("A")){
				//初始化查询单条记录
				BsRabiesNum bsRabiesNum = new BsRabiesNum();
				String ids = id.substring(1);
				bsRabiesNum.setId(ids);
				bsRabiesNum = bsRabiesNumService.get(bsRabiesNum);
				//签字状态
				if(StringUtils.isNotBlank(signatureData)){
					//base64 转换签字
					BASE64Decoder decoder = new BASE64Decoder(); 
					byte[] sign = decoder.decodeBuffer(signatureData);
					bsRabiesNum.setSignatureData(sign);
					bsRabiesNum.setStype("1");
					bsRabiesNum.setsId(IdGen.uuid());
				}
				//判断该记录是否付款
				if(bsRabiesNum.getPaystatus().equals(BsRabiesNum.STATUS_NORMAL)){
					//签字状态
					if(null != bsRabiesNum.getSignatureData() && bsRabiesNum.getSignatureData().length > 0){
						//查询该记录签字是否存在
						int count = bsRabiesNumService.querySignature(bsRabiesNum);
						if(count == 0){
							//新增签字
							bsRabiesNumService.saveSignature(bsRabiesNum);
						}
						//补充用户告知书签字
						bsRabiesNumService.updateCheckSId(bsRabiesNum);
						//补充记录签字
						bsRabiesNum.setSignature(BsRabiesNum.SIGNATURE_YES);
						bsRabiesNumService.updateSignatures(bsRabiesNum);
						//推送前台
						DwrUtils.sendSignture(sessionId, ids);
						DwrUtils.sendSignture(sessionId, "");
					}
				}else{
					// 查询全部
					List<BsRabiesNum> bsBumList = bsRabiesNumService.queryBsNumListOut(bsRabiesNum);
					if(bsBumList.size() != 0){
						//签字状态
						if(null != bsRabiesNum.getSignatureData() && bsRabiesNum.getSignatureData().length > 0){
							//查询该记录签字是否存在
							int count = bsRabiesNumService.querySignature(bsRabiesNum);
							if(count == 0){
								//新增签字
								bsRabiesNumService.saveSignature(bsRabiesNum);
							}
							//补充用户告知书签字
							bsRabiesNumService.updateCheckSId(bsRabiesNum);
							//补充记录签字
							for(int i = 0;i < bsBumList.size();i++){
								bsRabiesNum.setId(bsBumList.get(i).getId());
								bsRabiesNum.setSignature(BsRabiesNum.SIGNATURE_YES);
								bsRabiesNumService.updateSignatures(bsRabiesNum);
							}
							//推送前台
							DwrUtils.sendSignture(sessionId, ids);
							DwrUtils.sendSignture(sessionId, "");
						}
					}
				}
			}else{
				//初始化查询单条记录
				BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
				String ids = id.substring(1);
				bsHepatitisBNum.setId(ids);
				bsHepatitisBNum = bsHepatitisBNumService.get(bsHepatitisBNum);
				//签字状态
				if(StringUtils.isNotBlank(signatureData)){
					//base64 转换签字
					BASE64Decoder decoder = new BASE64Decoder(); 
					byte[] sign = decoder.decodeBuffer(signatureData);
					bsHepatitisBNum.setSignatureData(sign);
					bsHepatitisBNum.setStype("1");
					bsHepatitisBNum.setsId(IdGen.uuid());
				}
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
						bsHepatitisBNumService.updateSignatures(bsHepatitisBNum);
						//推送前台
						DwrUtils.sendSignture(sessionId, ids);
						DwrUtils.sendSignture(sessionId, "");
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
								bsHepatitisBNumService.updateSignatures(bsHepatitisBNum);
							}
							//推送前台
							DwrUtils.sendSignture(sessionId, ids);
							DwrUtils.sendSignture(sessionId, "");
						}
					}
				}
			}
			logger.info("签字回调成功，param=" + sessionId);
		} catch (Exception e) {
			logger.info("签字回调失败，param=" + sessionId,e);
		}		
		return "ok";
	}

}