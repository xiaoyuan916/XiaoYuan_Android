package com.sgcc.pda.jszp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.fragment.DeviceInfoFragment;
import com.sgcc.pda.jszp.fragment.DeviceStateFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class QueryDeviceActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_device)
    ImageView ivDevice;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_num)
    TextView tvDeviceNum;
    @BindView(R.id.tv_device_describle)
    TextView tvDeviceDescrible;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.v_indicator0)
    View vIndicator0;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.v_indicator1)
    View vIndicator1;


    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int index = 0;
    private TextView tv_sleected;
    private View v_selected;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_device;
    }

    @Override
    public void initView() {

        tv_sleected = tvInfo;
        v_selected = vIndicator0;
        tvTitle.setText("补库入库");
        fragments.add(new DeviceInfoFragment());
        fragments.add(new DeviceStateFragment());
        switchFragment(0);



    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.rl_info, R.id.rl_state})
    public void onViewClicked(View view) {
        tv_sleected.setTextColor(getResources().getColor(R.color.tv_black));
        v_selected.setVisibility(View.GONE);

        switch (view.getId()) {
            case R.id.rl_info:
                if (index != 0) {
                    tv_sleected = tvInfo;
                    v_selected = vIndicator0;
                    index = 0;
                    switchFragment(0);
                }
                break;
            case R.id.rl_state:
                if (index != 1) {
                    tv_sleected = tvState;
                    v_selected = vIndicator1;
                    index = 1;
                    switchFragment(1);
                }
                break;
        }
        tv_sleected.setTextColor(getResources().getColor(R.color.title_green));
        v_selected.setVisibility(View.VISIBLE);
    }

    public void switchFragment(int position) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == position) {
                if (fragment.isAdded()) {
                    fragmentTransaction.show(fragment);
                } else {
                    fragmentTransaction.add(R.id.fl_content, fragment);
                }
            } else {
                if (fragment.isAdded()) {
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        fragmentTransaction.commit();
    }


}
