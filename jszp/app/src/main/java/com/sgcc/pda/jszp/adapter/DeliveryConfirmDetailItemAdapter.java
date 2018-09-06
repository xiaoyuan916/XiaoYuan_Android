package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesDetsItem;
import com.sgcc.pda.jszp.bean.OrderItem;
/**
 * 装车确认列表--》装车确认详情  配送单信息列表
 */
public class DeliveryConfirmDetailItemAdapter<T extends LogisticsDistAutoesDetsItem> extends BaseViewHolderManager<T> {
    private Context context;
    private CountNotifiCallBack countNotifiCallBack;

    public DeliveryConfirmDetailItemAdapter(Context context) {
        this.context = context;
    }

    public DeliveryConfirmDetailItemAdapter() {

    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        LinearLayout ll_order = getView(baseViewHolder,R.id.ll_order);
        ImageView iv_selected = getView(baseViewHolder, R.id.iv_selected);
        View v_indicator_order = getView(baseViewHolder, R.id.v_indicator_order);
        View v_indicator_order_head = getView(baseViewHolder, R.id.v_indicator_order_head);
        ImageView iv_right = getView(baseViewHolder, R.id.iv_right);
        TextView tv_order_title = getView(baseViewHolder, R.id.tv_order_title);
        TextView tv_order_num = getView(baseViewHolder, R.id.tv_order_num);
        TextView tv_address_title = getView(baseViewHolder, R.id.tv_address_title);
        TextView tv_address_content = getView(baseViewHolder, R.id.tv_address_content);
        TextView tv_boxs_title = getView(baseViewHolder, R.id.tv_boxs_title);
        TextView tv_boxs_num = getView(baseViewHolder, R.id.tv_boxs_num);

        ll_order.setVisibility(View.GONE);
//        tv_order_num.setText(t.getDistAutoDetId()+"");
        tv_address_content.setText(t.getDpName());
        if (!TextUtils.isEmpty(t.getRealcount())){
            tv_boxs_num.setText(t.getRealcount());
        }

        if (t.isSelected()) {
            iv_selected.setImageResource(R.drawable.jiedian_down);
            v_indicator_order.setBackgroundResource(R.color.title_green);
            v_indicator_order_head.setBackgroundResource(R.color.title_green);
        } else {
            iv_selected.setImageResource(R.drawable.jiedian_downhui);
            v_indicator_order.setBackgroundResource(R.color.darkgray);
            v_indicator_order_head.setBackgroundResource(R.color.darkgray);
        }
        if (t.isConfirmed()) {
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
            if (baseViewHolder.getAdapterPosition() == 0) {
                v_indicator_order_head.setVisibility(View.INVISIBLE);
            } else {
                v_indicator_order_head.setVisibility(View.VISIBLE);
            }
            if (baseViewHolder.getAdapterPosition() == getCountNotifiCallBack().getcount()-1) {
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
        return R.layout.item_delivery_confirm_detail;
    }

    public static interface CountNotifiCallBack {
        int getcount();
    }


}
