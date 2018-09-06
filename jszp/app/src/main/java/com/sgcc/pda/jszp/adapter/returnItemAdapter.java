package com.sgcc.pda.jszp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.util.JzspConstants;

public class ReturnItemAdapter<T extends IoTaskDets> extends BaseViewHolderManager<T> {
    private onChildClickCallBack onChildClickCallBack;

    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, final T t) {
        ImageView ivReturnType = getView(baseViewHolder, R.id.iv_return_type);
        ImageView ivSaoma = getView(baseViewHolder, R.id.iv_saoma);
        ImageView ivType = getView(baseViewHolder, R.id.iv_type);
        TextView tvTaskNum = getView(baseViewHolder, R.id.tv_task_num);
        TextView tvDeviceName = getView(baseViewHolder, R.id.tv_device_name);
        TextView tvPlanNum = getView(baseViewHolder, R.id.tv_plan_num);
        TextView tvRealNum = getView(baseViewHolder, R.id.tv_real_num);
        LinearLayout llContent = getView(baseViewHolder, R.id.ll_content);
        tvTaskNum.setText(t.getPlanDetNo()+"");
        tvDeviceName.setText(t.getEquipDesc());
        tvPlanNum.setText(String.valueOf(t.getQty()) + "只");
        if (t.getRealCount() > 0) {
            tvRealNum.setText(String.valueOf(t.getRealCount()) + "只");
        } else {
            tvRealNum.setText("请输入实际数量");
        }
        switch (t.getEquipCateg()) {
            case JzspConstants.Device_Category_DianNengBiao:
                ivType.setImageResource(R.drawable.diannengf);
                break;
            case JzspConstants.Device_Category_CaiJiZhongDuan:
                ivType.setImageResource(R.drawable.caijiqif);
                break;
            case JzspConstants.Device_Category_ZhouZhuanXiang:
                ivType.setImageResource(R.mipmap.zhouzhuanxiangf);
                break;
            case JzspConstants.Device_Category_TongXunMoKuai:
                ivType.setImageResource(R.drawable.tongxunf);
                break;
            case JzspConstants.Device_Category_DianLiuHuGanQi:
            case JzspConstants.Device_Category_DianYaHuGanQi:
            case JzspConstants.Device_Category_ZuHeHuGanQi:
                ivType.setImageResource(R.drawable.huganqif);
                break;
            default:
                ivType.setImageResource(R.drawable.diannengf);
                break;
        }

        ivReturnType.setSelected(t.getQty() == t.getRealCount());
        if (t.getReturnType() != null) {
            switch (t.getReturnType()) {
                case JzspConstants.Return_Type_ZhouZhuanXiang:
                    //空周转箱
                    ivReturnType.setBackgroundResource(R.drawable.select_return_type_zhouzhuanxiang);
                    break;
                case JzspConstants.Return_Type_FenJianBiao:
                    //分拣表
                    ivReturnType.setBackgroundResource(R.drawable.select_return_type_fenjian);
                    break;
                case JzspConstants.Return_Type_ChaoQiBiao:
                    //超期表
                    ivReturnType.setBackgroundResource(R.drawable.select_return_type_chaoqi);
                    break;
                case JzspConstants.Return_Type_Other:
                    //其他
                    ivReturnType.setBackgroundResource(R.drawable.select_return_type_other);
                    break;
            }
        }
        ivSaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onChildClickCallBack != null)
                    onChildClickCallBack.onSomaClick(baseViewHolder);
            }
        });
        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onChildClickCallBack != null)
                    onChildClickCallBack.onItemClick(baseViewHolder);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_return_layout;
    }


    public ReturnItemAdapter.onChildClickCallBack getOnChildClickCallBack() {
        return onChildClickCallBack;
    }

    public void setOnChildClickCallBack(ReturnItemAdapter.onChildClickCallBack onChildClickCallBack) {
        this.onChildClickCallBack = onChildClickCallBack;
    }

    public interface onChildClickCallBack {

        void onSomaClick(BaseViewHolder baseViewHolder);

        void onItemClick(BaseViewHolder baseViewHolder);


    }
}
