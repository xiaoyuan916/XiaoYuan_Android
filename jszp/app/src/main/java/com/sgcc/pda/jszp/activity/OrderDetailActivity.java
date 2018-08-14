package com.sgcc.pda.jszp.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.devicetypeItemAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DeviceItem;

import java.util.ArrayList;

import butterknife.BindView;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_address)
    TextView tvOrderAddress;
    @BindView(R.id.et_boxcount)
    EditText etBoxcount;
    @BindView(R.id.rv_types)
    RecyclerView rvTypes;


    BaseItemAdapter typeAdapter;
    ArrayList<DeviceItem> deviceItems;
    
    


    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText("配送单详情");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("确认");
        

    }

    @Override
    public void initData() {
        rvTypes.setLayoutManager(new LinearLayoutManager(this));
        deviceItems = new ArrayList<>();
        deviceItems.add(new DeviceItem(0,"0.2S单相智能220V10A单方向1.0费率"));
        deviceItems.add(new DeviceItem(1,"采集器"));
        deviceItems.add(new DeviceItem(2,"集中器"));
        deviceItems.add(new DeviceItem(3,"通讯模块"));
        deviceItems.add(new DeviceItem(4,"组合互感器 1A：2A"));
        typeAdapter = new BaseItemAdapter();
        devicetypeItemAdapter<DeviceItem> devicetypeAdapter=new devicetypeItemAdapter<DeviceItem>();
        typeAdapter.register(DeviceItem.class, devicetypeAdapter);
        rvTypes.setAdapter(typeAdapter);
        typeAdapter.setDataItems(deviceItems);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        finish();
    }
}
