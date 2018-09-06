package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.ExpressDistAutoesItem;
import com.sgcc.pda.jszp.util.JzspConstants;

public class ExpressConfirmAdapter<T extends ExpressDistAutoesItem> extends BaseViewHolderManager<T> {
    Context context;
    public ExpressConfirmAdapter(Context context) {
        this.context = context;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tv_task_no = getView(baseViewHolder, R.id.tv_task_no);
        TextView tv_time = getView(baseViewHolder, R.id.tv_time);
        TextView tv_express_company = getView(baseViewHolder, R.id.tv_express_company);
        TextView tv_addr = getView(baseViewHolder, R.id.tv_addr);
        TextView tv_num = getView(baseViewHolder, R.id.tv_num);
        TextView tv_status = getView(baseViewHolder, R.id.tv_status);

        tv_task_no.setText("配送任务："+t.getTaskNo());
        tv_express_company.setText("快递公司："+t.getCompanyName());
        tv_addr.setText("配送地点："+t.getDpName());


        //这个列表只会有三种状态  已出库 用户确认 已绑定
        //已出库状态在这个列表上就是待确认  点击之后进入快递确认页面
        //用户确认状态在这个列表上就是已确认  点击之后进入快递绑定页面
        //已绑定状态不可点击
        if(!TextUtils.isEmpty(t.getTaskStatus())) {
            switch (t.getTaskStatus()) {
                case JzspConstants.Delivery_Task_Status_yck:
                    //已出库
                    tv_num.setText("设备总数："+"-");
                    tv_status.setText("待确认");
                    tv_status.setTextColor(context.getResources().getColor(R.color.color_ls_status_o));
                    break;
                case JzspConstants.Delivery_Task_Status_yhqr:
                    //用户确认
                    tv_num.setText("设备总数："+t.getQty()+"只");
                    tv_status.setText("已确认");
                    tv_status.setTextColor(context.getResources().getColor(R.color.color_ls_status_green));
                    break;
                case JzspConstants.Delivery_Task_Status_ybd:
                    //已绑定
                    tv_num.setText("设备总数："+t.getQty()+"只");
                    tv_status.setText("已绑定");
                    tv_status.setTextColor(context.getResources().getColor(R.color.title_green));
                    break;
            }
        }
//        tv_time.setText();
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_express_confirm;
    }
}
