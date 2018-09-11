package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DevicePickFinishRequestEntity;
import com.sgcc.pda.jszp.bean.DevicePickFinishResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;

import java.io.Serializable;

import butterknife.OnClick;
import wangfei.scan2.Scan2Activity;
import wangfei.scan2.client.android.AutoScannerView;

public class ScanDevicePickFinshActivity extends Scan2Activity implements View.OnClickListener {
    private static final int DEVICE_PICK_FINISH = 1303;

    TextView tvTitle;
    TextView tvManual;
    SurfaceView surfaceView;
    AutoScannerView autoScanner;
    TextView tvContent;
    TextView tvShaixuan;
    ImageView ivPickSuccess;
    private DevicePickFinishRequestEntity mRequestEntity;
    private DevicePickFinishResultEntity resultEntity;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DEVICE_PICK_FINISH:
                     resultEntity = (DevicePickFinishResultEntity) msg.obj;
                   setView(resultEntity);
                    break;
            }
        }
    };

    private void setView(DevicePickFinishResultEntity resultEntity) {
        if ("1".equals(resultEntity.RT_F )){
            ivPickSuccess.setVisibility(View.VISIBLE);
        }else {
            ivPickSuccess.setImageResource(R.mipmap.pick_fail);
            ivPickSuccess.setVisibility(View.VISIBLE);
        }
    }

    private String barCode = "";

    @Override
    public void initView() {
        setContentView(R.layout.activity_scan_device_pick_finsh);
//        AppManager.getAppManager().addActivity(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvManual = (TextView) findViewById(R.id.tv_manual);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        autoScanner = (AutoScannerView) findViewById(R.id.autoScanner);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvShaixuan = (TextView) findViewById(R.id.tv_shaixuan);
        ivPickSuccess= (ImageView) findViewById(R.id.iv_pick_success);

        tvManual.setOnClickListener(this);
        tvShaixuan.setOnClickListener(this);

        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra("DevicePickFinishRequestEntity");
        if (serializable != null && serializable instanceof DevicePickFinishRequestEntity) {
            mRequestEntity = (DevicePickFinishRequestEntity) serializable;
        }

    }

    @Override
    protected AutoScannerView getAutoScannerView() {
        return autoScanner;
    }

    @Override
    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    @Override
    public void handleResult(String text) {
        if (TextUtils.isEmpty(text)) {
            barCode = text;
            tvContent.setText("请扫描或输入设备条形码");
        } else {
            scanData(text);
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                /**
//                 *要执行的操作
//                 */
//                reScan();
//            }
//        }, 1000);//1秒后执行Runnable中的run
    }

    private void scanData(String text) {
        mRequestEntity.setBarCode(text);
        getListData();
    }


    private void getListData() {
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_DEVICE_PICK,
                this, mRequestEntity,
                mHandler, DEVICE_PICK_FINISH, DevicePickFinishResultEntity.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1303) {
            barCode = data.getStringExtra("number");
            mRequestEntity.setBarCode(barCode);
            getListData();
//            onGetNumberCallBack(number);
//            return;
        }

    }

    @OnClick({R.id.tv_manual, R.id.tv_shaixuan})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_manual://输入
                Intent intent = new Intent(this, ManualActivity.class);
                startActivityForResult(intent, 1303);
                break;
            case R.id.tv_shaixuan://筛选
                finish();
                break;
        }
    }
}
