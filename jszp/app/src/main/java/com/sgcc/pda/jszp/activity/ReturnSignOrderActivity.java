package com.sgcc.pda.jszp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.returnItemAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.ReturnItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ReturnSignOrderActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_returnorder)
    RecyclerView rvReturnOrder;

    BaseItemAdapter returnAdapter;
    ArrayList<ReturnItem> returnItems;
    int checkposition = -1;
    @BindView(R.id.bt_compelte)
    Button btCompelte;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_return_sign_order;
    }

    @Override
    public void initView() {
        tvTitle.setText("返程签收");
        ivRight.setVisibility(View.VISIBLE);

        rvReturnOrder.setLayoutManager(new LinearLayoutManager(this));
        returnAdapter = new BaseItemAdapter();
        returnItems = new ArrayList<>();
        returnItems.add(new ReturnItem(true, "201805081234567896", 0, "单相电能表", 30, 20));
        returnItems.add(new ReturnItem(false, "201805081234567896", 0, "1级三相费控智能电能表（远程—开关外 置）3X220/380V，1.5A", 30, 20));
        returnItems.add(new ReturnItem(false, "201805081234567896", 4, "互感器", 30, 20));
        returnItems.add(new ReturnItem(false, "201805081234567896", 3, "通讯模块", 30, 20));
        returnItems.add(new ReturnItem(false, "201805081234567896", 1, "采集器", 30, 20));
        returnItems.add(new ReturnItem(false, "201805081234567896", 2, "集中器", 30, 20));
        LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headview = lif.inflate(R.layout.head_return_sign_order, rvReturnOrder, false);
        View footview = lif.inflate(R.layout.foot_orders, rvReturnOrder, false);
        returnAdapter.addHeadView(headview);
        returnAdapter.addFootView(footview);
        returnItemAdapter ria = new returnItemAdapter<ReturnItem>();
        ria.setOnChildClickCallBack(new returnItemAdapter.onChildClickCallBack() {

            @Override
            public void onSomaClick(BaseViewHolder baseViewHolder) {
                checkposition = baseViewHolder.getItemPosition();
                Intent intent = new Intent(ReturnSignOrderActivity.this, ScanActivity.class);
                ReturnItem data = (ReturnItem) baseViewHolder.getItemData();
                intent.putExtra("type", MyComment.SCAN_RETURN_SIGN);
                intent.putExtra("sub_type", 2);
                intent.putExtra("tasknum", ((ReturnItem) baseViewHolder.getItemData()).getTasknum());
                intent.putExtra("plancount", data.getPlancount());
                startActivityForResult(intent, 101);
            }

            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                checkposition = baseViewHolder.getItemPosition();
                Intent intent = new Intent(ReturnSignOrderActivity.this, ModifierCountActivity.class);
                ReturnItem data = (ReturnItem) baseViewHolder.getItemData();
                intent.putExtra("plancount", data.getPlancount());
                startActivityForResult(intent, 102);
            }
        });
        returnAdapter.register(ReturnItem.class, ria);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.gray_divider));
        rvReturnOrder.addItemDecoration(divider);
        rvReturnOrder.setAdapter(returnAdapter);
        returnAdapter.setDataItems(returnItems);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101: {
                if (resultCode != RESULT_OK) return;
                int realcount = data.getIntExtra("realcount", 0);
                returnItems.get(checkposition - 1).setRealcount(realcount);
                returnItems.get(checkposition - 1).setSelected(true);
                returnAdapter.notifyItemChanged(checkposition);

                break;
            }
            case 102: {
                if (resultCode != RESULT_OK) return;
                int realcount = data.getIntExtra("realcount", 0);
                returnItems.get(checkposition - 1).setRealcount(realcount);
                returnItems.get(checkposition - 1).setSelected(true);
                returnAdapter.notifyItemChanged(checkposition);
                break;
            }
        }
    }


    @OnClick(R.id.bt_compelte)
    public void onViewClicked() {
        Intent intent = new Intent(this, SignForResultActivity.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void onIvRightClick(View v) {
        super.onIvRightClick(v);

    }
}
