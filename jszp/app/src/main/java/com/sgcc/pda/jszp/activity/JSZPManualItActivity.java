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
 * 补库入库  返程入库  返程签收  返程装车  手动输入
 */
public class JSZPManualItActivity extends BaseActivity {
    /**
     * 各种码值
     */
    private static final int SCAN_DEV_WHAT = 1001;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_tiaoxingma)
    EditText et_tiaoxingma;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.tv_scan_box)
    RadioButton tv_scan_box;
    @BindView(R.id.tv_scan_device)
    RadioButton tv_scan_device;
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
    private boolean isIo;//是否出入库

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

                    currentNum = currentNum + scanData.getNomralNum();
                    tvTaskNum.setText(currentNum + "/" + totalNum);
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_manual_for_it;
    }

    @Override
    public void initView() {
        tvTitle.setText("输入条码");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("扫码");

        rbIndex = getIntent().getIntExtra("index", 0);
        changeRb(rbIndex);

        taskNo = getIntent().getStringExtra("taskNo");
        equipCode = getIntent().getStringExtra("equipCode");
        realNo = getIntent().getStringExtra("realNo");
        totalNum = getIntent().getIntExtra("totalNum", 0);
        currentNum = getIntent().getIntExtra("currentNum", 0);
        isIo = getIntent().getBooleanExtra("isIo",isIo);

        tvTaskNo.setText("任务号：" + taskNo);
        tvTaskNum.setText(currentNum + "/" + totalNum);

        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.tv_scan_box) {
                    changeRb(0);
                } else if (i == R.id.tv_scan_device) {
                    changeRb(1);
                }

            }
        });
    }

    private void changeRb(int index) {
        rbIndex = index;
        switch (index) {
            case 0:
                //箱扫码
                tv_scan_box.setChecked(true);
                et_tiaoxingma.setHint("请输入周转箱条形码");
                break;
            case 1:
                //单设备扫码
                tv_scan_device.setChecked(true);
                et_tiaoxingma.setHint("请输入设备条形码");
                break;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.iv_close, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(JSZPScanItActivity.class);
                AppManager.getAppManager().finishActivity(DeviceInDetailActivity.class);
                AppManager.getAppManager().finishActivity(DeviceInActivity.class);
                AppManager.getAppManager().finishActivity(ReturnWarehouseGoodsDetailActivity.class);
                AppManager.getAppManager().finishActivity(ReturnWarehouseActivity.class);
                AppManager.getAppManager().finishActivity(ReturnWarehouseGoodsActivity.class);
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
        tofinish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tofinish();
    }

    //关闭
    private void tofinish(){
        Intent intent = new Intent();
        intent.putExtra("realcount",currentNum);
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent intent = new Intent(this, JSZPScanItActivity.class);
        intent.putExtra("index", rbIndex);
        intent.putExtra("taskNo", taskNo);
        intent.putExtra("realNo", realNo);
        intent.putExtra("equipCode", equipCode);
        intent.putExtra("totalNum", totalNum);
        intent.putExtra("currentNum", currentNum);
        intent.putExtra("isIo",isIo );
        startActivity(intent);
        finish();
    }


    //提交扫描接口
    private void scanData() {
        JSZPEquipmentScanningRequestEntity jszpEquipmentScanningRequestEntity = new JSZPEquipmentScanningRequestEntity();
        if (TextUtils.isEmpty(et_tiaoxingma.getText().toString())) {
            ToastUtils.showToast(this, "请输入条形码");
            return;
        }
        jszpEquipmentScanningRequestEntity.setBarCodes(et_tiaoxingma.getText().toString());
        jszpEquipmentScanningRequestEntity.setIoFlag(isIo);
        jszpEquipmentScanningRequestEntity.setRelaNo(realNo);
        jszpEquipmentScanningRequestEntity.setEquipCode(equipCode);
        jszpEquipmentScanningRequestEntity.setResultFlag(false);
        jszpEquipmentScanningRequestEntity.setBarCodes(et_tiaoxingma.getText().toString());
//        jszpEquipmentScanningRequestEntity.setIoFlag(isIo);
//        jszpEquipmentScanningRequestEntity.setRelaNo("1572");
//        jszpEquipmentScanningRequestEntity.setEquipCode("8200000000100989");
//        jszpEquipmentScanningRequestEntity.setResultFlag(false);

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SCAN_DEV,
                this, jszpEquipmentScanningRequestEntity,
                mHandler, SCAN_DEV_WHAT, ScanResultEntity.class);
    }

}
