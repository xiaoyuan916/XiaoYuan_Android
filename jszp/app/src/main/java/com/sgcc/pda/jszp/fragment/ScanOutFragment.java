package com.sgcc.pda.jszp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.ScanActivity;
import com.sgcc.pda.jszp.adapter.ScanOutAdpater;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.DeviceItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class ScanOutFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.et_device_num)
    EditText etDeviceNum;
    @BindView(R.id.rv_devices)
    RecyclerView rvDevices;
    @BindView(R.id.rl_end)
    RelativeLayout rlEnd;
    @BindView(R.id.et_end_device_num)
    EditText etEndDeviceNum;
    private Context context;

    private int type;//0是扫码，1是周转箱，2是批量
    private int inorout = MyComment.SCAN_DEVICE_OUT;
    BaseItemAdapter scanoutAdapter;
    List<DeviceItem> data = new ArrayList<>();

    private OnCountChangedListener onCountChangedListener;

    @SuppressLint("ValidFragment")
    public ScanOutFragment(int type) {
        this.type = type;
    }

    public ScanOutFragment(int type, int inorout) {
        this.type = type;
        this.inorout = inorout;
    }

    public ScanOutFragment(int type, int inorout, OnCountChangedListener onCountChangedListener) {
        this.type = type;
        this.inorout = inorout;
        this.onCountChangedListener = onCountChangedListener;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_scan_out, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListener();
        initData();
    }

    public void initView() {
        if (type == 0) etDeviceNum.setHint("请输入资产编码");
        else if (type == 1) etDeviceNum.setHint("请输入周转箱编码");
        else if (type == 2) {
            etDeviceNum.setHint("请输入开始资产编号");
            rlEnd.setVisibility(View.VISIBLE);

        }

        rvDevices.setLayoutManager(new LinearLayoutManager(context));
        rvDevices.setNestedScrollingEnabled(false);
        scanoutAdapter = new BaseItemAdapter();

        data.add(new DeviceItem("5430009013315160441136", 0));
        data.add(new DeviceItem("5430009013315160441136", 0));
        data.add(new DeviceItem("5430009013315160441136", 0));
        data.add(new DeviceItem("5430009013315160441136", 0));
        data.add(new DeviceItem("5430009013315160441136", 0));
        data.add(new DeviceItem("5430009013315160441136", 0));
        data.add(new DeviceItem("5430009013315160441136", 0));
        LayoutInflater lif = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headview = lif.inflate(R.layout.head_scan_out, rvDevices, false);
        View footview = lif.inflate(R.layout.foot_orders, rvDevices, false);
        scanoutAdapter.addHeadView(headview);
        scanoutAdapter.addFootView(footview);
        ScanOutAdpater<DeviceItem> soa = new ScanOutAdpater<DeviceItem>();
        soa.setOnDeleteClickListener(new ScanOutAdpater.OnDeleteClickListener() {
            @Override
            public void onDelete(int position) {
                data.remove(position - 1);
                scanoutAdapter.notifyDataSetChanged();
                if (onCountChangedListener != null)
                    onCountChangedListener.onCountChanged(data.size());
            }
        });
        rvDevices.addItemDecoration(new SpaceItemDecoration(1));
        scanoutAdapter.register(DeviceItem.class, soa);
        rvDevices.setAdapter(scanoutAdapter);
        scanoutAdapter.setDataItems(data);
        if (onCountChangedListener != null) onCountChangedListener.onCountChanged(data.size());
    }

    private void initData() {
    }

    private void initListener() {
    }


    @OnClick({R.id.iv_saoma, R.id.iv_end_saoma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_saoma: {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                intent.putExtra("type", inorout);
                intent.putExtra("sub_type", 1);
                startActivityForResult(intent, 100);
            }

            break;
            case R.id.iv_end_saoma: {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                intent.putExtra("type", inorout);
                intent.putExtra("sub_type", 1);
                startActivityForResult(intent, 101);
            }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case 100: {
            }
            break;
            case 101: {
            }
            break;
        }

    }

    public OnCountChangedListener getOnCountChangedListener() {
        return onCountChangedListener;
    }

    public void setOnCountChangedListener(OnCountChangedListener onCountChangedListener) {
        this.onCountChangedListener = onCountChangedListener;
    }


    public static interface OnCountChangedListener {
        void onCountChanged(int count);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && onCountChangedListener != null)
            onCountChangedListener.onCountChanged(data.size());
    }
}
