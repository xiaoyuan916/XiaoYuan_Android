package com.sgcc.pda.jszp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JSZPReturnSignAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.JSZPreturnStoragePlanRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPreturnStoragePlanResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 返程签收
 */
public class ReturnSignActivity extends BaseActivity {
    /**
     * 各种码值
     */
    private static final int QUERY_WAIT_SIGN_NEGATIVE_IN_PLAN_DETS_WHAT = 7001;
    private static final int SCAN_REQUEST_CODE = 7002;
    /**
     * 控件UI
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 单据的code码
     */
    private String number;

    /**
     * 界面handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case QUERY_WAIT_SIGN_NEGATIVE_IN_PLAN_DETS_WHAT:
                    //得到数据
                    JSZPreturnStoragePlanResultEntity obj = (JSZPreturnStoragePlanResultEntity) msg.obj;
                    refreshDataUI(obj);
                    break;
            }
        }
    };
    /**
     * 定义的adapter
     */
    private JSZPReturnSignAdapter jszpReturnSignAdapter;
    /**
     * 请求参数bean
     */
    private JSZPreturnStoragePlanRequestEntity requestEntity;
    /**
     * 请求数据
     */
    private ArrayList<JSZPreturnStoragePlanResultEntity.JSZPreturnStoragePlanSplitTask> splitTasks =new ArrayList<>();

    /**
     * 刷新UI
     * @param obj
     */
    private void refreshDataUI(JSZPreturnStoragePlanResultEntity obj) {
        if (obj==null||obj.getSplitTasks()==null){
            return;
        }
        if (1!=requestEntity.getPageNo()){
            splitTasks.addAll(obj.getSplitTasks());
            if (obj.getSplitTasks().size()<10){
                refreshLayout.setEnableLoadmore(false);
            }
        }else {
            splitTasks.clear();
            splitTasks.addAll(obj.getSplitTasks());
        }
        jszpReturnSignAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_return_sign;
    }

    @Override
    public void initView() {
        ivRight.setVisibility(View.VISIBLE);
        tvTitle.setText("返程签收");
        jszpReturnSignAdapter = new JSZPReturnSignAdapter(this);
        jszpReturnSignAdapter.setSplitTasks(splitTasks);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.addItemDecoration(new SpaceItemDecoration(1));
        rvOrders.setAdapter(jszpReturnSignAdapter);
    }


    @Override
    public void initData() {
//        Intent intent = getIntent();
//        number = intent.getStringExtra("number");
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(true);//启用加载
        //网络获取补库入库的计划
        obtainNetData();
    }
    /**
     * 初次获取
     */
    private void obtainNetData() {
        requestEntity = new JSZPreturnStoragePlanRequestEntity();
        requestEntity.setBaseNo(number);
        requestEntity.setPageNo(JzspConstants.PageStart);
        requestEntity.setPageSize(JzspConstants.PageSize);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_QUERY_WAIT_SIGN_NEGATIVE_IN_PLAN_DETS,
                this, requestEntity,
                mHandler, QUERY_WAIT_SIGN_NEGATIVE_IN_PLAN_DETS_WHAT,JSZPreturnStoragePlanResultEntity.class);
    }
    private void getListData() {
        requestEntity.setBaseNo(requestEntity.getBaseNo()+1);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_QUERY_WAIT_SIGN_NEGATIVE_IN_PLAN_DETS,
                this, requestEntity,
                mHandler, QUERY_WAIT_SIGN_NEGATIVE_IN_PLAN_DETS_WHAT,JSZPreturnStoragePlanResultEntity.class);
    }

    /**
     * 点击事件
     */
    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                obtainNetData();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getListData();
                refreshlayout.finishLoadmore();
            }
        });
    }

    @OnClick({R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                Intent intent = new Intent(this, ScanActivity.class);
                intent.putExtra("type", MyComment.SCAN_RETURN_SIGN);
                startActivityForResult(intent, SCAN_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SCAN_REQUEST_CODE&&resultCode== Activity.RESULT_OK){
            number = data.getStringExtra("number");
            obtainNetData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSZPOkgoHttpUtils.cancelHttp(this);
    }
}
