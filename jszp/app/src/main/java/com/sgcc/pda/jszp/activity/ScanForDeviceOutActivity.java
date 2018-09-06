package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.JSZPEquipmentScanningRequestEntity;
import com.sgcc.pda.jszp.bean.ScanResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;
import com.sgcc.pda.sdk.utils.ToastUtils;

import butterknife.OnClick;
import wangfei.scan2.Scan2Activity;
import wangfei.scan2.client.android.AutoScannerView;

/**
 * 平库出库  扫码出库
 */
public class ScanForDeviceOutActivity extends Scan2Activity implements View.OnClickListener {

    /**
     * 各种码值
     */
    private static final int SCAN_DEV_WHAT = 1001;

    SurfaceView surfaceView;
    AutoScannerView autoScanner;
    ImageView ivReturn;
    TextView btmanual;
    TextView tvTaskNo;
    TextView tvtitle;
    TextView tvTaskNum;
    TextView tvContent;
    RadioButton rbScanBox;
    RadioButton rbScanDevice;
    RadioButton rbScanPiliang;
    ImageView ivClose;

    private int rbIndex;

    //批量扫描时  开始编码
    private String startCode;

    private String realNo;//关联单号
    private String equipCode;//设备码
    private int totalNum;//需要扫描的总数
    private int currentNum;//目前已扫描的数量

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_DEV_WHAT:
                    reScan();
                    ScanResultEntity scanResultEntity =
                            (ScanResultEntity) msg.obj;
                    ScanResultEntity.ScanDate scanData
                            = scanResultEntity.getScanData();

                    currentNum = currentNum+scanData.getNomralNum();
                    tvTaskNum.setText(currentNum + "/" + totalNum);
                    break;
                case SCAN_DEV_WHAT+ JSZPOkgoHttpUtils.JSZP_OK_HTTTP_ERROR:
                case SCAN_DEV_WHAT+ JSZPOkgoHttpUtils.JSZP_OK_HTTTP_FAIL:
                    //扫描提交失败
                    reScan();
                    break;

            }
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_scan_for_device_out);
        AppManager.getAppManager().addActivity(this);
        ivReturn = (ImageView) findViewById(R.id.iv_return);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        autoScanner = (AutoScannerView) findViewById(R.id.autoScanner);
        btmanual = (TextView) findViewById(R.id.bt_manual);
        tvTaskNo = (TextView) findViewById(R.id.tv_task_no);
        tvtitle = (TextView) findViewById(R.id.tv_title);
        tvTaskNum = (TextView) findViewById(R.id.tv_task_num);
        tvContent = (TextView) findViewById(R.id.tv_content);
        rbScanBox = (RadioButton) findViewById(R.id.tv_scan_box);
        rbScanDevice = (RadioButton) findViewById(R.id.tv_scan_device);
        rbScanPiliang = (RadioButton) findViewById(R.id.tv_scan_piliang);
        ivClose = (ImageView) findViewById(R.id.iv_close);


        equipCode = getIntent().getStringExtra("equipCode");
        realNo = getIntent().getStringExtra("realNo");
        totalNum = getIntent().getIntExtra("totalNum",0);
        currentNum = getIntent().getIntExtra("currentNum",0);

        tvTaskNum.setText(currentNum+"/"+totalNum);

        rbScanBox.setChecked(true);

        btmanual.setOnClickListener(this);
        rbScanPiliang.setOnClickListener(this);
        rbScanBox.setOnClickListener(this);
        rbScanDevice.setOnClickListener(this);
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
        ToastUtils.showToast(this,text);
        if(rbScanPiliang.isChecked()){
            if(TextUtils.isEmpty(startCode)){
                startCode = text;
            }else{
                scanData(text);
            }
        }else{
            scanData(text);
        }
    }

    @OnClick({R.id.bt_manual,R.id.tv_scan_box,R.id.tv_scan_device,R.id.tv_scan_piliang,R.id.iv_close})
    public void onClick(View view) {
        startCode ="";
        switch (view.getId()){
            case R.id.bt_manual:
                Intent intent = new Intent(this, ManualForDeviceOutActivity.class);
                intent.putExtra("index",rbIndex);
                startActivityForResult(intent, 111);
                break;
            case R.id.tv_scan_box:
                //周转箱
                rbScanBox.setChecked(true);
                tvContent.setText("请扫描或输入周转箱条形码");
                rbIndex = 0;
                break;
            case R.id.tv_scan_device:
                //单设备
                rbScanDevice.setChecked(true);
                tvContent.setText("请扫描或输入设备条形码");
                rbIndex = 1;
                break;
            case R.id.tv_scan_piliang:
                //批量设备
                rbScanPiliang.setChecked(true);
                tvContent.setText("请扫描或输入开始和结束设备条形码");
                rbIndex = 2;
                break;
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(DeviceOutDetailActivity.class);
                AppManager.getAppManager().finishActivity(DeviceOutActivity.class);
                finish();
                break;
            case R.id.iv_return:
                finish();
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    //提交扫描接口
    private void scanData(String code) {
        JSZPEquipmentScanningRequestEntity jszpEquipmentScanningRequestEntity = new JSZPEquipmentScanningRequestEntity();
//        if(rbScanPiliang.isChecked()){
//            jszpEquipmentScanningRequestEntity.setBeginBarCode(startCode);
//            jszpEquipmentScanningRequestEntity.setEndBarCode(code);
//        }else {
//            jszpEquipmentScanningRequestEntity.setBarCodes(code);
//        }
//        jszpEquipmentScanningRequestEntity.setIo(true);
//        jszpEquipmentScanningRequestEntity.setRelaNo(realNo);
//        jszpEquipmentScanningRequestEntity.setEquipCode(equipCode);
//        jszpEquipmentScanningRequestEntity.setResult(false);

        jszpEquipmentScanningRequestEntity.setBarCodes("001");
        jszpEquipmentScanningRequestEntity.setIoFlag(true);
        jszpEquipmentScanningRequestEntity.setRelaNo("1572");
        jszpEquipmentScanningRequestEntity.setEquipCode("8200000000100989");

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SCAN_DEV,
                this, jszpEquipmentScanningRequestEntity,
                mHandler, SCAN_DEV_WHAT,ScanResultEntity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
