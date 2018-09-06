package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.ScanDeviceDate;

/**
 * 平库出库  扫描设备列表
 *
 * @param <T>
 */
public class ScanDeviceDataAdapter<T extends ScanDeviceDate> extends BaseViewHolderManager<T> {

    Context context;
    OnScanClickListener onScanClickListener;

    public ScanDeviceDataAdapter(Context context) {
        this.context = context;

    }

    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, T t) {
        TextView tvDeviceNo = getView(baseViewHolder, R.id.tv_device_no);
        ImageView ivDelete = getView(baseViewHolder, R.id.iv_delete);
        TextView tvState = getView(baseViewHolder, R.id.tv_state);

        tvDeviceNo.setText(t.getBarCode());
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除
                if(onScanClickListener!=null){
                    onScanClickListener.onDeleteDevice(baseViewHolder.getItemPosition());
                }

            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_scan_device_data;
    }

    public static interface OnScanClickListener{
        void onDeleteDevice(int position);
    }

    public OnScanClickListener getOnScanClickListener() {
        return onScanClickListener;
    }

    public void setOnScanClickListener(OnScanClickListener onScanClickListener) {
        this.onScanClickListener = onScanClickListener;
    }
}
