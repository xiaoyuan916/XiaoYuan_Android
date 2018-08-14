package com.sgcc.pda.jszp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.ExpressBindAdapter;
import com.sgcc.pda.jszp.adapter.ExpressConfirmAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.DriverItem;
import com.sgcc.pda.jszp.bean.ExpressBindItem;
import com.sgcc.pda.jszp.bean.TaskItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快递绑定
 */
public class ExpressBindActivitiy extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.et_express_no)
    EditText et_express_no;

    BaseItemAdapter baseItemAdapter;
    ExpressBindAdapter<ExpressBindItem> expressBindAdapter;
    ArrayList<ExpressBindItem> expressBindItems;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_express_bind;
    }

    @Override
    public void initView() {
        et_express_no.clearFocus();
        //初始化列表
        rv.setLayoutManager(new LinearLayoutManager(this));
        baseItemAdapter = new BaseItemAdapter();
        expressBindItems = new ArrayList<>();

        expressBindAdapter = new ExpressBindAdapter(this);
        baseItemAdapter.register(ExpressBindItem.class, expressBindAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(1));
        rv.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(expressBindItems);
        //扫描添加设备
        expressBindAdapter.setOnExpressBindClickListener(new ExpressBindAdapter.OnExpressBindClickListener() {
            @Override
            public void onSaoma(int position) {
                Intent intent = new Intent(ExpressBindActivitiy.this, ScanActivity.class);
                intent.putExtra("type", MyComment.SCAN_EXPRESS_DEVICE);
                intent.putExtra("sub_type", 0);
                intent.putExtra("position",position);
                startActivityForResult(intent, 100);
            }


            @Override
            public void onDeleteDevice(int parentPosition, int childPosition) {
                expressBindItems.get(parentPosition).getDeviceList().remove(childPosition);
                baseItemAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initData() {
        tvTitle.setText("快递绑定");
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.iv_plus,R.id.iv_delete})
   void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_plus:
                //增加
                expressBindItems.add(new ExpressBindItem());
                baseItemAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_delete:
                //刪除
                expressBindItems.clear();
                baseItemAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case 100: {
                //扫描设备
                String number = data.getStringExtra("number");
                int position =  data.getIntExtra("position",0);
                ExpressBindItem expressBindItem=  expressBindItems.get(position);
                expressBindItem.addExpressDeviceItem(number,"扫描表");
                baseItemAdapter.notifyDataSetChanged();
            }
            break;
        }

    }
}
