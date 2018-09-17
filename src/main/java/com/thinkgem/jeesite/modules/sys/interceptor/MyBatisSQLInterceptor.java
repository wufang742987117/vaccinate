package com.thinkgem.jeesite.modules.sys.interceptor;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
/*import org.apache.ibatis.mapping.BoundSql;*/  
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.thinkgem.jeesite.common.config.Global;
   
/** 
 * 拦截StatementHandler里的 prepare方法把执行的sql进行记录到文件里 
 * @author panguixiang 
 * 
 */  
@Intercepts({
@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
@Signature(type = Executor.class, method = "query", args = {  
        MappedStatement.class, Object.class, RowBounds.class,  
        ResultHandler.class })
}) 
public class MyBatisSQLInterceptor implements Interceptor {  
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private final String[] keyWord = {" BS_CHILD_",
										" BS_MANAGE_VACCINE",
										" BS_MANAGE_VACCINE_MODEL",
										" BS_MANAGE_VACCINENUM",
										" SYS_VACC_DEPARTMENT",
										" BS_RABIES_CHECKIN",
										" BS_RABIES_NUM",
										" BS_HEPATITIS_B_NUM",
										" BS_HEPATITIS_BCHECKIN",
										" VAC_JOB_REMIND",
										" VAC_CHILD_REMIND",
										" SYS_WORKING_HOURS"
									};
	private final String[] exkeyWord = {"BS_CHILD_SIGNATURE", "BS_RABIES_SIGNATURE", "BS_HEPATITIS_B_SIGNATURE"};
	
	@SuppressWarnings("unused")
	private Properties properties;
	 
	    public Object intercept(Invocation invocation) throws Throwable {
	        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
	        Object parameter = null;
	        if (invocation.getArgs().length > 1) {
	            parameter = invocation.getArgs()[1];
	        }
	        String sqlId = mappedStatement.getId();
	        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
	        Configuration configuration = mappedStatement.getConfiguration();
	        Object returnValue = null;
	        long start = System.currentTimeMillis();
	        returnValue = invocation.proceed();
	        long end = System.currentTimeMillis();
	        long time = (end - start);
//	        if (time > 1) {
	            String sql = getSql(configuration, boundSql, sqlId, time);
	            if(checkSql(sql)){
	            	logger.info("###微信数据同步sql--> " + sql);
	            	//将sql放入队列
	            	Global.getInstance().pullSqlQueue(sql);
	            }
//	        }
	        return returnValue;
	    }
	 
	    private boolean checkSql(String sql) {
	    	logger.debug("==showsql========  " + sql);
	    	if(null != sql){
	    		sql = sql.substring(0,sql.length()> 50?50:sql.length()).trim().toUpperCase();
		    	if(sql.startsWith("INSERT") || sql.startsWith("UPDATE") || sql.startsWith("DELETE")){
		    		for(String str:keyWord){
		    			if(sql.contains(str)){
		    				for(String exstr:exkeyWord){
		    					if(sql.contains(exstr)){
		    						return false;
		    					}
		    				}
		    				return true;
		    			}
		    		}
		    	}
	    	}
			return false;
		}

		public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId, long time) {
	        String sql = showSql(configuration, boundSql);			
	        StringBuilder str = new StringBuilder();
	        sql = HtmlUtils.htmlUnescape(sql);
	        str.append(sql);
	        return str.toString();
	    }
	 
	    private static String getParameterValue(Object obj) {
	        String value = null;
	        if (obj instanceof String) {
	            value = "'" + obj.toString() + "'";
	        } else if (obj instanceof Date) {
	            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
	            value = "\"TO_DATE\"('" + formatter.format(obj) + "', 'yyyy-mm-dd hh24:mi:ss') ";
	        } else {
	            if (obj != null) {
	                value = obj.toString();
	            } else {
	                value = "null";
	            }
	 
	        }
	        return value;
	    }
	 
	    public static String showSql(Configuration configuration, BoundSql boundSql) {
	        Object parameterObject = boundSql.getParameterObject();
	        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
	        if (parameterMappings.size() > 0 && parameterObject != null) {
	            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
	            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
	                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
	 
	            } else {
	                MetaObject metaObject = configuration.newMetaObject(parameterObject);
	                for (ParameterMapping parameterMapping : parameterMappings) {
	                    String propertyName = parameterMapping.getProperty();
	                    if (metaObject.hasGetter(propertyName)) {
	                        Object obj = metaObject.getValue(propertyName);
	                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
	                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
	                        Object obj = boundSql.getAdditionalParameter(propertyName);
	                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
	                    }
	                }
	            }
	        }
	        return sql;
	    }
	 
	    @Override
	    public Object plugin(Object target) {
	        return Plugin.wrap(target, this);
	    }
	 
	    @Override
	    public void setProperties(Properties properties0) {
	        this.properties = properties0;
	    }
}  