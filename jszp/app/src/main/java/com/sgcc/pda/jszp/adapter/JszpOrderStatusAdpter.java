package com.sgcc.pda.jszp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.JszpOrderTrackEntity;
import com.sgcc.pda.jszp.fragment.JszpOrderStatusFragment;
import com.sgcc.pda.sdk.utils.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/5
 */
public class JszpOrderStatusAdpter extends RecyclerView.Adapter<JszpOrderStatusAdpter.ViewHolder> {
    private Activity context;
    private String greenStrs[]  = {"00", "01", "02", "03",
           "035", "04", "045", "05", "06", "07", "08"};
    private String grayStrs[] = {"085", "09", "10", "11", "12", "13", "98", "99"};
    List<String>  greenStrList = Arrays.asList(greenStrs);
    List<String>  grayStrList = Arrays.asList(grayStrs);

    /**
     * LIST信息
     */
    List<JszpOrderTrackEntity> tracks = new ArrayList<>();

    public JszpOrderStatusAdpter(Activity activity) {
        this.context = activity;
    }

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
            holder.mV_order_status_line_top.setVisibility(View.INVISIBLE);
            holder.mV_order_status_line.setVisibility(View.VISIBLE);
        } else if (holder.getAdapterPosition() == tracks.size()) {
            holder.mV_order_status_line_top.setVisibility(View.VISIBLE);
            holder.mV_order_status_line.setVisibility(View.INVISIBLE);
        } else {
            holder.mV_order_status_line_top.setVisibility(View.VISIBLE);
            holder.mV_order_status_line.setVisibility(View.VISIBLE);
        }

        if (greenStrList.contains(trackEntity.getAppDetStatus())) {
            holder.mV_order_status_line_top.setBackgroundResource(R.color.title_green);
            holder.mV_order_status_line.setBackgroundResource(R.color.title_green);
            holder.mTv_order_status_date.setTextColor(context.getResources().getColor(R.color.title_green));
            holder.mTv_order_status_info.setTextColor(context.getResources().getColor(R.color.title_green));
            holder.mIv_order_status_circle.setImageResource(R.mipmap.jszp_jiedian_yuanxin);
        } else {
            holder.mV_order_status_line_top.setBackgroundResource(R.color.darkgray);
            holder.mV_order_status_line.setBackgroundResource(R.color.darkgray);
            holder.mTv_order_status_date.setTextColor(context.getResources().getColor(R.color.darkgray));
            holder.mTv_order_status_info.setTextColor(context.getResources().getColor(R.color.darkgray));
            holder.mIv_order_status_circle.setImageResource(R.mipmap.jszp_jiedian_uphui);
        }
        holder.mTv_order_status_info.setText(trackEntity.getHandleDesc());
        holder.mTv_order_status_date.setText(DateUtil.toYYmmDD(new Date(trackEntity.getHandleTime())));
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
        if (tracks == null || tracks.size() == 0) return;
        this.tracks.clear();
        this.tracks.addAll(tracks);
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
