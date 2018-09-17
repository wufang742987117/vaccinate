/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_vaccinaterecord.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.SMS;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.BCV;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.service.ChildBaseinfoService;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.dao.ChildVaccinaterecordDao;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.inoculate.dao.QueneDao;
import com.thinkgem.jeesite.modules.inoculate.entity.Quene;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.refund.service.SysRefundService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageBinded;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageBindedService;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 疫苗接种管理Service
 * 
 * @author 王德地
 * @version 2017-02-07
 */

@Service
@Transactional(readOnly = true)
public class ChildVaccinaterecordService extends CrudService<ChildVaccinaterecordDao, ChildVaccinaterecord> {
	
	@Autowired
	private ChildBaseinfoService childBaseinfoService;
	@Autowired
	private BsManageProductService bsManageProductService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private BsManageBindedService bsManageBindedService;
	@Autowired
	private SysRefundService sysRefundService;

	public ChildVaccinaterecord get(String id) {
		return super.get(id);
	}

	public List<ChildVaccinaterecord> findList(ChildVaccinaterecord childVaccinaterecord) {
		if(childVaccinaterecord != null && StringUtils.isNotBlank(childVaccinaterecord.getChildcode())){
			ChildBaseinfo baseinfo = childBaseinfoService.getByNo(childVaccinaterecord.getChildcode(), childVaccinaterecord.getLocalCode());
			childVaccinaterecord.setChildcode(null);
			childVaccinaterecord.setChildid(baseinfo.getId());
		}
		List<ChildVaccinaterecord> list = super.findList(childVaccinaterecord);
		return findList4(list,StringUtils.isBlank(childVaccinaterecord.getSource()));
	}
	
	/**
	 * 获取所有记录
	 * @author fuxin
	 * @date 2017年7月31日 下午9:02
	 * @description 
	 *		TODO
	 * @param list
	 * @param isblank 是否去除替代记录 true-去除 false-不去除
	 * @return
	 *
	 */
	public static List<ChildVaccinaterecord> findList4(List<ChildVaccinaterecord> list, boolean isblank){
		if(isblank){
			for(int i = 0; i <list.size(); i ++ ){
				if("4".equals(list.get(i).getSource())){
					list.remove(i--);
				}
			}
		}
		return list;
	};
	
	public List<ChildVaccinaterecord> findListWith4(ChildVaccinaterecord childVaccinaterecord) {
		return super.findList(childVaccinaterecord);
	}
	
	public Page<ChildVaccinaterecord> findPage(Page<ChildVaccinaterecord> page,
			ChildVaccinaterecord childVaccinaterecord) {
		return super.findPage(page, childVaccinaterecord);
	}
	
	/**
	 * 保存接种记录，若为新纪录则插入替代记录
	 * @author fuxin
	 * @date 2017年9月29日 下午9:41:32
	 * @description 
	 *		TODO
	 * @param vname
	 * @return
	 *
	 */
	@Transactional(readOnly = false)
	public void save(ChildVaccinaterecord childVaccinaterecord) {
		boolean isnew = childVaccinaterecord.getIsNewRecord();
		super.save(childVaccinaterecord);
		//生成替代记录
		if(isnew){
			genBindedRecored(childVaccinaterecord);
		}
		ChildVaccinaterecord tempRec = new ChildVaccinaterecord();
		tempRec.setChildid(childVaccinaterecord.getChildid());
		if(childVaccinaterecord.getLocalCode()!=null) {
			tempRec.setLocalCode(childVaccinaterecord.getLocalCode());
		}
		List<ChildVaccinaterecord> rcvs =  findListWith4(tempRec);
		updateBind(rcvs, childVaccinaterecord);
		//调整剂次顺序
		updatePin(rcvs);
	}
	
	/**
	 * 逻辑删除,删除替代记录
	 * @author fuxin
	 * @date 2017年9月29日 下午9:46:45
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 *
	 */
	@Transactional(readOnly = false)
	public void delFlag(ChildVaccinaterecord childVaccinaterecord){
		childVaccinaterecord.setStatus(ChildVaccinaterecord.STATUS_DEL);
//		delBindedRecored(childVaccinaterecord.getId());
		dao.update(childVaccinaterecord);
		ChildVaccinaterecord tempRec = new ChildVaccinaterecord();
		tempRec.setChildid(childVaccinaterecord.getChildid());
		List<ChildVaccinaterecord> rcvs =  findListWith4(tempRec);
		updateBind(rcvs, childVaccinaterecord);
		updatePin(rcvs);
	}

