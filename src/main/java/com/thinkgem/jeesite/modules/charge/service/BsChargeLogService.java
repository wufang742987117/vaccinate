/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.charge.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.LinkedMap;
import org.directwebremoting.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.dwr.DwrUtils;
import com.thinkgem.jeesite.modules.charge.dao.BsChargeLogDao;
import com.thinkgem.jeesite.modules.charge.entity.BsChargeLog;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.hepatitis.service.BsHepatitisBcheckinService;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.service.BsHepatitisBNumService;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.service.BsRabiesNumService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinDeal;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 门诊收费Service
 * @author zhaojing
 * @version 2017-10-26
 */
@Service
@Transactional(readOnly = true)
public class BsChargeLogService extends CrudService<BsChargeLogDao, BsChargeLog> {
	
	@Autowired
	private BsManageProductService bsManageProductService;
	@Autowired
	private BsRabiesNumService bsRabiesNumService;
	@Autowired
	private BsRabiesCheckinService bsRabiesCheckinService;
	@Autowired
	private BsHepatitisBNumService bsHepatitisBNumService;
	@Autowired
	private BsHepatitisBcheckinService bsHepatitisBcheckinService;
	@Autowired
	private QueneService queneService;
	@Autowired
	private ChildVaccinaterecordService childVaccinaterecordService;
	
	public BsChargeLog get(String id) {
		return super.get(id);
	}
	
	public List<BsChargeLog> findList(BsChargeLog bsChargeLog) {
		return super.findList(bsChargeLog);
	}
	
	public Page<BsChargeLog> findPage(Page<BsChargeLog> page, BsChargeLog bsChargeLog) {
		return super.findPage(page, bsChargeLog);
	}
	
