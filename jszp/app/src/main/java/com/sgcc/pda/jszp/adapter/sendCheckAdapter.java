package com.sgcc.pda.jszp.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.sgcc.pda.jszp.activity.SendCheckDetailActivity;
import com.sgcc.pda.jszp.bean.TaskItemResultEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//物流出发适配器
public class SendCheckAdapter extends RecyclerView.Adapter<SendCheckAdapter.ViewHolder> {

    Activity activity;
    private boolean multi_select = false;
    ArrayList<TaskItemResultEntity.LogisticsDistAutoes> taskdata;

    public SendCheckAdapter(Activity activity, ArrayList<TaskItemResultEntity.LogisticsDistAutoes> taskdata) {
        this.activity = activity;
        this.taskdata = taskdata;
    }

    public void setTaskdata(ArrayList<TaskItemResultEntity.LogisticsDistAutoes> taskdata) {
        this.taskdata = taskdata;
    }

    public boolean isMulti_select() {
        return multi_select;
    }

    public void setMulti_select(boolean multi_select) {
        this.multi_select = multi_select;
    }


    @Override
    public SendCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_task, parent, false);
        return new SendCheckAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SendCheckAdapter.ViewHolder holder, final int position) {
        final TaskItemResultEntity.LogisticsDistAutoes mTaskItemResultData=taskdata.get(position);

        holder.tvTaskNum.setText(mTaskItemResultData.getTaskNo());
        holder.tvCarInfo.setText("车辆牌号："+mTaskItemResultData.getAutoBrandNo());
        holder.tvDriverInfo.setText("司机信息："+mTaskItemResultData.getStaffName()+mTaskItemResultData.getPhoneNo());
        holder.tvCount.setText("设备箱数"+mTaskItemResultData.getQty()+"箱");
        holder.ll_item.setVisibility(View.VISIBLE);
//        holder.cb_selecte.setVisibility(View.GONE);
        if (multi_select) {
                holder.cb_selecte.setVisibility(View.VISIBLE);
        }else{
            holder.cb_selecte.setVisibility(View.GONE);
        }
        holder.cb_selecte.setChecked(mTaskItemResultData.isSelected());
        holder.cb_selecte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mTaskItemResultData.setSelected(b);
            }
        });
        if ("0401".equals(mTaskItemResultData.getTaskStatus())) {
            holder.tvState.setText("待确认");
        }else {
            holder.tvState.setText("已确认");
        }
        //点击事件
        initListener(holder,mTaskItemResultData);
    }
    //点击跳转
    private void initListener(ViewHolder holder, final TaskItemResultEntity.LogisticsDistAutoes mTaskItemResultData) {
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SendCheckDetailActivity.class);
                intent.putExtra("mTaskItemResultData",
                        (Serializable) mTaskItemResultData);
                activity.startActivityForResult(intent,401);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout ll_item;
        public final TextView tvTaskNum ;
        public final TextView tv_head ;
        public final TextView tvCarInfo ;
        public final TextView tvCount ;
        public final TextView tvState ;
        public final TextView tvTime ;
        private final TextView tvDriverInfo;//司机信息
        public final ImageView iv_state ;
        public final CheckBox cb_selecte ;

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
