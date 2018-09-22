package com.xiao.jsbrige.jsbrige;

import android.util.Log;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class XiaoCallHandler {
    private static final String TAG = "XiaoCallHandler";
    /**
     * 单例使用
     */
    private static XiaoCallHandler instance;
    public static XiaoCallHandler getInstance() {
        if (instance == null) {
            synchronized (XiaoCallHandler.class) {
                if (instance == null) {
                    instance = new XiaoCallHandler();
                }
            }
        }
        return instance;
    }



    /**
     * 原生向js发送消息
     * @param webView
     */
    public void functionInJs(BridgeWebView webView){
        webView.callHandler("functionInJs", "data from Java", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i(TAG, "reponse data from js " + data);
            }
        });
    }
}
