package com.thinkgem.jeesite.modules.charge.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 数字转换为汉语中人民币的大写
 */
public class ChargePrint {
	/**
	 * 汉语中数字大写
	 */
	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	/**
	 * 汉语中货币单位大写
	 */
	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟" };
	/**
	 * 特殊字符：整
	 */
	private static final String CN_FULL = "整";
	/**
	 * 特殊字符：负
	 */
	private static final String CN_NEGATIVE = "负";
	/**
	 * 金额的精度，默认值为2
	 */
	private static final int MONEY_PRECISION = 2;
	/**
	 * 特殊字符：零元整
	 */
	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

	/**  
	* zhaojing
	* 2017年11月2日  下午1:26:36
	* 获取发票打印的字符串
	*/  
	@SuppressWarnings("unchecked")
	public static String getPrintStr(Map<String, Object> data) {
		ArrayList<Map<String, String>> vaccs = (ArrayList<Map<String, String>>) data.get("vacc");
		if (vaccs == null || vaccs.size() <= 0) {
			return "";
		}
		StringBuffer printStr = new StringBuffer("open,");
		printStr.append(String.valueOf(data.get("billNum")) + "|150|80,");
		printStr.append(String.valueOf(data.get("patientName")) + "|100|105,");
		printStr.append(String.valueOf(data.get("patientType")) + "|350|105,");
		for (int i = 0; i < vaccs.size(); i++) {
			Map<String, String> vacc = vaccs.get(i);
			printStr.append("【" + String.valueOf(vacc.get("type")) + "】" 
					+ String.valueOf(vacc.get("vaccName")) + "      " 
					+ String.valueOf(vacc.get("vaccCount")) + "      "
					+ String.valueOf(vacc.get("vaccPrice")) + "|120|");
			printStr.append((170 + 30 * i) + ",");
		}
		String pay = String.valueOf(data.get("receivables"));
		BigDecimal bd = new BigDecimal(pay);
		bd = bd.setScale(2, BigDecimal.ROUND_UP);
		printStr.append("就诊卡支付:0.00       ");
		printStr.append("现金支付：" + pay + "      ");
		printStr.append("就诊卡支付:0.00");
		printStr.append("|120|360,");
		printStr.append(number2CNMontrayUnit(bd) + "|150|390,");
		printStr.append(pay + "|560|390,");
		printStr.append(UserUtils.getUser().getName() + "|480|450,");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		printStr.append(calendar.get(Calendar.YEAR) + "    " + (calendar.get(Calendar.MONTH) + 1) + "    " + calendar.get(Calendar.DAY_OF_MONTH) + "|570|450");
		return printStr.toString();
	}

	/**  
	* zhaojing
	* 2017年11月2日  下午1:27:12
	* 把输入的金额转换为汉语中人民币的大写
	* @param numberOfMoney
	*/  
	public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
		StringBuffer sb = new StringBuffer();
		int signum = numberOfMoney.signum();
		// 零元整的情况
		if (signum == 0) {
			return CN_ZEOR_FULL;
		}
		// 这里会进行金额的四舍五入
		long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
		// 得到小数点后两位值
		long scale = number % 100;
		int numUnit = 0;
		int numIndex = 0;
		boolean getZero = false;
		// 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
		if (!(scale > 0)) {
			numIndex = 2;
			number = number / 100;
			getZero = true;
		}
		if ((scale > 0) && (!(scale % 10 > 0))) {
			numIndex = 1;
			number = number / 10;
			getZero = true;
		}
		int zeroSize = 0;
		while (true) {
			if (number <= 0) {
				break;
			}
			// 每次获取到最后一个数
			numUnit = (int) (number % 10);
			if (numUnit > 0) {
				if ((numIndex == 9) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
				}
				if ((numIndex == 13) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
				}
				sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				getZero = false;
				zeroSize = 0;
			} else {
				++zeroSize;
				if (!(getZero)) {
					sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				}
				if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			// 让number每次都去掉最后一个数
			number = number / 10;
			++numIndex;
		}
		// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
		if (signum == -1) {
			sb.insert(0, CN_NEGATIVE);
		}
		// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
		if (!(scale > 0)) {
			sb.append(CN_FULL);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		double money = 2020004.01;
		BigDecimal numberOfMoney = new BigDecimal(money);
		String s = ChargePrint.number2CNMontrayUnit(numberOfMoney);
		System.out.println("你输入的金额为：【" + money + "】   #--# [" + s.toString() + "]");
	}
}