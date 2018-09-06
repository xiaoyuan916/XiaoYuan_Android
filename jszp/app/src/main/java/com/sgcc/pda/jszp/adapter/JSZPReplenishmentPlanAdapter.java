package com.sgcc.pda.jszp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.DeviceInActivity;
import com.sgcc.pda.jszp.activity.DeviceInDetailActivity;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptResultEntity;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.io.Serializable;
import java.util.ArrayList;

public class JSZPReplenishmentPlanAdapter extends RecyclerView.Adapter<JSZPReplenishmentPlanAdapter.IoPlanDetsViewHolder> {

    private ArrayList<IoTaskDets> ioPlanDets;
    private Activity activity;

    public JSZPReplenishmentPlanAdapter(Activity activity, ArrayList<IoTaskDets> ioPlanDets) {
        this.ioPlanDets = ioPlanDets;
        this.activity = activity;
    }

    @Override
    public IoPlanDetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_replenishmen_plan_dets, parent, false);
        return new JSZPReplenishmentPlanAdapter.IoPlanDetsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IoPlanDetsViewHolder holder, int position) {
        IoTaskDets jszpDeliveryReceiptResultIoPlanDetsEntity
                = ioPlanDets.get(position);
        holder.tv_replenishment_odd_number.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getEquipCode());
        holder.tv_replenishment_device_description.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getEquipDesc());
        holder.tv_replenishment_outgoing_qty.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getQty()+"");
        //对图标进行赋值
        switch (jszpDeliveryReceiptResultIoPlanDetsEntity.getEquipCateg()) {
            case JzspConstants.Device_Category_DianNengBiao:
                //电能表
                holder.iv_replenishment_materiel.setBackgroundResource(R.drawable.dianneng);
                break;
            case JzspConstants.Device_Category_DianYaHuGanQi:
            case JzspConstants.Device_Category_DianLiuHuGanQi:
            case JzspConstants.Device_Category_ZuHeHuGanQi:
                //互感器
                holder.iv_replenishment_materiel.setBackgroundResource(R.drawable.huganqi);
                break;
            case JzspConstants.Device_Category_CaiJiZhongDuan:
                //采集器
                holder.iv_replenishment_materiel.setBackgroundResource(R.drawable.caijiqi);
                break;
            case JzspConstants.Device_Category_TongXunMoKuai:
                //通讯模块
                holder.iv_replenishment_materiel.setBackgroundResource(R.drawable.tongxun);
                break;
        }
        //对任务状态进行区分
        switch (jszpDeliveryReceiptResultIoPlanDetsEntity.getStatus()) {
            case JzspConstants.Device_Out_UnSign:
                holder.iv_replenishment_state.setBackgroundResource(R.drawable.jzsp_replenishment_in_storage);
                break;
            case JzspConstants.Device_Out_New:
                holder.iv_replenishment_state.setBackgroundResource(R.drawable.jzsp_replenishment_in_storage);
                break;
            case JzspConstants.Device_Out_Sended:
                holder.iv_replenishment_state.setBackgroundResource(R.drawable.jzsp_replenishment_already_down);
                break;
            case JzspConstants.Device_Out_Finish:
                holder.iv_replenishment_state.setBackgroundResource(R.drawable.jzsp_replenishment_completed);
                break;
        }
        //点击事件
        initListener(holder,jszpDeliveryReceiptResultIoPlanDetsEntity);
    }

    /**
     * 点击事件
     *
     * @param holder
     * @param jszpDeliveryReceiptResultIoPlanDetsEntity
     */
    private void initListener(IoPlanDetsViewHolder holder, final IoTaskDets
            jszpDeliveryReceiptResultIoPlanDetsEntity) {
        holder.ll_replenishment_materiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DeviceInDetailActivity.class);
                intent.putExtra("jszpDeliveryReceiptResultIoPlanDetsEntity",
                        (Serializable) jszpDeliveryReceiptResultIoPlanDetsEntity);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ioPlanDets==null?0:ioPlanDets.size();
    }


    public class IoPlanDetsViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_replenishment_materiel;
        public TextView tv_replenishment_odd_number;
        public TextView tv_replenishment_device_description;
        public TextView tv_replenishment_outgoing_qty;
        public ImageView iv_replenishment_state;
        public LinearLayout ll_replenishment_materiel;

        public IoPlanDetsViewHolder(View v) {
            super(v);
            ll_replenishment_materiel = (LinearLayout) v.findViewById(R.id.ll_replenishment_materiel);
            iv_replenishment_materiel = (ImageView) v.findViewById(R.id.iv_replenishment_materiel);
            tv_replenishment_odd_number = (TextView) v.findViewById(R.id.tv_replenishment_odd_number);
            tv_replenishment_device_description = (TextView) v.findViewById(R.id.tv_replenishment_device_description);
            tv_replenishment_outgoing_qty = (TextView) v.findViewById(R.id.tv_replenishment_outgoing_qty);
            iv_replenishment_state = (ImageView) v.findViewById(R.id.iv_replenishment_state);
        }
    }
}
