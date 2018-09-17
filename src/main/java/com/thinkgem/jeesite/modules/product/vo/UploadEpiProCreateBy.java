package com.thinkgem.jeesite.modules.product.vo;

import java.io.Serializable;

public class UploadEpiProCreateBy implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	
	public UploadEpiProCreateBy() {
		super();
	}

	public UploadEpiProCreateBy(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}