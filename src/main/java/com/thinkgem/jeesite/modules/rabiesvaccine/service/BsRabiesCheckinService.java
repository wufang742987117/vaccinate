/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rabiesvaccine.service;

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
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.rabiesvaccine.dao.BsRabiesCheckinDao;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesBubble;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckin;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinStock;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 狂犬疫苗管理Service
 * @author wangdedi
 * @version 2017-02-24
 */
@Service
@Transactional(readOnly = true)
public class BsRabiesCheckinService extends CrudService<BsRabiesCheckinDao, BsRabiesCheckin> {
	
	@Autowired
	private SysAreaService sysAreaService;
	
	public BsRabiesCheckin get(String id) {
		return super.get(id);
	}
	
	public List<BsRabiesCheckin> findList(BsRabiesCheckin bsRabiesCheckin) {
		return super.findList(bsRabiesCheckin);
	}
	
	public Page<BsRabiesCheckin> findPage(Page<BsRabiesCheckin> page, BsRabiesCheckin bsRabiesCheckin) {
		return super.findPage(page, bsRabiesCheckin);
	}
	
	@Transactional(readOnly = false)
	public void save(BsRabiesCheckin bsRabiesCheckin) {
		super.save(bsRabiesCheckin);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsRabiesCheckin bsRabiesCheckin) {
		super.delete(bsRabiesCheckin);
	}
	
	/**
	 * 根据名字或者电话或者身份证号查询所有的个案
	 * @author wangdedi
	 * 
	 * @return 
	 */
	public List<BsRabiesCheckin> namelist(BsRabiesCheckin bsRabiesCheckin){
		return dao.namelist(bsRabiesCheckin);
	}
	
	/**
	 * 转换当前档案的总针次
	 * @author zhouqj
	 * @date 2017年9月13日 下午8:19:36
	 * @description 
	 *		TODO
	 * @param namelist
	 * @return
	 *
	 */
	public List<BsRabiesCheckin> updateNameList(List<BsRabiesCheckin> namelist) {
		int a = 0;
		//多条显示转换
		for (BsRabiesCheckin bsRabiesCheckin : namelist) {
			//查询所有针次数据
			List<BsRabiesNum> bList = finishpin(bsRabiesCheckin);
			bsRabiesCheckin.setUndonepin(bList.size());
			for (BsRabiesNum bsRabiesNum : bList) {
				if("1".equals(bsRabiesNum.getStatus())){
					a = a + 1;
				}
				bsRabiesCheckin.setFinishpin(a);
			}
			a = 0;
		}
		return namelist;
	}
	
