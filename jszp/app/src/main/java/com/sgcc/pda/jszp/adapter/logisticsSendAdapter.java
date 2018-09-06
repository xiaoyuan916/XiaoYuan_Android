package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.jszp.util.StrUtil;

public class logisticsSendAdapter<T extends LogisticsDistAutoesItem> extends BaseViewHolderManager<T> {
    Context mContext;
    public logisticsSendAdapter(Context context){
        mContext = context;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvState=getView(baseViewHolder,R.id.tv_state);
        ImageView ivState=getView(baseViewHolder,R.id.iv_state);
        TextView tvCarNum=getView(baseViewHolder,R.id.tv_car_num);
        TextView tvSrcaddress=getView(baseViewHolder,R.id.tv_srcaddress);
        TextView tvDscaddress=getView(baseViewHolder,R.id.tv_dscaddress);
        TextView tvDriver=getView(baseViewHolder,R.id.tv_driver);
        TextView tvDriverTime=getView(baseViewHolder,R.id.tv_driver_time);
        TextView tvCarTx=getView(baseViewHolder,R.id.tv_car_tx);


        tvState.setText(t.getStatusLabel());
        //未派車用黄字  绿图标   已派车 未出发的 绿字  绿图标    已发出的  蓝字 蓝图标
        if(JzspConstants.Car_Status_UnSend.equals(t.getStatus())){
            //未派车
            tvState.setTextColor(mContext.getResources().getColor(R.color.color_ls_status_o) );
            tvState.setBackgroundResource(R.drawable.bg_ls_status_o_coners);
            ivState.setBackgroundResource(R.mipmap.icon_kache_lv);
        }
//        if(baseViewHolder.getItemPosition()==1){
//            tvState.setText("已派车");
//            tvState.setTextColor(mContext.getResources().getColor(R.color.color_ls_status_green) );
//            tvState.setBackgroundResource(R.drawable.bg_ls_status_g_coners);
//            ivState.setBackgroundResource(R.mipmap.icon_kache_lv);
//        }
        else{
            tvState.setTextColor(mContext.getResources().getColor(R.color.title_green) );
            tvState.setBackgroundResource(R.drawable.bg_ls_status_b_coners);
            ivState.setBackgroundResource(R.mipmap.icon_kache_blue);
        }

        tvCarTx.setText(t.getLoaded()+"T");
        tvCarNum.setText(t.getAutoBrandNo());
        tvSrcaddress.setText(t.getWhName());
        tvDscaddress.setText(t.getDpName());
        tvDriver.setText(TextUtils.isEmpty(t.getStaffName())?"未指定":t.getStaffName());
        tvDriverTime.setText(TextUtils.isEmpty(t.getPlanPlaceTime())?"未指定": StrUtil.longToString(Long.parseLong(t.getPlanPlaceTime())));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_logistics_send;
    }
}