	@Transactional(readOnly = false)
	public void save(BsChargeLog bsChargeLog) {
		super.save(bsChargeLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsChargeLog bsChargeLog) {
		super.delete(bsChargeLog);
	}

	/**
	 * 扫码根据排号信息组装查询打印发票数据（儿童）
	 * @author zhaojing
	 * @date 2018年1月1日 下午4:48:47
	 * @description 
	 *		TODO
	 * @param queue
	 * @param office
	 * @return
	 *
	 */
	public Map<String, String> getChargeVacc(Quene queue,Office office){
		Map<String, String> vacc = new HashMap<String, String>();
		BsManageProduct product = bsManageProductService.get(queue.getPid());
		vacc.put("VACCINEID", product.getVaccineid());
		vacc.put("BATCHNUM", product.getBatchno());
		vacc.put("MANUFACTURER", product.getManufacturer());
		vacc.put("DOSE", product.getSpecification());
		vacc.put("COUNT", "1");
		vacc.put("PID", product.getId());
		vacc.put("fundStatus", queue.getFundStatus());
		vacc.put("currentPrice", String.valueOf(queue.getCurrentPrice()));
		vacc = getVaccInfo(vacc,office);
		return vacc;
	}
	
	/**
	 * 扫码组装查询打印发票数据（狂犬病）
	 * @author fuxin
	 * @date 2018年1月2日 下午9:30:50
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param office
	 * @return
	 *
	 */
	public Map<String, String> getChargeVacc(BsRabiesNum bsRabiesNum, BsRabiesCheckinDeal bsRabiesCheckinDeal, Office office){
		Map<String, String> vacc = new HashMap<String, String>();
		if(bsRabiesNum != null){
			vacc.put("VACCINEID", bsRabiesNum.getVaccineid());
			vacc.put("BATCHNUM", bsRabiesNum.getBatchnum());
			vacc.put("MANUFACTURER", bsRabiesNum.getManufacturer());
			vacc.put("DOSE", bsRabiesNum.getDose());
			vacc.put("PID", bsRabiesNum.getPid());
			vacc.put("fundStatus", bsRabiesNum.getFundStatus());
			vacc.put("currentPrice", String.valueOf(bsRabiesNum.getCurrentPrice()));
			vacc.put("COUNT", "1");
			vacc = getVaccInfo(vacc,office);
		}else if(bsRabiesCheckinDeal != null){
			vacc.put("VACC_NAME", "伤口处理费");
			vacc.put("MANUFACTURER", "");
			vacc.put("BATCHNUM", "");
			vacc.put("officeCodeDb", "");
			vacc.put("SPECIFICATIONVALUE", "");
			vacc.put("SELLPRICE", String.valueOf(bsRabiesCheckinDeal.getPrice()));
			vacc.put("PID", bsRabiesCheckinDeal.getId());
			vacc.put("COUNT", "1");
		}
		
		return vacc;
	}
	
	/**
	 * 扫码组装查询打印发票数据（狂犬病）
	 * @author fuxin
	 * @date 2018年1月2日 下午9:31:10
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param office
	 * @return
	 *
	 */
	public Map<String, String> getChargeVacc(BsHepatitisBNum bsHepatitisBNum,Office office){
		Map<String, String> vacc = new HashMap<String, String>();
		vacc.put("VACCINEID", bsHepatitisBNum.getVaccineId());
		vacc.put("BATCHNUM", bsHepatitisBNum.getBatch());
		vacc.put("MANUFACTURER", bsHepatitisBNum.getManufacturer());
		vacc.put("DOSE", bsHepatitisBNum.getStandard());
		vacc.put("COUNT", "1");
		vacc.put("PID", bsHepatitisBNum.getPid());
		vacc.put("fundStatus", bsHepatitisBNum.getFundStatus());
		vacc.put("currentPrice", String.valueOf(bsHepatitisBNum.getCurrentPrice()));
		vacc = getVaccInfo(vacc,office);
		return vacc;
	}
	
	/**
	 * 组装打印发票数据
	 * @author fuxin
	 * @date 2018年1月2日 下午9:26:17
	 * @description 
	 *		TODO
	 * @param vacc
	 * @param office
	 * @return
	 *
	 */
	private Map<String, String> getVaccInfo(Map<String, String> vacc, Office office){
		vacc.put("localCode", office.getCode());
		Map<String, String> qvacc = dao.getVaccById(vacc);
		if(qvacc == null){
			vacc =  new HashMap<String, String>();
		}else{
			vacc.put("VACC_NAME", qvacc.get("VACC_NAME"));
			vacc.put("SPECIFICATIONVALUE",qvacc.get("SPECIFICATIONVALUE"));
			if(BsChargeLog.FUNDSTATUS_YES.equals(vacc.get("fundStatus"))){
				vacc.put("SELLPRICE", vacc.get("currentPrice"));
			}else{
				vacc.put("SELLPRICE", String.valueOf(qvacc.get("SELLPRICE")));
			}
			vacc.put("PID", qvacc.get("ID"));
		}
		return vacc;
	}
	
	/**
	 * 发票总览数据
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:58:50
	 * @description 
	 *		TODO
	 * @param page
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public Page<BsChargeLog> findListOverview(Page<BsChargeLog> page, BsChargeLog bsChargeLog) {
		bsChargeLog.setPage(page);
		page.setList(dao.findListOverview(bsChargeLog));
		return page;
	}

	/**
	 * 发票明细数据
	 * @author zhouqj
	 * @date 2018年2月5日 下午4:59:06
	 * @description 
	 *		TODO
	 * @param page
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public Page<BsChargeLog> findListDetails(Page<BsChargeLog> page, BsChargeLog bsChargeLog) {
		bsChargeLog.setPage(page);
		page.setList(dao.findListDetails(bsChargeLog));
		return page;
	}
	
	/**
	 * 作废发票
	 * @author zhouqj
	 * @date 2018年1月22日 上午10:01:14
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@Transactional(readOnly = false)
	public String deleteTicketData(BsChargeLog bsChargeLog) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取参数
		logger.info("接到报废发票-->" + bsChargeLog.getBillNum());
		//查询发票包含记录
		String patientId = getPatientIdByTicket(bsChargeLog);
		if(StringUtils.isNotBlank(patientId)){
			//常规免疫
			if(patientId.contains("_")){
				logger.info("<--报废发票常规免疫开始-->");
				String[] chenks = patientId.split("_");
				Quene queue = new Quene();
				queue.setQueueno(chenks[1]);
				queue.setLocalCode(chenks[0]);
				queue.setStatus(StringUtils.EMPTY);
				List<Quene> queues = queneService.findList(queue);
				if(queues.size() == 0){
					logger.error("线下作废回调失败，排号数据不存在");
					data.put("result", false);
					data.put("message", "线下作废回调失败，排号数据不存在");
					return new JsonMapper(Include.ALWAYS).toJson(data);
				}
				//排号变为等待缴费
				logger.info("<--获取排号开始-->");
				queue = queues.get(0);
				logger.info("<--获取排号成功-->");
				//判断排号信息是否存在问题
				if(queue == null || StringUtils.isBlank(queue.getQueueno())){
					logger.error("线下作废回调失败，排号状态异常" + JsonMapper.toJsonString(queue));
					data.put("result", false);
					data.put("message", "线下作废回调失败，排号状态异常");
					return new JsonMapper(Include.ALWAYS).toJson(data);
				}
				queue.setStatus(Quene.STATUS_WAITPAY);
				logger.info("<--作废排号开始-->");
				queneService.refundById(queue);
				//刷新叫号屏
				queneService.refresh(chenks[0]);
				logger.info("<--作废排号成功-->");
				//查询接种记录状态
				ChildVaccinaterecord childVaccinaterecord = new ChildVaccinaterecord();
				childVaccinaterecord.setNid(queue.getVaccineid());
				childVaccinaterecord.setChildid(chenks[2]);
				childVaccinaterecord.setLocalCode(chenks[0]);
				childVaccinaterecord.setPayStatus(ChildVaccinaterecord.PAY_STATUS_NO);
				//记录变变为未付
				logger.info("<--作废记录开始-->");
				childVaccinaterecordService.refundById(childVaccinaterecord);
				logger.info("<--作废记录开始-->");
				logger.info("<--报废发票常规免疫结束-->");
			}
			//非常规免疫
			if(patientId.contains("-")){
				logger.info("<--报废发票非常规免疫开始-->");
				String[] chenks = patientId.split("-");
				String[] str = chenks[0].split(",");
				if(BsChargeLog.MODULE_DOG.equals(chenks[1])){
					logger.info("<--报废发票犬伤开始-->");
					//犬伤
					BsRabiesNum bsRabiesNum = new BsRabiesNum();
					for(int i = 0; i < str.length; i++){
						bsRabiesNum.setId(str[i]);
						bsRabiesNum.setPaystatus(BsRabiesNum.STATUS_NORMAL);
						logger.info("<--作废记录开始【"+ str[i] +"】-->");
						bsRabiesNumService.refundById(bsRabiesNum);
						logger.info("<--作废记录结束【"+ str[i] +"】-->");
					}
					logger.info("<--报废发票犬伤结束-->");
				}else if(BsChargeLog.MODULE_ADULT.equals(chenks[1])){
					logger.info("<--报废发票成人开始-->");
					//成人
					BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
					for(int i = 0; i < str.length; i++){
						bsHepatitisBNum.setId(str[i]);
						bsHepatitisBNum.setPayStatus(BsHepatitisBNum.STATUS_NORMAL);
						logger.info("<--作废记录开始【"+ str[i] +"】-->");
						bsHepatitisBNumService.refundById(bsHepatitisBNum);
						logger.info("<--作废记录结束【"+ str[i] +"】-->");
					}
					logger.info("<--报废发票成人结束-->");
				}
				logger.info("<--报废发票非常规免疫结束-->");
			}
		}
		logger.info("报废发票结束-->" + bsChargeLog.getBillNum());
		//作废发票
		int count = deleteTicket(bsChargeLog);
		if (count > 0) {
			data.put("billNum", bsChargeLog.getBillNum());
			data.put("result", true);
			data.put("message", "退费成功");
			return new JsonMapper(Include.ALWAYS).toJson(data);
		}else {
			data.put("result", false);
			data.put("message", "线下作废回调失败，退费发票数量为0");
			return new JsonMapper(Include.ALWAYS).toJson(data);
		}
	}
	
	/**
	 * 查询业务流水号所属记录
	 * @author zhouqj
	 * @date 2018年1月16日 下午9:57:47
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public String getPatientIdByTicket(BsChargeLog bsChargeLog) {
		return dao.getPatientIdByTicket(bsChargeLog);
	}
	
	/**  
	* zhaojing
	* 2017年11月2日  下午4:30:23
	* 作废发票，返回作废记录数量
	*/  
	public int deleteTicket(BsChargeLog bsChargeLog){
		return dao.updateTicketStatus(bsChargeLog);
	}
	
	/**
	 * 修改开票状态
	 * @author zhouqj
	 * @date 2018年1月22日 下午3:13:49
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	public int updateBilling(BsChargeLog bsChargeLog) {
		return dao.updateBilling(bsChargeLog);
	}
	
	/**  
	* zhaojing
	* 2017年11月2日  下午1:32:10
	* 获得当前业务流水号
	*/  
	public String getBillNumCurral() {
		int count = getBillNumByUserCount();
		if(count == 0){
			return null;
		}
		int num = dao.getBillNumCurral(OfficeService.getFirstOfficeCode(), UserUtils.getUser().getId());
		return String.format("%010d", num);
	};
	
	/**  
	* zhaojing
	* 2017年11月2日  下午1:27:49
	* 查询疫苗名称列表
	*/  
	public List<Map<String,String>> getVaccList(){
		return dao.getVaccList(OfficeService.getFirstOfficeCode());
	}
	
	/**  
	* zhaojing
	* 2017年11月2日  下午1:29:17
	* 按照id，批号，厂家查询疫苗相关信息
	* @param map 包含编号，批号，厂家 
	*/  
	public Map<String,String> getVaccById(Map<String,String> map){
		map.put("localCode", OfficeService.getFirstOfficeCode());
		return dao.getVaccById(map);
	}
	
	/**
	 * 更新付费状态，并保存发票信息
	 * @author fuxin
	 * @date 2018年1月2日 下午10:34:19
	 * @description 
	 *		TODO
	 * @param sessionId
	 * @param data
	 * @return
	 *
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> saveChargeData(String sessionId, Map<String, Object> data) {
		Map<String, Object> map = new HashMap<String, Object>();
		//业务流水号统一验证方法
		String billNum = verificationBillNum(String.valueOf(data.get("billNum")));
		int count = getBillNumCount(billNum);
		if(count > 0){
			map.put("result", false);
			map.put("message", "业务流水号数据库已存在，不可重复");
			return map;
		}
		if(!StringUtils.isBlank(sessionId)){
			String[] ss = sessionId.split("_");
			//参数两个位儿童
			if(ss.length == 2){
				logger.info("打印儿童发票" + sessionId);
				//更新排号状态
				//查询排号信息
				Quene tempQuene = new Quene();
				tempQuene.setQueueno(ss[0]);
				tempQuene.setLocalCode(ss[1]);
				tempQuene.setStatus(Quene.STATUS_WAITPAY);
				List<Quene> queues = queneService.findList(tempQuene);
				if(queues.size() == 0){
					map.put("result", false);
					map.put("message", "排号数据不存在");
					return map;
				}
				Quene quene = queues.get(0);
				//判断排号信息是否存在问题
				if(quene == null || StringUtils.isBlank(quene.getQueueno()) || !Quene.STATUS_WAITPAY.equals(quene.getStatus())){
					logger.error("线下支付回调失败，排号状态异常" + JsonMapper.toJsonString(quene));
					map.put("result", false);
					map.put("message", "queue is finished, request failed [ " + sessionId + " ]");
					return map;
				}
				quene.setStatus(Quene.STATUS_NORMAL);
				queneService.update(quene);
				//查询接种记录状态
				ChildVaccinaterecord rcv = new ChildVaccinaterecord();
				rcv.setChildcode(quene.getChildid());
				rcv.setNid(quene.getVaccineid());
				rcv.setStatus(ChildVaccinaterecord.STATUS_NOT);
				List<ChildVaccinaterecord> rcvs = childVaccinaterecordService.findList(rcv);
				//更新接种记录状态
				if(rcvs.size()>0){
					for(ChildVaccinaterecord r : rcvs){
						r.setPayStatus(ChildVaccinaterecord.PAY_STATUS_YES);
						childVaccinaterecordService.save(r);
					}
				}
				//刷新叫号屏
				queneService.refresh(ss[1]);
				logger.info("线下回调成功-->" + quene.getQueueno());
			}else{	
				//参数三个为犬伤和成人
				logger.info("打印成人发票" + sessionId);
				if(ss.length != 3){
					logger.info("sessionId有误，本次操作失败");
					map.put("result", false);
					map.put("message", "参数错误，不为狂犬病或成人所需参数");
					return map;
				}
				String check = bsRabiesNumService.queryPay(ss[1]);
				String[] chenks = check.split(",");
				List<String> checkList = new ArrayList<String>();
				//修改付款状态为1
				if(BsChargeLog.MODULE_DOG.equals(ss[2])){
					for(String checkId : chenks){
						bsRabiesCheckinService.updateByPayStatus(checkId);
						checkList.add(checkId);
					}
				}else if(BsChargeLog.MODULE_ADULT.equals(ss[2])){
					for(String checkId : chenks){
						bsHepatitisBcheckinService.updateByPayStatus(checkId);
						checkList.add(checkId);
					}
				}
				try {
					logger.info("保存成功，打印发票" + JsonUtil.toJson(checkList));
					DwrUtils.send(ss[0], JsonUtil.toJson(checkList));
					DwrUtils.send(ss[0], "");
					logger.info("保存发票信息回调成功，param=" + sessionId);
				} catch (Exception e) {
					logger.error("保存发票信息注射台回调失败，param=" + sessionId + e.getMessage());
					map.put("result", false);
					map.put("message", "狂犬病或成人完成缴费状态异常");
					return map;
				}	
			}
		}
		if(saveChargeLog(data)) {
			logger.info("记录保存成功，param=" + sessionId);
			getBillNumNextval();
		}else {
			logger.info("保存记录失败，param=" + sessionId);
			map.put("result", false);
			map.put("message", "发票保存记录失败,疫苗数据不存在或支付金额问题");
			return map;
		}
		map.put("result", true);
		map.put("message", "发票保存记录成功");
		return map;
	}

	/**
	 * 查询业务流水号是否存在
	 * @author zhouqj
	 * @date 2018年2月7日 上午10:17:32
	 * @description 
	 *		TODO
	 * @param billNum
	 * @return
	 *
	 */
	public int getBillNumCount(String billNum) {
		return dao.getBillNumCount(billNum, OfficeService.getFirstOfficeCode());
	}

	/**  
	* zhaojing
	* 2017年11月2日  下午1:30:01
	* 保存付款信息
	*/  
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public Boolean saveChargeLog(Map<String,Object> map){
		BsChargeLog bslog = new BsChargeLog();
		ArrayList<Map<String,String>> vaccs = (ArrayList<Map<String,String>>) map.get("vacc");
		if(vaccs.size() > 0) {
			//业务流水号统一验证方法
			String billNum = verificationBillNum(String.valueOf(map.get("billNum")));
			bslog.setBillNum(billNum);
			bslog.setPatientName(String.valueOf(map.get("patientName")));
			bslog.setPatientId(String.valueOf(map.get("patientId")));
			double refund = Double.parseDouble(String.valueOf(map.get("refund")));
			if(refund < 0){
				return false;
			}
			bslog.setSumPrice(Double.parseDouble(String.valueOf(map.get("sumPrice"))));
			bslog.setPay(Double.parseDouble(String.valueOf(map.get("pay"))));
			bslog.setRefund(refund);
			bslog.setBilling(String.valueOf(map.get("billing")));
			for (int i = 0; i < vaccs.size(); i++) {
				Map<String,String> vacc = vaccs.get(i);
				if(vacc.containsKey("vaccCode")){
					bslog.setVaccCode(vacc.get("vaccCode"));
					bslog.setVaccBatchnum(vacc.get("batchnum"));
					bslog.setVaccManufacturer(vacc.get("manufacturer"));
					bslog.setVaccDose(vacc.get("dose"));
					bslog.setType(BsChargeLog.TYPE_VACC);
				}else{
					bslog.setVaccCode("1");
					bslog.setVaccBatchnum("1");
					bslog.setVaccManufacturer("1");
					bslog.setVaccDose("1");
					bslog.setType(BsChargeLog.TYPE_WOUN);
				}
				bslog.setVaccPrice(Double.parseDouble(vacc.get("vaccPrice")));
				bslog.setVaccName(vacc.get("vaccName"));
				bslog.setPid(vacc.get("pid"));
				bslog.setVaccCount(Integer.parseInt(vacc.get("vaccCount")));
				bslog.setCreateDate(new Date());
				bslog.setCreateBy(UserUtils.getUser());
				bslog.setStatus(1);
				bslog.preInsert();
				super.insert(bslog);
			}
			//保存发票编号
			updateBillNum(billNum);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 根据前台输入发票编号修改发票编号
	 * @author yangjian
	 * @date 2018年1月26日下午16:17:14
	 * @description 
	 *		TODO
	 * @param billNum
	 */
	@Transactional(readOnly = false)
	public void updateBillNum(String billNum){
		int count = getBillNumByUserCount();
		if(count > 0){
			dao.updateBillNum(Integer.valueOf(billNum), OfficeService.getFirstOfficeCode(), UserUtils.getUser().getId());
		}else{
			dao.insertBillNum(Integer.valueOf(billNum), OfficeService.getFirstOfficeCode(), UserUtils.getUser().getId());
		}
	}
	
	/**
	 * 查询改单位用户是否存在业务流水号
	 * @author zhouqj
	 * @date 2018年2月7日 上午10:48:31
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public int getBillNumByUserCount(){
		return dao.getBillNumByUserCount(OfficeService.getFirstOfficeCode(), UserUtils.getUser().getId());
	}
	
	/**
	 * 修改用户开票权限
	 * @author zhouqj
	 * @date 2018年2月7日 上午11:04:20
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	@Transactional(readOnly = false)
	public void updateUserBillingStatus(BsChargeLog bsChargeLog){
		dao.updateUserBillingStatus(bsChargeLog);
	}
	
	/**  
	* zhaojing
	* 2017年11月2日  下午1:31:43
	* 获得下一个业务流水号
	*/  
	@Transactional(readOnly = false)
	public String getBillNumNextval() {
		dao.getBillNumNextval(OfficeService.getFirstOfficeCode(), UserUtils.getUser().getId());
		int num = dao.getBillNumCurral(OfficeService.getFirstOfficeCode(), UserUtils.getUser().getId());
		return String.format("%010d", num);
	};
	
	/**
	 * chenming
	 * 根据业务流水号获取疫苗信息
	 */
	public List<BsChargeLog> findChargeCase(BsChargeLog bsChargeLog){
		return dao.findChargeCase(bsChargeLog);
	}
	
	/**
	 * 保存退费发票信息
	 * @author zhouqj
	 * @date 2018年2月6日 下午8:55:01
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return
	 *
	 */
	@Transactional(readOnly = false)
	public int updateCharge(BsChargeLog bsChargeLog) {
		return dao.updateCharge(bsChargeLog);
	}
	
	/**
	 * 更新发票退款金额
	 * @author zhouqj
	 * @date 2018年1月18日 下午8:41:46
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	@Transactional(readOnly = false)
	public void updateChargeList(BsChargeLog bsChargeLog) {
		dao.updateChargeList(bsChargeLog);
	}

	/**
	 * 更改未开票发票业务流水号
	 * @author zhouqj
	 * @date 2018年2月7日 下午1:28:07
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	@Transactional(readOnly = false)
	public void updateChargeByBillNum(BsChargeLog bsChargeLog) {
		dao.updateChargeByBillNum(bsChargeLog);
	}
	
	/**
	 * 统计发票中疫苗使用详情（分页）
	 * @author yangjian
	 * @date 2018年2月7日 下午1:28:07
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	public Page<BsChargeLog> PageChargeVaccine(Page<BsChargeLog> page, BsChargeLog bsChargeLog){
		bsChargeLog.setPage(page);
		page.setList(dao.findChargeVaccine(bsChargeLog));
		return page;
	}
	
	/**
	 * 统计发票中疫苗使用详情（不分页）
	 * @author yangjian
	 * @date 2018年2月7日 下午1:28:07
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	public List<BsChargeLog> findChargeVaccine(BsChargeLog bsChargeLog){
		return dao.findChargeVaccine(bsChargeLog);
	}
	
	/**
	 * 定时查询前一天发票记录 每天00:03
	 * @author yangjian
	 * @date 2018年1月25日 下午15:30
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 *
	 */
	public List<BsChargeLog> findDataListChargeEveryday(){
		return dao.findDataListChargeEveryday();
	}

	/**
	 * 统一验证业务流水号方法
	 * @author zhouqj
	 * @date 2018年2月8日 下午10:05:39
	 * @description 
	 *		TODO
	 * @param billNum
	 * @return
	 *
	 */
	public String verificationBillNum(String billNum) {
		if(StringUtils.isNotBlank(billNum)){
			int num = Integer.valueOf(billNum);
			return String.format("%010d", num);
		}
		return billNum;
	}

	/**
	 * 合并疫苗同类并返还
	 * @author zhouqj
	 * @date 2018年2月8日 下午11:48:31
	 * @description 
	 *		TODO
	 * @param vacc
	 * @return
	 *
	 */
	public List<Map<String, String>> ConversionByVacc(List<Map<String, String>> vacc) {
		//合并数量
		Map<String, Map<String, String>> treeMap = new LinkedMap();
		for(Map<String, String> tempMap : vacc){
			String tp = tempMap.get("PID");
			String pr = tempMap.get("SELLPRICE");
			if(treeMap.containsKey(tp+"_"+pr)){
				String c  = treeMap.get(tp+"_"+pr).get("COUNT");
				int ci = Integer.parseInt(c);
				treeMap.get(tp+"_"+pr).put("COUNT", (ci + 1) + "");
			}else{
				treeMap.put(tp+"_"+pr, tempMap);
			}
		}
		vacc =  new ArrayList<Map<String, String>>();
		for(Entry<String, Map<String, String>> treeEntry : treeMap.entrySet() ){
			vacc.add(treeEntry.getValue());
		}
		return vacc;
	}

	/**
	 * 疫苗数据联动接口
	 * @author zhouqj
	 * @date 2018年3月1日 下午4:20:01
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	public List<BsManageProduct> findViewList(BsManageProduct bsManageProduct) {
		return dao.findViewList(bsManageProduct);
	}
	
	/**
	 * 查询打印发票
	 * @author zhouqj
	 * @date 2018年3月7日 
	 * @description 
	 *		TODO
	 * @param bsChargeLog
	 * @return list
	 *
	 */
	public List<BsChargeLog> findPrintList(BsChargeLog bsChargeLog){
		return dao.findPrintList(bsChargeLog);
	}
}