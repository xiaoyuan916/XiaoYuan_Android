package com.xiao.project.http;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class OkGoUtils {
    public static void post(Context context) {
        OkGo.<String>post("http://www.baidu.com").tag(context)
                .upJson("")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(getClass().getSimpleName(), response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.d(getClass().getSimpleName(), response.body().toString());
                    }
                });
    }
}
