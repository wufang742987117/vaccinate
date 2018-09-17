/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vaccine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.product.entity.BsManageProduct;
import com.thinkgem.jeesite.modules.product.service.BsManageCheckService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.vaccine.dao.BsManageVaccineDao;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.entity.ImportContext;
import com.thinkgem.jeesite.modules.vaccine.entity.PlanRule;

/**
 * 疫苗信息Service
 * @author wangdedi
 * @version 2017-02-20
 */
@Service
@Transactional(readOnly = true)
public class BsManageVaccineService extends CrudService<BsManageVaccineDao, BsManageVaccine> {

	public BsManageVaccine get(String id) {
		return super.get(id);
	}
	
	/**
	 * 根据id获取疫苗信息，包含模型大类
	 * @author fuxin
	 * @date 2018年2月3日 下午12:32:59
	 * @description 
	 *		TODO
	 * @param id
	 * @param localcode
	 * @return
	 *
	 */
	public BsManageVaccine getWithModel(String id, String localcode) {
		BsManageVaccine temp = new BsManageVaccine();
		temp.setId(id);
		temp.setLocalCode(localcode);
		return dao.getWithModel(temp);
	}
	
	/**
	 * 根据id获取疫苗信息，包含模型大类	
	 * @author fuxin
	 * @date 2018年2月3日 下午12:33:33
	 * @description 
	 *		TODO
	 * @param id
	 * @return
	 *
	 */
	public BsManageVaccine getWithModel(String id) {
		return getWithModel(id, OfficeService.getFirstOfficeCode());
	}
	
	public List<BsManageVaccine> findList(BsManageVaccine bsManageVaccine) {
		return super.findList(bsManageVaccine);
	}
	
	/**
	 * 单表查询
	 * @author fuxin
	 * @date 2018年2月3日 下午2:34:41
	 * @description 
	 *		TODO
	 * @param bsManageVaccine
	 * @return
	 *
	 */
	public List<BsManageVaccine> findListSimple(BsManageVaccine bsManageVaccine) {
		return dao.findListSimple(bsManageVaccine);
	}
	
	public List<BsManageVaccine> findListChild(BsManageVaccine bsManageVaccine) {
		bsManageVaccine.setVacctype("1");
		return super.findList(bsManageVaccine);
	}
	
	public Page<BsManageVaccine> findPage(Page<BsManageVaccine> page, BsManageVaccine bsManageVaccine) {
		return super.findPage(page, bsManageVaccine);
	}
	
	@Transactional(readOnly = false)
	public void save(BsManageVaccine bsManageVaccine) {
		super.save(bsManageVaccine);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsManageVaccine bsManageVaccine) {
		super.delete(bsManageVaccine);
	}

	/**
	 * 获取大类列表
	 * @author fuxin
	 * @date 2017年3月27日 上午11:28:17
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageVaccine> findGroupList() {
		return dao.findGroupList(new BsManageVaccine());
	}
	
	/**
	 * 获取有库存的产品大类
	 * @author fuxin
	 * @date 2017年8月25日 下午2:46:45
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageVaccine> findGroupListAble() {
		return dao.findGroupListAble(new BsManageVaccine());
	}
	
	/**
	 * 获取有库存的产品模型大类
	 * @author fuxin
	 * @date 2017年8月25日 下午2:46:45
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageVaccine> findGroupListAbleModel(String showAll) {
		BsManageProduct product = new BsManageProduct();
		product.setShowAll(showAll);
		return dao.findGroupListAbleModel(product);
	}
	
	/**
	 * 获取有库存的产品模型大类
	 * @author fuxin
	 * @date 2017年8月25日 下午2:46:45
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageVaccine> findGroupListAbleModel() {
		BsManageProduct product = new BsManageProduct();
		product.setShowAll(BsManageProduct.SHOWALL_YES);
		return dao.findGroupListAbleModel(product);
	}

	/**
	 * 获取模型大类列表
	 * @author fuxin
	 * @date 2017年7月20日 上午12:12:05
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public List<BsManageVaccine> findModelList() {
		return dao.findModelList(new BsManageVaccine());
	}

	/**
	 * 根据大类获取有库存的小类信息
	 * @author fuxin
	 * @date 2017年4月21日 下午3:19:20
	 * @description 
	 *		TODO
	 * @param vtemp
	 * @return
	 *
	 */
	public List<BsManageVaccine> findVaccListAble(String group) {
		BsManageVaccine bsvacc = new BsManageVaccine();
		bsvacc.setgNum(group);
		return dao.findVaccListAble(bsvacc);
	}
	
	/**
	 * 
	 * @author wangdedi
	 * @date 2017年4月22日 下午1:56:48
	 * @description 
	 *	查询所有的疫苗小类（去重）
	 * @return
	 *
	 */	
	public List<BsManageVaccine> repertorySum() {
		return dao.repertorySum(new BsManageVaccine());
	}

