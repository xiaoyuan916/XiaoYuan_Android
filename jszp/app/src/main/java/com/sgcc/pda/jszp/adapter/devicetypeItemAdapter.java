package com.sgcc.pda.jszp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceItem;

public class devicetypeItemAdapter<T extends DeviceItem> extends BaseViewHolderManager<T> {
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        ImageView iv_type = getView(baseViewHolder, R.id.iv_type);
        TextView tv_type_name = getView(baseViewHolder, R.id.tv_type_name);
        tv_type_name.setText(t.getName());
        iv_type.setImageResource(DeviceItem.getImgSourceId(t.getType(),false));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_devicetype_layout;
    }
}
