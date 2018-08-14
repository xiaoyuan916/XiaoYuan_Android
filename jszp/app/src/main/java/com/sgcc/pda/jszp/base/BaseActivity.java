package com.sgcc.pda.jszp.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import com.sgcc.pda.sdk.utils.HttpClientUtil;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.SharepreferenceUtil;

import butterknife.ButterKnife;

/**
 * Created by zwj on 2017/10/26.
 */

public abstract class BaseActivity extends FragmentActivity {

    protected Context mContext;
    public BaseApplication application;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpClientUtil.cancle(this);
        application.removeActivity(this);
    }

    public void returnback(View v){

        finish();
    }
    public void onRightClick(View v){

    }
    public void onIvRightClick(View v){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int width = getScreenWidth();

        //设置屏幕方向为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SharepreferenceUtil.setScreenWidth(this, width);
        mContext = this;
        application = (BaseApplication) getApplication();

        setContentView(getLayoutResId());
        application.addActivity(this);
        ButterKnife.bind(this);

        initView();
        initListener();
        initData();

    }

    //获取屏幕宽高
    public int getScreenWidth() {
        WindowManager wm = getWindowManager();
        if (null != wm) {
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            LogUtil.d("tag3", " big width = " + width + " height = " + height);
            LogUtil.d("TAG", "屏幕宽度为：" + width);
            LogUtil.d("TAG", "屏幕高度为：" + height);
            return width;
        }

        return 480;
    }

    public abstract int getLayoutResId();

    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

}
