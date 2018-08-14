package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.OrderItem;

public class sendCheckDetailAdapter<T extends OrderItem> extends BaseViewHolderManager<T> {
    Context context;

    int tvblack, white;

    public sendCheckDetailAdapter(Context context) {
        this.context = context;
        tvblack = context.getResources().getColor(R.color.tv_black);
        white = context.getResources().getColor(R.color.white);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        View vLianjie = getView(baseViewHolder, R.id.v_lianjie);
        ImageView ivLocation = getView(baseViewHolder, R.id.iv_location);
        TextView tvOrderState = getView(baseViewHolder, R.id.tv_order_state);
        TextView tvAddress = getView(baseViewHolder, R.id.tv_address);
        RelativeLayout rlContent = getView(baseViewHolder, R.id.rl_content);
        tvAddress.setText(t.getDscAddress());
        if (baseViewHolder.getItemPosition() == 1) vLianjie.setVisibility(View.INVISIBLE);
        int state = t.getState();
        switch (state) {
            case 0:
                tvOrderState.setText("前往");
                break;
            case 1:
                tvOrderState.setText("即将前往");
                break;
            case 2:
                tvOrderState.setText("当前位置");
                break;
            case 3:
                tvOrderState.setText("已前往");
                break;
        }
        if (state < 2) {
            vLianjie.setBackgroundResource(R.color.fenge);
            ivLocation.setImageResource(R.drawable.weizhi_hui);
            ivLocation.setBackgroundResource(R.drawable.bg_send_check_left_normal);
            rlContent.setBackgroundResource(R.drawable.bg_send_check_right_normal);
            tvAddress.setTextColor(tvblack);
            tvOrderState.setTextColor(tvblack);
        } else {
            vLianjie.setBackgroundResource(R.color.title_green);
            ivLocation.setImageResource(R.drawable.weizhi);
            ivLocation.setBackgroundResource(R.drawable.bg_send_check_left_sended);
            rlContent.setBackgroundResource(R.drawable.bg_send_check_right_sended);
            tvAddress.setTextColor(white);
            tvOrderState.setTextColor(white);
        }


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_send_check_address;
    }
}
