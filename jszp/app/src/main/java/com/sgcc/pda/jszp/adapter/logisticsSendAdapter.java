package com.sgcc.pda.jszp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.TaskItem;

public class logisticsSendAdapter<T extends TaskItem> extends BaseViewHolderManager<T> {


    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvState=getView(baseViewHolder,R.id.tv_state);
        ImageView ivState=getView(baseViewHolder,R.id.iv_state);
        TextView tvCarNum=getView(baseViewHolder,R.id.tv_car_num);
        TextView tvSrcaddress=getView(baseViewHolder,R.id.tv_srcaddress);
        TextView tvDscaddress=getView(baseViewHolder,R.id.tv_dscaddress);
        TextView tvDriver=getView(baseViewHolder,R.id.tv_driver);
        TextView tvDriverTime=getView(baseViewHolder,R.id.tv_driver_time);




    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_logistics_send;
    }
}
