/*
 * 
 */
package com.thinkgem.jeesite.common.persistence;

import java.io.Serializable;

/**
 * 消息响应
 * 
 * @author ngh
 * @datetime [2015年12月30日 下午2:49:56]
 * 
 */
public final class MessageResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8307101666652261203L;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 消息内容
	 */
	private String message;

	/**
	 * 返回结果
	 */
	private Object result;

	/**
	 * 
	 */
	private MessageResponse() {
	}

	/**
	 * @param status
	 * @param message
	 * @param result
	 */
	private MessageResponse(String status, String message, Object result) {
		super();
		this.status = status;
		this.message = message;
		this.result = result;
	}

	/**
	 * 创建消息
	 * 
	 * @return MessageResponse
	 */
	public static MessageResponse newInstance() {
		return newInstance(null, null, null);
	}

	/**
	 * 创建消息
	 * 
	 * @param status
	 *            消息码
	 * @param message
	 *            消息内容
	 * @return MessageResponse
	 */
	public static MessageResponse newInstance(String status, String message) {
		return newInstance(status, message, null);
	}

	/**
	 * 创建消息
	 * 
	 * @param status
	 *            消息码
	 * @param message
	 *            消息内容
	 * @param result
	 *            参数
	 * @return MessageResponse
	 */
	public static MessageResponse newInstance(String status, String message, Object result) {
		return new MessageResponse(status, message, result);
	}

	/**
	 * 设置消息码
	 * 
	 * @param status
	 *            消息码
	 * @return 当前实例
	 */
	public MessageResponse status(String status) {
		this.status = status;
		return this;
	}

	/**
	 * 设置消息内容
	 * 
	 * @param message
	 *            消息内容
	 * @return 当前实例
	 */
	public MessageResponse message(String message) {
		this.message = message;
		return this;
	}

	/**
	 * 设置消息附加信息
	 * 
	 * @param result
	 *            附加信息
	 * @return 当前实例
	 */
	public MessageResponse result(Object result) {
		this.result = result;
		return this;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

}
