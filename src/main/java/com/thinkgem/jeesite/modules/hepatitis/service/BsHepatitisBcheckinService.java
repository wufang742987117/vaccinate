/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitis.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hepatitis.dao.BsHepatitisBcheckinDao;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckin;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckinStock;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;

/**
 * 乙肝信息管理Service
 * 
 * @author xuejinshan
 * @version 2017-07-25
 */
@Service
@Transactional(readOnly = true)
public class BsHepatitisBcheckinService extends CrudService<BsHepatitisBcheckinDao, BsHepatitisBcheckin> {

	@Autowired
	private SysAreaService SysAreaService;

	/**
	 * 通过id获取对象
	 */
	public BsHepatitisBcheckin get(String id) {
		return super.get(id);
	}

	public List<BsHepatitisBcheckin> findList(BsHepatitisBcheckin bsHepatitisBcheckin) {
		return super.findList(bsHepatitisBcheckin);
	}

	public Page<BsHepatitisBcheckin> findPage(Page<BsHepatitisBcheckin> page, BsHepatitisBcheckin bsHepatitisBcheckin) {
		return super.findPage(page, bsHepatitisBcheckin);
	}

	@Transactional(readOnly = false)
	public void save(BsHepatitisBcheckin bsHepatitisBcheckin) {
		super.save(bsHepatitisBcheckin);
	}

	@Transactional(readOnly = false)
	public void delete(BsHepatitisBcheckin bsHepatitisBcheckin) {
		super.delete(bsHepatitisBcheckin);
	}
	
	/**
	 * 根据名字或者电话或者身份证号查询所有的个案
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:55:11
	 * @description TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckin> namelist(BsHepatitisBcheckin bsHepatitisBcheckin) {
		return dao.namelist(bsHepatitisBcheckin);
	}
	
	/**
	 * 转换当前档案的总针次
	 * @author zhouqj
	 * @date 2017年9月14日 下午6:31:40
	 * @description 
	 *		TODO
	 * @param namelist
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckin> updateNameList(List<BsHepatitisBcheckin> namelist) {
		int a = 0;
		// 统计已完成针次信息
		for (BsHepatitisBcheckin bsHepatitisBchekin : namelist) {
			//转换疫苗类型
			String vaccName = getQueryVaccName(bsHepatitisBchekin.getVaccType());
			bsHepatitisBchekin.setVaccType(vaccName);
			//转针次记录
			List<BsHepatitisBNum> bsHepatitisBNumList = finishTimes(bsHepatitisBchekin);
			bsHepatitisBchekin.setTotalTimes(bsHepatitisBNumList.size());
			if(bsHepatitisBNumList.size() == 0){
				bsHepatitisBchekin.setFinishTimes(a);
			}
			for (BsHepatitisBNum bsHepatitisBNum : bsHepatitisBNumList) {
				if (bsHepatitisBNum.getStatus().equals("1")) {
					a = a + 1;
				}
				bsHepatitisBchekin.setFinishTimes(a);
			}
			a = 0;
		}
		return namelist;
	}
	
	/**
	 * 根据疫苗type查询疫苗名称
	 * @author zhouqj
	 * @date 2017年10月11日 下午3:34:38
	 * @description 
	 *		TODO
	 * @param vaccType
	 * @return
	 *
	 */
	public String getQueryVaccName(String vaccType) {
		return dao.getQueryVaccName(vaccType, OfficeService.getFirstOfficeCode());
	}

	/**
	 * 查询所有针次信息
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 下午5:50:28
	 * @description TODO
	 * @param bsHepatitisBchekin2
	 * @return
	 *
	 */
	public List<BsHepatitisBNum> finishTimes(BsHepatitisBcheckin bsHepatitisBchekin2) {
		return dao.finishTimes(bsHepatitisBchekin2);
	}
	
