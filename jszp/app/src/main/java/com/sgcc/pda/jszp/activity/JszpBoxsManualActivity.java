package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.JszpBoxScanRequestEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;
import com.sgcc.pda.sdk.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils.JSZP_OK_HTTTP_FAIL;

public class JszpBoxsManualActivity extends BaseActivity {
    /**
     * 响应码
     */
    private static final int SCAN_TURNOVER_BOXS_WHAT = 4002;
    /**
     * 控件
     */
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.et_tiaoxingma)
    EditText etTiaoxingma;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.tv_count_box)
    TextView tvCountBox;
    /**
     * 全局变量
     */
    private int mBoxCount;
    private String mRecallID;
    /**
     * hanlder处理数据
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_TURNOVER_BOXS_WHAT:
                    mBoxCount++;
                    tvCountBox.setText(mBoxCount + "箱");
                    break;
                case JSZP_OK_HTTTP_FAIL + SCAN_TURNOVER_BOXS_WHAT:
                    ToastUtils.showToast(JszpBoxsManualActivity.this, "扫描失败");
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jszp_boxs_manual;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mBoxCount = intent.getIntExtra("boxCount", 0);
        mRecallID = intent.getStringExtra("mRecallID");
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.bt_sure,R.id.iv_close,R.id.iv_return,R.id.tv_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_sure: {
                obtainScanData(etTiaoxingma.getText().toString());
            }
            break;
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(JszpTurnoverBoxRecallActivity.class);
                AppManager.getAppManager().finishActivity(JszpTurnoverBoxScanActivity.class);
                finish();
                break;
            case R.id.iv_return:
                Intent intent1 = new Intent(this, JszpTurnoverBoxRecallActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_scan:
                Intent intent2 = new Intent(this, JszpTurnoverBoxRecallActivity.class);
                intent2.putExtra("mBoxCount",mBoxCount);
                startActivity(intent2);
                finish();
                break;
        }
    }

    /**
     * 扫描箱子
     *
     * @param text
     */
    private void obtainScanData(String text) {
        if (TextUtils.isEmpty(mRecallID)) {
            ToastUtils.showToast(this, "初始化mRecallID失败,请重新点击X重新进入当前界面");
            return;
        }
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showToast(this, "请输入码值");
            return;
        }
        JszpBoxScanRequestEntity requestEntity = new JszpBoxScanRequestEntity();
        requestEntity.setBarCodes(text);
        requestEntity.setRecallId(mRecallID);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SCAN_TURNOVER_BOXS,
                this, requestEntity,
                mHandler, SCAN_TURNOVER_BOXS_WHAT, BaseEntity.class);
    }

    /**
     * 直接回退
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent();
        intent.putExtra("mBoxCount",mBoxCount);
        setResult(RESULT_OK,intent);
    }
}
