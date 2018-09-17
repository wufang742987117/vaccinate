package com.thinkgem.jeesite.common.utils;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 * Http 请求类
 * 
 * @author ngh
 * @datetime [2016年6月17日 下午2:36:51]
 *
 */
public final class Http {

	private Http() {
	}

	/**
	 * get请求
	 * 
	 * @param uri
	 *            请求url
	 * @param header
	 *            请求头参数
	 * @return 请求响应结果
	 */
	public static String get(final String uri, final Map<String, String> header) {
		Request request = Request.Get(uri);
		if (MapUtils.isNotEmpty(header)) {
			for (Entry<String, String> entry : header.entrySet()) {
				request.setHeader(entry.getKey(), entry.getValue());
			}
		}
		try {
			return request.execute().returnContent().asString(Charset.defaultCharset());
		} catch (Exception e) {
		}
		return StringUtils.EMPTY;
	}

	/**
	 * post请求
	 * 
	 * @param uri
	 *            请求url
	 * @param header
	 *            请求头参数
	 * @param body
	 *            请求主体参数
	 * @return 请求响应结果
	 */
	public static String post(final String uri, final Map<String, String> header, final Map<String, String> body) {
		Request request = Request.Post(uri);
		if (MapUtils.isNotEmpty(header)) {
			for (Entry<String, String> entry : header.entrySet()) {
				request.setHeader(entry.getKey(), entry.getValue());
			}
		}
		if (MapUtils.isNotEmpty(body)) {
			Form form = Form.form();
			for (Entry<String, String> entry : body.entrySet()) {
				form.add(entry.getKey(), entry.getValue());
			}
			request.bodyForm(form.build(), Charset.defaultCharset());
		}
		try {
			return request.execute().returnContent().asString(Charset.defaultCharset());
		} catch (Exception e) {
		}
		return StringUtils.EMPTY;
	}

}
