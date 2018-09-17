package com.thinkgem.jeesite.common.utils.dwr;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  

/**
 * 
 * @author fuxin
 * @date 2017年8月9日 下午2:49:11
 * @description 
 *		TODO
 */
@SuppressWarnings("deprecation")
public class DwrUtils {
	
	private static Logger logger = LoggerFactory.getLogger(DwrUtils.class);
	
	 //创造两个MAP数据结构，一个是存放标识+ScriptSession，另一个是标识+HttpSession，HttpSession要被其他java操作，所以是public  
    private static Map<String, ScriptSession> scrSessionMap = new HashMap<String, ScriptSession>();  
    public static Map<String, HttpSession> httpSessionMap = new HashMap<String, HttpSession>();  
    //这个标识就是httpSessionId  
    public void getwebindex(String httpSessionId) {  
        //Dwr通过以下的两种方式取得ScriptSession与HttpSession  
        ScriptSession sessions = WebContextFactory.get().getScriptSession();  
        HttpSession httpSession = WebContextFactory.get().getHttpServletRequest().getSession();           
        httpSessionMap.put(httpSessionId, httpSession);  
        scrSessionMap.put(httpSessionId, sessions);  
        //再把HttpSession打回给waitforscan.jsp，这个HttpSession与waitforscan.jsp里面利用jsp生成的HttpSession相同  
        Util utilAll = new Util(sessions);  
        utilAll.addFunctionCall("recid", httpSessionId);  
    }  

    public static void sendSign(String to, String msg) {  
        //这里是对于Scanhandle.java传过来消息进行处理，其实无论传递过来什么，都可以告诉waitforscan.jsp要跳转，这个msg是没有意义的  
        ScriptSession sessions = scrSessionMap.get(to);  
        Util utilAll = new Util(sessions);  
        utilAll.addFunctionCall("ifSignatureSuccess", msg);  
    }
    
    /**
     * 批量发送到以start开头的页面中
     * @author fuxin
     * @date 2017年10月19日 下午7:10:26
     * @description 
     *		TODO
     * @param string
     * @param waitNum
     *
     */
	public static void sendStartWith(String start, int msg) {
		for(Entry<String, ScriptSession> en : scrSessionMap.entrySet()){
			if(en.getKey().startsWith(start)){
				Util utilAll = new Util(en.getValue());  
		        utilAll.addFunctionCall("queneWaitNum", msg);  
			}
		}
		
	}  
	
	/**
     * 支付调用
     * @author zhouqj
     * @date 2017年9月9日 下午2:11:15
     * @description 
     *		TODO
     * @param to
     * @param msg
     * @return boolean 推送成功/推送失败
     *
     */
    public static boolean send(String to, String msg){
    	try {
    		//这里是对于Scanhandle.java传过来消息进行处理，其实无论传递过来什么，都可以告诉waitforscan.jsp要跳转，这个msg是没有意义的  
            ScriptSession sessions = scrSessionMap.get(to);  
            Util utilAll = new Util(sessions);  
            utilAll.addFunctionCall("ifPaySuccess", msg);  
		} catch (Exception e) {
			logger.error("[DwrUtils-send]DWR推送失败", e);
			return false;
		}
       return true;
    }  
    
    /**
     * 签字调用
     * @author zhouqj
     * @date 2017年9月9日 下午2:10:44
     * @description 
     *		TODO
     * @param to
     * @param msg
     *
     */
    public static void sendSignture(String to, String msg) {
        //这里是对于Scanhandle.java传过来消息进行处理，其实无论传递过来什么，都可以告诉waitforscan.jsp要跳转，这个msg是没有意义的  
        ScriptSession sessions = scrSessionMap.get(to);  
        Util utilAll = new Util(sessions);  
        utilAll.addFunctionCall("ifSigntureSuccess", msg);  
    }  
    
    /**
     * 签字调用
     * @author zhaojing
     * @date 2017年11月1日
     * @description 
     *		TODO
     * @param to
     * @param msg
     *
     */
    public static void send(String to, String msg,String function) {
    	//这里是对于Scanhandle.java传过来消息进行处理，其实无论传递过来什么，都可以告诉waitforscan.jsp要跳转，这个msg是没有意义的  
    	ScriptSession sessions = scrSessionMap.get(to);  
    	Util utilAll = new Util(sessions);  
    	utilAll.addFunctionCall(function, msg);  
    }  

}
