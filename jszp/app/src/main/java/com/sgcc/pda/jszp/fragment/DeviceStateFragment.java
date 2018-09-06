package com.sgcc.pda.jszp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.DeliveryConfirmDetailItemAdapter;
import com.sgcc.pda.jszp.adapter.DeviceStateAdapter;
import com.sgcc.pda.jszp.bean.DeviceItem;
import com.sgcc.pda.jszp.bean.DeviceQueryResultEntity;
import com.sgcc.pda.jszp.bean.DeviceQueryTracks;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DeviceStateFragment extends Fragment implements DeliveryConfirmDetailItemAdapter.CountNotifiCallBack{
    Unbinder unbinder;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;

    private Context context;

    BaseItemAdapter stateAdapter;
    List<DeviceItem> data;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_state, container, false);
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
        rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        stateAdapter=new BaseItemAdapter();
        data=new ArrayList<>();


        stateAdapter.register(DeviceItem.class,new DeviceStateAdapter<DeviceItem>(this));
        rvOrders.setAdapter(stateAdapter);
        stateAdapter.setDataItems(data);

    }

    private void initData() {
    }

    private void initListener() {

    }


    @Override
    public int getcount() {
        return data.size();
    }

    /**
     * 更新UI
     * @param obj
     */
    public void upDataUI(DeviceQueryResultEntity obj) {
        for (DeviceQueryTracks
                jszpDeviceQueryAssertEntity :obj.getAsset().getTracks()){
            data.add(new DeviceItem(jszpDeviceQueryAssertEntity.getBusi_id()));
        }
        stateAdapter.notifyDataSetChanged();
    }
}
