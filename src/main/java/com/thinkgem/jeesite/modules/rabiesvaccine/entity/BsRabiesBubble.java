package com.thinkgem.jeesite.modules.rabiesvaccine.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class BsRabiesBubble extends DataEntity<BsRabiesBubble> {
	
	private static final long serialVersionUID = 1L;
	private String username; // 姓名
	private String rabiescode;//犬伤编码
	private String type;//气泡类别
	private Date createDate;
	
	public BsRabiesBubble() {
		super();
	}
	public BsRabiesBubble(String id) {
		super(id);
	}
	
	@Length(min = 0, max = 50, message = "姓名长度必须介于 0 和 50 之间")
	@ExcelField(title = "姓名", align = 1, sort = 10)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title = "犬伤编号", align = 1, sort = 1)
	public String getRabiescode() {
		return rabiescode;
	}
	
	public void setRabiescode(String rabiescode) {
		this.rabiescode = rabiescode;
	}
	
	@ExcelField(title = "气泡类别", align = 1, sort = 1)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
