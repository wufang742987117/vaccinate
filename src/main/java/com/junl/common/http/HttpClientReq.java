package com.junl.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class HttpClientReq {
	/** 日志 */
	protected static Logger log = LoggerFactory.getLogger(HttpClientReq.class);
	
	private static final int connectTimeout = 10000;  
	private static final int soTimeout = 10000;  
	public static String httpClientPost(String url,Map<String,String> postData){
		//创建默认的 HttpClient 实例  
        HttpClient httpClient = new DefaultHttpClient();
        //连接超时
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), connectTimeout);
        //请求超时
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), soTimeout);
        
        HttpPost httpPost = new HttpPost(url);  
  
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();  
        for(String key:postData.keySet()){
        	formParams.add(new BasicNameValuePair(key, postData.get(key)));  
        } 
        UrlEncodedFormEntity urlEncodedFormEntity;  
  
        try {  
            urlEncodedFormEntity = new UrlEncodedFormEntity(formParams, "UTF-8");  
            httpPost.setEntity(urlEncodedFormEntity);   
            HttpResponse httpResponse = null;  
            httpResponse = httpClient.execute(httpPost);  
            HttpEntity httpEntity = (HttpEntity) httpResponse.getEntity();  
            if (httpEntity != null) {  
                String content = EntityUtils.toString(httpEntity, "UTF-8");  
                return content;
            } 
            
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();
            log.error("========微信接口连接异常[httpClientPost]=================httpsUrl:{}",url);
        } finally {  
            //关闭连接，释放资源  
            httpClient.getConnectionManager().shutdown();  
        }  
        return "";
	}
	
	
	/**
	 * 发送json
	 * @author fuxin
	 * @date 2017年3月25日 下午5:49:45
	 * @description 
	 *		TODO
	 * @param url
	 * @param json
	 * @return  String类型返回值
	 *
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public static String httpClientPostJson(String url,String json){
		  log.info("发送post请求 url:" + url + "    json:" + json);
		  HttpClient client = new DefaultHttpClient();    
		  HttpPost post = new HttpPost(url);    
		  try {    
		      StringEntity s = new StringEntity(json.toString(), "UTF-8");    
		      s.setContentEncoding("UTF-8");
		      s.setContentType("application/json");    
		      post.setEntity(s);    
		           
		       HttpResponse res = client.execute(post);    
		       if(res.getStatusLine().getStatusCode() == HttpStatus.OK.value()){    
		           HttpEntity entity = res.getEntity();    
		           String charset = EntityUtils.getContentCharSet(entity);   
		           String result = EntityUtils.toString(entity, charset);
		           log.info("post请求发送成功--> " + result);
		           return  result;
		       }else{
		    	   log.error("post请求发送失败" + res.getStatusLine().getStatusCode());
		       }
		   } catch (Exception e) {    
			   log.error("post请求发送失败" + e);
		      return "";
		   }
		   return "";    
	}
	
	/**
	 * 发送json
	 * @author zhouqj
	 * @date 2017年4月27日 上午11:17:51
	 * @description 
	 *		TODO
	 * @param url
	 * @param json
	 * @return
	 *
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public static String httpClientPostJsonIn(String url,String json){
		log.info("发送post请求 url:" + url + "    json:" + json);
		  HttpClient client = new DefaultHttpClient();    
		  HttpPost post = new HttpPost(url);    
		  try {    
		      StringEntity s = new StringEntity(json.toString(), "UTF-8");    
		      s.setContentEncoding("UTF-8");
		      s.setContentType("application/json");    
		      post.setEntity(s);    
		           
		       HttpResponse res = client.execute(post);    
		       if(res.getStatusLine().getStatusCode() == HttpStatus.OK.value()){    
		           HttpEntity entity = res.getEntity();    
		           String result = EntityUtils.toString(entity, "GBK") ;
		           log.info("post请求发送成功--> " + result);
		           return result;
		       }else{
		    	   log.error("post请求发送失败" + res.getStatusLine().getStatusCode());
		       }
		   } catch (Exception e) {    
			   log.error("post请求发送失败" + e);
		      return "";
		   }
		   return "";    
	}
	
	/**
	 * @description 得到查询结果集的ＸＭＬ文件
	 * @param wsdl需要访问的WebService
	 *            的URL地址
	 * @param encodingStyle访问时头文件的格式
	 * @param methodNme
	 *            需要调用的方法名称
	 * @param parameterName
	 *            需要的参数的名称
	 * @param parameterValue
	 *            需要的参数的 值
	 * @return 得到的结果集的xml
	 * 
	 */
	public static String webServiceInvoke(String wsdl, String methodNme, Object[] obj, Logger logger) {

		logger.info("==========开始调用wsdl接口========== \r\n methodNme=" + methodNme + ",obj=" + JsonMapper.toJsonString(obj) + ",url=" + wsdl);
		String errorText = null;

		// 返回结果的XML字符串
		String retuenStr = null;
		// 创建一个WebService对象
		Service service = new Service();
		// 得到一个client 的 call 对象
		Call call = null;
		try {
			// 实例化call对象
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			errorText = "创建WebService对象出现异常";

		}
		try {
			// 调用call对象的设置参数 URL
			call.setTargetEndpointAddress(new java.net.URL(wsdl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			errorText = "WebService对象的设置参数出现异常";
		}

		call.setOperationName(new QName(wsdl, methodNme));
		try {
			retuenStr = (String) call.invoke(obj);
		} catch (RemoteException e) {
			e.printStackTrace();
			errorText = "WebService调用出现异常";
		}
		if(StringUtils.isNotBlank(errorText)){
			log.error(errorText);
		}
		logger.info("==========调用wsdl接口结束========== \r\n methodNme=" + methodNme + ",obj=" + JsonMapper.toJsonString(obj) + ",url=" + wsdl
				 + ",errorText=" + errorText + ",returnStr=" + retuenStr);
		return retuenStr;
	}
	
	public static  String webServiceInvoke(String methodNme, Object[] obj, Logger logger) {

		logger.info("==========开始调用wsdl接口========== \r\n methodNme=" + methodNme + ",obj=" + JsonMapper.toJsonString(obj) + ",url=" + Global.getConfig("query.url"));
		String errorText = null;

		// 返回结果的XML字符串
		String returnStr = null;
		// 创建一个WebService对象
		Service service = new Service();
		// 得到一个client 的 call 对象
		Call call = null;
		try {
			// 实例化call对象
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			errorText += "创建WebService对象出现异常,";
		}
		try {
			// 调用call对象的设置参数 URL
			call.setTargetEndpointAddress(new java.net.URL(Global.getConfig("query.url")));
		} catch (MalformedURLException e) {
			errorText += "WebService对象的设置参数出现异常,";

		}
		call.setOperationName(new QName(Global.getConfig("query.url"), methodNme));
		try {
			returnStr = call.invoke(obj).toString();
		} catch (Exception e) {
			errorText += "WebService调用出现异常";
		}
		if(StringUtils.isNotBlank(errorText)){
			log.error(errorText);
		}
		logger.info("==========调用wsdl接口结束========== \r\n methodNme=" + methodNme + ",obj=" + JsonMapper.toJsonString(obj) + ",url=" + Global.getConfig("query.url")
				 + ",errorText=" + errorText + ",returnStr=" + returnStr);
		return returnStr;

	}
	
}
