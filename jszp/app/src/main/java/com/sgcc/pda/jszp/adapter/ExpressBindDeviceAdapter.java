package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.ExpressBindItem;

import java.util.List;

/**
 * 快递绑定中的设备列表
 * @param <T>
 */
public class ExpressBindDeviceAdapter<T extends ExpressBindItem.ExpressDeviceItem> extends BaseViewHolderManager<T> {
    Context context;
    private List<ExpressBindItem.ExpressDeviceItem> mList;
    private OnDeleteClickListener onDeleteClickListener;
    public ExpressBindDeviceAdapter(Context context, List<ExpressBindItem.ExpressDeviceItem> mList) {
        this.context = context;
        this.mList = mList;
    }
    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, T t) {
        TextView tv_devicce_name = getView(baseViewHolder, R.id.tv_devicce_name);
        TextView tv_devicce_no = getView(baseViewHolder, R.id.tv_devicce_no);
        ImageView iv_del = getView(baseViewHolder, R.id.iv_del);
        View line = getView(baseViewHolder,R.id.line);

        tv_devicce_name.setText(t.getDeviceName());
        tv_devicce_no.setText(t.getBarCode());

        if(baseViewHolder.getItemPosition() == mList.size()-1){
            line.setVisibility(View.GONE);
        }else{
            line.setVisibility(View.VISIBLE);
        }

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
