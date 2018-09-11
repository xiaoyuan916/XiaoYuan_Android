package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.ScanDeviceDataAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.DeviceBoxScanBoxRequestEntity;
import com.sgcc.pda.jszp.bean.DeviceBoxScanResultEntity;
import com.sgcc.pda.jszp.bean.ScanDeviceDate;
import com.sgcc.pda.jszp.bean.TurnoverBoxInfo;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:赵锦
 * date:2018/9/3 0003 14:15
 * <p>
 * 设备组箱
 */
public class DeviceBoxActivity extends BaseActivity {
    public static final int GET_SCAN_BOX_WHAT = 1001;
    public static final int DELETE_SCAN_BOX_WHAT = 1002;
    public static final int DELETE_SCAN_DEVICE_WHAT = 1003;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.rv_devices)
    RecyclerView rvDevices;

    BaseItemAdapter baseItemAdapter;
    List<ScanDeviceDate> mList;
    ScanDeviceDataAdapter scanDeviceDataAdapter;

    TurnoverBoxInfo turnoverBoxInfo;
    @BindView(R.id.tv_box_no)
    TextView tvBoxNo;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_capacity)
    TextView tvCapacity;


    private String barCode;//周转箱条码

    private int deletePosition;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_SCAN_BOX_WHAT:
                    //获取周转箱信息
                    DeviceBoxScanResultEntity deviceBoxScanResultEntity = (DeviceBoxScanResultEntity) msg.obj;
                    turnoverBoxInfo = deviceBoxScanResultEntity.getTurnoverBoxInfo();
                    setBoxData();
                    if (turnoverBoxInfo.getAssets() != null) {
                        mList.clear();
                        mList.addAll(turnoverBoxInfo.getAssets());
                        baseItemAdapter.notifyDataSetChanged();
                    }
                    break;
                case DELETE_SCAN_DEVICE_WHAT:
                    //刪除设备
                    mList.remove(deletePosition);
                    baseItemAdapter.notifyDataSetChanged();
                    break;
                case DELETE_SCAN_BOX_WHAT:
                    //刪除整箱设备
                    mList.clear();
                    baseItemAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_box;
    }

    @Override
    public void initView() {
        tvTitle.setText("设备组箱");
        barCode = getIntent().getStringExtra("barCode");
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();

//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
//        mList.add(new ScanDeviceDate("3434343434"));
        //初始化数据
        initListUiData();

        if (!TextUtils.isEmpty(barCode)) {
            getScanBoxData();
        }
    }

    @Override
    public void initListener() {
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
                deletePosition = position;
                deleteDeviceData(mList.get(position).getBarCode());
            }
        });
    }

    //设置周转箱数据
    private void setBoxData() {
        tvBoxNo.setText(turnoverBoxInfo.getBarCode());
        tvCapacity.setText(turnoverBoxInfo.getEquipQty() + "/" + turnoverBoxInfo.getCap());
        tvType.setText(turnoverBoxInfo.getSortCodeLabel());
    }

    @OnClick({R.id.bt_saoma,R.id.iv_delete})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_saoma:
                //扫一扫
                Intent intent = new Intent(DeviceBoxActivity.this, DeviceBoxScanActivity.class);
                intent.putExtra("turnoverBoxInfo", turnoverBoxInfo);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_delete:
                //删除整箱设备
                deleteBoxData();
                break;
        }
    }


    /**
     * 获取周转箱
     */
    private void getScanBoxData() {
        DeviceBoxScanBoxRequestEntity deviceBoxScanBoxRequestEntity = new DeviceBoxScanBoxRequestEntity();
        deviceBoxScanBoxRequestEntity.setBarCode(barCode);
        deviceBoxScanBoxRequestEntity.setReturnFlag(true);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_BOX_SCAN_TURNOVERBOX,
                this, deviceBoxScanBoxRequestEntity,
                mHandler, GET_SCAN_BOX_WHAT, DeviceBoxScanResultEntity.class);
    }

    /**
     * 删除设备
     */
    private void deleteDeviceData(String barCode) {
        Map<String, String> map = new HashMap<>();
        map.put("equipCateg", turnoverBoxInfo.getEquipCateg());
        map.put("barCode", barCode);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_ASSET_EQUIP_REMOVE_BIND_BOX,
                this, map,
                mHandler, DELETE_SCAN_DEVICE_WHAT, BaseEntity.class);
    }

    /**
     * 删除整箱设备
     */
    private void deleteBoxData() {
        if(turnoverBoxInfo==null){
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("equipCateg", turnoverBoxInfo.getEquipCateg());
        map.put("boxBarCode", turnoverBoxInfo.getBarCode());
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_ASSET_EQUIP_REMOVE_BIND_BOX,
                this, map,
                mHandler, DELETE_SCAN_BOX_WHAT, BaseEntity.class);
    }
}
