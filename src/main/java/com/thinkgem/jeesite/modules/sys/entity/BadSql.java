/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * badsql
 * 
 * @author fuxin
 */
public class BadSql extends DataEntity<BadSql> {

	private static final long serialVersionUID = 1L;

	/** 状态-用户信息判断*/
	public static final String STATUS_NOTEXEC = "-1";
	/** 状态-执行失败  */
	public static final String STATUS_EXECED = "0";
	
	private Date sqlTime;
	private String sqlContext;
	private String sqlStatus;

	public BadSql() {
		super();
	}

	public BadSql(String sqlContext, String sqlStatus) {
		this.sqlContext = sqlContext;
		this.sqlTime = new Date();
		this.sqlStatus = sqlStatus;
	}

	public Date getSqlTime() {
		return sqlTime;
	}

	public void setSqlTime(Date sqlTime) {
		this.sqlTime = sqlTime;
	}

	public String getSqlContext() {
		return sqlContext;
	}

	public void setSqlContext(String sqlContext) {
		this.sqlContext = sqlContext;
	}

	public String getSqlStatus() {
		return sqlStatus;
	}

	public void setSqlStatus(String sqlStatus) {
		this.sqlStatus = sqlStatus;
	}

}