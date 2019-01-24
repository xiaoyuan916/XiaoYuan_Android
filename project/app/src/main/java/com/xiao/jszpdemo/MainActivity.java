package com.xiao.jszpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xiao.jszpdemo.activity.GreenDaoActivity;
import com.xiao.jszpdemo.activity.OkHttpActivity;
import com.xiao.jszpdemo.activity.RefrshActivity;
import com.xiao.jszpdemo.activity.Scan3Activity;
import com.xiao.jszpdemo.rxjava.RxjavaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_refresh)
    Button btRefresh;
    @BindView(R.id.bt_okhttp)
    Button btOkhttp;
    @BindView(R.id.bt_scaner)
    Button btScaner;
    @BindView(R.id.bt_rxjava)
    Button btRxjava;
    @BindView(R.id.bt_greendao)
    Button btGreendao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_refresh, R.id.bt_okhttp, R.id.bt_scaner, R.id.bt_rxjava, R.id.bt_greendao})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_refresh:
                startActivity(new Intent(MainActivity.this, RefrshActivity.class));
                break;
            case R.id.bt_okhttp:
                startActivity(new Intent(MainActivity.this, OkHttpActivity.class));
                break;
            case R.id.bt_scaner:
                startActivity(new Intent(MainActivity.this, Scan3Activity.class));
                break;
            case R.id.bt_rxjava:
                startActivity(new Intent(MainActivity.this, RxjavaActivity.class));
                break;
            case R.id.bt_greendao:
                startActivity(new Intent(MainActivity.this, GreenDaoActivity.class));
                break;
        }
    }
}
