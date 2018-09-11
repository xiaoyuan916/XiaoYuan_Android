package com.sgcc.pda.jszp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.JszpOrderDistAppDetEntity;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/5
 */
public class JszpOrderDetailsAdapter extends RecyclerView.Adapter<JszpOrderDetailsAdapter.ViewHolder> {
    private List<JszpOrderDistAppDetEntity> distDistAppDets = new ArrayList<>();

    @NonNull
    @Override
    public JszpOrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new JszpOrderDetailsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JszpOrderDetailsAdapter.ViewHolder holder, int position) {
        JszpOrderDistAppDetEntity distAppDetEntity = distDistAppDets.get(position);
        if (distAppDetEntity == null) return;
        if ("0".equals(distAppDetEntity.getIsReturnSend())) {
            switch (distAppDetEntity.getEquipCateg()) {
                case JzspConstants.Device_Category_DianNengBiao:
                    holder.mIv_order_detail.setImageResource(R.drawable.dianneng);
                    break;
                case JzspConstants.Device_Category_CaiJiZhongDuan:
                    holder.mIv_order_detail.setImageResource(R.drawable.caijiqi);
                    break;
                case JzspConstants.Device_Category_ZhouZhuanXiang:
                    holder.mIv_order_detail.setImageResource(R.drawable.zhouzhuan);
                    break;
                case JzspConstants.Device_Category_TongXunMoKuai:
                    holder.mIv_order_detail.setImageResource(R.drawable.tongxun);
                    break;
                case JzspConstants.Device_Category_DianLiuHuGanQi:
                case JzspConstants.Device_Category_DianYaHuGanQi:
                case JzspConstants.Device_Category_ZuHeHuGanQi:
                    holder.mIv_order_detail.setImageResource(R.drawable.huganqi);
                    break;
                default:
                    holder.mIv_order_detail.setImageResource(R.drawable.dianneng);
                    break;
            }
        } else {
            switch (distAppDetEntity.getEquipCateg()) {
                case JzspConstants.Device_Category_DianNengBiao:
                    holder.mIv_order_detail.setImageResource(R.drawable.diannengf);
                    break;
                case JzspConstants.Device_Category_CaiJiZhongDuan:
                    holder.mIv_order_detail.setImageResource(R.drawable.caijiqif);
                    break;
                case JzspConstants.Device_Category_ZhouZhuanXiang:
                    holder.mIv_order_detail.setImageResource(R.mipmap.zhouzhuanxiangf);
                    break;
                case JzspConstants.Device_Category_TongXunMoKuai:
                    holder.mIv_order_detail.setImageResource(R.drawable.tongxunf);
                    break;
                case JzspConstants.Device_Category_DianLiuHuGanQi:
                case JzspConstants.Device_Category_DianYaHuGanQi:
                case JzspConstants.Device_Category_ZuHeHuGanQi:
                    holder.mIv_order_detail.setImageResource(R.drawable.huganqif);
                    break;
                default:
                    holder.mIv_order_detail.setImageResource(R.drawable.diannengf);
                    break;
            }
        }

        holder.mTv_order_detail_info.setText(distAppDetEntity.getEquipDesc());
        holder.mTv_order_detail_qty.setText(String.valueOf(distAppDetEntity.getQty()) + "只");
    }

    @Override
    public int getItemCount() {
        return distDistAppDets.size();
    }

    public void updataListUI(List<JszpOrderDistAppDetEntity> distDistAppDets) {
        if (distDistAppDets==null||distDistAppDets.size()==0)return;
        this.distDistAppDets.clear();
        this.distDistAppDets.addAll(distDistAppDets);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIv_order_detail;
        public TextView mTv_order_detail_info;
        public TextView mTv_order_detail_qty;

        public ViewHolder(View itemView) {
            super(itemView);
            mIv_order_detail = itemView.findViewById(R.id.iv_order_detail);
            mTv_order_detail_info = itemView.findViewById(R.id.tv_order_detail_info);
            mTv_order_detail_qty = itemView.findViewById(R.id.tv_order_detail_qty);
        }
    }
}
