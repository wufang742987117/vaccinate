package com.thinkgem.jeesite.modules.child_baseinfo.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

public class chhe implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty("CHHE_PREHERE")
	private String chhe_prehere;

	@JsonProperty("CHHE_CHGCODE")
	private String chhe_chgcode;

	@JsonProperty("CHHE_EDITSTATE")
	private String chhe_editstate;

	@JsonProperty("CHHE_CHIL_ID")
	private String chhe_chil_id;

	@JsonProperty("CHHE_HERE")
	private String chhe_here;

	@JsonProperty("CHHE_CHIL_PROVINCEREMARK")
	private String chhe_chil_provinceremark;

	@JsonProperty("CHHE_CHIL_PROVINCE")
	private String chhe_chil_province;

	@JsonProperty("CHHE_DEPA_ID")
	private String chhe_depa_id;

	@JsonProperty("CHHE_CHANGEDATE")
	private String chhe_changedate;
	
	private String localCode;

	public String getChhe_prehere() {
		return this.chhe_prehere;
	}

	public void setChhe_prehere(String chhe_prehere) {
		this.chhe_prehere = chhe_prehere;
	}

	public String getChhe_chgcode() {
		return this.chhe_chgcode;
	}

	public void setChhe_chgcode(String chhe_chgcode) {
		this.chhe_chgcode = chhe_chgcode;
	}

	public String getChhe_editstate() {
		return this.chhe_editstate;
	}

	public void setChhe_editstate(String chhe_editstate) {
		this.chhe_editstate = chhe_editstate;
	}

	public String getChhe_chil_id() {
		return this.chhe_chil_id;
	}

	public void setChhe_chil_id(String chhe_chil_id) {
		this.chhe_chil_id = chhe_chil_id;
	}

	public String getChhe_here() {
		return this.chhe_here;
	}

	public void setChhe_here(String chhe_here) {
		this.chhe_here = chhe_here;
	}

	public String getChhe_chil_provinceremark() {
		return this.chhe_chil_provinceremark;
	}

	public void setChhe_chil_provinceremark(String chhe_chil_provinceremark) {
		this.chhe_chil_provinceremark = chhe_chil_provinceremark;
	}

	public String getChhe_chil_province() {
		return this.chhe_chil_province;
	}

	public void setChhe_chil_province(String chhe_chil_province) {
		this.chhe_chil_province = chhe_chil_province;
	}

	public String getChhe_depa_id() {
		return this.chhe_depa_id;
	}

	public void setChhe_depa_id(String chhe_depa_id) {
		this.chhe_depa_id = chhe_depa_id;
	}

	public String getChhe_changedate() {
		return this.chhe_changedate;
	}

	public void setChhe_changedate(String chhe_changedate) {
		this.chhe_changedate = chhe_changedate;
	}

	public String getLocalCode() {
		return OfficeService.getFirstOfficeCode();
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	
	
}