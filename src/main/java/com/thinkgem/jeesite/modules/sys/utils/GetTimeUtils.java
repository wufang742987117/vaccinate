package com.thinkgem.jeesite.modules.sys.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 获取时间Utils类
 * @author Jack
 * @date 2017年11月1日 下午6:45:43
 * @description 
 */
public class GetTimeUtils {
	
    /**
     * 通过输入的年和月获取该月上一个月第一天
     * @author Jack
     * @date 2017年11月1日 下午6:48:45
     * @description 
     * @param year 指定的年份
     * @param month 指定的月份
     * @return
     *
     */
    public static String getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
       return new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
    } 
	
	/**
	 * 通过输入的年和月获取该月上一个月最后一天
	 * @author Jack
	 * @date 2017年11月1日 下午6:46:25
	 * @description
	 * @param year int,指定的年份
	 * @param month int,指定的月份
	 * @return
	 * 注意:public static final int MONTH;中MONTH常量是从0开始的<br>
	 * 计算月份时请减一,如此处传入为5实际得到的是4月的最后一天
	 */
    public static String getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
       return new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
    }   
    
}
