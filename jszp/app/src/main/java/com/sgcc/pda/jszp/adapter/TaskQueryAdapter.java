package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;

/**
 * 出库查询  入库查询
 * @param <T>
 */
public class TaskQueryAdapter<T extends LogisticsDistAutoesItem> extends BaseViewHolderManager<T> {
    Context mContext;
    public TaskQueryAdapter(Context context){
        mContext = context;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvTaskNo=getView(baseViewHolder,R.id.tv_task_no);
        TextView tvType=getView(baseViewHolder,R.id.tv_type);
        ImageView ivDevice=getView(baseViewHolder,R.id.iv_device);
        TextView tvDesc=getView(baseViewHolder,R.id.tv_desc);
        TextView tvChukou=getView(baseViewHolder,R.id.tv_chukou);
        TextView tvNum=getView(baseViewHolder,R.id.tv_num);
        TextView tvDpName=getView(baseViewHolder,R.id.tv_dp_name);

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_task_query;

    }
}
