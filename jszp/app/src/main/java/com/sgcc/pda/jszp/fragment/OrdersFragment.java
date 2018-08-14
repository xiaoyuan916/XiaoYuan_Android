package com.sgcc.pda.jszp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.OrderDetailActivity;
import com.sgcc.pda.jszp.adapter.orderItemAdapter;
import com.sgcc.pda.jszp.bean.OrderItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrdersFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    BaseItemAdapter orderAdapter;
    private Context context;
    ArrayList<OrderItem> orders;


    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListener();
        initData();
    }

    private void initData() {
    }

    private void initListener() {

    }

    public void initView() {
        rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOrders.setNestedScrollingEnabled(false);
        orders = new ArrayList<>();
        orders.add(new OrderItem(true,"124124124155",1,"浦东区供电公司",20,3000));
        orders.add(new OrderItem(false,"124124124155",2,"浦东区供电公司",22,3000));
        orders.add(new OrderItem(false,"124124124155",3,"浦东区供电公司",20,3000));
        orders.add(new OrderItem(false,"124124124155",1,"浦东区供电公司",25,3000));
        orders.add(new OrderItem(false,"124124124155",2,"浦东区供电公司",20,3000));
        orders.add(new OrderItem(false,"124124124155",2,"浦东区供电公司",20,3000));
        orders.add(new OrderItem(false,"124124124155",2,"浦东区供电公司",20,3000));
        orders.add(new OrderItem(false,"124124124155",2,"浦东区供电公司",20,3000));
        orders.add(new OrderItem(false,"124124124155",2,"浦东区供电公司",20,3000));
        orderAdapter = new BaseItemAdapter();
        LayoutInflater lif=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headview=lif.inflate(R.layout.head_orders,rvOrders,false);
        View footview=lif.inflate(R.layout.foot_orders,rvOrders,false);
        orderAdapter.addHeadView(headview);
        orderAdapter.addFootView(footview);
        orderItemAdapter<OrderItem> orderItemAdapter=new orderItemAdapter<OrderItem>(getContext());
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
                startActivity(new Intent(getContext(), OrderDetailActivity.class));
            }
        });

    }
}
