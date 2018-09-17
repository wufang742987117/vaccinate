/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hepatitisnum.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckin;
import com.thinkgem.jeesite.modules.hepatitis.entity.BsHepatitisBcheckinPay;
import com.thinkgem.jeesite.modules.hepatitis.service.BsHepatitisBcheckinService;
import com.thinkgem.jeesite.modules.hepatitisnum.dao.BsHepatitisBNumDao;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBNum;
import com.thinkgem.jeesite.modules.hepatitisnum.entity.BsHepatitisBSocket;
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.entity.VacRemind;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.entity.VacJobRemind;
import com.thinkgem.jeesite.modules.wx.service.VacJobRemindService;

import sun.misc.BASE64Decoder;

/**
 * 乙肝疫苗针次信息Service
 * 
 * @author xuejinshan
 * @version 2017-07-26
 */
@Service
@Transactional(readOnly = true)
public class BsHepatitisBNumService extends CrudService<BsHepatitisBNumDao, BsHepatitisBNum> {
	
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private VacJobRemindService vacJobRemindService;
	@Autowired
	private BsHepatitisBcheckinService bsHepatitisBcheckinService;
	@Autowired
	private BsManageProductService bsManageProductService;
	
	public BsHepatitisBNum get(String id) {
		return super.get(id);
	}

	public List<BsHepatitisBNum> findList(BsHepatitisBNum bsHepatitisBNum) {
		return super.findList(bsHepatitisBNum);
	}

	public Page<BsHepatitisBNum> findPage(Page<BsHepatitisBNum> page, BsHepatitisBNum bsHepatitisBNum) {
		return super.findPage(page, bsHepatitisBNum);
	}

	@Transactional(readOnly = false)
	public void save(BsHepatitisBNum bsHepatitisBNum) {
		super.save(bsHepatitisBNum);
	}

	@Transactional(readOnly = false)
	public void delete(BsHepatitisBNum bsHepatitisBNum) {
		super.delete(bsHepatitisBNum);
	}

	/**
	 * 初始化记录参数
	 * @author zhouqj
	 * @date 2017年9月14日 下午8:44:34
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param type
	 * @return
	 *
	 */
	public BsHepatitisBNum updateBsHepaNum(BsHepatitisBNum bsHepatitisBNum, String type) {
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		date = ca.getTime();
		if(bsHepatitisBNum.getRealDate() == null){
			bsHepatitisBNum.setRealDate(date);
		}
		if(bsHepatitisBNum.getVaccineDate() == null){
			bsHepatitisBNum.setVaccineDate(date);
		}
		if(type.equals("1") && StringUtils.isBlank(bsHepatitisBNum.getPayStatus())){
			bsHepatitisBNum.setPayStatus(BsHepatitisBNum.STATUS_NORMAL);
			bsHepatitisBNum.setStatus(BsHepatitisBNum.STATUS_NOFINSH);
			bsHepatitisBNum.setRealDate(null);
		}
		if(type.equals("1") && BsHepatitisBNum.STATUS_PAYMAL.equals(bsHepatitisBNum.getPayStatus())){
			bsHepatitisBNum.setStatus(BsHepatitisBNum.STATUS_FINSH);
		}
		if(StringUtils.isBlank(bsHepatitisBNum.getwStatus())){
			bsHepatitisBNum.setwStatus(BsHepatitisBNum.WSTATUS_BEFOR);
		}
		return bsHepatitisBNum;
	}
	
