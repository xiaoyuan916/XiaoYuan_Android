package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JszpQueryOrderAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.JszpQueryDistAppsItemEntity;
import com.sgcc.pda.jszp.bean.JszpQueryOrderRequestEntity;
import com.sgcc.pda.jszp.bean.JszpQueryOrderResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.AppManager;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueryOrderListActivity extends BaseActivity {
    private static final int QUERY_DIST_APPS_WHAT = 1101;
    /**
     * 控件
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_orders)
    RecyclerView rv_orders;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;


    /**
     * handler处理数据和UI
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case QUERY_DIST_APPS_WHAT:
                    JszpQueryOrderResultEntity resultEntity = (JszpQueryOrderResultEntity) msg.obj;
                    if (resultEntity == null) return;
                    if (mRequestEntity.getPageNo() == 1) {
                        distApps.clear();
                        distApps.addAll(resultEntity.getDistApps());
                    } else {
                        distApps.addAll(resultEntity.getDistApps());
                        if (resultEntity.getDistApps().size() < 10) {
                            refreshLayout.setEnableLoadmore(false);
                        } else if (resultEntity.getDistApps().size() == 10) {
                            refreshLayout.setEnableLoadmore(true);
                        }
                    }
                    mQueryOrderAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    /**
     * 网络请求的bean
     */
    private JszpQueryOrderRequestEntity mRequestEntity;

    /**
     * 列表数据
     */
    private List<JszpQueryDistAppsItemEntity> distApps = new ArrayList<>();
    /**
     * 列表的adapter
     */
    private JszpQueryOrderAdapter mQueryOrderAdapter;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_order_list;
    }

    @Override
    public void initView() {
        tvTitle.setText("订单查询");
        iv_search.setVisibility(View.VISIBLE);
        //初始化列表
        rv_orders.setNestedScrollingEnabled(false);
        rv_orders.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.gray_divider));
        rv_orders.addItemDecoration(divider);
        mQueryOrderAdapter = new JszpQueryOrderAdapter(QueryOrderListActivity.this, distApps);
        rv_orders.setAdapter(mQueryOrderAdapter);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra("JszpQueryOrderRequestEntity");
        if (serializable != null && serializable instanceof JszpQueryOrderRequestEntity) {
            mRequestEntity = (JszpQueryOrderRequestEntity) serializable;
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setEnableLoadmore(true);
            obtainData();
        }
    }

    /**
     * 获取数据
     */
    private void obtainData() {
        mRequestEntity.setPageNo(JzspConstants.PageStart);
        mRequestEntity.setPageSize(JzspConstants.PageSize);
        getListData();
    }

    private void getListData() {
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_QUERY_DIST_APPS,
                this, mRequestEntity,
                mHandler, QUERY_DIST_APPS_WHAT, JszpQueryOrderResultEntity.class);
    }

    @Override
    public void initListener() {
        //条件刷新和加载更多的方法
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500);
                obtainData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500);
                mRequestEntity.setPageNo(mRequestEntity.getPageNo() + 1);
                refreshlayout.setEnableLoadmore(false);
                getListData();
            }
        });
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @OnClick({R.id.iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_return:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity(QueryOrderActivity.class);
        finish();
    }
}
