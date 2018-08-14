package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.DeviceOutDetailActivity;
import com.sgcc.pda.jszp.bean.DeviceOutSubTaskItem;
import com.sgcc.pda.jszp.bean.DeviceOutTaskItem;

public class DeviceOutAdapter<T extends DeviceOutTaskItem> extends BaseViewHolderManager<T> {

    Context context;

    public DeviceOutAdapter(Context context) {
        this.context = context;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvTaskNum = getView(baseViewHolder, R.id.tv_task_num);
        RecyclerView rvSubOutTasks = getView(baseViewHolder, R.id.rv_sub_out_tasks);
        tvTaskNum.setText(t.getNum());

        BaseItemAdapter outtaskAdapter = new BaseItemAdapter();
        rvSubOutTasks.setLayoutManager(new LinearLayoutManager(context));
        LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footview = lif.inflate(R.layout.head_orders, rvSubOutTasks, false);
        outtaskAdapter.addFootView(footview);
        outtaskAdapter.register(DeviceOutSubTaskItem.class, new DeviceOutSubAdapter<DeviceOutSubTaskItem>());
        rvSubOutTasks.setAdapter(outtaskAdapter);
        outtaskAdapter.setDataItems(t.getSubTaskItems());
        outtaskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                context.startActivity(new Intent(context,DeviceOutDetailActivity.class));
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_device_out;
    }
}
