package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.JSZPEquipmentScanningRequestEntity;
import com.sgcc.pda.jszp.bean.ScanResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;
import com.sgcc.pda.sdk.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 平库出库   扫码出库  手动输入
 * */
public class ManualForDeviceOutActivity extends BaseActivity {
    /**
     * 各种码值
     */
    private static final int SCAN_DEV_WHAT = 1001;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_tiaoxingma)
    EditText et_tiaoxingma;
    @BindView(R.id.et_end_tixingma)
    EditText et_end_tixingma;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.tv_scan_box)
    RadioButton tv_scan_box;
    @BindView(R.id.tv_scan_device)
    RadioButton tv_scan_device;
    @BindView(R.id.tv_scan_piliang)
    RadioButton tv_scan_piliang;
    @BindView(R.id.rg_bottom)
    RadioGroup rg_bottom;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.tv_task_num)
    TextView tvTaskNum;

    private int rbIndex;

    private String taskNo;//任务号
    private String realNo;//关联单号
    private String equipCode;//设备码
    private int totalNum;//需要扫描的总数
    private int currentNum;//目前已扫描的数量

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_DEV_WHAT:
                    ScanResultEntity scanResultEntity =
                            (ScanResultEntity) msg.obj;
                    ScanResultEntity.ScanDate scanData
                            = scanResultEntity.getScanData();

                    currentNum = currentNum+scanData.getNomralNum();
                    tvTaskNum.setText(currentNum + "/" + totalNum);
                    break;
            }
        }
    };
    @Override
    public int getLayoutResId() {
        return R.layout.activity_manual_for_device_out;
    }

    @Override
    public void initView() {
        tvTitle.setText("输入条码");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("扫码");

        rbIndex = getIntent().getIntExtra("index",0);
        changeRb(rbIndex);

        taskNo = getIntent().getStringExtra("taskNo");
        equipCode = getIntent().getStringExtra("equipCode");
        realNo = getIntent().getStringExtra("realNo");
        totalNum = getIntent().getIntExtra("totalNum",0);
        currentNum = getIntent().getIntExtra("currentNum",0);

        tvTaskNo.setText("任务号："+taskNo);
        tvTaskNum.setText(currentNum+"/"+totalNum);

        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.tv_scan_box){
                    changeRb(0);
                }else if(i == R.id.tv_scan_device){
                    changeRb(1);
                }else if(i == R.id.tv_scan_piliang){
                    changeRb(2);
                }

            }
        });
    }

    private void changeRb(int index){
        rbIndex = index;
        switch (index){
            case 0:
                //箱扫码
                tv_scan_box.setChecked(true);
                et_end_tixingma.setVisibility(View.GONE);
                et_tiaoxingma.setHint("请输入周转箱条形码");
                break;
            case 1:
                //单设备扫码
                tv_scan_device.setChecked(true);
                et_end_tixingma.setVisibility(View.GONE);
                et_tiaoxingma.setHint("请输入设备条形码");
                break;
            case 2:
                //批量扫码
                tv_scan_piliang.setChecked(true);
                et_end_tixingma.setVisibility(View.VISIBLE);
                et_tiaoxingma.setHint("请输入开始设备条形码");
                break;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.iv_close,R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(ScanForDeviceOutActivity.class);
                AppManager.getAppManager().finishActivity(DeviceOutDetailActivity.class);
                AppManager.getAppManager().finishActivity(DeviceOutActivity.class);
                finish();
                break;
            case R.id.bt_sure:
                //确定
                scanData();
                break;
        }
    }

    @Override
    public void returnback(View v) {
        finish();
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent intent = new Intent(this, ScanForDeviceOutActivity.class);
        intent.putExtra("index",rbIndex);
        intent.putExtra("taskNo", taskNo);
        intent.putExtra("realNo", realNo);
        intent.putExtra("equipCode", equipCode);
        intent.putExtra("totalNum", totalNum);
        intent.putExtra("currentNum", currentNum);
        startActivity(intent);
        finish();
    }


    //提交扫描接口
    private void scanData() {
        JSZPEquipmentScanningRequestEntity jszpEquipmentScanningRequestEntity = new JSZPEquipmentScanningRequestEntity();
        if(tv_scan_piliang.isChecked()){
            if(TextUtils.isEmpty(et_tiaoxingma.getText().toString())){
                ToastUtils.showToast(this,"请输入开始设备条形码");
                return;
            }
            if(TextUtils.isEmpty(et_end_tixingma.getText().toString())){
                ToastUtils.showToast(this,"请输入结束设备条形码");
                return;
            }
            jszpEquipmentScanningRequestEntity.setBeginBarCode(et_tiaoxingma.getText().toString());
            jszpEquipmentScanningRequestEntity.setEndBarCode(et_end_tixingma.getText().toString());
        }else {
            if(TextUtils.isEmpty(et_tiaoxingma.getText().toString())){
                ToastUtils.showToast(this,"请输入条形码");
                return;
            }
            jszpEquipmentScanningRequestEntity.setBarCodes(et_tiaoxingma.getText().toString());
        }
//        jszpEquipmentScanningRequestEntity.setIoFlag(true);
//        jszpEquipmentScanningRequestEntity.setRelaNo(realNo);
//        jszpEquipmentScanningRequestEntity.setEquipCode(equipCode);
//        jszpEquipmentScanningRequestEntity.setResultFlag(false);

//        jszpEquipmentScanningRequestEntity.setBarCodes("001");
        jszpEquipmentScanningRequestEntity.setIoFlag(true);
        jszpEquipmentScanningRequestEntity.setRelaNo("1572");
        jszpEquipmentScanningRequestEntity.setEquipCode("8200000000100989");
        jszpEquipmentScanningRequestEntity.setResultFlag(false);

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SCAN_DEV,
                this, jszpEquipmentScanningRequestEntity,
                mHandler, SCAN_DEV_WHAT,ScanResultEntity.class);
    }

}
