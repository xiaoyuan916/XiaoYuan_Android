package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.OptionItem;

public class OptionAdapter<T extends OptionItem> extends BaseViewHolderManager<T> {

    Context context;

    public OptionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvName = getView(baseViewHolder, R.id.tv_name);
        ImageView ivSelected = getView(baseViewHolder, R.id.iv_selected);
        if (t.isSelected()) {
            tvName.setTextColor(context.getResources().getColor(R.color.title_green));
            ivSelected.setVisibility(View.VISIBLE);
        } else {
            tvName.setTextColor(context.getResources().getColor(R.color.darkgray));
            ivSelected.setVisibility(View.GONE);
        }
        tvName.setText(t.getName());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_option;
    }
}
