/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.config;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.core.io.DefaultResourceLoader;

import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.OfficePreference;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("jeesite.properties");

	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "../userfiles/";

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取配置 带默认值
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key, String defaultValue) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : defaultValue);
		}
		return value;
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}
    
	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
//		System.out.println("userfiles.basedir: " + dir);
		return dir;
	}
	
    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
    
    /**
     * 判断是否创建留观屏推送线程
     */
    private Vector<String> observThreadList = new Vector<String>();
    
	/**
	 * 判断是否已经创建过留观屏推送线程
	 * @author fuxin
	 * @date 2017年3月22日 下午5:08:49
	 * @description 
	 * @return 已创建-true 未创建-false
	 *
	 */
	public synchronized  boolean isCreateSocketThread(String observ){
		if(observThreadList.contains(observ)){
			return true;
		}else{
			observThreadList.add(observ);
		}
		return false;
	}

	/**
	 * sql传输队列
	 */
	private LinkedBlockingQueue<String> sqlQueue = new LinkedBlockingQueue<String>();

 	public void pullSqlQueue(String sql) throws InterruptedException{
			sqlQueue.put(sql);
	}
	
	public String takeSqlQueue() throws InterruptedException{
		if(sqlQueue == null || sqlQueue.isEmpty()){
			return "";
		}
		return sqlQueue.take();
	}
	
	public boolean isEmptySqlQueue(){
		return sqlQueue.isEmpty();
	}
	
	private	Map<String, String> loginmap=new HashMap<String, String>();

	public Map<String, String> getLoginmap() {
		return loginmap;
	}

	public void setLoginmap(Map<String, String> loginmap) {
		
		this.loginmap = loginmap;
	}
	
	
	/**
	 * 缴费扫码排号功能，默认关闭
	 */
	public boolean isPayOption() {
		OfficePreference pref = OfficeService.getOfficeOption();
		return pref.isPay();
	}

	synchronized public boolean togglePayOption() {
		//获取首选项
		OfficePreference pref = OfficeService.getOfficeOption();
		//更新首选项
		pref.togglePayOption();
		OfficeService.saveOfficeOption(pref);
		//返回更新后状态
		return pref.isPay();
	}
	
	/**
	 * 一体机签字功能
	 * @author fuxin
	 * @date 2017年12月4日 下午3:12:29
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public boolean isReserveOption() {
		OfficePreference pref = OfficeService.getOfficeOption();
		return pref.isReserve();
	}
	
	synchronized public boolean toggleReserveOption() {
		//获取首选项
		OfficePreference pref = OfficeService.getOfficeOption();
		//更新首选项
		pref.toggleReserveOption();
		OfficeService.saveOfficeOption(pref);
		//返回更新后状态
		return pref.isReserve();
	}
	
	/**
	 * 微型门诊排号使用
	 * @author fuxin
	 * @date 2017年12月4日 下午3:12:46
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public boolean isQuickOption() {
		OfficePreference pref = OfficeService.getOfficeOption();
		return pref.isQuick();
	}
	
	synchronized public boolean toggleQuickOption(){
		//获取首选项
		OfficePreference pref = OfficeService.getOfficeOption();
		//更新首选项
		pref.toggleQuickOption();
		OfficeService.saveOfficeOption(pref);
		//返回更新后状态
		return pref.isQuick();
	}
	
	/**
	 * 小票打印是否使用
	 * @author zhouqj
	 * @date 2017年12月4日 下午3:12:46
	 * @description 
	 *		TODO
	 * @return
	 *
	 */
	public boolean isReceiptOption() {
		OfficePreference pref = OfficeService.getOfficeOption();
		return pref.isReceipt();
	}
	
	synchronized public boolean toggleReceiptOption(){
		//获取首选项
		OfficePreference pref = OfficeService.getOfficeOption();
		//更新首选项
		pref.toggleReceiptOption();
		OfficeService.saveOfficeOption(pref);
		//返回更新后状态
		return pref.isReceipt();
	}
	
	
	/**
	 * 排号均衡优先级
	 */
	private Vector<String> officePRJ;

	/**
	 * 项目启动是初始化所有科室数据
	 * @author fuxin
	 * @date 2017年8月21日 下午10:33:42
	 * @description 
	 * @param officePRJ
	 *
	 */
	public void setOfficePRJ(Vector<String> officePRJ) {
		this.officePRJ = officePRJ;
	}
	
	/**
	 * 多科室同时接种相同疫苗，获取排号优先级
	 * @author fuxin
	 * @date 2017年8月21日 下午10:32:17
	 * @description 
	 *		TODO
	 * @param args
	 * @return
	 *
	 */
	synchronized public String getOfficePRJ(String[] args) throws NullPointerException{
		if(args == null || args.length == 0){
			throw new NullPointerException("排号均衡查询条件不能为空");
		}
		if(officePRJ == null || officePRJ.size() == 0){
			officePRJ = new Vector<String>();
			for(String str : args){
				officePRJ.add(str);
			}
		}
		//将args转换为再优先级队列中的位置
		int[] idxs = new int[args.length];
		for(int i = 0 ;i < args.length; i ++){
			idxs[i] = -1;
			for(String s : officePRJ){
				if(s.equals(args[i])){
					idxs[i] = i;
					break;
				}
			}
		}
		
		int min = officePRJ.size() - 1;
		for(int i = 0; i < idxs.length; i ++){
			//遇到-1 将该科室放到最后一位，并返回
			if(idxs[i] == -1){
				officePRJ.add(args[i]);
				return args[i];
			}
			if(idxs[i] < min){
				min = idxs[i];
			}
		}
		//将当前科室优先级放到最末
		officePRJ.remove(min);
		officePRJ.add(args[min]);
		return args[min];
	}
	
	
	/**
	 * 签字状态
	 */
	private static Map<String, String> smap = new HashMap<String, String>();
	private static Map<String, String> hmap = new HashMap<String, String>();
	
	/**
	 * 获取签字状态 0-未成功 1-成功
	 * @author zhouqj
	 * @date 2017年7月27日 上午12:12:21
	 * @description 
	 *		TODO
	 * @param op 操作类型 put/get
	 * @param id
	 * @return
	 *
	 */
	synchronized public static String isSmap(String op, String id){
		//赋值操作
		if("put".equals(op)){
			smap.put(id, "1");
			return "";
		}
		//get操作
		if(smap.containsKey(id)){
			return smap.get(id);
		}else{
			smap.put(id, "0");
			return "0";
		}
	}
	
	/**
	 * 获取签字状态 0-未成功 1-成功
	 * @author zhouqj
	 * @date 2017年7月27日 上午12:12:21
	 * @description 
	 *		TODO
	 * @param op 操作类型 put/get
	 * @param id
	 * @return
	 *
	 */
	synchronized public static String isHmap(String op, String id){
		//赋值操作
		if("put".equals(op)){
			hmap.put(id, "1");
			return "";
		}
		//get操作
		if(hmap.containsKey(id)){
			return hmap.get(id);
		}else{
			hmap.put(id, "0");
			return "0";
		}
	}
	
	/**
	 * 最后一次完成时间,用于计算留观完成
	 */
	private Date lastFinishTime = null;

	public Date getLastFinishTime() {
		return lastFinishTime;
	}

	public void setLastFinishTime(Date lastFinishTime) {
		this.lastFinishTime = lastFinishTime;
	}
	
}
