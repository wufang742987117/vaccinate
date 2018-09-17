package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.charge.entity.BsChargeLog;
import com.thinkgem.jeesite.modules.charge.service.BsChargeLogService;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.dao.ChildVaccinaterecordDao;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.export.service.ExpRoutinevacc6_1Service;
import com.thinkgem.jeesite.modules.export.service.Exp_vacc_7_2Service;
import com.thinkgem.jeesite.modules.inoculate.dao.QueneDao;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.product.service.BsManageCheckService;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;
import com.thinkgem.jeesite.modules.remind.entity.VacChildRemind;
import com.thinkgem.jeesite.modules.remind.service.VacChildRemindService;
import com.thinkgem.jeesite.modules.sys.dao.BadSqlDao;
import com.thinkgem.jeesite.modules.sys.entity.BadSql;

/**
 * 定时任务
 * 
 * @author ngh
 * @datetime [2016年5月10日 上午11:06:20]
 *
 */
@Service
@Lazy(false)
@Async
public class JobService {

	private static final Logger logger = LoggerFactory.getLogger(JobService.class);

	@Autowired
	private QueneDao queneDao;
	@Autowired
	private BsManageProductService bsManageProductService;
	@Autowired
	private ExpRoutinevacc6_1Service expRoutinevacc6_1Service;
	@Autowired
	private BsRabiesCheckinService bsRabiesCheckinService;
	@Autowired
	private BsChargeLogService bsChargeLogService;
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private Exp_vacc_7_2Service exp_vacc_7_2Service;
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private ChildVaccinaterecordDao childVaccinaterecordDao;
	@Autowired
	BsManageCheckService bsManageCheckService;
	
	
	private static final String TYPE_VIEW="05";
	/**
	 * 每小时执行一次，超过一天未提交的订单，更改状态为客户池带领
	 */
	/*@Scheduled(cron = "0 0 0/1 * * ?")
	@Transactional(readOnly = false)
	public void updateLoanStatus13OverOneDay() {
		logger.info("定时任务解冻审查员开始");
		Long count =examinerDao.updateIsFreeze(); 
		logger.info("定时任务解冻审查员结束，更新条数：" + count);
	}*/
	
	/**
	 * 定时删除排号队列
	 * @author fuxin
	 * @date 2017年2月28日 下午6:05:29
	 * @description 
	 *		TODO
	 *
	 */
	@Scheduled(cron = "0 0 23 * * ?")
	@Transactional(readOnly = false)
	public void deleteQuene() {
		logger.info("定时任务删除排号队列-" + DateUtils.formatDateTime(new Date()));
		Long count = queneDao.clear();
		int rest = queneDao.findList(new Quene()).size();
		logger.info("定时任务删除排号队列结束，删除条数：" + count + " , 删除后数据库剩余条数：" + rest);
	}
	
	/**
	 * 定时删除排号队列 备用
	 * @author fuxin
	 * @date 2017年2月28日 下午6:05:29
	 * @description 
	 *		TODO
	 *
	 */
	@Scheduled(cron = "0 0 2 * * ?")
	@Transactional(readOnly = false)
	public void deleteQuene1() {
		logger.info("定时任务删除排号队列2-" + DateUtils.formatDateTime(new Date()));
		Long count = queneDao.clear();
		int rest = queneDao.findList(new Quene()).size();
		logger.info("定时任务删除排号队列2结束，删除条数：" + count + " , 删除后数据库剩余条数：" + rest);
	}
	
