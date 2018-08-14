package com.sgcc.pda.jszp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptResultEntity;

import java.util.ArrayList;

public class JSZPDeliveryReceipIoPlanDetsAdapter extends RecyclerView.Adapter<JSZPDeliveryReceipIoPlanDetsAdapter.IoPlanDetsViewHolder>{

    private  ArrayList<JSZPDeliveryReceiptResultEntity.JSZPDeliveryReceiptResultSplitTaskEntity
            .JSZPDeliveryReceiptResultIoPlanDetsEntity> ioPlanDets;

    // 创建ViewHolder
    public  class IoPlanDetsViewHolder extends RecyclerView.ViewHolder{
        public IoPlanDetsViewHolder(View v) {
            super(v);
        }
    }

    /**
     * 使用构造传入参数
     * @param ioPlanDets
     */
    public JSZPDeliveryReceipIoPlanDetsAdapter(ArrayList<JSZPDeliveryReceiptResultEntity.JSZPDeliveryReceiptResultSplitTaskEntity.
            JSZPDeliveryReceiptResultIoPlanDetsEntity> ioPlanDets) {
        this.ioPlanDets=ioPlanDets;
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
        JSZPDeliveryReceiptResultEntity.JSZPDeliveryReceiptResultSplitTaskEntity.
                JSZPDeliveryReceiptResultIoPlanDetsEntity jszpDeliveryReceiptResultIoPlanDetsEntity
                = ioPlanDets.get(position);
    }

    @Override
    public int getItemCount() {
        return ioPlanDets.size();
    }
}
