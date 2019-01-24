package com.xiao.jszpdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiao.jszpdemo.R;

public class RefrshActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrsh);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mRecyclerView=findViewById(R.id.rv);
        mRefreshLayout = findViewById(R.id.refreshLayout);

    }

    private void initData() {
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        mRefreshLayout.setEnableLoadMore(true);//启用加载
    }

    private void initListener() {
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }
        });

    }
}
