/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.export.entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报表系统——常规统计表6-1Entity
 * @author Jack
 * @version 2017-10-13
 */
public class ExpRoutinevacc6_1 extends DataEntity<ExpRoutinevacc6_1> {
	
	private static final long serialVersionUID = 1L;
//	private String localCode;		//互联网版本标识站点的唯一编码
	private String YEAR_MONTH;		// 查询的年份和条件
	private String UNIT_NAME;		// 报告单位
	private String UNIT_CODE;		// 单位编码
	private String RESIDE;			// 居住属性
	private String DISPLAY;			// 显示类型  常规/特殊
	private String COMMITTEE;		// 居委名称
	private Integer HEPB_A_SH;		// HepB1应种
	private Integer HEPB_A_RE;		// HepB1实种
	private Float HEPB_A_SR;		// HepB1接种率
	private Integer HEPB_AA_SH;		// HepB1及时数应种
	private Integer HEPB_AA_RE;		// HepB1及时数实种
	private Float HEPB_AA_SR;		// HepB1及时数接种率
	private Integer HEPB_B_SH;		// HepB2应种
	private Integer HEPB_B_RE;		// HepB2实种
	private Float HEPB_B_SR;		// HepB2接种率
	private Integer HEPB_C_SH;		// HepB3应种
	private Integer HEPB_C_RE;		// HepB3实种
	private Float HEPB_C_SR;		// HepB3接种率
	private Integer BCG_SH;			// BCG应种
	private Integer BCG_RE;			// BCG实种
	private Float BCG_SR;			// BCG接种率
	private Integer PV_A_SH;		// PV1应种
	private Integer PV_A_RE;		// PV1实种
	private Float PV_A_SR;			// PV1接种率
	private Integer PV_B_SH;		// PV2应种
	private Integer PV_B_RE;		// PV2实种
	private Float PV_B_SR;			// PV2接种率
	private Integer PV_C_SH;		// PV3应种
	private Integer PV_C_RE;		// PV3实种
	private Float PV_C_SR;			// PV3接种率
	private Integer PV_D_SH;		// PV4应种
	private Integer PV_D_RE;		// PV4实种
	private Float PV_D_SR;			// PV4接种率
	private Integer DTP_A_SH;		// DTP1应种
	private Integer DTP_A_RE;		// DTP1实种
	private Float DTP_A_SR;			// DTP1接种率
	private Integer DTP_B_SH;		// DTP2应种
	private Integer DTP_B_RE;		// DTP2实种
	private Float DTP_B_SR;			// DTP2接种率
	private Integer DTP_C_SH;		// DTP3应种
	private Integer DTP_C_RE;		// DTP3实种
	private Float DTP_C_SR;			// DTP3接种率
	private Integer DTP_D_SH;		// DTP4应种
	private Integer DTP_D_RE;		// DTP4实种
	private Float DTP_D_SR;			// DTP4接种率
	private Integer DT_SH;			// DT应种
	private Integer DT_RE;			// DT实种
	private Float DT_SR;			// DT接种率
	private Integer MR_A_SH;		// MR1应种
	private Integer MR_A_RE;		// MR1实种
	private Float MR_A_SR;			// MR1接种率
	private Integer MR_B_SH;		// MR2应种
	private Integer MR_B_RE;		// MR2实种
	private Float MR_B_SR;			// MR2接种率
	private Integer MMR_A_SH;		// MMR1应种
	private Integer MMR_A_RE;		// MMR1实种
	private Float MMR_A_SR;			// MMR1接种率
	private Integer MMR_B_SH;		// MMR2应种
	private Integer MMR_B_RE;		// MMR2实种
	private Float MMR_B_SR;			// MMR2接种率
	private Integer MM_A_SH;		// MM1应种
	private Integer MM_A_RE;		// MM1实种
	private Float MM_A_SR;			// MM1接种率
	private Integer MM_B_SH;		// MM2应种
	private Integer MM_B_RE;		// MM2实种
	private Float MM_B_SR;			// MM2接种率
	private Integer MV_A_SH;		// MV1应种
	private Integer MV_A_RE;		// MV1实种
	private Float MV_A_SR;			// MV1接种率
	private Integer MV_B_SH;		// MV2应种
	private Integer MV_B_RE;		// MV2实种
	private Float MV_B_SR;			// MV2接种率
	private Integer MENA_A_SH;		// MenA1应种
	private Integer MENA_A_RE;		// MenA1实种
	private Float MENA_A_SR;		// MenA1接种率
	private Integer MENA_B_SH;		// MenA2应种
	private Integer MENA_B_RE;		// MenA2实种
	private Float MENA_B_SR;		// MenA2接种率
	private Integer MENAC_A_SH;		// MenAC1应种
	private Integer MENAC_A_RE;		// MenAC1实种
	private Float MENAC_A_SR;		// MenAC1接种率
	private Integer MENAC_B_SH;		// MenAC2应种
	private Integer MENAC_B_RE;		// MenAC2实种
	private Float MENAC_B_SR;		// MenAC2接种率
	private Integer JEL_A_SH;		// JE-l1应种
	private Integer JEL_A_RE;		// JE-l1实种
	private Float JEL_A_SR;			// JE-l1接种率
	private Integer JEL_B_SH;		// JE-l2应种
	private Integer JEL_B_RE;		// JE-l2实种
	private Float JEL_B_SR;			// JE-l2接种率
	private Integer JEI_A_SH;		// JE-i1应种
	private Integer JEI_A_RE;		// JE-i1实种
	private Float JEI_A_SR;			// JE-i1接种率
	private Integer JEI_B_SH;		// JE-i2应种
	private Integer JEI_B_RE;		// JE-i2实种
	private Float JEI_B_SR;			// JE-i2接种率
	private Integer JEI_C_SH;		// JE-i3应种
	private Integer JEI_C_RE;		// JE-i3实种
	private Float JEI_C_SR;			// JE-i3接种率
	private Integer JEI_D_SH;		// JE-i4应种
	private Integer JEI_D_RE;		// JE-i4实种
	private Float JEI_D_SR;			// JE-i4接种率
	private Integer HEPAL_SH;		// Hepa-l应种
	private Integer HEPAL_RE;		// Hepa-l实种
	private Float HEPAL_SR;			// Hepa-l接种率
	private Integer HEPAI_A_SH;		// Hepa-i1应种
	private Integer HEPAI_A_RE;		// Hepa-i1实种
	private Float HEPAI_A_SR;		// Hepa-i1接种率
	private Integer HEPAI_B_SH;		// Hepa-i2应种
	private Integer HEPAI_B_RE;		// Hepa-i2实种
	private Float HEPAI_B_SR;		// Hepa-i2接种率
	private Integer MR_SUM_A_SH;	// MR应种总和
	private Integer MR_SUM_A_RE;	// MR实种总和
	private Float MR_SUM_A_SR;		//MR接种率总和
	private Integer MR_SUM_B_SH;	// MR应种总和
	private Integer MR_SUM_B_RE;	// MR实种总和
	private Float MR_SUM_B_SR;		//MR接种率总和
	private Integer MMR_SUM_A_SH;	// MMR应种总和
	private Integer MMR_SUM_A_RE;	// MMR实种总和
	private Float MMR_SUM_A_SR;		//MMR接种率总和
	private Integer MMR_SUM_B_SH;	// MMR应种总和
	private Integer MMR_SUM_B_RE;	// MMR实种总和
	private Float MMR_SUM_B_SR;		//MMR接种率总和
	private Integer MM_SUM_A_SH;	// MM应种总和
	private Integer MM_SUM_A_RE;	// MM实种总和
	private Float MM_SUM_A_SR;		//MM接种率总和
	private Integer MM_SUM_B_SH;	// MM应种总和
	private Integer MM_SUM_B_RE;	// MM实种总和
	private Float MM_SUM_B_SR;		//MM接种率总和
	private Integer MV_SUM_A_SH;	// MV应种总和
	private Integer MV_SUM_A_RE;	// MV实种总和
	private Float MV_SUM_A_SR;		//MV接种率总和
	private Integer MV_SUM_B_SH;	// MV应种总和
	private Integer MV_SUM_B_RE;	// MV实种总和
	private Float MV_SUM_B_SR;		//MV接种率总和
	private Integer HEPAL_SUM_A_SH;	// HEPAL应种总和
	private Integer HEPAL_SUM_A_RE;	// HEPAL实种总和
	private Float HEPAL_SUM_A_SR;	//HEPAL接种率总和
	private Integer HEPAL_SUM_B_SH;	// HEPAL应种总和
	private Integer HEPAL_SUM_B_RE;	// HEPAL实种总和
	private Float HEPAL_SUM_B_SR;	//HEPAL接种率总和
	private Integer HEPAI_SUM_A_SH;	// HEPAI应种总和
	private Integer HEPAI_SUM_A_RE;	// HEPI实种总和
	private Float HEPAI_SUM_A_SR;	//HEPAI接种率总和
	private Integer HEPAI_SUM_B_SH;	// HEPAI应种总和
	private Integer HEPAI_SUM_B_RE;	// HEPI实种总和
	private Float HEPAI_SUM_B_SR;	//HEPAI接种率总和
	private Integer MCV_SUM_A_SH;	// MCV大类应种总和
	private Integer MCV_SUM_A_RE;	// MCV大类实种总和
	private Float MCV_SUM_A_SR;		//MCV大类接种率总和
	private Integer MCV_SUM_B_SH;	// MCV大类应种总和
	private Integer MCV_SUM_B_RE;	// MCV大类实种总和
	private Float MCV_SUM_B_SR;		//MCV大类接种率总和
	private Integer RCV_SUM_A_SH;	// RCV大类应种总和
	private Integer RCV_SUM_A_RE;	// RCV大类实种总和
	private Float RCV_SUM_A_SR;		//RCV大类接种率总和
	private Integer RCV_SUM_B_SH;	// RCV大类应种总和
	private Integer RCV_SUM_B_RE;	// RCV大类实种总和
	private Float RCV_SUM_B_SR;		//RCV大类接种率总和
	private Integer MUMCV_SUM_A_SH;	// MumCV大类应种总和
	private Integer MUMCV_SUM_A_RE;	// MumCV大类实种总和
	private Float MUMCV_SUM_A_SR;	//MumCV大类接种率总和
	private Integer MUMCV_SUM_B_SH;	// MumCV大类应种总和
	private Integer MUMCV_SUM_B_RE;	// MumCV大类实种总和
	private Float MUMCV_SUM_B_SR;	//MumCV大类接种率总和
	private Integer HEPA_SUM_A_SH;	// HepA大类应种总和
	private Integer HEPA_SUM_A_RE;	// HepA大类实种总和
	private Float HEPA_SUM_A_SR;	//HEPA大类接种率总和	
	private Integer HEPA_SUM_B_SH;	// HepA大类应种总和
	private Integer HEPA_SUM_B_RE;	// HepA大类实种总和
	private Float HEPA_SUM_B_SR;	//HEPA大类接种率总和	
	
