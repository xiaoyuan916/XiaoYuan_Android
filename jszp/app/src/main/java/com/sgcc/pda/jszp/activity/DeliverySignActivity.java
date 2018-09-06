package com.sgcc.pda.jszp.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JSZPDeliveryReceipIoPlanDetsAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;

import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;

import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptResultEntity;
import com.sgcc.pda.jszp.bean.JSZPDeliverySigningRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPDeliverySigningResultEntity;

import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JSZPProgressDialogUtils;
import com.sgcc.pda.jszp.util.JszpNumberUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 配送签收界面
 */
public class DeliverySignActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET_WHAT = 1001;
    public static final int SIGN_POSITIVE_IN_PLAN_DET_WHAT = 1002;
    /**
     * 控件UI
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //    @BindView(R.id.bt_kouling)
//    Button btKouling;
    @BindView(R.id.bt_saoma)
    Button btSaoma;
    @BindView(R.id.tv_task_num)
    TextView tvTaskNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 扫描的单据code
     */
    private String number;
    /**
     * 拆分任务编号
     */
//    private String splitTaskNo;
    /**
     * 界面handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET_WHAT:
                    //得到数据
                    JSZPDeliveryReceiptResultEntity obj = (JSZPDeliveryReceiptResultEntity) msg.obj;
                    //初始化显示UI
                    initUi(obj);
                    break;
                case SIGN_POSITIVE_IN_PLAN_DET_WHAT:
                    JSZPDeliverySigningResultEntity jszpDeliverySigningResultEntity = (JSZPDeliverySigningResultEntity) msg.obj;
                    //签收成功
                    Intent intent = new Intent(DeliverySignActivity.this, SignForResultActivity.class);
                    intent.putExtra("success", true);
                    intent.putExtra("orderNum",tvTaskNum.getText().toString());
                    intent.putExtra("boxCount",tvCount.getText().toString());
                    intent.putExtra("address",tvAddress.getText().toString());
                    startActivity(intent);
                    finish();
                    break;
                case SIGN_POSITIVE_IN_PLAN_DET_WHAT + JSZPOkgoHttpUtils.JSZP_OK_HTTTP_ERROR:
                    Intent intent2 = new Intent(DeliverySignActivity.this, SignForResultActivity.class);
                    intent2.putExtra("success", false);
                    intent2.putExtra("orderNum",tvTaskNum.getText().toString());
                    intent2.putExtra("boxCount",tvCount.getText().toString());
                    intent2.putExtra("address",tvAddress.getText().toString());
                    startActivity(intent2);
                    break;
            }
        }
    };
    private String splitTaskNo;


    /**
     * 将数据和UI绑定
     *
     * @param obj
     */
    private void initUi(JSZPDeliveryReceiptResultEntity obj) {
        if (obj==null||obj.getSplitTask()==null)return;
//        splitTaskNo = obj.getSplitTask().getSplitTaskNo();
        tvTaskNum.setText(obj.getSplitTask().getTaskNo());
        tvAddress.setText(obj.getSplitTask().getDpName());
        tvCount.setText(JszpNumberUtils.sumNumber(obj.getSplitTask().getIoTaskDets()));
        splitTaskNo = obj.getSplitTask().getSplitTaskNo();
        initListViewUI(obj.getSplitTask().getIoTaskDets());
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_pssignfor;
    }

    @Override
    public void initView() {
        tvTitle.setText("配送签收");
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(false);//启用加载
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        number = intent.getStringExtra("number");
        obtainNetData(number);
    }

    /**
     * 获取网络数据
     *
     * @param number 单据的编号
     */
    private void obtainNetData(String number) {
        JSZPDeliveryReceiptRequestEntity jszpDeliveryReceiptRequestEntity = new JSZPDeliveryReceiptRequestEntity();
        jszpDeliveryReceiptRequestEntity.setBaseNo(number);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET,
                this, jszpDeliveryReceiptRequestEntity,
                mHandler, GET_WAIT_SIGN_POSITIVE_IN_PLAN_DET_WHAT, JSZPDeliveryReceiptResultEntity.class);
    }

    /**
     * 初始化list的ui
     *
     * @param ioPlanDets
     */
    private void initListViewUI(ArrayList<IoTaskDets> ioPlanDets) {
        rvOrders.setNestedScrollingEnabled(false);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.addItemDecoration(new SpaceItemDecoration(15));
        rvOrders.setAdapter(new JSZPDeliveryReceipIoPlanDetsAdapter(ioPlanDets));

    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                obtainNetData(number);
                refreshlayout.finishRefresh();
            }
        });
    }

    @OnClick({R.id.bt_saoma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_saoma: {
                Intent intent = new Intent(this, ScanActivity.class);
                intent.putExtra("type", MyComment.SIGN_FOR);
                startActivityForResult(intent, 102);
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String number = data.getStringExtra("number");
        if (TextUtils.isEmpty(number)) {
            return;
        }
        if (resultCode == RESULT_OK) {
            obtainNetSigningData(number);
        }
    }

    /**
     * 签收确认接口
     *
     * @param number
     */
    private void obtainNetSigningData(String number) {
        JSZPDeliverySigningRequestEntity jszpDeliverySigningRequestEntity = new JSZPDeliverySigningRequestEntity();
        jszpDeliverySigningRequestEntity.setSplitTaskNo(splitTaskNo);
        jszpDeliverySigningRequestEntity.setRandomCode(number);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SIGN_POSITIVE_IN_PLAN_DET,
                this, jszpDeliverySigningRequestEntity,
                mHandler, SIGN_POSITIVE_IN_PLAN_DET_WHAT, JSZPDeliverySigningResultEntity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面消失取消掉网络请求
        JSZPOkgoHttpUtils.cancelHttp(this);
    }
}
