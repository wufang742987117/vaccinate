package com.thinkgem.jeesite.common.utils;

/**
 * 
 * @author chenmaolong
 * @date 2016年3月29日 下午3:22:39
 * @description 
 *		响应json信息
 */
public class JSONMessage {
	
	/** 请求失败 */
	public static final String CODE_ERROR = "500";
	/** 拒绝访问 */
	public static final String CODE_REFUSE = "405";
	/** 请求成功 */
	public static final String CODE_SUCCESS = "200";
	/** 请求不到资源 */
	public static final String CODE_NOTFOND = "404";
	
    private String code;
    private String msg;
    private boolean result;
    private boolean success;
    private Object data;
    public JSONMessage(boolean result,String msg) {
        this.msg = msg;
        this.result = result;
        this.success=result;
    }
    public JSONMessage( boolean result,String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.result = result;
        this.success=result;
    }
    public JSONMessage( boolean result,Object data) {
        this.data = data;
        this.result = result;
        this.success=result;
    }
    public JSONMessage( boolean result,String msg,Object data) {
        this.data = data;
        this.msg = msg;
        this.result = result;
        this.success = result;
    }
    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public boolean isResult() {
        return result;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