	/**
	 * 转换地址信息
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:54:43
	 * @description TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public BsHepatitisBcheckin updateAddr(BsHepatitisBcheckin bsHepatitisBcheckin) {
		if (bsHepatitisBcheckin == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// 更新详细地址
		sb.append(updateAddr(bsHepatitisBcheckin.getProvince()) + " ");
		sb.append(updateAddr(bsHepatitisBcheckin.getCity()) + " ");
		if (StringUtils.isNoneBlank(bsHepatitisBcheckin.getCounty())) {
			sb.append(updateAddr(bsHepatitisBcheckin.getCounty()) + " ");
		}
		if (StringUtils.isNoneBlank(bsHepatitisBcheckin.getAddress())) {
			sb.append(bsHepatitisBcheckin.getAddress());
		}
		bsHepatitisBcheckin.setHomeAddress(sb.toString());
		return bsHepatitisBcheckin;
	}
	
	/**
	 * 转换地址信息
	 * 
	 * @author xuejinshan
	 * @date 2017年7月25日 下午4:39:51
	 * @description TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public static String updateAddr(String id) {
		SysAreaService ser = SpringContextHolder.getBean(SysAreaService.class);
		// 地址id转换为地址描述
		SysArea t = ser.get(id);
		if (t != null) {
			return t.getName();
		}
		return "";
	}
	
	/**
	 * 转换疫苗名称
	 * @author zhouqj
	 * @date 2017年9月14日 下午6:48:05
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public BsHepatitisBcheckin updatVaccName(BsHepatitisBcheckin bsHepatitisBcheckin) {
		String vaccName = bsHepatitisBcheckin.getVaccineName();
		if(StringUtils.isNotBlank(vaccName)){
			bsHepatitisBcheckin.setVaccineName(vaccineName(vaccName));
		}
		return bsHepatitisBcheckin;
	}
	
	/**
	 * 查询疫苗名称
	 * 
	 * @author xuejinshan
	 * @date 2017年7月27日 上午9:56:18
	 * @description TODO
	 * @param vname
	 * @return
	 *
	 */
	public String vaccineName(String vname) {
		return dao.vaccineName(vname, OfficeService.getFirstOfficeCode());
	}

	/**
	 * 部分实体参数赋初值
	 * @author zhouqj
	 * @date 2017年9月14日 下午7:09:18
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public BsHepatitisBcheckin updateBsHepaCheckin(BsHepatitisBcheckin bsHepatitisBcheckin) {
		if(bsHepatitisBcheckin.getNewdate() == null){
			bsHepatitisBcheckin.setNewdate(new Date());
		}
		if(StringUtils.isBlank(bsHepatitisBcheckin.getPayStatus())){
			bsHepatitisBcheckin.setPayStatus(BsHepatitisBcheckin.EBEFOR);
		}
		//默认为乙肝
		if(StringUtils.isBlank(bsHepatitisBcheckin.getVaccType())){
			bsHepatitisBcheckin.setVaccType("2");
		}
		//查询疫苗配置
		VaccInfo vaccInfo = queryVaccInterVal(bsHepatitisBcheckin.getVaccType(), null,VaccInfo.DEFAULTAEFOR);
		//判断默认展示配置
		if(StringUtils.isBlank(bsHepatitisBcheckin.getDosage())){
			// 添加时设定针次默认值
			bsHepatitisBcheckin.setDosage(vaccInfo.getDefaultNeedle());
		}
		//当疫苗类型为乙肝
		if(StringUtils.isBlank(bsHepatitisBcheckin.getAntibodies())){
			bsHepatitisBcheckin.setAntibodies(BsHepatitisBcheckin.ANTIBODIES_DEFAULT);
		}
		return bsHepatitisBcheckin;
	}
	
	/**
	 * 查询疫苗配置
	 * @author zhouqj
	 * @param vnum 
	 * @date 2017年12月26日 下午7:11:04
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public VaccInfo queryVaccInterVal(String vaccType , String count, String status) {
		return dao.queryVaccInterVal(vaccType, status, OfficeService.getFirstOfficeCode(), count);
	}
	
	/**
	 * 判断id或者地址是否存在
	 * @author zhouqj
	 * @date 2017年9月14日 下午7:16:02
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @param model
	 *
	 */
	public void updatePcc(BsHepatitisBcheckin bsHepatitisBcheckin, Model model) {
		model.addAttribute("sheng", JsonMapper.toJsonString(SysAreaService.getByPid(0)));
		if (StringUtils.isBlank(bsHepatitisBcheckin.getId()) && (StringUtils.isBlank(bsHepatitisBcheckin.getProvince()) || StringUtils.isBlank(bsHepatitisBcheckin.getCity()))) {
			model.addAttribute("shi", JsonMapper.toJsonString(SysAreaService.getByPid(340000)));
			model.addAttribute("qu", JsonMapper.toJsonString(SysAreaService.getByPid(340600)));
			model.addAttribute("defaultSheng", 340000);
			model.addAttribute("defaultShi", 340600);
			model.addAttribute("defaultQu", 340604);
		}else{
			model.addAttribute("shi", JsonMapper.toJsonString(SysAreaService.getByPid(Integer.valueOf(bsHepatitisBcheckin.getProvince()))));
			model.addAttribute("qu", JsonMapper.toJsonString(SysAreaService.getByPid(Integer.valueOf(bsHepatitisBcheckin.getCity()))));
			model.addAttribute("defaultSheng", bsHepatitisBcheckin.getProvince());
			model.addAttribute("defaultShi", bsHepatitisBcheckin.getCity());
			if (bsHepatitisBcheckin.getCounty() == null) {
				model.addAttribute("defaultQu", "-1");
			} else {
				model.addAttribute("defaultQu", bsHepatitisBcheckin.getCounty());
			}
		}
	}
	
