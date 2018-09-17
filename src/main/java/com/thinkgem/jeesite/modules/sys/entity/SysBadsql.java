/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * sql上报失败Entity
 * @author liyuan
 * @version 2018-01-19
 */
public class SysBadsql extends DataEntity<SysBadsql> {
	
	private static final long serialVersionUID = 1L;
	private Date sqlTime;		// sql时间
	private String sqlContext;		// sql内容
	private String sqlStatus;		// sql状态
	
	public SysBadsql() {
		super();
	}

	public SysBadsql(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="sql时间不能为空")
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
	
	@Length(min=1, max=10, message="sql状态长度必须介于 1 和 10 之间")
	public String getSqlStatus() {
		return sqlStatus;
	}

	public void setSqlStatus(String sqlStatus) {
		this.sqlStatus = sqlStatus;
	}
	
}