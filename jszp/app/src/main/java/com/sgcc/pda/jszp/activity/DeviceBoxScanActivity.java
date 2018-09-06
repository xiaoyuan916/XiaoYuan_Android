package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.util.AppManager;

import wangfei.scan2.Scan2Activity;
import wangfei.scan2.client.android.AutoScannerView;

/**
 * author:赵锦
 * date:2018/9/3 0003 14:15
 * <p>
 * 设备组箱  扫描
 */
public class DeviceBoxScanActivity extends Scan2Activity implements View.OnClickListener {
    SurfaceView surfaceView;
    AutoScannerView autoScanner;
    ImageView ivReturn;
    TextView btmanual;
    ImageView ivClose;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_device_box_scan);
        AppManager.getAppManager().addActivity(this);

        ivReturn = (ImageView) findViewById(R.id.iv_return);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        autoScanner = (AutoScannerView) findViewById(R.id.autoScanner);
        btmanual = (TextView) findViewById(R.id.bt_manual);
        ivClose = (ImageView) findViewById(R.id.iv_close);

        btmanual.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        ivReturn.setOnClickListener(this);
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_manual:
                //输入
                Intent intent = new Intent(this, DeviceBoxManualActivity.class);
                startActivityForResult(intent, 111);
                break;
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(DeviceBoxActivity.class);
                finish();
                break;
            case R.id.iv_return:
                startActivity(new Intent(DeviceBoxScanActivity.this, DeviceBoxActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}