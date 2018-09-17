package com.thinkgem.jeesite.modules.api;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.dwr.DwrUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.charge.entity.BsChargeLog;
import com.thinkgem.jeesite.modules.charge.service.BsChargeLogService;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.cms.dao.CmsDisclosureDao;
import com.thinkgem.jeesite.modules.cms.entity.CmsDisclosure;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckin;
import com.thinkgem.jeesite.modules.hepatitis.service.BsHepatitisBcheckinService;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.service.BsHepatitisBNumService;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.service.BsRabiesNumService;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckin;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinDeal;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

import sun.misc.BASE64Decoder;

/**
 * 对外接口（C#程序专用）
 * @author fuxin
 * @date 2017年8月10日 上午10:47:11
 * @description 
 *		TODO
 */
@Controller
@RequestMapping(value = "${frontPath}/api")
public class PublicApiController extends BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private CmsDisclosureDao cmsDisclosureDao;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private BsRabiesCheckinService bsRabiesCheckinService;
	@Autowired
	private BsHepatitisBcheckinService bsHepatitisBcheckinService;
	@Autowired
	private BsRabiesNumService bsRabiesNumService;
	@Autowired
	private BsHepatitisBNumService bsHepatitisBNumService;
	@Autowired
	private BsChargeLogService bsChargeLogService;
	@Autowired
	private QueneService queneService;
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private VacChildRemindService vacChildRemindService;
	
	
	/**
	 * 显示知情告知书统一接口
	 * @author fuxin
	 * @date 2017年10月5日 下午6:01:50
	 * @description 
	 *		TODO
	 * @param response
	 * @param vaccid
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/disclosure")
	public String disclosure(HttpServletResponse response, String vaccid, Model model) {
		logger.info("签字获取告知书vaccid" + vaccid);
		String disContext = "";
		if(vaccid.startsWith("G_")){
			logger.info("排号签字告知书开始");
			//登记台获取告知书
			String localcode = vaccid.split("_")[1];
			String mNum = vaccid.split("_")[4];
			BsManageVaccine tempVacc = new BsManageVaccine();
			tempVacc.setmNum(mNum);
			tempVacc.setLocalCode(localcode);
			List<BsManageVaccine> listVacc = bsManageVaccineService.findList(tempVacc);
			logger.info("排号签字告知书获取小类" + listVacc.size());
			if(listVacc != null && listVacc.size() > 0 ){
				CmsDisclosure disclosure = cmsDisclosureDao.get(listVacc.get(0).getgNum());
				if(disclosure != null && StringUtils.isNotBlank(disclosure.getContext())){
					logger.info("排号签字告知书获取大类" + listVacc.get(0).getgNum());
					disContext = disclosure.getContext().replaceAll("#unit#", " ");
					model.addAttribute("disclosure", disContext);
				}
			}else{
				logger.info("排号签字告知书失败" + listVacc.size());
			}
		}else{
			if(vaccid.startsWith("A")){
				logger.info("狂犬病签字告知书开始");
				//初始化查询单条记录
				BsRabiesNum bsRabiesNum = new BsRabiesNum();
				bsRabiesNum = bsRabiesNumService.get(vaccid.substring(1));
				if(bsRabiesNum == null){
					model.addAttribute("disclosure", "未找到接种记录！！！");
					return "modules/child_vaccinaterecord/signatureShow";
				}
				vaccid = bsRabiesNum.getVaccineid();
				logger.info("狂犬病签字告知书 获取疫苗种类" + vaccid);
			}else if(vaccid.startsWith("B")){
				logger.info("成人接种签字告知书开始");
				BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
				bsHepatitisBNum = bsHepatitisBNumService.get(vaccid.substring(1));
				if(bsHepatitisBNum == null){
					model.addAttribute("disclosure", "未找到接种记录！！！");
					return "modules/child_vaccinaterecord/signatureShow";
				}
				vaccid = bsHepatitisBNum.getVaccineId();
				logger.info("成人接种签字告知书 获取疫苗种类" + vaccid);
			}
			BsManageVaccine vacc = bsManageVaccineService.get(vaccid);
			logger.info("接种签字告知书 获取疫苗种类" + vaccid);
			if(vacc != null){
				CmsDisclosure disclosure = cmsDisclosureDao.get(vacc.getgNum());
				if(disclosure != null && StringUtils.isNotBlank(disclosure.getContext())){
					disContext = disclosure.getContext().replaceAll("#unit#", " ");
					model.addAttribute("disclosure", disContext);
				}
			}else{
				model.addAttribute("disclosure", "未找到疫苗告知书！！！");
			}
		}
		return "modules/child_vaccinaterecord/signatureShow";
	}
	
	/**
	 * 签字统一处理回调接口
	 * @author zhouqj
	 * @date 2017年9月8日 下午4:45:36
	 * @description 
	 *		TODO
	 * @param request
	 * @param sessionId
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/offlineSignatureCallBackApi")
	public @ResponseBody String offlineSignatureCallBackApi(@RequestBody(required=false)String data){
		logger.debug("接到签字回调1，data=" + data);
		//安卓签字转码
		data = URLDecoder.decode(data);
		if(data.indexOf("{") >0){
			data = data.substring(data.indexOf("{"));
		}
		logger.debug("接到签字回调2，data=" + data);
		//获取参数
		Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(data, Map.class);
		String signatureData = map.get("signatureData");
		String id = map.get("id");
		//打印签字回调日志
		logger.debug("接到签字回调参数，【signatureData】=" + signatureData.length() + "B");
		signatureData = signatureData.replaceAll(" ", "+");
		logger.info("接到签字回调参数，【id】=" + id);
		
		if(id.startsWith("D_")){
			try {
				//登记台签字
				String childid = id.split("_")[1];
				String mNum = id.split("_")[2];
				CacheUtils.put(CacheUtils.SIGN_CACHE, childid + "_" + mNum, signatureData);
				//推送前台
				logger.info("签字回调成功 -->" + "ok|" + id);
				return "ok|" + id;
			} catch (Exception e) {
				logger.error("签字回调失败:id=" + id);
				return "close|" + id;
			}
		}else{
			//接种台签字+非常规免疫签字
			try {
				if(id.contains("A")){
					//初始化查询单条记录（犬伤）
					BsRabiesNum bsRabiesNum = new BsRabiesNum();
					bsRabiesNum = bsRabiesNumService.get(id.substring(1));
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
							logger.info("签字回调成功 -->" + "ok|" + id.substring(1));
							return "ok|" + id.substring(1);
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
								logger.info("签字回调成功 -->" + "ok|" + id.substring(1));
								return "ok|" + id.substring(1);
							}
						}
					}
				}else if(id.contains("B")){
					//初始化查询单条记录（成人）
					BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
					bsHepatitisBNum = bsHepatitisBNumService.get(id.substring(1));
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
							logger.info("签字回调成功 -->" + "ok|" + id.substring(1));
							return "ok|" + id.substring(1);
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
								logger.info("签字回调成功 -->" + "ok|" + id.substring(1));
								return "ok|" + id.substring(1);
							}
						}
					}
				}else{
					//签字状态（儿童）
					if(StringUtils.isNotBlank(signatureData)){
						ChildVaccinaterecord record = new ChildVaccinaterecord();
						record.setId(id);
						record.setStype(ChildVaccinaterecord.SIGNATURE_SOURCE_SELF);
						//签字转换
						BASE64Decoder decoder = new BASE64Decoder(); 
						byte[] data1 = decoder.decodeBuffer(signatureData);
						record.setSignatureData(data1);
						//查询该记录签字是否存在
						int count = childVaccinaterecordService.querySignature(record);
						if(count == 0){
							//新增签字
							childVaccinaterecordService.insertSignatures(record);
						}
						//修改签字状态
						record.setSignature(ChildVaccinaterecord.SIGNATURE_YES);
						childVaccinaterecordService.updateSignatures(record);
						//推送前台
						logger.info("签字回调成功 -->" + "ok|" + id);
						return "ok|" + id;
					}
				}
			} catch (Exception e) {
				logger.info("签字回调失败，param=" + id,e);
				return "close|" + id; 
			}		
			logger.info("签字回调失败，无签字保存操作 -->" + "close|" + id);
			return "close|" + id;
		}
	}
	
	/**
	 * 支付-线下支付扫码枪回调接口
	 * 成人直接扫码确认接口，（不用收银台时收费确认）
	 * @author fuxin
	 * @date 2017年8月10日 上午11:09:34
	 * @description 
	 *		http://127.0.0.1:8080/vaccadult/f/api/offlinePayCallBack.do?sessionId=0b3c3071688a40aa93c5cf1c7f540191_05df4ab1befa47a289a22a42c3eb3ae9,9e13b0e25c7648ada6cf16acfe1bf0cd
	 * @param request
	 * @param sessionId
	 * @return
	 *
	 */
	@RequestMapping("/offlinePayCallBack")
	public @ResponseBody String offlinePayCallBack(HttpServletRequest request, @RequestParam(required = false) String sessionId) {
		logger.info("接到线下支付回调，param=" + sessionId);
		if(StringUtils.isBlank(sessionId)){
			logger.error("sessionId为空，本次操作失败");
			return "error";
		}
		String[] ss = sessionId.split("_");
		if(ss.length == 2){
			logger.info("直接扫码确认接口儿童" + sessionId);
			return queneService.offlinePayCallBack(sessionId);
		}else if(ss.length == 3){
			try {
				String check = bsRabiesNumService.queryPay(ss[1]);
				if(check == null){
					logger.error("库存不足");
					return "error";
				}
				String[] chenks = check.split(","); 
				List<String> checkList = new ArrayList<String>();
				//修改付款状态为1
				if(ss[2].equals("1")){
					for(String checkId : chenks){
						BsRabiesNum bsRabiesNum = bsRabiesNumService.get(checkId);
						if(bsRabiesNum == null){
							logger.error("小票无效，请前往咨询台咨询");
							return "error";
						}
						if(BsRabiesNum.STATUS_PAYMAL.equals(bsRabiesNum.getPaystatus())){
							logger.error("小票已付费");
							return "error";
						}
						if(!BsRabiesNum.STATUS_NORMAL.equals(bsRabiesNum.getPaystatus())){
							logger.error("小票无效，请前往咨询台咨询");
							return "error";
						}
						bsRabiesCheckinService.updateByPayStatus(checkId);
						checkList.add(checkId);
					}
				}else if(ss[2].equals("2")){
					for(String checkId : chenks){
						BsHepatitisBNum bsHepatitisBNum = bsHepatitisBNumService.get(checkId);
						if(bsHepatitisBNum == null){
							logger.error("小票无效，请前往咨询台咨询");
							return "error";
						}
						if(BsHepatitisBNum.STATUS_PAYMAL.equals(bsHepatitisBNum.getPayStatus())){
							logger.error("小票已付费");
							return "error";
						}
						if(!BsHepatitisBNum.STATUS_NORMAL.equals(bsHepatitisBNum.getPayStatus())){
							logger.error("小票无效，请前往咨询台咨询");
							return "error";
						}
						bsHepatitisBcheckinService.updateByPayStatus(checkId);
						checkList.add(checkId);
					}
				}
				DwrUtils.send(ss[0], JsonUtil.toJson(checkList));
				DwrUtils.send(ss[0], "");
				logger.info("线下支付回调成功，param=" + sessionId);
			} catch (Exception e) {
				logger.info("线下支付回调失败，param=" + sessionId,e);
			}	
			
		} else{
			logger.info("sessionId有误，本次操作失败");
			return "error";
		}
			
		return "success";
	}
	
	/**
	 * 支付系统接扫码枪统一接口
	 * @author zhaojing
	 * @date 2017年11月2日  上午11:22:28
	 * @description 
	 *		TODO
	 * @param sessionId 请求数据
	 * @return
	 *
	 */
	@RequestMapping("/saveToPage")
	@ResponseBody 
	public String saveToPage(HttpServletRequest request, @RequestParam(required = false) String sessionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		BsRabiesCheckin rabcheckin = new BsRabiesCheckin();
		BsRabiesCheckinDeal bsRabiesCheckinDeal = new BsRabiesCheckinDeal();
		//获取回调参数
		logger.info("接到线下支付回调，param=" + sessionId);
		if(StringUtils.isBlank(sessionId)){
			logger.error("sessionId为空，本次操作失败");
			map.put("result", false);
			map.put("message", "sessionId为空，请前往咨询台咨询");
			return new JsonMapper(Include.ALWAYS).toJson(map);
		}
		String[] ss = sessionId.split("_");
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, String>> vacc = new ArrayList<Map<String, String>>();
		if(ss.length == 2){
			//查询排号信息
			Quene tempQuene = new Quene();
			tempQuene.setQueueno(ss[0]);
			tempQuene.setLocalCode(ss[1]);
			tempQuene.setStatus(null);
			List<Quene> queues = queneService.findList(tempQuene);
			if(queues.size() == 0){
				map.put("result", false);
				map.put("message", "排号信息不存在，请前往咨询台咨询");
				return new JsonMapper(Include.ALWAYS).toJson(map);
			}else{
				if(Quene.STATUS_NORMAL.equals(queues.get(0).getStatus())){
					map.put("result", false);
					map.put("message", "小票已付费，请前往咨询台咨询");
					return new JsonMapper(Include.ALWAYS).toJson(map);
				}
				if(!Quene.STATUS_WAITPAY.equals(queues.get(0).getStatus())){
					map.put("result", false);
					map.put("message", "小票无效，请前往咨询台咨询");
					return new JsonMapper(Include.ALWAYS).toJson(map);
				}
			}
			Quene queue = queues.get(0);
			ChildBaseinfo baseinfo = childBaseinfoService.getByNo(queue.getChildid(), ss[1]);
			Office office = officeService.getOfficeByCode(ss[1]);
			data.put("PATIENTID", queue.getLocalCode() + "_" + queue.getQueueno() + "_" + baseinfo.getId());	//记录信息
			data.put("PATIENT_NAME", baseinfo.getChildname());						//用户名
			vacc.add(bsChargeLogService.getChargeVacc(queue,office));
			if(vacc.get(0).isEmpty()){
				logger.error("疫苗产品信息不存在，本次操作失败");
				map.put("result", false);
				map.put("message", "疫苗产品信息不存在，请前往咨询台咨询");
				return new JsonMapper(Include.ALWAYS).toJson(map);
			}
			data.put("vacc", vacc);
		}else{
			if(ss.length != 3){
				logger.error("sessionId有误，本次操作失败");
				map.put("result", false);
				map.put("message", "sessionId有误，请前往咨询台咨询");
				return new JsonMapper(Include.ALWAYS).toJson(map);
			}
			try {
				String check = bsRabiesNumService.queryPay(ss[1]);
				if(check == null){
					logger.error("小票记录不存在，本次操作失败");
					map.put("result", false);
					map.put("message", "小票记录不存在，请前往咨询台咨询");
					return new JsonMapper(Include.ALWAYS).toJson(map);
				}
				String[] chenks = check.split(",");
				Office office = null;
				User user = null;
				if(BsChargeLog.MODULE_DOG.equals(ss[2])){		
					//犬伤
					for(int i = 0; i < chenks.length; i++){
						BsRabiesNum bsRabiesNum = bsRabiesNumService.get(chenks[i]);
						if(bsRabiesNum == null){
							bsRabiesCheckinDeal.setId(chenks[i]);
							bsRabiesCheckinDeal.setCheckinId(rabcheckin.getId());
							List<BsRabiesCheckinDeal>list = bsRabiesNumService.findCheckinDeal(bsRabiesCheckinDeal);
							if(list.size() == 0){
								map.put("result", false);
								map.put("message", "接种记录不存在，请前往咨询台咨询");
								return new JsonMapper(Include.ALWAYS).toJson(map);
							}
							bsRabiesCheckinDeal = list.get(0);
							
						}else{
							if(BsRabiesNum.STATUS_PAYMAL.equals(bsRabiesNum.getPaystatus())){
								map.put("result", false);
								map.put("message", "小票已付费，请前往咨询台咨询");
								return new JsonMapper(Include.ALWAYS).toJson(map);
							}
							if(!BsRabiesNum.STATUS_NORMAL.equals(bsRabiesNum.getPaystatus())){
								map.put("result", false);
								map.put("message", "小票无效，请前往咨询台咨询");
								return new JsonMapper(Include.ALWAYS).toJson(map);
							}
						}
						if(bsRabiesNum == null){
							user = UserUtils.get(bsRabiesCheckinDeal.getCreateBy().getId());
						}else{
							user = UserUtils.get(bsRabiesNum.getCreateBy().getId());
						}
						
						office = officeService.get(user.getCompany().getId());
						if(i == 0) {
							rabcheckin = bsRabiesCheckinService.get(bsRabiesNum.getCheckid());
							data.put("PATIENTID", check + "-1");				//记录信息
							data.put("PATIENT_NAME", rabcheckin.getUsername());	//用户名
						}
						vacc.add(bsChargeLogService.getChargeVacc(bsRabiesNum,bsRabiesCheckinDeal,office));
						if(vacc.get(0).isEmpty()){
							logger.error("疫苗产品信息不存在，本次操作失败");
							map.put("result", false);
							map.put("message", "疫苗产品信息不存在，请前往咨询台咨询");
							return new JsonMapper(Include.ALWAYS).toJson(map);
						}
					}
					//合并疫苗同类并返还
					if(vacc.size() > 0){
						vacc = bsChargeLogService.ConversionByVacc(vacc);
					}
					data.put("vacc", vacc);
				}else if(BsChargeLog.MODULE_ADULT.equals(ss[2])){	
					//成人
					for(int i = 0; i < chenks.length; i++){
						BsHepatitisBNum bsHepatitisBNum = bsHepatitisBNumService.get(chenks[i]);
						if(bsHepatitisBNum == null){
							map.put("result", false);
							map.put("message", "接种记录不存在，请前往咨询台咨询");
							return new JsonMapper(Include.ALWAYS).toJson(map);
						}else{
							if(BsHepatitisBNum.STATUS_PAYMAL.equals(bsHepatitisBNum.getPayStatus())){
								map.put("result", false);
								map.put("message", "小票已付费，请前往咨询台咨询");
								return new JsonMapper(Include.ALWAYS).toJson(map);
							}
							if(!BsHepatitisBNum.STATUS_NORMAL.equals(bsHepatitisBNum.getPayStatus())){
								map.put("result", false);
								map.put("message", "小票无效，请前往咨询台咨询");
								return new JsonMapper(Include.ALWAYS).toJson(map);
							}
						}
						user = UserUtils.get(bsHepatitisBNum.getCreateBy().getId());
						office = officeService.get(user.getCompany().getId());
						if(i == 0) {
							BsHepatitisBcheckin hepcheckin = bsHepatitisBcheckinService.get(bsHepatitisBNum.getCheckId());
							data.put("PATIENTID", check + "-2");
							data.put("PATIENT_NAME", hepcheckin.getUsername());
						}
						vacc.add(bsChargeLogService.getChargeVacc(bsHepatitisBNum,office));
						if(vacc.get(0).isEmpty()){
							logger.error("疫苗产品信息不存在，本次操作失败");
							map.put("result", false);
							map.put("message", "疫苗产品信息不存在，请前往咨询台咨询");
							return new JsonMapper(Include.ALWAYS).toJson(map);
						}
					}
					//合并疫苗同类并返还
					if(vacc.size() > 0){
						vacc = bsChargeLogService.ConversionByVacc(vacc);
					}
					data.put("vacc", vacc);
				}
			} catch (Exception e) {
				logger.error("扫码接口异常操作失败，本次操作失败！",e);
				map.put("result", false);
				map.put("message", "扫码接口异常操作失败，请前往咨询台咨询！");
				return new JsonMapper(Include.ALWAYS).toJson(map);
			}	
		}
		data.put("PAY_SESSION_PARAM", sessionId);
		data.put("result", true);
		data.put("message", "扫码成功！");
		logger.info("接到线下支付回调，param=" + sessionId +"，操作成功！");
		return new JsonMapper(Include.ALWAYS).toJson(data);
	}
	
	/**
	 * 检查预约rid是否有签字 
	 * @author fuxin
	 * @date 2017年12月28日 上午1:09:53
	 * @description 
	 *		TODO
	 * @param rid
	 * @return
	 *
	 */
	@RequestMapping("/hasWxSign")
	@ResponseBody
	Map<String, Object> hasWxSign(@RequestParam(required=false, defaultValue="")String rid){
		logger.info("获取签字开始，参数：" + rid);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String,Object>();
		//map.put("0", "0");
		returnMap.put("code", "500");
		returnMap.put("msg", "获取失败");
		returnMap.put("sign", map);
		Map<String, String> signs = new HashMap<String, String>();
		if(StringUtils.isNotBlank(rid)){
			if(rid.indexOf("_") > 0){
				String[] rids = rid.split("_");
				for(String r : rids){
					VacChildRemind vacRemind = vacChildRemindService.getWxSignData(r);
					if(vacRemind != null && StringUtils.isNotBlank(vacRemind.getSignature())){
						signs.put(r,vacRemind.getSignature());
					}
				}
				returnMap.put("code", "200");
				returnMap.put("msg", "已签字");
				returnMap.put("sign", signs);
			}else{
				VacChildRemind vacRemind = vacChildRemindService.getWxSignData(rid);
				if(vacRemind != null && StringUtils.isNotBlank(vacRemind.getSignature())){
					returnMap.put("code", "200");
					returnMap.put("msg", "已签字");
					signs.put(rid,vacRemind.getSignature());
					returnMap.put("sign", signs);
				}
			}
		}
		logger.info("获取签字结束，返回值：" + JsonMapper.toJsonString(returnMap));
		return returnMap;
	}
}
