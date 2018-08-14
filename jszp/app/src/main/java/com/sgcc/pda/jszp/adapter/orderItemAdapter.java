package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.OrderItem;

public class orderItemAdapter<T extends OrderItem> extends BaseViewHolderManager<T> {
    private Context context;
    private CountNotifiCallBack countNotifiCallBack;
    private boolean isReturn = false;

    public orderItemAdapter(Context context) {
        this.context = context;
    }

    public orderItemAdapter() {

    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        ImageView iv_selected = getView(baseViewHolder, R.id.iv_selected);
        View v_indicator_order = getView(baseViewHolder, R.id.v_indicator_order);
        ImageView iv_right = getView(baseViewHolder, R.id.iv_right);
        TextView tv_order_title = getView(baseViewHolder, R.id.tv_order_title);
        TextView tv_order_num = getView(baseViewHolder, R.id.tv_order_num);
        TextView tv_address_title = getView(baseViewHolder, R.id.tv_address_title);
        TextView tv_address_content = getView(baseViewHolder, R.id.tv_address_content);
        TextView tv_boxs_title = getView(baseViewHolder, R.id.tv_boxs_title);
        TextView tv_boxs_num = getView(baseViewHolder, R.id.tv_boxs_num);
        tv_order_num.setText(t.getNum());
        tv_address_content.setText(t.getDscAddress());
        tv_boxs_num.setText(String.valueOf(t.getBoxcount()));


        if (isReturn) {


        } else {


        }
        if (t.isSelected()) {
            iv_selected.setImageResource(R.drawable.jiedian_down);
            v_indicator_order.setBackgroundResource(R.color.title_green);

        } else {
            iv_selected.setImageResource(R.drawable.jiedian_downhui);
            v_indicator_order.setBackgroundResource(R.color.darkgray);
        }
        if (t.isSelected() && !isReturn) {
            tv_order_title.setTextColor(context.getResources().getColor(R.color.title_green));
            tv_order_num.setTextColor(context.getResources().getColor(R.color.title_green));
            tv_address_title.setTextColor(context.getResources().getColor(R.color.title_green));
            tv_address_content.setTextColor(context.getResources().getColor(R.color.title_green));
            tv_boxs_title.setTextColor(context.getResources().getColor(R.color.title_green));
            tv_boxs_num.setTextColor(context.getResources().getColor(R.color.title_green));
        } else {
            tv_order_title.setTextColor(context.getResources().getColor(R.color.darkgray));
            tv_order_num.setTextColor(context.getResources().getColor(R.color.darkgray));
            tv_address_title.setTextColor(context.getResources().getColor(R.color.darkgray));
            tv_address_content.setTextColor(context.getResources().getColor(R.color.darkgray));
            tv_boxs_title.setTextColor(context.getResources().getColor(R.color.darkgray));
            tv_boxs_num.setTextColor(context.getResources().getColor(R.color.darkgray));
        }

        if (getCountNotifiCallBack() != null) {
            if (baseViewHolder.getAdapterPosition() == getCountNotifiCallBack().getcount()) {
                v_indicator_order.setVisibility(View.GONE);
            } else {
                v_indicator_order.setVisibility(View.VISIBLE);
            }
        }
    }

    public  CountNotifiCallBack getCountNotifiCallBack() {
        return countNotifiCallBack;
    }

    public void setCountNotifiCallBack(CountNotifiCallBack countNotifiCallBack) {
        this.countNotifiCallBack = countNotifiCallBack;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_order_layout;
    }

    public static interface CountNotifiCallBack {
        int getcount();
    }


    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }
}
