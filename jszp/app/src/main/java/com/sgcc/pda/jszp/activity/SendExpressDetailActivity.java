package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.ExpressConfirmResultEntity;
import com.sgcc.pda.jszp.bean.ExpressDistAutoesItem;
import com.sgcc.pda.jszp.bean.TaskConfirmRequestEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快递出发详情界面
 */
public class SendExpressDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.task_num)
    TextView taskNum;
    @BindView(R.id.task_room)
    TextView taskRoom;
    @BindView(R.id.task_receive_people)
    TextView taskReceivePeople;
    @BindView(R.id.task_phoneNum)
    TextView taskPhoneNum;
    @BindView(R.id.task_address)
    TextView taskAddress;
    @BindView(R.id.task_equipment_num)
    TextView taskEquipmentNum;
    @BindView(R.id.bt_sure)
    Button btSure;

    public static final int GET_DETAIL_WHAT = 1001;
    public static final int GET_CONFIRM_WHAT = 4003;//确认出发

    private String taskNo;

    private ExpressDistAutoesItem expressDistAutoesItem;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_CONFIRM_WHAT://发车成功
                    Toast.makeText(SendExpressDetailActivity.this, "发车成功!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                    break;
                case GET_DETAIL_WHAT:
                    //详情数据
                    ExpressConfirmResultEntity expressConfirmResultEntity = (ExpressConfirmResultEntity) msg.obj;
                    expressDistAutoesItem = expressConfirmResultEntity.getExpressDistAuto();
                    initViewData();
                    break;

            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_send_express_detail;
    }

    @Override
    public void initView() {
        tvTitle.setText("快递出发");
        taskNo = getIntent().getStringExtra("taskNo");
    }

    @Override
    public void initData() {
        getDetailData();
    }

    //界面赋值
    private void initViewData() {
        taskNum.setText(expressDistAutoesItem.getTaskNo());
        taskRoom.setText(expressDistAutoesItem.getDpName());
        taskReceivePeople.setText(expressDistAutoesItem.getRcver());
        taskPhoneNum.setText(expressDistAutoesItem.getMobile());
        taskAddress.setText(expressDistAutoesItem.getDpAddress());
        taskEquipmentNum.setText(expressDistAutoesItem.getQty() + "只");
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        TaskConfirmRequestEntity mTaskConfirmRequestEntity = new TaskConfirmRequestEntity();
        mTaskConfirmRequestEntity.setTaskNos(taskNo);
//        mTaskConfirmRequestEntity.setUserId(JzspConstants.userId);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_POST_CONFIRM_GO,
                this, mTaskConfirmRequestEntity,
                mHandler, GET_CONFIRM_WHAT,BaseEntity.class);
    }

    /**
     * 获取物流派车列表数据
     */
    private void getDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("taskNo", taskNo);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_WAIT_START_EXPRESS_TASK,
                this, map,
                mHandler, GET_DETAIL_WHAT, ExpressConfirmResultEntity.class);
    }

}

