package com.thinkgem.jeesite.modules.export.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.department.entity.SysVaccDepartment;
import com.thinkgem.jeesite.modules.enter.entity.SysEnterpriseInfo;
import com.thinkgem.jeesite.modules.enter.service.SysEnterpriseInfoService;
import com.thinkgem.jeesite.modules.export.dao.Exp_vacc_7_2Dao;
import com.thinkgem.jeesite.modules.export.entity.Exp_vacc_7_2;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vaccine.entity.BsManageVaccine;
import com.thinkgem.jeesite.modules.vaccine.service.BsManageVaccineService;

import freemarker.template.utility.StringUtil;

@Service
@Transactional(readOnly = true)
public class Exp_vacc_7_2Service extends CrudService<Exp_vacc_7_2Dao, Exp_vacc_7_2> {
	@Autowired
	Exp_vacc_7_2Dao dao;
	@Autowired
	OfficeService officeService;
	@Autowired
	SysAreaService sysAreaService;

	public Exp_vacc_7_2 get(String id) {
		return super.get(id);
	}

	public List<Exp_vacc_7_2> findList(Exp_vacc_7_2 exp_vacc_2) {
		return super.findList(exp_vacc_2);
	}

	public Page<Exp_vacc_7_2> findPage(Page<Exp_vacc_7_2> page, Exp_vacc_7_2 exp_vacc_2) {
		return super.findPage(page, exp_vacc_2);
	}

	@Transactional(readOnly = false)
	public void save(Exp_vacc_7_2 exp_vacc_2) {
		super.save(exp_vacc_2);
	}

	@Transactional(readOnly = false)
	public void delete(Exp_vacc_7_2 exp_vacc_2) {
		super.delete(exp_vacc_2);
	}