	/**
	 * 获取疫苗信息，最大针次+其他信息
	 * @author fuxin
	 * @date 2017年5月25日 上午10:49:13
	 * @description 
	 *		TODO
	 * @param gnum
	 * @return
	 *
	 */
	public BsManageVaccine getLastPin(String gnum) {
		BsManageVaccine bsvacc = new BsManageVaccine();
		bsvacc.setgNum(gnum);
		return dao.getLastPin(bsvacc);
	}

	/**
	 * 获取活苗模型大类的集合
	 * @author fuxin
	 * @date 2017年7月29日 下午4:00:39
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String findLiveGnumList() {
		List<String> lives = dao.findLiveGnumList(new BsManageVaccine());
		String livestr = "'";
		for(String l : lives){
			livestr += l +  "','";
		}
		if(livestr.length() > 2){
			livestr = livestr.substring(0,livestr.length() -2);
		}else{
			livestr = null;
		}
		return livestr;
	}

	/**
	 * 获取所有特殊规则
	 * @author fuxin
	 * @date 2017年7月30日 下午5:45:44
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PlanRule> findRules() {
		return findRules(OfficeService.getFirstOfficeCode());
	}
	
	/**
	 * 获取所有特殊规则
	 * @author liyuan
	 * @date 2018年2月26日 上午10:02:01
	 * @description 
	 *		TODO
	 * @param localcode
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PlanRule> findRules(String localcode) {
		BsManageVaccine b= new BsManageVaccine();
		b.setLocalCode(localcode);
		List<String> rulestrs = dao.findRules(b);
		List<PlanRule> rules = new ArrayList<PlanRule>();
		for(String str : rulestrs){
			try {
				if(str.startsWith("[")){
					List<PlanRule> r =  (List<PlanRule>) JsonMapper.fromJsonString(str, List.class); 
					for(int i = 0; i < r.size(); i ++){
						rules.add((PlanRule) JsonMapper.fromJsonString(JsonMapper.toJsonString(r.get(i)), PlanRule.class));
					}
				}else{
					PlanRule r = (PlanRule) JsonMapper.fromJsonString(str, PlanRule.class); 
					rules.add(r);
				}
			} catch (Exception e) {
				logger.error("模型特殊规则反序列化失败,json=" + str,e);
				continue;
			}
		}
		return rules;
	}
	
	/**
	 * 根据大类获取告知书内容
	 * @author fuxin
	 * @date 2017年8月2日 上午1:38:33
	 * @description 
	 *		TODO
	 * @param gnum
	 * @return
	 *
	 */
	public ImportContext getImpartByGnum(String gnum) {
		if(StringUtils.isBlank(gnum)){
			return new ImportContext();
		}
		List<String> imparts = dao.getImpartByGnum(new BsManageVaccine());
		for(String im : imparts){
			if(StringUtils.isBlank(im)){
				continue;
			}
			if(im.indexOf("\"" + gnum +"\"") > -1){
				ImportContext ic = (ImportContext) JsonMapper.fromJsonString(im, ImportContext.class);
				if(null != ic &&gnum.equals(ic.getGroup())){
					ic.setChoose(StringEscapeUtils.escapeHtml4(ic.getChoose()));
					return ic;
				}
			}
		}
		return new ImportContext();
	}
	
	/**
	 * 根据小类id获取告知书内容，若没有则根据大类查找
	 * @author fuxin
	 * @date 2017年8月2日 上午1:38:33
	 * @description 
	 *		TODO
	 * @param gnum
	 * @return
	 *
	 */
	public ImportContext getImpartByVaccinateId(String id) {
		if(StringUtils.isBlank(id)){
			return new ImportContext();
		}
		List<String> imparts = dao.getImpartByGnum(new BsManageVaccine());
		List<ImportContext> ims = new ArrayList<ImportContext>();
		for(String im : imparts){
			if(StringUtils.isBlank(im)){
				continue;
			}
			ImportContext ic = (ImportContext) JsonMapper.fromJsonString(im, ImportContext.class);
			ic.setChoose(StringEscapeUtils.escapeHtml4(ic.getChoose()));
			if(id.equals(ic.getGroup())){
				return ic;
			}
			ims.add(ic);
		}
		for(ImportContext ic : ims){
			if(id.substring(0,2).equals(ic.getGroup())){
				return ic;
			}
		}		
		return new ImportContext();
	}
	
