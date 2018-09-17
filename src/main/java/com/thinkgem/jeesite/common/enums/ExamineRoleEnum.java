package com.thinkgem.jeesite.common.enums;

/**
 * 角色枚举 Created by Administrator on 2016/8/3.
 */
public enum ExamineRoleEnum {

	SCZX_SSQSCY("sczx_ssqscy"), // 实习期审查员

	SCZX_SHJ("sczx_shj"), // 省局

	SCZX_SCY("sczx_scy"), // 审查员

	SCZX_CPFZR("sczx_cpfzr"), // 审查中心-产品负责人

	SCZX_GLY("sczx_gly"), // 管理员

	SCZX_JYJG("sczx_jyjg"), // 检验机构

	SCZX_SCZZ("sczx_sczz"), // 审查组长

	SCZX_JHBZ("sczx_jhbz"), // 审查中心-计划编制

	SCZX_SJ("sczx_sj"), // 市局

	SCZX_GCY("sczx_gcy");// 观察员

	private String value;

	private ExamineRoleEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	};
}
