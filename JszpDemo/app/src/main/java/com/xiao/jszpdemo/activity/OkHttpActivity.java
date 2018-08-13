package com.xiao.jszpdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiao.jszpdemo.MainActivity;
import com.xiao.jszpdemo.R;
import com.xiao.jszpdemo.http.OkGoUtils;

public class OkHttpActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button bt_post;
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        initView();
        initData();
        initListener();
    }
    private void initView() {
        bt_post = findViewById(R.id.bt_post);
        tv_text = findViewById(R.id.tv_text);
    }

    private void initData() {

    }

    private void initListener() {
        bt_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_post:
                OkGoUtils.post(OkHttpActivity.this);
                break;
        }
    }
}
