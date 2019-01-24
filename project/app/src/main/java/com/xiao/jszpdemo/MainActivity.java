package com.xiao.jszpdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiao.jszpdemo.activity.OkHttpActivity;
import com.xiao.jszpdemo.activity.RefrshActivity;
import com.xiao.jszpdemo.activity.Scan3Activity;
import com.xiao.jszpdemo.http.HttpTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_refresh;
    private Button bt_okhttp;
    private Button bt_scaner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }



    private void initView() {
        bt_refresh = findViewById(R.id.bt_refresh);
        bt_okhttp = findViewById(R.id.bt_okhttp);
        bt_scaner = findViewById(R.id.bt_scaner);
    }

    private void initData() {
//        new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void initListener() {
        bt_refresh.setOnClickListener(this);
        bt_okhttp.setOnClickListener(this);
        bt_scaner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_refresh:
                startActivity(new Intent(MainActivity.this, RefrshActivity.class));
                break;
            case R.id.bt_okhttp:
                startActivity(new Intent(MainActivity.this, OkHttpActivity.class));
                break;
            case R.id.bt_scaner:
                startActivity(new Intent(MainActivity.this, Scan3Activity.class));
                break;
        }
    }
}
