package com.sgcc.pda.hardware.util;

import android.content.Context;
import android.content.SharedPreferences;

/*****************************************************
 * 版权信息: 北京合众伟奇科技有限公司版权所有
 * 创建作者: 设备组
 * 创建日期: 2017/9/18 13:23
 * ****************************************************
 * 更改记录: 更新人:    更新时间:    更新概要:
 *
 * ****************************************************
 *  类功能说明: 外设信息保存
 * ****************************************************/

public class PeripheralParamUtil {
    private static final String PERIPHERAL_PARAM = "peripheral_param";
    private static final String PERIPHERAL_FACTORY = "peripheral_factory";//外设厂家
    private static final String PERIPHERAL_NAME = "peripheral_name";//外设名称
    private static final String PERIPHERAL_ADDRESS = "peripheral_address";//外设地址

    /**
     * 获取外设厂家
     *
     * @param context 上下文
     * @return 外设厂家
     */
    public static int getPeripheralFactory(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                PERIPHERAL_PARAM, 0);

        if (preferences.contains(PERIPHERAL_FACTORY)) {
            return preferences.getInt(PERIPHERAL_FACTORY, 0);
        } else {
            return 0;
        }
    }

    /**
     * 设置外设厂家
     *
     * @param context 上下文
     * @param factory 外设厂家
     */
    public static void setPeripheralFactory(Context context, int factory) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PERIPHERAL_PARAM, 0).edit();
        editor.putInt(PERIPHERAL_FACTORY, factory);
        editor.commit();
    }

    /**
     * 获取外设名称
     *
     * @param context 上下文
     * @return 外设名称
     */
    public static String getPeripheralName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                PERIPHERAL_PARAM, 0);

        if (preferences.contains(PERIPHERAL_NAME)) {
            return preferences.getString(PERIPHERAL_NAME, "");
        } else {
            return "";
        }
    }

    /**
     * 设置外设名称
     *
     * @param context 上下文
     * @param name    外设名称
     */
    public static void setPeripheralName(Context context, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PERIPHERAL_PARAM, 0).edit();
        editor.putString(PERIPHERAL_NAME, name);
        editor.commit();
    }

    /**
     * 获取外设地址
     *
     * @param context 上下文
     * @return 外设地址
     */
    public static String getPeripheralAddress(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                PERIPHERAL_PARAM, 0);

        if (preferences.contains(PERIPHERAL_ADDRESS)) {
            return preferences.getString(PERIPHERAL_ADDRESS, "");
        } else {
            return "";
        }
    }

    /**
     * 设置外设地址
     *
     * @param context 上下文
     * @param address 外设地址
     */
    public static void setPeripheralAddress(Context context, String address) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PERIPHERAL_PARAM, 0).edit();
        editor.putString(PERIPHERAL_ADDRESS, address);
        editor.commit();
    }


}
