package com.sgcc.pda.jszp.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.util.AppManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 平库出库   扫码出库  手动输入
 * */
public class ManualForDeviceOutActivity extends BaseActivity {

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

    private int rbIndex;
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

    @OnClick(R.id.iv_close)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(ScanForDeviceOutActivity.class);
                AppManager.getAppManager().finishActivity(DeviceOutDetailActivity.class);
                AppManager.getAppManager().finishActivity(DeviceOutActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void returnback(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        setResult(RESULT_CANCELED);
        finish();
    }
}
