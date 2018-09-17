/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.num.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
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
import com.thinkgem.jeesite.modules.manage_stock_in.entity.VaccInfo;
import com.thinkgem.jeesite.modules.num.dao.BsRabiesNumDao;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesNum;
import com.thinkgem.jeesite.modules.num.entity.BsRabiesSocket;
import com.thinkgem.jeesite.modules.num.entity.VacRemind;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckin;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinDeal;
import com.thinkgem.jeesite.modules.rabiesvaccine.entity.BsRabiesCheckinPay;
import com.thinkgem.jeesite.modules.rabiesvaccine.service.BsRabiesCheckinService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AsyncService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.entity.VacJobRemind;
import com.thinkgem.jeesite.modules.wx.service.VacJobRemindService;

import sun.misc.BASE64Decoder;

/**
 * 狂犬疫苗针次Service
 * @author wangdedi
 * @version 2017-03-06
 */
@Service
@Transactional(readOnly = true)
public class BsRabiesNumService extends CrudService<BsRabiesNumDao, BsRabiesNum> {
	
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private VacJobRemindService vacJobRemindService;
	@Autowired
	private BsRabiesCheckinService bsRabiesCheckinService;
	@Autowired
	private BsManageProductService bsManageProductService;
	
	public BsRabiesNum get(String id) {
		return super.get(id);
	}
	
	public List<BsRabiesNum> findList(BsRabiesNum bsRabiesNum) {
		return super.findList(bsRabiesNum);
	}
	
	public Page<BsRabiesNum> findPage(Page<BsRabiesNum> page, BsRabiesNum bsRabiesNum) {
		return super.findPage(page, bsRabiesNum);
	}
	
	@Transactional(readOnly = false)
	public void save(BsRabiesNum bsRabiesNum) {
		super.save(bsRabiesNum);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsRabiesNum bsRabiesNum) {
		super.delete(bsRabiesNum);
	}
	
	/**
	 * 初始化记录参数
	 * @author zhouqj
	 * @date 2017年9月14日 下午2:36:07
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param type 
	 * @return
	 *
	 */
	public BsRabiesNum updateBsRabiesNum(BsRabiesNum bsRabiesNum, String type) {
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		date = ca.getTime();
		if(bsRabiesNum.getRealdate() == null){
			bsRabiesNum.setRealdate(date);
		}
		if(bsRabiesNum.getVaccidate() == null){
			bsRabiesNum.setVaccidate(date);
		}
		if(type.equals("1") && StringUtils.isBlank(bsRabiesNum.getPaystatus()) ){
			bsRabiesNum.setPaystatus(BsRabiesNum.STATUS_NORMAL);
			bsRabiesNum.setStatus(BsRabiesNum.STATUS_NOFINSH);
			bsRabiesNum.setRealdate(null);
		}
		if(type.equals("1") && BsRabiesNum.STATUS_PAYMAL.equals(bsRabiesNum.getPaystatus())){
			bsRabiesNum.setStatus(BsRabiesNum.STATUS_FINSH);
		}
		if(StringUtils.isBlank(bsRabiesNum.getWstatus())){
			bsRabiesNum.setWstatus(BsRabiesNum.WSTATUS_BEFOR);
		}
		return bsRabiesNum;
	}
	
