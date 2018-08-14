package com.sgcc.pda.sdk.utils;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.sgcc.pda.sdk.utils.http.BaseRequestParams;
import com.sgcc.pda.sdk.utils.http.Mybody;
import com.sgcc.pda.sdk.utils.http.OkHttpManager;
import com.sgcc.pda.sdk.utils.http.UIHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp 工具类
 */
public class HttpClientUtil {
    private static String TAG = "HttpClientUtil";

    // 防止实例化
    private HttpClientUtil() {
    }

    private enum METHOD {
        PUT, POST, GET, DELETE
    }

    private static void httpRequest(
            final Context context,
            final Object callObject,
            METHOD method,
            final String url,
            final BaseRequestParams params,
            final OkHttpManager.Listener<String> listener,
            final OkHttpManager.ErrorListener errorListener) {

        Request.Builder requestBuilder = null;

        switch (method) {
            case POST: {
                MultipartBody.Builder body = params.createMultipartBody();
                body.setType(MultipartBody.FORM);
                requestBuilder = new Request.Builder()
                        .url(url)
                        .post(body.build())
                        .tag(callObject);
                break;
            }
            case PUT: {
                MultipartBody.Builder body = params.createMultipartBody();
                body.setType(MultipartBody.FORM);
                requestBuilder = new Request.Builder()
                        .url(url)
                        .put(body.build())
                        .tag(callObject);
                break;
            }
            case DELETE: {
                MultipartBody.Builder body = params.createMultipartBody();
                body.setType(MultipartBody.FORM);
                requestBuilder = new Request.Builder()
                        .url(url)
                        .delete(body.build())
                        .tag(callObject);
                break;
            }
            case GET: {
                String getUrl = "";
                StringBuffer sb = new StringBuffer();
                sb.append(url + "?");

                try {
                    for (ConcurrentHashMap.Entry<String, String> entry : params.urlParams.entrySet()) {
                        sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                        sb.append('=');
                        sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                        sb.append('&');
                    }
                    getUrl = sb.substring(0, sb.length() - 1);
                } catch (UnsupportedEncodingException uee) {
                    uee.printStackTrace();
                    getUrl = sb.toString();
                }

                requestBuilder = new Request.Builder()
                        .url(getUrl)
                        .get()
                        .tag(callObject);
                break;
            }
        }

        //需要打开将来
        //----------------------------------------
//        List<Cookie> asyncCookies = OkHttpManager.getClient(context).cookieJar().loadForRequest(HttpUrl.parse(url));
//		if(asyncCookies!=null && !asyncCookies.isEmpty()) {
//			for(Cookie cookie:asyncCookies) {
//				requestBuilder.header("cookie", cookie.name() + "=" + cookie.value());
//			}
//		}
        //-----------------------------------------------------

        OkHttpManager.getClient(context).newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (!call.isCanceled()) {
                    e.printStackTrace();
                    UIHandler.get().post(new Runnable() {
                        @Override
                        public void run() {
                            if (errorListener != null) {
                                if (e != null) {
                                    if (e instanceof SocketTimeoutException) {
                                        errorListener.onErrorResponse(new OkHttpManager.OkHttpTimeoutError(e.getMessage(), e));
                                    } else {
                                        errorListener.onErrorResponse(new OkHttpManager.OkHttpError(e.getMessage(), e));
                                    }
                                } else {
                                    errorListener.onErrorResponse(new OkHttpManager.OkHttpError("Unknown Error."));
                                }
                            }
                        }
                    });
                } else {
                    LogUtil.d("HttpClientUtils", call.request().tag().getClass().getName() + "取消了网络请求");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //code >= 200 && code < 300
                if (response.isSuccessful()) {
                    final String responseBodyString = response.body().string();
                    UIHandler.get().post(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onResponse(responseBodyString);
                            }
                        }
                    });
                } else {
                    onFailure(call, new OkHttpManager.OkHttpServerError("返回码:" + response.code() + ";message = "
                            + response.message() + ";返回：" + response.body()));
                }
            }
        });
    }

    /**
     * 网络请求的封装，只需要实现Listener，ErrorListener 对数据进行加密上传 post请求
     *
     * @param context       上下文
     * @param url           请求地址
     * @param listener      响应监听事件
     * @param errorListener 错误监听事件
     */
    public static void get(final Context context,
                           String url,
                           OkHttpManager.Listener<String> listener, OkHttpManager.ErrorListener errorListener) {
        httpRequest(context, context, METHOD.GET, url, null, listener, errorListener);
    }

    /**
     * 网络请求的封装，只需要实现Listener，ErrorListener 对数据进行加密上传 post请求
     *
     * @param context       上下文
     * @param url           请求地址
     * @param param         请求参数
     * @param listener      响应监听事件
     * @param errorListener 错误监听事件
     */
    public static void post(
            final Context context,
            String url,
            final BaseRequestParams param,
            OkHttpManager.Listener<String> listener, OkHttpManager.ErrorListener errorListener) {
        httpRequest(context, context, METHOD.POST, url, param, listener, errorListener);
    }

    /**
     * 网络请求的封装，只需要实现Listener，ErrorListener 对数据进行加密上传 post请求
     * @param context  上下文
     * @param object 请求的tag
     * @param url 请求地址
     * @param param 请求参数
     * @param listener 响应监听事件
     * @param errorListener 错误监听事件
     */
    public static void postTag(
            final Context context,
            Object object,
            String url,
            final BaseRequestParams param,
            OkHttpManager.Listener<String> listener, OkHttpManager.ErrorListener errorListener) {
        httpRequest(context, object, METHOD.POST, url, param, listener, errorListener);
    }

    public static void post(
            final Fragment fragment,
            String url,
            final BaseRequestParams param,
            OkHttpManager.Listener<String> listener, OkHttpManager.ErrorListener errorListener) {
        httpRequest(fragment.getContext(), fragment, METHOD.POST, url, param, listener, errorListener);
    }

    /**
     * 特殊的处理方式（只适合掌机）
     *
     * @param context
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     */
    public static void postFileCreateNewURL(final Context context,
                                            String url,
                                            final BaseRequestParams params,
                                            final OkHttpManager.Listener<String> listener, final OkHttpManager.ErrorListener errorListener) {
        //-------------组织新的post地址--------------
        String getUrl = "";
        StringBuffer sb = new StringBuffer();
        sb.append(url + "?");
        for (ConcurrentHashMap.Entry<String, String> entry : params.urlParams.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        getUrl = sb.substring(0, sb.length() - 1);
        LogUtil.d("HttpClientUtils", "新的上传文件路径：" + getUrl);
        //-------------------上传------------
        //方式一（单文件、多文件都失败）
//        LogUtil.d("HttpClientUtils","方式一MultipartBody上传文件");
//        params.urlParams.clear();
//        post(context,getUrl,params,listener,errorListener);
        //方式二
        LogUtil.d("HttpClientUtils", "方式二Mybody上传文件");
        Mybody body = new Mybody(params);
        Request.Builder requestBuilder = new Request.Builder()
                .url(getUrl)
                .post(body)
                .tag(context);
        OkHttpManager.getClient(context).newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                UIHandler.get().post(new Runnable() {
                    @Override
                    public void run() {
                        if (errorListener != null) {
                            if (e != null) {
                                if (e instanceof SocketTimeoutException) {
                                    errorListener.onErrorResponse(new OkHttpManager.OkHttpTimeoutError(e.getMessage(), e));
                                } else {
                                    errorListener.onErrorResponse(new OkHttpManager.OkHttpError(e.getMessage(), e));
                                }
                            } else {
                                errorListener.onErrorResponse(new OkHttpManager.OkHttpError("Unknown Error."));
                            }
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //code >= 200 && code < 300
                if (response.isSuccessful()) {
                    final String responseBodyString = response.body().string();
                    UIHandler.get().post(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onResponse(responseBodyString);
                            }
                        }
                    });
                } else {
                    onFailure(call, new OkHttpManager.OkHttpServerError("Server Error."));
                }
            }
        });
    }

    public static void get(
            final Context context,
            String url,
            final BaseRequestParams param,
            OkHttpManager.Listener<String> listener, OkHttpManager.ErrorListener errorListener) {
        httpRequest(context, context, METHOD.GET, url, param, listener, errorListener);
    }

    public static void get(
            final Context context,
            final Object callObject,
            String url,
            final BaseRequestParams param,
            OkHttpManager.Listener<String> listener, OkHttpManager.ErrorListener errorListener) {
        httpRequest(context, callObject, METHOD.GET, url, param, listener, errorListener);
    }

    public static void put(
            final Context context,
            String url,
            final BaseRequestParams param,
            OkHttpManager.Listener<String> listener, OkHttpManager.ErrorListener errorListener) {
        httpRequest(context, context, METHOD.PUT, url, param, listener, errorListener);
    }

    public static void delete(
            final Context context,
            String url,
            final BaseRequestParams param,
            OkHttpManager.Listener<String> listener, OkHttpManager.ErrorListener errorListener) {
        httpRequest(context, context, METHOD.DELETE, url, param, listener, errorListener);
    }

    /**
     * 下载文件
     *
     * @param context   上下文
     * @param stringUrl 下载文件的url路径
     * @param target    下载文件的保存路径
     * @param listener  下载侦听
     */
    public static void download(Context context, String stringUrl, final String target, final OkHttpManager.DownLoadListener listener) {
        Request request = new Request.Builder().url(stringUrl).tag(context).build();
        OkHttpManager.getClient(context).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                UIHandler.get().post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            if (e != null) {
                                if (e instanceof SocketTimeoutException) {
                                    listener.onFailure(new OkHttpManager.OkHttpTimeoutError(e.getMessage(), e));
                                } else {
                                    listener.onFailure(new OkHttpManager.OkHttpError(e.getMessage(), e));
                                }
                            } else {
                                listener.onFailure(new OkHttpManager.OkHttpError("Unknown Error."));
                            }
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                File file = new File(target);
                //code >= 200 && code < 300
                if (response.isSuccessful()) {
                    saveDownLoadFile(response, target, listener);
                } else {
                    onFailure(call, new OkHttpManager.OkHttpServerError("Server Error."));
                }
            }
        });
    }

    public static void download(Fragment fragment, String stringUrl, final String target, final OkHttpManager.DownLoadListener listener) {
        Request request = new Request.Builder().url(stringUrl).tag(fragment).build();
        OkHttpManager.getClient(fragment.getContext()).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                UIHandler.get().post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            if (e != null) {
                                if (e instanceof SocketTimeoutException) {
                                    listener.onFailure(new OkHttpManager.OkHttpTimeoutError(e.getMessage(), e));
                                } else {
                                    listener.onFailure(new OkHttpManager.OkHttpError(e.getMessage(), e));
                                }
                            } else {
                                listener.onFailure(new OkHttpManager.OkHttpError("Unknown Error."));
                            }
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                File file = new File(target);
                //code >= 200 && code < 300
                if (response.isSuccessful()) {
                    saveDownLoadFile(response, target, listener);
                } else {
                    onFailure(call, new OkHttpManager.OkHttpServerError("Server Error."));
                }
            }
        });
    }

    /**
     * 保存下载的文件
     *
     * @param response
     * @param target
     * @param listener
     */
    private static void saveDownLoadFile(Response response, String target, final OkHttpManager.DownLoadListener listener) {
//        File dir=new File(Environment.getExternalStorageDirectory(),"/lili/img/");
//        if (!dir.isDirectory()|| !dir.exists()){
//            dir.mkdirs();
//        }
//        final File file = new File(dir, target+ ".jpg");

        final File file = new File(target);
        InputStream is = null;
        FileOutputStream fos = null;
        byte[] buf = new byte[1024];
        try {
            long total = response.body().contentLength();
            LogUtil.d(TAG, "===========下载文件总大小：" + total);
            long current = 0;
            is = response.body().byteStream();
            fos = new FileOutputStream(file);
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                current += len;
                fos.write(buf, 0, len);
                LogUtil.d(TAG, "===========下载完成：" + current);

                //进度回调
                final long currentProgress = current;
                final long totalProgress = total;
                UIHandler.get().post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onProgress(currentProgress, totalProgress);
                        }
                    }
                });
            }
            fos.flush();
            //结束回调
            UIHandler.get().post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        listener.onSuccess(file);
                    }
                }
            });
        } catch (final IOException e) {
            LogUtil.d(TAG, e.toString());
            //失败回调
            UIHandler.get().post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        listener.onFailure(new OkHttpManager.OkHttpError(e.getMessage(), e));
                    }
                }
            });
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                LogUtil.e(TAG, e.toString());
            }
        }
    }

    /**
     * 按tag取消请求.
     * 源自: http://www.jianshu.com/p/ab41007f95c5
     *
     * @param context
     */
    public static void cancle(Context context) {
        if (context == null) {
            return;
        }
        synchronized (OkHttpManager.getClient(context).dispatcher().getClass()) {
            for (Call call : OkHttpManager.getClient(context).dispatcher().queuedCalls()) {
                if (context.equals(call.request().tag())) {
                    call.cancel();
                }
            }

            for (Call call : OkHttpManager.getClient(context).dispatcher().runningCalls()) {
                if (context.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    public static void cancle(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        synchronized (OkHttpManager.getClient(fragment.getActivity()).dispatcher().getClass()) {
            for (Call call : OkHttpManager.getClient(fragment.getActivity()).dispatcher().queuedCalls()) {
                if (fragment.equals(call.request().tag())) {
                    call.cancel();
                }
            }

            for (Call call : OkHttpManager.getClient(fragment.getActivity()).dispatcher().runningCalls()) {
                if (fragment.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }
}
