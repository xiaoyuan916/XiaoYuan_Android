package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceItem;
import com.sgcc.pda.jszp.bean.OrderItem;

import butterknife.BindView;

public class deliverySignAdapter<T extends OrderItem> extends BaseViewHolderManager<T> {
    @BindView(R.id.iv_order_type)
    ImageView ivOrderType;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_box_count)
    TextView tvOrderBoxCount;
    @BindView(R.id.rv_devices)
    RecyclerView rvDevices;

    private Context context;

    public deliverySignAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        ImageView ivOrderType = getView(baseViewHolder, R.id.iv_order_type);
        TextView tvOrderNum = getView(baseViewHolder, R.id.tv_order_num);
        TextView tvOrderBoxCount = getView(baseViewHolder, R.id.tv_order_box_count);
        RecyclerView rvDevices = getView(baseViewHolder, R.id.rv_devices);

        switch (t.getType()) {
            case 1:
                ivOrderType.setImageResource(R.drawable.gongcheng);
                break;
            case 2:
                ivOrderType.setImageResource(R.drawable.yekuo);
                break;
            case 3:
                ivOrderType.setImageResource(R.drawable.weihu);
                break;
        }
        tvOrderNum.setText(t.getNum());
        tvOrderBoxCount.setText(t.getDevicecount()+"只/"+t.getBoxcount()+"箱");
        rvDevices.setLayoutManager(new LinearLayoutManager(context));
        rvDevices.setNestedScrollingEnabled(false);
        BaseItemAdapter deviceAdapter = new BaseItemAdapter();
        deviceAdapter.register(DeviceItem.class, new deliverySignOrderAdapter<DeviceItem>(context));
//        rvDevices.addItemDecoration(new SpaceItemDecoration(15));
//        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.gray_divider_1dp));
//        rvDevices.addItemDecoration(divider);
        rvDevices.setAdapter(deviceAdapter);
        deviceAdapter.setDataItems(t.getDevices());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_sign_order;
    }
}
