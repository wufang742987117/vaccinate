/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.child_baseinfo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.child_baseinfo.dao.ChildBaseinfoDao;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.BaseInfoRoot;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.ChildBaseinfo;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.Nursery;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.aefi;
import com.thinkgem.jeesite.modules.child_baseinfo.entity.chhe;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;
import com.thinkgem.jeesite.modules.department.service.SysVaccDepartmentService;
import com.thinkgem.jeesite.modules.nation.entity.BsNation;
import com.thinkgem.jeesite.modules.nation.service.BsNationService;
import com.thinkgem.jeesite.modules.shequ.entity.SysCommunity;
import com.thinkgem.jeesite.modules.shequ.service.SysCommunityService;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.yiyuan.entity.SysBirthHospital;
import com.thinkgem.jeesite.modules.yiyuan.service.SysBirthHospitalService;

/**
 * 档案管理Service
 * 
 * @author 王德地
 * @version 2017-02-06
 */
@Service
@Transactional(readOnly = true)
public class ChildBaseinfoService extends
		CrudService<ChildBaseinfoDao, ChildBaseinfo> {

	@Autowired
	SysAreaService sysAreaService;
	@Autowired
	private SysCommunityService sysCommunityService;
	@Autowired
	private SysBirthHospitalService sysBirthHospitalService;
	@Autowired
	private BsNationService bsNationService;
	@Autowired
	private SysVaccDepartmentService sysVaccDepartmentService;
	
	public ChildBaseinfo get(String id) {
		return updateAddr(super.get(id));
	}

	public List<ChildBaseinfo> findList(ChildBaseinfo childBaseinfo) {
		return super.findList(childBaseinfo);
	}

	public Page<ChildBaseinfo> findPage(Page<ChildBaseinfo> page,
			ChildBaseinfo childBaseinfo) {
		return super.findPage(page, childBaseinfo);
	}

	@Transactional(readOnly = false)
	public void save(ChildBaseinfo childBaseinfo) {
		super.save(childBaseinfo);
	}

	@Transactional(readOnly = false)
	public void delete(ChildBaseinfo childBaseinfo) {
		super.delete(childBaseinfo);
	}

	public ChildBaseinfo getByNo(String no) {
		return getByNo(no, OfficeService.getFirstOfficeCode());
	}
	
	public ChildBaseinfo getByNo(String no,String localcode) {
		ChildBaseinfo info = new ChildBaseinfo();
		info.setChildcode(no);
		info.setLocalCode(localcode);
		return updateAddr(dao.getByNo(info));
	}

	/**
	 * 转换地址信息
	 * 
	 * @author fuxin
	 * @param childBaseinfo
	 */
	public ChildBaseinfo updateAddr(ChildBaseinfo childBaseinfo) {
		if (childBaseinfo == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// 更新详细地址
		if(StringUtils.isNotBlank(childBaseinfo.getProvince())){
			sb.append(updateAddr(childBaseinfo.getProvince()) + " ");
		}
		if(StringUtils.isNotBlank(childBaseinfo.getCity())){
			sb.append(updateAddr(childBaseinfo.getCity()) + " ");
		}
		if(StringUtils.isNotBlank(childBaseinfo.getCounty())){
			sb.append(updateAddr(childBaseinfo.getCounty()) + " ");
		}
		if(StringUtils.isNotBlank(childBaseinfo.getAddress())){
			sb.append(childBaseinfo.getAddress());
		}
		
		childBaseinfo.setHomeaddress(sb.toString());
		// 更新户籍地址
		sb = new StringBuilder();
		if(StringUtils.isNotBlank(childBaseinfo.getPr())){
			sb.append(updateAddr(childBaseinfo.getPr()) + " ");
		}
		if(StringUtils.isNotBlank(childBaseinfo.getCi())){
			sb.append(updateAddr(childBaseinfo.getCi()) + " ");
		}
		if(StringUtils.isNotBlank(childBaseinfo.getCo())){
			sb.append(updateAddr(childBaseinfo.getCo()) + " ");
		}
		if(StringUtils.isNotBlank(childBaseinfo.getAdd())){
			sb.append(childBaseinfo.getAdd() + " ");
		}
		childBaseinfo.setRegistryaddress(sb.toString());
		return childBaseinfo;
	}

	/**
	 * 转换地址信息
	 * 
	 * @author fuxin
	 * 
	 * @return
	 */
	public static String updateAddr(String id) {
		
		if(StringUtils.isBlank(id)){
			return "";
		}
		SysAreaService ser = SpringContextHolder.getBean(SysAreaService.class);
		// 地址id转换为地址描述
		SysArea aa = ser.get(id);
		if(aa != null){
			return aa.getName();
		}
		return "";
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月3日 下午3:53:49
	 * @description 根据时间和间隔天数以及疫苗名称查询满足添加的儿童信息
	 * @param begin
	 * @param end
	 * @param days
	 * @return
	 * 
	 */
	public Page<ChildBaseinfo> sel(Page<ChildBaseinfo> p, com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.sel sel) {
		sel.setPage(p);
		p.setList(dao.sel1(sel));
		return p;
	}
	public List<ChildBaseinfo> sel1( com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.sel sel) {
		return dao.sel1(sel);
	}
	
	/**
	 * 根据儿童编号计算儿童月龄
	 * 
	 * @author fuxin
	 * @date 2017年3月4日 下午4:26:52
	 * @description TODO
	 * @param code
	 * @return -1:该code没有对应儿童信息
	 * 
	 */
	public int getMouAge(String code) {
		return getMouAge(code, null);
	}
	
	/**
	 * 根据儿童编号计算儿童月龄
	 * 
	 * @author fuxin
	 * @date 2017年3月4日 下午4:26:52
	 * @description TODO
	 * @param code
	 * @return -1:该code没有对应儿童信息
	 * 
	 */
	public int getMouAge(String code, Date targetDate) {
		return getMouAge(code,targetDate,OfficeService.getFirstOfficeCode());
	}
	
	/**
	 * 根据儿童编号计算儿童月龄
	 * @author liyuan
	 * @date 2018年2月26日 上午9:28:24
	 * @description 
	 *		TODO
	 * @param code
	 * @param targetDate
	 * @param localcode
	 * @return
	 *
	 */
	public int getMouAge(String code, Date targetDate,String localcode) {
		if(null == targetDate){
			targetDate = new Date();
		}
		ChildBaseinfo temp = new ChildBaseinfo();
		temp.setChildcode(code);
		temp.setLocalCode(localcode);
		ChildBaseinfo info = dao.getByNo(temp);
		if (info == null) {
			return -1;
		}
		
		// 获取儿童生日时期
		Date birth = info.getBirthday();
		Calendar calInfo = GregorianCalendar.getInstance();
		calInfo.setTime(birth);
		int y1 = calInfo.get(Calendar.YEAR);
		int m1 = calInfo.get(Calendar.MONTH) + 1;
		int d1 = calInfo.get(Calendar.DATE);
		
		// 获取系统时间
		calInfo.setTime(targetDate);
		int y2 = calInfo.get(Calendar.YEAR);
		int m2 = calInfo.get(Calendar.MONTH) + 1;
		int d2 = calInfo.get(Calendar.DATE);
		
		int age = (y2 - y1) * 12 + m2 - m1;
		if (d2 < d1) {
			age--;
		}
		return age;
	}
	
	/**
	 * 根据儿童编号计算儿童月龄
	 * 
	 * @author fuxin
	 * @date 2017年3月4日 下午4:26:52
	 * @description TODO
	 * @param code
	 * @return -1:该code没有对应儿童信息
	 * 
	 */
	public int getMouAge(Date birth) {
		if (birth == null) {
			return -1;
		}
		
		// 获取儿童生日时期
		Calendar calInfo = GregorianCalendar.getInstance();
		calInfo.setTime(birth);
		int y1 = calInfo.get(Calendar.YEAR);
		int m1 = calInfo.get(Calendar.MONTH) + 1;
		int d1 = calInfo.get(Calendar.DATE);
		
		// 获取系统时间
		calInfo.setTime(new Date());
		int y2 = calInfo.get(Calendar.YEAR);
		int m2 = calInfo.get(Calendar.MONTH) + 1;
		int d2 = calInfo.get(Calendar.DATE);
		
		int age = (y2 - y1) * 12 + m2 - m1;
		if (d2 < d1) {
			age--;
		}
		return age;
	}

	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月9日 下午6:15:19
	 * @description 根据儿童id查询儿童接种记录并根据疫苗名称和针次排序 (一类疫苗)
	 * @param childBaseinfo
	 * @return
	 * 
	 */
	public List<Nursery> one(ChildBaseinfo childBaseinfo) {

		return dao.one(childBaseinfo);
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月9日 下午6:15:03
	 * @description 根据儿童id查询儿童接种记录并根据疫苗名称和针次排序 (二类疫苗)
	 * @param childBaseinfo
	 * @return
	 * 
	 */
	public List<Nursery> two(ChildBaseinfo childBaseinfo) {

		return dao.two(childBaseinfo);
	}

	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月9日 下午6:13:59
	 * @description 生成编码
	 * @param code1
	 * @param code
	 * @return
	 * 
	 */
	public synchronized String code() {
		String year=DateUtils.getYear();
		// 生成编号
		String	bianma = OfficeService.getFirstOfficeCode()+year+dao.bianma(year,OfficeService.getFirstOfficeCode());
		return  bianma;
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年3月9日 下午6:13:59
	 * @description 生成编码
	 * @param code1
	 * @param code
	 * @return
	 * 
	 */
	public synchronized String codedog() {
		String year=DateUtils.getYear();	
		// 生成编号
		String	bianma = OfficeService.getFirstOfficeCode()+year+dao.bianmadog(year,OfficeService.getFirstOfficeCode());
		return  bianma;
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年3月7日 下午5:12:12
	 * @description 字符串日期转换成时间
	 * @param date
	 * @throws ParseException
	 * 
	 */
	public Date convert(String date) throws ParseException {
		if (date.length() < 6 || date.length() > 8 || date.length() == 7) {
			return null;
		}
		if (date.length() == 6) {
			date = "20" + date;
		}
		String a = date.substring(0, 4);
		String b = date.substring(4, 6);
		String c = date.substring(6, 8);
		date = a + "-" + b + "-" + c;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(date);
		return d;
	}
	/**
	 * <li>出生医院code和name 的转换</li> <li>区域name和code的转换</li><li>民族code和name的转换</li><li>接种单位code和name的转换</li>
	 * @author wangdedi
	 * @date 2017年3月22日 下午12:00:07
	 * @description 
	 *		code和name的转换
	 * @param childBaseinfo
	 * @return
	 *
	 */
	public ChildBaseinfo convert(ChildBaseinfo childBaseinfo) {
		if(childBaseinfo==null){
			return childBaseinfo;
		}
		//出生医院code和name 的转换
		if (StringUtils.isNoneBlank(childBaseinfo.getBirthhostipal())) {
			SysBirthHospital sysBirthHospital = new SysBirthHospital();
			sysBirthHospital.setCode(childBaseinfo.getBirthhostipal());
			List<SysBirthHospital> BirthHospitallist = sysBirthHospitalService
					.findList(sysBirthHospital);
			if (BirthHospitallist.size() > 0) {
				childBaseinfo.setBirthhostipal(BirthHospitallist.get(0).getName());
			}
		}
		//区域name和code的转换
		if (StringUtils.isNoneBlank(childBaseinfo.getArea())) {
			SysCommunity sysCommunity = new SysCommunity();
			sysCommunity.setCode(childBaseinfo.getArea());
			List<SysCommunity> Communitylist = sysCommunityService
					.findList(sysCommunity);
			if (Communitylist.size() > 0) {
				childBaseinfo.setArea(Communitylist.get(0).getName());
			}
		}
		//民族code和name的转换
		if (StringUtils.isNoneBlank(childBaseinfo.getNation())) {
			BsNation bsNation = new BsNation();
			bsNation.setCode(childBaseinfo.getNation());
			List<BsNation> Nationlist = bsNationService.findList(bsNation);
			if (Nationlist.size() > 0) {
				childBaseinfo.setNation(Nationlist.get(0).getName());
			}
		}
		//接种门诊code和name的转换
	if (StringUtils.isNoneBlank(childBaseinfo.getOfficeinfo())) {
			SysVaccDepartment department = new SysVaccDepartment();
			department.setCode(childBaseinfo.getOfficeinfo());
			List<SysVaccDepartment> departmentlist =sysVaccDepartmentService.findList(department);
			if (departmentlist.size() > 0) {
				childBaseinfo.setOfficeinfo(departmentlist.get(0).getName());
			}
		}
	
		return childBaseinfo;
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月11日 下午2:51:34
	 * @description 
	 *		TODO 根据儿童编码或者身份证号码查询儿童信息
	 * @param childBaseinfo
	 * @return
	 *
	 */
	public List<ChildBaseinfo> findList1(ChildBaseinfo childBaseinfo) {
		return dao.findListByCode(childBaseinfo);
	}


	public void insertRoot(BaseInfoRoot root) {
		dao.insertRoot(root);
	}
	
	/**
	 * 
	 * <strong> 根据儿童编号获取儿童id</strong>
	 * @author fuxin
	 * @date 2017年7月17日 下午5:55:10
	 * @description 
	 *		TODO
	 * @param code
	 * @return
	 *
	 */
	public String getIdByCode(String code){
		return dao.getIdByCode(code,OfficeService.getFirstOfficeCode());
	}

	
	public void insertAefi(aefi f) {
		dao.insertAefi(f);
		
	}

	public void insertChhe(chhe c) {
		dao.insertChhe(c);
		
	}

	public void clearBaseInfo(ChildBaseinfo childBaseinfo) {
		logger.info("清除儿童信息:" + JsonMapper.toJsonString(childBaseinfo));
		String localCode = OfficeService.getFirstOfficeCode();
		dao.clearAefi(childBaseinfo.getChildcode(),localCode);
		dao.clearHere(childBaseinfo.getChildcode(),localCode);
		dao.clearRecord(childBaseinfo.getId(),localCode);
		dao.clearBaseInfo(childBaseinfo.getId(),localCode);
	}
	
	public Page<ChildBaseinfo> findOD(Page<ChildBaseinfo> p,ChildBaseinfo childBaseinfo) {
		childBaseinfo.setPage(p);
		p.setList(dao.findOD(childBaseinfo));
		return p;
	}
	
	public List<ChildBaseinfo> findOD(ChildBaseinfo childBaseinfo) {
		return dao.findOD(childBaseinfo);
	}

	/**
	 * 查询当天完成接种的儿童
	 * @author fuxin
	 * @date 2017年12月10日 下午7:57:08
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
//	public List<ChildBaseinfo> findFinishInDay() {
//		return findFinishInDay(new Date());
//	}
	
	/**
	 * 查询指定日期完成接种的儿童
	 * @author fuxin
	 * @date 2017年12月10日 下午7:53:27
	 * @description 
	 *		TODO
	 * @param day
	 * @return
	 *
	 */
	public List<ChildBaseinfo> findFinishInDay(ChildBaseinfo childBaseinfo) {
		if(childBaseinfo == null || childBaseinfo.getBeginTime() == null || childBaseinfo.getEndTime() == null){
			return new ArrayList<ChildBaseinfo>();
		}
		List<ChildBaseinfo> list = dao.findFinishInDay(childBaseinfo);
		for(ChildBaseinfo info : list){
			info.setMouage(DateUtils.getMouthAge(info.getBirthday()));
		}
		return list;
	}
	
	

}