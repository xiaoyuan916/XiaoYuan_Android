package com.sgcc.pda.jszp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.SendExpressDetailActivity;
import com.sgcc.pda.jszp.bean.TaskExpressResultEntity;

import java.util.ArrayList;

//快递出发适配器
public class SendExpressAdapter extends RecyclerView.Adapter<SendExpressAdapter.ViewHolder> {

    private Activity activity;
    private boolean multi_select = false;
    private ArrayList<TaskExpressResultEntity.ExpressDistAutoes> taskdata;

    public SendExpressAdapter(Activity activity, ArrayList<TaskExpressResultEntity.ExpressDistAutoes> taskdata) {
        this.activity = activity;
        this.taskdata = taskdata;
    }

    public boolean isMulti_select() {
        return multi_select;
    }

    public void setMulti_select(boolean multi_select) {
        this.multi_select = multi_select;
    }


    @Override
    public SendExpressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_task, parent, false);
        return new SendExpressAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SendExpressAdapter.ViewHolder holder, int position) {
        final TaskExpressResultEntity.ExpressDistAutoes mTaskExpressResultData = taskdata.get(position);
        holder.iv_state.setImageResource(R.mipmap.kuaidipeisong);
        holder.tvTaskNum.setText(mTaskExpressResultData.getTaskNo());
        holder.tvCarInfo.setText("接收库房："+mTaskExpressResultData.getDpName());
        holder.tvCount.setText("设备号："+mTaskExpressResultData.getQty() + "只");
        holder.tvDriverInfo.setVisibility(View.GONE);
        //点击事件
        initListener(holder, mTaskExpressResultData);
        if (multi_select) {
            holder.cb_selecte.setVisibility(View.VISIBLE);
            holder.cb_selecte.setChecked(mTaskExpressResultData.isSelected());
        } else {
            holder.cb_selecte.setVisibility(View.GONE);
            holder.cb_selecte.setChecked(mTaskExpressResultData.isSelected());
        }

        holder.cb_selecte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mTaskExpressResultData.setSelected(b);
            }
        });
        if ("0401".equals(mTaskExpressResultData.getTaskStatus())) {
            holder.tvState.setText("待确认");
        } else {
            holder.tvState.setText("已确认");
        }
    }

    private void initListener(ViewHolder holder, final TaskExpressResultEntity.ExpressDistAutoes mTaskExpressResultData) {
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SendExpressDetailActivity.class);
                intent.putExtra("taskNo", mTaskExpressResultData.getTaskNo());
                activity.startActivityForResult(intent,402);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout ll_item;
        public final TextView tvTaskNum;
        public final TextView tv_head;
        public final TextView tvCarInfo;
        public final TextView tvCount;
        public final TextView tvState;
        public final TextView tvTime;
        public final TextView tvDriverInfo;//司机信息
        public final ImageView iv_state;
        public final CheckBox cb_selecte;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            tvTaskNum = (TextView) itemView.findViewById(R.id.tv_task_num);
            tv_head = (TextView) itemView.findViewById(R.id.tv_head);
            tvCarInfo = (TextView) itemView.findViewById(R.id.tv_car_info);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvState = (TextView) itemView.findViewById(R.id.tv_state);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvDriverInfo = (TextView) itemView.findViewById(R.id.tv_dirver_info);
            iv_state = (ImageView) itemView.findViewById(R.id.iv_state);
            cb_selecte = (CheckBox) itemView.findViewById(R.id.cb_selecte);
        }
    }
}