	/**
	 * 根据id查询所有的针次
	 * @author wangdedi
	 * @date 2017年4月24日 下午7:37:05
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public List<BsRabiesNum> finishpin(BsRabiesCheckin bsRabiesCheckin){
		return dao.finishpin(bsRabiesCheckin);
	}
	
	/**
	 * 转换地址信息
	 * @author fuxin
	 * @param childBaseinfo
	 */
	public BsRabiesCheckin updateAddr(BsRabiesCheckin bsRabiesCheckin){
		if(bsRabiesCheckin == null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		//更新详细地址
		sb.append(updateAddr(bsRabiesCheckin.getProvince()) + " ");
		sb.append(updateAddr(bsRabiesCheckin.getCity()) + " ");
		if(StringUtils.isNoneBlank(bsRabiesCheckin.getCounty())){
			sb.append(updateAddr(bsRabiesCheckin.getCounty()) + " ");
		}
		if(StringUtils.isNoneBlank(bsRabiesCheckin.getAddress())){
			sb.append(bsRabiesCheckin.getAddress());
		}
		bsRabiesCheckin.setHomeaddress(sb.toString());
		return bsRabiesCheckin;
	}
	
	/**
	 * 转换地址信息
	 * @author fuxin
	 * 
	 * @return 
	 */
	public static String updateAddr(String id){
		SysAreaService ser = SpringContextHolder.getBean(SysAreaService.class);
		//地址id转换为地址描述
		SysArea t = ser.get(id);
		if(t != null){
			return t.getName();
		}
		return "";
	}
	
	/**
	 * 转换咬伤部位
	 * @author zhouqj
	 * @date 2017年8月7日 下午6:24:00
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public BsRabiesCheckin updateBitepart(BsRabiesCheckin bsRabiesCheckin){
		if(bsRabiesCheckin == null){
			return null;
		}
		//转换咬伤部位显示
		String bitepart = bsRabiesCheckin.getBitepart();
		if(StringUtils.isNotBlank(bitepart)){
			StringBuilder ss = new StringBuilder();
			if(StringUtils.isNotBlank(bitepart)){
				String[] arr = bitepart.split(",");
				for(String s : arr){
					ss.append(DictUtils.getDictLabel(s, "bite", "") + ",");
				}
			}
			bsRabiesCheckin.setBitepart(ss.toString().substring(0, ss.length()-1));
		}
		return bsRabiesCheckin;
	}
	
	/**
	 * 转换疫苗名称
	 * @author zhouqj
	 * @date 2017年9月13日 下午7:46:39
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public BsRabiesCheckin updatVaccName(BsRabiesCheckin bsRabiesCheckin) {
		String vaccName = bsRabiesCheckin.getVaccinatename();
		String vaccNameNo = bsRabiesCheckin.getVaccinatenameNo();
		if(StringUtils.isNotBlank(vaccName)){
			bsRabiesCheckin.setVaccinatename(vaccineName(vaccName));
		}
		if(StringUtils.isNotBlank(vaccNameNo)){
			bsRabiesCheckin.setVaccinatenameNo(vaccineName(vaccNameNo));
		}
		return bsRabiesCheckin;
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月6日 上午11:14:13
	 * @description 
	 *		查询疫苗名称
	 * @return
	 *
	 */
	public String vaccineName(String vname){
		return dao.vaccineName(vname, OfficeService.getFirstOfficeCode());
	};
	
	/**
	 * 部分实体参数赋初值
	 * @author zhouqj
	 * @date 2017年9月14日 上午10:37:59
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public BsRabiesCheckin updateBsRabiesCheckin(BsRabiesCheckin bsRabiesCheckin) {
		if(bsRabiesCheckin.getNewdate() == null){
			bsRabiesCheckin.setNewdate(new Date());
		}
		if(StringUtils.isBlank(bsRabiesCheckin.getExpose())){
			bsRabiesCheckin.setExpose(BsRabiesCheckin.EXPOSE_AFTER);
		}
		if(StringUtils.isBlank(bsRabiesCheckin.getBitepart())){
			bsRabiesCheckin.setBitepart(BsRabiesCheckin.BITEPART_AFTER);
		}
		if(StringUtils.isBlank(bsRabiesCheckin.getIsinoculate())){
			bsRabiesCheckin.setIsinoculate(BsRabiesCheckin.EBEFOR);
		}
		if(StringUtils.isBlank(bsRabiesCheckin.getJudgmentTimes())){
			bsRabiesCheckin.setJudgmentTimes(BsRabiesCheckin.EBEFOR);
		}
		if(StringUtils.isBlank(bsRabiesCheckin.getPayment())){
			bsRabiesCheckin.setPayment(BsRabiesCheckin.EBEFOR);
		}
		return bsRabiesCheckin;
	}
	
	/**
	 * 判断id或者地址是否存在
	 * @author zhouqj
	 * @date 2017年9月14日 下午12:26:41
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @param model
	 *
	 */
	public void updatePcc(BsRabiesCheckin bsRabiesCheckin, Model model) {
		model.addAttribute("sheng",JsonMapper.toJsonString(sysAreaService.getByPid(0)));
		if(StringUtils.isBlank(bsRabiesCheckin.getId()) && (StringUtils.isBlank(bsRabiesCheckin.getProvince()) || StringUtils.isBlank(bsRabiesCheckin.getCity()))){
			model.addAttribute("shi",JsonMapper.toJsonString(sysAreaService.getByPid(340000)));
			model.addAttribute("qu",JsonMapper.toJsonString(sysAreaService.getByPid(340600)));
			model.addAttribute("defaultShen",340000);
			model.addAttribute("defaultShi",340600);
			model.addAttribute("defaultQu",340603);
		}else{
			model.addAttribute("shi",JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(bsRabiesCheckin.getProvince()))));
			model.addAttribute("qu",JsonMapper.toJsonString(sysAreaService.getByPid(Integer.valueOf(bsRabiesCheckin.getCity()))));
			model.addAttribute("defaultShen",bsRabiesCheckin.getProvince());
			model.addAttribute("defaultShi",bsRabiesCheckin.getCity());
			if(bsRabiesCheckin.getCounty()==null){
				model.addAttribute("defaultQu","-1");
			}else{
				model.addAttribute("defaultQu",bsRabiesCheckin.getCounty());
			}
		}
	}
	
	/**
	 * 分类犬伤疫苗（蛋白与非蛋白）
	 * @author zhouqj
	 * @param model 
	 * @date 2017年9月13日 下午8:52:43
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public void updateBsProduct(Model model) {
		//查询所有狂犬疫苗下的小类信息
		List<BsManageProduct> productlist = vaccineType();
		//查询狂犬蛋白小类code
		List<String> list = vaccById();
		//查询显示小类列表数据（不含免疫蛋白）
		List<BsManageProduct> list1 = new ArrayList<>();
		//查询显示小类列表数据（只含免疫蛋白）
		List<BsManageProduct> list2 = new ArrayList<>();
		for(BsManageProduct p :  productlist){
			if(list.contains(p.getId())){
				list2.add(p);
			}else {
				list1.add(p);
			}
		}
		model.addAttribute("productlist", list1);
		model.addAttribute("productlist1", list2);
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @param vcc 
	 * @date 2017年4月18日 上午10:04:41
	 * @description 
	 *		查询数据库的狂犬疫苗小类种类
	 * @return
	 *
	 */
	public List<BsManageProduct> vaccineType(){
		return dao.vaccineType(OfficeService.getFirstOfficeCode(),UserUtils.getUser().getOffice().getCode());
	}
	
	/**
	 * 查询蛋白所有的种类
	 * @author zhouqj
	 * @date 2017年8月5日 下午4:12:12
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<String> vaccById() {
		return dao.vaccById(OfficeService.getFirstOfficeCode());
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
		String year = DateUtils.getYear();		
		// 生成编号
		String	bianma = OfficeService.getFirstOfficeCode() + year + dao.bianmadog(year,OfficeService.getFirstOfficeCode());
		return  bianma;
	}
	
	/**
	 * 查询建档是否重复
	 * @author zhouqj
	 * @date 2017年9月9日 下午1:10:35
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public int countRabiesCode(BsRabiesCheckin bsRabiesCheckin) {
		return dao.countRabiesCode(bsRabiesCheckin);
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 上午10:47:20
	 * @description 
	 *		TODO 根据id查询针次为0
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public List<BsRabiesNum> finishpinNot(BsRabiesCheckin bsRabiesCheckin) {
		List<BsRabiesNum> bsRabiesNumNotList =	dao.finishpinNot(bsRabiesCheckin);
		for(BsRabiesNum bs : bsRabiesNumNotList){
			if(bs.getSignature().equals("1") && StringUtils.isNotBlank(bs.getsId())){
				try {
					bs.setSignatureList(new String(bs.getSignatureData(),"UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bsRabiesNumNotList;
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 上午10:47:20
	 * @description 
	 *		TODO 根据id查询针次不为0
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public List<BsRabiesNum> finishpinNum(BsRabiesCheckin bsRabiesCheckin) {
		List<BsRabiesNum> bsRabiesNumList =	dao.finishpinNum(bsRabiesCheckin);
		for(BsRabiesNum bs : bsRabiesNumList){
			if(bs.getSignature().equals("1") && StringUtils.isNotBlank(bs.getsId())){
				try {
					bs.setSignatureList(new String(bs.getSignatureData(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return bsRabiesNumList;
	}
	
	/**
	 * 删除记录
	 * @author zhouqj
	 * @date 2017年8月15日 上午9:59:03
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	public void deleteCheckId(BsRabiesNum bsRabiesNum) {
		dao.deleteCheckId(bsRabiesNum);
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
		arges[1] = day.getTime();
		day.setTime(arges[0]);
		arges[0] = day.getTime();
		return arges;
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月19日 下午2:21:53
	 * @description 
	 *		TODO 查询犬伤疫苗库存统计
	 * @return
	 *
	 */
	public List<List<BsRabiesCheckinStock>> vaccineStock(BsRabiesCheckinStock bsRabiesCheckinStock) {
		//查询现有库存
		List<BsRabiesCheckinStock> bsList = dao.vaccineStock(OfficeService.getFirstOfficeCode(),UserUtils.getUser().getOffice().getCode());
		//查询未付款总量
		List<BsRabiesCheckinStock> bsList1 = vaccineStock1(bsRabiesCheckinStock);
		//查询已付款总量
		List<BsRabiesCheckinStock> bsList2 = vaccineStock2(bsRabiesCheckinStock);
		//查询已完成总量
		List<BsRabiesCheckinStock> bsList3 = vaccineStock3(bsRabiesCheckinStock);
		//转化数据
		for(BsRabiesCheckinStock p :  bsList){
			if(bsList1.size() == 0){
				p.setStorenum1("0");
			}
			if(bsList2.size() == 0){
				p.setStorenum2("0");
			}
			if(bsList3.size() == 0){
				p.setStorenum3("0");
			}
			for(BsRabiesCheckinStock b : bsList1){
				if(p.getVaccinateid().equals(b.getVaccinateid()) && p.getBatchnum().equals(b.getBatchnum()) && p.getStandard().equals(b.getStandard())){
					p.setStorenum1(b.getStorenum1());
				}
			}
			for(BsRabiesCheckinStock c : bsList2){
				if(p.getVaccinateid().equals(c.getVaccinateid()) && p.getBatchnum().equals(c.getBatchnum()) && p.getStandard().equals(c.getStandard())){
					p.setStorenum2(c.getStorenum2());
				}
			}
			for(BsRabiesCheckinStock d : bsList3){
				if(p.getVaccinateid().equals(d.getVaccinateid()) && p.getBatchnum().equals(d.getBatchnum()) && p.getStandard().equals(d.getStandard())){
					p.setStorenum3(d.getStorenum3());
				}
			}
			if(p.getStorenum1() == null){
				p.setStorenum1("0");
			}
			if(p.getStorenum2() == null){
				p.setStorenum2("0");
			}
			if(p.getStorenum3() == null){
				p.setStorenum3("0");
			}
		}
		// 数组转树形
		String opo = "first";
		List<BsRabiesCheckinStock> templistOne = new ArrayList<>();
		List<List<BsRabiesCheckinStock>> returnlistOne = new ArrayList<>();
		for (BsRabiesCheckinStock s : bsList) {
			if (opo.equals("first")) {
				opo = s.getVaccinatename();
			}
			if (!opo.equals(s.getVaccinatename())) {
				templistOne.get(0).setLeng(templistOne.size());
				returnlistOne.add(templistOne);
				templistOne = new ArrayList<>();
				opo = s.getVaccinatename();
			}
			templistOne.add(s);
		}
		if (templistOne.size() > 0) {
			templistOne.get(0).setLeng(templistOne.size());
			returnlistOne.add(templistOne);
		}
		return returnlistOne;
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @param bsRabiesCheckinStock 
	 * @date 2017年4月19日 下午2:24:54
	 * @description 
	 *		TODO 查询未付款总量
	 * @return
	 *
	 */
	public List<BsRabiesCheckinStock> vaccineStock1(BsRabiesCheckinStock bsRabiesCheckinStock) {
		return dao.vaccineStock1(bsRabiesCheckinStock);
	}

	/**
	 * 
	 * @author zhouqj
	 * @param bsRabiesCheckinStock 
	 * @date 2017年4月19日 下午2:24:54
	 * @description 
	 *		TODO 查询已付款总量
	 * @return
	 *
	 */
	public List<BsRabiesCheckinStock> vaccineStock2(BsRabiesCheckinStock bsRabiesCheckinStock) {
		return  dao.vaccineStock2(bsRabiesCheckinStock);
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @param bsRabiesCheckinStock 
	 * @date 2017年4月19日 下午2:24:54
	 * @description 
	 *		TODO 查询已完成总量
	 * @return
	 *
	 */
	public List<BsRabiesCheckinStock> vaccineStock3(BsRabiesCheckinStock bsRabiesCheckinStock) {
		return dao.vaccineStock3(bsRabiesCheckinStock);
	}
	
	/**
	 * 查询用户签字
	 * @author zhouqj
	 * @date 2017年7月28日 下午6:40:59
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @return 
	 *
	 */
	public BsRabiesCheckin querySignature(BsRabiesCheckin bsRabiesCheckin) {
		return dao.querySignature(bsRabiesCheckin);
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @param bsRabiesNum 
	 * @date 2017年6月6日 上午10:37:57
	 * @description 
	 *		TODO 新建卡数
	 * @return
	 *
	 */
	public int countNumOne(BsRabiesNum bsRabiesNum) {
		return dao.countNumOne(bsRabiesNum);
	}

	/**
	 * 
	 * @author zhouqj
	 * @param bsRabiesNum 
	 * @date 2017年6月6日 上午10:38:02
	 * @description 
	 *		TODO 接种数
	 * @return
	 *
	 */
	public int countNumTwo(BsRabiesNum bsRabiesNum) {
		return dao.countNumTwo(bsRabiesNum);
	}

	/**
	 * 修改付款状态为1
	 * @author zhouqj
	 * @date 2017年8月10日 下午5:26:27
	 * @description 
	 *		TODO
	 * @param checkId
	 *
	 */
	public void updateByPayStatus(String checkId) {
		dao.updateByPayStatus(checkId);
		dao.updateDealByPayStatus(checkId);
	}

	/**
	 * 每天定时删除bs_rabies_pay表中的数据s
	 * @author xuejinshan
	 * @date 2017年8月22日 上午9:51:06
	 * @description 
	 *		TODO
	 *
	 */
	public void deleteDataFromBsRabiesPayEveryday(){
		dao.deleteDataFromBsRabiesPayEveryday();
	}

	/**
	 * 查询疫苗配置
	 * @author zhouqj
	 * @date 2018年1月5日 上午10:25:59
	 * @description 
	 *		TODO
	 * @param vaccType
	 * @param count
	 * @param status
	 * @return
	 *
	 */
	public VaccInfo queryVaccInterVal(String vaccType, String count, String status) {
		return dao.queryVaccInterVal(vaccType, status, OfficeService.getFirstOfficeCode(), count);
	}
	
	/**
	 * 添加犬伤气泡队列
	 * @author yangjian
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param type
	 * @return
	 *
	 */
	public void insertBsRabiesBubble(BsRabiesBubble bsRabiesBubble){
		bsRabiesBubble.setId(IdGen.uuid());
		bsRabiesBubble.setCreateDate(new Date());
		dao.insertBsRabiesBubble(bsRabiesBubble);
	}
	
	/**
	 * 查询犬伤气泡队列
	 * @author yangjian
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param type
	 * @return
	 *
	 */
	public List<BsRabiesBubble> findBsRabiesBubble(BsRabiesBubble bsRabiesBubble){
		return dao.findBsRabiesBubble(bsRabiesBubble);
		
	}
	
	/**
	 * 删除犬伤气泡队列
	 * @author yangjian
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param type
	 * @return
	 */
	public void deleteBsRabiesBubble(BsRabiesBubble bsRabiesBubble){
		dao.deleteBsRabiesBubble(bsRabiesBubble);
	}
	
	/**
	 * 查询所有气泡
	 * @author yangjian
	 * @date 2018年1月5日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param bsRabiesBubble
	 * @return
	 */
	public List<BsRabiesBubble> findBubbleList(BsRabiesBubble bsRabiesBubble){
		return dao.findBubbleList(bsRabiesBubble);
	}

	/**
	 * 每天定时删除表中的数据
	 * @author yangjian
	 * @date 2018年2月26日 上午10:25:52
	 * @description 
	 *		TODO
	 * @param 
	 * @return
	 */
	public void deleteBubble(){
		dao.deleteBubble();
	}
}