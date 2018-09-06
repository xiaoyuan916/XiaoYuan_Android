package com.sgcc.pda.jszp.activity;

import android.view.View;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.util.AppManager;

import butterknife.OnClick;

/**
 * author:赵锦
 * date:2018/9/3 0003 14:15
 * <p>
 * 设备组箱  扫描  手动输入
 */
public class DeviceBoxManualActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_box_manual;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.bt_saoma,R.id.iv_close,R.id.iv_return})
    void onClick(View view){
        switch (view.getId()){
            case R.id.bt_saoma:
                //扫码
                finish();
                break;
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(DeviceBoxActivity.class);
                AppManager.getAppManager().finishActivity(DeviceBoxScanActivity.class);
                finish();
                break;
            case R.id.iv_return:
                finish();
                break;
        }
    }
}