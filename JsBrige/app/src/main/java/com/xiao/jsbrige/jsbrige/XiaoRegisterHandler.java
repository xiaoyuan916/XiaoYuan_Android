package com.xiao.jsbrige.jsbrige;

import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class XiaoRegisterHandler {

    private static final String TAG = "XiaoRegisterHandler";
    /**
     * 使用单例
     */
    private static XiaoRegisterHandler instance;
    public static XiaoRegisterHandler getInstance() {
        if (instance == null) {
            synchronized (XiaoRegisterHandler.class) {
                if (instance == null) {
                    instance = new XiaoRegisterHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 声明注册方案submitFromWeb
     * @param webView
     */
    public void submitFromWeb(BridgeWebView webView){
        webView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "js调用原生返回的数据 " + data);
                function.onCallBack("js调用原生返回的数据");
            }
        });
    }
}
