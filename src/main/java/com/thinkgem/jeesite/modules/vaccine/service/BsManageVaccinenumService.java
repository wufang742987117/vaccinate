/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junl.common.CommonUtils;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.dao.ChildVaccinaterecordDao;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.service.ChildVaccinaterecordService;
import com.thinkgem.jeesite.modules.holiday.service.SysHolidayService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.sys.entity.OfficePreference;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.vaccine.dao.BsManageBindedDao;
import com.thinkgem.jeesite.modules.vaccine.dao.BsManageVaccineDao;
import com.thinkgem.jeesite.modules.vaccine.dao.BsManageVaccinenumDao;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageBinded;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccinenum;
import com.thinkgem.jeesite.modules.vaccine.entity.PlanRule;

/**
 * 疫苗剂次管理Service
 * @author fuxin
 * @version 2017-02-24
 */
@Service
@Transactional(readOnly = true)
public class BsManageVaccinenumService extends CrudService<BsManageVaccinenumDao, BsManageVaccinenum> {

	@Autowired
	private ChildBaseinfoService childInfoService;
	@Autowired
	private ChildVaccinaterecordDao childRecordDao;
	@Autowired
	private BsManageVaccineService vaccineService;
	@Autowired
	private SysHolidayService holidayService;
	@Autowired
	private BsManageVaccineDao bsManageVaccineDao;
	@Autowired
	private BsManageBindedDao bsManageBindedDao;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private BsManageVaccinenumDao bsManageVaccinenumDao;
	
	public BsManageVaccinenum get(String id) {
		return super.get(id);
	}
	public BsManageVaccinenum get(String id,String localCode) {
		BsManageVaccinenum b = new BsManageVaccinenum(id);
		b.setLocalCode(localCode);
		return dao.get(b);
	}
	
	public List<BsManageVaccinenum> findList(BsManageVaccinenum bsManageVaccinenum) {
		return super.findList(bsManageVaccinenum);
	}
	
	public Page<BsManageVaccinenum> findPage(Page<BsManageVaccinenum> page, BsManageVaccinenum bsManageVaccinenum) {
		return super.findPage(page, bsManageVaccinenum);
	}
	
	@Transactional(readOnly = false)
	public void save(BsManageVaccinenum bsManageVaccinenum) {
		super.save(bsManageVaccinenum);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsManageVaccinenum bsManageVaccinenum) {
		super.delete(bsManageVaccinenum);
	}
	
	public void insert(BsManageVaccinenum bsManageVaccinenum) {
		//将免疫类型添加在nid后面
		dao.insert(bsManageVaccinenum.updateNid24());
	}
	