	/**
	 * 保存针次记录
	 * @author zhouqj
	 * @date 2017年8月7日 下午5:09:52
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param bsBumList
	 * @param num
	 * @param model 
	 * @param type 
	 * @param wflag
	 * @param flag
	 * @return
	 *
	 */
	public BsRabiesNum saveNstatus(BsRabiesNum bsRabiesNum, int num, String type, Model model) {
		//查询产品信息
		BsManageProduct product = queryBsProductPId(bsRabiesNum.getPid());
		// 库存+1
		if(num == 1){
			BsRabiesNum bsRabiesNumOld = get(bsRabiesNum.getId());
			BsManageProduct productOld = queryBsProductPId(bsRabiesNumOld.getPid());
			if (null != productOld && StringUtils.isNoneBlank(productOld.getId())) {
				productOld.setStorenum(productOld.getStorenum() + 1);
				bsManageProductService.save(productOld);
				bsRabiesNum.setNstatus(BsRabiesNum.NSTATUS_BEFOR);
			}
		}
		// 查询该时间应种针数
		List<BsRabiesNum> bsBumList = queryBsNumList0(bsRabiesNum);
		// 是否异地完成
		boolean wflag = bsRabiesNum.getWstatus().equals("1");
		// 是否异地完成
		if(!wflag){
			// 库存-1
			if (bsRabiesNum.getRealdate() != null && bsRabiesNum.getStatus().equals("1")) {
				if (null != product && StringUtils.isNoneBlank(product.getId())) {
					Boolean fly = true;
					if(bsBumList.size() != 0 && num == 0){
						fly = bsManageProductService.minusStockIn(product.getId(), (long) bsBumList.size());
					}else{
						fly = bsManageProductService.minusStockIn(product.getId(), (long) 1);
					}
					if(!fly){
						model.addAttribute("msg", "疫苗出库异常,出库数量大于库存数");
					}
					bsRabiesNum.setNstatus(BsRabiesNum.NSTATUS_ARROR);
					//自动出接种单
					if (type.equals("1")) {
						if(bsRabiesNum.getVaccinum() == 0){
							model.addAttribute("notitems", bsRabiesNum.getId());
						}else {
							model.addAttribute("items", bsRabiesNum.getId());
						}
					}
				} else {
					bsRabiesNum.setNstatus(BsRabiesNum.NSTATUS_BEFOR);
				}
			} else {
				bsRabiesNum.setNstatus(BsRabiesNum.NSTATUS_BEFOR);
			}
		}else{
			bsRabiesNum.setNstatus(BsRabiesNum.NSTATUS_BEFOR);
		}
		//保存狂犬病疫苗接种者针次信息
		if(bsBumList.size() != 0 && num == 0){
			for(int i = 0;i < bsBumList.size();i++){
				bsRabiesNum.setId(bsBumList.get(i).getId());
				save(bsRabiesNum);
			}
		}else{
			save(bsRabiesNum);
		}
		// 是否异地完成
		if(!wflag){
			if(bsRabiesNum.getRealdate() != null && bsRabiesNum.getStatus().equals("1") && bsRabiesNum.getNstatus().equals("1")){
				// 查询用户信息
				BsRabiesCheckin bsCheckin = new BsRabiesCheckin();
				bsCheckin.setId(bsRabiesNum.getCheckid());
				// 判断用户openid是否存在
				boolean flag = StringUtils.isNotBlank(bsRabiesCheckinService.get(bsCheckin).getOpenid());
				//判断用户openid是否存在
				if(flag){
					// 发送微信推送
					HashMap<String, String> wxmap = new HashMap<>();
					// 查询用户名字并存入
					wxmap.put("openid",bsRabiesCheckinService.get(bsCheckin).getOpenid());
					// 查询openid并存入
					wxmap.put("username",bsRabiesCheckinService.get(bsCheckin).getUsername());
					if (findList2(bsRabiesNum).size() > 0) {
						// 下一针的程序时间并存入
						wxmap.put("nexttime",DateUtils.formatDate(findList2(bsRabiesNum).get(0).getVaccidate(), "yyyy-MM-dd"));
					} else {
						wxmap.put("nexttime", "您已完成全部犬伤针次接种！");
					}
					//完成接种状态的微信提醒
					asyncService.sendWxMessage(Global.getConfig("wpwxdoge.url") + "api/sendWxTempMsgDog.do",JsonMapper.toJsonString(wxmap));
				}
			}
		}
		return bsRabiesNum;
	}
	
