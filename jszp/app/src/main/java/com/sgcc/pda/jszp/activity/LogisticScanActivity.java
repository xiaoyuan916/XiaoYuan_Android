package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LogisticScanActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_3t)
    TextView tv3t;
    @BindView(R.id.tv_5t)
    TextView tv5t;
    @BindView(R.id.tv_8t)
    TextView tv8t;
    @BindView(R.id.tv_send_all)
    TextView tvSendAll;
    @BindView(R.id.tv_not_send)
    TextView tvNotSend;
    @BindView(R.id.tv_sended)
    TextView tvSended;


    private int car_type = 0;
    private int car_status = 0;
    private TextView selected_type;
    private TextView selected_status;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_logistic_scan;
    }

    @Override
    public void initView() {
        tvTitle.setText("查询");
        selected_type = tvAll;
        selected_status = tvSendAll;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_all, R.id.tv_3t, R.id.tv_5t, R.id.tv_8t, R.id.tv_send_all, R.id.tv_not_send, R.id.tv_sended, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                switchtype(0);
                break;
            case R.id.tv_3t:
                switchtype(1);
                break;
            case R.id.tv_5t:
                switchtype(2);
                break;
            case R.id.tv_8t:
                switchtype(3);
                break;
            case R.id.tv_send_all:
                switchstatus(0);
                break;
            case R.id.tv_not_send:
                switchstatus(1);
                break;
            case R.id.tv_sended:
                switchstatus(2);
                break;
            case R.id.bt_sure:
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }

    public void switchtype(int index) {
        if (index == car_type) return;
        selected_type.setTextColor(getResources().getColor(R.color.tv_black));
        selected_type.setBackgroundResource(R.drawable.bg_gray_coners1);
        car_type = index;
        switch (index) {
            case 0:
                selected_type = tvAll;
                break;
            case 1:
                selected_type = tv3t;
                break;
            case 2:
                selected_type = tv5t;
                break;
            case 3:
                selected_type = tv8t;
                break;
        }
        selected_type.setTextColor(getResources().getColor(R.color.darkgreen));
        selected_type.setBackgroundResource(R.drawable.bg_blue_coners);
    }

    public void switchstatus(int index) {
        if (index == car_status) return;
        car_status = index;
        selected_status.setTextColor(getResources().getColor(R.color.tv_black));
        selected_status.setBackgroundResource(R.drawable.bg_gray_coners1);
        switch (index) {
            case 0:
                selected_status = tvSendAll;
                break;
            case 1:
                selected_status = tvNotSend;
                break;
            case 2:
                selected_status = tvSended;
                break;
        }
        selected_status.setTextColor(getResources().getColor(R.color.darkgreen));
        selected_status.setBackgroundResource(R.drawable.bg_blue_coners);


    }


}
