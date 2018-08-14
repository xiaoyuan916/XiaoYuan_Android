package com.sgcc.pda.sdk.app;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.exception.ExceptionHandle;


/**
 * 对Activity的生命周期事件进行集中处理
 * Created by xuzl on 2016/9/29.
 */
public class SDKApplication extends Application implements Application.ActivityLifecycleCallbacks {
    /**
     * 当前显示的Activity
     */
    private Activity foregroundActivity;
    /**
     * Activity个数
     */
    private int activeUICount = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(this);

        //自定义异常捕获处理
        ExceptionHandle.getInstance().init(getApplicationContext());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.d("app", "创建->" + activity.getClass().getName());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        foregroundActivity = activity;
        activeUICount++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activeUICount--;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.d("app", "释放->" + activity.getClass().getName());
    }

    /***
     * 获取当前活动的界面
     */
    public Activity getForegroundActivity() {
        if (null == foregroundActivity) {
            return null;
        }

        if (Build.VERSION.SDK_INT >= 17) {
            if (foregroundActivity.isDestroyed())
                return null;
        } else {
            if (foregroundActivity.isFinishing())
                return null;
        }

        return foregroundActivity;
    }


    /**
     * 应用是否在前台执行
     *
     * @return
     */
    public boolean isFore() {
        return activeUICount > 0;
    }
}
