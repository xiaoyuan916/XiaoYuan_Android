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
import com.sgcc.pda.jszp.bean.DeviceOutSecondSubTaskItem;
import com.sgcc.pda.jszp.bean.DeviceOutSubTaskItem;

/**
 * 平库出库列表 二级
 * <p>
 * 配送任务 --》接收单位
 *
 * @param <T>
 */
public class DeviceOutSecondSubAdapter<T extends DeviceOutSecondSubTaskItem> extends BaseViewHolderManager<T> {
    Context mContext;
   public DeviceOutSecondSubAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, final T t) {
        TextView tvCompany = getView(baseViewHolder, R.id.tv_company);
        RecyclerView rv_second= getView(baseViewHolder, R.id.rv_second);
        tvCompany.setText(t.getDpName());

        BaseItemAdapter outtaskAdapter = new BaseItemAdapter();
        rv_second.setLayoutManager(new LinearLayoutManager(mContext));
        LayoutInflater lif = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footview = lif.inflate(R.layout.head_orders, rv_second, false);
        outtaskAdapter.addFootView(footview);
        outtaskAdapter.register(DeviceOutSubTaskItem.class, new DeviceOutSubAdapter<DeviceOutSubTaskItem>());
        rv_second.setAdapter(outtaskAdapter);
        outtaskAdapter.setDataItems(t.getIoTaskDets());
        outtaskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent intent = new Intent(mContext,DeviceOutDetailActivity.class);
                intent.putExtra("planDetNo",t.getIoTaskDets().get(baseViewHolder.getItemPosition()).getPlanDetNo());
                intent.putExtra("realNo",t.getIoTaskDets().get(baseViewHolder.getItemPosition()).getTaskId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_device_out_sub_second;
    }
}
