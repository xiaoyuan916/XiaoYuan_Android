package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.util.JzspConstants;

/**
 * 出库查询  入库查询
 * @param <T>
 */
public class TaskQueryAdapter<T extends IoTaskDets> extends BaseViewHolderManager<T> {
    Context mContext;
    public TaskQueryAdapter(Context context){
        mContext = context;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvTaskNo = getView(baseViewHolder, R.id.tv_task_no);
        TextView tvType = getView(baseViewHolder, R.id.tv_type);
        ImageView ivDevice = getView(baseViewHolder, R.id.iv_device);
        TextView tvDesc = getView(baseViewHolder, R.id.tv_desc);
        TextView tvChukou = getView(baseViewHolder, R.id.tv_chukou);
        TextView tvNum = getView(baseViewHolder, R.id.tv_num);
        TextView tvDpName = getView(baseViewHolder, R.id.tv_dp_name);


        tvChukou.setText(t.getPlatformName());
        tvDesc.setText(t.getEquipDesc());
        tvDpName.setText("接收单位：" + t.getDpName());
        tvNum.setText(t.getQty() + "只/" + t.getBoxQty() + "箱");
        tvTaskNo.setText("出库任务：" + t.getTaskNo());
        tvType.setText(t.getNvType());
        if(!TextUtils.isEmpty(t.getNvType())){
            if(t.getNvType().contains("平库")){
                tvType.setBackground(mContext.getResources().getDrawable(R.drawable.bg_orange_stroke_coners));
                tvType.setTextColor(mContext.getResources().getColor(R.color.color_ls_status_o));
            }else{
                tvType.setBackground(mContext.getResources().getDrawable(R.drawable.bg_green_stroke_coners));
                tvType.setTextColor(mContext.getResources().getColor(R.color.color_ls_status_green));
            }
        }

        if(t.getEquipCateg()!=null) {
            switch (t.getEquipCateg()) {
                default:
                case JzspConstants.Device_Category_DianNengBiao:
                    //电能表
                    ivDevice.setBackgroundResource(R.drawable.dianneng);
                    break;
                case JzspConstants.Device_Category_DianYaHuGanQi:
                case JzspConstants.Device_Category_DianLiuHuGanQi:
                case JzspConstants.Device_Category_ZuHeHuGanQi:
                    //互感器
                    ivDevice.setBackgroundResource(R.drawable.huganqi);
                    break;
                case JzspConstants.Device_Category_CaiJiZhongDuan:
                    //采集器
                    ivDevice.setBackgroundResource(R.drawable.caijiqi);
                    break;
                case JzspConstants.Device_Category_TongXunMoKuai:
                    //通讯模块
                    ivDevice.setBackgroundResource(R.drawable.tongxun);
                    break;

            }
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_task_query;

    }
}
