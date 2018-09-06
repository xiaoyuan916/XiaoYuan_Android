package com.sgcc.pda.jszp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptResultEntity;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;

public class JSZPDeliveryReceipIoPlanDetsAdapter extends RecyclerView.Adapter<JSZPDeliveryReceipIoPlanDetsAdapter.IoPlanDetsViewHolder> {

    private ArrayList<IoTaskDets> ioPlanDets;

    // 创建ViewHolder
    public class IoPlanDetsViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_equip;
        public TextView tv_equip_desc;
        public TextView tv_equip_qty;

        public IoPlanDetsViewHolder(View v) {
            super(v);
            iv_equip = (ImageView) v.findViewById(R.id.iv_equip);
            tv_equip_desc = (TextView) v.findViewById(R.id.tv_equip_desc);
            tv_equip_qty = (TextView) v.findViewById(R.id.tv_equip_qty);
        }
    }

    /**
     * 使用构造传入参数
     *
     * @param ioPlanDets
     */
    public JSZPDeliveryReceipIoPlanDetsAdapter(ArrayList<IoTaskDets> ioPlanDets) {
        this.ioPlanDets = ioPlanDets;
    }

    @Override
    public JSZPDeliveryReceipIoPlanDetsAdapter.IoPlanDetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery_receip_io_plan_dets, parent, false);
        return new IoPlanDetsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JSZPDeliveryReceipIoPlanDetsAdapter.IoPlanDetsViewHolder holder,
                                 int position) {
        IoTaskDets jszpDeliveryReceiptResultIoPlanDetsEntity
                = ioPlanDets.get(position);
        holder.tv_equip_desc.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getEquipDesc());
        holder.tv_equip_qty.setText(jszpDeliveryReceiptResultIoPlanDetsEntity.getQty()+"只/"
                +jszpDeliveryReceiptResultIoPlanDetsEntity.getBoxQty()+"箱");
        //对图标进行赋值
        switch (jszpDeliveryReceiptResultIoPlanDetsEntity.getEquipCateg()) {
            case JzspConstants.Device_Category_DianNengBiao:
                //电能表
                holder.iv_equip.setBackgroundResource(R.drawable.dianneng);
                break;
            case JzspConstants.Device_Category_DianYaHuGanQi:
            case JzspConstants.Device_Category_DianLiuHuGanQi:
            case JzspConstants.Device_Category_ZuHeHuGanQi:
                //互感器
                holder.iv_equip.setBackgroundResource(R.drawable.huganqi);
                break;
            case JzspConstants.Device_Category_CaiJiZhongDuan:
                //采集器
                holder.iv_equip.setBackgroundResource(R.drawable.caijiqi);
                break;
            case JzspConstants.Device_Category_TongXunMoKuai:
                //通讯模块
                holder.iv_equip.setBackgroundResource(R.drawable.tongxun);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return ioPlanDets==null?0: ioPlanDets.size();
    }
}
