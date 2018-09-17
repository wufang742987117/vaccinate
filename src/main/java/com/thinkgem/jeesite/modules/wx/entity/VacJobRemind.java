/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信定时提醒Entity
 * @author zhouqj
 * @version 2017-04-26
 */
public class VacJobRemind extends DataEntity<VacJobRemind> {
	
	/** 疫苗类别-成人 */
	public static String RTYPE_BRROR = "2";
	/** 疫苗类别-犬伤 */
	public static String RTYPE_ARROR = "1";
	/** 提示状态-正常状态 */
	public static String RSTATUS_ARROR = "0";
	
	private static final long serialVersionUID = 1L;
	private String openid;		// 用户openid
	private String ctxusername;		// 用户名
	private String rtype;		// 1:犬伤  2:乙肝
	private Date ctxdate;		// 提醒内容时间
	private Date createdate;		// 创建时间
	private String ctxvaccname;		// 疫苗名称
	private String rstatus;		// 0:正常状态
	private String code;		// 小类编号
	private String office;		// 接种单位编码
	
	public VacJobRemind() {
		super();
	}

	public VacJobRemind(String id){
		super(id);
	}

	@Length(min=1, max=32, message="用户openid长度必须介于 1 和 32 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=32, message="用户名长度必须介于 0 和 32 之间")
	public String getCtxusername() {
		return ctxusername;
	}

	public void setCtxusername(String ctxusername) {
		this.ctxusername = ctxusername;
	}
	
	@Length(min=0, max=10, message="1:犬伤长度必须介于 0 和 10 之间")
	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="提醒内容时间不能为空")
	public Date getCtxdate() {
		return ctxdate;
	}

	public void setCtxdate(Date ctxdate) {
		this.ctxdate = ctxdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@Length(min=0, max=50, message="疫苗名称长度必须介于 0 和 50 之间")
	public String getCtxvaccname() {
		return ctxvaccname;
	}

	public void setCtxvaccname(String ctxvaccname) {
		this.ctxvaccname = ctxvaccname;
	}
	
	@Length(min=1, max=10, message="0:正常状态长度必须介于 1 和 10 之间")
	public String getRstatus() {
		return rstatus;
	}

	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
}