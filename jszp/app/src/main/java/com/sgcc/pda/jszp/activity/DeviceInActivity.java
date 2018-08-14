package com.sgcc.pda.jszp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.DeviceInAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DeviceInTaskItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceInActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.rv_in_tasks)
    RecyclerView rvInTasks;

    BaseItemAdapter intaskAdapter;
    List<DeviceInTaskItem> data;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_in;
    }

    @Override
    public void initView() {
        tvTitle.setText("补库入库");

        rvInTasks.setLayoutManager(new LinearLayoutManager(this));
        intaskAdapter = new BaseItemAdapter();
        data = new ArrayList<>();
        data.add(new DeviceInTaskItem("1223521514", 400, 300, "2级单相远程费控智能电能表 (载波/远程/开关内置)", 0, 0));
        data.add(new DeviceInTaskItem("1223521514", 400, 300, "2级单相远程费控智能电能表 (载波/远程/开关内置)", 0, 1));
        data.add(new DeviceInTaskItem("1223521514", 400, 300, "2级单相远程费控智能电能表 (载波/远程/开关内置)", 0, 2));
        data.add(new DeviceInTaskItem("1223521514", 400, 300, "2级单相远程费控智能电能表 (载波/远程/开关内置)", 0, 3));
        data.add(new DeviceInTaskItem("1223521514", 400, 300, "2级单相远程费控智能电能表 (载波/远程/开关内置)", 0, 3));
        data.add(new DeviceInTaskItem("1223521514", 400, 300, "2级单相远程费控智能电能表 (载波/远程/开关内置)", 0, 3));
        intaskAdapter.register(DeviceInTaskItem.class, new DeviceInAdapter<DeviceInTaskItem>());
        LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headview = lif.inflate(R.layout.head_device_in, rvInTasks, false);
        intaskAdapter.addHeadView(headview);

        rvInTasks.addItemDecoration(new SpaceItemDecoration(1));
        rvInTasks.setAdapter(intaskAdapter);
        intaskAdapter.setDataItems(data);

        intaskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent intent = new Intent(DeviceInActivity.this, DeviceInDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
    }
}
