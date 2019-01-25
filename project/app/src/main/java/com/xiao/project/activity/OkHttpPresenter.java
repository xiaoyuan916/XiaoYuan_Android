package com.xiao.project.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xiao.project.bean.DemoEntity;
import com.xiao.project.bean.User;
import com.xiao.project.http.JSZPOkgoHttpUtils;

/**
 * author:xuxiaoyuan
 * date:2019/1/25
 */
public class OkHttpPresenter {
    private OkHttpActivity okHttpActivity;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    DemoEntity obj = (DemoEntity) msg.obj;
                    Log.d("OkHttpActivity", obj.getDemo().getDemoStringTwo());
                    okHttpActivity.logPrint("数据回来了");
                    break;
            }
        }
    };

    public OkHttpPresenter(OkHttpActivity okHttpActivity) {
        this.okHttpActivity = okHttpActivity;
    }

    public void obtainData() {
        User user = new User();
        user.setName("xiaoyuan");
        JSZPOkgoHttpUtils.postString("http://10.0.2.2:8080/users/user",
                okHttpActivity,
                user, mHandler, 1001, DemoEntity.class);
    }
}
