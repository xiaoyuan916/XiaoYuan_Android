package com.sgcc.pda.jszp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.JszpOrderTrackEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/5
 */
public class JszpOrderStatusAdpter extends RecyclerView.Adapter<JszpOrderStatusAdpter.ViewHolder> {
    /**
     * LIST信息
     */
    List<JszpOrderTrackEntity> tracks = new ArrayList<>();

    @NonNull
    @Override
    public JszpOrderStatusAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JszpOrderStatusAdpter.ViewHolder holder, int position) {
        JszpOrderTrackEntity trackEntity = tracks.get(position);
        if (holder.getAdapterPosition() == 0) {
            holder.mV_order_status_line_top.setVisibility(View.GONE);
            holder.mV_order_status_line.setVisibility(View.VISIBLE);
        } else if (holder.getAdapterPosition() == tracks.size()) {
            holder.mV_order_status_line_top.setVisibility(View.VISIBLE);
            holder.mV_order_status_line.setVisibility(View.GONE);
        }else {
            holder.mV_order_status_line_top.setVisibility(View.VISIBLE);
            holder.mV_order_status_line.setVisibility(View.VISIBLE);
        }

        if ("01".equals(trackEntity)){
            holder.mV_order_status_line_top.setBackgroundResource(R.color.title_green);
            holder.mV_order_status_line.setBackgroundResource(R.color.title_green);
            holder.mTv_order_status_date.setBackgroundResource(R.color.title_green);
            holder.mTv_order_status_info.setBackgroundResource(R.color.title_green);
            holder.mIv_order_status_circle.setImageResource(R.mipmap.jszp_jiedian_yuanxin);
        }else {
            holder.mV_order_status_line_top.setBackgroundResource(R.color.darkgray);
            holder.mV_order_status_line.setBackgroundResource(R.color.darkgray);
            holder.mTv_order_status_date.setBackgroundResource(R.color.darkgray);
            holder.mTv_order_status_info.setBackgroundResource(R.color.darkgray);
            holder.mIv_order_status_circle.setImageResource(R.mipmap.jszp_jiedian_uphui);
        }
        holder.mTv_order_status_info.setText(trackEntity.getHandleDesc());
        holder.mTv_order_status_date.setText(trackEntity.getHandleTime());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }


    /**
     * 更新数据
     *
     * @param tracks
     */
    public void updataListUI(List<JszpOrderTrackEntity> tracks) {
        tracks.clear();
        tracks.addAll(tracks);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mV_order_status_line_top;
        public ImageView mIv_order_status_circle;
        public View mV_order_status_line;
        public TextView mTv_order_status_info;
        public TextView mTv_order_status_date;

        public ViewHolder(View itemView) {
            super(itemView);
            mV_order_status_line_top = itemView.findViewById(R.id.v_order_status_line_top);
            mIv_order_status_circle = itemView.findViewById(R.id.iv_order_status_circle);
            mV_order_status_line = itemView.findViewById(R.id.v_order_status_line);
            mTv_order_status_info = itemView.findViewById(R.id.tv_order_status_info);
            mTv_order_status_date = itemView.findViewById(R.id.tv_order_status_date);
        }
    }
}
