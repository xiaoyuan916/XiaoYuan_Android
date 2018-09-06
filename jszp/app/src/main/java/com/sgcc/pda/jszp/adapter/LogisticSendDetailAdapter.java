package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesDetsItem;

import java.util.List;

/**
 * 物流派车   线路详情
 *
 * @param <T>
 */
public class LogisticSendDetailAdapter<T extends LogisticsDistAutoesDetsItem> extends BaseViewHolderManager<T> {
    private Context context;
    private List<LogisticsDistAutoesDetsItem> mList;

    public LogisticSendDetailAdapter(Context context, List<LogisticsDistAutoesDetsItem> mList) {
        this.context = context;
        this.mList = mList;
    }

    private DeliveryConfirmDetailItemAdapter.CountNotifiCallBack countNotifiCallBack;

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        View dottedLineBotttom = getView(baseViewHolder, R.id.dotted_line_bottom);
        View dottedLineTop = getView(baseViewHolder, R.id.dotted_line_top);
        TextView tvAddress = getView(baseViewHolder, R.id.tv_address);
        TextView tvCount = getView(baseViewHolder, R.id.tv_count);
        TextView tvReturnCount = getView(baseViewHolder, R.id.tv_return_count);
        ImageView ivDp = getView(baseViewHolder, R.id.iv_dp);


        tvAddress.setText(t.getDpName());
        tvCount.setText(String.valueOf(t.getQty()));
        tvReturnCount.setText(t.getReturnQty() + "");

        //状态大于0501（正向已签收）标识已经到达到该直配点
       if(!t.isArrived()) {
           //未到达到该直配点
           dottedLineTop.setBackground(context.getResources().getDrawable(R.drawable.shape_yoko_xuxian));
           dottedLineBotttom.setBackground(context.getResources().getDrawable(R.drawable.shape_yoko_xuxian));
           ivDp.setBackgroundResource(R.drawable.jiedian_downhui);
       }else{
           //已到达该支配点
           dottedLineTop.setBackground(context.getResources().getDrawable(R.drawable.shape_yoko_xuxian_green));
           ivDp.setBackgroundResource(R.drawable.jiedian_down);
           if(baseViewHolder.getAdapterPosition()!=mList.size()-1){
               //不是最后一条
               if(mList.get(baseViewHolder.getAdapterPosition()+1).isArrived()){
                   dottedLineBotttom.setBackground(context.getResources().getDrawable(R.drawable.shape_yoko_xuxian_green));
               }else{
                   dottedLineBotttom.setBackground(context.getResources().getDrawable(R.drawable.shape_yoko_xuxian));
               }
           }
       }

        if (getCountNotifiCallBack() != null) {
            if (baseViewHolder.getAdapterPosition() == getCountNotifiCallBack().getcount() - 1) {
                dottedLineBotttom.setVisibility(View.GONE);
            } else {
                dottedLineBotttom.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_logistic_order;
    }

    public DeliveryConfirmDetailItemAdapter.CountNotifiCallBack getCountNotifiCallBack() {
        return countNotifiCallBack;
    }

    public void setCountNotifiCallBack(DeliveryConfirmDetailItemAdapter.CountNotifiCallBack countNotifiCallBack) {
        this.countNotifiCallBack = countNotifiCallBack;
    }
}