	/**
	 * 查询疫苗类型配置(除犬伤)
	 * @author zhouqj
	 * @param vaccType 
	 * @date 2017年10月10日 下午1:46:42
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<VaccInfo> getQueryVacc(String vaccType) {
		return dao.getQueryVacc(vaccType, OfficeService.getFirstOfficeCode());
	}
	
	/**
	 * 查询除犬伤外其他配置疫苗
	 * @author zhouqj
	 * @param vaccType 
	 * @date 2017年10月10日 下午1:42:51
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public BsManageVaccine queryVacc(String vaccType) {
		BsManageVaccine bsManageVaccine = new BsManageVaccine();
		bsManageVaccine.setVacctype("2");
		//查询疫苗类型配置
		List<VaccInfo> list = getQueryVacc(vaccType);
		StringBuffer check = new StringBuffer();
		for(VaccInfo vi : list){
			check.append(vi.getVaccCode() + ",");
		}
		String vacc = check.substring(0,check.length()-1).toString();
		bsManageVaccine.setVaccSum(StringUtils.splitToList(vacc,","));
		return bsManageVaccine;
	}
	
	/**
	 * 
	 * @author xuejinshan
	 * @param bsManageVaccine 
	 * @date 2017年7月27日 下午3:01:00
	 * @description TODO 查询乙肝疫苗种类信息
	 * @return
	 *
	 */
	public List<BsManageProduct> vaccineTypeHepa(BsManageVaccine bsManageVaccine) {
		List<BsManageProduct> bplist = new ArrayList<BsManageProduct>();
		List<BsManageProduct> list = dao.vaccineTypeHepa(bsManageVaccine);
		for(BsManageProduct bp : list){
			for(String str : bsManageVaccine.getVaccSum()){
				if(bp.getVaccineid().substring(0, 2).equals(str)){
					bplist.add(bp);
				}
			}
		}
		return bplist;
	}
	
	/**
	 * 乙肝疫苗后四位生成规则
	 * @author fuxin
	 * @date 2017年11月26日 下午11:28:50
	 * @description 
	 *		TODO
	 * @param vaccType
	 * @return
	 * 
	 */
	public synchronized String codedog(String vaccType) {
		String year = DateUtils.getYear();
		// 生成编号
		String bianma = OfficeService.getFirstOfficeCode() + year + dao.bianmadog(year,vaccType, OfficeService.getFirstOfficeCode());
		return bianma;
	}
	
