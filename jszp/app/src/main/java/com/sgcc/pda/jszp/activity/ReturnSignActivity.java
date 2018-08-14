package com.sgcc.pda.jszp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.orderItemAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.OrderItem;

import java.util.ArrayList;

import butterknife.BindView;

public class ReturnSignActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;

    BaseItemAdapter orderAdapter;
    ArrayList<OrderItem> orders;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_return_sign;
    }

    @Override
    public void initView() {
        ivRight.setVisibility(View.VISIBLE);
        tvTitle.setText("返程签收");
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        orders = new ArrayList<>();
        orders.add(new OrderItem(true, "124124124155", 1, "浦东区供电公司", 20, 3000));
        orders.add(new OrderItem(true, "124124124155", 2, "浦东区供电公司", 22, 3000));
        orders.add(new OrderItem(true, "124124124155", 3, "浦东区供电公司", 20, 3000));
        orders.add(new OrderItem(true, "124124124155", 1, "浦东区供电公司", 25, 3000));
        orders.add(new OrderItem(true, "124124124155", 2, "浦东区供电公司", 20, 3000));
        orderAdapter = new BaseItemAdapter();
        LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headview = lif.inflate(R.layout.head_return_sign, rvOrders, false);
        orderAdapter.addHeadView(headview);
        orderItemAdapter<OrderItem> orderItemAdapter = new orderItemAdapter<OrderItem>(this);
        orderItemAdapter.setReturn(true);
        orderItemAdapter.setCountNotifiCallBack(new orderItemAdapter.CountNotifiCallBack() {
            @Override
            public int getcount() {
                return orders.size();
            }
        });
        orderAdapter.register(OrderItem.class, orderItemAdapter);
        rvOrders.setAdapter(orderAdapter);
        orderAdapter.setDataItems(orders);
        orderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                startActivity(new Intent(ReturnSignActivity.this, ReturnSignOrderActivity.class));
            }
        });

    }

    @Override
    public void onIvRightClick(View v) {
        super.onIvRightClick(v);
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("type", MyComment.SCAN_RETURN_SIGN);
        intent.putExtra("sub_type", 1);
        startActivity(intent);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
