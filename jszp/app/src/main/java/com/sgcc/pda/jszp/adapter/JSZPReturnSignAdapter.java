package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;

import com.sgcc.pda.jszp.activity.ReturnSignOrderActivity;
import com.sgcc.pda.jszp.bean.JSZPreturnStoragePlanResultEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class JSZPReturnSignAdapter extends RecyclerView.Adapter<JSZPReturnSignAdapter.ViewHolder> {

    private Context context;
    private ArrayList<JSZPreturnStoragePlanResultEntity.JSZPreturnStoragePlanSplitTask> splitTasks = new ArrayList<>();

    public JSZPReturnSignAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<JSZPreturnStoragePlanResultEntity.JSZPreturnStoragePlanSplitTask> getSplitTasks() {
        return splitTasks;
    }

    public void setSplitTasks(ArrayList<JSZPreturnStoragePlanResultEntity.JSZPreturnStoragePlanSplitTask> splitTasks) {
        this.splitTasks = splitTasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_return_sign, parent, false);
        return new JSZPReturnSignAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSZPreturnStoragePlanResultEntity.JSZPreturnStoragePlanSplitTask
                jszPreturnStoragePlanSplitTask = splitTasks.get(position);
        holder.tv_return_sign_order.setText(jszPreturnStoragePlanSplitTask.getSplitTaskNo());
        holder.tv_return_sign_receiving_warehouse.setText(jszPreturnStoragePlanSplitTask.getDpName());
        initListener(holder,jszPreturnStoragePlanSplitTask);
    }

    /**
     * 点击事件
     *
     * @param holder
     * @param jszPreturnStoragePlanSplitTask
     */
    private void initListener(ViewHolder holder, final JSZPreturnStoragePlanResultEntity.
            JSZPreturnStoragePlanSplitTask jszPreturnStoragePlanSplitTask) {
        holder.ll_return_sign_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReturnSignOrderActivity.class);
                intent.putExtra("jszPreturnStoragePlanSplitTask", (Serializable) jszPreturnStoragePlanSplitTask);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return splitTasks==null?0:splitTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_return_sign_order;
        public final TextView tv_return_sign_receiving_warehouse;
        public final LinearLayout ll_return_sign_order;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_return_sign_order = (TextView) itemView.findViewById(R.id.tv_return_sign_order);
            tv_return_sign_receiving_warehouse = (TextView) itemView.findViewById(R.id.tv_return_sign_receiving_warehouse);
            ll_return_sign_order = (LinearLayout) itemView.findViewById(R.id.ll_return_sign_order);
        }
    }
}