	/**
	 * 保存乙肝针次库存信息
	 * @author xuejinshan
	 * @date 2017年8月8日 上午11:25:17
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param i
	 * @param Vxinflag
	 * @param vxinflag2
	 * @param bsBumList
	 * @return
	 *
	 */
	public BsHepatitisBNum dealProduct(BsHepatitisBNum bsHepatitisBNum ,int num, Model model){
		//查询产品信息
		BsManageProduct product = findBsProduct(bsHepatitisBNum.getPid());
		//nStatus为1时执行  加库存
		if(num == 1){
			BsHepatitisBNum bsHepatitisBNumOld = get(bsHepatitisBNum.getId());
			BsManageProduct productOld = findBsProduct(bsHepatitisBNumOld.getPid());
			if(null != productOld && StringUtils.isNoneBlank(productOld.getId())){
				productOld.setStorenum(productOld.getStorenum() + 1);
				bsManageProductService.save(productOld);
				bsHepatitisBNum.setnStatus(BsHepatitisBNum.NSTATUS_BEFOR);
			}
		}
		//是否外地==》  是
		boolean wflag = bsHepatitisBNum.getwStatus().equals("1");
		// 是否异地完成
		if(!wflag){
			//nStatus为空或为0时执行 减库存 或者 只修改记录
			if (bsHepatitisBNum.getRealDate() != null && bsHepatitisBNum.getStatus().equals("1")) {
				if (null != product && StringUtils.isNoneBlank(product.getId())) {
					Boolean fly = true;
					fly = bsManageProductService.minusStockIn(product.getId(), (long) 1);
					if(!fly){
						model.addAttribute("msg", "疫苗出库异常,出库数量大于库存数");
					}
					bsHepatitisBNum.setnStatus(BsHepatitisBNum.NSTATUS_ARROR);
				} else {
					bsHepatitisBNum.setnStatus(BsHepatitisBNum.NSTATUS_BEFOR);
				}
			} else {
				bsHepatitisBNum.setnStatus(BsHepatitisBNum.NSTATUS_BEFOR);
			}
		}else{
			bsHepatitisBNum.setnStatus(BsHepatitisBNum.NSTATUS_BEFOR);
		}
		//保存狂犬病疫苗接种者针次信息
		save(bsHepatitisBNum);
		// 是否异地完成
		if(!wflag){
			if(bsHepatitisBNum.getRealDate() != null && bsHepatitisBNum.getStatus().equals("1") && bsHepatitisBNum.getnStatus().equals("1")){
				// 设置接种者的信息
				BsHepatitisBcheckin bsCheckin = new BsHepatitisBcheckin();
				bsCheckin.setId(bsHepatitisBNum.getCheckId());
				//微信建档id是否存在==>存在
				boolean vxinflag = StringUtils.isNotBlank(bsHepatitisBcheckinService.get(bsCheckin).getOpenId());
				//判断用户openid是否存在
				if(vxinflag){
					// 发送微信推送
					HashMap<String, String> wxmap = new HashMap<>();
					// 查询用户名字并存入
					wxmap.put("openid", bsHepatitisBcheckinService.get(bsCheckin).getOpenId());
					// 查询openid并存入
					wxmap.put("username", bsHepatitisBcheckinService.get(bsCheckin).getUsername());
					if (findHepaList(bsHepatitisBNum).size() > 0) {
						// 下一针的程序时间并存入
						wxmap.put("nexttime",DateUtils.formatDate(findHepaList(bsHepatitisBNum).get(0).getVaccineDate(), "yyyy-MM-dd"));
					} else {
						wxmap.put("nexttime", "您已完成全部疫苗针次接种！");
					}
					//完成接种状态的微信提醒
					asyncService.sendWxMessage(Global.getConfig("wpwxdoge.url") + "api/sendWxTempMsgDog.do",JsonMapper.toJsonString(wxmap));
				}
			}
		}
		return bsHepatitisBNum;
	}
	
