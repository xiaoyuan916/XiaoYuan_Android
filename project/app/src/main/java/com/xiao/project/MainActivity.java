package com.xiao.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiao.project.activity.GreenDaoActivity;
import com.xiao.project.activity.OkHttpActivity;
import com.xiao.project.activity.RefrshActivity;
import com.xiao.project.activity.RetrofitActivity;
import com.xiao.project.activity.XCodeScannerActivity;
import com.xiao.project.adapter.RecyclerViewAdapter;
import com.xiao.project.ui.activity.BusBendActivity;
import com.xiao.project.ui.activity.BusViewActivity;
import com.xiao.project.ui.activity.ThemGlideActivity;
import com.xiao.project.ui.aidl.AIDLActivity;
import com.xiao.project.bean.MainItem;
import com.xiao.project.ui.filedownloader.FilesDownLoaderActivity;
import com.xiao.project.ui.map.DriveRouteActivity;
import com.xiao.project.ui.messenger.MessengerActivity;
import com.xiao.project.ui.mqtt.MqttActivity;
import com.xiao.project.ui.notification.NotificationActivity;
import com.xiao.project.ui.rxjava.RxPermissionsActivity;
import com.xiao.project.ui.tcp.TcpActivity;
import com.xiao.project.ui.toxsl.XSLActivity;
import com.xiao.project.ui.video.VideoActivity;
import com.xiao.project.utils.RxRecyclerViewDividerTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 6));
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
        mData.add(new MainItem("bluetooth", com.xiao.project.ui.bluetooth.MainActivity.class));
        mData.add(new MainItem("XSL使用",XSLActivity.class));
        mData.add(new MainItem("地图使用",DriveRouteActivity.class));
        mData.add(new MainItem("Mqtt协议",MqttActivity.class));
        mData.add(new MainItem("通知栏", NotificationActivity.class));
//        mData.add(new MainItem("socket编程", TcpClientActivity.class));
        mData.add(new MainItem("messenger使用", MessengerActivity.class));
        mData.add(new MainItem("AIDL使用", AIDLActivity.class));
        mData.add(new MainItem("dsbrige使用", com.xiao.project.ui.dsbridge.MainActivity.class));
        mData.add(new MainItem("Tcp使用", TcpActivity.class));
        mData.add(new MainItem("busview显示", BusViewActivity.class));
        mData.add(new MainItem("Retrofit使用", RetrofitActivity.class));
        mData.add(new MainItem("弯型busview使用", BusBendActivity.class));
        mData.add(new MainItem("filedownloader使用多任务", FilesDownLoaderActivity.class));
        mData.add(new MainItem("Glide使用", ThemGlideActivity.class));
        mData.add(new MainItem("视频播放", VideoActivity.class));
    }
}
