package com.sgcc.pda.jszp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.ExpressBindItem;
import com.sgcc.pda.jszp.util.JszpUtils;

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

        et_express_no.setText(t.getPkgNo());


        if(t.getExpressDevs()==null || t.getExpressDevs().size()==0){
            rv.setVisibility(View.GONE);
            tv_device_num.setText("0");
        }else{
            rv.setVisibility(View.VISIBLE);
            tv_device_num.setText(""+t.getExpressDevs().size());
            //初始化列表
            rv.setLayoutManager(new LinearLayoutManager(context));
            ExpressBindDeviceAdapter<ExpressBindItem.ExpressDeviceItem> expressBindDeviceAdapter;
            expressBindDeviceAdapter = new ExpressBindDeviceAdapter(context,t.getExpressDevs());
            final BaseItemAdapter baseItemAdapter = new BaseItemAdapter();
            baseItemAdapter.register(ExpressBindItem.ExpressDeviceItem.class, expressBindDeviceAdapter);
            rv.setAdapter(baseItemAdapter);
            baseItemAdapter.setDataItems(t.getExpressDevs());
            //删除设备
            expressBindDeviceAdapter.setOnDeleteClickListener(new ExpressBindDeviceAdapter.OnDeleteClickListener() {
                @Override
                public void onDelete(int position) {
                    if(onExpressBindClickListener!=null){
                        onExpressBindClickListener.onDeleteDevice(baseViewHolder.getItemPosition(),position);
                    }
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
                et_express_no.requestFocus();
                et_express_no.setSelection(et_express_no.getText().toString().length());
                JszpUtils.openInputSoft(context,et_express_no);
            }
        });

        et_express_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(onExpressBindClickListener!=null){
                    onExpressBindClickListener.onPackNoChange(baseViewHolder.getItemPosition(),editable.toString());
                }
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
        void onPackNoChange(int position,String str);
    }
}

