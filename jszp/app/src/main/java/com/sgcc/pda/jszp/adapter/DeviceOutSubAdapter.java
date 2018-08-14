package com.sgcc.pda.jszp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceItem;
import com.sgcc.pda.jszp.bean.DeviceOutSubTaskItem;

public class DeviceOutSubAdapter<T extends DeviceOutSubTaskItem> extends BaseViewHolderManager<T> {


    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        View vSpan = getView(baseViewHolder, R.id.v_span);
        TextView tvCompany = getView(baseViewHolder, R.id.tv_company);
        LinearLayout llOutSubHead = getView(baseViewHolder, R.id.ll_out_sub_head);
        ImageView ivDevice = getView(baseViewHolder, R.id.iv_device);
        TextView tvState = getView(baseViewHolder, R.id.tv_state);
        TextView tvOutTaskNum = getView(baseViewHolder, R.id.tv_out_task_num);
        TextView tvDescrible = getView(baseViewHolder, R.id.tv_describle);
        TextView tvCount = getView(baseViewHolder, R.id.tv_count);

        if (t.isIshead()) {
            vSpan.setVisibility(View.VISIBLE);
            llOutSubHead.setVisibility(View.VISIBLE);
            tvCompany.setText(t.getCompany());
        } else {
            vSpan.setVisibility(View.GONE);
            llOutSubHead.setVisibility(View.GONE);
        }
        tvOutTaskNum.setText("出库任务：" + t.getNum());
        tvDescrible.setText("设备码描述：" + t.getDescribe());
        tvCount.setText("出库数量：" + t.getOutcount() + "/" + t.getTaskcount());
        ivDevice.setImageResource(DeviceItem.getImgSourceId(t.getDevicetype(), false));
        switch (t.getState()) {
            case 0:
                tvState.setText("新建");
                tvState.setBackgroundResource(R.drawable.bg_out_state0);
                break;
            case 2:
                tvState.setText("已下发");
                tvState.setBackgroundResource(R.drawable.bg_out_state1);
                break;
            case 3:
                tvState.setText("出库中");
                tvState.setBackgroundResource(R.drawable.bg_out_state2);
                break;
            case 4:
                tvState.setText("已完成");
                tvState.setBackgroundResource(R.drawable.bg_out_state3);
                break;

        }


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_device_out_sub;
    }
}
