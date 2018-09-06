package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.AutoDocsItem;
/**
 * 物流派车  选择车牌号  司机姓名
 */
public class LogisticSendChooseDriveAdapter<T extends AutoDocsItem> extends BaseViewHolderManager<T> {

    Context context;

    public LogisticSendChooseDriveAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, T t) {
        TextView tvName = getView(baseViewHolder, R.id.tv_name);
        TextView tv_dun = getView(baseViewHolder, R.id.tv_dun);
        tvName.setText(t.getAutoBrandNo()+"/"+t.getStaffName()+"/"+t.getPhoneNo());
        tv_dun.setText(t.getLoaded()+"T");
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_logic_send_choose_drive;
    }
}