	/**
	 * 定时补传sql
	 * @author fuxin
	 * @date 2017年2月28日 下午6:05:29
	 * @description 
	 *		TODO
	 *
	 */
	@Scheduled(cron = "0 0 23 * * ?")
	public void reSendBadSql() {
		
		int threadNum = 0;
		String threadNumStr = Global.getConfig("dataSyncThreadNum");
		try {
			threadNum = Integer.parseInt(threadNumStr);
		} catch (Exception e) {
			logger.error("数据同步线程数配置失败，默认值为0");
		}
		if(threadNum > 0){
			logger.info("定时补传badsql-" + DateUtils.formatDateTime(new Date()));
			BadSqlDao badSqlDao = SpringContextHolder.getBean(BadSqlDao.class);
			List<BadSql> sqls = badSqlDao.findList(new BadSql());
			int successnum = 0;
			int failnum = 0;
			
			for(int i = 0; i < sqls.size(); i ++){
				String sql = sqls.get(i).getSqlContext();
				try {
					if(!"".equals(sql)){
						logger.info("badsql补传sql-" + i + " -->" + sql);
						String result = HttpClientReq.webServiceInvoke(Global.getConfig("uploadSql.url"), ExecSqlThread.methodNme, new Object[]{"3406031001",sql}, logger);
						if("1".equals(result)){
							badSqlDao.delete(sqls.get(i));
							logger.info("定时补传badsql-" + i + ":上传成功");
							successnum ++;
						}else{
							logger.info("定时补传badsql-" + i + ":上传失败");
							failnum++;
						}
					}
					Thread.sleep(100);
				} catch (InterruptedException e) {
					failnum ++;
					logger.error("定时补传badsql-" + i + "异常" + e);
				}
			}
			logger.info("定时补传badsql任务结束，成功次数：" + successnum + " , 失败此时：" + failnum);
		}else{
			logger.info("微信同步线程已关闭，补传任务不启动");
		}

	}
	
	/**
	 * 定时上传接种数据
	 * @author zhouqj
	 * @date 2017年6月8日 上午10:31:48
	 * @description 
	 *		TODO
	 *
	 */
//	@Scheduled(cron = "0 0 23 * * ?")
//	public void reSendBadVaccinate() {
//		logger.info("定时上传接种数据：" + DateUtils.formatDateTime(new Date()));
//		BillRecord billRecord = new BillRecord();
//		//默认当天时间
//		Date d[] = childVaccinaterecordService.dateTime(billRecord.getBeginVaccinatedate(), billRecord.getEndVaccinatedate());
//		billRecord.setBeginVaccinatedate(d[0]);
//		billRecord.setEndVaccinatedate(d[1]);
//		try {
//			//查询当天接种完成数据
//			List<BillRecord> billList = childVaccinaterecordService.querySendBadVaccinate(billRecord);
//			if(billList.size() != 0){
//				logger.info("接种完成记录查询成功" + JsonMapper.toJsonString(billList));
//				//发送数据上传请求
//				String result = sendWxMessage(Global.getConfig("pc.url") + "uploadVaccinate", JsonMapper.toJsonString(billList));
//				if(result == null || result.equals("")){
//					logger.info("定时上传接种数据失败，上传数量：" + billList.size());
//				}else{
//					logger.info("定时上传接种数据成功，上传数量：" + billList.size());
//				}
//			}else{
//				logger.info("接种完成记录查询成功,当天不存在新数据" + JsonMapper.toJsonString(billList));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("接种完成记录查询异常：" + e);
//		}
//		logger.info("定时上传接种数据结束：" + DateUtils.formatDateTime(new Date()));
//	}
	
	/**
	 * 发送post请求
	 * @author zhouqj
	 * @date 2017年6月9日 上午11:08:39
	 * @description 
	 *		TODO
	 * @param url
	 * @param json
	 * @return
	 *
	 */
	public String sendWxMessage(String url, String json){
		logger.info("发送post请求,url--> " + url + "  --> " + json);
		try {
			String str = HttpClientReq.httpClientPostJson(url, json);
			logger.error("发送post请求成功" + "info--> " + str);
			return str;
		} catch (Exception e) {
			logger.error("发送post请求失败");
		}
		return null;
	}
	
	
	@Scheduled(cron = "0 0 23 * * ?")
	@Transactional(readOnly = false)
	public void clearRest() {
		logger.info("定时清空多剂次未用完的rest-" + DateUtils.formatDateTime(new Date()));
		Long count = bsManageProductService.clearRest();
		logger.info("定时清空多剂次未用完的rest结束，删除条数：" + count);
	}
	
