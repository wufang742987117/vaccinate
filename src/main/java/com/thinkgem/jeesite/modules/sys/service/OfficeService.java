/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.OfficePreference;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		UserUtils.getOfficeList();
	}
	
	
	/**
	 * 获取用户所在科室的编号(eg. A B C D)
	 * @author fuxin
	 * @date 2017年2月20日 下午6:48:38
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String getOfficeCode(){
		for(Office off : UserUtils.getOfficeList()){
			if(off.getId().equals(UserUtils.getUser().getOffice().getId())){
				return off.getCode();
			}
		}
		return "";
	}
	
	/**
	 * 获取当前科室
	 * @author fuxin
	 * @date 2017年8月8日 下午2:23:11
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public Office getCurrentOffice(){
		 List<Office> list = UserUtils.getOfficeList();
		 for(Office off : list){
				if(off.getId().equals(UserUtils.getUser().getOffice().getId())){
					return off;
				}
			}
		 return UserUtils.getUser().getOffice();
	}
	
	
	/**
	 * 获取用户所在科室可以接种的疫苗(eg. 1,2,3,4)
	 * @author fuxin
	 * @date 2017年2月20日 下午6:48:38
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public String getOfficeVaccines(){
		for(Office off : UserUtils.getOfficeList()){
			if(off.getId().equals(UserUtils.getUser().getOffice().getId())){
				return off.getVaccines();
			}
		}
		return "";
	}

	
	/**
	 * 根据疫苗id分配科室code
	 * @author fuxin
	 * @date 2017年2月24日 上午11:58:36
	 * @description 
	 *		TODO Deprecated on 2017年8月21日 下午9:28:15
	 * @param vaccineid
	 * @return
	 *
	 */
	@Deprecated
	public String getOfficeCodeByVaccined(String vaccineid) {
		if(StringUtils.isNotBlank(vaccineid)){
			for(Office off : UserUtils.getOfficeList()){
				if(StringUtils.isNoneBlank(off.getVaccines())){
					String[] strs = off.getVaccines().split(",");
					for(String v : strs){
						if(vaccineid.trim().equals(v.trim())){
							return off.getCode();
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据疫苗id分配科室code 多个
	 * @author fuxin
	 * @date 2017年8月21日 下午9:28:15
	 * @description 
	 *		TODO
	 * @param vaccineid
	 * @return
	 *
	 */
	@Deprecated
	public List<String> getAllOfficeCodeByVaccined(String vaccineid) {
		List<String> offices = new ArrayList<String>();
		if(StringUtils.isNotBlank(vaccineid)){
			for(Office off : UserUtils.getOfficeList()){
				if(StringUtils.isNoneBlank(off.getVaccines())){
					String[] strs = off.getVaccines().split(",");
					for(String v : strs){
						if(vaccineid.trim().equals(v.trim())){
							offices.add(off.getCode());
							break;
						}
					}
				}
			}
		}
		return offices;
	}

	/**
	 * 根据疫苗小类找科室，找不到根据大类找
	 * @author fuxin
	 * @date 2018年1月7日 下午11:42:04
	 * @description 
	 *		TODO
	 * @param group
	 * @param vaccineid
	 * @return
	 *
	 */
	public List<String> getAllOfficeCodeByVaccined(String group, String vaccineid) {
		List<String> offices = new ArrayList<String>();
		List<String> officesGroup = new ArrayList<String>();
		if(StringUtils.isNotBlank(vaccineid)){
			for(Office off : UserUtils.getOfficeList()){
				if(StringUtils.isNoneBlank(off.getVaccines())){
					String[] strs = off.getVaccines().split(",");
					for(String v : strs){
						if(vaccineid.trim().equals(v.trim())){
							offices.add(off.getCode());
						}
						if(group.trim().equals(v.trim())){
							officesGroup.add(off.getCode());
						}
					}
				}
			}
		}
		if(offices.size() > 0){
			return offices;
		}
		return officesGroup;
	}
	/**
	 * 
	 * @author liyuan
	 * @date 2018年2月9日 下午1:43:09
	 * @description 
	 *		TODO
	 * @param group
	 * @param vaccineid
	 * @param localcode
	 * @return
	 *
	 */
	public List<String> getAllOfficeCodeByVaccined(String group, String vaccineid,String localcode) {
		List<String> offices = new ArrayList<String>();
		List<String> officesGroup = new ArrayList<String>();
		if(StringUtils.isNotBlank(vaccineid)){
			for(Office off : dao.getAllOfficeByCode(localcode)){
				if(StringUtils.isNoneBlank(off.getVaccines())){
					String[] strs = off.getVaccines().split(",");
					for(String v : strs){
						if(vaccineid.trim().equals(v.trim())){
							offices.add(off.getCode());
						}
						if(group.trim().equals(v.trim())){
							officesGroup.add(off.getCode());
						}
					}
				}
			}
		}
		if(offices.size() > 0){
			return offices;
		}
		return officesGroup;
	}

	
	/**
	 * 获取一级单位
	 * @author fuxin
	 * @date 2017年11月23日 下午22:45:49
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public static Office getFirstOffice() {
		return UserUtils.getUser().getCompany();
	}
	
	/**
	 * 获取一级单位编码
	 * @author fuxin
	 * @date 2017年11月23日 下午22:45:49
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public static String getFirstOfficeCode() {
		String local = null;
		try {
			if(UserUtils.getUser() == null || UserUtils.getUser().getCompany() == null || StringUtils.isBlank(UserUtils.getUser().getCompany().getCode()) ){
//				local = Global.getConfig("childcodePre");
				throw new RuntimeException("获取不到localcode");
			}else{
				local = UserUtils.getUser().getCompany().getCode();
			}
		} catch (Exception e) {
//			local = Global.getConfig("childcodePre");
			e.printStackTrace();
			throw new RuntimeException("获取不到localcode");
		}
		return local;
	}

	/**
	 * 获取所有不是一级的科室信息
	 * @author wangdedi
	 * @date 2017年4月7日 下午1:54:03
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public  List<Office> getAbleOffice() {
		List<Office> returnList = new ArrayList<>();
		for(Office off : UserUtils.getOfficeList()){
			if("2".equals(off.getGrade())){
				returnList.add(off);
			}
		}
		return returnList;
	}
	
	/**
	 * 获取站点首选项
	 * @author fuxin
	 * @date 2017年8月18日 下午3:56:03
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public static OfficePreference getOfficeOption(){
		//获取科室首选项
		OfficePreference op = null;
		try {
			op = (OfficePreference) JsonMapper.fromJsonString(UserUtils.getUser().getCompany().getPreference(), OfficePreference.class);
			if(op == null){
				op = new OfficePreference();
			}
			op.setId(UserUtils.getUser().getCompany().getId());
		} catch (Exception e) {
			op = new OfficePreference();
		}
		return op;
	}
	
	/**
	 * 获取站点首选项
	 * @author fuxin
	 * @date 2017年8月18日 下午3:56:03
	 * @description 
	 \*		
	 * @return
	 *
	 */
	public OfficePreference getOfficeOption(String localcode){
		//获取科室首选项
		OfficePreference op = null;
		try {
			Office off = dao.getByCode(localcode);
			op = (OfficePreference) JsonMapper.fromJsonString(off.getPreference(), OfficePreference.class);
			if(op == null){
				op = new OfficePreference();
			}
			op.setId(UserUtils.getUser().getCompany().getId());
		} catch (Exception e) {
			op = new OfficePreference();
		}
		return op;
	}
	
	/**
	 * 更新站点首选项
	 * @author fuxin
	 * @date 2017年12月4日 下午3:30:54
	 * @description 
	 *		TODO
	 * @param pref
	 * @return
	 *
	 */
	public static OfficePreference saveOfficeOption(OfficePreference pref){
		if(pref == null){
			pref = new OfficePreference();
		}
		OfficeDao dao = SpringContextHolder.getBean(OfficeDao.class);
		String json = JsonMapper.toJsonString(pref);
		dao.savePreference(pref.getId(),json);
		UserUtils.getUser().getCompany().setPreference(json);
		return pref;
	}

	/**
	 * 根据code查询id
	 * @author zhouqj
	 * @date 2017年12月15日 下午2:42:22
	 * @description 
	 *		TODO
	 * @param localCode
	 * @return
	 *
	 */
	public Office getOfficeByCode(String code) {
		return dao.getOfficeByCode(code);
	}

	/**
	 * 根据parent_id查询子节点
	 * @author zhouqj
	 * @date 2017年12月15日 下午2:55:09
	 * @description 
	 *		TODO
	 * @param companyOffice
	 * @return
	 *
	 */
	public Office getOfficeByParentId(Office companyOffice) {
		return dao.getOfficeByParentId(companyOffice);
	}
	/**
	 * 
	 * @author liyuan
	 * @date 2018年1月27日 下午3:42:16
	 * @description 
	 *		用户输入用户名后，根据用户名查到用的所在的部门和部门id
	 * @param username
	 * @return
	 *
	 */
	public List<Office> getOfficeNameAndCode(String username){
		return dao.getOfficeNameAndCode(username);
	}
}
