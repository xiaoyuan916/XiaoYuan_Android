package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JSZPOutboundScanQueryAdapter;
import com.sgcc.pda.jszp.adapter.ScanDeviceDataAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.JSZPOutboundScanQueryRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPOutboundScanQueryResultEntity;
import com.sgcc.pda.jszp.bean.ReturnWarehouseGoodsConfirmRequestEntity;
import com.sgcc.pda.jszp.bean.ReturnWarehouseGoodsConfirmResultEntity;
import com.sgcc.pda.jszp.bean.ScanDeviceDate;
import com.sgcc.pda.jszp.bean.ScanDeviceResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 返程入库设备详情界面（第三层界面）
 */
public class ReturnWarehouseGoodsDetailActivity extends BaseActivity {

    private static final int WHAT_RETURN_WAREHOUSE_GOODS_CONFIRM_LIS = 9006;
    private static final int GET_List_WHAT = 9008;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.tv_return_task)
    TextView tvReturnTask;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_task_info)
    TextView tvTaskInfo;
    @BindView(R.id.sbzs)
    TextView sbzs;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.tv_statue_text)
    TextView tvStatueText;
    @BindView(R.id.tv_return_num)
    TextView tvReturnNum;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.rv_devices)
    RecyclerView rvDevices;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_state)
    ImageView ivState;

    private IoTaskDets mIoTaskDets;
    private long mPlanDetNo;//请求参数

    BaseItemAdapter baseItemAdapter;
    List<ScanDeviceDate> mList;
    ScanDeviceDataAdapter scanDeviceDataAdapter;
    ScanDeviceResultEntity scanDeviceResultEntity;
    //分页
    int pageNo = JzspConstants.PageStart;

    /**
     * 列表adapter
     */
    private JSZPOutboundScanQueryAdapter jszpOutboundScanQueryAdapter;
    /**
     * 请求的bean
     */
    private JSZPOutboundScanQueryRequestEntity mRequestEntity;
    /**
     * 数据bean
     */
    private ArrayList<JSZPOutboundScanQueryResultEntity.JSZPOutboundScanQueryScanResultEntity.
            JSZPOutboundScanQueryDevData> devDatas = new ArrayList<>();

    private void refreshUIMore(ArrayList<JSZPOutboundScanQueryResultEntity.
            JSZPOutboundScanQueryScanResultEntity.JSZPOutboundScanQueryDevData> devData) {
        jszpOutboundScanQueryAdapter = new JSZPOutboundScanQueryAdapter(devData);
        jszpOutboundScanQueryAdapter.setDatas(devData);

    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_RETURN_WAREHOUSE_GOODS_CONFIRM_LIS:
                    //得到数据
                    ReturnWarehouseGoodsConfirmResultEntity obj = (ReturnWarehouseGoodsConfirmResultEntity) msg.obj;
                    if ("1".equals(obj.getRT_F())) {
                        ToastUtils.showToast(ReturnWarehouseGoodsDetailActivity.this, "入库成功");
                    }

                    break;
                case GET_List_WHAT:
                    JSZPOutboundScanQueryResultEntity jszpOutboundScanQueryResultEntity =
                            (JSZPOutboundScanQueryResultEntity) msg.obj;
                    if (mRequestEntity.getPageNo() != 1) {
                        refreshUIMore(jszpOutboundScanQueryResultEntity.getScanResult().getDevData());
                        if (jszpOutboundScanQueryResultEntity.getScanResult().getDevData().size() < 0) {
                            refreshLayout.setEnableLoadmore(false);
                        }
                        return;
                    }
                    refreshUI(jszpOutboundScanQueryResultEntity.getScanResult().getDevData());
                    refreshLayout.setEnableLoadmore(true);

//                    //列表数据
//                    scanDeviceResultEntity = (ScanDeviceResultEntity) msg.obj;
//                    if (pageNo == JzspConstants.PageStart) {
//                        //刷新
//                        mList.clear();
//                    }
//                    List<ScanDeviceDate> items = scanDeviceResultEntity.getDevData();
//                    if (items != null) {
//                        mList.addAll(items);
//                        baseItemAdapter.notifyDataSetChanged();
//                        if (items.size() < JzspConstants.PageSize) {
//                            refreshLayout.setEnableLoadmore(false);
//                        }
//                    } else {
//                        refreshLayout.setEnableLoadmore(false);
//                    }
                    break;
            }
        }
    };


    /**
     * 数据请求回来刷新界面
     *
     * @param devData
     */
    private void refreshUI(ArrayList<JSZPOutboundScanQueryResultEntity.
            JSZPOutboundScanQueryScanResultEntity.JSZPOutboundScanQueryDevData> devData) {
        devDatas = devData;
        rvDevices.setNestedScrollingEnabled(false);
        rvDevices.setLayoutManager(new LinearLayoutManager(this));
        jszpOutboundScanQueryAdapter = new JSZPOutboundScanQueryAdapter(devDatas);
        rvDevices.setAdapter(jszpOutboundScanQueryAdapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_return_warehouse_goods_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText("返程入库");
        tvState.setVisibility(View.GONE);
        tvStatueText.setVisibility(View.GONE);
        ivState.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra("mReturnWarehousGoodsResultEntity");
        //强转Serializable
        if (serializable instanceof IoTaskDets) {
            mIoTaskDets = (IoTaskDets) serializable;
        }
        mPlanDetNo = mIoTaskDets.getPlanDetNo();

        initViewData(mIoTaskDets);
        mList = new ArrayList<>();
        //初始化数据
        obtainNetData();
    }

    /**
     * 请求获取当前的单据的条码数据
     */
    private void obtainNetData() {
        mRequestEntity = new JSZPOutboundScanQueryRequestEntity();
        mRequestEntity.setPageNo(JzspConstants.PageStart);
        mRequestEntity.setPageSize(JzspConstants.PageSize);
        getListData();
    }
//    //初始化列表数据
//    private void initListUiData() {
//        baseItemAdapter = new BaseItemAdapter();
//        rvDevices.setLayoutManager(new LinearLayoutManager(this));
//        rvDevices.addItemDecoration(new SpaceItemDecoration(2));
//        scanDeviceDataAdapter = new ScanDeviceDataAdapter(this);
//        baseItemAdapter.register(ScanDeviceDate.class, scanDeviceDataAdapter);
//        rvDevices.setAdapter(baseItemAdapter);
//        baseItemAdapter.setDataItems(mList);
//        scanDeviceDataAdapter.setOnScanClickListener(new ScanDeviceDataAdapter.OnScanClickListener() {
//            @Override
//            public void onDeleteDevice(int position) {
//                mList.remove(position);
//                baseItemAdapter.notifyDataSetChanged();
//            }
//        });
//    }

    /**
     * 绑定数据
     *
     * @param mIoTaskDets
     */
    private void initViewData(IoTaskDets mIoTaskDets) {
        tvReturnTask.setText(mIoTaskDets.getTaskNo());
        tvTaskInfo.setText(mIoTaskDets.getEquipDesc());
        tvGoodsCount.setText("" + mIoTaskDets.getQty());
    }

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
                mRequestEntity.setPageSize(mRequestEntity.getPageNo() + 1);
                getListData();
                refreshlayout.finishLoadmore();

            }
        });

    }

    //刷新数据
    private void refreshListData() {
        refreshLayout.setEnableLoadmore(true);
        pageNo = JzspConstants.PageStart;
        getListData();
    }


    private void getListData() {
        mRequestEntity.setRelaNo(mIoTaskDets.getTaskId());//1572
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_OUTBOUND_STORAGE_DEVICE_SCAN_RESULT_QUERY, this,
                mRequestEntity, mHandler,
                GET_List_WHAT, JSZPOutboundScanQueryResultEntity.class);
    }


    @OnClick({R.id.tv_scan, R.id.tv_confirm, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scan://扫一扫

                Intent intent = new Intent(ReturnWarehouseGoodsDetailActivity.this, JSZPScanItActivity.class);
                intent.putExtra("jszpDeliveryReceiptResultIoPlanDetsEntity", (Serializable) mIoTaskDets);
                intent.putExtra("flag", 1);
                startActivityForResult(intent, 999);
                break;
            case R.id.tv_confirm://确认入库
                confirmReturnRoom();
                break;
            case R.id.iv_delete://删除已扫描设备
                //删除
                mList.clear();
                baseItemAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {

            obtainNetData();


        }
    }

    /**
     * 确认入库网络请求
     */
    private void confirmReturnRoom() {
        ReturnWarehouseGoodsConfirmRequestEntity requestEntity = new ReturnWarehouseGoodsConfirmRequestEntity();
        requestEntity.setPlanDetNo(mPlanDetNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_RETURN_WAREHOUSE_GOODS_CONFIRM_LIST,
                this, requestEntity,
                mHandler, WHAT_RETURN_WAREHOUSE_GOODS_CONFIRM_LIS, ReturnWarehouseGoodsConfirmResultEntity.class);
    }


}
