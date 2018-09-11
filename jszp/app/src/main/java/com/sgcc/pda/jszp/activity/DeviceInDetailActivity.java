package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JSZPOutboundScanQueryAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.JSZPOutboundScanQueryRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPOutboundScanQueryResultEntity;
import com.sgcc.pda.jszp.bean.JSZPReplenishmentLibraryRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPReplenishmentLibraryResultEntity;
import com.sgcc.pda.jszp.bean.ScanDeleteRequest;
import com.sgcc.pda.jszp.bean.ScanDeleteResult;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceInDetailActivity extends BaseActivity implements JSZPOutboundScanQueryAdapter.DeleteItemListerner {
    /**
     * 各种码
     */
    private static final int GO_JSZP_SCAN_IT_ACTIVITY_REQUEST = 3001;
    public static final int OUTBOUND_STORAGE_DEVICE_SCAN_RESULT_QUERY_WHAT = 3002;
    public static final int URL_POSITIVE_IN_WHAT = 3003;
    public static final int URL_DELETE_IN_WHAT = 3004;
    /**
     * 控件UI
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.tv_in_task_num)
    TextView tvInTaskNum;
    @BindView(R.id.tv_device_describle)
    TextView tvDeviceDescrible;
    @BindView(R.id.tv_task_count)
    TextView tvTaskCount;
    @BindView(R.id.bt_scan_it)
    Button bt_scan_it;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv_outbound_device)
    RecyclerView rv_outbound_device;
    @BindView(R.id.iv_delete_all)
    ImageView ivDeleteAll;

    /**
     * 数据bean
     */
    private IoTaskDets jszpDeliveryReceiptResultIoPlanDetsEntity;

    private ScanDeleteRequest mScanDeleteRequest;

    private JSZPOutboundScanQueryResultEntity jszpOutboundScanQueryResultEntity;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case OUTBOUND_STORAGE_DEVICE_SCAN_RESULT_QUERY_WHAT:
                    jszpOutboundScanQueryResultEntity =
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
                    break;
                case URL_POSITIVE_IN_WHAT:
                    JSZPReplenishmentLibraryResultEntity jszpReplenishmentLibraryResultEntity =
                            (JSZPReplenishmentLibraryResultEntity) msg.obj;
                    if ("1".equals(jszpReplenishmentLibraryResultEntity.getRT_F())) {
                        ToastUtils.showToast(DeviceInDetailActivity.this, "入库完成");
                        finish();
                    }
                    break;
                case URL_DELETE_IN_WHAT:
                    ScanDeleteResult mScanDeleteResult = (ScanDeleteResult) msg.obj;
                    if ("1".equals(mScanDeleteResult.getRT_F())) {
                        ToastUtils.showToast(DeviceInDetailActivity.this, "删除成功");
                        obtainNetData();
                        jszpOutboundScanQueryAdapter.notifyDataSetChanged();
//                        finish();
                    }
                    break;
            }
        }
    };
    /**
     * 列表adapter
     */
    private JSZPOutboundScanQueryAdapter jszpOutboundScanQueryAdapter;
    /**
     * 请求的bean
     */
    private JSZPOutboundScanQueryRequestEntity mRequestEntity;

    private void refreshUIMore(ArrayList<JSZPOutboundScanQueryResultEntity.
            JSZPOutboundScanQueryScanResultEntity.JSZPOutboundScanQueryDevData> devData) {
        jszpOutboundScanQueryAdapter = new JSZPOutboundScanQueryAdapter(devData);
        jszpOutboundScanQueryAdapter.setDatas(devData);
    }

    /**
     * 数据bean
     */
    private ArrayList<JSZPOutboundScanQueryResultEntity.JSZPOutboundScanQueryScanResultEntity.
            JSZPOutboundScanQueryDevData> devDatas = new ArrayList<>();

    /**
     * 数据请求回来刷新界面
     *
     * @param devData
     */
    private void refreshUI(ArrayList<JSZPOutboundScanQueryResultEntity.
            JSZPOutboundScanQueryScanResultEntity.JSZPOutboundScanQueryDevData> devData) {
        devDatas = devData;
        rv_outbound_device.setNestedScrollingEnabled(false);
        rv_outbound_device.setLayoutManager(new LinearLayoutManager(this));
        jszpOutboundScanQueryAdapter = new JSZPOutboundScanQueryAdapter(devDatas);
        rv_outbound_device.setAdapter(jszpOutboundScanQueryAdapter);
        jszpOutboundScanQueryAdapter.setDeleteItemListerner(this);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_in_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText("补库入库");
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableRefresh(true);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Serializable serializable
                = (Serializable) intent.getSerializableExtra("jszpDeliveryReceiptResultIoPlanDetsEntity");
        //强转Serializable
        if (serializable instanceof IoTaskDets) {
            jszpDeliveryReceiptResultIoPlanDetsEntity = (IoTaskDets) serializable;
            //UI赋值
            initHeaderView(jszpDeliveryReceiptResultIoPlanDetsEntity);
        }
        //获取当前入库的资产
        obtainNetData();
    }

    /**
     * 顶层UI赋值
     *
     * @param jszpDeliveryReceiptResultIoPlanDetsEntity
     */
    private void initHeaderView(IoTaskDets jszpDeliveryReceiptResultIoPlanDetsEntity) {
        tvInTaskNum.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getPlanNo());
        tvDeviceDescrible.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getEquipDesc());
        tvTaskCount.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getQty() + "");
    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                obtainNetData();
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mRequestEntity.setPageSize(mRequestEntity.getPageNo() + 1);
                getListData();
            }
        });
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

    private void getListData() {
        mRequestEntity.setRelaNo(jszpDeliveryReceiptResultIoPlanDetsEntity.getTaskId());//1572
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_OUTBOUND_STORAGE_DEVICE_SCAN_RESULT_QUERY, this,
                mRequestEntity, mHandler,
                OUTBOUND_STORAGE_DEVICE_SCAN_RESULT_QUERY_WHAT, JSZPOutboundScanQueryResultEntity.class);
    }

    @OnClick({R.id.bt_scan_it, R.id.bt_sure, R.id.iv_delete_all})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_scan_it:
                if(jszpDeliveryReceiptResultIoPlanDetsEntity==null || jszpOutboundScanQueryResultEntity==null){
                    ToastUtils.showToast(DeviceInDetailActivity.this,"数据还未获取成功，请稍候...");
                    return;
                }
                Intent intent = new Intent(DeviceInDetailActivity.this, JSZPScanItActivity.class);
                intent.putExtra("taskNo",jszpDeliveryReceiptResultIoPlanDetsEntity.getPlanNo());
                intent.putExtra("realNo",jszpDeliveryReceiptResultIoPlanDetsEntity.getTaskId());
                intent.putExtra("currentNum",jszpOutboundScanQueryResultEntity.getScanResult().getTotalRecords());
                intent.putExtra("totalNum",jszpDeliveryReceiptResultIoPlanDetsEntity.getQty());
                intent.putExtra("isIo",true);
                intent.putExtra("equipCode",jszpDeliveryReceiptResultIoPlanDetsEntity.getEquipCode());
                startActivityForResult(intent, GO_JSZP_SCAN_IT_ACTIVITY_REQUEST);
                break;
            case R.id.bt_sure:
                obtainNetSubmit();
                break;
            case R.id.iv_delete_all:
                deleteAll();
                break;
        }
    }

    //删除全部扫描
    private void deleteAll() {
        mScanDeleteRequest =
                new ScanDeleteRequest();
        mScanDeleteRequest.setTaskId(jszpDeliveryReceiptResultIoPlanDetsEntity.getTaskId());
        deleteDevice();
    }

    private void deleteDevice() {
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_SCAN_DELETE, this,
                mScanDeleteRequest, mHandler,
                URL_DELETE_IN_WHAT, ScanDeleteResult.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_JSZP_SCAN_IT_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
            obtainNetData();
        }
    }

    /**
     * 确认出库接口
     */
    private void obtainNetSubmit() {
        JSZPReplenishmentLibraryRequestEntity jszpReplenishmentLibraryRequestEntity =
                new JSZPReplenishmentLibraryRequestEntity();
        jszpReplenishmentLibraryRequestEntity.setPlanDetNo(jszpDeliveryReceiptResultIoPlanDetsEntity.getPlanDetNo());
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_POSITIVE_IN, this,
                jszpReplenishmentLibraryRequestEntity, mHandler,
                URL_POSITIVE_IN_WHAT, JSZPReplenishmentLibraryResultEntity.class);
    }


    @Override
    public void deleteItem(int position) {
        mScanDeleteRequest = new ScanDeleteRequest();
        mScanDeleteRequest.setBarCode(jszpOutboundScanQueryResultEntity.getScanResult().getDevData().get(position).getBarCode());
        deleteDevice();
    }

}
