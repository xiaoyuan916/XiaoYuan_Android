package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.fragment.DriverDetailFragment;
import com.sgcc.pda.jszp.fragment.OrdersFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DeliveryActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_task_num)
    TextView tvTaskNum;
    @BindView(R.id.tv_box_count)
    TextView tvBoxCount;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.v_indicator_left)
    View vIndicatorLeft;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.tv_driver_title)
    TextView tvDriverTitle;
    @BindView(R.id.v_indicator_right)
    View vIndicatorRight;
    @BindView(R.id.rl_driver)
    RelativeLayout rlDriver;
    @BindView(R.id.bt_order_sure)
    Button btOrderSure;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    public int getLayoutResId() {
        return R.layout.activity_delivery;
    }

    @Override
    public void initView() {
        tvTitle.setText("配送设备装车");
        fragments.add(new OrdersFragment());
        fragments.add(new DriverDetailFragment());
        switchFragment(0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.rl_order, R.id.rl_driver, R.id.bt_order_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_order:
                tvOrderTitle.setTextColor(getResources().getColor(R.color.title_green));
                vIndicatorLeft.setVisibility(View.VISIBLE);
                tvDriverTitle.setTextColor(getResources().getColor(R.color.tv_black));
                vIndicatorRight.setVisibility(View.GONE);
                switchFragment(0);
                break;
            case R.id.rl_driver:
                tvOrderTitle.setTextColor(getResources().getColor(R.color.tv_black));
                vIndicatorLeft.setVisibility(View.GONE);
                tvDriverTitle.setTextColor(getResources().getColor(R.color.title_green));
                vIndicatorRight.setVisibility(View.VISIBLE);
                switchFragment(1);
                break;
            case R.id.bt_order_sure:
                btOrderSure.setTextColor(getResources().getColor(R.color.darkgray));
                startActivity(new Intent(this, DeliverySendResultActivity.class));
                finish();

                break;
        }
    }

    public void switchFragment(int position) {
        //开启事务
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        //遍历集合
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == position) {
                //显示fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,显示
                    fragmentTransaction.show(fragment);
                } else {
                    //如果这个fragment没有被事务添加过,添加
                    fragmentTransaction.add(R.id.fl_content, fragment);
                }
            } else {
                //隐藏fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,隐藏
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        //提交事务
        fragmentTransaction.commit();
    }
}
