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
import com.sgcc.pda.jszp.adapter.ReturnWarehouseAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.ReturnWarehousListItem;
import com.sgcc.pda.jszp.bean.ReturnWarehouseRequestEntity;
import com.sgcc.pda.jszp.bean.ReturnWarehouseResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 返程入库1
 */
public class ReturnWarehouseActivity extends BaseActivity {
    //请求码
    private static final int WHAT_RETURN_WAREHOUSE_LIS = 9001;
    private static final int SCAN_REQUEST_CODE = 9002;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_return_warehouse)
    RecyclerView rvReturnWarehouse;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    ArrayList<ReturnWarehousListItem> ReturnWarehousListItems;
    private ReturnWarehouseAdapter mReturnWarehouseAdapter;
    private ReturnWarehouseRequestEntity requestEntity;

    private String number;//配送单编号
    /**
     * 页码
     */
    private int pageNo = 1;
    /**
     * 请求数据
     */
    private ArrayList<ReturnWarehouseResultEntity.SplitTasks> splitTasks =new ArrayList<>();

    /**
     * 界面handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_RETURN_WAREHOUSE_LIS:
                    //得到数据
                    ReturnWarehouseResultEntity obj = (ReturnWarehouseResultEntity) msg.obj;
                    ArrayList<ReturnWarehouseResultEntity.SplitTasks> items = ((ReturnWarehouseResultEntity) msg.obj).getSplitTasks();
                    splitTasks.addAll(items);
                    mReturnWarehouseAdapter.notifyDataSetChanged();
                    if(items.size()<JzspConstants.PageSize){
                        refreshLayout.setEnableLoadmore(false);
                    }
                    refreshDataUI(obj);
                    break;
            }
        }
    };

    /**
     * 更新界面数据
     *
     * @param obj
     */
    private void refreshDataUI(ReturnWarehouseResultEntity obj) {

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

        mReturnWarehouseAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_return_warehouse;
    }

    @Override
    public void initView() {
        tvTitle.setText(R.string.ps10);
        ivRight.setVisibility(View.VISIBLE);
        mReturnWarehouseAdapter = new ReturnWarehouseAdapter(this);

        mReturnWarehouseAdapter.setSplitTasksList(splitTasks);
        rvReturnWarehouse.setLayoutManager(new LinearLayoutManager(this));
        rvReturnWarehouse.addItemDecoration(new SpaceItemDecoration(1));
        rvReturnWarehouse.setAdapter(mReturnWarehouseAdapter);

    }

    @Override
    public void initData() {
        refreshLayout.setEnableRefresh(true);//启用刷新
        refreshLayout.setEnableLoadmore(true);//启用加载
        //返程签收网络请求
        obtainNetData();
    }

    //网络请求
    private void obtainNetData() {
        requestEntity = new ReturnWarehouseRequestEntity();
        requestEntity.setBaseNo(number);
        requestEntity.setPageNo(JzspConstants.PageStart);
        requestEntity.setPageSize(JzspConstants.PageSize);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_RETURN_WAREHOUSE_LIST,
                this, requestEntity,
                mHandler, WHAT_RETURN_WAREHOUSE_LIS, ReturnWarehouseResultEntity.class);
    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                obtainNetData();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNo++;
                obtainNetData();
                refreshlayout.finishLoadmore();
            }
        });

    }

    @OnClick({R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                Intent intent = new Intent(this, ScanActivity.class);
                intent.putExtra("type", MyComment.SCAN_RETURN_WAREHOUSE);
                startActivityForResult(intent, SCAN_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
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
