package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.TaskItem;

public class sendCheckAdapter<T extends TaskItem> extends BaseViewHolderManager<T> {


    Context context;
    private boolean multi_select = false;

    public sendCheckAdapter(Context context) {

        this.context = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, final T t) {
        LinearLayout ll_item = getView(baseViewHolder, R.id.ll_item);
        TextView tvTaskNum = getView(baseViewHolder, R.id.tv_task_num);
        TextView tv_head = getView(baseViewHolder, R.id.tv_head);
        TextView tvCarInfo = getView(baseViewHolder, R.id.tv_car_info);
        TextView tvcount = getView(baseViewHolder, R.id.tv_count);
        TextView tvState = getView(baseViewHolder, R.id.tv_state);
        TextView tvTime = getView(baseViewHolder, R.id.tv_time);
        ImageView iv_state = getView(baseViewHolder, R.id.iv_state);
        CheckBox cb_selecte = getView(baseViewHolder, R.id.cb_selecte);
        ll_item.setVisibility(View.VISIBLE);
        cb_selecte.setVisibility(View.GONE);
        if (multi_select) {
            if (t.getState() == 0) {
                cb_selecte.setVisibility(View.VISIBLE);
            } else {
                ll_item.setVisibility(View.GONE);
                return;
            }
            cb_selecte.setChecked(t.isSelected());
        }
        cb_selecte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                t.setSelected(b);
            }
        });
        tvTaskNum.setText(t.getNum());
        tvcount.setText(t.getBoxcount() + "箱/" + t.getDevicecount() + "只");
        if (t.getState() == 0) {
            iv_state.setImageResource(R.drawable.huoche);
            tvState.setText("待发车");
            tv_head.setTextColor(context.getResources().getColor(R.color.black));
            tvTaskNum.setTextColor(context.getResources().getColor(R.color.title_green));
            tvState.setTextColor(context.getResources().getColor(R.color.title_green));
        } else {
            iv_state.setImageResource(R.drawable.huoche_hui);
            tvState.setText("已发车");
            tv_head.setTextColor(context.getResources().getColor(R.color.darkgray));
            tvTaskNum.setTextColor(context.getResources().getColor(R.color.darkgray));
            tvState.setTextColor(context.getResources().getColor(R.color.darkgray));
        }

        tvTime.setText(t.getTime());
        tvCarInfo.setText(t.getCarInfo());

    }

    public boolean isMulti_select() {
        return multi_select;
    }

    public void setMulti_select(boolean multi_select) {
        this.multi_select = multi_select;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_send_task;
    }
}
