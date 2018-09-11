package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DeviceQueryResultEntity;
import com.sgcc.pda.jszp.bean.JSZPDeviceQueryRequestEntity;
import com.sgcc.pda.jszp.fragment.DeviceInfoFragment;
import com.sgcc.pda.jszp.fragment.DeviceStateFragment;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class QueryDeviceActivity extends BaseActivity {
    /**
     * 各种code
     */
    private static final int GET_EQUIP_INFO_WHAT = 9001;
    /**
     * 控件
     */
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

    /**
     * 扫描的单据code
     */
    private String number;
    /**
     * 两个详情的界面的fragment
     */
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int index = 0;
    private TextView tv_sleected;
    private View v_selected;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_EQUIP_INFO_WHAT:
                    DeviceQueryResultEntity obj = (DeviceQueryResultEntity) msg.obj;
                    updataUI(obj);
                    break;
            }
        }
    };

    /**
     * 刷新UI
     *
     * @param obj
     */
    private void updataUI(DeviceQueryResultEntity obj) {
        tvDeviceName.setText(obj.getAsset().getEquipCategLable());
        tvDeviceNum.setText(obj.getAsset().getEquipCode());
        tvDeviceDescrible.setText(obj.getAsset().getEquipDesc());
        switch (obj.getAsset().getEquipCateg()) {
            //对图标进行赋值
            case JzspConstants.Device_Category_DianNengBiao:
                //电能表
                ivDevice.setBackgroundResource(R.drawable.dianneng);
                break;
            case JzspConstants.Device_Category_DianYaHuGanQi:
            case JzspConstants.Device_Category_DianLiuHuGanQi:
            case JzspConstants.Device_Category_ZuHeHuGanQi:
                //互感器
               ivDevice.setBackgroundResource(R.drawable.huganqi);
                break;
            case JzspConstants.Device_Category_CaiJiZhongDuan:
                //采集器
               ivDevice.setBackgroundResource(R.drawable.caijiqi);
                break;
            case JzspConstants.Device_Category_TongXunMoKuai:
                //通讯模块
               ivDevice.setBackgroundResource(R.drawable.tongxun);
                break;
        }

        deviceInfoFragment.upDataUI(obj);

        if (obj.getAsset().getTracks() != null) {
            deviceStateFragment.setData(obj.getAsset().getTracks());
        }
    }

    /**
     * 展示的fragment
     */
    private DeviceInfoFragment deviceInfoFragment;
    private DeviceStateFragment deviceStateFragment;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_device;
    }

    /**
     * 初始化UI
     */
    @Override
    public void initView() {

        tv_sleected = tvInfo;
        v_selected = vIndicator0;
        tvTitle.setText("设备详情");
        deviceInfoFragment = new DeviceInfoFragment();
        deviceStateFragment = new DeviceStateFragment();
        fragments.add(deviceInfoFragment);
        fragments.add(deviceStateFragment);
        switchFragment(0);
    }

    /**
     * 初始化数据，并请求数据
     */
    @Override
    public void initData() {
        Intent intent = getIntent();
        number = intent.getStringExtra("number");
        obtainNetData();
    }

    /**
     * 获取设备详情
     */
    private void obtainNetData() {
        JSZPDeviceQueryRequestEntity requestEntity = new JSZPDeviceQueryRequestEntity();
        requestEntity.setBarCode(number);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_EQUIP_INFO,
                this, requestEntity,
                mHandler, GET_EQUIP_INFO_WHAT, DeviceQueryResultEntity.class);
    }

    @Override
    public void initListener() {

    }

    /**
     * 点击切换
     *
     * @param view
     */
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

    /**
     * fragment的切换
     *
     * @param position
     */
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
