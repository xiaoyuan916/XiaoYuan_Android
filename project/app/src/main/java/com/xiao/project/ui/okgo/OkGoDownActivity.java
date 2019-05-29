package com.xiao.project.ui.okgo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;
import com.xiao.project.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OkGoDownActivity extends AppCompatActivity implements XExecutor.OnAllTaskEndListener{

    private static final String TAG = "OkGoDownActivity";

    @BindView(R.id.bt_all)
    Button btAll;
    private ArrayList<DownInfoBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_go_down);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //初始化下载控件
        OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/down/");
        OkDownload.getInstance().getThreadPool().setCorePoolSize(5);
        OkDownload.getInstance().addOnAllTaskEndListener(this);
    }

    @OnClick({R.id.bt_all})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_all:
                startDownFile();
                break;
        }
    }

    private void startDownFile() {
        mList = new ArrayList<>();
        DownInfoBean apk1 = new DownInfoBean();
        apk1.setUrl("http://116.117.158.129/f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk");
        apk1.setPriority(100);
        mList.add(apk1);

        DownInfoBean apk2 = new DownInfoBean();
        apk2.setUrl("http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk");
        apk2.setPriority(100);
        mList.add(apk2);

        for (DownInfoBean apk : mList){
            downFile(apk);
        }
    }

    private void downFile(DownInfoBean apk) {
        //这里只是演示，表示请求可以传参，怎么传都行，和okgo使用方法一样
        GetRequest<File> request = OkGo.<File>get(apk.getUrl());
        //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
        String tag = apk.getUrl();
        OkDownload.request(tag, request)//
                .priority(apk.priority)//
                .extra1(apk)//
                .save()//
                .register(new ListDownloadListener(tag, OkGoDownActivity.this))
                .start();
    }

    /**
     * 所有任务都结束
     */
    @Override
    public void onAllTaskEnd() {
        Log.d(TAG, "onAllTaskEnd: 所有任务都结束");
    }
}
