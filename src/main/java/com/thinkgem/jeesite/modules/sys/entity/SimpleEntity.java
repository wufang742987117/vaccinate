package com.thinkgem.jeesite.modules.sys.entity;

import java.io.Serializable;
import java.util.List;

import com.thinkgem.jeesite.common.mapper.JsonMapper;

public class SimpleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	String str;
	String json;
	String str2;
	Object obj;
	List<?> list;
	int i;

	public SimpleEntity(String str, List<?> list) {
		super();
		this.str = str;
		this.list = list;
		this.json = JsonMapper.toJsonString(list);
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getJson() {
		return json;
	}

	
	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public List<?> getList() {
		return list;
	}
		

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

}
