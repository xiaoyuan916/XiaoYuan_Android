package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.ReturnSignOrderActivity;
import com.sgcc.pda.jszp.activity.ReturnWarehouseGoodsActivity;
import com.sgcc.pda.jszp.activity.ReturnWarehouseGoodsDetailActivity;
import com.sgcc.pda.jszp.bean.ReturnWarehousListItem;
import com.sgcc.pda.jszp.bean.ReturnWarehouseResultEntity;

import java.io.Serializable;
import java.util.ArrayList;


//返程入库列表
public class ReturnWarehouseAdapter extends RecyclerView.Adapter<ReturnWarehouseAdapter.ViewHolder> {
    Context context;
    private ArrayList<ReturnWarehouseResultEntity.SplitTasks> splitTasksList=new ArrayList<>();

    public ReturnWarehouseAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<ReturnWarehouseResultEntity.SplitTasks> getSplitTasksList() {
        return splitTasksList;
    }

    public void setSplitTasksList(ArrayList<ReturnWarehouseResultEntity.SplitTasks> splitTasksList) {
        this.splitTasksList = splitTasksList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_return_warehouse, parent, false);
        return new ReturnWarehouseAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReturnWarehouseResultEntity.SplitTasks mSplitTasks = splitTasksList.get(position);
        holder.tvReturnCompany.setText("返回单位："+mSplitTasks.getDpName());
        holder.tvReturnNum.setText("配送单："+mSplitTasks.getTaskNo());
        //点击事件
        initListener(holder, mSplitTasks);
    }

    private void initListener(ViewHolder holder, final ReturnWarehouseResultEntity.SplitTasks mSplitTasks) {
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReturnWarehouseGoodsActivity.class);
                intent.putExtra("returnWarehouseResultEntity", (Serializable) mSplitTasks);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
       return   splitTasksList==null?0:splitTasksList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvReturnNum;
        public final TextView tvReturnCompany;
        public final LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvReturnNum = (TextView) itemView.findViewById(R.id.tv_return_num);
            tvReturnCompany = (TextView) itemView.findViewById(R.id.tv_return_company);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