	public ExpRoutinevacc6_1() {
		super();
		DISPLAY="1";
		UNIT_NAME = "";
		UNIT_CODE = "";
		RESIDE = "";
		DISPLAY = "1";	
		COMMITTEE = "";
		HEPB_A_SH = 0;
		HEPB_A_RE = 0;
		HEPB_A_SR = 0F;
		HEPB_AA_SH = 0;
		HEPB_AA_RE = 0;
		HEPB_AA_SR = 0F;
		HEPB_B_SH = 0;	
		HEPB_B_RE = 0;	
		HEPB_B_SR = 0F;
		HEPB_C_SH = 0;	
		HEPB_C_RE = 0;	
		HEPB_C_SR = 0F;	
		BCG_SH = 0;	
		BCG_RE = 0;	
		BCG_SR = 0F;	
		PV_A_SH = 0;	
		PV_A_RE = 0;	
		PV_A_SR = 0F;	
		PV_B_SH = 0;	
		PV_B_RE = 0;	
		PV_B_SR = 0F;	
		PV_C_SH = 0;	
		PV_C_RE = 0;	
		PV_C_SR = 0F;	
		PV_D_SH = 0;	
		PV_D_RE = 0;	
		PV_D_SR = 0F;	
		DTP_A_SH = 0;	
		DTP_A_RE = 0;	
		DTP_A_SR = 0F;	
		DTP_B_SH = 0;	
		DTP_B_RE = 0;	
		DTP_B_SR = 0F;	
		DTP_C_SH = 0;	
		DTP_C_RE = 0;	
		DTP_C_SR = 0F;	
		DTP_D_SH = 0;	
		DTP_D_RE = 0;	
		DTP_D_SR = 0F;	
		DT_SH = 0;	
		DT_RE = 0;	
		DT_SR = 0F;	
		MR_A_SH = 0;
		MR_A_RE = 0;
		MR_A_SR = 0F;
		MR_B_SH = 0;
		MR_B_RE = 0;
		MR_B_SR = 0F;
		MMR_A_SH = 0;
		MMR_A_RE = 0;
		MMR_A_SR = 0F;
		MMR_B_SH = 0;
		MMR_B_RE = 0;
		MMR_B_SR = 0F;
		MM_A_SH = 0;
		MM_A_RE = 0;
		MM_A_SR = 0F;
		MM_B_SH = 0;
		MM_B_RE = 0;
		MM_B_SR = 0F;
		MV_A_SH = 0;
		MV_A_RE = 0;
		MV_A_SR = 0F;
		MV_B_SH = 0;
		MV_B_RE = 0;
		MV_B_SR = 0F;
		MENA_A_SH = 0;
		MENA_A_RE = 0;
		MENA_A_SR = 0F;
		MENA_B_SH = 0;
		MENA_B_RE = 0;
		MENA_B_SR = 0F;
		MENAC_A_SH = 0;
		MENAC_A_RE = 0;
		MENAC_A_SR = 0F;
		MENAC_B_SH = 0;
		MENAC_B_RE = 0;
		MENAC_B_SR = 0F;
		JEL_A_SH = 0;
		JEL_A_RE = 0;
		JEL_A_SR = 0F;
		JEL_B_SH = 0;
		JEL_B_RE = 0;
		JEL_B_SR = 0F;
		JEI_A_SH = 0;
		JEI_A_RE = 0;
		JEI_A_SR = 0F;
		JEI_B_SH = 0;
		JEI_B_RE = 0;
		JEI_B_SR = 0F;
		JEI_C_SH = 0;
		JEI_C_RE = 0;
		JEI_C_SR = 0F;
		JEI_D_SH = 0;
		JEI_D_RE = 0;
		JEI_D_SR = 0F;
		HEPAL_SH = 0;
		HEPAL_RE = 0;
		HEPAL_SR = 0F;
		HEPAI_A_SH = 0;	
		HEPAI_A_RE = 0;	
		HEPAI_A_SR = 0F;	
		HEPAI_B_SH = 0;	
		HEPAI_B_RE = 0;	
		HEPAI_B_SR = 0F;
		MR_SUM_A_SH = 0;
		MR_SUM_A_RE = 0;
		MR_SUM_A_SR = 0F;
		MR_SUM_B_SH = 0;
		MR_SUM_B_RE = 0;
		MR_SUM_B_SR = 0F;
		MMR_SUM_A_SH = 0;	
		MMR_SUM_A_RE = 0;
		MMR_SUM_A_SR = 0F;
		MMR_SUM_B_SH = 0;	
		MMR_SUM_B_RE = 0;
		MMR_SUM_B_SR = 0F;
		MM_SUM_A_SH = 0;	
		MM_SUM_A_RE = 0;
		MM_SUM_A_SR = 0F;
		MM_SUM_B_SH = 0;	
		MM_SUM_B_RE = 0;
		MM_SUM_B_SR = 0F;
		MV_SUM_A_SH = 0;	
		MV_SUM_A_RE = 0;	
		MV_SUM_A_SR = 0F;
		MV_SUM_B_SH = 0;	
		MV_SUM_B_RE = 0;	
		MV_SUM_B_SR = 0F;
		HEPAL_SUM_A_SH = 0;	
		HEPAL_SUM_A_RE = 0;
		HEPAL_SUM_A_SR = 0F;
		HEPAL_SUM_B_SH = 0;	
		HEPAL_SUM_B_RE = 0;
		HEPAL_SUM_B_SR = 0F;
		HEPAI_SUM_A_SH = 0;	
		HEPAI_SUM_A_RE = 0;
		HEPAI_SUM_A_SR = 0F;
		HEPAI_SUM_B_SH = 0;	
		HEPAI_SUM_B_RE = 0;
		HEPAI_SUM_B_SR = 0F;
		MCV_SUM_A_SH = 0;	
		MCV_SUM_A_RE = 0;	
		MCV_SUM_A_SR = 0F;
		MCV_SUM_B_SH = 0;	
		MCV_SUM_B_RE = 0;	
		MCV_SUM_B_SR = 0F;
		RCV_SUM_A_SH = 0;	
		RCV_SUM_A_RE = 0;	
		RCV_SUM_A_SR = 0F;
		RCV_SUM_B_SH = 0;	
		RCV_SUM_B_RE = 0;	
		RCV_SUM_B_SR = 0F;
		MUMCV_SUM_A_SH = 0;	
		MUMCV_SUM_A_RE = 0;
		MUMCV_SUM_A_SR = 0F;
		MUMCV_SUM_B_SH = 0;	
		MUMCV_SUM_B_RE = 0;
		MUMCV_SUM_B_SR = 0F;
		HEPA_SUM_A_SH = 0;	
		HEPA_SUM_A_RE = 0;
		HEPA_SUM_A_SR = 0F;
		HEPA_SUM_B_SH = 0;	
		HEPA_SUM_B_RE = 0;
		HEPA_SUM_B_SR = 0F;
	}

