package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.ScanDeviceDataAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DeviceOutDetailResultEntity;
import com.sgcc.pda.jszp.bean.DeviceOutSubTaskItem;
import com.sgcc.pda.jszp.bean.ScanDeviceDate;
import com.sgcc.pda.jszp.bean.ScanDeviceResultEntity;
import com.sgcc.pda.jszp.fragment.ScanOutFragment;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 平库出库  出库设备列表
 */
public class DeviceOutDetailActivity extends BaseActivity implements ScanOutFragment.OnCountChangedListener {
    /**
     * 请求码
     */
    public static final int GET_Detail_WHAT = 1001;
    public static final int GET_List_WHAT = 1002;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.tv_device_describle)
    TextView tvDeviceDescrible;
    @BindView(R.id.tv_task_count)
    TextView tvTaskCount;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.rv_devices)
    RecyclerView rvDevices;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    BaseItemAdapter baseItemAdapter;
    List<ScanDeviceDate> mList;
    ScanDeviceDataAdapter scanDeviceDataAdapter;
    ScanDeviceResultEntity scanDeviceResultEntity;
    //分页
    int pageNo = JzspConstants.PageStart;

    private String planDetNo;
    private String realNo;

    //详情数据
    private DeviceOutSubTaskItem deviceOutTaskItem;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_out_detail;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_Detail_WHAT:
                    //详情数据
                    DeviceOutDetailResultEntity deviceOutDetailResultEntity = (DeviceOutDetailResultEntity) msg.obj;
                    deviceOutTaskItem = deviceOutDetailResultEntity.getIoTaskDet();
                    //初始化数据
                    initUiData();
                    break;
                case GET_List_WHAT:
                    //列表数据
                    scanDeviceResultEntity = (ScanDeviceResultEntity) msg.obj;
                    if (pageNo == JzspConstants.PageStart) {
                        //刷新
                        mList.clear();
                    }
                    List<ScanDeviceDate> items = scanDeviceResultEntity.getDevData();
                    if(items !=null) {
                        mList.addAll(items);
                        baseItemAdapter.notifyDataSetChanged();
                        if (items.size() < JzspConstants.PageSize) {
                            refreshLayout.setEnableLoadmore(false);
                        }
                    }else{
                        refreshLayout.setEnableLoadmore(false);
                    }
                    break;
            }
        }
    };

    @Override
    public void initView() {
        tvTitle.setText("平库出库");
        planDetNo = getIntent().getStringExtra("planDetNo");
        realNo = getIntent().getStringExtra("realNo");

        mList = new ArrayList<>();
        //初始化数据
        initListUiData();
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void initData() {
        getDetailData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListData();
    }

    @Override
    public void initListener() {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshListData();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNo++;
                getListData();
                refreshlayout.finishLoadmore();
            }
        });
    }

    private void initUiData() {
        tvTaskCount.setText(deviceOutTaskItem.getFinishQty() + "/" + deviceOutTaskItem.getQty());
        tvDeviceDescrible.setText(deviceOutTaskItem.getEquipDesc());
        tvTaskNo.setText(deviceOutTaskItem.getPlanDetNo());
    }

    //初始化列表数据
    private void initListUiData() {
        baseItemAdapter = new BaseItemAdapter();
        rvDevices.setLayoutManager(new LinearLayoutManager(this));
        rvDevices.addItemDecoration(new SpaceItemDecoration(2));
        scanDeviceDataAdapter = new ScanDeviceDataAdapter(this);
        baseItemAdapter.register(ScanDeviceDate.class, scanDeviceDataAdapter);
        rvDevices.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(mList);
        scanDeviceDataAdapter.setOnScanClickListener(new ScanDeviceDataAdapter.OnScanClickListener() {
            @Override
            public void onDeleteDevice(int position) {
                mList.remove(position);
                baseItemAdapter.notifyDataSetChanged();
            }
        });
    }

    //获取详情数据
    private void getDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("planDetNo", planDetNo);

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_POSITIVE_OUT_PLAN_DET,
                this, map,
                mHandler, GET_Detail_WHAT, DeviceOutDetailResultEntity.class);
    }

    //刷新数据
    private void refreshListData() {
        refreshLayout.setEnableLoadmore(true);
        pageNo = JzspConstants.PageStart;
        getListData();
    }

    //获取扫描列表数据
    private void getListData() {
        Map<String, String> map = new HashMap<>();
        map.put("relaNo", realNo);
        map.put("pageNo", pageNo + "");
        map.put("pageSize", JzspConstants.PageSize + "");

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_OUTBOUND_STORAGE_DEVICE_SCAN_RESULT_QUERY,
                this, map,
                mHandler, GET_List_WHAT, ScanDeviceResultEntity.class);
    }


    @OnClick({R.id.bt_sure, R.id.bt_saoma, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_sure:
                //确认出库
                break;
            case R.id.bt_saoma:
                //出库扫码
                if(deviceOutTaskItem == null || scanDeviceResultEntity==null){
                    ToastUtils.showToast(DeviceOutDetailActivity.this,"数据还未获取成功，请稍候...");
                    return;
                }
                Intent intent = new Intent(DeviceOutDetailActivity.this, ScanForDeviceOutActivity.class);
                intent.putExtra("realNo", deviceOutTaskItem.getTaskId());
                intent.putExtra("equipCode", deviceOutTaskItem.getEquipCode());
                intent.putExtra("totalNum", deviceOutTaskItem.getQty());
                intent.putExtra("currentNum", scanDeviceResultEntity.getTotalRecords());
                startActivityForResult(intent, 111);
                break;
            case R.id.iv_delete:
                //删除
                mList.clear();
                baseItemAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 111) {
                //扫码
            }
        }
    }

    @Override
    public void onCountChanged(int count) {
        if (count == 0) btSure.setVisibility(View.GONE);
        else
            btSure.setText("确定出库(" + count + ")");
    }
}