	public void dataService(HttpServletRequest request, Model model, String yearstr, String monthstr) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(yearstr) && StringUtils.isNotBlank(monthstr)) {
			String begintime = new SimpleDateFormat("yyyy-MM-dd ").format(getFirstDayOfMonth(Integer.parseInt(yearstr), Integer.parseInt(monthstr)));
			String endtime = new SimpleDateFormat("yyyy-MM-dd ").format(getLastDayOfMonth(Integer.parseInt(yearstr), Integer.parseInt(monthstr)));
			model.addAttribute("yearstr", yearstr);
			model.addAttribute("monthstr", monthstr);
			model.addAttribute("date", df.format(new Date()));
		} else {
			// 没有时间默认上月
			Integer endMonth = Integer.parseInt(DateUtils.getMonth());
			if (endMonth == 1) {
				yearstr = (Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")) - 1) + "";
				monthstr = "12";
				// export.setBegintime(getFirstDayOfMonth(Integer.valueOf(DateUtils.formatDate(new
				// Date(), "yyyy")) - 1, 12));
				// export.setEndtime(getLastDayOfMonth(Integer.valueOf(DateUtils.formatDate(new
				// Date(), "yyyy")) - 1, 12));
				model.addAttribute("yearstr", Integer.valueOf(DateUtils.formatDate(new Date(), "yyyy")) - 1 + "");
				model.addAttribute("monthstr", "12");
			} else {
				yearstr = DateUtils.formatDate(new Date(), "yyyy");
				monthstr = DateUtils.formatDate(new Date(), "MM");
				// export.setBegintime(getFirstDayOfMonth(Integer.valueOf(DateUtils.formatDate(new
				// Date(), "yyyy")),
				// Integer.valueOf(DateUtils.formatDate(new Date(), "MM")) -
				// 1));
				// export.setEndtime(getLastDayOfMonth(Integer.valueOf(DateUtils.formatDate(new
				// Date(), "yyyy")),
				// Integer.valueOf(DateUtils.formatDate(new Date(), "MM")) -
				// 1));
				model.addAttribute("yearstr", DateUtils.formatDate(new Date(), "yyyy"));
				model.addAttribute("monthstr", endMonth - 1);
			}
			model.addAttribute("date", df.format(new Date()));
		}
		// 根据code 查询所属的省，市 ，区
		String code = OfficeService.getFirstOfficeCode();// 获取一级单位编码 3406030301
		if (StringUtils.isNotBlank(code)) {
			String sheng = StringUtils.substring(code, 0, 2);
			String shi = StringUtils.substring(code, 2, 4);
			String qu = StringUtils.substring(code, 4, 6);
			model.addAttribute("sheng", sysAreaService.getAreaNameById(Integer.parseInt(sheng + "0000")).getName());
			model.addAttribute("shi", sysAreaService.getAreaNameById(Integer.parseInt(sheng + shi + "00")).getName());
			model.addAttribute("qu", sysAreaService.getAreaNameById(Integer.parseInt(sheng + shi + qu)).getName());
		}

		if (StringUtils.isNotBlank(yearstr) && StringUtils.isNotBlank(monthstr)) {
			Exp_vacc_7_2 exp_vacc_2 = new Exp_vacc_7_2();
			exp_vacc_2.setYearmonth(yearstr + StringUtils.leftPad(monthstr, 2, '0'));
			List<Exp_vacc_7_2> exp_vacc_2List = dao.getExp_vacc_7_2Table(exp_vacc_2);
			model.addAttribute("tables", exp_vacc_2List);
		}
		model.addAttribute("username", UserUtils.getUser().getName());
		model.addAttribute("year", DateUtils.formatDate(new Date(), "yyyy"));
		model.addAttribute("month", DateUtils.formatDate(new Date(), "MM"));
		model.addAttribute("date", DateUtils.formatDate(new Date(), "yyyy年MM月dd日"));
		model.addAttribute("off", OfficeService.getFirstOffice().getName());
	}
	
	public static Date getLastDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return cal.getTime();
	}

	public static Date getFirstDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return cal.getTime();
	}
	
	

	public void getMonthData(String yearStr, String monthStr) {
		// 上月预售和库存
		Exp_vacc_7_2 exp_vacc_2last = new Exp_vacc_7_2();
		int y=Integer.parseInt(yearStr);
		int m=Integer.parseInt(monthStr);
		m=m-1;
		if(m<1){
			m=12;
			y-=1;
		}
		exp_vacc_2last.setYearmonth(y+StringUtil.leftPad(m+"", 2));
		List<Exp_vacc_7_2> exp_vacc_2Listlast = dao.getExp_vacc_7_2Table(exp_vacc_2last);

		//// 本月库存
		Exp_vacc_7_2 exp_vacc_2kucun = new Exp_vacc_7_2();
		List<Exp_vacc_7_2> exp_vacc_2Listkc = dao.getstorenum(exp_vacc_2kucun);
		
		// 本月领苗
		Exp_vacc_7_2 exp_vacc_2income = new Exp_vacc_7_2();
		exp_vacc_2income.setYearmonth(yearStr+StringUtils.leftPad(monthStr, 2));
		List<Exp_vacc_7_2> exp_vacc_2Listincome = dao.getincomenum(exp_vacc_2income);

		// 本月损耗
		Exp_vacc_7_2 exp_vacc_2sunhao = new Exp_vacc_7_2();
		exp_vacc_2sunhao.setYearmonth(yearStr+StringUtils.leftPad(monthStr, 2));
		List<Exp_vacc_7_2> exp_vacc_2Listsunhao = dao.getoutcomenum(exp_vacc_2sunhao);

		// 本月接种
		Exp_vacc_7_2 exp_vacc_2 = new Exp_vacc_7_2();
		exp_vacc_2.setYearmonth(yearStr+StringUtils.leftPad(monthStr, 2));
		List<Exp_vacc_7_2> exp_vacc_2Listadultal = dao.getadultalcount(exp_vacc_2);
		List<Exp_vacc_7_2> exp_vacc_2Listadultpay = dao.getadultpaycount(exp_vacc_2);
		List<Exp_vacc_7_2> exp_vacc_2Listchildtwo = dao.getchildtwocount(exp_vacc_2);
		List<Exp_vacc_7_2> exp_vacc_2Listdogal = dao.getdogalcount(exp_vacc_2);
		List<Exp_vacc_7_2> exp_vacc_2Listdogpay = dao.getdogpaycount(exp_vacc_2);

		// 生成上月结余
		List<Exp_vacc_7_2> tmpList = new ArrayList();
		for (Exp_vacc_7_2 exp_vacc_22 : exp_vacc_2Listlast) {
			boolean isnew = true;
			Exp_vacc_7_2 tmp = new Exp_vacc_7_2();
			tmp.setId(IdGen.uuid());
			tmp.setVaccineid(exp_vacc_22.getVaccineid());
			tmp.setBatchno(exp_vacc_22.getBatchno());
			tmp.setManufacturercode(exp_vacc_22.getManufacturercode());
			tmp.setLastwcount(exp_vacc_22.getNwcount());
			tmp.setLastjcount(exp_vacc_22.getNjcount());
			tmp.setCostprice(exp_vacc_22.getCostprice());
			tmp.setSellprice(exp_vacc_22.getSellprice());
			tmp.setYearmonth(yearStr+StringUtils.leftPad(monthStr,2,'0'));
			for (Exp_vacc_7_2 exp_vacc_7_2 : tmpList) {
				if (exp_vacc_7_2.getVaccineid().equals(tmp.getVaccineid())
						&& exp_vacc_7_2.getBatchno().equals(tmp.getBatchno())) {
					exp_vacc_7_2.setLastwcount(exp_vacc_7_2.getLastwcount()+tmp.getLastwcount());
					exp_vacc_7_2.setLastjcount(exp_vacc_7_2.getLastjcount()+tmp.getLastjcount());
					isnew = false;
					break;
				}
			}
			if(isnew){
				tmpList.add(tmp);
			}
		}

		// 生成本月库存
		for (Exp_vacc_7_2 exp_vacc_2kc : exp_vacc_2Listkc) {
			boolean isnew = true;
			for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
				if (exp_vacc_22.getVaccineid().equals(exp_vacc_2kc.getVaccineid())
						&& exp_vacc_22.getBatchno().equals(exp_vacc_2kc.getBatchno())) {
					exp_vacc_22.setNalcount(exp_vacc_2kc.getNalcount());
					isnew = false;
					break;
				}
			}
			if (isnew) {
				Exp_vacc_7_2 tmp = new Exp_vacc_7_2();
				tmp.setId(IdGen.uuid());
				tmp.setVaccineid(exp_vacc_2kc.getVaccineid());
				tmp.setBatchno(exp_vacc_2kc.getBatchno());
				tmp.setManufacturercode(exp_vacc_2kc.getManufacturercode());
				tmp.setNalcount(exp_vacc_2kc.getNalcount());
				tmp.setYearmonth(yearStr+StringUtils.leftPad(monthStr,2,'0'));
				tmp.setCostprice(exp_vacc_2kc.getCostprice());
				tmp.setSellprice(exp_vacc_2kc.getSellprice());
				tmpList.add(tmp);
			}
		}
		
		// 生成领用
		for (Exp_vacc_7_2 exp_vacc_2in : exp_vacc_2Listincome) {
			boolean isnew = true;
			for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
				if (exp_vacc_22.getVaccineid().equals(exp_vacc_2in.getVaccineid())
						&& exp_vacc_22.getBatchno().equals(exp_vacc_2in.getBatchno())) {
					exp_vacc_22.setIncome(exp_vacc_2in.getIncome());
					isnew = false;
					break;
				}
			}
			if (isnew) {
				Exp_vacc_7_2 tmp = new Exp_vacc_7_2();
				tmp.setId(IdGen.uuid());
				tmp.setVaccineid(exp_vacc_2in.getVaccineid());
				tmp.setBatchno(exp_vacc_2in.getBatchno());
				tmp.setManufacturercode(exp_vacc_2in.getManufacturercode());
				tmp.setIncome(exp_vacc_2in.getIncome());
				tmp.setYearmonth(yearStr+StringUtils.leftPad(monthStr,2,'0'));
				tmp.setCostprice(exp_vacc_2in.getCostprice());
				tmp.setSellprice(exp_vacc_2in.getSellprice());
				tmpList.add(tmp);
			}
		}

		// 生成报损
		for (Exp_vacc_7_2 exp_vacc_2out : exp_vacc_2Listsunhao) {
			for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
				if (exp_vacc_22.getVaccineid().equals(exp_vacc_2out.getVaccineid())
						&& exp_vacc_22.getBatchno().equals(exp_vacc_2out.getBatchno())) {
					exp_vacc_22.setBaofei(exp_vacc_2out.getBaofei());
					break;
				}
			}
		}

		// 生成消耗
		for (Exp_vacc_7_2 exp_vacc_2aal : exp_vacc_2Listadultal) {
			for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
				if (exp_vacc_22.getVaccineid().equals(exp_vacc_2aal.getVaccineid())
						&& exp_vacc_22.getBatchno().equals(exp_vacc_2aal.getBatchno())) {
					exp_vacc_22.setVacccount(exp_vacc_2aal.getVacccount());
					break;
				}
			}
		}

		for (Exp_vacc_7_2 exp_vacc_2apay : exp_vacc_2Listadultpay) {
			for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
				if (exp_vacc_22.getVaccineid().equals(exp_vacc_2apay.getVaccineid())
						&& exp_vacc_22.getBatchno().equals(exp_vacc_2apay.getBatchno())) {
					exp_vacc_22.setNwcount(exp_vacc_2apay.getNwcount());
					break;
				}
			}
		}
		
		for (Exp_vacc_7_2 exp_vacc_2dal : exp_vacc_2Listdogal) {
			for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
				if (exp_vacc_22.getVaccineid().equals(exp_vacc_2dal.getVaccineid())
						&& exp_vacc_22.getBatchno().equals(exp_vacc_2dal.getBatchno())) {
					exp_vacc_22.setVacccount(exp_vacc_2dal.getVacccount());
					break;
				}
			}
		}

		for (Exp_vacc_7_2 exp_vacc_2dpay : exp_vacc_2Listdogpay) {
			for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
				if (exp_vacc_22.getVaccineid().equals(exp_vacc_2dpay.getVaccineid())
						&& exp_vacc_22.getBatchno().equals(exp_vacc_2dpay.getBatchno())) {
					exp_vacc_22.setNwcount(exp_vacc_2dpay.getNwcount());
					break;
				}
			}
		}
		
		for (Exp_vacc_7_2 exp_vacc_2ct : exp_vacc_2Listchildtwo) {
			for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
				if (exp_vacc_22.getVaccineid().equals(exp_vacc_2ct.getVaccineid())
						&& exp_vacc_22.getBatchno().equals(exp_vacc_2ct.getBatchno())) {
					exp_vacc_22.setVacccount(exp_vacc_22.getVacccount()+exp_vacc_2ct.getVacccount());
					break;
				}
			}
		}
		
		
		//处理金额
		for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
			exp_vacc_22.setNjcount(exp_vacc_22.getNalcount()-exp_vacc_22.getNwcount());
			exp_vacc_22.setLastmoney(exp_vacc_22.getLastjcount()*exp_vacc_22.getCostprice());
			exp_vacc_22.setIncomemoney(exp_vacc_22.getIncome()*exp_vacc_22.getCostprice());
			exp_vacc_22.setVacccost(exp_vacc_22.getVacccount()*exp_vacc_22.getCostprice());
			exp_vacc_22.setVaccincome(exp_vacc_22.getVacccount()*exp_vacc_22.getSellprice());
			exp_vacc_22.setNmoney(exp_vacc_22.getNjcount()*exp_vacc_22.getCostprice());
			exp_vacc_22.setSellcount(exp_vacc_22.getNwcount()-exp_vacc_22.getLastwcount()+exp_vacc_22.getVacccount());
			exp_vacc_22.setSellcost(exp_vacc_22.getSellcount()*exp_vacc_22.getCostprice());
			exp_vacc_22.setSellincome(exp_vacc_22.getSellcount()*exp_vacc_22.getSellprice());
		}
		
		for (Exp_vacc_7_2 exp_vacc_22 : tmpList) {
			Exp_vacc_7_2 exp_t = new Exp_vacc_7_2();
			exp_t.setVaccineid(exp_vacc_22.getVaccineid());
			exp_t.setBatchno(exp_vacc_22.getBatchno());
			exp_t.setManufacturercode(exp_vacc_22.getManufacturercode());
			exp_t.setYearmonth(exp_vacc_22.getYearmonth());
			List<Exp_vacc_7_2> exptlist=dao.findList(exp_t);
			if(exptlist==null || exptlist.isEmpty()){
				exp_vacc_22.setCreateBy(UserUtils.getUser());
				exp_vacc_22.setCreateDate(new Date());
				dao.insert(exp_vacc_22);				
			}else{
				exp_vacc_22.setId(exptlist.get(0).getId());
				dao.update(exp_vacc_22);
			}
		}
	}

}
