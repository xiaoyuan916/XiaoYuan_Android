package com.sgcc.pda.jszp.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.ReturnItem;

public class returnItemAdapter<T extends ReturnItem> extends BaseViewHolderManager<T> {
    private onChildClickCallBack onChildClickCallBack;

    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, final T t) {
        CheckBox cbSelected = getView(baseViewHolder, R.id.cb_selected);
        ImageView ivSaoma = getView(baseViewHolder, R.id.iv_saoma);
        ImageView ivType = getView(baseViewHolder, R.id.iv_type);
        TextView tvTaskNum = getView(baseViewHolder, R.id.tv_task_num);
        TextView tvDeviceName = getView(baseViewHolder, R.id.tv_device_name);
        TextView tvPlanNum = getView(baseViewHolder, R.id.tv_plan_num);
        TextView tvRealNum = getView(baseViewHolder, R.id.tv_real_num);
        LinearLayout llContent = getView(baseViewHolder, R.id.ll_content);
        cbSelected.setChecked(t.isSelected());
        tvTaskNum.setText(t.getTasknum());
        tvDeviceName.setText(t.getDevicename());
        tvPlanNum.setText(String.valueOf(t.getPlancount()) + "只");
        if (t.getRealcount() > 0) tvRealNum.setText(String.valueOf(t.getRealcount()) + "只");
        else
            tvRealNum.setText("--");
        switch (t.getDevicetype()) {
            case 0:
                ivType.setImageResource(R.drawable.diannengf);
                break;
            case 1:
                ivType.setImageResource(R.drawable.caijiqif);
                break;
            case 2:
                ivType.setImageResource(R.drawable.jizhongqif);
                break;
            case 3:
                ivType.setImageResource(R.drawable.tongxunf);
                break;
            case 4:
                ivType.setImageResource(R.drawable.huganqif);
                break;
            default:
                ivType.setImageResource(R.drawable.diannengf);
                break;
        }
        ivSaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onChildClickCallBack != null)
                    onChildClickCallBack.onSomaClick(baseViewHolder);
            }
        });
        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onChildClickCallBack != null)
                    onChildClickCallBack.onItemClick(baseViewHolder);
            }
        });
        final T d=t;

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_return_layout;
    }


    public returnItemAdapter.onChildClickCallBack getOnChildClickCallBack() {
        return onChildClickCallBack;
    }

    public void setOnChildClickCallBack(returnItemAdapter.onChildClickCallBack onChildClickCallBack) {
        this.onChildClickCallBack = onChildClickCallBack;
    }

    public interface onChildClickCallBack {

        void onSomaClick(BaseViewHolder baseViewHolder);

        void onItemClick(BaseViewHolder baseViewHolder);


    }
}