	/**
	 * 查询该时间应种针数
	 * @author zhouqj
	 * @param bsRabiesNum 
	 * @date 2017年6月26日 下午2:00:59
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsRabiesNum> queryBsNumList0(BsRabiesNum bsRabiesNum) {
		return dao.queryBsNumList0(bsRabiesNum);
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月26日 下午1:40:01
	 * @description 
	 *		TODO 查询犬伤疫苗数据
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<BsRabiesNum> findList2(BsRabiesNum bsRabiesNum) {
		return dao.findList2(bsRabiesNum);
	}
	
	/**
	 * 查询所有用户
	 * @author zhouqj
	 * @date 2017年7月4日 下午9:33:42
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<User> getUserName() {
		return UserUtils.getCompanyUsers();
	}
	
	/**
	 * 查询除了蛋白的接种记录
	 * @author zhouqj
	 * @date 2017年7月12日 上午11:27:07
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<List<BsRabiesNum>> findListDown(BsRabiesNum bsRabiesNum) {
		List<BsRabiesNum> socketlist = dao.findListDown(bsRabiesNum);
		// 数组转树形
		String opo = "first";
		List<BsRabiesNum> templistOne = new ArrayList<>();
		List<List<BsRabiesNum>> returnlistOne = new ArrayList<>();
		for (BsRabiesNum s : socketlist) {
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
	 * 查询狂免疫苗
	 * @author zhouqj
	 * @date 2017年7月7日 下午6:05:48
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<BsRabiesNum> findListUp(BsRabiesNum bsRabiesNum) {
		return dao.findListUp(bsRabiesNum);
	}
	
	/**
	 * 转换记录参数
	 * @author zhouqj
	 * @date 2017年9月14日 下午4:19:29
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param model 
	 * @param beginTime
	 * @param endTime
	 * @param endTime 
	 * @param createById 
	 * @return
	 *
	 */
	public BsRabiesNum updateBsRabiesNum(BsRabiesNum bsRabiesNum, String manufacturer, String beginTime, String endTime, String createById) {
		// 默认当前时间
		Date begin = DateUtils.parseDate(beginTime);
		Date end = DateUtils.parseDate(endTime);
		bsRabiesNum.setBeginTime(begin);
		bsRabiesNum.setEndTime(end);
		// 厂家名称
		try {
			bsRabiesNum.setManufacturer(URLDecoder.decode(manufacturer,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 医生id
		if(bsRabiesNum.getCreateById().equals("0")){
			bsRabiesNum.setCreateById(null);
		}else{
			bsRabiesNum.setCreateById(createById);
		}
		return bsRabiesNum;
	}
	
	/**
	 * 统计记录公用方法
	 * @author zhouqj
	 * @date 2017年9月14日 下午4:39:58
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param model
	 *
	 */
	public void updateStock(BsRabiesNum bsRabiesNum, Model model) {
		// 新建卡数
		int countNumOne = bsRabiesCheckinService.countNumOne(bsRabiesNum);
		model.addAttribute("countNumOne", countNumOne);
		// 接种数
		int countNumTwo = bsRabiesCheckinService.countNumTwo(bsRabiesNum);
		model.addAttribute("countNumTwo", countNumTwo);
		// 疫苗当前库存
		List<List<BsRabiesSocket>> returnlist0 = storeList(bsRabiesNum);
		model.addAttribute("returnlist0", returnlist0);
		// 疫苗消耗数
		List<BsRabiesSocket> socketlist = socketlist(bsRabiesNum);
		// 合计
		int socketlistCount = 0;
		for (BsRabiesSocket s : socketlist) {
			socketlistCount += Integer.valueOf(s.getVcount());
		}
		model.addAttribute("socketlistCount", socketlistCount);
		// 疫苗消耗统计
		List<List<BsRabiesSocket>> returnlistOne = socketlistToFrom(bsRabiesNum);
		model.addAttribute("returnlistOne", returnlistOne);
		// 接种统计
		String[][] str = socketlistUp(bsRabiesNum);
		model.addAttribute("str", str);
	}

	/**
	 * 查询疫苗当前库存
	 * @author zhouqj
	 * @date 2017年8月5日 下午3:28:52
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<List<BsRabiesSocket>> storeList(BsRabiesNum bsRabiesNum) {
		List<BsRabiesSocket> list = dao.storeList(bsRabiesNum);
		//查询已付款总量
		List<BsRabiesSocket> bsList2 = dao.vaccineStock2(bsRabiesNum);
		//转化数据
		for(BsRabiesSocket p :  list){
			if(bsList2.size() == 0){
				p.setStorenum2("0");
			}
			for(BsRabiesSocket c : bsList2){
				if(p.getVaccid().equals(c.getVaccid()) && p.getBatchnum().equals(c.getBatchnum()) && p.getStandard().equals(c.getStandard())){
					p.setStorenum2(c.getStorenum2());
				}
			}
			if(p.getStorenum2() == null){
				p.setStorenum2("0");
			}
		}
		// 数组转树形
		String o = "first";
		List<BsRabiesSocket> templist0 = new ArrayList<>();
		List<List<BsRabiesSocket>> returnlist0 = new ArrayList<>();
		for (BsRabiesSocket s : list) {
			if (o.equals("first")) {
				o = s.getVaccname();
			}
			if (!o.equals(s.getVaccname())) {
				templist0.get(0).setLeng(templist0.size());
				returnlist0.add(templist0);
				templist0 = new ArrayList<>();
				o = s.getVaccname();
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
	 * 
	 * @author zhouqj
	 * @date 2017年6月6日 下午2:01:12
	 * @description 
	 *		TODO 疫苗消耗数
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<BsRabiesSocket> socketlist(BsRabiesNum bsRabiesNum) {
		return dao.socketlist(bsRabiesNum);
	}

	/**
	 * 疫苗消耗统计
	 * @author zhouqj
	 * @date 2017年8月7日 下午5:41:51
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<List<BsRabiesSocket>> socketlistToFrom(BsRabiesNum bsRabiesNum) {
		List<BsRabiesSocket> socketlist = dao.socketlist(bsRabiesNum);
		// 数组转树形
		String opo = "first";
		List<BsRabiesSocket> templistOne = new ArrayList<>();
		List<List<BsRabiesSocket>> returnlistOne = new ArrayList<>();
		for (BsRabiesSocket s : socketlist) {
			if (opo.equals("first")) {
				opo = s.getVaccname();
			}
			if (!opo.equals(s.getVaccname())) {
				templistOne.get(0).setLeng(templistOne.size());
				returnlistOne.add(templistOne);
				templistOne = new ArrayList<>();
				opo = s.getVaccname();
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
	 * @date 2017年6月6日 下午2:26:02
	 * @description 
	 *		TODO 接种统计数
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public String[][] socketlistUp(BsRabiesNum bsRabiesNum) {
		List<BsRabiesSocket> socketlistUp = dao.socketlistUp(bsRabiesNum);
		String op = "first";
		List<BsRabiesSocket> templist = new ArrayList<>();
		List<List<BsRabiesSocket>> returnlist = new ArrayList<>();
		// 最大值
		int max = 0;
		// 数组转树形
		for (BsRabiesSocket s : socketlistUp) {
			if (max < s.getVaccinum()) {
				max = s.getVaccinum();
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
		if (templist.size() > 0) {
			returnlist.add(templist);
		}
		// 数组赋初值
		String[][] str = new String[returnlist.size() + 1][max + 2];
		for (int i = 0; i <= returnlist.size(); i++) {
			for (int j = 0; j <= max + 1; j++) {
				if (i == 0 && j == 0) {
					str[0][0] = "剂次";
				} else {
					str[i][j] = "0";
				}
			}
		}
		// 第一列标题赋值
		for (int i = 0; i < max + 1; i++) {
			String val = "";
			if (i == 0) {
				val = "免疫蛋白";
			} else {
				val = "第" + i + "针";
			}
			str[0][i + 1] = val;
		}
		for (int i = 0; i < returnlist.size(); i++) {
			str[i + 1][0] = returnlist.get(i).get(0).getManufacturer();
			for (BsRabiesSocket s : returnlist.get(i)) {
				str[i + 1][s.getVaccinum() + 1] = s.getVcount();
			}
		}
		return str;
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月7日 下午4:31:55
	 * @description 
	 *		TODO 查询用户id
	 * @param createByName
	 * @return
	 *
	 */
	public String queryCreateById(String createByName) {
		return dao.queryCreateById(createByName);
	}

	/**
	 * 查询全部免疫蛋白
	 * @author xuejinshan
	 * @date 2017年9月1日 下午6:49:05
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<BsRabiesNum> queryBsNumListDB(BsRabiesNum bsRabiesNum) {
		return dao.queryBsNumListDB(bsRabiesNum);
	}

	/**
	 * 查询全部
	 * @author zhouqj
	 * @date 2017年7月21日 上午10:20:43
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<BsRabiesNum> queryBsNumListOut(BsRabiesNum bsRabiesNum) {
		return dao.queryBsNumListOut(bsRabiesNum);
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
		BsRabiesCheckinPay bsRabiesCheckinPay = new BsRabiesCheckinPay();
		bsRabiesCheckinPay.setId(IdGen.uuid());
		bsRabiesCheckinPay.setCheck(check);
		dao.insertPay(bsRabiesCheckinPay);
		return bsRabiesCheckinPay.getId();
	}

	/**
	 * 查询记录id
	 * @author zhouqj
	 * @date 2017年8月21日 下午6:06:15
	 * @description 
	 *		TODO
	 * @param id
	 * @return
	 *
	 */
	public String queryPay(String id) {
		return dao.queryPay(id);
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年10月18日 下午8:17:40
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @param map 
	 *
	 */
	@SuppressWarnings("unchecked")
	public void updateWxSign(BsRabiesNum bsRabiesNum, Map<String, Object> map) {
		//获取当前未种并且未签字针次
		List<String> sList = findByChenkWxId(bsRabiesNum);
		List<String> list = new ArrayList<String>();
		//发送参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("office",OfficeService.getFirstOfficeCode());
		param.put("vList",sList);
		// 网络请求微信服务器，获得签字信息
		String wx = null;
		try {
			long time = System.currentTimeMillis();
			logger.info("----------开始前：" + time);
			//"http://127.0.0.1/wpwxdoge/api/rabiesTempSign.do"
			wx = HttpClientReq.httpClientPostJson(Global.getConfig("wpwxdoge.url") + "api/rabiesTempSign.do", JsonMapper.toJsonString(param));
			logger.info(wx);
			logger.info("----------开始后：" + System.currentTimeMillis() + "----------耗时：" + (System.currentTimeMillis() - time)+"ms");
		} catch (Exception e) {
			logger.error("微信请求异常",e);
		}
		if(wx != null && !StringUtils.isBlank(wx)){
			JSONObject obj = JSONObject.parseObject(wx);
			VacRemind vacRemind = null;
			BsRabiesNum bs = null;
			//判断是否成功
			if (obj.getBoolean("success")) {
				JSONArray array = new JSONArray((List<Object>) obj.get("data"));
				for (int i = 0; i < array.size(); i++){
					vacRemind = new VacRemind();
					vacRemind = (VacRemind) JsonMapper.fromJsonString(array.get(i).toString(), VacRemind.class);
					//判断结果集是否存在
					if(StringUtils.isNotBlank(vacRemind.getSid())){
						try {
							//初始化记录数据
							bs = new BsRabiesNum();
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
	 * @date 2017年10月18日 上午10:02:19
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<String> findByChenkWxId(BsRabiesNum bsRabiesNum) {
		return dao.findByChenkWxId(bsRabiesNum);
	}
	
	/**
	 * 查询该记录签字是否存在
	 * @author zhouqj
	 * @date 2017年7月28日 下午8:09:05
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public int querySignature(BsRabiesNum bsRabiesNum) {
		return dao.querySignature(bsRabiesNum);
	}
	
	/**
	 * 签字插入
	 * @author zhouqj
	 * @date 2017年7月13日 下午4:39:40
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	public void saveSignature(BsRabiesNum bsRabiesNum) {
		dao.insertSignature(bsRabiesNum);
	}
	
	/**
	 * 用户告知书签字
	 * @author zhouqj
	 * @date 2017年7月28日 上午9:26:26
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	public void updateCheckSId(BsRabiesNum bsRabiesNum) {
		dao.updateCheckSId(bsRabiesNum);
	}

	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年7月17日 下午3:31:55
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	public void updateSignatures(BsRabiesNum bsRabiesNum) {
		dao.updateSignatures(bsRabiesNum);
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 上午10:19:48
	 * @description 
	 *		TODO 犬伤接种针次为0的数据
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<BsRabiesNum> findListVaccinumNot(BsRabiesNum bsRabiesNum) {
		List<BsRabiesNum> notNumlist = dao.findListVaccinumNot(bsRabiesNum);
		for(BsRabiesNum bs : notNumlist){
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
		return notNumlist;
	}

	/**
	 * 
	 * @author zhouqj
	 * @date 2017年6月12日 上午10:20:27
	 * @description 
	 *		TODO 犬伤接种针次不为0的数据
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<BsRabiesNum> findListVaccinum(BsRabiesNum bsRabiesNum) {
		List<BsRabiesNum> numlist = dao.findListVaccinum(bsRabiesNum);
		for(BsRabiesNum bs : numlist){
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
		return numlist;
	}
	
	/**
	 * 转化默认接种日期
	 * @author zhouqj
	 * @date 2017年9月13日 下午9:44:17
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @return
	 *
	 */
	public BsRabiesCheckin updateNewDate(BsRabiesCheckin bsRabiesCheckin) {
		BsRabiesNum bs = new BsRabiesNum();
		bs.setCheckid(bsRabiesCheckin.getId());
		List<BsRabiesNum> blist = findListVaccinum(bs);
		List<BsRabiesNum> blistNot = findListVaccinumNot(bs);
		if(blist.size() != 0){
			bsRabiesCheckin.setNewdate(blist.get(0).getVaccidate());
		}else if(blistNot.size() != 0){
			bsRabiesCheckin.setNewdate(blistNot.get(0).getVaccidate());
		}else{
			bsRabiesCheckin.setNewdate(new Date());
		}
		return bsRabiesCheckin;
	}
	
	/**
	 * 建档默认增加针次
	 * @author zhouqj
	 * @date 2017年8月7日 上午11:05:25
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckin
	 * @param num
	 * @param flag
	 *
	 */
	public void saveDosage(BsRabiesCheckin bsRabiesCheckin, VaccInfo vaccInfo, int num, int i, int flag) {
		//初始化
		BsRabiesNum bsRabiesNum = new BsRabiesNum();
		VacJobRemind vacJobRemind = new VacJobRemind();
		Date date = null;
		Calendar ca = Calendar.getInstance();
		if(bsRabiesCheckin.getNewdate() == null){
			date = new Date();
		}else{
			date = bsRabiesCheckin.getNewdate();
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
			if(defaultNeedle >= num){
				ca.add(near, Integer.parseInt(list.get(i)));
			}
		}else{
			ca.add(Calendar.DATE, 0);// 增加的天数
		}
		
		//参数赋值
		date = ca.getTime();
		bsRabiesNum.setVaccidate(date);
		bsRabiesNum.setCreateDate(new Date());
		bsRabiesNum.setCreateBy(UserUtils.getUser());
		bsRabiesNum.setWstatus(BsRabiesNum.WSTATUS_BEFOR);
		bsRabiesNum.setNstatus(BsRabiesNum.NSTATUS_BEFOR);
		bsRabiesNum.setSignature(BsRabiesNum.SIGNATURE_NO);
		bsRabiesNum.setCheckid(bsRabiesCheckin.getId());
		bsRabiesNum.setPaystatus(bsRabiesCheckin.getPayment());
		bsRabiesNum.setStatus(BsRabiesNum.STATUS_NOFINSH);
		if(flag == 0){
			bsRabiesNum.setVaccinum(i);
			bsRabiesNum.setPid(bsRabiesCheckin.getPidNo());
			bsRabiesNum.setDose(bsRabiesCheckin.getStandardNo());
			bsRabiesNum.setBatchnum(bsRabiesCheckin.getBatchnumNo());
			bsRabiesNum.setVaccineid(bsRabiesCheckin.getVaccinatenameNo());
			bsRabiesNum.setManufacturer(bsRabiesCheckin.getManufacturerNo());
		}else{
			bsRabiesNum.setVaccinum(i+1);
			bsRabiesNum.setPid(bsRabiesCheckin.getPid());
			bsRabiesNum.setDose(bsRabiesCheckin.getStandard());
			bsRabiesNum.setBatchnum(bsRabiesCheckin.getBatchnum());
			bsRabiesNum.setVaccineid(bsRabiesCheckin.getVaccinatename());
			bsRabiesNum.setManufacturer(bsRabiesCheckin.getManufacturer());
		}
		//保存狂犬病疫苗接种者针次信息
		save(bsRabiesNum);
		//判断是否是微信来源的信息
		if(StringUtils.isNotBlank(bsRabiesCheckin.getOpenid())){
			vacJobRemind.setId(bsRabiesNum.getId());
			vacJobRemind.setOpenid(bsRabiesCheckin.getOpenid());
			vacJobRemind.setCtxdate(date);
			vacJobRemind.setCreatedate(new Date());
			vacJobRemind.setRtype(VacJobRemind.RTYPE_ARROR);
			vacJobRemind.setRstatus(VacJobRemind.RSTATUS_ARROR);
			vacJobRemind.setCode(bsRabiesNum.getVaccineid());
			vacJobRemind.setOffice(OfficeService.getFirstOfficeCode());
			vacJobRemind.setCtxusername(bsRabiesCheckin.getUsername());
			vacJobRemind.setCtxvaccname(bsRabiesCheckinService.vaccineName(bsRabiesCheckin.getVaccinatename()));
			//删除狂犬病疫苗接种者针次提示信息
			vacJobRemindService.delete(vacJobRemind);
			//保存狂犬病疫苗接种者针次提示信息
			vacJobRemindService.insert(vacJobRemind);
		}
	}
	
	/**
	 * 查询已完成并且已减库存记录
	 * @author zhouqj
	 * @date 2017年9月9日 上午11:00:10
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 * @return
	 *
	 */
	public List<BsRabiesNum> findByCheckId(BsRabiesNum bsRabiesNum) {
		return dao.findByCheckId(bsRabiesNum);
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月19日 下午4:59:42
	 * @description 
	 *		TODO 查询产品pid
	 * @param batchnum
	 * @return
	 *
	 */
	public BsManageProduct queryBsProductPId(String pid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pid", pid);
		return dao.queryBsProductPId(map);
	}
	
	/**
	 * 修改签字状态
	 * @author zhouqj
	 * @date 2017年11月14日 下午5:10:06
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	public void updateSignStatus(BsRabiesNum bsRabiesNum) {
		dao.updateSignStatus(bsRabiesNum);
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2018年1月5日 上午10:23:32
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
		vaccInfo = bsRabiesCheckinService.queryVaccInterVal(vaccType, vaccCode ,VaccInfo.SPECIALAEFOR1);
		if(vaccInfo != null){
			if(!Integer.toString(vnum).equals(vaccInfo.getDefaultNeedle())){
				vaccInfo = null;
			}
		}
		if(vaccInfo == null){
			vaccInfo = bsRabiesCheckinService.queryVaccInterVal(vaccType, vaccMan ,VaccInfo.SPECIALAEFOR2);
		}
		if(vaccInfo != null){
			if(!Integer.toString(vnum).equals(vaccInfo.getDefaultNeedle())){
				vaccInfo = null;
			}
		}
		if(vaccInfo == null){
			vaccInfo = bsRabiesCheckinService.queryVaccInterVal(vaccType, Integer.toString(vnum) ,VaccInfo.SPECIALAEFOR3);
		}
		if(vaccInfo == null){
			vaccInfo = bsRabiesCheckinService.queryVaccInterVal(vaccType, null ,VaccInfo.DEFAULTAEFOR);
		}
		return vaccInfo;
	}

	/**
	 * 记录退款
	 * @author zhouqj
	 * @date 2018年1月16日 上午9:23:36
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	@Transactional(readOnly = false)
	public void refundById(BsRabiesNum bsRabiesNum) {
		dao.refundById(bsRabiesNum);
	}
	
	/**
	 * 记录退款
	 * @author yangjian
	 * @date 2018年3月6日 
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckinDeal
	 *
	 */
	@Transactional(readOnly = false)
	public void refundDealById(BsRabiesCheckinDeal bsRabiesCheckinDeal){
		dao.refundDealById(bsRabiesCheckinDeal);
	}

	/**
	 * 保存调价信息
	 * @author zhouqj
	 * @date 2018年1月24日 下午3:10:07
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 *
	 */
	@Transactional(readOnly = false)
	public void saveAdjustment(BsRabiesNum bsRabiesNum) {
		dao.saveAdjustment(bsRabiesNum);
	}

	/**
	 * 变换同类其他有库存疫苗
	 * @author zhouqj
	 * @date 2018年1月25日 下午4:51:16
	 * @description 
	 *		TODO
	 * @param bs
	 * @param product
	 * @return
	 *
	 */
	@Transactional(readOnly = false)
	public BsManageProduct updatePidByName(BsRabiesNum bs, BsManageProduct product) {
		List<BsManageProduct> products = bsManageProductService.findAllById(bs.getPid());
		if(products == null){
			return null;
		}
		if(products.size() > 0){
			product = queryBsProductPId(products.get(0).getId());
			if(!products.get(0).getId().equals(bs.getPid())){
				bs.setPid(product.getId());
				// 清除用户缓存
				UserUtils.clearCache();
				bs.setCreateBy(UserUtils.getUser());
				save(bs);
			}
		}else{
			product = queryBsProductPId(bs.getPid());
		}
		return product;
	}
	
	/**
	 * 获取当天接种完成数量
	 * @author zhouqj
	 * @date 2018年1月24日 下午3:11:45
	 * @description 
	 *		TODO
	 * @param bsRabiesNum
	 */
	public Integer getRabiesNumCount(){
		return dao.getRabiesNumCount();
	}
	
	/**
	 * 查询当前犬伤用户伤口处理费用
	 * @author yangjian
	 * @date 2018年3月5日 下午13:11:45
	 * @description 
	 *		TODO
	 * @param checkinId
	 * @param localCode
	 */
	public List<BsRabiesCheckinDeal> findCheckinDeal(BsRabiesCheckinDeal bsRabiesCheckinDeal){
		return dao.findCheckinDeal(bsRabiesCheckinDeal);
	}
	
	/**
	 * 添加伤口处理费用记录
	 * @author yangjian
	 * @date 2018年3月5日 下午13:11:45
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckinDeal
	 */
	public void insertCheckinDeal(BsRabiesCheckinDeal bsRabiesCheckinDeal){
		bsRabiesCheckinDeal.setId(IdGen.uuid());
		dao.insertCheckinDeal(bsRabiesCheckinDeal);
	}
	
	/**
	 * 保存伤口处理费用记录信息
	 * @author yangjian
	 * @date 2018年3月5日 下午13:11:45
	 * @description 
	 *		TODO
	 * @param bsRabiesCheckinDeal
	 */
	public void saveCheckinDeal(BsRabiesCheckin bsRabiesCheckin, BsRabiesCheckinDeal bsRabiesCheckinDeal){
		//赋值
		bsRabiesCheckinDeal.setCheckinId(bsRabiesCheckin.getId());
		bsRabiesCheckinDeal.setCreateBy(bsRabiesCheckin.getCreateBy());
		bsRabiesCheckinDeal.setCreateName(bsRabiesCheckin.getCreateBy().getLoginName());
		insertCheckinDeal(bsRabiesCheckinDeal);
	}
}
