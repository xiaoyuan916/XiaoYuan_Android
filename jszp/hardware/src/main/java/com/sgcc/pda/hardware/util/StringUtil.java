package com.sgcc.pda.hardware.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:GuangJie-Wang
 * @Date: 2016/4/19
 * @Time: 10:31
 */
public class StringUtil {
    private StringUtil() {
    }

    static String ipRegex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
            "\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.(" +
            "(?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    /**
     * 判断是否是合法的ip地址
     *
     * @param ip
     * @return
     */
    public static boolean isIp(String ip) {
        boolean strEmpty = isStrEmpty(ip);
        if (strEmpty) {
            return false;
        }
        Pattern pattern = Pattern.compile(ipRegex);
        Matcher matcher = pattern.matcher(ip.trim());
        boolean matches = matcher.matches();
        return matches;
    }

    /**
     * 判断String是否是null,""
     *
     * @param str
     * @return
     */
    public static boolean isStrEmpty(String str) {
        if (str == null || "".equals(str)
                || str.equalsIgnoreCase("NULL")) {
            return true;
        }
        return false;
    }

    public static String toString(Object object) {
        String value = String.valueOf(object);
        return isStrEmpty(value) ? "" : value;
    }

}
