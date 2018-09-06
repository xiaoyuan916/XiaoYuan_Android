package com.sgcc.pda.hardware.protocol.p698.utils;

import android.util.Log;

/**
 * Log日志输出工具类
 */
public class LogUtil {
    private static boolean isDebug = false;
    private static boolean SHOW_LINE_NUMBER_IN_LOG = isDebug;// 是否在log中显示行号
    private static final String TAG = "com.sgcc.pda";
    //是否已初始化
    private static boolean initFlag = false;

    public static void init(boolean debugFlag) {
        isDebug = debugFlag;
        SHOW_LINE_NUMBER_IN_LOG = debugFlag;
        initFlag = true;
    }

    /**
     * 打印Log.w信息
     *
     * @param aPrintLogStr 打印信息字符串
     */
    public static void w(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.WARN, false);
    }

    public static void w(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.WARN, false);
    }

    public static void i(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.INFO, false);
    }

    public static void i(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.INFO, false);
    }

    public static void callerI(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.INFO, true);
    }

    public static void d(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.DEBUG, false);
    }

    public static void d(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.DEBUG, false);
    }

    public static void e(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.ERROR, false);
    }

    public static void e(String aPrintLogStr) {
        LOG(TAG, aPrintLogStr, Log.ERROR, false);
    }

    public static void v(String tag, String aPrintLogStr) {
        LOG(tag, aPrintLogStr, Log.VERBOSE, false);
    }

    /**
     * @param tag        log的tag
     * @param content    log的内容
     * @param logType    log的类型,如Log.INFO,Log.DEBUG等
     * @param showCaller 是否显示调用者的方法名和行号等
     */
    private static void LOG(String tag, String content, int logType, boolean showCaller) {
        if(!initFlag) {
            Log.e("LogUtil","LogUtil没有初始化，请先初始化后使用，如果未初始化Log将不会输出显示");
        }
        if (isDebug) {
            if (SHOW_LINE_NUMBER_IN_LOG) {
                Throwable throwable = new Throwable();

                int methodDepth = 2;//LogUtils.LOGI的depth是0, 该函数的depth是1, 调用者的depth是2,调用者的调用者是3
                StackTraceElement[] stackTraceElements = throwable.getStackTrace();

                if (methodDepth < stackTraceElements.length) {
                    StackTraceElement element = stackTraceElements[methodDepth];

                    if (showCaller) {
                        if ((methodDepth + 1) < stackTraceElements.length) {
                            StackTraceElement elementCaller = stackTraceElements[methodDepth + 1];

                            if (elementCaller != null && elementCaller.getFileName() != null) {

                                String callerClassName = elementCaller.getFileName().substring(0, elementCaller.getFileName().lastIndexOf("."));
                                String className = element.getFileName().substring(0, element.getFileName().lastIndexOf("."));
                                String loginfo = String.format("[%s.%s() (%s:%d)]", callerClassName, elementCaller.getMethodName(), elementCaller.getFileName(), elementCaller.getLineNumber());
                                loginfo = loginfo + "\n";
                                loginfo = loginfo + "    " + String.format("[%s.%s() (%s:%d)]%s", className, element.getMethodName(), element.getFileName(), element.getLineNumber(), content);

                                content = loginfo;
                            }

                        }

                    } else {
                        if (element != null && element.getFileName() != null) {
                            String className = element.getFileName().substring(0, element.getFileName().lastIndexOf("."));
                            String loginfo = String.format("[%s.%s() (%s:%d)]%s", className, element.getMethodName(), element.getFileName(), element.getLineNumber(), content);
                            content = loginfo;
                        }
                    }

                }
            }

            switch (logType) {
                case Log.INFO:
                    Log.i(tag, "===================>"+content);
                    break;
                case Log.DEBUG:
                    Log.d(tag, "===================>"+content);
                    break;
                case Log.ERROR:
                    Log.e(tag, "===================>"+content);
                    break;
                case Log.WARN:
                    Log.w(tag, "===================>"+content);
                    break;
                case Log.VERBOSE:
                    Log.v(tag, "===================>"+content);
                    break;
                default:
                    break;
            }
        }

    }
}
