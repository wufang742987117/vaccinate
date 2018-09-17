package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.common.persistence.MessageResponse;

/**
 * 消息响应工具类
 * 
 * @author NGH
 *
 */
public final class MessageResponseUtils {

	public static String success = "0";
	public static String failure = "1";

	private MessageResponseUtils() {

	}

	/**
	 * 响应消息，带结果
	 * 
	 * @param status
	 *            状态
	 * @param message
	 *            信息
	 * @param result
	 *            返回结果
	 * @return
	 */
	public static MessageResponse responseMessage(final String status, final String message, final Object result) {
		return MessageResponse.newInstance().status(status).message(message).result(result);
	}

	/**
	 * 响应消息
	 * 
	 * @param status
	 *            状态
	 * @param message
	 *            信息
	 * @return
	 */
	public static MessageResponse responseMessage(final String status, final String message) {
		return responseMessage(status, message, null);
	}

	/**
	 * 失败消息
	 * 
	 * @param message
	 *            信息
	 * @return
	 */
	public static MessageResponse failure(final String message) {
		return responseMessage(failure, message);
	}

	/**
	 * 成功消息
	 * 
	 * @param message
	 *            信息
	 * @return
	 */
	public static MessageResponse success(final String message) {
		return responseMessage(success, message);
	}

	/**
	 * 失败消息，带结果
	 * 
	 * @param message
	 *            信息
	 * @param result
	 *            结果
	 * @return
	 */
	public static MessageResponse failure(final String message, final Object result) {
		return responseMessage(failure, message, result);
	}

	/**
	 * 成功消息，带结果
	 * 
	 * @param message
	 *            信息
	 * @param result
	 *            结果
	 * @return
	 */
	public static MessageResponse success(final String message, final Object result) {
		return responseMessage(success, message, result);
	}
}
