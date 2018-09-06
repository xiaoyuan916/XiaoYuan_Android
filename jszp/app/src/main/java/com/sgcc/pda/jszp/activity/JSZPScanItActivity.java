package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.JSZPEquipmentScanningRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPEquipmentScanningResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;

import java.io.Serializable;

import wangfei.scan2.Scan2Activity;
import wangfei.scan2.client.android.AutoScannerView;

public class JSZPScanItActivity extends Scan2Activity implements View.OnClickListener {
    /**
     * 各种码值
     */
    private static final int SCAN_DEV_WHAT = 5001;
    private static final int MANUAL_REQUEST_CODE = 5002;
    /**
     * ui控件
     */
//    @BindView(R.id.scanner_view)
//    ScannerView scanner_view;
//    @BindView(R.id.iv_return)
    private ImageView iv_return;
    //    @BindView(R.id.tv_title)
    private TextView tv_title;
    //    @BindView(R.id.tv_enter)
    private TextView tv_enter;
    //    @BindView(R.id.tv_inbound_task_code)
    private TextView tv_inbound_task_code;
    //    @BindView(R.id.tv_inbound_task_count)
    private TextView tv_inbound_task_count;
    //    @BindView(R.id.tl_tab)
    private TabLayout tl_tab;
    private ImageView iv_close;
    /**
     * 入库任务
     */
    private IoTaskDets jszpDeliveryReceiptResultIoPlanDetsEntity;

    /**
     * 返程入库传过来标识
     */
    private int flag = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_DEV_WHAT:
                    JSZPEquipmentScanningResultEntity jszpEquipmentScanningResultEntity =
                            (JSZPEquipmentScanningResultEntity) msg.obj;
                    JSZPEquipmentScanningResultEntity.JSZPEquipmentScanningScanDataEntity scanData
                            = jszpEquipmentScanningResultEntity.getScanData();
                    int inputNumNow = scanData.getInputNum();
                    inputNum += inputNumNow;
                    tv_inbound_task_count.setText(inputNum + "/" + jszpDeliveryReceiptResultIoPlanDetsEntity.getQty());
                    //重置扫描
                    reScan();
                    break;
            }
        }
    };
    /**
     * 获取到的数量
     */
    private int inputNum = 0;

//    public int getLayoutResId() {
//        return R.layout.activity_jszpscan_it;
//    }

    @Override
    public void initView() {
        //设置屏幕方向为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_jszpscan_it);

        iv_return = (ImageView) findViewById(R.id.iv_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_enter = (TextView) findViewById(R.id.tv_enter);
        tv_inbound_task_code = (TextView) findViewById(R.id.tv_inbound_task_code);
        tv_inbound_task_count = (TextView) findViewById(R.id.tv_inbound_task_count);
        tl_tab = (TabLayout) findViewById(R.id.tl_tab);
        iv_close = (ImageView) findViewById(R.id.iv_close);

        initData();
        initListener();
    }

    @Override
    protected AutoScannerView getAutoScannerView() {
        return (AutoScannerView) findViewById(R.id.autoScanner);
    }

    @Override
    public SurfaceView getSurfaceView() {
        return (SurfaceView) findViewById(R.id.surfaceView);
    }

    @Override
    public void handleResult(String text) {
        obtainNetData(text);
    }

    public void initData() {
        flag = getIntent().getIntExtra("flag", 0);
        if (1 == flag) {
        }
        Intent intent = getIntent();
        Serializable serializable
                = (Serializable) intent.getStringExtra("jszpDeliveryReceiptResultIoPlanDetsEntity");
        //强转Serializable
        if (serializable instanceof IoTaskDets) {
            jszpDeliveryReceiptResultIoPlanDetsEntity = (IoTaskDets) serializable;
            //UI赋值
            initHeaderView(jszpDeliveryReceiptResultIoPlanDetsEntity);
        }
    }

    private void initHeaderView(IoTaskDets jszpDeliveryReceiptResultIoPlanDetsEntity) {
        tv_inbound_task_code.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getPlanNo());
        tv_inbound_task_count.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getQty());
    }

    public void initListener() {
        iv_return.setOnClickListener(this);
        tv_enter.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    /**
     *添加扫描设置
     */
//    @Override
//    protected void onResume() {
//        scanner_view.onResume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        scanner_view.onPause();
//        super.onPause();
//    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
//    }
//    @OnClick({R.id.iv_return,R.id.tv_enter,R.id.iv_close})
//    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_return:
                onBackPressed();
                setResult(RESULT_OK);
                break;
            case R.id.tv_enter:
                Intent intent = new Intent(this, ManualActivity.class);
                startActivityForResult(intent, MANUAL_REQUEST_CODE);
                break;
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(DeviceInDetailActivity.class);
                AppManager.getAppManager().finishActivity(DeviceInActivity.class);
                AppManager.getAppManager().finishActivity(ReturnWarehouseGoodsDetailActivity.class);
                AppManager.getAppManager().finishActivity(ReturnWarehouseActivity.class);
                AppManager.getAppManager().finishActivity(ReturnWarehouseGoodsActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MANUAL_REQUEST_CODE && requestCode == RESULT_OK) {
            String number = data.getStringExtra("number");
            obtainNetData(number);
        }
    }

    /**
     * 扫描结果的实现
     *
     * @param rawResult    扫描结果
     * @param parsedResult 抽象类，结果转换成目标类型
     * @param barcode      位图
     */
//    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
//        String text = rawResult.getText();
//        obtainNetData(text);
//    }

    /**
     * 请求网络获取扫描设备接口
     *
     * @param text
     */
    private void obtainNetData(String text) {
        JSZPEquipmentScanningRequestEntity jszpEquipmentScanningRequestEntity = new JSZPEquipmentScanningRequestEntity();
        jszpEquipmentScanningRequestEntity.setBarCodes(text);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SCAN_DEV,
                this, jszpEquipmentScanningRequestEntity,
                mHandler, SCAN_DEV_WHAT, JSZPEquipmentScanningResultEntity.class);
    }
}
