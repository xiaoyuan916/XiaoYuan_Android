package com.sgcc.pda.jszp.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.JszpOrderDetailsActivity;
import com.sgcc.pda.jszp.activity.QueryOrderListActivity;
import com.sgcc.pda.jszp.bean.JszpQueryDistAppsItemEntity;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.DateUtil;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/4
 */
public class JszpQueryOrderAdapter extends RecyclerView.Adapter<JszpQueryOrderAdapter.ViewHolder> {


    private  QueryOrderListActivity activity;
    private List<JszpQueryDistAppsItemEntity> distApps;

    public JszpQueryOrderAdapter(QueryOrderListActivity activity, List<JszpQueryDistAppsItemEntity> distApps) {
        this.distApps = distApps;
        this.activity=activity;
    }

    @NonNull
    @Override
    public JszpQueryOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_query_order, parent, false);
        return new JszpQueryOrderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JszpQueryOrderAdapter.ViewHolder holder, int position) {
        JszpQueryDistAppsItemEntity itemEntity = distApps.get(position);
        if (itemEntity == null) return;
        switch (itemEntity.getSgAppType()) {
            case JzspConstants.SG_APP_TYPE_EXPANSION_INDUSTRY:
                holder.mIv_query_order_state.setBackgroundResource(R.mipmap.jszp_expansion_industry);
                break;
            case JzspConstants.SG_APP_TYPE_ENGINEERING:
                holder.mIv_query_order_state.setBackgroundResource(R.mipmap.jszp_engineering);
                break;
            case JzspConstants.SG_APP_TYPE_MAINTAIN:
                holder.mIv_query_order_state.setBackgroundResource(R.mipmap.jszp_maintain);
                break;
        }
        holder.tv_query_order_number.setText("订单编号：" + itemEntity.getAppNo().trim());
        holder.mTv_query_order_date.setText(DateUtil.toYYmmDD(new Date(itemEntity.getDistDate())));
        holder.mTv_query_order_address.setText(itemEntity.getOrgName());
        holder.mTv_query_order_remaining_time.setText(itemEntity.getRemainingTime());
        holder.mTv_query_order_check.setText(itemEntity.getAppStatusLabel());
        initListener(holder,itemEntity);
    }

    /**
     * 点击事件
     * @param holder
     * @param itemEntity
     */
    private void initListener(ViewHolder holder,final JszpQueryDistAppsItemEntity itemEntity) {
        holder.mLl_query_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,JszpOrderDetailsActivity.class);
                intent.putExtra("appNo",itemEntity.getAppNo());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return distApps == null ? 0 : distApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLl_query_order;
        public ImageView mIv_query_order_state;
        public TextView mTv_query_order_date;
        public TextView mTv_query_order_address;
        public TextView mTv_query_order_qty;
        public TextView mTv_query_order_remaining_time;
        public TextView mTv_query_order_check;
        public TextView tv_query_order_number;

        public ViewHolder(View itemView) {
            super(itemView);
            mLl_query_order = itemView.findViewById(R.id.ll_query_order);
            mIv_query_order_state = itemView.findViewById(R.id.iv_query_order_state);
            mTv_query_order_date = itemView.findViewById(R.id.tv_query_order_date);
            mTv_query_order_address = itemView.findViewById(R.id.tv_query_order_address);
            mTv_query_order_qty = itemView.findViewById(R.id.tv_query_order_qty);
            mTv_query_order_remaining_time = itemView.findViewById(R.id.tv_query_order_remaining_time);
            mTv_query_order_check = itemView.findViewById(R.id.tv_query_order_check);
            tv_query_order_number = itemView.findViewById(R.id.tv_query_order_number);

        }
    }
}
