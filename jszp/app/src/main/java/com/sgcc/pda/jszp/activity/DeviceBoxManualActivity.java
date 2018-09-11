package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.DeviceBoxScanBoxRequestEntity;
import com.sgcc.pda.jszp.bean.DeviceBoxScanResultEntity;
import com.sgcc.pda.jszp.bean.TurnoverBoxInfo;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:赵锦
 * date:2018/9/3 0003 14:15
 * <p>
 * 设备组箱  扫描  手动输入
 */
public class DeviceBoxManualActivity extends BaseActivity {
    public static final int SCAN_BOX_WHAT = 1001;
    public static final int SCAN_DEVICE_WHAT = 1002;


    @BindView(R.id.bt_saoma)
    TextView btSaoma;
    @BindView(R.id.tv_box_no)
    TextView tvBoxNo;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_capacity)
    TextView tvCapacity;
    @BindView(R.id.et_tiaoxingma)
    EditText etTiaoxingma;


    private TurnoverBoxInfo turnoverBoxInfo;


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
                    setBoxData();
                    break;
                case SCAN_DEVICE_WHAT:
                    //扫描设备
                    turnoverBoxInfo.setEquipQty(turnoverBoxInfo.getEquipQty() + 1);
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_box_manual;
    }

    @Override
    public void initView() {
        turnoverBoxInfo = (TurnoverBoxInfo) getIntent().getSerializableExtra("turnoverBoxInfo");
        if (turnoverBoxInfo != null) {
            setBoxData();
        }


    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        etTiaoxingma.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId== EditorInfo.IME_ACTION_DONE ||(keyEvent!=null&&keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    confirm();
                    return true;
                }
                return false;
            }
        });
    }


    //设置周转箱数据
    private void setBoxData() {
        tvBoxNo.setText(turnoverBoxInfo.getBarCode());
        tvCapacity.setText(turnoverBoxInfo.getEquipQty() + "/" + turnoverBoxInfo.getCap());
        tvType.setText(turnoverBoxInfo.getSortCodeLabel());
    }


    @OnClick({R.id.bt_saoma, R.id.iv_close, R.id.iv_return, R.id.bt_sure})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_saoma:
                //扫码
                Intent intent = new Intent(DeviceBoxManualActivity.this, DeviceBoxScanActivity.class);
                intent.putExtra("turnoverBoxInfo", turnoverBoxInfo);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(DeviceBoxActivity.class);
                finish();
                break;
            case R.id.iv_return:
                back();
                break;
            case R.id.bt_sure:
                //确定
                confirm();
                break;
        }
    }

    private void back() {
        Intent intent1 = new Intent(DeviceBoxManualActivity.this, DeviceBoxActivity.class);
        if (turnoverBoxInfo != null) {
            intent1.putExtra("barCode", turnoverBoxInfo.getBarCode());
        }
        startActivity(intent1);
        finish();
    }

    private void confirm() {
        String text = etTiaoxingma.getText().toString();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showToast(this, "请输入条形码");
            return;
        }
        if (turnoverBoxInfo == null) {
            //先扫描周转箱
            scanBoxData(text);
        } else {
            //掃描设备
            scanDeviceData(text);
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
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }
}