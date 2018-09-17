package com.thinkgem.jeesite.modules.vaccine.entity;

import java.io.Serializable;

import com.thinkgem.jeesite.common.utils.StringUtils;

public class ImportContext implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private String choose;
	private String group;

	public String getTitle() {
		if(StringUtils.isBlank(title)){
			return "";
		}
		return title;
	}

	public String getChoose() {
		if(StringUtils.isBlank(choose)){
			return "";
		}
		return choose;
	}

	public String getGroup() {
		return group;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
