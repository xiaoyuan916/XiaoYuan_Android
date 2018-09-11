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

import java.util.ArrayList;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/7
 */
public class JszpBoxRcallAdapter extends RecyclerView.Adapter<JszpBoxRcallAdapter.ViewHolder> {
    private List<JszpBoxRecallDetEntity> turnoverBoxRecallDets =new ArrayList<>();

    @NonNull
    @Override
    public JszpBoxRcallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box_recall, parent, false);
        return new JszpBoxRcallAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JszpBoxRcallAdapter.ViewHolder holder, final int position) {
        JszpBoxRecallDetEntity jszpBoxRecallDetEntity = turnoverBoxRecallDets.get(position);
        holder.mTvBoxCode.setText(jszpBoxRecallDetEntity.getBarCode());
        holder.mTvBoxInfo.setText(jszpBoxRecallDetEntity.getSortCodeLabel());
        holder.mIvDeleteBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnDeleteItemClickListener!=null){
                    mOnDeleteItemClickListener.onDeleteItemClick(position);
                }
            }
        });
    }

    /**
     * 声明一个删除的接口
     */
    private OnDeleteItemClickListener mOnDeleteItemClickListener;
    public interface OnDeleteItemClickListener{
        void onDeleteItemClick(int position);
    }
    public void setDeleteItemClickListener(OnDeleteItemClickListener listener) {
        mOnDeleteItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return turnoverBoxRecallDets==null?0:turnoverBoxRecallDets.size();
    }

    /**
     * 数据个更新
     * @param turnoverBoxRecallDets
     */
    public void updataUI(List<JszpBoxRecallDetEntity> turnoverBoxRecallDets) {
        this.turnoverBoxRecallDets.clear();
        this.turnoverBoxRecallDets.addAll(turnoverBoxRecallDets);
        notifyDataSetChanged();
    }

    public void addDataUI(List<JszpBoxRecallDetEntity> turnoverBoxRecallDets) {
        this.turnoverBoxRecallDets.addAll(turnoverBoxRecallDets);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvBoxCode;
        public TextView mTvBoxInfo;
        public ImageView mIvDeleteBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvBoxCode = itemView.findViewById(R.id.tv_box_code);
            mTvBoxInfo = itemView.findViewById(R.id.tv_box_info);
            mIvDeleteBox = itemView.findViewById(R.id.iv_delete_box);

        }
    }
}
