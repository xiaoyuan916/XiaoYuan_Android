package com.xiao.jszpdemo.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xuzl on 2016/10/14.
 * 自定义异常捕获处理
 */
public class ExceptionHandler implements UncaughtExceptionHandler {
    public static final String TAG = "ExceptionHandle";
    //系统默认的UncaughtException处理类
    private UncaughtExceptionHandler defaultExceptionHandler;
    //ExceptionHandle实例
    private static ExceptionHandler instance;
    //程序的Context对象
    private Context mContext;

    private ExceptionHandler() {
    }

    public static synchronized ExceptionHandler getInstance() {
        if (instance == null) {
            instance = new ExceptionHandler();
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该ExceptionHandle为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //删除过期的日志
//        deleteLog();
    }

    private void deleteLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String filePath = null;
                    boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                    if (hasSDCard) { // SD卡根目录的log.text
                        filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "logger";
                    } else  // 系统下载缓存根目录的log.text
                        filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "logger";

                    File files = new File(filePath);
//                    SimpleDateFormat   dateFormat = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA);
                    File[] fileList = files.listFiles();
                    for (File file :fileList){
                        long lastModified = file.lastModified();
//                        String name = file.getName();
//                        String substring = name.substring(0, 10);
                        long timeNow = new Date().getTime();
                        long timeFile = new Date(lastModified).getTime();
                        if ((timeNow-timeFile)>7*24*60*60*1000){
                            file.delete();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "============>捕获到异常");

        boolean handleException = handleException(ex);

        if (!handleException && defaultExceptionHandler != null) {
            Log.e(TAG, "============>交由系统defaultExceptionHandler处理出现的异常");
            defaultExceptionHandler.uncaughtException(thread, ex);
        } else {
            Log.e(TAG, "============>自定义异常捕获，拦截系统的异常处理，避免崩溃");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : InterruptedException");
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (null == ex) {
            return false;
        }

        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.",Toast.LENGTH_SHORT);
                Looper.loop();
            }
        }.start();
        //保存日志
        saveCatchInfo2File(ex);
        return true;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private void saveCatchInfo2File(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "准备保存的错误日志信息：" + result);
        Date  date = new Date();
        SimpleDateFormat   dateFormat = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA);
        String format = dateFormat.format(date);
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的log.text
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "logger/"+format+"_crush.txt";
        } else  // 系统下载缓存根目录的log.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "logger/"+format+"_crush.txt";

        try {
            File file=new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
//            FileOutputStream outStream = new FileOutputStream(file);
            FileWriter  fileWriter = new FileWriter(file, true);
            fileWriter.append(result);

            fileWriter.flush();
            fileWriter.close();
//            outStream.write(result.getBytes());
//            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
