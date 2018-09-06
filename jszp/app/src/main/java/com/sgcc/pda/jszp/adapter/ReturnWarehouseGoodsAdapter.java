package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.ReturnWarehouseGoodsDetailActivity;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import java.io.Serializable;


//返程入库商品
public class ReturnWarehouseGoodsAdapter<T extends IoTaskDets> extends BaseViewHolderManager<T> {

    Context context;
    public ReturnWarehouseGoodsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, final T t) {
        TextView tvReturnTask = getView(baseViewHolder, R.id.tv_return_task);
        TextView tvTaskInfo = getView(baseViewHolder, R.id.tv_task_info);
        TextView tvGoodsCount = getView(baseViewHolder, R.id.tv_goods_count);
        TextView tvStatue = getView(baseViewHolder, R.id.tv_statue_text);
        ImageView ivState = getView(baseViewHolder, R.id.iv_state);
        LinearLayout llItem = getView(baseViewHolder, R.id.ll_item);

        tvReturnTask.setText(t.getPlanNo());
        tvTaskInfo.setText(t.getEquipCategLabel());
        tvGoodsCount.setText(""+t.getQty());
        switch (t.getStatus()){
            case "0":
                ivState.setImageResource(R.drawable.diannengf);
                tvStatue.setText("已下发");
                break;
            case "1":
                ivState.setImageResource(R.drawable.caijiqif);
                break;
            case "2":
                ivState.setImageResource(R.drawable.jizhongqif);
                break;
            case "3":
                ivState.setImageResource(R.drawable.tongxunf);
                break;
            case "4":
                ivState.setImageResource(R.drawable.huganqif);
                break;
            default:
                ivState.setImageResource(R.drawable.diannengf);
                break;
        }

        llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReturnWarehouseGoodsDetailActivity.class);
                intent.putExtra("mReturnWarehousGoodsResultEntity",
                        (Serializable) t);
                context.startActivity(intent);
            }
        });

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_return_warehouse_goods;
    }

}