	/**
	 * 查询建档是否重复
	 * @author zhouqj
	 * @date 2017年9月9日 下午1:36:53
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public int countHepaBcode(BsHepatitisBcheckin bsHepatitisBcheckin) {
		return dao.countHepaBcode(bsHepatitisBcheckin);
	}

	
	/**
	 * 乙肝疫苗（非蛋白）
	 * @author zhouqj
	 * @date 2017年8月10日 上午9:21:54
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public List<BsHepatitisBNum> finishpinNum(BsHepatitisBcheckin bsHepatitisBcheckin) {
		List<BsHepatitisBNum> bsList =	dao.finishpinNum(bsHepatitisBcheckin);
		for(BsHepatitisBNum bs : bsList){
			if(bs.getSignature().equals("1") && StringUtils.isNotBlank(bs.getsId())){
				try {
					bs.setSignatureList(new String(bs.getSignatureData(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return bsList;
	}

	/**
	 * 当前时间
	 * 
	 * @author xuejinshan
	 * @date 2017年7月31日 下午4:11:45
	 * @description TODO
	 * @param arges
	 * @return
	 *
	 */
	public Date[] dateTime(Date... arges) {
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
		arges[1] = day.getTime();
		day.setTime(arges[0]);
		arges[0] = day.getTime();
		return arges;
	}
	
	/**
	 * 乙肝疫苗库存 明细统计
	 * @author xuejinshan
	 * @date 2017年8月11日 下午7:17:26
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckinStock
	 * @return
	 *
	 */
	public  List<List<BsHepatitisBcheckinStock>> vaccineHepaStock(BsHepatitisBcheckinStock bsHepatitisBcheckinStock) {
		//查询现有库存
		List<BsHepatitisBcheckinStock> bsListOn = dao.vaccineHepaStock(OfficeService.getFirstOfficeCode(),UserUtils.getUser().getOffice().getCode());
		List<BsHepatitisBcheckinStock> bsList = new ArrayList<BsHepatitisBcheckinStock>();
		//获取疫苗类型
		String vaccType = bsHepatitisBcheckinStock.getVaccType();
		List<VaccInfo> list = getQueryVacc(vaccType);
		//转换疫苗大类code
		StringBuffer check = new StringBuffer();
		for(VaccInfo vi : list){
			check.append(vi.getVaccCode() + ",");
		}
		String vacc = check.substring(0,check.length()-1).toString();
		List<String> slist = StringUtils.splitToList(vacc,",");
		//排除疫苗
		for(BsHepatitisBcheckinStock bp : bsListOn){
			for(String str : slist){
				if(bp.getVaccineId().substring(0, 2).equals(str)){
					bsList.add(bp);
				}
			}
		}
		//查询针次表中未付款总量
		List<BsHepatitisBcheckinStock> bsList1 = vaccineHepaStock1(bsHepatitisBcheckinStock);
		//查询针次表中已付款总量
		List<BsHepatitisBcheckinStock> bsList2 = vaccineHepaStock2(bsHepatitisBcheckinStock);
		//查询针次表中已完成总量
		List<BsHepatitisBcheckinStock> bsList3 = vaccineHepaStock3(bsHepatitisBcheckinStock);
		//转化数据
		for(BsHepatitisBcheckinStock p : bsList){
			if(bsList1.size() == 0){
				p.setStoreNum1("0");
			}
			if(bsList2.size() == 0){
				p.setStoreNum2("0");
			}
			if(bsList3.size() == 0){
				p.setStoreNum3("0");
			}
			for(BsHepatitisBcheckinStock b : bsList1){
				if(p.getVaccineId().equals(b.getVaccineId()) && p.getBatch().equals(b.getBatch()) && p.getStandard().equals(b.getStandard())){
					p.setStoreNum1(b.getStoreNum1());
				}
			}
			for(BsHepatitisBcheckinStock c : bsList2){
				if(p.getVaccineId().equals(c.getVaccineId()) && p.getBatch().equals(c.getBatch()) && p.getStandard().equals(c.getStandard())){
					p.setStoreNum2(c.getStoreNum2());
				}
			}
			for(BsHepatitisBcheckinStock d : bsList3){
				if(p.getVaccineId().equals(d.getVaccineId()) && p.getBatch().equals(d.getBatch()) && p.getStandard().equals(d.getStandard())){
					p.setStoreNum3(d.getStoreNum3());
				}
			}
			if(p.getStoreNum1() == null){
				p.setStoreNum1("0");
			}
			if(p.getStoreNum2() == null){
				p.setStoreNum2("0");
			}
			if(p.getStoreNum3() == null){
				p.setStoreNum3("0");
			}
		}
		// 数组转树形   以疫苗名称进行
		String opo = "first";
		List<BsHepatitisBcheckinStock> templistOne = new ArrayList<>();
		List<List<BsHepatitisBcheckinStock>> returnlistOne = new ArrayList<>();
		for (BsHepatitisBcheckinStock s : bsList) {
			if (opo.equals("first")) {
				opo = s.getVaccineName();
			}
			//疫苗名称为多个的时候执行
			if (!opo.equals(s.getVaccineName())) {
				templistOne.get(0).setLeng(templistOne.size());
				returnlistOne.add(templistOne);
				templistOne = new ArrayList<>();
				opo = s.getVaccineName();
			}
			templistOne.add(s);
		}
		//疫苗名称为一个时执行
		if (templistOne.size() > 0) {
			templistOne.get(0).setLeng(templistOne.size());
			returnlistOne.add(templistOne);
		}
		return returnlistOne;
	}
	
