package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.TaskItem;

public class ExpressConfirmAdapter<T extends TaskItem> extends BaseViewHolderManager<T> {
    Context context;
    public ExpressConfirmAdapter(Context context) {
        this.context = context;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tv_task_no = getView(baseViewHolder, R.id.tv_task_no);
        TextView tv_time = getView(baseViewHolder, R.id.tv_time);
        TextView tv_express_company = getView(baseViewHolder, R.id.tv_express_company);
        TextView tv_addr = getView(baseViewHolder, R.id.tv_addr);
        TextView tv_num = getView(baseViewHolder, R.id.tv_num);
        TextView tv_status = getView(baseViewHolder, R.id.tv_status);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_express_confirm;
    }
}