	public ExpRoutinevacc6_1(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getYearMonth() {
		return YEAR_MONTH;
	}

	public void setYearMonth(String createDate) {
		this.YEAR_MONTH = createDate;
	}
	
	@Length(min=0, max=50, message="报告单位长度必须介于 0 和 50 之间")
	public String getUnitName() {
		return UNIT_NAME;
	}

	public void setUnitName(String UNIT_NAME) {
		this.UNIT_NAME = UNIT_NAME;
	}
	
	@Length(min=0, max=50, message="单位编码长度必须介于 0 和 50 之间")
	public String getUnitCode() {
		return UNIT_CODE;
	}

	public void setUnitCode(String UNIT_CODE) {
		this.UNIT_CODE = UNIT_CODE;
	}
	
	@Length(min=0, max=2, message="居住属性长度必须介于 0 和 2 之间")
	public String getReside() {
		return RESIDE;
	}

	public void setReside(String RESIDE) {
		this.RESIDE = RESIDE;
	}
	
	@Length(min=0, max=2, message="显示类型  常规/特殊长度必须介于 0 和 2 之间")
	public String getDisplay() {
		return DISPLAY;
	}

	public void setDisplay(String DISPLAY) {
		this.DISPLAY = DISPLAY;
	}
	
	@Length(min=0, max=50, message="居委名称长度必须介于 0 和 50 之间")
	public String getCommittee() {
		return COMMITTEE;
	}

	public void setCommittee(String COMMITTEE) {
		this.COMMITTEE = COMMITTEE;
	}
	
	@Length(min=0, max=20, message="HepB1应种长度必须介于 0 和 20 之间")
	public Integer getHepbASh() {
		return HEPB_A_SH;
	}

	public void setHepbASh(Integer HEPB_A_SH) {
		this.HEPB_A_SH = HEPB_A_SH;
	}
	
	@Length(min=0, max=20, message="HepB1实种长度必须介于 0 和 20 之间")
	public Integer getHepbARe() {
		return HEPB_A_RE;
	}

	public void setHepbARe(Integer HEPB_A_RE) {
		this.HEPB_A_RE = HEPB_A_RE;
	}
	
	@Length(min=0, max=20, message="HepB1接种率长度必须介于 0 和 20 之间")
	public float getHepbASr() {
		return HEPB_A_SR;
	}

	public void setHepbASr(float HEPB_A_SR) {
		this.HEPB_A_SR = HEPB_A_SR;
	}
	
	@Length(min=0, max=20, message="HepB1及时数应种长度必须介于 0 和 20 之间")
	public Integer getHepbAaSh() {
		return HEPB_AA_SH;
	}

	public void setHepbAaSh(Integer HEPB_AA_SH) {
		this.HEPB_AA_SH = HEPB_AA_SH;
	}
	
	@Length(min=0, max=20, message="HepB1及时数实种长度必须介于 0 和 20 之间")
	public Integer getHepbAaRe() {
		return HEPB_AA_RE;
	}

	public void setHepbAaRe(Integer HEPB_AA_RE) {
		this.HEPB_AA_RE = HEPB_AA_RE;
	}
	
	@Length(min=0, max=20, message="HepB1及时数接种率长度必须介于 0 和 20 之间")
	public Float getHepbAaSr() {
		return HEPB_AA_SR;
	}

	public void setHepbAaSr(Float HEPB_AA_SR) {
		this.HEPB_AA_SR = HEPB_AA_SR;
	}
	
	@Length(min=0, max=20, message="HepB2应种长度必须介于 0 和 20 之间")
	public Integer getHepbBSh() {
		return HEPB_B_SH;
	}

	public void setHepbBSh(Integer HEPB_B_SH) {
		this.HEPB_B_SH = HEPB_B_SH;
	}
	
	@Length(min=0, max=20, message="HepB2实种长度必须介于 0 和 20 之间")
	public Integer getHepbBRe() {
		return HEPB_B_RE;
	}

	public void setHepbBRe(Integer HEPB_B_RE) {
		this.HEPB_B_RE = HEPB_B_RE;
	}
	
	@Length(min=0, max=20, message="HepB2接种率长度必须介于 0 和 20 之间")
	public Float getHepbBSr() {
		return HEPB_B_SR;
	}

	public void setHepbBSr(Float HEPB_B_SR) {
		this.HEPB_B_SR = HEPB_B_SR;
	}
	
	@Length(min=0, max=20, message="HepB3应种长度必须介于 0 和 20 之间")
	public Integer getHepbCSh() {
		return HEPB_C_SH;
	}

	public void setHepbCSh(Integer HEPB_C_SH) {
		this.HEPB_C_SH = HEPB_C_SH;
	}
	
	@Length(min=0, max=20, message="HepB3实种长度必须介于 0 和 20 之间")
	public Integer getHepbCRe() {
		return HEPB_C_RE;
	}

	public void setHepbCRe(Integer HEPB_C_RE) {
		this.HEPB_C_RE = HEPB_C_RE;
	}
	
	@Length(min=0, max=20, message="HepB3接种率长度必须介于 0 和 20 之间")
	public Float getHepbCSr() {
		return HEPB_C_SR;
	}

	public void setHepbCSr(Float HEPB_C_SR) {
		this.HEPB_C_SR = HEPB_C_SR;
	}
	
	@Length(min=0, max=20, message="BCG应种长度必须介于 0 和 20 之间")
	public Integer getBcgSh() {
		return BCG_SH;
	}

	public void setBcgSh(Integer BCG_SH) {
		this.BCG_SH = BCG_SH;
	}
	
	@Length(min=0, max=20, message="BCG实种长度必须介于 0 和 20 之间")
	public Integer getBcgRe() {
		return BCG_RE;
	}

	public void setBcgRe(Integer BCG_RE) {
		this.BCG_RE = BCG_RE;
	}
	
	@Length(min=0, max=20, message="BCG接种率长度必须介于 0 和 20 之间")
	public Float getBcgSr() {
		return BCG_SR;
	}

	public void setBcgSr(Float BCG_SR) {
		this.BCG_SR = BCG_SR;
	}
	
	@Length(min=0, max=20, message="PV1应种长度必须介于 0 和 20 之间")
	public Integer getPvASh() {
		return PV_A_SH;
	}

	public void setPvASh(Integer PV_A_SH) {
		this.PV_A_SH = PV_A_SH;
	}
	
	@Length(min=0, max=20, message="PV1实种长度必须介于 0 和 20 之间")
	public Integer getPvARe() {
		return PV_A_RE;
	}

	public void setPvARe(Integer PV_A_RE) {
		this.PV_A_RE = PV_A_RE;
	}
	
	@Length(min=0, max=20, message="PV1接种率长度必须介于 0 和 20 之间")
	public Float getPvASr() {
		return PV_A_SR;
	}

	public void setPvASr(Float PV_A_SR) {
		this.PV_A_SR = PV_A_SR;
	}
	
	@Length(min=0, max=20, message="PV2应种长度必须介于 0 和 20 之间")
	public Integer getPvBSh() {
		return PV_B_SH;
	}

	public void setPvBSh(Integer PV_B_SH) {
		this.PV_B_SH = PV_B_SH;
	}
	
	@Length(min=0, max=20, message="PV2实种长度必须介于 0 和 20 之间")
	public Integer getPvBRe() {
		return PV_B_RE;
	}

	public void setPvBRe(Integer PV_B_RE) {
		this.PV_B_RE = PV_B_RE;
	}
	
	@Length(min=0, max=20, message="PV2接种率长度必须介于 0 和 20 之间")
	public Float getPvBSr() {
		return PV_B_SR;
	}

	public void setPvBSr(Float PV_B_SR) {
		this.PV_B_SR = PV_B_SR;
	}
	
	@Length(min=0, max=20, message="PV3应种长度必须介于 0 和 20 之间")
	public Integer getPvCSh() {
		return PV_C_SH;
	}

	public void setPvCSh(Integer PV_C_SH) {
		this.PV_C_SH = PV_C_SH;
	}
	
	@Length(min=0, max=20, message="PV3实种长度必须介于 0 和 20 之间")
	public Integer getPvCRe() {
		return PV_C_RE;
	}

	public void setPvCRe(Integer PV_C_RE) {
		this.PV_C_RE = PV_C_RE;
	}
	
	@Length(min=0, max=20, message="PV3接种率长度必须介于 0 和 20 之间")
	public Float getPvCSr() {
		return PV_C_SR;
	}

	public void setPvCSr(Float PV_C_SR) {
		this.PV_C_SR = PV_C_SR;
	}
	
	@Length(min=0, max=20, message="PV4应种长度必须介于 0 和 20 之间")
	public Integer getPvDSh() {
		return PV_D_SH;
	}

	public void setPvDSh(Integer PV_D_SH) {
		this.PV_D_SH = PV_D_SH;
	}
	
	@Length(min=0, max=20, message="PV4实种长度必须介于 0 和 20 之间")
	public Integer getPvDRe() {
		return PV_D_RE;
	}

	public void setPvDRe(Integer PV_D_RE) {
		this.PV_D_RE = PV_D_RE;
	}
	
	@Length(min=0, max=20, message="PV4接种率长度必须介于 0 和 20 之间")
	public Float getPvDSr() {
		return PV_D_SR;
	}

	public void setPvDSr(Float PV_D_SR) {
		this.PV_D_SR = PV_D_SR;
	}
	
	@Length(min=0, max=20, message="DTP1应种长度必须介于 0 和 20 之间")
	public Integer getDtpASh() {
		return DTP_A_SH;
	}

	public void setDtpASh(Integer DTP_A_SH) {
		this.DTP_A_SH = DTP_A_SH;
	}
	
	@Length(min=0, max=20, message="DTP1实种长度必须介于 0 和 20 之间")
	public Integer getDtpARe() {
		return DTP_A_RE;
	}

	public void setDtpARe(Integer DTP_A_RE) {
		this.DTP_A_RE = DTP_A_RE;
	}
	
	@Length(min=0, max=20, message="DTP1接种率长度必须介于 0 和 20 之间")
	public Float getDtpASr() {
		return DTP_A_SR;
	}

	public void setDtpASr(Float DTP_A_SR) {
		this.DTP_A_SR = DTP_A_SR;
	}
	
	@Length(min=0, max=20, message="DTP2应种长度必须介于 0 和 20 之间")
	public Integer getDtpBSh() {
		return DTP_B_SH;
	}

	public void setDtpBSh(Integer DTP_B_SH) {
		this.DTP_B_SH = DTP_B_SH;
	}
	
	@Length(min=0, max=20, message="DTP2实种长度必须介于 0 和 20 之间")
	public Integer getDtpBRe() {
		return DTP_B_RE;
	}

	public void setDtpBRe(Integer DTP_B_RE) {
		this.DTP_B_RE = DTP_B_RE;
	}
	
	@Length(min=0, max=20, message="DTP2接种率长度必须介于 0 和 20 之间")
	public Float getDtpBSr() {
		return DTP_B_SR;
	}

	public void setDtpBSr(Float DTP_B_SR) {
		this.DTP_B_SR = DTP_B_SR;
	}
	
	@Length(min=0, max=20, message="DTP3应种长度必须介于 0 和 20 之间")
	public Integer getDtpCSh() {
		return DTP_C_SH;
	}

	public void setDtpCSh(Integer DTP_C_SH) {
		this.DTP_C_SH = DTP_C_SH;
	}
	
	@Length(min=0, max=20, message="DTP3实种长度必须介于 0 和 20 之间")
	public Integer getDtpCRe() {
		return DTP_C_RE;
	}

	public void setDtpCRe(Integer DTP_C_RE) {
		this.DTP_C_RE = DTP_C_RE;
	}
	
	@Length(min=0, max=20, message="DTP3接种率长度必须介于 0 和 20 之间")
	public Float getDtpCSr() {
		return DTP_C_SR;
	}

	public void setDtpCSr(Float DTP_C_SR) {
		this.DTP_C_SR = DTP_C_SR;
	}
	
	@Length(min=0, max=20, message="DTP4应种长度必须介于 0 和 20 之间")
	public Integer getDtpDSh() {
		return DTP_D_SH;
	}

	public void setDtpDSh(Integer DTP_D_SH) {
		this.DTP_D_SH = DTP_D_SH;
	}
	
	@Length(min=0, max=20, message="DTP4实种长度必须介于 0 和 20 之间")
	public Integer getDtpDRe() {
		return DTP_D_RE;
	}

	public void setDtpDRe(Integer DTP_D_RE) {
		this.DTP_D_RE = DTP_D_RE;
	}
	
	@Length(min=0, max=20, message="DTP4接种率长度必须介于 0 和 20 之间")
	public Float getDtpDSr() {
		return DTP_D_SR;
	}

	public void setDtpDSr(Float DTP_D_SR) {
		this.DTP_D_SR = DTP_D_SR;
	}
	
	@Length(min=0, max=20, message="DT应种长度必须介于 0 和 20 之间")
	public Integer getDtSh() {
		return DT_SH;
	}

	public void setDtSh(Integer DT_SH) {
		this.DT_SH = DT_SH;
	}
	
	@Length(min=0, max=20, message="DT实种长度必须介于 0 和 20 之间")
	public Integer getDtRe() {
		return DT_RE;
	}

	public void setDtRe(Integer DT_SH) {
		this.DT_RE = DT_SH;
	}
	
	@Length(min=0, max=20, message="DT接种率长度必须介于 0 和 20 之间")
	public Float getDtSr() {
		return DT_SR;
	}

	public void setDtSr(Float DT_SR) {
		this.DT_SR = DT_SR;
	}
	
	@Length(min=0, max=20, message="MR1应种长度必须介于 0 和 20 之间")
	public Integer getMrASh() {
		return MR_A_SH;
	}

	public void setMrASh(Integer MR_A_SH) {
		this.MR_A_SH = MR_A_SH;
	}
	
	@Length(min=0, max=20, message="MR1实种长度必须介于 0 和 20 之间")
	public Integer getMrARe() {
		return MR_A_RE;
	}

	public void setMrARe(Integer MR_A_RE) {
		this.MR_A_RE = MR_A_RE;
	}
	
	@Length(min=0, max=20, message="MR1接种率长度必须介于 0 和 20 之间")
	public Float getMrASr() {
		return MR_A_SR;
	}

	public void setMrASr(Float MR_A_SR) {
		this.MR_A_SR = MR_A_SR;
	}
	
	@Length(min=0, max=20, message="MR2应种长度必须介于 0 和 20 之间")
	public Integer getMrBSh() {
		return MR_B_SH;
	}

	public void setMrBSh(Integer MR_B_SH) {
		this.MR_B_SH = MR_B_SH;
	}
	
	@Length(min=0, max=20, message="MR2实种长度必须介于 0 和 20 之间")
	public Integer getMrBRe() {
		return MR_B_RE;
	}

	public void setMrBRe(Integer MR_B_RE) {
		this.MR_B_RE = MR_B_RE;
	}
	
	@Length(min=0, max=20, message="MR2接种率长度必须介于 0 和 20 之间")
	public Float getMrBSr() {
		return MR_B_SR;
	}

	public void setMrBSr(Float MR_B_SR) {
		this.MR_B_SR = MR_B_SR;
	}
	
	@Length(min=0, max=20, message="MMR1应种长度必须介于 0 和 20 之间")
	public Integer getMmrASh() {
		return MMR_A_SH;
	}

	public void setMmrASh(Integer MMR_A_SH) {
		this.MMR_A_SH = MMR_A_SH;
	}
	
	@Length(min=0, max=20, message="MMR1实种长度必须介于 0 和 20 之间")
	public Integer getMmrARe() {
		return MMR_A_RE;
	}

	public void setMmrARe(Integer MMR_A_RE) {
		this.MMR_A_RE = MMR_A_RE;
	}
	
	@Length(min=0, max=20, message="MMR1接种率长度必须介于 0 和 20 之间")
	public Float getMmrASr() {
		return MMR_A_SR;
	}

	public void setMmrASr(Float MMR_A_SR) {
		this.MMR_A_SR = MMR_A_SR;
	}
	
	@Length(min=0, max=20, message="MMR2应种长度必须介于 0 和 20 之间")
	public Integer getMmrBSh() {
		return MMR_B_SH;
	}

	public void setMmrBSh(Integer MMR_B_SH) {
		this.MMR_B_SH = MMR_B_SH;
	}
	
	@Length(min=0, max=20, message="MMR2实种长度必须介于 0 和 20 之间")
	public Integer getMmrBRe() {
		return MMR_B_RE;
	}

	public void setMmrBRe(Integer MMR_B_RE) {
		this.MMR_B_RE = MMR_B_RE;
	}
	
	@Length(min=0, max=20, message="MMR2接种率长度必须介于 0 和 20 之间")
	public Float getMmrBSr() {
		return MMR_B_SR;
	}

	public void setMmrBSr(Float MMR_B_SR) {
		this.MMR_B_SR = MMR_B_SR;
	}
	
	@Length(min=0, max=20, message="MM1应种长度必须介于 0 和 20 之间")
	public Integer getMmASh() {
		return MM_A_SH;
	}

	public void setMmASh(Integer MM_A_SH) {
		this.MM_A_SH = MM_A_SH;
	}
	
	@Length(min=0, max=20, message="MM1实种长度必须介于 0 和 20 之间")
	public Integer getMmARe() {
		return MM_A_RE;
	}

	public void setMmARe(Integer MM_A_RE) {
		this.MM_A_RE = MM_A_RE;
	}
	
	@Length(min=0, max=20, message="MM1接种率长度必须介于 0 和 20 之间")
	public Float getMmASr() {
		return MM_A_SR;
	}

	public void setMmASr(Float MM_A_SR) {
		this.MM_A_SR = MM_A_SR;
	}
	
	@Length(min=0, max=20, message="MM2应种长度必须介于 0 和 20 之间")
	public Integer getMmBSh() {
		return MM_B_SH;
	}

	public void setMmBSh(Integer MM_B_SH) {
		this.MM_B_SH = MM_B_SH;
	}
	
	@Length(min=0, max=20, message="MM2实种长度必须介于 0 和 20 之间")
	public Integer getMmBRe() {
		return MM_B_RE;
	}

	public void setMmBRe(Integer MM_B_RE) {
		this.MM_B_RE = MM_B_RE;
	}
	
	@Length(min=0, max=20, message="MM2接种率长度必须介于 0 和 20 之间")
	public Float getMmBSr() {
		return MM_B_SR;
	}

	public void setMmBSr(Float MM_B_SR) {
		this.MM_B_SR = MM_B_SR;
	}
	
	@Length(min=0, max=20, message="MV1应种长度必须介于 0 和 20 之间")
	public Integer getMvASh() {
		return MV_A_SH;
	}

	public void setMvASh(Integer MV_A_SH) {
		this.MV_A_SH = MV_A_SH;
	}
	
	@Length(min=0, max=20, message="MV1实种长度必须介于 0 和 20 之间")
	public Integer getMvARe() {
		return MV_A_RE;
	}

	public void setMvARe(Integer MV_A_RE) {
		this.MV_A_RE = MV_A_RE;
	}
	
	@Length(min=0, max=20, message="MV1接种率长度必须介于 0 和 20 之间")
	public Float getMvASr() {
		return MV_A_SR;
	}

	public void setMvASr(Float MV_A_SR) {
		this.MV_A_SR = MV_A_SR;
	}
	
	@Length(min=0, max=20, message="MV2应种长度必须介于 0 和 20 之间")
	public Integer getMvBSh() {
		return MV_B_SH;
	}

	public void setMvBSh(Integer MV_B_SH) {
		this.MV_B_SH = MV_B_SH;
	}
	
	@Length(min=0, max=20, message="MV2实种长度必须介于 0 和 20 之间")
	public Integer getMvBRe() {
		return MV_B_RE;
	}

	public void setMvBRe(Integer MV_B_RE) {
		this.MV_B_RE = MV_B_RE;
	}
	
	@Length(min=0, max=20, message="MV2接种率长度必须介于 0 和 20 之间")
	public Float getMvBSr() {
		return MV_B_SR;
	}

	public void setMvBSr(Float MV_B_SR) {
		this.MV_B_SR = MV_B_SR;
	}
	
	@Length(min=0, max=20, message="MenA1应种长度必须介于 0 和 20 之间")
	public Integer getMenaASh() {
		return MENA_A_SH;
	}

	public void setMenaASh(Integer MENA_A_SH) {
		this.MENA_A_SH = MENA_A_SH;
	}
	
	@Length(min=0, max=20, message="MenA1实种长度必须介于 0 和 20 之间")
	public Integer getMenaARe() {
		return MENA_A_RE;
	}

	public void setMenaARe(Integer MENA_A_RE) {
		this.MENA_A_RE = MENA_A_RE;
	}
	
	@Length(min=0, max=20, message="MenA1接种率长度必须介于 0 和 20 之间")
	public Float getMenaASr() {
		return MENA_A_SR;
	}

	public void setMenaASr(Float MENA_A_SR) {
		this.MENA_A_SR = MENA_A_SR;
	}
	
	@Length(min=0, max=20, message="MenA2应种长度必须介于 0 和 20 之间")
	public Integer getMenaBSh() {
		return MENA_B_SH;
	}

	public void setMenaBSh(Integer MENA_B_SH) {
		this.MENA_B_SH = MENA_B_SH;
	}
	
	@Length(min=0, max=20, message="MenA2实种长度必须介于 0 和 20 之间")
	public Integer getMenaBRe() {
		return MENA_B_RE;
	}

	public void setMenaBRe(Integer MENA_B_RE) {
		this.MENA_B_RE = MENA_B_RE;
	}
	
	@Length(min=0, max=20, message="MenA2接种率长度必须介于 0 和 20 之间")
	public Float getMenaBSr() {
		return MENA_B_SR;
	}

	public void setMenaBSr(Float MENA_B_SR) {
		this.MENA_B_SR = MENA_B_SR;
	}
	
	@Length(min=0, max=20, message="MenAC1应种长度必须介于 0 和 20 之间")
	public Integer getMenacASh() {
		return MENAC_A_SH;
	}

	public void setMenacASh(Integer MENAC_A_SH) {
		this.MENAC_A_SH = MENAC_A_SH;
	}
	
	@Length(min=0, max=20, message="MenAC1实种长度必须介于 0 和 20 之间")
	public Integer getMenacARe() {
		return MENAC_A_RE;
	}

	public void setMenacARe(Integer MENAC_A_RE) {
		this.MENAC_A_RE = MENAC_A_RE;
	}
	
	@Length(min=0, max=20, message="MenAC1接种率长度必须介于 0 和 20 之间")
	public Float getMenacASr() {
		return MENAC_A_SR;
	}

	public void setMenacASr(Float MENAC_A_SR) {
		this.MENAC_A_SR = MENAC_A_SR;
	}
	
	@Length(min=0, max=20, message="MenAC2应种长度必须介于 0 和 20 之间")
	public Integer getMenacBSh() {
		return MENAC_B_SH;
	}

	public void setMenacBSh(Integer MENAC_B_SH) {
		this.MENAC_B_SH = MENAC_B_SH;
	}
	
	@Length(min=0, max=20, message="MenAC2实种长度必须介于 0 和 20 之间")
	public Integer getMenacBRe() {
		return MENAC_B_RE;
	}

	public void setMenacBRe(Integer MENAC_B_RE) {
		this.MENAC_B_RE = MENAC_B_RE;
	}
	
	@Length(min=0, max=20, message="MenAC2接种率长度必须介于 0 和 20 之间")
	public Float getMenacBSr() {
		return MENAC_B_SR;
	}

	public void setMenacBSr(Float MENAC_B_SR) {
		this.MENAC_B_SR = MENAC_B_SR;
	}
	
	@Length(min=0, max=20, message="JE-l1应种长度必须介于 0 和 20 之间")
	public Integer getJelASh() {
		return JEL_A_SH;
	}

	public void setJelASh(Integer JEL_A_SH) {
		this.JEL_A_SH = JEL_A_SH;
	}
	
	@Length(min=0, max=20, message="JE-l1实种长度必须介于 0 和 20 之间")
	public Integer getJelARe() {
		return JEL_A_RE;
	}

	public void setJelARe(Integer JEL_A_RE) {
		this.JEL_A_RE = JEL_A_RE;
	}
	
	@Length(min=0, max=20, message="JE-l1接种率长度必须介于 0 和 20 之间")
	public Float getJelASr() {
		return JEL_A_SR;
	}

	public void setJelASr(Float JEL_A_SR) {
		this.JEL_A_SR = JEL_A_SR;
	}
	
	@Length(min=0, max=20, message="JE-l2应种长度必须介于 0 和 20 之间")
	public Integer getJelBSh() {
		return JEL_B_SH;
	}

	public void setJelBSh(Integer JEL_B_SH) {
		this.JEL_B_SH = JEL_B_SH;
	}
	
	@Length(min=0, max=20, message="JE-l2实种长度必须介于 0 和 20 之间")
	public Integer getJelBRe() {
		return JEL_B_RE;
	}

	public void setJelBRe(Integer JEL_B_RE) {
		this.JEL_B_RE = JEL_B_RE;
	}
	
	@Length(min=0, max=20, message="JE-l2接种率长度必须介于 0 和 20 之间")
	public Float getJelBSr() {
		return JEL_B_SR;
	}

	public void setJelBSr(Float JEL_B_SR) {
		this.JEL_B_SR = JEL_B_SR;
	}
	
	@Length(min=0, max=20, message="JE-i1应种长度必须介于 0 和 20 之间")
	public Integer getJeiASh() {
		return JEI_A_SH;
	}

	public void setJeiASh(Integer JEI_A_SH) {
		this.JEI_A_SH = JEI_A_SH;
	}
	
	@Length(min=0, max=20, message="JE-i1实种长度必须介于 0 和 20 之间")
	public Integer getJeiARe() {
		return JEI_A_RE;
	}

	public void setJeiARe(Integer JEI_A_RE) {
		this.JEI_A_RE = JEI_A_RE;
	}
	
	@Length(min=0, max=20, message="JE-i1接种率长度必须介于 0 和 20 之间")
	public Float getJeiASr() {
		return JEI_A_SR;
	}

	public void setJeiASr(Float JEI_A_SR) {
		this.JEI_A_SR = JEI_A_SR;
	}
	
	@Length(min=0, max=20, message="JE-i2应种长度必须介于 0 和 20 之间")
	public Integer getJeiBSh() {
		return JEI_B_SH;
	}

	public void setJeiBSh(Integer JEI_B_SH) {
		this.JEI_B_SH = JEI_B_SH;
	}
	
	@Length(min=0, max=20, message="JE-i2实种长度必须介于 0 和 20 之间")
	public Integer getJeiBRe() {
		return JEI_B_RE;
	}

	public void setJeiBRe(Integer JEI_B_RE) {
		this.JEI_B_RE = JEI_B_RE;
	}
	
	@Length(min=0, max=20, message="JE-i2接种率长度必须介于 0 和 20 之间")
	public Float getJeiBSr() {
		return JEI_B_SR;
	}

	public void setJeiBSr(Float JEI_B_SR) {
		this.JEI_B_SR = JEI_B_SR;
	}
	
	@Length(min=0, max=20, message="JE-i3应种长度必须介于 0 和 20 之间")
	public Integer getJeiCSh() {
		return JEI_C_SH;
	}

	public void setJeiCSh(Integer JEI_C_SH) {
		this.JEI_C_SH = JEI_C_SH;
	}
	
	@Length(min=0, max=20, message="JE-i3实种长度必须介于 0 和 20 之间")
	public Integer getJeiCRe() {
		return JEI_C_RE;
	}

	public void setJeiCRe(Integer JEI_C_RE) {
		this.JEI_C_RE = JEI_C_RE;
	}
	
	@Length(min=0, max=20, message="JE-i3接种率长度必须介于 0 和 20 之间")
	public Float getJeiCSr() {
		return JEI_C_SR;
	}

	public void setJeiCSr(Float JEI_C_SR) {
		this.JEI_C_SR = JEI_C_SR;
	}
	
	@Length(min=0, max=20, message="JE-i4应种长度必须介于 0 和 20 之间")
	public Integer getJeiDSh() {
		return JEI_D_SH;
	}

	public void setJeiDSh(Integer JEI_D_SH) {
		this.JEI_D_SH = JEI_D_SH;
	}
	
	@Length(min=0, max=20, message="JE-i4实种长度必须介于 0 和 20 之间")
	public Integer getJeiDRe() {
		return JEI_D_RE;
	}

	public void setJeiDRe(Integer JEI_D_RE) {
		this.JEI_D_RE = JEI_D_RE;
	}
	
	@Length(min=0, max=20, message="JE-i4接种率长度必须介于 0 和 20 之间")
	public Float getJeiDSr() {
		return JEI_D_SR;
	}

	public void setJeiDSr(Float JEI_D_SR) {
		this.JEI_D_SR = JEI_D_SR;
	}
	
	@Length(min=0, max=20, message="Hepa-l应种长度必须介于 0 和 20 之间")
	public Integer getHepalSh() {
		return HEPAL_SH;
	}

	public void setHepalSh(Integer HEPAL_SH) {
		this.HEPAL_SH = HEPAL_SH;
	}
	
	@Length(min=0, max=20, message="Hepa-l实种长度必须介于 0 和 20 之间")
	public Integer getHepalRe() {
		return HEPAL_RE;
	}

	public void setHepalRe(Integer HEPAL_RE) {
		this.HEPAL_RE = HEPAL_RE;
	}
	
	@Length(min=0, max=20, message="Hepa-l接种率长度必须介于 0 和 20 之间")
	public Float getHepalSr() {
		return HEPAL_SR;
	}

	public void setHepalSr(Float HEPAL_SR) {
		this.HEPAL_SR = HEPAL_SR;
	}
	
	@Length(min=0, max=20, message="Hepa-i1应种长度必须介于 0 和 20 之间")
	public Integer getHepaiASh() {
		return HEPAI_A_SH;
	}

	public void setHepaiASh(Integer HEPAI_A_SH) {
		this.HEPAI_A_SH = HEPAI_A_SH;
	}
	
	@Length(min=0, max=20, message="Hepa-i1实种长度必须介于 0 和 20 之间")
	public Integer getHepaiARe() {
		return HEPAI_A_RE;
	}

	public void setHepaiARe(Integer HEPAI_A_RE) {
		this.HEPAI_A_RE = HEPAI_A_RE;
	}
	
	@Length(min=0, max=20, message="Hepa-i1接种率长度必须介于 0 和 20 之间")
	public Float getHepaiASr() {
		return HEPAI_A_SR;
	}

	public void setHepaiASr(Float HEPAI_A_SR) {
		this.HEPAI_A_SR = HEPAI_A_SR;
	}
	
	@Length(min=0, max=20, message="Hepa-i2应种长度必须介于 0 和 20 之间")
	public Integer getHepaiBSh() {
		return HEPAI_B_SH;
	}

	public void setHepaiBSh(Integer HEPAI_B_SH) {
		this.HEPAI_B_SH = HEPAI_B_SH;
	}
	
	@Length(min=0, max=20, message="Hepa-i2实种长度必须介于 0 和 20 之间")
	public Integer getHepaiBRe() {
		return HEPAI_B_RE;
	}

	public void setHepaiBRe(Integer HEPAI_B_RE) {
		this.HEPAI_B_RE = HEPAI_B_RE;
	}
	
	@Length(min=0, max=20, message="Hepa-i2接种率长度必须介于 0 和 20 之间")
	public Float getHepaiBSr() {
		return HEPAI_B_SR;
	}

	public void setHepaiBSr(Float HEPAI_B_SR) {
		this.HEPAI_B_SR = HEPAI_B_SR;
	}
	
	@Length(min=0, max=22, message="MR应种总和长度必须介于 0 和 22 之间")
	public Integer getMrSumASh() {
		return MR_SUM_A_SH;
	}

	public void setMrSumASh(Integer MR_SUM_A_SH) {
		this.MR_SUM_A_SH = MR_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="MR接种率总和长度必须介于 0 和 22 之间")
	public Integer getMrSumARe() {
		return MR_SUM_A_RE;
	}

	public void setMrSumARe(Integer MR_SUM_A_RE) {
		this.MR_SUM_A_RE = MR_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="MR接种率总和长度必须介于 0 和 22 之间")
	public Float getMrSumASr() {
		return MR_SUM_A_SR;
	}

	public void setMrSumASr(Float MR_SUM_A_SR) {
		this.MR_SUM_A_SR = MR_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="MR应种总和长度必须介于 0 和 22 之间")
	public Integer getMrSumBSh() {
		return MR_SUM_B_SH;
	}

	public void setMrSumBSh(Integer MR_SUM_B_SH) {
		this.MR_SUM_B_SH = MR_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="MR接种率总和长度必须介于 0 和 22 之间")
	public Integer getMrSumBRe() {
		return MR_SUM_B_RE;
	}

	public void setMrSumBRe(Integer MR_SUM_B_RE) {
		this.MR_SUM_B_RE = MR_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="MR接种率总和长度必须介于 0 和 22 之间")
	public Float getMrSumBSr() {
		return MR_SUM_B_SR;
	}

	public void setMrSumBSr(Float MR_SUM_B_SR) {
		this.MR_SUM_B_SR = MR_SUM_B_SR;
	}
	
	
	
	
	@Length(min=0, max=22, message="MMR应种总和长度必须介于 0 和 22 之间")
	public Integer getMmrSumASh() {
		return MMR_SUM_A_SH;
	}

	public void setMmrSumASh(Integer MMR_SUM_A_SH) {
		this.MMR_SUM_A_SH = MMR_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="MMR接种率总和长度必须介于 0 和 22 之间")
	public Integer getMmrSumARe() {
		return MMR_SUM_A_RE;
	}

	public void setMmrSumARe(Integer MMR_SUM_A_RE) {
		this.MMR_SUM_A_RE = MMR_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="MMR接种率总和长度必须介于 0 和 22 之间")
	public Float getMmrSumASr() {
		return MMR_SUM_A_SR;
	}

	public void setMmrSumASr(Float MMR_SUM_A_SR) {
		this.MMR_SUM_A_SR = MMR_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="MMR应种总和长度必须介于 0 和 22 之间")
	public Integer getMmrSumBSh() {
		return MMR_SUM_B_SH;
	}

	public void setMmrSumBSh(Integer MMR_SUM_B_SH) {
		this.MMR_SUM_B_SH = MMR_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="MMR接种率总和长度必须介于 0 和 22 之间")
	public Integer getMmrSumBRe() {
		return MMR_SUM_B_RE;
	}

	public void setMmrSumBRe(Integer MMR_SUM_B_RE) {
		this.MMR_SUM_B_RE = MMR_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="MMR接种率总和长度必须介于 0 和 22 之间")
	public Float getMmrSumBSr() {
		return MMR_SUM_B_SR;
	}

	public void setMmrSumBSr(Float MMR_SUM_B_SR) {
		this.MMR_SUM_B_SR = MMR_SUM_B_SR;
	}
	
	
	
	
	
	@Length(min=0, max=22, message="MM应种总和长度必须介于 0 和 22 之间")
	public Integer getMmSumASh() {
		return MM_SUM_A_SH;
	}

	public void setMmSumASh(Integer MM_SUM_A_SH) {
		this.MM_SUM_A_SH = MM_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="MM接种率总和长度必须介于 0 和 22 之间")
	public Integer getMmSumARe() {
		return MM_SUM_A_RE;
	}

	public void setMmSumARe(Integer MM_SUM_A_RE) {
		this.MM_SUM_A_RE = MM_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="MM接种率总和长度必须介于 0 和 22 之间")
	public Float getMmSumASr() {
		return MM_SUM_A_SR;
	}

	public void setMmSumASr(Float MM_SUM_A_SR) {
		this.MM_SUM_A_SR = MM_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="MM应种总和长度必须介于 0 和 22 之间")
	public Integer getMmSumBSh() {
		return MM_SUM_B_SH;
	}

	public void setMmSumBSh(Integer MM_SUM_B_SH) {
		this.MM_SUM_B_SH = MM_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="MM接种率总和长度必须介于 0 和 22 之间")
	public Integer getMmSumBRe() {
		return MM_SUM_B_RE;
	}

	public void setMmSumBRe(Integer MM_SUM_B_RE) {
		this.MM_SUM_B_RE = MM_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="MM接种率总和长度必须介于 0 和 22 之间")
	public Float getMmSumBSr() {
		return MM_SUM_B_SR;
	}

	public void setMmSumBSr(Float MM_SUM_B_SR) {
		this.MM_SUM_B_SR = MM_SUM_B_SR;
	}
	
	
	
	
	
	@Length(min=0, max=22, message="MV应种总和长度必须介于 0 和 22 之间")
	public Integer getMvSumASh() {
		return MV_SUM_A_SH;
	}

	public void setMvSumASh(Integer MV_SUM_A_SH) {
		this.MV_SUM_A_SH = MV_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="MV接种率总和长度必须介于 0 和 22 之间")
	public Integer getMvSumARe() {
		return MV_SUM_A_RE;
	}

	public void setMvSumARe(Integer MV_SUM_A_RE) {
		this.MV_SUM_A_RE = MV_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="MV接种率总和长度必须介于 0 和 22 之间")
	public Float getMvSumASr() {
		return MV_SUM_A_SR;
	}

	public void setMvSumASr(Float MV_SUM_A_SR) {
		this.MV_SUM_A_SR = MV_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="MV应种总和长度必须介于 0 和 22 之间")
	public Integer getMvSumBSh() {
		return MV_SUM_B_SH;
	}

	public void setMvSumBSh(Integer MV_SUM_B_SH) {
		this.MV_SUM_B_SH = MV_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="MV接种率总和长度必须介于 0 和 22 之间")
	public Integer getMvSumBRe() {
		return MV_SUM_B_RE;
	}

	public void setMvSumBRe(Integer MV_SUM_B_RE) {
		this.MV_SUM_B_RE = MV_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="MV接种率总和长度必须介于 0 和 22 之间")
	public Float getMvSumBSr() {
		return MV_SUM_B_SR;
	}

	public void setMvSumBSr(Float MV_SUM_B_SR) {
		this.MV_SUM_B_SR = MV_SUM_B_SR;
	}
	
	
	
		
	@Length(min=0, max=22, message="HEPAL应种总和长度必须介于 0 和 22 之间")
	public Integer getHepalSumASh() {
		return HEPAL_SUM_A_SH;
	}

	public void setHepalSumASh(Integer HEPAL_SUM_A_SH) {
		this.HEPAL_SUM_A_SH = HEPAL_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="HEPAL接种率总和长度必须介于 0 和 22 之间")
	public Integer getHepalSumARe() {
		return HEPAL_SUM_A_RE;
	}

	public void setHepalSumARe(Integer HEPAL_SUM_A_RE) {
		this.HEPAL_SUM_A_RE = HEPAL_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="HEPAL接种率总和长度必须介于 0 和 22 之间")
	public Float getHepalSumASr() {
		return HEPAL_SUM_A_SR;
	}

	public void setHepalSumASr(Float HEPAL_SUM_A_SR) {
		this.HEPAL_SUM_A_SR = HEPAL_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="HEPAL应种总和长度必须介于 0 和 22 之间")
	public Integer getHepalSumBSh() {
		return HEPAL_SUM_B_SH;
	}

	public void setHepalSumBSh(Integer HEPAL_SUM_B_SH) {
		this.HEPAL_SUM_B_SH = HEPAL_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="HEPAL接种率总和长度必须介于 0 和 22 之间")
	public Integer getHepalSumBRe() {
		return HEPAL_SUM_B_RE;
	}

	public void setHepalSumBRe(Integer HEPAL_SUM_B_RE) {
		this.HEPAL_SUM_B_RE = HEPAL_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="HEPAL接种率总和长度必须介于 0 和 22 之间")
	public Float getHepalSumBSr() {
		return HEPAL_SUM_B_SR;
	}

	public void setHepalSumBSr(Float HEPAL_SUM_B_SR) {
		this.HEPAL_SUM_B_SR = HEPAL_SUM_B_SR;
	}
	
	
	
	
	@Length(min=0, max=22, message="HEPAI应种总和长度必须介于 0 和 22 之间")
	public Integer getHepaiSumASh() {
		return HEPAI_SUM_A_SH;
	}

	public void setHepaiSumASh(Integer HEPAI_SUM_A_SH) {
		this.HEPAI_SUM_A_SH = HEPAI_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="HEPI接种率总和长度必须介于 0 和 22 之间")
	public Integer getHepaiSumARe() {
		return HEPAI_SUM_A_RE;
	}

	public void setHepaiSumARe(Integer HEPAI_SUM_A_RE) {
		this.HEPAI_SUM_A_RE = HEPAI_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="HEPI接种率总和长度必须介于 0 和 22 之间")
	public Float getHepaiSumASr() {
		return HEPAI_SUM_A_SR;
	}

	public void setHepaiSumASr(Float HEPAI_SUM_A_SR) {
		this.HEPAI_SUM_A_SR = HEPAI_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="HEPAI应种总和长度必须介于 0 和 22 之间")
	public Integer getHepaiSumBSh() {
		return HEPAI_SUM_B_SH;
	}

	public void setHepaiSumBSh(Integer HEPAI_SUM_B_SH) {
		this.HEPAI_SUM_B_SH = HEPAI_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="HEPI接种率总和长度必须介于 0 和 22 之间")
	public Integer getHepaiSumBRe() {
		return HEPAI_SUM_B_RE;
	}

	public void setHepaiSumBRe(Integer HEPAI_SUM_B_RE) {
		this.HEPAI_SUM_B_RE = HEPAI_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="HEPI接种率总和长度必须介于 0 和 22 之间")
	public Float getHepaiSumBSr() {
		return HEPAI_SUM_B_SR;
	}

	public void setHepaiSumBSr(Float HEPAI_SUM_B_SR) {
		this.HEPAI_SUM_B_SR = HEPAI_SUM_B_SR;
	}
	
	
	
	
	@Length(min=0, max=22, message="MCV大类应种总和长度必须介于 0 和 22 之间")
	public Integer getMcvSumASh() {
		return MCV_SUM_A_SH;
	}

	public void setMcvSumASh(Integer MCV_SUM_A_SH) {
		this.MCV_SUM_A_SH = MCV_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="MCV大类接种率总和长度必须介于 0 和 22 之间")
	public Integer getMcvSumARe() {
		return MCV_SUM_A_RE;
	}

	public void setMcvSumARe(Integer MCV_SUM_A_RE) {
		this.MCV_SUM_A_RE = MCV_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="MCV大类接种率总和长度必须介于 0 和 22 之间")
	public Float getMcvSumASr() {
		return MCV_SUM_A_SR;
	}

	public void setMcvSumASr(Float MCV_SUM_A_SR) {
		this.MCV_SUM_A_SR = MCV_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="MCV大类应种总和长度必须介于 0 和 22 之间")
	public Integer getMcvSumBSh() {
		return MCV_SUM_B_SH;
	}

	public void setMcvSumBSh(Integer MCV_SUM_B_SH) {
		this.MCV_SUM_B_SH = MCV_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="MCV大类接种率总和长度必须介于 0 和 22 之间")
	public Integer getMcvSumBRe() {
		return MCV_SUM_B_RE;
	}

	public void setMcvSumBRe(Integer MCV_SUM_B_RE) {
		this.MCV_SUM_B_RE = MCV_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="MCV大类接种率总和长度必须介于 0 和 22 之间")
	public Float getMcvSumBSr() {
		return MCV_SUM_B_SR;
	}

	public void setMcvSumBSr(Float MCV_SUM_B_SR) {
		this.MCV_SUM_B_SR = MCV_SUM_B_SR;
	}
	
	
	
	
	@Length(min=0, max=22, message="RCV大类应种总和长度必须介于 0 和 22 之间")
	public Integer getRcvSumASh() {
		return RCV_SUM_A_SH;
	}

	public void setRcvSumASh(Integer RCV_SUM_A_SH) {
		this.RCV_SUM_A_SH = RCV_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="RCV大类接种率总和长度必须介于 0 和 22 之间")
	public Integer getRcvSumARe() {
		return RCV_SUM_A_RE;
	}

	public void setRcvSumARe(Integer RCV_SUM_A_RE) {
		this.RCV_SUM_A_RE = RCV_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="RCV大类接种率总和长度必须介于 0 和 22 之间")
	public Float getRcvSumASr() {
		return RCV_SUM_A_SR;
	}

	public void setRcvSumASr(Float RCV_SUM_A_SR) {
		this.RCV_SUM_A_SR = RCV_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="RCV大类应种总和长度必须介于 0 和 22 之间")
	public Integer getRcvSumBSh() {
		return RCV_SUM_B_SH;
	}

	public void setRcvSumBSh(Integer RCV_SUM_B_SH) {
		this.RCV_SUM_B_SH = RCV_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="RCV大类接种率总和长度必须介于 0 和 22 之间")
	public Integer getRcvSumBRe() {
		return RCV_SUM_B_RE;
	}

	public void setRcvSumBRe(Integer RCV_SUM_B_RE) {
		this.RCV_SUM_B_RE = RCV_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="RCV大类接种率总和长度必须介于 0 和 22 之间")
	public Float getRcvSumBSr() {
		return RCV_SUM_B_SR;
	}

	public void setRcvSumBSr(Float RCV_SUM_B_SR) {
		this.RCV_SUM_B_SR = RCV_SUM_B_SR;
	}
	
	
	
	
	
	@Length(min=0, max=22, message="MumCV大类应种总和长度必须介于 0 和 22 之间")
	public Integer getMumcvSumASh() {
		return MUMCV_SUM_A_SH;
	}

	public void setMumcvSumASh(Integer MUMCV_SUM_A_SH) {
		this.MUMCV_SUM_A_SH = MUMCV_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="MumCV大类接种率总和长度必须介于 0 和 22 之间")
	public Integer getMumcvSumARe() {
		return MUMCV_SUM_A_RE;
	}

	public void setMumcvSumARe(Integer MUMCV_SUM_A_RE) {
		this.MUMCV_SUM_A_RE = MUMCV_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="MumCV大类接种率总和长度必须介于 0 和 22 之间")
	public Float getMumcvSumASr() {
		return MUMCV_SUM_A_SR;
	}

	public void setMumcvSumASr(Float MUMCV_SUM_A_SR) {
		this.MUMCV_SUM_A_SR = MUMCV_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="MumCV大类应种总和长度必须介于 0 和 22 之间")
	public Integer getMumcvSumBSh() {
		return MUMCV_SUM_B_SH;
	}

	public void setMumcvSumBSh(Integer MUMCV_SUM_B_SH) {
		this.MUMCV_SUM_B_SH = MUMCV_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="MumCV大类接种率总和长度必须介于 0 和 22 之间")
	public Integer getMumcvSumBRe() {
		return MUMCV_SUM_B_RE;
	}

	public void setMumcvSumBRe(Integer MUMCV_SUM_B_RE) {
		this.MUMCV_SUM_B_RE = MUMCV_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="MumCV大类接种率总和长度必须介于 0 和 22 之间")
	public Float getMumcvSumBSr() {
		return MUMCV_SUM_B_SR;
	}

	public void setMumcvSumBSr(Float MUMCV_SUM_B_SR) {
		this.MUMCV_SUM_B_SR = MUMCV_SUM_B_SR;
	}
	
	
	
	
	@Length(min=0, max=22, message="HepA大类应种总和长度必须介于 0 和 22 之间")
	public Integer getHepaSumASh() {
		return HEPA_SUM_A_SH;
	}

	public void setHepaSumASh(Integer HEPA_SUM_A_SH) {
		this.HEPA_SUM_A_SH = HEPA_SUM_A_SH;
	}
	
	@Length(min=0, max=22, message="HepA大类接种率总和长度必须介于 0 和 22 之间")
	public Integer getHepaSumARe() {
		return HEPA_SUM_A_RE;
	}

	public void setHepaSumARe(Integer HEPA_SUM_A_RE) {
		this.HEPA_SUM_A_RE = HEPA_SUM_A_RE;
	}
	
	@Length(min=0, max=22, message="HepA大类接种率总和长度必须介于 0 和 22 之间")
	public Float getHepaSumASr() {
		return HEPA_SUM_A_SR;
	}

	public void setHepaSumASr(Float HEPA_SUM_A_SR) {
		this.HEPA_SUM_A_SR = HEPA_SUM_A_SR;
	}
	
	@Length(min=0, max=22, message="HepA大类应种总和长度必须介于 0 和 22 之间")
	public Integer getHepaSumBSh() {
		return HEPA_SUM_B_SH;
	}

	public void setHepaSumBSh(Integer HEPA_SUM_B_SH) {
		this.HEPA_SUM_B_SH = HEPA_SUM_B_SH;
	}
	
	@Length(min=0, max=22, message="HepA大类接种率总和长度必须介于 0 和 22 之间")
	public Integer getHepaSumBRe() {
		return HEPA_SUM_B_RE;
	}

	public void setHepaSumBRe(Integer HEPA_SUM_B_RE) {
		this.HEPA_SUM_B_RE = HEPA_SUM_B_RE;
	}
	
	@Length(min=0, max=22, message="HepA大类接种率总和长度必须介于 0 和 22 之间")
	public Float getHepaSumBSr() {
		return HEPA_SUM_B_SR;
	}

	public void setHepaSumBSr(Float HEPA_SUM_B_SR) {
		this.HEPA_SUM_B_SR = HEPA_SUM_B_SR;
	}
	
	private String CHILDID;		// childid
	private String VACCCODE;		// vacccode
	private String TYPE;

	@Length(min=1, max=64, message="childid长度必须介于 1 和 64 之间")
	public String getChildid() {
		return CHILDID;
	}

	public void setChildid(String CHILDID) {
		this.CHILDID = CHILDID;
	}
	
	@Length(min=0, max=64, message="vacccode长度必须介于 0 和 64 之间")
	public String getVacccode() {
		return VACCCODE;
	}

	public void setVacccode(String VACCCODE) {
		this.VACCCODE = VACCCODE;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String type) {
		this.TYPE = type;
	}
	

	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	
}