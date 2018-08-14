package com.sgcc.pda.jszp.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sgcc.pda.jszp.bean.BaseEntity;

import okhttp3.ResponseBody;

public class JSZPOkgoHttpUtils {
    private static final String TAG = "JSZPOkgoHttpUtils";

    /**
     * okgo的post请求
     */
    public static void post(String url,
                            Context context,
                            Object jsonObject,
                            final Handler handler,
                            final int what){
        try{
            String json = new Gson().toJson(jsonObject);
            //okgo的POST请求
            OkGo.<String>post(url)
                    .tag(context)//
                    .upJson(json)
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            handlerResponse(response,handler,what);
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d(getClass().getSimpleName(),response.toString());
                            handlerResponse(response, handler,what);
                        }
                    });
        }catch (Exception e){
            Log.e(TAG, "post报错: ",e);
        }
    }

    /**
     * 直接发起网络请求，返回String
     * @param url  请求的url
     * @param context  请求的上下文
     * @param json  请求的json
     * @param handler
     * @param what
     */
    public static void postString(String url,
                                  Context context,
                                  String json,
                                 final Handler handler,
                                  final int what){
        //okgo的POST请求
        OkGo.<String>post(url)
                .tag(context)//
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        handlerResponse(response,handler,what);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(getClass().getSimpleName(),response.toString());
                        handlerResponse(response, handler,what);
                    }
                });
    }

    private static void handlerResponse(Response response,
                                        Handler handler,
                                        int what) {
        handler.obtainMessage(what,response).sendToTarget();
    }

    /**
     * 当界面销毁的时候取消网络请求
     */
    public static void cancelHttp(Context context){
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(context);
    }
}
