package com.xiao.jsbrige.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.xiao.jsbrige.R;
import com.xiao.jsbrige.jsbrige.XiaoCallHandler;
import com.xiao.jsbrige.jsbrige.XiaoRegisterHandler;
import com.xiao.jsbrige.jsbrige.XiaoWebChromeClient;

public class XiaoDemoActivity extends AppCompatActivity implements View.OnClickListener{
    BridgeWebView webView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_demo);
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
//        webView.loadUrl("file:///android_asset/demo/demo.html");
        webView.loadUrl("http://192.168.31.115");
        //注册js调用java的代码
        XiaoRegisterHandler.getInstance().submitFromWeb(webView);
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

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode== KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
