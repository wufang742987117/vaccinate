package com.thinkgem.jeesite.modules.sys.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.service.ExecSqlThread;
import com.thinkgem.jeesite.modules.sys.service.SystemService;


public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}

	@Override
	public void contextInitialized(ServletContextEvent event){
		super.contextInitialized(event);
		int threadNum = 0;
		String threadNumStr = Global.getConfig("dataSyncThreadNum");
		try {
			threadNum = Integer.parseInt(threadNumStr);
		} catch (Exception e) {
			logger.error("数据同步线程数配置失败，默认值为0");
		}
		logger.info("数据同步线程数数为：" + threadNum);
		for(int i = 0;i < threadNum; i++){
			new Thread(new ExecSqlThread("数据同步线程" + (i+1)),"UpLoadSqlThread"+(i+1)).start();
		}
	}	
}
