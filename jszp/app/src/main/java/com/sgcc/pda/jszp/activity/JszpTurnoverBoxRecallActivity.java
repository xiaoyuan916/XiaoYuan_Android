package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JszpBoxRcallAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.JszpBoxRecallDetEntity;
import com.sgcc.pda.jszp.bean.JszpBoxRequstEntity;
import com.sgcc.pda.jszp.bean.JszpBoxResultEntity;
import com.sgcc.pda.jszp.bean.JszpRecallBoxDeleteRequsetEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils.JSZP_OK_HTTTP_FAIL;

/**
 * 周转箱召回
 */
public class JszpTurnoverBoxRecallActivity extends BaseActivity {
    /**
     * 各种码值
     */
    private static final int QUERY_TURNOVER_BOXS_WHAT = 5101;
    private static final int CALL_TURNOVER_BOXS_WHAT = 5102;
    private static final int DELETE_AL_SCAN_TURNOVER_BOX_WHAT = 5103;

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_statistics)
    ImageView ivStatistics;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_box_count)
    TextView tvBoxCount;
    @BindView(R.id.tv_box_enter_recall)
    TextView tvBoxEnterRecall;
    @BindView(R.id.iv_recall_box_delete_all)
    ImageView ivRecallBoxDeleteAll;
    /**
     * 全局常量
     */
    private int mBoxCount;
    private String mRecallID;
    private JszpBoxRequstEntity mRequestEntity;

    /**
     * 处理数据data
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case QUERY_TURNOVER_BOXS_WHAT:
                    JszpBoxResultEntity obj = (JszpBoxResultEntity) msg.obj;
                    updataUI(obj);
                    break;
                case CALL_TURNOVER_BOXS_WHAT:
                    //召回成功
                    startStatisticsActivity(true);
                    break;
                case JSZP_OK_HTTTP_FAIL + CALL_TURNOVER_BOXS_WHAT:
                    //召回失败
                    ToastUtils.showToast(JszpTurnoverBoxRecallActivity.this, "召回失败");
                    break;
                case DELETE_AL_SCAN_TURNOVER_BOX_WHAT:
                    //删除成功刷新当前列表
                    //请求箱子明细列表
                    obtainData();
                    break;
                case JSZP_OK_HTTTP_FAIL + DELETE_AL_SCAN_TURNOVER_BOX_WHAT:
                    ToastUtils.showToast(JszpTurnoverBoxRecallActivity.this, "删除失败");
                    break;
            }
        }
    };
    /**
     * 箱子明细
     */
    private List<JszpBoxRecallDetEntity> mTurnoverBoxRecallDets;

    /**
     * 更新UI
     *
     * @param obj
     */
    private void updataUI(JszpBoxResultEntity obj) {
        mTurnoverBoxRecallDets = obj.getTurnoverBoxRecallDets();
        if (mTurnoverBoxRecallDets == null) return;
        tvBoxCount.setText(mTurnoverBoxRecallDets.size() + "箱");
        mBoxCount=mTurnoverBoxRecallDets.size();
        tvBoxEnterRecall.setText("召回（"+mBoxCount+")");
        boxRecallAdapter.updataUI(mTurnoverBoxRecallDets);
    }

    /**
     * adpter适配器
     */
    private JszpBoxRcallAdapter boxRecallAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jszp_turnover_box_recall;
    }

    @Override
    public void initView() {
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new SpaceItemDecoration(5));
        boxRecallAdapter = new JszpBoxRcallAdapter();
        rv.setAdapter(boxRecallAdapter);

        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableRefresh(true);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mBoxCount = intent.getIntExtra("boxCount", 0);
        mRecallID = intent.getStringExtra("mRecallID");
        tvBoxCount.setText(mBoxCount + "箱");
        //请求箱子明细列表
        obtainData();
    }

    private void obtainData() {
        mRequestEntity = new JszpBoxRequstEntity();
        mRequestEntity.setRecallId(mRecallID);
        getListData();
    }

    private void getListData() {
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_QUERY_TURNOVER_BOXS,
                this, mRequestEntity,
                mHandler, QUERY_TURNOVER_BOXS_WHAT, JszpBoxResultEntity.class);
    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                obtainData();
                refreshlayout.finishRefresh(500);
            }
        });

        boxRecallAdapter.setDeleteItemClickListener(new JszpBoxRcallAdapter.OnDeleteItemClickListener() {
            @Override
            public void onDeleteItemClick(int position) {
                JszpRecallBoxDeleteRequsetEntity requsetEntity = new JszpRecallBoxDeleteRequsetEntity();
                requsetEntity.setBarCode(mTurnoverBoxRecallDets.get(position).getBarCode());
                obtainDeleteData(requsetEntity);
            }
        });
    }

    /**
     * 删除条目的接口
     *
     * @param requsetEntity
     */
    private void obtainDeleteData(JszpRecallBoxDeleteRequsetEntity requsetEntity) {
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_DELETE_AL_SCAN_TURNOVER_BOX,
                this, requsetEntity,
                mHandler, DELETE_AL_SCAN_TURNOVER_BOX_WHAT, BaseEntity.class);
    }

    @OnClick({R.id.iv_statistics, R.id.iv_return,R.id.tv_scan_recall,R.id.tv_box_enter_recall,R.id.iv_recall_box_delete_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scan_recall:
                Intent intent2 = new Intent(this, JszpTurnoverBoxScanActivity.class);
                intent2.putExtra("mBoxCount",mBoxCount);
                intent2.putExtra("mRecallID",mRecallID);
                startActivity(intent2);
                finish();
                break;
            case R.id.iv_statistics:
                startStatisticsActivity(false);
                break;
            case R.id.tv_box_enter_recall:
                obtainEnterRecallBox();
                break;
            case  R.id.iv_recall_box_delete_all:
                JszpRecallBoxDeleteRequsetEntity requsetEntity = new JszpRecallBoxDeleteRequsetEntity();
                requsetEntity.setRecallId(mRecallID);
                obtainDeleteData(requsetEntity);
                break;
            case R.id.iv_return:
                AppManager.getAppManager().finishActivity(JszpBoxsManualActivity.class);
                AppManager.getAppManager().finishActivity(JszpTurnoverBoxScanActivity.class);
                finish();
                break;
        }
    }

    /**
     * 是否召回成功的跳转
     *
     * @param isSubmitRecall
     */
    private void startStatisticsActivity(boolean isSubmitRecall) {
        if (mTurnoverBoxRecallDets == null||mTurnoverBoxRecallDets.size()==0) {
            ToastUtils.showToast(JszpTurnoverBoxRecallActivity.this, "无明细列表");
            return;
        }
        //进入统计界面
        Intent intent = new Intent(this, JszpRecallStatisticsActivity.class);
        intent.putExtra("mBoxCount", mBoxCount + "");
        intent.putExtra("mTurnoverBoxRecallDets", (Serializable) mTurnoverBoxRecallDets);
        intent.putExtra("isSubmitRecall", isSubmitRecall);
        startActivity(intent);
    }

    /**
     * 确认召回箱子
     */
    private void obtainEnterRecallBox() {
        JszpBoxRequstEntity requstEntity = new JszpBoxRequstEntity();
        requstEntity.setRecallId(mRecallID);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_CALL_TURNOVER_BOXS,
                this, requstEntity,
                mHandler, CALL_TURNOVER_BOXS_WHAT, BaseEntity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity(JszpBoxsManualActivity.class);
        AppManager.getAppManager().finishActivity(JszpTurnoverBoxScanActivity.class);
        finish();
    }
}