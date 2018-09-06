package com.sgcc.pda.jszp.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.sgcc.pda.sdk.utils.LogUtil;

import java.lang.reflect.Method;

/**
 * Created by xuzl on 2016/10/8.
 * 设备工具类
 */
public class DeviceUtil {

    /**
     * 获取设备imei号
     * @return 设备IMEI号
     */

    @SuppressLint("MissingPermission")
    public static String getDeviceIMEI(Context context) {
        TelephonyManager tpManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<? extends TelephonyManager> zClass = tpManager.getClass();
        Method getImei = null;
        try {
            if (zClass != null) {
                getImei = zClass.getDeclaredMethod("getImei", int.class);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            LogUtil.d("com.sgcc.pda", " getDeviceIMEI fali" + e.getMessage());
        }

        try {
            if (getImei != null) {
                String deviceId = (String) getImei.invoke(tpManager, 0);
                LogUtil.d("com.sgcc.pda", " invoke " + deviceId);
                if (null != deviceId && deviceId.length() == 15) {
                    return deviceId;
                }

                return tpManager.getDeviceId();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("com.sgcc.pda", " getDeviceIMEI fali" + e.getMessage());
        }
        String deviceId = tpManager.getDeviceId();
        LogUtil.d("com.sgcc.pda", " deviceId " + deviceId);
        return deviceId;
    }

    //如果适用的话,返回SIM卡的序列号
    @SuppressLint("MissingPermission")
    public static String getSimSerialNumber(Context context) {
        TelephonyManager tpManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
         String simSerialNumber = tpManager.getSimSerialNumber();
        return simSerialNumber;
    }

    public static String getVersion(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != appInfo && appInfo.metaData!=null) {
                return appInfo.metaData.getString("appVrsionCode");
            } else {
                return "00.00.00.00";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "00.00.00.00";
        }

    }
    //返回一个常数表示SIM卡设备的状态
    public static Integer getSimState(Context context) {
        TelephonyManager tpManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int SimState = tpManager.getSimState();
        return SimState;
    }
    //@SuppressLint("MissingPermission")
    //返回主卡的手机号
/*    public static String getline1Number(Context context) {
        TelephonyManager tpManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String line1Number = tpManager.getLine1Number();
        return line1Number;
    }*/

    //返回唯一的用户ID
    @SuppressLint("MissingPermission")
    public static String getSubscriberId(Context context) {
        TelephonyManager tpManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String subscriberId = tpManager.getSubscriberId();
        return subscriberId;
    }
}
