package com.sgcc.pda.sdk.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.sgcc.pda.sdk.utils.statusbar.SystemBarTintManager;

/**
 * 状态栏工具类
 * Created by xuzl on 2015/10/4.
 */
public class SystemBarTintUtil {
    /**
     * 设置状态栏为背景色
     * @param ac
     */
    public static void setSystemBar(Activity ac)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = ac.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);

            SystemBarTintManager mTintManager = new SystemBarTintManager(ac);
            mTintManager.setStatusBarTintEnabled(true);
            //加载你的actionbar的颜色或者背景图
            mTintManager.setStatusBarTintColor(Color.parseColor("#009D85"));
        }
    }
}

//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//        tintManager.setStatusBarTintEnabled(true);
//        // 使用颜色资源
//        tintManager.setStatusBarTintResource(R.color.status_color);
//        }
