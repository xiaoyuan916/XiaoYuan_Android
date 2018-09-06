package com.sgcc.pda.hardware;

import android.os.Build;
import android.text.TextUtils;

/**
 * Created by xuzl on 2016/11/7.
 */
public class SOConfig {
    /**
     * 是否使用老的so文件
     */
    public static boolean useOldSO = false;
    /**
     * 是否是同昌HT380的掌机
     */
    public static boolean isTC(){
        String model = Build.MODEL;
        if(!TextUtils.isEmpty(model)) {
            if("HT380".equalsIgnoreCase(model)){
                return true;
            }
        }
        return false;
    }
    /**
     * 是否是同昌HT380的掌机
     */
    public static boolean isResamNew(){
        String model = Build.MODEL;
        if(!TextUtils.isEmpty(model)) {
            if("TCHN-106".equalsIgnoreCase(model)){
                return true;
            }
        }
        return false;
    }
}
