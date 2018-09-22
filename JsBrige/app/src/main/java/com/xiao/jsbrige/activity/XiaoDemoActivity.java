package com.xiao.jsbrige.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        webView.loadUrl("file:///android_asset/demo/demo.html");
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
}
