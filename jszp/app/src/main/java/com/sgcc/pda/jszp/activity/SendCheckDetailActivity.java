package com.sgcc.pda.jszp.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;
import com.sgcc.pda.jszp.bean.TaskConfirmRequestEntity;
import com.sgcc.pda.jszp.bean.TaskItemResultEntity;
import com.sgcc.pda.jszp.bean.TaskLogisticsDetailRequestEntity;
import com.sgcc.pda.jszp.bean.TaskLogisticsDetailResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

public class SendCheckDetailActivity extends BaseActivity {

    public static final int GET_CONFIRM_WHAT = 4003;//确认出发
    public static final int GET_LOGISTICS_LIST_WHAT = 4002;//请求成功码

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_sure)
    Button btSure;

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.tv_car_no)
    TextView tvCarNo;
    @BindView(R.id.tv_driver)
    TextView tvDriver;
    @BindView(R.id.tv_box_num)
    TextView tvBoxNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_CONFIRM_WHAT://发车成功
                    Toast.makeText(SendCheckDetailActivity.this, "发车成功!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                    break;
                case GET_LOGISTICS_LIST_WHAT:
                    TaskLogisticsDetailResultEntity mLogisticsDistAuto = (TaskLogisticsDetailResultEntity) msg.obj;
                    initLogisticsData(mLogisticsDistAuto.getLogisticsDistAuto());
                    break;
            }
        }
    };

    //给物流出发详情赋值
    private void initLogisticsData(LogisticsDistAutoesItem mLogisticsDistAuto) {
        if (null != mLogisticsDistAuto.getTaskNo()) {
            tvTaskNo.setText("配送任务：" + mLogisticsDistAuto.getTaskNo());
        }
        if (null != mLogisticsDistAuto.getAutoBrandNo()) {
            tvCarNo.setText("车辆信息：" + mLogisticsDistAuto.getAutoBrandNo());
        }
        if (null != mLogisticsDistAuto.getStaffName()) {
            tvDriver.setText("司机信息：" + mLogisticsDistAuto.getStaffName() + " " + mLogisticsDistAuto.getPhoneNo());
        }

        tvBoxNum.setText("设备箱数：" + mLogisticsDistAuto.getBoxQty() + "箱");
        tvAddress.setText(mLogisticsDistAuto.getDpName());
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_send_check_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText("物流出发");

    }

    private String distAutoId = "";
    private String taskNo = "";

    @Override
    public void initData() {
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra("mTaskItemResultData");
        //强转Serializable
        if (serializable instanceof TaskItemResultEntity.LogisticsDistAutoes) {
            TaskItemResultEntity.LogisticsDistAutoes mTaskItemResultEntity = (TaskItemResultEntity.LogisticsDistAutoes) serializable;
            distAutoId = mTaskItemResultEntity.getDistAutoId();
            taskNo = mTaskItemResultEntity.getTaskNo();
        }
        //获取网络列表
        getLogisticsListData();

    }

    //获取物流信息网络请求
    private void getLogisticsListData() {
        TaskLogisticsDetailRequestEntity mTaskLogisticsDetailRequestEntity = new TaskLogisticsDetailRequestEntity();
        mTaskLogisticsDetailRequestEntity.setDistAutoId(distAutoId);
        mTaskLogisticsDetailRequestEntity.setTaskNo(taskNo);
        JSZPOkgoHttpUtils.<TaskLogisticsDetailRequestEntity>postString(JSZPUrls.URL_POST_LOGISTICS_DETAIL_GO,
                this, mTaskLogisticsDetailRequestEntity,
                mHandler, GET_LOGISTICS_LIST_WHAT, TaskLogisticsDetailResultEntity.class);
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        TaskConfirmRequestEntity mTaskConfirmRequestEntity = new TaskConfirmRequestEntity();
        mTaskConfirmRequestEntity.setTaskNos(taskNo);
        mTaskConfirmRequestEntity.setUserId(JzspConstants.userId);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_POST_CONFIRM_GO,
                this, mTaskConfirmRequestEntity,
                mHandler, GET_CONFIRM_WHAT, BaseEntity.class);
    }


}