	/**
	 * 根据模型大类查询所有小类
	 * @author fuxin
	 * @date 2017年10月4日 下午2:55:04
	 * @description 
	 *		TODO
	 * @param vacc
	 * @return
	 *
	 */
	public List<BsManageVaccine> findByGroup(String vacc){
		List<BsManageVaccine> returnList = new ArrayList<BsManageVaccine>();
		if(StringUtils.isNotBlank(vacc)){
			vacc  = "'" + vacc.replaceAll(",", "','") + "'";
			BsManageVaccine tempVac = new BsManageVaccine();
			tempVac.setInGroups(vacc);
			returnList = findList(tempVac);
		}
		
		return returnList;
	}

	/**
	 * 保存疫苗模型信息
	 * @author fuxin
	 * @date 2017年11月15日 下午4:09:30
	 * @description 
	 *		TODO
	 * @param bsManageVaccine
	 *
	 */
	@Transactional(readOnly=false)
	public void saveModel(BsManageVaccine bsManageVaccine) {
		bsManageVaccine.setExcep(BsManageVaccine.EXCEP_YES);
		bsManageVaccine.setUpdateDate(new Date());
		bsManageVaccine.setBan(bsManageVaccine.getId().substring(0,2));
		bsManageVaccine.setgNum(bsManageVaccine.getId().substring(0,2));
		bsManageVaccine.setgCode(bsManageVaccine.getCodeAll());
		bsManageVaccine.setStatus(BsManageVaccine.STATUS_NORMAL);
		if(StringUtils.isBlank(bsManageVaccine.getmNum())){
			bsManageVaccine.setmNum(bsManageVaccine.getId().substring(0,2));
		}
		dao.insert(bsManageVaccine);
		dao.saveModel(bsManageVaccine);
		
	}
	
	/**
	 * 根据条件筛选所有符合条件的逾期未种儿童个案
	 * @author Jack
	 * @date 2018年2月6日 下午3:07:22
	 * @description 
	 * @param endSearchMonth 结束查询时间,EXP_ROUTINEVACC6_1DETAIL 表中YEAR_MONTH字段
	 * @param officeCode 管理单位编码
	 * @param birthbegin 查询儿童生日开始时间
	 * @param birthend 查询儿童生日结束时间
	 * @param residesStr 居住属性
	 * @param situationStr 在册情况
	 * @param areaStr 社区的编码
	 * @param vaccNumStr 查询的疫苗大类  + 针次编码
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> getYQWZChildBaseInfo(Map<String, Object> searchMap,Page<HashMap<String, Object>> page){
		return dao.getYQWZChildBaseInfo(searchMap, page);
	}
	/**
	 * 根据条件筛选所有符合条件的逾期未种儿童个案 排除近期已接种的儿童个案
	 * @author Jack
	 * @date 2018年2月6日 下午9:22:04
	 * @description 
	 * @param endSearchMonth 结束查询时间,EXP_ROUTINEVACC6_1DETAIL 表中YEAR_MONTH字段
	 * @param officeCode 管理单位编码
	 * @param birthbegin 查询儿童生日开始时间
	 * @param birthend 查询儿童生日结束时间
	 * @param residesStr 居住属性
	 * @param situationStr 在册情况
	 * @param areaStr 社区的编码
	 * @param vaccNumStr 查询的疫苗大类  + 针次编码
	 * @param page
	 * @return
	 *
	 */
	public List<HashMap<String, Object>> getYQWZChildBaseInfoReal(Map<String, Object> searchMap,Page<HashMap<String, Object>> page){
		return dao.getYQWZChildBaseInfoReal(searchMap, page);
	}
	
	/**
	 * 导出逾期未种数据
	 * @author Jack
	 * @date 2018年2月27日 下午3:52:22
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
	 * 根据条件筛选所有符合条件的逾期未种儿童个案总数
	 * @author Jack
	 * @date 2018年2月6日 下午6:54:32
	 * @description 
	 * @param endSearchMonth
	 * @param officeCode
	 * @param birthDayStart
	 * @param birthDayEnd
	 * @param residesStr
	 * @param situationStr
	 * @param areaArr
	 * @param vaccNumArr
	 * @return
	 *
	 */
	public int getYQWZChildBaseInfoCount(Map<String, Object> searchMap){
		return dao.getYQWZChildBaseInfoCount(searchMap);
	}
	/**
	 * 统计所有排除近期接种的符合筛选条件的记录总数
	 * @author Jack
	 * @date 2018年2月7日 下午10:28:48
	 * @description 
	 * @param endSearchMonth
	 * @param officeCode
	 * @param birthDayStart
	 * @param birthDayEnd
	 * @param residesStr
	 * @param situationStr
	 * @param areaArr
	 * @param vaccNumArr
	 * @return
	 *
	 */
	public int getYQWZChildBaseInfoRealCount(Map<String, Object> searchMap){
		return dao.getYQWZChildBaseInfoRealCount(searchMap);
	}

	
}