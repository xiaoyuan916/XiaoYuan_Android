package com.sgcc.pda.hardware.protocol.p698.dataType698;

import java.text.DecimalFormat;


public class Utils {
	public static String addType(String value, int length) {
		int len = length - value.length();
		for (int i = 0; i < len; i++) {
			value = "0" + value;
		}
		return value;
	}

	// 判断字节长度（前补0）
	public static String judgeLen(String value, int length) {
		int len = length - value.length();
		for (int i = 0; i < len; i++) {
			value = "0" + value;
		}
		return value;
	}
	
	/**
	 * 
	 * @param value
	 * @param scale
	 * @return
	 */
	public static String roundByScale(double value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (scale == 0) {
			return new DecimalFormat("0").format(value);
		}
		String formatStr = "0.";
		for (int i = 0; i < scale; i++) {
			formatStr = formatStr + "0";
		}
		return new DecimalFormat(formatStr).format(value);
	} 


	/**
	 * 将字节数组转为ASCII字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToAsciiStr(byte[] bytes) {
		char[] chars = new char[bytes.length];
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			chars[i] = (char) bytes[i];
		}
		sbf.append(chars);
		return sbf.toString();
	}

}
