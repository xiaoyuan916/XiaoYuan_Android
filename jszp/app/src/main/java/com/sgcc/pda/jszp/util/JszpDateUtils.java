package com.sgcc.pda.jszp.util;

import android.provider.ContactsContract;
import android.text.TextUtils;

import com.sgcc.pda.sdk.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author:xuxiaoyuan
 * date:2018/9/5
 */
public class JszpDateUtils {
    public static String getResidualTime(String date) {
        if (TextUtils.isEmpty(date)){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //预定时间
        long timePlan = parse.getTime();
        //当前时间
        long timeNow = new Date().getTime();
        long time = timePlan - timeNow;
        if (time>0){
            return getDayCount(timePlan)+"日"+getDiffHour(time,timePlan)+"小时";
        }else {
            return "超期";
        }
    }
    /**
     * 计算日期除了天数以外还差差多少小时
     *
     */
    public static String getDiffHour(long time, long timePlan) {
        return String.valueOf((time-24*60*60*1000*Integer.parseInt(getDayCount(timePlan))) / (1000 * 60 * 60));
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
}
