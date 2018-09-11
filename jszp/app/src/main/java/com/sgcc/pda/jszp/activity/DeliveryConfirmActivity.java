package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.DeliveryConfirmAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DeliveryConfirmRequestEntity;
import com.sgcc.pda.jszp.bean.DeliveryConfirmResultEntity;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.ActivityUtils;
import com.sgcc.pda.jszp.util.BottomSpaceItemDecoration;
import com.sgcc.pda.jszp.util.JSZPProgressDialogUtils;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 装车确认
 */
public class DeliveryConfirmActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_LIST_WHAT = 1001;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    BaseItemAdapter baseItemAdapter;
    private List<LogisticsDistAutoesItem> mList;

    DeliveryConfirmRequestEntity deliveryConfirmRequestEntity;//列表请求集
    DeliveryConfirmResultEntity deliveryConfirmResultEntity;//列表结果集

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case JSZPOkgoHttpUtils.DISMISS_PROGRESS_DIALOG:
                    JSZPProgressDialogUtils.getInstance().onFinish();
                    break;
                case GET_LIST_WHAT:
                    //列表数据
                    if (deliveryConfirmResultEntity == null) {
                        deliveryConfirmResultEntity = (DeliveryConfirmResultEntity) msg.obj;
                        if (deliveryConfirmResultEntity.getLogisticsDistAutoes() != null) {
                            mList.addAll(deliveryConfirmResultEntity.getLogisticsDistAutoes());
                        }
                        //初始化数据
                        initUiData();
                        if(mList.size()<JzspConstants.PageSize){
                            refreshLayout.setEnableLoadmore(false);
                        }
                    } else {
                        if (deliveryConfirmRequestEntity.getPageNo() == JzspConstants.PageStart) {
                            //刷新
                            mList.clear();
                        }
                        List<LogisticsDistAutoesItem> items = ((DeliveryConfirmResultEntity) msg.obj).getLogisticsDistAutoes();
                        mList.addAll(items);
                        baseItemAdapter.notifyDataSetChanged();
                        refreshLayout.setEnableLoadmore(true);
                        if(items.size()<JzspConstants.PageSize){
                            refreshLayout.setEnableLoadmore(false);
                        }
                    }
                    break;
            }
        }
    };
    private String carNo;
    private String driverName;
    private String kufang;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_refresh_list;
    }

    @Override
    public void initView() {
        ActivityUtils.getInstance().addActivity("DeliveryConfirmActivity",this);
        tvTitle.setText("装车确认");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("查询");

        mList = new ArrayList<>();
    }

    @Override
    public void initData() {
        deliveryConfirmRequestEntity = new DeliveryConfirmRequestEntity();
        refreshListData();
    }

    @Override
    public void initListener() {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshListData();
                refreshlayout.finishRefresh(500);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                deliveryConfirmRequestEntity.setPageNo(deliveryConfirmRequestEntity.getPageNo() + 1);
                getListData();
                refreshlayout.finishLoadmore(500);
                refreshLayout.setEnableLoadmore(false);
            }
        });
    }

    //初始化界面数据
    private void initUiData() {
        initListUiData();
    }

    //初始化列表数据
    private void initListUiData() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        baseItemAdapter = new BaseItemAdapter();
//        mList = new ArrayList<>();
        baseItemAdapter.register(LogisticsDistAutoesItem.class, new DeliveryConfirmAdapter<LogisticsDistAutoesItem>(this));
        rv.addItemDecoration(new BottomSpaceItemDecoration(7));

        rv.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(mList);
        baseItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                LogisticsDistAutoesItem itemData = (LogisticsDistAutoesItem) baseViewHolder.getItemData();
                Intent intent = new Intent(DeliveryConfirmActivity.this, DeliveryActivity.class);
                if(JzspConstants.Delivery_Task_Status_yck.equals( itemData.getTaskStatus())){
                    //待确认

                }else {
                    intent.putExtra("taskStatus",itemData.getTaskStatus());
                }
                intent.putExtra("distAutoId",itemData.getDistAutoId());
                intent.putExtra("taskNo",itemData.getTaskNo());
                startActivityForResult(intent, 101);
            }
        });

    }

    private void refreshListData() {
        deliveryConfirmRequestEntity.setPageNo(JzspConstants.PageStart);
        deliveryConfirmRequestEntity.setPageSize(JzspConstants.PageSize);
        refreshLayout.setEnableLoadmore(true);
        getListData();
    }

    /**
     * 获取物流派车列表数据
     */
    private void getListData() {
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_WAIT_CONFIRM_LOGISTICS_LIST,
                this, deliveryConfirmRequestEntity,
                mHandler, GET_LIST_WHAT, DeliveryConfirmResultEntity.class);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent intent = new Intent(this, DeliveryQueryActivity.class);
        intent.putExtra("carNo",carNo);
        intent.putExtra("driverName",driverName);
        intent.putExtra("kufang",kufang);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSZPOkgoHttpUtils.cancelHttp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        switch (requestCode) {
            case 100:
                carNo = data.getStringExtra("carNo");
                driverName = data.getStringExtra("driverName");
                kufang = data.getStringExtra("kufang");
                deliveryConfirmRequestEntity.setAutoBrandNo(carNo);
                deliveryConfirmRequestEntity.setStaffName(driverName);
                deliveryConfirmRequestEntity.setDpName(kufang);
                refreshListData();
                break;
        }
    }

}
