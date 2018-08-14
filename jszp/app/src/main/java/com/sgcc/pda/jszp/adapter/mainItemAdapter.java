package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.MainItem;

public class mainItemAdapter<T extends MainItem> extends BaseViewHolderManager<T> {

    private Context context;

    public mainItemAdapter(Context context) {
        this.context = context;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {

        ImageView iv=getView(baseViewHolder,R.id.iv_main_item);
        TextView tv=getView(baseViewHolder,R.id.tv_main_item);
        iv.setImageResource(t.getImg());
        tv.setText(t.getTitle());


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_main_item_layout;
    }
}
