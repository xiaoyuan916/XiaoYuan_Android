package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceOutSecondSubTaskItem;
import com.sgcc.pda.jszp.bean.DeviceOutTaskItem;

/**
 * 平库出库  列表
 *
 * @param <T>
 */
public class DeviceOutAdapter<T extends DeviceOutTaskItem> extends BaseViewHolderManager<T> {

    Context context;

    public DeviceOutAdapter(Context context) {
        this.context = context;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvTaskNum = getView(baseViewHolder, R.id.tv_task_num);
        final ImageView iv_arrow = getView(baseViewHolder, R.id.iv_arrow);
        final RecyclerView rvSubOutTasks = getView(baseViewHolder, R.id.rv_sub_out_tasks);
        LinearLayout ll_task = getView(baseViewHolder, R.id.ll_task);
        tvTaskNum.setText(t.getTaskNo());

        BaseItemAdapter outtaskAdapter = new BaseItemAdapter();
        rvSubOutTasks.setLayoutManager(new LinearLayoutManager(context));
        LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footview = lif.inflate(R.layout.head_orders, rvSubOutTasks, false);
        outtaskAdapter.addFootView(footview);
        outtaskAdapter.register(DeviceOutSecondSubTaskItem.class, new DeviceOutSecondSubAdapter<DeviceOutSecondSubTaskItem>(context));
        rvSubOutTasks.setAdapter(outtaskAdapter);
        outtaskAdapter.setDataItems(t.getSubTaskItems());
        rvSubOutTasks.setVisibility(View.GONE);
        iv_arrow.setBackgroundResource(R.drawable.down);
        ll_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvSubOutTasks.setVisibility(rvSubOutTasks.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                iv_arrow.setBackgroundResource(rvSubOutTasks.getVisibility() == View.GONE ?R.drawable.down : R.drawable.up);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_device_out;
    }
}
