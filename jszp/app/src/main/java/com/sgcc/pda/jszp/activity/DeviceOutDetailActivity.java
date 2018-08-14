package com.sgcc.pda.jszp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.fragment.ScanOutFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceOutDetailActivity extends BaseActivity implements ScanOutFragment.OnCountChangedListener{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_out_task_num)
    TextView tvOutTaskNum;
    @BindView(R.id.tv_device_describle)
    TextView tvDeviceDescrible;
    @BindView(R.id.tv_task_count)
    TextView tvTaskCount;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_saoma)
    TextView tvSaoma;
    @BindView(R.id.v_indicator0)
    View vIndicator0;
    @BindView(R.id.rl_saoma)
    RelativeLayout rlSaoma;
    @BindView(R.id.tv_xiangzi)
    TextView tvXiangzi;
    @BindView(R.id.v_indicator1)
    View vIndicator1;
    @BindView(R.id.rl_xiangzi)
    RelativeLayout rlXiangzi;
    @BindView(R.id.tv_piliang)
    TextView tvPiliang;
    @BindView(R.id.v_indicator2)
    View vIndicator2;
    @BindView(R.id.rl_piliang)
    RelativeLayout rlPiliang;
    @BindView(R.id.bt_sure)
    Button btSure;

    ScanOutFragment sof;
    FrameLayout flContent;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.activity_device_out_detail;
    }

    @Override
    public void initView() {
        tv_sleected = tvSaoma;
        v_selected = vIndicator0;
        tvTitle.setText("设备出库");
        fragments.add(new ScanOutFragment(0, MyComment.SCAN_DEVICE_OUT,this));
        fragments.add(new ScanOutFragment(1,MyComment.SCAN_DEVICE_OUT,this));
        fragments.add(new ScanOutFragment(2,MyComment.SCAN_DEVICE_OUT,this));
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

    private int index = 0;
    private TextView tv_sleected;
    private View v_selected;

    @OnClick({R.id.rl_saoma, R.id.rl_xiangzi, R.id.rl_piliang})
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
            case R.id.rl_piliang:
                if (index != 2) {
                    tv_sleected = tvPiliang;
                    v_selected = vIndicator2;
                    index = 2;
                    switchFragment(2);
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
            btSure.setText("确定出库(" + count + ")");
    }
}
