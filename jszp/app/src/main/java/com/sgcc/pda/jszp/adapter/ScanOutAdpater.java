package com.sgcc.pda.jszp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceItem;

import butterknife.OnClick;

public class ScanOutAdpater<T extends DeviceItem> extends BaseViewHolderManager<T> {

    private OnDeleteClickListener onDeleteClickListener;

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvDeviceNum = getView(baseViewHolder, R.id.tv_device_num);
        TextView tvState = getView(baseViewHolder, R.id.tv_state);
        ImageView ivDelete = getView(baseViewHolder, R.id.iv_delete);

        tvDeviceNum.setText(t.getNum());
        switch (t.getState()) {
            case 0:
                tvState.setText("合格在库");
                break;
            case 1:
                break;
            case 2:
                break;
        }
        final int position = baseViewHolder.getItemPosition();
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDeleteClickListener != null)
                    onDeleteClickListener.onDelete(position);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_scan_out;
    }

    @OnClick(R.id.iv_delete)
    public void onViewClicked() {

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
