package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;
import com.sgcc.pda.jszp.util.StrUtil;

/**
 * 装车确认  列表
 * @param <T>
 */
public class DeliveryConfirmAdapter<T extends LogisticsDistAutoesItem> extends BaseViewHolderManager<T> {
    Context mContext;
    public DeliveryConfirmAdapter(Context context){
        mContext = context;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tv_status=getView(baseViewHolder,R.id.tv_status);
        TextView tv_task_no=getView(baseViewHolder,R.id.tv_task_no);
        TextView tv_car_no=getView(baseViewHolder,R.id.tv_car_no);
        TextView tv_driver=getView(baseViewHolder,R.id.tv_driver);
        TextView tv_box_num=getView(baseViewHolder,R.id.tv_box_num);
        TextView tv_time=getView(baseViewHolder,R.id.tv_time);

        tv_status.setText(t.getTaskStatusLabel());
        tv_task_no.setText("配送任务:"+t.getTaskNo());
        tv_car_no.setText("车辆号码："+t.getAutoBrandNo());
        tv_driver.setText("司机信息："+t.getStaffName()+"    "+t.getPhoneNo());
        tv_box_num.setText("设备箱数："+t.getBoxQty()+"箱");
        if(!TextUtils.isEmpty(t.getPlanPlaceTime())) {
            tv_time.setText(StrUtil.longToString(Long.parseLong(t.getPlanPlaceTime())));
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_delivery_confirm;
    }
}
