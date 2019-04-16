package com.xiao.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.xiao.project.activity.GreenDaoActivity;
import com.xiao.project.activity.OkHttpActivity;
import com.xiao.project.activity.RefrshActivity;
import com.xiao.project.activity.XCodeScannerActivity;
import com.xiao.project.adapter.RecyclerViewAdapter;
import com.xiao.project.bean.MainItem;
import com.xiao.project.map.DriveRouteActivity;
import com.xiao.project.mqtt.MqttActivity;
import com.xiao.project.mvp.MVPUserActivity;
import com.xiao.project.notification.NotificationActivity;
import com.xiao.project.rxjava.RxPermissionsActivity;
import com.xiao.project.rxjava.RxjavaActivity;
import com.xiao.project.socket.TcpClientActivity;
import com.xiao.project.toxsl.XSLActivity;
import com.xiao.project.utils.RxRecyclerViewDividerTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<MainItem> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        recyclerview.addItemDecoration(new RxRecyclerViewDividerTool(dp2px(5f)));
        RecyclerViewAdapter recyclerViewMain = new RecyclerViewAdapter(mData);
        recyclerview.setAdapter(recyclerViewMain);
    }

    public int dp2px(float dpValue) {
        final float scale = MainActivity.this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void initData() {
        mData.add(new MainItem("下拉刷新",RefrshActivity.class));
        mData.add(new MainItem("网络请求",OkHttpActivity.class));
        mData.add(new MainItem("扫描",XCodeScannerActivity.class));
        mData.add(new MainItem("GreenDao使用",GreenDaoActivity.class));
        mData.add(new MainItem("RxPermissions",RxPermissionsActivity.class));
        mData.add(new MainItem("bluetooth",com.xiao.project.bluetooth.MainActivity.class));
        mData.add(new MainItem("XSL使用",XSLActivity.class));
        mData.add(new MainItem("地图使用",DriveRouteActivity.class));
        mData.add(new MainItem("Mqtt协议",MqttActivity.class));
        mData.add(new MainItem("通知栏", NotificationActivity.class));
        mData.add(new MainItem("socket编程", TcpClientActivity.class));
    }
}
