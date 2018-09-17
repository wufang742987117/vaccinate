package com.thinkgem.jeesite.modules.sys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.junl.common.http.HttpClientReq;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.BadSqlDao;
import com.thinkgem.jeesite.modules.sys.entity.BadSql;

public class ExecSqlThread implements Runnable {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String methodNme = "execSql";
	private int count = 1;
	
	private String name;
	
	public ExecSqlThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		String wsdl = Global.getConfig("uploadSql.url");
		BadSqlDao badSqlDao = SpringContextHolder.getBean(BadSqlDao.class);
		logger.info(name + "启动成功");
		String sql;
		for(;;){
			try {
				sql = Global.getInstance().takeSqlQueue();
				/*logger.info(name + "检查是否有sql");*/
				if(!"".equals(sql)){
					logger.info(name + "上传sql数据 -->" + sql);
					String result = HttpClientReq.webServiceInvoke(wsdl, methodNme, new Object[]{"3406031001",sql}, logger);
					if("1".equals(result)){
						logger.info(name + " - " + count++ + ":上传成功");
					}else{
						badSqlDao.insert(new BadSql(sql, null == result?"0":result));
						logger.error(name + " - " + count++ + ":上传失败");
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(name + "异常" + e);
			}
		}
	}
	
}
