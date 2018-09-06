package com.sgcc.pda.hardware.protocol.p698.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by jiliangliang
 * on 2018/1/9
 * 北京合众伟奇科技有限公司
 */

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks  {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


}
