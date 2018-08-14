package com.sgcc.pda.hardware.util;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * @author WangJinKe
 * @version: 1.0
 * @CreateTime 2015年4月1日 下午2:45:51
 * @Description:时间处理
 */

public class DateUtil {


    private DateUtil() {
    }

    static SimpleDateFormat dateformat;

    public static class DatePattern {
        public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
        public static final String HM = "HH:mm";
        public static final String DHM = "DD HH:mm";
    }

    /**
     * 获取时分秒
     *
     * @return
     */
    public static String getHourTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time = format.format(new Date());
        return time;
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        return time;
    }


    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        String time = format.format(new Date()) + "0" + week;
        return time;
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getTime(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(str);
        return time;
    }

    /**
     * 获取时间  yy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String get2YTime() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        return time;
    }

    /**
     * 获取时间  yy-MM-dd
     *
     * @return
     */
    public static String get2YDTime() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        String time = format.format(new Date());
        return time;
    }

    /**
     * 得到前n天的日期
     *
     * @return
     */
    public static String getOneDayTime(int index) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        Date d1 = sdf.parse(get2YDTime(), new ParsePosition(0));

        long m1 = d1.getTime();
        long m2 = m1 - (long) (index * (1000 * 3600 * 24));
        Date d2 = new Date(m2);
        return sdf.format(d2);
    }

    /**
     * 得到前n天的日期
     *
     * @return
     */
    public static String getOneDayTime(String dataDate, int index) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        Date d1 = sdf.parse(dataDate, new ParsePosition(0));

        long m1 = d1.getTime();
        long m2 = m1 - (long) (index * (1000 * 3600 * 24));
        Date d2 = new Date(m2);
        return sdf.format(d2);
    }


    /**
     * 获取某个时间的后一天
     *
     * @param date1 某个时间值  yy-MM-dd
     * @param index 天数
     * @return
     */
    public static String getLastOneDayTime(String date1, int index) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        Date d1 = sdf.parse(date1, new ParsePosition(0));
        long m1 = d1.getTime();
        long m2 = m1 + (long) (index * (1000 * 3600 * 24));
        Date d2 = new Date(m2);
        return sdf.format(d2);
    }


    /**
     * 获取当前时间 yyyy-MM-dd"
     *
     * @return
     */
    public static String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String now = format.format(new Date());
        return now;
    }

    /**
     * 获取X天前的时间
     *
     * @return
     */
    public static String getXDayAgo(int x) {
        Calendar c = Calendar.getInstance();
        int today = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DATE, today - x);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String xDayAgo = format.format(c.getTime());
        return xDayAgo;
    }

    public static SimpleDateFormat getDateFormat() {
        if (dateformat == null) {
            dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return dateformat;
    }

    /**
     * 获取当前的时间,精确到小时
     *
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        String now = format.format(new Date());
        return now;
    }

    /**
     * 获取x小时前的时间
     *
     * @param x
     * @return
     */
    public static String getXHourAgo(int x) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        c.set(Calendar.HOUR_OF_DAY, hour - x);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        String xHourAgo = format.format(c.getTime());
        return xHourAgo;
    }

    /**
     * 字符串时间 转成 Date类型
     *
     * @param str
     * @param pattern
     * @return
     */
    public static Date parse(String str, String pattern) {
        Date date = null;
        try {
            if (str == null || str.trim().length() == 0) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            date = sdf.parse(str);
        } catch (ParseException e) {
            System.out.println("DateUtils" + e.getMessage());
        }
        return date;
    }

    //截取掉最后一位特殊字符
    public static String substringStr(String str) {
        if (null != str) {
            if (str.endsWith(",")) {
                return str.substring(0, str.length() - 1);
            }
        }
        return "";
    }
}
