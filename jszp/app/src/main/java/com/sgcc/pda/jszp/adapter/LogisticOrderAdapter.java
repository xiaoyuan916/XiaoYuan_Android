package com.sgcc.pda.jszp.adapter;

import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.OrderItem;

public class LogisticOrderAdapter<T extends OrderItem> extends BaseViewHolderManager<T> {

    private orderItemAdapter.CountNotifiCallBack countNotifiCallBack;
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        View dottedLine=getView(baseViewHolder,R.id.dotted_line);
        TextView tvAddress=getView(baseViewHolder,R.id.tv_address);
        TextView tvCount=getView(baseViewHolder,R.id.tv_count);
        TextView tvReturnCount=getView(baseViewHolder,R.id.tv_return_count);


        tvAddress.setText(t.getDscAddress());
        tvCount.setText(String.valueOf(t.getDevicecount()));


        if (getCountNotifiCallBack() != null) {
            if (baseViewHolder.getAdapterPosition() == getCountNotifiCallBack().getcount()-1) {
                dottedLine.setVisibility(View.GONE);
            } else {
                dottedLine.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_logistic_order;
    }

    public orderItemAdapter.CountNotifiCallBack getCountNotifiCallBack() {
        return countNotifiCallBack;
    }

    public void setCountNotifiCallBack(orderItemAdapter.CountNotifiCallBack countNotifiCallBack) {
        this.countNotifiCallBack = countNotifiCallBack;
    }
}