	/**
	 * 转换记录参数
	 * @author zhouqj
	 * @date 2017年9月14日 下午11:55:15
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param manufacturer
	 * @param beginTime
	 * @param endTime
	 * @param createById
	 * @param vaccType 
	 * @return
	 *
	 */
	public BsHepatitisBNum updateBsRabiesNum(BsHepatitisBNum bsHepatitisBNum, String manufacturer, String beginTime, String endTime, String createById, String vaccType) {
		// 默认当前时间
		Date begin = DateUtils.parseDate(beginTime);
		Date end = DateUtils.parseDate(endTime);
		bsHepatitisBNum.setBeginTime(begin);
		bsHepatitisBNum.setEndTime(end);
		// 厂家名称
		try {
			bsHepatitisBNum.setManufacturer(URLDecoder.decode(manufacturer,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//完成状态
		if(StringUtils.isBlank(bsHepatitisBNum.getPayStatus())){
			bsHepatitisBNum.setPayStatus("2");
		}
		// 医生id
		if(bsHepatitisBNum.getCreateById().equals("0")){
			bsHepatitisBNum.setCreateById(null);
		}else{
			bsHepatitisBNum.setCreateById(createById);
		}
		//疫苗类型
		if(bsHepatitisBNum.getVaccType().equals("0")){
			bsHepatitisBNum.setVaccType(null);
		}else{
			bsHepatitisBNum.setVaccType(vaccType);
		}
		return bsHepatitisBNum;
	}


	/**
	 * 查询乙肝疫苗数据
	 * 
	 * @author xuejinshan
	 * @date 2017年7月29日 下午5:34:40
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<BsHepatitisBNum> findHepaList(BsHepatitisBNum bsHepatitisBNum) {
		return dao.findHepaList(bsHepatitisBNum);
	}

	/**
	 * 查询所有用户
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 上午10:09:27
	 * @description TODO
	 * @return
	 *
	 */
	public List<User> getUserName() {
		return UserUtils.getCompanyUsers();
	}

	/**
	 * 查询接种明细
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 下午1:46:44
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<List<BsHepatitisBNum>> findListDown(BsHepatitisBNum bsHepatitisBNum) {
		List<BsHepatitisBNum> socketlist = dao.findListDown(bsHepatitisBNum);
		for (BsHepatitisBNum bs : socketlist) {
			//查询疫苗类型
			bs.setVaccType(bsHepatitisBcheckinService.getQueryVaccName(bs.getVaccType()));
		}
		// 数组转树形
		String opo = "first";
		List<BsHepatitisBNum> templistOne = new ArrayList<>();
		List<List<BsHepatitisBNum>> returnlistOne = new ArrayList<>();
		for (BsHepatitisBNum s : socketlist) {
			if (opo.equals("first")) {
				opo = s.getManufacturer();
			}
			if (!opo.equals(s.getManufacturer())) {
				returnlistOne.add(templistOne);
				templistOne = new ArrayList<>();
				opo = s.getManufacturer();
			}
			templistOne.add(s);
		}
		if (templistOne.size() > 0) {
			returnlistOne.add(templistOne);
		}
		return returnlistOne;
	}

	/**
	 * 疫苗消耗数
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 下午4:42:57
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<BsHepatitisBSocket> socketlist(BsHepatitisBNum bsHepatitisBNum) {
		return dao.socketlist(bsHepatitisBNum);
	}

	/**
	 * 接种统计数  要显示已接种针次
	 * 
	 * @author xuejinshan
	 * @date 2017年8月2日 下午4:43:43
	 * @description TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public String[][] socketlistUp(BsHepatitisBNum bsHepatitisBNum) {
		List<BsHepatitisBSocket> socketlistUp = dao.socketlistUp(bsHepatitisBNum);
		String op = "first";
		List<BsHepatitisBSocket> templist = new ArrayList<>();
		List<List<BsHepatitisBSocket>> returnlist = new ArrayList<>();
		// 最大值
		int max = 0;
		// 数组转树形
		for (BsHepatitisBSocket s : socketlistUp) {
			if (max < s.getVaccineNum()) {
				max = s.getVaccineNum();
			}
			if (op.equals("first")) {
				op = s.getManufacturer();
			}
			if (!op.equals(s.getManufacturer())) {
				returnlist.add(templist);
				templist = new ArrayList<>();
				op = s.getManufacturer();
			}
			templist.add(s);
		}
		//将最后一个templist加入returnlist
		if (templist.size() > 0) {
			returnlist.add(templist);
		}
		// 数组赋初值
		String[][] str = new String[returnlist.size() + 1][max + 1];
		for (int i = 0; i <= returnlist.size(); i++) {
			for (int j = 0; j <= max; j++) {
				if (i == 0 && j == 0) {
					str[0][0] = "剂次";
				} else {
					str[i][j] = "0";
				}
			}
		}
		// 第一列标题赋值
		for (int i = 0; i < max; i++) {
			str[0][i + 1] = "第" + (i+1) + "针";
		}
		for (int i = 0; i < returnlist.size(); i++) {
			str[i + 1][0] = returnlist.get(i).get(0).getManufacturer();
			for (BsHepatitisBSocket s : returnlist.get(i)) {
				str[i + 1][s.getVaccineNum()] = s.getVcount();
			}
		}
		return str;
	}

	/**
	 * 查询用户id
	 * 
	 * @author xuejinshan
	 * @date 2017年8月3日 上午10:32:34
	 * @description TODO
	 * @param createById
	 * @return
	 *
	 */
	public String queryCreateById(String createById) {
		return dao.queryCreateById(createById);
	}
	
	/**
	 * 查询全部乙肝疫苗
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:28:21
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<BsHepatitisBNum> queryBsNumListOut(BsHepatitisBNum bsHepatitisBNum) {
		return dao.queryBsNumListOut(bsHepatitisBNum);
	}

	/**
	 * 查询乙肝疫苗实际库存 并根据乙肝疫苗名称进行分组
	 * @author xuejinshan
	 * @date 2017年8月11日 下午7:12:03
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<List<BsHepatitisBSocket>> storeList(BsHepatitisBNum bsHepatitisBNum) {
		List<BsHepatitisBSocket> listOn = dao.storeList(bsHepatitisBNum);
		List<BsHepatitisBSocket> list = new ArrayList<BsHepatitisBSocket>();
		//获取疫苗类型
		String vaccType = bsHepatitisBNum.getVaccType();
		List<VaccInfo> vlist = bsHepatitisBcheckinService.getQueryVacc(vaccType);
		//转换疫苗大类code
		StringBuffer check = new StringBuffer();
		for(VaccInfo vi : vlist){
			check.append(vi.getVaccCode() + ",");
		}
		String vacc = check.substring(0,check.length()-1).toString();
		List<String> slist = StringUtils.splitToList(vacc,",");
		//排除疫苗
		for(BsHepatitisBSocket bp : listOn){
			for(String str : slist){
				if(bp.getVaccineId().substring(0, 2).equals(str)){
					list.add(bp);
				}
			}
		}
		//查询针次表中已付款总量
		List<BsHepatitisBSocket> bsList2 = dao.vaccineHepaStock2(bsHepatitisBNum);
		//转化数据
		for(BsHepatitisBSocket p : list){
			if(bsList2.size() == 0){
				p.setStoreNum2("0");
			}
			for(BsHepatitisBSocket c : bsList2){
				if(p.getVaccineId().equals(c.getVaccineId()) && p.getBatch().equals(c.getBatch()) && p.getStandard().equals(c.getStandard())){
					p.setStoreNum2(c.getStoreNum2());
				}
			}
			if(p.getStoreNum2() == null){
				p.setStoreNum2("0");
			}
		}
		// 数组转树形
		String o = "first";
		List<BsHepatitisBSocket> templist0 = new ArrayList<>();
		List<List<BsHepatitisBSocket>> returnlist0 = new ArrayList<>();
		for (BsHepatitisBSocket s : list) {
			if (o.equals("first")) {
				o = s.getVaccineName();
			}
			if (!o.equals(s.getVaccineName())) {
				templist0.get(0).setLeng(templist0.size());
				returnlist0.add(templist0);
				templist0 = new ArrayList<>();
				o = s.getVaccineName();
			}
			templist0.add(s);
		}
		if (templist0.size() > 0) {
			templist0.get(0).setLeng(templist0.size());
			returnlist0.add(templist0);
		}
		
		return returnlist0;
	}

	/**
	 * 乙肝疫苗实际消耗统计   并以疫苗名进行分组
	 * @author xuejinshan
	 * @date 2017年8月11日 下午7:05:40
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<List<BsHepatitisBSocket>> socketlistToFrom(BsHepatitisBNum bsHepatitisBNum) {
		List<BsHepatitisBSocket> socketlist = dao.socketlist(bsHepatitisBNum);
		// 数组转树形  以疫苗名称进行分组
		String opo = "first";
		List<BsHepatitisBSocket> templistOne = new ArrayList<>();
		List<List<BsHepatitisBSocket>> returnlistOne = new ArrayList<>();
		for (BsHepatitisBSocket s : socketlist) {
			if (opo.equals("first")) {
				opo = s.getVaccineName();
			}
			if (!opo.equals(s.getVaccineName())) {
				templistOne.get(0).setLeng(templistOne.size());
				returnlistOne.add(templistOne);
				templistOne = new ArrayList<>();
				opo = s.getVaccineName();
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
	 *根据checkid查询针次信息
	 * @author xuejinshan
	 * @date 2017年8月21日 上午10:39:58
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<BsHepatitisBNum> findByCheckid(BsHepatitisBNum bsHepatitisBNum) {
		return dao.findByCheckid(bsHepatitisBNum);
	}
	
	/**
	 * 统计记录公用方法
	 * @author zhouqj
	 * @date 2017年9月15日 上午12:18:00
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @param model
	 *
	 */
	public void updateStock(BsHepatitisBNum bsHepatitisBNum, Model model) {
		// 新建卡数
		int countNumOne = bsHepatitisBcheckinService.countNumOne(bsHepatitisBNum);
		// 接种数
		int countNumTwo = bsHepatitisBcheckinService.countNumTwo(bsHepatitisBNum);
		model.addAttribute("countNumOne", countNumOne);
		model.addAttribute("countNumTwo", countNumTwo);
		// 疫苗当前库存
		List<List<BsHepatitisBSocket>> returnlist0 = storeList(bsHepatitisBNum);
		model.addAttribute("returnlist0", returnlist0);
		// 疫苗消耗数
		List<BsHepatitisBSocket> socketlist = socketlist(bsHepatitisBNum);
		// 合计  即消耗总量
		int socketlistCount = 0;
		for (BsHepatitisBSocket s : socketlist) {
			socketlistCount += Integer.valueOf(s.getVcount());
		}
		model.addAttribute("socketlistCount", socketlistCount);
		// 疫苗实际消耗统计
		List<List<BsHepatitisBSocket>> returnlistOne = socketlistToFrom(bsHepatitisBNum);
		model.addAttribute("returnlistOne", returnlistOne);
		// 接种统计
		String[][] str = socketlistUp(bsHepatitisBNum);
		model.addAttribute("str", str);
	}

	/**
	 * 
	 * @author zhouqj
	 * @param map 
	 * @date 2017年10月18日 下午8:17:40
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	@SuppressWarnings("unchecked")
	public void updateWxSign(BsHepatitisBNum bsHepatitisBNum, Map<String, Object> map) {
		//获取当前未种并且未签字针次
		List<String> sList = findByChenkWxId(bsHepatitisBNum);
		List<String> list = new ArrayList<String>();
		//发送参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("office",OfficeService.getFirstOfficeCode());
		param.put("vList",sList);
		// 网络请求微信服务器，获得签字信息
		String wx = null;
		try {
			//"http://127.0.0.1/wpwxdoge/api/rabiesTempSign.do"
			wx = HttpClientReq.httpClientPostJson(Global.getConfig("wpwxdoge.url") + "api/rabiesTempSign.do", JsonMapper.toJsonString(param));
			logger.info(wx);
		} catch (Exception e) {
			logger.error("微信请求异常",e);
		}
		if(wx != null && !StringUtils.isBlank(wx)){
			JSONObject obj = JSONObject.parseObject(wx);
			VacRemind vacRemind = null;
			BsHepatitisBNum bs = null;
			//判断是否成功
			if (obj.getBoolean("success")) {
				JSONArray array = new JSONArray((List<Object>) obj.get("data"));
				for (int i = 0; i < array.size(); i++){
					vacRemind = new VacRemind();
					vacRemind =(VacRemind) JsonMapper.fromJsonString(array.get(i).toString(), VacRemind.class);
					//判断结果集是否存在
					if(StringUtils.isNotBlank(vacRemind.getSid())){
						try {
							//初始化记录数据
							bs = new BsHepatitisBNum();
							bs.setId(vacRemind.getVid());
							bs = get(bs);
							//判断签字是否存在
							if(StringUtils.isNotBlank(vacRemind.getSignature())){
								//base64 转换签字
								BASE64Decoder decoder = new BASE64Decoder(); 
								byte[] sign = decoder.decodeBuffer(vacRemind.getSignature());
								//签字状态
								if(null != sign && sign.length > 0){
									bs.setSignatureData(sign);
									bs.setStype(vacRemind.getStype());
									bs.setsId(vacRemind.getSid());
									//查询该记录签字是否存在
									int count = querySignature(bs);
									if(count == 0){
										//新增签字
										saveSignature(bs);
									}
									//补充用户告知书签字
									updateCheckSId(bs);
									//补充记录签字
									bs.setSignature(BsRabiesNum.SIGNATURE_YES);
									updateSignatures(bs);
									//成功获取签字id
									list.add(vacRemind.getVid());
								}
							}	
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if(array.size() != 0){
					map.put("list", list);
					map.put("success", true);
				}
			}
		}
	}
	
	/**
	 * 获取当前未种并且未签字针次
	 * @author zhouqj
	 * @date 2017年10月18日 下午8:20:41
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	private List<String> findByChenkWxId(BsHepatitisBNum bsHepatitisBNum) {
		return dao.findByChenkWxId(bsHepatitisBNum);
	}
	
	/**
	 * 查询该记录签字是否存在
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:35:26
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public int querySignature(BsHepatitisBNum bsHepatitisBNum) {
		return dao.querySignature(bsHepatitisBNum);
	}

	/**
	 * 签字插入
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:39:38
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	public void saveSignature(BsHepatitisBNum bsHepatitisBNum) {
		dao.insertSignature(bsHepatitisBNum);
	}

	/**
	 * 用户告知书签字
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:43:44
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	public void updateCheckSId(BsHepatitisBNum bsHepatitisBNum) {
		dao.updateCheckSId(bsHepatitisBNum);
	}

	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年8月9日 下午5:52:26
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	public void updateSignatures(BsHepatitisBNum bsHepatitisBNum) {
		dao.updateSignatures(bsHepatitisBNum);
	}
	
	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月28日 下午2:10:45
	 * @description TODO 查找针次信息
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<BsHepatitisBNum> findById(BsHepatitisBNum bsHepatitisBNum) {
		List<BsHepatitisBNum> bsHepatitisBNumsList = dao.findById(bsHepatitisBNum);
		for(BsHepatitisBNum bs : bsHepatitisBNumsList){
			if(bs.getCreateDate() != null){
				 Calendar c1 = Calendar.getInstance();
				 c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) -1);
				 Calendar c2 = Calendar.getInstance();
				 c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH) +1);
				 Date old = c1.getTime();
				 Date now = c2.getTime();
				 if(bs.getCreateDate().after(old) && bs.getCreateDate().before(now)) {
		            bs.setDataStatus("0");
				 }
			}
		}
		return bsHepatitisBNumsList;
	}
	
	/**
	 * 转化默认接种日期
	 * @author zhouqj
	 * @date 2017年9月14日 下午7:14:23
	 * @description 
	 *		TODO
	 * @param bsHepatitisBcheckin
	 * @return
	 *
	 */
	public BsHepatitisBcheckin updateNewDate(BsHepatitisBcheckin bsHepatitisBcheckin) {
		BsHepatitisBNum bh = new BsHepatitisBNum();
		bh.setCheckId(bsHepatitisBcheckin.getId());
		List<BsHepatitisBNum> list = findById(bh);
		if(list.size() != 0){
			bsHepatitisBcheckin.setNewdate(list.get(0).getVaccineDate());
		}else{
			bsHepatitisBcheckin.setNewdate(new Date());
		}
		return bsHepatitisBcheckin;
	}
	
	/**
	 * 获取间隔配置
	 * @author zhouqj
	 * @date 2018年1月4日 下午8:45:39
	 * @description 
	 *		TODO
	 * @param vaccType
	 * @param vaccCode
	 * @param vaccMan
	 * @param vnum
	 * @return
	 *
	 */
	public VaccInfo updateVaccInterVal(String vaccType, String vaccCode, String vaccMan, int vnum) {
		VaccInfo vaccInfo = new VaccInfo();
		vaccInfo = bsHepatitisBcheckinService.queryVaccInterVal(vaccType, vaccCode ,VaccInfo.SPECIALAEFOR1);
		if(vaccInfo == null){
			vaccInfo = bsHepatitisBcheckinService.queryVaccInterVal(vaccType, vaccMan ,VaccInfo.SPECIALAEFOR2);
		}
		if(vaccInfo == null){
			vaccInfo = bsHepatitisBcheckinService.queryVaccInterVal(vaccType, Integer.toString(vnum) ,VaccInfo.SPECIALAEFOR3);
		}
		if(vaccInfo == null){
			vaccInfo = bsHepatitisBcheckinService.queryVaccInterVal(vaccType, null ,VaccInfo.DEFAULTAEFOR);
		}
		return vaccInfo;
	}
	
	/**
	 * 建档案时 设置针次信息
	 * 
	 * @author xuejinshan
	 * @param i 
	 * @param vnum 
	 * @param bsHepatitisBcheckin 
	 * @date 2017年8月5日 下午4:52:53
	 * @description TODO
	 * @param bsHepatitisBcheckin
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public void saveNew(BsHepatitisBcheckin bsHepatitisBcheckin, VaccInfo vaccInfo, int vnum, int i, String deleteOne) {
		//疫苗类型
		String vaccType = bsHepatitisBcheckin.getVaccType();
		//初始化
		BsHepatitisBNum bsHepatitisBNum = new BsHepatitisBNum();
		VacJobRemind vacJobRemind = new VacJobRemind();
		Date date = null;
		Calendar ca = Calendar.getInstance();
		if(bsHepatitisBcheckin.getNewdate() == null){
			date = new Date();
		}else{
			date = bsHepatitisBcheckin.getNewdate();
		}
		ca.setTime(date);
		
		if(vaccInfo != null){
			//时间类型
			int near = 0;
			switch (vaccInfo.getLasting()) {
			case "month":
				near = Calendar.MONTH;
				break;
			case "day":
				near = Calendar.DATE;
				break;
			case "year":
				near = Calendar.YEAR;
				break;
			}
			//时间针次间隔
			List<String> list = StringUtils.splitToList(vaccInfo.getInterval(), ",");
			int defaultNeedle = Integer.parseInt(vaccInfo.getDefaultNeedle());
			if(defaultNeedle >= vnum){
				ca.add(near, Integer.parseInt(list.get(i)));
			}
		}
		date = ca.getTime();
		bsHepatitisBNum.setVaccineDate(date);
		bsHepatitisBNum.setCreateDate(new Date());
		bsHepatitisBNum.setCreateBy(UserUtils.getUser());
		bsHepatitisBNum.setwStatus(BsHepatitisBNum.WSTATUS_BEFOR);
		bsHepatitisBNum.setnStatus(BsHepatitisBNum.NSTATUS_BEFOR);
		bsHepatitisBNum.setSignature(BsHepatitisBNum.SIGNATURE_NO);
		bsHepatitisBNum.setCheckId(bsHepatitisBcheckin.getId());
		bsHepatitisBNum.setPayStatus(bsHepatitisBcheckin.getPayStatus());
		bsHepatitisBNum.setStatus(BsHepatitisBNum.STATUS_NOFINSH);
		//疫苗信息
		bsHepatitisBNum.setVaccType(bsHepatitisBcheckin.getVaccType());
		bsHepatitisBNum.setVaccineNum(i + 1);
		bsHepatitisBNum.setPid(bsHepatitisBcheckin.getPid());
		bsHepatitisBNum.setBatch(bsHepatitisBcheckin.getBatch());
		bsHepatitisBNum.setDose(bsHepatitisBcheckin.getStandard());
		bsHepatitisBNum.setStandard(bsHepatitisBcheckin.getStandard());
		bsHepatitisBNum.setVaccineId(bsHepatitisBcheckin.getVaccineName());
		bsHepatitisBNum.setManufacturer(bsHepatitisBcheckin.getManufacturer());
		// 保存乙肝疫苗接种者针次信息
		save(bsHepatitisBNum);
		//判断是否是微信来源的信息
		if(StringUtils.isNotBlank(bsHepatitisBcheckin.getOpenId())){
			vacJobRemind.setId(bsHepatitisBNum.getId());
			vacJobRemind.setOpenid(bsHepatitisBcheckin.getOpenId());
			vacJobRemind.setCtxdate(date);
			vacJobRemind.setCreatedate(new Date());
			vacJobRemind.setRtype(VacJobRemind.RTYPE_BRROR);
			vacJobRemind.setRstatus(VacJobRemind.RSTATUS_ARROR);
			vacJobRemind.setCode(bsHepatitisBNum.getVaccineId());
			vacJobRemind.setOffice(OfficeService.getFirstOfficeCode());
			vacJobRemind.setCtxusername(bsHepatitisBcheckin.getUsername());
			vacJobRemind.setCtxvaccname(bsHepatitisBcheckinService.vaccineName(bsHepatitisBcheckin.getVaccineName()));
			//删除狂犬病疫苗接种者针次提示信息
			vacJobRemindService.delete(vacJobRemind);
			//保存狂犬病疫苗接种者针次提示信息
			vacJobRemindService.insert(vacJobRemind);
		}
	}
	
	/**
	 * 查询已完成并且已减库存记录
	 * @author zhouqj
	 * @date 2017年9月9日 上午11:11:43
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 * @return
	 *
	 */
	public List<BsHepatitisBNum> findByCheckId(BsHepatitisBNum bsHepatitisBNum) {
		return dao.findByCheckId(bsHepatitisBNum);
	}
	
	/**
	 * 
	 * @author xuejinshan
	 * @date 2017年7月29日 下午12:02:01
	 * @description TODO
	 * @param batch
	 * @param vaccineId
	 * @return
	 *
	 */
	public BsManageProduct findBsProduct(String pid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pid", pid);
		return dao.findBsProduct(map);
	}
	
	/**
	 * 根据checkId删除针次
	 * @author xuejinshan
	 * @date 2017年8月14日 下午7:24:51
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	public void deleteByCheckid(BsHepatitisBNum bsHepatitisBNum) {
		dao.deleteByCheckid(bsHepatitisBNum);
	}

	/**
	 * 插入pay表
	 * @author zhouqj
	 * @date 2017年8月21日 下午5:39:00
	 * @description 
	 *		TODO
	 * @param id
	 * @return 
	 *
	 */
	public String insertPay(String check) {
		BsHepatitisBcheckinPay bsHepatitisBcheckinPay = new BsHepatitisBcheckinPay();
		bsHepatitisBcheckinPay.setId(IdGen.uuid());
		bsHepatitisBcheckinPay.setCheck(check);
		dao.insertPay(bsHepatitisBcheckinPay);
		return bsHepatitisBcheckinPay.getId();
	}
	
	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年11月14日 下午9:32:16
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	public void updateSignStatus(BsHepatitisBNum bsHepatitisBNum) {
		dao.updateSignStatus(bsHepatitisBNum);
	}

	/**
	 * 记录退款
	 * @author zhouqj
	 * @date 2018年1月16日 上午9:59:53
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	@Transactional(readOnly = false)
	public void refundById(BsHepatitisBNum bsHepatitisBNum) {
		dao.refundById(bsHepatitisBNum);
	}

	/**
	 * 保存调价信息
	 * @author zhouqj
	 * @date 2018年1月24日 下午4:10:19
	 * @description 
	 *		TODO
	 * @param bsHepatitisBNum
	 *
	 */
	@Transactional(readOnly = false)
	public void saveAdjustment(BsHepatitisBNum bsHepatitisBNum) {
		dao.saveAdjustment(bsHepatitisBNum);
	}

	/**
	 * 变换同类其他有库存疫苗
	 * @author zhouqj
	 * @date 2018年1月25日 下午5:10:28
	 * @description 
	 *		TODO
	 * @param bs
	 * @param product
	 * @return
	 *
	 */
	@Transactional(readOnly = false)
	public BsManageProduct updatePidByName(BsHepatitisBNum bs, BsManageProduct product) {
		List<BsManageProduct> products = bsManageProductService.findAllById(bs.getPid());
		if(products == null){
			return null;
		}
		if(products.size() > 0){
			product = findBsProduct(products.get(0).getId());
			if(!products.get(0).getId().equals(bs.getPid())){
				bs.setPid(product.getId());
				// 清除用户缓存
				UserUtils.clearCache();
				bs.setCreateBy(UserUtils.getUser());
				save(bs);
			}
		}else{
			product = findBsProduct(bs.getPid());
		}
		return product;
	}

}