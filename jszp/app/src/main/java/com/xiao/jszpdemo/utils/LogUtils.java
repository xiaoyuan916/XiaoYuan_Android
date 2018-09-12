package com.xiao.jszpdemo.utils;

import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.xiao.jszpdemo.BuildConfig;

/**
 * author:xuxiaoyuan
 * date:2018/8/29
 */
public class LogUtils {
    /**
     * 初始化日志的使用
     */
    public static void init() {
        FormatStrategy formatStrategy1 = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //是否选择显示线程信息，默认为true
                .methodCount(3)         //方法数显示多少行，默认2行
//                .methodOffset(5)        //隐藏方法内部调用到偏移量，默认5
//                .logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
                .tag("jszp_log")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy1) {
            //线上环境是否需要输出日志
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });

        FormatStrategy formatStrategy2 = CsvFormatStrategy.newBuilder()
                .tag("jazp_log")
                .build();
        //日志向文件中输出
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy2));
    }

    public static void w( String aPrintLogStr) {
        Logger.w(aPrintLogStr);
    }

    public static void i(String aPrintLogStr) {
        Logger.i(aPrintLogStr);
    }


    public static void d(String aPrintLogStr) {
        Logger.d(aPrintLogStr);
    }


    public static void e(String aPrintLogStr) {
        Logger.e(aPrintLogStr);
    }

}
