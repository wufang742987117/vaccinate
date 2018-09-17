/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.web;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.child_vaccinaterecord.entity.ChildVaccinaterecord;
import com.thinkgem.jeesite.modules.enter.entity.SysEnterpriseInfo;
import com.thinkgem.jeesite.modules.enter.service.SysEnterpriseInfoService;
import com.thinkgem.jeesite.modules.manage_stock_in.service.ManageStockInService;
import com.thinkgem.jeesite.modules.product.entity.BsCheckRecord;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.entity.BsProductDO;
import com.thinkgem.jeesite.modules.product.entity.ProductUseList;
import com.thinkgem.jeesite.modules.product.entity.RepertorySum;
import com.thinkgem.jeesite.modules.product.service.BsCheckRecordService;
import com.thinkgem.jeesite.modules.product.service.BsManageProductService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

/**
 * 疫苗信息Controller
 * @author 王德地
 * @version 2017-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/product/bsManageProduct")
public class BsManageProductController extends BaseController {

	private final static String STORE_OPTION_RELOAD = "reload";
	
	@Autowired
	private BsManageProductService bsManageProductService;
	@Autowired
	private BsManageVaccineService bsManageVaccineService;
	@Autowired
	private SysEnterpriseInfoService sysEnterpriseInfoService;
	@Autowired
	private ManageStockInService manageStockInService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private BsCheckRecordService bsCheckRecordService;

	@ModelAttribute
	public BsManageProduct get(@RequestParam(required=false) String id) {
		BsManageProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsManageProductService.get(id);
		}
		if (entity == null){
			entity = new BsManageProduct();
		}
		return entity;
	}
	
	@RequiresPermissions("product:bsManageProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(BsManageProduct bsManageProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(bsManageProduct.getCheck0() != 1){
			bsManageProduct.setStorenumIsNull(true);
		}
		List<SysEnterpriseInfo> EnterpriseInfoList = sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
		for (SysEnterpriseInfo sysEnterpriseInfo : EnterpriseInfoList) {
			sysEnterpriseInfo.setName(sysEnterpriseInfo.getCode()+"__"+sysEnterpriseInfo.getName());
		}
		model.addAttribute("EnterpriseInfoList", EnterpriseInfoList);
		bsManageProduct.setOrderBy("a.vaccineid, A.CREATE_DATE DESC");
		Page<BsManageProduct> page = bsManageProductService.findPage(new Page<BsManageProduct>(request, response), bsManageProduct); 
		model.addAttribute("page", page);
		model.addAttribute("vacclist", bsManageProductService.findVaccinateAble(new BsManageProduct(), bsManageProduct.getShowAll()));
		
		//转换科室信息
		List<Office> officelist = UserUtils.getOfficeList();
		for(BsManageProduct tempPro : page.getList()){
			for(Office off : officelist){
				if(tempPro.getOfficeCodeDb().equals(off.getCode())){
					tempPro.setOfficeCodeDb(off.getName());
					break;
				}
			}
		}
		
		return "modules/product/bsManageProductList";
	}
	
	@RequiresPermissions("product:bsManageProduct:view")
	@RequestMapping(value = "form")
	public String form(BsManageProduct bsManageProduct, Model model) {
		model.addAttribute("bsManageProduct", bsManageProduct);
		List<BsManageVaccine> vacclist = bsManageVaccineService.findList(new BsManageVaccine());
		for(BsManageVaccine v : vacclist){
			v.setName(v.getCodeAll() + "___" + v.getName());
		}
		model.addAttribute("vacclist", vacclist);
		//初始化数据
		if(!StringUtils.isNotBlank(bsManageProduct.getId())){
			bsManageProduct.setIsforeign(BsManageProduct.TYPE1);
			bsManageProduct.setIsshow("Y");
			bsManageProduct.setStorenum(0l);
			bsManageProduct.setSellprice(0l);
			bsManageProduct.setSpec(1);
			bsManageProduct.setDosage(1l);
			bsManageProduct.setCostprice(0.0);
		}
		bsManageProduct.setManufacturer(bsManageProduct.getCode());
		//疫苗制造厂商
		List<SysEnterpriseInfo>EnterpriseInfoList = sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
		for (SysEnterpriseInfo sysEnterpriseInfo : EnterpriseInfoList) {
			sysEnterpriseInfo.setName(sysEnterpriseInfo.getCode()+"__"+sysEnterpriseInfo.getName());
		}
		model.addAttribute("EnterpriseInfoList", EnterpriseInfoList);
		return "modules/product/bsManageProductForm";
	}

	@RequiresPermissions("product:bsManageProduct:edit")
	@RequestMapping(value = "save")
	public String save(BsManageProduct bsManageProduct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsManageProduct)){
			return form(bsManageProduct, model);
		}
		BsManageProduct product = new BsManageProduct();
		product.setBatchno(bsManageProduct.getBatchno());
		product.setVaccineid(bsManageProduct.getVaccineid());
		if(bsManageProductService.findList(product).size() > 0 && StringUtils.isBlank(bsManageProduct.getId())){
			List<BsManageVaccine> vacclist = bsManageVaccineService.findList(new BsManageVaccine());
			model.addAttribute("vacclist", vacclist);
			List<SysEnterpriseInfo> EnterpriseInfoList = sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
			model.addAttribute("EnterpriseInfoList", EnterpriseInfoList);
			addMessage(model, "疫苗名称和批号已存在");
			return "modules/product/bsManageProductForm";	
		}
		//获取疫苗的小类名称以及疫苗的英文名称
		BsManageVaccine P = bsManageVaccineService.get(bsManageProduct.getVaccineid());
		if(BsManageProduct.TYPE1.equals(bsManageProduct.getIsforeign())){//如果是一类疫苗  则成本价为0
			bsManageProduct.setCostprice(0d);
		}
		bsManageProduct.setVaccName(P.getName());
		bsManageProduct.setCodeall(P.getCodeAll());
		SysEnterpriseInfo infor = sysEnterpriseInfoService.get(bsManageProduct.getManufacturer());
		bsManageProduct.setManufacturer(infor.getName());
		bsManageProduct.setCode(infor.getCode());
		bsManageProductService.save(bsManageProduct);
		addMessage(redirectAttributes, "保存信息成功");
		bsManageProduct = new BsManageProduct();
		model.addAttribute(bsManageProduct);
		model.addAttribute("isok", "ok");
		return "modules/product/bsManageProductForm";
	}
	
	@RequiresPermissions("product:bsManageProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(BsManageProduct bsManageProduct, RedirectAttributes redirectAttributes) {
		bsManageProductService.delete(bsManageProduct);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:"+Global.getAdminPath()+"/product/bsManageProduct/?repage";
	}
	
	/**
	 * 库存统计
	 * @author wangdedi
	 * @date 2017年4月22日 下午2:33:35
	 * @description 
	 *		
	 * @param bsManageProduct
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping(value = "repertorySum")
	public String repertorySum(BsManageProduct bsManageProduct,Model model, RedirectAttributes redirectAttributes) {
		//查询所有的大类
		List<BsManageVaccine> bsmanagevaccineList = bsManageVaccineService.findGroupList();
		model.addAttribute("bsmanagevaccineList", bsmanagevaccineList);
		//查询所有的小类
		List<BsManageVaccine> bsManageVaccineList = bsManageVaccineService.repertorySum();
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);
		//查询所有的批次
		List<BsManageProduct> batchList = bsManageProductService.batch();
		model.addAttribute("batchList", batchList);
		List<RepertorySum>	bsmanageproductlist = null;
		if(StringUtils.isNoneBlank(bsManageProduct.getVaccineid())||StringUtils.isNoneBlank(bsManageProduct.getBigcode())||StringUtils.isNoneBlank(bsManageProduct.getBatchno())){
			bsmanageproductlist = bsManageProductService.repertorySum(bsManageProduct);
		}else{
			bsmanageproductlist = bsManageProductService.repertorySum();
		}
		model.addAttribute("bsmanageproductlist", bsmanageproductlist);
		return "modules/product/repertorySum";
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 下午2:48:50
	 * @description 
	 *		库存统计导出
	 * @param bsManageProduct
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 *
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(BsManageProduct bsManageProduct,RedirectAttributes redirectAttributes,
		HttpServletRequest request, HttpServletResponse response) throws ParseException {
		List<RepertorySum> bsmanageproductlist = null;
		if(StringUtils.isNoneBlank(bsManageProduct.getVaccineid())||StringUtils.isNoneBlank(bsManageProduct.getBigcode())||StringUtils.isNoneBlank(bsManageProduct.getBatchno())){
			bsmanageproductlist = bsManageProductService.repertorySum(bsManageProduct);
		}else{
			bsmanageproductlist = bsManageProductService.repertorySum();
		}
		
		try {
			String fileName = "库存统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			new ExportExcel("库存统计", RepertorySum.class).setDataList(bsmanageproductlist).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出库存失败！失败信息：" + e.getMessage());
		}
		return "modules/product/repertorySum";
	}
	
	@RequestMapping("/getPrice")
	public @ResponseBody BsManageProduct getPrice(BsManageProduct bsManageProduct){
		if(!StringUtils.isNotBlank(bsManageProduct.getBatchno()) || !StringUtils.isNotBlank(bsManageProduct.getVaccineid())){
			return new BsManageProduct();
		}
		bsManageProduct.setShowAll(BsManageProduct.SHOWALL_YES);
		List<BsManageProduct> list = bsManageProductService.findQueueViewListApi(bsManageProduct);
		if(list.size() > 0){
			return list.get(0);
		}
		return new BsManageProduct();
	}
	
	/**
	 * 盘点
	 * @author fuxin
	 * @date 2017年7月26日 下午5:03:52
	 * @description 
	 *		TODO
	 * @param record
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "/checkStock")
	public String checkStock(ChildVaccinaterecord record, Model model,HttpServletRequest request, HttpServletResponse response) {
		if(null == record){
			record = new ChildVaccinaterecord();
		}
		//查询医生信息
		List<User> doctorlist = userDao.findList(new User());
		model.addAttribute("doctorlist", doctorlist);
		
		if(record.getDoctor() == null){
			record.setDoctor(UserUtils.getUser().getName());
		}
		
		//获取所以小类信息
		BsManageVaccine vac = new BsManageVaccine();
		//设置为儿童疫苗，排除成人
		vac.setVacctype("1");
		List<BsManageVaccine> vaccinates = bsManageVaccineService.findList(vac);
		String vacc = "";
		//设置默认值
		if(StringUtils.isNotBlank(record.getVacselected())){
			vacc = record.getVacselected();
		}else{
			vacc = officeService.getOfficeVaccines();
			record.setVacselected(vacc);
		}
		if(StringUtils.isNotBlank(vacc)){
			record.setVacselected("'" + record.getVacselected().replaceAll(",", "','") + "'");
		}
		model.addAttribute("vacc", vacc);
		List<BsManageProduct> products = bsManageProductService.findUseCount(record);
		model.addAttribute("products", products);
		model.addAttribute("record", record);
		
		//默认勾选
		String[] vaccs;
		List<BsManageVaccine> ordervacc = new ArrayList<>();
		if(StringUtils.isNotBlank(vacc) && (vaccs = vacc.split(",")).length > 0){
			for(int i = 0; i < vaccs.length; i ++){
				for(int j = 0; j < vaccinates.size(); j++){
					if(vaccs[i].equals(vaccinates.get(j).getmNum()) || vaccs[i].equals(vaccinates.get(j).getId())){
						ordervacc.add(vaccinates.get(j));
						vaccinates.remove(j);
						j--;
					}
				}
			}
			vaccinates.addAll(0, ordervacc);
		}
		model.addAttribute("vacclist", vaccinates);
		
		//基础表结束
		model.addAttribute("sysdata",DateUtils.formatDate(new Date()));
		return "modules/product/checkStock";
	}
	
	@RequestMapping("/saveCheckStock")
	@Transactional(readOnly = false)
	public @ResponseBody Map<String, Object> saveCheckStock(@RequestParam(value="data", required=false) String json){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
//			Map<String, Object> productMap = new HashMap<String, Object>();
			List<Map<String, Object>> tt = new ArrayList<Map<String,Object>>();
			/*json = StringEscapeUtils.unescapeHtml4(json);
			List<Map<String, Object>> data = (List<Map<String, Object>>) JsonMapper.fromJsonString(json, List.class);
			for(Map<String, Object> m : data){
				BsManageProduct p = bsManageProductService.get(m.get("productId").toString()); 
				Date oldDate = p.getOldStoredate();
				Long num = new Long(m.get("num").toString());
				Long old = p.getOldStorenum();
				if(null == oldDate || !DateUtils.formatDate(new Date()).equals(DateUtils.formatDate(oldDate)) || null == old){
					old = 0l;
				}
				//更新盘点时间
				p.setOldStoredate(new Date());
				//更新盘点库存
				p.setOldStorenum(num);
				//更新库存
				p.setStorenum(p.getStorenum() - old + num);
				bsManageProductService.save(p);
				productMap.put("pid", p.getId());
				productMap.put("storenum", p.getStorenum());
				tt.add(productMap);
				productMap = new HashMap<String, Object>();
			}*/
			returnMap.put("products", tt);
			returnMap.put("code", "200");
		} catch (Exception e) {
			logger.error("库存盘点失败",e);
			returnMap.put("code", "500");
			returnMap.put("msg", "库存盘点失败");
		}
		return returnMap;
	}

	/**
	 * 添加token验证后的市平台入库处理
	 * @author Jack
	 * @date 2017年9月28日 下午3:09:06
	 * @description 
	 * 1、token校验通过返回当前页面
	 * 2、校验失败提示访问权限异常
	 * 3、当前默认slaver系统中只存入一条token记录
	 * @param bsManageProduct
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws UnknownHostException 获取本地IP异常
	 *
	 */
	@RequestMapping(value = "/store")
	public String store(Model model, @RequestParam(value = "option", required = false, defaultValue = "") String option){
		//获取token
		String localToken = null;
		//主动刷新，刷新token数据
		if(StringUtils.isNotBlank(option) && STORE_OPTION_RELOAD.equals(option)){
			localToken = TokenUtils.genToken(true);
		}else{
			localToken = TokenUtils.genToken();
		}
		//验证token是否获取成功
		if(StringUtils.isEmpty(localToken)){
			addMessage(model, "查询失败，请重新加载");
			return "modules/product/bsManageProductStoreList"; 
		}
		model.addAttribute("childcodePre", OfficeService.getFirstOfficeCode());
		model.addAttribute("storeup", Global.getConfig("hbepi.url") + "/f/bsOutAndIn/");
		model.addAttribute("token", localToken);
		model.addAttribute("appid", TokenUtils.APPID);
		model.addAttribute("offices", JsonMapper.toJsonString(UserUtils.getOfficeList()));
		model.addAttribute("vaccModel", JsonMapper.toJsonString(bsManageVaccineService.findList(new BsManageVaccine())));
		return "modules/product/bsManageProductStoreList"; 
	}
	
	@RequestMapping("/findListApi")
	@ResponseBody
	List<BsManageProduct> findListApi(BsManageProduct bsManageProduct){
		return bsManageProductService.findList(bsManageProduct);
	}
	
	/**
	 * 库存合并后，列表查询
	 * @author fuxin
	 * @date 2017年9月14日 上午2:20:42
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	@RequestMapping("/findViewListApi")
	@ResponseBody
	List<BsManageProduct> findViewListApi(BsManageProduct bsManageProduct){
		return bsManageProductService.findViewList(bsManageProduct);
	}
	
	/**
	 * 查询参与排号的所有疫苗
	 * @author fuxin
	 * @date 2017年11月29日 下午11:10:44
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	@RequestMapping("/findQueueViewListApi")
	@ResponseBody
	List<BsManageProduct> findQueueViewListApi(BsManageProduct bsManageProduct){
		
		//TODO:TEMP 乙肝只用免费
		List<BsManageProduct> list = bsManageProductService.findQueueViewListApi(bsManageProduct);
		/*for(int i = 0; i < list.size(); i ++){
			if(list.get(i).getVaccineid().substring(0, 2).equals("02") && list.get(i).getSellprice() > 0){
				list.remove(i --);
			}
		}*/
		//TODO:END TEMP 乙肝只用免费
		
		return list;
	}
	
	@RequestMapping("/findAllBatchApi")
	@ResponseBody
	List<BsManageProduct> findAllBatchApi(BsManageProduct bsManageProduct){
		return bsManageProductService.findAllBatch(bsManageProduct);
	}
	
	/**
	 * 
	 * @author Jack
	 * @date 2017年9月13日 上午9:06:47
	 * @description 
	 *		绑定page等信息
	 * @param bsManageProduct
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 */
	@RequiresPermissions("product:bsManageProduct:view")
	@RequestMapping(value = "/bsProduct")
	public String checklist(BsManageProduct bsManageProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BsManageProduct> page = bsManageProductService.findPage(new Page<BsManageProduct>(request, response), bsManageProduct); 
		model.addAttribute("page", page);
		List<BsManageProduct>BsManageProductInfoList=	bsManageProductService.findList(new BsManageProduct());
		for (BsManageProduct bsManageProductInfo : BsManageProductInfoList) {
			bsManageProductInfo.setName(bsManageProductInfo.getCode()+"__"+bsManageProductInfo.getName());
		}
		model.addAttribute("BsProductList", BsManageProductInfoList);
		return "modules/product/bsManageProductCheckList";
	}
	
	/**
	 * 
	 * @author Jack
	 * @date 2017年9月13日 上午9:06:28
	 * @description 
	 *		盘点逻辑
	 * @param json
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@RequestMapping(value = "/saveCheck", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveCheck(@RequestParam(value="list", required=false) String json){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			json = StringEscapeUtils.unescapeHtml4(json);
			List<Map<String, Object>> data = (List<Map<String, Object>>) JsonMapper.fromJsonString(json, List.class);
				//生成盘点编号
			   String code =DateUtils.formatDate(new Date(), "yyyyMMdd") + IdGen.randomLong(2);
				for(Map<String, Object> m : data){
					//获取疫苗产品
					BsManageProduct product = bsManageProductService.get((String) m.get("id"));
					BsCheckRecord bsCheckRecord = new BsCheckRecord();
					//计算并保存
					bsCheckRecord.setCheckBefore(product.getStorenum());
					product.setStorenum(Long.valueOf((String) m.get("num")));
					bsManageProductService.save(product);
					
					//保存盘点记录
					bsCheckRecord.setCode(code);
					bsCheckRecord.setCheckAfter(product.getStorenum());
					bsCheckRecord.setProductId(product.getId());
					bsCheckRecord.setRemarks((String) m.get("remarks"));
					bsCheckRecord.setStatus(BsCheckRecord.STATUS_NORMAL);
					int checkAfter = bsCheckRecord.getCheckAfter().intValue();
					int checkBefore = bsCheckRecord.getCheckBefore().intValue();
					int checkNum = checkAfter-checkBefore;
					bsCheckRecord.setChecknum(String.valueOf(checkNum));
					bsCheckRecordService.save(bsCheckRecord);
				}
				returnMap.put("code", "200");
				returnMap.put("message", "库存盘点成功");
				returnMap.put("list", JsonMapper.toJsonString(data));
		} catch (Exception e) {
			logger.error("库存盘点失败",e);
			returnMap.put("code", "500");
			returnMap.put("message", "库存盘点失败");
		}
		return returnMap;
	}
	
	/**
	 * 库存盘点
	 * @author Jack
	 * @date 2017年9月12日 下午4:50:14
	 * @description 
	 *		库存查询，对零库存维护
	 * @param bsProduct
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/check")
	public String check(BsManageProduct bsManageProduct, Model model){
		//是否包含0库存，int默认是0 ，未勾选0  已勾选1
		//但check0是2的时候，库存必须大于0
		if(bsManageProduct.getCheck0() != 1){
			bsManageProduct.setStorenumIsNull(true);
//			bsManageProduct.setCheck0(2);
		}
		bsManageProduct.setOrderBy("a.vaccineid, a.create_date");
		//查询所有库存
		List<BsManageProduct> list = bsManageProductService.findList(bsManageProduct);
		model.addAttribute("bsProductList", list);
		BsManageVaccine tempVacc = new BsManageVaccine();
		tempVacc.setVacctype("1");
		List<BsManageProduct> products = bsManageProductService.findVaccinateAble(new BsManageProduct(), bsManageProduct.getShowAll());
		model.addAttribute("products", products);
		return "modules/product/bsManageProductCheckList";
	}
	
	/**
	 * 
	 * @author Jack
	 * @date 2017年9月12日 下午7:09:40
	 * @description 
	 *		查询大的分类下的小生产厂商
	 * @param vaccid
	 * @return
	 *
	 */
	@RequestMapping("/findManufacturer")
	public @ResponseBody List<BsManageProduct> findManufacturer(
			@RequestParam(value = "vaccineid", required = false, defaultValue = "")String vaccineid){
		List<BsManageProduct> list = new ArrayList<BsManageProduct>();
		if(StringUtils.isNotBlank(vaccineid)){
			BsManageProduct tempProduct = new BsManageProduct();
			tempProduct.setVaccineid(vaccineid);
			list = bsManageProductService.findList(tempProduct);	
		}else{
			List<SysEnterpriseInfo> listcom = sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
			BsManageProduct tempInfo;
			for(SysEnterpriseInfo com : listcom ){
				tempInfo = new BsManageProduct();
				tempInfo.setManufacturer(com.getName());
				tempInfo.setCode(com.getCode());
				list.add(tempInfo); 
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@RequestMapping(value = "/stockInFromEpi", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> stockInFromEpi(@RequestParam(value="list", required=false) String json){
		logger.info("------stockInFromEpi:开始注入本地库存----------");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Date now = new Date();
		try {
			json = StringEscapeUtils.unescapeHtml4(json);
			List<Map<String, Object>> data = (List<Map<String, Object>>) JsonMapper.fromJsonString(json, List.class);
			for(Map<String, Object> m : data){
				BsProductDO productDO = (BsProductDO) JsonMapper.fromJsonString(JsonMapper.toJsonString(m), BsProductDO.class);
				if(productDO != null){
					manageStockInService.stockInFromEpi(productDO,now);
				}
			}
			logger.info("------stockInFromEpi:注入本地库存成功----------");
			returnMap.put("code", "200");
			returnMap.put("message", "入库成功");
			returnMap.put("list", JsonMapper.toJsonString(data));
		} catch (Exception e) {
			logger.error("------stockInFromEpi:注入本地库存失败----------",e);
			returnMap.put("code", "500");
			returnMap.put("message", "入库失败");
		}
		logger.info("------stockInFromEpi:结束注入本地库存----------");
		return returnMap;
	}
	
	/**
	 * 库存合并后，列表查询
	 * @author zhouqj
	 * @date 2017年9月14日 上午2:20:42
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	@RequestMapping("/findViewListApiNo")
	@ResponseBody
	List<BsManageProduct> findViewListApiNo(BsManageProduct bsManageProduct){
		return bsManageProductService.findViewListNo(bsManageProduct);
	}
	
	/**
	 * 查询同一厂商的其他批号同疫苗
	 * @author zhouqj
	 * @date 2017年12月18日 上午9:59:11
	 * @description 
	 *		TODO
	 * @param bsManageProduct
	 * @return
	 *
	 */
	@RequestMapping("/findViewListApiMan")
	@ResponseBody
	BsManageProduct findViewListApiMan(BsManageProduct bsManageProduct){
		return bsManageProductService.findViewListMan(bsManageProduct);
	}
	
	/**
	 * 统计疫苗使用详情
	 * @author chenming
	 * @date 2017年12月25日 上午9:59:11
	 * @description 
	 *		TODO
	 * @param productUseList
	 * @return
	 *
	 */
	@RequiresPermissions("product:bsManageProduct:view")
	@RequestMapping(value = "/productUseList")
	public String productUseList(ProductUseList productUseList, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 加载所有的疫苗名称
		List<BsManageVaccine> bsManageVaccineList = bsManageVaccineService.findList(new BsManageVaccine());
		model.addAttribute("bsManageVaccineList", bsManageVaccineList);
		// 加载所有的厂商
		List<SysEnterpriseInfo>EnterpriseInfoList = sysEnterpriseInfoService.findList(new SysEnterpriseInfo());
		model.addAttribute("EnterpriseInfoList", EnterpriseInfoList);
		// 获取所有批次
		List<BsManageProduct> batchList = bsManageProductService.batch();
		model.addAttribute("batchList", batchList);
		List<ProductUseList> list = bsManageProductService.productUseList(productUseList);
		model.addAttribute("list", list);
		model.addAttribute("beginCreateDate",productUseList.getBeginCreateDate());
		model.addAttribute("endCreateDate", productUseList.getEndCreateDate());
		return "modules/product/bsManageProductUseList";
	}
	
	/**
	 * 统计疫苗使用详情
	 * @author chenming
	 * @date 2017年12月25日 上午9:59:11
	 * @description 
	 *		TODO
	 * @param productUseList
	 * @return
	 *
	 */
	@RequiresPermissions("product:bsManageProduct:view")
	@RequestMapping(value = "/childVaccinatereCord")
	public String childVaccinatereCord(ProductUseList productUseList, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductUseList> page = new Page<ProductUseList>(request, response);
		productUseList.setPage(page);
		page.setList(bsManageProductService.childVaccinatereCord(productUseList)) ;
		model.addAttribute("beginCreateDate",productUseList.getBeginCreateDate());
		model.addAttribute("endCreateDate", productUseList.getEndCreateDate());
		model.addAttribute("page", page);
		return "modules/product/childVaccinatereCord";
	}
	
}