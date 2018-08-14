package com.sgcc.pda.sdk.utils.http;

import android.content.Context;
import android.util.Log;

import com.sgcc.pda.sdk.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class OkHttpManager {
    private static OkHttpManager sManager;
    private OkHttpClient mClient;
    private static PersistentCookieStore persistentCookieStore;

    private OkHttpManager(Context context) {
        persistentCookieStore = new PersistentCookieStore(context);
        //ok http网络请求的第一步：创建OkHttpClient对象,请求加入cookie
        mClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS)//设置写入超时时间
                .followSslRedirects(false)//禁止重新定向

                .followRedirects(false)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        LogUtil.d("TAG", "得到服务器端的jsessionid" + cookies);

                        if (cookies != null && cookies.size() > 0) {
                            for (Cookie item : cookies) {
                                LogUtil.d("cookie monitor:=======>保存cookie" + item.toString());
                                persistentCookieStore.addCookie(item);
                            }
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        LogUtil.d("cookie monitor:=======>请求中加入cookie");
                        List<Cookie> cookies = persistentCookieStore.getCookies();
                        return cookies == null ? new ArrayList<Cookie>() : cookies;
                    }
                })
                .retryOnConnectionFailure(true)
                .build();
    }

    //获取单例引用
    public static OkHttpManager getManager(Context context) {
        if (sManager == null) {
            synchronized (OkHttpManager.class) {
                if (sManager == null) {
                    sManager = new OkHttpManager(context);
                }
            }
        }
        return sManager;
    }

    //获取单例引用
    public static void removeManager(Context context) {
        PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context);
        List<Cookie> cookies = persistentCookieStore.getCookies();
        Log.d("TAG", "removeManager中获取到保存的cookie长度：" + cookies.size());
        persistentCookieStore.clear();
        PersistentCookieStore persistentCookieStore1 = new PersistentCookieStore(context);
        List<Cookie> cookies1 = persistentCookieStore1.getCookies();
        LogUtil.d("TAG","removeManager清除后得到cookie的长度="+cookies1.size());
        if (sManager != null) {
            synchronized (OkHttpManager.class) {
                if (sManager != null) {
                    sManager = null;
                }
            }
        }
    }

    //清除cookie
    public static void clearCookie() {
        if (persistentCookieStore != null) {
            persistentCookieStore.clear();
        }
    }

    //获取OkHttpClient对象
    public static OkHttpClient getClient(Context context) {
        return OkHttpManager.getManager(context).mClient;
    }


    /**
     * Callback interface for delivering parsed responses.
     */
    //请求成功时的响应监听
    public interface Listener<T> {
        /**
         * Called when a response is received.
         */
        public void onResponse(T response);
    }

    /**
     * Callback interface for delivering error responses.
     */
    //请求失败的监听
    public interface ErrorListener {
        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        public void onErrorResponse(OkHttpError error);
    }

    public interface DownLoadListener {
        public void onProgress(long curr, long total);

        public void onSuccess(File file);

        public void onFailure(OkHttpError error);
    }

    public static class OkHttpError extends IOException {
        public OkHttpError(String detailMessage) {
            super(detailMessage);
        }

        public OkHttpError(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class OkHttpTimeoutError extends OkHttpError {
        public OkHttpTimeoutError(String detailMessage) {
            super(detailMessage);
        }

        public OkHttpTimeoutError(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * code >= 200 && code < 300
     */
    public static class OkHttpServerError extends OkHttpError {
        public OkHttpServerError(String detailMessage) {
            super(detailMessage);
        }

        public OkHttpServerError(String message, Throwable cause) {
            super(message, cause);
        }
    }

}