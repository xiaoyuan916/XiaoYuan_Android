package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SignForResultActivity extends BaseActivity {

    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_box_count)
    TextView tvBoxCount;
    @BindView(R.id.ll_form)
    LinearLayout llForm;
    @BindView(R.id.iv_sign_result)
    ImageView ivSignResult;
    @BindView(R.id.iv_success)
    ImageView ivSuccess;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private boolean success = false;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_sign_for_result;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        success = intent.getBooleanExtra("success", false);
        String orderNum=intent.getStringExtra("orderNum");
        String boxCount= intent.getStringExtra("boxCount");
        String address = intent.getStringExtra("address");
        if (success) {
            tvResult.setText("签收成功");
            ivSuccess.setImageResource(R.drawable.ic_duigou);
            ivSignResult.setImageResource(R.drawable.signsuccess);
        } else {
            tvResult.setText("签收失败");
            ivSuccess.setImageResource(R.drawable.ic_x);
            ivSignResult.setImageResource(R.drawable.signfailure);
        }
        tvOrderNum.setText(orderNum);
        tvBoxCount.setText(boxCount);
        tvAddress.setText(address);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.tv_complete)
    public void onViewClicked() {

        finish();
    }


}
