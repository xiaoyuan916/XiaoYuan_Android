package com.sgcc.pda.hardware.util;


import android.content.Context;
import android.util.Log;

import com.cepri.dev.SecurityUnit;

/**
 * Created by jiliangliang on 2017/8/1.
 */
public class NewProtocol {


    private boolean isNewProtocol = true;
    /**
     * 使用单例模式，使得 生命周期与app 一致。 保证全局的 isNewProtocol 值不变。
     * @return
     */
    public static boolean isNewProtocol() {
        return getInstance().isNewProtocol;
    }

    private static class SingletonHolder {
        private static final NewProtocol mInstance = new NewProtocol();
    }

    public static NewProtocol getInstance() {
        return SingletonHolder.mInstance;
    }

    private NewProtocol() {
    }

    /**
     * 在Application 中初始化，获取是否是新协议的so
     */
    public void init() {
        try {
            int status = cepri.device.utils.SecurityUnit.Init();
            isNewProtocol = true; // 能够找到对应SO，不崩溃，即是新协议
            Log.d("loadSo", "init : " + status + "is New " + NewProtocol.isNewProtocol());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.d("loadSo", throwable.getMessage());
            isNewProtocol = false;
        }
    }



}
