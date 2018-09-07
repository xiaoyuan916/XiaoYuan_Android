package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.BaseRequestEntity;
import com.sgcc.pda.jszp.bean.JszpBoxScanRequestEntity;
import com.sgcc.pda.jszp.bean.JszpRecallIDResultEntity;
import com.sgcc.pda.jszp.bean.ReturnCarResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;
import com.sgcc.pda.sdk.utils.ToastUtils;

import wangfei.scan2.Scan2Activity;
import wangfei.scan2.client.android.AutoScannerView;

import static com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils.JSZP_OK_HTTTP_FAIL;

public class JszpTurnoverBoxScanActivity extends Scan2Activity implements View.OnClickListener {

    /**
     * 响应码
     */
    private static final int INIT_TURNOVER_BOX_WHAT = 4001;
    private static final int SCAN_TURNOVER_BOXS_WHAT = 4002;
    private static final int BOX_REQUEST_CODE = 4003;
    /**
     * 控件
     */
    private ImageView mIvReturn;
    private ImageView mIvClose;
    private TextView mTvSure;
    private TextView mTvTurnoverBoxCount;

    /**
     * 箱子的个数
     */
    private int boxCount = 0;
    /**
     * hanlder处理数据
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INIT_TURNOVER_BOX_WHAT:
                    JszpRecallIDResultEntity obj = (JszpRecallIDResultEntity) msg.obj;
                    mRecallID = obj.getRecallID();
                    break;
                case SCAN_TURNOVER_BOXS_WHAT:
                    boxCount++;
                    mTvTurnoverBoxCount.setText(boxCount + "箱");
                    break;
                case JSZP_OK_HTTTP_FAIL + SCAN_TURNOVER_BOXS_WHAT:
                    ToastUtils.showToast(JszpTurnoverBoxScanActivity.this, "扫描失败");
                    break;
            }
        }
    };
    /**
     * 初始化的mRecallID信息
     */
    private String mRecallID;

    @Override
    protected void initView() {
        //设置屏幕方向为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_jszp_turnover_box_scan);
        mIvReturn = (ImageView) findViewById(R.id.iv_return);
        mIvClose = (ImageView) findViewById(R.id.iv_close);
        mTvSure = (TextView) findViewById(R.id.tv_sure);
        mTvTurnoverBoxCount = (TextView) findViewById(R.id.tv_turnover_box_count);

        initData();
        initListener();

    }

    private void initListener() {
        mIvReturn.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mTvSure.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        boxCount = intent.getIntExtra("mBoxCount", 0);
        obtainRecallIdData();
    }

    /**
     * 获取recallID
     */
    private void obtainRecallIdData() {
        BaseRequestEntity requestEntity = new BaseRequestEntity();
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_INIT_TURNOVER_BOX,
                this, requestEntity,
                mHandler, INIT_TURNOVER_BOX_WHAT, JszpRecallIDResultEntity.class);
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
        obtainScan(text);
    }

    private void obtainScan(String text) {
        if (TextUtils.isEmpty(mRecallID)) {
            ToastUtils.showToast(this, "初始化mRecallID失败,请重新点击X重新进入当前界面");
            return;
        }
        obtainScanData(text);
    }

    /**
     * 扫描箱子
     *
     * @param text
     */
    private void obtainScanData(String text) {
        JszpBoxScanRequestEntity requestEntity = new JszpBoxScanRequestEntity();
        requestEntity.setBarCodes(text);
        requestEntity.setRecallId(mRecallID);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SCAN_TURNOVER_BOXS,
                this, requestEntity,
                mHandler, SCAN_TURNOVER_BOXS_WHAT, BaseEntity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                if (TextUtils.isEmpty(mRecallID)){
                    ToastUtils.showToast(this,"召回ID为空，请x掉重新进入界面");
                    return;
                }
                Intent intent = new Intent(this, JszpTurnoverBoxRecallActivity.class);
                intent.putExtra("boxCount",boxCount);
                intent.putExtra("mRecallID",mRecallID);
                startActivity(intent);
                break;
            case R.id.iv_close:
                AppManager.getAppManager().finishActivity(JszpTurnoverBoxRecallActivity.class);
                finish();
                break;
            case R.id.tv_sure:
                //跳转到输入条码的界面
                Intent intent1 = new Intent(this, JszpBoxsManualActivity.class);
                intent1.putExtra("boxCount", boxCount);
                intent1.putExtra("mRecallID", mRecallID);
                startActivityForResult(intent1, BOX_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BOX_REQUEST_CODE && resultCode == RESULT_OK) {
            boxCount = data.getIntExtra("mBoxCount", 0);
        }
    }
}
