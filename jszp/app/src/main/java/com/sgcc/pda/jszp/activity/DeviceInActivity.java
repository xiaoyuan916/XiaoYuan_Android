package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;

import com.sgcc.pda.jszp.adapter.JSZPReplenishmentPlanAdapter;

import com.sgcc.pda.jszp.base.BaseActivity;

import com.sgcc.pda.jszp.bean.JSZPReplenishmentPlanRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPReplenishmentPlanResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JSZPProgressDialogUtils;

import butterknife.BindView;


/**
 * 补库入库的界面
 */
public class DeviceInActivity extends BaseActivity {

    /**
     * UI控件
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.bt_sure)
//    Button btSure;
    @BindView(R.id.rv_in_tasks)
    RecyclerView rvInTasks;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    @BindView(R.id.tv_company)
    TextView tv_company;
    /**
     * 单据的code码
     */
    private String number;
    /**
     * 请求what码
     */
    private static final int GET_WAIT_IN_POSITIVE_IN_PLAN_DET_WHAT = 2001;
    /**
     * 界面handler
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_WAIT_IN_POSITIVE_IN_PLAN_DET_WHAT:
                    //得到数据
                    JSZPReplenishmentPlanResultEntity obj = (JSZPReplenishmentPlanResultEntity) msg.obj;
                    initViewUI(obj);
                    break;
                case JSZPOkgoHttpUtils.DISMISS_PROGRESS_DIALOG:
                    JSZPProgressDialogUtils.getInstance().onFinish();
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_in;
    }

    @Override
    public void initView() {
        tvTitle.setText("补库入库");
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(false);//启用加载
    }

    /**
     * UI和视图绑定
     * @param obj
     */
    private void initViewUI(JSZPReplenishmentPlanResultEntity obj) {
        tv_order_num.setText(obj.getSplitTask().getTaskNo());
        tv_company.setText(obj.getSplitTask().getDpName());
        rvInTasks.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.gray_divider));
        rvInTasks.addItemDecoration(divider);
        rvInTasks.setAdapter(new JSZPReplenishmentPlanAdapter(this,obj.getSplitTask().getIoTaskDets()));
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        number = intent.getStringExtra("number");
        //网络获取补库入库的计划
        obtainNetData();
    }

    private void obtainNetData() {
        JSZPReplenishmentPlanRequestEntity jszpReplenishmentPlanRequestEntity = new JSZPReplenishmentPlanRequestEntity();
        jszpReplenishmentPlanRequestEntity.setBaseNo(number);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_WAIT_IN_POSITIVE_IN_PLAN_DET,
                this, jszpReplenishmentPlanRequestEntity,
                mHandler, GET_WAIT_IN_POSITIVE_IN_PLAN_DET_WHAT,JSZPReplenishmentPlanResultEntity.class);
    }

    @Override
    public void initListener() {
        /**
         * 下拉刷新界面
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500);
                obtainNetData();
            }
        });
    }

//    @OnClick(R.id.bt_sure)
//    public void onViewClicked() {
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSZPOkgoHttpUtils.cancelHttp(this);
    }
}