	/**
	 * 根据儿童编号查询儿童应该接种何种疫苗
	 * @author fuxin
	 * @date 2017年3月4日 下午5:10:17
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccList(String code){
		return getVaccList(code,0,null,0);
	}
	
	/**
	 * 获取有库存的计划一类
	 * @author fuxin
	 * @date 2017年4月21日 下午12:00:12
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccListAble(String code){
		List<BsManageVaccinenum> list = getVaccList(code, 0, "1", 0);
		List<BsManageVaccinenum> returnList = new ArrayList<>();
		for(BsManageVaccinenum n :list){
			 if("0".equals(n.getStock())){
			   continue;
		   }
			 returnList.add(n);
		}
		return returnList;
	}
	
	/**
	 * 模型计算下一针需接种的疫苗
	 * @author fuxin
	 * @date 2017年7月29日 下午4:56:43
	 * @description 
	 *		TODO
	 * @param code 儿童编号
	 * @param rownum 查询行数 =0查询所有
	 * @param type	类型(1-一类，2-二类)
	 * @param finish	是否包含未完成的记录(0-不包含 1-包含)
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccList(String code, int rownum, String type,  int finish){
		return getVaccList(code, rownum, type, finish, true, null);
	}
	
	/**
	 * 模型计算下一针需接种的疫苗
	 * @author fuxin
	 * @date 2017年8月27日 上午11:52:06
	 * @description 
	 *		TODO
	 * @param code 儿童编号
	 * @param rownum 查询行数 =0查询所有
	 * @param type	类型(1-一类，2-二类)
	 * @param finish	是否包含未完成的记录(0-不包含 1-包含)
	 * @param mouthAgeLimit		是否启用月龄限制
	 * @param orderBy	默认排序
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccList(String code, int rownum, String type,  int finish, boolean mouthAgeLimit, String orderBy){
		return getVaccList(code, rownum, type, finish, mouthAgeLimit, orderBy, null);
	}
	
	/**
	 * 模型计算下一针需接种的疫苗
	 * @author fuxin
	 * @date 2017年8月27日 上午11:52:06
	 * @description 
	 *		TODO
	 * @param code 儿童编号
	 * @param rownum 查询行数 =0查询所有
	 * @param type	类型(1-一类，2-二类)
	 * @param finish	是否包含未完成的记录(0-不包含 1-包含)
	 * @param mouthAgeLimit		是否启用月龄限制
	 * @param orderBy	默认排序
	 * @param targetDate	目标时间
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccList(String code, int rownum, String type,  int finish, boolean mouthAgeLimit, String orderBy, Date targetDate){
		return getVaccList(code, rownum, type, finish, mouthAgeLimit, orderBy, targetDate, OfficeService.getFirstOfficeCode());
	}
	
	/**
	 * 模型计算下一针需接种的疫苗
	 * @author fuxin
	 * @date 2017年8月27日 上午11:52:06
	 * @description 
	 *		TODO
	 * @param code 儿童编号
	 * @param rownum 查询行数 =0查询所有
	 * @param type	类型(1-一类，2-二类)
	 * @param finish	是否包含未完成的记录(0-不包含 1-包含)
	 * @param mouthAgeLimit		是否启用月龄限制
	 * @param orderBy	默认排序
	 * @param targetDate	目标时间
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccList(String code, int rownum, String type,  int finish, boolean mouthAgeLimit, String orderBy, Date targetDate,String localcode){
		//String localcode = "3406030301";
		if(null == targetDate){
			targetDate = new Date();
		}
		if(!StringUtils.isNotBlank(code)){
			throw new RuntimeException(this.getClass().getName() + "查询接种计划失败：儿童编号缺失");
		}
		
		//查询儿童月龄
		int mouage = childInfoService.getMouAge(code, targetDate,localcode);
		ChildBaseinfo baseinfo = childInfoService.getByNo(code,localcode);
		if(mouage == -1){
			throw new RuntimeException(this.getClass().getName() + "查询接种计划失败：儿童信息不存在");
		}
		
		//获取儿童已接种的计划id
		Set<String> vaccceaselist = new HashSet<String>();
		List<BsManageVaccinenum> nums = dao.findFinish(code, finish, localcode);
		for(BsManageVaccinenum num : nums ){
			vaccceaselist.add(num.getId());
		}

		
		//接种记录
		ChildVaccinaterecord r = new ChildVaccinaterecord();
		r.setOrderBy("a.vaccinatedate desc");
		r.setChildid(baseinfo.getId());
		r.setLocalCode(localcode);
//		r.setStatus(ChildVaccinaterecord.STATUS_YET);
		List<ChildVaccinaterecord> rcv = ChildVaccinaterecordService.findList4(childRecordDao.findList(r), false);
		//获取最近的活苗
		ChildVaccinaterecord recordLive = null;
		//获取最近完成的疫苗
		ChildVaccinaterecord recordLast = null;
		//获取已拆解疫苗记录
		List<ChildVaccinaterecord> dismissList = new ArrayList<ChildVaccinaterecord>();
		boolean isIpv = false;
		for(ChildVaccinaterecord cr : rcv){
			if(cr.getVaccinatedate() == null){
				continue;
			}
			//获取脊灰记录
			if("03".equals(cr.getVaccineid().substring(0, 2)) && BsManageVaccine.LIVE_NO.equals(cr.getVaccine().getLive())){
				isIpv = true;
			}
			//跳过替代记录
			if(ChildVaccinaterecord.SOURCE_CJ.equals(cr.getSource())){
				dismissList.add(cr);
				continue;
			}
			if(ChildVaccinaterecord.STATUS_YET.equals(cr.getStatus()) 
					&& cr.getVaccinatedate() != null 
					&& cr.getVaccinatedate().before(DateUtils.truncate(targetDate,Calendar.DATE))){
				if(recordLast == null){
					recordLast = cr;
				}
				if(cr.getVaccinatedate().after(recordLast.getVaccinatedate())){
					recordLast = cr;
				}
			}
			
			if(recordLive == null && cr.getVaccine() != null 
					&& StringUtils.isNotBlank(cr.getVaccine().getLive()) 
					&& BsManageVaccine.LIVE_YES.equals(cr.getVaccine().getLive()) 
					&& cr.getVaccinatedate().before(DateUtils.truncate(targetDate,Calendar.DATE))){
				recordLive = cr;
			}
		}
		
		//乙脑减毒和乙脑灭活混打方案2================================
		String jev = "";
		for(ChildVaccinaterecord cr : rcv){
			if(ChildVaccinaterecord.SOURCE_CJ.equals(cr.getSource())){
				continue;
			}
			if("831".equals(cr.getNid())){
				jev += "_831_";
			}
			if("841".equals(cr.getNid())){
				jev += "_841_";
			}
		}
		if(jev.contains("831") && jev.contains("841")){
			vaccceaselist.add("833");
		}
		//乙脑减毒和乙脑灭活混打方案2 end=============================
		
		//普通疫苗间隔15天
		OfficePreference op = OfficeService.getOfficeOption();
		if(recordLast != null && DateUtils.getDay(targetDate).before(DateUtils.addDays(DateUtils.getDay(recordLast.getVaccinatedate()), op.getVacDelay()))){
			return  new ArrayList<BsManageVaccinenum>(); 
		}
		
		//==活疫苗校验
		//查询一月内是否接种过活苗
//		int lastLiveDays = childRecordDao.getLastLiveDaysByNo(code);
		String lives = null;
		if(recordLive != null && recordLive.getVaccinatedate() != null 
				&& DateUtils.getDay(targetDate).before(DateUtils.addMonths(DateUtils.getDay(recordLive.getVaccinatedate()), 1))){
			lives = vaccineService.findLiveGnumList();
			//若接种过ipv则认为脊灰是活苗
			if(isIpv){
				lives += ",'03'";
			}
		}
		//联合疫苗校验 打完联合疫苗之后再接种被联合疫苗，则不打联合疫苗
		Set<String> disSet = new HashSet<String>();
		if(dismissList.size() > 0){
			for(ChildVaccinaterecord cr : rcv){
				if(ChildVaccinaterecord.SOURCE_CJ.equals(cr.getSource())){
					continue;
				}
				for(ChildVaccinaterecord dis:dismissList){
					Date disDate = dis.getVaccinatedate()==null?dis.getCreateDate():dis.getVaccinatedate() ;
					Date crDate = cr.getVaccinatedate()==null?cr.getCreateDate():cr.getVaccinatedate();
					if(cr.getNid().substring(0, 2).equals(dis.getNid().substring(0, 2)) && crDate.after(disDate)){
						BsManageVaccine bv = vaccineService.getWithModel(dis.getInocUnionCode(),localcode);
						if(bv != null){
							disSet.add(bv.getmNum());
						}else{
							logger.error("模型替代记录inocUnionCode错误[" + dis.getId() +  "]");
						}
					}
				}
			}
		}
		for(String disStr:disSet){
			if(lives == null){
				lives = "'" + disStr + "'";
			}else{
				lives += ",'" + disStr + "'";
			}
		}
		
		//获取特殊规则
		List<PlanRule> rules = vaccineService.findRules(localcode);
		//校验前置特殊规则
		Set<String> vaccceaselistAll = checkRulePre(vaccceaselist,rules,rcv);
		
		//将已完成前面的nid补齐
		for(String s : vaccceaselistAll){
			/*if("1".equals(s.substring(2,3))){
				continue;
			}*/
			int dosage = Integer.parseInt(s.substring(2,3));
			for(int i = dosage; i > 0; i --){
				vaccceaselist.add(s.substring(0, 2) + i);
			}
		}
		
		
		//将已完成的接种记录拼接
		String vacccease = "";
		for(String s : vaccceaselist){
			vacccease += "'"+ s +"'" + ",";
		}
		if(vacccease.endsWith(",")){
			vacccease = vacccease.substring(0, vacccease.length() -1);
		}		
		
		
		//使用
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("localCode", localcode);
		//是否需要月龄限制
		if(mouthAgeLimit){
			parm.put("monage", mouage+"");
		}else{
			parm.put("monage", "");
		}
		//排序
		parm.put("orderBy",orderBy);
		parm.put("lives",lives);
		parm.put("vacccease", vacccease);
		if(rownum > 0){
			parm.put("rownum", (rownum+1)+"");
		}
		if(StringUtils.isNotBlank(type)){
			parm.put("type", type);
		}
		List<BsManageVaccinenum> templist = dao.getVaccList(parm);
		List<BsManageVaccinenum> returnList = new ArrayList<>();
		
		//去重
		List<String> vaccGroupList = new ArrayList<String>();
		for(int i=0;i<templist.size();i++){
		   BsManageVaccinenum num = templist.get(i);
		   if(vaccGroupList.contains(num.getGroup())){
			   int idx = vaccGroupList.indexOf(num.getGroup().toString());
			   if(returnList.get(idx).getPin() > num.getPin()){
				   returnList.set(idx,  num);
			   }
		   }else{
			   returnList.add(num);		
			   vaccGroupList.add(num.getGroup());
		   }
		 }
		
		//校验时间间隔
		for(int i = 0; i < returnList.size(); i ++){
			if(null == returnList.get(i).getPintime()){
				continue;
			}
			for(ChildVaccinaterecord tr: rcv){
				if(!returnList.get(i).getGroup().equals(tr.getNid().substring(0,2))){
					continue;
				}
				//当今天再间隔时间之前，则不能接种
				if(ChildVaccinaterecord.STATUS_NOT.equals(tr.getStatus())){
					if(finish == 0){
						returnList.remove(i--);
						break;
					}else{
						continue;
					}
				}
				if(returnList.get(i).getPintime()>700 && returnList.get(i).getPintime()<800){
					if(tr.getVaccinatedate() != null 
							&& DateUtils.getDay(targetDate).before(DateUtils.addDays(DateUtils.getDay(tr.getVaccinatedate()), (returnList.get(i).getPintime().intValue()-700)*7))){
						returnList.remove(i--);
					}
					break;
				}else{
					if(tr.getVaccinatedate() != null 
							&& DateUtils.getDay(targetDate).before(DateUtils.addMonths(DateUtils.getDay(tr.getVaccinatedate()), returnList.get(i).getPintime().intValue()))){
						returnList.remove(i--);
					}
					break;
				}
			}
		}
		
		//校验特殊规则
		checkRule(returnList,rules,rcv,targetDate, baseinfo);
		
		//校验hib规则
		boolean isbreak = false;
		for( int i = 0; i < returnList.size(); i ++){
			if(isbreak){
				break;
			}
			if(BsManageVaccine.VACC_HIB_GNUM.equals(returnList.get(i).getGroup())){
				//校验hib
				List<ChildVaccinaterecord> hibs = new ArrayList<ChildVaccinaterecord>();
				int firstBlood = 0;
				int lastPin = 0;
				for(ChildVaccinaterecord c : rcv){
					if(c.getVaccinatedate() == null){
						continue;
					}
					if(BsManageVaccine.VACC_HIB_GNUM.equals(c.getVaccineid().substring(0, 2))){
						hibs.add(c);
						int tempLast = DateUtils.subtractMonths(baseinfo.getBirthday(),c.getVaccinatedate());
						lastPin = tempLast>lastPin?tempLast:lastPin;
						if("1".equals(c.getDosage())){
							firstBlood = tempLast;
						}
					}
				}
				
				if(firstBlood >= 12){
					returnList.remove(i--);
					isbreak = true;
					break;
				}
				if(lastPin >= 18){
					returnList.remove(i--);
					isbreak = true;
					break;
				}
				if(hibs.size() > 0){
					if(mouage >= 12 && mouage < 18){
						returnList.remove(i--);
						isbreak = true;
						break;
					}
					if(mouage < 12){
						if(firstBlood >= 6 && firstBlood < 12 && returnList.get(i).getPin() > 2){
							returnList.remove(i--);
							isbreak = true;
							break;
						}else if(firstBlood < 6 && returnList.get(i).getPin() > 3){
							returnList.remove(i--);
							isbreak = true;
							break;
						}
					}
				}
			}
		}
		
		//校验联合疫苗 所替代疫苗都可以打，才可以打
		//List<BsManageVaccine> vaccs = bsManageVaccineDao.findList(new BsManageVaccine());
		BsManageVaccine b1 = new BsManageVaccine();
		b1.setLocalCode(localcode);
		List<BsManageVaccine> vaccs = bsManageVaccineDao.findList(b1);
		BsManageBinded bb = new BsManageBinded();
		bb.setOrderBy(" a.\"LEVEL\" desc ,a.BIND_VACC_ID"); 
		bb.setBind(BsManageBinded.BIND_YES);
		bb.setLocalCode(localcode);
		List<BsManageBinded> binds = bsManageBindedDao.findList(bb);
		for(BsManageBinded bind : binds){
			String mNum = "";
			for(BsManageVaccine bv : vaccs){
				if(bind.getBindVaccId().equals(bv.getId())){
					mNum = bv.getmNum();
					break;
				}
			}
			bind.setBindVaccId(mNum);
			mNum = "";
			for(BsManageVaccine bv : vaccs){
				if(bind.getVaccId().equals(bv.getId())){
					mNum = bv.getmNum();
					break;
				}
			}
			bind.setVaccId(mNum);
		}
		
		Map<String, List<BsManageBinded>> bindMap = new LinkedMap();
		List<BsManageBinded> bindlist = new ArrayList<BsManageBinded>();
		String flag = "first";
		for(BsManageBinded b : binds){
			if("first".equals(flag)){
				flag = b.getBindVaccId();
			}
			if(!flag.equals(b.getBindVaccId())){
				bindMap.put(flag, bindlist);
				flag = b.getBindVaccId();
				bindlist = new ArrayList<BsManageBinded>();
			}
			bindlist.add(b);
		}
		if(bindlist.size() > 0){
			bindMap.put(flag, bindlist);
		}
		
		for(int i = 0; i < returnList.size(); i ++){
    		if(bindMap.keySet().contains(returnList.get(i).getGroup())){
    			List<BsManageBinded> bdl = bindMap.get(returnList.get(i).getGroup());
    			int match = 0;
    			for( BsManageVaccinenum rl :returnList){
    				for( BsManageBinded b :bdl){
        				if(rl.getGroup().equals(b.getVaccId())){
        					match ++;
        				}
        			}
    			}
    			if(match != bdl.size()){
    				returnList.remove(i--);
    			}
    			
    		}
	    }
		
		//预扣除库存
		//List<BsManageVaccinenum> queneNum = dao.getQueneStock(new BsManageVaccinenum());
		BsManageVaccinenum b2 = new BsManageVaccinenum();
		b2.setLocalCode(localcode);
		List<BsManageVaccinenum> queneNum = dao.getQueneStock(b2);
		for(BsManageVaccinenum n1 : returnList){
			for(BsManageVaccinenum n2 : queneNum){
				if(n1.getGroup().equals(n2.getGroup()) && StringUtils.isNotBlank(n1.getStock()) && StringUtils.isNotBlank(n2.getStock())){
					n1.setStock((Integer.parseInt(n1.getStock()) - Integer.parseInt(n2.getStock())) + "");
				}
			}
		}
		return returnList;
	}
	
	
	/**
	 * 获取下次接种时间
	 * @author fuxin
	 * @date 2017年12月16日 下午7:37:40
	 * @description 
	 *		TODO
	 * @param rcvs
	 * @param baseinfo
	 * @param num
	 * @return
	 *
	 */
	public BsManageVaccinenum getNextVaccDate(List<ChildVaccinaterecord> rcvs, ChildBaseinfo baseinfo, BsManageVaccinenum num){
		return getNextVaccDate(rcvs, baseinfo, num,OfficeService.getFirstOfficeCode());
	}
	
	/**
	 * 获取下次接种时间
	 * @author liyuan
	 * @date 2018年2月26日 上午9:47:13
	 * @description 
	 *		TODO
	 * @param rcvs
	 * @param baseinfo
	 * @param num
	 * @param localcode
	 * @return
	 *
	 */
	public BsManageVaccinenum getNextVaccDate(List<ChildVaccinaterecord> rcvs, ChildBaseinfo baseinfo, BsManageVaccinenum num,String localcode){
		//查询儿童月龄
		Date nextDay = new Date();
		Date tempDay = new Date();
		
		//基础月龄计算
		nextDay = DateUtils.addMonths(baseinfo.getBirthday(),num.getMouage().intValue());
		
		//计算同种疫苗时间间隔
		if(num.getPin() != 1){
			Long pinTime = num.getPintime();
			if(pinTime != null && pinTime != 0){
				//若是第一针，则不考虑与上一针间隔
				for(ChildVaccinaterecord cr :rcvs){
					if(cr.getVaccinatedate() == null){
						continue;
					}
					
					if(num.getId().equals(cr.getNid())){
						BsManageVaccinenum tempNum = new BsManageVaccinenum(num.getGroup() + (num.getPin() + 1));
						tempNum.setLocalCode(localcode);
						BsManageVaccinenum nextNum = bsManageVaccinenumDao.get(tempNum);
						if(nextNum != null){
							if(pinTime > 700 && pinTime < 800){
								tempDay = DateUtils.addDays(cr.getVaccinatedate(), (pinTime.intValue() - 700)*7);
							}else{
								tempDay = DateUtils.addMonths(cr.getVaccinatedate(), pinTime.intValue());
							}
							nextDay = nextDay.after(tempDay)?nextDay:tempDay;
						}
					}else if((num.getGroup() + (num.getPin() - 1)).equals(cr.getNid())){
						if(pinTime > 700 && pinTime < 800){
							tempDay = DateUtils.addDays(cr.getVaccinatedate(), (pinTime.intValue() - 700)*7);
						}else{
							tempDay = DateUtils.addMonths(cr.getVaccinatedate(), pinTime.intValue());
						}
						nextDay = nextDay.after(tempDay)?nextDay:tempDay;
					}
				}
			}
		}		
		
		ChildVaccinaterecord[] lastRec = getLastRecord(rcvs);
		ChildVaccinaterecord recordLast = lastRec[0];
		ChildVaccinaterecord recordLive = lastRec[1];
		//活苗间隔
		//List<String> lives = bsManageVaccineDao.findLiveGnumList(new BsManageVaccine());
		BsManageVaccine b = new BsManageVaccine();
		b.setLocalCode(localcode);
		List<String> lives = bsManageVaccineDao.findLiveGnumList(b);
		if(recordLive != null && lives.contains(num.getGroup())){
			tempDay = DateUtils.addMonths(recordLive.getVaccinatedate(), 1);
			nextDay = nextDay.after(tempDay)?nextDay:tempDay;
		}
		if(recordLast != null){
			OfficePreference op = officeService.getOfficeOption(localcode);
			tempDay = DateUtils.addDays(recordLast.getVaccinatedate(), op.getVacDelay());
			nextDay = nextDay.after(tempDay)?nextDay:tempDay;
		}
		
		List<PlanRule> rules = vaccineService.findRules(localcode);
		for(PlanRule rule : rules){
			switch (rule.getType()) {
			case 1:{
				//规则1：特殊剂次之间的时间间隔
				//{"type":1,"description":"特殊剂次之间的时间间隔","targetPin":1,"currentPin":3,"offset":36, "group":"17", "targetGroup":"02"}
				//targetGroup没有则默认 targetGroup=group
				
				for(int i = 0; i < rcvs.size(); i ++){
					//计划和规则吻合时进行校验
					if(num.getGroup().equals(rule.getGroup()) && num.getPin() == rule.getCurrentPin()){
						for(ChildVaccinaterecord r : rcvs){
							if(ChildVaccinaterecord.SOURCE_CJ.equals(r.getSource())){
								continue;
							}
							//规则和接种记录吻合时进行比较
							if(StringUtils.isNotBlank(r.getNid()) 
									&& r.getNid().substring(0,2).equals(rule.getTargetGroup()!=null?rule.getTargetGroup():rule.getGroup())
									&& (rule.getTargetPin()+"").equals(r.getDosage())){
								//未过间隔时间时移除计划	
//								tempDay = DateUtils.addMonths(recordLive.getVaccinatedate(), 1);
								tempDay = DateUtils.addMonths(r.getVaccinatedate(), rule.getOffset());;
								nextDay = nextDay.after(tempDay)?nextDay:tempDay;
								break;
							}
						}
					}
				}
				break;
			}
			
			default:
				break;
			}
		}
		
		num.setVaccDate(nextDay);
		return num;
	}
	
	/**
	 * 根据儿童编号查询儿童应该接种的一类疫苗
	 * @author fuxin
	 * @date 2017年3月4日 下午5:10:17
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccListfree(String code){
		return getVaccList(code,0,"1",0);
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月10日 下午3:23:47
	 * @description 
	 *	根据模型计算计划接种的疫苗,并根据疫苗名称排序
	 * @param code
	 * @return
	 *
	 */
	
	public List<BsManageVaccinenum> getVaccNameList(String code){
		return getVaccNameList(code, null, "1");
	}
	
	/**
	 * 根据模型计算计划接种的疫苗,并排序
	 * @author fuxin
	 * @date 2017年8月27日 下午3:25:53
	 * @description 
	 *		TODO
	 * @param code
	 * @param orderBy
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccNameList(String code, String orderBy, String type){
		if(!StringUtils.isNotBlank(code)){
			throw new RuntimeException(this.getClass().getName() + "查询接种计划失败：儿童编号缺失");
		}
		
		//查询儿童月龄
		int mouage = childInfoService.getMouAge(code);
		if(mouage == -1){
			throw new RuntimeException(this.getClass().getName() + "查询接种计划失败：儿童信息不存在");
		}
		
		//获取儿童已接种的计划id
		String vacccease = "";
		List<BsManageVaccinenum> nums = dao.findFinish(code, 1, OfficeService.getFirstOfficeCode());
		for(BsManageVaccinenum num : nums ){
			vacccease += "'"+num.getId()+"'" + ",";
		}
		if(vacccease.endsWith(",")){
			vacccease = vacccease.substring(0, vacccease.length() -1);
		}
		
		//接种记录
		ChildBaseinfo baseinfo = childInfoService.getByNo(code);
		ChildVaccinaterecord r = new ChildVaccinaterecord();
		r.setOrderBy("a.vaccinatedate desc");
		r.setChildid(baseinfo.getId());
//		r.setStatus(ChildVaccinaterecord.STATUS_YET);
		List<ChildVaccinaterecord> rcv = ChildVaccinaterecordService.findList4(childRecordDao.findList(r), false);
		
		//获取特殊规则
		List<PlanRule> rules = vaccineService.findRules();
		//校验前置特殊规则
		vacccease = checkRulePre(vacccease,rules,rcv);
		
		//使用
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("localCode", OfficeService.getFirstOfficeCode());
		parm.put("vacccease", vacccease);
		parm.put("orderBy", orderBy);
		parm.put("type", type);
		List<BsManageVaccinenum> templist = dao.getVaccNameList(parm);
		
		//校验特殊规则
//		List<PlanRule> rules = vaccineService.findRules();
		for(int i = 0; i < rules.size(); i ++){
			if(rules.get(i).getType() != 2){
				rules.remove(i--);
			}
		}
		checkRule(templist,rules,rcv, null);
		return templist;
	}
	
	/**
	
	/**
	 * 取出价格最高的产品信息
	 * @author fuxin
	 * @date 2017年5月25日 上午10:51:53
	 * @description 
	 *		TODO
	 * @param list
	 * @return
	 *
	 */
	private BsManageProduct maxPriceProduct(List<BsManageProduct> list){
		BsManageProduct max = null;
		if(null != list && list.size() > 0){
			max =  list.get(0);
			for(BsManageProduct p : list){
				if(max.getSellprice() < p.getSellprice()){
					max = p;
				}
			}
		}
		return max;
	}
	
	/**
	 * 校验特殊规则
	 * @author fuxin
	 * @date 2017年8月15日 上午9:52:44
	 * @description 
	 *		TODO
	 * @param returnList
	 * @param rules
	 * @param rcv
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> checkRule(List<BsManageVaccinenum> returnList, List<PlanRule> rules, List<ChildVaccinaterecord> rcv,ChildBaseinfo baseinfo) {
		return checkRule(returnList,rules,rcv,new Date(),baseinfo);
	}
		
		
	/**
	 * 校验特殊规则
	 * @author fuxin
	 * @date 2017年8月15日 上午9:52:44
	 * @description 
	 *		TODO
	 * @param returnList
	 * @param rules
	 * @param rcv
	 * @param baseinfo	儿童信息(规则4)
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> checkRule(List<BsManageVaccinenum> returnList,
			List<PlanRule> rules, List<ChildVaccinaterecord> rcv,Date targetDate, ChildBaseinfo baseinfo) {
		if(targetDate==null){
			targetDate=new Date();
		}
		for(PlanRule rule : rules){
			switch (rule.getType()) {
			case 1:{
				//规则1：特殊剂次之间的时间间隔
				//{"type":1,"description":"特殊剂次之间的时间间隔","targetPin":1,"currentPin":3,"offset":36, "group":"17", "targetGroup":"02"}
				//targetGroup没有则默认 targetGroup=group
				
				for(int i = 0; i < returnList.size(); i ++){
					//计划和规则吻合时进行校验
					if(returnList.get(i).getGroup().equals(rule.getGroup()) && (returnList.get(i).getPin() == rule.getCurrentPin() || 0l == rule.getCurrentPin())){
						for(ChildVaccinaterecord r : rcv){
							if(ChildVaccinaterecord.SOURCE_CJ.equals(r.getSource())){
								continue;
							}
							//规则和接种记录吻合时进行比较
							if(StringUtils.isNotBlank(r.getNid()) 
									&& r.getNid().substring(0,2).equals(rule.getTargetGroup()!=null?rule.getTargetGroup():rule.getGroup())
									&& ((rule.getTargetPin()+"").equals(r.getDosage()) || rule.getTargetPin() == 0l)){
								//未过间隔时间时移除计划	
								Date rDate = r.getVaccinatedate() == null?targetDate:r.getVaccinatedate();
								Date sss=DateUtils.addMonths(DateUtils.getDay(rDate), rule.getOffset());
								if(DateUtils.getDay(targetDate).before(sss)){
										returnList.remove(i--);
									}
								break;
							}
						}
					}
				}
				break;
			}
			
			case 2:{
				//规则2：一种疫苗打完，则不显示另一种疫苗
				//{ "type": 2,"description": "一种疫苗打完，则不显示另一种疫苗","targetPin": 2,"targetGroup": "81","group": "82"}
				
				for(int i = 0; i < returnList.size(); i ++){
					//计划和规则吻合时进行校验
					if(returnList.get(i).getGroup().equals(rule.getGroup())){
						for(ChildVaccinaterecord r : rcv){
							//成功找到目标结束疫苗则不显另一种疫苗
							if(StringUtils.isNotBlank(r.getNid()) && r.getNid().equals(rule.getTargetGroup() + rule.getTargetPin())){
								returnList.remove(i--);
								break;
							}
							if(StringUtils.isNotBlank(r.getVaccineid()) && r.getVaccineid().equals(rule.getTargetGroup())){
								returnList.remove(i--);
								break;
							}
							
						}
					}
				}
				break;
			}
			
			case 3:{
				//模型大类共享免疫程序
				//{"type": 3,"description": "模型大类共享免疫计划","targetGroup":"81","group": "82"}
				int idx1 = -1;
				int idx2 = -1;
				
				//去除共享目标对象
				for(int i = 0; i < returnList.size(); i ++){
					if(returnList.get(i).getGroup().equals(rule.getGroup())){
						idx1 = i;
						break;
					}
				}
				//去除共享目标对象
				for(int i = 0; i < returnList.size(); i ++){
					if(returnList.get(i).getGroup().equals(rule.getTargetGroup())){
						idx2 = i;
						break;
					}
				}
				if(idx1 < 0 || idx2 < 0){
					if(idx1 >= 0){
						returnList.remove(idx1);
					}
					if(idx2 >= 0){
						returnList.remove(idx2);
					}
					break;
				}
				
				//比较谁是比较靠后的
				if(returnList.get(idx1).getPin() > returnList.get(idx2).getPin()){
					BsManageVaccinenum n = get(returnList.get(idx2).getGroup() + returnList.get(idx1).getPin());
					returnList.get(idx2).setPin(returnList.get(idx1).getPin());
					returnList.get(idx2).setMouage(n.getMouage());
					returnList.get(idx2).setPintime(n.getPintime());
				}else{
					returnList.get(idx1).setPin(returnList.get(idx2).getPin());
					BsManageVaccinenum n = get(returnList.get(idx1).getGroup() + returnList.get(idx2).getPin());
					returnList.get(idx1).setPin(returnList.get(idx2).getPin());
					returnList.get(idx1).setMouage(n.getMouage());
					returnList.get(idx1).setPintime(n.getPintime());
				}
				break;
			}
			
			case 4:{
				String interval = rule.getInterval();
				if(StringUtils.isBlank(interval) || rule.getOffset() == 0){
					break;
				}
				if(rule.getStartAge() > 0 && DateUtils.subtractMonths(baseinfo.getBirthday(), targetDate) < rule.getStartAge()){
					break;
				}
				if(rule.getEndAge() > 0 && DateUtils.subtractMonths(baseinfo.getBirthday(), targetDate) > rule.getEndAge()){
					break;
				}
				int count = 0;
				for(int i = 0; i < returnList.size(); i ++){
					//计划和规则吻合时进行校验
					if(returnList.get(i).getGroup().equals(rule.getGroup())){
						for(ChildVaccinaterecord r : rcv){
							//成功找到目标结束疫苗则不显另一种疫苗
							if(StringUtils.isNotBlank(r.getNid()) && r.getNid().substring(0,2).equals(rule.getGroup())){
								if("year".equals(interval)){
									if(r.getVaccinatedate().getYear() + rule.getOffset() > targetDate.getYear()){
//											returnList.remove(i--);
										count ++;
										if(count >= rule.getPinNum()){
											returnList.remove(i--);
										}
									}
								}
							}
							
						}
					}
				}
			}
			
			default:
				break;
			}
		}
		return returnList;
	}
		
	/**
	 * 校验前置规则
	 * @author fuxin
	 * @date 2017年9月6日 下午1:58:47
	 * @description 
	 *		TODO
	 * @param vacccease
	 * @param rules
	 * @param rcv
	 *
	 */
	private String checkRulePre(String vacccease, List<PlanRule> rules, List<ChildVaccinaterecord> rcv) {
		for(PlanRule rule : rules){
			switch (rule.getType()) {
			case 5:{
				//规则5：特殊剂次之间的时间间隔
				//{"type": 5, "description": "单剂次替代", "targetPin": 1, "targetGroup":"16", "currentPin": 1, "group": "87" }
				for(ChildVaccinaterecord r : rcv){
					//规则和接种记录吻合时进行比较
					if(StringUtils.isNotBlank(r.getNid()) 
							&& r.getNid().substring(0,2).equals(rule.getTargetGroup())
							&& (rule.getTargetPin()+"").equals(r.getDosage())){
						//未过间隔时间时移除计划	
						vacccease += ",'" + rule.getGroup() + rule.getCurrentPin() + "'";
						break;
					}
				}
				break;
			}
			
			default:
				break;
			}
		}
		if(StringUtils.isNotBlank(vacccease) && vacccease.startsWith(",")){
			vacccease = vacccease.substring(1);
		}
		return vacccease;
	}
		
	/**
	 * 校验前置规则
	 * @author fuxin
	 * @date 2017年10月4日 下午1:43:14
	 * @description 
	 *		TODO
	 * @param vaccceaselist
	 * @param rules
	 * @param rcv
	 * @return
	 *
	 */
	private Set<String> checkRulePre(Set<String> vaccceaselist, List<PlanRule> rules, List<ChildVaccinaterecord> rcv) {
		Set<String> vaccceaselistAll = new HashSet<String>();
		vaccceaselistAll.addAll(vaccceaselist);
		for(PlanRule rule : rules){
			switch (rule.getType()) {
			case 5:{
				//规则5：特殊剂次之间的时间间隔
				//{"type": 5, "description": "单剂次替代", "targetPin": 1, "targetGroup":"16", "currentPin": 1, "group": "87" }
				for(ChildVaccinaterecord r : rcv){
					//规则和接种记录吻合时进行比较
					if(StringUtils.isNotBlank(r.getNid()) 
							&& r.getNid().substring(0,2).equals(rule.getTargetGroup())
							&& (rule.getTargetPin()+"").equals(r.getDosage())){
						//未过间隔时间时移除计划	
						vaccceaselistAll.add(rule.getGroup() + rule.getCurrentPin());
						break;
					}
				}
				break;
			}
			
			default:
				break;
			}
		}
		return vaccceaselistAll;
	}
		
	/**
	 * 模型计算下一针需接种的疫苗
	 * @author liumin
	 * @date 2017年8月27日 上午11:52:06
	 * @description 
	 *		TODO
	 * @param code 儿童编号
	 * @param rownum 查询行数 =0查询所有
	 * @param type	类型(1-一类，2-二类)
	 * @param finish	是否包含未完成的记录(0-不包含 1-包含)
	 * @param mouthAgeLimit		是否启用月龄限制
	 * @param orderBy	默认排序
	 * @param targetDate	目标时间
	 * @param cvr	需要排除的记录
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> getVaccList(String code, int rownum, String type,  int finish, boolean mouthAgeLimit, String orderBy, Date targetDate,
			List<ChildVaccinaterecord> cvrs) {
		
		if(null == targetDate){
			targetDate = new Date();
		}
		if(!StringUtils.isNotBlank(code)){
			throw new RuntimeException(this.getClass().getName() + "查询接种计划失败：儿童编号缺失");
		}
		
		//查询儿童月龄
		int mouage = childInfoService.getMouAge(code, targetDate);
		ChildBaseinfo baseinfo = childInfoService.getByNo(code);
		if(mouage == -1){
			throw new RuntimeException(this.getClass().getName() + "查询接种计划失败：儿童信息不存在");
		}
		
		//获取儿童已接种的计划id
		Set<String> vaccceaselist = new HashSet<String>();
		List<BsManageVaccinenum> nums = dao.findFinish(code, finish, OfficeService.getFirstOfficeCode());
		for(BsManageVaccinenum num : nums ){
			vaccceaselist.add(num.getId());
		}
		
		//接种记录
		ChildVaccinaterecord r = new ChildVaccinaterecord();
		r.setOrderBy("a.vaccinatedate desc");
		r.setChildid(baseinfo.getId());
//			r.setStatus(ChildVaccinaterecord.STATUS_YET);
		List<ChildVaccinaterecord> rcv = ChildVaccinaterecordService.findList4(childRecordDao.findList(r), false);
		Map<Object, List<ChildVaccinaterecord>> rcvMap = CommonUtils.getTreeDateByParam(ChildVaccinaterecord.class, rcv, "NidPre");
		
		if(finish==0){
			for(ChildVaccinaterecord cvr : cvrs){
				
				BsManageVaccine tempVacc = new BsManageVaccine();
				List<BsManageVaccine> vaccs = bsManageVaccineDao.findList(tempVacc);
				// 计算替代记录
				BsManageBinded bb = new BsManageBinded();
				bb.setOrderBy(" a.\"LEVEL\" ");
				List<BsManageBinded> binds = bsManageBindedDao.findList(bb);
				for (BsManageBinded bin : binds) {
					if (!cvr.getVaccineid().equals(bin.getBindVaccId()) || (bin.getBindVaccPin().intValue() != 0
							&& bin.getBindVaccPin().intValue() != cvr.getDosageInt())) {
						continue;
					}
					String mNum = "";
					for (BsManageVaccine bv : vaccs) {
						if (bin.getVaccId().equals(bv.getId())) {
							mNum = bv.getmNum();
							break;
						}
					}

					// 生成替代记录，并修改默认值
					ChildVaccinaterecord tempRec = new ChildVaccinaterecord();
					BeanUtils.copyProperties(r, tempRec);
					tempRec.setSource(ChildVaccinaterecord.SOURCE_CJ);
					tempRec.setId(IdGen.uuid());
					tempRec.setNid(mNum);
					tempRec.setBatch("00000000");
					tempRec.setVaccineid(bin.getVaccId());
					tempRec.setVaccCode(bin.getVaccine().getgName());
					tempRec.setVaccName(cvr.getVaccName());
					tempRec.setManufacturer("");
					tempRec.setManufacturercode("");
					tempRec.setProductid("");
					tempRec.setInocUnionCode(cvr.getVaccineid());
					tempRec.setInocUnionRecord(cvr.getId());
					tempRec.setSign(ChildVaccinaterecord.SIGNATURE_NO);
					tempRec.setStatus(ChildVaccinaterecord.STATUS_NOT.equals(cvr.getStatus())
							? ChildVaccinaterecord.STATUS_NOT : ChildVaccinaterecord.STATUS_YET);
					tempRec.preInsert();
					tempRec.setDosage("0");
					//计算nid
					int pinNo = (rcvMap.get(bin.getVaccine().getmNum()) == null ? 0:rcvMap.get(bin.getVaccine().getmNum()).size());
					tempRec.setNid(bin.getVaccine().getmNum() + pinNo);
					
					vaccceaselist.add(tempRec.getNid());
					rcv.add(0,tempRec);
				}
				vaccceaselist.add(cvr.getNid());
				rcv.add(0,cvr);
			}
			//设置未完成接种日期
			for (ChildVaccinaterecord cr : rcv) {
				if(cr.getVaccinatedate()==null){
					cr.setVaccinatedate(new Date());
				}
				cr.setStatus(ChildVaccinaterecord.STATUS_YET);
			}
		}
		
		//获取最近的活苗
		ChildVaccinaterecord recordLive = null;
		//获取最近完成的疫苗
		ChildVaccinaterecord recordLast = null;
		//获取已拆解疫苗记录
		List<ChildVaccinaterecord> dismissList = new ArrayList<ChildVaccinaterecord>();
		boolean isIpv = false;
		for(ChildVaccinaterecord cr : rcv){
			if(cr.getVaccinatedate() == null){
				continue;
			}
			//获取脊灰记录
			if("03".equals(cr.getVaccineid().substring(0, 2)) && BsManageVaccine.LIVE_NO.equals(cr.getVaccine().getLive())){
				isIpv = true;
			}
			if(ChildVaccinaterecord.SOURCE_CJ.equals(cr.getSource())){
				dismissList.add(cr);
				continue;
			}
			if(ChildVaccinaterecord.STATUS_YET.equals(cr.getStatus()) 
					&& cr.getVaccinatedate() != null 
					&& cr.getVaccinatedate().before(DateUtils.getDayEnd(targetDate))){
				if(recordLast == null){
					recordLast = cr;
				}
				if(cr.getVaccinatedate().after(recordLast.getVaccinatedate())){
					recordLast = cr;
				}
			}
			
			if(recordLive == null && cr.getVaccine() != null 
					&& StringUtils.isNotBlank(cr.getVaccine().getLive()) 
					&& BsManageVaccine.LIVE_YES.equals(cr.getVaccine().getLive()) 
					&& cr.getVaccinatedate().before(DateUtils.truncate(targetDate,Calendar.DATE))){
				recordLive = cr;
			}
			
		}
		
		//乙脑减毒和乙脑灭活混打方案2================================
		String jev = "";
		for(ChildVaccinaterecord cr : rcv){
			if(ChildVaccinaterecord.SOURCE_CJ.equals(cr.getSource())){
				continue;
			}
			if("831".equals(cr.getNid())){
				jev += "_831_";
			}
			if("841".equals(cr.getNid())){
				jev += "_841_";
			}
		}
		if(jev.contains("831") && jev.contains("841")){
			vaccceaselist.add("833");
		}
		//乙脑减毒和乙脑灭活混打方案2 end=============================
		
		// 普通疫苗间隔7天
		OfficePreference op = OfficeService.getOfficeOption();
		if (recordLast != null && DateUtils.getDay(targetDate).before(DateUtils.addDays(DateUtils.getDay(recordLast.getVaccinatedate()),op.getVacDelay()))) {
			return new ArrayList<BsManageVaccinenum>();
		}
		
		//==活疫苗校验
		//查询一月内是否接种过活苗
//			int lastLiveDays = childRecordDao.getLastLiveDaysByNo(code);
		String lives = null;
		if(recordLive != null && recordLive.getVaccinatedate() != null 
				&& DateUtils.getDay(targetDate).before(DateUtils.addMonths(DateUtils.getDay(recordLive.getVaccinatedate()), 1))){
			lives = vaccineService.findLiveGnumList();
			//若接种过ipv则认为脊灰是活苗
			if(isIpv){
				lives += ",'03'";
			}
		}
		//联合疫苗校验 打完联合疫苗之后再接种被联合疫苗，则不打联合疫苗
		Set<String> disSet = new HashSet<String>();
		if(dismissList.size() > 0){
			for(ChildVaccinaterecord cr : rcv){
				if(ChildVaccinaterecord.SOURCE_CJ.equals(cr.getSource())){
					continue;
				}
				for(ChildVaccinaterecord dis:dismissList){
					Date disDate = dis.getVaccinatedate()==null?dis.getCreateDate():dis.getVaccinatedate() ;
					Date crDate = cr.getVaccinatedate()==null?cr.getCreateDate():cr.getVaccinatedate();
					if(cr.getNid().substring(0, 2).equals(dis.getNid().substring(0, 2)) && crDate.after(disDate)){
						BsManageVaccine bv = vaccineService.getWithModel(dis.getInocUnionCode());
						if(bv != null){
							disSet.add(bv.getmNum());
						}else{
							logger.error("模型替代记录inocUnionCode错误[" + dis.getId() +  "]");
						}
					}
				}
			}
		}
		
		//获取特殊规则
		List<PlanRule> rules = vaccineService.findRules();
		//校验前置特殊规则
		Set<String> vaccceaselistAll = checkRulePre(vaccceaselist,rules,rcv);
		
		//将已完成前面的nid补齐
		for(String s : vaccceaselistAll){
			int dosage = Integer.parseInt(s.substring(2,3));
			for(int i = dosage; i > 0; i --){
				vaccceaselist.add(s.substring(0, 2) + i);
			}
		}			
		
		//将已完成的接种记录拼接
		String vacccease = "";
		for(String s : vaccceaselist){
			vacccease += "'"+ s +"'" + ",";
		}
		if(vacccease.endsWith(",")){
			vacccease = vacccease.substring(0, vacccease.length() -1);
		}		
		
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("localCode", OfficeService.getFirstOfficeCode());
		//是否需要月龄限制
		if(mouthAgeLimit){
			parm.put("monage", mouage+"");
		}else{
			parm.put("monage", "");
		}
		//排序
		parm.put("orderBy",orderBy);
		parm.put("lives",lives);
		parm.put("vacccease", vacccease);
		if(rownum > 0){
			parm.put("rownum", (rownum+1)+"");
		}
		if(StringUtils.isNotBlank(type)){
			parm.put("type", type);
		}
		List<BsManageVaccinenum> templist = dao.getVaccList(parm);
		List<BsManageVaccinenum> returnList = new ArrayList<>();
		//去重
		List<String> vaccGroupList = new ArrayList<String>();
		for(int i=0;i<templist.size();i++){
		   BsManageVaccinenum num = templist.get(i);
		   if(vaccGroupList.contains(num.getGroup())){
			   int idx = vaccGroupList.indexOf(num.getGroup().toString());
			   if(returnList.get(idx).getPin() > num.getPin()){
				   returnList.set(idx,  num);
			   }
		   }else{
			   returnList.add(num);		
			   vaccGroupList.add(num.getGroup());
		   }
		 }
		
		//校验时间间隔
		for(int i = 0; i < returnList.size(); i ++){
			if(null == returnList.get(i).getPintime()){
				continue;
			}
			for(ChildVaccinaterecord tr: rcv){
				if(!returnList.get(i).getGroup().equals(tr.getNid().substring(0,2))){
					continue;
				}
				//当今天再间隔时间之前，则不能接种
				if(ChildVaccinaterecord.STATUS_NOT.equals(tr.getStatus())){
					if(finish == 0){
						returnList.remove(i--);
						break;
					}else{
						continue;
					}
				}
				if(returnList.get(i).getPintime()>700 && returnList.get(i).getPintime()<800){
					if(tr.getVaccinatedate() != null 
							&& DateUtils.getDay(targetDate).before(DateUtils.addDays(DateUtils.getDay(tr.getVaccinatedate()), (returnList.get(i).getPintime().intValue()-700)*7))){
						returnList.remove(i--);
					}
					break;
				}else{
					if(tr.getVaccinatedate() != null 
							&& DateUtils.getDay(targetDate).before(DateUtils.addMonths(DateUtils.getDay(tr.getVaccinatedate()), returnList.get(i).getPintime().intValue()))){
						returnList.remove(i--);
					}
					break;
				}
			}
		}
		
		//校验特殊规则
		checkRule(returnList,rules,rcv,targetDate, baseinfo);
		
		//校验hib规则
		boolean isbreak = false;
		for( int i = 0; i < returnList.size(); i ++){
			if(isbreak){
				break;
			}
			if(BsManageVaccine.VACC_HIB_GNUM.equals(returnList.get(i).getGroup())){
				//校验hib
				List<ChildVaccinaterecord> hibs = new ArrayList<ChildVaccinaterecord>();
				int firstBlood = 0;
				int lastPin = 0;
				for(ChildVaccinaterecord c : rcv){
					if(BsManageVaccine.VACC_HIB_GNUM.equals(c.getVaccineid().substring(0, 2))){
						hibs.add(c);
						int tempLast = DateUtils.subtractMonths(baseinfo.getBirthday(),c.getVaccinatedate());
						lastPin = tempLast>lastPin?tempLast:lastPin;
						if("1".equals(c.getDosage())){
							firstBlood = tempLast;
						}
					}
				}
				
				if(firstBlood >= 12){
					returnList.remove(i--);
					isbreak = true;
					break;
				}
				if(lastPin >= 18){
					returnList.remove(i--);
					isbreak = true;
					break;
				}
				if(hibs.size() > 0){
					if(mouage >= 12 && mouage < 18){
						returnList.remove(i--);
						isbreak = true;
						break;
					}
					if(mouage < 12){
						if(firstBlood >= 6 && firstBlood < 12 && returnList.get(i).getPin() > 2){
							returnList.remove(i--);
							isbreak = true;
							break;
						}else if(firstBlood < 6 && returnList.get(i).getPin() > 3){
							returnList.remove(i--);
							isbreak = true;
							break;
						}
					}
				}
			}
		}
		//校验联合疫苗 所替代疫苗都可以打，才可以打
		List<BsManageVaccine> vaccs = bsManageVaccineDao.findList(new BsManageVaccine());
		BsManageBinded bb = new BsManageBinded();
		bb.setOrderBy(" a.\"LEVEL\" desc ,a.BIND_VACC_ID"); 
		bb.setBind(BsManageBinded.BIND_YES);
		List<BsManageBinded> binds = bsManageBindedDao.findList(bb);
		for(BsManageBinded bind : binds){
			String mNum = "";
			for(BsManageVaccine bv : vaccs){
				if(bind.getBindVaccId().equals(bv.getId())){
					mNum = bv.getmNum();
					break;
				}
			}
			bind.setBindVaccId(mNum);
			mNum = "";
			for(BsManageVaccine bv : vaccs){
				if(bind.getVaccId().equals(bv.getId())){
					mNum = bv.getmNum();
					break;
				}
			}
			bind.setVaccId(mNum);
		}
		
		Map<String, List<BsManageBinded>> bindMap = new LinkedMap();
		List<BsManageBinded> bindlist = new ArrayList<BsManageBinded>();
		String flag = "first";
		for(BsManageBinded b : binds){
			if("first".equals(flag)){
				flag = b.getBindVaccId();
			}
			if(!flag.equals(b.getBindVaccId())){
				bindMap.put(flag, bindlist);
				flag = b.getBindVaccId();
				bindlist = new ArrayList<BsManageBinded>();
			}
			bindlist.add(b);
		}
		if(bindlist.size() > 0){
			bindMap.put(flag, bindlist);
		}
		
		for(int i = 0; i < returnList.size(); i ++){
    		if(bindMap.keySet().contains(returnList.get(i).getGroup())){
    			List<BsManageBinded> bdl = bindMap.get(returnList.get(i).getGroup());
    			int match = 0;
    			for( BsManageVaccinenum rl :returnList){
    				for( BsManageBinded b :bdl){
        				if(rl.getGroup().equals(b.getVaccId())){
        					match ++;
        				}
        			}
    			}
    			if(match != bdl.size()){
    				returnList.remove(i--);
    			}
    			
    		}
	    }
		return returnList;
	}

	/**
	 * 计算一类苗时间
	 * @author fuxin
	 * @date 2017年9月19日 下午4:10:07
	 * @description 
	 *		TODO
	 * @param vaccList
	 * @param childid 
	 * @param initDate	 第一次
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> setReserveDate(List<BsManageVaccinenum> vaccList, String childid) {
		return setReserveDate(vaccList, childid, null, false);
	}
	
	/**
	 * 计算一类苗时间
	 * @author fuxin
	 * @date 2017年9月19日 下午4:10:07
	 * @description 
	 *		TODO
	 * @param vaccList
	 * @param childid 
	 * @param rcv	 接种记录, =null时自动查询
	 * @param inDay	 是否当天接种 =true当天接种，下一剂14天以后
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> setReserveDate(List<BsManageVaccinenum> vaccList, String childid, List<ChildVaccinaterecord> rcv, boolean inDay) {
		/** 活苗的模型大类 */
		List<String> liveVaccs = Arrays.asList("01","03","09","12","13","14","82","84");
		//儿童信息
		ChildBaseinfo baseinfo = childInfoService.get(childid);
		
		//将未种一类苗根据月龄进行排序
		vaccList.sort(new Comparator<BsManageVaccinenum>() {
			@Override
			public int compare(BsManageVaccinenum o1, BsManageVaccinenum o2) {
				if(o1.getMouage() > o2.getMouage()){
					return 1;
				}
				if(o1.getMouage() < o2.getMouage()){
					return -1;
				}
				return 0;
			}
		});
		
		if(rcv == null){
			ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
			tempRcv.setChildid(childid);
			rcv = childRecordDao.findList(tempRcv);
		}
		ChildVaccinaterecord[] lastRcv = getLastRecord(rcv);
		//获取最近完成的疫苗
		ChildVaccinaterecord recordLast = lastRcv[0];
		Date lastVacc;
		Date now = new Date();
		if(recordLast == null){
			if(inDay){
				lastVacc = now;
			}else{
				lastVacc = DateUtils.addDays(now, -14);
			}
		}else{
			lastVacc = recordLast.getVaccinatedate();
		}
		//获取最近的活苗
		ChildVaccinaterecord recordLive = lastRcv[1];
		Date lastLiveVacc;
		if(recordLive == null){
			if(inDay){
				lastLiveVacc = now;
			}else{
				lastLiveVacc = DateUtils.addMonths(now, -1);
			}
		}else{
			lastLiveVacc = recordLive.getVaccinatedate();
		}
		
		for(BsManageVaccinenum num : vaccList){
			Date vaccDate = new Date();
			//计算月龄满足条件时间
			Date mouthDate = DateUtils.addMonths(baseinfo.getBirthday(), num.getMouage().intValue());
			//计算满足时间间隔的时间
			if(num.getPin() > 1 && num.getPintime() != null){
				for(ChildVaccinaterecord record : rcv){
					if(record.getNid().equals(num.getGroup() + (num.getPin() - 1)) && record.getVaccinatedate() != null){
						Date tempDate = null;
						if(num.getPintime()>700 && num.getPintime()<800){
							tempDate = DateUtils.addDays(DateUtils.truncate(record.getVaccinatedate(),Calendar.DAY_OF_MONTH), (num.getPintime().intValue()-700)*7);
						}else{
							tempDate = DateUtils.addMonths(DateUtils.truncate(record.getVaccinatedate(),Calendar.DAY_OF_MONTH), num.getPintime().intValue());
						}
						mouthDate = tempDate.after(mouthDate)?tempDate:mouthDate;
					}
				}
			}
			//计算普通苗间隔满足条件时间
			if(liveVaccs.contains(num.getGroup())){
				lastLiveVacc = DateUtils.addMonths(lastLiveVacc, 1);
				vaccDate = vaccDate.after(lastLiveVacc)?vaccDate:lastLiveVacc;
				vaccDate = vaccDate.after(mouthDate)?vaccDate:mouthDate;
				lastLiveVacc = vaccDate;
				vaccDate = holidayService.nextWorkDay(vaccDate);
				if(vaccDate != null){
					lastLiveVacc = vaccDate;
				}else{
					vaccDate = lastLiveVacc;
				}
			}else{
				lastVacc = DateUtils.addDays(lastVacc, 14);
				vaccDate = vaccDate.after(lastVacc)?vaccDate:lastVacc;
				vaccDate = vaccDate.after(mouthDate)?vaccDate:mouthDate;
				lastVacc = vaccDate;
				vaccDate = holidayService.nextWorkDay(vaccDate);
				if(vaccDate != null){
					lastVacc = vaccDate;
				}else{
					vaccDate = lastVacc;
				}
			}
			num.setRemindDate(vaccDate);			
		}
		return vaccList;
	}
		
	/**
	 * 根据接种记录获取最后接种的疫苗和最后接种的活苗
	 * @author fuxin
	 * @date 2017年9月19日 下午4:26:36
	 * @description 
	 *		TODO
	 * @param rcv
	 * @return		 	
	 * 	returnRcv[0] = recordLast; 最后接种记录 <br>
	 *	returnRcv[1] = recordLive; 最后接种活苗记录
	 *
	 */
	public static ChildVaccinaterecord[] getLastRecord(List<ChildVaccinaterecord> rcv){
		ChildVaccinaterecord[] returnRcv = {null,null};
		//获取最近的活苗
		ChildVaccinaterecord recordLive = null;
		//获取最近完成的疫苗
		ChildVaccinaterecord recordLast = null;
		for(ChildVaccinaterecord cr : rcv){
			if(ChildVaccinaterecord.SOURCE_CJ.equals(cr.getSource()) || cr.getVaccinatedate() == null || ChildVaccinaterecord.STATUS_NOT.equals(cr.getStatus())){
				continue;
			}
			if(recordLast == null){
				recordLast = cr;
			}
			if(cr.getVaccinatedate().after(recordLast.getVaccinatedate())){
				recordLast = cr;
			}
			if(cr.getVaccine() != null 
					&& StringUtils.isNotBlank(cr.getVaccine().getLive()) 
					&& BsManageVaccine.LIVE_YES.equals(cr.getVaccine().getLive()) 
					&& cr.getVaccinatedate().before(DateUtils.truncate(new Date(),Calendar.DATE))){
				if(recordLive == null){
					recordLive = cr;
				}
				if(cr.getVaccinatedate().after(recordLive.getVaccinatedate())){
					recordLive = cr;
				}
			}
		}
		returnRcv[0] = recordLast;
		returnRcv[1] = recordLive;
		return returnRcv;
	}

	/**
	 * 查询儿童未完成的接种计划
	 * @author fuxin
	 * @date 2017年11月3日 下午7:03:17
	 * @description 
	 *		TODO
	 * @param childid
	 * @return
	 *
	 */
	public List<BsManageVaccinenum> findNotFinishNum(String childid) {
		ChildBaseinfo info = new ChildBaseinfo();
		info.setId(childid);
		return dao.findNotFinishNum(info);
	}
		
		/**
		 * 判断是否可以接种
		 * @param childBaseinfo
		 * @return
		 */
		public boolean checkVacc(ChildBaseinfo childBaseinfo) {
			ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
			OfficePreference op = OfficeService.getOfficeOption();
			tempRcv.setChildid(childBaseinfo.getId());
			List<ChildVaccinaterecord> rcv = childRecordDao.findList(tempRcv);
			ChildVaccinaterecord[] lastRcv = getLastRecord(rcv);
			Date now = DateUtils.getDay(DateUtils.addDays(new Date(),1));
			//获取最近完成的疫苗
			ChildVaccinaterecord recordLast = lastRcv[0];
			Date lastVacc;
			if(recordLast == null){
				lastVacc = DateUtils.addDays(now, -op.getVacDelay());
			}else{
				lastVacc = DateUtils.getDay(DateUtils.addDays(recordLast.getVaccinatedate(), 1));
			}
			//获取最近的活苗
			ChildVaccinaterecord recordLive = lastRcv[1];
			Date lastLiveVacc;
			if(recordLive == null){
				lastLiveVacc = DateUtils.addMonths(now, -1);
			}else{
				lastLiveVacc = DateUtils.getDay(DateUtils.addDays(recordLast.getVaccinatedate(), 1));
			}
			if(vaccineService.findLiveGnumList().indexOf(childBaseinfo.getNid().substring(0,2))>-1){
				if(now.after(DateUtils.addMonths(lastLiveVacc,1))&& now.after(DateUtils.addDays(lastVacc,op.getVacDelay())))
					return true;
			}else{
				if(now.after(DateUtils.addDays(lastVacc,op.getVacDelay())))
					return true;
			}
			return false;
		}
		
}
