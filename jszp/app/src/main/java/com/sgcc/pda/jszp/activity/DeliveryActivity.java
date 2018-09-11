package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.BaseApplication;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.DeliveryConfirmResultEntity;
import com.sgcc.pda.jszp.bean.JSZPConfirmationDetailsRequestEntity;
import com.sgcc.pda.jszp.bean.JSZPConfirmationDetailsResultEntity;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;
import com.sgcc.pda.jszp.fragment.DeliveryConfirmDetailFragment;
import com.sgcc.pda.jszp.fragment.DriverDetailFragment;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JSZPProgressDialogUtils;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 装车确认列表--》装车确认详情
 */
public class DeliveryActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_Detail_WHAT=1001;
    public static final int Confirm_WHAT=1002;
    /**
     * 控件UI
     */
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

    private JSZPConfirmationDetailsResultEntity logisticsDistAutoesItem=new JSZPConfirmationDetailsResultEntity();//配送单详情

    private String distAutoId,taskNo;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_Detail_WHAT:
                    //详情数据
                    logisticsDistAutoesItem = (JSZPConfirmationDetailsResultEntity) msg.obj;

                    //初始化数据
                    initUiData();
                    break;
                case Confirm_WHAT:
                    //提交确认
//                    btOrderSure.setTextColor(getResources().getColor(R.color.darkgray));
//                    btOrderSure.setClickable(false);
//                    btOrderSure.setText("审核中");
                   BaseEntity baseEntity = (BaseEntity) msg.obj;
                    if ("1".equals(baseEntity.getRT_F())){
                        startActivity(new Intent(DeliveryActivity.this, DeliverySendResultActivity.class));
                        finish();
                    }
                    break;

            }
        }
    };
    /**
     * 两个二级界面的fragment
     */
    private DeliveryConfirmDetailFragment deliveryConfirmDetailFragment;
    private DriverDetailFragment driverDetailFragment;
    /**
     * 当前的任务状态
     */
    private String taskStatus;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_delivery;
    }

    @Override
    public void initView() {
        distAutoId = getIntent().getStringExtra("distAutoId");
        taskNo = getIntent().getStringExtra("taskNo");
        taskStatus =   getIntent().getStringExtra("taskStatus");
        tvTitle.setText("装车确认");
        initSubmitView();
    }

    /**
     * 初始化提交的UI逻辑和其他类型的任务区分出来
     */
    private void initSubmitView() {
        if (!TextUtils.isEmpty(taskStatus)|| JzspConstants.Delivery_Task_Status_yck.equals(taskStatus)){
           //提交按钮消失掉
            btOrderSure.setVisibility(View.GONE);
            ToastUtils.showToast(DeliveryActivity.this,"当前任务已经确认");
        }
    }

    @Override
    public void initData() {
        getDetailData();
    }

    @Override
    public void initListener() {

    }

    //初始化页面数据
    private void initUiData(){
        if (logisticsDistAutoesItem.getLogisticsDistAuto()==null)return;
        tvTaskNum.setText(logisticsDistAutoesItem.getLogisticsDistAuto().getTaskNo());
        tvBoxCount.setText(logisticsDistAutoesItem.getLogisticsDistAuto().getBoxQty()+"箱");
        deliveryConfirmDetailFragment = new DeliveryConfirmDetailFragment();
        driverDetailFragment = new DriverDetailFragment();
        fragments.add(deliveryConfirmDetailFragment);
        fragments.add(driverDetailFragment);
        switchFragment(0);
    }

    //获取详情数据
    private void getDetailData(){
        JSZPConfirmationDetailsRequestEntity requestEntity = new JSZPConfirmationDetailsRequestEntity();
        requestEntity.setDistAutoId(distAutoId);
        requestEntity.setTaskNo(taskNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_WAIT_CONFIRM_LOGISTICS_DETAIL,
                this,requestEntity,
                mHandler,GET_Detail_WHAT, JSZPConfirmationDetailsResultEntity.class);
    }

    //提交  确认
    private void confirm(){
        //校验当前check是否完整
       if (!driverDetailFragment.checkInfo()){
           ToastUtils.showToast(DeliveryActivity.this,"请核验司机信息！");
           return;
       }
        if (!deliveryConfirmDetailFragment.checkInfo()){
            ToastUtils.showToast(DeliveryActivity.this,"请核验配送单信息！");
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("taskNo",taskNo);

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GLOGISTICS_CONFIRM,
                this,map,
                mHandler,Confirm_WHAT, BaseEntity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSZPOkgoHttpUtils.cancelHttp(this);
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
                confirm();
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

    /**
     * 向下frament传递数据
     * @return
     */
    public JSZPConfirmationDetailsResultEntity getLogisticsDistAutoesItem() {
        return logisticsDistAutoesItem;
    }

    public void setLogisticsDistAutoesItem(JSZPConfirmationDetailsResultEntity logisticsDistAutoesItem) {
        this.logisticsDistAutoesItem = logisticsDistAutoesItem;
    }
}
