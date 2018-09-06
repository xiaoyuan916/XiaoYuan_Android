package com.sgcc.pda.sdk.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 */
public class DateUtil {
    public static SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat time2 = new SimpleDateFormat("MM月dd日  HH:mm");
    public static SimpleDateFormat time3 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat time4 = new SimpleDateFormat("MM月dd日");
    public static SimpleDateFormat time5 = new SimpleDateFormat("HH");
    public static SimpleDateFormat time6 = new SimpleDateFormat("MM月份");
    public static SimpleDateFormat time7 = new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat time8 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat time9 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    public static SimpleDateFormat time10 = new SimpleDateFormat("MM.dd HH:mm");
    public static SimpleDateFormat time11 = new SimpleDateFormat("mm:ss");
    public static SimpleDateFormat time12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 以友好的方式显示时间差
     *
     * @param millions
     * @return
     */
    public static String friendly_time(Long millions) {
        String sdate=GetStringFromLong(millions);
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 输入为long型
     * 返回“2015-03-26 23:21:11”格式
     */
    public static String GetStringFromLong(long millis) {
        java.util.Date dt = new Date(millis);
        return time12.format(dt);
    }


    /**
     * 返回“2015-03-26”格式
     *
     * @param date
     * @return
     */
    public static String toYYmmDD(Date date) {
        return time.format(date);
    }

    /**
     * 返回“2015-03-26”格式
     *
     * @param date
     * @return
     */
    public static String toDateString(Date date,SimpleDateFormat format) {
        return format.format(date);
    }

    /**
     * 获取星期几，返回“星期二”
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 获取几天后是星期几，返回“星期二”
     *
     * @param date
     * @param after
     * @return
     */
    public static String getWeek(Date date, int after) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, after);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 获取是几号
     *
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取几天后是几号
     *
     * @param date
     * @param after
     * @return
     */
    public static String getDay(Date date, int after) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, after);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取几天后日期，返回 “2”
     *
     * @param date
     * @param after
     * @return
     */
    public static String getDateOfLong(Date date, int after) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, after);
        return String.valueOf(cal.getTime().getTime() / 1000);
    }

    /**
     * 返回“xx月xx日  12：23”
     *
     * @param d
     * @return
     */
    public static String getMonthDayHourMinute(Date d) {
        return time2.format(d);
    }

    /**
     * 获取月日  返回“xx月xx日”
     *
     * @param d
     * @return
     */
    public static String getMonthDay(Date d) {
        return time4.format(d);
    }

    /**
     * 获取时分  返回“12：23”
     *
     * @param d
     * @return
     */
    public static String getHourMinute(Date d) {
        return time3.format(d);
    }

    /**
     * 返回分秒“12：23”
     *
     * @param d
     * @return
     */
    public static String getMinuteSecond(Date d) {
        return time11.format(d);
    }

    /**
     * 获取小时 返回“12”
     *
     * @param d
     * @return
     */
    public static String getHour(Date d) {
        return time5.format(d);
    }

    /**
     * 返回“12月份”
     *
     * @param d
     * @return
     */
    public static String getMonthEla(Date d) {
        return time6.format(d);
    }

    /**
     * 返回“12-23 09:30”
     *
     * @param d
     * @return
     */
    public static String getTime(Date d) {
        return time7.format(d);
    }

    /**
     * 返回"1月"，如果是当月返回“本月”
     *
     * @param d
     * @return
     */
    public static String getMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        int month = cal.get(Calendar.MONTH);

        cal.setTime(new Date());

        int currMonth = cal.get(Calendar.MONTH);

        if (currMonth == month) {
            return "本月";
        } else {
            return (month + 1) + "月";
        }
    }

    /**
     * 返回“2015-12-12 12：20”格式
     *
     * @param d
     * @return
     */
    public static String getYearMonthDayHourMinute(Date d) {
        return time8.format(d);
    }

    /**
     * 计算日期差多少小时
     *
     * @param s
     * @param e
     * @return
     */
    public static String getDiffHour(long s, long e) {
        return String.valueOf((e - s) / (1000 * 60 * 60));
    }

    /**
     * 返回“2015.02.25”格式
     *
     * @param d
     * @return
     */
    public static String getDotYearMonthDay(long d) {
        return time9.format(new Date(d));
    }

    /**
     * 返回“02.25”月日格式
     *
     * @param d
     * @return
     */
    public static String getDotMonthDay(long d) {
        return time10.format(new Date(d));
    }

    /**
     * 当前时间距离目标时间的天数
     *
     * @param d
     * @return
     */
    public static String getDayCount(long d) {
        Date cur = new Date();
        return String.valueOf((d - cur.getTime()) / (1000 * 60 * 60 * 24));
    }

    //得到当前时间（精确到毫秒），以时间戳命名导出文件
    public static String getFileName(){
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
        return fileName;
    }

    /**
     * 在SD卡下的mmp文件夹下创建txt文件
     *
     * @param fileName 文件名字
     * @param result 要写入文件的内容
     */
    public static void createFile(String fileName, String result) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            // 有SD卡，在SD卡下创建
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/assetscheck/" + fileName + ".txt";
            String filePath1 = Environment.getExternalStorageDirectory().toString() + File.separator + "assetscheck/" + fileName + ".txt";
        } else {
            // 没有SD卡，系统下载缓存根目录
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "assetscheck/" + fileName + ".txt";
        }

        try

        {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            // 没有第二个参数时代表覆盖创建文件，有第二个参数时为追加创建文件
            FileOutputStream outStream = new FileOutputStream(file,true);
            outStream.write(result.getBytes());
            outStream.close();
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }


    }


}
