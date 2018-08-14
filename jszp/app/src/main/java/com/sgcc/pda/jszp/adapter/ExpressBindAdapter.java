package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceItem;
import com.sgcc.pda.jszp.bean.ExpressBindItem;
import com.sgcc.pda.jszp.bean.TaskItem;

import java.util.ArrayList;

/**
 * 快递绑定列表
 * @param <T>
 */
public class ExpressBindAdapter<T extends ExpressBindItem> extends BaseViewHolderManager<T> {
    Context context;
    int index = -1;
    private OnExpressBindClickListener onExpressBindClickListener;
    public ExpressBindAdapter(Context context) {
        this.context = context;
    }
    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder,final T t) {
        final EditText et_express_no = getView(baseViewHolder, R.id.et_express_no);
        ImageView iv_edit = getView(baseViewHolder, R.id.iv_edit);
        TextView tv_device_num = getView(baseViewHolder, R.id.tv_device_num);
        ImageView iv_saomiao = getView(baseViewHolder, R.id.iv_saomiao);
        RecyclerView rv = getView(baseViewHolder, R.id.rv);


        //初始化列表
        rv.setLayoutManager(new LinearLayoutManager(context));
        ExpressBindDeviceAdapter<ExpressBindItem.ExpressDeviceItem> expressBindDeviceAdapter;
        expressBindDeviceAdapter = new ExpressBindDeviceAdapter(context);
        final BaseItemAdapter baseItemAdapter = new BaseItemAdapter();
        baseItemAdapter.register(ExpressBindItem.ExpressDeviceItem.class, expressBindDeviceAdapter);
        rv.setAdapter(baseItemAdapter);

        if(t.getDeviceList()==null || t.getDeviceList().size()==0){
            rv.setVisibility(View.GONE);
            tv_device_num.setText("0");
        }else{
            rv.setVisibility(View.VISIBLE);
            tv_device_num.setText(""+t.getDeviceList().size());
            baseItemAdapter.setDataItems(t.getDeviceList());
            //删除设备
            expressBindDeviceAdapter.setOnDeleteClickListener(new ExpressBindDeviceAdapter.OnDeleteClickListener() {
                @Override
                public void onDelete(int position) {
                    if(onExpressBindClickListener!=null){
                        onExpressBindClickListener.onDeleteDevice(baseViewHolder.getItemPosition(),position);
                    }
//                    t.getDeviceList().remove(position);
//                    baseItemAdapter.notifyDataSetChanged();

                }
            });
        }

        //扫描添加设备
        iv_saomiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onExpressBindClickListener!=null){
                    onExpressBindClickListener.onSaoma(baseViewHolder.getItemPosition());
                }
            }
        });
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.requestFocus();
            }
        });




    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_express_bind;
    }

    public OnExpressBindClickListener getOnExpressBindClickListener() {
        return onExpressBindClickListener;
    }

    public void setOnExpressBindClickListener(OnExpressBindClickListener onExpressBindClickListener) {
        this.onExpressBindClickListener = onExpressBindClickListener;
    }

    public static interface OnExpressBindClickListener {
        void onSaoma(int position);
        void onDeleteDevice(int parentPosition,int childPosition);
    }
}
