package com.sgcc.pda.jszp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceOutSubTaskItem;
import com.sgcc.pda.jszp.util.JzspConstants;

/**
 * 平库出库列表 三级
 *
 * 配送任务 --》接收单位--》出库任务
 * @param <T>
 */
public class DeviceOutSubAdapter<T extends DeviceOutSubTaskItem> extends BaseViewHolderManager<T> {


    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        ImageView ivDevice = getView(baseViewHolder, R.id.iv_device);
        TextView tvState = getView(baseViewHolder, R.id.tv_state);
        TextView tvOutTaskNum = getView(baseViewHolder, R.id.tv_out_task_num);
        TextView tvDescrible = getView(baseViewHolder, R.id.tv_describle);
        TextView tvCount = getView(baseViewHolder, R.id.tv_count);

        tvOutTaskNum.setText("出库任务：" + t.getPlanDetNo());
        tvDescrible.setText("设备码描述：" + t.getEquipDesc());
        tvCount.setText("出库数量：" + t.getFinishQty() + "/" + t.getQty());
        switch (t.getEquipCateg()) {
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
        tvState.setText(t.getStatusLabel());
        //目前接口中获取的列表只会有已下发和已完成状态  新建和代签收不会出现在列表中
        switch (t.getStatus()) {
            case "02":
                //已下发
                tvState.setBackgroundResource(R.drawable.bg_out_state1);
                break;
            case "03":
                //已完成
                tvState.setBackgroundResource(R.drawable.bg_out_state0);
                break;
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_device_out_sub;
    }
}
