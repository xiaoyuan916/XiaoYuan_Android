package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JSZPDpSelectAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.JszpDpsResultEntity;
import com.sgcc.pda.jszp.bean.JszpOrgListEntity;
import com.sgcc.pda.jszp.util.JszpDpInfoController;

import java.util.ArrayList;

import butterknife.BindView;

public class JszpDpSelect3Activity extends BaseActivity {
    /**
     * 响应码
     */
    private static final int QUERY_SUB_WH_AND_DP_WHAT = 3001;

    @BindView(R.id.rv_options)
    RecyclerView rvOptions;

    private ArrayList<JszpOrgListEntity> mOrgList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case QUERY_SUB_WH_AND_DP_WHAT:
                    JszpDpsResultEntity obj = (JszpDpsResultEntity) msg.obj;
                    if (obj == null || obj.getOrgList() == null) return;
                    mOrgList = new ArrayList<>();
                    mOrgList.addAll(obj.getOrgList());
                    initShowDp();
                    break;
            }
        }
    };

    private void initShowDp() {
        adapter.setOrgList(mOrgList);
    }

    /**
     * 展示的adpter
     */
    private JSZPDpSelectAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jszp_dp_select3;
    }

    @Override
    public void initView() {
        adapter = new JSZPDpSelectAdapter(this,3);
        rvOptions.setLayoutManager(new LinearLayoutManager(this));
        rvOptions.addItemDecoration(new SpaceItemDecoration(1));
        rvOptions.setAdapter(adapter);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        JszpOrgListEntity jszpOrgListEntity = (JszpOrgListEntity) intent.getSerializableExtra("jszpOrgListEntity");
        //获取公司的信息
        JszpDpInfoController.obtainCompanyData(this,mHandler,
                QUERY_SUB_WH_AND_DP_WHAT,jszpOrgListEntity,"05");

    }

    @Override
    public void initListener() {

    }
}
