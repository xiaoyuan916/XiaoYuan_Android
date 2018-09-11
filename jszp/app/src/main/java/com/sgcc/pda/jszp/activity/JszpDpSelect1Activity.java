package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

import static com.sgcc.pda.jszp.adapter.JSZPDpSelectAdapter.SELECT_DP_OPTION_REQUEST_2;

public class JszpDpSelect1Activity extends BaseActivity {

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
    /**
     * 展示的adpter
     */
    private JSZPDpSelectAdapter adapter;

    /**
     * 展示列表
     */
    private void initShowDp() {
        adapter.setOrgList(mOrgList);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jszp_dp_select1;
    }

    @Override
    public void initView() {
        adapter = new JSZPDpSelectAdapter(this, 1);
        rvOptions.setLayoutManager(new LinearLayoutManager(this));
        rvOptions.addItemDecoration(new SpaceItemDecoration(1));
        rvOptions.setAdapter(adapter);
    }

    @Override
    public void initData() {
        //获取公司的信息
        JszpDpInfoController.obtainCompanyData(this, mHandler,
                QUERY_SUB_WH_AND_DP_WHAT, null,"03");
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
