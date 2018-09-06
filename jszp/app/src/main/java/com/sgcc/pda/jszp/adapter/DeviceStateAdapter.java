package com.sgcc.pda.jszp.adapter;

import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceItem;

public class DeviceStateAdapter<T extends DeviceItem> extends BaseViewHolderManager<T> {
    private DeliveryConfirmDetailItemAdapter.CountNotifiCallBack countNotifiCallBack;


    public DeviceStateAdapter(DeliveryConfirmDetailItemAdapter.CountNotifiCallBack countNotifiCallBack) {
        this.countNotifiCallBack = countNotifiCallBack;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvDate = getView(baseViewHolder, R.id.tv_date);
        TextView tvYear = getView(baseViewHolder, R.id.tv_year);
        View v_indicator = getView(baseViewHolder, R.id.v_indicator);
        TextView tvState = getView(baseViewHolder, R.id.tv_state);
        TextView tvOrderNum = getView(baseViewHolder, R.id.tv_order_num);
        tvOrderNum.setText(t.getOrder_num());
        if (getCountNotifiCallBack() != null) {
            if (baseViewHolder.getAdapterPosition() == getCountNotifiCallBack().getcount()-1) {
                v_indicator.setVisibility(View.GONE);
            } else {
                v_indicator.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_device_state;
    }

    public DeliveryConfirmDetailItemAdapter.CountNotifiCallBack getCountNotifiCallBack() {
        return countNotifiCallBack;
    }

    public void setCountNotifiCallBack(DeliveryConfirmDetailItemAdapter.CountNotifiCallBack countNotifiCallBack) {
        this.countNotifiCallBack = countNotifiCallBack;
    }
}
