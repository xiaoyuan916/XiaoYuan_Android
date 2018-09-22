package com.xiao.jsbrige;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.xiao.jsbrige.jsbrige.XiaoCallHandler;
import com.xiao.jsbrige.jsbrige.XiaoRegisterHandler;
import com.xiao.jsbrige.jsbrige.XiaoWebChromeClient;

public class MainActivity extends Activity implements OnClickListener {

    private final String TAG = "MainActivity";

    BridgeWebView webView;

    Button button;

    int RESULT_CODE = 0;

    ValueCallback<Uri> mUploadMessage;

    static class Location {
        String address;
    }

    static class User {
        String name;
        Location location;
        String testStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        webView = (BridgeWebView) findViewById(R.id.webView);
        button = (Button) findViewById(R.id.button);
    }

    private void initData() {
        //webView初始化
        webView.setDefaultHandler(new DefaultHandler());
        webView.setWebChromeClient(new XiaoWebChromeClient());
        webView.loadUrl("file:///android_asset/demo/demo.html");
        //注册js调用java的代码
        XiaoRegisterHandler.getInstance().submitFromWeb(webView);
        webView.send("hello");
    }

    private void initListener() {
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (button.equals(v)) {
            XiaoCallHandler.getInstance().functionInJs(webView);
        }
    }

}