	/**
	 * 定时统计常规6-1数据
	 * @author Jack
	 * @date 2017年10月15日 下午4:11:43
	 * @description 
	 * 0 0 1 1 * ? 每月1号凌晨1点执行
	 */
	@Scheduled(cron = "0 0 1 1 * ?")
	public void countExpRoutinevacc6_1(){
		Calendar c = Calendar.getInstance();
		Integer nowYear = c.get(Calendar.YEAR);
		Integer nowMonth = c.get(Calendar.MONTH) + 1;
		
		//distinct查询SYS_COMMUNITY表中的所有LOCALCODE,遍历执行所有站点数据生成
		List<HashMap<String, String>> communityList = expRoutinevacc6_1Service.selectCommunityListNetVersion();
		
		for(HashMap<String, String> map : communityList){
			String localCode = map.get("LOCALCODE");
			if(nowMonth > 1){
				expRoutinevacc6_1Service.getDataByMonth(nowYear + "", nowMonth-1 + "", localCode);
			}else{
				expRoutinevacc6_1Service.getDataByMonth(nowYear-1 + "", 12 + "", localCode);
			}
		}
	}
	
	/**
	 * 定时统计7-2数据
	 * @author lm
	 * @date 2017年10月15日 下午4:11:43
	 * @description 
	 * 0 0 1 1 * ? 每月1号凌晨1点执行
	 */
	@Scheduled(cron = "0 50 0 1 * ?")
	@Transactional(readOnly = false)
	public void countExp_vacc_2(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Integer year = c.get(Calendar.YEAR);
		Integer month = c.get(Calendar.MONTH) + 1;
		exp_vacc_7_2Service.getMonthData(year+"", month+"");
//		if(nowMonth == 1){
//			//如果月份为1,统计上年所有数据
//			for(int i=1; i<=12; i++){
//				exp_vacc_2Service.getLastMonthData(nowYear-1 + "",i + "");
//			}
//		}else{
//			//如果月份不为1,统计当年当前月份之前的所有月份
//			for(int i=1; i<nowMonth; i++){
//				exp_vacc_2Service.getLastMonthData(nowYear + "",i + "");
//			}
//			
//		}
		
	}
	
	/**
	 * 定时删除表中的数据  每天凌晨2:00
	 * @author xuejinshan
	 * @date 2017年8月22日 上午9:53:54
	 * @description 
	 *		TODO
	 *
	 */
	@Scheduled(cron = "0 0 2 * * ?")
	@Transactional(readOnly = false)
	public void deleteDataFromBsRabiesPayEveryday() {
		logger.info("定时删除表数据开始");
		bsRabiesCheckinService.deleteDataFromBsRabiesPayEveryday();
		logger.info("定时删除表数据结束");
	}
	
	/**
	 * 向微信服务器获取当日预约的签字信息  每天凌晨2:30
	 * @author fuxin
	 * @date 2017年12月22日 下午4:07:02
	 * @description 
	 *		TODO
	 *
	 */
	@Scheduled(cron = "0 30 2 * * ?")
	@Transactional(readOnly = false)
	public void getSignFromWx() {
		logger.info("定时获取微信签字任务开始");
		VacChildRemindService ser = SpringContextHolder.getBean(VacChildRemindService.class);
		VacChildRemind remind = new VacChildRemind();
		remind.setBeginRemindDate(DateUtils.getDay(new Date()));
		remind.setEndRemindDate(DateUtils.getDayEnd(new Date()));
		List<VacChildRemind> reminds = ser.findList(remind);
		List<String> pids = new ArrayList<String>();
		for(VacChildRemind r: reminds){
			pids.add(r.getId());
		}
		if(pids.size() > 0){
			List<VacChildRemind> list = ser.getSignDataFromWx(pids);
			logger.info("定时获取微信签字任务成功,获取到" + list.size() + "条数据。清除重复微信签字任务开始");
			int resoult = ser.clearSignJob();
			logger.info("清除重复微信签字任务成功,删除" + resoult + "条签字数据");
		}
		logger.info("定时获取微信签字任务结束");
	}
	
