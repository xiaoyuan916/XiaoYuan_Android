package com.sgcc.pda.jszp.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;

public class DeliverySendResultActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.tv_dealyreturn)
    TextView tvDealyreturn;


    private int time = 5;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if (time == 0) finish();
            else {
                tvDealyreturn.setText(time + "s后返回首页");
                handler.sendEmptyMessageDelayed(1, 1000);
            }

        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_ps_send;
    }

    @Override
    public void initView() {
        tvTitle.setText("配送设备装车");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        handler.sendEmptyMessageDelayed(1, 1000);

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
        finish();
    }
}
