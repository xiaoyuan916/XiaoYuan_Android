package com.xiao.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xiao.project.activity.GreenDaoActivity;
import com.xiao.project.activity.OkHttpActivity;
import com.xiao.project.activity.RefrshActivity;
import com.xiao.project.activity.Scan3Activity;
import com.xiao.project.activity.XCodeScannerActivity;
import com.xiao.project.mvp.MVPUserActivity;
import com.xiao.project.rxjava.RxPermissionsActivity;
import com.xiao.project.rxjava.RxjavaActivity;
import com.xiao.project.toxsl.XSLActivity;

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
    @BindView(R.id.bt_permission_go)
    Button btPermissionGo;
    @BindView(R.id.bt_mvp)
    Button btMvp;
    @BindView(R.id.bt_bluetooth)
    Button btBluetooth;
    @BindView(R.id.bt_xsl)
    Button btXsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_refresh, R.id.bt_okhttp, R.id.bt_scaner,
            R.id.bt_rxjava, R.id.bt_greendao, R.id.bt_permission_go,
            R.id.bt_mvp, R.id.bt_bluetooth,R.id.bt_xsl})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_refresh:
                startActivity(new Intent(MainActivity.this, RefrshActivity.class));
                break;
            case R.id.bt_okhttp:
                startActivity(new Intent(MainActivity.this, OkHttpActivity.class));
                break;
            case R.id.bt_scaner:
                startActivity(new Intent(MainActivity.this, XCodeScannerActivity.class));
                break;
            case R.id.bt_rxjava:
                startActivity(new Intent(MainActivity.this, RxjavaActivity.class));
                break;
            case R.id.bt_greendao:
                startActivity(new Intent(MainActivity.this, GreenDaoActivity.class));
                break;
            case R.id.bt_permission_go:
                startActivity(new Intent(MainActivity.this, RxPermissionsActivity.class));
                break;
            case R.id.bt_mvp:
                startActivity(new Intent(MainActivity.this, MVPUserActivity.class));
                break;
            case R.id.bt_bluetooth:
                startActivity(new Intent(MainActivity.this, com.xiao.project.bluetooth.MainActivity.class));
                break;
            case R.id.bt_xsl:
                startActivity(new Intent(MainActivity.this,XSLActivity.class));
                break;
        }
    }
}
