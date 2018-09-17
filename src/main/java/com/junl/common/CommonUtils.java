package com.junl.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinkgem.jeesite.common.mapper.JsonMapper;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

/**
 * 
 * @Class CommonUtils
 * @author LEON
 * @Date 2014-3-21 下午4:32:30
 * @Description
 * 		一些工具类
 *
 */
public class CommonUtils {
	
	
	public static final Log logger = LogFactory.getLog(CommonUtils.class);
	
	/**
	 * 
	 * @Method UUIDGenerator
	 * @author LEON
	 * @Date 2014-3-21 下午4:33:58
	 * @Description
	 * 		uuid 生成器
	 *
	 * @return
	 */
	public static String UUIDGenerator() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id.replace("-", "");
	}
	
	/** 
	* Html转换为TextArea文本:编辑时拿来做转换 
	* @author zhengxingmiao 
	* @param str 
	* @return 
	*/  
	public static String html2Text(String str) {  
		if (str == null) {  
		return "";  
		}else if (str.length() == 0) {  
		return "";  
		}  
		str = str.replaceAll("<br />", "\n");  
		str = str.replaceAll("<br />", "\r");  
		return str;  
	}  
	  
	/** 
	* TextArea文本转换为Html:写入数据库时使用 
	* @author zhengxingmiao 
	* @param str 
	* @return 
	*/  
	public static String text2Html(String str) {  
		if (str == null) {  
		return "";  
		}else if (str.length() == 0) {  
		return "";  
		}  
		str = str.replaceAll("\n", "<br />");  
		str = str.replaceAll("\r", "<br />");  
		return str;  
	}
	
	
	/**
	 * 
	 * @method getProperties
	 * @author LEON
	 * @date 2015年8月31日 下午1:36:15
	 * @description
	 *		 获取配置文件
	 *
	 * @param inputstream
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> getProperties(InputStream inputstream) {
		
		Map<String, String> propertyMap = null;
		try {
			Properties properties = new Properties();
			properties.load(inputstream);
			propertyMap = (Map) properties;
			
			inputstream.close();
			return propertyMap;
		} catch (Exception e) {
			logger.error("加载配置文件出错");
			e.printStackTrace();
			try {
				inputstream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				inputstream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return propertyMap;

	}
	
	/**
	 * 
	 * @Method randomGenerator
	 * @author chenmaolong
	 * @date 2015年9月9日 下午6:14:53
	 * @Description
	 * 		random 生成器
	 *
	 * @return
	 */
	public static String randomGenerator() {
	    double random = Math.random()*89999999+10000000;
		
		return String.valueOf(Math.round(random));
	}
	
	public static String randomData(){
		int max = 9999;
		int min = 1000;
		Random ran = new Random();
		int re = ran.nextInt(max)%(max - min + 1) + min;
		
		return String.valueOf(re);
	}
	/**
	 * 
	 * @author chenmaolong
	 * @date 2016年5月4日 下午6:35:02
	 * @description 
	 *		xml字符串转换为json字符串
	 * @param xmlString
	 * @return
	 *
	 */
	public static String getJsonStringFromXML(String xmlString){
		// xml转json
		XMLSerializer xmlSerializer = new XMLSerializer();  
        JSON json = xmlSerializer.read(xmlString);
        String jsonxml = json.toString();
        
        return jsonxml;
	}
	
	/**
	 * 根据某个属性，将list拆分为map
	 * @author fuxin
	 * @date 2018年1月26日 下午6:44:19
	 * @description 
	 *		TODO
	 * @param clazz
	 * @param objs
	 * @param param
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	public static  <T> Map<Object, List<T>> getTreeDateByParam(Class<T> clazz, List<T> objs, String param){
		Map<Object, List<T>> map = new LinkedMap();
		if(clazz == null || objs == null || objs.size() == 0 || param == null){
			return map;
		}
		try {
			Method methd = clazz.getMethod("get" + param.substring(0,1).toUpperCase() + param.substring(1));
			for(T t : objs){
				Object result = methd.invoke(t);
				if(map.containsKey(result)){
					map.get(result).add(t);
				}else{
					List<T> temp = new ArrayList<T>();
					temp.add(t);
					map.put(result, temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
	public static void main(String[] args) {
//		System.out.println(UUIDGenerator());
//		System.out.println(randomData());
		
		List<Quene> list1 = new ArrayList<Quene>();
		list1.add(new Quene("1", "1"));
		list1.add(new Quene("2","2"));
		list1.add(new Quene("1","3"));
		list1.add(new Quene("1","4"));
		list1.add(new Quene("2","5"));
		Map<Object, List<Quene>> map = getTreeDateByParam(Quene.class, list1, "id");
		System.out.println(map.size());
		
	}
}

class Quene{
	private String id;
	private String queneno;
	
	public Quene() {
		super();
	}
	public Quene(String id, String queneno) {
		super();
		this.id = id;
		this.queneno = queneno;
	}
	public String getId() {
		return id;
	}
	public String getQueneno() {
		return queneno;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setQueneno(String queneno) {
		this.queneno = queneno;
	}
}
