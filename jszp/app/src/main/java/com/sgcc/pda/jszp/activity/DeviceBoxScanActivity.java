package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.DeviceBoxScanBoxRequestEntity;
import com.sgcc.pda.jszp.bean.DeviceBoxScanResultEntity;
import com.sgcc.pda.jszp.bean.TurnoverBoxInfo;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;

import java.util.HashMap;
import java.util.Map;

import wangfei.scan2.Scan2Activity;
import wangfei.scan2.client.android.AutoScannerView;

/**
 * author:赵锦
 * date:2018/9/3 0003 14:15
 * <p>
 * 设备组箱  扫描
 */
public class DeviceBoxScanActivity extends Scan2Activity implements View.OnClickListener {
    public static final int SCAN_BOX_WHAT = 1001;
    public static final int SCAN_DEVICE_WHAT = 1002;

    SurfaceView surfaceView;
    AutoScannerView autoScanner;
    ImageView ivReturn;
    TextView btmanual;
    ImageView ivClose;
    TextView tvBoxNo;
    TextView tvType;
    TextView tvCapacity;

    private TurnoverBoxInfo turnoverBoxInfo;//周转箱信息
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_BOX_WHAT:
                    //扫描周转箱
                    DeviceBoxScanResultEntity deviceBoxScanResultEntity = (DeviceBoxScanResultEntity) msg.obj;
                    turnoverBoxInfo = deviceBoxScanResultEntity.getTurnoverBoxInfo();
                    reScan();
                    setBoxData();
                    break;
                case SCAN_BOX_WHAT + JSZPOkgoHttpUtils.JSZP_OK_HTTTP_FAIL:
                case SCAN_BOX_WHAT + JSZPOkgoHttpUtils.JSZP_OK_HTTTP_ERROR:
                    reScan();
                    break;

                case SCAN_DEVICE_WHAT:
                    //扫描设备
                    turnoverBoxInfo.setEquipQty(turnoverBoxInfo.getEquipQty()+1);
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_device_box_scan);
        AppManager.getAppManager().addActivity(this);

        turnoverBoxInfo = (TurnoverBoxInfo) getIntent().getSerializableExtra("turnoverBoxInfo");

        ivReturn = (ImageView) findViewById(R.id.iv_return);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        autoScanner = (AutoScannerView) findViewById(R.id.autoScanner);
        btmanual = (TextView) findViewById(R.id.bt_manual);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        tvBoxNo = (TextView) findViewById(R.id.tv_box_no);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvCapacity = (TextView) findViewById(R.id.tv_capacity);

        btmanual.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        ivReturn.setOnClickListener(this);

        if(turnoverBoxInfo !=null){
            setBoxData();
        }
    }

    //设置周转箱数据
    private void setBoxData() {
        tvBoxNo.setText(turnoverBoxInfo.getBarCode());
        tvCapacity.setText(turnoverBoxInfo.getEquipQty() + "/" + turnoverBoxInfo.getCap());
        tvType.setText(turnoverBoxInfo.getSortCodeLabel());
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
        if (turnoverBoxInfo == null) {
            //先扫描周转箱
            scanBoxData(text);
        } else {
            //掃描设备
            scanDeviceData(text);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    reScan();
                }
            },1000);
        }
    }

    /**
     * 扫描周转箱
     */
    private void scanBoxData(String barCode) {
        DeviceBoxScanBoxRequestEntity deviceBoxScanBoxRequestEntity = new DeviceBoxScanBoxRequestEntity();
        deviceBoxScanBoxRequestEntity.setBarCode(barCode);
        deviceBoxScanBoxRequestEntity.setReturnFlag(false);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_BOX_SCAN_TURNOVERBOX,
                this, deviceBoxScanBoxRequestEntity,
                mHandler, SCAN_BOX_WHAT, DeviceBoxScanResultEntity.class);
    }

    /**
     * 扫描设备
     */
    private void scanDeviceData(String barCode) {
        Map<String, String> map = new HashMap<>();
        map.put("boxBarCode", turnoverBoxInfo.getBarCode());//周转箱条码
        map.put("barCodes", barCode);//设备条码,多个设备条码以逗号分隔
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_ASSET_EQUIP_BIND_BOX,
                this, map,
                mHandler, SCAN_DEVICE_WHAT, BaseEntity.class);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_manual:
                //输入
                Intent intent = new Intent(this, DeviceBoxManualActivity.class);
                intent.putExtra("turnoverBoxInfo",turnoverBoxInfo);
                startActivityForResult(intent, 111);
                finish();
                break;
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(DeviceBoxActivity.class);
                finish();
                break;
            case R.id.iv_return:
                back();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    private void back(){
        Intent intent1 =  new Intent(DeviceBoxScanActivity.this, DeviceBoxActivity.class);
        if(turnoverBoxInfo!=null) {
            intent1.putExtra("barCode", turnoverBoxInfo.getBarCode());
        }
        startActivity(intent1);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}