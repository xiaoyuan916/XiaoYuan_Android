package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.liuwan.customdatepicker.widget.CustomDatePicker;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.LogisticOrderAdapter;
import com.sgcc.pda.jszp.adapter.orderItemAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.OrderItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class LogisticDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_chexing)
    TextView tvChexing;
    @BindView(R.id.tv_car_num)
    TextView tvCarNum;
    @BindView(R.id.tv_driver_name)
    TextView tvDriverName;
    @BindView(R.id.tv_driver_phone)
    TextView tvDriverPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;

    BaseItemAdapter orderAdapter;
    List<OrderItem> data;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    CustomDatePicker customDatePicker;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_logistic_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText("派车");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("提交");

        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new BaseItemAdapter();
        data = new ArrayList<>();
        data.add(new OrderItem("南京", 300, 0));
        data.add(new OrderItem("六合分中心", 300, 1));
        data.add(new OrderItem("江宁供电", 300, 2));
        data.add(new OrderItem("南京供电", 300, 3));
        LogisticOrderAdapter<OrderItem> logisticOrderAdapter = new LogisticOrderAdapter<>();
        logisticOrderAdapter.setCountNotifiCallBack(new orderItemAdapter.CountNotifiCallBack() {
            @Override
            public int getcount() {
                return data.size();
            }
        });
        orderAdapter.register(OrderItem.class, logisticOrderAdapter);
        rvOrders.setAdapter(orderAdapter);
        orderAdapter.setDataItems(data);
        initDatePicker();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    @OnClick({R.id.rl_car_num, R.id.rl_driver_name, R.id.rl_driver_phone, R.id.rl_time})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, PickOptionActivity.class);
        switch (view.getId()) {
            case R.id.rl_car_num:
                startActivityForResult(intent, 100);
                break;
            case R.id.rl_driver_name:
                startActivityForResult(intent, 101);
                break;
            case R.id.rl_driver_phone:
                startActivityForResult(intent, 102);
                break;
            case R.id.rl_time:

                customDatePicker.show(tvTime.getText().toString());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        switch (requestCode) {
            case 100:
                break;
            case 101:
                break;
            case 102:
                break;
        }
    }

    private void initDatePicker() {
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvTime.setText(time);
            }
        }, "2018-01-01 00:00", "2020-12-30 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true);
        customDatePicker.setIsLoop(true);
        String now = sdf.format(new Date());
        tvTime.setText(now);
    }
}