	/**
	 * 查询乙肝疫苗库存
	 * @author xuejinshan
	 * @date 2017年7月31日 下午7:23:28
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckinStock> vaccineHepaStock() {
		return dao.vaccineHepaStock(OfficeService.getFirstOfficeCode(),UserUtils.getUser().getOffice().getCode());
	}
	
	/**
	 * 乙肝疫苗    查询未付款总量
	 * @author xuejinshan
	 * @param bsHepatitisBcheckinStock 
	 * @date 2017年7月31日 下午7:23:28
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckinStock> vaccineHepaStock1(BsHepatitisBcheckinStock bsHepatitisBcheckinStock) {
		return dao.vaccineHepaStock1(bsHepatitisBcheckinStock);
	}
	
	/**
	 * 乙肝疫苗    查询已付款总量
	 * @author xuejinshan
	 * @param bsHepatitisBcheckinStock 
	 * @date 2017年7月31日 下午7:23:28
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckinStock> vaccineHepaStock2(BsHepatitisBcheckinStock bsHepatitisBcheckinStock) {
		return dao.vaccineHepaStock2(bsHepatitisBcheckinStock);
	}
	
	/**
	 * 乙肝疫苗    查询已完成总量
	 * @author xuejinshan
	 * @param bsHepatitisBcheckinStock 
	 * @date 2017年7月31日 下午7:23:28
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsHepatitisBcheckinStock> vaccineHepaStock3(BsHepatitisBcheckinStock bsHepatitisBcheckinStock) {
		return dao.vaccineHepaStock3(bsHepatitisBcheckinStock);
	}
	
	/**
	 * 根据年龄查询规格和厂家信息
	 * @author xuejinshan
	 * @date 2017年8月15日 下午4:48:04
	 * @description 
	 *		TODO
	 * @param specification
	 * @param attr 
	 * @return
	 *
	 */
	public BsHepatitisBcheckin findData(String attr, String vaccType) {
		return dao.findData(attr, OfficeService.getFirstOfficeCode(),UserUtils.getUser().getOffice().getCode(), vaccType);
	}

	/**
	 * 新建卡数
	 * @author xuejinshan
	 * @date 2017年8月2日 下午4:09:48
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public int countNumOne(BsHepatitisBNum bsHepatitisBNum) {
		return dao.countNumOne(bsHepatitisBNum);
	}

	/**
	 * 接种数
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 下午4:09:57
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public int countNumTwo(BsHepatitisBNum bsHepatitisBNum) {
		return dao.countNumTwo(bsHepatitisBNum);
	}

	/**
	 * 修改付款状态为1
	 * @author zhouqj
	 * @date 2017年8月10日 下午6:50:50
	 * @description 
	 *		TODO
	 * @param id
	 *
	 */
	public void updateByPayStatus(String id) {
		dao.updateByPayStatus(id);
	}

}