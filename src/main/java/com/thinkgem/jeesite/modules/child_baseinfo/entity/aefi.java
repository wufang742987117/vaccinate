package com.thinkgem.jeesite.modules.child_baseinfo.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

public class aefi implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("AEFI_BACT_CODE")
	private String aefi_bact_code;

	@JsonProperty("AEFI_CHIL_ID")
	private String aefi_chil_id;

	@JsonProperty("AEFI_CODE")
	private String aefi_code;

	@JsonProperty("AEFI_DATE")
	private String aefi_date;
	
	private String localCode;

	public String getAefi_bact_code() {
		return this.aefi_bact_code;
	}

	public void setAefi_bact_code(String aefi_bact_code) {
		this.aefi_bact_code = aefi_bact_code;
	}

	public String getAefi_chil_id() {
		return this.aefi_chil_id;
	}

	public void setAefi_chil_id(String aefi_chil_id) {
		this.aefi_chil_id = aefi_chil_id;
	}

	public String getAefi_code() {
		return this.aefi_code;
	}

	public void setAefi_code(String aefi_code) {
		this.aefi_code = aefi_code;
	}

	public String getAefi_date() {
		return this.aefi_date;
	}

	public void setAefi_date(String aefi_date) {
		this.aefi_date = aefi_date;
	}

	public String getLocalCode() {
		return OfficeService.getFirstOfficeCode();
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	
	
}