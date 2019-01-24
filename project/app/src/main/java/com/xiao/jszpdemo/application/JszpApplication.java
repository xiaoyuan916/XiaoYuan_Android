package com.xiao.jszpdemo.application;

import android.app.Application;


public class JszpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化okhttp
        initOkhttp();
    }

    /**
     * 初始化okhttp
     */
    private void initOkhttp() {
        new OkGoInitUtils().initOkGo(this);
    }
}
