package com.xiao.jszpdemo.http;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.$Gson$Types;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.xiao.jszpdemo.bean.BaseEntity;
import com.xiao.jszpdemo.http.callback.JsonCallback;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


public class JSZPOkgoHttpUtils {
    private static final String TAG = "JSZPOkgoHttpUtils";
    public static final int DISMISS_PROGRESS_DIALOG = 4001;
    public static final int JSZP_OK_HTTTP_ERROR = 4002;

    private static final Gson gson = new Gson();

    /**
     * okgo的post请求
     */
    public static <T> void post(String url,
                                final Context context,
                                Object jsonRequestObject,
                                final Handler handler,
                                final int what,final  Class classOfT) {
        try {
            String json = new Gson().toJson(jsonRequestObject);
            Log.d(TAG, "请求参数：" + json);
            //okgo的POST请求
            OkGo.<T>post(url)
                    .tag(context)//
                    .upJson(json)
                    .execute(new JsonCallback<T>() {
                        @Override
                        public void onError(Response<T> response) {
                            super.onError(response);
//                            handlerResponse(response,handler,what);
                            handler.obtainMessage(what, response.body()).sendToTarget();
                            handlerDismissProgressDialog(context);

                        }

                        @Override
                        public void onSuccess(Response<T> response) {
                            handlerResponse(context, response, handler, what,classOfT);
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "post报错: ", e);
            handlerDismissProgressDialog(context);
        }
    }

    public static abstract class JSZPCallcak<T> {
        Type mType;

        public JSZPCallcak() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            //通过反射得到泛型参数
            //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            //ParameterizedType参数化类型，即泛型
            ParameterizedType parameterized = (ParameterizedType) superclass;
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            //将Java 中的Type实现,转化为自己内部的数据实现,得到gson解析需要的泛型
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Throwable e);

        public abstract void onSuccess(T response, String json);
    }


    public static void postParams(String url,
                                  final Context context,
                                  HttpParams params, final JSZPCallcak callback) {
        try {
            //okgo的POST请求
            OkGo.<String>post(url)
                    .tag(context)//
                    .params(params)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (response != null && response.isSuccessful()) {
                                String json = response.body();
                                callback.onSuccess(gson.fromJson(json, callback.mType), json);
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            callback.onError(response.getException());
                        }
                    });
        } catch (Exception e) {
            if (callback != null) {
                callback.onError(e);
            }
        }
    }

    /**
     * 将旋转加载控件消失掉
     *
     * @param context
     */
    private static void handlerDismissProgressDialog(Context context) {
//        handler.obtainMessage(DISMISS_PROGRESS_DIALOG).sendToTarget();
    }

    /**
     * 直接发起网络请求，返回String
     *
     * @param url     请求的url
     * @param context 请求的上下文
     * @param handler
     * @param what
     */
    public static void postString(String url,
                                  final Context context,
                                  Object jsonRequestObject,
                                  final Handler handler,
                                  final int what, final Class classOfT) {
        String json = gson.toJson(jsonRequestObject);
        //okgo的POST请求
        OkGo.<String>post(url)
                .tag(context)//
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        handlerResponse(context, response,handler,what);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(getClass().getSimpleName(), response.toString());
                        handlerResponse(context, response, handler, what,classOfT);
                    }
                });
    }

    /**
     * 直接发起网络请求，返回String
     *
     * @param url     请求的url
     * @param context 请求的上下文
     * @param params  请求的参数
     * @param handler
     * @param what
     */
    public static <T> void postMap(String url,
                                   final Context context,
                                   Map<String, String> params,
                                   final Handler handler,
                                   final int what,final Class classOfT) {

        params.put("UID", "");
        params.put("userId", "");
        params.put("baseNo", "");
        params.put("MAC", "");

        JSONObject json = new JSONObject(params);
        //okgo的POST请求
        try {
            Log.d(TAG, "请求参数：" + json);
            //okgo的POST请求
            OkGo.<BaseEntity>post(url)
                    .tag(context)//
                    .upJson(json)
                    .execute(new JsonCallback<BaseEntity>() {
                        @Override
                        public void onError(Response<BaseEntity> response) {
                            super.onError(response);
//                            handlerResponse(response,handler,what);
                            handler.obtainMessage(what, response.body()).sendToTarget();
                            handlerDismissProgressDialog(context);
                        }

                        @Override
                        public void onSuccess(Response<BaseEntity> response) {
                            handlerResponse(context, response, handler, what,classOfT);
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "post报错: ", e);
            handlerDismissProgressDialog(context);
        }
    }

    private static void handlerResponse(Context context, Response response,
                                        Handler handler,
                                        int what,Class classOfT) {
        try {
            handlerDismissProgressDialog(context);
            BaseEntity body = gson.fromJson((String) response.body(), (Type)classOfT);
//            BaseEntity body = (BaseEntity) response.body();
            if ("1".equals(body.getRT_F())) {
                handler.obtainMessage(what, body).sendToTarget();
                Log.d(TAG,"成功了");
            } else {
                handler.obtainMessage(what + JSZP_OK_HTTTP_ERROR, body).sendToTarget();
                handlerDismissProgressDialog(context);
            }
        } catch (Exception e) {
            Log.e(TAG, "post返回bean转换报错: ", e);
            handlerDismissProgressDialog(context);
        }
    }

    /**
     * 当界面销毁的时候取消网络请求
     */
    public static void cancelHttp(Context context) {
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(context);
    }
}
