package com.thinkgem.jeesite.modules.export.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.export.entity.Exp_vacc_7_2;

@MyBatisDao
public interface Exp_vacc_7_2Dao extends CrudDao<Exp_vacc_7_2>{
	
	
	List<Exp_vacc_7_2> getExp_vacc_7_2Table(Exp_vacc_7_2 exp_vacc_2);
	
	List<Exp_vacc_7_2> getdogalcount(Exp_vacc_7_2 exp_vacc_2);

	List<Exp_vacc_7_2> getdogpaycount(Exp_vacc_7_2 exp_vacc_2);
	
	List<Exp_vacc_7_2> getadultalcount(Exp_vacc_7_2 exp_vacc_2);
	
	List<Exp_vacc_7_2> getadultpaycount(Exp_vacc_7_2 exp_vacc_2);
	
	List<Exp_vacc_7_2> getchildtwocount(Exp_vacc_7_2 exp_vacc_2);

	List<Exp_vacc_7_2> getincomenum(Exp_vacc_7_2 exp_vacc_2);
	
	List<Exp_vacc_7_2> getoutcomenum(Exp_vacc_7_2 exp_vacc_2);
	
	List<Exp_vacc_7_2> getstorenum(Exp_vacc_7_2 exp_vacc_2);
}
