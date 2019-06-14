package com.xiao.serialport.base;

import android.app.Application;

import com.xiao.serialport.utils.LogUtil;


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init();
    }
}
