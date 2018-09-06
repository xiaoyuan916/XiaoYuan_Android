package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.sgcc.pda.jszp.bean.ScanDeviceDate;
import com.sgcc.pda.jszp.bean.ScanDeviceResultEntity;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:赵锦
 * date:2018/9/3 0003 14:15
 *
 * 设备组箱
 */
public class DeviceBoxActivity extends BaseActivity{

    @BindView(R.id.tv_title)
    TextView tvTitle;

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

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_box;
    }

    @Override
    public void initView() {
        tvTitle.setText("设备组箱");

    }

    @Override
    public void initData() {
        mList = new ArrayList<>();

        mList.add(new ScanDeviceDate("3434343434"));
        mList.add(new ScanDeviceDate("3434343434"));
        mList.add(new ScanDeviceDate("3434343434"));
        mList.add(new ScanDeviceDate("3434343434"));
        //初始化数据
        initListUiData();
    }

    @Override
    public void initListener() {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshListData();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNo++;
//                getListData();
                refreshlayout.finishLoadmore();
            }
        });
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

    @OnClick({R.id.bt_saoma})
    void onClick(View view){
        switch (view.getId()){
            case R.id.bt_saoma:
                //扫一扫
                Intent intent = new Intent(DeviceBoxActivity.this,DeviceBoxScanActivity.class);
                startActivity(intent);
                break;
        }
    }
}
