package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.ExpressBindItem;

import java.util.ArrayList;

/**
 * 快递绑定中的设备列表
 * @param <T>
 */
public class ExpressBindDeviceAdapter<T extends ExpressBindItem.ExpressDeviceItem> extends BaseViewHolderManager<T> {
    Context context;
    private OnDeleteClickListener onDeleteClickListener;
    public ExpressBindDeviceAdapter(Context context) {
        this.context = context;
    }
    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, T t) {
        TextView tv_devicce_name = getView(baseViewHolder, R.id.tv_devicce_name);
        TextView tv_devicce_no = getView(baseViewHolder, R.id.tv_devicce_no);
        ImageView iv_del = getView(baseViewHolder, R.id.iv_del);

        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onDeleteClickListener!=null){
                    onDeleteClickListener.onDelete(baseViewHolder.getItemPosition());
                }
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_express_bind_device;
    }


    public OnDeleteClickListener getOnDeleteClickListener() {
        return onDeleteClickListener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public static interface OnDeleteClickListener {
        void onDelete(int position);
    }
}