	/**
	 * 定时读取bs_charge_log表中前一天的数据  每天凌晨00:03
	 * @author yangjian
	 * @date 2018年1月22日 下午15:30
	 * @description 
	 *		TODO
	 *
	 */
	@Scheduled(cron = "0 3 0 * * ?")
	@Transactional(readOnly = false)
	public void findDataListChargeEveryday() {
		Map<String, List<BsChargeLog>> orderlist = new HashMap<String, List<BsChargeLog>>();
		logger.info("定时读取表格前一天的数据开始" + JsonMapper.toJsonString(orderlist));
		orderlist.put("bsChargeLog", bsChargeLogService.findDataListChargeEveryday());
		logger.info("定时读取表格前一天的数据开始" + JsonMapper.toJsonString(orderlist));
		asyncService.uploadBsChargeLog(orderlist);
		logger.info("定时读取表格前一天的数据结束");
	}
	
	/**
	 * 定时获取已完成留观的儿童信息
	 * @author yangjian
	 * @date 2018年1月31日 下午14:06:54
	 * @description 
	 *		TODO
	 */
	@Scheduled(cron = "1 * * * * ?")
	@Transactional(readOnly = false)
	public void getCompleteView(){
		//一分钟发送一次
		//先检查半个小时之内有没有完成记录
		String localcode="";
		if(Global.getInstance().getLastFinishTime() == null){
			ChildVaccinaterecord rec = childVaccinaterecordDao.getLastFinishRecord();
			if(rec != null){
				Global.getInstance().setLastFinishTime(rec.getVaccinatedate());
			}else{
				Global.getInstance().setLastFinishTime(DateUtils.addDays(new Date(), -1));
			}
		}
		if(new Date().after(DateUtils.addMinutes(Global.getInstance().getLastFinishTime(), +35))){
			logger.info("半个小时之内无接种完成，跳过留观完成提醒任务");
			return ;
		}
		
		List<HashMap<String, String>> map = queneDao.getCompleteView(localcode);
		logger.info("获取留观完成的数据："+map);
		for (HashMap<String, String> hashMap : map) {
			Map<String, Object> tempParam = new HashMap<String, Object>();
			ChildBaseinfo childBaseinfo = childBaseinfoService.get(hashMap.get("childid"));
			tempParam.put("username", childBaseinfo.getChildname());
			asyncService.sendMessageToWX(childBaseinfo.getChildcode(), TYPE_VIEW, tempParam);
		}
		logger.info("获取留观完成的数据结束："+map);
	}
	
	/**
	 * 定时删除bs_rabies_checkin_bubble表中的数据  每天凌晨00:01
	 * @author yangjian
	 * @date 2018年2月26日 上午9:53:54
	 * @description 
	 *		TODO
	 */
	@Scheduled(cron = "0 1 0 * * ?")
	@Transactional(readOnly = false)
	public void deleteBubbleEveryDay(){
		logger.info("删除表数据开始");
		bsRabiesCheckinService.deleteBubble();
		logger.info("删除表数据结束");
	}
	
	/**
	 * 每月1号凌晨0:30,为用户自动盘点一次上月末数据定时器
	 * @author Jack
	 * @date 2018年3月6日 下午9:08:47
	 * @description 
	 *
	 */
	@Scheduled(cron = "0 30 0 1 * ?")
	public void withinPlanVaccAutoCount(){
		bsManageCheckService.withinPlanVaccAutoCount();
	}
}
