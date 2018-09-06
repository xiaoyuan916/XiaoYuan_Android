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
import com.sgcc.pda.jszp.activity.SendSelfDetailActivity;
import com.sgcc.pda.jszp.bean.TaskSelfResultEntity;

import java.io.Serializable;
import java.util.ArrayList;

//自提出发适配器
public class SendSelfAdapter extends RecyclerView.Adapter<SendSelfAdapter.ViewHolder> {

    private Activity activity;
    private boolean multi_select = false;
    private ArrayList<TaskSelfResultEntity.TakeDistAutoes> taskdata;

    public SendSelfAdapter(Activity activity, ArrayList<TaskSelfResultEntity.TakeDistAutoes> taskdata) {
        this.activity = activity;
        this.taskdata = taskdata;
    }


    public boolean isMulti_select() {
        return multi_select;
    }

    public void setMulti_select(boolean multi_select) {
        this.multi_select = multi_select;
    }

//    @Override
//    protected int getItemLayoutId() {
//        return R.layout.item_send_task;
//    }

    @Override
    public SendSelfAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_task, parent, false);
        return new SendSelfAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SendSelfAdapter.ViewHolder holder, int position) {
        final TaskSelfResultEntity.TakeDistAutoes mTaskSelfResultData = taskdata.get(position);
        holder.iv_state.setImageResource(R.mipmap.ziti);
        holder.tvTaskNum.setText(mTaskSelfResultData.getTaskNo());
        holder.tvCarInfo.setText("接收库房：" + mTaskSelfResultData.getDpNmae());
        holder.tvCount.setText("设备数：" + mTaskSelfResultData.getQty() + "只");
        holder.tvDriverInfo.setVisibility(View.GONE);

        //点击事件
        initListener(holder, mTaskSelfResultData);
        if (multi_select) {
            holder.cb_selecte.setVisibility(View.VISIBLE);
            holder.cb_selecte.setChecked(mTaskSelfResultData.isSelected());
        } else {
            holder.cb_selecte.setVisibility(View.GONE);
            holder.cb_selecte.setChecked(mTaskSelfResultData.isSelected());
        }
        holder.cb_selecte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mTaskSelfResultData.setSelected(b);
            }
        });
    }

    private void initListener(ViewHolder holder, final TaskSelfResultEntity.TakeDistAutoes mTaskSelfResultData) {
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SendSelfDetailActivity.class);
                intent.putExtra("taskNo",
                        mTaskSelfResultData.getTaskNo());
                activity.startActivityForResult(intent, 403);
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
        private final TextView tvDriverInfo;//司机信息
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
