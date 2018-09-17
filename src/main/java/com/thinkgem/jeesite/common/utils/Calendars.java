package com.thinkgem.jeesite.common.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;

/**
 * 中国日历操作类
 * 
 * @author ngh
 * @datetime [2016年8月11日 下午4:57:57]
 *
 */
public final class Calendars {

	private static final String MMdd = "MMdd";

	// 节假日集合，格式 MMdd
	private static Set<String> holidays = Sets.newHashSet();

	static {
		refreshHolidays();
	}

	/**
	 * 刷新法定节假日
	 */
	public synchronized static void refreshHolidays() {
		String year = DateUtils.getYear();
		String json = Http.get(Global.getConfig("holidays.url") + "?d=" + DateUtils.getYear(),
				ImmutableMap.of("apikey", Global.getConfig("apikey")));
		try {
			JsonMapper jm = JsonMapper.getInstance();
			JsonNode jn = jm.readTree(json).withArray(year);
			holidays = jm.convertValue(jn, new TypeReference<Set<String>>() {});
		} catch (IOException e) {

		}
	}

	/**
	 * 计算当前日期之后一定天数内不包含法定节假日的结束日期
	 * 
	 * @param start
	 *            开始日期
	 * @param days
	 *            距离开始日期不包含法定节假日的天数
	 * @return 结束日期
	 */
	public static Date calculateLastDayWithoutHolidays(Date start, int days) {
		Date lastDay = start;
		do {
			String currentMMddStr = DateFormatUtils.format(lastDay, MMdd);
			if (!holidays.contains(currentMMddStr)) {
				--days;
			}
			lastDay = DateUtils.addDays(lastDay, 1);
		} while (days != 0);
		return lastDay;
	}
}
