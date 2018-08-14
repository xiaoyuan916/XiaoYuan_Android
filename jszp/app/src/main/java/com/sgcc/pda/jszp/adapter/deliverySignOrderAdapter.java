package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceItem;
import com.sgcc.pda.jszp.util.StrUtil;

public class deliverySignOrderAdapter<T extends DeviceItem> extends BaseViewHolderManager<T> {

    private Context context;

    public deliverySignOrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        ImageView ivDevice = getView(baseViewHolder, R.id.iv_device);
        TextView tvDeviceCount = getView(baseViewHolder, R.id.tv_device_count);
        TextView tvDeviceName = getView(baseViewHolder, R.id.tv_device_name);

        ivDevice.setImageResource(DeviceItem.getImgSourceId(t.getType(),false));
        tvDeviceCount.setText(t.getCount()+"只");
        String count= StrUtil.stripUnit(tvDeviceCount.getText().toString());
        tvDeviceName.setText(t.getName());


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_sign_device;
    }
}
