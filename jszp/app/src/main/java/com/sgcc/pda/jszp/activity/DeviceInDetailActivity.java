package com.sgcc.pda.jszp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.fragment.ScanOutFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceInDetailActivity extends BaseActivity implements ScanOutFragment.OnCountChangedListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.tv_in_task_num)
    TextView tvInTaskNum;
    @BindView(R.id.tv_device_describle)
    TextView tvDeviceDescrible;
    @BindView(R.id.tv_task_count)
    TextView tvTaskCount;
    @BindView(R.id.tv_saoma)
    TextView tvSaoma;
    @BindView(R.id.v_indicator0)
    View vIndicator0;
    @BindView(R.id.tv_xiangzi)
    TextView tvXiangzi;
    @BindView(R.id.v_indicator1)
    View vIndicator1;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int index = 0;
    private TextView tv_sleected;
    private View v_selected;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_in_detail;
    }

    @Override
    public void initView() {
        tv_sleected = tvSaoma;
        v_selected = vIndicator0;
        tvTitle.setText("补库入库");
        fragments.add(new ScanOutFragment(0, MyComment.SCAN_DEVICE_IN, this));
        fragments.add(new ScanOutFragment(1, MyComment.SCAN_DEVICE_IN, this));
        fragments.add(new ScanOutFragment(2, MyComment.SCAN_DEVICE_IN, this));
        switchFragment(0);

        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.rl_saoma, R.id.rl_xiangzi})
    public void onViewClicked(View view) {
        tv_sleected.setTextColor(getResources().getColor(R.color.tv_black));
        v_selected.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.rl_saoma:
                if (index != 0) {
                    tv_sleected = tvSaoma;
                    v_selected = vIndicator0;
                    index = 0;
                    switchFragment(0);
                }

                break;
            case R.id.rl_xiangzi:
                if (index != 1) {
                    tv_sleected = tvXiangzi;
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


    @Override
    public void onCountChanged(int count) {
        if (count == 0) btSure.setVisibility(View.GONE);
        else
            btSure.setText("确定入库(" + count + ")");
    }
}
