package com.sgcc.pda.jszp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.JszpBoxRecallDetEntity;

import java.util.HashMap;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/7
 */
public class JszpBoxStatisticsAdapter extends RecyclerView.Adapter<JszpBoxStatisticsAdapter.ViewHolder> {
    private List<JszpBoxRecallDetEntity> mList;

    @NonNull
    @Override
    public JszpBoxStatisticsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box_recall_statistics, parent, false);
        return new JszpBoxStatisticsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JszpBoxStatisticsAdapter.ViewHolder holder, int position) {
        JszpBoxRecallDetEntity jszpBoxRecallDetEntity = mList.get(position);
        if (jszpBoxRecallDetEntity==null)return;
        holder.mTvBoxRecallInfo.setText(jszpBoxRecallDetEntity.getRecallDetId());
        holder.mTvBoxRecallCount.setText(jszpBoxRecallDetEntity.getSumQty());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 更新UI
     */
    public void updataUI(List<JszpBoxRecallDetEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIvRecallBox;
        public TextView mTvBoxRecallInfo;
        public TextView mTvBoxRecallCount;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvRecallBox = itemView.findViewById(R.id.iv_recall_box);
            mTvBoxRecallInfo = itemView.findViewById(R.id.tv_box_recall_info);
            mTvBoxRecallCount = itemView.findViewById(R.id.tv_box_recall_count);

        }
    }
}
