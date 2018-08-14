package com.sgcc.pda.jszp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceInTaskItem;
import com.sgcc.pda.jszp.bean.DeviceItem;

public class DeviceInAdapter<T extends DeviceInTaskItem> extends BaseViewHolderManager<T> {


    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        ImageView ivDevice = getView(baseViewHolder, R.id.iv_device);
        TextView tvState = getView(baseViewHolder, R.id.tv_state);
        TextView tvInTaskNum = getView(baseViewHolder, R.id.tv_in_task_num);
        TextView tvDescrible = getView(baseViewHolder, R.id.tv_describle);
        TextView tvCount = getView(baseViewHolder, R.id.tv_count);

        tvInTaskNum.setText(t.getNum());
        tvDescrible.setText("设备码描述：" + t.getDescribe());
        tvCount.setText("入库数量：" + t.getIncount() + "/" + t.getTaskcount());
        ivDevice.setImageResource(DeviceItem.getImgSourceId(t.getDevicetype(), false));
        switch (t.getState()) {
            case 0:
                tvState.setText("新建");
                break;
            case 2:
                tvState.setText("已下发");
                break;
            case 3:
                tvState.setText("出库中");
                break;
            case 4:
                tvState.setText("已完成");
                break;
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_device_in;
    }
}
