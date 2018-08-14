package com.sgcc.pda.hardware.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.text.MessageFormat;

/******************************************************************
 * @文件名称 : ToastManager.java
 * @创建时间 : 2012-10-29 下午4:08:07
 * @文件描述 : 显示toast
 * @修改历史 : 2012-10-29 1.00 初始版本
 ******************************************************************/

@Deprecated
public class ToastManager {

    private static ToastManager sInstance;
    private final Context mContext;
    private final Handler hToast;
    public static boolean isShowing = false;
    static Toast toaster;

    public static void init(Context context) {
        if (sInstance == null)
            sInstance = new ToastManager(context);
        else
            throw new IllegalStateException("Toaster has been inited.");
    }

    public static ToastManager getInstance() {
        if (sInstance == null)
            throw new IllegalStateException("Toaster has not been inited.");
        else
            return sInstance;
    }

    private ToastManager(Context context) {
        this.mContext = context;
        this.hToast = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        isShowing = true;
                        toaster = Toast.makeText(mContext, (String) msg.obj,
                                Toast.LENGTH_SHORT);
                        toaster.show();
                        break;
                    case 1:
                        if (isShowing && toaster != null) {
                            toaster.cancel();
                            isShowing = false;
                        }
                        break;

                    default:
                        break;
                }

            }
        };
    }

    public void displayToast(String str) {
        Message msg = Message.obtain(hToast, 0, str);
        msg.sendToTarget();
    }

    public void displayToast(int res) {
        displayToast(mContext.getString(res));
    }

    public void displayToast(int formatRes, Object[] params) {
        displayToast(MessageFormat
                .format(mContext.getString(formatRes), params));
    }

    public void cancelShow() {
        Message msg = Message.obtain(hToast, 1);
        msg.sendToTarget();
    }

    /**
     * 通用提示
     *
     * @param mContext
     *            字符串
     */
    public static void showToast(Context mContext, String toastString) {
        Toast.makeText(mContext, toastString, Toast.LENGTH_SHORT).show();
    }

    /**
     * 通用提示
     *
     * @param mContext
     * @param id
     *            字符串本地ID
     */
    public static void showToast(Context mContext, int id) {
        Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show();
    }

}