	@Transactional(readOnly = false)
	public void delete(ChildVaccinaterecord childVaccinaterecord) {
		super.delete(childVaccinaterecord);
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午4:30:32
	 * @description 
	 *	childVaccinaterecord的信息转换	
	 * @param childVaccinaterecord
	 * @return
	 *
	 */
	public ChildVaccinaterecord sel(ChildVaccinaterecord childVaccinaterecord){
		// 儿童编码和名称
		ChildBaseinfo childBaseinfo = new ChildBaseinfo();
		if (!childVaccinaterecord.getChildcode().equals("")) {
			childBaseinfo.setChildcode(childVaccinaterecord.getChildcode());
		}
		if (!childVaccinaterecord.getChildname().equals("")) {
			childBaseinfo.setChildname(childVaccinaterecord.getChildname());
			List<ChildBaseinfo> childBaseinfoList = childBaseinfoService
					.findList(childBaseinfo);
			if (childBaseinfoList.size() > 0) {
				String childid = childBaseinfoList.get(0).getId();
				childVaccinaterecord.setChildid(childid);
			}
		}
		// 疫苗名称
		if (StringUtils.isNotBlank(childVaccinaterecord.getName())) {
			BsManageVaccine bsManageVaccine = new BsManageVaccine();
			bsManageVaccine.setName(childVaccinaterecord.getName());
			List<BsManageVaccine> bsManageVaccinelist = bsManageVaccineService.findList(bsManageVaccine);
			if (bsManageVaccinelist.size() > 0) {
				String vaccineid = bsManageVaccinelist.get(0).getId();
				childVaccinaterecord.setVaccineid(vaccineid);
			}
		} else {
			childVaccinaterecord.setName(null);
		}
		// 结束时间为当天23：59：59：999
			Calendar day=Calendar.getInstance();
			day.setTime(childVaccinaterecord.getEndTime());
			day.set(Calendar.HOUR_OF_DAY,23);
			day.set(Calendar.MINUTE,59);
			day.set(Calendar.SECOND, 59);
			day.set(Calendar.MILLISECOND, 999);
			childVaccinaterecord.setEndTime(day.getTime());
	
		if (childVaccinaterecord.getBeginTime().compareTo(childVaccinaterecord.getEndTime())>0) {
			return null;
		}
		return childVaccinaterecord;
	}
	
	/**
	 * 
	 * @param count 往前推的月份
	 * @param arges
	 * @return
	 */
	public Date[] countmonth(int count,Date... arges){
		if (arges[0] == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -count);
	        Date m = c.getTime();
			arges[0] = DateUtils.parseDate(DateUtils.formatDate(m, "yyyy-MM-dd HH:mm:ss"));// 表示当天的零点
		}
		//判断结束时间，如果结束时间为空则默认当前时间
		if (arges[1] == null) {
			arges[1]=DateUtils.parseDate(DateUtils.getDateTime());
		}
		// 确保beginTime在endTime之前
		if (arges[0].compareTo(arges[1]) == 0 || arges[1] != null) {
			Calendar day=Calendar.getInstance();
			day.setTime(arges[1]);
			day.set(Calendar.HOUR_OF_DAY,23);
			day.set(Calendar.MINUTE,59);
			day.set(Calendar.SECOND, 59);
			day.set(Calendar.MILLISECOND, 999);
			arges[1]=day.getTime();
			day.setTime(arges[0]);
			day.set(Calendar.HOUR_OF_DAY,00);
			day.set(Calendar.MINUTE,00);
			day.set(Calendar.SECOND, 00);
			day.set(Calendar.MILLISECOND, 000);
			arges[0]=day.getTime();
		}
		if (arges[0].compareTo(arges[1])>0) {
			return null;
		}
		return arges;
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午12:55:29
	 * @description 
	 *	时间判断
	 * @param arges
	 * @return
	 *
	 */
	public Date[] date(Date... arges){
		if (arges[0] == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -1);
	        Date m = c.getTime();
			arges[0] = DateUtils.parseDate(DateUtils.formatDate(m, "yyyy-MM-dd HH:mm:ss"));// 表示当天的零点
		}
		//判断结束时间，如果结束时间为空则默认当前时间
		if (arges[1] == null) {
			arges[1]=DateUtils.parseDate(DateUtils.getDateTime());
		}
		// 确保beginTime在endTime之前
		if (arges[0].compareTo(arges[1]) == 0 || arges[1] != null) {
			Calendar day=Calendar.getInstance();
			day.setTime(arges[1]);
			day.set(Calendar.HOUR_OF_DAY,23);
			day.set(Calendar.MINUTE,59);
			day.set(Calendar.SECOND, 59);
			day.set(Calendar.MILLISECOND, 999);
			arges[1]=day.getTime();
			day.setTime(arges[0]);
			day.set(Calendar.HOUR_OF_DAY,00);
			day.set(Calendar.MINUTE,00);
			day.set(Calendar.SECOND, 00);
			day.set(Calendar.MILLISECOND, 000);
			arges[0]=day.getTime();
		}
		if (arges[0].compareTo(arges[1])>0) {
			return null;
		}
		return arges;
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年5月8日 上午9:21:33
	 * @description 
	 *		TODO 当前时间
	 * @param arges
	 * @return
	 *
	 */
	public Date[] dateTime(Date... arges){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (arges[0] == null) {
			c.set(Calendar.HOUR_OF_DAY,00);
			c.set(Calendar.MINUTE,00);
			c.set(Calendar.SECOND, 00);
			c.set(Calendar.MILLISECOND, 000);
			arges[0] = c.getTime();
		}
		//判断结束时间，如果结束时间为空则默认当前时间
		if (arges[1] == null) {
			c.set(Calendar.HOUR_OF_DAY,23);
			c.set(Calendar.MINUTE,59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			arges[1] = c.getTime();
		}
		// 确保beginTime在endTime之前
		if (arges[0].compareTo(arges[1]) == 0||arges[1] != null) {
			arges = updateDate(arges);
		}
		if (arges[0].compareTo(arges[1])>0) {
			Date argess = null;
			argess = arges[0];
			arges[0] = arges[1];
			arges[1] = argess;
			arges = updateDate(arges);
		}
		return arges;
	}
	
	/**
	 * 转换时分秒
	 * @author zhouqj
	 * @date 2017年9月14日 下午4:09:35
	 * @description 
	 *		TODO
	 * @param arges
	 * @return
	 *
	 */
	private Date[] updateDate(Date[] arges) {
		Calendar day = Calendar.getInstance();
		day.setTime(arges[1]);
		day.set(Calendar.HOUR_OF_DAY,23);
		day.set(Calendar.MINUTE,59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		arges[1] = day.getTime();
		day.setTime(arges[0]);
		day.set(Calendar.HOUR_OF_DAY,00);
		day.set(Calendar.MINUTE,00);
		day.set(Calendar.SECOND, 00);
		day.set(Calendar.MILLISECOND, 000);
		arges[0] = day.getTime();
		return arges;
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月28日 上午11:45:25
	 * @description 
	 *		根据儿童ID查询儿童最近的一次接种时间，若等于今天则取第二个，第三个......
	 * @param childVaccinaterecord
	 * @return
	 *
	 */
	public List<ChildVaccinaterecord> date(ChildVaccinaterecord childVaccinaterecord) {
		return	dao.date(childVaccinaterecord);
	}
	
	/**
	 * 添加接种记录时插入数据
	 * @author liyuan
	 * @date 2018年2月26日 上午9:31:13
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @param nid
	 * @param p
	 * @return
	 *
	 */
	public ChildVaccinaterecord insertion(ChildVaccinaterecord childVaccinaterecord,String nid,BsManageProduct	p) {
		return insertion(childVaccinaterecord, nid, p, OfficeService.getFirstOfficeCode());
	}
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月22日 下午11:47:45
	 * @description 
	 *		添加接种记录时插入数据
	 * @param childVaccinaterecord
	 * @param nid
	 * @return
	 *
	 */
	public ChildVaccinaterecord insertion(ChildVaccinaterecord childVaccinaterecord,String nid,BsManageProduct	p,String localcode) {
		//接种单位
		String code = OfficeService.getFirstOfficeCode();
		if(StringUtils.isBlank(childVaccinaterecord.getOffice())){
			childVaccinaterecord.setOffice(code);	
		}
		//疫苗接种计划ID
		if(StringUtils.isBlank(childVaccinaterecord.getNid())){
			childVaccinaterecord.setNid(nid);
		}
		//接种状态
		if(StringUtils.isBlank(childVaccinaterecord.getStatus())){
			childVaccinaterecord.setStatus("0");
		}
		//获取疫苗的大类名称
//		BsManageVaccinenum bsManageVaccinenum=new BsManageVaccinenum();
//		bsManageVaccinenum=	bsManageVaccinenumService.get(nid);
//		if(StringUtils.isBlank(childVaccinaterecord.getVaccCode())){
//			childVaccinaterecord.setVaccCode(bsManageVaccinenum.getName());
//		}
					
		if(StringUtils.isBlank(p.getId())){
			//疫苗名称
			p.setVaccName(childVaccinaterecord.getVaccName());
			//疫苗批次
			p.setBatchno(childVaccinaterecord.getBatch());
			//疫苗厂家
			p.setManufacturer(childVaccinaterecord.getManufacturer());
			p.setShowAll(BsManageProduct.SHOWALL_YES);
			p.setLocalCode(localcode);
			List<BsManageProduct> plist=bsManageProductService.findQueueViewListApi(p);
			//疫苗小类的详细信息
			if(plist.size()==1){
				p=plist.get(0);
			}else{
				childVaccinaterecord.setProductid(null);
				return childVaccinaterecord;
			}
		}
		
		//疫苗小类的vaccineID
		if(StringUtils.isBlank(childVaccinaterecord.getVaccineid())){
			childVaccinaterecord.setVaccineid(p.getVaccineid());
		}
		//productid
		/*if(StringUtils.isBlank(childVaccinaterecord.getProductid())){*/
			childVaccinaterecord.setProductid(p.getId());
		/*}*/
		//获取疫苗的大类名称
		childVaccinaterecord.setVaccCode(p.getGname());
		
		//疫苗厂家编码
		if(StringUtils.isBlank(childVaccinaterecord.getManufacturer())){
			childVaccinaterecord.setManufacturer(p.getManufacturer());
		}
		//疫苗厂家编码
		if(StringUtils.isBlank(childVaccinaterecord.getManufacturercode())){
			childVaccinaterecord.setManufacturercode(p.getCode());
		}
		return childVaccinaterecord;
	}

	/**
	 * 向省疾控中心查询儿童信息
	 * @author wangdedi
	 * @date 2017年4月27日 下午4:15:04
	 * @description 
	 *		TODO
	 * @param childcode
	 * @return
	 *
	 */
	public String downloadChildBaseInfo (String childcode,String localCode){
		String methodNme = "downloaddept";
		return HttpClientReq.webServiceInvoke(methodNme, new Object[]{childcode,"0",localCode}, logger);
	}
	
	public String signature (String signature){
		return dao.signature(signature, OfficeService.getFirstOfficeCode());
	}

	public void insertBcv(BCV bc) {
		dao.insertBcv(bc);
		
	}

	/**
	 * 签字插入
	 * @author zhouqj
	 * @date 2017年5月23日 下午2:16:08
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 *
	 */
	public void saveSignature(ChildVaccinaterecord childVaccinaterecord) {
		dao.insertSignature(childVaccinaterecord);
	}
	
	public void insertSignatures(ChildVaccinaterecord childVaccinaterecord) {
		dao.insertSignatures(childVaccinaterecord);
	}
	
	public void updateSignature(ChildVaccinaterecord childVaccinaterecord) {
		dao.updateSignature(childVaccinaterecord);
	}
	
	/**
	 * 短信发送
	 */
	@SuppressWarnings("finally")
	public String sendNoticeSMS(String mobiles, String name) {
		logger.info("发送短信:号码-" + mobiles + " 参数-"
				+ JsonMapper.toJsonString(name));
		String result = null;
		try {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("name", name);
			result = SMS.sendaliMSM(mobiles, parm, SMS.TEMP_NOTICE);
			logger.info("短信发送成功:" + result);
		} catch (Exception e) {
			logger.error("短信发送失败" + e.getMessage());
		} finally {
			return result;
		}
	}
	
	/**
	 * 获取最后一剂活苗时间
	 * @author fuxin
	 * @date 2017年7月28日 下午8:03:31
	 * @description 
	 *		TODO
	 * @param id
	 * @return
	 *
	 */
	public int getLastLiveDays(ChildBaseinfo childid) {
		return dao.getLastLiveDays(childid);
	}
	

	/**
	 * 查询该记录签字是否存在
	 * @author zhouqj
	 * @date 2017年9月8日 下午7:21:24
	 * @description 
	 *		TODO
	 * @param record
	 * @return
	 *
	 */
	public int querySignature(ChildVaccinaterecord record) {
		return dao.querySignature(record);
	}

	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年9月8日 下午7:31:38
	 * @description 
	 *		TODO
	 * @param record
	 *
	 */
	public void updateSignatures(ChildVaccinaterecord record) {
		dao.updateSignatures(record);
	}



	/**
	 * 取消提醒
	 * @author fuxin
	 * @date 2017年9月16日 下午2:40:50
	 * @description 
	 *		TODO
	 * @param id
	 *
	 */
	public int cancelRemind(String id) {
		return dao.cancelRemind(id);
	}
	

	public int genBindedRecored(List<ChildVaccinaterecord> childVaccinaterecords){
		return genBindedRecored(childVaccinaterecords, OfficeService.getFirstOfficeCode());
	}

	/**
	 * 根据联合疫苗替代规则生成替代记录
	 * @author fuxin
	 * @date 2017年9月29日 下午5:19:06
	 * @description 
	 *		TODO
	 * @param rec
	 * @return
	 *
	 */
	public int genBindedRecored(List<ChildVaccinaterecord> childVaccinaterecords, String localcode){
		List<ChildVaccinaterecord> rcvOrgin = new ArrayList<ChildVaccinaterecord>();
		rcvOrgin.addAll(childVaccinaterecords);
		int count = 0;
		//检查参数
		if(childVaccinaterecords == null || childVaccinaterecords.size() == 0){
			return count;
		}
		BsManageVaccine tempVacc = new BsManageVaccine();
		tempVacc.setLocalCode(localcode);
		List<BsManageVaccine> vaccs = bsManageVaccineService.findList(tempVacc);
		BsManageBinded bb = new BsManageBinded();
		bb.setOrderBy(" a.\"LEVEL\" "); 
		bb.setLocalCode(localcode);
		List<BsManageBinded> binds = bsManageBindedService.findList(bb);
		List<ChildVaccinaterecord> newRec = new ArrayList<ChildVaccinaterecord>();
		
		for(BsManageBinded bin : binds){
			for(int i = 0; i < childVaccinaterecords.size(); i ++){
				ChildVaccinaterecord r = childVaccinaterecords.get(i);
				if(!r.getVaccineid().equals(bin.getBindVaccId()) 
						|| (bin.getBindVaccPin().intValue() != 0 && bin.getBindVaccPin().intValue() != r.getDosageInt())){
					continue;
				}
				String mNum = "";
				for(BsManageVaccine bv : vaccs){
					if(bin.getVaccId().equals(bv.getId())){
						mNum = bv.getmNum();
						break;
					}
				}
				
				//生成替代记录，并修改默认值
				ChildVaccinaterecord tempRec = new ChildVaccinaterecord();
				BeanUtils.copyProperties(r, tempRec);
				tempRec.setSource(ChildVaccinaterecord.SOURCE_CJ);
				tempRec.setId(IdGen.uuid());
				tempRec.setNid(mNum);
				tempRec.setBatch("00000000");
				tempRec.setVaccineid(bin.getVaccId());
				tempRec.setVaccCode(bin.getVaccine().getgName());
				tempRec.setVaccName(r.getVaccName());
				tempRec.setManufacturer("");
				tempRec.setManufacturercode("");
				tempRec.setProductid("");
				tempRec.setInocUnionCode(r.getVaccineid());
				tempRec.setInocUnionRecord(r.getId());
				tempRec.setRemarks(r.getVaccName());
				tempRec.setSign(ChildVaccinaterecord.SIGNATURE_NO);
				tempRec.setStatus(ChildVaccinaterecord.STATUS_NOT.equals(r.getStatus())?ChildVaccinaterecord.STATUS_NOT:ChildVaccinaterecord.STATUS_YET);
				tempRec.preInsert();
				tempRec.setDosage("0");
				tempRec.setNid(bin.getVaccine().getmNum() + "0");
				tempRec.setIsNewRecord(true);
				newRec.add(tempRec);
				count ++;
			}
		}
		
		
		rcvOrgin.addAll(newRec);
		rcvOrgin.sort(new RcvComparator());
		
		//更新剂次
		String option = "first";
		String optype = "first";
		int idx = 1;
		for(ChildVaccinaterecord rec : rcvOrgin){
			if(option.equals("first")){
				option = rec.getNidPre();
				optype = rec.getVacctype();
			}
			if(!option.equals(rec.getNidPre()) ||!optype.equals(rec.getVacctype()) ){
				option = rec.getNidPre();
				optype = rec.getVacctype();
				idx = 1;
			}
			rec.setDosage(idx + "");
			rec.setNid(rec.getNidPre()  + idx);
			idx ++;
		}
		if(idx == 1 && rcvOrgin.size() > 0){
			rcvOrgin.get(rcvOrgin.size() - 1).setDosage(idx + "");
			rcvOrgin.get(rcvOrgin.size() - 1).setNid(rcvOrgin.get(rcvOrgin.size() - 1).getNidPre()  + idx);
		}
		
		
		//循环插入替代记录
		for(ChildVaccinaterecord nr : rcvOrgin){
			nr.setLocalCode(localcode);
			if(nr.getIsNewRecord()){
				dao.insert(nr);
			}else{
				dao.update(nr);
			}
		}
		return count;
	}
	
	/**
	 * 添加一条记录，生成模型替代记录
	 * @author fuxin
	 * @date 2018年1月14日 下午8:17:26
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 * @return
	 *
	 */
	private int genBindedRecored(ChildVaccinaterecord childVaccinaterecord) {
		
		ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
		tempRcv.setChildid(childVaccinaterecord.getChildid());
		tempRcv.setStatus(ChildVaccinaterecord.STATUS_YET);
		tempRcv.setOrderBy(" a.vaccinatedate desc");
		if(childVaccinaterecord.getLocalCode()!=null) {
			tempRcv.setLocalCode(childVaccinaterecord.getLocalCode());
		}
		List<ChildVaccinaterecord> childVaccinaterecords = findList(tempRcv);
		
		List<ChildVaccinaterecord> rcvOrgin = new ArrayList<ChildVaccinaterecord>();
		rcvOrgin.addAll(childVaccinaterecords);
		int count = 0;
		//检查参数
//		if(childVaccinaterecords == null || childVaccinaterecords.size() == 0){
//			return count;
//		}
		//List<BsManageVaccine> vaccs = bsManageVaccineService.findList(new BsManageVaccine());
		BsManageVaccine b1=new BsManageVaccine();
		if(childVaccinaterecord.getLocalCode()!=null) {
			b1.setLocalCode(childVaccinaterecord.getLocalCode());
		}
		List<BsManageVaccine> vaccs = bsManageVaccineService.findList(b1);
		BsManageBinded bb = new BsManageBinded();
		bb.setOrderBy(" a.\"LEVEL\" "); 
		if(childVaccinaterecord.getLocalCode()!=null) {
			bb.setLocalCode(childVaccinaterecord.getLocalCode());
		}
		List<BsManageBinded> binds = bsManageBindedService.findList(bb);
		List<ChildVaccinaterecord> newRec = new ArrayList<ChildVaccinaterecord>();
		List<ChildVaccinaterecord> newRecBind = new ArrayList<ChildVaccinaterecord>();
		
		for(BsManageBinded bin : binds){
			if(!childVaccinaterecord.getVaccineid().equals(bin.getBindVaccId()) 
					|| (bin.getBindVaccPin().intValue() != 0 && bin.getBindVaccPin().intValue() != childVaccinaterecord.getDosageInt())){
				continue;
			}
			
				String mNum = "";
				for(BsManageVaccine bv : vaccs){
					if(bin.getVaccId().equals(bv.getId())){
						mNum = bv.getmNum();
						break;
					}
				}
				
				//生成替代记录，并修改默认值
				ChildVaccinaterecord tempRec = new ChildVaccinaterecord();
				BeanUtils.copyProperties(childVaccinaterecord, tempRec);
				tempRec.setSource(ChildVaccinaterecord.SOURCE_CJ);
				tempRec.setId(IdGen.uuid());
				tempRec.setNid(mNum);
				tempRec.setBatch("00000000");
				tempRec.setVaccineid(bin.getVaccId());
				tempRec.setVaccCode(bin.getVaccine().getgName());
				tempRec.setVaccName(childVaccinaterecord.getVaccName());
				tempRec.setManufacturer("");
				tempRec.setManufacturercode("");
				tempRec.setProductid("");
				tempRec.setInocUnionCode(childVaccinaterecord.getVaccineid());
				tempRec.setInocUnionRecord(childVaccinaterecord.getId());
				tempRec.setRemarks(childVaccinaterecord.getVaccName());
				tempRec.setSign(ChildVaccinaterecord.SIGNATURE_NO);
				tempRec.setStatus(ChildVaccinaterecord.STATUS_YET.equals(childVaccinaterecord.getStatus())?ChildVaccinaterecord.STATUS_YET:ChildVaccinaterecord.STATUS_NOT);
				tempRec.preInsert();
				tempRec.setDosage("0");
				tempRec.setNid(bin.getVaccine().getmNum() + "0");
				tempRec.setIsNewRecord(true);
//				dao.insert(tempRec);
				newRec.add(tempRec);
//				if(!"1702".equals(bin.getBindVaccId()) 
//						&& !"1601".equals(bin.getBindVaccId()) 
//						&& !"1701".equals(bin.getBindVaccId()) 
//						&& !"1703".equals(bin.getBindVaccId())
//						&& !"1801".equals(bin.getBindVaccId())
//						&& !"1901".equals(bin.getBindVaccId())
//						&& !"1903".equals(bin.getBindVaccId())){
//					newRecBind.add(tempRec);
////					childVaccinaterecords.add(tempRec);
//				}
				count ++;
			
		}
		
		
		if(newRecBind.size() > 0){
			for(BsManageBinded bin : binds){
				for(int i = 0; i < newRecBind.size(); i ++){
					ChildVaccinaterecord r = newRecBind.get(i);
					if(!r.getVaccineid().equals(bin.getBindVaccId()) 
							|| (bin.getBindVaccPin().intValue() != 0 && bin.getBindVaccPin().intValue() != r.getDosageInt())){
						continue;
					}
					String mNum = "";
					for(BsManageVaccine bv : vaccs){
						if(bin.getVaccId().equals(bv.getId())){
							mNum = bv.getmNum();
							break;
						}
					}
					
					//生成替代记录，并修改默认值
					ChildVaccinaterecord tempRec = new ChildVaccinaterecord();
					BeanUtils.copyProperties(r, tempRec);
					tempRec.setSource(ChildVaccinaterecord.SOURCE_CJ);
					tempRec.setId(IdGen.uuid());
					tempRec.setNid(mNum);
					tempRec.setBatch("00000000");
					tempRec.setVaccineid(bin.getVaccId());
					tempRec.setVaccCode(bin.getVaccine().getgName());
					tempRec.setVaccName(r.getVaccName());
					tempRec.setManufacturer("");
					tempRec.setManufacturercode("");
					tempRec.setProductid("");
					tempRec.setInocUnionCode(r.getVaccineid());
					tempRec.setInocUnionRecord(r.getId());
					tempRec.setRemarks(r.getVaccName());
					tempRec.setSign(ChildVaccinaterecord.SIGNATURE_NO);
					tempRec.setStatus(ChildVaccinaterecord.STATUS_NOT.equals(r.getStatus())?ChildVaccinaterecord.STATUS_NOT:ChildVaccinaterecord.STATUS_YET);
					tempRec.preInsert();
					tempRec.setDosage("0");
					tempRec.setNid(bin.getVaccine().getmNum() + "0");
					tempRec.setIsNewRecord(true);
//					dao.insert(tempRec);
					newRec.add(tempRec);
//					if(!"1702".equals(bin.getBindVaccId()) 
//							&& !"1601".equals(bin.getBindVaccId()) 
//							&& !"1701".equals(bin.getBindVaccId()) 
//							&& !"1703".equals(bin.getBindVaccId())
//							&& !"1801".equals(bin.getBindVaccId())
//							&& !"1901".equals(bin.getBindVaccId())
//							&& !"1903".equals(bin.getBindVaccId())){
//						newRecBind.add(tempRec);
//					}
					count ++;
				}
			}
		}
		
		for(ChildVaccinaterecord nr : newRec){
			dao.insert(nr);
		}
		childVaccinaterecords.addAll(newRec);
		updatePin(childVaccinaterecords);
		return count;
	}
	
	/**
	 * 删除替代记录
	 * @author fuxin
	 * @param recs 
	 * @date 2017年9月29日 下午9:29:05
	 * @description 
	 *		TODO
	 * @param recordId
	 * @return
	 *
	 */
	@Transactional(readOnly=false)
	public int delBindedRecored(String recordId){
		int count = 0;
		if(StringUtils.isBlank(recordId)){
			return count;
		}
		ChildVaccinaterecord tempRecord = new ChildVaccinaterecord();
		tempRecord.setInocUnionRecord(recordId);
		tempRecord.setSource(ChildVaccinaterecord.SOURCE_CJ);
		List<ChildVaccinaterecord> recs = findListWith4(tempRecord);
		for(ChildVaccinaterecord rec : recs){
			dao.delete(rec);
			count ++ ;
		}
		return count;
	}
	
	/**
	 * 更新针次
	 * @author fuxin
	 * @date 2017年9月30日 上午4:09:58
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@Transactional(readOnly=false)
	public int updatePin(List<ChildVaccinaterecord> recs){
		int count = 0;
		recs.sort(new RcvComparator());
		
		//更新剂次
		String option = "first";
		String optype = "first";
		int idx = 1;
		for(ChildVaccinaterecord rec : recs){
			if(ChildVaccinaterecord.STATUS_DEL.equals(rec.getStatus())){
				continue;
			}
			if(option.equals("first")){
				option = rec.getNidPre();
				optype = rec.getVacctype();
			}
			if(!option.equals(rec.getNidPre()) ||!optype.equals(rec.getVacctype()) ){
				option = rec.getNidPre();
				optype = rec.getVacctype();
				idx = 1;
			}
			if(rec.getDosageInt() != idx){
				rec.setDosage(idx + "");
				rec.setNid(rec.getNidPre()  + idx);
				dao.update(rec);
				count ++;
			}
			idx ++;
		}
		return count;
	}
	
	/**
	 * 更新替代记录
	 * @author fuxin
	 * @date 2017年9月30日 上午4:09:58
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@Transactional(readOnly=false)
	public int updateBind(List<ChildVaccinaterecord> recs, ChildVaccinaterecord rec){
		int count = 0;
		if(rec == null){
			return count;
		}
		dao.update(rec);
		
		//未完成则不更新替代记录
		
		//更新替代信息
		for(ChildVaccinaterecord bin : recs){
			if(rec.getId().equals(bin.getInocUnionRecord())){
				bin.setStatus(rec.getStatus());
				bin.setVaccinatedate(rec.getVaccinatedate());
				updateBind(recs,bin);
			}
		}
		return count;
	}
	
	
	/**
	 * 重置替代记录
	 * @author fuxin
	 * @date 2017年10月4日 下午6:15:51
	 * @description 
	 *		TODO
	 * @param childcode
	 *
	 */
	@Transactional(readOnly=false)
	public void clearRecord(String childcode, String localcode){
		if(StringUtils.isBlank(childcode) || StringUtils.isBlank(localcode)){
			return ;
		}
		logger.info("重置替代记录开始：" + childcode + "  local:" + localcode);
		//1.查询儿童信息
		ChildBaseinfo baseinfo = childBaseinfoService.getByNo(childcode, localcode);
		logger.info("查询儿童信息：" + ((baseinfo != null)?"OK":"error"));
		//2.清除拆解记录
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("childid", baseinfo.getId());
		param.put("source", ChildVaccinaterecord.SOURCE_CJ);
		param.put("localCode", localcode);
		int delInt = deleteWhere(param);
		logger.info("清除旧数据：" + delInt);
		//3.根据重新生成替代记录
		ChildVaccinaterecord tempRcv = new ChildVaccinaterecord();
		tempRcv.setChildid(baseinfo.getId());
		tempRcv.setStatus(ChildVaccinaterecord.STATUS_YET);
		tempRcv.setOrderBy(" a.vaccinatedate desc");
		tempRcv.setLocalCode(localcode);
		List<ChildVaccinaterecord> rcv = findList(tempRcv);
		genBindedRecored(rcv, localcode);
//		childBaseinfoService.save(baseinfo);
		logger.info("重置替代记录完成" + childcode);
	}
	
	public void clearRecord(String childcode){
		clearRecord(childcode, OfficeService.getFirstOfficeCode());
	}

	@Transactional(readOnly=false)
	private int deleteWhere(Map<String, Object> param) {
		if(param.size() < 2 || null == param.get("childid") || "".equals(param.get("childid"))){
			return 0;
		}
//		if(!param.containsKey("localCode")){
//			param.put("localCode", OfficeService.getFirstOfficeCode());
//		}
		return dao.deleteWhere(param);
	}

	/**
	 * 根据接种大类计算下一针次
	 * @author fuxin
	 * @date 2017年12月8日 下午4:02:26
	 * @description 
	 *		TODO
	 * @param group
	 * @param childid
	 * @param vacctype
	 * @param localcode
	 * @return
	 *
	 */
	public int findLastPinByGroup(String group, String childid, String vacctype, String localcode) {
		ChildVaccinaterecord tempRec = new ChildVaccinaterecord();
		tempRec.setNid(group);
		tempRec.setChildid(childid);
		tempRec.setVacctype(vacctype);
		tempRec.setLocalCode(localcode);
		tempRec.setOrderBy("a.dosage desc");
		List<ChildVaccinaterecord> recs = findList(tempRec);
		if(recs.size() == 0){
			return 1;
		}else{
			return recs.get(0).getDosageInt() + 1;
		}
	}

	/**
	 * 根据接种大类计算下一针次
	 * @author liyuan
	 * @date 2018年2月6日 下午10:28:16
	 * @description 
	 *		TODO
	 * @param group
	 * @param childid
	 * @param vacctype
	 * @return
	 *
	 */
	public int findLastPinByGroup(String group, String childid, String vacctype) {
		return findLastPinByGroup(group, childid, vacctype, OfficeService.getFirstOfficeCode());
	}
	/**
	 * 造签字假数据接口（一条一插）
	 * @author zhouqj
	 * @date 2017年12月22日 上午9:04:58
	 * @description 
	 *		TODO
	 * @param cccc
	 *
	 */
	public void updateSignTest(ChildVaccinaterecord cccc) {
		 dao.updateSignTest(cccc);
	}

	/**
	 * 记录退款
	 * @author zhouqj
	 * @date 2018年1月16日 下午10:42:22
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 *
	 */
	@Transactional(readOnly = false)
	public void refundById(ChildVaccinaterecord childVaccinaterecord) {
		dao.refundById(childVaccinaterecord);
	}

	/**
	 * 保存调价信息
	 * @author zhouqj
	 * @date 2018年1月25日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param childVaccinaterecord
	 *
	 */
	@Transactional(readOnly = false)
	public void saveAdjustment(ChildVaccinaterecord childVaccinaterecord) {
		dao.saveAdjustment(childVaccinaterecord);
	}
	
	/**
	 * 查询所有符合筛选条件的应种儿童个案详细记录总条数
	 * @author Jack
	 * @date 2018年2月7日 下午7:09:01
	 * @description 
	 * @param startTime
	 * @param endTime
	 * @param residesStr
	 * @param situationStr
	 * @param areaArr
	 * @param vaccNumArr
	 * @return
	 *
	 */
	public Integer getYQWZChildBaseInfoCount(Map<String, Object> searchMap){
		return dao.getYQWZChildBaseInfoCount(searchMap);
	}
	/**
	 * 查询所有符合筛选条件的应种儿童个案详细记录
	 * @author Jack
	 * @date 2018年2月7日 下午7:12:31
	 * @description 
	 * @param startTime
	 * @param endTime
	 * @param residesStr
	 * @param situationStr
	 * @param areaArr
	 * @param vaccNumArr
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> getYQWZChildBaseInfo(Map<String, Object> searchMap, Page<HashMap<String, Object>> page){
		return dao.getYQWZChildBaseInfo(searchMap, page);
	}
	
	/**
	 * 获取应种数据导出的Excel数据结果
	 * @author Jack
	 * @date 2018年2月27日 下午4:24:36
	 * @description 
	 * @param searchMap
	 * @param page
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> getYQWZChildBaseInfoExcel(Map<String, Object> searchMap){
		return dao.getYQWZChildBaseInfoExcel(searchMap);
	}
	
	/**
	 * 获取完成人数
	 * @author yangjian
	 * @date 2018年3月2日 下午4:25:40
	 * @description 
	 * @return
	 *
	 */
	public Integer getRecordCount(){
		return dao.getRecordCount();
	}

	
	/**
	 * 登记台删除接种记录,若有排号则取消排号
	 * @author fuxin
	 * @date 2018年3月7日 下午1:35:03
	 * @param childVaccinaterecord
	 * @return
	 *
	 */
	@Transactional(readOnly=false)
	public String deleteRecord(ChildVaccinaterecord childVaccinaterecord) {
		String status=childVaccinaterecord.getStatus();
		//由于不能互相注入，此处手动获取QueneService
		if(ChildVaccinaterecord.STATUS_NOT.equals(status)){
			QueneService queneService = SpringContextHolder.getBean(QueneService.class);
			//逻辑删除排号队列
			Quene q=new Quene();
			q.setChildid(childVaccinaterecord.getChildcode());
			q.setVaccineid(childVaccinaterecord.getNid());
			q.setStatus(null);
			q.setStatusIn("'0','3'");
			List<Quene> qlist	=queneService.findList(q);
			if(qlist.size()>0){
				q=qlist.get(0);
				queneService.doCancel(q);
				queneService.refresh("");
			}
			//添加退款记录（一体机的）
			if("2".equals(childVaccinaterecord.getSource())&&(childVaccinaterecord.getPrice()>0||"1".equals(childVaccinaterecord.getInsurance()))){
				String 	result=	sysRefundService.refund(childVaccinaterecord);
				logger.info("退款结果：" + result);
				//判断是否退款成功
			 	if(!"OK".equals(result)){
			 		return result;
			 	}
			}
		}
		//删除记录
		delFlag(childVaccinaterecord);
		if(childVaccinaterecord.getPrice()!=0&&"1".equals(childVaccinaterecord.getSource())&&"0".equals(status)){
//			return "取消排号成功，请告知家长去微信公众号中退款";
			return "取消排号成功";
		}else if(childVaccinaterecord.getPrice()>0&&"2".equals(childVaccinaterecord.getSource())&&"0".equals(status)){
//			return "退款成功，已取消排号";
			return "取消排号成功";
		}else if(childVaccinaterecord.getPrice()>0&&"0".equals(childVaccinaterecord.getSource())&&"0".equals(status)){
//			return "取消排号成功,请告知家长线下退款处";
			return "取消排号成功";
		}else if("0".equals(status)){
			return "取消排号成功";
		}else{
			return "删除成功";
		}
	}
	
}

class RcvComparator implements Comparator<ChildVaccinaterecord> {
	@Override
	public int compare(ChildVaccinaterecord o1, ChildVaccinaterecord o2) {
		if(o1.getNidPre().compareTo(o2.getNidPre()) != 0){
			return o1.getVaccineid().compareTo(o2.getVaccineid());
		}else if(o1.getVacctype().compareTo(o2.getVacctype()) != 0){
			return o1.getVacctype().compareTo(o2.getVacctype());
		}else{
			if(o1.getVaccinatedate() == null && o2.getVaccinatedate() == null){
				return 0;
			}
			if(o1.getVaccinatedate() == null){
				return 1;
			}
			if(o2.getVaccinatedate() == null){
				return -1;
			}
			return o1.getVaccinatedate().compareTo(o2.getVaccinatedate());
		}
	}
}

