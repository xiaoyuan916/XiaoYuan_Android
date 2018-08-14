package com.sgcc.pda.jszp.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.sendCheckDetailAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.OrderItem;
import com.sgcc.pda.jszp.bean.TaskItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SendCheckDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;

    BaseItemAdapter sendAdapter;
    List<OrderItem> SendItems;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_send_check_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText(R.string.ps5);
        TaskItem task = (TaskItem) getIntent().getSerializableExtra("task");
//        if(task!=null)SendItems=task.getOrderItems();
        if (task.getState() == 1) {
            btSure.setText("行程中");
            btSure.setTextColor(getResources().getColor(R.color.gray1));
            btSure.setClickable(false);
        }
        rvAddress.setLayoutManager(new LinearLayoutManager(this));
        sendAdapter = new BaseItemAdapter();
        SendItems = new ArrayList<>();
        SendItems.add(new OrderItem("浦口", 3));
        SendItems.add(new OrderItem("六合", 2));
        SendItems.add(new OrderItem("建邺", 1));
        SendItems.add(new OrderItem("雨花台", 0));
        SendItems.add(new OrderItem("中山陵", 0));
        sendCheckDetailAdapter sendCheckDetailAdapter = new sendCheckDetailAdapter(this);
        LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headview = lif.inflate(R.layout.head_send_check_detail, rvAddress, false);
        sendAdapter.addHeadView(headview);
        sendAdapter.register(OrderItem.class, sendCheckDetailAdapter);
        rvAddress.setAdapter(sendAdapter);
        sendAdapter.setDataItems(SendItems);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        Toast.makeText(this,"发车成功!",Toast.LENGTH_SHORT).show();
        finish();

    }
}
